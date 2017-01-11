package lukas2005.idea;

import lukas2005.idea.caps.IPlayerData;
import lukas2005.idea.caps.ModCaps;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class EventHooks {
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent e) {
		if (e.phase == TickEvent.Phase.END) {
			IPlayerData CAP = e.player.getCapability(ModCaps.PLAYER_DATA_CAP, null);
			
			//CAP.setStrength(100);
			Logger.info(CAP.getStrength());
			
		}
	}
	
	@SubscribeEvent
	public void attachCap(AttachCapabilitiesEvent<Entity> e) {
		if (e.getObject() instanceof EntityPlayer) {
			
			e.addCapability(new ResourceLocation(Reference.MODID, "PlayerData"), new IPlayerData.Provider((EntityPlayer) e.getObject()));                   
			
		}
	}
	
	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent e) {
		if (Reference.CONFIG_USE_WEIGHT) {
			if (e.getItemStack().getItem() instanceof ItemBlock) {
			
				e.getToolTip().add("Weight: "+CustomValues.BLOCKWEIGHT.get(Block.getBlockFromItem(e.getItemStack().getItem())));
				
			} else if (e.getItemStack().getItem() instanceof Item) {
				
				e.getToolTip().add("Weight: "+CustomValues.ITEMWEIGHT.get(e.getItemStack().getItem()));
				
			}
		}
	}
	
}
