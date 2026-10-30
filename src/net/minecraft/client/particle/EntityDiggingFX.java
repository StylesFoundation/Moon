package net.minecraft.client.particle;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityDiggingFX extends EntityFX {
   private final IBlockState sourceState;
   private BlockPos sourcePos;

   protected EntityDiggingFX(
      World world, double double2, double double3, double double4, double double5, double double6, double double7, IBlockState iBlockState
   ) {
      super(world, double2, double3, double4, double5, double6, double7);
      this.sourceState = iBlockState;
      this.setParticleIcon(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(iBlockState));
      this.particleGravity = iBlockState.getBlock().blockParticleGravity;
      this.particleRed = this.particleGreen = this.particleBlue = 0.6F;
      this.particleScale /= 2.0F;
   }

   public EntityDiggingFX setBlockPos(BlockPos blockPos) {
      this.sourcePos = blockPos;
      if (this.sourceState.getBlock() == Blocks.grass) {
         return this;
      }

      int var4 = this.sourceState.getBlock().colorMultiplier(this.worldObj, blockPos);
      this.particleRed *= (var4 >> 16 & 0xFF) / 255.0F;
      this.particleGreen *= (var4 >> 8 & 0xFF) / 255.0F;
      this.particleBlue *= (var4 & 0xFF) / 255.0F;
      return this;
   }

   public EntityDiggingFX func_174845_l() {
      this.sourcePos = new BlockPos(this.posX, this.posY, this.posZ);
      Block var3 = this.sourceState.getBlock();
      if (var3 != Blocks.grass) {
         int var4 = var3.getRenderColor(this.sourceState);
         this.particleRed *= (var4 >> 16 & 0xFF) / 255.0F;
         this.particleGreen *= (var4 >> 8 & 0xFF) / 255.0F;
         this.particleBlue *= (var4 & 0xFF) / 255.0F;
      }

      return this;
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
   public int getBrightnessForRender(float float1) {
      int var2 = super.getBrightnessForRender(float1);
      int var3 = 0;
      if (this.worldObj.Method322(this.sourcePos)) {
         var3 = this.worldObj.getCombinedLight(this.sourcePos, 0);
      }

      return var2 == 0 ? var3 : var2;
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
         return new EntityDiggingFX(world, double3, double4, double5, double6, double7, double8, Block.getStateById(arr[0])).func_174845_l();
      }
   }
}
