package lukas2005.idea.caps;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ModCaps {

	@CapabilityInject(IPlayerData.class)
	public static Capability<IPlayerData> PLAYER_DATA_CAP = null;

	public static void main() {
		
		CapabilityManager.INSTANCE.register(IPlayerData.class, new IPlayerData.Storage(), IPlayerData.DefaultImpl.class);
		
	}
	
}