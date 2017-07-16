package ru.pearx.teleporto.common.networking;

import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import ru.pearx.teleporto.Teleporto;

import java.nio.channels.NetworkChannel;

/*
 * Created by mrAppleXZ on 15.07.17 20:26.
 */
public class NetworkManager
{
    public static SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Teleporto.MODID);

    public static void setup()
    {
        int id = 0;
        INSTANCE.registerMessage(CPacketSyncTelenergy.Handler.class, CPacketSyncTelenergy.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(CPacketSyncMaxTelenergy.Handler.class, CPacketSyncMaxTelenergy.class, id++, Side.CLIENT);
    }
}
