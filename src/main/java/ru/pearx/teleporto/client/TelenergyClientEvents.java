package ru.pearx.teleporto.client;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.pearx.libmc.client.gui.controls.common.ProgressBar;
import ru.pearx.teleporto.Teleporto;
import ru.pearx.teleporto.common.caps.CapabilityRegistry;
import ru.pearx.teleporto.common.items.ItemRegistry;

import java.awt.*;

/*
 * Created by mrAppleXZ on 15.07.17 21:19.
 */
@Mod.EventBusSubscriber(modid = Teleporto.MODID)
@SideOnly(Side.CLIENT)
public class TelenergyClientEvents
{
    public static ProgressBar telenergyBar = new ProgressBar()
    {
        @Override
        public int getValue()
        {
            return Minecraft.getMinecraft().player.getCapability(CapabilityRegistry.TELENERGY_STORE_CAP, null).get();
        }

        @Override
        public int getMaxValue()
        {
            return Minecraft.getMinecraft().player.getCapability(CapabilityRegistry.TELENERGY_STORE_CAP, null).getMax();
        }

        @Override
        public String getText()
        {
            return I18n.translateToLocal("misc.telenergy_bar.text");
        }
    };
    static
    {
        telenergyBar.setColor(Color.BLUE);
    }

    @SubscribeEvent
    public static void onRender(RenderGameOverlayEvent.Text e)
    {
        if(Minecraft.getMinecraft().player.inventory.hasItemStack(new ItemStack(ItemRegistry.telenergy_meter)))
        {
            telenergyBar.render();
        }
    }
}
