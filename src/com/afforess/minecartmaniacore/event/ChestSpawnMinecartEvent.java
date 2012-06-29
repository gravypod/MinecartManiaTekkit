package com.afforess.minecartmaniacore.event;

import org.bukkit.Location;
import org.bukkit.event.Cancellable;

import com.afforess.minecartmaniacore.inventory.MinecartManiaChest;

public class ChestSpawnMinecartEvent extends MinecartManiaEvent implements Cancellable{
	private MinecartManiaChest chest;
	private Location spawnLocation;
	private boolean cancelled = false;
	private int type;
	
	public ChestSpawnMinecartEvent(MinecartManiaChest chest, Location spawnLocation, int type) {
		super("ChestPoweredEvent");
		this.chest = chest;
		this.spawnLocation = spawnLocation;
		this.type = type;
	}
	
	public MinecartManiaChest getChest(){
		return chest;
	}
	
	public Location getSpawnLocation() {
		return spawnLocation.clone();
	}
	
	public void setSpawnLocation(Location l) {
		spawnLocation = l;
	}
	
	/**
	 * The type of minecart to be spawned. 0 - Standard. 1 - Powered. 2 - Storage.
	 * @return type.
	 */
	public int getMinecartType() {
		return type;
	}
	
	/**
	 * Sets the type of minecart to be spawned. 0 - Standard. 1 - Powered. 2 - Storage.
	 */
	public void setMinecartType(int type) {
		this.type = type;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}

}
