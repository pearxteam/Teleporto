package ru.pearx.teleporto.client;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.model.animation.AnimationStateMachine;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.pearx.libmc.client.models.ModelUtils;
import ru.pearx.teleporto.Teleporto;
import ru.pearx.teleporto.client.models.StandardModels;

/*
 * Created by mrAppleXZ on 21.07.17 15:03.
 */
@Mod.EventBusSubscriber(modid = Teleporto.MODID)
@SideOnly(Side.CLIENT)
public class ClientEvents
{
    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent e)
    {
        ModelUtils.register(e.getModelRegistry(), new ResourceLocation(Teleporto.MODID, "teleporting_station"), new StandardModels.ModelTeleportingStation());
    }

    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent e)
    {
        e.getMap().registerSprite(new ResourceLocation(Teleporto.MODID, "models/teleporting_station"));
    }
}
