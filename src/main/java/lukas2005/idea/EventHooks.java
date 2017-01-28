package lukas2005.idea;

import lukas2005.idea.caps.IItemStackData;
import lukas2005.idea.caps.ModCaps;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHooks {
	
	@SubscribeEvent
	public void onItemEvent(ItemEvent e) {
		
		
		
	}
	
	@SubscribeEvent
	public void onItemAttachCap(AttachCapabilitiesEvent.Item e) {	
		Double d;
		
		if (e.getObject() instanceof ItemBlock) {
			
			d = CustomValues.BLOCKWEIGHT.get(e.getObject());
			
		} else {
			
			d = CustomValues.ITEMWEIGHT.get(e.getObject());
			
		}
		
		e.addCapability(new ResourceLocation(Reference.MODID, "ItemStackData"), new IItemStackData.Provider(d));    
	}
	
	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent e) {
		if (Reference.CONFIG_USE_WEIGHT) {
			e.getToolTip().add("Weight: "+e.getItemStack().getCapability(ModCaps.ITEMSTACK_DATA_CAP, null).getWeight());
		}
	}
	
}
