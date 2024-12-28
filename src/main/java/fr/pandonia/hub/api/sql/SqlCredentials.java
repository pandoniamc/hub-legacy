package fr.pandonia.hub.api.sql;

import fr.pandonia.hub.api.configuration.Configuration;

public class SqlCredentials {

    private final String host;
    private final int port;
    private final String database;
    private final String username;
    private final String password;

    private SqlCredentials(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public static SqlCredentials fromConfiguration(Configuration configuration) {
        return new SqlCredentials(
                configuration.getDatabaseHost(),
                configuration.getDatabasePort(),
                configuration.getDatabaseName(),
                configuration.getDatabaseUsername(),
                configuration.getDatabasePassword()
        );
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String toUrl() {
        return "jdbc:mysql://" + host + ":" + port + "/" + database;
    }
}
