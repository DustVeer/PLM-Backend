create table ProductCategories
(
    CategoryID  int identity
        constraint PK_ProductCategories
            primary key,
    Name        nvarchar(100) not null
        constraint UQ_ProductCategories_Name
            unique,
    Description nvarchar(4000)
)
go

create table ProductStatuses
(
    StatusID    int identity
        constraint PK_ProductStatuses
            primary key,
    Name        nvarchar(50) not null
        constraint UQ_ProductStatuses_Name
            unique,
    Description nvarchar(4000)
)
go

create table Suppliers
(
    SupplierID    int identity
        constraint PK_Suppliers
            primary key,
    Name          nvarchar(200) not null,
    ContactPerson nvarchar(200),
    Email         nvarchar(320),
    Phone         nvarchar(50),
    Address       nvarchar(1000)
)
go

create table Materials
(
    MaterialID  int identity
        constraint PK_Materials
            primary key,
    Name        nvarchar(200) not null,
    Description nvarchar(4000),
    SupplierID  int
        constraint FK_Materials_SupplierID
            references Suppliers
            on delete set null
)
go

create index IX_Materials_SupplierID
    on Materials (SupplierID)
go

create unique index UQ_Materials_Name
    on Materials (Name)
go

create table UserRoles
(
    RoleID int identity
        constraint PK_UserRoles
            primary key,
    Name   nvarchar(50) not null
        constraint UQ_UserRoles_Name
            unique
)
go

create table Users
(
    UserID       int identity
        constraint PK_Users
            primary key,
    Name         nvarchar(100),
    Email        nvarchar(320)                                 not null
        constraint UQ_Users_Email
            unique,
    PasswordHash varbinary(512)                                not null,
    PasswordSalt varbinary(512)                                not null,
    RoleID       int
        constraint DF_Users_RoleID default 1                   not null
        constraint FK_Users_RoleID
            references UserRoles,
    CreatedAt    datetime2(0)
        constraint DF_Users_CreatedAt default sysutcdatetime() not null
)
go

create table Products
(
    ProductID   int identity
        constraint PK_Products
            primary key,
    Name        nvarchar(200)                                     not null,
    Description nvarchar(4000),
    CategoryID  int                                               not null
        constraint FK_Products_CategoryID
            references ProductCategories,
    CreatedBy   int                                               not null
        constraint FK_Products_CreatedBy
            references Users,
    StatusID    int                                               not null
        constraint FK_Products_StatusID
            references ProductStatuses,
    CreatedAt   datetime2(0)
        constraint DF_Products_CreatedAt default sysutcdatetime() not null,
    UpdatedAt   datetime2(0)
        constraint DF_Products_UpdatedAt default sysutcdatetime() not null
)
go

create table Comments
(
    CommentID int identity
        constraint PK_Comments
            primary key,
    ProductID int                                                 not null
        constraint FK_Comments_ProductID
            references Products
            on delete cascade,
    UserID    int                                                 not null
        constraint FK_Comments_UserID
            references Users
            on delete cascade,
    Content   nvarchar(4000)                                      not null,
    CreatedAt datetime2(0)
        constraint DF_Comments_CreatedAt default sysutcdatetime() not null
)
go

create index IX_Comments_ProductID
    on Comments (ProductID)
go

create index IX_Comments_UserID
    on Comments (UserID)
go

create table ProductMaterials
(
    ProductID  int                                        not null
        constraint FK_ProductMaterials_ProductID
            references Products
            on delete cascade,
    MaterialID int                                        not null
        constraint FK_ProductMaterials_MaterialID
            references Materials
            on delete cascade,
    Quantity   decimal(18, 4)
        constraint DF_ProductMaterials_Quantity default 0 not null,
    Unit       nvarchar(20)                               not null,
    constraint PK_ProductMaterials
        primary key (ProductID, MaterialID)
)
go

create index IX_ProductMaterials_MaterialID
    on ProductMaterials (MaterialID)
go

create table ProductVersions
(
    VersionID          int identity
        constraint PK_ProductVersions
            primary key,
    ProductID          int                                               not null
        constraint FK_ProductVersions_ProductID
            references Products
            on delete cascade,
    VersionNumber      int                                               not null,
    ChangesDescription nvarchar(4000),
    CreatedAt          datetime2(0)
        constraint DF_ProductVersions_CreatedAt default sysutcdatetime() not null,
    ApprovedBy         int
        constraint FK_ProductVersions_ApprovedBy
            references Users
            on delete set null,
    ApprovedAt         datetime2(0),
    constraint UQ_ProductVersions_Product_Version
        unique (ProductID, VersionNumber)
)
go

create table ProductDocuments
(
    DocumentID int identity
        constraint PK_ProductDocuments
            primary key,
    ProductID  int                                                         not null
        constraint FK_ProductDocuments_ProductID
            references Products
            on delete cascade,
    VersionID  int
        constraint FK_ProductDocuments_VersionID
            references ProductVersions,
    FilePath   nvarchar(1024)                                              not null,
    FileType   nvarchar(50)                                                not null,
    UploadedBy int                                                         not null
        constraint FK_ProductDocuments_UploadedBy
            references Users,
    UploadedAt datetime2(0)
        constraint DF_ProductDocuments_UploadedAt default sysutcdatetime() not null
)
go

create index IX_ProductDocuments_ProductID
    on ProductDocuments (ProductID)
go

create index IX_ProductDocuments_VersionID
    on ProductDocuments (VersionID)
go

create index IX_ProductDocuments_UploadedBy
    on ProductDocuments (UploadedBy)
go

create index IX_ProductVersions_ProductID
    on ProductVersions (ProductID)
go

create index IX_ProductVersions_ApprovedBy
    on ProductVersions (ApprovedBy)
go

create unique index UQ_Products_Name
    on Products (Name)
go

create index IX_Products_CategoryID
    on Products (CategoryID)
go

create index IX_Products_StatusID
    on Products (StatusID)
go

create index IX_Products_CreatedBy
    on Products (CreatedBy)
go

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
go

