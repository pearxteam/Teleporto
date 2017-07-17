package ru.pearx.teleporto.common.caps.telenergy;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import ru.pearx.teleporto.Teleporto;
import ru.pearx.teleporto.common.caps.CapabilityRegistry;

/*
 * Created by mrAppleXZ on 16.07.17 13:47.
 */
@Mod.EventBusSubscriber(modid = Teleporto.MODID)
public class TelenergyEvents
{
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent e)
    {
        if(e.side == Side.SERVER)
        {
            EntityPlayerMP p = (EntityPlayerMP) e.player;
            ITelenergyStore store = p.getCapability(CapabilityRegistry.TELENERGY_STORE_CAP, null);
            store.setTicks(store.getTicks() + 1);
        }
    }
}
