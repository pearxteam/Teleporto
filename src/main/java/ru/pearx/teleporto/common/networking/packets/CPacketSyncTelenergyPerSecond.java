package ru.pearx.teleporto.common.networking.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.pearx.teleporto.common.caps.CapabilityRegistry;

/*
 * Created by mrAppleXZ on 16.07.17 14:32.
 */
public class CPacketSyncTelenergyPerSecond implements IMessage
{
    public int perSecond;

    public CPacketSyncTelenergyPerSecond() {}
    public CPacketSyncTelenergyPerSecond(int perSecond)
    {
        this.perSecond = perSecond;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        perSecond = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(perSecond);
    }

    public static class Handler implements IMessageHandler<CPacketSyncTelenergyPerSecond, IMessage>
    {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(CPacketSyncTelenergyPerSecond message, MessageContext ctx)
        {
            Minecraft.getMinecraft().addScheduledTask(() ->
                    Minecraft.getMinecraft().player.getCapability(CapabilityRegistry.TELENERGY_STORE_CAP, null).setPerSecond(message.perSecond));
            return null;
        }
    }
}
