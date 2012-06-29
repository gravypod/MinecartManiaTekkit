package com.afforess.minecartmaniacore.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.ChatColor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.afforess.minecartmaniacore.MinecartManiaCore;
import com.afforess.minecartmaniacore.debug.MinecartManiaLogger;

public class LocaleParser implements SettingParser{
	private final double version = 1.02;
	private static final ConcurrentHashMap<String, String> textKeys = new ConcurrentHashMap<String, String>();

	public boolean isUpToDate(Document document) {
		try {
			NodeList list = document.getElementsByTagName("version");
			Double version = MinecartManiaConfigurationParser.toDouble(list.item(0).getChildNodes().item(0).getNodeValue(), 0);
			return version == this.version;
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean read(Document document) {
		NodeList list = document.getElementsByTagName("TextKey");
		for (int temp = 0; temp < list.getLength(); temp++) {
			Node n = list.item(temp);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) n;
				NodeList templist = element.getElementsByTagName("Key").item(0).getChildNodes();
				Node tempNode = (Node) templist.item(0);
				String key = getNodeValue(tempNode);
				templist = element.getElementsByTagName("Text").item(0).getChildNodes();
				tempNode = (Node) templist.item(0);
				String text = getNodeValue(tempNode);
				if (key != null && text != null) {
					MinecartManiaLogger.getInstance().debug("Added Text Key Key: " + key + " Text: " + text);
					textKeys.put(key, text);
				}
				else {
					MinecartManiaLogger.getInstance().severe("Invalid Text Key! Key: " + key + " Text: " + text);
				}
			}
		}
		return true;
	}

	public boolean write(File config, Document document) {
		try {
			JarFile jar = new JarFile(MinecartManiaCore.getMinecartManiaCoreJar());
			JarEntry entry = jar.getJarEntry("MinecartManiaLocale.xml");
			InputStream is = jar.getInputStream(entry);
			FileOutputStream os = new FileOutputStream(config);
			byte[] buf = new byte[(int)entry.getSize()];
			is.read(buf, 0, (int)entry.getSize());
			os.write(buf);
			os.close();
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public static String parseColors(String str) {
		for (ChatColor color : ChatColor.values()) {
			String name = "\\[" + color.name().toUpperCase() + "]";
			str = str.replaceAll(name, color.toString());
		}
		return str;
	}
	
	public static String getTextKey(String key, Object ...args) {
		String value = textKeys.get(key);
		if (value == null) {
			return "Missing Text Key: " + key;
		}
		String result = String.format(parseColors(value), (Object[])args);
		result = result.replace("\\n", "\n");
		return result;
	}
	
	private String getNodeValue(Node node) {
		if (node == null) return null;
		return node.getNodeValue();
	}


}
