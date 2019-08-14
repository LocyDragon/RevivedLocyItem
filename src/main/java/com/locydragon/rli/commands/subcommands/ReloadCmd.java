package com.locydragon.rli.commands.subcommands;

import com.locydragon.rli.RevivedLocyItem;
import com.locydragon.rli.commands.SubCmd;
import com.locydragon.rli.init.ConfigMaster;
import com.locydragon.rli.io.ItemConfigReader;
import com.locydragon.rli.util.Colors;
import com.locydragon.rli.util.ConfigLinker;
import org.bukkit.entity.Player;

public class ReloadCmd implements SubCmd {
	@Override
	public void runSubCommand(Player commandSender, String[] arguments) {
		if (!commandSender.hasPermission("RevivedLocyItem.admin")) {
			return;
		}
		ItemConfigReader.readFile();
		ConfigMaster.reloadConfig();
		String msg = ConfigLinker.readConfigLang
				("Reload", "{prefix}", Colors.color(RevivedLocyItem.mainConfiguration.getString("prefix")));
		commandSender.sendMessage(msg);
	}

	@Override
	public String tellMeCmdPrefix() {
		return "reload";
	}
}
