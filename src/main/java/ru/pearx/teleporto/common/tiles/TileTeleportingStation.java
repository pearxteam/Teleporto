package ru.pearx.teleporto.common.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import ru.pearx.libmc.common.tiles.TileSyncable;
import ru.pearx.teleporto.common.inventory.DesFocusHandler;

import javax.annotation.Nullable;

/*
 * Created by mrAppleXZ on 21.07.17 22:51.
 */
public class TileTeleportingStation extends TileSyncable
{
    public DesFocusHandler handler = new DesFocusHandler()
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            TileTeleportingStation.this.markDirty();
            IBlockState state = getWorld().getBlockState(getPos());
            getWorld().notifyBlockUpdate(getPos(), state, state, 2);
        }
    };

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setTag("items", handler.serializeNBT());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        handler.deserializeNBT(compound.getCompoundTag("items"));
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T)handler : super.getCapability(capability, facing);
    }
}
