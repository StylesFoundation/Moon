package com.moon.viamcp.gui;

import com.moon.viamcp.ViaMCP;
import com.moon.viamcp.protocols.ProtocolCollection;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class VersionSlider extends GuiButton {
   private final int minIndex;
   private final int maxIndex;
   private final String initialText;
   public boolean dragging;
   private float sliderValue = 1.0F;

   public VersionSlider(int buttonId, int x, int y, String text, int minIndex, int maxIndex, float defaultValue) {
      super(buttonId, x, y, 125, 20, "");
      this.initialText = text;
      this.minIndex = minIndex;
      this.maxIndex = maxIndex;
      this.sliderValue = (defaultValue - minIndex) / (maxIndex - minIndex);
      this.displayString = this.updateDisplayString();
   }

   public float getSelectedIndex() {
      return this.minIndex + (this.maxIndex - this.minIndex) * this.sliderValue;
   }

   public void setByVersionAndRange(float version, boolean inRange) {
      this.sliderValue = (version - this.minIndex) / (this.maxIndex - this.minIndex);
      this.displayString = this.updateDisplayString();
   }

   public float getSliderValue() {
      return this.sliderValue;
   }

   private String updateDisplayString() {
      Arrays.asList(ProtocolCollection.values());
      int selectedIndex = Math.round(this.getSelectedIndex());
      ProtocolCollection protocol = ProtocolCollection.values()[ProtocolCollection.values().length - 1 - selectedIndex];
      if (protocol != null) {
         int version = protocol.Method572().getVersion();
         if (ViaMCP.getInstance().getVersion() != version) {
            ViaMCP.getInstance().setVersion(version);
         }

         return protocol.Method572().getName();
      } else {
         return "Invalid Selection";
      }
   }

   @Override
   protected int getHoverState(boolean mouseOver) {
      return 0;
   }

   @Override
   protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
      if (this.visible) {
         if (this.dragging) {
            this.sliderValue = (float)(mouseX - (this.xPosition + 4)) / (this.width - 8);
            if (this.sliderValue < 0.0F) {
               this.sliderValue = 0.0F;
            }

            if (this.sliderValue > 1.0F) {
               this.sliderValue = 1.0F;
            }

            this.displayString = this.updateDisplayString();
         }

         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
         this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
      }
   }

   public void setSliderValue(float sliderValue) {
      this.sliderValue = sliderValue;
      this.displayString = this.updateDisplayString();
   }

   @Override
   public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
      if (super.mousePressed(mc, mouseX, mouseY)) {
         this.sliderValue = (float)(mouseX - (this.xPosition + 4)) / (this.width - 8);
         if (this.sliderValue < 0.0F) {
            this.sliderValue = 0.0F;
         }

         if (this.sliderValue > 1.0F) {
            this.sliderValue = 1.0F;
         }

         this.displayString = this.updateDisplayString();
         this.dragging = true;
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void mouseReleased(int mouseX, int mouseY) {
      this.dragging = false;
   }
}
