package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityBreakingFX extends EntityFX {
   protected EntityBreakingFX(World world, double double2, double double3, double double4, Item item) {
      this(world, double2, double3, double4, item, 0);
   }

   protected EntityBreakingFX(
      World world, double double2, double double3, double double4, double double5, double double6, double double7, Item item, int integer
   ) {
      this(world, double2, double3, double4, item, integer);
      this.motionX *= 0.1F;
      this.motionY *= 0.1F;
      this.motionZ *= 0.1F;
      this.motionX += double5;
      this.motionY += double6;
      this.motionZ += double7;
   }

   protected EntityBreakingFX(World world, double double2, double double3, double double4, Item item, int integer) {
      super(world, double2, double3, double4, 0.0, 0.0, 0.0);
      this.setParticleIcon(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(item, integer));
      this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
      this.particleGravity = Blocks.snow.blockParticleGravity;
      this.particleScale /= 2.0F;
   }

   @Override
   public int getFXLayer() {
      return 1;
   }

   @Override
   public void renderParticle(WorldRenderer worldRenderer, Entity entity, float float3, float float4, float float5, float float6, float float7, float float8) {
      float var11 = (this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
      float var12 = var11 + 0.015609375F;
      float var13 = (this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
      float var14 = var13 + 0.015609375F;
      float var15 = 0.1F * this.particleScale;
      if (this.particleIcon != null) {
         var11 = this.particleIcon.getInterpolatedU(this.particleTextureJitterX / 4.0F * 16.0F);
         var12 = this.particleIcon.getInterpolatedU((this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F);
         var13 = this.particleIcon.getInterpolatedV(this.particleTextureJitterY / 4.0F * 16.0F);
         var14 = this.particleIcon.getInterpolatedV((this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F);
      }

      float var16 = (float)(this.prevPosX + (this.posX - this.prevPosX) * float3 - interpPosX);
      float var17 = (float)(this.prevPosY + (this.posY - this.prevPosY) * float3 - interpPosY);
      float var18 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * float3 - interpPosZ);
      int var19 = this.getBrightnessForRender(float3);
      int var20 = var19 >> 16 & 65535;
      int var21 = var19 & 65535;
      worldRenderer.pos(var16 - float4 * var15 - float7 * var15, var17 - float5 * var15, var18 - float6 * var15 - float8 * var15)
         .tex(var11, var14)
         .color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F)
         .lightmap(var20, var21)
         .endVertex();
      worldRenderer.pos(var16 - float4 * var15 + float7 * var15, var17 + float5 * var15, var18 - float6 * var15 + float8 * var15)
         .tex(var11, var13)
         .color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F)
         .lightmap(var20, var21)
         .endVertex();
      worldRenderer.pos(var16 + float4 * var15 + float7 * var15, var17 + float5 * var15, var18 + float6 * var15 + float8 * var15)
         .tex(var12, var13)
         .color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F)
         .lightmap(var20, var21)
         .endVertex();
      worldRenderer.pos(var16 + float4 * var15 - float7 * var15, var17 - float5 * var15, var18 + float6 * var15 - float8 * var15)
         .tex(var12, var14)
         .color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F)
         .lightmap(var20, var21)
         .endVertex();
   }

   @Override
   public boolean Method239() {
      return true;
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         int var16 = arr.length > 1 ? arr[1] : 0;
         return new EntityBreakingFX(world, double3, double4, double5, double6, double7, double8, Item.getItemById(arr[0]), var16);
      }
   }

   public static class SlimeFactory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         return new EntityBreakingFX(world, double3, double4, double5, Items.slime_ball);
      }
   }

   public static class SnowballFactory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         return new EntityBreakingFX(world, double3, double4, double5, Items.snowball);
      }
   }
}
