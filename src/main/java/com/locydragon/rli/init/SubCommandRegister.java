package com.locydragon.rli.init;

import com.locydragon.rli.commands.CommandDiverter;
import com.locydragon.rli.commands.subcommands.EditorOpenCmd;
import com.locydragon.rli.commands.subcommands.VersionHelperCmd;

public class SubCommandRegister {
	public static void registerSubCommands() {
		CommandDiverter.addSubBasicCommand(new VersionHelperCmd());
		CommandDiverter.addSubBasicCommand(new EditorOpenCmd());
	}
}
