package net.minecraft.client.gui.inventory;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiCrafting extends GuiContainer {
   private static final ResourceLocation craftingTableGuiTextures = new ResourceLocation("textures/gui/container/crafting_table.png");

   public GuiCrafting(InventoryPlayer inventoryPlayer, World world) {
      this(inventoryPlayer, world, BlockPos.ORIGIN);
   }

   public GuiCrafting(InventoryPlayer inventoryPlayer, World world, BlockPos blockPos) {
      super(new ContainerWorkbench(inventoryPlayer, world, blockPos));
   }

   @Override
   protected void drawGuiContainerForegroundLayer(int integer1, int integer2) {
      this.fontRendererObj.drawString(I18n.format("container.crafting"), 28.0F, 6.0F, 4210752);
      this.fontRendererObj.drawString(I18n.format("container.inventory"), 8.0F, this.ySize - 96 + 2, 4210752);
   }

   @Override
   protected void drawGuiContainerBackgroundLayer(float float1, int integer2, int integer3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(craftingTableGuiTextures);
      int var4 = (this.width - this.xSize) / 2;
      int var5 = (this.height - this.ySize) / 2;
      this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.ySize);
   }
}
