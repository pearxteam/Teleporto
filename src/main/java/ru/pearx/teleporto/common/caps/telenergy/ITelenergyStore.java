package ru.pearx.teleporto.common.caps.telenergy;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.INBTSerializable;

/*
 * Created by mrAppleXZ on 15.07.17 20:22.
 */
public interface ITelenergyStore extends INBTSerializable<NBTTagCompound>
{
    int get();
    void set(int value);
    void setNoSync(int value);

    int getMax();
    void setMax(int value);
    void setMaxNoSync(int value);;

    int getPerSecond();
    void setPerSecond(int value);
    void setPerSecondNoSync(int value);

    void sync(boolean energy, boolean max, boolean perSecond);
    boolean canTeleport(int cost);
    //Server side only things \/
    int getTicks();
    void setTicks(int count);

}
