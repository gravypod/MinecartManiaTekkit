package com.afforess.minecartmaniacore.event;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;

public interface MinecartEvent{
	
	public boolean isActionTaken();
	
	public void setActionTaken(boolean Action);
	
	public MinecartManiaMinecart getMinecart();

}
