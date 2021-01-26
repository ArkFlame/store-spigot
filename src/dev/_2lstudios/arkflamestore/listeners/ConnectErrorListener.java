package dev._2lstudios.arkflamestore.listeners;

import java.util.logging.Logger;

import io.socket.emitter.Emitter.Listener;

public class ConnectErrorListener implements Listener {
    private final Logger logger;

    public ConnectErrorListener(final Logger logger) {
        this.logger = logger;
    }

    @Override
    public void call(final Object... args) {
        if (args.length > 1) {
            logger.info("Received too many arguments: " + args.length);
        } else if (args.length == 1) {
            if (args[0] instanceof Exception) {
                logger.info("Received exception when connecting: " + ((Throwable) args[0]).getMessage());
            } else {
                logger.info("Received " + args[0].getClass().getName() + ". Expected: String[]");
            }
        } else {
            logger.info("Didn't receive enough arguments: " + args.length);
        }
    }
}
