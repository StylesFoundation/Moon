package net.minecraft.client.gui.achievement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.stats.Achievement;
import net.minecraft.util.ResourceLocation;

public class GuiAchievement extends Gui {
   private static final ResourceLocation achievementBg = new ResourceLocation("textures/gui/achievement/achievement_background.png");
   private final Minecraft mc;
   private int width;
   private int height;
   private String achievementTitle;
   private String achievementDescription;
   private Achievement theAchievement;
   private long notificationTime;
   private final RenderItem renderItem;
   private boolean permanentNotification;

   public GuiAchievement(Minecraft minecraft) {
      this.mc = minecraft;
      this.renderItem = minecraft.getRenderItem();
   }

   public void displayAchievement(Achievement achievement) {
      this.achievementTitle = I18n.format("achievement.get");
      this.achievementDescription = achievement.getStatName().getUnformattedText();
      this.notificationTime = Minecraft.getSystemTime();
      this.theAchievement = achievement;
      this.permanentNotification = false;
   }

   public void displayUnformattedAchievement(Achievement achievement) {
      this.achievementTitle = achievement.getStatName().getUnformattedText();
      this.achievementDescription = achievement.getDescription();
      this.notificationTime = Minecraft.getSystemTime() + 2500L;
      this.theAchievement = achievement;
      this.permanentNotification = true;
   }

   private void updateAchievementWindowScale() {
      GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
      GlStateManager.matrixMode(5889);
      GlStateManager.loadIdentity();
      GlStateManager.matrixMode(5888);
      GlStateManager.loadIdentity();
      this.width = this.mc.displayWidth;
      this.height = this.mc.displayHeight;
      ScaledResolution var3 = new ScaledResolution(this.mc);
      this.width = var3.Method237();
      this.height = var3.Method238();
      GlStateManager.clear(256);
      GlStateManager.matrixMode(5889);
      GlStateManager.loadIdentity();
      GlStateManager.ortho(0.0, this.width, this.height, 0.0, 1000.0, 3000.0);
      GlStateManager.matrixMode(5888);
      GlStateManager.loadIdentity();
      GlStateManager.translate(0.0F, 0.0F, -2000.0F);
   }

   public void updateAchievementWindow() {
      if (this.theAchievement != null && this.notificationTime != 0L && Minecraft.getMinecraft().thePlayer != null) {
         double var3 = (Minecraft.getSystemTime() - this.notificationTime) / 3000.0;
         if (!this.permanentNotification) {
            if (var3 < 0.0 || var3 > 1.0) {
               this.notificationTime = 0L;
               return;
            }
         } else if (var3 > 0.5) {
            var3 = 0.5;
         }

         this.updateAchievementWindowScale();
         GlStateManager.disableDepth();
         GlStateManager.depthMask(false);
         double var5 = var3 * 2.0;
         if (var5 > 1.0) {
            var5 = 2.0 - var5;
         }

         var5 *= 4.0;
         var5 = 1.0 - var5;
         if (var5 < 0.0) {
            var5 = 0.0;
         }

         var5 *= var5;
         var5 *= var5;
         int var7 = this.width - 160;
         int var8 = -((int)(var5 * 36.0));
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.enableTexture2D();
         this.mc.getTextureManager().bindTexture(achievementBg);
         GlStateManager.disableLighting();
         this.drawTexturedModalRect(var7, var8, 96, 202, 160, 32);
         if (this.permanentNotification) {
            this.mc.fontRendererObj.drawSplitString(this.achievementDescription, var7 + 30, var8 + 7, 120, -1);
         } else {
            this.mc.fontRendererObj.drawString(this.achievementTitle, var7 + 30, var8 + 7, -256);
            this.mc.fontRendererObj.drawString(this.achievementDescription, var7 + 30, var8 + 18, -1);
         }

         RenderHelper.enableGUIStandardItemLighting();
         GlStateManager.disableLighting();
         GlStateManager.enableRescaleNormal();
         GlStateManager.enableColorMaterial();
         GlStateManager.enableLighting();
         this.renderItem.renderItemAndEffectIntoGUI(this.theAchievement.theItemStack, var7 + 8, var8 + 8);
         GlStateManager.disableLighting();
         GlStateManager.depthMask(true);
         GlStateManager.enableDepth();
      }
   }

   public void clearAchievements() {
      this.theAchievement = null;
      this.notificationTime = 0L;
   }
}
