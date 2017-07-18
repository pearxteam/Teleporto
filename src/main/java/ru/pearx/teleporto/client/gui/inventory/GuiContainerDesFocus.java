package ru.pearx.teleporto.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.pearx.libmc.client.gui.DrawingTools;
import ru.pearx.libmc.client.gui.inventory.PXLGuiContainer;
import ru.pearx.teleporto.Teleporto;

import java.awt.*;

/*
 * Created by mrAppleXZ on 18.07.17 12:23.
 */
@SideOnly(Side.CLIENT)
public class GuiContainerDesFocus extends PXLGuiContainer
{
    public GuiContainerDesFocus(Container inventorySlotsIn)
    {
        super(inventorySlotsIn);
        xSize = 176;
        ySize = 133;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        DrawingTools.drawTexture(new ResourceLocation(Teleporto.MODID, "textures/gui/des_focus_container.png"), (width - xSize) / 2, (height - ySize) / 2, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        DrawingTools.drawString(Minecraft.getMinecraft().player.inventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, Color.DARK_GRAY, false);
    }
}
