package ru.pearx.teleporto.client;

import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.pearx.libmc.client.models.IModelProvider;
import ru.pearx.teleporto.Teleporto;
import ru.pearx.teleporto.client.tesrs.TESRTeleportingStation;
import ru.pearx.teleporto.common.CommonProxy;
import ru.pearx.teleporto.common.tiles.TileTeleportingStation;

/*
 * Created by mrAppleXZ on 14.07.17 14:03.
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    @Override
    public void setupModels(IModelProvider prov)
    {
        prov.setupModels();
    }

    @Override
    public void preInit()
    {
        MinecraftForge.EVENT_BUS.register(ClientEvents.class);
        MinecraftForge.EVENT_BUS.register(TelenergyClientEvents.class);
        OBJLoader.INSTANCE.addDomain(Teleporto.MODID);
    }

    @Override
    public void init()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileTeleportingStation.class, new TESRTeleportingStation());
    }
}
