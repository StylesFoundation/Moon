package net.minecraft.client.gui.spectator;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.spectator.categories.SpectatorDetails;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class SpectatorMenu {
   private static final ISpectatorMenuObject field_178655_b = new SpectatorMenu.EndSpectatorObject();
   private static final ISpectatorMenuObject field_178656_c = new SpectatorMenu.MoveMenuObject(-1, true);
   private static final ISpectatorMenuObject field_178653_d = new SpectatorMenu.MoveMenuObject(1, true);
   private static final ISpectatorMenuObject field_178654_e = new SpectatorMenu.MoveMenuObject(1, false);
   public static final ISpectatorMenuObject field_178657_a = new ISpectatorMenuObject() {
      @Override
      public void func_178661_a(SpectatorMenu spectatorMenu) {
      }

      @Override
      public IChatComponent getSpectatorName() {
         return new ChatComponentText("");
      }

      @Override
      public void func_178663_a(float float1, int integer) {
      }

      @Override
      public boolean func_178662_A_() {
         return false;
      }
   };
   private final ISpectatorMenuRecipient field_178651_f;
   private final List<SpectatorDetails> field_178652_g = Lists.newArrayList();
   private ISpectatorMenuView field_178659_h = new BaseSpectatorGroup();
   private int field_178660_i = -1;
   private int field_178658_j;

   public SpectatorMenu(ISpectatorMenuRecipient iSpectatorMenuRecipient) {
      this.field_178651_f = iSpectatorMenuRecipient;
   }

   public ISpectatorMenuObject func_178643_a(int integer) {
      int var4 = integer + this.field_178658_j * 6;
      return this.field_178658_j > 0 && integer == 0
         ? field_178656_c
         : (
            integer == 7
               ? (var4 < this.field_178659_h.func_178669_a().size() ? field_178653_d : field_178654_e)
               : (
                  integer == 8
                     ? field_178655_b
                     : (
                        var4 >= 0 && var4 < this.field_178659_h.func_178669_a().size()
                           ? (ISpectatorMenuObject)MoreObjects.firstNonNull(this.field_178659_h.func_178669_a().get(var4), field_178657_a)
                           : field_178657_a
                     )
               )
         );
   }

   public List<ISpectatorMenuObject> func_178642_a() {
      ArrayList var3 = Lists.newArrayList();

      for (int var4 = 0; var4 <= 8; var4++) {
         var3.add(this.func_178643_a(var4));
      }

      return var3;
   }

   public ISpectatorMenuObject func_178645_b() {
      return this.func_178643_a(this.field_178660_i);
   }

   public ISpectatorMenuView func_178650_c() {
      return this.field_178659_h;
   }

   public void func_178644_b(int integer) {
      ISpectatorMenuObject var2 = this.func_178643_a(integer);
      if (var2 != field_178657_a) {
         if (this.field_178660_i == integer && var2.func_178662_A_()) {
            var2.func_178661_a(this);
         } else {
            this.field_178660_i = integer;
         }
      }
   }

   public void func_178641_d() {
      this.field_178651_f.func_175257_a(this);
   }

   public int func_178648_e() {
      return this.field_178660_i;
   }

   public void func_178647_a(ISpectatorMenuView iSpectatorMenuView) {
      this.field_178652_g.add(this.func_178646_f());
      this.field_178659_h = iSpectatorMenuView;
      this.field_178660_i = -1;
      this.field_178658_j = 0;
   }

   public SpectatorDetails func_178646_f() {
      return new SpectatorDetails(this.field_178659_h, this.func_178642_a(), this.field_178660_i);
   }

   static class EndSpectatorObject implements ISpectatorMenuObject {
      private EndSpectatorObject() {
      }

      @Override
      public void func_178661_a(SpectatorMenu spectatorMenu) {
         spectatorMenu.func_178641_d();
      }

      @Override
      public IChatComponent getSpectatorName() {
         return new ChatComponentText("Close menu");
      }

      @Override
      public void func_178663_a(float float1, int integer) {
         Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
         Gui.drawModalRectWithCustomSizedTexture(0, 0, 128.0F, 0.0F, 16, 16, 256.0F, 256.0F);
      }

      @Override
      public boolean func_178662_A_() {
         return true;
      }
   }

   static class MoveMenuObject implements ISpectatorMenuObject {
      private final int field_178666_a;
      private final boolean field_178665_b;

      public MoveMenuObject(int integer, boolean boolean2) {
         this.field_178666_a = integer;
         this.field_178665_b = boolean2;
      }

      @Override
      public void func_178661_a(SpectatorMenu spectatorMenu) {
         spectatorMenu.field_178658_j = this.field_178666_a;
      }

      @Override
      public IChatComponent getSpectatorName() {
         return this.field_178666_a < 0 ? new ChatComponentText("Previous Page") : new ChatComponentText("Next Page");
      }

      @Override
      public void func_178663_a(float float1, int integer) {
         Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
         if (this.field_178666_a < 0) {
            Gui.drawModalRectWithCustomSizedTexture(0, 0, 144.0F, 0.0F, 16, 16, 256.0F, 256.0F);
         } else {
            Gui.drawModalRectWithCustomSizedTexture(0, 0, 160.0F, 0.0F, 16, 16, 256.0F, 256.0F);
         }
      }

      @Override
      public boolean func_178662_A_() {
         return this.field_178665_b;
      }
   }
}
