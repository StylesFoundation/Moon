package net.minecraft.client.resources;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

public abstract class ResourcePackListEntry implements GuiListExtended.IGuiListEntry {
   private static final ResourceLocation RESOURCE_PACKS_TEXTURE = new ResourceLocation("textures/gui/resource_packs.png");
   private static final IChatComponent field_183020_d = new ChatComponentTranslation("resourcePack.incompatible");
   private static final IChatComponent field_183021_e = new ChatComponentTranslation("resourcePack.incompatible.old");
   private static final IChatComponent field_183022_f = new ChatComponentTranslation("resourcePack.incompatible.new");
   protected final Minecraft mc;
   protected final GuiScreenResourcePacks resourcePacksGUI;

   public ResourcePackListEntry(GuiScreenResourcePacks guiScreenResourcePacks) {
      this.resourcePacksGUI = guiScreenResourcePacks;
      this.mc = Minecraft.getMinecraft();
   }

   @Override
   public void drawEntry(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7, boolean boolean8) {
      int var12 = this.func_183019_a();
      if (var12 != 1) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         Gui.drawRect(integer2 - 1, integer3 - 1, integer2 + integer4 - 9, integer3 + integer5 + 1, -8978432);
      }

      this.func_148313_c();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      Gui.drawModalRectWithCustomSizedTexture(integer2, integer3, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
      String var13 = this.func_148312_b();
      String var14 = this.func_148311_a();
      if ((this.mc.gameSettings.touchscreen || boolean8) && this.func_148310_d()) {
         this.mc.getTextureManager().bindTexture(RESOURCE_PACKS_TEXTURE);
         Gui.drawRect(integer2, integer3, integer2 + 32, integer3 + 32, -1601138544);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         int var15 = integer6 - integer2;
         int var16 = integer7 - integer3;
         if (var12 < 1) {
            var13 = field_183020_d.getFormattedText();
            var14 = field_183021_e.getFormattedText();
         } else if (var12 > 1) {
            var13 = field_183020_d.getFormattedText();
            var14 = field_183022_f.getFormattedText();
         }

         if (this.func_148309_e()) {
            if (var15 < 32) {
               Gui.drawModalRectWithCustomSizedTexture(integer2, integer3, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
            } else {
               Gui.drawModalRectWithCustomSizedTexture(integer2, integer3, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
            }
         } else {
            if (this.func_148308_f()) {
               if (var15 < 16) {
                  Gui.drawModalRectWithCustomSizedTexture(integer2, integer3, 32.0F, 32.0F, 32, 32, 256.0F, 256.0F);
               } else {
                  Gui.drawModalRectWithCustomSizedTexture(integer2, integer3, 32.0F, 0.0F, 32, 32, 256.0F, 256.0F);
               }
            }

            if (this.func_148314_g()) {
               if (var15 < 32 && var15 > 16 && var16 < 16) {
                  Gui.drawModalRectWithCustomSizedTexture(integer2, integer3, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
               } else {
                  Gui.drawModalRectWithCustomSizedTexture(integer2, integer3, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
               }
            }

            if (this.func_148307_h()) {
               if (var15 < 32 && var15 > 16 && var16 > 16) {
                  Gui.drawModalRectWithCustomSizedTexture(integer2, integer3, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
               } else {
                  Gui.drawModalRectWithCustomSizedTexture(integer2, integer3, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
               }
            }
         }
      }

      if (this.mc.fontRendererObj.getStringWidth(var13) > 157) {
         var13 = this.mc.fontRendererObj.trimStringToWidth(var13, 157 - this.mc.fontRendererObj.getStringWidth("...")) + "...";
      }

      this.mc.fontRendererObj.drawStringWithShadow(var13, integer2 + 32 + 2, integer3 + 1, 16777215);
      List var18 = this.mc.fontRendererObj.listFormattedStringToWidth(var14, 157);

      for (int var17 = 0; var17 < 2 && var17 < var18.size(); var17++) {
         this.mc.fontRendererObj.drawStringWithShadow((String)var18.get(var17), integer2 + 32 + 2, integer3 + 12 + 10 * var17, 8421504);
      }
   }

   protected abstract int func_183019_a();

   protected abstract String func_148311_a();

   protected abstract String func_148312_b();

   protected abstract void func_148313_c();

   protected boolean func_148310_d() {
      return true;
   }

   protected boolean func_148309_e() {
      return !this.resourcePacksGUI.hasResourcePackEntry(this);
   }

   protected boolean func_148308_f() {
      return this.resourcePacksGUI.hasResourcePackEntry(this);
   }

   protected boolean func_148314_g() {
      List var4 = this.resourcePacksGUI.getListContaining(this);
      int var5 = var4.indexOf(this);
      return var5 > 0 && ((ResourcePackListEntry)var4.get(var5 - 1)).func_148310_d();
   }

   protected boolean func_148307_h() {
      List var4 = this.resourcePacksGUI.getListContaining(this);
      int var5 = var4.indexOf(this);
      return var5 >= 0 && var5 < var4.size() - 1 && ((ResourcePackListEntry)var4.get(var5 + 1)).func_148310_d();
   }

   @Override
   public boolean mousePressed(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
      if (this.func_148310_d() && integer5 <= 32) {
         if (this.func_148309_e()) {
            this.resourcePacksGUI.markChanged();
            int var14 = this.func_183019_a();
            if (var14 != 1) {
               String var16 = I18n.format("resourcePack.incompatible.confirm.title");
               String var12 = I18n.format("resourcePack.incompatible.confirm." + (var14 > 1 ? "new" : "old"));
               this.mc.displayGuiScreen(new GuiYesNo((boolean1, integer) -> {
                  List var6 = this.resourcePacksGUI.getListContaining(this);
                  this.mc.displayGuiScreen(this.resourcePacksGUI);
                  if (boolean1) {
                     var6.remove(this);
                     this.resourcePacksGUI.getSelectedResourcePacks().add(0, this);
                  }
               }, var16, var12, 0));
            } else {
               this.resourcePacksGUI.getListContaining(this).remove(this);
               this.resourcePacksGUI.getSelectedResourcePacks().add(0, this);
            }

            return true;
         }

         if (integer5 < 16 && this.func_148308_f()) {
            this.resourcePacksGUI.getListContaining(this).remove(this);
            this.resourcePacksGUI.getAvailableResourcePacks().add(0, this);
            this.resourcePacksGUI.markChanged();
            return true;
         }

         if (integer5 > 16 && integer6 < 16 && this.func_148314_g()) {
            List var13 = this.resourcePacksGUI.getListContaining(this);
            int var15 = var13.indexOf(this);
            var13.remove(this);
            var13.add(var15 - 1, this);
            this.resourcePacksGUI.markChanged();
            return true;
         }

         if (integer5 > 16 && integer6 > 16 && this.func_148307_h()) {
            List var10 = this.resourcePacksGUI.getListContaining(this);
            int var11 = var10.indexOf(this);
            var10.remove(this);
            var10.add(var11 + 1, this);
            this.resourcePacksGUI.markChanged();
            return true;
         }
      }

      return false;
   }

   @Override
   public void setSelected(int integer1, int integer2, int integer3) {
   }

   @Override
   public void mouseReleased(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
   }
}
