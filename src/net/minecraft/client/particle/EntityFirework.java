package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemDye;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFirework {
   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         EntityFirework.SparkFX var16 = new EntityFirework.SparkFX(
            world, double3, double4, double5, double6, double7, double8, Minecraft.getMinecraft().effectRenderer
         );
         var16.setAlphaF(0.99F);
         return var16;
      }
   }

   public static class OverlayFX extends EntityFX {
      protected OverlayFX(World world, double double2, double double3, double double4) {
         super(world, double2, double3, double4);
         this.particleMaxAge = 4;
      }

      @Override
      public void renderParticle(WorldRenderer worldRenderer, Entity entity, float float3, float float4, float float5, float float6, float float7, float float8) {
         float var15 = 7.1F * MathHelper.sin((this.particleAge + float3 - 1.0F) * 0.25F * (float) Math.PI);
         this.particleAlpha = 0.6F - (this.particleAge + float3 - 1.0F) * 0.25F * 0.5F;
         float var16 = (float)(this.prevPosX + (this.posX - this.prevPosX) * float3 - interpPosX);
         float var17 = (float)(this.prevPosY + (this.posY - this.prevPosY) * float3 - interpPosY);
         float var18 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * float3 - interpPosZ);
         int var19 = this.getBrightnessForRender(float3);
         int var20 = var19 >> 16 & 65535;
         int var21 = var19 & 65535;
         worldRenderer.pos(var16 - float4 * var15 - float7 * var15, var17 - float5 * var15, var18 - float6 * var15 - float8 * var15)
            .tex(0.5, 0.375)
            .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
            .lightmap(var20, var21)
            .endVertex();
         worldRenderer.pos(var16 - float4 * var15 + float7 * var15, var17 + float5 * var15, var18 - float6 * var15 + float8 * var15)
            .tex(0.5, 0.125)
            .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
            .lightmap(var20, var21)
            .endVertex();
         worldRenderer.pos(var16 + float4 * var15 + float7 * var15, var17 + float5 * var15, var18 + float6 * var15 + float8 * var15)
            .tex(0.25, 0.125)
            .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
            .lightmap(var20, var21)
            .endVertex();
         worldRenderer.pos(var16 + float4 * var15 - float7 * var15, var17 - float5 * var15, var18 + float6 * var15 - float8 * var15)
            .tex(0.25, 0.375)
            .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
            .lightmap(var20, var21)
            .endVertex();
      }
   }

   public static class SparkFX extends EntityFX {
      private final int aV = 160;
      private boolean trail;
      private boolean twinkle;
      private final EffectRenderer field_92047_az;
      private float fadeColourRed;
      private float fadeColourGreen;
      private float fadeColourBlue;
      private boolean hasFadeColour;

      public SparkFX(World world, double double2, double double3, double double4, double double5, double double6, double double7, EffectRenderer effectRenderer) {
         super(world, double2, double3, double4);
         this.motionX = double5;
         this.motionY = double6;
         this.motionZ = double7;
         this.field_92047_az = effectRenderer;
         this.particleScale *= 0.75F;
         this.particleMaxAge = 48 + this.rand.nextInt(12);
         this.noClip = false;
      }

      public void setTrail(boolean boolean1) {
         this.trail = boolean1;
      }

      public void setTwinkle(boolean boolean1) {
         this.twinkle = boolean1;
      }

      public void setColour(int integer) {
         float var4 = ((integer & 0xFF0000) >> 16) / 255.0F;
         float var5 = ((integer & 0xFF00) >> 8) / 255.0F;
         float var6 = (integer & 0xFF) / 255.0F;
         this.setRBGColorF(var4 * 1.0F, var5 * 1.0F, var6 * 1.0F);
      }

      public void setFadeColour(int integer) {
         this.fadeColourRed = ((integer & 0xFF0000) >> 16) / 255.0F;
         this.fadeColourGreen = ((integer & 0xFF00) >> 8) / 255.0F;
         this.fadeColourBlue = (integer & 0xFF) / 255.0F;
         this.hasFadeColour = true;
      }

      @Override
      public AxisAlignedBB getCollisionBoundingBox() {
         return null;
      }

      @Override
      public boolean canBePushed() {
         return false;
      }

      @Override
      public void renderParticle(WorldRenderer worldRenderer, Entity entity, float float3, float float4, float float5, float float6, float float7, float float8) {
         if (!this.twinkle || this.particleAge < this.particleMaxAge / 3 || (this.particleAge + this.particleMaxAge) / 3 % 2 == 0) {
            super.renderParticle(worldRenderer, entity, float3, float4, float5, float6, float7, float8);
         }
      }

      @Override
      public void onUpdate() {
         this.prevPosX = this.posX;
         this.prevPosY = this.posY;
         this.prevPosZ = this.posZ;
         if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
         }

         if (this.particleAge > this.particleMaxAge / 2) {
            this.setAlphaF(1.0F - ((float)this.particleAge - this.particleMaxAge / 2) / this.particleMaxAge);
            if (this.hasFadeColour) {
               this.particleRed = this.particleRed + (this.fadeColourRed - this.particleRed) * 0.2F;
               this.particleGreen = this.particleGreen + (this.fadeColourGreen - this.particleGreen) * 0.2F;
               this.particleBlue = this.particleBlue + (this.fadeColourBlue - this.particleBlue) * 0.2F;
            }
         }

         this.setParticleTextureIndex(160 + (7 - this.particleAge * 8 / this.particleMaxAge));
         this.motionY -= 0.004;
         this.moveEntity(this.motionX, this.motionY, this.motionZ);
         this.motionX *= 0.91F;
         this.motionY *= 0.91F;
         this.motionZ *= 0.91F;
         if (this.onGround) {
            this.motionX *= 0.7F;
            this.motionZ *= 0.7F;
         }

         if (this.trail && this.particleAge < this.particleMaxAge / 2 && (this.particleAge + this.particleMaxAge) % 2 == 0) {
            EntityFirework.SparkFX var3 = new EntityFirework.SparkFX(this.worldObj, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0, this.field_92047_az);
            var3.setAlphaF(0.99F);
            var3.setRBGColorF(this.particleRed, this.particleGreen, this.particleBlue);
            var3.particleAge = var3.particleMaxAge / 2;
            if (this.hasFadeColour) {
               var3.hasFadeColour = true;
               var3.fadeColourRed = this.fadeColourRed;
               var3.fadeColourGreen = this.fadeColourGreen;
               var3.fadeColourBlue = this.fadeColourBlue;
            }

            var3.twinkle = this.twinkle;
            this.field_92047_az.addEffect(var3);
         }
      }

      @Override
      public int getBrightnessForRender(float float1) {
         return 15728880;
      }

      @Override
      public float getBrightness(float float1) {
         return 1.0F;
      }

      @Override
      public boolean Method239() {
         return true;
      }
   }

   public static class StarterFX extends EntityFX {
      private int fireworkAge;
      private final EffectRenderer theEffectRenderer;
      private NBTTagList fireworkExplosions;
      boolean twinkle;

      public StarterFX(
         World world,
         double double2,
         double double3,
         double double4,
         double double5,
         double double6,
         double double7,
         EffectRenderer effectRenderer,
         NBTTagCompound nBTTagCompound
      ) {
         super(world, double2, double3, double4, 0.0, 0.0, 0.0);
         this.motionX = double5;
         this.motionY = double6;
         this.motionZ = double7;
         this.theEffectRenderer = effectRenderer;
         this.particleMaxAge = 8;
         if (nBTTagCompound != null) {
            this.fireworkExplosions = nBTTagCompound.getTagList("Explosions", 10);
            if (this.fireworkExplosions.tagCount() == 0) {
               this.fireworkExplosions = null;
            } else {
               this.particleMaxAge = this.fireworkExplosions.tagCount() * 2 - 1;

               for (int var18 = 0; var18 < this.fireworkExplosions.tagCount(); var18++) {
                  NBTTagCompound var19 = this.fireworkExplosions.getCompoundTagAt(var18);
                  if (var19.getBoolean("Flicker")) {
                     this.twinkle = true;
                     this.particleMaxAge += 15;
                     break;
                  }
               }
            }
         }
      }

      @Override
      public void renderParticle(WorldRenderer worldRenderer, Entity entity, float float3, float float4, float float5, float float6, float float7, float float8) {
      }

      @Override
      public void onUpdate() {
         if (this.fireworkAge == 0 && this.fireworkExplosions != null) {
            boolean var3 = this.func_92037_i();
            boolean var4 = false;
            if (this.fireworkExplosions.tagCount() >= 3) {
               var4 = true;
            } else {
               for (int var5 = 0; var5 < this.fireworkExplosions.tagCount(); var5++) {
                  if (this.fireworkExplosions.getCompoundTagAt(var5).getByte("Type") == 1) {
                     var4 = true;
                     break;
                  }
               }
            }

            String var19 = "fireworks." + (var4 ? "largeBlast" : "blast") + (var3 ? "_far" : "");
            this.worldObj.playSound(this.posX, this.posY, this.posZ, var19, 20.0F, 0.95F + this.rand.nextFloat() * 0.1F, true);
         }

         if (this.fireworkAge % 2 == 0 && this.fireworkExplosions != null && this.fireworkAge / 2 < this.fireworkExplosions.tagCount()) {
            int var15 = this.fireworkAge / 2;
            NBTTagCompound var17 = this.fireworkExplosions.getCompoundTagAt(var15);
            byte var20 = var17.getByte("Type");
            boolean var6 = var17.getBoolean("Trail");
            boolean var7 = var17.getBoolean("Flicker");
            int[] var8 = var17.getIntArray("Colors");
            int[] var9 = var17.getIntArray("FadeColors");
            if (var8.length == 0) {
               var8 = new int[]{ItemDye.dyeColors[0]};
            }

            if (var20 == 1) {
               this.createBall(0.5, 4, var8, var9, var6, var7);
            } else if (var20 == 2) {
               this.createShaped(
                  0.5,
                  new double[][]{
                     {0.0, 1.0},
                     {0.3455, 0.309},
                     {0.9511, 0.309},
                     {0.3795918367346939, -0.12653061224489795},
                     {0.6122448979591837, -0.8040816326530612},
                     {0.0, -0.35918367346938773}
                  },
                  var8,
                  var9,
                  var6,
                  var7,
                  false
               );
            } else if (var20 == 3) {
               this.createShaped(
                  0.5,
                  new double[][]{
                     {0.0, 0.2},
                     {0.2, 0.2},
                     {0.2, 0.6},
                     {0.6, 0.6},
                     {0.6, 0.2},
                     {0.2, 0.2},
                     {0.2, 0.0},
                     {0.4, 0.0},
                     {0.4, -0.6},
                     {0.2, -0.6},
                     {0.2, -0.4},
                     {0.0, -0.4}
                  },
                  var8,
                  var9,
                  var6,
                  var7,
                  true
               );
            } else if (var20 == 4) {
               this.createBurst(var8, var9, var6, var7);
            } else {
               this.createBall(0.25, 2, var8, var9, var6, var7);
            }

            int var10 = var8[0];
            float var11 = ((var10 & 0xFF0000) >> 16) / 255.0F;
            float var12 = ((var10 & 0xFF00) >> 8) / 255.0F;
            float var13 = (var10 & 0xFF) / 255.0F;
            EntityFirework.OverlayFX var14 = new EntityFirework.OverlayFX(this.worldObj, this.posX, this.posY, this.posZ);
            var14.setRBGColorF(var11, var12, var13);
            this.theEffectRenderer.addEffect(var14);
         }

         this.fireworkAge++;
         if (this.fireworkAge > this.particleMaxAge) {
            if (this.twinkle) {
               boolean var16 = this.func_92037_i();
               String var18 = "fireworks." + (var16 ? "twinkle_far" : "twinkle");
               this.worldObj.playSound(this.posX, this.posY, this.posZ, var18, 20.0F, 0.9F + this.rand.nextFloat() * 0.15F, true);
            }

            this.setDead();
         }
      }

      private boolean func_92037_i() {
         Minecraft var1 = Minecraft.getMinecraft();
         return var1 == null || var1.getRenderViewEntity() == null || var1.getRenderViewEntity().getDistanceSq(this.posX, this.posY, this.posZ) >= 256.0;
      }

      private void createParticle(
         double double1,
         double double2,
         double double3,
         double double4,
         double double5,
         double double6,
         int[] arr7,
         int[] arr8,
         boolean boolean9,
         boolean boolean10
      ) {
         EntityFirework.SparkFX var17 = new EntityFirework.SparkFX(this.worldObj, double1, double2, double3, double4, double5, double6, this.theEffectRenderer);
         var17.setAlphaF(0.99F);
         var17.setTrail(boolean9);
         var17.setTwinkle(boolean10);
         int var18 = this.rand.nextInt(arr7.length);
         var17.setColour(arr7[var18]);
         if (arr8 != null && arr8.length > 0) {
            var17.setFadeColour(arr8[this.rand.nextInt(arr8.length)]);
         }

         this.theEffectRenderer.addEffect(var17);
      }

      private void createBall(double double1, int integer, int[] arr3, int[] arr4, boolean boolean5, boolean boolean6) {
         double var8 = this.posX;
         double var10 = this.posY;
         double var12 = this.posZ;

         for (int var14 = -integer; var14 <= integer; var14++) {
            for (int var15 = -integer; var15 <= integer; var15++) {
               for (int var16 = -integer; var16 <= integer; var16++) {
                  double var17 = var15 + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5;
                  double var19 = var14 + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5;
                  double var21 = var16 + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5;
                  double var23 = MathHelper.sqrt_double(var17 * var17 + var19 * var19 + var21 * var21) / double1 + this.rand.nextGaussian() * 0.05;
                  this.createParticle(var8, var10, var12, var17 / var23, var19 / var23, var21 / var23, arr3, arr4, boolean5, boolean6);
                  if (var14 != -integer && var14 != integer && var15 != -integer && var15 != integer) {
                     var16 += integer * 2 - 1;
                  }
               }
            }
         }
      }

      private void createShaped(double double1, double[][] arr, int[] arr3, int[] arr4, boolean boolean5, boolean boolean6, boolean boolean7) {
         double var9 = arr[0][0];
         double var11 = arr[0][1];
         this.createParticle(this.posX, this.posY, this.posZ, var9 * double1, var11 * double1, 0.0, arr3, arr4, boolean5, boolean6);
         float var13 = this.rand.nextFloat() * (float) Math.PI;
         double var14 = boolean7 ? 0.034 : 0.34;

         for (int var16 = 0; var16 < 3; var16++) {
            double var17 = var13 + var16 * (float) Math.PI * var14;
            double var19 = var9;
            double var21 = var11;

            for (int var23 = 1; var23 < arr.length; var23++) {
               double var24 = arr[var23][0];
               double var26 = arr[var23][1];

               for (double var28 = 0.25; var28 <= 1.0; var28 += 0.25) {
                  double var30 = (var19 + (var24 - var19) * var28) * double1;
                  double var32 = (var21 + (var26 - var21) * var28) * double1;
                  double var34 = var30 * Math.sin(var17);
                  var30 *= Math.cos(var17);

                  for (double var36 = -1.0; var36 <= 1.0; var36 += 2.0) {
                     this.createParticle(this.posX, this.posY, this.posZ, var30 * var36, var32, var34 * var36, arr3, arr4, boolean5, boolean6);
                  }
               }

               var19 = var24;
               var21 = var26;
            }
         }
      }

      private void createBurst(int[] arr1, int[] arr2, boolean boolean3, boolean boolean4) {
         double var7 = this.rand.nextGaussian() * 0.05;
         double var9 = this.rand.nextGaussian() * 0.05;

         for (int var11 = 0; var11 < 70; var11++) {
            double var12 = this.motionX * 0.5 + this.rand.nextGaussian() * 0.15 + var7;
            double var14 = this.motionZ * 0.5 + this.rand.nextGaussian() * 0.15 + var9;
            double var16 = this.motionY * 0.5 + this.rand.nextDouble() * 0.5;
            this.createParticle(this.posX, this.posY, this.posZ, var12, var16, var14, arr1, arr2, boolean3, boolean4);
         }
      }

      @Override
      public int getFXLayer() {
         return 0;
      }
   }
}
