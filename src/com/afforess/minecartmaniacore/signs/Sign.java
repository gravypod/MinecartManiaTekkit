package com.afforess.minecartmaniacore.signs;

import java.util.Collection;

import org.bukkit.Location;
import org.bukkit.block.Block;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.utils.DirectionUtils.CompassDirection;

/**
 * A wrapper of a bukkit sign
 * 
 * @author Afforess
 */
public interface Sign {
	
	/**
	 * Get's the text from the given line of the sign
	 * @param line to get the text of
	 * @return the text from the sign
	 */
	public String getLine(int line);
	
	/**
	 * Set's the text of the given line of the sign
	 * @param line to get the text of
	 * @param text to set at the line
	 */
	public void setLine(int line, String text);
	
	/**
	 * Set's the text of the given line of the sign
	 * @param line to get the text of
	 * @param text to set at the line
	 * @param update whether to update the physical state of the sign
	 */
	public void setLine(int line, String text, boolean update);
	
	/**
	 * Get's all the lines from the sign
	 * @return lines of the sign
	 */
	public String[] getLines();
	
	/**
	 * Get's the number of lines on the sign
	 * @return number of lines
	 */
	public int getNumLines();
	
	/**
	 * Adds brackets to this sign
	 */
	public void addBrackets();
	
	/**
	 * Get's the direction that this sign is facing
	 * @return direction the sign is facing
	 */
	public CompassDirection getFacingDirection();
	
	/**
	 * Get's the object (or null, if no object) associated with the given key
	 * @param key associated with the key
	 * @return object stored
	 */
	public Object getDataValue(Object key);
	
	/**
	 * Set's the object at the given key, or removes it if null is the value
	 * @param key to set the value at
	 * @param value to set
	 */
	public void setDataValue(Object key, Object value);
	
	/**
	 * Updates's the contents of the sign
	 * @param sign
	 */
	public void update(org.bukkit.block.Sign sign);
	
	/**
	 * Copies the values and settings of this sign onto the given sign
	 * @param sign to copy values to
	 */
	public void copy(Sign sign);
	
	/**
	 * Adds a sign action to the list of actions this sign must execute.
	 * @param action to add
	 */
	public void addSignAction(SignAction action);
	
	/**
	 * Removes a sign action from the list of actions this sign must exectue.
	 * @param action to remove.
	 * @return true if the action was removed.
	 */
	public boolean removeSignAction(SignAction action);
	
	/**
	 * Checks to see if this sign has this action attached to it
	 * @param action to check
	 * @return true if the sign has the given action
	 */
	public boolean hasSignAction(SignAction action);
	
	
	/**
	 * Checks to see if this sign has a sign action of this type
	 * Executes in O(n) time!
	 * @param action to check
	 * @return true if the sign has the given action
	 */
	public boolean hasSignAction(Class<? extends SignAction> action);
	
	/**
	 * Executes all the actions attached to this sign.
	 * @param minecart to execute the actions for.
	 * @param sync forces the actions to be performed on the main thread
	 * @return true if at least one action was executed.
	 */
	public boolean executeActions(MinecartManiaMinecart minecart, boolean sync);
	
	/**
	 * Executes all the actions attached to this sign.
	 * @param minecart to execute the actions for.
	 * @return true if at least one action was executed.
	 */
	public boolean executeActions(MinecartManiaMinecart minecart);
	
	/**
	 * Get's a list of sign actions attached to this sign
	 * @return the list of sign actions attached to this sign (or an empty list, if none)
	 */
	public Collection<SignAction> getSignActions();

	
	/**
	 * Executes the given action, if and only if the sign contains the action already
	 * @param minecart to execute the action for.
	 * @param the action to execute
	 * @return true if the action was executed
	 */
	public boolean executeAction(MinecartManiaMinecart minecart, Class<? extends SignAction> action);
	
	/**
	 * Get's the block occupied by the sign
	 * @return block
	 */
	public Block getBlock();
	
	/**
	 * Get's the location of the sign
	 * @return location
	 */
	public Location getLocation();
	public int getX();
	public int getY();
	public int getZ();
}
