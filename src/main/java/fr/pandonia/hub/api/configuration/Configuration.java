package fr.pandonia.hub.api.configuration;

public interface Configuration {

    String getServerHost();

    int getServerPort();

    String getDatabaseHost();

    int getDatabasePort();

    String getDatabaseName();

    String getDatabaseUsername();

    String getDatabasePassword();
}
