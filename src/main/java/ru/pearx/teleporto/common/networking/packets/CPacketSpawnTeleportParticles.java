package ru.pearx.teleporto.common.networking.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.pearx.libmc.common.networking.ByteBufTools;

import javax.vecmath.Vector3d;

/*
 * Created by mrAppleXZ on 17.07.17 11:08.
 */
public class CPacketSpawnTeleportParticles implements IMessage
{
    public Vector3d from, to;

    public CPacketSpawnTeleportParticles() {}

    public CPacketSpawnTeleportParticles(Vector3d from, Vector3d to)
    {
        this.from = from;
        this.to = to;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        from = ByteBufTools.readVector3d(buf);
        to = ByteBufTools.readVector3d(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufTools.writeVector3d(buf, from);
        ByteBufTools.writeVector3d(buf, to);
    }

    public static class Handler implements IMessageHandler<CPacketSpawnTeleportParticles, IMessage>
    {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(CPacketSpawnTeleportParticles message, MessageContext ctx)
        {
            Minecraft.getMinecraft().addScheduledTask(() ->
            {
                   for (double dx = message.from.x; dx <= message.to.x; dx += 0.05d)
                   {
                       for (double dy = message.from.y; dy <= message.to.y; dy += 0.05d)
                       {
                           for (double dz = message.from.z; dz <= message.to.z; dz += 0.05d)
                           {
                               Minecraft.getMinecraft().world.spawnParticle(EnumParticleTypes.PORTAL, dx, dy, dz, 0, 0, 0);
                           }
                       }
                   }
            });
            return null;
        }
    }
}
