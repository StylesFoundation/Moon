package net.minecraft.client.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public abstract class ModelBase {
   public float swingProgress;
   public boolean isRiding;
   public boolean isChild = true;
   public List<ModelRenderer> boxList = Lists.newArrayList();
   private final Map<String, TextureOffset> modelTextureMap = Maps.newHashMap();
   public int textureWidth = 64;
   public int textureHeight = 32;

   public void render(Entity entity, float float2, float float3, float float4, float float5, float float6, float float7) {
   }

   public void setRotationAngles(float float1, float float2, float float3, float float4, float float5, float float6, Entity entity) {
   }

   public void setLivingAnimations(EntityLivingBase entityLivingBase, float float2, float float3, float float4) {
   }

   public ModelRenderer getRandomModelBox(Random random) {
      return this.boxList.get(random.nextInt(this.boxList.size()));
   }

   protected void setTextureOffset(String string, int integer2, int integer3) {
      this.modelTextureMap.put(string, new TextureOffset(integer2, integer3));
   }

   public TextureOffset getTextureOffset(String string) {
      return this.modelTextureMap.get(string);
   }

   public static void copyModelAngles(ModelRenderer modelRenderer1, ModelRenderer modelRenderer2) {
      modelRenderer2.rotateAngleX = modelRenderer1.rotateAngleX;
      modelRenderer2.rotateAngleY = modelRenderer1.rotateAngleY;
      modelRenderer2.rotateAngleZ = modelRenderer1.rotateAngleZ;
      modelRenderer2.rotationPointX = modelRenderer1.rotationPointX;
      modelRenderer2.rotationPointY = modelRenderer1.rotationPointY;
      modelRenderer2.rotationPointZ = modelRenderer1.rotationPointZ;
   }

   public void setModelAttributes(ModelBase modelBase) {
      this.swingProgress = modelBase.swingProgress;
      this.isRiding = modelBase.isRiding;
      this.isChild = modelBase.isChild;
   }
}
