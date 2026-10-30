package net.optifine.player;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;

public class PlayerItemModel {
   private Dimension textureSize = null;
   private boolean usePlayerTexture = false;
   private PlayerItemRenderer[] modelRenderers = new PlayerItemRenderer[0];
   private ResourceLocation textureLocation = null;
   private BufferedImage textureImage = null;
   private DynamicTexture texture = null;
   private final ResourceLocation locationMissing = new ResourceLocation("textures/blocks/wool_colored_red.png");

   public PlayerItemModel(Dimension dimension, boolean boolean2, PlayerItemRenderer[] arr) {
      this.textureSize = dimension;
      this.usePlayerTexture = boolean2;
      this.modelRenderers = arr;
   }

   public void render(ModelBiped modelBiped, AbstractClientPlayer abstractClientPlayer, float float3, float float4) {
      TextureManager var8 = Config.getTextureManager();
      if (this.usePlayerTexture) {
         var8.bindTexture(abstractClientPlayer.getLocationSkin());
      } else if (this.textureLocation != null) {
         if (this.texture == null && this.textureImage != null) {
            this.texture = new DynamicTexture(this.textureImage);
            Minecraft.getMinecraft().getTextureManager().loadTexture(this.textureLocation, this.texture);
         }

         var8.bindTexture(this.textureLocation);
      } else {
         var8.bindTexture(this.locationMissing);
      }

      for (PlayerItemRenderer var12 : this.modelRenderers) {
         GlStateManager.pushMatrix();
         if (abstractClientPlayer.isSneaking()) {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
         }

         var12.render(modelBiped, float3);
         GlStateManager.popMatrix();
      }
   }

   public static ModelRenderer getAttachModel(ModelBiped modelBiped, int integer) {
      switch (integer) {
         case 0:
            return modelBiped.bipedBody;
         case 1:
            return modelBiped.bipedHead;
         case 2:
            return modelBiped.bipedLeftArm;
         case 3:
            return modelBiped.bipedRightArm;
         case 4:
            return modelBiped.bipedLeftLeg;
         case 5:
            return modelBiped.bipedRightLeg;
         default:
            return null;
      }
   }

   public BufferedImage getTextureImage() {
      return this.textureImage;
   }

   public void setTextureImage(BufferedImage bufferedImage) {
      this.textureImage = bufferedImage;
   }

   public DynamicTexture getTexture() {
      return this.texture;
   }

   public ResourceLocation getTextureLocation() {
      return this.textureLocation;
   }

   public void setTextureLocation(ResourceLocation resourceLocation) {
      this.textureLocation = resourceLocation;
   }

   public boolean isUsePlayerTexture() {
      return this.usePlayerTexture;
   }
}
