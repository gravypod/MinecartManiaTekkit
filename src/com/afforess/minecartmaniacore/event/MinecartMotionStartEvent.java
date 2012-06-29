package com.afforess.minecartmaniacore.event;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;

public class MinecartMotionStartEvent extends MinecartManiaEvent{
	private MinecartManiaMinecart minecart;
	
	public MinecartMotionStartEvent(MinecartManiaMinecart cart) {
		super("MinecartMotionStartEvent");
		minecart = cart;
	}

	public MinecartManiaMinecart getMinecart() {
		return minecart;
	}
}
