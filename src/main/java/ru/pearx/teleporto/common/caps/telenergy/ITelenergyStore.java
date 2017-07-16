package ru.pearx.teleporto.common.caps.telenergy;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

/*
 * Created by mrAppleXZ on 15.07.17 20:22.
 */
public interface ITelenergyStore extends INBTSerializable<NBTTagCompound>
{
    int get();
    int getMax();
    void set(int value, EntityPlayerMP p);
    void setMax(int value, EntityPlayerMP p);
    void setNoSync(int value);
    void setMaxNoSync(int value);
    void sync(EntityPlayerMP p);
    void add(int value, EntityPlayerMP p);
    void addMax(int value, EntityPlayerMP p);
}
