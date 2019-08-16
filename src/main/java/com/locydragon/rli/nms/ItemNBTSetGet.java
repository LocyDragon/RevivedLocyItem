package com.locydragon.rli.nms;

import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ItemNBTSetGet {
	private static String version = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	private static Class<?> craftItemClass = null;
	private static Class<?> nmsItemClass = null;
	private static Class<?> nbtCore = null;
	private static Class<?> nbtList = null;
	private static Class<?> nbtBase = null;

	static {
		try {
			craftItemClass = Class.forName("org.bukkit.craftbukkit." + version+ ".inventory.CraftItemStack");
			nmsItemClass = Class.forName("net.minecraft.server." + version +".ItemStack");
			nbtCore = Class.forName("net.minecraft.server." + version +".NBTTagCompound");
			nbtList = Class.forName("net.minecraft.server." + version +".NBTTagList");
			nbtBase = Class.forName("net.minecraft.server." + version +".NBTBase");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static ItemStack addPluginTag(ItemStack item, String ID) {
		Object nmsItem = null;
		try {
			nmsItem = craftItemClass.getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
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
		try {
			return (ItemStack) craftItemClass.getMethod("asBukkitCopy",nmsItemClass).invoke(null, nmsItem);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		throw new IllegalArgumentException();
	}

	public static ItemStack unbreakable(ItemStack item) {
		Object nmsItem = null;
		try {
			nmsItem = craftItemClass.getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
			Object tag = nmsItemClass.getMethod("getTag").invoke(nmsItem);
			if (tag == null) {
				try {
					tag = nbtCore.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
			}
			nbtCore.getMethod("setByte", new Class[] {String.class, byte.class}).invoke(tag, new Object[] {"Unbreakable", (byte)1});
			nmsItemClass.getMethod("setTag", nbtCore).invoke(nmsItem, tag);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			return (ItemStack) craftItemClass.getMethod("asBukkitCopy",nmsItemClass).invoke(null, nmsItem);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		throw new IllegalArgumentException();
	}

	public static ItemStack setInt(ItemStack item, String key, double value) {
		Object nmsItem = null;
		try {
			nmsItem = craftItemClass.getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
			Object tag = nmsItemClass.getMethod("getTag").invoke(nmsItem);
			if (tag == null) {
				try {
					tag = nbtCore.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
			}
			Object list = nbtList.cast(nbtCore.getMethod("get", String.class).invoke(tag, new Object[] {"AttributeModifiers"}));
			if (list == null) {
				try {
					list = nbtList.cast(nbtList.newInstance());
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
			}
			try {
				Object tagInstance = nbtCore.newInstance();
				nbtCore.getMethod("setString", new Class[] {String.class, String.class}).invoke(tagInstance,
						new Object[] {"AttributeName", key});
				nbtCore.getMethod("setString", new Class[] {String.class, String.class}).invoke(tagInstance,
						new Object[] {"Name", key.split("\\.", 2)[1]});
				nbtCore.getMethod("setDouble", new Class[] {String.class, double.class}).invoke(tagInstance,
						new Object[] {"Amount", value});
				nbtCore.getMethod("setInt", new Class[] {String.class, int.class}).invoke(tagInstance,
						new Object[] {"Operation", 0});
				nbtCore.getMethod("setInt", new Class[] {String.class, int.class}).invoke(tagInstance,
						new Object[] {"UUIDLeast", 20000});
				nbtCore.getMethod("setInt", new Class[] {String.class, int.class}).invoke(tagInstance,
						new Object[] {"UUIDMost", 1000});
				nbtList.getMethod("add", nbtBase).invoke(list,
						new Object[] {tagInstance});
				nbtCore.getMethod("set", String.class, nbtBase).invoke(tag, "AttributeModifiers", list);
				nmsItemClass.getMethod("setTag", nbtCore).invoke(nmsItem, tag);
			} catch (InstantiationException e) {
				e.printStackTrace();
			}
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		try {
			return (ItemStack) craftItemClass.getMethod("asBukkitCopy",nmsItemClass).invoke(null, nmsItem);
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		throw new IllegalArgumentException();
	}

	public static String getPluginTag(ItemStack item) {
		try {
			Object nmsItem = craftItemClass.getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
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
