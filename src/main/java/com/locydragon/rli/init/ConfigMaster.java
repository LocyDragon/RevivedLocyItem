package com.locydragon.rli.init;

import com.locydragon.rli.RevivedLocyItem;
import com.locydragon.rli.io.ItemConfigReader;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigMaster {
	private RevivedLocyItem plugin;
	public ConfigMaster(RevivedLocyItem plugin) {
		this.plugin = plugin;
	}

	public void initConfig() {
		plugin.saveDefaultConfig();
		RevivedLocyItem.mainConfiguration = plugin.getConfig();
		ItemConfigReader.readFile();
	}

	public void reloadConfig(){
		plugin.reloadConfig();
	}

	public void saveConfig() {
		plugin.saveConfig();
	}

	public FileConfiguration showConfig() {
		return plugin.getConfig();
	}
}
