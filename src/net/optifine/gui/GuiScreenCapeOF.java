package net.optifine.gui;

import com.mojang.authlib.exceptions.InvalidCredentialsException;
import java.math.BigInteger;
import java.net.URI;
import java.util.Random;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.src.Config;
import net.optifine.Lang;

public class GuiScreenCapeOF extends GuiScreenOF {
   private final GuiScreen parentScreen;
   private String title;
   private String message;
   private long messageHideTimeMs;
   private String linkUrl;
   private GuiButtonOF buttonCopyLink;
   private final FontRenderer fontRenderer = Config.getMinecraft().fontRendererObj;

   public GuiScreenCapeOF(GuiScreen guiScreen) {
      this.parentScreen = guiScreen;
   }

   @Override
   public void onInitGui() {
      this.title = I18n.format("of.options.capeOF.title");
      this.m.add(new GuiButtonOF(210, this.width / 2 - 155, this.height / 6 + 24, 150, 20, I18n.format("of.options.capeOF.openEditor")));
      this.m.add(new GuiButtonOF(220, this.width / 2 - 155 + 160, this.height / 6 + 24, 150, 20, I18n.format("of.options.capeOF.reloadCape")));
      byte var4 = 8;
      this.buttonCopyLink = new GuiButtonOF(230, this.width / 2 - 100, this.height / 6 + 96, 200, 20, I18n.format("of.options.capeOF.copyEditorLink"));
      this.buttonCopyLink.visible = this.linkUrl != null;
      this.m.add(this.buttonCopyLink);
      var4 = 12;
      this.m.add(new GuiButtonOF(200, this.width / 2 - 100, this.height / 6 + 144, I18n.format("gui.done")));
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) {
      if (guiButton.enabled) {
         if (guiButton.l == 200) {
            this.mc.displayGuiScreen(this.parentScreen);
         }

         if (guiButton.l == 210) {
            try {
               String var5 = this.mc.getSession().getProfile().getName();
               String var6 = this.mc.getSession().getProfile().getId().toString().replace("-", "");
               String var7 = this.mc.getSession().getToken();
               Random var8 = new Random();
               Random var9 = new Random(System.identityHashCode(new Object()));
               BigInteger var10 = new BigInteger(128, var8);
               BigInteger var11 = new BigInteger(128, var9);
               String var13 = var10.xor(var11).toString(16);
               this.mc.getSessionService().joinServer(this.mc.getSession().getProfile(), var7, var13);
               String var16 = var13;
               String var17 = var5;
               String var18 = var6;
               String var14 = "https://optifine.net/capeChange?u=" + var18 + "&n=" + var17 + "&s=" + var16;
               boolean var15 = Config.Method277(new URI(var14));
               if (var15) {
                  this.showMessage(Lang.get("of.message.capeOF.openEditor"), 10000L);
               } else {
                  this.showMessage(Lang.get("of.message.capeOF.openEditorError"), 10000L);
                  this.setLinkUrl(var14);
               }
            } catch (InvalidCredentialsException var19) {
               Config.showGuiMessage(I18n.format("of.message.capeOF.error1"), I18n.format("of.message.capeOF.error2", var19.getMessage()));
               Config.Method252("Mojang authentication failed");
               Config.Method252(var19.getClass().getName() + ": " + var19.getMessage());
            } catch (Exception var20) {
               Config.Method252("Error opening OptiFine cape link");
               Config.Method252(var20.getClass().getName() + ": " + var20.getMessage());
            }
         }

         if (guiButton.l == 220) {
            this.showMessage(Lang.get("of.message.capeOF.reloadCape"), 15000L);
            if (this.mc.thePlayer != null) {
               long var21 = 15000L;
               long var22 = System.currentTimeMillis() + var21;
               this.mc.thePlayer.setReloadCapeTimeMs(var22);
            }
         }

         if (guiButton.l == 230 && this.linkUrl != null) {
            setClipboardString(this.linkUrl);
         }
      }
   }

   private void showMessage(String string, long long2) {
      this.message = string;
      this.messageHideTimeMs = System.currentTimeMillis() + long2;
      this.setLinkUrl(null);
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRenderer, this.title, this.width / 2, 20, 16777215);
      if (this.message != null) {
         this.drawCenteredString(this.fontRenderer, this.message, this.width / 2, this.height / 6 + 60, 16777215);
         if (System.currentTimeMillis() > this.messageHideTimeMs) {
            this.message = null;
            this.setLinkUrl(null);
         }
      }

      super.onDrawScreen(mouseX, mouseY, partialTicks);
   }

   public void setLinkUrl(String string) {
      this.linkUrl = string;
      this.buttonCopyLink.visible = string != null;
   }
}
