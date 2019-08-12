package com.locydragon.rli.util;

import com.locydragon.rli.RevivedLocyItem;
import org.bukkit.ChatColor;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class ConfigLinker {
	public static String readConfigLang(String path, String placeHolder, String to) {
		String output = RevivedLocyItem.configMaster.showConfig().getString("Lang.lang-" + path);
		output = Colors.color(output);
		if (placeHolder != null) {
			output = output.replace(placeHolder, to);
		}
		try {
			return URLDecoder.decode(URLEncoder.encode(output, RevivedLocyItem.configMaster.showConfig().getString("Plugin-Encode", "GB2312")),
					RevivedLocyItem.configMaster.showConfig().getString("Plugin-Decode", "GB18030"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ChatColor.RED + "插件编码错误……(Unknown Plugin Encode Type)";
	}
}
