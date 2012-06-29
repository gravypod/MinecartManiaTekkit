package com.afforess.minecartmaniacore.event;

import org.bukkit.entity.Player;

import com.afforess.minecartmaniacore.signs.Sign;

public class MinecartManiaSignFoundEvent extends MinecartManiaEvent{
	private Sign sign;
	private Player player;

	public MinecartManiaSignFoundEvent(Sign sign, Player player) {
		super("MinecartManiaSignFoundEvent");
		this.sign = sign;
		this.player = player;
	}
	
	public Sign getSign() {
		return sign;
	}
	
	public void setSign(Sign sign) {
		this.sign = sign;
	}
	
	public Player getPlayer() {
		return player;
	}

}
