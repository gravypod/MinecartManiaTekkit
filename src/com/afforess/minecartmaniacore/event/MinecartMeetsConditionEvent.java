package com.afforess.minecartmaniacore.event;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.signs.Sign;

public class MinecartMeetsConditionEvent extends MinecartManiaEvent{
	private MinecartManiaMinecart minecart;
	private Sign sign;
	private boolean condition = false;

	public MinecartMeetsConditionEvent(MinecartManiaMinecart minecart, Sign sign) {
		super("MinecartMeetsConditionEvent");
		this.minecart = minecart;
		this.sign = sign;
	}
	
	public MinecartManiaMinecart getMinecart() {
		return minecart;
	}
	
	public Sign getSign() {
		return sign;
	}
	
	public boolean isMeetCondition() {
		return condition;
	}
	
	public void setMeetCondition(boolean condition) {
		this.condition = condition;
	}

}
