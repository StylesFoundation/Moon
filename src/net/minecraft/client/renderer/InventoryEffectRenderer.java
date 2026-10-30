package net.minecraft.client.renderer;

import java.util.Collection;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public abstract class InventoryEffectRenderer extends GuiContainer {
   private boolean hasActivePotionEffects;

   public InventoryEffectRenderer(Container container) {
      super(container);
   }

   @Override
   public void onInitGui() {
      super.onInitGui();
      this.updateActivePotionEffects();
   }

   protected void updateActivePotionEffects() {
      if (!this.mc.thePlayer.getActivePotionEffects().isEmpty()) {
         this.guiLeft = 160 + (this.width - this.xSize - 200) / 2;
         this.hasActivePotionEffects = true;
      } else {
         this.guiLeft = (this.width - this.xSize) / 2;
         this.hasActivePotionEffects = false;
      }
   }

   @Override
   public void onDrawScreen(int mouseX, int mouseY, float partialTicks) {
      super.onDrawScreen(mouseX, mouseY, partialTicks);
      if (this.hasActivePotionEffects) {
         this.drawActivePotionEffects();
      }
   }

   private void drawActivePotionEffects() {
      int var3 = this.guiLeft - 124;
      int var4 = this.guiTop;
      Collection var6 = this.mc.thePlayer.getActivePotionEffects();
      if (!var6.isEmpty()) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.disableLighting();
         int var7 = 33;
         if (var6.size() > 5) {
            var7 = 132 / (var6.size() - 1);
         }

         for (PotionEffect var9 : this.mc.thePlayer.getActivePotionEffects()) {
            Potion var10 = Potion.potionTypes[var9.getPotionID()];
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(inventoryBackground);
            this.drawTexturedModalRect(var3, var4, 0, 166, 140, 32);
            if (var10 != null) {
               if (var10.hasStatusIcon()) {
                  int var11 = var10.getStatusIconIndex();
                  this.drawTexturedModalRect(var3 + 6, var4 + 7, var11 % 8 * 18, 198 + var11 / 8 * 18, 18, 18);
               }

               String var13 = I18n.format(var10.getName());
               if (var9.getAmplifier() == 1) {
                  var13 = var13 + " " + I18n.format("enchantment.level.2");
               } else if (var9.getAmplifier() == 2) {
                  var13 = var13 + " " + I18n.format("enchantment.level.3");
               } else if (var9.getAmplifier() == 3) {
                  var13 = var13 + " " + I18n.format("enchantment.level.4");
               }

               this.fontRendererObj.drawStringWithShadow(var13, var3 + 10 + 18, var4 + 6, 16777215);
               String var12 = Potion.getDurationString(var9);
               this.fontRendererObj.drawStringWithShadow(var12, var3 + 10 + 18, var4 + 6 + 10, 8355711);
               var4 += var7;
            }
         }
      }
   }
}
