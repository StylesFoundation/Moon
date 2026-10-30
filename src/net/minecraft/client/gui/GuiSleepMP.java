package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class GuiSleepMP extends GuiChat {
   @Override
   public void onInitGui() {
      super.onInitGui();
      this.m.add(new GuiButton(1, this.width / 2 - 100, this.height - 40, I18n.format("multiplayer.stopSleeping")));
   }

   @Override
   protected void onKeyTyped(char charTyped, int keyCode) throws IOException {
      if (keyCode == 1) {
         this.wakeFromSleep();
      } else if (keyCode != 28 && keyCode != 156) {
         super.onKeyTyped(charTyped, keyCode);
      } else {
         String var5 = this.inputField.getText().trim();
         if (!var5.isEmpty()) {
            this.mc.thePlayer.sendChatMessage(var5);
         }

         this.inputField.setText("");
         this.mc.ingameGUI.getChatGUI().resetScroll();
      }
   }

   @Override
   protected void actionPerformed(GuiButton guiButton) throws IOException {
      if (guiButton.l == 1) {
         this.wakeFromSleep();
      } else {
         super.actionPerformed(guiButton);
      }
   }

   private void wakeFromSleep() {
      this.mc.thePlayer.sendQueue.Method754(new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SLEEPING));
   }
}
