package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelDragon;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.layers.LayerEnderDragonDeath;
import net.minecraft.client.renderer.entity.layers.LayerEnderDragonEyes;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderDragon extends RenderLiving<EntityDragon> {
   private static final ResourceLocation enderDragonCrystalBeamTextures = new ResourceLocation("textures/entity/endercrystal/endercrystal_beam.png");
   private static final ResourceLocation enderDragonExplodingTextures = new ResourceLocation("textures/entity/enderdragon/dragon_exploding.png");
   private static final ResourceLocation enderDragonTextures = new ResourceLocation("textures/entity/enderdragon/dragon.png");
   protected ModelDragon modelDragon = (ModelDragon)this.mainModel;

   public RenderDragon(RenderManager renderManager) {
      super(renderManager, new ModelDragon(0.0F), 0.5F);
      this.addLayer(new LayerEnderDragonEyes(this));
      this.addLayer(new LayerEnderDragonDeath());
   }

   protected void rotateCorpse(EntityDragon entityDragon, float float2, float float3, float float4) {
      float var7 = (float)entityDragon.getMovementOffsets(7, float4)[0];
      float var8 = (float)(entityDragon.getMovementOffsets(5, float4)[1] - entityDragon.getMovementOffsets(10, float4)[1]);
      GlStateManager.rotate(-var7, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(var8 * 10.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.translate(0.0F, 0.0F, 1.0F);
      if (entityDragon.deathTime > 0) {
         float var9 = MathHelper.sqrt_float((entityDragon.deathTime + float4 - 1.0F) / 20.0F * 1.6F);
         if (var9 > 1.0F) {
            var9 = 1.0F;
         }

         GlStateManager.rotate(var9 * this.getDeathMaxRotation(entityDragon), 0.0F, 0.0F, 1.0F);
      }
   }

   protected void renderModel(EntityDragon entityDragon, float float2, float float3, float float4, float float5, float float6, float float7) {
      if (entityDragon.deathTicks > 0) {
         float var10 = entityDragon.deathTicks / 200.0F;
         GlStateManager.depthFunc(515);
         GlStateManager.enableAlpha();
         GlStateManager.alphaFunc(516, var10);
         this.bindTexture(enderDragonExplodingTextures);
         this.mainModel.render(entityDragon, float2, float3, float4, float5, float6, float7);
         GlStateManager.alphaFunc(516, 0.1F);
         GlStateManager.depthFunc(514);
      }

      this.bindEntityTexture(entityDragon);
      this.mainModel.render(entityDragon, float2, float3, float4, float5, float6, float7);
      if (entityDragon.hurtTime > 0) {
         GlStateManager.depthFunc(514);
         GlStateManager.disableTexture2D();
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(770, 771);
         GlStateManager.color(1.0F, 0.0F, 0.0F, 0.5F);
         this.mainModel.render(entityDragon, float2, float3, float4, float5, float6, float7);
         GlStateManager.enableTexture2D();
         GlStateManager.disableBlend();
         GlStateManager.depthFunc(515);
      }
   }

   public void doRender(EntityDragon entityDragon, double double2, double double3, double double4, float float5, float float6) {
      BossStatus.setBossStatus(entityDragon, false);
      super.doRender(entityDragon, double2, double3, double4, float5, float6);
      if (entityDragon.healingEnderCrystal != null) {
         this.drawRechargeRay(entityDragon, double2, double3, double4, float6);
      }
   }

   protected void drawRechargeRay(EntityDragon entityDragon, double double2, double double3, double double4, float float5) {
      float var12 = MathHelper.sin((entityDragon.healingEnderCrystal.innerRotation + float5) * 0.2F) / 2.0F + 0.5F;
      var12 = (var12 * var12 + var12) * 0.2F;
      float var13 = (float)(entityDragon.healingEnderCrystal.posX - entityDragon.posX - (entityDragon.prevPosX - entityDragon.posX) * (1.0F - float5));
      float var14 = (float)(
         var12 + entityDragon.healingEnderCrystal.posY - 1.0 - entityDragon.posY - (entityDragon.prevPosY - entityDragon.posY) * (1.0F - float5)
      );
      float var15 = (float)(entityDragon.healingEnderCrystal.posZ - entityDragon.posZ - (entityDragon.prevPosZ - entityDragon.posZ) * (1.0F - float5));
      float var16 = MathHelper.sqrt_float(var13 * var13 + var15 * var15);
      float var17 = MathHelper.sqrt_float(var13 * var13 + var14 * var14 + var15 * var15);
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)double2, (float)double3 + 2.0F, (float)double4);
      GlStateManager.rotate((float)(-Math.atan2(var15, var13)) * 180.0F / (float) Math.PI - 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate((float)(-Math.atan2(var16, var14)) * 180.0F / (float) Math.PI - 90.0F, 1.0F, 0.0F, 0.0F);
      Tessellator var18 = Tessellator.getInstance();
      WorldRenderer var19 = var18.getWorldRenderer();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableCull();
      this.bindTexture(enderDragonCrystalBeamTextures);
      GlStateManager.shadeModel(7425);
      float var20 = 0.0F - (entityDragon.ticksExisted + float5) * 0.01F;
      float var21 = MathHelper.sqrt_float(var13 * var13 + var14 * var14 + var15 * var15) / 32.0F - (entityDragon.ticksExisted + float5) * 0.01F;
      var19.begin(5, DefaultVertexFormats.POSITION_TEX_COLOR);

      for (int var23 = 0; var23 <= 8; var23++) {
         float var24 = MathHelper.sin(var23 % 8 * (float) Math.PI * 2.0F / 8.0F) * 0.75F;
         float var25 = MathHelper.cos(var23 % 8 * (float) Math.PI * 2.0F / 8.0F) * 0.75F;
         float var26 = var23 % 8 / 8.0F;
         var19.pos(var24 * 0.2F, var25 * 0.2F, 0.0).tex(var26, var21).color(0, 0, 0, 255).endVertex();
         var19.pos(var24, var25, var17).tex(var26, var20).color(255, 255, 255, 255).endVertex();
      }

      var18.draw();
      GlStateManager.enableCull();
      GlStateManager.shadeModel(7424);
      RenderHelper.enableStandardItemLighting();
      GlStateManager.popMatrix();
   }

   protected ResourceLocation getEntityTexture(EntityDragon entityDragon) {
      return enderDragonTextures;
   }
}
