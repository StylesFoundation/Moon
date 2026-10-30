package net.minecraft.client;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MinecraftError;
import net.optifine.CustomLoadingScreen;
import net.optifine.CustomLoadingScreens;

public class LoadingScreenRenderer implements IProgressUpdate {
   private String message = "";
   private final Minecraft mc;
   private String currentlyDisplayedText = "";
   private long systemTime = Minecraft.getSystemTime();
   private boolean loadingSuccess;
   private final ScaledResolution scaledResolution;
   private final Framebuffer framebuffer;

   public LoadingScreenRenderer(Minecraft minecraft) {
      this.mc = minecraft;
      this.scaledResolution = new ScaledResolution(minecraft);
      this.framebuffer = new Framebuffer(minecraft.displayWidth, minecraft.displayHeight, false);
      this.framebuffer.setFramebufferFilter(9728);
   }

   @Override
   public void resetProgressAndMessage(String string) {
      this.loadingSuccess = false;
      this.displayString(string);
   }

   @Override
   public void displaySavingString(String string) {
      this.loadingSuccess = true;
      this.displayString(string);
   }

   private void displayString(String string) {
      this.currentlyDisplayedText = string;
      if (!this.mc.running) {
         if (!this.loadingSuccess) {
            throw new MinecraftError();
         }
      } else {
         GlStateManager.clear(256);
         GlStateManager.matrixMode(5889);
         GlStateManager.loadIdentity();
         if (OpenGlHelper.isFramebufferEnabled()) {
            int var5 = this.scaledResolution.getScaleFactor();
            GlStateManager.ortho(0.0, this.scaledResolution.Method237() * var5, this.scaledResolution.Method238() * var5, 0.0, 100.0, 300.0);
         } else {
            ScaledResolution var6 = new ScaledResolution(this.mc);
            GlStateManager.ortho(0.0, var6.getScaledWidth_double(), var6.getScaledHeight_double(), 0.0, 100.0, 300.0);
         }

         GlStateManager.matrixMode(5888);
         GlStateManager.loadIdentity();
         GlStateManager.translate(0.0F, 0.0F, -200.0F);
      }
   }

   @Override
   public void displayLoadingString(String string) {
      if (!this.mc.running) {
         if (!this.loadingSuccess) {
            throw new MinecraftError();
         }
      } else {
         this.systemTime = 0L;
         this.message = string;
         this.setLoadingProgress(-1);
         this.systemTime = 0L;
      }
   }

   @Override
   public void setLoadingProgress(int integer) {
      if (!this.mc.running) {
         if (!this.loadingSuccess) {
            throw new MinecraftError();
         }
      } else {
         long var5 = Minecraft.getSystemTime();
         if (var5 - this.systemTime >= 100L) {
            this.systemTime = var5;
            ScaledResolution var7 = new ScaledResolution(this.mc);
            int var8 = var7.getScaleFactor();
            int var9 = var7.Method237();
            int var10 = var7.Method238();
            if (OpenGlHelper.isFramebufferEnabled()) {
               this.framebuffer.framebufferClear();
            } else {
               GlStateManager.clear(256);
            }

            this.framebuffer.bindFramebuffer(false);
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            GlStateManager.ortho(0.0, var7.getScaledWidth_double(), var7.getScaledHeight_double(), 0.0, 100.0, 300.0);
            GlStateManager.matrixMode(5888);
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0F, 0.0F, -200.0F);
            if (!OpenGlHelper.isFramebufferEnabled()) {
               GlStateManager.clear(16640);
            }

            boolean var11 = true;
            if (var11) {
               Tessellator var12 = Tessellator.getInstance();
               WorldRenderer var13 = var12.getWorldRenderer();
               CustomLoadingScreen var14 = CustomLoadingScreens.getCustomLoadingScreen();
               if (var14 != null) {
                  var14.drawBackground(var7.Method237(), var7.Method238());
               } else {
                  this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
                  var13.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                  var13.pos(0.0, var10, 0.0).tex(0.0, var10 / 32.0F).color(64, 64, 64, 255).endVertex();
                  var13.pos(var9, var10, 0.0).tex(var9 / 32.0F, var10 / 32.0F).color(64, 64, 64, 255).endVertex();
                  var13.pos(var9, 0.0, 0.0).tex(var9 / 32.0F, 0.0).color(64, 64, 64, 255).endVertex();
                  var13.pos(0.0, 0.0, 0.0).tex(0.0, 0.0).color(64, 64, 64, 255).endVertex();
                  var12.draw();
               }

               if (integer >= 0) {
                  byte var15 = 100;
                  int var17 = var9 / 2 - 50;
                  int var18 = var10 / 2 + 16;
                  GlStateManager.disableTexture2D();
                  var13.begin(7, DefaultVertexFormats.POSITION_COLOR);
                  var13.pos(var17, var18, 0.0).color(128, 128, 128, 255).endVertex();
                  var13.pos(var17, var18 + 2, 0.0).color(128, 128, 128, 255).endVertex();
                  var13.pos(var17 + var15, var18 + 2, 0.0).color(128, 128, 128, 255).endVertex();
                  var13.pos(var17 + var15, var18, 0.0).color(128, 128, 128, 255).endVertex();
                  var13.pos(var17, var18, 0.0).color(128, 255, 128, 255).endVertex();
                  var13.pos(var17, var18 + 2, 0.0).color(128, 255, 128, 255).endVertex();
                  var13.pos(var17 + integer, var18 + 2, 0.0).color(128, 255, 128, 255).endVertex();
                  var13.pos(var17 + integer, var18, 0.0).color(128, 255, 128, 255).endVertex();
                  var12.draw();
                  GlStateManager.enableTexture2D();
               }

               GlStateManager.enableBlend();
               GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
               this.mc
                  .fontRendererObj
                  .drawStringWithShadow(
                     this.currentlyDisplayedText,
                     (var9 - this.mc.fontRendererObj.getStringWidth(this.currentlyDisplayedText)) / 2,
                     var10 / 2 - 4 - 16,
                     16777215
                  );
               this.mc
                  .fontRendererObj
                  .drawStringWithShadow(this.message, (var9 - this.mc.fontRendererObj.getStringWidth(this.message)) / 2, var10 / 2 - 4 + 8, 16777215);
            }

            this.framebuffer.unbindFramebuffer();
            if (OpenGlHelper.isFramebufferEnabled()) {
               this.framebuffer.framebufferRender(var9 * var8, var10 * var8);
            }

            this.mc.updateDisplay();

            try {
               Thread.yield();
            } catch (Exception var19) {
            }
         }
      }
   }

   @Override
   public void setDoneWorking() {
   }
}
