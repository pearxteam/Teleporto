package ru.pearx.teleporto.common.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import ru.pearx.libmc.common.inventory.PXLContainer;
import ru.pearx.teleporto.common.items.ItemRegistry;

import javax.annotation.Nonnull;

/*
 * Created by mrAppleXZ on 18.07.17 11:39.
 */
public class ContainerDesFocus extends PXLContainer
{
    protected IItemHandler handler;

    public ContainerDesFocus(IInventory playerInventory, IItemHandler handler)
    {
        super(playerInventory, 1, 8, 51);
        this.handler = handler;

        addSlots();
        addPlayerSlots();
    }

    protected void addSlots()
    {
        addSlotToContainer(new SlotItemHandler(handler, 0, 80, 20)
        {
            @Override
            public boolean isItemValid(@Nonnull ItemStack stack)
            {
                return stack.getItem() == ItemRegistry.des_focus && super.isItemValid(stack);
            }
        });
    }
}
