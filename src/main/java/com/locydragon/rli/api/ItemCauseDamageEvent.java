package com.locydragon.rli.api;

import com.locydragon.rli.util.OptionReader;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class ItemCauseDamageEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private Player player;
	private String event;
	private OptionReader reader;
	private LocyItem item;
	private UUID eventID;

	public ItemCauseDamageEvent(Player player, String event, OptionReader reader, LocyItem item) {
		this.player = player;
		this.event = event;
		this.reader = reader;
		this.item = item;
		eventID = UUID.randomUUID();
	}

	public UUID getEventID() {
		return this.eventID;
	}

	public LocyItem getOnUseItem() {
		return this.item;
	}

	public OptionReader getOption() {
		return this.reader;
	}

	public String getEventType() {
		return this.event;
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
