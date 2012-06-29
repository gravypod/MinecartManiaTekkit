package com.afforess.minecartmaniacore.event;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;

public class MinecartMotionStopEvent extends MinecartManiaEvent{
	private MinecartManiaMinecart minecart;
	
	public MinecartMotionStopEvent(MinecartManiaMinecart cart) {
		super("MinecartMotionStopEvent");
		minecart = cart;
	}

	public MinecartManiaMinecart getMinecart() {
		return minecart;
	}
}
