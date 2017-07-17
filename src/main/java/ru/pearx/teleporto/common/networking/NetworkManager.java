package ru.pearx.teleporto.common.networking;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import ru.pearx.teleporto.Teleporto;
import ru.pearx.teleporto.common.networking.packets.CPacketSpawnTeleportParticles;
import ru.pearx.teleporto.common.networking.packets.CPacketSyncMaxTelenergy;
import ru.pearx.teleporto.common.networking.packets.CPacketSyncTelenergy;
import ru.pearx.teleporto.common.networking.packets.CPacketSyncTelenergyPerSecond;

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
        INSTANCE.registerMessage(CPacketSyncTelenergyPerSecond.Handler.class, CPacketSyncTelenergyPerSecond.class, id++, Side.CLIENT);
        INSTANCE.registerMessage(CPacketSpawnTeleportParticles.Handler.class, CPacketSpawnTeleportParticles.class, id++, Side.CLIENT);
    }
}
