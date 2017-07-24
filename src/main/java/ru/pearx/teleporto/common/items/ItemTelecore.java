package ru.pearx.teleporto.common.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.pearx.teleporto.Teleporto;

/*
 * Created by mrAppleXZ on 23.07.17 21:36.
 */
public class ItemTelecore extends ItemBase
{
    public ItemTelecore()
    {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setupModels()
    {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(new ResourceLocation(Teleporto.MODID, "telecore_item"), "normal"));
    }
}
