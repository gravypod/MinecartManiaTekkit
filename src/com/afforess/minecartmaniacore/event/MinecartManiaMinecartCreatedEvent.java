package com.afforess.minecartmaniacore.event;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;

public class MinecartManiaMinecartCreatedEvent extends MinecartManiaEvent{
	private MinecartManiaMinecart minecart;
	
	public MinecartManiaMinecartCreatedEvent(MinecartManiaMinecart cart) {
		super("MinecartManiaMinecartCreatedEvent");
		minecart = cart;
	}

	public MinecartManiaMinecart getMinecart() {
		return minecart;
	}
}
