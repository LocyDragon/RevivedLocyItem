package com.locydragon.rli.io;

import com.locydragon.rli.api.LocyItemAPI;
import com.locydragon.rli.factory.ItemConfigReadFactory;
import com.locydragon.rli.factory.ParticleConfigReaderFactory;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ParticleConfigReader {
	public static final String dir = ".//plugins//RevivedLocyItem//Particles//";

	public static void readFile() {
		LocyItemAPI.clearRegisteredParticle();
		genDefault();
		int total = 0;
		for (File exists : new File(dir).listFiles()) {
			if (exists.getName().endsWith(".yml")) {
				total += ParticleConfigReaderFactory
						.readFile(YamlConfiguration.loadConfiguration(exists));
			}
		}
		Bukkit.getLogger().info("Successfully load " + total + " particles in RLI!");
	}

	public static void genDefault() {
		boolean willGen = !new File(dir + "ExampleParticle.yml").exists();
		if (willGen) {
			File genFile = new File(dir + "ExampleParticle.yml");
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
