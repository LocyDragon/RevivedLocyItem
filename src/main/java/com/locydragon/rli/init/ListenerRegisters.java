package com.locydragon.rli.init;

import com.locydragon.rli.RevivedLocyItem;
import com.locydragon.rli.editor.listeners.MainMenuListener;
import com.locydragon.rli.editor.optionreader.OptionDamage;
import com.locydragon.rli.factory.OptionReaders;
import com.locydragon.rli.listeners.InteractFatherListener;
import com.locydragon.rli.listeners.sub.CommandExecutor;
import com.locydragon.rli.listeners.sub.LaunchExecutor;
import com.locydragon.rli.listeners.sub.NearByExecutor;
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
		registerEventForBukkit(new LaunchExecutor());
		registerEventForBukkit(new MainMenuListener());
		registerEventForBukkit(new OptionReaders());
		registerEventForBukkit(new OptionDamage());
		registerEventForBukkit(new CommandExecutor());
		registerEventForBukkit(new NearByExecutor());
	}

	public void registerEventForBukkit(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, registers);
	}
}
