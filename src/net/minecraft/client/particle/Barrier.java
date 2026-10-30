package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class Barrier extends EntityFX {
   protected Barrier(World world, double double2, double double3, double double4, Item item) {
      super(world, double2, double3, double4, 0.0, 0.0, 0.0);
      this.setParticleIcon(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(item));
      this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
      this.motionX = this.motionY = this.motionZ = 0.0;
      this.particleGravity = 0.0F;
      this.particleMaxAge = 80;
   }

   @Override
   public int getFXLayer() {
      return 1;
   }

   @Override
   public void renderParticle(WorldRenderer worldRenderer, Entity entity, float float3, float float4, float float5, float float6, float float7, float float8) {
      float var11 = this.particleIcon.getMinU();
      float var12 = this.particleIcon.getMaxU();
      float var13 = this.particleIcon.getMinV();
      float var14 = this.particleIcon.getMaxV();
      float var16 = (float)(this.prevPosX + (this.posX - this.prevPosX) * float3 - interpPosX);
      float var17 = (float)(this.prevPosY + (this.posY - this.prevPosY) * float3 - interpPosY);
      float var18 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * float3 - interpPosZ);
      int var19 = this.getBrightnessForRender(float3);
      int var20 = var19 >> 16 & 65535;
      int var21 = var19 & 65535;
      worldRenderer.pos(var16 - float4 * 0.5F - float7 * 0.5F, var17 - float5 * 0.5F, var18 - float6 * 0.5F - float8 * 0.5F)
         .tex(var12, var14)
         .color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F)
         .lightmap(var20, var21)
         .endVertex();
      worldRenderer.pos(var16 - float4 * 0.5F + float7 * 0.5F, var17 + float5 * 0.5F, var18 - float6 * 0.5F + float8 * 0.5F)
         .tex(var12, var13)
         .color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F)
         .lightmap(var20, var21)
         .endVertex();
      worldRenderer.pos(var16 + float4 * 0.5F + float7 * 0.5F, var17 + float5 * 0.5F, var18 + float6 * 0.5F + float8 * 0.5F)
         .tex(var11, var13)
         .color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F)
         .lightmap(var20, var21)
         .endVertex();
      worldRenderer.pos(var16 + float4 * 0.5F - float7 * 0.5F, var17 - float5 * 0.5F, var18 + float6 * 0.5F - float8 * 0.5F)
         .tex(var11, var14)
         .color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F)
         .lightmap(var20, var21)
         .endVertex();
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         return new Barrier(world, double3, double4, double5, Item.getItemFromBlock(Blocks.barrier));
      }
   }
}
