package com.afforess.minecartmaniacore.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.afforess.minecartmaniacore.world.MinecartManiaWorld;

public class EntityUtils {
	
	public static Location getValidLocation(Block block) {
		return getValidLocation(block, 3);
	}

	public static Location getValidLocation(Block block, int searchRange) {
		
		if (!isSolidMaterial(block.getRelative(BlockFace.UP).getType())) {
			if (!isSolidMaterial(block.getRelative(BlockFace.UP).getRelative(BlockFace.UP).getType())) {
				return new Location(block.getWorld(), block.getX()+ 0.5D, block.getY(), block.getZ() + 0.5D);
				
			}
		}
		
		for (int range = 1; range < searchRange+1; range++) {
			for (int dx = -(range); dx <= range; dx++){
				for (int dy = -(range); dy <= range; dy++){
					for (int dz = -(range); dz <= range; dz++){
						Block b = MinecartManiaWorld.getBlockAt(block.getWorld(), block.getX() + dx, block.getY() + dy, block.getZ() + dz);
						if (!isSolidMaterial(b.getType())) {
							if (!isSolidMaterial(b.getRelative(BlockFace.UP).getType())) {
								return new Location(b.getWorld(), b.getX()+ 0.5D, b.getY(), b.getZ() + 0.5D);
							}
						}
					}
				}
			}
		}
		return null;
	}
			
	public static boolean isSolidMaterial(Material m) {
		return
			m == Material.STONE ||
			m == Material.GRASS ||
			m == Material.DIRT ||
			m == Material.COBBLESTONE ||
			m == Material.WOOD ||
			m == Material.BEDROCK ||
			m == Material.SAND ||
			m == Material.GRAVEL ||
			m == Material.GOLD_ORE ||
			m == Material.IRON_ORE ||
			m == Material.COAL_ORE ||
			m == Material.LOG ||
			m == Material.LEAVES ||
			m == Material.SPONGE ||
			m == Material.LAPIS_ORE ||
			m == Material.LAPIS_BLOCK ||
			m == Material.DISPENSER ||
			m == Material.SANDSTONE ||
			m == Material.NOTE_BLOCK ||
			m == Material.WOOL ||
			m == Material.GOLD_BLOCK ||
			m == Material.IRON_BLOCK ||
			m == Material.DOUBLE_STEP ||
			m == Material.STEP ||
			m == Material.BRICK ||
			m == Material.TNT ||
			m == Material.BOOKSHELF ||
			m == Material.MOSSY_COBBLESTONE ||
			m == Material.OBSIDIAN ||
			m == Material.MOB_SPAWNER ||
			m == Material.WOOD_STAIRS ||
			m == Material.CHEST ||
			m == Material.DIAMOND_ORE ||
			m == Material.DIAMOND_BLOCK ||
			m == Material.WORKBENCH ||
			m == Material.SOIL ||
			m == Material.FURNACE ||
			m == Material.BURNING_FURNACE ||
			m == Material.COBBLESTONE_STAIRS ||
			m == Material.REDSTONE_ORE ||
			m == Material.GLOWING_REDSTONE_ORE ||
			m == Material.ICE ||
			m == Material.SNOW_BLOCK ||
			m == Material.CACTUS ||
			m == Material.CLAY ||
			m == Material.JUKEBOX ||
			m == Material.FENCE ||
			m == Material.PUMPKIN ||
			m == Material.NETHERRACK ||
			m == Material.SOUL_SAND ||
			m == Material.GLOWSTONE ||
			m == Material.JACK_O_LANTERN ||
			m == Material.CAKE_BLOCK
			
			;
	}

}
