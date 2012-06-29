package com.afforess.minecartmaniacore.event;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;

public class MinecartActionEvent extends MinecartManiaEvent implements MinecartEvent {
	private boolean action = false;
	private MinecartManiaMinecart minecart;
	
	public MinecartActionEvent(MinecartManiaMinecart cart) {
		super("MinecartActionEvent");
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
