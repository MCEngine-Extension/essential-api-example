package io.github.mcengine.extension.api.essential.example;

import io.github.mcengine.api.core.MCEngineCoreApi;
import io.github.mcengine.api.core.extension.logger.MCEngineExtensionLogger;
import io.github.mcengine.api.essential.extension.api.IMCEngineEssentialAPI;

import io.github.mcengine.extension.api.essential.example.command.EssentialAPICommand;
import io.github.mcengine.extension.api.essential.example.listener.EssentialAPIListener;
import io.github.mcengine.extension.api.essential.example.tabcompleter.EssentialAPITabCompleter;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Main class for the Essential API example module.
 * <p>
 * Registers the {@code /essentialapiexample} command and related event listeners.
 */
public class ExampleEssentialAPI implements IMCEngineEssentialAPI {

    /**
     * Custom extension logger for this module, with contextual labeling.
     */
    private MCEngineExtensionLogger logger;

    /**
     * Initializes the Essential API example module.
     * Called automatically by the MCEngine core plugin.
     *
     * @param plugin The Bukkit plugin instance.
     */
    @Override
    public void onLoad(Plugin plugin) {
        // Initialize contextual logger once and keep it for later use.
        this.logger = new MCEngineExtensionLogger(plugin, "API", "EssentialExampleAPI");

        try {
            // Register event listener
            PluginManager pluginManager = Bukkit.getPluginManager();
            pluginManager.registerEvents(new EssentialAPIListener(plugin, this.logger), plugin);

            // Reflectively access Bukkit's CommandMap
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());

            // Define the /essentialapiexample command
            Command essentialApiExampleCommand = new Command("essentialapiexample") {

                /**
                 * Handles command execution for /essentialapiexample.
                 */
                private final EssentialAPICommand handler = new EssentialAPICommand();

                /**
                 * Handles tab-completion for /essentialapiexample.
                 */
                private final EssentialAPITabCompleter completer = new EssentialAPITabCompleter();

                @Override
                public boolean execute(CommandSender sender, String label, String[] args) {
                    return handler.onCommand(sender, this, label, args);
                }

                @Override
                public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
                    return completer.onTabComplete(sender, this, alias, args);
                }
            };

            essentialApiExampleCommand.setDescription("Essential API example command.");
            essentialApiExampleCommand.setUsage("/essentialapiexample");

            // Dynamically register the /essentialapiexample command
            commandMap.register(plugin.getName().toLowerCase(), essentialApiExampleCommand);

            this.logger.info("Enabled successfully.");
        } catch (Exception e) {
            this.logger.warning("Failed to initialize ExampleEssentialAPI: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Called when the Essential API example module is disabled/unloaded.
     * No explicit unregistration is required for the anonymous Command here.
     *
     * @param plugin The Bukkit plugin instance.
     */
    @Override
    public void onDisload(Plugin plugin) {
        if (this.logger != null) {
            this.logger.info("Disabled.");
        }
    }

    /**
     * Sets the unique ID for this module.
     *
     * @param id the assigned identifier (ignored; a fixed ID is used for consistency)
     */
    @Override
    public void setId(String id) {
        MCEngineCoreApi.setId("mcengine-essential-api-example");
    }
}
