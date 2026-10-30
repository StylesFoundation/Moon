package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerCreeperCharge;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderCreeper extends RenderLiving<EntityCreeper> {
   private static final ResourceLocation creeperTextures = new ResourceLocation("textures/entity/creeper/creeper.png");

   public RenderCreeper(RenderManager renderManager) {
      super(renderManager, new ModelCreeper(), 0.5F);
      this.addLayer(new LayerCreeperCharge(this));
   }

   protected void preRenderCallback(EntityCreeper entityCreeper, float float2) {
      float var3 = entityCreeper.getCreeperFlashIntensity(float2);
      float var4 = 1.0F + MathHelper.sin(var3 * 100.0F) * var3 * 0.01F;
      var3 = MathHelper.clamp_float(var3, 0.0F, 1.0F);
      var3 *= var3;
      var3 *= var3;
      float var5 = (1.0F + var3 * 0.4F) * var4;
      float var6 = (1.0F + var3 * 0.1F) / var4;
      GlStateManager.scale(var5, var6, var5);
   }

   protected int getColorMultiplier(EntityCreeper entityCreeper, float float2, float float3) {
      float var6 = entityCreeper.getCreeperFlashIntensity(float3);
      return (int)(var6 * 10.0F) % 2 == 0 ? 0 : MathHelper.clamp_int((int)(var6 * 0.2F * 255.0F), 0, 255) << 24 | 16777215;
   }

   protected ResourceLocation getEntityTexture(EntityCreeper entityCreeper) {
      return creeperTextures;
   }
}
