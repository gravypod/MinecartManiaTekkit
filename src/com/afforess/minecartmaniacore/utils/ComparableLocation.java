package com.afforess.minecartmaniacore.utils;

import org.bukkit.Location;

public class ComparableLocation extends Location {
	public ComparableLocation(Location location) {
		super(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
	}
	
	@Override
	public int hashCode() {
		return this.getBlock().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Location) {
			return this.getBlock().equals(((Location)obj).getBlock());
		}
		return false;
	}

}
