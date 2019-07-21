package properties;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import network.NetworkManager;
import properties.event.EventHandler;
import properties.network.PropertiesPacketCallback;

@Mod(modid = "custom_properties", name = "CustomProperties")
public class ModMain {

    public static NetworkManager network = new NetworkManager("custom_properties", new PropertiesPacketCallback());

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        new EventHandler();
    }
}
