package com.afforess.minecartmaniacore.inventory;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.Inventory;

public class MinecartManiaFurnace extends MinecartManiaSingleContainer implements MinecartManiaInventory{

	private final Location furnace;
	private ConcurrentHashMap<String, Object> data = new ConcurrentHashMap<String,Object>();
	
	public MinecartManiaFurnace(Furnace furnace) {
		super(furnace.getInventory());
		this.furnace = furnace.getBlock().getLocation().clone();
	}
	
	public int getX() {
		return this.furnace.getBlockX();
	}
	
	public int getY() {
		return this.furnace.getBlockY();
	}
	
	public int getZ() {
		return this.furnace.getBlockZ();
	}
	
	public World getWorld() {
		return this.furnace.getWorld();
	}
	
	public Location getLocation() {
		return furnace;
	}
	
	public Furnace getFurnace() {
		return (Furnace)furnace.getBlock().getState();
	}
	
	/**
	 ** Returns the value from the loaded data
	 ** @param the string key the data value is associated with
	 **/
	 public Object getDataValue(String key) {
		 if (data.containsKey(key)) {
			 return data.get(key);
		 }
		 return null;
	 }
	 
	/**
	 ** Creates a new data value if it does not already exists, or resets an existing value
	 ** @param the string key the data value is associated with
	 ** @param the value to store
	 **/	 
	 public void setDataValue(String key, Object value) {
		 if (value == null) {
			 data.remove(key);
		 }else {
			 data.put(key, value);
		 }
	 }
	 
	public Inventory getInventory() {
		return getFurnace().getInventory();
	}
}
