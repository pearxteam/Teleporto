package ru.pearx.teleporto.client.tesrs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import ru.pearx.lib.math.MathUtils;
import ru.pearx.libmc.client.PXLFastTESR;
import ru.pearx.libmc.client.TessellatorUtils;
import ru.pearx.teleporto.Teleporto;
import ru.pearx.teleporto.common.items.ItemRegistry;
import ru.pearx.teleporto.common.tiles.TileTeleportingStation;

/*
 * Created by mrAppleXZ on 22.07.17 11:41.
 */
@SideOnly(Side.CLIENT)
public class TESRTeleportingStation extends PXLFastTESR<TileTeleportingStation>
{
    @Override
    public void renderTileEntityFast(TileTeleportingStation te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer)
    {
        if(ItemRegistry.des_focus.isSettedUp(te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(0)))
        {
            BlockRendererDispatcher dis = Minecraft.getMinecraft().getBlockRendererDispatcher();
            IBakedModel model = dis.getBlockModelShapes().getModelManager().getModel(new ModelResourceLocation(new ResourceLocation(Teleporto.MODID, "teleporting_station_rotating"), "normal"));
            /*if(model instanceof StandardModels.TeleportingStationRotating)
            {
                ((StandardModels.TeleportingStationRotating) model).setTelenergy(te.store.get());
                ((StandardModels.TeleportingStationRotating) model).setMax(te.store.getMax());
            }*/
            dis.getBlockModelRenderer().renderModelFlat(te.getWorld(), model, te.getWorld().getBlockState(te.getPos()), te.getPos(), buffer, false, MathHelper.getPositionRandom(te.getPos()));
            float rg = 1 - te.store.get() / (float)te.store.getMax();
            float a = MathHelper.sin(MathUtils.toRadians(System.currentTimeMillis() / 10 % 360)) * 0.1f + 0.9f;
            for(int vert = 0; vert <= buffer.getVertexCount(); vert++)
            {
                TessellatorUtils.multiplyColor(buffer, vert, rg, rg, 1, a);
            }
        }
    }

    @Override
    public void renderPre(TileTeleportingStation te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        int degrees = (int)(System.currentTimeMillis() / 10 % 360);

        GlStateManager.translate(0.5f, 2.5f + MathHelper.sin(MathUtils.toRadians(degrees)) * 0.5f, 0.5f);
        GlStateManager.rotate(degrees, 1, 0, 0);
        GlStateManager.rotate(degrees, 0, 1, 0);
        GlStateManager.rotate(degrees, 0, 0, 1);
    }

    @Override
    public void renderPost(TileTeleportingStation te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {

    }
}
