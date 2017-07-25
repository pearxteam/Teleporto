package ru.pearx.teleporto.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.pearx.libmc.client.ClientUtils;
import ru.pearx.teleporto.Teleporto;
import ru.pearx.teleporto.common.TeleportationUtils;
import ru.pearx.teleporto.common.caps.CapabilityRegistry;

/*
 * Created by mrAppleXZ on 14.07.17 13:34.
 */
public class ItemPrimalTeleport extends ru.pearx.teleporto.common.items.ItemBase
{
    public ItemPrimalTeleport()
    {
        setMaxStackSize(1);
        setHasSubtypes(true);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if(!world.isRemote)
        {
            int dimension = stack.getMetadata() == 0 ? world.provider.getDimension() : Teleporto.spawnDimension;
            World w = stack.getMetadata() == 0 ? world : DimensionManager.getWorld(Teleporto.spawnDimension);
            BlockPos spawn = w.provider.getSpawnCoordinate() != null ? w.provider.getSpawnCoordinate() : w.provider.getSpawnPoint();
            BlockPos pos = !w.collidesWithAnyBlock(new AxisAlignedBB(spawn.getX(), spawn.getY(), spawn.getZ(), spawn.getX() + player.width, spawn.getY() + player.height, spawn.getZ() + player.width)) ? spawn : w.provider.getRandomizedSpawnPoint();
            TeleportationUtils.teleport(pos.getX() + .5, pos.getY(), pos.getZ() + .5,
                    player.rotationYaw, player.rotationPitch, player.getRotationYawHead(), dimension,
                    player, player.getCapability(CapabilityRegistry.TELENERGY_STORE_CAP, null));
        }
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if(isInCreativeTab(tab))
        {
            items.add(new ItemStack(this));
            items.add(new ItemStack(this, 1, 1));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        String unloc = getUnlocalizedName();
        return stack.getMetadata() == 0 ? unloc : unloc + ".global";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setupModels()
    {
        ClientUtils.setModelLocation(this);
        ClientUtils.setModelLocation(this, 1, ".global");
    }
}
