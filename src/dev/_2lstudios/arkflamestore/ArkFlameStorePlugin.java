package dev._2lstudios.arkflamestore;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import dev._2lstudios.arkflamestore.networking.StoreListener;
import dev._2lstudios.arkflamestore.util.CommandUtil;

public class ArkFlameStorePlugin extends JavaPlugin {
	private StoreListener storeListener;

	@Override
	public void onEnable() {
		final Logger logger = getLogger();

		storeListener = new StoreListener(logger, new CommandUtil(logger, this, getServer()));
		storeListener.start("127.0.0.1", 8132);
	}

	@Override
	public void onDisable() {
		if (storeListener != null) {
			storeListener.kill();
		}
	}
}