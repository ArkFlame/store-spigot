package dev._2lstudios.arkflamestore.listeners;

import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

import dev._2lstudios.arkflamestore.utils.CommandUtil;
import io.socket.emitter.Emitter.Listener;

public class RunCommandsListener implements Listener {
    private Plugin plugin;
    private final Logger logger;
    private final CommandUtil commandUtil;

    public RunCommandsListener(final Plugin plugin, final Logger logger, final CommandUtil commandUtil) {
        this.plugin = plugin;
        this.logger = logger;
        this.commandUtil = commandUtil;
    }

    private void runSync(final Runnable runnable) {
        final Server server = plugin.getServer();

        if (server.isPrimaryThread()) {
            runnable.run();
        } else {
            server.getScheduler().runTask(plugin, runnable);
        }
    }

    @Override
    public void call(final Object... args) {
        if (args.length > 1) {
            logger.info("<RunCommands> Received too many arguments: " + args.length);
        } else if (args.length == 1) {
            if (args[0] instanceof String) {
                final String[] commands = ((String) args[0]).split("\n");

                if (commands.length > 0) {
                    runSync(() -> {
                        for (final String command : commands) {
                            commandUtil.executeCommand(command);
                        }
                    });
                } else {
                    logger.info("<RunCommands> No commands received to execute.");
                }
            } else {
                logger.info("<RunCommands> Received " + args[0].getClass().getName() + ". Expected: String");
            }
        } else {
            logger.info("<RunCommands> Didn't receive enough arguments: " + args.length);
        }
    }
}