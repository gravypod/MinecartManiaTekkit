package com.afforess.minecartmaniacore.api;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.afforess.minecartmaniacore.debug.MinecartManiaLogger;
import com.afforess.minecartmaniacore.event.MinecartManiaSignFoundEvent;
import com.afforess.minecartmaniacore.signs.LaunchMinecartAction;
import com.afforess.minecartmaniacore.signs.LaunchPlayerAction;
import com.afforess.minecartmaniacore.signs.MinecartTypeSign;
import com.afforess.minecartmaniacore.signs.Sign;
import com.afforess.minecartmaniacore.signs.SignAction;

public class MinecartManiaActionListener implements Listener{
	
	@EventHandler
	public void onMinecartManiaSignFoundEvent(MinecartManiaSignFoundEvent event) {
		MinecartManiaLogger.getInstance().debug("MinecartManiaCore - Minecart Mania Sign Found Event");
		Sign sign = event.getSign();
		if (MinecartTypeSign.isMinecartTypeSign(sign)) {
			event.setSign(new MinecartTypeSign(sign));
			sign = event.getSign();
		}
		SignAction action = new LaunchPlayerAction(sign);
		if (action.valid(sign)) {
			sign.addSignAction(action);
		}
		action = new LaunchMinecartAction(sign);
		if (action.valid(sign)) {
			sign.addSignAction(action);
		}
	}

}
