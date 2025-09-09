SET NOCOUNT ON;
SET XACT_ABORT ON;

-- Eerst afhankelijke objecten (views/triggers)
IF OBJECT_ID('plm.vw_ProductOverview','V') IS NOT NULL DROP VIEW plm.vw_ProductOverview;
IF OBJECT_ID('plm.trg_Products_SetUpdatedAt','TR') IS NOT NULL DROP TRIGGER plm.trg_Products_SetUpdatedAt;

-- Dan tabellen in dependency-orde (children -> parents)
IF OBJECT_ID('plm.Comments','U') IS NOT NULL DROP TABLE plm.Comments;
IF OBJECT_ID('plm.ProductDocuments','U') IS NOT NULL DROP TABLE plm.ProductDocuments;
IF OBJECT_ID('plm.ProductVersions','U') IS NOT NULL DROP TABLE plm.ProductVersions;
IF OBJECT_ID('plm.ProductMaterials','U') IS NOT NULL DROP TABLE plm.ProductMaterials;
IF OBJECT_ID('plm.Products','U') IS NOT NULL DROP TABLE plm.Products;
IF OBJECT_ID('plm.Materials','U') IS NOT NULL DROP TABLE plm.Materials;
IF OBJECT_ID('plm.Suppliers','U') IS NOT NULL DROP TABLE plm.Suppliers;
IF OBJECT_ID('plm.Users','U') IS NOT NULL DROP TABLE plm.Users;
IF OBJECT_ID('plm.ProductCategories','U') IS NOT NULL DROP TABLE plm.ProductCategories;
IF OBJECT_ID('plm.ProductStatuses','U') IS NOT NULL DROP TABLE plm.ProductStatuses;
IF OBJECT_ID('plm.UserRoles','U') IS NOT NULL DROP TABLE plm.UserRoles;

/* ============================================================
   PLM Poelman - Database Schema (SQL Server)
   Author: ChatGPT
   Created: 2025-09-09
   Notes:
   - Targets Microsoft SQL Server (T-SQL).
   - Uses schema [plm]. Adjust as needed.
   - Includes PKs, FKs, indexes, and sensible defaults.
   ============================================================ */

SET NOCOUNT ON;
SET XACT_ABORT ON;
GO

/* ---------- Create schema ---------- */
IF NOT EXISTS (SELECT * FROM sys.schemas WHERE name = 'plm')
EXEC('CREATE SCHEMA plm');
GO

/* ---------- Lookup tables ---------- */

-- User roles (Admin, Designer, Developer, Manufacturer, etc.)

CREATE TABLE plm.UserRoles (
    RoleID         INT IDENTITY(1,1) CONSTRAINT PK_UserRoles PRIMARY KEY,
    Name           NVARCHAR(50)  NOT NULL CONSTRAINT UQ_UserRoles_Name UNIQUE
);
GO

-- Product categories

CREATE TABLE plm.ProductCategories (
    CategoryID     INT IDENTITY(1,1) CONSTRAINT PK_ProductCategories PRIMARY KEY,
    Name           NVARCHAR(100) NOT NULL CONSTRAINT UQ_ProductCategories_Name UNIQUE,
    Description    NVARCHAR(4000) NULL
);
GO

-- Product statuses (Concept, Development, Prototype, Production, Discontinued, ...)

CREATE TABLE plm.ProductStatuses (
    StatusID       INT IDENTITY(1,1) CONSTRAINT PK_ProductStatuses PRIMARY KEY,
    Name           NVARCHAR(50)  NOT NULL CONSTRAINT UQ_ProductStatuses_Name UNIQUE,
    Description    NVARCHAR(4000) NULL
);
GO

/* ---------- Core tables ---------- */

-- Users

CREATE TABLE plm.Users (
    UserID         INT IDENTITY(1,1) CONSTRAINT PK_Users PRIMARY KEY,
    FirstName      NVARCHAR(100) NOT NULL,
    LastName       NVARCHAR(100) NOT NULL,
    Email          NVARCHAR(320) NOT NULL CONSTRAINT UQ_Users_Email UNIQUE,
    PasswordHash   VARBINARY(512) NOT NULL, -- store hashed+salted bytes
    PasswordSalt   VARBINARY(512) NOT NULL,
    RoleID         INT NOT NULL CONSTRAINT DF_Users_RoleID DEFAULT (1), -- default role
    CreatedAt      DATETIME2(0) NOT NULL CONSTRAINT DF_Users_CreatedAt DEFAULT (SYSUTCDATETIME()),
    CONSTRAINT FK_Users_RoleID FOREIGN KEY (RoleID) REFERENCES plm.UserRoles(RoleID)
);
GO

-- Suppliers

CREATE TABLE plm.Suppliers (
    SupplierID     INT IDENTITY(1,1) CONSTRAINT PK_Suppliers PRIMARY KEY,
    Name           NVARCHAR(200) NOT NULL,
    ContactPerson  NVARCHAR(200) NULL,
    Email          NVARCHAR(320) NULL,
    Phone          NVARCHAR(50)  NULL,
    Address        NVARCHAR(1000) NULL
);
GO

-- Materials

CREATE TABLE plm.Materials (
    MaterialID     INT IDENTITY(1,1) CONSTRAINT PK_Materials PRIMARY KEY,
    Name           NVARCHAR(200) NOT NULL,
    Description    NVARCHAR(4000) NULL,
    SupplierID     INT NULL,
    CONSTRAINT FK_Materials_SupplierID FOREIGN KEY (SupplierID) REFERENCES plm.Suppliers(SupplierID) ON DELETE SET NULL
);
GO
CREATE INDEX IX_Materials_SupplierID ON plm.Materials(SupplierID);
GO
CREATE UNIQUE INDEX UQ_Materials_Name ON plm.Materials(Name);
GO

-- Products

CREATE TABLE plm.Products (
    ProductID      INT IDENTITY(1,1) CONSTRAINT PK_Products PRIMARY KEY,
    Name           NVARCHAR(200) NOT NULL,
    Description    NVARCHAR(4000) NULL,
    CategoryID     INT NOT NULL,
    CreatedBy      INT NOT NULL,    -- Users.UserID
    StatusID       INT NOT NULL,
    CreatedAt      DATETIME2(0) NOT NULL CONSTRAINT DF_Products_CreatedAt DEFAULT (SYSUTCDATETIME()),
    UpdatedAt      DATETIME2(0) NOT NULL CONSTRAINT DF_Products_UpdatedAt DEFAULT (SYSUTCDATETIME()),
    CONSTRAINT FK_Products_CategoryID FOREIGN KEY (CategoryID) REFERENCES plm.ProductCategories(CategoryID),
    CONSTRAINT FK_Products_CreatedBy  FOREIGN KEY (CreatedBy)  REFERENCES plm.Users(UserID),
    CONSTRAINT FK_Products_StatusID   FOREIGN KEY (StatusID)   REFERENCES plm.ProductStatuses(StatusID)
);
GO
CREATE UNIQUE INDEX UQ_Products_Name ON plm.Products(Name);
GO
CREATE INDEX IX_Products_CategoryID ON plm.Products(CategoryID);
GO
CREATE INDEX IX_Products_StatusID ON plm.Products(StatusID);
GO
CREATE INDEX IX_Products_CreatedBy ON plm.Products(CreatedBy);
GO

-- ProductVersions

CREATE TABLE plm.ProductVersions (
    VersionID         INT IDENTITY(1,1) CONSTRAINT PK_ProductVersions PRIMARY KEY,
    ProductID         INT NOT NULL,
    VersionNumber     INT NOT NULL,
    ChangesDescription NVARCHAR(4000) NULL,
    CreatedAt         DATETIME2(0) NOT NULL CONSTRAINT DF_ProductVersions_CreatedAt DEFAULT (SYSUTCDATETIME()),
    ApprovedBy        INT NULL, -- Users.UserID
    ApprovedAt        DATETIME2(0) NULL,
    CONSTRAINT FK_ProductVersions_ProductID FOREIGN KEY (ProductID) REFERENCES plm.Products(ProductID) ON DELETE CASCADE,
    CONSTRAINT FK_ProductVersions_ApprovedBy FOREIGN KEY (ApprovedBy) REFERENCES plm.Users(UserID) ON DELETE SET NULL,
    CONSTRAINT UQ_ProductVersions_Product_Version UNIQUE (ProductID, VersionNumber)
);
GO
CREATE INDEX IX_ProductVersions_ProductID ON plm.ProductVersions(ProductID);
GO
CREATE INDEX IX_ProductVersions_ApprovedBy ON plm.ProductVersions(ApprovedBy);
GO

-- ProductDocuments

CREATE TABLE plm.ProductDocuments (
    DocumentID     INT IDENTITY(1,1) CONSTRAINT PK_ProductDocuments PRIMARY KEY,
    ProductID      INT NOT NULL,
    VersionID      INT NULL, -- optional link to a specific version
    FilePath       NVARCHAR(1024) NOT NULL,
    FileType       NVARCHAR(50) NOT NULL, -- e.g., CAD, PDF, Excel, Image
    UploadedBy     INT NOT NULL, -- Users.UserID
    UploadedAt     DATETIME2(0) NOT NULL CONSTRAINT DF_ProductDocuments_UploadedAt DEFAULT (SYSUTCDATETIME()),
    CONSTRAINT FK_ProductDocuments_ProductID FOREIGN KEY (ProductID) REFERENCES plm.Products(ProductID) ON DELETE CASCADE,
    CONSTRAINT FK_ProductDocuments_VersionID FOREIGN KEY (VersionID) REFERENCES plm.ProductVersions(VersionID) ON DELETE NO ACTION ,
    CONSTRAINT FK_ProductDocuments_UploadedBy FOREIGN KEY (UploadedBy) REFERENCES plm.Users(UserID)
);
GO
CREATE INDEX IX_ProductDocuments_ProductID ON plm.ProductDocuments(ProductID);
GO
CREATE INDEX IX_ProductDocuments_VersionID ON plm.ProductDocuments(VersionID);
GO
CREATE INDEX IX_ProductDocuments_UploadedBy ON plm.ProductDocuments(UploadedBy);
GO

-- Comments

CREATE TABLE plm.Comments (
    CommentID      INT IDENTITY(1,1) CONSTRAINT PK_Comments PRIMARY KEY,
    ProductID      INT NOT NULL,
    UserID         INT NOT NULL,
    Content        NVARCHAR(4000) NOT NULL,
    CreatedAt      DATETIME2(0) NOT NULL CONSTRAINT DF_Comments_CreatedAt DEFAULT (SYSUTCDATETIME()),
    CONSTRAINT FK_Comments_ProductID FOREIGN KEY (ProductID) REFERENCES plm.Products(ProductID) ON DELETE CASCADE,
    CONSTRAINT FK_Comments_UserID    FOREIGN KEY (UserID)    REFERENCES plm.Users(UserID) ON DELETE CASCADE
);
GO
CREATE INDEX IX_Comments_ProductID ON plm.Comments(ProductID);
GO
CREATE INDEX IX_Comments_UserID ON plm.Comments(UserID);
GO

-- Junction: ProductMaterials

CREATE TABLE plm.ProductMaterials (
    ProductID      INT NOT NULL,
    MaterialID     INT NOT NULL,
    Quantity       DECIMAL(18,4) NOT NULL CONSTRAINT DF_ProductMaterials_Quantity DEFAULT (0),
    Unit           NVARCHAR(20)   NOT NULL, -- e.g., pcs, m, kg
    CONSTRAINT PK_ProductMaterials PRIMARY KEY (ProductID, MaterialID),
    CONSTRAINT FK_ProductMaterials_ProductID  FOREIGN KEY (ProductID)  REFERENCES plm.Products(ProductID)  ON DELETE CASCADE,
    CONSTRAINT FK_ProductMaterials_MaterialID FOREIGN KEY (MaterialID) REFERENCES plm.Materials(MaterialID) ON DELETE CASCADE
);
GO
CREATE INDEX IX_ProductMaterials_MaterialID ON plm.ProductMaterials(MaterialID);
GO

/* ---------- Triggers & housekeeping ---------- */

-- Auto-update UpdatedAt on Products when row changes

CREATE TRIGGER plm.trg_Products_SetUpdatedAt
ON plm.Products
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    UPDATE p
      SET UpdatedAt = SYSUTCDATETIME()
    FROM plm.Products p
    INNER JOIN inserted i ON p.ProductID = i.ProductID;
END
GO

/* ---------- Seed minimal lookups (optional) ---------- */

-- Roles
IF NOT EXISTS (SELECT 1 FROM plm.UserRoles)
BEGIN
    INSERT INTO plm.UserRoles(Name) VALUES
    (N'Admin'), (N'Designer'), (N'Developer'), (N'Manufacturer'), (N'Viewer');
END
GO

-- Statuses
IF NOT EXISTS (SELECT 1 FROM plm.ProductStatuses)
BEGIN
    INSERT INTO plm.ProductStatuses(Name, Description) VALUES
    (N'Concept',      N'Initial idea and scoping'),
    (N'Development',  N'Design & development in progress'),
    (N'Prototype',    N'Prototype built and under evaluation'),
    (N'Production',   N'Approved and in production'),
    (N'Discontinued', N'No longer maintained or produced');
END
GO

