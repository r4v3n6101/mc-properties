package properties.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayerMP;
import network.PacketCallback;
import properties.data.CustomProperties;

public class PropertiesPacketCallback implements PacketCallback {

    @Override
    public void handleServerSide(ByteBuf buf, EntityPlayerMP player) {
    }

    @Override
    public void handleClientSide(ByteBuf buf, EntityClientPlayerMP player) {
        int id = buf.readInt();
        if (id == 0) {
            CustomProperties.get(player).readSyncTag(ByteBufUtils.readTag(buf));
        }
    }
}
