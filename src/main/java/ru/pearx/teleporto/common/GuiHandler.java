package ru.pearx.teleporto.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.input.Mouse;
import ru.pearx.teleporto.client.gui.inventory.GuiContainerDesFocus;
import ru.pearx.teleporto.common.inventory.ContainerDesFocusTeleport;
import ru.pearx.teleporto.common.items.ItemEnderTeleport;
import ru.pearx.teleporto.common.items.ItemRegistry;

import javax.annotation.Nullable;

/*
 * Created by mrAppleXZ on 18.07.17 12:24.
 */
public class GuiHandler implements IGuiHandler
{
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case ItemEnderTeleport.GUI_ID:
                for(EnumHand hand : EnumHand.values())
                {
                    ItemStack stack = player.getHeldItem(hand);
                    if(stack.getItem() == ItemRegistry.ender_teleport)
                    {
                        Mouse.setGrabbed(false);
                        IItemHandler handler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                        return new ContainerDesFocusTeleport(player.inventory, handler, hand);
                    }
                }
                break;
        }
        return null;
    }

    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case ItemEnderTeleport.GUI_ID:
                for(EnumHand hand : EnumHand.values())
                {
                    ItemStack stack = player.getHeldItem(hand);
                    if(stack.getItem() == ItemRegistry.ender_teleport)
                    {
                        IItemHandler handler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                        return new GuiContainerDesFocus(new ContainerDesFocusTeleport(player.inventory, handler, hand));
                    }
                }
                break;
        }
        return null;
    }
}
