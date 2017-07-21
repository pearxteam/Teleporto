package ru.pearx.teleporto.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;
import ru.pearx.teleporto.common.tiles.TileTeleportingStation;

/*
 * Created by mrAppleXZ on 21.07.17 22:46.
 */
public class ContainerDesFocusStation extends ContainerDesFocus
{
    private TileTeleportingStation tile;

    public ContainerDesFocusStation(TileTeleportingStation tile, IInventory playerInventory, IItemHandler handler)
    {
        super(playerInventory, handler);
        this.tile = tile;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return playerIn.getDistanceSq(tile.getPos()) < 64;
    }
}
