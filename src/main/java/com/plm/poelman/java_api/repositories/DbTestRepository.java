package com.plm.poelman.java_api.repositories;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;

@Repository
public class DbTestRepository {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    public String testConnection() {

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return "✅ Connected to SQL Server! " + conn.getMetaData().getDatabaseProductVersion();
        } catch (Exception e) {
            return "❌ Connection failed: " + e.getMessage();
        }
    }
}
