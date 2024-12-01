package fr.pandonia.hub.api.server;

import fr.pandonia.hub.Instance;

public class ServerServiceImpl implements ServerService {

    private final Instance instance;

    public ServerServiceImpl(Instance instance) {
        this.instance = instance;
    }

    @Override
    public int getOnlinePlayers() {
        return instance.getPlayers().size();
    }

    @Override
    public String getServerId() {
        return "1";
    }
}
