package com.locydragon.rli.io;

import com.locydragon.rli.api.LocyItemAPI;
import com.locydragon.rli.factory.SkillConfigReaderFactory;
import com.locydragon.rli.util.ListBuilder;
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
		Bukkit.getPluginManager().getPlugin("RevivedLocyItem").getLogger().info("成功加载了 " + total + " 个技能于 RLI!");
	}

	public static void genDefault() {
		boolean willGen = !new File(dir + "ExampleSkill.yml").exists();
		if (willGen) {
			File genFile = new File(dir + "ExampleSkill.yml");
			genFile.getParentFile().mkdirs();
			try {
				genFile.createNewFile();
				FileConfiguration config = YamlConfiguration.loadConfiguration(genFile);
				config.set("ExampleSkill.cooldown", 10);
				config.set("ExampleSkill.wait",
						"&7>>> &bPlease wait for {cd}s so that you can use skill again!");
				config.set("ExampleSkill.Skills", ListBuilder.buildList("msg ~ m=&7Chug! Chug!", "particle ~ name=Star"
				, "lightning ~", "delay ~ 1000", "launch ~ type=FireBall;d=15", "push ~ dp=-1.6;dh=0.8"));

				config.set("ExampleSkillReach.cooldown", 3);
				config.set("ExampleSkillReach.wait",
						"&7>>> &cPlease wait for {cd}s so that you can use skill again!");
				config.set("ExampleSkillReach.Skills", ListBuilder.buildList("reach ~ r=15;d=25"));

				config.save(genFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
