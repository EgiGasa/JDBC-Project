package com.lhind.internship.jdbc.util;

import com.lhind.internship.jdbc.model.DBProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class JdbcConnection {

    private JdbcConnection() {
    }

    public static Connection connect() {
        Connection connection = null;
        try (final FileInputStream stream = new FileInputStream("src/main/resources/db.properties")) {
            final Properties properties = new Properties();
            properties.load(stream);
            final DBProperties db = getDbProperties(properties);
            connection = DriverManager.getConnection(
                    db.getUrl(),
                    db.getUser(),
                    db.getPassword()
            );
        } catch (IOException | SQLException e) {
            System.err.println(e.getMessage());
        }
        return connection;
    }

    private static DBProperties getDbProperties(Properties properties) {
        final DBProperties dbProperties = new DBProperties();
        dbProperties.setUrl(properties.getProperty("jdbc.url"));
        dbProperties.setUser(properties.getProperty("jdbc.user"));
        dbProperties.setPassword(properties.getProperty("jdbc.password"));
        return dbProperties;
    }
}
