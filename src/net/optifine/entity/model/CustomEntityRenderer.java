package net.optifine.entity.model;

import net.minecraft.util.ResourceLocation;

public class CustomEntityRenderer {
   private String name = null;
   private String basePath = null;
   private ResourceLocation textureLocation = null;
   private CustomModelRenderer[] customModelRenderers = null;
   private float shadowSize = 0.0F;

   public CustomEntityRenderer(String string1, String string2, ResourceLocation resourceLocation, CustomModelRenderer[] arr, float float5) {
      this.name = string1;
      this.basePath = string2;
      this.textureLocation = resourceLocation;
      this.customModelRenderers = arr;
      this.shadowSize = float5;
   }

   public String getName() {
      return this.name;
   }

   public String getBasePath() {
      return this.basePath;
   }

   public ResourceLocation getTextureLocation() {
      return this.textureLocation;
   }

   public CustomModelRenderer[] getCustomModelRenderers() {
      return this.customModelRenderers;
   }

   public float getShadowSize() {
      return this.shadowSize;
   }
}
