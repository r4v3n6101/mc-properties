package network;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;

public class NetworkManager {

    private final String channelName;
    private final PacketCallback callback;
    private final FMLEventChannel channel;

    public NetworkManager(String channelName, PacketCallback callback) {
        this.channelName = channelName;
        this.callback = callback;
        this.channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(channelName);
        this.channel.register(this);
    }

    @SubscribeEvent
    public void onServerPacket(FMLNetworkEvent.ServerCustomPacketEvent e) {
        EntityPlayerMP player = ((NetHandlerPlayServer) e.handler).playerEntity;
        ByteBuf buf = e.packet.payload();
        callback.handleServerSide(buf, player);
    }

    @SubscribeEvent
    public void onClientPacket(FMLNetworkEvent.ClientCustomPacketEvent event) {
        EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
        ByteBuf buf = event.packet.payload();
        callback.handleClientSide(buf, player);
    }

    public void sendToServer(FMLProxyPacket packet) {
        channel.sendToServer(packet);
    }

    public void sendToClients(FMLProxyPacket packet) {
        channel.sendToAll(packet);
    }

    public void sendToPlayer(FMLProxyPacket packet, EntityPlayerMP player) {
        channel.sendTo(packet, player);
    }

    public void sendToArea(FMLProxyPacket packet, double x, double y, double z, double range, int dim) {
        channel.sendToAllAround(packet, new NetworkRegistry.TargetPoint(dim, x, y, z, range));
    }

    public void sendToDimension(FMLProxyPacket packet, int id) {
        channel.sendToDimension(packet, id);
    }

    public FMLProxyPacket createPacket(Object... data) {
        return createPacket(Unpooled.buffer(32), data);
    }

    public FMLProxyPacket createPacket(ByteBuf buf, Object... data) {
        for (Object obj : data) {
            if (obj instanceof Byte) {
                buf.writeByte((Byte) obj);
            } else if (obj instanceof Short) {
                buf.writeShort((Short) obj);
            } else if (obj instanceof Integer) {
                buf.writeInt((Integer) obj);
            } else if (obj instanceof Float) {
                buf.writeFloat((Float) obj);
            } else if (obj instanceof Double) {
                buf.writeDouble((Double) obj);
            } else if (obj instanceof Long) {
                buf.writeLong((Long) obj);
            } else if (obj instanceof Character) {
                buf.writeChar((Character) obj);
            } else if (obj instanceof String) {
                ByteBufUtils.writeUTF8String(buf, (String) obj);
            } else if (obj instanceof ItemStack) {
                ByteBufUtils.writeItemStack(buf, (ItemStack) obj);
            } else if (obj instanceof NBTTagCompound) {
                ByteBufUtils.writeTag(buf, (NBTTagCompound) obj);
            }
        }
        return new FMLProxyPacket(buf, channelName);
    }
}
