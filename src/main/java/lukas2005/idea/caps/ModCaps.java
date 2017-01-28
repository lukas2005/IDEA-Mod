package lukas2005.idea.caps;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ModCaps {

	@CapabilityInject(IItemStackData.class)
	public static Capability<IItemStackData> ITEMSTACK_DATA_CAP = null;
	
	public static void main() {
		
		CapabilityManager.INSTANCE.register(IItemStackData.class, new IItemStackData.Storage(), IItemStackData.DefaultImpl.class);
		
	}
	
}