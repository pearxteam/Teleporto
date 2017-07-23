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
 * Created by mrAppleXZ on 15.07.17 20:29.
 */
public class CPacketSyncTelenergy implements IMessage
{
    public int energy;

    public CPacketSyncTelenergy() {}
    public CPacketSyncTelenergy(int energy)
    {
        this.energy = energy;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        energy = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(energy);
    }

    public static class Handler implements IMessageHandler<CPacketSyncTelenergy, IMessage>
    {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(CPacketSyncTelenergy message, MessageContext ctx)
        {
            Minecraft.getMinecraft().addScheduledTask(() ->
                    Minecraft.getMinecraft().player.getCapability(CapabilityRegistry.TELENERGY_STORE_CAP, null).set(message.energy));
            return null;
        }
    }
}
