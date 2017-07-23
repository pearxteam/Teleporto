package ru.pearx.teleporto.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import ru.pearx.teleporto.common.caps.CapabilityRegistry;
import ru.pearx.teleporto.common.caps.telenergy.ITelenergyStore;

/*
 * Created by mrAppleXZ on 15.07.17 21:25.
 */
public class ItemTelenergyMeter extends ItemBase
{
    public ItemTelenergyMeter()
    {
        setRegistryName("telenergy_meter");
        setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileEntity te = worldIn.getTileEntity(pos);
        if(te.hasCapability(CapabilityRegistry.TELENERGY_STORE_CAP, null))
        {
            ITelenergyStore store = te.getCapability(CapabilityRegistry.TELENERGY_STORE_CAP, null);
            player.sendMessage(new TextComponentTranslation("message.telenergy_info.text", store.get(), store.getMax(), store.getPerSecond()));
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }
}
