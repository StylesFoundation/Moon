package net.minecraft.client.gui.inventory;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiDispenser extends GuiContainer {
   private static final ResourceLocation dispenserGuiTextures = new ResourceLocation("textures/gui/container/dispenser.png");
   private final InventoryPlayer playerInventory;
   public IInventory dispenserInventory;

   public GuiDispenser(InventoryPlayer inventoryPlayer, IInventory iInventory) {
      super(new ContainerDispenser(inventoryPlayer, iInventory));
      this.playerInventory = inventoryPlayer;
      this.dispenserInventory = iInventory;
   }

   @Override
   protected void drawGuiContainerForegroundLayer(int integer1, int integer2) {
      String var5 = this.dispenserInventory.getDisplayName().getUnformattedText();
      this.fontRendererObj.drawString(var5, this.xSize / 2 - this.fontRendererObj.getStringWidth(var5) / 2, 6.0F, 4210752);
      this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8.0F, this.ySize - 96 + 2, 4210752);
   }

   @Override
   protected void drawGuiContainerBackgroundLayer(float float1, int integer2, int integer3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(dispenserGuiTextures);
      int var4 = (this.width - this.xSize) / 2;
      int var5 = (this.height - this.ySize) / 2;
      this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.ySize);
   }
}
