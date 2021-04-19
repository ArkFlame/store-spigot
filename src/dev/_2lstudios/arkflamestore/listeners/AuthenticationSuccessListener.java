package dev._2lstudios.arkflamestore.listeners;

import java.util.logging.Logger;

import dev._2lstudios.arkflamestore.ArkFlameStore;
import io.socket.client.Socket;
import io.socket.emitter.Emitter.Listener;

public class AuthenticationSuccessListener implements Listener {
    private final Socket socket;
    private final Logger logger;

    public AuthenticationSuccessListener(final Socket socket, final Logger logger, final int port) {
        this.socket = socket;
        this.logger = logger;
    }

    @Override
    public void call(Object... arg0) {
        logger.info("Successfully authenticated with " + ArkFlameStore.BACKEND_URL + "!");
        logger.info("Emitting server name: '" + ArkFlameStore.SERVER_NAME + "' to " + ArkFlameStore.BACKEND_URL + "!");
        socket.emit("server", ArkFlameStore.SERVER_NAME);
    }
}
