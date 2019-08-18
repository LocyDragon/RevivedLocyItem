package com.locydragon.rli.util;

import com.locydragon.rli.util.enums.JobType;

public class ParticleJob {
	private JobType type;
	private OptionReader reader;
	private String extraValue = null;

	public ParticleJob(JobType type, OptionReader option) {
		this.type = type;
		this.reader = option;
	}

	public JobType getType() {
		return this.type;
	}

	public OptionReader getOptions() {
		return this.reader;
	}

	public void setExtra(String value) {
		this.extraValue = value;
	}

	public String getExtraValue() {
		return this.extraValue;
	}
}
