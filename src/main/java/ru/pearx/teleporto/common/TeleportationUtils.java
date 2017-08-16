package ru.pearx.teleporto.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketPlayerAbilities;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import ru.pearx.teleporto.Teleporto;
import ru.pearx.teleporto.common.caps.telenergy.ITelenergyStore;
import ru.pearx.teleporto.common.networking.NetworkManager;
import ru.pearx.teleporto.common.networking.packets.CPacketSpawnTeleportParticles;

import javax.vecmath.Vector3d;

/*
 * Created by mrAppleXZ on 16.07.17 15:46.
 */
public class TeleportationUtils
{
    public static void teleport(double x, double y, double z, float yaw, float pitch, float yawHead, int dimension, Entity e)
    {
        spawnTeleportParticles(e.posX, e.posY, e.posZ, e.dimension, e);
        e.getEntityWorld().playSound(null, e.posX, e.posY, e.posZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.MASTER, 1, 1);
        if(dimension != e.dimension)
        {
            if(e instanceof EntityPlayerMP)
            {
                transferPlayerToDimension((EntityPlayerMP) e, x, y, z, dimension, e.getServer().getPlayerList());
            }
            else
            {
                transferEntityToDimension(e, x, y, z, dimension, e.getServer().getPlayerList());
            }
        }
        e.fallDistance = 0;
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
        int cost = Teleporto.baseCost;
        if(dimension != e.dimension)
            cost += Teleporto.crossDimCost;
        else if(Teleporto.blocksToAddPerBlocksCost > 0)
            cost += (int)((new Vector3d(Math.abs(x - e.posX), Math.abs(y - e.posY), Math.abs(z - e.posZ)).length() / Teleporto.blocksToAddPerBlocksCost) * Teleporto.perBlocksCost);
        return cost;
    }

    public static boolean teleport(double x, double y, double z, float yaw, float pitch, float yawHead, int dimension, Entity e, ITelenergyStore store)
    {
        int cost = countTeleport(x, y, z, dimension, e);
        if(store.canTeleport(cost))
        {
            teleport(x, y, z, yaw, pitch, yawHead, dimension, e);
            store.set(store.get() - cost);
            store.sync(true, false, false);
            return true;
        }
        e.sendMessage(new TextComponentTranslation("message.not_enough_telenergy.text", cost));
        return false;
    }

    private static void transferPlayerToDimension(EntityPlayerMP player, double x, double y, double z, int dimensionIn, PlayerList lst)
    {
        int i = player.dimension;
        WorldServer worldserver = lst.getServerInstance().getWorld(player.dimension);
        player.dimension = dimensionIn;
        WorldServer worldserver1 = lst.getServerInstance().getWorld(player.dimension);
        player.connection.sendPacket(new SPacketRespawn(player.dimension, worldserver1.getDifficulty(), worldserver1.getWorldInfo().getTerrainType(), player.interactionManager.getGameType()));
        lst.updatePermissionLevel(player);
        worldserver.removeEntityDangerously(player);
        player.isDead = false;
        transferEntityToWorld(player, x, y, z, worldserver1);
        lst.preparePlayer(player, worldserver);
        player.connection.setPlayerLocation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);
        player.interactionManager.setWorld(worldserver1);
        player.connection.sendPacket(new SPacketPlayerAbilities(player.capabilities));
        lst.updateTimeAndWeatherForPlayer(player, worldserver1);
        lst.syncPlayerInventory(player);

        for (PotionEffect potioneffect : player.getActivePotionEffects())
        {
            player.connection.sendPacket(new SPacketEntityEffect(player.getEntityId(), potioneffect));
        }
        net.minecraftforge.fml.common.FMLCommonHandler.instance().firePlayerChangedDimensionEvent(player, i, dimensionIn);
    }

    private static void transferEntityToDimension(Entity e, double x, double y, double z, int dimensionIn, PlayerList lst)
    {
        WorldServer worldserver = lst.getServerInstance().getWorld(e.dimension);
        e.dimension = dimensionIn;
        WorldServer worldserver1 = lst.getServerInstance().getWorld(e.dimension);
        worldserver.removeEntityDangerously(e);
        e.isDead = false;
        transferEntityToWorld(e, x, y, z, worldserver1);
    }

    private static void transferEntityToWorld(Entity entityIn, double x, double y, double z, WorldServer toWorldIn)
    {
        if (entityIn.isEntityAlive())
        {
            entityIn.setLocationAndAngles(x, y, z, entityIn.rotationYaw, entityIn.rotationPitch);
            toWorldIn.spawnEntity(entityIn);
            toWorldIn.updateEntityWithOptionalForce(entityIn, false);
        }
        entityIn.setWorld(toWorldIn);
    }
}
