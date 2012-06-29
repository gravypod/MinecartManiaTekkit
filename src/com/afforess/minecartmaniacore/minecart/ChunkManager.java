package com.afforess.minecartmaniacore.minecart;

import java.util.HashSet;
import java.util.Iterator;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;

public class ChunkManager {
	protected HashSet<Chunk> loaded = new HashSet<Chunk>();
	
	public void updateChunks(Location location) {
		Chunk center = location.getBlock().getChunk();
		World world = center.getWorld();
		int range = 4;
		for (int dx = -(range); dx <= range; dx++){
			for (int dz = -(range); dz <= range; dz++){
				Chunk chunk = world.getChunkAt(center.getX()+dx, center.getZ()+dz);
				world.loadChunk(chunk);
				//if (!loaded.contains(chunk)) {
					//loaded.add(chunk);
				//}
			}
		}
		/*Iterator<Chunk> i = loaded.iterator();
		while(i.hasNext()) {
			Chunk old = i.next();
			if (old.getX() > center.getX()+range || old.getX() < center.getX()-range) {
				if (unloadChunk(old)) {
					i.remove();
				}
				else if (spawnChunk(old)) {
					i.remove();
				}
			}
			else if (old.getZ() > center.getZ()+range || old.getZ() < center.getZ()-range) {
				if (unloadChunk(old)) {
					
					i.remove();
				}
				else if (spawnChunk(old)) {
					i.remove();
				}
			}
		}*/
	}
	
	public void unloadChunks(Location location) {
		/*Iterator<Chunk> i = loaded.iterator();
		while(i.hasNext()) {
			Chunk old = i.next();
			if (unloadChunk(old)) {
				i.remove();
			}
		}*/
	}
	
	private static boolean spawnChunk(Chunk chunk) {
		//copied from MC Server code
		int k = chunk.getX() * 16 + 8;
        int l = chunk.getZ() * 16 + 8;
        short short1 = 128;
		if (k < -short1 || k > short1 || l < -short1 || l > short1) {
			return false;
		}
		return true;
	}
	
	private static boolean unloadChunk(Chunk chunk) {
		CraftWorld world = (CraftWorld)chunk.getWorld();
		//Spawn must never be unloaded
		if (spawnChunk(chunk)) {
			return false;
		}
		if (!world.isChunkInUse(chunk.getX(), chunk.getZ())) {
			if (world.unloadChunk(chunk.getX(), chunk.getZ(), true, false)) {
				return true;
			}
			
		}
		return false;
	}
}
