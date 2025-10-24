package io.github.mcengine.extension.api.essential.example.listener;

import io.github.mcengine.api.core.extension.logger.MCEngineExtensionLogger;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

/**
 * Example event listener for the Essential API example module.
 */
public class EssentialAPIListener implements Listener {

    /**
     * The plugin instance used by this listener.
     */
    private final Plugin plugin;

    /**
     * Custom extension logger for this listener, with contextual labeling.
     */
    private final MCEngineExtensionLogger logger;

    /**
     * Creates a new {@link EssentialAPIListener}.
     *
     * @param plugin The plugin instance.
     * @param logger The custom extension logger instance.
     */
    public EssentialAPIListener(Plugin plugin, MCEngineExtensionLogger logger) {
        this.plugin = plugin;
        this.logger = logger;
    }

    /**
     * Handles player join events and sends a welcome message.
     *
     * @param event The player join event.
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(ChatColor.AQUA + "[API][essential-addon-example] Hello " + player.getName() + ", enjoy your time!");
    }

    /**
     * Handles player quit events and logs the departure.
     *
     * @param event The player quit event.
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        this.logger.info(player.getName() + " has left the server.");
    }
}
