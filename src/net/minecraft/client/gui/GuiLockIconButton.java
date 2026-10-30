package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class GuiLockIconButton extends GuiButton {
   private boolean field_175231_o = false;

   public GuiLockIconButton(int integer1, int integer2, int integer3) {
      super(integer1, integer2, integer3, 20, 20, "");
   }

   public boolean func_175230_c() {
      return this.field_175231_o;
   }

   public void func_175229_b(boolean boolean1) {
      this.field_175231_o = boolean1;
   }

   @Override
   public void drawButton(Minecraft minecraft, int integer2, int integer3) {
      if (this.visible) {
         minecraft.getTextureManager().bindTexture(GuiButton.buttonTextures);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         boolean var4 = integer2 >= this.xPosition
            && integer3 >= this.yPosition
            && integer2 < this.xPosition + this.width
            && integer3 < this.yPosition + this.h;
         GuiLockIconButton.Icon var5;
         if (this.field_175231_o) {
            if (!this.enabled) {
               var5 = GuiLockIconButton.Icon.LOCKED_DISABLED;
            } else if (var4) {
               var5 = GuiLockIconButton.Icon.LOCKED_HOVER;
            } else {
               var5 = GuiLockIconButton.Icon.LOCKED;
            }
         } else if (!this.enabled) {
            var5 = GuiLockIconButton.Icon.UNLOCKED_DISABLED;
         } else if (var4) {
            var5 = GuiLockIconButton.Icon.UNLOCKED_HOVER;
         } else {
            var5 = GuiLockIconButton.Icon.UNLOCKED;
         }

         this.drawTexturedModalRect(this.xPosition, this.yPosition, var5.func_178910_a(), var5.func_178912_b(), this.width, this.h);
      }
   }

   enum Icon {
      LOCKED(0, 146),
      LOCKED_HOVER(0, 166),
      LOCKED_DISABLED(0, 186),
      UNLOCKED(20, 146),
      UNLOCKED_HOVER(20, 166),
      UNLOCKED_DISABLED(20, 186);

      private final int field_178914_g;
      private final int field_178920_h;

      Icon(int integer3, int integer4) {
         this.field_178914_g = integer3;
         this.field_178920_h = integer4;
      }

      public int func_178910_a() {
         return this.field_178914_g;
      }

      public int func_178912_b() {
         return this.field_178920_h;
      }

      private static GuiLockIconButton.Icon[] Method696() {
         return new GuiLockIconButton.Icon[]{LOCKED, LOCKED_HOVER, LOCKED_DISABLED, UNLOCKED, UNLOCKED_HOVER, UNLOCKED_DISABLED};
      }
   }
}
