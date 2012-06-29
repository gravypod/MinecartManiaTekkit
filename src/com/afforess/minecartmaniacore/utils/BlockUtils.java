package com.afforess.minecartmaniacore.utils;

import java.util.HashSet;
import org.bukkit.Location;
import org.bukkit.block.Block;

import com.afforess.minecartmaniacore.world.MinecartManiaWorld;

public class BlockUtils {
	
	public static HashSet<Block> getAdjacentBlocks(Location location, int range) {
		//default constructor size is purely for efficiency reasons - and to show off my math skills
		HashSet<Block> blockList = new HashSet<Block>();
		Block center = MinecartManiaWorld.getBlockAt(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
		for (int dx = -(range); dx <= range; dx++){
			for (int dy = -(range); dy <= range; dy++){
				for (int dz = -(range); dz <= range; dz++){
					blockList.add(center.getRelative(dx, dy, dz));
				}
			}
		}
		return blockList;
	}
	
	public static HashSet<Location> getAdjacentLocations(Location location, int range) {
		HashSet<Location> set = new HashSet<Location>((int)Math.floor(Math.pow(1 + (range * 2), 3)));
		for (int dx = -(range); dx <= range; dx++){
			for (int dy = -(range); dy <= range; dy++){
				for (int dz = -(range); dz <= range; dz++){
					Location temp = new ComparableLocation(location);
					temp.setX(temp.getX() + dx);
					temp.setY(temp.getY() + dy);
					temp.setZ(temp.getZ() + dz);
					set.add(temp);
				}
			}
		}
		return set;
	}
	
	public static HashSet<Block> getBlocksBeneath(Location location, int range) {
		HashSet<Block> blockList = new HashSet<Block>();
		for (int dy = -range; dy <= 0; dy++) {
			blockList.add(MinecartManiaWorld.getBlockAt(location.getWorld(), location.getBlockX(), location.getBlockY()+dy, location.getBlockZ()));
		}
		return blockList;
	}
}