package net.minecraft.client.gui;

import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.lang3.ArrayUtils;

public class GuiKeyBindingList extends GuiListExtended {
   private final GuiControls field_148191_k;
   private final Minecraft mc;
   private final GuiListExtended.IGuiListEntry[] listEntries;
   private int maxListLabelWidth = 0;

   public GuiKeyBindingList(GuiControls guiControls, Minecraft minecraft) {
      super(minecraft, guiControls.width, guiControls.height, 63, guiControls.height - 32, 20);
      this.field_148191_k = guiControls;
      this.mc = minecraft;
      KeyBinding[] var5 = (KeyBinding[])ArrayUtils.clone(minecraft.gameSettings.keyBindings);
      this.listEntries = new GuiListExtended.IGuiListEntry[var5.length + KeyBinding.getKeybinds().size()];
      Arrays.sort(var5);
      int var6 = 0;
      String var7 = null;

      for (KeyBinding var11 : var5) {
         String var12 = var11.getKeyCategory();
         if (!var12.equals(var7)) {
            var7 = var12;
            this.listEntries[var6++] = new GuiKeyBindingList.CategoryEntry(var12);
         }

         int var13 = minecraft.fontRendererObj.getStringWidth(I18n.format(var11.getKeyDescription()));
         if (var13 > this.maxListLabelWidth) {
            this.maxListLabelWidth = var13;
         }

         this.listEntries[var6++] = new GuiKeyBindingList.KeyEntry(var11);
      }
   }

   @Override
   protected int getSize() {
      return this.listEntries.length;
   }

   @Override
   public GuiListExtended.IGuiListEntry getListEntry(int integer) {
      return this.listEntries[integer];
   }

   @Override
   protected int getScrollBarX() {
      return super.getScrollBarX() + 15;
   }

   @Override
   public int getListWidth() {
      return super.getListWidth() + 32;
   }

   public class CategoryEntry implements GuiListExtended.IGuiListEntry {
      private final String labelText;
      private final int labelWidth;

      public CategoryEntry(String string) {
         this.labelText = I18n.format(string);
         this.labelWidth = GuiKeyBindingList.this.mc.fontRendererObj.getStringWidth(this.labelText);
      }

      @Override
      public void drawEntry(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7, boolean boolean8) {
         GuiKeyBindingList.this.mc
            .fontRendererObj
            .drawString(
               this.labelText,
               GuiKeyBindingList.this.mc.currentScreen.width / 2 - this.labelWidth / 2,
               integer3 + integer5 - GuiKeyBindingList.this.mc.fontRendererObj.FONT_HEIGHT - 1,
               16777215
            );
      }

      @Override
      public boolean mousePressed(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
         return false;
      }

      @Override
      public void mouseReleased(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
      }

      @Override
      public void setSelected(int integer1, int integer2, int integer3) {
      }
   }

   public class KeyEntry implements GuiListExtended.IGuiListEntry {
      private final KeyBinding keybinding;
      private final String keyDesc;
      private final GuiButton btnChangeKeyBinding;
      private final GuiButton btnReset;

      private KeyEntry(KeyBinding keyBinding) {
         this.keybinding = keyBinding;
         this.keyDesc = I18n.format(keyBinding.getKeyDescription());
         this.btnChangeKeyBinding = new GuiButton(0, 0, 0, 75, 20, I18n.format(keyBinding.getKeyDescription()));
         this.btnReset = new GuiButton(0, 0, 0, 50, 20, I18n.format("controls.reset"));
      }

      @Override
      public void drawEntry(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7, boolean boolean8) {
         boolean var11 = GuiKeyBindingList.this.field_148191_k.buttonId == this.keybinding;
         GuiKeyBindingList.this.mc
            .fontRendererObj
            .drawString(
               this.keyDesc,
               integer2 + 90 - GuiKeyBindingList.this.maxListLabelWidth,
               integer3 + integer5 / 2 - GuiKeyBindingList.this.mc.fontRendererObj.FONT_HEIGHT / 2,
               16777215
            );
         this.btnReset.xPosition = integer2 + 190;
         this.btnReset.yPosition = integer3;
         this.btnReset.enabled = this.keybinding.getKeyCode() != this.keybinding.getKeyCodeDefault();
         this.btnReset.drawButton(GuiKeyBindingList.this.mc, integer6, integer7);
         this.btnChangeKeyBinding.xPosition = integer2 + 105;
         this.btnChangeKeyBinding.yPosition = integer3;
         this.btnChangeKeyBinding.displayString = GameSettings.getKeyDisplayString(this.keybinding.getKeyCode());
         boolean var12 = false;
         if (this.keybinding.getKeyCode() != 0) {
            for (KeyBinding var16 : GuiKeyBindingList.this.mc.gameSettings.keyBindings) {
               if (var16 != this.keybinding && var16.getKeyCode() == this.keybinding.getKeyCode()) {
                  var12 = true;
                  break;
               }
            }
         }

         if (var11) {
            this.btnChangeKeyBinding.displayString = EnumChatFormatting.WHITE
               + "> "
               + EnumChatFormatting.YELLOW
               + this.btnChangeKeyBinding.displayString
               + EnumChatFormatting.WHITE
               + " <";
         } else if (var12) {
            this.btnChangeKeyBinding.displayString = EnumChatFormatting.RED + this.btnChangeKeyBinding.displayString;
         }

         this.btnChangeKeyBinding.drawButton(GuiKeyBindingList.this.mc, integer6, integer7);
      }

      @Override
      public boolean mousePressed(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
         if (this.btnChangeKeyBinding.mousePressed(GuiKeyBindingList.this.mc, integer2, integer3)) {
            GuiKeyBindingList.this.field_148191_k.buttonId = this.keybinding;
            return true;
         } else if (this.btnReset.mousePressed(GuiKeyBindingList.this.mc, integer2, integer3)) {
            GuiKeyBindingList.this.mc.gameSettings.setOptionKeyBinding(this.keybinding, this.keybinding.getKeyCodeDefault());
            KeyBinding.resetKeyBindingArrayAndHash();
            return true;
         } else {
            return false;
         }
      }

      @Override
      public void mouseReleased(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
         this.btnChangeKeyBinding.mouseReleased(integer2, integer3);
         this.btnReset.mouseReleased(integer2, integer3);
      }

      @Override
      public void setSelected(int integer1, int integer2, int integer3) {
      }
   }
}
