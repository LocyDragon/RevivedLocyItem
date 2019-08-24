package com.locydragon.rli.listeners.heat.sub;

import com.locydragon.rli.listeners.heat.HeatCore;
import com.locydragon.rli.util.particle.LocationModel;
import com.locydragon.rli.util.particle.ParticleEffect;
import com.locydragon.rli.util.particle.ParticleExpression;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LineHeat implements Listener {
    private static Executor executor = Executors.newCachedThreadPool();

    @EventHandler
    public void core(HeatCore e) {
        if (e.getSkillType().equalsIgnoreCase("line")) {
            if (!e.getPlayer().getWorld().getName().equalsIgnoreCase(e.getOnEntity().getWorld().getName())) {
                return;
            }
            String type = e.getOption().getValue("HAPPY_VILLAGER", "type", "t").toUpperCase();
            executor.execute(() -> {
                List<Location> lineLocList = new ArrayList<>();
                for (LocationModel model : ParticleExpression.buildLine(e.getOnEntity().getLocation()
                        , e.getPlayer().getLocation(), 0.2)) {
                    Location where = new Location(e.getPlayer().getWorld(), model.x, model.y + 1, model.z);
                    ParticleEffect.valueOf(type).display
                            (0, 0, 0, 0, 5, where, 10);
                }
            });
        }
    }
}
