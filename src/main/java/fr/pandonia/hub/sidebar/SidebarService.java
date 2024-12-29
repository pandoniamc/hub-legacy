package fr.pandonia.hub.sidebar;

import fr.pandonia.hub.api.server.ServerService;
import fr.pandonia.hub.player.HubPlayer;
import fr.pandonia.hub.utils.ComponentUtils;
import fr.pandonia.hub.utils.Constants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.entity.Player;
import net.minestom.server.scoreboard.Sidebar;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SidebarService {

    private static final int SIDEBAR_LENGTH = 25;

    private final ServerService serverService;

    private final Map<UUID, Sidebar> viewers = new HashMap<>();

    public SidebarService(ServerService serverService) {
        this.serverService = serverService;
    }

    public void addViewer(HubPlayer viewer) {
        Sidebar sidebar = new Sidebar(Constants.NAME);

        sidebar.createLine(blankLine(1, 9));
        sidebar.createLine(
                new Sidebar.ScoreboardLine("category.profile",
                        Component.text()
                                .append(Component.text("|", NamedTextColor.GRAY))
                                .appendSpace()
                                .append(Component.text("PROFIL", NamedTextColor.GREEN, TextDecoration.BOLD))
                                .build(), 8)
        );
        sidebar.createLine(
                new Sidebar.ScoreboardLine("profile.group",
                        Component.text()
                                .appendSpace()
                                .append(Component.text("Grade", NamedTextColor.WHITE))
                                .appendSpace()
                                .append(Component.text("▪", NamedTextColor.DARK_GRAY))
                                .appendSpace()
                                .append(viewer.getGroup().getCustomName())
                                .build(), 7)
        );
        sidebar.createLine(
                new Sidebar.ScoreboardLine("profile.coins",
                        Component.text()
                                .appendSpace()
                                .append(Component.text("Pièces", NamedTextColor.WHITE))
                                .appendSpace()
                                .append(Component.text("▪", NamedTextColor.DARK_GRAY))
                                .appendSpace()
                                .append(ComponentUtils.getCoins(viewer.getPurse().getCoins()))
                                .build(), 6)
        );
        sidebar.createLine(blankLine(2, 5));
        sidebar.createLine(
                new Sidebar.ScoreboardLine("category.server",
                        Component.text()
                                .append(Component.text("|", NamedTextColor.GRAY))
                                .appendSpace()
                                .append(Component.text("SERVEUR", NamedTextColor.AQUA, TextDecoration.BOLD))
                                .build(), 4)
        );
        sidebar.createLine(
                new Sidebar.ScoreboardLine("server.name",
                        Component.text()
                                .appendSpace()
                                .append(Component.text("Hub", NamedTextColor.WHITE))
                                .appendSpace()
                                .append(Component.text("▪", NamedTextColor.DARK_GRAY))
                                .appendSpace()
                                .append(Component.text("#", NamedTextColor.AQUA))
                                .append(Component.text(serverService.getServerId(), NamedTextColor.AQUA))
                                .build(), 3)
        );
        sidebar.createLine(
                new Sidebar.ScoreboardLine("server.players",
                        Component.text()
                                .appendSpace()
                                .append(Component.text("Joueurs", NamedTextColor.WHITE))
                                .appendSpace()
                                .append(Component.text("▪", NamedTextColor.DARK_GRAY))
                                .appendSpace()
                                .append(Component.text(serverService.getOnlinePlayers(), NamedTextColor.BLUE))
                                .build(), 2)
        );
        sidebar.createLine(blankLine(3, 1));

        int spaces = (SIDEBAR_LENGTH - Constants.IP.length()) / 2;
        sidebar.createLine(
                new Sidebar.ScoreboardLine(
                        "server.ip",
                        Component.text(" ".repeat(spaces) + Constants.IP + " ".repeat(spaces), NamedTextColor.GRAY),
                        0
                )
        );

        sidebar.addViewer(viewer);

        viewers.put(viewer.getUuid(), sidebar);
    }

    public void removeViewer(Player viewer) {
        Sidebar sidebar = viewers.remove(viewer.getUuid());

        if (sidebar != null) {
            sidebar.removeViewer(viewer);
        }
    }

    private Sidebar.ScoreboardLine blankLine(int id, int line) {
        return new Sidebar.ScoreboardLine("blank.%d".formatted(id), Component.text(" ".repeat(SIDEBAR_LENGTH)), line);
    }
}
