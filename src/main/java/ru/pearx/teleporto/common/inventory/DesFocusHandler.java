package ru.pearx.teleporto.common.inventory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import ru.pearx.teleporto.common.items.ItemRegistry;

import javax.annotation.Nonnull;

/*
 * Created by mrAppleXZ on 21.07.17 22:57.
 */
public class DesFocusHandler extends ItemStackHandler
{
    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
    {
        return stack.getItem() == ItemRegistry.des_focus ? super.insertItem(slot, stack, simulate) : stack;
    }
}
