package com.afforess.minecartmaniacore.utils;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Deprecated
public class ChatUtils {
	
	@Deprecated
	public static void sendMultilineWarning(Player player, String message) {
		sendMultilineMessage(player, message, ChatColor.DARK_RED.toString());
	}
	
	@Deprecated
	public static void sendMultilineMessage(org.bukkit.entity.Player player, String message) {
		sendMultilineMessage(player, message, ChatColor.WHITE.toString());
	}
	
	@Deprecated
	public static void sendMultilineMessage(Player player, String message, String color) {
		final int maxLength = 61;
		final String newLine = "[NEWLINE]";
		ArrayList<String> chat = new ArrayList<String>();
		chat.add(0, color);
		String[] lines = message.split(newLine);
		String format = "";
		for (String s : lines) {
			format += s.trim() + " " + newLine + " ";
		}
		message = format;
		String[] words = message.split(" ");
		int lineNumber = 0;
		for (int i = 0; i < words.length; i++) {
			if (chat.get(lineNumber).length() + words[i].length() < maxLength && !words[i].equals(newLine)) {
				chat.set(lineNumber, chat.get(lineNumber) + " " + words[i]);
			}
			else {
				lineNumber++;
				if (!words[i].equals(newLine)) {
					chat.add(lineNumber, color + words[i]);
				}
				else
					chat.add(lineNumber,color);
			}
		}
		for (int i = 0; i < chat.size(); i++) {
			player.sendMessage(chat.get(i).trim());
		}
		
	}
}
