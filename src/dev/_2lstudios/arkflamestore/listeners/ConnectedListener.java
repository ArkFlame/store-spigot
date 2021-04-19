package dev._2lstudios.arkflamestore.listeners;

import java.util.logging.Logger;

import dev._2lstudios.arkflamestore.ArkFlameStore;
import io.socket.client.Socket;
import io.socket.emitter.Emitter.Listener;

public class ConnectedListener implements Listener {
    private final Socket socket;
    private final Logger logger;

    public ConnectedListener(final Socket socket, final Logger logger) {
        this.socket = socket;
        this.logger = logger;
    }

    @Override
    public void call(final Object... args) {
        logger.info("Connected to " + ArkFlameStore.BACKEND_URL + ". Trying to authenticate.");
	    socket.emit("auth", ArkFlameStore.TOKEN);
    }
}
