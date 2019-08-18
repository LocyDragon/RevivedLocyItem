package com.locydragon.rli.util.particle;

import com.locydragon.rli.util.Calculator;
import com.locydragon.rli.util.ExpressionHelper;
import org.bukkit.Location;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ParticleExpression {
	public static List<LocationModel> buildLine(Location locA, Location locB, double precision) {
		List<LocationModel> save = new ArrayList<>();
		org.bukkit.util.Vector vectorAB = locB.clone().subtract(locA).toVector();
		double vectorLength = vectorAB.length();
		vectorAB.normalize();
		for (double i = 0; i < vectorLength; i +=precision) {
			org.bukkit.util.Vector vector = vectorAB.clone().multiply(i);
			locA.add(vector);
			save.add(new LocationModel(locA));
			locA.subtract(vector);
		}
		return save;
	}

	public static Set<LocationModel> sendParticleCircle(Location loc, double radius, double precisionExtra) {
		Set<LocationModel> result = new HashSet<>();
		for (double degree = 0; degree < 360; degree += precisionExtra) {
			double offsetX = Math.sin(degree/180D * Math.PI) * radius;
			double offsetZ = Math.cos(degree/180D * Math.PI) * radius;
			Location result1 = loc.clone();
			result1.add(offsetX, 0D, offsetZ);
			result.add(new LocationModel(result1));
		}
		return result;
	}

	public static List<LocationModel> asFunction(String expression, double start, double end, double precision, double y_origin, boolean symmetric) {
		List<LocationModel> save = new ArrayList<>();
		for (BigDecimal begin = new BigDecimal(String.valueOf(start))
			 ; begin.compareTo(new BigDecimal(end)) == -1; begin = begin.add(new BigDecimal(precision))) {
			String value;
			if (begin.compareTo(new BigDecimal(0)) == -1) {
				value = "(0-" + String.valueOf(Math.abs(begin.setScale(10,
						BigDecimal.ROUND_HALF_UP).doubleValue())) + ")";
			} else {
				value = String.valueOf(begin.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue());
			}
			double output = new Calculator()
					.calculateWithOutPlayer(expression.replace("x", value));
			if (!symmetric) {
				save.add(new LocationModel(begin.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue(), y_origin, output));
			} else {
				save.add(new LocationModel(output, y_origin, begin.setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue()));
			}
		}
		return save;
	}
}
