package properties.event;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import properties.ModMain;
import properties.data.CustomProperties;

public class EventHandler {

    public EventHandler() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void onEntityConstructed(EntityEvent.EntityConstructing event) {
        Entity entity = event.entity;
        MinecraftForge.EVENT_BUS.post(new InitPropertiesEvent(CustomProperties.get(entity).getProperties(), entity));
    }

    @SubscribeEvent
    public void onPlayerDeath(PlayerEvent.Clone event) {
        EntityPlayer newPlayer = event.entityPlayer;
        EntityPlayer oldPlayer = event.original;
        if (!newPlayer.worldObj.isRemote) {
            CustomProperties from = CustomProperties.get(oldPlayer);
            CustomProperties to = CustomProperties.get(newPlayer);
            from.copy(to);
        }
    }

    @SubscribeEvent
    public void onEntityTick(LivingEvent.LivingUpdateEvent event) {
        Entity entity = event.entity;
        if (!entity.worldObj.isRemote) {
            NBTTagCompound syncTag = CustomProperties.get(entity).makeSyncTag();

            if (syncTag.hasKey("list")) {
                ModMain.network.sendToClients(ModMain.network.createPacket(0, syncTag)); // not to all
            }
        }
    }
}
