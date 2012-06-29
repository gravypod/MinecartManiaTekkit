package com.afforess.minecartmaniacore.signs;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;

public class LaunchPlayerAction implements SignAction{
	protected Sign sign;
	public LaunchPlayerAction(Sign sign) {
		this.sign = sign;
	}


	public boolean execute(MinecartManiaMinecart minecart) {
		minecart.launchCart();
		minecart.setDataValue("hold sign data", null);
		return true;
	}


	public boolean async() {
		return true;
	}


	public boolean valid(Sign sign) {
		for (String line : sign.getLines()) {
			if (line.toLowerCase().contains("launch player")) {
				sign.addBrackets();
				return true;
			}
		}
		return false;
	}


	public String getName() {
		return "launchplayersign";
	}


	public String getFriendlyName() {
		return "Launch Player Sign";
	}
	
	
}
