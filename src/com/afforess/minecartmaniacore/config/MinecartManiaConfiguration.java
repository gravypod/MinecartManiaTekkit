package com.afforess.minecartmaniacore.config;

import com.afforess.minecartmaniacore.world.MinecartManiaWorld;

public abstract class MinecartManiaConfiguration {

	public static int getMaximumMinecartSpeedPercent() {
		return (Integer)MinecartManiaWorld.getConfigurationValue("MaximumMinecartSpeedPercent");
	}

	public static int getDefaultMinecartSpeedPercent() {
		return (Integer)MinecartManiaWorld.getConfigurationValue("DefaultMinecartSpeedPercent");
	}
	 
	public static int getMinecartsClearRailsSetting() {
		return (Integer)MinecartManiaWorld.getConfigurationValue("MinecartsClearRails");
	}
	
	public static int getMinecartRange() {
		return (Integer)MinecartManiaWorld.getConfigurationValue("Range");
	}
	
	public static int getMinecartRangeY() {
		return (Integer)MinecartManiaWorld.getConfigurationValue("RangeY");
	}
	
	public static int getMinecartMaximumRange() {
		return (Integer)MinecartManiaWorld.getConfigurationValue("MaximumRange");
	}
	
	public static boolean isKeepMinecartsLoaded() {
		return (Boolean)MinecartManiaWorld.getConfigurationValue("KeepMinecartsLoaded");
	}

	public static boolean isMinecartsKillMobs() {
		return (Boolean)MinecartManiaWorld.getConfigurationValue("MinecartsKillMobs");
	}

	public static boolean isReturnMinecartToOwner() {
		return (Boolean)MinecartManiaWorld.getConfigurationValue("MinecartsReturnToOwner");
	}
	
	public static boolean isStackAllItems() {
		return (Boolean)MinecartManiaWorld.getConfigurationValue("StackAllItems");
	}
	
	public static boolean isLimitedSignRange() {
		return (Boolean)MinecartManiaWorld.getConfigurationValue("LimitedSignRange");
	}
    
    public static boolean isDisappearOnDisconnect() {
        return (Boolean)MinecartManiaWorld.getConfigurationValue("DisappearOnDisconnect");
    }
    
    public static boolean useOldDirections() {
        return (Boolean)MinecartManiaWorld.getConfigurationValue("UseOldDirections");
    }

}
