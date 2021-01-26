package dev._2lstudios.arkflamestore;

import java.net.URISyntaxException;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import dev._2lstudios.arkflamestore.listeners.ConnectedListener;
import dev._2lstudios.arkflamestore.listeners.DisconnectedListener;
import dev._2lstudios.arkflamestore.listeners.AuthenticationSuccessListener;
import dev._2lstudios.arkflamestore.listeners.ConnectErrorListener;
import dev._2lstudios.arkflamestore.listeners.RunCommandsListener;
import dev._2lstudios.arkflamestore.util.CommandUtil;
import io.socket.client.IO;
import io.socket.client.Socket;

public class ArkFlameStore extends JavaPlugin {
	public static final String BACKEND_URL = "http://192.168.0.11:6501";
	private Socket socket;

	@Override
	public void onEnable() {
		try {
			final Server server = getServer();
			final Logger logger = getLogger();
			final CommandUtil commandUtil = new CommandUtil(logger, this, server);
			final int port = server.getPort();

			logger.info("Connecting to " + BACKEND_URL + ".");

			socket = IO.socket(BACKEND_URL);
			socket.on(Socket.EVENT_CONNECT, new ConnectedListener(socket, logger));
			socket.on(Socket.EVENT_DISCONNECT, new DisconnectedListener(logger));
			socket.on(Socket.EVENT_CONNECT_ERROR, new ConnectErrorListener(logger));
			socket.on("authenticationSuccess", new AuthenticationSuccessListener(socket, logger, port));
			socket.on("runCommands", new RunCommandsListener(this, logger, commandUtil));
			socket.connect();
		} catch (final URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDisable() {
		socket.close();
	}
}