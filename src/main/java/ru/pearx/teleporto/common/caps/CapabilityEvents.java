package ru.pearx.teleporto.common.caps;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import ru.pearx.teleporto.Teleporto;
import ru.pearx.teleporto.common.caps.telenergy.TelenergyStoreProvider;

/*
 * Created by mrAppleXZ on 15.07.17 21:01.
 */
@Mod.EventBusSubscriber(modid = Teleporto.MODID)
public class CapabilityEvents
{
    @SubscribeEvent
    public static void onEntityCaps(AttachCapabilitiesEvent<Entity> e)
    {
        if(e.getObject() instanceof EntityPlayer)
        {
            e.addCapability(CapabilityRegistry.TELENERGY_STORE_NAME, new TelenergyStoreProvider((EntityPlayerMP)(e.getObject() instanceof EntityPlayerMP ? e.getObject() : null)));
        }
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent e)
    {
        e.player.getCapability(CapabilityRegistry.TELENERGY_STORE_CAP, null).sync(true, true, true);
    }

    @SubscribeEvent
    public static void onJoin(PlayerEvent.PlayerLoggedInEvent e)
    {
        e.player.getCapability(CapabilityRegistry.TELENERGY_STORE_CAP, null).sync(true, true, true);
    }

    @SubscribeEvent
    public static void onClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone e)
    {
        NBTTagCompound old = e.getOriginal().getCapability(CapabilityRegistry.TELENERGY_STORE_CAP, null).serializeNBT();
        e.getEntityPlayer().getCapability(CapabilityRegistry.TELENERGY_STORE_CAP, null).deserializeNBT(old);
    }

    @SubscribeEvent
    public static void onChangeDim(PlayerEvent.PlayerChangedDimensionEvent e)
    {
        e.player.getCapability(CapabilityRegistry.TELENERGY_STORE_CAP, null).sync(true, true, true);
    }
}
