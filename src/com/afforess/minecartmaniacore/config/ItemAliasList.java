package com.afforess.minecartmaniacore.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.afforess.minecartmaniacore.world.Item;

public class ItemAliasList{
	public static ConcurrentHashMap<String, List<Item>> aliases = new ConcurrentHashMap<String, List<Item>>();
	
	public static boolean isAlias(String alias) {
		Iterator<Entry<String, List<Item>>> i = aliases.entrySet().iterator();
		while(i.hasNext()) {
			Entry<String, List<Item>> e = i.next();
			String key = e.getKey();
			if (key.equalsIgnoreCase(alias)) {
				return true;
			}
		}
		return false;
	}
	
	public static List<Item> getItemsForAlias(String alias) {
		Iterator<Entry<String, List<Item>>> i = aliases.entrySet().iterator();
		while(i.hasNext()) {
			Entry<String, List<Item>> e = i.next();
			String key = e.getKey();
			if (key.equalsIgnoreCase(alias)) {
				return e.getValue();
			}
		}
		return new ArrayList<Item>();
	}
}
