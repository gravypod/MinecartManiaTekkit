package com.afforess.minecartmaniacore;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.afforess.minecartmaniacore.api.MinecartManiaActionListener;
import com.afforess.minecartmaniacore.api.MinecartManiaCoreBlockListener;
import com.afforess.minecartmaniacore.api.MinecartManiaCoreListener;
import com.afforess.minecartmaniacore.api.MinecartManiaCorePlayerListener;
import com.afforess.minecartmaniacore.api.MinecartManiaCoreWorldListener;
import com.afforess.minecartmaniacore.config.CoreSettingParser;
import com.afforess.minecartmaniacore.config.LocaleParser;
import com.afforess.minecartmaniacore.config.MinecartManiaConfigurationParser;
import com.afforess.minecartmaniacore.debug.MinecartManiaLogger;
import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecartDataTable;
import com.afforess.minecartmaniacore.minecart.MinecartOwner;
import com.afforess.minecartmaniacore.world.Item;

public class MinecartManiaCore extends JavaPlugin {
	
	public static final MinecartManiaCoreListener listener = new MinecartManiaCoreListener();
	public static final MinecartManiaCoreBlockListener blockListener = new MinecartManiaCoreBlockListener();
	public static final MinecartManiaCoreWorldListener worldListener = new MinecartManiaCoreWorldListener();
	public static final MinecartManiaActionListener actionListener = new MinecartManiaActionListener();
	public static final MinecartManiaCorePlayerListener playerListener = new MinecartManiaCorePlayerListener();
	public static MinecartManiaLogger log = MinecartManiaLogger.getInstance();
	@Deprecated
	public static Server server;
	@Deprecated
	public static Plugin instance;
	@Deprecated
	public static PluginDescriptionFile description;
	@Deprecated
	public static File data;
	@Deprecated
	public static File MinecartManiaCore;
	@Deprecated
	public static String dataDirectory = "plugins" + File.separator + "MinecartMania";
	@Deprecated
	public static boolean WormholeXTreme = false;
	@Deprecated
	public static boolean Nethrar = false;
	@Deprecated
	public static boolean Lockette = false;
	@Deprecated
	public static boolean LWC = false;
	private static final int DATABASE_VERSION = 3;
	
	public void onLoad() {
		setNaggable(false);
	}
	
	public void onEnable(){
		server = this.getServer();
		description = this.getDescription();
		instance = this;
		data = getDataFolder();
		MinecartManiaCore = this.getFile();
		
		//manage external plugins
		WormholeXTreme = getServer().getPluginManager().getPlugin("WormholeXTreme") != null;
		Nethrar = getServer().getPluginManager().getPlugin("Nethrar") != null;
		Lockette = getServer().getPluginManager().getPlugin("Lockette") != null;
		LWC = getServer().getPluginManager().getPlugin("LWC") != null;

		writeItemsFile();

		MinecartManiaConfigurationParser.read("MinecartManiaConfiguration.xml", dataDirectory, new CoreSettingParser());
		MinecartManiaConfigurationParser.read("MinecartManiaLocale.xml", dataDirectory, new LocaleParser());

		getServer().getPluginManager().registerEvents(listener, this);
		getServer().getPluginManager().registerEvents(worldListener, this);
		getServer().getPluginManager().registerEvents(blockListener, this);
		getServer().getPluginManager().registerEvents(playerListener, this);
		getServer().getPluginManager().registerEvents(actionListener, this);
		
		//database setup
		File ebeans = new File(new File(this.getDataFolder().getParent()).getParent(), "ebean.properties");
		if (!ebeans.exists()) {
			try {
				ebeans.createNewFile();
				PrintWriter pw = new PrintWriter(ebeans);
				pw.append("# General logging level: (none, explicit, all)");
				pw.append('\n');
				pw.append("ebean.logging=none");
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		setupDatabase();

		log.info( description.getName() + " version " + description.getVersion() + " is enabled!" );
	}
	
	public void onDisable(){
		getServer().getScheduler().cancelTasks(this);
		log.info( description.getName() + " version " + description.getVersion() + " is disabled!" );
	}
	
	private void writeItemsFile() {
		try {
			File items = new File(dataDirectory + File.separator + "items.txt");
			PrintWriter pw = new PrintWriter(items);
			pw.append("This file is a list of all the data values, and matching item names for Minecart Mania. \nThis list is never used, and changes made to this file will be ignored");
			pw.append("\n");
			pw.append("\n");
			pw.append("Items:");
			pw.append("\n");
			for (Item item : Item.values()) {
				String name = "Item Name: " + item.toString();
				pw.append(name);
				String id = "";
				for (int i = name.length()-1; i < 40; i++) {
					id += " ";
				}
				pw.append(id);
				id = "Item Id: " + String.valueOf(item.getId());
				pw.append(id);
				String data = "";
				for (int i = id.length()-1; i < 15; i++) {
					data += " ";
				}
				data += "Item Data: " + String.valueOf(item.getData());
				pw.append(data);
				pw.append("\n");
			}
			pw.close();
		}
		catch (Exception e) {}
	}
	
	private int getDatabaseVersion() {
		try {
			getDatabase().find(MinecartOwner.class).findRowCount();
		} catch (PersistenceException ex) {
			return 0;
		}
		try {
			getDatabase().find(MinecartManiaMinecartDataTable.class).findRowCount();
		} catch (PersistenceException ex) {
			return 1;
		}
		try {
			getDatabase().find(MinecartManiaMinecartDataTable.class).findList();
		} catch (PersistenceException ex) {
			return 2;
		}
		return DATABASE_VERSION;
	}
	
	protected void setupInitialDatabase() {
		try {
			getDatabase().find(MinecartOwner.class).findRowCount();
			getDatabase().find(MinecartManiaMinecartDataTable.class).findRowCount();
		}
		catch (PersistenceException ex) {
			log.info("Installing database");
			installDDL();
		}
	}
	
	protected void setupDatabase() {
		int version = getDatabaseVersion();
		switch(version) {
			case 0: setupInitialDatabase(); break;
			case 1: upgradeDatabase(1); break;
			case 2: upgradeDatabase(2); break;
			case 3: /*up to date database*/break;
		}
	}
	
	private void upgradeDatabase(int current) {
		log.info(String.format("Upgrading database from version %d to version %d", current, DATABASE_VERSION));
		if (current == 1 || current == 2) {
			this.removeDDL();
			setupInitialDatabase();
		}
		/*
		 * Add additional versions here
		 */
	}

	
	@Override
	public List<Class<?>> getDatabaseClasses() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(MinecartOwner.class);
		list.add(MinecartManiaMinecartDataTable.class);
		return list;
	}
	
	public static MinecartManiaCore getInstance() {
		return (MinecartManiaCore)instance;
	}
	
	public static PluginDescriptionFile getPluginDescription() {
		return description;
	}
	
	public static File getPluginDataFolder() {
		return data;
	}
	
	public static File getMinecartManiaCoreJar() {
		return MinecartManiaCore;
	}
	
	public static String getDataDirectoryRelativePath() {
		return dataDirectory;
	}
	
	public static boolean isWormholeXTremeEnabled() {
		return WormholeXTreme;
	}
	
	public static boolean isNethrarEnabled() {
		return Nethrar;
	}
	
	public static boolean isLocketteEnabled() {
		return Lockette;
	}
	
	public static boolean isLWCEnabled() {
		return LWC;
	}
	
	public static void callEvent(Event event) {
		Bukkit.getServer().getPluginManager().callEvent(event);
	}
}
