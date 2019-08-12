package com.locydragon.rli.listeners.sub;

import com.locydragon.rli.api.SkillExecuteEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.*;

public class LaunchExecutor implements Listener {
	public static final String NONE = "NONE";
	public static List<String> values = new ArrayList<>();
	public static HashMap<String,Class<? extends Projectile>> classMap = new HashMap<>();
	public static HashMap<UUID,Integer> damageMap = new HashMap<>();

	static {
		String values = "AbstractArrow, Arrow, DragonFireball, Egg, EnderPearl, Fireball, FishHook, LargeFireball, LingeringPotion, LlamaSpit, ShulkerBullet, " +
				"SmallFireball, Snowball, SpectralArrow, SplashPotion, ThrownExpBottle, ThrownPotion, TippedArrow, Trident, WitherSkull";
		for (String each : values.split(",")) {
			LaunchExecutor.values.add(each.trim());
			try {
				classMap.put(each, (Class<? extends Projectile>) Class.forName("org.bukkit.entity." + each));
			} catch (ClassNotFoundException e) {
				continue;
			}
		}
	}

	@EventHandler
	public void onExecute(SkillExecuteEvent e) {
		if (e.getEventType().equalsIgnoreCase("launch")) {
			String damage = e.getOption().getValue(NONE, "damage", "d", "da", "dg");
			String type = e.getOption().getValue(NONE, "type", "t", "ty");
			if (type.equals(NONE)) {
				type = values.get(new Random().nextInt(values.size()) - 1);
			} else {
				for (String object : values) {
					if (object.equalsIgnoreCase(type)) {
						type = object;
					}
				}
			}
			if (!values.contains(type)) {
				Bukkit.getLogger().info("Warning: Type wrong with item " + e.getOnUseItem().getID() + "'s skill: launch!Type not found!");
				return;
			}
			if (classMap.getOrDefault(type, null) == null) {
				Bukkit.getLogger().info("Warning: Type wrong with item " + e.getOnUseItem().getID() + "'s skill: launch!Type version error.");
				return;
			}
			UUID entityUUID = e.getPlayer().launchProjectile(classMap.get(type), e.getPlayer().getVelocity()).getUniqueId();
		}
	}
}
