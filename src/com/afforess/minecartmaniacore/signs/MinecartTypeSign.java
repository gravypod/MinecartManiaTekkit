package com.afforess.minecartmaniacore.signs;

import com.afforess.minecartmaniacore.debug.MinecartManiaLogger;
import com.afforess.minecartmaniacore.signs.MinecartManiaSign;
import com.afforess.minecartmaniacore.signs.Sign;
import com.afforess.minecartmaniacore.world.Item;

public class MinecartTypeSign extends MinecartManiaSign{
	protected boolean standard = false;
	protected boolean powered = false;
	protected boolean storage = false;
	protected boolean calculated = false;
	public MinecartTypeSign(Sign sign) {
		super(sign.getBlock());
		sign.copy(this);
	}
	
	public boolean canDispenseMinecartType(Item item) {
		if (!calculated) {
			for (String line : lines) {
				if (line.toLowerCase().contains("empty") || line.toLowerCase().contains("standard")) {
					standard = true;
				}
				else if (line.toLowerCase().contains("powered")) {
					powered = true;
				}
				else if (line.toLowerCase().contains("storage")) {
					storage = true;
				}
			}
			calculated = true;
		}
		if (item == Item.MINECART) {
			return standard;
		}
		if (item == Item.POWERED_MINECART) {
			return powered;
		}
		if (item == Item.STORAGE_MINECART) {
			return storage;
		}
		return false;
	}
	
	@Override
	public void update(org.bukkit.block.Sign sign) {
		calculated = false;
		MinecartManiaLogger.getInstance().severe("Updating type sign. Text is: " + lines[0]);
		super.update(sign);
	}
	
	@Override
	public void copy(Sign sign) {
		if (sign instanceof MinecartTypeSign) {
			((MinecartTypeSign)sign).calculated = this.calculated;
			((MinecartTypeSign)sign).standard = this.standard;
			((MinecartTypeSign)sign).powered = this.powered;
			((MinecartTypeSign)sign).storage = this.storage;
		}
		super.copy(sign);
	}
	
	public static boolean isMinecartTypeSign(Sign sign) {
		MinecartManiaLogger.getInstance().debug("Testing Sign For Minecart Type Sign, Line 0: " + sign.getLine(0));
		if (sign.getLine(0).contains("[Dispenser]")) {
			sign.setLine(0, "minecart type");
			sign.addBrackets();
			return true;
		}
		if (sign.getLine(0).toLowerCase().contains("minecart type")) {
			sign.setLine(0, "[Minecart Type]");
			MinecartManiaLogger.getInstance().debug("Found valid Minecart Type Sign");
			return true;
		}
		return false;
	}

}
