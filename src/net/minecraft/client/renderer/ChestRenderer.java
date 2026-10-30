package net.minecraft.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;

public class ChestRenderer {
   public void renderChestBrightness(Block block, float float2) {
      GlStateManager.color(float2, float2, float2, 1.0F);
      GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
      TileEntityItemStackRenderer.instance.renderByItem(new ItemStack(block));
   }
}
