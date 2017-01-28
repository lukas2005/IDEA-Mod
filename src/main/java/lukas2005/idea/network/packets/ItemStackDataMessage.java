package lukas2005.idea.network.packets;


import io.netty.buffer.ByteBuf;
import lukas2005.idea.Logger;
import lukas2005.idea.caps.IItemStackData;
import lukas2005.idea.caps.ModCaps;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ItemStackDataMessage implements IMessage {
	private Double Weight = 1.0;

	  // A default constructor is always required
	  public ItemStackDataMessage(){}


	  public ItemStackDataMessage(Double Weight) {
	    this.Weight = Weight;
	  }

	  @Override 
	  public void toBytes(ByteBuf buf) {
		  
	    buf.writeDouble(Weight);
	    
	  }

	  @Override 
	  public void fromBytes(ByteBuf buf) {
		  
	    // Reads the int back from the buf. Note that if you have multiple values, you must read in the same order you wrote.
		  Weight = buf.readDouble();
	  }
	 
	// The params of the IMessageHandler are <REQ, REPLY>
	// This means that the first param is the packet you are receiving, and the second is the packet you are returning.
	// The returned packet can be used as a "response" from a sent packet.
	public static class PlayerDataMessageHandler implements IMessageHandler<ItemStackDataMessage, IMessage> {
	  // Do note that the default constructor is required, but implicitly defined in this case

	  @Override 
	  public IMessage onMessage(ItemStackDataMessage message, MessageContext ctx) {
		final EntityPlayer p = (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : ctx.getServerHandler().playerEntity);
	    final Double Weight = message.Weight;
	    IThreadListener thread = (ctx.side.isClient() ? Minecraft.getMinecraft() : FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(p.dimension));
		thread.addScheduledTask(new Runnable(){

			@Override
			public void run() {
			    IItemStackData d = p.getCapability(ModCaps.ITEMSTACK_DATA_CAP, null);
			    d.setWeight(Weight);
				Logger.info(d.getWeight() + " : " + p.getName().toString());
			}
			
		});
	    
	    // No response packet
	    return null;
	  }
	  
	}
	  
	  
}