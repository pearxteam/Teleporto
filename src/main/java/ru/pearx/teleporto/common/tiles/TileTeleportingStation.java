package ru.pearx.teleporto.common.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import ru.pearx.libmc.common.tiles.TileSyncable;
import ru.pearx.teleporto.common.TeleportationUtils;
import ru.pearx.teleporto.common.caps.CapabilityRegistry;
import ru.pearx.teleporto.common.caps.telenergy.TelenergyStore;
import ru.pearx.teleporto.common.inventory.DesFocusHandler;
import ru.pearx.teleporto.common.items.ItemRegistry;

import javax.annotation.Nullable;

/*
 * Created by mrAppleXZ on 21.07.17 22:51.
 */
public class TileTeleportingStation extends TileSyncable implements ITickable
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

    public TelenergyStore store = new TelenergyStore(this);

    private int cooldown;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setTag("items", handler.serializeNBT());
        compound.setTag("telenergy", store.serializeNBT());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        if(compound.hasKey("items"))
            handler.deserializeNBT(compound.getCompoundTag("items"));
        if(compound.hasKey("telenergy"))
            store.deserializeNBT(compound.getCompoundTag("telenergy"));
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityRegistry.TELENERGY_STORE_CAP || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (T) handler;
        if(capability == CapabilityRegistry.TELENERGY_STORE_CAP)
            return (T) store;
        return super.getCapability(capability, facing);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        return new AxisAlignedBB(pos.getX(), pos.getY() + 1.5, pos.getZ(), pos.getX() + 1, pos.getY() + 3, pos.getZ() + 1);
    }

    @Override
    public void update()
    {
        if(!getWorld().isRemote)
        {
            store.setTicks(store.getTicks() + 1);

            if(cooldown == 0)
            {
                ItemStack focus = handler.getStackInSlot(0);
                if (ItemRegistry.des_focus.isSettedUp(focus))
                {
                    NBTTagCompound tag = focus.getTagCompound();
                    for (Entity e : getWorld().getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(getPos().getX(), getPos().getY(), getPos().getZ(), getPos().getX() + 1, getPos().getY() + 1.5f, getPos().getZ() + 1)))
                    {
                        if (!TeleportationUtils.teleport(tag.getDouble("posX"), tag.getDouble("posY"), tag.getDouble("posZ"), tag.getFloat("rotYaw"), tag.getFloat("rotPitch"), tag.getFloat("rotHead"), tag.getInteger("dimension"), e, store))
                        {
                            cooldown = 20;
                        }
                    }
                }
            }
            else
                cooldown--;
        }
    }
}
