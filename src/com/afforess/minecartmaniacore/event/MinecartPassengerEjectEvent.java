package com.afforess.minecartmaniacore.event;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;

public class MinecartPassengerEjectEvent extends MinecartManiaEvent implements Cancellable{
	private MinecartManiaMinecart minecart;
	private Entity passenger;
	private boolean cancelled = false;

	public MinecartPassengerEjectEvent(MinecartManiaMinecart minecart, Entity passenger) {
		super("MinecartPassengerEjectEvent");
		this.minecart = minecart;
		this.passenger = passenger;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}

	public MinecartManiaMinecart getMinecart() {
		return minecart;
	}

	public Entity getPassenger() {
		return passenger;
	} 

}
