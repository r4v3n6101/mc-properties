package example;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import example.handler.GuiHandler;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import properties.data.CustomProperties;
import properties.data.impl.IntProperty;
import properties.data.impl.NBTBaseProperty;
import properties.event.InitPropertiesEvent;

import static example.Constants.*;

@Mod(modid = MODID, name = NAME, version = VERSION)
public class ExampleMod {

    private static int ticker = 0;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }

    @SubscribeEvent
    public void keyPress(TickEvent.PlayerTickEvent event) {
        if (event.player.isSneaking() && !event.player.worldObj.isRemote) {
            event.player.openGui(this, 0, event.player.worldObj, 0, 0, 0);
        }
    }

    @SubscribeEvent
    public void tick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            ticker++; // FIXME : not working as supposed to
        }
    }

    @SubscribeEvent
    public void propInit(InitPropertiesEvent event) {
        event.properties.put("thirst", new IntProperty(20, true));
        event.properties.put("customInventory", new NBTBaseProperty(new NBTTagList(), true));
    }

    @SubscribeEvent
    public void tick(TickEvent.PlayerTickEvent event) {
        if (!event.player.worldObj.isRemote && event.phase == TickEvent.Phase.END) {
            IntProperty thirst = (IntProperty) CustomProperties.get(event.player).getProperties().get("thirst");
            if (thirst.getValue() <= 0) {
                event.player.addChatMessage(new ChatComponentTranslation("commands.kill.success"));
                event.player.attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
                thirst.reset();
            } else if (ticker % 20 == 0) {
                thirst.setValue(thirst.getValue() - 1);
            }
        }
    }
}
