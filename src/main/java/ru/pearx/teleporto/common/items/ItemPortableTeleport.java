package ru.pearx.teleporto.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import ru.pearx.teleporto.Teleporto;
import ru.pearx.teleporto.common.TeleportationUtils;
import ru.pearx.teleporto.common.caps.CapabilityRegistry;
import ru.pearx.teleporto.common.inventory.DesFocusHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/*
 * Created by mrAppleXZ on 18.07.17 10:30.
 */
public class ItemPortableTeleport extends ItemBase
{
    public static final int GUI_ID = 0;

    public ItemPortableTeleport()
    {
        setMaxStackSize(1);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt)
    {
        return new ICapabilitySerializable<NBTTagCompound>()
        {
            DesFocusHandler handler = new DesFocusHandler();

            @Override
            public NBTTagCompound serializeNBT()
            {
                return handler.serializeNBT();
            }

            @Override
            public void deserializeNBT(NBTTagCompound nbt)
            {
                handler.deserializeNBT(nbt);
            }

            @Override
            public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
            {
                return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
            }

            @Nullable
            @Override
            public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
            {
                return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T) handler : null;
            }
        };
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if(!worldIn.isRemote)
        {
            if(playerIn.isSneaking())
                playerIn.openGui(Teleporto.INSTANCE, GUI_ID, worldIn, (int)playerIn.posX, (int)playerIn.posY, (int)playerIn.posZ);
            else
            {
                ItemStack focus = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0);
                if(ItemRegistry.des_focus.isSettedUp(focus))
                {
                    NBTTagCompound tag = focus.getTagCompound();
                    TeleportationUtils.teleport(tag.getDouble("posX"), tag.getDouble("posY"), tag.getDouble("posZ"),
                            tag.getFloat("rotYaw"), tag.getFloat("rotPitch"), tag.getFloat("rotHead"),
                            tag.getInteger("dimension"), playerIn, playerIn.getCapability(CapabilityRegistry.TELENERGY_STORE_CAP, null));
                }
            }
        }
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }
}
