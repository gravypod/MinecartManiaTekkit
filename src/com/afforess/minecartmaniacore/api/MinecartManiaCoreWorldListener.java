package com.afforess.minecartmaniacore.api;

import java.util.ArrayList;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

import com.afforess.minecartmaniacore.config.MinecartManiaConfiguration;
import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.world.MinecartManiaWorld;

public class MinecartManiaCoreWorldListener implements Listener{
	public static final int CHUNK_RANGE = 4;

	@EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
    	if (!event.isCancelled()) {
    		if (MinecartManiaConfiguration.isKeepMinecartsLoaded()) {
    			ArrayList<MinecartManiaMinecart> minecarts = MinecartManiaWorld.getMinecartManiaMinecartList();
    			for (MinecartManiaMinecart minecart : minecarts) {
    				if (Math.abs(event.getChunk().getX() - minecart.minecart.getLocation().getBlock().getChunk().getX()) > CHUNK_RANGE) {
    					continue;
    				}
    				if (Math.abs(event.getChunk().getZ() - minecart.minecart.getLocation().getBlock().getChunk().getZ()) > CHUNK_RANGE) {
    					continue;
    				}
    				event.setCancelled(true);
    				return;
    			}
    		}
    	}
    }
}
