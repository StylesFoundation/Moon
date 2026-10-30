package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.ResourceLocation;

public class TileEntityEnderChestRenderer extends TileEntitySpecialRenderer<TileEntityEnderChest> {
   private static final ResourceLocation ENDER_CHEST_TEXTURE = new ResourceLocation("textures/entity/chest/ender.png");
   public ModelChest g = new ModelChest();

   public void renderTileEntityAt(TileEntityEnderChest tileEntityEnderChest, double double2, double double3, double double4, float float5, int integer) {
      int var12 = 0;
      if (tileEntityEnderChest.hasWorldObj()) {
         var12 = tileEntityEnderChest.getBlockMetadata();
      }

      if (integer >= 0) {
         this.bindTexture(DESTROY_STAGES[integer]);
         GlStateManager.matrixMode(5890);
         GlStateManager.pushMatrix();
         GlStateManager.scale(4.0F, 4.0F, 1.0F);
         GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
         GlStateManager.matrixMode(5888);
      } else {
         this.bindTexture(ENDER_CHEST_TEXTURE);
      }

      GlStateManager.pushMatrix();
      GlStateManager.enableRescaleNormal();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.translate((float)double2, (float)double3 + 1.0F, (float)double4 + 1.0F);
      GlStateManager.scale(1.0F, -1.0F, -1.0F);
      GlStateManager.translate(0.5F, 0.5F, 0.5F);
      short var13 = 0;
      if (var12 == 2) {
         var13 = 180;
      }

      if (var12 == 3) {
         var13 = 0;
      }

      if (var12 == 4) {
         var13 = 90;
      }

      if (var12 == 5) {
         var13 = -90;
      }

      GlStateManager.rotate(var13, 0.0F, 1.0F, 0.0F);
      GlStateManager.translate(-0.5F, -0.5F, -0.5F);
      float var14 = tileEntityEnderChest.prevLidAngle + (tileEntityEnderChest.lidAngle - tileEntityEnderChest.prevLidAngle) * float5;
      var14 = 1.0F - var14;
      var14 = 1.0F - var14 * var14 * var14;
      this.g.chestLid.rotateAngleX = -(var14 * (float) Math.PI / 2.0F);
      this.g.renderAll();
      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      if (integer >= 0) {
         GlStateManager.matrixMode(5890);
         GlStateManager.popMatrix();
         GlStateManager.matrixMode(5888);
      }
   }
}
