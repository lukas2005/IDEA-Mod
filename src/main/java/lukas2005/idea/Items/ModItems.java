package lukas2005.idea.Items;

import java.util.HashMap;

import lukas2005.idea.Logger;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
public class ModItems {
	
	public static HashMap<String,Item> ITEMS = new HashMap<String,Item>();
	
	public static void main() {
		registerItems();
	}
	
	private static void registerItems() {
		for (Item item : ITEMS.values()) {
		
			Logger.info("Registering item: " + item.getRegistryName());
			GameRegistry.register(item);
			
		}
	}
	
	public static void registerRenders() {
		for (Item item : ITEMS.values()) {
			
			Logger.info("Registering render for item: " + item.getRegistryName());
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
			
		}
	}
	
}
