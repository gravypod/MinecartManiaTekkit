package com.afforess.minecartmaniacore.event;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;

public class MinecartManiaMinecartDestroyedEvent extends MinecartManiaEvent{
	private MinecartManiaMinecart minecart;
	
	public MinecartManiaMinecartDestroyedEvent(MinecartManiaMinecart cart) {
		super("MinecartManiaMinecartDestroyedEvent");
		minecart = cart;
	}

	public MinecartManiaMinecart getMinecart() {
		return minecart;
	}
}
