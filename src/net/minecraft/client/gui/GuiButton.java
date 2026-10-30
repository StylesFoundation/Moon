package net.minecraft.client.gui;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiButton extends Gui {
   protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");
   protected int width;
   protected int h;
   public int xPosition;
   public int yPosition;
   public String displayString;
   public int l;
   public boolean enabled = true;
   public boolean visible = true;
   protected boolean hovered;
   private boolean p;

   public GuiButton(int integer1, int integer2, int integer3, String string) {
      this(integer1, integer2, integer3, 200, 20, string);
   }

   public GuiButton(int integer1, int integer2, int integer3, int integer4, int integer5, String string) {
      this(integer1, integer2, integer3, integer4, integer5, string, false);
   }

   public GuiButton(int integer1, int integer2, int integer3, int integer4, int integer5, String string, boolean boolean7) {
      this.l = integer1;
      this.xPosition = integer2;
      this.yPosition = integer3;
      this.width = integer4;
      this.h = integer5;
      this.displayString = string;
      this.p = boolean7;
   }

   protected int getHoverState(boolean boolean1) {
      byte var2 = 1;
      if (!this.enabled) {
         var2 = 0;
      } else if (boolean1) {
         var2 = 2;
      }

      return var2;
   }

   public void drawButton(Minecraft minecraft, int integer2, int integer3) {
      if (this.visible) {
         FontRenderer var6 = minecraft.fontRendererObj;
         minecraft.getTextureManager().bindTexture(buttonTextures);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.hovered = integer2 >= this.xPosition
            && integer3 >= this.yPosition
            && integer2 < this.xPosition + this.width
            && integer3 < this.yPosition + this.h;
         int var7 = this.getHoverState(this.hovered);
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.blendFunc(770, 771);
         if (this.p) {
            Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.h, Integer.MIN_VALUE);
         } else {
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + var7 * 20, this.width / 2, this.h);
            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + var7 * 20, this.width / 2, this.h);
         }

         this.mouseDragged(minecraft, integer2, integer3);
         int var8 = 14737632;
         if (!this.enabled) {
            var8 = 10526880;
         } else if (this.hovered) {
            var8 = 16777120;
         }

         if (this.p) {
            this.drawCenteredString(
               var6,
               this.displayString,
               this.xPosition + this.width / 2,
               this.yPosition + (this.h - 8) / 2,
               this.enabled ? (this.hovered ? -1 : -5592406) : Color.darkGray.getRGB()
            );
         } else {
            this.drawCenteredString(var6, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.h - 8) / 2, var8);
         }
      }
   }

   protected void mouseDragged(Minecraft minecraft, int integer2, int integer3) {
   }

   public void mouseReleased(int integer1, int integer2) {
   }

   public boolean mousePressed(Minecraft minecraft, int integer2, int integer3) {
      return this.enabled
         && this.visible
         && integer2 >= this.xPosition
         && integer3 >= this.yPosition
         && integer2 < this.xPosition + this.width
         && integer3 < this.yPosition + this.h;
   }

   public boolean isMouseOver() {
      return this.hovered;
   }

   public void drawButtonForegroundLayer(int integer1, int integer2) {
   }

   public void playPressSound(SoundHandler soundHandler) {
      soundHandler.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
   }

   public int getButtonWidth() {
      return this.width;
   }

   public void setWidth(int integer) {
      this.width = integer;
   }
}
