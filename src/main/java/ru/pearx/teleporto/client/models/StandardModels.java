package ru.pearx.teleporto.client.models;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.Pair;
import ru.pearx.libmc.client.models.OvModel;
import ru.pearx.teleporto.Teleporto;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

/*
 * Created by mrAppleXZ on 21.07.17 15:03.
 */
@SideOnly(Side.CLIENT)
public class StandardModels
{
    public static class TeleportingStation extends OvModel
    {
        public TeleportingStation()
        {
            setBaseModel(new ResourceLocation(Teleporto.MODID, "block/teleporting_station.obj"));
        }

        @Override
        public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType)
        {
            if(cameraTransformType == ItemCameraTransforms.TransformType.GUI)
                return Pair.of(this, new TRSRTransformation(new Vector3f(0, -0.17f, 0), new Quat4f(0.23f, -0.23f, 0, 0.94f), new Vector3f(0.42f, 0.42f, 0.42f), null).getMatrix());
            else
                return Pair.of(this, new TRSRTransformation(null, null, new Vector3f(0.42f, 0.42f, 0.42f), null).getMatrix());
        }
    }

    public static class Telecore extends OvModel
    {
        public Telecore()
        {
            setBaseModel(new ResourceLocation(Teleporto.MODID, "block/telecore.obj"));
        }
    }

    public static class TelecoreItem extends Telecore
    {
        @Override
        public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType)
        {
            if(cameraTransformType == ItemCameraTransforms.TransformType.GUI)
                return Pair.of(this, new TRSRTransformation(new Vector3f(0.75f, 0.75f, 0.75f), null, new Vector3f(1.5f, 1.5f, 1.5f), null).getMatrix());
            return Pair.of(this, new TRSRTransformation(new Vector3f(0.5f, 0.5f, 0.5f), null, null, null).getMatrix());
        }
    }
}
