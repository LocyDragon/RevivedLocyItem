package com.locydragon.rli.io;

import com.locydragon.rli.api.LocyItemAPI;
import com.locydragon.rli.factory.SkillConfigReaderFactory;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SkillConfigReader {
	public static final String dir = ".//plugins//RevivedLocyItem//Skills//";

	public static void readFile() {
		LocyItemAPI.clearRegisteredSkill();
		genDefault();
		int total = 0;
		for (File exists : new File(dir).listFiles()) {
			if (exists.getName().endsWith(".yml")) {
				total += SkillConfigReaderFactory.readFile(YamlConfiguration.loadConfiguration(exists));
			}
		}
		Bukkit.getPluginManager().getPlugin("RevivedLocyItem").getLogger().info("Successfully load " + total + " skills in RLI!");
	}

	public static void genDefault() {
		boolean willGen = !new File(dir + "ExampleSkill.yml").exists();
		if (willGen) {
			File genFile = new File(dir + "ExampleSkill.yml");
			genFile.getParentFile().mkdirs();
			try {
				genFile.createNewFile();
				FileConfiguration config = YamlConfiguration.loadConfiguration(genFile);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
