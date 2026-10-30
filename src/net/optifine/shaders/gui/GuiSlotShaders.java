package net.optifine.shaders.gui;

import java.util.ArrayList;
import java.util.Properties;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.I18n;
import net.minecraft.src.Config;
import net.optifine.Lang;
import net.optifine.shaders.IShaderPack;
import net.optifine.shaders.Shaders;
import net.optifine.util.ResUtils;

class GuiSlotShaders extends GuiSlot {
   private ArrayList shaderslist;
   private int selectedIndex;
   private long lastClickedCached = 0L;
   final GuiShaders shadersGui;

   public GuiSlotShaders(GuiShaders guiShaders, int integer2, int integer3, int integer4, int integer5, int integer6) {
      super(guiShaders.getMc(), integer2, integer3, integer4, integer5, integer6);
      this.shadersGui = guiShaders;
      this.updateList();
      this.amountScrolled = 0.0F;
      int var9 = this.selectedIndex * integer6;
      int var10 = (integer5 - integer4) / 2;
      if (var9 > var10) {
         this.scrollBy(var9 - var10);
      }
   }

   @Override
   public int getListWidth() {
      return this.width - 20;
   }

   public void updateList() {
      this.shaderslist = Shaders.listOfShaders();
      this.selectedIndex = 0;
      int var4 = 0;

      for (int var5 = this.shaderslist.size(); var4 < var5; var4++) {
         if (this.shaderslist.get(var4).equals(Shaders.currentShaderName)) {
            this.selectedIndex = var4;
            break;
         }
      }
   }

   @Override
   protected int getSize() {
      return this.shaderslist.size();
   }

   @Override
   protected void elementClicked(int index, boolean doubleClick, int mouseX, int mouseY) {
      if (index != this.selectedIndex || this.lastClicked != this.lastClickedCached) {
         IShaderPack var9 = Shaders.getShaderPack((String)this.shaderslist.get(index));
         if (this.checkCompatible(var9, index)) {
            this.selectIndex(index);
         }
      }
   }

   private void selectIndex(int integer) {
      this.selectedIndex = integer;
      this.lastClickedCached = this.lastClicked;
      Shaders.setShaderPack((String)this.shaderslist.get(integer));
      Shaders.uninit();
      this.shadersGui.updateButtons();
   }

   private boolean checkCompatible(IShaderPack iShaderPack, int integer) {
      if (iShaderPack == null) {
         return true;
      }

      Properties var7 = ResUtils.readProperties(iShaderPack.getResourceAsStream("/shaders/shaders.properties"), "Shaders");
      if (var7 == null) {
         return true;
      }

      String var8 = "version.1.8.9";
      String var9 = var7.getProperty(var8);
      if (var9 == null) {
         return true;
      }

      var9 = var9.trim();
      int var11 = Config.compareRelease("M5", var9);
      if (var11 >= 0) {
         return true;
      }

      String var12 = ("HD_U_" + var9).replace('_', ' ');
      String var13 = I18n.format("of.message.shaders.nv1", var12);
      String var14 = I18n.format("of.message.shaders.nv2");
      GuiYesNoCallback var15 = (boolean2, integer3) -> {
         if (boolean2) {
            this.selectIndex(integer);
         }

         this.mc.displayGuiScreen(this.shadersGui);
      };
      GuiYesNo var16 = new GuiYesNo(var15, var13, var14, 0);
      this.mc.displayGuiScreen(var16);
      return false;
   }

   @Override
   protected boolean isSelected(int index) {
      return index == this.selectedIndex;
   }

   @Override
   protected int getScrollBarX() {
      return this.width - 6;
   }

   @Override
   protected int getContentHeight() {
      return this.getSize() * 18;
   }

   @Override
   protected void drawBackground() {
   }

   @Override
   protected void drawSlot(int index, int left, int top, int right, int bottom, int slotHeight) {
      String var10 = (String)this.shaderslist.get(index);
      if (var10.equals("OFF")) {
         var10 = Lang.get("of.options.shaders.packNone");
      } else if (var10.equals("(internal)")) {
         var10 = Lang.get("of.options.shaders.packDefault");
      }

      this.shadersGui.drawCenteredString(var10, this.width / 2, top + 1, 14737632);
   }

   public int getSelectedIndex() {
      return this.selectedIndex;
   }
}
