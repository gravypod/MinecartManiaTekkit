package com.afforess.minecartmaniacore.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.afforess.minecartmaniacore.config.ItemAliasList;
import com.afforess.minecartmaniacore.debug.MinecartManiaLogger;
import com.afforess.minecartmaniacore.utils.DirectionUtils.CompassDirection;
import com.afforess.minecartmaniacore.world.AbstractItem;
import com.afforess.minecartmaniacore.world.Item;
/**
 * Authors: Afforess, Meaglin
 */
public class ItemUtils {
	
	/**
	 * Returns the first item name or id found in the given string, or null if there was no item name or id.
	 * If the item name is a partial name, it will match the name with the shortest number of letter.
	 * Ex ("reds" will match "redstone" (the wire item) and not redstone ore.
	 * @return material found, or null
	 */
	public static AbstractItem getFirstItemStringToMaterial(String str) {
		String[] list = {str};
		AbstractItem items[] = getItemStringListToMaterial(list);
		return items.length == 0 ? null : items[0];
	}
	
	/**
	 * Returns the list of material for each item name or id found in the given string, or an empty array if there was no item name or id.
	 * If the item name is a partial name, it will match the name with the shortest number of letter.
	 * Ex ("reds" will match "redstone" (the wire item) and not redstone ore.
	 * @return materials found, or an empty array
	 */
	public static AbstractItem[] getItemStringToMaterial(String str) {
		String[] list = {str};
		return getItemStringListToMaterial(list);
	}
	
	public static AbstractItem[] getItemStringListToMaterial(String[] list) {
		return getItemStringListToMaterial(list, null);
	}
	
	public static CompassDirection getLineItemDirection(String str) {
		CompassDirection direction = CompassDirection.NO_DIRECTION;
		int index = str.indexOf("+");
		if (index == 1) {
			String dir = str.substring(0, 1);
			if (dir.equalsIgnoreCase("n")) direction = CompassDirection.NORTH;
			if (dir.equalsIgnoreCase("s")) direction = CompassDirection.SOUTH;
			if (dir.equalsIgnoreCase("e")) direction = CompassDirection.EAST;
			if (dir.equalsIgnoreCase("w")) direction = CompassDirection.WEST;
		}
		return direction;
	}

	/**
	 * Returns the list of material for each item name or id found in the given array of strings, or an empty array if there was no item names or ids.
	 * If the item name is a partial name, it will match the name with the shortest number of letter.
	 * If there is a '!' next to a id or item name it will be removed from the list
	 * Ex ("reds" will match "redstone" (the wire item) and not redstone ore.
	 * @return materials found, or an empty array
	 */
	public static AbstractItem[] getItemStringListToMaterial(String[] list, CompassDirection facing) {
		ArrayList<AbstractItem> items = new ArrayList<AbstractItem>();
		for (int line = 0; line < list.length; line++) {
			String str = StringUtils.removeBrackets(list[line].toLowerCase());
			str = str.trim();
			if (str.isEmpty()) {
				continue;
			}

			//Check the given direction and intended direction from the sign
			CompassDirection direction = getLineItemDirection(str);
			if (direction != CompassDirection.NO_DIRECTION) {
				str = str.substring(2,str.length()); // remove the direction for further parsing.
			}
			if (facing != null && direction != facing && direction != CompassDirection.NO_DIRECTION) {
				continue;
			}
			
			//short circuit if it's everything
			if (str.contains("all items")) {
				for (Item m : Item.values()) {
					if (!items.contains(m)) {
						items.add(new AbstractItem(m));
					}
				}
			}
			
			String[] keys = str.split(":");
			for (int i = 0; i < keys.length; i++) {
				String part = keys[i].trim();
				List<AbstractItem> parsedset = parsePart(part);
				
				if(parsedset == null || parsedset.size() < 1)
					continue;
				
				for(AbstractItem item : parsedset){
					if (item == null) continue;
					if(item.getAmount() == -2)
						items.remove(item);
					else if(item.getAmount() != -1) {
						if(items.contains(item))
							items.remove(item);
						
						items.add(item);
					} else
						items.add(item);
				}
					
			}
			
		}
		
		
		//Remove Air from the list
		Iterator<AbstractItem> i = items.iterator();
		while (i.hasNext()) {
			AbstractItem type = i.next();
			if (type == null || type.type() == null || type.equals(Item.AIR)) {
				i.remove();
			}
		}
		
		AbstractItem itemList[] = new AbstractItem[items.size()];
		return items.toArray(itemList);
	}
	

	protected enum TYPE {
		AMOUNT("@"),
		REMOVE("!"),
		RANGE("-"),
		DATA(";"),
		NONE("");
		
		private final String tag;
		TYPE(String tag) {
			this.tag = tag;
		}
		public String getTag()	{
			return tag;
		}
		@Override
		public String toString() {
			return tag;
		}

		public static TYPE getType(String part)	{
			if(part.contains(RANGE.getTag())) // Range is parsed first Always!
				return RANGE;
			if(part.contains(REMOVE.getTag())) // since this 1 doesn't need special priority handling
				return REMOVE;
			
			return (part.lastIndexOf(DATA.getTag()) > part.lastIndexOf(AMOUNT.getTag()) ? DATA : (part.contains(AMOUNT.getTag())  ? AMOUNT : NONE) );
		}
	}
	
	/**
	 * Please don't change this order as it might screw up certain priorities!
	 * 
	 * @param part
	 * @return
	 */
	private static List<AbstractItem> parsePart(String part) {
		try {
			switch(TYPE.getType(part)) {
				case RANGE:
					return parseRange(part);
				case DATA:
					return Arrays.asList(parseData(part));
				case REMOVE:
					return parseNegative(part);
				case AMOUNT:
					return parseAmount(part);
				default:
					return parseNormal(part);
			}
		} catch(Exception e) {
			return null;
		}
	}
	private static List<AbstractItem> parseAmount(String part){
		String[] split   = part.split(TYPE.AMOUNT.getTag());
		List<AbstractItem> items = parsePart(split[0]);
		
		int amount = Integer.parseInt(split[1]);
		if (amount > 0) {
			for(AbstractItem item : items)
				item.setAmount(amount);
		}
		
		return items;
	}
	private static List<AbstractItem> parseNegative(String part){
		part = part.replace(TYPE.REMOVE.getTag(), "");
		List<AbstractItem> items = parsePart(part);
		for(AbstractItem item : items) {
			item.setAmount(-2);
			MinecartManiaLogger.getInstance().debug("Removing Item: " + item.type());
		}
		
		return items;
	}
	private static List<AbstractItem> parseRange(String part){
		String[] split   = part.split(TYPE.RANGE.getTag());
		List<AbstractItem> start = parsePart(split[0]);
		List<AbstractItem> end = parsePart(split[1]);
		List<AbstractItem> items = new ArrayList<AbstractItem>();
		AbstractItem startitem = start.get(0);
		AbstractItem enditem = end.get(end.size()-1);
		for(int item = startitem.getId();item <= enditem.getId();item++) {
			for(AbstractItem i : AbstractItem.getItem(item)) {
				if(i.getId() == startitem.getId()) {
					if(!startitem.hasData() || i.getData() >= startitem.getData())
						items.add(i);
				} else if(i.getId() == enditem.getId()) {
					if(!enditem.hasData() || i.getData() <= enditem.getData())	 
						items.add(i);
				} else
					items.add(i);
			}
		}
		if(startitem.getAmount() != -1) {
			for(AbstractItem i : items)
				i.setAmount(startitem.getAmount());
		} else if(enditem.getAmount() != -1) {
			for(AbstractItem i : items)
				i.setAmount(enditem.getAmount());
		}
		return items;
	}
	private static AbstractItem parseData(String part){
		String[] split   = part.split(TYPE.DATA.getTag());
		List<AbstractItem> items = parsePart(split[0]);
		int data = Integer.parseInt(split[1]);
		for(AbstractItem item : items)
			if(item.getData() == data)
				return item;
		
		return null;
	}
	private static List<AbstractItem> parseNormal(String part){
		try {
			return AbstractItem.getItem(Integer.parseInt(part));
		} catch(NumberFormatException exception) {
			List<Item> alias = ItemAliasList.getItemsForAlias(part);
			if(alias.size() > 0)
				return AbstractItem.itemListToAbstractItemList(alias);
			
			Item best = null;
			for (Item e : Item.values()) {
				if (e != null) {
					String item = e.toString().toLowerCase();
					if (item.contains(part)) {
						//If two items have the same partial string in them (e.g diamond and diamond shovel) the shorter name wins
						if (best == null || item.length() < best.toString().length()) {
							best = e;
						}
					}
				}
			}
			return Arrays.asList(new AbstractItem[] {new AbstractItem(best)});
		}
	}
}
