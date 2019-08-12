package com.locydragon.rli.init;

import com.locydragon.rli.RevivedLocyItem;
import com.locydragon.rli.editor.listeners.MainMenuListener;
import com.locydragon.rli.listeners.InteractFatherListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class ListenerRegisters {
	private RevivedLocyItem registers;
	public ListenerRegisters(RevivedLocyItem plugin) {
		this.registers = plugin;
	}

	//TODO WRITE LISTENERS HERE
	public void registerListeners() {
		registerEventForBukkit(new InteractFatherListener());
		registerEventForBukkit(new MainMenuListener());
	}

	public void registerEventForBukkit(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, registers);
	}
}
