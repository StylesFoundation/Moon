package net.minecraft.client.gui.inventory;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiBrewingStand extends GuiContainer {
   private static final ResourceLocation brewingStandGuiTextures = new ResourceLocation("textures/gui/container/brewing_stand.png");
   private final InventoryPlayer playerInventory;
   public IInventory tileBrewingStand;

   public GuiBrewingStand(InventoryPlayer inventoryPlayer, IInventory iInventory) {
      super(new ContainerBrewingStand(inventoryPlayer, iInventory));
      this.playerInventory = inventoryPlayer;
      this.tileBrewingStand = iInventory;
   }

   @Override
   protected void drawGuiContainerForegroundLayer(int integer1, int integer2) {
      String var5 = this.tileBrewingStand.getDisplayName().getUnformattedText();
      this.fontRendererObj.drawString(var5, this.xSize / 2 - this.fontRendererObj.getStringWidth(var5) / 2, 6.0F, 4210752);
      this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8.0F, this.ySize - 96 + 2, 4210752);
   }

   @Override
   protected void drawGuiContainerBackgroundLayer(float float1, int integer2, int integer3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(brewingStandGuiTextures);
      int var6 = (this.width - this.xSize) / 2;
      int var7 = (this.height - this.ySize) / 2;
      this.drawTexturedModalRect(var6, var7, 0, 0, this.xSize, this.ySize);
      int var8 = this.tileBrewingStand.getField(0);
      if (var8 > 0) {
         int var9 = (int)(28.0F * (1.0F - var8 / 400.0F));
         if (var9 > 0) {
            this.drawTexturedModalRect(var6 + 97, var7 + 16, 176, 0, 9, var9);
         }

         switch (var8 / 2 % 7) {
            case 0:
               var9 = 29;
               break;
            case 1:
               var9 = 24;
               break;
            case 2:
               var9 = 20;
               break;
            case 3:
               var9 = 16;
               break;
            case 4:
               var9 = 11;
               break;
            case 5:
               var9 = 6;
               break;
            case 6:
               var9 = 0;
         }

         if (var9 > 0) {
            this.drawTexturedModalRect(var6 + 65, var7 + 14 + 29 - var9, 185, 29 - var9, 12, var9);
         }
      }
   }
}
