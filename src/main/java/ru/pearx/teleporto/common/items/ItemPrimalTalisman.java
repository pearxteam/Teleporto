package ru.pearx.teleporto.common.items;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.pearx.libmc.client.ClientUtils;
import ru.pearx.teleporto.Teleporto;
import ru.pearx.teleporto.common.TeleportationUtils;
import ru.pearx.teleporto.common.caps.CapabilityRegistry;
import ru.pearx.teleporto.common.caps.telenergy.ITelenergyStore;

/*
 * Created by mrAppleXZ on 14.07.17 13:34.
 */
public class ItemPrimalTalisman extends ru.pearx.teleporto.common.items.ItemBase
{
    public ItemPrimalTalisman()
    {
        setHasSubtypes(true);
        setRegistryName("primal_talisman");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if(player instanceof EntityPlayerMP)
        {
            int dimension = stack.getMetadata() == 0 ? world.provider.getDimension() : Teleporto.spawnDimension;
            BlockPos pos = stack.getMetadata() == 0 ? world.getSpawnPoint() : DimensionManager.getWorld(Teleporto.spawnDimension).getSpawnPoint();

            int cost = TeleportationUtils.countTeleport(pos, dimension, player);
            ITelenergyStore store = player.getCapability(CapabilityRegistry.TELENERGY_STORE_CAP, null);
            if(store.canTeleport(cost))
            {
                TeleportationUtils.teleport(pos, dimension, player);
                store.set(store.get() - cost);
            }
            else
                player.sendMessage(new TextComponentTranslation("message.not_enough_telenergy.text", cost));
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
