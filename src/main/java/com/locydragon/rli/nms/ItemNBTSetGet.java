package com.locydragon.rli.nms;

import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ItemNBTSetGet {
	private static String version = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	private static Class<?> craftItemClass = null;
	private static Class<?> nmsItemClass = null;
	private static Class<?> nbtCore = null;

	static {
		try {
			craftItemClass = Class.forName("org.bukkit.craftbukkit." + version+ ".inventory.CraftItemStack");
			nmsItemClass = Class.forName("net.minecraft.server." + version +".ItemStack");
			nbtCore = Class.forName("net.minecraft.server." + version +".NBTTagCompound");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void addPluginTag(ItemStack item, String ID) {
		try {
			Object nmsItem = craftItemClass.getMethod("asNMSCopy", ItemStack.class).invoke(item, null);
			Object tag = nmsItemClass.getMethod("getTag").invoke(nmsItem);
			if (tag == null) {
				try {
					tag = nbtCore.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
			}
			nbtCore.getMethod("setString", new Class[] {String.class, String.class}).invoke(tag, new Object[] {"LOCYITEM", ID});
			nmsItemClass.getMethod("setTag", nbtCore).invoke(nmsItem, tag);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	public static String getPluginTag(ItemStack item) {
		try {
			Object nmsItem = craftItemClass.getMethod("asNMSCopy", ItemStack.class).invoke(item, null);
			Object tag = nmsItemClass.getMethod("getTag").invoke(nmsItem);
			if (tag == null) {
				return null;
			}
			Object obj = nbtCore.getMethod("get", String.class).invoke(tag, "LOCYITEM");
			if (obj == null) {
				return null;
			}
			try {
				Field field = obj.getClass().getDeclaredField("data");
				field.setAccessible(true);
				return (String) field.get(obj);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
}
