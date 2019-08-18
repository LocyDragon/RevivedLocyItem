package com.locydragon.rli.api;

import com.locydragon.rli.util.LangReader;
import com.locydragon.rli.util.OptionReader;
import com.locydragon.rli.util.ParticleJob;
import com.locydragon.rli.util.particle.LocationModel;

import java.util.List;
import java.util.Vector;

public class ParticleUnit {
	public float offsetX = 0;
	public float offsetY = 0;
	public float offsetZ = 0;
	public float speed = 0.2F;
	public int amount = 3;
	public int range = 10;
	public String name;
	public LocationModel centre;
	private Vector<ParticleJob> jobList = new Vector<>();

	public ParticleUnit(String name, LocationModel origin) {
		this.name = name;
		this.centre = origin;
	}

	public void load(List<String> effects) {
		for (String obj : effects) {
			LangReader line = new LangReader(obj);
			if (!line.illegal()) {
				continue;
			}
			OptionReader reader = new OptionReader(line.value());
		}
	}
}
