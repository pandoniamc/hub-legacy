package fr.pandonia.hub.api.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariConnectionProvider implements ConnectionProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(HikariConnectionProvider.class);

    private final SqlCredentials credentials;

    private HikariDataSource dataSource;

    public HikariConnectionProvider(SqlCredentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new IllegalStateException("Connection pool is not initialized");
        }

        return dataSource.getConnection();
    }

    public void open() {
        if (dataSource != null) {
            throw new IllegalStateException("Connection pool is already initialized");
        }

        LOGGER.debug("Opening connection pool for {}", credentials.toUrl());

        HikariConfig configuration = new HikariConfig();
        configuration.setJdbcUrl(credentials.toUrl());
        configuration.setUsername(credentials.getUsername());
        configuration.setPassword(credentials.getPassword());

        dataSource = new HikariDataSource(configuration);

        LOGGER.debug("Connection pool opened");
    }

    public void close() {
        if (dataSource != null) {
            LOGGER.debug("Closing connection pool");

            dataSource.close();
            dataSource = null;

            LOGGER.debug("Connection pool closed");
        }
    }
}
