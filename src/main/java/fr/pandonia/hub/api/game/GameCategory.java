package fr.pandonia.hub.api.game;

public enum GameCategory {

    PVE("PvE"),
    PVP("PvP"),
    ROLE_PLAY("Role-Play"),
    SOLO("Solo"),
    TEAM("Team");

    private final String name;

    GameCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
