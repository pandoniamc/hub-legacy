package fr.pandonia.hub.api.player;

import java.util.Locale;
import java.util.UUID;

public interface PlayerService {

    PlayerData getData(UUID id);

    void updateLocale(UUID playerId, Locale locale);
}
