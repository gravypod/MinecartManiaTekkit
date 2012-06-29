package com.afforess.minecartmaniacore.config;

import java.io.File;

import org.w3c.dom.Document;

public interface SettingParser {
	
	public boolean isUpToDate(Document document);
	
	public boolean read(Document document);
	
	public boolean write(File config, Document document);

}
