package com.locydragon.rli.listeners.sub;

import com.locydragon.rli.api.SkillExecuteEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CommandExecutor implements Listener {
	@EventHandler
	public void onExecutor(SkillExecuteEvent e) {
		if (e.getEventType().equalsIgnoreCase("command")) {
			String cmdType = e.getOption().getValue("NONE", "type", "t", "ty");
			String command = e.getOption().getValue("NONE", "command", "cmd", "c");
			if (cmdType.equalsIgnoreCase("NONE")) {
				if (!command.startsWith("/")) {
					e.getPlayer().chat("/" + command);
				} else {
					e.getPlayer().chat(command);
				}
				return;
			}
			if (cmdType.equalsIgnoreCase("player")) {
				if (!command.startsWith("/")) {
					e.getPlayer().chat("/" + command);
				} else {
					e.getPlayer().chat(command);
				}
				return;
			}
			if (cmdType.equalsIgnoreCase("op")) {
				if (!command.startsWith("/")) {
					boolean isOp = e.getPlayer().isOp();
					e.getPlayer().setOp(true);
					e.getPlayer().chat("/" + command);
					e.getPlayer().setOp(isOp);
				} else {
					boolean isOp = e.getPlayer().isOp();
					e.getPlayer().setOp(true);
					e.getPlayer().chat(command);
					e.getPlayer().setOp(isOp);
				}
				return;
			}
			if (cmdType.equalsIgnoreCase("console")) {
				if (command.startsWith("/")) {
					command = command.replace("/", "");
				}
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
			}
		}
	}
}
