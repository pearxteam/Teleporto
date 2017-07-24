package ru.pearx.teleporto.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import ru.pearx.libmc.client.models.IModelProvider;
import ru.pearx.teleporto.Teleporto;
import ru.pearx.teleporto.common.blocks.BlockRegistry;

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
    public static final ItemPortableTeleport portable_teleport = null;
    public static final ItemBlock teleporting_station = null;

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> e)
    {
        register(new ItemPrimalTeleport().setRegistryName("primal_teleport"), e.getRegistry());
        register(new ItemTelenergyMeter().setRegistryName("telenergy_meter"), e.getRegistry());
        register(new ItemDesFocus().setRegistryName("des_focus"), e.getRegistry());
        register(new ItemPortableTeleport().setRegistryName("portable_teleport"), e.getRegistry());
        register(new ItemBlockBase(BlockRegistry.teleporting_station), e.getRegistry());
        register(new ItemTelecore().setRegistryName("telecore"), e.getRegistry());
        register(new ItemUpgrade().setRegistryName("upgrade"), e.getRegistry());
    }

    public static void register(Item itm, IForgeRegistry<Item> reg)
    {
        reg.register(itm);
        if(itm instanceof IModelProvider)
            Teleporto.proxy.setupModels((IModelProvider) itm);
    }
}
