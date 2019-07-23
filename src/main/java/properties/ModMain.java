package properties;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import network.NetworkManager;
import properties.event.EventHandler;
import properties.network.PropertiesPacketCallback;

import static properties.Constants.*;

@Mod(modid = MODID, name = NAME, version = VERSION)
public class ModMain {

    public static NetworkManager network = new NetworkManager(MODID, new PropertiesPacketCallback());

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        new EventHandler();
    }
}
