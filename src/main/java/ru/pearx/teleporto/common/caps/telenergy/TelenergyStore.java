package ru.pearx.teleporto.common.caps.telenergy;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import ru.pearx.teleporto.common.networking.packets.CPacketSyncMaxTelenergy;
import ru.pearx.teleporto.common.networking.packets.CPacketSyncTelenergy;
import ru.pearx.teleporto.common.networking.packets.CPacketSyncTelenergyPerSecond;
import ru.pearx.teleporto.common.networking.NetworkManager;

/*
 * Created by mrAppleXZ on 15.07.17 20:24.
 */
public class TelenergyStore implements ITelenergyStore
{
    private int energy;
    private int max = 200;
    private int ticks = 0;
    private int perSecond = 1;

    private EntityPlayerMP player;
    private TileEntity tile;

    public TelenergyStore(EntityPlayerMP p)
    {
        player = p;
    }

    public TelenergyStore(TileEntity te)
    {
        tile = te;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("max", getMax());
        tag.setInteger("energy", get());
        tag.setInteger("perSecond", getPerSecond());
        tag.setInteger("ticks", getTicks());
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        setMaxNoSync(nbt.getInteger("max"));
        setNoSync(nbt.getInteger("energy"));
        setPerSecondNoSync(nbt.getInteger("perSecond"));
        setTicks(nbt.getInteger("ticks"));
    }

    @Override
    public int get()
    {
        return energy;
    }

    @Override
    public int getMax()
    {
        return max;
    }

    @Override
    public void set(int value)
    {
        int prev = get();
        if(value > getMax())
            value = getMax();
        setNoSync(value);
        if(prev != get())
        {
            if (tile != null)
                tile.markDirty();
            sync(true, false, false);
        }
    }

    @Override
    public void setMax(int value)
    {
        setMaxNoSync(value);
        if(tile != null)
            tile.markDirty();
        sync(false, true, false);
    }

    @Override
    public void setNoSync(int value)
    {
        energy = value;
    }

    @Override
    public void setMaxNoSync(int value)
    {
        max = value;
    }

    @Override
    public void sync(boolean energy, boolean max, boolean perSecond)
    {
        if(tile != null)
        {
            IBlockState state = tile.getWorld().getBlockState(tile.getPos());
            tile.getWorld().notifyBlockUpdate(tile.getPos(), state, state, 2);
        }
        if(player != null)
        {
            if(energy)
                NetworkManager.INSTANCE.sendTo(new CPacketSyncTelenergy(get()), player);
            if(max)
                NetworkManager.INSTANCE.sendTo(new CPacketSyncMaxTelenergy(getMax()), player);
            if(perSecond)
                NetworkManager.INSTANCE.sendTo(new CPacketSyncTelenergyPerSecond(getPerSecond()), player);
        }
    }

    @Override
    public int getTicks()
    {
        return ticks;
    }

    @Override
    public void setTicks(int count)
    {
        if(count >= 20)
        {
            count = 0;
            set(get() + getPerSecond());
        }
        ticks = count;
    }

    @Override
    public int getPerSecond()
    {
        return perSecond;
    }

    @Override
    public void setPerSecond(int value)
    {
        setPerSecondNoSync(value);
        if(tile != null)
            tile.markDirty();
        sync(false, false, true);
    }

    @Override
    public void setPerSecondNoSync(int value)
    {
        perSecond = value;
    }

    @Override
    public boolean canTeleport(int cost)
    {
        return get() >= cost;
    }
}
