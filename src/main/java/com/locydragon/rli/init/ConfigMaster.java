package com.locydragon.rli.init;

import com.locydragon.rli.RevivedLocyItem;
import com.locydragon.rli.io.ItemConfigReader;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigMaster {
	private static RevivedLocyItem plugin;
	public ConfigMaster(RevivedLocyItem plugin) {
		ConfigMaster.plugin = plugin;
	}

	public void initConfig() {
		plugin.saveDefaultConfig();
		RevivedLocyItem.mainConfiguration = plugin.getConfig();
		ItemConfigReader.readFile();
	}

	public static void reloadConfig(){
		plugin.reloadConfig();
		RevivedLocyItem.mainConfiguration = plugin.getConfig();
	}

	public void saveConfig() {
		plugin.saveConfig();
	}

	public FileConfiguration showConfig() {
		return plugin.getConfig();
	}
}
