package com.locydragon.rli.util;

public class LangReader {
	String info;

	public LangReader(String info) {
		this.info = info.trim();
	}

	public String headValue() {
		return info.split("~", 2)[0].trim();
	}

	public String value() {
		return info.split("~", 2)[1].trim();
	}

	public boolean illegal() {
		return info.split("~", 2).length == 2;
	}
}
