package com.locydragon.rli.factory;

import com.locydragon.rli.api.ParticleUnit;
import com.locydragon.rli.util.particle.LocationModel;
import org.bukkit.configuration.file.FileConfiguration;

public class ParticleConfigReaderFactory {
	public static int readFile(FileConfiguration config) {
		int find = 0;
		for (String key : config.getKeys(false)) {
			find++;
			ParticleUnit unit = new ParticleUnit(key, new LocationModel
					(config.getString(key + ".origin", "(0,0,0)")));
		}
		return find;
	}
}
