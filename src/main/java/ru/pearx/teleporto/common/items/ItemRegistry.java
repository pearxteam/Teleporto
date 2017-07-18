package ru.pearx.teleporto.common.items;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import ru.pearx.libmc.client.models.IModelProvider;
import ru.pearx.teleporto.Teleporto;

/*
 * Created by mrAppleXZ on 14.07.17 13:33.
 */
@GameRegistry.ObjectHolder(Teleporto.MODID)
@Mod.EventBusSubscriber
public class ItemRegistry
{
    public static final ItemPrimalTeleport primal_teleport = null;
    public static final ItemTelenergyMeter telenergy_meter = null;
    public static final ItemDesFocus des_focus = null;
    public static final ItemEnderTeleport ender_teleport = null;

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> e)
    {
        register(new ItemPrimalTeleport(), e.getRegistry());
        register(new ItemTelenergyMeter(), e.getRegistry());
        register(new ItemDesFocus(), e.getRegistry());
        register(new ItemEnderTeleport(), e.getRegistry());
    }

    public static void register(Item itm, IForgeRegistry<Item> reg)
    {
        reg.register(itm);
        if(itm instanceof IModelProvider)
            Teleporto.proxy.setupModels((IModelProvider) itm);
    }
}
