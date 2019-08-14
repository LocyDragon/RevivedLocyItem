package com.locydragon.rli.listeners.sub;

import com.locydragon.rli.api.ItemCauseDamageEvent;
import com.locydragon.rli.api.SkillExecuteEvent;
import com.locydragon.rli.nms.NpcUtils;
import com.locydragon.rli.util.ExpressionHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NearByExecutor implements Listener {
	@EventHandler
	public void onExecute(SkillExecuteEvent e) {
		if (e.getEventType().equalsIgnoreCase("near")) {
			String x = e.getOption().getValue("0", "x");
			String y = e.getOption().getValue("0", "y");
			String z = e.getOption().getValue("0", "z");
			String damage = e.getOption().getValue("0", "damage", "d", "da", "dg");
			double damageDouble = ExpressionHelper.run(e.getPlayer(), damage);
			for (Entity nearBy : e.getPlayer().getNearbyEntities(Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z))) {
				if (nearBy instanceof LivingEntity && !NpcUtils.isNPC(nearBy)) {
					LivingEntity entity = (LivingEntity)nearBy;
					ItemCauseDamageEvent event = new ItemCauseDamageEvent(e.getPlayer(), e.getEventType()
							, e.getOption(), e.getOnUseItem(), damageDouble, entity);
					Bukkit.getPluginManager().callEvent(event);
					if (damageDouble != 0) {
						entity.damage(event.getDamage(), e.getPlayer());
					}
				}
			}
		}
	}
}
