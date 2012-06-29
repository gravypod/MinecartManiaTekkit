package com.afforess.minecartmaniacore.world;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * An abstract representation of an item in MC.
 */
public class AbstractItem{
	final private Item item;
	private int amount = -1;
	public AbstractItem(Item item) {
		if (item == null) throw new UnsupportedOperationException("The item can not be null!");
		this.item = item;
	}
	
	public AbstractItem(Item item, int amount) {
		if (item == null) throw new UnsupportedOperationException("The item can not be null!");
		this.item = item;
		this.amount = amount;
	}
	
	public Item type() {
		return item;
	}
	
	public int getId() {
		return item.getId();
	}
	
	public int getData() {
		return item.getData();
	}
	
	public boolean hasData() {
		return item.hasData();
	}
	
	public boolean isInfinite() {
		return amount == -1;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public Material toMaterial() {
		return item.toMaterial();
	}
	
	public ItemStack toItemStack() {
		ItemStack item = this.item.toItemStack();
		if (!isInfinite()) {
			item.setAmount(getAmount());
		}
		return item;
	}
	
	public static List<AbstractItem> getItem(int id) {
		List<Item> list = Item.getItem(id);
		return itemListToAbstractItemList(list);
	}
	
	public static AbstractItem getItem(int id, int data) {
		Item i = Item.getItem(id, data);
		if (i != null) {
			return new AbstractItem(i);
		}
		return null;
	}
	
	public static List<AbstractItem> itemListToAbstractItemList(List<Item> list) {
		List<AbstractItem> aList = new ArrayList<AbstractItem>(list.size());
		for (Item i : list) {
			aList.add(new AbstractItem(i));
		}
		return aList;
	}
    
	public boolean equals(Item item) {
		return this.item.equals(item);
	}
	
	public boolean equals(Object o) {
		if (o instanceof AbstractItem) {
			return this.item.equals(((AbstractItem)o).type());
		}
		if (o instanceof Item) {
			return this.item.equals((Item)o);
		}
		return false;
	}
	
	public String toString() {
		return type().toString();
	}
}