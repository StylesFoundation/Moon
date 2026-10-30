package net.minecraft.client.renderer.tileentity;

import java.util.Calendar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;

public class TileEntityChestRenderer extends TileEntitySpecialRenderer<TileEntityChest> {
   private static final ResourceLocation textureTrappedDouble = new ResourceLocation("textures/entity/chest/trapped_double.png");
   private static final ResourceLocation textureChristmasDouble = new ResourceLocation("textures/entity/chest/christmas_double.png");
   private static final ResourceLocation textureNormalDouble = new ResourceLocation("textures/entity/chest/normal_double.png");
   private static final ResourceLocation textureTrapped = new ResourceLocation("textures/entity/chest/trapped.png");
   private static final ResourceLocation textureChristmas = new ResourceLocation("textures/entity/chest/christmas.png");
   private static final ResourceLocation textureNormal = new ResourceLocation("textures/entity/chest/normal.png");
   public ModelChest l = new ModelChest();
   public ModelChest m = new ModelLargeChest();
   private boolean isChristmas;

   public TileEntityChestRenderer() {
      Calendar var3 = Calendar.getInstance();
      if (var3.get(2) + 1 == 12 && var3.get(5) >= 24 && var3.get(5) <= 26) {
         this.isChristmas = true;
      }
   }

   public void renderTileEntityAt(TileEntityChest tileEntityChest, double double2, double double3, double double4, float float5, int integer) {
      GlStateManager.enableDepth();
      GlStateManager.depthFunc(515);
      GlStateManager.depthMask(true);
      int var12;
      if (!tileEntityChest.hasWorldObj()) {
         var12 = 0;
      } else {
         Block var13 = tileEntityChest.getBlockType();
         var12 = tileEntityChest.getBlockMetadata();
         if (var13 instanceof BlockChest && var12 == 0) {
            ((BlockChest)var13)
               .checkForSurroundingChests(
                  tileEntityChest.getWorld(), tileEntityChest.getPos(), tileEntityChest.getWorld().getBlockState(tileEntityChest.getPos())
               );
            var12 = tileEntityChest.getBlockMetadata();
         }

         tileEntityChest.checkForAdjacentChests();
      }

      if (tileEntityChest.adjacentChestZNeg == null && tileEntityChest.adjacentChestXNeg == null) {
         ModelChest var17;
         if (tileEntityChest.adjacentChestXPos == null && tileEntityChest.adjacentChestZPos == null) {
            var17 = this.l;
            if (integer >= 0) {
               this.bindTexture(DESTROY_STAGES[integer]);
               GlStateManager.matrixMode(5890);
               GlStateManager.pushMatrix();
               GlStateManager.scale(4.0F, 4.0F, 1.0F);
               GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
               GlStateManager.matrixMode(5888);
            } else if (this.isChristmas) {
               this.bindTexture(textureChristmas);
            } else if (tileEntityChest.getChestType() == 1) {
               this.bindTexture(textureTrapped);
            } else {
               this.bindTexture(textureNormal);
            }
         } else {
            var17 = this.m;
            if (integer >= 0) {
               this.bindTexture(DESTROY_STAGES[integer]);
               GlStateManager.matrixMode(5890);
               GlStateManager.pushMatrix();
               GlStateManager.scale(8.0F, 4.0F, 1.0F);
               GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
               GlStateManager.matrixMode(5888);
            } else if (this.isChristmas) {
               this.bindTexture(textureChristmasDouble);
            } else if (tileEntityChest.getChestType() == 1) {
               this.bindTexture(textureTrappedDouble);
            } else {
               this.bindTexture(textureNormalDouble);
            }
         }

         GlStateManager.pushMatrix();
         GlStateManager.enableRescaleNormal();
         if (integer < 0) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         }

         GlStateManager.translate((float)double2, (float)double3 + 1.0F, (float)double4 + 1.0F);
         GlStateManager.scale(1.0F, -1.0F, -1.0F);
         GlStateManager.translate(0.5F, 0.5F, 0.5F);
         short var14 = 0;
         if (var12 == 2) {
            var14 = 180;
         }

         if (var12 == 3) {
            var14 = 0;
         }

         if (var12 == 4) {
            var14 = 90;
         }

         if (var12 == 5) {
            var14 = -90;
         }

         if (var12 == 2 && tileEntityChest.adjacentChestXPos != null) {
            GlStateManager.translate(1.0F, 0.0F, 0.0F);
         }

         if (var12 == 5 && tileEntityChest.adjacentChestZPos != null) {
            GlStateManager.translate(0.0F, 0.0F, -1.0F);
         }

         GlStateManager.rotate(var14, 0.0F, 1.0F, 0.0F);
         GlStateManager.translate(-0.5F, -0.5F, -0.5F);
         float var15 = tileEntityChest.prevLidAngle + (tileEntityChest.lidAngle - tileEntityChest.prevLidAngle) * float5;
         if (tileEntityChest.adjacentChestZNeg != null) {
            float var16 = tileEntityChest.adjacentChestZNeg.prevLidAngle
               + (tileEntityChest.adjacentChestZNeg.lidAngle - tileEntityChest.adjacentChestZNeg.prevLidAngle) * float5;
            if (var16 > var15) {
               var15 = var16;
            }
         }

         if (tileEntityChest.adjacentChestXNeg != null) {
            float var20 = tileEntityChest.adjacentChestXNeg.prevLidAngle
               + (tileEntityChest.adjacentChestXNeg.lidAngle - tileEntityChest.adjacentChestXNeg.prevLidAngle) * float5;
            if (var20 > var15) {
               var15 = var20;
            }
         }

         var15 = 1.0F - var15;
         var15 = 1.0F - var15 * var15 * var15;
         var17.chestLid.rotateAngleX = -(var15 * (float) Math.PI / 2.0F);
         var17.renderAll();
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
}
