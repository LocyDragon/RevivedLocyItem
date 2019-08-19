package com.locydragon.rli;

import com.locydragon.rli.commands.CommandDiverter;
import com.locydragon.rli.init.*;
import com.locydragon.rli.runnable.ItemSyncRunnable;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author LocyDragon
 */
public class RevivedLocyItem extends JavaPlugin {
	public static FileConfiguration mainConfiguration = null;
	public static ConfigMaster configMaster = null;
	public static RevivedLocyItem instance;
	public static boolean isCitizensOnServer = false;

	@Override
	public void onEnable() {
		/** 输出插件信息 **/
		new InfoLogger(this).printOutLog();
		/** 加载配置文件 **/
		configMaster = new ConfigMaster(this);
		configMaster.initConfig();
		/** 注册指令 **/
		new ListenerRegisters(this).registerListeners();
		/** 注册指令呀 **/
		Bukkit.getPluginCommand("rli").setExecutor(new CommandDiverter());
		Bukkit.getPluginCommand("rlitem").setExecutor(new CommandDiverter());
		Bukkit.getPluginCommand("revivedlocyitem").setExecutor(new CommandDiverter());
		/** 初始化编辑者模式 **/
		EditorInventoryIniter.initMainInventory();
		SubCommandRegister.registerSubCommands();/** 注册子命令呀 **/
		instance = this;

		if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			Bukkit.getPluginManager().disablePlugin(this);
			getLogger().info("Failed to find plugin PlaceholderAPI……");
		}

		new ItemSyncRunnable().runTaskTimer(this, 0
				, getConfig().getInt("SyncDelay") * 20);

		isCitizensOnServer = Bukkit.getPluginManager().isPluginEnabled("Citizens");
		new Metrics(this);
	}
}
