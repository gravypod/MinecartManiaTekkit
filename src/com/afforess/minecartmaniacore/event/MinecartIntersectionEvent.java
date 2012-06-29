package com.afforess.minecartmaniacore.event;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;

public class MinecartIntersectionEvent extends MinecartManiaEvent implements MinecartEvent {
	private boolean action = false;
	private MinecartManiaMinecart minecart;
	
	public MinecartIntersectionEvent(MinecartManiaMinecart cart) {
		super("MinecartIntersectionEvent");
		minecart = cart;
	}
	
	
	public MinecartManiaMinecart getMinecart() {
		return minecart;
	}
	
	public boolean isActionTaken() {
		return action;
	}
	
	public void setActionTaken(boolean b) {
		action = b;
	}

}
