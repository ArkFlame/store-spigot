package dev._2lstudios.arkflamestore.networking;

import java.net.UnknownHostException;
import java.util.logging.Logger;

import dev._2lstudios.arkflamestore.util.CommandUtil;

public class StoreListener {
    private final Logger logger;
    private final CommandUtil commandUtil;
    private StoreRunnable storeRunnable;

    public StoreListener(final Logger logger, final CommandUtil commandUtil) {
        this.logger = logger;
        this.commandUtil = commandUtil;
    }

    public void start(final String address, final int port) {
        kill();

        try {
            storeRunnable = new StoreRunnable(logger, commandUtil, address, port, 1000);

            new Thread(storeRunnable).start();
        } catch (final UnknownHostException e) {
            logger.warning("The host specified is unknown!");
        }
    }

    public void kill() {
        if (storeRunnable != null) {
            storeRunnable.kill();
        }
    }

    public void close() {
        if (storeRunnable != null) {
            storeRunnable.close();
        }
    }
}
