package ru.pearx.teleporto.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.IItemHandler;
import ru.pearx.teleporto.common.items.ItemRegistry;

/*
 * Created by mrAppleXZ on 18.07.17 11:54.
 */
public class ContainerDesFocusTeleport extends ContainerDesFocus
{
    private EnumHand hand;

    public ContainerDesFocusTeleport(IInventory playerInventory, IItemHandler handler, EnumHand hand)
    {
        super(playerInventory, handler);
        this.hand = hand;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return playerIn.getHeldItem(hand).getItem() == ItemRegistry.ender_teleport;
    }
}
