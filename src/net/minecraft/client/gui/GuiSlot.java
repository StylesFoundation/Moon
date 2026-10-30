package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;

public abstract class GuiSlot {
   protected final Minecraft mc;
   protected int width;
   protected int height;
   protected int top;
   protected int bottom;
   protected int right;
   protected int left;
   protected final int slotHeight;
   private int scrollUpButtonID;
   private int scrollDownButtonID;
   protected int mouseX;
   protected int mouseY;
   protected boolean field_148163_i = true;
   protected int initialClickY = -2;
   protected float scrollMultiplier;
   protected float amountScrolled;
   protected int selectedElement = -1;
   protected long lastClicked;
   protected boolean field_178041_q = true;
   protected boolean showSelectionBox = true;
   protected boolean hasListHeader;
   protected int headerPadding;
   private boolean enabled = true;

   public GuiSlot(Minecraft minecraft, int integer2, int integer3, int integer4, int integer5, int integer6) {
      this.mc = minecraft;
      this.width = integer2;
      this.height = integer3;
      this.top = integer4;
      this.bottom = integer5;
      this.slotHeight = integer6;
      this.left = 0;
      this.right = integer2;
   }

   public void setDimensions(int integer1, int integer2, int integer3, int integer4) {
      this.width = integer1;
      this.height = integer2;
      this.top = integer3;
      this.bottom = integer4;
      this.left = 0;
      this.right = integer1;
   }

   public void setShowSelectionBox(boolean boolean1) {
      this.showSelectionBox = boolean1;
   }

   protected void setHasListHeader(boolean boolean1, int integer) {
      this.hasListHeader = boolean1;
      this.headerPadding = integer;
      if (!boolean1) {
         this.headerPadding = 0;
      }
   }

   protected abstract int getSize();

   protected abstract void elementClicked(int index, boolean doubleClick, int mouseX, int mouseY);

   protected abstract boolean isSelected(int index);

   protected int getContentHeight() {
      return this.getSize() * this.slotHeight + this.headerPadding;
   }

   protected abstract void drawBackground();

   protected void func_178040_a(int integer1, int integer2, int integer3) {
   }

   protected abstract void drawSlot(int index, int left, int top, int right, int bottom, int slotHeight);

   protected void drawListHeader(int integer1, int integer2, Tessellator tessellator) {
   }

   protected void func_148132_a(int integer1, int integer2) {
   }

   protected void func_148142_b(int integer1, int integer2) {
   }

   public int getSlotIndexFromScreenCoords(int integer1, int integer2) {
      int var3 = this.left + this.width / 2 - this.getListWidth() / 2;
      int var4 = this.left + this.width / 2 + this.getListWidth() / 2;
      int var5 = integer2 - this.top - this.headerPadding + (int)this.amountScrolled - 4;
      int var6 = var5 / this.slotHeight;
      return integer1 < this.getScrollBarX() && integer1 >= var3 && integer1 <= var4 && var6 >= 0 && var5 >= 0 && var6 < this.getSize() ? var6 : -1;
   }

   public void registerScrollButtons(int integer1, int integer2) {
      this.scrollUpButtonID = integer1;
      this.scrollDownButtonID = integer2;
   }

   protected void bindAmountScrolled() {
      this.amountScrolled = MathHelper.clamp_float(this.amountScrolled, 0.0F, this.func_148135_f());
   }

   public int func_148135_f() {
      return Math.max(0, this.getContentHeight() - (this.bottom - this.top - 4));
   }

   public int getAmountScrolled() {
      return (int)this.amountScrolled;
   }

   public boolean isMouseYWithinSlotBounds(int integer) {
      return integer >= this.top && integer <= this.bottom && this.mouseX >= this.left && this.mouseX <= this.right;
   }

   public void scrollBy(int integer) {
      this.amountScrolled += integer;
      this.bindAmountScrolled();
      this.initialClickY = -2;
   }

   public void actionPerformed(GuiButton guiButton) {
      if (guiButton.enabled) {
         if (guiButton.l == this.scrollUpButtonID) {
            this.amountScrolled = this.amountScrolled - this.slotHeight * 2 / 3;
            this.initialClickY = -2;
            this.bindAmountScrolled();
         } else if (guiButton.l == this.scrollDownButtonID) {
            this.amountScrolled = this.amountScrolled + this.slotHeight * 2 / 3;
            this.initialClickY = -2;
            this.bindAmountScrolled();
         }
      }
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      if (this.field_178041_q) {
         this.mouseX = mouseX;
         this.mouseY = mouseY;
         this.drawBackground();
         int var6 = this.getScrollBarX();
         int var7 = var6 + 6;
         this.bindAmountScrolled();
         GlStateManager.disableLighting();
         GlStateManager.disableFog();
         Tessellator var8 = Tessellator.getInstance();
         WorldRenderer var9 = var8.getWorldRenderer();
         this.drawContainerBackground(var8);
         int var10 = this.left + this.width / 2 - this.getListWidth() / 2 + 2;
         int var11 = this.top + 4 - (int)this.amountScrolled;
         if (this.hasListHeader) {
            this.drawListHeader(var10, var11, var8);
         }

         this.drawSelectionBox(var10, var11, mouseX, mouseY);
         GlStateManager.disableDepth();
         this.overlayBackground(0, this.top, 255, 255);
         this.overlayBackground(this.bottom, this.height, 255, 255);
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
         GlStateManager.disableAlpha();
         GlStateManager.shadeModel(7425);
         GlStateManager.disableTexture2D();
         var9.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
         var9.pos(this.left, this.top + 4, 0.0).tex(0.0, 1.0).color(0, 0, 0, 0).endVertex();
         var9.pos(this.right, this.top + 4, 0.0).tex(1.0, 1.0).color(0, 0, 0, 0).endVertex();
         var9.pos(this.right, this.top, 0.0).tex(1.0, 0.0).color(0, 0, 0, 255).endVertex();
         var9.pos(this.left, this.top, 0.0).tex(0.0, 0.0).color(0, 0, 0, 255).endVertex();
         var8.draw();
         var9.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
         var9.pos(this.left, this.bottom, 0.0).tex(0.0, 1.0).color(0, 0, 0, 255).endVertex();
         var9.pos(this.right, this.bottom, 0.0).tex(1.0, 1.0).color(0, 0, 0, 255).endVertex();
         var9.pos(this.right, this.bottom - 4, 0.0).tex(1.0, 0.0).color(0, 0, 0, 0).endVertex();
         var9.pos(this.left, this.bottom - 4, 0.0).tex(0.0, 0.0).color(0, 0, 0, 0).endVertex();
         var8.draw();
         int var13 = this.func_148135_f();
         if (var13 > 0) {
            int var14 = MathHelper.clamp_int(
               (this.bottom - this.top) * (this.bottom - this.top) / Math.max(this.getContentHeight(), 1), 32, this.bottom - this.top - 8
            );
            int var15 = (int)this.amountScrolled * (this.bottom - this.top - var14) / var13 + this.top;
            if (var15 < this.top) {
               var15 = this.top;
            }

            var9.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            var9.pos(var6, this.bottom, 0.0).tex(0.0, 1.0).color(0, 0, 0, 255).endVertex();
            var9.pos(var7, this.bottom, 0.0).tex(1.0, 1.0).color(0, 0, 0, 255).endVertex();
            var9.pos(var7, this.top, 0.0).tex(1.0, 0.0).color(0, 0, 0, 255).endVertex();
            var9.pos(var6, this.top, 0.0).tex(0.0, 0.0).color(0, 0, 0, 255).endVertex();
            var8.draw();
            var9.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            var9.pos(var6, var15 + var14, 0.0).tex(0.0, 1.0).color(128, 128, 128, 255).endVertex();
            var9.pos(var7, var15 + var14, 0.0).tex(1.0, 1.0).color(128, 128, 128, 255).endVertex();
            var9.pos(var7, var15, 0.0).tex(1.0, 0.0).color(128, 128, 128, 255).endVertex();
            var9.pos(var6, var15, 0.0).tex(0.0, 0.0).color(128, 128, 128, 255).endVertex();
            var8.draw();
            var9.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            var9.pos(var6, var15 + var14 - 1, 0.0).tex(0.0, 1.0).color(192, 192, 192, 255).endVertex();
            var9.pos(var7 - 1, var15 + var14 - 1, 0.0).tex(1.0, 1.0).color(192, 192, 192, 255).endVertex();
            var9.pos(var7 - 1, var15, 0.0).tex(1.0, 0.0).color(192, 192, 192, 255).endVertex();
            var9.pos(var6, var15, 0.0).tex(0.0, 0.0).color(192, 192, 192, 255).endVertex();
            var8.draw();
         }

         this.func_148142_b(mouseX, mouseY);
         GlStateManager.enableTexture2D();
         GlStateManager.shadeModel(7424);
         GlStateManager.enableAlpha();
         GlStateManager.disableBlend();
      }
   }

   public void handleMouseInput() {
      if (this.isMouseYWithinSlotBounds(this.mouseY)) {
         if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState() && this.mouseY >= this.top && this.mouseY <= this.bottom) {
            int var3 = (this.width - this.getListWidth()) / 2;
            int var4 = (this.width + this.getListWidth()) / 2;
            int var5 = this.mouseY - this.top - this.headerPadding + (int)this.amountScrolled - 4;
            int var6 = var5 / this.slotHeight;
            if (var6 < this.getSize() && this.mouseX >= var3 && this.mouseX <= var4 && var6 >= 0 && var5 >= 0) {
               this.elementClicked(var6, false, this.mouseX, this.mouseY);
               this.selectedElement = var6;
            } else if (this.mouseX >= var3 && this.mouseX <= var4 && var5 < 0) {
               this.func_148132_a(this.mouseX - var3, this.mouseY - this.top + (int)this.amountScrolled - 4);
            }
         }

         if (!Mouse.isButtonDown(0) || !this.getEnabled()) {
            this.initialClickY = -1;
         } else if (this.initialClickY == -1) {
            boolean var12 = true;
            if (this.mouseY >= this.top && this.mouseY <= this.bottom) {
               int var14 = (this.width - this.getListWidth()) / 2;
               int var15 = (this.width + this.getListWidth()) / 2;
               int var16 = this.mouseY - this.top - this.headerPadding + (int)this.amountScrolled - 4;
               int var7 = var16 / this.slotHeight;
               if (var7 < this.getSize() && this.mouseX >= var14 && this.mouseX <= var15 && var7 >= 0 && var16 >= 0) {
                  boolean var8 = var7 == this.selectedElement && Minecraft.getSystemTime() - this.lastClicked < 250L;
                  this.elementClicked(var7, var8, this.mouseX, this.mouseY);
                  this.selectedElement = var7;
                  this.lastClicked = Minecraft.getSystemTime();
               } else if (this.mouseX >= var14 && this.mouseX <= var15 && var16 < 0) {
                  this.func_148132_a(this.mouseX - var14, this.mouseY - this.top + (int)this.amountScrolled - 4);
                  var12 = false;
               }

               int var17 = this.getScrollBarX();
               int var9 = var17 + 6;
               if (this.mouseX >= var17 && this.mouseX <= var9) {
                  this.scrollMultiplier = -1.0F;
                  int var10 = this.func_148135_f();
                  if (var10 < 1) {
                     var10 = 1;
                  }

                  int var11 = MathHelper.clamp_int(
                     (int)((float)((this.bottom - this.top) * (this.bottom - this.top)) / this.getContentHeight()), 32, this.bottom - this.top - 8
                  );
                  this.scrollMultiplier = this.scrollMultiplier / ((float)(this.bottom - this.top - var11) / var10);
               } else {
                  this.scrollMultiplier = 1.0F;
               }

               if (var12) {
                  this.initialClickY = this.mouseY;
               } else {
                  this.initialClickY = -2;
               }
            } else {
               this.initialClickY = -2;
            }
         } else if (this.initialClickY >= 0) {
            this.amountScrolled = this.amountScrolled - (this.mouseY - this.initialClickY) * this.scrollMultiplier;
            this.initialClickY = this.mouseY;
         }

         int var13 = Mouse.getEventDWheel();
         if (var13 != 0) {
            if (var13 > 0) {
               var13 = -1;
            } else if (var13 < 0) {
               var13 = 1;
            }

            this.amountScrolled = this.amountScrolled + var13 * this.slotHeight / 2;
         }
      }
   }

   public void setEnabled(boolean boolean1) {
      this.enabled = boolean1;
   }

   public boolean getEnabled() {
      return this.enabled;
   }

   public int getListWidth() {
      return 220;
   }

   protected void drawSelectionBox(int integer1, int integer2, int integer3, int integer4) {
      int var7 = this.getSize();
      Tessellator var8 = Tessellator.getInstance();
      WorldRenderer var9 = var8.getWorldRenderer();

      for (int var10 = 0; var10 < var7; var10++) {
         int var11 = integer2 + var10 * this.slotHeight + this.headerPadding;
         int var12 = this.slotHeight - 4;
         if (var11 > this.bottom || var11 + var12 < this.top) {
            this.func_178040_a(var10, integer1, var11);
         }

         if (this.showSelectionBox && this.isSelected(var10)) {
            int var13 = this.left + (this.width / 2 - this.getListWidth() / 2);
            int var14 = this.left + this.width / 2 + this.getListWidth() / 2;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableTexture2D();
            var9.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            var9.pos(var13, var11 + var12 + 2, 0.0).tex(0.0, 1.0).color(128, 128, 128, 255).endVertex();
            var9.pos(var14, var11 + var12 + 2, 0.0).tex(1.0, 1.0).color(128, 128, 128, 255).endVertex();
            var9.pos(var14, var11 - 2, 0.0).tex(1.0, 0.0).color(128, 128, 128, 255).endVertex();
            var9.pos(var13, var11 - 2, 0.0).tex(0.0, 0.0).color(128, 128, 128, 255).endVertex();
            var9.pos(var13 + 1, var11 + var12 + 1, 0.0).tex(0.0, 1.0).color(0, 0, 0, 255).endVertex();
            var9.pos(var14 - 1, var11 + var12 + 1, 0.0).tex(1.0, 1.0).color(0, 0, 0, 255).endVertex();
            var9.pos(var14 - 1, var11 - 1, 0.0).tex(1.0, 0.0).color(0, 0, 0, 255).endVertex();
            var9.pos(var13 + 1, var11 - 1, 0.0).tex(0.0, 0.0).color(0, 0, 0, 255).endVertex();
            var8.draw();
            GlStateManager.enableTexture2D();
         }

         if (!(this instanceof GuiResourcePackList) || var11 >= this.top - this.slotHeight && var11 <= this.bottom) {
            this.drawSlot(var10, integer1, var11, var12, integer3, integer4);
         }
      }
   }

   protected int getScrollBarX() {
      return this.width / 2 + 124;
   }

   protected void overlayBackground(int integer1, int integer2, int integer3, int integer4) {
      Tessellator var7 = Tessellator.getInstance();
      WorldRenderer var8 = var7.getWorldRenderer();
      this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      var8.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      var8.pos(this.left, integer2, 0.0).tex(0.0, integer2 / 32.0F).color(64, 64, 64, integer4).endVertex();
      var8.pos(this.left + this.width, integer2, 0.0).tex(this.width / 32.0F, integer2 / 32.0F).color(64, 64, 64, integer4).endVertex();
      var8.pos(this.left + this.width, integer1, 0.0).tex(this.width / 32.0F, integer1 / 32.0F).color(64, 64, 64, integer3).endVertex();
      var8.pos(this.left, integer1, 0.0).tex(0.0, integer1 / 32.0F).color(64, 64, 64, integer3).endVertex();
      var7.draw();
   }

   public void setSlotXBoundsFromLeft(int integer) {
      this.left = integer;
      this.right = integer + this.width;
   }

   public int getSlotHeight() {
      return this.slotHeight;
   }

   protected void drawContainerBackground(Tessellator tessellator) {
      WorldRenderer var4 = tessellator.getWorldRenderer();
      this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      var4.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      var4.pos(this.left, this.bottom, 0.0).tex(this.left / 32.0F, (this.bottom + (int)this.amountScrolled) / 32.0F).color(32, 32, 32, 255).endVertex();
      var4.pos(this.right, this.bottom, 0.0).tex(this.right / 32.0F, (this.bottom + (int)this.amountScrolled) / 32.0F).color(32, 32, 32, 255).endVertex();
      var4.pos(this.right, this.top, 0.0).tex(this.right / 32.0F, (this.top + (int)this.amountScrolled) / 32.0F).color(32, 32, 32, 255).endVertex();
      var4.pos(this.left, this.top, 0.0).tex(this.left / 32.0F, (this.top + (int)this.amountScrolled) / 32.0F).color(32, 32, 32, 255).endVertex();
      tessellator.draw();
   }
}
