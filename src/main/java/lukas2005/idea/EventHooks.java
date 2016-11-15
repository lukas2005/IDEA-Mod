package lukas2005.idea;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHooks {
	
	@SubscribeEvent
	public static void onItemTooltip(ItemTooltipEvent e) {
		
		if (e.getItemStack().getItem() instanceof ItemBlock) {
		
			e.getToolTip().add("Weight: "+CustomValues.BLOCKWEIGHT.get(Block.getBlockFromItem(e.getItemStack().getItem())));
			
		} else if (e.getItemStack().getItem() instanceof Item) {
			
			e.getToolTip().add("Weight: "+CustomValues.BLOCKWEIGHT.get(e.getItemStack().getItem()));
			
		}
		
		
		
	}
	
}
