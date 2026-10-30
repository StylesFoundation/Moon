package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

public class GuiOptionsRowList extends GuiListExtended {
   private final List<GuiOptionsRowList.Row> field_148184_k = Lists.newArrayList();

   public GuiOptionsRowList(Minecraft minecraft, int integer2, int integer3, int integer4, int integer5, int integer6, GameSettings.Options... arr) {
      super(minecraft, integer2, integer3, integer4, integer5, integer6);
      this.field_148163_i = false;

      for (byte var10 = 0; var10 < arr.length; var10 += 2) {
         GameSettings.Options var11 = arr[var10];
         GameSettings.Options var12 = var10 < arr.length - 1 ? arr[var10 + 1] : null;
         GuiButton var13 = this.func_148182_a(minecraft, integer2 / 2 - 155, 0, var11);
         GuiButton var14 = this.func_148182_a(minecraft, integer2 / 2 - 155 + 160, 0, var12);
         this.field_148184_k.add(new GuiOptionsRowList.Row(var13, var14));
      }
   }

   private GuiButton func_148182_a(Minecraft minecraft, int integer2, int integer3, GameSettings.Options options) {
      if (options == null) {
         return null;
      }

      int var5 = options.returnEnumOrdinal();
      return options.getEnumFloat()
         ? new GuiOptionSlider(var5, integer2, integer3, options)
         : new GuiOptionButton(var5, integer2, integer3, options, minecraft.gameSettings.getKeyBinding(options));
   }

   public GuiOptionsRowList.Row getListEntry(int integer) {
      return this.field_148184_k.get(integer);
   }

   @Override
   protected int getSize() {
      return this.field_148184_k.size();
   }

   @Override
   public int getListWidth() {
      return 400;
   }

   @Override
   protected int getScrollBarX() {
      return super.getScrollBarX() + 32;
   }

   public static class Row implements GuiListExtended.IGuiListEntry {
      private final Minecraft field_148325_a = Minecraft.getMinecraft();
      private final GuiButton field_148323_b;
      private final GuiButton field_148324_c;

      public Row(GuiButton guiButton1, GuiButton guiButton2) {
         this.field_148323_b = guiButton1;
         this.field_148324_c = guiButton2;
      }

      @Override
      public void drawEntry(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7, boolean boolean8) {
         if (this.field_148323_b != null) {
            this.field_148323_b.yPosition = integer3;
            this.field_148323_b.drawButton(this.field_148325_a, integer6, integer7);
         }

         if (this.field_148324_c != null) {
            this.field_148324_c.yPosition = integer3;
            this.field_148324_c.drawButton(this.field_148325_a, integer6, integer7);
         }
      }

      @Override
      public boolean mousePressed(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
         if (this.field_148323_b.mousePressed(this.field_148325_a, integer2, integer3)) {
            if (this.field_148323_b instanceof GuiOptionButton) {
               this.field_148325_a.gameSettings.setOptionValue(((GuiOptionButton)this.field_148323_b).returnEnumOptions(), 1);
               this.field_148323_b.displayString = this.field_148325_a.gameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(this.field_148323_b.l));
            }

            return true;
         } else if (this.field_148324_c != null && this.field_148324_c.mousePressed(this.field_148325_a, integer2, integer3)) {
            if (this.field_148324_c instanceof GuiOptionButton) {
               this.field_148325_a.gameSettings.setOptionValue(((GuiOptionButton)this.field_148324_c).returnEnumOptions(), 1);
               this.field_148324_c.displayString = this.field_148325_a.gameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(this.field_148324_c.l));
            }

            return true;
         } else {
            return false;
         }
      }

      @Override
      public void mouseReleased(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
         if (this.field_148323_b != null) {
            this.field_148323_b.mouseReleased(integer2, integer3);
         }

         if (this.field_148324_c != null) {
            this.field_148324_c.mouseReleased(integer2, integer3);
         }
      }

      @Override
      public void setSelected(int integer1, int integer2, int integer3) {
      }
   }
}
