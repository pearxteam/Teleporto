package ru.pearx.teleporto.common.blocks;

import net.minecraft.block.material.Material;
import ru.pearx.teleporto.common.TCreativeTab;

/*
 * Created by mrAppleXZ on 23.07.17 11:06.
 */
public class BlockBase extends ru.pearx.libmc.common.blocks.BlockBase
{
    public BlockBase(Material materialIn)
    {
        super(materialIn);
        setCreativeTab(TCreativeTab.INSTANCE);
    }
}
