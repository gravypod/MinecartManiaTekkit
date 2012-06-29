package com.afforess.minecartmaniacore.signs;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.afforess.minecartmaniacore.MinecartManiaCore;
import com.afforess.minecartmaniacore.debug.MinecartManiaLogger;
import com.afforess.minecartmaniacore.event.MinecartManiaSignFoundEvent;
import com.afforess.minecartmaniacore.event.MinecartManiaSignUpdatedEvent;

public class SignManager {
	private static ConcurrentHashMap<Block, Sign> signList = new ConcurrentHashMap<Block, Sign>();
	
	public static Sign getSignAt(Block block) {
		return getSignAt(block, null);
	}
	
	public static Sign getSignAt(Location location) {
		return getSignAt(location.getBlock(), null);
	}
	
	public static Sign getSignAt(Location location, Player player) {
		return getSignAt(location.getBlock(), player);
	}
	
	public static Sign getSignAt(Block block, Player player) {
		switch(block.getTypeId()) {
			case 63:
			case 68:
				break;
			default:
				return null;
		}

		Sign temp = signList.get(block);
		if (temp != null) {
			org.bukkit.block.Sign sign = (org.bukkit.block.Sign)block.getState();
			if (!temp.equals(sign)) {
				temp.update(sign);
				MinecartManiaSignFoundEvent mmsfe = new MinecartManiaSignUpdatedEvent(temp, player);
				MinecartManiaCore.callEvent(mmsfe);
				temp = mmsfe.getSign();
				MinecartManiaLogger.getInstance().debug("Found updated sign: " + temp);
				signList.put(block, temp);
			}
			return temp;
		}
		temp = new MinecartManiaSign(block);
		MinecartManiaSignFoundEvent mmsfe = new MinecartManiaSignFoundEvent(temp, player);
		MinecartManiaCore.callEvent(mmsfe);
		mmsfe.logProcessTime();
		temp = mmsfe.getSign();
		MinecartManiaLogger.getInstance().debug("Found new sign: " + temp);
		signList.put(block, temp);
		return temp;
	}
	
	public static void updateSign(Location location, Sign sign) {
		updateSign(location.getBlock(), sign);
	}
	
	public static void updateSign(Block block, Sign sign) {
		if (sign == null) {
			signList.remove(block);
		}
		else {
			signList.put(sign.getBlock(), sign);
		}
	}

}
