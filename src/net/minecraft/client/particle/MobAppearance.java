package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MobAppearance extends EntityFX {
   private EntityLivingBase entity;

   protected MobAppearance(World world, double double2, double double3, double double4) {
      super(world, double2, double3, double4, 0.0, 0.0, 0.0);
      this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
      this.motionX = this.motionY = this.motionZ = 0.0;
      this.particleGravity = 0.0F;
      this.particleMaxAge = 30;
   }

   @Override
   public int getFXLayer() {
      return 3;
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.entity == null) {
         EntityGuardian var1 = new EntityGuardian(this.worldObj);
         var1.setElder();
         this.entity = var1;
      }
   }

   @Override
   public void renderParticle(WorldRenderer worldRenderer, Entity entity, float float3, float float4, float float5, float float6, float float7, float float8) {
      if (this.entity != null) {
         RenderManager var11 = Minecraft.getMinecraft().getRenderManager();
         var11.setRenderPosition(EntityFX.interpPosX, EntityFX.interpPosY, EntityFX.interpPosZ);
         float var13 = (this.particleAge + float3) / this.particleMaxAge;
         GlStateManager.depthMask(true);
         GlStateManager.enableBlend();
         GlStateManager.enableDepth();
         GlStateManager.blendFunc(770, 771);
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
         GlStateManager.pushMatrix();
         float var15 = 0.05F + 0.5F * MathHelper.sin(var13 * (float) Math.PI);
         GlStateManager.color(1.0F, 1.0F, 1.0F, var15);
         GlStateManager.translate(0.0F, 1.8F, 0.0F);
         GlStateManager.rotate(180.0F - entity.rotationYaw, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(60.0F - 150.0F * var13 - entity.rotationPitch, 1.0F, 0.0F, 0.0F);
         GlStateManager.translate(0.0F, -0.4F, -1.5F);
         GlStateManager.scale(0.42553192F, 0.42553192F, 0.42553192F);
         this.entity.rotationYaw = this.entity.prevRotationYaw = 0.0F;
         this.entity.rotationYawHead = this.entity.prevRotationYawHead = 0.0F;
         var11.renderEntityWithPosYaw(this.entity, 0.0, 0.0, 0.0, 0.0F, float3);
         GlStateManager.popMatrix();
         GlStateManager.enableDepth();
      }
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         return new MobAppearance(world, double3, double4, double5);
      }
   }
}
