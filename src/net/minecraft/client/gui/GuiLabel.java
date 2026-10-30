package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

public class GuiLabel extends Gui {
   protected int field_146167_a = 200;
   protected int field_146161_f = 20;
   public int field_146162_g;
   public int field_146174_h;
   private final List<String> field_146173_k;
   public int field_175204_i;
   private boolean centered;
   public boolean visible = true;
   private final boolean labelBgEnabled;
   private final int field_146168_n;
   private final int field_146169_o;
   private final int field_146166_p;
   private final int field_146165_q;
   private final FontRenderer fontRenderer;
   private final int field_146163_s;

   public GuiLabel(FontRenderer fontRenderer, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7) {
      this.fontRenderer = fontRenderer;
      this.field_175204_i = integer2;
      this.field_146162_g = integer3;
      this.field_146174_h = integer4;
      this.field_146167_a = integer5;
      this.field_146161_f = integer6;
      this.field_146173_k = Lists.newArrayList();
      this.centered = false;
      this.labelBgEnabled = false;
      this.field_146168_n = integer7;
      this.field_146169_o = -1;
      this.field_146166_p = -1;
      this.field_146165_q = -1;
      this.field_146163_s = 0;
   }

   public void func_175202_a(String string) {
      this.field_146173_k.add(I18n.format(string));
   }

   public GuiLabel setCentered() {
      this.centered = true;
      return this;
   }

   public void drawLabel(Minecraft minecraft, int integer2, int integer3) {
      if (this.visible) {
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         this.drawLabelBackground(minecraft, integer2, integer3);
         int var7 = this.field_146174_h + this.field_146161_f / 2 + this.field_146163_s / 2 - this.field_146173_k.size() * 10 / 2;

         for (int var8 = 0; var8 < this.field_146173_k.size(); var8++) {
            if (this.centered) {
               this.drawCenteredString(
                  this.fontRenderer, this.field_146173_k.get(var8), this.field_146162_g + this.field_146167_a / 2, var7 + var8 * 10, this.field_146168_n
               );
            } else {
               this.drawString(this.fontRenderer, this.field_146173_k.get(var8), this.field_146162_g, var7 + var8 * 10, this.field_146168_n);
            }
         }
      }
   }

   protected void drawLabelBackground(Minecraft minecraft, int integer2, int integer3) {
      if (this.labelBgEnabled) {
         int var4 = this.field_146167_a + this.field_146163_s * 2;
         int var5 = this.field_146161_f + this.field_146163_s * 2;
         int var6 = this.field_146162_g - this.field_146163_s;
         int var7 = this.field_146174_h - this.field_146163_s;
         drawRect(var6, var7, var6 + var4, var7 + var5, this.field_146169_o);
         this.drawHorizontalLine(var6, var6 + var4, var7, this.field_146166_p);
         this.drawHorizontalLine(var6, var6 + var4, var7 + var5, this.field_146165_q);
         this.drawVerticalLine(var6, var7, var7 + var5, this.field_146166_p);
         this.drawVerticalLine(var6 + var4, var7, var7 + var5, this.field_146165_q);
      }
   }
}
