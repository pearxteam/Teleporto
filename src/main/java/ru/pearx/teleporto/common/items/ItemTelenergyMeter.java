package ru.pearx.teleporto.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
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
        setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te != null && te.hasCapability(CapabilityRegistry.TELENERGY_STORE_CAP, facing))
        {
            if (!worldIn.isRemote)
            {
                ITelenergyStore store = te.getCapability(CapabilityRegistry.TELENERGY_STORE_CAP, facing);
                use(player, store);
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        if(!worldIn.isRemote)
        {
            ITelenergyStore store = playerIn.getCapability(CapabilityRegistry.TELENERGY_STORE_CAP, null);
            use(playerIn, store);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    private void use(EntityPlayer p, ITelenergyStore store)
    {
        p.sendMessage(new TextComponentTranslation("message.telenergy_info.text", store.get(), store.getMax(), store.getPerSecond()));
    }
}
