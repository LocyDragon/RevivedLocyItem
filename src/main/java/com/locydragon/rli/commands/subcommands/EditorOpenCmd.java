package com.locydragon.rli.commands.subcommands;

import com.locydragon.rli.commands.SubCmd;
import com.locydragon.rli.editor.listeners.MainMenuListener;
import com.locydragon.rli.util.ConfigLinker;
import org.bukkit.entity.Player;

public class EditorOpenCmd implements SubCmd {

	@Override
	public void runSubCommand(Player player, String[] arguments) {
		player.openInventory(MainMenuListener.MAIN_MENU.targetInventory());
		player.sendMessage(ConfigLinker.readConfigLang("EditorMode", null, null	));
	}

	@Override
	public String tellMeCmdPrefix() {
		return "GO";
	}
}
