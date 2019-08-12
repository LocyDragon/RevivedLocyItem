package com.locydragon.rli.init;

import com.locydragon.rli.commands.CommandDiverter;
import com.locydragon.rli.commands.subcommands.ItemRewardCmd;
import com.locydragon.rli.commands.subcommands.ItemRewardCmdCopy;
import com.locydragon.rli.commands.subcommands.ReloadCmd;
import com.locydragon.rli.commands.subcommands.VersionHelperCmd;

public class SubCommandRegister {
	public static void registerSubCommands() {
		CommandDiverter.addSubBasicCommand(new VersionHelperCmd());
		CommandDiverter.addSubBasicCommand(new ItemRewardCmd());
		CommandDiverter.addSubBasicCommand(new ItemRewardCmdCopy());
		CommandDiverter.addSubBasicCommand(new ReloadCmd());
	}
}
