package network;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayerMP;

public interface PacketCallback {

    void handleServerSide(ByteBuf buf, EntityPlayerMP player);

    @SideOnly(Side.CLIENT)
    void handleClientSide(ByteBuf buf, EntityClientPlayerMP player);
}
