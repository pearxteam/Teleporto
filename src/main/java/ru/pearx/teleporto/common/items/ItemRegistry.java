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
    public static final ItemPrimalTalisman primal_talisman = null;
    public static final ItemTelenergyMeter telenergy_meter = null;

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> e)
    {
        register(new ItemPrimalTalisman(), e.getRegistry());
        register(new ItemTelenergyMeter(), e.getRegistry());
    }

    public static void register(Item itm, IForgeRegistry<Item> reg)
    {
        reg.register(itm);
        if(itm instanceof IModelProvider)
            Teleporto.proxy.setupModels((IModelProvider) itm);
    }
}
