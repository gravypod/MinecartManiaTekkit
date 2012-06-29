package com.afforess.minecartmaniacore.config;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.afforess.minecartmaniacore.MinecartManiaCore;
import com.afforess.minecartmaniacore.debug.DebugMode;
import com.afforess.minecartmaniacore.debug.MinecartManiaLogger;
import com.afforess.minecartmaniacore.utils.DirectionUtils.CompassDirection;
import com.afforess.minecartmaniacore.world.Item;
import com.afforess.minecartmaniacore.world.MinecartManiaWorld;

public class CoreSettingParser implements SettingParser{
	private static final double version = 1.54;
	private static MinecartManiaLogger log = MinecartManiaLogger.getInstance();

	//This not only will tell true/false if the document is up to date, but will try to update it before it answers.
	//This will only try to update existing settings and add ones where necessary.
	//It will not create new blocks or anything. It will try its best to keep settings exactly the same
	public boolean isUpToDate(Document document) {
		try {
			NodeList list = document.getElementsByTagName("version");
			Double version = MinecartManiaConfigurationParser.toDouble(list.item(0).getChildNodes().item(0).getNodeValue(), 0);
			log.debug("Core Config read: version: " + list.item(0).getTextContent());
			if (version == 1.3 || version == 1.4) {
				//do changes to make this 1.3 or 1.4 into a 1.51 structure.
				//NOTE: this will not reset it to the new default, only update it while preserving data.
				list = document.getElementsByTagName("ControlBlock");
				for (int idx = 0; idx < list.getLength(); idx++) {
					Node controlBlock = list.item(idx);
					//Add the Elevator option to each Control block
					Element newNode = document.createElement("Elevator");
						controlBlock.appendChild(newNode);
					//Add the AutoConvertToPoweredRails option to each Control block and set it to false
					newNode = document.createElement("AutoConvertToPoweredRails");
						newNode.appendChild(document.createTextNode("false"));
						controlBlock.appendChild(newNode);
				}
				//convert the speedMultiplier node to the new structure
				list = document.getElementsByTagName("SpeedMultiplier");
				for (int idx = list.getLength()-1; idx >= 0; idx--) { //go in descending order so our list doesn't disappear from under us when we replace nodes.
					Node oldNode = list.item(idx);
					String oldNodeValue = oldNode.getTextContent();
					Node oldRedstone = oldNode.getAttributes().getNamedItem("redstone");
					String oldNodeRedstoneValue = (oldRedstone == null ? "default":oldRedstone.getNodeValue());
					//Add the SpeedMultipliers structure
					Element speedMultipliers = document.createElement("SpeedMultipliers");
						if (oldNodeValue != "") {
							Element speedMultiplier = document.createElement("SpeedMultiplier");
								Element speedMultiplierNode = document.createElement("Redstone");
									speedMultiplierNode.appendChild(document.createTextNode(oldNodeRedstoneValue));
									//Append the child node
									speedMultiplier.appendChild(speedMultiplierNode);
								speedMultiplierNode = document.createElement("Multiplier");
									speedMultiplierNode.appendChild(document.createTextNode(oldNodeValue));
									//Append the child node
									speedMultiplier.appendChild(speedMultiplierNode);
								speedMultiplierNode = document.createElement("Direction");
									speedMultiplierNode.appendChild(document.createTextNode("Any"));
									//Append the child node
									speedMultiplier.appendChild(speedMultiplierNode);
								speedMultiplierNode = document.createElement("MinecartTypes");
									Element minecartNode = document.createElement("MinecartType");
										minecartNode.appendChild(document.createTextNode("Standard"));
										speedMultiplierNode.appendChild(minecartNode);
									minecartNode = document.createElement("MinecartType");
										minecartNode.appendChild(document.createTextNode("Powered"));
										speedMultiplierNode.appendChild(minecartNode);
									minecartNode = document.createElement("MinecartType");
										minecartNode.appendChild(document.createTextNode("Storage"));
										speedMultiplierNode.appendChild(minecartNode);
									//Append the child node
									speedMultiplier.appendChild(speedMultiplierNode);
								speedMultiplierNode = document.createElement("Passenger");
									speedMultiplierNode.appendChild(document.createTextNode("default"));
									//Append the child node
									speedMultiplier.appendChild(speedMultiplierNode);
								//Append the child node
								speedMultipliers.appendChild(speedMultiplier);
						}
						//Replace the speedMultiplier node
						Node parent = oldNode.getParentNode();
						parent.replaceChild(speedMultipliers, oldNode);
				}
				list = document.getElementsByTagName("version");
				version = 1.51;
				list.item(0).setTextContent(version.toString());
			} 
			if (version == 1.51) {
				Node root = document.getElementsByTagName("MinecartManiaConfiguration").item(0);
				Node last = document.getElementsByTagName("ControlBlocks").item(0);
				root.insertBefore(document.createComment("Minecarts that are destroyed will not drop an item if they are destroyed") , last);
				root.insertBefore(document.createTextNode("\n\t"), last);
				
				Element removeDeadMinecarts = document.createElement("RemoveDeadMinecarts");
				removeDeadMinecarts.appendChild(document.createTextNode("false"));
				root.insertBefore(removeDeadMinecarts, last);
				
				root.insertBefore(document.createComment("MM only searchs under or parallel to rails for signs, harshly limiting the search radius. \n\t" +
						"This will improve performance, but will restrict sign placement.") , last);
				root.insertBefore(document.createTextNode("\n\t"), last);
				
				Element limitedSignRange = document.createElement("LimitedSignRange");
				limitedSignRange.appendChild(document.createTextNode("false"));
				root.insertBefore(limitedSignRange, last);
				
				version = 1.52;	//This needs to be updated to the next version of the document.
				list.item(0).setTextContent(version.toString());
			}
			if (version == 1.52) {
				Node root = document.getElementsByTagName("MinecartManiaConfiguration").item(0);
				Node last = document.getElementsByTagName("ControlBlocks").item(0);
				root.insertBefore(document.createComment("Minecarts Disappear on disconnect. Players who are inside of a minecart will cause the minecart to \"disconnect\" " +
						"\n\tas well, and re-join when the player does. Minecarts will re-appear and retain their previous settings and speed on reconnecting.") , last);
				root.insertBefore(document.createTextNode("\n\t"), last);
				
				Element dissappearOnDisconnect = document.createElement("DisappearOnDisconnect");
				dissappearOnDisconnect.appendChild(document.createTextNode("true"));
				root.insertBefore(dissappearOnDisconnect, last);
				
				version = 1.53;	//This needs to be updated to the next version of the document.
				list.item(0).setTextContent(version.toString());
			}
			if(version == 1.53)
			{
                Node root = document.getElementsByTagName("MinecartManiaConfiguration").item(0); 
                Node last = root.getLastChild();
                root.insertBefore(document.createComment("Specifies if the old or new bukkit/minecraft directions should be used. If you have a long existing network (pre MC 1.0) stay with false.") , last);
                Element useOldDirections = document.createElement("UseOldDirections");
                useOldDirections.appendChild(document.createTextNode("true"));
                root.insertBefore(useOldDirections, last);
                version = 1.54;
                list.item(0).setTextContent(version.toString());			    
			}
			return version == CoreSettingParser.version;
		}
		catch (Exception e) {
			return false;
		}
	}

	//This will read the configuration document passed in and set values based on the nodes it finds
	public boolean read(Document document) {
		//Set the default configuration before we try to read anything.
		setDefaultConfiguration();

		//Object to hold a list of Nodes that we extract from the document
		NodeList list;
		try {
			list = document.getElementsByTagName("MinecartManiaConfiguration").item(0).getChildNodes();	//get the root nodes of the ConfigurationTree
			String elementChildName = "";		//holds the name of the node
			String elementChildValue = "";		//holds the value of the node
			//loop through each of the child nodes of the document
			for (int idx = 0; idx < list.getLength(); idx++) {
				Node elementChild = list.item(idx);	//extract the node
				elementChildName = "";				//reset the child name
				elementChildValue = null;			//reset the child value
				//do we have a valid element node
				if (elementChild.getNodeType() == Node.ELEMENT_NODE) {
					elementChildName = elementChild.getNodeName();	//get the node name
					//does this node have children
					if (elementChild.getChildNodes() != null) {
						//get the first child node - this should give us the true/false/value within a single node
						elementChildValue = getNodeValue(elementChild.getChildNodes().item(0));
					}
					if (elementChildValue != null && elementChildValue != "") {
						//Handle the possible nodes we have at this level.
						if (elementChildName == "version") {
							if (elementChildValue != String.valueOf(version)) { /* documentUpgrade(document); */ }
						} else if (elementChildName == "LoggingMode") {
							DebugMode mode = DebugMode.debugModeFromString(elementChildValue);
							MinecartManiaLogger.getInstance().switchDebugMode(mode);
							log.debug("Core Config read: " + elementChildName + " = " + elementChildValue);
						} else if (elementChildName == "MinecartsKillMobs"
								|| elementChildName == "KeepMinecartsLoaded"
								|| elementChildName == "MinecartsReturnToOwner"
								|| elementChildName == "StackAllItems"
								|| elementChildName == "RemoveDeadMinecarts"
								|| elementChildName == "LimitedSignRange"
                                || elementChildName == "UseOldDirections"
                                || elementChildName == "DisappearOnDisconnect"
								) {
							MinecartManiaWorld.getConfiguration().put(elementChildName, MinecartManiaConfigurationParser.toBool(elementChildValue));
							log.debug("Core Config read: " + elementChildName + " = " + (MinecartManiaConfigurationParser.toBool(elementChildValue) ? "true" : "false"));
						} else if (elementChildName == "MinecartsClearRails"
								|| elementChildName == "MaximumMinecartSpeedPercent"
								|| elementChildName == "DefaultMinecartSpeedPercent"
								|| elementChildName == "Range"
								|| elementChildName == "RangeY"
								|| elementChildName == "MaximumRange"
								) {
							MinecartManiaWorld.getConfiguration().put(elementChildName, MinecartManiaConfigurationParser.toInt(elementChildValue, getDefaultConfigurationIntegerValue(elementChildName)));
							log.debug("Core Config read: " + elementChildName + " = " + elementChildValue);
						} else if (elementChildName == "ControlBlocks") {
							log.debug("Core Config read: ControlBlocks");
							readControlBlocks(elementChild.getChildNodes());
						} else if (elementChildName == "ItemAliases") {
							log.debug("Core Config read: Item Aliases");
							readItemAliases(elementChild.getChildNodes());
						} else {
							log.info("Core Config read unknown node: " + elementChildName);
						}
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		debugShowConfigs();
		return true;
	}
	//This will read the Control blocks in the Configuration file
	private boolean readControlBlocks(NodeList list) {
		try {
			ControlBlockList.controlBlocks = new ArrayList<ControlBlock>();	//init where we store the control blocks
			ControlBlock 	cb; //create a holder for our control blocks that will be injected into the controlBlocks list
			NodeList 		elementChildren;		//Holder for the children of the ControlBlock we are processing
			Node     		elementChild;      		//A specific child node being processed
			Node     		elementChildAttribute;	//The attribute of a node
			String			elementChildName;		//The name of a child node
			String			elementChildValue;		//The value of a child node
			RedstoneState 	attributeRedstone;		//The attributeRedstone of a node
			double 			range;					//The range attribute of a node
			double			ejectY;					//The Y axis offset to eject at

			//Loop through each node in the list looking for a control block
			for (int temp = 0; temp < list.getLength(); temp++) {
				Node element = list.item(temp);
				//Make sure it is a ControlBlock element node
				if (element.getNodeType() == Node.ELEMENT_NODE && element.getNodeName() == "ControlBlock") {
					cb = new ControlBlock();					//initialize our new control block object
					elementChildren = element.getChildNodes();	//get the children of this ControlBlock node
					//Loop through each of the control block modifiers.
					for(int idx = 0; idx < elementChildren.getLength(); idx++) {
						attributeRedstone = null;					//reset the attributeRedstone to a null value
						elementChildValue = null;					//reset the elementChildVlue to a null value
						range = -1;									//reset the range to an invalid value
						ejectY = -1;
						elementChild = elementChildren.item(idx);	//Get the specific control block modifier node
						//make sure it is an elementNode tag and not a space or comment.
						if (elementChild.getNodeType() == Node.ELEMENT_NODE) {
							elementChildName = elementChild.getNodeName(); //get the specific modifier node name.
							//Get the child node value if this node has children
							if (elementChild.getChildNodes() != null)
								elementChildValue = getNodeValue(elementChild.getChildNodes().item(0));
							//Do special handling of the SpeedMultipliers node
							if (elementChildName == "SpeedMultipliers") {
								if (elementChildName != "BlockType") {
									log.debug("Core Config read:       Modifier: SpeedMultipliers");
								}
								readSpeedMultiplierModifiers(cb, elementChild.getChildNodes());
							} else {
								if (elementChildName != "BlockType") {
									log.debug("Core Config read:       Modifier: " + elementChildName + " = " + elementChildValue);
								}
								if (elementChildValue != null){
									//see if this modifier has attributes to it.
									if(elementChild.getAttributes() != null) {
										//Loop through each of the attributes of the modifier
										for(int idxAttrib = 0; idxAttrib < elementChild.getAttributes().getLength(); idxAttrib++) {
											//process the attribute of the modifier if we recognize it
											elementChildAttribute = elementChild.getAttributes().item(idxAttrib);
											if(elementChildAttribute.getNodeName() == "redstone") {
												attributeRedstone = parseRedstoneState(elementChildAttribute.getNodeValue());
												if (elementChildName != "BlockType") {
													log.debug("Core Config read:                 redstone: " + elementChildAttribute.getNodeValue());
												}
											}
											else if (elementChildAttribute.getNodeName() == "range") {
												range = MinecartManiaConfigurationParser.toDouble(elementChildAttribute.getNodeValue(), 4);
												if (elementChildName == "Platform") {
													log.debug("Core Config read:                 platform range: " + elementChildAttribute.getNodeValue());
												}
											}
											else if (elementChildAttribute.getNodeName() == "ejecty") {
												ejectY = MinecartManiaConfigurationParser.toDouble(elementChildAttribute.getNodeValue(), 0);
												if (elementChildName == "Eject") {
													log.debug("Core Config read:                 eject Y: " + elementChildAttribute.getNodeValue());
												}
											}
											else if (elementChildAttribute.getNodeName() != null ){
												log.info("Core Config read:                 unknown attribute: " + elementChildAttribute.getNodeValue());
											}
										}
									}
									if (elementChildName == "BlockType") {
										log.debug("Core Config read:   ControlBlock: " + elementChildValue);
										cb.setType(MinecartManiaConfigurationParser.toItem(elementChildValue));
									} else if (elementChildName == "Catch") {
										cb.setCatcherState(attributeRedstone);
										cb.setCatcherBlock(MinecartManiaConfigurationParser.toBool(elementChildValue));
									} else if (elementChildName == "LauncherSpeed") {
										cb.setLauncherState(attributeRedstone);
										cb.setLauncherSpeed(MinecartManiaConfigurationParser.toDouble(elementChildValue, 0.0));
									} else if (elementChildName == "Eject") {
										cb.setEjectorState(attributeRedstone);
										cb.setEjectorBlock(MinecartManiaConfigurationParser.toBool(elementChildValue));
										if (ejectY > 0) {
											cb.setEjectY(ejectY);
										}
									} else if (elementChildName == "Platform") {
										cb.setPlatformState(attributeRedstone);
										cb.setPlatformBlock(MinecartManiaConfigurationParser.toBool(elementChildValue));
										if (range > 0) {
											cb.setPlatformRange(range);
										}
									} else if (elementChildName == "Station") {
										cb.setStationState(attributeRedstone);
										cb.setStationBlock(MinecartManiaConfigurationParser.toBool(elementChildValue));
									} else if (elementChildName == "SpawnMinecart") {
										cb.setSpawnState(attributeRedstone);
										cb.setSpawnMinecart(MinecartManiaConfigurationParser.toBool(elementChildValue));
									} else if (elementChildName == "KillMinecart") {
										cb.setKillState(attributeRedstone);
										cb.setKillMinecart(MinecartManiaConfigurationParser.toBool(elementChildValue));
									} else if (elementChildName == "Elevator") {
										cb.setElevatorState(attributeRedstone);
										cb.setElevatorBlock(MinecartManiaConfigurationParser.toBool(elementChildValue));
									} else if (elementChildName == "AutoConvertToPoweredRails") {
										cb.updateToPoweredRail = MinecartManiaConfigurationParser.toBool(elementChildValue);
									} else {
										log.info("Core Config read unknown node in ControlBlock: " + elementChildName);
									}
								}
							}
						}
					}
					ControlBlockList.controlBlocks.add(cb);
				} else if (element.getNodeType() == Node.ELEMENT_NODE) {
					log.info("Core Config read unknown node in ControlBlocks: " + element.getNodeName());
				}
			}
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}
	//This will read the SpeedMultiplier Modifiers
	private boolean readSpeedMultiplierModifiers(ControlBlock cb, NodeList list) {
		ArrayList<SpeedMultiplier> speedMultipliers = new ArrayList<SpeedMultiplier>(); //init where we store the speed multiplier settings
		NodeList	elementChildren;		//Holder for the children of the SpeedMultiplier node we are processing
		Node     	elementChild;      		//A specific child node being processed
		String		elementChildName;		//The name of a child node
		String		elementChildValue;		//The value of a child node

		try {
			//Loop through each node in the list looking for a SpeedMultiplier
			for (int temp = 0; temp < list.getLength(); temp++) {
				Node element = list.item(temp);			//extract the single node from the list
				//Make sure it is a ControlBlock element node
				if (element.getNodeType() == Node.ELEMENT_NODE && element.getNodeName() == "SpeedMultiplier") {
					elementChildren = element.getChildNodes();		//get the children of this ControlBlock node
					SpeedMultiplier speed = new SpeedMultiplier();	//Create a new SpeedMultiplier
						//set default values
						speed.redstone = RedstoneState.Default;
						speed.multiplier = 1.0;
						speed.direction = CompassDirection.NO_DIRECTION;
						speed.types = new boolean[3];
						speed.passenger =  PassengerState.Default;
					//Loop through each of the Speed Multiplier modifiers.
					for(int idx = 0; idx < elementChildren.getLength(); idx++) {
						elementChild = elementChildren.item(idx);	//Get the specific SpeedMultiplier modifier node
						elementChildValue = null;					//reset the elementChildVlue to a null value
						//make sure it is an elementNode tag and not a space or comment.
						if (elementChild.getNodeType() == Node.ELEMENT_NODE) {
							elementChildName = elementChild.getNodeName(); //get the specific modifier node name.
							//Get the child node value if this node has children
							if (elementChild.getChildNodes() != null)
								elementChildValue = getNodeValue(elementChild.getChildNodes().item(0));
							if (elementChildName == "MinecartTypes") {
								readSpeedMultiplierModifiersMinecartTypes(speed, elementChild.getChildNodes());
							} else {
								if (elementChildValue != null){
									if        (elementChildName == "Redstone") {
										log.debug("Core Config read:                     Redstone: " + elementChildValue);
										speed.redstone = parseRedstoneState(elementChildValue);
									} else if (elementChildName == "Multiplier") {
										log.debug("Core Config read:                     Multiplier: " + elementChildValue);
										speed.multiplier = MinecartManiaConfigurationParser.toDouble(elementChildValue, 1.0);
									} else if (elementChildName == "Direction") {
										log.debug("Core Config read:                     Direction: " + elementChildValue);
										speed.direction = parseDirectionState(elementChildValue);
									} else if (elementChildName == "Passenger") {
										log.debug("Core Config read:                     Passenger: " + elementChildValue);
										speed.passenger = parsePassengerState(elementChildValue);
									} else {
										log.info("Core Config read unknown node in ControlBlock SpeedMultiplier modifiers: " + elementChildName);
									}
								}
							}
						}
					}
					//Add the speed multiplier setting to the modifier
					speedMultipliers.add(speed);
				} else if (element.getNodeType() == Node.ELEMENT_NODE) {
					log.info("Core Config read unknown node in ControlBlock SpeedMultiplier: " + element.getNodeName());
				}
			}
			cb.setSpeedMultipliers(speedMultipliers);
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}
	//This will read the MinecartTypes in the SpeedMultiplier modifier.
	private boolean readSpeedMultiplierModifiersMinecartTypes(SpeedMultiplier speed, NodeList list) {
		String elementValue;		//The value of a child node

		try {
			//Loop through each node in the list looking for a MinecartType node
			boolean types[] = new boolean[3];  //declare return value for the function
			log.debug("Core Config read:                     MinecartTypes");
			for (int temp = 0; temp < list.getLength(); temp++) {
				Node element = list.item(temp);			//extract the single node from the list
				//Make sure it is a MinecartType element node
				if (element.getNodeType() == Node.ELEMENT_NODE && element.getNodeName() == "MinecartType") {
					elementValue = null;
					//Get the element value
					if (element.getChildNodes() != null)
						elementValue = getNodeValue(element.getChildNodes().item(0));
					log.debug("Core Config read:                             Type:" + elementValue);
					//Record the element value in one of the possible types
					if      (elementValue.equalsIgnoreCase("standard")) {
						types[0] = true;
					} else if (elementValue.equalsIgnoreCase("powered")) {
						types[1] = true;
					} else if (elementValue.equalsIgnoreCase("storage")) {
						types[2] = true;
					} else {
						log.info("Core Config read unknown value in ControlBlock SpeedMultiplier MinecartTypes: " + elementValue);
					}
				} else if (element.getNodeType() == Node.ELEMENT_NODE) {
					log.info("Core Config read unknown node in ControlBlock SpeedMultiplier MinecartTypes: " + element.getNodeName());
				}
			}
			speed.types = types;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	//This will read the ItemAliases
	private boolean readItemAliases(NodeList list) {
		try {
			for (int temp = 0; temp < list.getLength(); temp++) {
				Node n = list.item(temp);
				if (n.getNodeType() == Node.ELEMENT_NODE) {
					if (n.getNodeName() == "ItemAlias") {
						Element element = (Element)n;
						NodeList elementChildren = element.getChildNodes();
						String elementChildName = "";
						String elementChildValue = null;
						String aliasName = "";
						ArrayList<Item> aliasValues = new ArrayList<Item>();
						for(int idx = 0; idx < elementChildren.getLength(); idx++) {
							Node elementChild = elementChildren.item(idx);
							if (elementChild.getNodeType() == Node.ELEMENT_NODE) {
								elementChildName = elementChild.getNodeName();
								elementChildValue = null;
								if (elementChild.getChildNodes() != null) {
									elementChildValue = getNodeValue(elementChild.getChildNodes().item(0));
								}
								if (elementChildName == "AliasName") {
									log.debug("Core Config read:    Item Alias: " + elementChildValue);
									aliasName = elementChildValue;
								} else if (elementChildName == "ItemType") {
									//special case: all items
									if (elementChildValue != null && elementChildValue.toLowerCase().contains("all item")) {
	                                    log.debug("Core Config read:         Block: " + elementChildValue);
										aliasValues.addAll(Arrays.asList(Item.values()));
									}
									else {
									    Item item = MinecartManiaConfigurationParser.toItem(elementChildValue);
									    if (item != null) {
									        log.debug("Core Config read:         Block: " + elementChildValue);
									        aliasValues.add(item);
									    } else {
									        log.debug("Core Config read:         Block Error: '" + elementChildValue + "' invalid name.");
									    }
									}
								} else {
									log.info("Core Config read unknown node in ItemAlias: " + elementChildName);
								}
							}
						}
						ItemAliasList.aliases.put(aliasName, aliasValues);
					}
				}
			}
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}

	private void debugShowConfigs() {
		//Display global configuration values
		for (Enumeration<String> ConfigKeys = MinecartManiaWorld.getConfiguration().keys(); ConfigKeys.hasMoreElements();) {
			String temp = ConfigKeys.nextElement();
			String value = MinecartManiaWorld.getConfigurationValue(temp) != null ? MinecartManiaWorld.getConfigurationValue(temp).toString() : "null";
			log.debug("Core Config: " + temp + " = " + value);
		}
		//Display the control blocks
		ListIterator<ControlBlock> li = ControlBlockList.getControlBlockList().listIterator();
		log.debug("Core Config: ControlBlocks");
		while(li.hasNext()) {
			ControlBlock cb = li.next();
			log.debug("Core Config:   ControlBlock: " + cb.getType());
			if (cb.isCatcherBlock())    log.debug("Core Config:       Modifier: Catch (redstone = " + cb.getCatcherState().toString() + ")");
			if (cb.isEjectorBlock())    log.debug("Core Config:       Modifier: Eject (redstone = " + cb.getEjectorState().toString() + ", ejecty = " + cb.getEjectY() + ")");
			if (cb.isPlatformBlock())   log.debug("Core Config:       Modifier: Platform (redstone = " + cb.getPlatformState().toString() + ", range = " + cb.getPlatformRange() + ")");
			if (cb.isStationBlock())    log.debug("Core Config:       Modifier: Station (redstone = " + cb.getStationState().toString() + ")");
			if (cb.isSpawnMinecart())   log.debug("Core Config:       Modifier: SpawnMinecart (redstone = " + cb.getSpawnState().toString() + ")");
			if (cb.isKillMinecart())    log.debug("Core Config:       Modifier: KillMinecart (redstone = " + cb.getKillState().toString() + ")");
			if (cb.isElevatorBlock())   log.debug("Core Config:       Modifier: Elevator (redstone = " + cb.getElevatorState().toString() + ")");
			if (cb.updateToPoweredRail) log.debug("Core Config:       Modifier: UpdateToPoweredRail = true");
			if (cb.getSpeedMultipliers().listIterator().hasNext()) {
				ListIterator<SpeedMultiplier> smli = cb.getSpeedMultipliers().listIterator();
				log.debug("Core Config:       Modifier: SpeedMultipliers");
				while(smli.hasNext()) {
					SpeedMultiplier sm = smli.next();
					log.debug("Core Config:                   SpeedMultiplier");
					if      (sm.redstone == RedstoneState.Default)  log.debug("Core Config:                     Redstone: Default");
					else if (sm.redstone == RedstoneState.Enables)  log.debug("Core Config:                     Redstone: Enables");
					else if (sm.redstone == RedstoneState.Disables) log.debug("Core Config:                     Redstone: Disables");
					log.debug("Core Config:                     Multiplier: " + String.valueOf(sm.multiplier));
					if      (sm.direction == CompassDirection.NO_DIRECTION)	log.debug("Core Config:                     Direction: Any");
					else if (sm.direction == CompassDirection.NORTH)		log.debug("Core Config:                     Direction: North");
					else if (sm.direction == CompassDirection.SOUTH)		log.debug("Core Config:                     Direction: South");
					else if (sm.direction == CompassDirection.EAST)			log.debug("Core Config:                     Direction: East");
					else if (sm.direction == CompassDirection.WEST)			log.debug("Core Config:                     Direction: West");
					if      (sm.passenger == PassengerState.Default)  log.debug("Core Config:                     Passenger: Default");
					else if (sm.passenger == PassengerState.Enables)  log.debug("Core Config:                     Passenger: Enables");
					else if (sm.passenger == PassengerState.Disables) log.debug("Core Config:                     Passenger: Disables");
					log.debug("Core Config:                     MinecartTypes: " + (sm.types[0]?"Standard,":"") + (sm.types[0]?"Powered,":"") + (sm.types[0]?"Storage":""));
				}
			}
		}
		//Display the aliases
		log.debug("Core Config: Item Aliases");
		String CurrentKey= "";
		Iterator<Entry<String, List<Item>>> i = ItemAliasList.aliases.entrySet().iterator();
		while(i.hasNext()) {
			Entry<String, List<Item>> e = i.next();
			String key = e.getKey();
			if (!key.equalsIgnoreCase(CurrentKey)) {
				log.debug("Core Config:   Item Alias: " + key);
				CurrentKey = key;
			}
			List<Item> items = e.getValue();

			ListIterator<Item> ali = items.listIterator();
			while(ali.hasNext()) {
				Item ai = ali.next();
				log.debug("Core Config:     Type: " + ai.toString());
			}
		}
	}

	//This will set the configuration values so the configuration file can override them if defined
	private void setDefaultConfiguration() {
		
		//Create the default Configuration values
		//MinecartManiaLogger.getInstance().switchDebugMode(DebugMode.NORMAL);
		MinecartManiaWorld.getConfiguration().put("MinecartsKillMobs",				true);
		MinecartManiaWorld.getConfiguration().put("MinecartsClearRails",			getDefaultConfigurationIntegerValue("MinecartsClearRails"));
		MinecartManiaWorld.getConfiguration().put("KeepMinecartsLoaded",			true);
		MinecartManiaWorld.getConfiguration().put("MinecartsReturnToOwner",			true);
		MinecartManiaWorld.getConfiguration().put("MaximumMinecartSpeedPercent",	getDefaultConfigurationIntegerValue("MaximumMinecartSpeedPercent"));
		MinecartManiaWorld.getConfiguration().put("DefaultMinecartSpeedPercent",	getDefaultConfigurationIntegerValue("DefaultMinecartSpeedPercent"));
		MinecartManiaWorld.getConfiguration().put("Range",							getDefaultConfigurationIntegerValue("Range"));
		MinecartManiaWorld.getConfiguration().put("RangeY",							getDefaultConfigurationIntegerValue("RangeY"));
		MinecartManiaWorld.getConfiguration().put("MaximumRange",					getDefaultConfigurationIntegerValue("MaximumRange"));
		MinecartManiaWorld.getConfiguration().put("StackAllItems",					true);
		MinecartManiaWorld.getConfiguration().put("RemoveDeadMinecarts",			false);
		MinecartManiaWorld.getConfiguration().put("LimitedSignRange",				false);
        MinecartManiaWorld.getConfiguration().put("DisappearOnDisconnect",          true);
        MinecartManiaWorld.getConfiguration().put("UseOldDirections",               true);
		//Create Ores Alias
		ArrayList<Item> values = new ArrayList<Item>();
		values.add(MinecartManiaConfigurationParser.toItem("GOLD_ORE"));
		values.add(MinecartManiaConfigurationParser.toItem("IRON_ORE"));
		values.add(MinecartManiaConfigurationParser.toItem("COAL_ORE"));
		values.add(MinecartManiaConfigurationParser.toItem("LAPIS_ORE"));
		ItemAliasList.aliases.put("Ores", values);
		//Create Ores Alias
		values = new ArrayList<Item>();
		values.add(MinecartManiaConfigurationParser.toItem("260"));
		values.add(MinecartManiaConfigurationParser.toItem("297"));
		values.add(MinecartManiaConfigurationParser.toItem("319"));
		values.add(MinecartManiaConfigurationParser.toItem("320"));
		values.add(MinecartManiaConfigurationParser.toItem("322"));
		values.add(MinecartManiaConfigurationParser.toItem("350"));
		values.add(MinecartManiaConfigurationParser.toItem("354"));
		ItemAliasList.aliases.put("Food", values);
	}
	//This will return the default integer values to be used for configuration
	private int getDefaultConfigurationIntegerValue(String ConfigName) {
		if (ConfigName == "MinecartsClearRails") return (1);
		if (ConfigName == "MaximumMinecartSpeedPercent") return (165);
		if (ConfigName == "DefaultMinecartSpeedPercent") return (100);
		if (ConfigName == "Range") return (2);
		if (ConfigName == "RangeY") return (2);
		if (ConfigName == "MaximumRange") return (25);
		return 0;
	}

	public boolean write(File configFile, Document document) {
		try {
			if (document == null) {
				//we do not have a document to write, so read one from disk.
				JarFile jar = new JarFile(MinecartManiaCore.getMinecartManiaCoreJar());
				JarEntry entry = jar.getJarEntry("MinecartManiaConfiguration.xml");
				InputStream is = jar.getInputStream(entry);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				document = dBuilder.parse(is);
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(configFile);
			transformer.transform(source, result);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private String getNodeValue(Node node) {
		if (node == null) return null;
		return node.getNodeValue();
	}
	
	private CompassDirection parseDirectionState(String str) {
		if (str == null || str.equalsIgnoreCase("any")) return CompassDirection.NO_DIRECTION;
		if (str.equalsIgnoreCase("north")) return CompassDirection.NORTH;
		if (str.equalsIgnoreCase("south")) return CompassDirection.SOUTH;
		if (str.equalsIgnoreCase("east")) return CompassDirection.EAST;
		if (str.equalsIgnoreCase("west")) return CompassDirection.WEST;
		return CompassDirection.NO_DIRECTION;
	}

	private RedstoneState parseRedstoneState(String str) {
		if (str == null || str.equalsIgnoreCase("default")) return RedstoneState.Default;
		if (str.toLowerCase().contains("enable")) return RedstoneState.Enables;
		if (str.toLowerCase().contains("disable")) return RedstoneState.Disables;
		return RedstoneState.Default;
	}
	
	private PassengerState parsePassengerState(String str) {
		if (str == null || str.equalsIgnoreCase("default")) return PassengerState.Default;
		if (str.toLowerCase().contains("enable")) return PassengerState.Enables;
		if (str.toLowerCase().contains("disable")) return PassengerState.Disables;
		return PassengerState.Default;
	}
}
