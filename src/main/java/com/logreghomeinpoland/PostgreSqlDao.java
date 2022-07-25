package com.logreghomeinpoland;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

// Połączenie z bazy danych do aplikacji //
public class PostgreSqlDao implements Dao<User, Integer> {
    private static final Logger LOGGER = Logger.getLogger(PostgreSqlDao.class.getName());
    private final Optional<Connection> connection;

    public PostgreSqlDao() {
        this.connection = JdbcConnection.getConnection();
    }

    // Metoda ta jest używana do pobierania danych z bazy danych //
    // Select //
    @Override
    public Optional<User> get(String email, String password) {
        return connection.flatMap(conn -> {
            Optional<User> user = Optional.empty();
            String sql = "SELECT * FROM users WHERE email = " + email;

            try (var statement = conn.createStatement(); var resultSet = statement.executeQuery(sql)) {
                if (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String pwd = resultSet.getString("password");

                    if (password.equals(pwd)) {
                        user = Optional.of(new User(id, email, pwd));

                        LOGGER.log(Level.INFO, "Found {0} in database", user.get());
                    }
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, null, e);
            }

            return user;
        });
    }

    @Override
    public Collection<User> getAll() {
        return null;
    }

    //Insert//
    @Override
    public Optional<Integer> save(User user) {
        var message = "The user added should not be null";
        var nonNullUser = Objects.requireNonNull(user, message);
        var sql = "INSERT INTO users(email, password) VALUES(?, ?)";

        return connection.flatMap(connection1 -> {
            Optional<Integer> id = Optional.empty();

            try (var statement = connection1.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, nonNullUser.getEmail());
                statement.setString(2, nonNullUser.getPassword());

                int numberOfInsertedRows = statement.executeUpdate();

                if (numberOfInsertedRows > 0) {
                    try (var resultSet = statement.getGeneratedKeys()) {
                        if (resultSet.next()) {
                            id = Optional.of(resultSet.getInt(1));
                        }
                    }
                }

                LOGGER.log(Level.INFO, "{0} created successfully? {1}", new Object[]{nonNullUser, (numberOfInsertedRows > 0)});
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, null, e);
            }
            return id;
        });
    }

    //*Update*//

    @Override
    public void update(User user) {

    }

    //*Delete*//

    @Override
    public void delete(User user) {

    }
}
