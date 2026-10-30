package net.optifine.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class CloudRenderer {
   private final Minecraft mc;
   private boolean updated = false;
   private boolean renderFancy = false;
   int cloudTickCounter;
   private Vec3 cloudColor;
   float partialTicks;
   private boolean updateRenderFancy = false;
   private int updateCloudTickCounter = 0;
   private Vec3 updateCloudColor = new Vec3(-1.0, -1.0, -1.0);
   private double updatePlayerX = 0.0;
   private double updatePlayerY = 0.0;
   private double updatePlayerZ = 0.0;
   private int glListClouds = -1;

   public CloudRenderer(Minecraft minecraft) {
      this.mc = minecraft;
      this.glListClouds = GLAllocation.generateDisplayLists(1);
   }

   public void prepareToRender(boolean boolean1, int integer, float float3, Vec3 vec3) {
      this.renderFancy = boolean1;
      this.cloudTickCounter = integer;
      this.partialTicks = float3;
      this.cloudColor = vec3;
   }

   public boolean shouldUpdateGlList() {
      if (!this.updated) {
         return true;
      }

      if (this.renderFancy != this.updateRenderFancy) {
         return true;
      }

      if (this.cloudTickCounter >= this.updateCloudTickCounter + 20) {
         return true;
      }

      if (Math.abs(this.cloudColor.xCoord - this.updateCloudColor.xCoord) > 0.003) {
         return true;
      }

      if (Math.abs(this.cloudColor.yCoord - this.updateCloudColor.yCoord) > 0.003) {
         return true;
      }

      if (Math.abs(this.cloudColor.zCoord - this.updateCloudColor.zCoord) > 0.003) {
         return true;
      }

      Entity var4 = this.mc.getRenderViewEntity();
      boolean var5 = this.updatePlayerY + var4.getEyeHeight() < 128.0 + this.mc.gameSettings.ofCloudsHeight * 128.0F;
      boolean var6 = var4.prevPosY + var4.getEyeHeight() < 128.0 + this.mc.gameSettings.ofCloudsHeight * 128.0F;
      return var6 != var5;
   }

   public void startUpdateGlList() {
      GL11.glNewList(this.glListClouds, GL11.GL_COMPILE);
   }

   public void endUpdateGlList() {
      GL11.glEndList();
      this.updateRenderFancy = this.renderFancy;
      this.updateCloudTickCounter = this.cloudTickCounter;
      this.updateCloudColor = this.cloudColor;
      this.updatePlayerX = this.mc.getRenderViewEntity().prevPosX;
      this.updatePlayerY = this.mc.getRenderViewEntity().prevPosY;
      this.updatePlayerZ = this.mc.getRenderViewEntity().prevPosZ;
      this.updated = true;
      GlStateManager.resetColor();
   }

   public void renderGlList() {
      Entity var4 = this.mc.getRenderViewEntity();
      double var5 = var4.prevPosX + (var4.posX - var4.prevPosX) * this.partialTicks;
      double var7 = var4.prevPosY + (var4.posY - var4.prevPosY) * this.partialTicks;
      double var9 = var4.prevPosZ + (var4.posZ - var4.prevPosZ) * this.partialTicks;
      double var11 = this.cloudTickCounter - this.updateCloudTickCounter + this.partialTicks;
      float var13 = (float)(var5 - this.updatePlayerX + var11 * 0.03);
      float var14 = (float)(var7 - this.updatePlayerY);
      float var15 = (float)(var9 - this.updatePlayerZ);
      GlStateManager.pushMatrix();
      if (this.renderFancy) {
         GlStateManager.translate(-var13 / 12.0F, -var14, -var15 / 12.0F);
      } else {
         GlStateManager.translate(-var13, -var14, -var15);
      }

      GlStateManager.callList(this.glListClouds);
      GlStateManager.popMatrix();
      GlStateManager.resetColor();
   }

   public void reset() {
      this.updated = false;
   }
}
