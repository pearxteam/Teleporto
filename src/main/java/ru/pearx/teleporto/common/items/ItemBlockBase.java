package ru.pearx.teleporto.common.items;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/*
 * Created by mrAppleXZ on 23.07.17 10:58.
 */
public class ItemBlockBase extends ru.pearx.libmc.common.items.ItemBlockBase
{
    public ItemBlockBase(Block block)
    {
        super(block);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        String key = getUnlocalizedName(stack) + ".tooltip";
        if(I18n.hasKey(key))
            tooltip.add(I18n.format(key));
    }
}
