package com.afforess.minecartmaniacore.api;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

import com.afforess.minecartmaniacore.MinecartManiaCore;
import com.afforess.minecartmaniacore.config.ControlBlockList;
import com.afforess.minecartmaniacore.config.RedstoneState;
import com.afforess.minecartmaniacore.event.ChestPoweredEvent;
import com.afforess.minecartmaniacore.inventory.MinecartManiaChest;
import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.signs.MinecartTypeSign;
import com.afforess.minecartmaniacore.signs.Sign;
import com.afforess.minecartmaniacore.utils.MinecartUtils;
import com.afforess.minecartmaniacore.utils.SignUtils;
import com.afforess.minecartmaniacore.world.Item;
import com.afforess.minecartmaniacore.world.MinecartManiaWorld;

public class MinecartManiaCoreBlockListener implements Listener{
	private HashMap<Location, Long> lastSpawn = new HashMap<Location, Long>();
	
	@EventHandler
    public void onBlockRedstoneChange(BlockRedstoneEvent event) {    
    	if (event.getOldCurrent() > 0 && event.getNewCurrent() > 0) {
    		return;
    	}
    	boolean power = event.getNewCurrent() > 0;
    	Block block = event.getBlock();

    	int range = 1;
    	for (int dx = -(range); dx <= range; dx++){
			for (int dy = -(range); dy <= range; dy++){
				for (int dz = -(range); dz <= range; dz++){
					Block b = MinecartManiaWorld.getBlockAt(block.getWorld(), block.getX() + dx, block.getY() + dy, block.getZ() + dz);
					if (b.getState() instanceof Chest) {
						Chest chest = (Chest)b.getState();
						MinecartManiaChest mmc = MinecartManiaWorld.getMinecartManiaChest(chest);
						if (mmc != null) {
							boolean previouslyPowered = mmc.isRedstonePower();
							if (!previouslyPowered && power) {
								mmc.setRedstonePower(power);
								ChestPoweredEvent cpe = new ChestPoweredEvent(mmc, power);
								MinecartManiaCore.callEvent(cpe);
							}
							else if (previouslyPowered && !power) {
								mmc.setRedstonePower(power);
								ChestPoweredEvent cpe = new ChestPoweredEvent(mmc, power);
								MinecartManiaCore.callEvent(cpe);
							}
						}
					}
					Item type = Item.getItem(b.getTypeId(), b.getData());
					if (Item.getItem(b.getTypeId()).size() == 1) {
						type = Item.getItem(b.getTypeId()).get(0);
					}
					if (ControlBlockList.isSpawnMinecartBlock(type)) {
						if (ControlBlockList.getControlBlock(type).getSpawnState() != RedstoneState.Enables || power) {
							if (ControlBlockList.getControlBlock(type).getSpawnState() != RedstoneState.Disables || !power) {
								if (MinecartUtils.isTrack(b.getRelative(0, 1, 0).getTypeId())) {
									Long lastSpawn = this.lastSpawn.get(b.getLocation());
									if (lastSpawn == null || (Math.abs(System.currentTimeMillis() - lastSpawn) > 1000)) {
										Location spawn = b.getLocation().clone();
										spawn.setY(spawn.getY() + 1);
										MinecartManiaMinecart minecart = MinecartManiaWorld.spawnMinecart(spawn, getMinecartType(b.getLocation()), null);
										this.lastSpawn.put(b.getLocation(), System.currentTimeMillis());
										if (ControlBlockList.getLaunchSpeed(Item.materialToItem(b.getType())) != 0.0) {
											minecart.launchCart(ControlBlockList.getLaunchSpeed(Item.materialToItem(b.getType())));
										}
									}
								}
							}
						}
					}
				}
			}
    	}
    }
    
    private static Item getMinecartType(Location loc) {
		ArrayList<Sign> signList = SignUtils.getAdjacentMinecartManiaSignList(loc, 2);
		for (Sign sign : signList) {
			if (sign instanceof MinecartTypeSign) {
				MinecartTypeSign type = (MinecartTypeSign)sign;
				if (type.canDispenseMinecartType(Item.MINECART)) {
					return Item.MINECART;
				}
				if (type.canDispenseMinecartType(Item.POWERED_MINECART)) {
					return Item.POWERED_MINECART;
				}
				if (type.canDispenseMinecartType(Item.STORAGE_MINECART)) {
					return Item.STORAGE_MINECART;
				}
			}
		}

		//Returns standard minecart by default
		return Item.MINECART;
	}
}
