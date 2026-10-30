package com.moon.module.impl.visual.targethud;

import com.moon.Moon;
import com.moon.module.impl.combat.KillAura;
import com.moon.module.impl.visual.TargetHUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public abstract class TargetHUDDesign {
   protected static final Minecraft minecraft = Minecraft.getMinecraft();
   private TargetHUD targetHud;
   private KillAura killAura;
   protected float x;
   protected float y;

   public abstract void onRender();

   public abstract void onShadowRender(boolean shadow);

   public abstract void onTick();

   protected float getHudX() {
      return this.targetHud.getXSetting().getValue();
   }

   protected float getHudY() {
      return this.targetHud.getYSetting().getValue();
   }

   protected EntityPlayer getTargetPlayer() {
      this.initKillAura();
      if (minecraft.currentScreen instanceof GuiChat) {
         return minecraft.thePlayer;
      } else {
         return this.killAura.getTargetSelector().getTarget() instanceof EntityPlayer player && this.killAura.isEnabled() ? player : null;
      }
   }

   public void renderEntity(float x, float y, int scale, float yaw, float pitch, EntityLivingBase entity) {
      GlStateManager.enableColorMaterial();
      GlStateManager.pushMatrix();
      GlStateManager.enableDepth();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 255.0F);
      GlStateManager.translate(x, y, 50.0F);
      GlStateManager.scale(-scale, scale, scale);
      GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
      float savedRenderYawOffset = entity.renderYawOffset;
      float savedRotationYaw = entity.rotationYaw;
      float savedRotationPitch = entity.rotationPitch;
      float savedPrevRotationYawHead = entity.prevRotationYawHead;
      float savedRotationYawHead = entity.rotationYawHead;
      GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
      RenderHelper.enableStandardItemLighting();
      GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-((float)Math.atan(pitch / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
      entity.renderYawOffset = yaw;
      entity.rotationYaw = yaw;
      entity.rotationPitch = -pitch;
      entity.rotationYawHead = yaw;
      entity.prevRotationYawHead = yaw;
      GlStateManager.translate(0.0F, 0.0F, 0.0F);
      RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
      renderManager.setPlayerViewY(180.0F);
      renderManager.setRenderShadow(false);
      renderManager.renderEntityWithPosYaw(entity, 0.0, 0.0, 0.0, 0.0F, 1.0F);
      renderManager.setRenderShadow(true);
      entity.renderYawOffset = savedRenderYawOffset;
      entity.rotationYaw = savedRotationYaw;
      entity.rotationPitch = savedRotationPitch;
      entity.prevRotationYawHead = savedPrevRotationYawHead;
      entity.rotationYawHead = savedRotationYawHead;
      GlStateManager.popMatrix();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableRescaleNormal();
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GlStateManager.disableTexture2D();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
   }

   private void initKillAura() {
      if (this.killAura == null) {
         this.killAura = Moon.getInstance().getModuleManager().getModule(KillAura.class);
      }
   }

   public TargetHUD getTargetHud() {
      return this.targetHud;
   }

   public KillAura getKillAura() {
      return this.killAura;
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   public TargetHUDDesign setTargetHud(TargetHUD targetHud) {
      this.targetHud = targetHud;
      return this;
   }

   public TargetHUDDesign setKillAura(KillAura killAura) {
      this.killAura = killAura;
      return this;
   }

   public TargetHUDDesign setX(float x) {
      this.x = x;
      return this;
   }

   public TargetHUDDesign setY(float y) {
      this.y = y;
      return this;
   }
}
