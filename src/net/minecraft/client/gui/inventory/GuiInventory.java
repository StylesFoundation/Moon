package net.minecraft.client.gui.inventory;

import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class GuiInventory extends InventoryEffectRenderer {
   private float oldMouseX;
   private float oldMouseY;

   public GuiInventory(EntityPlayer entityPlayer) {
      super(entityPlayer.inventoryContainer);
      this.allowUserInput = true;
   }

   @Override
   public void onUpdateScreen() {
      if (this.mc.playerController.isInCreativeMode()) {
         this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.thePlayer));
      }

      this.updateActivePotionEffects();
   }

   @Override
   public void onInitGui() {
      this.m.clear();
      if (this.mc.playerController.isInCreativeMode()) {
         this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.thePlayer));
      } else {
         super.onInitGui();
      }
   }

   @Override
   protected void drawGuiContainerForegroundLayer(int integer1, int integer2) {
      this.fontRendererObj.drawString(I18n.format("container.crafting"), 86.0F, 16.0F, 4210752);
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      super.onDrawScreen(mouseX, mouseY, partialTicks);
      this.oldMouseX = mouseX;
      this.oldMouseY = mouseY;
   }

   @Override
   protected void drawGuiContainerBackgroundLayer(float float1, int integer2, int integer3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(inventoryBackground);
      int var6 = this.guiLeft;
      int var7 = this.guiTop;
      this.drawTexturedModalRect(var6, var7, 0, 0, this.xSize, this.ySize);
      drawEntityOnScreen(var6 + 51, var7 + 75, 30, var6 + 51 - this.oldMouseX, var7 + 75 - 50 - this.oldMouseY, this.mc.thePlayer);
   }

   public static void drawEntityOnScreen(int integer1, int integer2, int integer3, float float4, float float5, EntityLivingBase entityLivingBase) {
      GlStateManager.enableColorMaterial();
      GlStateManager.pushMatrix();
      GlStateManager.translate(integer1, integer2, 50.0F);
      GlStateManager.scale(-integer3, integer3, integer3);
      GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
      float var6 = entityLivingBase.renderYawOffset;
      float var7 = entityLivingBase.rotationYaw;
      float var8 = entityLivingBase.rotationPitch;
      float var9 = entityLivingBase.prevRotationYawHead;
      float var10 = entityLivingBase.rotationYawHead;
      GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
      RenderHelper.enableStandardItemLighting();
      GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-((float)Math.atan(float5 / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);
      entityLivingBase.renderYawOffset = (float)Math.atan(float4 / 40.0F) * 20.0F;
      entityLivingBase.rotationYaw = (float)Math.atan(float4 / 40.0F) * 40.0F;
      entityLivingBase.rotationPitch = -((float)Math.atan(float5 / 40.0F)) * 20.0F;
      entityLivingBase.rotationYawHead = entityLivingBase.rotationYaw;
      entityLivingBase.prevRotationYawHead = entityLivingBase.rotationYaw;
      GlStateManager.translate(0.0F, 0.0F, 0.0F);
      RenderManager var11 = Minecraft.getMinecraft().getRenderManager();
      var11.setPlayerViewY(180.0F);
      var11.setRenderShadow(false);
      var11.renderEntityWithPosYaw(entityLivingBase, 0.0, 0.0, 0.0, 0.0F, 1.0F);
      var11.setRenderShadow(true);
      entityLivingBase.renderYawOffset = var6;
      entityLivingBase.rotationYaw = var7;
      entityLivingBase.rotationPitch = var8;
      entityLivingBase.prevRotationYawHead = var9;
      entityLivingBase.rotationYawHead = var10;
      GlStateManager.popMatrix();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableRescaleNormal();
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GlStateManager.disableTexture2D();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.l == 0) {
         this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
      }

      if (guiButton.l == 1) {
         this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
      }

      super.actionPerformed(guiButton);
   }
}
