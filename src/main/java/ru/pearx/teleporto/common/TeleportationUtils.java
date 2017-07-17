package ru.pearx.teleporto.common;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import ru.pearx.teleporto.common.caps.CapabilityRegistry;
import ru.pearx.teleporto.common.caps.telenergy.ITelenergyStore;
import ru.pearx.teleporto.common.networking.NetworkManager;
import ru.pearx.teleporto.common.networking.packets.CPacketSpawnTeleportParticles;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

/*
 * Created by mrAppleXZ on 16.07.17 15:46.
 */
public class TeleportationUtils
{
    public static class TTeleporter extends Teleporter
    {
        public TTeleporter(WorldServer worldIn)
        {
            super(worldIn);
        }

        @Override
        public boolean placeInExistingPortal(Entity entityIn, float rotationYaw)
        {
            return true;
        }

        @Override
        public void placeInPortal(Entity entityIn, float rotationYaw)
        {

        }
    }

    public static void teleport(double x, double y, double z, float yaw, float pitch, float yawHead, int dimension, Entity e)
    {
        spawnTeleportParticles(e.posX, e.posY, e.posZ, e.dimension, e);
        e.getEntityWorld().playSound(null, e.posX, e.posY, e.posZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.MASTER, 1, 1);
        if(dimension != e.dimension)
        {
            WorldServer w = (WorldServer) e.getEntityWorld();
            if(e instanceof EntityPlayerMP)
            {
                e.getServer().getPlayerList().transferPlayerToDimension((EntityPlayerMP)e, 0, new TTeleporter(w));
            }
            else
            {
                e.getServer().getPlayerList().transferEntityToWorld(e, e.dimension, w, DimensionManager.getWorld(dimension), new TTeleporter(w));
            }
        }
        e.setPositionAndRotation(x, y, z, yaw, pitch);
        e.setRotationYawHead(yawHead);
        e.setPositionAndUpdate(x, y, z);
        e.getEntityWorld().playSound(null, x, y, z, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.MASTER, 1, 1);
        spawnTeleportParticles(x, y, z, dimension, e);
    }

    public static void spawnTeleportParticles(double x, double y, double z, int dimension, Entity e)
    {
        float w2 = e.width / 2;
        float h2 = e.height / 2;
        NetworkManager.INSTANCE.sendToAllAround(new CPacketSpawnTeleportParticles(
                new Vector3d(x - w2, y - h2, z - w2),
                new Vector3d(x + w2, y + h2, z + w2)),
                new NetworkRegistry.TargetPoint(dimension, x, y, z, 256));
    }

    public static int countTeleport(double x, double y, double z, int dimension, Entity e)
    {
        int cost = 45;
        if(dimension != e.dimension)
            cost += 450;
        else
            cost += (int)(new Vector3d(Math.abs(x - e.posX), Math.abs(y - e.posY), Math.abs(z - e.posZ)).length() / 20);
        return cost;
    }

    public static void teleport(double x, double y, double z, float yaw, float pitch, float yawHead, int dimension, Entity e, ITelenergyStore store)
    {
        int cost = countTeleport(x, y, z, dimension, e);
        if(store.canTeleport(cost))
        {
            teleport(x, y, z, yaw, pitch, yawHead, dimension, e);
            store.set(store.get() - cost);
        }
        else
            e.sendMessage(new TextComponentTranslation("message.not_enough_telenergy.text", cost));
    }
}
