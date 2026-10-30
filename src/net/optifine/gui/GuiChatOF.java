package net.optifine.gui;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.src.Config;
import net.optifine.shaders.Shaders;

public class GuiChatOF extends GuiChat {
   public GuiChatOF(GuiChat guiChat) {
      super(GuiVideoSettings.getGuiChatText(guiChat));
   }

   @Override
   public void sendChatMessage(String string) {
      if (this.checkCustomCommand(string)) {
         this.mc.ingameGUI.getChatGUI().addToSentMessages(string);
      } else {
         super.sendChatMessage(string);
      }
   }

   private boolean checkCustomCommand(String string) {
      if (string == null) {
         return false;
      }

      string = string.trim();
      if (string.equals("/reloadShaders")) {
         if (Config.isShaders()) {
            Shaders.uninit();
            Shaders.loadShaderPack();
         }

         return true;
      } else if (string.equals("/reloadChunks")) {
         this.mc.renderGlobal.loadRenderers();
         return true;
      } else {
         return false;
      }
   }
}
