package com.locydragon.rli.util.particle;

public class LocationModel {
	public double x;
	public double y;
	public double z;
	public LocationModel(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public LocationModel(org.bukkit.Location loc) {
		this.x = loc.getX();
		this.y = loc.getY();
		this.z = loc.getZ();
	}

	public LocationModel clone() {
		return new LocationModel(this.x, this.y, this.z);
	}
}
