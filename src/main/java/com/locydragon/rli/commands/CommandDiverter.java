package com.locydragon.rli.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LocyDragon
 */
public class CommandDiverter implements CommandExecutor {
	public static List<SubCmd> cmdAche = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		for (SubCmd subCmd : cmdAche) {
			if (subCmd.tellMeCmdPrefix().equalsIgnoreCase(args[0])) {
				subCmd.runSubCommand((Player)sender, args);
				return false;
			}
		}
		return false;
	}

	public static void addSubBasicCommand(SubCmd cmd) {
		cmdAche.add(cmd);
	}
}
