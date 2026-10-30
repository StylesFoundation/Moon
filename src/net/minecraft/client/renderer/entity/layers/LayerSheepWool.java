package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.CustomColors;

public class LayerSheepWool implements LayerRenderer<EntitySheep> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
   private final RenderSheep sheepRenderer;
   public ModelSheep1 sheepModel = new ModelSheep1();

   public LayerSheepWool(RenderSheep renderSheep) {
      this.sheepRenderer = renderSheep;
   }

   public void doRenderLayer(EntitySheep entitySheep, float float2, float float3, float float4, float float5, float float6, float float7, float float8) {
      if (!entitySheep.getSheared() && !entitySheep.isInvisible()) {
         this.sheepRenderer.bindTexture(TEXTURE);
         if (entitySheep.hasCustomName() && "jeb_".equals(entitySheep.getCustomNameTag())) {
            int var12 = entitySheep.ticksExisted / 25 + entitySheep.getEntityId();
            int var13 = EnumDyeColor.values().length;
            int var14 = var12 % var13;
            int var15 = (var12 + 1) % var13;
            float var16 = (entitySheep.ticksExisted % 25 + float4) / 25.0F;
            float[] var17 = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(var14));
            float[] var18 = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(var15));
            if (Config.isCustomColors()) {
               var17 = CustomColors.getSheepColors(EnumDyeColor.byMetadata(var14), var17);
               var18 = CustomColors.getSheepColors(EnumDyeColor.byMetadata(var15), var18);
            }

            GlStateManager.color(
               var17[0] * (1.0F - var16) + var18[0] * var16, var17[1] * (1.0F - var16) + var18[1] * var16, var17[2] * (1.0F - var16) + var18[2] * var16
            );
         } else {
            float[] var11 = EntitySheep.getDyeRgb(entitySheep.getFleeceColor());
            if (Config.isCustomColors()) {
               var11 = CustomColors.getSheepColors(entitySheep.getFleeceColor(), var11);
            }

            GlStateManager.color(var11[0], var11[1], var11[2]);
         }

         this.sheepModel.setModelAttributes(this.sheepRenderer.getMainModel());
         this.sheepModel.setLivingAnimations(entitySheep, float2, float3, float4);
         this.sheepModel.render(entitySheep, float2, float3, float5, float6, float7, float8);
      }
   }

   @Override
   public boolean shouldCombineTextures() {
      return true;
   }
}
