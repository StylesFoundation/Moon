package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class TileEntityEnchantmentTableRenderer extends TileEntitySpecialRenderer<TileEntityEnchantmentTable> {
   private static final ResourceLocation TEXTURE_BOOK = new ResourceLocation("textures/entity/enchanting_table_book.png");
   public ModelBook g = new ModelBook();

   public void renderTileEntityAt(
      TileEntityEnchantmentTable tileEntityEnchantmentTable, double double2, double double3, double double4, float float5, int integer
   ) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)double2 + 0.5F, (float)double3 + 0.75F, (float)double4 + 0.5F);
      float var10 = tileEntityEnchantmentTable.tickCount + float5;
      GlStateManager.translate(0.0F, 0.1F + MathHelper.sin(var10 * 0.1F) * 0.01F, 0.0F);
      float var11 = tileEntityEnchantmentTable.bookRotation - tileEntityEnchantmentTable.bookRotationPrev;

      while (var11 >= (float) Math.PI) {
         var11 -= (float) (Math.PI * 2);
      }

      while (var11 < (float) -Math.PI) {
         var11 += (float) (Math.PI * 2);
      }

      GlStateManager.rotate(-(tileEntityEnchantmentTable.bookRotationPrev + var11 * float5) * 180.0F / (float) Math.PI, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(80.0F, 0.0F, 0.0F, 1.0F);
      this.bindTexture(TEXTURE_BOOK);
      float var13 = tileEntityEnchantmentTable.pageFlipPrev + (tileEntityEnchantmentTable.pageFlip - tileEntityEnchantmentTable.pageFlipPrev) * float5 + 0.25F;
      float var14 = tileEntityEnchantmentTable.pageFlipPrev + (tileEntityEnchantmentTable.pageFlip - tileEntityEnchantmentTable.pageFlipPrev) * float5 + 0.75F;
      var13 = (var13 - MathHelper.truncateDoubleToInt(var13)) * 1.6F - 0.3F;
      var14 = (var14 - MathHelper.truncateDoubleToInt(var14)) * 1.6F - 0.3F;
      if (var13 < 0.0F) {
         var13 = 0.0F;
      }

      if (var14 < 0.0F) {
         var14 = 0.0F;
      }

      if (var13 > 1.0F) {
         var13 = 1.0F;
      }

      if (var14 > 1.0F) {
         var14 = 1.0F;
      }

      float var15 = tileEntityEnchantmentTable.bookSpreadPrev + (tileEntityEnchantmentTable.bookSpread - tileEntityEnchantmentTable.bookSpreadPrev) * float5;
      GlStateManager.enableCull();
      this.g.render(null, var10, var13, var14, var15, 0.0F, 0.0625F);
      GlStateManager.popMatrix();
   }
}
