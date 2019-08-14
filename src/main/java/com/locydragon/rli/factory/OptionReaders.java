package com.locydragon.rli.factory;

import com.locydragon.rli.api.LocyItem;
import com.locydragon.rli.editor.optionreader.OptionDamage;
import com.locydragon.rli.nms.ItemNBTSetGet;
import com.locydragon.rli.util.LangReader;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;

import java.util.List;

public class OptionReaders implements Listener {
	public static void read(List<String> params, LocyItem item) {
		for (String object : params) {
			LangReader reader = new LangReader(object);
			if (!reader.illegal()) {
				Bukkit.getLogger().info("Something wrong with item: " +item.getID() + "'s Option!" + object);
			}
			if (reader.headValue().equalsIgnoreCase("HIDE_ENCHANTS") && reader.value().equalsIgnoreCase("true")) {
				item.getItem().getItemMeta().addItemFlags(ItemFlag.HIDE_ENCHANTS);
			} else if (reader.headValue().equalsIgnoreCase("HIDE_ENCHANTS") && reader.value().equalsIgnoreCase("false")) {
				item.getItem().getItemMeta().removeItemFlags(ItemFlag.HIDE_ENCHANTS);
			}
			if (reader.headValue().equalsIgnoreCase("HIDE_UNBREAKABLE") && reader.value().equalsIgnoreCase("true")) {
				item.getItem().getItemMeta().addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			} else if (reader.headValue().equalsIgnoreCase("HIDE_UNBREAKABLE") && reader.value().equalsIgnoreCase("false")) {
				item.getItem().getItemMeta().removeItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			}
			if (reader.headValue().equalsIgnoreCase("HIDE_ATTRIBUTES") && reader.value().equalsIgnoreCase("true")) {
				item.getItem().getItemMeta().addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			} else if (reader.headValue().equalsIgnoreCase("HIDE_ATTRIBUTES") && reader.value().equalsIgnoreCase("false")) {
				item.getItem().getItemMeta().removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			}
			if (reader.headValue().equalsIgnoreCase("HIDE_POTION_EFFECTS") && reader.value().equalsIgnoreCase("true")) {
				item.getItem().getItemMeta().addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			} else if (reader.headValue().equalsIgnoreCase("HIDE_POTION_EFFECTS") && reader.value().equalsIgnoreCase("false")) {
				item.getItem().getItemMeta().removeItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			}
			if (reader.headValue().equalsIgnoreCase("DAMAGE")) {
				String value = reader.value();
				OptionDamage.IDDamageMap.put(item.getID(), value);
			}
			if (reader.headValue().equalsIgnoreCase("UNBREAKABLE")) {
				if (!reader.value().equalsIgnoreCase("false")) {
					item.setBuildItem(ItemNBTSetGet.unbreakable(item.getItem()));
				}
			}
			if (reader.headValue().equalsIgnoreCase("ATTACK_SPEED")) {
				item.setBuildItem(ItemNBTSetGet.setInt(item.getItem(),
						"generic.attackSpeed", Integer.valueOf(reader.value())));
			}
			if (reader.headValue().equalsIgnoreCase("MAX_HEALTH")) {
				item.setBuildItem(ItemNBTSetGet.setInt(item.getItem(),
						"generic.maxHealth", Integer.valueOf(reader.value())));
			}
			if (reader.headValue().equalsIgnoreCase("MOVE_SPEED")) {
				item.setBuildItem(ItemNBTSetGet.setInt(item.getItem(),
						"generic.movementSpeed", Integer.valueOf(reader.value())));
			}
			if (reader.headValue().equalsIgnoreCase("ARMOR_VALUE")) {
				item.setBuildItem(ItemNBTSetGet.setInt(item.getItem(),
						"generic.armor", Integer.valueOf(reader.value())));
			}
			if (reader.headValue().equalsIgnoreCase("LUCK_VALUE")) {
				item.setBuildItem(ItemNBTSetGet.setInt(item.getItem(),
						"generic.luck", Integer.valueOf(reader.value())));
			}
		}
	}
}
