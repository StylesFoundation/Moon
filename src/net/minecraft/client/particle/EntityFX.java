package net.minecraft.client.particle;

import com.moon.Moon;
import com.moon.module.impl.visual.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFX extends Entity {
   protected int particleTextureIndexX;
   protected int particleTextureIndexY;
   protected float particleTextureJitterX;
   protected float particleTextureJitterY;
   protected int particleAge;
   protected int particleMaxAge;
   protected float particleScale;
   protected float particleGravity;
   protected float particleRed;
   protected float particleGreen;
   protected float particleBlue;
   protected float particleAlpha = 1.0F;
   protected TextureAtlasSprite particleIcon;
   public static double interpPosX;
   public static double interpPosY;
   public static double interpPosZ;

   protected EntityFX(World world, double double2, double double3, double double4) {
      super(world);
      this.setSize(0.2F, 0.2F);
      this.setPosition(double2, double3, double4);
      this.lastTickPosX = this.prevPosX = double2;
      this.lastTickPosY = this.prevPosY = double3;
      this.lastTickPosZ = this.prevPosZ = double4;
      this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
      this.particleTextureJitterX = this.rand.nextFloat() * 3.0F;
      this.particleTextureJitterY = this.rand.nextFloat() * 3.0F;
      this.particleScale = (this.rand.nextFloat() * 0.5F + 0.5F) * 2.0F;
      this.particleMaxAge = (int)(4.0F / (this.rand.nextFloat() * 0.9F + 0.1F));
      this.particleAge = 0;
   }

   public EntityFX(World world, double double2, double double3, double double4, double double5, double double6, double double7) {
      this(world, double2, double3, double4);
      this.motionX = double5 + (Math.random() * 2.0 - 1.0) * 0.4F;
      this.motionY = double6 + (Math.random() * 2.0 - 1.0) * 0.4F;
      this.motionZ = double7 + (Math.random() * 2.0 - 1.0) * 0.4F;
      float var14 = (float)(Math.random() + Math.random() + 1.0) * 0.15F;
      float var15 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
      this.motionX = this.motionX / var15 * var14 * 0.4F;
      this.motionY = this.motionY / var15 * var14 * 0.4F + 0.1F;
      this.motionZ = this.motionZ / var15 * var14 * 0.4F;
   }

   public EntityFX multiplyVelocity(float float1) {
      this.motionX *= float1;
      this.motionY = (this.motionY - 0.1F) * float1 + 0.1F;
      this.motionZ *= float1;
      return this;
   }

   public EntityFX multipleParticleScaleBy(float float1) {
      this.setSize(0.2F * float1, 0.2F * float1);
      this.particleScale *= float1;
      return this;
   }

   public void setRBGColorF(float float1, float float2, float float3) {
      this.particleRed = float1;
      this.particleGreen = float2;
      this.particleBlue = float3;
   }

   public void setAlphaF(float float1) {
      if (this.particleAlpha == 1.0F && float1 < 1.0F) {
         Minecraft.getMinecraft().effectRenderer.moveToAlphaLayer(this);
      } else if (this.particleAlpha < 1.0F && float1 == 1.0F) {
         Minecraft.getMinecraft().effectRenderer.moveToNoAlphaLayer(this);
      }

      this.particleAlpha = float1;
   }

   public float getRedColorF() {
      return this.particleRed;
   }

   public float getGreenColorF() {
      return this.particleGreen;
   }

   public float getBlueColorF() {
      return this.particleBlue;
   }

   public float getAlpha() {
      return this.particleAlpha;
   }

   @Override
   protected boolean canTriggerWalking() {
      return false;
   }

   @Override
   protected void entityInit() {
   }

   @Override
   public void onUpdate() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      if (this.particleAge++ >= this.particleMaxAge) {
         this.setDead();
      }

      this.motionY = this.motionY - 0.04 * this.particleGravity;
      this.moveEntity(this.motionX, this.motionY, this.motionZ);
      this.motionX *= 0.98F;
      this.motionY *= 0.98F;
      this.motionZ *= 0.98F;
      if (this.onGround) {
         this.motionX *= 0.7F;
         this.motionZ *= 0.7F;
      }
   }

   public void renderParticle(WorldRenderer worldRenderer, Entity entity, float float3, float float4, float float5, float float6, float float7, float float8) {
      float var11 = this.particleTextureIndexX / 16.0F;
      float var12 = var11 + 0.0624375F;
      float var13 = this.particleTextureIndexY / 16.0F;
      float var14 = var13 + 0.0624375F;
      float var15 = 0.1F * this.particleScale;
      if (this.particleIcon != null) {
         var11 = this.particleIcon.getMinU();
         var12 = this.particleIcon.getMaxU();
         var13 = this.particleIcon.getMinV();
         var14 = this.particleIcon.getMaxV();
      }

      float var16 = (float)(this.prevPosX + (this.posX - this.prevPosX) * float3 - interpPosX);
      float var17 = (float)(this.prevPosY + (this.posY - this.prevPosY) * float3 - interpPosY);
      float var18 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * float3 - interpPosZ);
      if (aB == null) {
         aB = Moon.getInstance().getModuleManager().getModule(Camera.class);
      }

      int var19 = aB.disableParticleLighting.getValue() ? 15728880 : this.getBrightnessForRender(float3);
      int var20 = var19 >> 16 & 65535;
      int var21 = var19 & 65535;
      worldRenderer.pos(var16 - float4 * var15 - float7 * var15, var17 - float5 * var15, var18 - float6 * var15 - float8 * var15)
         .tex(var12, var14)
         .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
         .lightmap(var20, var21)
         .endVertex();
      worldRenderer.pos(var16 - float4 * var15 + float7 * var15, var17 + float5 * var15, var18 - float6 * var15 + float8 * var15)
         .tex(var12, var13)
         .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
         .lightmap(var20, var21)
         .endVertex();
      worldRenderer.pos(var16 + float4 * var15 + float7 * var15, var17 + float5 * var15, var18 + float6 * var15 + float8 * var15)
         .tex(var11, var13)
         .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
         .lightmap(var20, var21)
         .endVertex();
      worldRenderer.pos(var16 + float4 * var15 - float7 * var15, var17 - float5 * var15, var18 + float6 * var15 - float8 * var15)
         .tex(var11, var14)
         .color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha)
         .lightmap(var20, var21)
         .endVertex();
   }

   public int getFXLayer() {
      return 0;
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
   }

   public void setParticleIcon(TextureAtlasSprite textureAtlasSprite) {
      int var2 = this.getFXLayer();
      if (var2 == 1) {
         this.particleIcon = textureAtlasSprite;
      } else {
         throw new RuntimeException("Invalid call to Particle.setTex, use coordinate methods");
      }
   }

   public void setParticleTextureIndex(int integer) {
      if (this.getFXLayer() != 0) {
         throw new RuntimeException("Invalid call to Particle.setMiscTex");
      }

      this.particleTextureIndexX = integer % 16;
      this.particleTextureIndexY = integer / 16;
   }

   public void nextTextureIndexX() {
      this.particleTextureIndexX++;
   }

   @Override
   public boolean canAttackWithItem() {
      return false;
   }

   @Override
   public String toString() {
      return this.getClass().getSimpleName()
         + ", Pos ("
         + this.posX
         + ","
         + this.posY
         + ","
         + this.posZ
         + "), RGBA ("
         + this.particleRed
         + ","
         + this.particleGreen
         + ","
         + this.particleBlue
         + ","
         + this.particleAlpha
         + "), Age "
         + this.particleAge;
   }

   public boolean Method239() {
      return true;
   }
}
