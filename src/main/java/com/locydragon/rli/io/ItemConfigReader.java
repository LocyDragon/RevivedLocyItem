package com.locydragon.rli.io;

import com.locydragon.rli.api.LocyItemAPI;
import com.locydragon.rli.factory.ItemConfigReadFactory;
import com.locydragon.rli.util.ListBuilder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
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
		Bukkit.getPluginManager().getPlugin("RevivedLocyItem").getLogger().info("Successfully load " + total + " items in RLI!");
		Bukkit.getPluginManager().getPlugin("RevivedLocyItem").getLogger().info("成功加载了 " + total + " 个特殊物品于 RLI!");
	}

	public static void genDefault() {
		boolean willGen = !new File(dir + "ExampleItem.yml").exists();
		if (willGen) {
			File genFile = new File(dir + "ExampleItem.yml");
			genFile.getParentFile().mkdirs();
			try {
				genFile.createNewFile();
				FileConfiguration config = YamlConfiguration.loadConfiguration(genFile);
				config.set("ExampleItem.name", "&aTest Item");
				config.set("ExampleItem.id", 283);
				config.set("ExampleItem.lore", ListBuilder.buildList(
						"&b&m=======================================", "&bThis is a test item!", "    &7---- which has been created on rli.",
						"&7Left_Click: Damage the entity far away!",
						"&7Right_Click: Do something magical!", "&b&m======================================="
				));
				config.set("ExampleItem.Options", ListBuilder.buildList("MOVE_SPEED ~ 0.15", "UNBREAKABLE ~ true", "HIDE_UNBREAKABLE ~ true"
				, "HIDE_Attributes ~ true", "MAX_HEALTH ~ 3.5"));
				config.set("ExampleItem.Enchantment", ListBuilder.buildList("DAMAGE_ALL ~ 8"));
				config.set("ExampleItem.Skills", ListBuilder.buildList("skill ~ name=ExampleSkillReach @LEFT",
						"msg ~ msg=&7Slurp~ @LEFT",
						"skill ~ name=ExampleSkill @RIGHT"));
				config.save(genFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
