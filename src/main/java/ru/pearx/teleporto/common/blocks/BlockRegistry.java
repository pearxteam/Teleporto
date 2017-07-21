package ru.pearx.teleporto.common.blocks;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import ru.pearx.libmc.client.models.IModelProvider;
import ru.pearx.teleporto.Teleporto;

/*
 * Created by mrAppleXZ on 21.07.17 17:36.
 */
@GameRegistry.ObjectHolder(Teleporto.MODID)
@Mod.EventBusSubscriber(modid = Teleporto.MODID)
public class BlockRegistry
{
    public static final BlockTeleportingStation teleporting_station = null;

    @SubscribeEvent
    public static void onRegisterBlocks(RegistryEvent.Register<Block> reg)
    {
        register(new BlockTeleportingStation(), reg.getRegistry());
    }

    public static void register(Block b, IForgeRegistry<Block> reg)
    {
        reg.register(b);
        if(b instanceof IModelProvider)
            Teleporto.proxy.setupModels((IModelProvider) b);
    }
}
