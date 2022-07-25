package com.logreghomeinpoland;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

// Database Connectivity //

public class JdbcConnection {
    private static final Logger LOGGER = Logger.getLogger(JdbcConnection.class.getName());
    private static Optional<Connection> connection = Optional.empty();

    // Connection to Database //
    public static Optional<Connection> getConnection() {
        if (connection.isEmpty()) {
            var url = "jdbc:postgresql://localhost:5342/test_db";
            var user = "postgres";
            var password = "admin";

            try {
                connection = Optional.ofNullable(
                        DriverManager.getConnection(url, user, password)
                );
            } catch (SQLException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }

        return connection;
    }
}
