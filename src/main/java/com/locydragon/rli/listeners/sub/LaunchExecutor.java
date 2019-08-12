package com.locydragon.rli.listeners.sub;

import com.locydragon.rli.api.SkillExecuteEvent;
import com.locydragon.rli.util.ExpressionHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.*;

public class LaunchExecutor implements Listener {
	public static final String NONE = "NONE";
	public static List<String> values = new ArrayList<>();
	public static HashMap<String,Class<? extends Projectile>> classMap = new HashMap<>();
	public static HashMap<UUID,Double> damageMap = new HashMap<>();

	static {
		String values = "AbstractArrow, Arrow, DragonFireball, Egg, EnderPearl, Fireball, FishHook, LargeFireball, LingeringPotion, LlamaSpit, ShulkerBullet, " +
				"SmallFireball, Snowball, SpectralArrow, SplashPotion, ThrownExpBottle, ThrownPotion, TippedArrow, Trident, WitherSkull";
		for (String each : values.split(",")) {
			LaunchExecutor.values.add(each.trim());
			try {
				classMap.put(each.trim(), (Class<? extends Projectile>) Class.forName("org.bukkit.entity." + each.trim()));
			} catch (ClassNotFoundException e) {
				continue;
			}
		}
	}

	@EventHandler
	public void onRemove(EntityDeathEvent e) {
		if (damageMap.containsKey(e.getEntity().getUniqueId())) {
			damageMap.remove(e.getEntity().getUniqueId());
		}
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (damageMap.containsKey(e.getDamager().getUniqueId())) {
			e.setDamage(damageMap.get(e.getDamager().getUniqueId()));
			damageMap.remove(e.getDamager().getUniqueId());
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
				Bukkit.getLogger().info("Warning: Type wrong with item " + e.getOnUseItem().getID() + "'s skill: launch!Type not found!——" + type);
				return;
			}
			if (classMap.getOrDefault(type, null) == null) {
				Bukkit.getLogger().info("Warning: Type wrong with item " + e.getOnUseItem().getID() + "'s skill: launch!Type version error.——" + type);
				return;
			}
			UUID entityUUID = e.getPlayer().launchProjectile(classMap.get(type), e.getPlayer().getVelocity()).getUniqueId();
			try {
				if (isNum(damage)) {
					damageMap.put(entityUUID, Double.valueOf(damage));
				} else {
					damageMap.put(entityUUID, ExpressionHelper.run(e.getPlayer(), damage));
				}
			} catch (Exception exc) {
				Bukkit.getLogger().info("Warning: Type wrong with item " + e.getOnUseItem().getID() + "'s skill: launch!Expression error.——" + damage);
				return;
			}
		}
	}

	public static boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
}
