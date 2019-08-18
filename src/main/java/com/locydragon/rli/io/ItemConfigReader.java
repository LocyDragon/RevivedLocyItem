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
		Bukkit.getLogger().info("Successfully load " + total + " items in RLI!");
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
				config.set("ExampleItem.lore", ListBuilder.buildList("&bThis is an test item!", "    &7---- which has been created on rli."));
				config.set("ExampleItem.Options", ListBuilder.buildList("MOVE_SPEED ~ 0.15", "UNBREAKABLE ~ true", "HIDE_UNBREAKABLE ~ true"
				, "HIDE_Attributes ~ true", "MAX_HEALTH ~ 3.5"));
				config.set("ExampleItem.Enchantment", ListBuilder.buildList("DAMAGE_ALL ~ 8"));
				config.set("ExampleItem.Skills", ListBuilder.buildList("launch ~ type=Fireball;damage=5 @LEFT", "launch ~ type=WitherSkull;damage=5 @RIGHT"));
				config.save(genFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
