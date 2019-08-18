package com.locydragon.rli.api;

import com.locydragon.rli.util.LangReader;
import com.locydragon.rli.util.OptionReader;
import com.locydragon.rli.util.ParticleJob;
import com.locydragon.rli.util.enums.JobType;
import com.locydragon.rli.util.particle.LocationModel;
import com.locydragon.rli.util.particle.ParticleEffect;
import com.locydragon.rli.util.particle.ParticleExpression;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ParticleUnit {
	public static Executor executor = Executors.newCachedThreadPool();

	public float offsetX = 0;
	public float offsetY = 0;
	public float offsetZ = 0;
	public float speed = 0.2F;
	public int amount = 3;
	public int range = 10;
	public String name;
	public LocationModel centre;
	public double precision = 0.2;
	public double cireclePrecision = 10.0;
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
			if (line.headValue().equalsIgnoreCase("delay")) {
				ParticleJob job = new ParticleJob(JobType.DELAY, null);
				job.setExtra(line.value());
				jobList.add(job);
			} else {
				OptionReader reader = new OptionReader(line.value());
				if (line.headValue().equalsIgnoreCase("circle")) {
					jobList.add(new ParticleJob(JobType.DRAW_CIRCLE, reader));
				} else if (line.headValue().equalsIgnoreCase("line")) {
					jobList.add(new ParticleJob(JobType.DRAW_LINE, reader));
				} else if (line.headValue().equalsIgnoreCase("f")) {
					jobList.add(new ParticleJob(JobType.DRAW_FUNCTION, reader));
				}
			}
		}
		calculate();
	}

	private void calculate() {
		Vector<ParticleJob> newVector = new Vector<>();
		for (ParticleJob job : this.jobList) {
			if (job.getType() == JobType.DELAY) {
				newVector.add(job);
			} else if (job.getType() == JobType.DRAW_CIRCLE) {
				LocationModel centre = new LocationModel(job.getOptions().getValue(null, "centre", "c"));
				org.bukkit.Location fake = new
						Location(Bukkit.getWorlds().get(0), centre.x, centre.y, centre.z);
				for (LocationModel result : ParticleExpression.sendParticleCircle(fake,
						Double.valueOf(job.getOptions().getValue(null,"radius", "r")), this.cireclePrecision)) {
					result.x += this.centre.x;
					result.y += this.centre.y;
					result.z += this.centre.z;
					job.addResult(result);
				}
				job.effect = ParticleEffect.valueOf(job.getOptions().getValue(null, "type", "t"));
				newVector.add(job);
			} else if (job.getType() == JobType.DRAW_LINE) {
				LocationModel A = new LocationModel(job.getOptions().getValue(null,"A", "a"));
				LocationModel B = new LocationModel(job.getOptions().getValue(null,"B", "b"));
				org.bukkit.Location fakeA = new
						Location(Bukkit.getWorlds().get(0), A.x, A.y, A.z);
				org.bukkit.Location fakeB = new
						Location(Bukkit.getWorlds().get(0),B.x, B.y,B.z);
				for (LocationModel result : ParticleExpression.buildLine(fakeA, fakeB, this.precision)) {
					result.x += this.centre.x;
					result.y += this.centre.y;
					result.z += this.centre.z;
					job.addResult(result);
				}
				job.effect = ParticleEffect.valueOf(job.getOptions().getValue(null, "type", "t"));
				newVector.add(job);
			} else if (job.getType() == JobType.DRAW_FUNCTION) {
				double start = Double.valueOf(job.getOptions().getValue(null, "start", "s", "begin"));
				double end = Double.valueOf(job.getOptions().getValue(null,"end", "e", "stop"));
				String expression = job.getOptions().getValue(null,"expression", "ex");
				for (LocationModel result : ParticleExpression.asFunction(expression, start, end, this.precision, this.centre.y,
						Boolean.valueOf(job.getOptions().getValue("false","symmetric", "s")))) {
					result.x += this.centre.x;
					result.y += this.centre.y;
					result.z += this.centre.z;
					job.addResult(result);
				}
				job.effect = ParticleEffect.valueOf(job.getOptions().getValue(null, "type", "t"));
				newVector.add(job);
			}
		}
		this.jobList = newVector;
	}

	public void draw(org.bukkit.Location centre) {
		executor.execute(() -> {
			for (ParticleJob job : this.jobList) {
				if (job.getType() == JobType.DELAY) {
					try {
						Thread.sleep(Long.valueOf(job.getExtraValue().trim()));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					for (LocationModel model : job.result()) {
						org.bukkit.Location clone = centre.clone();
						clone.add(model.x, model.y, model.z);
						job.effect.display(this.offsetX, this.offsetY, this.offsetZ, this.speed, this.amount, clone, this.range);
					}
				}
			}
		});
	}
}
