package net.minecraft.client.gui;

import net.minecraft.util.IProgressUpdate;
import net.optifine.CustomLoadingScreen;
import net.optifine.CustomLoadingScreens;

public class GuiScreenWorking extends GuiScreen implements IProgressUpdate {
   private String field_146591_a = "";
   private String field_146589_f = "";
   private int progress;
   private boolean doneWorking;
   private final CustomLoadingScreen customLoadingScreen = CustomLoadingScreens.getCustomLoadingScreen();

   @Override
   public void displaySavingString(String string) {
      this.resetProgressAndMessage(string);
   }

   @Override
   public void resetProgressAndMessage(String string) {
      this.field_146591_a = string;
      this.displayLoadingString("Working...");
   }

   @Override
   public void displayLoadingString(String string) {
      this.field_146589_f = string;
      this.setLoadingProgress(0);
   }

   @Override
   public void setLoadingProgress(int integer) {
      this.progress = integer;
   }

   @Override
   public void setDoneWorking() {
      this.doneWorking = true;
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      if (this.doneWorking) {
         if (!this.mc.isConnectedToRealms()) {
            this.mc.displayGuiScreen(null);
         }
      } else {
         if (this.customLoadingScreen != null && this.mc.theWorld == null) {
            this.customLoadingScreen.drawBackground(this.width, this.height);
         } else {
            this.drawDefaultBackground();
         }

         if (this.progress > 0) {
            this.drawCenteredString(this.fontRendererObj, this.field_146591_a, this.width / 2, 70, 16777215);
            this.drawCenteredString(this.fontRendererObj, this.field_146589_f + " " + this.progress + "%", this.width / 2, 90, 16777215);
         }

         super.onDrawScreen(mouseX, mouseY, partialTicks);
      }
   }
}
