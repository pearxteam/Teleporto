package ru.pearx.teleporto.common.caps.telenergy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import ru.pearx.teleporto.common.networking.CPacketSyncMaxTelenergy;
import ru.pearx.teleporto.common.networking.CPacketSyncTelenergy;
import ru.pearx.teleporto.common.networking.NetworkManager;

/*
 * Created by mrAppleXZ on 15.07.17 20:24.
 */
public class TelenergyStore implements ITelenergyStore
{
    private int energy;
    private int max;

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("energy", energy);
        tag.setInteger("max", max);
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        energy = nbt.getInteger("energy");
        max = nbt.getInteger("max");
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
    public void set(int value, EntityPlayerMP p)
    {
        int prev = get();
        setNoSync(value);
        if(prev != value)
            NetworkManager.INSTANCE.sendTo(new CPacketSyncTelenergy(get()), p);
    }

    @Override
    public void setMax(int value, EntityPlayerMP p)
    {
        int prev = getMax();
        setMaxNoSync(value);
        if(prev != value)
            NetworkManager.INSTANCE.sendTo(new CPacketSyncMaxTelenergy(getMax()), p);
    }

    @Override
    public void setNoSync(int value)
    {
        energy = value > getMax() ? getMax() : value;
    }

    @Override
    public void setMaxNoSync(int value)
    {
        max = value;
    }

    @Override
    public void sync(EntityPlayerMP p)
    {
        NetworkManager.INSTANCE.sendTo(new CPacketSyncTelenergy(get()), p);
        NetworkManager.INSTANCE.sendTo(new CPacketSyncMaxTelenergy(getMax()), p);
    }

    @Override
    public void add(int value, EntityPlayerMP p)
    {
        set(get() + value, p);
    }

    @Override
    public void addMax(int value, EntityPlayerMP p)
    {
        setMax(getMax() + value, p);
    }
}
