package com.locydragon.rli.api;

import com.locydragon.rli.nms.ItemNBTSetGet;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ConcurrentHashMap;

public class LocyItemAPI {
	private static ConcurrentHashMap<String,LocyItem> itemStack = new ConcurrentHashMap<>();

	public static void registerItem(LocyItem item) {
		itemStack.put(item.getID(), item);
	}

	public static LocyItem getItem(String ID) {
		return itemStack.getOrDefault(ID, null);
	}

	public static void clearRegisteredItem() {
		itemStack.clear();
	}

	public static boolean isLocyItem(ItemStack item) { return ItemNBTSetGet.getPluginTag(item) != null; }

	public static String getID(ItemStack item) {
		if (isLocyItem(item)) {
			return ItemNBTSetGet.getPluginTag(item);
		} else {
			return null;
		}
	}

	public static LocyItem asLocyItem(ItemStack item) {
		if (!isLocyItem(item)) {
			return null;
		}
		return getItem(getID(item));
	}
}
