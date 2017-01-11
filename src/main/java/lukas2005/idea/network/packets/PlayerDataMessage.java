package lukas2005.idea.network.packets;


import io.netty.buffer.ByteBuf;
import lukas2005.idea.Logger;
import lukas2005.idea.caps.IPlayerData;
import lukas2005.idea.caps.ModCaps;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PlayerDataMessage implements IMessage {
	private int Strength = 0;

	  // A default constructor is always required
	  public PlayerDataMessage(){}


	  public PlayerDataMessage(int Strength) {
	    this.Strength = Strength;
	  }

	  @Override 
	  public void toBytes(ByteBuf buf) {
		  
	    buf.writeInt(Strength);
	    
	  }

	  @Override 
	  public void fromBytes(ByteBuf buf) {
		  
	    // Reads the int back from the buf. Note that if you have multiple values, you must read in the same order you wrote.
		  Strength = buf.readInt();
	  }
	 
	// The params of the IMessageHandler are <REQ, REPLY>
	// This means that the first param is the packet you are receiving, and the second is the packet you are returning.
	// The returned packet can be used as a "response" from a sent packet.
	public static class PlayerDataMessageHandler implements IMessageHandler<PlayerDataMessage, IMessage> {
	  // Do note that the default constructor is required, but implicitly defined in this case

	  @Override 
	  public IMessage onMessage(PlayerDataMessage message, MessageContext ctx) {
		final EntityPlayer p = (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : ctx.getServerHandler().playerEntity);
	    final int Strength = message.Strength;
	    IThreadListener thread = (ctx.side.isClient() ? Minecraft.getMinecraft() : FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(p.dimension));
		thread.addScheduledTask(new Runnable(){

			@Override
			public void run() {
			    IPlayerData d = p.getCapability(ModCaps.PLAYER_DATA_CAP, null);
			    d.setStrength(Strength);
				Logger.info(d.getStrength() + " : " + p.getName().toString());
			}
			
		});
	    
	    // No response packet
	    return null;
	  }
	  
	}
	  
	  
}