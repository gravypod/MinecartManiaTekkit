package com.afforess.minecartmaniacore.signs;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;

public class ForceUnlockChestAction implements SignAction{
	protected boolean valid = false;
	
	public ForceUnlockChestAction(Sign sign) {
	}
	
	public boolean execute(MinecartManiaMinecart minecart) {
		return valid;
	}
	
	public boolean async() {
		return false;
	}

	public boolean valid(Sign sign) {
		boolean success = false;
		for (String line : sign.getLines()) {
			if (line.contains("unlock chest")) {
				success = true;
				break;
			}
		}
		if (success) {
			sign.addBrackets();
		}
		valid = success;
		return success;
	}

	public String getName() {
		return "forceunlockchestsign";
	}

	public String getFriendlyName() {
		return "Force Unlock Chest Sign";
	}
}
