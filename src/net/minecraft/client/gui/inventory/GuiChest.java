package net.minecraft.client.gui.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiChest extends GuiContainer {
   private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
   public final IInventory upperChestInventory;
   public IInventory lowerChestInventory;
   private final int inventoryRows;

   public GuiChest(IInventory iInventory1, IInventory iInventory2) {
      super(new ContainerChest(iInventory1, iInventory2, Minecraft.getMinecraft().thePlayer));
      this.upperChestInventory = iInventory1;
      this.lowerChestInventory = iInventory2;
      this.allowUserInput = false;
      byte var6 = 114;
      this.inventoryRows = iInventory2.getSizeInventory() / 9;
      this.ySize = var6 + this.inventoryRows * 18;
   }

   @Override
   protected void drawGuiContainerForegroundLayer(int integer1, int integer2) {
      this.fontRendererObj.drawString(this.lowerChestInventory.getDisplayName().getUnformattedText(), 8.0F, 6.0F, 4210752);
      this.fontRendererObj.drawString(this.upperChestInventory.getDisplayName().getUnformattedText(), 8.0F, this.ySize - 96 + 2, 4210752);
   }

   @Override
   protected void drawGuiContainerBackgroundLayer(float float1, int integer2, int integer3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);
      int var6 = (this.width - this.xSize) / 2;
      int var7 = (this.height - this.ySize) / 2;
      this.drawTexturedModalRect(var6, var7, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
      this.drawTexturedModalRect(var6, var7 + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
   }

   public int Method427() {
      return this.inventoryRows;
   }
}
