package ru.pearx.teleporto.client;

import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.pearx.libmc.client.models.IModelProvider;
import ru.pearx.teleporto.Teleporto;
import ru.pearx.teleporto.common.CommonProxy;

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
        OBJLoader.INSTANCE.addDomain(Teleporto.MODID);
    }
}
