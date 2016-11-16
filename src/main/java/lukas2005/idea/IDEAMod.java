package lukas2005.idea;

import org.apache.logging.log4j.LogManager;

import lukas2005.idea.Items.ModItems;
import lukas2005.idea.proxy.IProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, version = Reference.VERSION, name = Reference.NAME, acceptedMinecraftVersions = Reference.MCVERSION, dependencies = Reference.DEPENDENCIES)
public class IDEAMod {

	@SidedProxy(modId = Reference.MODID, serverSide = Reference.SERVER_PROXY, clientSide = Reference.CLIENT_PROXY)
	public static IProxy proxy;
	
	@Instance(Reference.MODID)
	public static IDEAMod INSTANCE;
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent e) {
		Logger.setLogger(LogManager.getLogger(Reference.MODID.toUpperCase()));
		Logger.info("Pre Init!");
		
		MinecraftForge.EVENT_BUS.register(new EventHooks());
		
		if (!e.getSuggestedConfigurationFile().exists()) {
			
			
			
		}
		
		Reference.CONFIG = new Configuration(e.getSuggestedConfigurationFile());
		
		Reference.CONFIG.setCategoryComment("GENERAL CONFIG", "GENERAL CONFIGURATION FOR IDEA MOD");
		
		Property tmpp = Reference.CONFIG.get("GENERAL CONFIG", "useWeight", true);
		
		tmpp.setComment("Use weight system?");
		
		Reference.CONFIG_USE_WEIGHT = tmpp.getBoolean();
		
		ModItems.main();
		//Logger.info("Loaded and registered succesfully: " + ModItems.ITEMS.size() + " Items");
		
		CustomValues.initValues(Reference.CONFIG);
		
		
		proxy.preInit(e);
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent e) {
		Logger.info("Init!");
		proxy.init(e);
	}
	
	@EventHandler
	public static void postInit(FMLPostInitializationEvent e) {
		Logger.info("Post Init!");
		
		proxy.postInit(e);
	}
	
}
