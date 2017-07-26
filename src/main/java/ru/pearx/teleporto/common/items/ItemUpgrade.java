package ru.pearx.teleporto.common.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.pearx.libmc.client.ClientUtils;
import ru.pearx.teleporto.common.caps.CapabilityRegistry;
import ru.pearx.teleporto.common.caps.telenergy.ITelenergyStore;

import java.util.*;
import java.util.function.Predicate;

/*
 * Created by mrAppleXZ on 24.07.17 8:39.
 */
public class ItemUpgrade extends ItemBase
{
    public ItemUpgrade()
    {
        setHasSubtypes(true);
    }

    public static class Entry
    {
        @FunctionalInterface
        public interface UseAction
        {
            void onUse(ITelenergyStore store);
        }

        private String name;
        private UseAction action;
        private Predicate<ITelenergyStore> canUse;

        public Entry(String name, UseAction action, Predicate<ITelenergyStore> canUse)
        {
            this.name = name;
            this.action = action;
            this.canUse = canUse;
        }

        public Entry()
        {
        }

        public String getName()
        {
            return name;
        }

        public UseAction getAction()
        {
            return action;
        }

        public Predicate<ITelenergyStore> getCanUse()
        {
            return canUse;
        }
    }

    //Don't add your own upgrades there.
    private static final List<Entry> REGISTRY = new ArrayList<>();
    static
    {
        REGISTRY.add(new Entry("max_capacity", store -> store.setMax(store.getMax() + 50), store -> store.getMax() < Integer.MAX_VALUE));
        REGISTRY.add(new Entry("per_second", store -> store.setPerSecond(store.getPerSecond() + 1), store -> store.getPerSecond() < Integer.MAX_VALUE));
        REGISTRY.add(new Entry("per_second_d", store -> store.setPerSecond(store.getPerSecond() - 1), store -> store.getPerSecond() > 1));
        REGISTRY.add(new Entry("max_capacity_d", store -> store.setMax(store.getMax() - 50), store -> store.getMax() > 50));
    }

    public static int getUpgradeMeta(String name)
    {
        for(int i = 0; i < REGISTRY.size(); i++)
            if(REGISTRY.get(i).getName().equals(name))
                return i;
        return -1;
    }


    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName() + "." + REGISTRY.get(stack.getMetadata()).getName();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setupModels()
    {
        for(int i = 0; i < REGISTRY.size(); i++)
        {
            ClientUtils.setModelLocation(this, i, "." + REGISTRY.get(i).getName());
        }
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if(isInCreativeTab(tab))
        {
            for(int i = 0; i < REGISTRY.size(); i++)
                items.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileEntity te = worldIn.getTileEntity(pos);
        if(te != null && te.hasCapability(CapabilityRegistry.TELENERGY_STORE_CAP, null))
        {
            return use(player, hand, worldIn, te.getCapability(CapabilityRegistry.TELENERGY_STORE_CAP, null)) ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
        }
        return EnumActionResult.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        return new ActionResult<>(use(player, hand, world, player.getCapability(CapabilityRegistry.TELENERGY_STORE_CAP, null)) ? EnumActionResult.SUCCESS : EnumActionResult.PASS, stack);
    }

    private boolean use(EntityPlayer p, EnumHand hand, World w, ITelenergyStore store)
    {
        ItemStack stack = p.getHeldItem(hand);
        Entry e = REGISTRY.get(stack.getMetadata());
        if(e.getCanUse().test(store))
        {
            if (!w.isRemote)
            {
                e.getAction().onUse(store);
                stack.shrink(1);
            }
            return true;
        }
        return false;
    }
}
