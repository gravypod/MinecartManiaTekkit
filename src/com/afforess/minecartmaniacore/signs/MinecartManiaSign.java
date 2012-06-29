package com.afforess.minecartmaniacore.signs;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.block.Block;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.utils.DirectionUtils;
import com.afforess.minecartmaniacore.utils.DirectionUtils.CompassDirection;
import com.afforess.minecartmaniacore.utils.StringUtils;
import com.afforess.minecartmaniacore.utils.WordUtils;

public class MinecartManiaSign implements Sign{
	protected final Block block;
	protected volatile String[] lines;
	protected HashSet<SignAction> actions = new HashSet<SignAction>();
	protected int updateId = -1;
	protected ConcurrentHashMap<Object, Object> data = new ConcurrentHashMap<Object, Object>();
	
	public MinecartManiaSign(org.bukkit.block.Sign sign) {
		block = sign.getBlock();
		lines = getSign().getLines();
	}
	
	protected MinecartManiaSign(Block block) {
		this.block = block;
		lines = getSign().getLines();
	}
	
	protected MinecartManiaSign(Location loc) {
		block = loc.getBlock();
		lines = getSign().getLines();
	}
	
	protected final org.bukkit.block.Sign getSign() {
		return ((org.bukkit.block.Sign)getBlock().getState());
	}


	public final String getLine(int line) {
		return lines[line];
	}
	

	public final void setLine(int line, String text) {
		setLine(line, text, true);
	}


	public final void setLine(int line, String text, boolean update) {
		if (text.length() < 16) 
			lines[line] = text;
		else
			lines[line] = text.substring(0, 15);
		if (update) {
		    org.bukkit.block.Sign sign = getSign();
			sign.setLine(line, lines[line]);
			sign.update();
		}
	}
	

	public final int getNumLines() {
		return lines.length;
	}
	

	public void addBrackets() {
		for (int i = 0; i < getNumLines(); i++) {
			if (!getLine(i).isEmpty() && getLine(i).length() < 14) {
				setLine(i, WordUtils.capitalize(StringUtils.addBrackets((getLine(i)))));
			}
		}
	}


	public final String[] getLines() {
		return lines;
	}


	public CompassDirection getFacingDirection() {
		return DirectionUtils.getSignFacingDirection(getSign());
	}


	public final Object getDataValue(Object key) {
		return data.get(key);
	}


	public final void setDataValue(Object key, Object value) {
		if (value != null) {
			data.put(key, value);
		}
		else {
			data.remove(key);
		}
	}
	

	public void update(org.bukkit.block.Sign sign) {
		lines = sign.getLines();
		actions = new HashSet<SignAction>();
	}

	public void copy(Sign sign) {
		if (sign instanceof MinecartManiaSign) {
			MinecartManiaSign temp = (MinecartManiaSign)sign;
			temp.data = this.data;
			temp.lines = this.lines;
			temp.actions = this.actions;
						
			//copy lines
			for(int i = 0; i < 4; i++)
			{
			    String line = "";
			    if(lines.length > i) {
			        line = lines[i];
			    }
                setLine(i, line);
			}
		}
	}
	
	private int hashCode(String[] lines) {
		int hash = getBlock().hashCode();
		for (int i = 0; i < lines.length; i++) {
			if (!lines[i].isEmpty()) {
				hash += lines[i].hashCode();
			}
		}
		return hash;
	}
	

	public int hashCode() {
		return hashCode(lines);
	}
	

	public boolean equals(Object obj) {
		if (obj instanceof Sign) {
			return hashCode() == ((Sign)obj).hashCode();
		}
		else if (obj instanceof org.bukkit.block.Sign) {
			return hashCode() == hashCode(((org.bukkit.block.Sign)obj).getLines());
		}
		return false;
	}

	public void addSignAction(SignAction action) {
		actions.add(action);
	}

	public boolean removeSignAction(SignAction action) {
		return actions.remove(action);
	}


	public boolean hasSignAction(SignAction action) {
		return actions.contains(action);
	}
	

	public boolean hasSignAction(Class<? extends SignAction> action) {
		Iterator<SignAction> i = actions.iterator();
		while(i.hasNext()){
			SignAction executor = i.next();
			if (action.isInstance(executor)) {
				return true;
			}
		}
		return false;
	}
	

	public boolean executeActions(MinecartManiaMinecart minecart, boolean sync) {
		for (SignAction action : actions) {
			if (!sync && action.async()) {
				(new SignActionThread(minecart, action)).start();
			}
			else {
				action.execute(minecart);
			}
		}
		return actions.size() > 0;
	}

	public boolean executeActions(MinecartManiaMinecart minecart) {
		return executeActions(minecart, false);
	}
	
	public boolean executeAction(MinecartManiaMinecart minecart, Class<? extends SignAction> action) {
		Iterator<SignAction> i = actions.iterator();
		boolean success = false;
		while(i.hasNext()){
			SignAction executor = i.next();
			if (action.isInstance(executor)) {
				if (executor.execute(minecart)) {
					success = true;
				}
			}
		}
		return success;
	}

	@SuppressWarnings("unchecked")
	public Collection<SignAction> getSignActions() {
		return (Collection<SignAction>) actions.clone();
	}
	
	public Location getLocation() {
		return block.getLocation();
	}

	public Block getBlock() {
		return block;
	}

	public int getX() {
		return getBlock().getX();
	}

	public int getY() {
		return getBlock().getY();
	}

	public int getZ() {
		return getBlock().getZ();
	}

}
