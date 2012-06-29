package com.afforess.minecartmaniacore.inventory;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Dispenser;
import org.bukkit.inventory.Inventory;

public class MinecartManiaDispenser extends MinecartManiaSingleContainer implements MinecartManiaInventory{

	private final Location dispenser;
	private ConcurrentHashMap<String, Object> data = new ConcurrentHashMap<String,Object>();
	
	public MinecartManiaDispenser(Dispenser dispenser) {
		super(dispenser.getInventory());
		this.dispenser = dispenser.getBlock().getLocation().clone();
	}
	
	public int getX() {
		return this.dispenser.getBlockX();
	}
	
	public int getY() {
		return this.dispenser.getBlockY();
	}
	
	public int getZ() {
		return this.dispenser.getBlockZ();
	}
	
	public World getWorld() {
		return this.dispenser.getWorld();
	}
	
	public Location getLocation() {
		return dispenser;
	}
	
	public Dispenser getDispenser() {
		return (Dispenser)dispenser.getBlock().getState();
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
			return getDispenser().getInventory();
		}
}
