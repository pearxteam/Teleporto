package ru.pearx.teleporto.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import ru.pearx.teleporto.common.items.ItemRegistry;

/*
 * Created by mrAppleXZ on 14.07.17 13:34.
 */
public class TCreativeTab extends CreativeTabs
{
    public static final TCreativeTab INSTANCE = new TCreativeTab();
    public TCreativeTab()
    {
        super("teleporto");
    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(ItemRegistry.portable_teleport);
    }
}
