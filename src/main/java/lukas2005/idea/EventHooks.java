package lukas2005.idea;

import net.minecraft.block.Block;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHooks {
	
	@SubscribeEvent
	public void onContainerOpen(PlayerContainerEvent e) {
		
		if (e.getContainer() instanceof Container) {
			
			e.setCanceled(true);
			e.getEntityPlayer().openGui(IDEAMod.INSTANCE, 1, e.getEntityPlayer().getEntityWorld(), e.getEntityPlayer().getPosition().getX(), e.getEntityPlayer().getPosition().getY(), e.getEntityPlayer().getPosition().getZ());
			
		}
		
	}
	
	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent e) {
		if (Reference.CONFIG_USE_WEIGHT) {
			if (e.getItemStack().getItem() instanceof ItemBlock) {
			
				e.getToolTip().add("Weight: "+CustomValues.BLOCKWEIGHT.get(Block.getBlockFromItem(e.getItemStack().getItem())));
				
			} else {
				
				e.getToolTip().add("Weight: "+CustomValues.BLOCKWEIGHT.get(e.getItemStack().getItem()));
				
			}
		}
	}
	
}
