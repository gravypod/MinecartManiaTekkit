package com.afforess.minecartmaniacore.event;

import org.bukkit.entity.Player;

import com.afforess.minecartmaniacore.signs.Sign;

public class MinecartManiaSignUpdatedEvent extends MinecartManiaSignFoundEvent{

	public MinecartManiaSignUpdatedEvent(Sign sign, Player player) {
		super(sign, player);
	}

}
