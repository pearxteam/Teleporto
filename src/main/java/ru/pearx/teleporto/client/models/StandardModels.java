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

    public static class TeleportingStationRotating extends OvModel
    {
        /*private int telenergy;
        private int max;*/

        public TeleportingStationRotating()
        {
            setBaseModel(new ResourceLocation(Teleporto.MODID, "block/teleporting_station_rotating.obj"));

            /*vertexProcessors.add(new IVertexProcessor()
            {
                float rg, a;
                Matrix4f mat;
                @Override
                public void preProcess(List<BakedQuad> quads, @Nullable IBlockState state, @Nullable EnumFacing side, long rand, IPXModel model)
                {
                    float rads = MathUtils.toRadians(System.currentTimeMillis() / 10 % 360);
                    float sin = MathHelper.sin(rads);

                    rg =  1 - telenergy / (float)max; //MathHelper.sin(-MathUtils.toRadians(time % 360)) * 0.2f + 0.5f;
                    a = sin * 0.1f + 0.9f;

                    mat = new TRSRTransformation(new Vector3f(0.5f, 2.5f + sin * 0.5f, 0.5f), null, null, TRSRTransformation.quatFromXYZ(rads, rads, rads)).getMatrix();
                }

                @Override
                public float[] process(BakedQuad quad, float[] data, int vert, int element, @Nullable IBlockState state, @Nullable EnumFacing side, long rand, IPXModel model)
                {
                    if (quad.getFormat().getElement(element).getUsage() == VertexFormatElement.EnumUsage.COLOR)
                    {
                        data[0] = rg;
                        data[1] = rg;
                        data[2] = 1;
                        data[3] = a;
                    }
                    else if(quad.getFormat().getElement(element).getUsage() == VertexFormatElement.EnumUsage.POSITION)
                    {
                        Vector4f vec = new Vector4f(data[0], data[1], data[2], 1);
                        mat.transform(vec);
                        data[0] = vec.x;
                        data[1] = vec.y;
                        data[2] = vec.z;
                    }
                    return data;
                }

                @Override
                public boolean processState()
                {
                    return true;
                }

                @Override
                public boolean processStack()
                {
                    return true;
                }
            });*/
        }

        /*public int getTelenergy()
        {
            return telenergy;
        }

        public void setTelenergy(int telenergy)
        {
            this.telenergy = telenergy;
        }

        public int getMax()
        {
            return max;
        }

        public void setMax(int max)
        {
            this.max = max;
        }

        @Override
        public boolean isCacheActual(@Nullable IBlockState state, @Nullable EnumFacing side, long rand)
        {
            return super.isCacheActual(state, side, rand) && time == System.currentTimeMillis() / 10;
        }

        @Override
        public void updateCache(@Nullable IBlockState state, @Nullable EnumFacing side, long rand)
        {
            time = System.currentTimeMillis() / 10;
            super.updateCache(state, side, rand);
        }*/
    }
}
