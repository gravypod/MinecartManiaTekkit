package com.afforess.minecartmaniacore.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

/**
 * An enum of all items accepted by the official server + client
 */
public enum Item {
			AIR(0), STONE(1), GRASS(2), DIRT(3), COBBLESTONE(4), WOOD(5), SAPLING(6, 0), SPRUCE_SAPLING(
			6, 1), BIRCH_SAPLING(6, 2), JUNGLE_SAPLING(6, 3), BEDROCK(7), WATER(
			8), STATIONARY_WATER(9), LAVA(10), STATIONARY_LAVA(11), SAND(12), GRAVEL(
			13), GOLD_ORE(14), IRON_ORE(15), COAL_ORE(16), LOG(17, 0), REDWOOD_LOG(
			17, 1), BIRCH_LOG(17, 2), JUNGLE_LOG(17, 3), LEAVES(18, 0), REDWOOD_LEAVES(
			18, 1), BIRCH_LEAVES(18, 2), JUNGLE_LEAVES(18, 3), SPONGE(19), GLASS(
			20), LAPIS_ORE(21), LAPIS_BLOCK(22), DISPENSER(23), NOTE_BLOCK(25), BED_BLOCK(
			26), POWERED_RAIL(27), DETECTOR_RAIL(28), STICKY_PISTON(29), WEB(30), LONG_GRASS(
			31), DEAD_BUSH(32), PISTON(33), PISTON_EXTENTION(34), WOOL(35, 0), ORANGE_WOOL(
			35, 1), MAGENTA_WOOL(35, 2), LIGHT_BLUE_WOOL(35, 3), YELLOW_WOOL(
			35, 4), LIGHT_GREEN_WOOL(35, 5), PINK_WOOL(35, 6), GRAY_WOOL(35, 7), LIGHT_GRAY_WOOL(
			35, 8), CYAN_WOOL(35, 9), PURPLE_WOOL(35, 10), BLUE_WOOL(35, 11), BROWN_WOOL(
			35, 12), DARK_GREEN_WOOL(35, 13), RED_WOOL(35, 14), BLACK_WOOL(35,
			15), BLOCK_MOVED_BY_PISTON(36), YELLOW_FLOWER(37), RED_ROSE(38), BROWN_MUSHROOM(
			39), RED_MUSHROOM(40), GOLD_BLOCK(41), IRON_BLOCK(42), STONE_DOUBLE_STEP(
			43, 0), SANDSTONE_DOUBLE_STEP(43, 1), WOODEN_DOUBLE_STEP(43, 2), COBBLESTONE_DOUBLE_STEP(
			43, 3), BRICK_DOBLE_STEP(43, 4), STONE_BRICK_DOUBLE_STEP(43, 5), STONE_STEP(
			44, 0), SANDSTONE_STEP(44, 1), WOODEN_STEP(44, 2), COBBLESTONE_STEP(
			44, 3), BRICK_STEP(44, 4), STONE_BRICKE_STEP(44, 5), BRICK(45), TNT(
			46), BOOKSHELF(47), MOSSY_COBBLESTONE(48), OBSIDIAN(49), TORCH(50), FIRE(
			51), MOB_SPAWNER(52), WOOD_STAIRS(53), CHEST(54), REDSTONE_WIRE(55), DIAMOND_ORE(
			56), DIAMOND_BLOCK(57), WORKBENCH(58), CROPS(59), SOIL(60), FURNACE(
			61), BURNING_FURNACE(62), SIGN_POST(63), WOODEN_DOOR(64), LADDER(65), RAILS(
			66), COBBLESTONE_STAIRS(67), WALL_SIGN(68), LEVER(69), STONE_PLATE(
			70), IRON_DOOR_BLOCK(71), WOOD_PLATE(72), REDSTONE_ORE(73), GLOWING_REDSTONE_ORE(
			74), REDSTONE_TORCH_OFF(75), REDSTONE_TORCH_ON(76), STONE_BUTTON(77), SNOW(
			78), ICE(79), SNOW_BLOCK(80), CACTUS(81), CLAY(82), SUGAR_CANE_BLOCK(
			83), JUKEBOX(84), FENCE(85), PUMPKIN(86), NETHERRACK(87), SOUL_SAND(
			88), GLOWSTONE(89), PORTAL(90), JACK_O_LANTERN(91), CAKE_BLOCK(92), DIODE_BLOCK_OFF(
			93), DIODE_BLOCK_ON(94), LOCKED_CHEST(95), TRAP_DOOR(96), HIDDEN_SIVERFISH(
			97), STONE_BRICK_BLOCK(98), HUGE_BROWN_MUSHROOM(99), HUGE_RED_MUSHROOM(
			100), IRON_BARS(101), GLASS_PANE(102), MELON_BLOCK(103), PUMPKIN_STEM(
			104), MELON_STEM(105), VINES(106), FENCE_GATE(107), BRICK_STAIRS(
			108), STONE_BRICK_STAIRS(109), MYCELIUM(110), LILY_PAD(111), NETHER_BRICK(
			112), NETHER_BRICK_FENCE(113), NETHER_BRICK_STAIRS(114), NETHER_WART(
			115), ENCHANTMENT_TABLE(116), BREWING_STAND(117), CAULDRON(118), END_PORTAL(
			119), END_PORTAL_FRAME(120), END_STONE(121), DRAGON_EGG(122), REDSTONE_LAMP_OFF(
			123), REDSTONE_LAMP_ON(124), IRON_SPADE(256), IRON_PICKAXE(257), IRON_AXE(
			258), FLINT_AND_STEEL(259), APPLE(260), BOW(261), ARROW(262), COAL(
			263, 0), CHARCOAL(263, 1), DIAMOND(264), IRON_INGOT(265), GOLD_INGOT(
			266), IRON_SWORD(267), WOOD_SWORD(268), WOOD_SPADE(269), WOOD_PICKAXE(
			270), WOOD_AXE(271), STONE_SWORD(272), STONE_SPADE(273), STONE_PICKAXE(
			274), STONE_AXE(275), DIAMOND_SWORD(276), DIAMOND_SPADE(277), DIAMOND_PICKAXE(
			278), DIAMOND_AXE(279), STICK(280), BOWL(281), MUSHROOM_SOUP(282), GOLD_SWORD(
			283), GOLD_SPADE(284), GOLD_PICKAXE(285), GOLD_AXE(286), STRING(287), FEATHER(
			288), SULPHUR(289), WOOD_HOE(290), STONE_HOE(291), IRON_HOE(292), DIAMOND_HOE(
			293), GOLD_HOE(294), SEEDS(295), WHEAT(296), BREAD(297), LEATHER_HELMET(
			298), LEATHER_CHESTPLATE(299), LEATHER_LEGGINGS(300), LEATHER_BOOTS(
			301), CHAINMAIL_HELMET(302), CHAINMAIL_CHESTPLATE(303), CHAINMAIL_LEGGINGS(
			304), CHAINMAIL_BOOTS(305), IRON_HELMET(306), IRON_CHESTPLATE(307), IRON_LEGGINGS(
			308), IRON_BOOTS(309), DIAMOND_HELMET(310), DIAMOND_CHESTPLATE(311), DIAMOND_LEGGINGS(
			312), DIAMOND_BOOTS(313), GOLD_HELMET(314), GOLD_CHESTPLATE(315), GOLD_LEGGINGS(
			316), GOLD_BOOTS(317), FLINT(318), PORK(319), GRILLED_PORK(320), PAINTING(
			321), GOLDEN_APPLE(322), SIGN(323), WOOD_DOOR(324), BUCKET(325), WATER_BUCKET(
			326), LAVA_BUCKET(327), MINECART(328), SADDLE(329), IRON_DOOR(330), REDSTONE(
			331), SNOW_BALL(332), BOAT(333), LEATHER(334), MILK_BUCKET(335), CLAY_BRICK(
			336), CLAY_BALL(337), SUGAR_CANE(338), PAPER(339), BOOK(340), SLIME_BALL(
			341), STORAGE_MINECART(342), POWERED_MINECART(343), EGG(344), COMPASS(
			345), FISHING_ROD(346), WATCH(347), GLOWSTONE_DUST(348), RAW_FISH(
			349), COOKED_FISH(350), INK_SACK(351, 0), ROSE_RED(351, 1), CACTUS_GREEN(
			351, 2), COCOA_BEANS(351, 3), LAPIS_LAZULI(351, 4), PURPLE_DYE(351,
			5), CYAN_DYE(351, 6), LIGHT_GRAY_DYE(351, 7), GRAY_DYE(351, 8), PINK_DYE(
			351, 9), LIME_DYE(351, 10), DANDELION_YELLOW(351, 11), LIGHT_BLUE_DYE(
			351, 12), MAGENTA_DYE(351, 13), ORANGE_DYE(351, 14), BONEMEAL(351,
			15), BONE(352), SUGAR(353), CAKE(354), BED(355), DIODE(356), COOKIE(
			357), MAP(358), SHEARS(359), MELON(360), PUMPKIN_SEEDS(361), MELON_SEEDS(
			362), RAW_BEEF(363), COOKED_BEEF(364), RAW_CHICKEN(365), COOKED_CHICKEN(
			366), ROTTEN_FLESH(367), ENDER_PEARL(368), BLAZE_ROD(369), GHAST_TEAR(
			370), GOLD_NUGGET(371), NETHER_STALK(372), POTIONS(373), GLASS_BOTTLE(
			374), SPIDER_EYE(375), FERMENTED_SPIDER_EYE(376), BLAZE_POWDER(377), MAGMA_CREAM(
			378), BREWING_STAND_ITEM(379), CAULDRON_ITEM(380), EYE_OF_ENDER(381), SPECKLED_MELON(
			382), MONSTER_EGG(383), EXP_BOTTLE(384), FIREBALL(385), GOLD_RECORD(
			2256), GREEN_RECORD(2257), RECORD_3(2258), RECORD_4(2259), RECORD_5(
			2260), RECORD_6(2261), RECORD_7(2262), RECORD_8(2263), RECORD_9(
			2264), RECORD_10(2265), RECORD_11(2266),

		// 1.2.5
			SANDSTONE(24, 0), CHISELED_SANDSTONE(24, 1), SMOOTH_SANDSTONE(24, 2),

			// 1.3
			OAK_DOUBLE_SLAP(125), OAK_SLAP(126), COCOA_PLANT(127), BOOK_AND_QUILL(386), WRITTEN_BOOK(387),

			// Tekkit
			EEStone(126), EEPedestal(127), EEChest(128), EETorch(129), EEDevice(130), PhilStone(
			27270), CatalystStone(27271), BaseRing(27272), SoulStone(27273), Evertide(
			27274), Volcanite(27275), AttractionRing(27276), IgnitionRing(27277), GrimarchRing(
			27278), HyperkineticLens(27279), SwiftWolfRing(27280), HarvestRing(
			27281), WatchOfTime(27282), AlchemicalCoal(27283), MobiusFuel(27284), DarkMatter(
			27285), CovalenceDust(27286), DarkPickaxe(27287), DarkSpade(27288), DarkHoe(
			27289), DarkSword(27290), DarkAxe(27291), DarkShears(27292), DarkHammer(
			27299), DarkMatterArmor(27293), DarkMatterHelmet(27294), DarkMatterGreaves(
			27295), DarkMatterBoots(27296), EternalDensity(27297), RepairCharm(
			27298), HyperCatalyst(27300), KleinStar(27301), KleinStar2(273020), KleinStar3(
			27303), KleinStar4(27304), KleinStar5(27305), KleinStar6(27335), AlchemyBag(
			27306), RedMatter(27307), RedPickaxe(27308), RedSpade(27309), RedHoe(
			27310), RedSword(27311), RedAxe(27312), RedShears(27313), RedHammer(
			27314), AeternalisFuel(27315), RedKatar(27316), RedMace(27317), ZeroRing(
			27318), RedMatterArmor(27319), RedMatterHelmet(27320), RedMatterGreaves(
			27321), RedMatterBoots(27322), RedMatterArmorP(27323), RedMatterHelmetP(
			27324), RedMatterGreavesP(27325), RedMatterBootsP(27326), MercurialEye(
			27327), ArcaneRing(27328), DiviningRod(27329), BodyStone(27332), LifeStone(
			27333), MindStone(27334), TransTablet(27336), VoidRing(27337), AlchemyTome(
			27338);

	private final int id;
	private final short data;
	private boolean hasData;
	private static final Map<ArrayList<Integer>, Item> lookupId = new HashMap<ArrayList<Integer>, Item>();
	private static final Map<String, Item> lookupName = new HashMap<String, Item>();

	Item(final int id) {
		this(id, 0);
		hasData = false;
	}

	Item(final int id, final int data) {
		this.id = id;
		this.data = (short) data;
		hasData = true;
	}

	/**
	 * Gets the item ID or block ID of this Item
	 * 
	 * @return ID of this Item
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the data for this Item
	 * 
	 * @return Data for this Item
	 */
	public int getData() {
		return data;
	}

	/**
	 * Checks if this Item is a placable block
	 * 
	 * @return true if this Item is a block
	 */
	public boolean isBlock() {
		return id < 256;
	}

	/**
	 * Checks to see if this Item has more than one Item using the same id
	 * 
	 * @return true if this Item has more than one Item using the same id
	 */
	public boolean hasData() {
		return hasData;
	}

	/**
	 * Finds the bukkit material associated with this item id, and returns it
	 * 
	 * @return the material if found, or null
	 */
	public Material toMaterial() {
		return Material.getMaterial(id);
	}

	public ItemStack toItemStack() {
		ItemStack item = new ItemStack(id, 1, (short) 0);
		if (hasData())
			item.setDurability(data);
		return item;
	}

	public boolean equals(Item i) {
		return i != null && i.getId() == id && data == i.getData();
	}

	public boolean equals(Material m) {
		return m != null && m.getId() == id;
	}

	public boolean equals(int id, short data) {
		return id == this.id && data == this.data;
	}

	public boolean equals(int id) {
		return id == this.id;
	}

	public boolean equals(AbstractItem item) {
		return equals(item.type());
	}

	/**
	 * Attempts to get the Item with the given ID and data value
	 * 
	 * @param id
	 *            ID of the Item to get
	 * @param the
	 *            data value of the Item to get
	 * @return Item if found, or null
	 */
	public static Item getItem(final int id, int data) {
		ArrayList<Integer> a = new ArrayList<Integer>(2);
		a.add(id);
		a.add(data);
		return lookupId.get(a);
	}

	/**
	 * Attempts to get the list of items with the given id
	 * 
	 * @param id
	 *            ID of the list of Items to get
	 * @return Items if found, or empty arraylist
	 */
	public static ArrayList<Item> getItem(final int id) {
		ArrayList<Item> list = new ArrayList<Item>();
		for (int i = 0; i < 16; i++) {
			Item temp = getItem(id, i);
			if (temp != null) {
				list.add(temp);
			}
		}
		return list;
	}

	/**
	 * Attempts to get the item associated with this block
	 * 
	 * @param block
	 *            to get the Item of
	 * @return Item if found
	 */
	public static Item getItem(Block block) {
		ArrayList<Item> list = getItem(block.getTypeId());
		if (list.size() == 1) {
			return list.get(0);
		}
		return getItem(block.getTypeId(), block.getData());
	}

	/**
	 * Attempts to get the item associated with this ItemStack
	 * 
	 * @param item
	 *            to get the Item of
	 * @return Item if found
	 */
	public static Item getItem(ItemStack item) {
		Item i;
		ArrayList<Item> list = getItem(item.getTypeId());
		if (list.size() == 1) {
			i = list.get(0);
		} else {
			i = getItem(item.getTypeId(), item.getDurability());
		}
		return i;
	}

	/**
	 * Attempts to get the Item with the given name. This is a normal lookup,
	 * names must be the precise name they are given in the enum.
	 * 
	 * @param name
	 *            Name of the Item to get
	 * @return Item if found, or null
	 */
	public static Item getItem(final String name) {
		return lookupName.get(name);
	}

	/**
	 * Finds the item associated with the given bukkit material, and returns it
	 * 
	 * @return the Item if found, or null
	 */
	public static Item materialToItem(Material m) {
		ArrayList<Integer> a = new ArrayList<Integer>(2);
		a.add(m.getId());
		a.add(0);
		return lookupId.get(a);
	}

	static {
		for (Item i : values()) {
			ArrayList<Integer> a = new ArrayList<Integer>(2);
			a.add(i.getId());
			a.add(i.getData());
			lookupId.put(a, i);
			lookupName.put(i.name(), i);
		}
	}
}