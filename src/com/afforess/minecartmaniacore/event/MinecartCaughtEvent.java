package com.afforess.minecartmaniacore.event;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;

public class MinecartCaughtEvent extends MinecartManiaEvent implements MinecartEvent {
	private MinecartManiaMinecart minecart;
	private boolean action = false;
	public MinecartCaughtEvent(MinecartManiaMinecart cart) {
		super("MinecartLaunchedEvent");
		minecart = cart;
	}

	public MinecartManiaMinecart getMinecart() {
		return minecart;
	}
	
	public boolean isActionTaken() {
		return action;
	}

	public void setActionTaken(boolean Action) {
		this.action = Action;
	}
}
