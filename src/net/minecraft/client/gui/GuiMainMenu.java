package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.moon.Moon;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import net.optifine.CustomPanorama;
import net.optifine.CustomPanoramaProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback {
   public static final String w = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
   private static final AtomicInteger field_175373_f = new AtomicInteger(0);
   private static final Logger logger = LogManager.getLogger();
   private static final Random RANDOM = new Random();
   private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
   private static final ResourceLocation minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");
   private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[]{
      new ResourceLocation("textures/gui/title/background/panorama_0.png"),
      new ResourceLocation("textures/gui/title/background/panorama_1.png"),
      new ResourceLocation("textures/gui/title/background/panorama_2.png"),
      new ResourceLocation("textures/gui/title/background/panorama_3.png"),
      new ResourceLocation("textures/gui/title/background/panorama_4.png"),
      new ResourceLocation("textures/gui/title/background/panorama_5.png")
   };
   private final float updateCounter;
   private final boolean E = true;
   private final Object threadLock = new Object();
   public String splashText;
   private GuiButton buttonResetDemo;
   private int panoramaTimer;
   private DynamicTexture viewportTexture;
   private String openGLWarning1;
   private String openGLWarning2 = w;
   private String openGLWarningLink;
   private int field_92024_r;
   private int field_92023_s;
   private int field_92022_t;
   private int field_92021_u;
   private int field_92020_v;
   private int field_92019_w;
   private ResourceLocation backgroundTexture;
   private boolean field_183502_L = false;
   private GuiScreen V;
   private GuiScreen modUpdateNotification;

   public GuiMainMenu() {
      this.splashText = "missingno";
      BufferedReader var3 = null;

      try {
         ArrayList var4 = Lists.newArrayList();
         var3 = new BufferedReader(
            new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(splashTexts).getInputStream(), StandardCharsets.UTF_8)
         );

         String var5;
         while ((var5 = var3.readLine()) != null) {
            var5 = var5.trim();
            if (!var5.isEmpty()) {
               var4.add(var5);
            }
         }

         if (!var4.isEmpty()) {
            do {
               this.splashText = (String)var4.get(RANDOM.nextInt(var4.size()));
            } while (this.splashText.hashCode() == 125780783);
         }
      } catch (IOException var13) {
      } finally {
         if (var3 != null) {
            try {
               var3.close();
            } catch (IOException var12) {
            }
         }
      }

      this.updateCounter = RANDOM.nextFloat();
      this.openGLWarning1 = "";
      if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported()) {
         this.openGLWarning1 = I18n.format("title.oldgl1");
         this.openGLWarning2 = I18n.format("title.oldgl2");
         this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
      }
   }

   private boolean func_183501_a() {
      return Minecraft.getMinecraft().gameSettings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS) && this.V != null;
   }

   @Override
   public void onUpdateScreen() {
      this.panoramaTimer++;
      if (this.func_183501_a()) {
         this.V.onUpdateScreen();
      }
   }

   @Override
   public boolean doesGuiPauseGame() {
      return false;
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
   }

   @Override
   public void onInitGui() {
      this.viewportTexture = new DynamicTexture(256, 256);
      this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
      Calendar var3 = Calendar.getInstance();
      var3.setTime(new Date());
      if (var3.get(2) + 1 == 12 && var3.get(5) == 24) {
         this.splashText = "Merry X-mas!";
      } else if (var3.get(2) + 1 == 1 && var3.get(5) == 1) {
         this.splashText = "Happy new year!";
      } else if (var3.get(2) + 1 == 10 && var3.get(5) == 31) {
         this.splashText = "OOoooOOOoooo! Spooky!";
      }

      int var5 = this.height / 4 + 48;
      if (this.mc.isDemo()) {
         this.addDemoButtons(var5, 24);
      } else {
         this.Method564(var5, 24);
      }

      this.m.add(new GuiButton(1337, this.width / 2 - 100, var5 + 36 + 12, 200, 20, "Account Manager"));
      this.m.add(new GuiButton(0, this.width / 2 - 100, var5 + 36 + 12 + 24, 98, 20, I18n.format("menu.options")));
      this.m.add(new GuiButton(4, this.width / 2 + 2, var5 + 36 + 12 + 24, 98, 20, I18n.format("menu.quit")));
      this.m.add(new GuiButtonLanguage(5, this.width / 2 - 124, var5 + 36 + 12 + 24));
      synchronized (this.threadLock) {
         this.field_92023_s = this.fontRendererObj.getStringWidth(this.openGLWarning1);
         this.field_92024_r = this.fontRendererObj.getStringWidth(this.openGLWarning2);
         int var7 = Math.max(this.field_92023_s, this.field_92024_r);
         this.field_92022_t = (this.width - var7) / 2;
         this.field_92021_u = this.m.get(0).yPosition - 24;
         this.field_92020_v = this.field_92022_t + var7;
         this.field_92019_w = this.field_92021_u + 24;
      }

      this.mc.setConnectedToRealms(false);
      if (Minecraft.getMinecraft().gameSettings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS) && !this.field_183502_L) {
         this.field_183502_L = true;
      }

      if (this.func_183501_a()) {
         this.V.setGuiSize(this.width, this.height);
         this.V.onInitGui();
      }
   }

   private void Method564(int integer1, int integer2) {
      this.m.add(new GuiButton(1, this.width / 2 - 100, integer1, I18n.format("menu.singleplayer")));
      this.m.add(new GuiButton(2, this.width / 2 - 100, integer1 + integer2, I18n.format("menu.multiplayer")));
   }

   private void addDemoButtons(int integer1, int integer2) {
      this.m.add(new GuiButton(11, this.width / 2 - 100, integer1, I18n.format("menu.playdemo")));
      this.m.add(this.buttonResetDemo = new GuiButton(12, this.width / 2 - 100, integer1 + integer2, I18n.format("menu.resetdemo")));
      WorldInfo var6 = this.mc.getSaveLoader().getWorldInfo("Demo_World");
      if (var6 == null) {
         this.buttonResetDemo.enabled = false;
      }
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.l == 0) {
         this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
      }

      if (guiButton.l == 5) {
         this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
      }

      if (guiButton.l == 1) {
         this.mc.displayGuiScreen(new GuiSelectWorld(this));
      }

      if (guiButton.l == 2) {
         this.mc.displayGuiScreen(new GuiMultiplayer(this));
      }

      if (guiButton.l == 4) {
         this.mc.shutdown();
      }

      if (guiButton.l == 11) {
         this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
      }

      if (guiButton.l == 12) {
         WorldInfo var5 = this.mc.getSaveLoader().getWorldInfo("Demo_World");
         if (var5 != null) {
            GuiYesNo var6 = GuiSelectWorld.makeDeleteWorldYesNo(this, var5.getWorldName(), 12);
            this.mc.displayGuiScreen(var6);
         }
      }

      if (guiButton.l == 1337) {
         this.mc.displayGuiScreen(Moon.getInstance().getAccountScreen());
      }
   }

   @Override
   public void confirmClicked(boolean boolean1, int integer) {
      if (boolean1 && integer == 12) {
         ISaveFormat var8 = this.mc.getSaveLoader();
         var8.flushCache();
         var8.deleteWorldDirectory("Demo_World");
         this.mc.displayGuiScreen(this);
      } else if (integer == 13) {
         if (boolean1) {
            try {
               Class var5 = Class.forName("java.awt.Desktop");
               Object var6 = var5.getMethod("getDesktop").invoke(null);
               var5.getMethod("browse", URI.class).invoke(var6, new URI(this.openGLWarningLink));
            } catch (Throwable var7) {
               logger.error("Couldn't open link", var7);
            }
         }

         this.mc.displayGuiScreen(this);
      }
   }

   private void drawPanorama(int integer1, int integer2, float float3) {
      Tessellator var6 = Tessellator.getInstance();
      WorldRenderer var7 = var6.getWorldRenderer();
      GlStateManager.matrixMode(5889);
      GlStateManager.pushMatrix();
      GlStateManager.loadIdentity();
      Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
      GlStateManager.matrixMode(5888);
      GlStateManager.pushMatrix();
      GlStateManager.loadIdentity();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.disableCull();
      GlStateManager.depthMask(false);
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      byte var8 = 8;
      int var9 = 64;
      CustomPanoramaProperties var10 = CustomPanorama.getCustomPanoramaProperties();
      if (var10 != null) {
         var9 = var10.getBlur1();
      }

      for (int var11 = 0; var11 < var9; var11++) {
         GlStateManager.pushMatrix();
         float var12 = (var11 % var8 / 8.0F - 0.5F) / 64.0F;
         float var13 = (var11 / var8 / 8.0F - 0.5F) / 64.0F;
         GlStateManager.translate(var12, var13, 0.0F);
         GlStateManager.rotate(MathHelper.sin((this.panoramaTimer + float3) / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(-(this.panoramaTimer + float3) * 0.1F, 0.0F, 1.0F, 0.0F);

         for (int var15 = 0; var15 < 6; var15++) {
            GlStateManager.pushMatrix();
            if (var15 == 1) {
               GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
            }

            if (var15 == 2) {
               GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            }

            if (var15 == 3) {
               GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
            }

            if (var15 == 4) {
               GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (var15 == 5) {
               GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            }

            ResourceLocation[] var16 = titlePanoramaPaths;
            if (var10 != null) {
               var16 = var10.getPanoramaLocations();
            }

            this.mc.getTextureManager().bindTexture(var16[var15]);
            var7.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            int var17 = 255 / (var11 + 1);
            var7.pos(-1.0, -1.0, 1.0).tex(0.0, 0.0).color(255, 255, 255, var17).endVertex();
            var7.pos(1.0, -1.0, 1.0).tex(1.0, 0.0).color(255, 255, 255, var17).endVertex();
            var7.pos(1.0, 1.0, 1.0).tex(1.0, 1.0).color(255, 255, 255, var17).endVertex();
            var7.pos(-1.0, 1.0, 1.0).tex(0.0, 1.0).color(255, 255, 255, var17).endVertex();
            var6.draw();
            GlStateManager.popMatrix();
         }

         GlStateManager.popMatrix();
         GlStateManager.colorMask(true, true, true, false);
      }

      var7.setTranslation(0.0, 0.0, 0.0);
      GlStateManager.colorMask(true, true, true, true);
      GlStateManager.matrixMode(5889);
      GlStateManager.popMatrix();
      GlStateManager.matrixMode(5888);
      GlStateManager.popMatrix();
      GlStateManager.depthMask(true);
      GlStateManager.enableCull();
      GlStateManager.enableDepth();
   }

   private void rotateAndBlurSkybox(float float1) {
      this.mc.getTextureManager().bindTexture(this.backgroundTexture);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
      GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, 256, 256);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.colorMask(true, true, true, false);
      Tessellator var4 = Tessellator.getInstance();
      WorldRenderer var5 = var4.getWorldRenderer();
      var5.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      GlStateManager.disableAlpha();
      int var7 = 3;
      CustomPanoramaProperties var8 = CustomPanorama.getCustomPanoramaProperties();
      if (var8 != null) {
         var7 = var8.getBlur2();
      }

      for (int var9 = 0; var9 < var7; var9++) {
         float var10 = 1.0F / (var9 + 1);
         int var11 = this.width;
         int var12 = this.height;
         float var13 = (var9 - 1) / 256.0F;
         var5.pos(var11, var12, this.zLevel).tex(0.0F + var13, 1.0).color(1.0F, 1.0F, 1.0F, var10).endVertex();
         var5.pos(var11, 0.0, this.zLevel).tex(1.0F + var13, 1.0).color(1.0F, 1.0F, 1.0F, var10).endVertex();
         var5.pos(0.0, 0.0, this.zLevel).tex(1.0F + var13, 0.0).color(1.0F, 1.0F, 1.0F, var10).endVertex();
         var5.pos(0.0, var12, this.zLevel).tex(0.0F + var13, 0.0).color(1.0F, 1.0F, 1.0F, var10).endVertex();
      }

      var4.draw();
      GlStateManager.enableAlpha();
      GlStateManager.colorMask(true, true, true, true);
   }

   private void renderSkybox(int integer1, int integer2, float float3) {
      this.mc.getFramebuffer().unbindFramebuffer();
      GlStateManager.viewport(0, 0, 256, 256);
      this.drawPanorama(integer1, integer2, float3);
      this.rotateAndBlurSkybox(float3);
      int var6 = 3;
      CustomPanoramaProperties var7 = CustomPanorama.getCustomPanoramaProperties();
      if (var7 != null) {
         var6 = var7.getBlur3();
      }

      for (int var8 = 0; var8 < var6; var8++) {
         this.rotateAndBlurSkybox(float3);
         this.rotateAndBlurSkybox(float3);
      }

      this.mc.getFramebuffer().bindFramebuffer(true);
      GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
      float var15 = this.width > this.height ? 120.0F / this.width : 120.0F / this.height;
      float var9 = this.height * var15 / 256.0F;
      float var10 = this.width * var15 / 256.0F;
      int var11 = this.width;
      int var12 = this.height;
      Tessellator var13 = Tessellator.getInstance();
      WorldRenderer var14 = var13.getWorldRenderer();
      var14.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      var14.pos(0.0, var12, this.zLevel).tex(0.5F - var9, 0.5F + var10).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      var14.pos(var11, var12, this.zLevel).tex(0.5F - var9, 0.5F - var10).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      var14.pos(var11, 0.0, this.zLevel).tex(0.5F + var9, 0.5F - var10).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      var14.pos(0.0, 0.0, this.zLevel).tex(0.5F + var9, 0.5F + var10).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
      var13.draw();
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      GlStateManager.disableAlpha();
      this.renderSkybox(mouseX, mouseY, partialTicks);
      GlStateManager.enableAlpha();
      Tessellator.getInstance().getWorldRenderer();
      short var8 = 274;
      int var9 = this.width / 2 - 137;
      byte var10 = 30;
      int var11 = -2130706433;
      int var12 = 16777215;
      int var13 = 0;
      int var14 = Integer.MIN_VALUE;
      CustomPanoramaProperties var15 = CustomPanorama.getCustomPanoramaProperties();
      if (var15 != null) {
         var11 = var15.getOverlay1Top();
         var12 = var15.getOverlay1Bottom();
         var13 = var15.getOverlay2Top();
         var14 = var15.getOverlay2Bottom();
      }

      if (var11 != 0 || var12 != 0) {
         this.drawGradientRect(0, 0, this.width, this.height, var11, var12);
      }

      if (var13 != 0 || var14 != 0) {
         this.drawGradientRect(0, 0, this.width, this.height, var13, var14);
      }

      this.mc.getTextureManager().bindTexture(minecraftTitleTextures);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      if (this.updateCounter < 1.0E-4) {
         this.drawTexturedModalRect(var9, var10, 0, 0, 99, 44);
         this.drawTexturedModalRect(var9 + 99, var10, 129, 0, 27, 44);
         this.drawTexturedModalRect(var9 + 99 + 26, var10, 126, 0, 3, 44);
         this.drawTexturedModalRect(var9 + 99 + 26 + 3, var10, 99, 0, 26, 44);
         this.drawTexturedModalRect(var9 + 155, var10, 0, 45, 155, 44);
      } else {
         this.drawTexturedModalRect(var9, var10, 0, 0, 155, 44);
         this.drawTexturedModalRect(var9 + 155, var10, 0, 45, 155, 44);
      }

      GlStateManager.pushMatrix();
      GlStateManager.translate(this.width / 2 + 90, 70.0F, 0.0F);
      GlStateManager.rotate(-20.0F, 0.0F, 0.0F, 1.0F);
      float var16 = (1.8F - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0F * (float) Math.PI * 2.0F) * 0.1F))
         * 100.0F
         / (this.fontRendererObj.getStringWidth(this.splashText) + 32);
      GlStateManager.scale(var16, var16, var16);
      this.drawCenteredString(this.fontRendererObj, this.splashText, 0, -8, -256);
      GlStateManager.popMatrix();
      String var17 = "Moon Client";
      if (this.mc.isDemo()) {
         var17 = "Moon Client" + " Demo";
      }

      this.drawString(this.fontRendererObj, var17, 2, this.height - 10, -1);
      this.drawString(
         this.fontRendererObj,
         "Copyright Mojang AB. Do not distribute!",
         this.width - this.fontRendererObj.getStringWidth("Copyright Mojang AB. Do not distribute!") - 2,
         this.height - 10,
         -1
      );
      if (this.openGLWarning1 != null && this.openGLWarning1.length() > 0) {
         drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 1428160512);
         this.drawString(this.fontRendererObj, this.openGLWarning1, this.field_92022_t, this.field_92021_u, -1);
         this.drawString(this.fontRendererObj, this.openGLWarning2, (this.width - this.field_92024_r) / 2, this.m.get(0).yPosition - 12, -1);
      }

      super.onDrawScreen(mouseX, mouseY, partialTicks);
      if (this.func_183501_a()) {
         this.V.onDrawScreen(mouseX, mouseY, partialTicks);
      }

      if (this.modUpdateNotification != null) {
         this.modUpdateNotification.onDrawScreen(mouseX, mouseY, partialTicks);
      }
   }

   @Override
   protected void onMouseClicked(int mouseX, int mouseY, int button) throws IOException {
      super.onMouseClicked(mouseX, mouseY, button);
      synchronized (this.threadLock) {
         if (this.openGLWarning1.length() > 0
            && mouseX >= this.field_92022_t
            && mouseX <= this.field_92020_v
            && mouseY >= this.field_92021_u
            && mouseY <= this.field_92019_w) {
            GuiConfirmOpenLink var7 = new GuiConfirmOpenLink(this, this.openGLWarningLink, 13, true);
            var7.disableSecurityWarning();
            this.mc.displayGuiScreen(var7);
         }
      }

      if (this.func_183501_a()) {
         this.V.onMouseClicked(mouseX, mouseY, button);
      }
   }

   @Override
   public void onGuiClosed() {
      if (this.V != null) {
         this.V.onGuiClosed();
      }
   }
}
