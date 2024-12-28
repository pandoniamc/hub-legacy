package fr.pandonia.hub.api.player;

import fr.pandonia.hub.api.group.Group;
import fr.pandonia.hub.api.sql.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                    SELECT g.name, p.coins
                    FROM players p
                    JOIN `groups` g ON g.id = p.group_id
                    WHERE p.id = ?
                    """)) {
                statement.setString(1, id.toString());

                try (ResultSet result = statement.executeQuery()) {
                    if (!result.next()) {
                        throw new IllegalArgumentException("Player not found");
                    }

                    Group group = Group.valueOf(result.getString("name"));
                    Purse purse = new Purse(0);

                    return new PlayerData(group, purse);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get data for player %s".formatted(id), e);
        }
    }
}
