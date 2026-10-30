package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelWither;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerWitherAura;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.util.ResourceLocation;

public class RenderWither extends RenderLiving<EntityWither> {
   private static final ResourceLocation invulnerableWitherTextures = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
   private static final ResourceLocation witherTextures = new ResourceLocation("textures/entity/wither/wither.png");

   public RenderWither(RenderManager renderManager) {
      super(renderManager, new ModelWither(0.0F), 1.0F);
      this.addLayer(new LayerWitherAura(this));
   }

   public void doRender(EntityWither entityWither, double double2, double double3, double double4, float float5, float float6) {
      BossStatus.setBossStatus(entityWither, true);
      super.doRender(entityWither, double2, double3, double4, float5, float6);
   }

   protected ResourceLocation getEntityTexture(EntityWither entityWither) {
      int var4 = entityWither.getInvulTime();
      return var4 <= 0 || var4 <= 80 && var4 / 5 % 2 == 1 ? witherTextures : invulnerableWitherTextures;
   }

   protected void preRenderCallback(EntityWither entityWither, float float2) {
      float var3 = 2.0F;
      int var4 = entityWither.getInvulTime();
      if (var4 > 0) {
         var3 = 2.0F - (var4 - float2) / 220.0F * 0.5F;
      }

      GlStateManager.scale(var3, var3, var3);
   }
}
