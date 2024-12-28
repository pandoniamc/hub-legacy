package fr.pandonia.hub.api.configuration;

import fr.pandonia.hub.Main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesConfiguration implements Configuration {

    public static final String CONFIG_FILE_NAME = "server.properties";

    private final Properties properties = new Properties();

    @Override
    public String getServerHost() {
        return properties.getProperty("server.host", "localhost");
    }

    @Override
    public int getServerPort() {
        return Integer.parseInt(properties.getProperty("server.port", "25565"));
    }

    @Override
    public String getDatabaseHost() {
        return properties.getProperty("database.host", "localhost");
    }

    @Override
    public int getDatabasePort() {
        return Integer.parseInt(properties.getProperty("database.port", "3306"));
    }

    @Override
    public String getDatabaseName() {
        return properties.getProperty("database.name", "pandonia");
    }

    @Override
    public String getDatabaseUsername() {
        return properties.getProperty("database.username", "root");
    }

    @Override
    public String getDatabasePassword() {
        return properties.getProperty("database.password", "");
    }

    public void load() throws IOException {
        try (InputStream in = Main.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME)) {
            if (in == null) {
                throw new IOException("Cannot find configuration file: %s".formatted(CONFIG_FILE_NAME));
            }

            properties.load(in);
        }
    }
}
