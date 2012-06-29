package com.afforess.minecartmaniacore.signs;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;

/**
 * An action specific to a sign
 * @author Afforess
 */
public interface SignAction{
	
	/**
	 * Executes the action 
	 * @param minecart used in executing this action
	 * @return true if an action was exectued
	 */
	public boolean execute(MinecartManiaMinecart minecart);
	
	/**
	 * Whether this action can be exectuted on a separate thread
	 * @return true if this can be executed on a separate thread
	 */
	public boolean async();
	
	/**
	 * Whether the sign is valid for this SignAction
	 * @param sign to check against
	 * @return true if the sign is valid
	 */
	public boolean valid(Sign sign);
	
	/**
	 * Get's the name of this action
	 * @return name
	 */
	public String getName();
	
	/**
	 * Get's the human-readable name of this action
	 * @return name
	 */
	public String getFriendlyName();
}
