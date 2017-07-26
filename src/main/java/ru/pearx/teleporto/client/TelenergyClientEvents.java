package ru.pearx.teleporto.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.pearx.lib.math.MathUtils;
import ru.pearx.libmc.client.gui.controls.common.ProgressBar;
import ru.pearx.teleporto.Teleporto;
import ru.pearx.teleporto.common.caps.CapabilityRegistry;
import ru.pearx.teleporto.common.items.ItemRegistry;

import java.awt.*;

/*
 * Created by mrAppleXZ on 15.07.17 21:19.
 */
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
        public int getX()
        {
            return MathUtils.calculateXPosition(getWidth(), new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth(), Teleporto.telenergyBarXPos) + Teleporto.telenergyBarXOffset;
        }

        @Override
        public int getY()
        {
            return MathUtils.calculateYPosition(getHeight(), new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight(), Teleporto.telenergyBarYPos) + Teleporto.telenergyBarYOffset;
        }

        @Override
        public String getText()
        {
            return I18n.translateToLocal("misc.telenergy_bar.text");
        }
    };
    static
    {
        telenergyBar.setSize(128, 24);
        telenergyBar.setColor(Color.BLUE);
        telenergyBar.setTextColor(Color.WHITE);
    }

    @SubscribeEvent
    public static void onRender(RenderGameOverlayEvent.Text e)
    {
        telenergyBar.setX(256);
        for(EnumHand hand : EnumHand.values())
            if(Minecraft.getMinecraft().player.getHeldItem(hand).getItem() == ItemRegistry.telenergy_meter)
                telenergyBar.invokeRender();
    }
}
