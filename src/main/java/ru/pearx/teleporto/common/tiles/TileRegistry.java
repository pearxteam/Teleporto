package ru.pearx.teleporto.common.tiles;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.pearx.teleporto.Teleporto;

/*
 * Created by mrAppleXZ on 21.07.17 23:19.
 */
public class TileRegistry
{
    public static void setup()
    {
        GameRegistry.registerTileEntity(TileTeleportingStation.class, new ResourceLocation(Teleporto.MODID, "teleporting_station").toString());
    }
}
