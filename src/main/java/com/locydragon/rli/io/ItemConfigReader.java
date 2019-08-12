package com.locydragon.rli.io;

import com.locydragon.rli.api.LocyItemAPI;
import com.locydragon.rli.factory.ItemConfigReadFactory;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ItemConfigReader {
	public static final String dir = ".//plugins//RevivedLocyItem//Items//";

	public static void readFile() {
		LocyItemAPI.clearRegisteredItem();
		genDefault();
		int total = 0;
		for (File exists : new File(dir).listFiles()) {
			if (exists.getName().endsWith(".yml")) {
				total += ItemConfigReadFactory.readFile(YamlConfiguration.loadConfiguration(exists));
			}
		}
		Bukkit.getLogger().info("Successfully load " + total + " items in RLI!");
	}

	public static void genDefault() {
		boolean willGen = !new File(dir + "ExampleItem.yml").exists();
		if (willGen) {
			File genFile = new File(dir + "ExampleItem.yml");
			genFile.mkdirs();
			try {
				genFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
