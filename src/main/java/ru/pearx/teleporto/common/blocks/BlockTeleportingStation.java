package ru.pearx.teleporto.common.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import ru.pearx.libmc.common.ItemStackUtils;
import ru.pearx.teleporto.Teleporto;
import ru.pearx.teleporto.common.items.ItemRegistry;
import ru.pearx.teleporto.common.tiles.TileTeleportingStation;

import javax.annotation.Nullable;
import java.util.Random;

/*
 * Created by mrAppleXZ on 21.07.17 17:33.
 */
public class BlockTeleportingStation extends BlockBase
{
    public static final int GUI_ID = 1;
    public static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.05f, 0, 0.05f, 0.95f, 0.07f, 0.95f);
    public BlockTeleportingStation()
    {
        super(Material.IRON);
        setUnlocalizedName("teleporting_station");
        setSoundType(SoundType.METAL);
        setHardness(1.6f);
        setHarvestLevel("pickaxe", 0);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }

    @Override
    public boolean isFullBlock(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.CENTER_SMALL;
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileTeleportingStation();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        playerIn.openGui(Teleporto.INSTANCE, GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        TileEntity te = worldIn.getTileEntity(pos);
        if(te instanceof TileTeleportingStation)
        {
            if(ItemRegistry.des_focus.isSettedUp(te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0)))
                for(float f = 0; f < 1; f += 0.05)
                    worldIn.spawnParticle(EnumParticleTypes.PORTAL, pos.getX() + .5, pos.getY() + f, pos.getZ() + .5, 0, 0, 0);
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity te = worldIn.getTileEntity(pos);
        if(te != null && te instanceof TileTeleportingStation)
        {
            ItemStackUtils.drop(te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null), worldIn, pos);
        }
        super.breakBlock(worldIn, pos, state);
    }
}
