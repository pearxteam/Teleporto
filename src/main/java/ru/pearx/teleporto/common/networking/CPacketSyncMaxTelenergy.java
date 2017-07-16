package ru.pearx.teleporto.common.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.pearx.teleporto.common.caps.CapabilityRegistry;

/*
 * Created by mrAppleXZ on 15.07.17 20:33.
 */
public class CPacketSyncMaxTelenergy implements IMessage
{
    public int max;

    public CPacketSyncMaxTelenergy(int max)
    {
        this.max = max;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        max = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(max);
    }

    public static class Handler implements IMessageHandler<CPacketSyncMaxTelenergy, IMessage>
    {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(CPacketSyncMaxTelenergy message, MessageContext ctx)
        {
            Minecraft.getMinecraft().addScheduledTask(() ->
                    Minecraft.getMinecraft().player.getCapability(CapabilityRegistry.TELENERGY_STORE_CAP, null).setMaxNoSync(message.max));
            return null;
        }
    }
}
