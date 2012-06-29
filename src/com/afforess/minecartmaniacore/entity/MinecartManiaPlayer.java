package com.afforess.minecartmaniacore.entity;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.afforess.minecartmaniacore.inventory.MinecartManiaInventory;
import com.afforess.minecartmaniacore.inventory.MinecartManiaSingleContainer;

public class MinecartManiaPlayer extends MinecartManiaSingleContainer implements MinecartManiaInventory{
	private final String name;
	private String lastStation = "";
	private ConcurrentHashMap<String, Object> data = new ConcurrentHashMap<String,Object>();
	public MinecartManiaPlayer(String name) {
		super(null);
		this.name = name;
		if (isOnline()) {
			updateInventory(getInventory());
		}
	}
	
	/** 
	 * Returns the player that this MinecartManiaPlayer represents
	 * MinecartManiaPlayer' represent a player in whatever state they are in, online or offline. Because of this, getPlayer will return null when offline.
	 * @return player
	 */
	public final Player getPlayer() {
		return Bukkit.getServer().getPlayer(name);
	}
	
	public final String getName() {
		return name;
	}
	
	public final boolean isOnline() {
		return getPlayer() != null;
	}
	
	public final String getLastStation() {
		return lastStation;
	}
	
	public final void setLastStation(String s) {
		lastStation = s;
	}
	
	public void sendMessage(String chat) {		
		getPlayer().sendMessage(chat);
	}
	/**
	 ** Returns the value from the loaded data
	 ** @param the string key the data value is associated with
	 **/
	 public final Object getDataValue(String key) {
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
	 public final void setDataValue(String key, Object value) {
		 if (value == null) {
			 data.remove(key);
		 }else {
			 data.put(key, value);
		 }
	 }
}
