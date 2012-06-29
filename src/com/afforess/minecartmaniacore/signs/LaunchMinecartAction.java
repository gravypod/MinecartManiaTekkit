package com.afforess.minecartmaniacore.signs;

import org.bukkit.util.Vector;

import com.afforess.minecartmaniacore.config.ControlBlockList;
import com.afforess.minecartmaniacore.config.MinecartManiaConfiguration;
import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.utils.DirectionUtils.CompassDirection;

public class LaunchMinecartAction implements SignAction {
	private volatile Vector launchSpeed = null;
	private volatile boolean previous = false;
	protected Sign sign;
	public LaunchMinecartAction(Sign sign) {
		this.sign = sign;
	}


	public boolean execute(MinecartManiaMinecart minecart) {
		if (ControlBlockList.getLaunchSpeed(minecart.getItemBeneath()) == 1.0D) {
			return false;
		}
		if (minecart.isMoving()) {
			return false;
		}
		Vector launch = calculateLaunchSpeed(false);
		if (previous) {
			if (minecart.getPreviousDirectionOfMotion() != null && minecart.getPreviousDirectionOfMotion() != CompassDirection.NO_DIRECTION) {
				minecart.setMotion(minecart.getPreviousDirectionOfMotion(), 0.6D);
			}
		}
		else {
			minecart.minecart.setVelocity(launch);
		}
		
		return true;
	}

	private Vector calculateLaunchSpeed(boolean force) {
		if (launchSpeed == null || force) {
			previous = false;
			launchSpeed = null;
			for (int i = 0; i < sign.getNumLines(); i++) {
				if (sign.getLine(i).toLowerCase().contains("previous dir")) {
					previous = true;
					break;
				}
				if(MinecartManiaConfiguration.useOldDirections())
				{
    				if (sign.getLine(i).toLowerCase().contains("launch north")) {
    					launchSpeed = new Vector(-0.6D, 0, 0);
    					break;
    				}
    				else if (sign.getLine(i).toLowerCase().contains("launch east")) {
    					launchSpeed = new Vector(0, 0, -0.6D);
    					break;
    				}
    				if (sign.getLine(i).toLowerCase().contains("launch south")) {
    					launchSpeed = new Vector(0.6D, 0, 0);
    					break;
    				}
    				if (sign.getLine(i).toLowerCase().contains("launch west")) {
    					launchSpeed = new Vector(0, 0, 0.6D);
    					break;
    				}
				}
				else
				{
				    if (sign.getLine(i).toLowerCase().contains("launch north")) {
                        launchSpeed = new Vector(0, 0, -0.6D);
                        break;
                    }
                    else if (sign.getLine(i).toLowerCase().contains("launch east")) {
                        launchSpeed = new Vector(0.6D, 0, 0);
                        break;
                    }
                    if (sign.getLine(i).toLowerCase().contains("launch south")) {
                        launchSpeed = new Vector(0, 0, 0.6D);
                        break;
                    }
                    if (sign.getLine(i).toLowerCase().contains("launch west")) {
                        launchSpeed = new Vector(-0.6D, 0, 0);
                        break;
                    }
				}
			}
			if (launchSpeed != null || previous) {
				sign.addBrackets();
			}
		}
		return launchSpeed;
	}


	public boolean async() {
		return true;
	}


	public boolean valid(Sign sign) {
		calculateLaunchSpeed(true);
		return launchSpeed != null || previous;
	}


	public String getName() {
		return "launchersign";
	}


	public String getFriendlyName() {
		return "Launcher Sign";
	}

}
