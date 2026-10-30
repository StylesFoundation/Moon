package net.minecraft.client.gui;

import com.google.common.base.MoreObjects;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IntHashMap;

public class GuiPageButtonList extends GuiListExtended {
   private final List<GuiPageButtonList.GuiEntry> field_178074_u = Lists.newArrayList();
   private final IntHashMap<Gui> field_178073_v = new IntHashMap<>();
   private final List<GuiTextField> field_178072_w = Lists.newArrayList();
   private final GuiPageButtonList.GuiListEntry[][] field_178078_x;
   private int field_178077_y;
   private final GuiPageButtonList.GuiResponder field_178076_z;
   private Gui field_178075_A;

   public GuiPageButtonList(
      Minecraft minecraft,
      int integer2,
      int integer3,
      int integer4,
      int integer5,
      int integer6,
      GuiPageButtonList.GuiResponder guiResponder,
      GuiPageButtonList.GuiListEntry[]... arr
   ) {
      super(minecraft, integer2, integer3, integer4, integer5, integer6);
      this.field_178076_z = guiResponder;
      this.field_178078_x = arr;
      this.field_148163_i = false;
      this.func_178069_s();
      this.func_178055_t();
   }

   private void func_178069_s() {
      for (GuiPageButtonList.GuiListEntry[] var6 : this.field_178078_x) {
         for (byte var7 = 0; var7 < var6.length; var7 += 2) {
            GuiPageButtonList.GuiListEntry var8 = var6[var7];
            GuiPageButtonList.GuiListEntry var9 = var7 < var6.length - 1 ? var6[var7 + 1] : null;
            Gui var10 = this.func_178058_a(var8, 0, var9 == null);
            Gui var11 = this.func_178058_a(var9, 160, var8 == null);
            GuiPageButtonList.GuiEntry var12 = new GuiPageButtonList.GuiEntry(var10, var11);
            this.field_178074_u.add(var12);
            if (var8 != null && var10 != null) {
               this.field_178073_v.addKey(var8.func_178935_b(), var10);
               if (var10 instanceof GuiTextField) {
                  this.field_178072_w.add((GuiTextField)var10);
               }
            }

            if (var9 != null && var11 != null) {
               this.field_178073_v.addKey(var9.func_178935_b(), var11);
               if (var11 instanceof GuiTextField) {
                  this.field_178072_w.add((GuiTextField)var11);
               }
            }
         }
      }
   }

   private void func_178055_t() {
      this.field_178074_u.clear();

      for (byte var1 = 0; var1 < this.field_178078_x[this.field_178077_y].length; var1 += 2) {
         GuiPageButtonList.GuiListEntry var2 = this.field_178078_x[this.field_178077_y][var1];
         GuiPageButtonList.GuiListEntry var3 = var1 < this.field_178078_x[this.field_178077_y].length - 1
            ? this.field_178078_x[this.field_178077_y][var1 + 1]
            : null;
         Gui var4 = this.field_178073_v.lookup(var2.func_178935_b());
         Gui var5 = var3 != null ? this.field_178073_v.lookup(var3.func_178935_b()) : null;
         GuiPageButtonList.GuiEntry var6 = new GuiPageButtonList.GuiEntry(var4, var5);
         this.field_178074_u.add(var6);
      }
   }

   public void func_181156_c(int integer) {
      if (integer != this.field_178077_y) {
         int var2 = this.field_178077_y;
         this.field_178077_y = integer;
         this.func_178055_t();
         this.func_178060_e(var2, integer);
         this.amountScrolled = 0.0F;
      }
   }

   public int func_178059_e() {
      return this.field_178077_y;
   }

   public int func_178057_f() {
      return this.field_178078_x.length;
   }

   public Gui func_178056_g() {
      return this.field_178075_A;
   }

   public void func_178071_h() {
      if (this.field_178077_y > 0) {
         this.func_181156_c(this.field_178077_y - 1);
      }
   }

   public void func_178064_i() {
      if (this.field_178077_y < this.field_178078_x.length - 1) {
         this.func_181156_c(this.field_178077_y + 1);
      }
   }

   public Gui func_178061_c(int integer) {
      return this.field_178073_v.lookup(integer);
   }

   private void func_178060_e(int integer1, int integer2) {
      for (GuiPageButtonList.GuiListEntry var6 : this.field_178078_x[integer1]) {
         if (var6 != null) {
            this.func_178066_a(this.field_178073_v.lookup(var6.func_178935_b()), false);
         }
      }

      for (GuiPageButtonList.GuiListEntry var10 : this.field_178078_x[integer2]) {
         if (var10 != null) {
            this.func_178066_a(this.field_178073_v.lookup(var10.func_178935_b()), true);
         }
      }
   }

   private void func_178066_a(Gui gui, boolean boolean2) {
      if (gui instanceof GuiButton) {
         ((GuiButton)gui).visible = boolean2;
      } else if (gui instanceof GuiTextField) {
         ((GuiTextField)gui).setVisible(boolean2);
      } else if (gui instanceof GuiLabel) {
         ((GuiLabel)gui).visible = boolean2;
      }
   }

   private Gui func_178058_a(GuiPageButtonList.GuiListEntry guiListEntry, int integer, boolean boolean3) {
      return guiListEntry instanceof GuiPageButtonList.GuiSlideEntry
         ? this.func_178067_a(this.width / 2 - 155 + integer, 0, (GuiPageButtonList.GuiSlideEntry)guiListEntry)
         : (
            guiListEntry instanceof GuiPageButtonList.GuiButtonEntry
               ? this.func_178065_a(this.width / 2 - 155 + integer, 0, (GuiPageButtonList.GuiButtonEntry)guiListEntry)
               : (
                  guiListEntry instanceof GuiPageButtonList.EditBoxEntry
                     ? this.func_178068_a(this.width / 2 - 155 + integer, 0, (GuiPageButtonList.EditBoxEntry)guiListEntry)
                     : (
                        guiListEntry instanceof GuiPageButtonList.GuiLabelEntry
                           ? this.func_178063_a(this.width / 2 - 155 + integer, 0, (GuiPageButtonList.GuiLabelEntry)guiListEntry, boolean3)
                           : null
                     )
               )
         );
   }

   public void func_181155_a(boolean boolean1) {
      for (GuiPageButtonList.GuiEntry var3 : this.field_178074_u) {
         if (var3.field_178029_b instanceof GuiButton) {
            ((GuiButton)var3.field_178029_b).enabled = boolean1;
         }

         if (var3.field_178030_c instanceof GuiButton) {
            ((GuiButton)var3.field_178030_c).enabled = boolean1;
         }
      }
   }

   @Override
   public boolean mouseClicked(int integer1, int integer2, int integer3) {
      boolean var4 = super.mouseClicked(integer1, integer2, integer3);
      int var5 = this.getSlotIndexFromScreenCoords(integer1, integer2);
      if (var5 >= 0) {
         GuiPageButtonList.GuiEntry var6 = this.getListEntry(var5);
         if (this.field_178075_A != var6.field_178028_d && this.field_178075_A != null && this.field_178075_A instanceof GuiTextField) {
            ((GuiTextField)this.field_178075_A).setFocused(false);
         }

         this.field_178075_A = var6.field_178028_d;
      }

      return var4;
   }

   private GuiSlider func_178067_a(int integer1, int integer2, GuiPageButtonList.GuiSlideEntry guiSlideEntry) {
      GuiSlider var4 = new GuiSlider(
         this.field_178076_z,
         guiSlideEntry.func_178935_b(),
         integer1,
         integer2,
         guiSlideEntry.func_178936_c(),
         guiSlideEntry.func_178943_e(),
         guiSlideEntry.func_178944_f(),
         guiSlideEntry.func_178942_g(),
         guiSlideEntry.func_178945_a()
      );
      var4.visible = guiSlideEntry.func_178934_d();
      return var4;
   }

   private GuiListButton func_178065_a(int integer1, int integer2, GuiPageButtonList.GuiButtonEntry guiButtonEntry) {
      GuiListButton var4 = new GuiListButton(
         this.field_178076_z, guiButtonEntry.func_178935_b(), integer1, integer2, guiButtonEntry.func_178936_c(), guiButtonEntry.func_178940_a()
      );
      var4.visible = guiButtonEntry.func_178934_d();
      return var4;
   }

   private GuiTextField func_178068_a(int integer1, int integer2, GuiPageButtonList.EditBoxEntry editBoxEntry) {
      GuiTextField var6 = new GuiTextField(editBoxEntry.func_178935_b(), this.mc.fontRendererObj, integer1, integer2, 150, 20);
      var6.setText(editBoxEntry.func_178936_c());
      var6.func_175207_a(this.field_178076_z);
      var6.setVisible(editBoxEntry.func_178934_d());
      var6.setValidator(editBoxEntry.func_178950_a());
      return var6;
   }

   private GuiLabel func_178063_a(int integer1, int integer2, GuiPageButtonList.GuiLabelEntry guiLabelEntry, boolean boolean4) {
      GuiLabel var7;
      if (boolean4) {
         var7 = new GuiLabel(this.mc.fontRendererObj, guiLabelEntry.func_178935_b(), integer1, integer2, this.width - integer1 * 2, 20, -1);
      } else {
         var7 = new GuiLabel(this.mc.fontRendererObj, guiLabelEntry.func_178935_b(), integer1, integer2, 150, 20, -1);
      }

      var7.visible = guiLabelEntry.func_178934_d();
      var7.func_175202_a(guiLabelEntry.func_178936_c());
      var7.setCentered();
      return var7;
   }

   public void func_178062_a(char character, int integer) {
      if (this.field_178075_A instanceof GuiTextField var5) {
         if (!GuiScreen.isKeyComboCtrlV(integer)) {
            if (integer == 15) {
               var5.setFocused(false);
               int var15 = this.field_178072_w.indexOf(this.field_178075_A);
               if (GuiScreen.isShiftKeyDown()) {
                  if (var15 == 0) {
                     var15 = this.field_178072_w.size() - 1;
                  } else {
                     var15--;
                  }
               } else if (var15 == this.field_178072_w.size() - 1) {
                  var15 = 0;
               } else {
                  var15++;
               }

               this.field_178075_A = this.field_178072_w.get(var15);
               GuiTextField var14 = (GuiTextField)this.field_178075_A;
               var14.setFocused(true);
               int var7 = var14.yPosition + this.slotHeight;
               int var8 = var14.yPosition;
               if (var7 > this.bottom) {
                  this.amountScrolled = this.amountScrolled + (var7 - this.bottom);
               } else if (var8 < this.top) {
                  this.amountScrolled = var8;
               }
            } else {
               var5.textboxKeyTyped(character, integer);
            }
         } else {
            String[] var17 = GuiScreen.getClipboardString().split(";");
            int var18 = this.field_178072_w.indexOf(this.field_178075_A);
            int var9 = var18;

            for (String var13 : var17) {
               this.field_178072_w.get(var9).setText(var13);
               if (var9 == this.field_178072_w.size() - 1) {
                  var9 = 0;
               } else {
                  var9++;
               }

               if (var9 == var18) {
                  break;
               }
            }
         }
      }
   }

   public GuiPageButtonList.GuiEntry getListEntry(int integer) {
      return this.field_178074_u.get(integer);
   }

   @Override
   public int getSize() {
      return this.field_178074_u.size();
   }

   @Override
   public int getListWidth() {
      return 400;
   }

   @Override
   protected int getScrollBarX() {
      return super.getScrollBarX() + 32;
   }

   public static class EditBoxEntry extends GuiPageButtonList.GuiListEntry {
      private final Predicate<String> field_178951_a;

      public EditBoxEntry(int integer, String string, boolean boolean3, Predicate<String> predicate) {
         super(integer, string, boolean3);
         this.field_178951_a = (Predicate<String>)MoreObjects.firstNonNull(predicate, Predicates.alwaysTrue());
      }

      public Predicate<String> func_178950_a() {
         return this.field_178951_a;
      }
   }

   public static class GuiButtonEntry extends GuiPageButtonList.GuiListEntry {
      private final boolean field_178941_a;

      public GuiButtonEntry(int integer, String string, boolean boolean3, boolean boolean4) {
         super(integer, string, boolean3);
         this.field_178941_a = boolean4;
      }

      public boolean func_178940_a() {
         return this.field_178941_a;
      }
   }

   public static class GuiEntry implements GuiListExtended.IGuiListEntry {
      private final Minecraft field_178031_a = Minecraft.getMinecraft();
      private final Gui field_178029_b;
      private final Gui field_178030_c;
      private Gui field_178028_d;

      public GuiEntry(Gui gui1, Gui gui2) {
         this.field_178029_b = gui1;
         this.field_178030_c = gui2;
      }

      public Gui func_178022_a() {
         return this.field_178029_b;
      }

      public Gui func_178021_b() {
         return this.field_178030_c;
      }

      @Override
      public void drawEntry(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7, boolean boolean8) {
         this.func_178017_a(this.field_178029_b, integer3, integer6, integer7, false);
         this.func_178017_a(this.field_178030_c, integer3, integer6, integer7, false);
      }

      private void func_178017_a(Gui gui, int integer2, int integer3, int integer4, boolean boolean5) {
         if (gui != null) {
            if (gui instanceof GuiButton) {
               this.func_178024_a((GuiButton)gui, integer2, integer3, integer4, boolean5);
            } else if (gui instanceof GuiTextField) {
               this.func_178027_a((GuiTextField)gui, integer2, boolean5);
            } else if (gui instanceof GuiLabel) {
               this.func_178025_a((GuiLabel)gui, integer2, integer3, integer4, boolean5);
            }
         }
      }

      private void func_178024_a(GuiButton guiButton, int integer2, int integer3, int integer4, boolean boolean5) {
         guiButton.yPosition = integer2;
         if (!boolean5) {
            guiButton.drawButton(this.field_178031_a, integer3, integer4);
         }
      }

      private void func_178027_a(GuiTextField guiTextField, int integer, boolean boolean3) {
         guiTextField.yPosition = integer;
         if (!boolean3) {
            guiTextField.drawTextBox();
         }
      }

      private void func_178025_a(GuiLabel guiLabel, int integer2, int integer3, int integer4, boolean boolean5) {
         guiLabel.field_146174_h = integer2;
         if (!boolean5) {
            guiLabel.drawLabel(this.field_178031_a, integer3, integer4);
         }
      }

      @Override
      public void setSelected(int integer1, int integer2, int integer3) {
         this.func_178017_a(this.field_178029_b, integer3, 0, 0, true);
         this.func_178017_a(this.field_178030_c, integer3, 0, 0, true);
      }

      @Override
      public boolean mousePressed(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
         boolean var7 = this.func_178026_a(this.field_178029_b, integer2, integer3, integer4);
         boolean var8 = this.func_178026_a(this.field_178030_c, integer2, integer3, integer4);
         return var7 || var8;
      }

      private boolean func_178026_a(Gui gui, int integer2, int integer3, int integer4) {
         if (gui == null) {
            return false;
         }

         if (gui instanceof GuiButton) {
            return this.func_178023_a((GuiButton)gui, integer2, integer3, integer4);
         }

         if (gui instanceof GuiTextField) {
            this.func_178018_a((GuiTextField)gui, integer2, integer3, integer4);
         }

         return false;
      }

      private boolean func_178023_a(GuiButton guiButton, int integer2, int integer3, int integer4) {
         boolean var5 = guiButton.mousePressed(this.field_178031_a, integer2, integer3);
         if (var5) {
            this.field_178028_d = guiButton;
         }

         return var5;
      }

      private void func_178018_a(GuiTextField guiTextField, int integer2, int integer3, int integer4) {
         guiTextField.mouseClicked(integer2, integer3, integer4);
         if (guiTextField.isFocused()) {
            this.field_178028_d = guiTextField;
         }
      }

      @Override
      public void mouseReleased(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
         this.func_178016_b(this.field_178029_b, integer2, integer3, integer4);
         this.func_178016_b(this.field_178030_c, integer2, integer3, integer4);
      }

      private void func_178016_b(Gui gui, int integer2, int integer3, int integer4) {
         if (gui != null && gui instanceof GuiButton) {
            this.func_178019_b((GuiButton)gui, integer2, integer3, integer4);
         }
      }

      private void func_178019_b(GuiButton guiButton, int integer2, int integer3, int integer4) {
         guiButton.mouseReleased(integer2, integer3);
      }
   }

   public static class GuiLabelEntry extends GuiPageButtonList.GuiListEntry {
      public GuiLabelEntry(int integer, String string, boolean boolean3) {
         super(integer, string, boolean3);
      }
   }

   public static class GuiListEntry {
      private final int field_178939_a;
      private final String field_178937_b;
      private final boolean field_178938_c;

      public GuiListEntry(int integer, String string, boolean boolean3) {
         this.field_178939_a = integer;
         this.field_178937_b = string;
         this.field_178938_c = boolean3;
      }

      public int func_178935_b() {
         return this.field_178939_a;
      }

      public String func_178936_c() {
         return this.field_178937_b;
      }

      public boolean func_178934_d() {
         return this.field_178938_c;
      }
   }

   public interface GuiResponder {
      void func_175321_a(int integer, boolean boolean2);

      void onTick(int integer, float float2);

      void func_175319_a(int integer, String string);
   }

   public static class GuiSlideEntry extends GuiPageButtonList.GuiListEntry {
      private final GuiSlider.FormatHelper field_178949_a;
      private final float field_178947_b;
      private final float field_178948_c;
      private final float field_178946_d;

      public GuiSlideEntry(int integer, String string, boolean boolean3, GuiSlider.FormatHelper formatHelper, float float5, float float6, float float7) {
         super(integer, string, boolean3);
         this.field_178949_a = formatHelper;
         this.field_178947_b = float5;
         this.field_178948_c = float6;
         this.field_178946_d = float7;
      }

      public GuiSlider.FormatHelper func_178945_a() {
         return this.field_178949_a;
      }

      public float func_178943_e() {
         return this.field_178947_b;
      }

      public float func_178944_f() {
         return this.field_178948_c;
      }

      public float func_178942_g() {
         return this.field_178946_d;
      }
   }
}
