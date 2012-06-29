package com.afforess.minecartmaniacore.event;

import org.bukkit.event.HandlerList;

import com.afforess.minecartmaniacore.debug.DebugTimer;

public abstract class MinecartManiaEvent extends org.bukkit.event.Event{
	private final DebugTimer timer;
	private static final HandlerList handlers = new HandlerList();

	protected MinecartManiaEvent(String name) {
		super();
		timer = new DebugTimer(name);
	}
	
	public void logProcessTime() {
		timer.logProcessTime();
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	} 
	
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
