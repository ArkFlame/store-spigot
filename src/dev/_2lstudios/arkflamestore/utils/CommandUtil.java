package dev._2lstudios.arkflamestore.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class CommandUtil {
    private final Logger logger;
    private final Plugin plugin;
    private final Server server;
    private final CommandSender consoleSender;
    private static final String BROADCAST_COMMAND = "broadcast ";

    public CommandUtil(final Logger logger, final Plugin plugin, final Server server) {
        this.logger = logger;
        this.plugin = plugin;
        this.server = server;
        this.consoleSender = server.getConsoleSender();
    }

    public void executeCommand(final String command) {
        final String coloredCommand = ChatColor.translateAlternateColorCodes('&', command);

        if (coloredCommand.startsWith(BROADCAST_COMMAND)) {
            server.broadcast(coloredCommand.replace(BROADCAST_COMMAND, ""), "");
        } else {
            server.dispatchCommand(consoleSender, coloredCommand);
        }

        logger.log(Level.INFO, "Executed command from Store: {0}", coloredCommand);
    }

    public void executeCommand(final String[] commands) {
        server.getScheduler().runTask(plugin, () -> {
            for (final String command : commands) {
                executeCommand(command);
            }
        });
    }
}
