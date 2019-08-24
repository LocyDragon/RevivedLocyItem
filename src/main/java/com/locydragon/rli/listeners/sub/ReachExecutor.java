package com.locydragon.rli.listeners.sub;

import com.locydragon.rli.api.ItemCauseDamageEvent;
import com.locydragon.rli.api.SkillExecuteEvent;
import com.locydragon.rli.util.ExpressionHelper;
import com.locydragon.rli.util.particle.ParticleExpression;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ReachExecutor implements Listener {
	@EventHandler
	public void onExecute(SkillExecuteEvent e) {
		if (e.getEventType().equalsIgnoreCase("reach")) {
			double damage = ExpressionHelper.run(e.getPlayer(),
					e.getOption().getValue("1", "damage", "d", "da", "dg"));
			double range = Double.valueOf(e.getOption().getValue("5", "range", "r"));
			LivingEntity target = (LivingEntity) ParticleExpression.getCursorTarget(e.getPlayer(), range);
			if (target != null) {
				ItemCauseDamageEvent event = new ItemCauseDamageEvent(e.getPlayer(), e.getEventType()
						, e.getOption(), e.getOnUseItem(), damage, target);
				Bukkit.getPluginManager().callEvent(event);
				target.damage(event.getDamage(), e.getPlayer());
			}
		}
	}
}
