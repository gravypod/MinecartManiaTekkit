package com.afforess.minecartmaniacore.event;

import org.bukkit.event.Cancellable;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;

public class MinecartSpawnEvent extends MinecartManiaEvent implements Cancellable{

	private boolean cancelled = false;
	private MinecartManiaMinecart minecart;
	protected MinecartSpawnEvent(MinecartManiaMinecart minecart) {
		super("MinecartSpawnEvent");
		this.minecart = minecart;
	}
	
	public MinecartManiaMinecart getMinecart() {
		return this.minecart;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}

}
