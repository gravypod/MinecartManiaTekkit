package com.afforess.minecartmaniacore.minecart;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import com.afforess.minecartmaniacore.MinecartManiaCore;
import com.afforess.minecartmaniacore.debug.MinecartManiaLogger;
import com.afforess.minecartmaniacore.utils.DirectionUtils.CompassDirection;
import com.afforess.minecartmaniacore.world.Item;
import com.afforess.minecartmaniacore.world.MinecartManiaWorld;
import com.avaje.ebean.validation.NotNull;
@Entity()
@Table(name="MinecartManiaMinecartDataTable")
public class MinecartManiaMinecartDataTable {
	private transient static HashMap<String, MinecartManiaMinecartDataTable> cache = new HashMap<String, MinecartManiaMinecartDataTable>();
	@NotNull
	protected double previousX;
	@NotNull
	protected double previousY;
	@NotNull
	protected double previousZ;
	@NotNull
	protected double previousMotionX;
	@NotNull
	protected double previousMotionY;
	@NotNull
	protected double previousMotionZ;
	@NotNull
	protected CompassDirection previousFacingDir;
	@NotNull
	protected boolean wasMovingLastTick;
	@NotNull
	protected String owner;
	@NotNull
	protected int myrange;
	@NotNull
	protected int rangeY;
	@NotNull
	protected boolean dead;
	@NotNull
	protected double X;
	@NotNull
	protected double Y;
	@NotNull
	protected double Z;
	@NotNull
	protected double motionX;
	@NotNull
	protected double motionY;
	@NotNull
	protected double motionZ;
	@NotNull
	protected int typeId;
	@NotNull
	protected String world;
	@NotNull
	protected String player;
	@Id
	protected int oldId;
	protected transient ConcurrentHashMap<String, Object> data = null;
	
	public MinecartManiaMinecartDataTable() {
		
	}
	
	public MinecartManiaMinecartDataTable(MinecartManiaMinecart minecart, String player) {
		this.previousX = minecart.previousLocation.getX();
		this.previousY = minecart.previousLocation.getY();
		this.previousZ = minecart.previousLocation.getZ();
		this.previousMotionX = minecart.previousMotion.getX();
		this.previousMotionY = minecart.previousMotion.getY();
		this.previousMotionZ = minecart.previousMotion.getZ();
		this.previousFacingDir = minecart.previousFacingDir;
		this.wasMovingLastTick = minecart.wasMovingLastTick;
		this.owner = minecart.owner.getOwner();
		this.myrange = minecart.range;
		this.rangeY = minecart.rangeY;
		this.dead = minecart.dead;
		this.oldId = minecart.minecart.getEntityId();
		this.data = minecart.data;
		this.X = minecart.getLocation().getX();
		this.Y = minecart.getLocation().getY();
		this.Z = minecart.getLocation().getZ();
		this.motionX = minecart.getMotionX();
		this.motionY = minecart.getMotionY();
		this.motionZ = minecart.getMotionZ();
		this.player = player;
		this.typeId = minecart.getType().getId();
		this.world = minecart.getWorld().getName();
	}
	
	public static MinecartManiaMinecartDataTable getDataTable(String player) {
		if (cache.containsKey(player)) {
			return cache.get(player);
		}
		try {
			MinecartManiaMinecartDataTable data = null;
			List<MinecartManiaMinecartDataTable> list = MinecartManiaCore.getInstance().getDatabase().find(MinecartManiaMinecartDataTable.class).where().ieq("player", player).findList();
			if (list.size() > 0) {
				data = list.get(0);
				//handle issues with the db gracefully
				if (list.size() > 1) {
					for (int i = 1; i < list.size(); i++) {
						MinecartManiaCore.getInstance().getDatabase().delete(list.get(i));
					}
				}
			}
			cache.put(player, data);
			return data;
		}
		catch (Exception e) {
			MinecartManiaLogger.getInstance().log("Failed to load the minecart from memory when " + player + " reconnected");
			MinecartManiaLogger.getInstance().logCore(e.getMessage(), false);
			return null;
		}
	}
	
	public static void delete(MinecartManiaMinecartDataTable data) {
		MinecartManiaCore.getInstance().getDatabase().delete(data);
		cache.remove(data.getPlayer());
	}
	
	public static void save(MinecartManiaMinecartDataTable data) {
		MinecartManiaCore.getInstance().getDatabase().save(data);
		cache.put(data.getPlayer(), data);
	}
	
	public MinecartManiaMinecart toMinecartManiaMinecart() {
		MinecartManiaMinecart minecart = MinecartManiaWorld.spawnMinecart(getLocation(), Item.getItem(typeId).get(0), owner);
		minecart.previousFacingDir = this.previousFacingDir;
		minecart.previousLocation = this.getPreviousLocation();
		minecart.previousMotion = this.getPreviousMotion();
		minecart.minecart.setVelocity(getMotion());
		minecart.range = this.myrange;
		minecart.rangeY = this.rangeY;
		minecart.wasMovingLastTick = this.wasMovingLastTick;
		minecart.dead = this.dead;
		if (this.data != null) {
			minecart.data = this.data;
		}
		return minecart;
	}
	
	public double getPreviousX() {
		return previousX;
	}

	public void setPreviousX(double previousX) {
		this.previousX = previousX;
	}

	public double getPreviousY() {
		return previousY;
	}

	public void setPreviousY(double previousY) {
		this.previousY = previousY;
	}

	public double getPreviousZ() {
		return previousZ;
	}

	public void setPreviousZ(double previousZ) {
		this.previousZ = previousZ;
	}

	public double getPreviousMotionX() {
		return previousMotionX;
	}

	public void setPreviousMotionX(double previousMotionX) {
		this.previousMotionX = previousMotionX;
	}

	public double getPreviousMotionY() {
		return previousMotionY;
	}

	public void setPreviousMotionY(double previousMotionY) {
		this.previousMotionY = previousMotionY;
	}

	public double getPreviousMotionZ() {
		return previousMotionZ;
	}

	public void setPreviousMotionZ(double previousMotionZ) {
		this.previousMotionZ = previousMotionZ;
	}

	public double getX() {
		return X;
	}

	public void setX(double x) {
		X = x;
	}

	public double getY() {
		return Y;
	}

	public void setY(double y) {
		Y = y;
	}

	public double getZ() {
		return Z;
	}

	public void setZ(double z) {
		Z = z;
	}

	public double getMotionX() {
		return motionX;
	}

	public void setMotionX(double motionX) {
		this.motionX = motionX;
	}

	public double getMotionY() {
		return motionY;
	}

	public void setMotionY(double motionY) {
		this.motionY = motionY;
	}

	public double getMotionZ() {
		return motionZ;
	}

	public void setMotionZ(double motionZ) {
		this.motionZ = motionZ;
	}

	public String getWorld() {
		return world;
	}

	public void setWorld(String world) {
		this.world = world;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}
	
	public Vector getPreviousLocation() {
		return new Vector(previousX, previousY, previousZ);
	}

	public Vector getPreviousMotion() {
		return new Vector(previousMotionX, previousMotionY, previousMotionZ);
	}
	
	public Vector getMotion() {
		return new Vector(motionX, motionY, motionZ);
	}
	
	public Location getLocation() {
		return new Location(Bukkit.getServer().getWorld(world), X, Y, Z);
	}

	public CompassDirection getPreviousFacingDir() {
		return previousFacingDir;
	}

	public void setPreviousFacingDir(CompassDirection previousFacingDir) {
		this.previousFacingDir = previousFacingDir;
	}

	public boolean isWasMovingLastTick() {
		return wasMovingLastTick;
	}

	public void setWasMovingLastTick(boolean wasMovingLastTick) {
		this.wasMovingLastTick = wasMovingLastTick;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getRangeY() {
		return rangeY;
	}

	public void setRangeY(int rangeY) {
		this.rangeY = rangeY;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public int getOldId() {
		return oldId;
	}

	public void setOldId(int oldId) {
		this.oldId = oldId;
	}
	
	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int type) {
		this.typeId = type;
	}

	public void setMyrange(int myrange) {
		this.myrange = myrange;
	}

	public int getMyrange() {
		return myrange;
	}

	

}
