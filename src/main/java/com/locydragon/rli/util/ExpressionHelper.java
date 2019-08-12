package com.locydragon.rli.util;

import org.bukkit.entity.Player;

public class ExpressionHelper {
	public static double run(Player who, String param) {
		return new Calculator().calculate(who, param);
	}
}
