package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityFootStepFX extends EntityFX {
   private static final ResourceLocation FOOTPRINT_TEXTURE = new ResourceLocation("textures/particle/footprint.png");
   private int footstepAge;
   private final int footstepMaxAge;
   private final TextureManager currentFootSteps;

   protected EntityFootStepFX(TextureManager textureManager, World world, double double3, double double4, double double5) {
      super(world, double3, double4, double5, 0.0, 0.0, 0.0);
      this.currentFootSteps = textureManager;
      this.motionX = this.motionY = this.motionZ = 0.0;
      this.footstepMaxAge = 200;
   }

   @Override
   public void renderParticle(WorldRenderer worldRenderer, Entity entity, float float3, float float4, float float5, float float6, float float7, float float8) {
      float var11 = (this.footstepAge + float3) / this.footstepMaxAge;
      var11 *= var11;
      float var12 = 2.0F - var11 * 2.0F;
      if (var12 > 1.0F) {
         var12 = 1.0F;
      }

      var12 *= 0.2F;
      GlStateManager.disableLighting();
      float var14 = (float)(this.posX - interpPosX);
      float var15 = (float)(this.posY - interpPosY);
      float var16 = (float)(this.posZ - interpPosZ);
      float var17 = this.worldObj.getLightBrightness(new BlockPos(this));
      this.currentFootSteps.bindTexture(FOOTPRINT_TEXTURE);
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 771);
      worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      worldRenderer.pos(var14 - 0.125F, var15, var16 + 0.125F).tex(0.0, 1.0).color(var17, var17, var17, var12).endVertex();
      worldRenderer.pos(var14 + 0.125F, var15, var16 + 0.125F).tex(1.0, 1.0).color(var17, var17, var17, var12).endVertex();
      worldRenderer.pos(var14 + 0.125F, var15, var16 - 0.125F).tex(1.0, 0.0).color(var17, var17, var17, var12).endVertex();
      worldRenderer.pos(var14 - 0.125F, var15, var16 - 0.125F).tex(0.0, 0.0).color(var17, var17, var17, var12).endVertex();
      Tessellator.getInstance().draw();
      GlStateManager.disableBlend();
      GlStateManager.enableLighting();
   }

   @Override
   public void onUpdate() {
      this.footstepAge++;
      if (this.footstepAge == this.footstepMaxAge) {
         this.setDead();
      }
   }

   @Override
   public int getFXLayer() {
      return 3;
   }

   @Override
   public boolean Method239() {
      return false;
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         return new EntityFootStepFX(Minecraft.getMinecraft().getTextureManager(), world, double3, double4, double5);
      }
   }
}
