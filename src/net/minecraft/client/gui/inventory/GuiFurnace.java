package net.minecraft.client.gui.inventory;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;

public class GuiFurnace extends GuiContainer {
   private static final ResourceLocation furnaceGuiTextures = new ResourceLocation("textures/gui/container/furnace.png");
   private final InventoryPlayer playerInventory;
   public IInventory tileFurnace;

   public GuiFurnace(InventoryPlayer inventoryPlayer, IInventory iInventory) {
      super(new ContainerFurnace(inventoryPlayer, iInventory));
      this.playerInventory = inventoryPlayer;
      this.tileFurnace = iInventory;
   }

   @Override
   protected void drawGuiContainerForegroundLayer(int integer1, int integer2) {
      String var5 = this.tileFurnace.getDisplayName().getUnformattedText();
      this.fontRendererObj.drawString(var5, this.xSize / 2 - this.fontRendererObj.getStringWidth(var5) / 2, 6.0F, 4210752);
      this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8.0F, this.ySize - 96 + 2, 4210752);
   }

   @Override
   protected void drawGuiContainerBackgroundLayer(float float1, int integer2, int integer3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(furnaceGuiTextures);
      int var6 = (this.width - this.xSize) / 2;
      int var7 = (this.height - this.ySize) / 2;
      this.drawTexturedModalRect(var6, var7, 0, 0, this.xSize, this.ySize);
      if (TileEntityFurnace.isBurning(this.tileFurnace)) {
         int var8 = this.getBurnLeftScaled(13);
         this.drawTexturedModalRect(var6 + 56, var7 + 36 + 12 - var8, 176, 12 - var8, 14, var8 + 1);
      }

      int var9 = this.getCookProgressScaled(24);
      this.drawTexturedModalRect(var6 + 79, var7 + 34, 176, 14, var9 + 1, 16);
   }

   private int getCookProgressScaled(int integer) {
      int var2 = this.tileFurnace.getField(2);
      int var3 = this.tileFurnace.getField(3);
      return var3 != 0 && var2 != 0 ? var2 * integer / var3 : 0;
   }

   private int getBurnLeftScaled(int integer) {
      int var4 = this.tileFurnace.getField(1);
      if (var4 == 0) {
         var4 = 200;
      }

      return this.tileFurnace.getField(0) * integer / var4;
   }
}
