package com.locydragon.rli.api;

import com.locydragon.rli.util.LangReader;
import com.locydragon.rli.util.OptionReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SkillUnit {
	private HashMap<String,List<OptionReader>> skillsMap = new HashMap<>();
	private String name;
	private int coolDown;
	private String outMessage;

	public SkillUnit(String name, int coolDown, String outMessage) {
		this.name = name;
		this.coolDown = coolDown;
		this.outMessage = outMessage;
	}

	public void addSkill(String param) {
		LangReader langReader = new LangReader(param);
		OptionReader reader = new OptionReader(langReader.value());
		List<OptionReader> readerList = skillsMap.getOrDefault(langReader.headValue().trim().toLowerCase(), new ArrayList<>());
		readerList.add(reader);
		skillsMap.put(langReader.headValue().trim().toLowerCase(), readerList);
	}

	public String getName() {
		return this.name;
	}

	public int getCoolDown() {
		return this.coolDown;
	}

	public String getCDMessage() {
		return this.outMessage;
	}
}
