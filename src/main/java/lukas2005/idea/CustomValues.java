package lukas2005.idea;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmorStand;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEmptyMap;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemShears;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class CustomValues {

	public static HashMap<Item, Double> ITEMWEIGHT = new HashMap<Item,Double>();
	public static HashMap<Block, Double> BLOCKWEIGHT = new HashMap<Block,Double>();
	
	public static void initValues(Configuration config) {
		
		config.addCustomCategoryComment("BLOCKS WEIGHT", "Here you can change how much ceratin blocks weight");
		
		if (Reference.CONFIG_USE_WEIGHT) {
			for (Block block : Block.REGISTRY) {
				
				Double wh = 1.0;
				
				if (block instanceof BlockLeaves || block instanceof BlockFlower || block instanceof BlockMushroom || block instanceof BlockBush) {
					
					wh = 0.3;
					
				} else if (block instanceof BlockBed || block instanceof BlockAir || block instanceof BlockFire) {
					
					wh = 0.0;

				} else if (block instanceof BlockLog) {
					
					wh = 4.0;
					
				} else if (block instanceof BlockPlanks) {
					
					wh = 1.0;
					
				} else if (BLOCKWEIGHT.containsKey(block)) {
					
					wh = BLOCKWEIGHT.get(block);
					
				}
				
				
				Property pr = config.get("BLOCKS WEIGHT", block.getRegistryName().toString(), wh);
					
				BLOCKWEIGHT.put(block, pr.getDouble());
					
				Logger.info("Weight of " + block.getRegistryName().toString()+"="+pr.getDouble());
					
				pr.setComment("Weight of " + block.getRegistryName().toString());
			}
		
			
			config.addCustomCategoryComment("ITEMS WEIGHT", "Here you can change how much ceratin items weight");
			
			for (Item item : Item.REGISTRY) {
				
				double wh = 1.0;
				
				if (item instanceof ItemBlock) {
					
					wh = BLOCKWEIGHT.get(Block.getBlockFromItem(item));
					
				} else if (item instanceof ItemBed || item instanceof ItemArmorStand) {
					
					wh = 5.5;
					
				} else if (item instanceof ItemArmor) {
					
					ItemArmor tmp = (ItemArmor) item;
					
					switch(tmp.getArmorMaterial()) {
						case CHAIN:
							wh = 3.5;
							break;
						case DIAMOND:
							wh = 4.0;
							break;
						case GOLD:
							wh = 7.5;
							break;
						case IRON:
							wh = 3.0;
							break;
						case LEATHER:
							wh = 0.5;
							break;
						default:
							break;
					}
					
					switch(tmp.getEquipmentSlot()) {
						case FEET:
							wh = wh/3;
							break;
						case HEAD:
							wh = wh/2;
							break;
						case LEGS:
							wh = wh/1.5;
							break;
						default:
							break;
					}
					
					wh = Math.ceil(wh);
					
				} else if (item == Items.STICK || item instanceof ItemMap || item instanceof ItemEmptyMap || item instanceof ItemPotion || item instanceof ItemEnchantedBook) {
					
					wh = 0.5;
					
				} else if (item instanceof ItemBow || item instanceof ItemFishingRod || item instanceof ItemShears || item instanceof ItemShears || item instanceof ItemShears) {
					
					wh = 2.5;
				} else if (ITEMWEIGHT.containsKey(item)) {
					
					wh = ITEMWEIGHT.get(item);
					
				}
				
				Property pr = config.get("ITEMS WEIGHT", item.getRegistryName().toString(), wh);
				
				ITEMWEIGHT.put(item, pr.getDouble());
				
				Logger.info("Weight of "+item.getRegistryName().toString()+"="+pr.getDouble());
				
				pr.setComment("Weight of " + item.getRegistryName().toString());
			}
		}
		
		config.save();
	}
	
}
