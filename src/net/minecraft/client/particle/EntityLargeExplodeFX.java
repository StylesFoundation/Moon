package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityLargeExplodeFX extends EntityFX {
   private static final ResourceLocation EXPLOSION_TEXTURE = new ResourceLocation("textures/entity/explosion.png");
   private static final VertexFormat field_181549_az = new VertexFormat()
      .addElement(DefaultVertexFormats.POSITION_3F)
      .addElement(DefaultVertexFormats.TEX_2F)
      .addElement(DefaultVertexFormats.COLOR_4UB)
      .addElement(DefaultVertexFormats.TEX_2S)
      .addElement(DefaultVertexFormats.NORMAL_3B)
      .addElement(DefaultVertexFormats.PADDING_1B);
   private int field_70581_a;
   private final int field_70584_aq;
   private final TextureManager theRenderEngine;
   private final float field_70582_as;

   protected EntityLargeExplodeFX(
      TextureManager textureManager, World world, double double3, double double4, double double5, double double6, double double7, double double8
   ) {
      super(world, double3, double4, double5, 0.0, 0.0, 0.0);
      this.theRenderEngine = textureManager;
      this.field_70584_aq = 6 + this.rand.nextInt(4);
      this.particleRed = this.particleGreen = this.particleBlue = this.rand.nextFloat() * 0.6F + 0.4F;
      this.field_70582_as = 1.0F - (float)double6 * 0.5F;
   }

   @Override
   public void renderParticle(WorldRenderer worldRenderer, Entity entity, float float3, float float4, float float5, float float6, float float7, float float8) {
      int var11 = (int)((this.field_70581_a + float3) * 15.0F / this.field_70584_aq);
      if (var11 <= 15) {
         this.theRenderEngine.bindTexture(EXPLOSION_TEXTURE);
         float var12 = var11 % 4 / 4.0F;
         float var13 = var12 + 0.24975F;
         float var14 = var11 / 4 / 4.0F;
         float var15 = var14 + 0.24975F;
         float var16 = 2.0F * this.field_70582_as;
         float var17 = (float)(this.prevPosX + (this.posX - this.prevPosX) * float3 - interpPosX);
         float var18 = (float)(this.prevPosY + (this.posY - this.prevPosY) * float3 - interpPosY);
         float var19 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * float3 - interpPosZ);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.disableLighting();
         RenderHelper.disableStandardItemLighting();
         worldRenderer.begin(7, field_181549_az);
         worldRenderer.pos(var17 - float4 * var16 - float7 * var16, var18 - float5 * var16, var19 - float6 * var16 - float8 * var16)
            .tex(var13, var15)
            .color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F)
            .lightmap(0, 240)
            .normal(0.0F, 1.0F, 0.0F)
            .endVertex();
         worldRenderer.pos(var17 - float4 * var16 + float7 * var16, var18 + float5 * var16, var19 - float6 * var16 + float8 * var16)
            .tex(var13, var14)
            .color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F)
            .lightmap(0, 240)
            .normal(0.0F, 1.0F, 0.0F)
            .endVertex();
         worldRenderer.pos(var17 + float4 * var16 + float7 * var16, var18 + float5 * var16, var19 + float6 * var16 + float8 * var16)
            .tex(var12, var14)
            .color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F)
            .lightmap(0, 240)
            .normal(0.0F, 1.0F, 0.0F)
            .endVertex();
         worldRenderer.pos(var17 + float4 * var16 - float7 * var16, var18 - float5 * var16, var19 + float6 * var16 - float8 * var16)
            .tex(var12, var15)
            .color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F)
            .lightmap(0, 240)
            .normal(0.0F, 1.0F, 0.0F)
            .endVertex();
         Tessellator.getInstance().draw();
         GlStateManager.enableLighting();
      }
   }

   @Override
   public int getBrightnessForRender(float float1) {
      return 61680;
   }

   @Override
   public void onUpdate() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      this.field_70581_a++;
      if (this.field_70581_a == this.field_70584_aq) {
         this.setDead();
      }
   }

   @Override
   public int getFXLayer() {
      return 3;
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         return new EntityLargeExplodeFX(Minecraft.getMinecraft().getTextureManager(), world, double3, double4, double5, double6, double7, double8);
      }
   }
}
