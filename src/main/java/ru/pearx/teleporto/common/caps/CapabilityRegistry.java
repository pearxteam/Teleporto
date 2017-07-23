package ru.pearx.teleporto.common.caps;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import ru.pearx.teleporto.Teleporto;
import ru.pearx.teleporto.common.caps.telenergy.ITelenergyStore;
import ru.pearx.teleporto.common.caps.telenergy.TelenergyStore;

import javax.annotation.Nullable;

/*
 * Created by mrAppleXZ on 15.07.17 20:41.
 */
public class CapabilityRegistry
{
    @CapabilityInject(ITelenergyStore.class)
    public static Capability<ITelenergyStore> TELENERGY_STORE_CAP;
    public static ResourceLocation TELENERGY_STORE_NAME = new ResourceLocation(Teleporto.MODID, "telenergy_store");

    public static void setup()
    {
        CapabilityManager.INSTANCE.register(ITelenergyStore.class, new Capability.IStorage<ITelenergyStore>()
        {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<ITelenergyStore> capability, ITelenergyStore instance, EnumFacing side)
            {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<ITelenergyStore> capability, ITelenergyStore instance, EnumFacing side, NBTBase nbt)
            {
                instance.deserializeNBT((NBTTagCompound) nbt);
            }
        }, TelenergyStore.class);
    }
}
