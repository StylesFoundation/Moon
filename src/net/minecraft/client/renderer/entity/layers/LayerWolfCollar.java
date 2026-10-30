package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import net.optifine.CustomColors;

public class LayerWolfCollar implements LayerRenderer<EntityWolf> {
   private static final ResourceLocation WOLF_COLLAR = new ResourceLocation("textures/entity/wolf/wolf_collar.png");
   private final RenderWolf wolfRenderer;

   public LayerWolfCollar(RenderWolf renderWolf) {
      this.wolfRenderer = renderWolf;
   }

   public void doRenderLayer(EntityWolf entityWolf, float float2, float float3, float float4, float float5, float float6, float float7, float float8) {
      if (entityWolf.isTamed() && !entityWolf.isInvisible()) {
         this.wolfRenderer.bindTexture(WOLF_COLLAR);
         EnumDyeColor var9 = EnumDyeColor.byMetadata(entityWolf.getCollarColor().getMetadata());
         float[] var10 = EntitySheep.getDyeRgb(var9);
         if (Config.isCustomColors()) {
            var10 = CustomColors.getWolfCollarColors(var9, var10);
         }

         GlStateManager.color(var10[0], var10[1], var10[2]);
         this.wolfRenderer.getMainModel().render(entityWolf, float2, float3, float5, float6, float7, float8);
      }
   }

   @Override
   public boolean shouldCombineTextures() {
      return true;
   }
}
