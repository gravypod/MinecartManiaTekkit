package com.afforess.minecartmaniacore.api;

import javax.persistence.OptimisticLockException;

import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.afforess.minecartmaniacore.config.MinecartManiaConfiguration;
import com.afforess.minecartmaniacore.debug.MinecartManiaLogger;
import com.afforess.minecartmaniacore.entity.MinecartManiaPlayer;
import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecartDataTable;
import com.afforess.minecartmaniacore.world.MinecartManiaWorld;

public class MinecartManiaCorePlayerListener implements Listener{

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (MinecartManiaConfiguration.isDisappearOnDisconnect()) {
			MinecartManiaPlayer player = MinecartManiaWorld.getMinecartManiaPlayer(event.getPlayer());
			if (event.getPlayer().getVehicle() instanceof Minecart) {
				final MinecartManiaMinecart minecart = MinecartManiaWorld.getMinecartManiaMinecart((Minecart)player.getPlayer().getVehicle());
				try {
					MinecartManiaMinecartDataTable data = new MinecartManiaMinecartDataTable(minecart, player.getName());
					MinecartManiaMinecartDataTable.save(data);
					minecart.kill(false);
				}
				catch (Exception e) {
					MinecartManiaLogger.getInstance().severe("Failed to remove the minecart when " + player.getName() + " disconnected");
					MinecartManiaLogger.getInstance().logCore(e.getMessage(), false);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (MinecartManiaConfiguration.isDisappearOnDisconnect()) {
			MinecartManiaPlayer player = MinecartManiaWorld.getMinecartManiaPlayer(event.getPlayer());
			final MinecartManiaMinecartDataTable data = MinecartManiaMinecartDataTable.getDataTable(player.getName());
			if (data != null) {
				MinecartManiaMinecart minecart = data.toMinecartManiaMinecart();
				minecart.minecart.setPassenger(player.getPlayer());
				try {
					MinecartManiaMinecartDataTable.delete(data);
				}
				//Make every effort to delete the entry
				catch (OptimisticLockException ole) {
					final String name = event.getPlayer().getName();
					Thread deleteEntry = new Thread() {
						public void run() {
							try {
								sleep(5000);
								MinecartManiaMinecartDataTable.delete(data);
							}
							catch (Exception e) {
								MinecartManiaLogger.getInstance().severe("Failed to remove the minecart data entry when " + name + " connected");
								MinecartManiaLogger.getInstance().logCore(e.getMessage(), false);
							}
						}
					};
					deleteEntry.start();
				}
			}
		}
	}

}
