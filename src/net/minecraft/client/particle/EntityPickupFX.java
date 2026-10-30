package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.src.Config;
import net.minecraft.world.World;
import net.optifine.shaders.Program;
import net.optifine.shaders.Shaders;

public class EntityPickupFX extends EntityFX {
   private final Entity field_174840_a;
   private final Entity field_174843_ax;
   private int age;
   private final int maxAge;
   private final float field_174841_aA;
   private final RenderManager field_174842_aB = Minecraft.getMinecraft().getRenderManager();

   public EntityPickupFX(World world, Entity entity2, Entity entity3, float float4) {
      super(world, entity2.posX, entity2.posY, entity2.posZ, entity2.motionX, entity2.motionY, entity2.motionZ);
      this.field_174840_a = entity2;
      this.field_174843_ax = entity3;
      this.maxAge = 3;
      this.field_174841_aA = float4;
   }

   @Override
   public void renderParticle(WorldRenderer worldRenderer, Entity entity, float float3, float float4, float float5, float float6, float float7, float float8) {
      Program var11 = null;
      if (Config.isShaders()) {
         var11 = Shaders.activeProgram;
         Shaders.nextEntity(this.field_174840_a);
      }

      float var12 = (this.age + float3) / this.maxAge;
      var12 *= var12;
      double var13 = this.field_174840_a.posX;
      double var15 = this.field_174840_a.posY;
      double var17 = this.field_174840_a.posZ;
      double var19 = this.field_174843_ax.lastTickPosX + (this.field_174843_ax.posX - this.field_174843_ax.lastTickPosX) * float3;
      double var21 = this.field_174843_ax.lastTickPosY + (this.field_174843_ax.posY - this.field_174843_ax.lastTickPosY) * float3 + this.field_174841_aA;
      double var23 = this.field_174843_ax.lastTickPosZ + (this.field_174843_ax.posZ - this.field_174843_ax.lastTickPosZ) * float3;
      double var25 = var13 + (var19 - var13) * var12;
      double var27 = var15 + (var21 - var15) * var12;
      double var29 = var17 + (var23 - var17) * var12;
      int var31 = this.getBrightnessForRender(float3);
      int var32 = var31 % 65536;
      int var33 = var31 / 65536;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var32, var33);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      var25 -= interpPosX;
      var27 -= interpPosY;
      var29 -= interpPosZ;
      this.field_174842_aB.renderEntityWithPosYaw(this.field_174840_a, (float)var25, (float)var27, (float)var29, this.field_174840_a.rotationYaw, float3);
      if (Config.isShaders()) {
         Shaders.setEntityId(null);
         Shaders.useProgram(var11);
      }
   }

   @Override
   public void onUpdate() {
      this.age++;
      if (this.age == this.maxAge) {
         this.setDead();
      }
   }

   @Override
   public int getFXLayer() {
      return 3;
   }
}
