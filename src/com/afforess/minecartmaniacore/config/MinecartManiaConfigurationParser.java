package com.afforess.minecartmaniacore.config;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.afforess.minecartmaniacore.debug.MinecartManiaLogger;
import com.afforess.minecartmaniacore.utils.ItemUtils;
import com.afforess.minecartmaniacore.utils.StringUtils;
import com.afforess.minecartmaniacore.world.AbstractItem;
import com.afforess.minecartmaniacore.world.Item;

public class MinecartManiaConfigurationParser {
	private static MinecartManiaLogger log = MinecartManiaLogger.getInstance();
	
	public static void read(String filename, String directory, SettingParser parser) {
		File dir = new File(directory);
		if (!dir.exists()) {
			dir.mkdir();
		}
		File config = new File(directory, filename);
		if (!config.exists()) {
			parser.write(config, null);
		}
		config = new File(directory, filename);
		Document doc = null;
		Document originalDoc = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(config.toURI().getPath());
			doc.getDocumentElement().normalize();
			originalDoc = dBuilder.parse(config.toURI().getPath());
			originalDoc.getDocumentElement().normalize();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		//if (parser instanceof CoreSettingParser) {
		//	System.out.println("attempting to update");
		//	((CoreSettingParser)parser).update(config);
		//}

		if (!parser.isUpToDate(doc)) {
			//Could not update the document
			File old = new File(directory, filename + ".bak");
			if (old.exists()) {
				old.delete();
			}
			config.renameTo(old);
			log.info("Attempted update failed - Backup config file - renamed " + filename + " to " + filename + ".bak");
			if (!parser.write(config, null)) {
				log.severe("FAILED TO WRITE CONFIGURATION! Directory: " + directory + " File: " + filename);
			} else {
				log.info("Created new config file - " + filename);
			}
		} else {
			String versionDoc = doc.getElementsByTagName("version").item(0).getTextContent();
			String versionOrigDoc = originalDoc.getElementsByTagName("version").item(0).getTextContent();

			if (!versionDoc.equalsIgnoreCase(versionOrigDoc)) {
				log.info("Updated config file from " + versionOrigDoc + " to " + versionDoc + ".");
				//doc was successfully updated by isUpToDate function
				//try to save the changed doc back down to the disk
				File old = new File(directory, filename + ".bak");
				if (old.exists()) old.delete();
				config.renameTo(old);
				log.info("Backup config file - renamed " + filename + " to " + filename + ".bak");
				if (!parser.write(config, doc)) {
					log.severe("FAILED TO WRITE CONFIGURATION! Directory: " + directory + " File: " + filename);
				} else {
					log.info("Saved updated config file - " + filename);
				}
			}
		}

		config = new File(directory, filename);
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(config.toURI().getPath());
			doc.getDocumentElement().normalize();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if (!parser.read(doc)) {
			log.severe("FAILED TO READ CONFIGURATION! Directory: " + directory + " File: " + filename);
		}
	}
	
	public static boolean toBool(String str) {
		if (str == null) {
			return false;
		}
		return str.equalsIgnoreCase("true");
	}
	
	public static int toInt(String str, int def) {
		if (str == null) return def;
		try {
			return Integer.parseInt(StringUtils.getNumber(str));
		}
		catch(Exception e) {}
		return def;
	}
	
	public static double toDouble(String str, double def) {
		if (str == null) return def;
		try {
			return Double.parseDouble(StringUtils.getNumber(str));
		}
		catch(Exception e) {}
		return def;
	}
	
	public static Item toItem(String str) {
		if (str == null) return null;
		AbstractItem[] list = ItemUtils.getItemStringToMaterial(str);
		if (list != null && list.length > 0) {
			return list[0].type();
		}
		return null;
	}
	
	public static void updateSetting(Document document, String setting, String defaultVal, Element root) {
		Node node = getNodeForTag(document, setting);
		if (node == null) {
			node = document.createElement(setting);
			node.appendChild(document.createTextNode(defaultVal));
			root.appendChild(node);
		}
	}
	
	public static Node getNodeForTag(Document document, String tag) {
		if (document.getElementsByTagName(tag) != null) {
			if (document.getElementsByTagName(tag).item(0) != null) {
				if (document.getElementsByTagName(tag).item(0).getChildNodes() != null) {
					return document.getElementsByTagName(tag).item(0).getChildNodes().item(0);
				}
			}
		}
		return null;
	}
}
