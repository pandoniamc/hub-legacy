package fr.pandonia.hub.player;

import fr.pandonia.hub.api.player.PlayerData;
import fr.pandonia.hub.api.player.Purse;
import fr.pandonia.hub.api.player.Group;
import net.minestom.server.entity.Player;
import net.minestom.server.network.player.GameProfile;
import net.minestom.server.network.player.PlayerConnection;

import java.util.Locale;

public class HubPlayer extends Player {

    private final PlayerData data;
    private Locale locale;

    public HubPlayer(PlayerConnection playerConnection, GameProfile gameProfile, PlayerData data) {
        super(playerConnection, gameProfile);

        this.data = data;
        this.locale = data.locale();
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Group getGroup() {
        return data.group();
    }

    public Purse getPurse() {
        return data.purse();
    }
}
