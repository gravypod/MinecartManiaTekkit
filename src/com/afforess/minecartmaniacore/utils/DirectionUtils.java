package com.afforess.minecartmaniacore.utils;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;

import com.afforess.minecartmaniacore.config.MinecartManiaConfiguration;
import com.afforess.minecartmaniacore.world.MinecartManiaWorld;

public abstract class DirectionUtils {
	 public enum CompassDirection {
			NO_DIRECTION(-1),
			NORTH(0),
			NORTH_EAST(1),
			EAST(2),
			SOUTH_EAST(3),
			SOUTH(4),
			SOUTH_WEST(5),
			WEST(6),
			NORTH_WEST(7)
			;
			private int id;
			private static Map<Integer, CompassDirection> map;

			private CompassDirection(int id){
				this.id = id;
				add( id, this );
			}

			private static void add( int type, CompassDirection name ) {
				if (map == null) {
					map = new HashMap<Integer, CompassDirection>();
				}

				map.put(type, name);
			}

			public int getType() {
				return id;
			}

			public static CompassDirection fromId(final int type) {
				return map.get(type);
			}
			
			public String toString() {
				if (this.equals(CompassDirection.NORTH)) {
					return "North";
				}
				if (this.equals(CompassDirection.NORTH_EAST)) {
					return "North-East";
				}
				if (this.equals(CompassDirection.EAST)) {
					return "East";
				}
				if (this.equals(CompassDirection.SOUTH_EAST)) {
					return "South-East";
				}
				if (this.equals(CompassDirection.SOUTH)) {
					return "South";
				}
				if (this.equals(CompassDirection.SOUTH_WEST)) {
					return "South-West";
				}
				if (this.equals(CompassDirection.WEST)) {
					return "West";
				}
				if (this.equals(CompassDirection.NORTH_WEST)) {
					return "North-West";
				}
				return "No Direction";
			}
		}
	 
	 public static boolean isEqualOrNoDirection(CompassDirection e1, CompassDirection e2)
	 {
		if (e1 == CompassDirection.NO_DIRECTION) return true;
		if (e2 == CompassDirection.NO_DIRECTION) return true;
		if (e1 == e2) return true;
		return false;
	 }
	 
	 public static boolean isOrthogonalDirection(CompassDirection dir) {
		 return dir == CompassDirection.NORTH ||
		 dir == CompassDirection.SOUTH ||
		 dir == CompassDirection.EAST ||
		 dir == CompassDirection.WEST;
	 }
	 
	 public static CompassDirection getLeftDirection(CompassDirection efacingDir) {
		 if (efacingDir == CompassDirection.NORTH) {
			 return CompassDirection.WEST;
		 }
		 if (efacingDir == CompassDirection.EAST) {
			 return CompassDirection.NORTH;
		 }
		 if (efacingDir == CompassDirection.SOUTH) {
			 return CompassDirection.EAST;
		 }
		 if (efacingDir == CompassDirection.WEST) {
			 return CompassDirection.SOUTH;
		 }
		 return CompassDirection.NO_DIRECTION;
	 }
	 
	 public static CompassDirection getRightDirection(CompassDirection efacingDir) {
		 if (efacingDir == CompassDirection.NORTH) {
			 return CompassDirection.EAST;
		 }
		 if (efacingDir == CompassDirection.EAST) {
			 return CompassDirection.SOUTH;
		 }
		 if (efacingDir == CompassDirection.SOUTH) {
			 return CompassDirection.WEST;
		 }
		 if (efacingDir == CompassDirection.WEST) {
			 return CompassDirection.NORTH;
		 }
		 return CompassDirection.NO_DIRECTION;
	 }
	 
	 public static Block getBlockTypeAhead(World w, CompassDirection efacingDir, int x, int y, int z) {
	     if(MinecartManiaConfiguration.useOldDirections())
	     {
			if (efacingDir == CompassDirection.NORTH) return MinecartManiaWorld.getBlockAt(w, x-1, y, z);
			if (efacingDir == CompassDirection.EAST) return MinecartManiaWorld.getBlockAt(w, x, y, z-1);
			if (efacingDir == CompassDirection.SOUTH) return MinecartManiaWorld.getBlockAt(w, x+1, y, z);
			if (efacingDir == CompassDirection.WEST) return MinecartManiaWorld.getBlockAt(w, x, y, z+1);
	     }
	     else
	     {
	         if (efacingDir == CompassDirection.NORTH)
	             return MinecartManiaWorld.getBlockAt(w, x, y, z - 1);
	         if (efacingDir == CompassDirection.EAST)
	             return MinecartManiaWorld.getBlockAt(w, x + 1, y, z);
	         if (efacingDir == CompassDirection.SOUTH)
	             return MinecartManiaWorld.getBlockAt(w, x, y, z + 1);
	         if (efacingDir == CompassDirection.WEST)
	             return MinecartManiaWorld.getBlockAt(w, x - 1, y, z);
	     }
			return null;
		}
	 
	 public static int getMinetrackRailDataForDirection(CompassDirection eOverrideDir, CompassDirection eFacingDir)
	 {
	     if(MinecartManiaConfiguration.useOldDirections())
	     {
    		 if (eFacingDir == CompassDirection.NORTH) {
    			 if (eOverrideDir == CompassDirection.EAST) {
    				 return 9;
    			 }
    			 if (eOverrideDir == CompassDirection.NORTH) {
    				 return 1;
    			 }
    			 if (eOverrideDir == CompassDirection.WEST) {
    				 return 6;
    			 }
    		 }
    		 if (eFacingDir == CompassDirection.EAST) {
    			 if (eOverrideDir == CompassDirection.EAST) {
    				 return 0;
    			 }
    			 if (eOverrideDir == CompassDirection.NORTH) {
    				 return 7;
    			 }
    			 if (eOverrideDir == CompassDirection.SOUTH) {
    				 return 6;
    			 }
    		 }
    		 if (eFacingDir == CompassDirection.WEST) {
    			 if (eOverrideDir == CompassDirection.WEST) {
    				 return 0;
    			 }
    			 if (eOverrideDir == CompassDirection.NORTH) {
    				 return 8;
    			 }
    			 if (eOverrideDir == CompassDirection.SOUTH) {
    				 return 9;
    			 }
    		 }
    		 if (eFacingDir == CompassDirection.SOUTH) {
    			 if (eOverrideDir == CompassDirection.WEST) {
    				 return 7;
    			 }
    			 if (eOverrideDir == CompassDirection.EAST) {
    				 return 8;
    			 }
    			 if (eOverrideDir == CompassDirection.SOUTH) {
    				 return 1;
    			 }
    		 }
	     }
	     else
	     {
	         if (eFacingDir == CompassDirection.NORTH) {
	             if (eOverrideDir == CompassDirection.EAST)
	                 return 6;
	             if (eOverrideDir == CompassDirection.NORTH)
	                 return 0;
	             if (eOverrideDir == CompassDirection.WEST)
	                 return 7;
	         }
	         if (eFacingDir == CompassDirection.EAST) {
	             if (eOverrideDir == CompassDirection.EAST)
	                 return 1;
	             if (eOverrideDir == CompassDirection.NORTH)
	                 return 8;
	             if (eOverrideDir == CompassDirection.SOUTH)
	                 return 7;
	         }
	         if (eFacingDir == CompassDirection.WEST) {
	             if (eOverrideDir == CompassDirection.WEST)
	                 return 1;
	             if (eOverrideDir == CompassDirection.NORTH)
	                 return 9;
	             if (eOverrideDir == CompassDirection.SOUTH)
	                 return 6;
	         }
	         if (eFacingDir == CompassDirection.SOUTH) {
	             if (eOverrideDir == CompassDirection.WEST)
	                 return 8;
	             if (eOverrideDir == CompassDirection.EAST)
	                 return 9;
	             if (eOverrideDir == CompassDirection.SOUTH)
	                 return 0;
	         }
	     }
		 return -1;
	 }

	public static CompassDirection getOppositeDirection(CompassDirection direction) {
		int val = direction.getType();
		if (val < 4)
			val += 4;
		else
			val -= 4;
		return CompassDirection.fromId(val);
	}
	
	private static boolean isFacingNorth(double degrees, double leeway) {
		return ((0 <= degrees) && (degrees < 45+leeway)) || ((315-leeway <= degrees) && (degrees <= 360));
	}
	private static boolean isFacingEast(double degrees, double leeway) {
		return (45-leeway <= degrees) && (degrees < 135+leeway);
	}
	private static boolean isFacingSouth(double degrees, double leeway) {
		return (135-leeway <= degrees) && (degrees < 225+leeway);
	}
	private static boolean isFacingWest(double degrees, double leeway) {
		return (225-leeway <= degrees) && (degrees < 315+leeway);
	}
	
	public static CompassDirection getDirectionFromMinecartRotation(double degrees) {
		
		while (degrees < 0D) {
			degrees += 360D;
		}
		while (degrees > 360D) {
			degrees -= 360D;
		}
		
		CompassDirection direction = getDirectionFromRotation(degrees);
		
		double leeway = 15;
		if (direction.equals(CompassDirection.NORTH) || direction.equals(CompassDirection.SOUTH)) {
			if (isFacingEast(degrees, leeway)) {
				return CompassDirection.EAST;
			}
			if (isFacingWest(degrees, leeway)) {
				return CompassDirection.WEST;
			}
		}
		else if (direction.equals(CompassDirection.EAST) || direction.equals(CompassDirection.WEST)) {
			if (isFacingNorth(degrees, leeway)) {
				return CompassDirection.NORTH;
			}
			if (isFacingSouth(degrees, leeway)) {
				return CompassDirection.SOUTH;
			}
		}
		
		return direction;
	}
	
	public static CompassDirection getDirectionFromRotation(double degrees) {
	    double corrDegrees = degrees;
    	if(!MinecartManiaConfiguration.useOldDirections())
    	{
            // Correct for South being 180 degrees, not 0 degrees
            corrDegrees = degrees + 180D;            
    	}
    	
        while (corrDegrees < 0D) {
            corrDegrees += 360D;
        }
        while (corrDegrees > 360D) {
            corrDegrees -= 360D;
        }
        
        degrees = corrDegrees;
        if (isFacingNorth(degrees, 0)) {
            return CompassDirection.NORTH;
        }
        if (isFacingEast(degrees, 0)) {
            return CompassDirection.EAST;
        }
        if (isFacingSouth(degrees, 0)) {
            return CompassDirection.SOUTH;
        }
        if (isFacingWest(degrees, 0)) {
            return CompassDirection.WEST;
        }
		
		return CompassDirection.NO_DIRECTION;
	}
	
	public static CompassDirection getDirectionFromString(String dir, CompassDirection facingDir) {
		
		if (dir.indexOf("W") > -1 || dir.toLowerCase().indexOf("west") > -1) {
			return CompassDirection.WEST;
		}
		if (dir.indexOf("E") > -1 || dir.toLowerCase().indexOf("east") > -1) {
			return CompassDirection.EAST;
		}
		if (dir.indexOf("S") > -1 || dir.toLowerCase().indexOf("south") > -1) {
			return CompassDirection.SOUTH;
		}
		if (dir.indexOf("N") > -1 || dir.toLowerCase().indexOf("north") > -1) {
			return CompassDirection.NORTH;
		}
		if (!facingDir.equals(CompassDirection.NO_DIRECTION)) {
			if (dir.indexOf("STR") > -1 || dir.toLowerCase().indexOf("straight") > -1) {
				return facingDir;
			}
			//TODO discuss
			if(MinecartManiaConfiguration.useOldDirections())
			{
    			if (dir.indexOf("L") > -1 || dir.toLowerCase().indexOf("left") > -1) {
    				return getRightDirection(facingDir);
    			}
    			if (dir.indexOf("R") > -1 || dir.toLowerCase().indexOf("right") > -1) {
    				return getLeftDirection(facingDir);
    			}
			}
			else
			{
			    if ((dir.indexOf("L") > -1) || (dir.toLowerCase().indexOf("left") > -1))
	                return getLeftDirection(facingDir);
	            if ((dir.indexOf("R") > -1) || (dir.toLowerCase().indexOf("right") > -1))
	                return getRightDirection(facingDir);
			}
		}
		return CompassDirection.NO_DIRECTION;
	}
	
	public static CompassDirection getSignFacingDirection(Sign sign) {
		int data = MinecartManiaWorld.getBlockData(sign.getWorld(), sign.getX(), sign.getY(), sign.getZ());
		Block block = sign.getBlock();
		if(MinecartManiaConfiguration.useOldDirections())
		{
    		if (block.getType().equals(Material.SIGN_POST)) {
    			if (data == 0x0) return DirectionUtils.CompassDirection.WEST;
    			if (data == 0x1 || data == 0x2 || data == 0x3) return DirectionUtils.CompassDirection.NORTH_WEST;
    			if (data == 0x4) return DirectionUtils.CompassDirection.NORTH;
    			if (data == 0x5 || data == 0x6 || data == 0x7) return DirectionUtils.CompassDirection.NORTH_EAST;
    			if (data == 0x8) return DirectionUtils.CompassDirection.EAST;
    			if (data == 0x9 || data == 0xA || data == 0xB) return DirectionUtils.CompassDirection.SOUTH_EAST;
    			if (data == 0xC) return DirectionUtils.CompassDirection.SOUTH;
    			if (data == 0xD || data == 0xE || data == 0xF) return DirectionUtils.CompassDirection.SOUTH_WEST;
    			return DirectionUtils.CompassDirection.NO_DIRECTION;
    		}
    		else {
    			if (data == 0x3) return DirectionUtils.CompassDirection.WEST;
    			if (data == 0x4) return DirectionUtils.CompassDirection.NORTH;
    			if (data == 0x2) return DirectionUtils.CompassDirection.EAST;
    			if (data == 0x5) return DirectionUtils.CompassDirection.SOUTH;
                return DirectionUtils.CompassDirection.NO_DIRECTION;    			
    		}
		}
		else
		{
		    if (block.getType().equals(Material.SIGN_POST)) {
	            if (data == 0x0)
	                return DirectionUtils.CompassDirection.SOUTH;
	            if ((data == 0x1) || (data == 0x2) || (data == 0x3))
	                return DirectionUtils.CompassDirection.SOUTH_WEST;
	            if (data == 0x4)
	                return DirectionUtils.CompassDirection.WEST;
	            if ((data == 0x5) || (data == 0x6) || (data == 0x7))
	                return DirectionUtils.CompassDirection.NORTH_WEST;
	            if (data == 0x8)
	                return DirectionUtils.CompassDirection.NORTH;
	            if ((data == 0x9) || (data == 0xA) || (data == 0xB))
	                return DirectionUtils.CompassDirection.NORTH_EAST;
	            if (data == 0xC)
	                return DirectionUtils.CompassDirection.EAST;
	            if ((data == 0xD) || (data == 0xE) || (data == 0xF))
	                return DirectionUtils.CompassDirection.SOUTH_EAST;
	            return DirectionUtils.CompassDirection.NO_DIRECTION;
	        } else {
	            if (data == 0x3)
	                return DirectionUtils.CompassDirection.SOUTH;
	            if (data == 0x4)
	                return DirectionUtils.CompassDirection.WEST;
	            if (data == 0x2)
	                return DirectionUtils.CompassDirection.NORTH;
	            if (data == 0x5)
	                return DirectionUtils.CompassDirection.EAST;
                
	            return DirectionUtils.CompassDirection.NO_DIRECTION;                
	        }               
		}
	}
	
	public static BlockFace CompassDirectionToBlockFace(CompassDirection dir) {
		if (dir.equals(CompassDirection.EAST))
			return BlockFace.EAST;
		if (dir.equals(CompassDirection.WEST))
			return BlockFace.WEST;
		if (dir.equals(CompassDirection.NORTH))
			return BlockFace.NORTH;
		if (dir.equals(CompassDirection.SOUTH))
			return BlockFace.SOUTH;
		if (dir.equals(CompassDirection.NORTH_EAST))
			return BlockFace.NORTH_EAST;
		if (dir.equals(CompassDirection.NORTH_WEST))
			return BlockFace.NORTH_WEST;
		if (dir.equals(CompassDirection.SOUTH_EAST))
			return BlockFace.SOUTH_EAST;
		if (dir.equals(CompassDirection.SOUTH_WEST))
			return BlockFace.SOUTH_WEST;
		
		return BlockFace.SELF;
	}
}
