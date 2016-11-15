package lukas2005.idea;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class CustomValues {

	public static HashMap<Item,Float> ITEMWEIGHT = new HashMap<Item,Float>();
	public static HashMap<Block,Float> BLOCKWEIGHT = new HashMap<Block,Float>();
	
	public static void initValues(Configuration config) {
		
		config.addCustomCategoryComment("BLOCKS WEIGHT", "Here you can change how much ceratin blocks weight");
		
		for (Block block : Block.REGISTRY) {
			
			Property pr = config.get("BLOCKS WEIGHT", block.getRegistryName().toString(), "1.0");
			
			BLOCKWEIGHT.put(block, Float.parseFloat(pr.getString()));
			
			Logger.info("Weight of " + block.getRegistryName().toString()+"="+Float.parseFloat(pr.getString()));
			
			pr.setComment("Weight of " + block.getRegistryName().toString());
			
		}
		
		config.addCustomCategoryComment("ITEMS WEIGHT", "Here you can change how much ceratin items weight");
		
		for (Item item : Item.REGISTRY) {
			
			Property pr = config.get("ITEMS WEIGHT", item.getRegistryName().toString(), "1.0");
			
			ITEMWEIGHT.put(item, Float.parseFloat(pr.getString()));
			
			Logger.info("Weight of "+item.getRegistryName().toString()+"="+Float.parseFloat(pr.getString()));
			
			pr.setComment("Weight of " + item.getRegistryName().toString());
			
		}
		config.save();
	}
	
}
