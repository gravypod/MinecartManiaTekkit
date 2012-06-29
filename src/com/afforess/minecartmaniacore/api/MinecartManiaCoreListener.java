package com.afforess.minecartmaniacore.api;

import java.util.ArrayList;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleUpdateEvent;

import com.afforess.minecartmaniacore.MinecartManiaCore;
import com.afforess.minecartmaniacore.config.ControlBlockList;
import com.afforess.minecartmaniacore.config.MinecartManiaConfiguration;
import com.afforess.minecartmaniacore.debug.MinecartManiaLogger;
import com.afforess.minecartmaniacore.event.MinecartActionEvent;
import com.afforess.minecartmaniacore.event.MinecartClickedEvent;
import com.afforess.minecartmaniacore.event.MinecartDirectionChangeEvent;
import com.afforess.minecartmaniacore.event.MinecartIntersectionEvent;
import com.afforess.minecartmaniacore.event.MinecartMotionStartEvent;
import com.afforess.minecartmaniacore.event.MinecartMotionStopEvent;
import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.signs.LaunchPlayerAction;
import com.afforess.minecartmaniacore.signs.SignManager;
import com.afforess.minecartmaniacore.utils.MinecartUtils;
import com.afforess.minecartmaniacore.utils.SignUtils;
import com.afforess.minecartmaniacore.world.MinecartManiaWorld;

public class MinecartManiaCoreListener implements Listener{
	public MinecartManiaCoreListener() {

	}

	@EventHandler
	public void onVehicleUpdate(VehicleUpdateEvent event) {
		if (event.getVehicle() instanceof Minecart) {
			Minecart cart = (Minecart)event.getVehicle();
			MinecartManiaMinecart minecart = MinecartManiaWorld.getMinecartManiaMinecart(cart);
			
			if (minecart.isDead()) {
				return;
			}

			minecart.updateCalendar(); 
			if (minecart.isMoving()) {
				if (minecart.getDirectionOfMotion() != minecart.getPreviousDirectionOfMotion()) {
					MinecartManiaCore.callEvent(new MinecartDirectionChangeEvent(minecart, minecart.getPreviousDirectionOfMotion(), minecart.getDirectionOfMotion()));
					minecart.setPreviousDirectionOfMotion(minecart.getDirectionOfMotion());
				}
			}
			
			//Fire new events
			if (minecart.wasMovingLastTick() && !minecart.isMoving()) {
				MinecartMotionStopEvent mmse = new MinecartMotionStopEvent(minecart);
				MinecartManiaCore.callEvent(mmse);
				mmse.logProcessTime();
			}
			else if (!minecart.wasMovingLastTick() && minecart.isMoving()) {
				MinecartMotionStartEvent mmse = new MinecartMotionStartEvent(minecart);
				MinecartManiaCore.callEvent(mmse);
				mmse.logProcessTime();
			}
			minecart.setWasMovingLastTick(minecart.isMoving());
			minecart.doRealisticFriction();
			minecart.doLauncherBlock();
			minecart.undoPoweredRails();
			
			//total hack workaround because of the inability to create runnables/threads w/o IllegalAccessError
			if (minecart.getDataValue("launch") != null) {
				minecart.launchCart();
				minecart.setDataValue("launch", null);
			}
			
			if (minecart.hasChangedPosition() || minecart.createdLastTick) {
				//minecart.updateToPoweredRails(); //TODO Remove by MC 1.7
				minecart.updateChunks();
				if (minecart.isAtIntersection()) {
					MinecartIntersectionEvent mie = new MinecartIntersectionEvent(minecart);
					MinecartManiaCore.callEvent(mie);
					mie.logProcessTime();
				}
				
					MinecartActionEvent mae = new MinecartActionEvent(minecart);
				if (!minecart.createdLastTick) {
					MinecartManiaCore.callEvent(mae);
					mae.logProcessTime();
				}
				
				minecart.doSpeedMultiplierBlock();
				minecart.doCatcherBlock();
				minecart.doPlatformBlock(); //platform must be after catcher block
				minecart.doElevatorBlock();
				minecart.doEjectorBlock();

				MinecartUtils.updateNearbyItems(minecart);
				
				minecart.updateMotion();
				minecart.updateLocation();
				
				//should do last
				minecart.doKillBlock();
				minecart.createdLastTick = false;
			}
		}
	}

	@EventHandler
	public void onVehicleDestroy(VehicleDestroyEvent event) {
		if (event.getVehicle() instanceof Minecart && !event.isCancelled()) {
			MinecartManiaMinecart minecart = MinecartManiaWorld.getMinecartManiaMinecart((Minecart)event.getVehicle());
			minecart.kill(false);
		}
	}

	@EventHandler
	public void onVehicleDamage(VehicleDamageEvent event) {
		if (event.getVehicle() instanceof Minecart) {
			MinecartManiaMinecart minecart = MinecartManiaWorld.getMinecartManiaMinecart((Minecart)event.getVehicle());
			//Start workaround for double damage events
			long lastDamage = -1;
			if (minecart.getDataValue("Last Damage") != null) {
				lastDamage = (Long)minecart.getDataValue("Last Damage");
			}
			if (lastDamage > -1) {
				if ((lastDamage + 100) > System.currentTimeMillis()) {
					return;
				}
			}
			minecart.setDataValue("Last Damage", System.currentTimeMillis());
			//End Workaround
			if (!event.isCancelled()) {
				MinecartManiaLogger.getInstance().debug("Damage: " + event.getDamage() + " Existing: " + minecart.minecart.getDamage());
				if ((event.getDamage() * 10) + minecart.minecart.getDamage() > 40) {
					minecart.kill();
					event.setCancelled(true);
					event.setDamage(0);
				}
				if (minecart.minecart.getPassenger() != null) {
					if (minecart.isOnRails()) {
						if(event.getAttacker() != null && event.getAttacker().getEntityId() == minecart.minecart.getPassenger().getEntityId()) {
							MinecartClickedEvent mce = new MinecartClickedEvent(minecart);
							MinecartManiaCore.callEvent(mce);
							if (mce.isActionTaken()) {
								event.setDamage(0);
								event.setCancelled(true);
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onVehicleEntityCollision(VehicleEntityCollisionEvent event) {
		if (event.getVehicle() instanceof Minecart) {
			Minecart cart = (Minecart)event.getVehicle();
			MinecartManiaMinecart minecart = MinecartManiaWorld.getMinecartManiaMinecart(cart);
			Entity collisioner = event.getEntity();
			
			if (minecart.doCatcherBlock()) {
				event.setCancelled(true);
				event.setCollisionCancelled(true);
				event.setPickupCancelled(true);
				return;
			}
			if (collisioner instanceof LivingEntity) {
				LivingEntity victim = (LivingEntity)(collisioner);
				if (!(victim instanceof Player) && !(victim instanceof Wolf)) {
					if (MinecartManiaConfiguration.isMinecartsKillMobs()) {
						if (minecart.isMoving()) {
							victim.remove();
							event.setCancelled(true);
							event.setCollisionCancelled(true);
							event.setPickupCancelled(true);
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onVehicleEnter(VehicleEnterEvent event) {
		if (event.isCancelled() || !(event.getVehicle() instanceof Minecart)) {
			return;
		}
		
		final MinecartManiaMinecart minecart = MinecartManiaWorld.getMinecartManiaMinecart((Minecart)event.getVehicle());
		if (minecart.minecart.getPassenger() != null) {
			return;
		}
		if (ControlBlockList.getLaunchSpeed(minecart.getItemBeneath()) != 0.0D) {
			if (!minecart.isMoving()) {
				ArrayList<Sign> signs = SignUtils.getAdjacentSignList(minecart, 2);
				for (Sign s : signs) {
					com.afforess.minecartmaniacore.signs.Sign sign = SignManager.getSignAt(s.getBlock());
					if (sign.executeAction(minecart, LaunchPlayerAction.class)) {
						break;
					}
				}
			}
		}
	}


}
