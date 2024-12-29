package fr.pandonia.hub.api.player;

import fr.pandonia.hub.api.sql.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.UUID;

public class PlayerServiceImpl implements PlayerService {

    private final ConnectionProvider connectionProvider;

    public PlayerServiceImpl(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public PlayerData getData(UUID id) {
        try (Connection connection = connectionProvider.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT g.name, p.locale, p.coins
                    FROM players p
                    JOIN `groups` g ON g.id = p.group_id
                    WHERE p.id = ?
                    """)) {
                statement.setString(1, id.toString());

                try (ResultSet result = statement.executeQuery()) {
                    if (!result.next()) {
                        throw new IllegalArgumentException("Player not found");
                    }

                    Group group = Group.valueOf(result.getString("g.name"));
                    Locale locale = Locale.forLanguageTag(result.getString("p.locale"));
                    Purse purse = new Purse(result.getInt("p.coins"));

                    return new PlayerData(group, locale, purse);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get data for player %s".formatted(id), e);
        }
    }

    @Override
    public void updateLocale(UUID playerId, Locale locale) {
        try (Connection connection = connectionProvider.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("""
                    UPDATE players
                    SET locale = ?
                    WHERE id = ?
                    """)) {
                statement.setString(1, locale.getLanguage());
                statement.setString(2, playerId.toString());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update locale for player %s".formatted(playerId), e);
        }
    }
}
