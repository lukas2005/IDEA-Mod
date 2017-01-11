package lukas2005.idea.network;

import lukas2005.idea.Reference;
import lukas2005.idea.network.packets.PlayerDataMessage;
import lukas2005.idea.network.packets.PlayerDataMessage.PlayerDataMessageHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ModNetwork {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);
	
	public static void main() {
		
		register();
		
	}
	
	public static void register() {
		
		INSTANCE.registerMessage(PlayerDataMessageHandler.class, PlayerDataMessage.class, 0, Side.CLIENT);
		
	}
	
}
