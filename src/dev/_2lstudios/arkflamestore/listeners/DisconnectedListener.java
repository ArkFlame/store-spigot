package dev._2lstudios.arkflamestore.listeners;

import java.util.logging.Logger;

import dev._2lstudios.arkflamestore.ArkFlameStore;
import io.socket.emitter.Emitter.Listener;

public class DisconnectedListener implements Listener {
    private final Logger logger;

    public DisconnectedListener(final Logger logger) {
        this.logger = logger;
    }

    @Override
    public void call(final Object... args) {
        logger.info("Disconnected from " + ArkFlameStore.BACKEND_URL + ".");
    }
}
