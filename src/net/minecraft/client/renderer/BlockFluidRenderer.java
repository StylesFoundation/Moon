package net.minecraft.client.renderer;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.optifine.CustomColors;
import net.optifine.render.RenderEnv;
import net.optifine.shaders.SVertexBuilder;

public class BlockFluidRenderer {
   private final TextureAtlasSprite[] atlasSpritesLava = new TextureAtlasSprite[2];
   private final TextureAtlasSprite[] atlasSpritesWater = new TextureAtlasSprite[2];

   public BlockFluidRenderer() {
      this.initAtlasSprites();
   }

   protected void initAtlasSprites() {
      TextureMap var1 = Minecraft.getMinecraft().getTextureMapBlocks();
      this.atlasSpritesLava[0] = var1.getAtlasSprite("minecraft:blocks/lava_still");
      this.atlasSpritesLava[1] = var1.getAtlasSprite("minecraft:blocks/lava_flow");
      this.atlasSpritesWater[0] = var1.getAtlasSprite("minecraft:blocks/water_still");
      this.atlasSpritesWater[1] = var1.getAtlasSprite("minecraft:blocks/water_flow");
   }

   public boolean renderFluid(IBlockAccess iBlockAccess, IBlockState iBlockState, BlockPos blockPos, WorldRenderer worldRenderer) {
      try {
         if (Config.isShaders()) {
            SVertexBuilder.pushEntity(iBlockState, blockPos, iBlockAccess, worldRenderer);
         }

         BlockLiquid var8 = (BlockLiquid)iBlockState.getBlock();
         var8.setBlockBoundsBasedOnState(iBlockAccess, blockPos);
         TextureAtlasSprite[] var9 = var8.getMaterial() == Material.lava ? this.atlasSpritesLava : this.atlasSpritesWater;
         RenderEnv var10 = worldRenderer.getRenderEnv(iBlockState, blockPos);
         int var11 = CustomColors.getFluidColor(iBlockAccess, iBlockState, blockPos, var10);
         float var12 = (var11 >> 16 & 0xFF) / 255.0F;
         float var13 = (var11 >> 8 & 0xFF) / 255.0F;
         float var14 = (var11 & 0xFF) / 255.0F;
         boolean var15 = var8.shouldSideBeRendered(iBlockAccess, blockPos.up(), EnumFacing.UP);
         boolean var16 = var8.shouldSideBeRendered(iBlockAccess, blockPos.down(), EnumFacing.DOWN);
         boolean[] var17 = var10.getBorderFlags();
         var17[0] = var8.shouldSideBeRendered(iBlockAccess, blockPos.north(), EnumFacing.NORTH);
         var17[1] = var8.shouldSideBeRendered(iBlockAccess, blockPos.south(), EnumFacing.SOUTH);
         var17[2] = var8.shouldSideBeRendered(iBlockAccess, blockPos.west(), EnumFacing.WEST);
         var17[3] = var8.shouldSideBeRendered(iBlockAccess, blockPos.east(), EnumFacing.EAST);
         if (var15 || var16 || var17[0] || var17[1] || var17[2] || var17[3]) {
            boolean var7 = false;
            Material var22 = var8.getMaterial();
            float var23 = this.getFluidHeight(iBlockAccess, blockPos, var22);
            float var24 = this.getFluidHeight(iBlockAccess, blockPos.south(), var22);
            float var25 = this.getFluidHeight(iBlockAccess, blockPos.east().south(), var22);
            float var26 = this.getFluidHeight(iBlockAccess, blockPos.east(), var22);
            double var27 = blockPos.getX();
            double var29 = blockPos.getY();
            double var31 = blockPos.getZ();
            if (var15) {
               var7 = true;
               TextureAtlasSprite var34 = var9[0];
               float var35 = (float)BlockLiquid.getFlowDirection(iBlockAccess, blockPos, var22);
               if (var35 > -999.0F) {
                  var34 = var9[1];
               }

               worldRenderer.setSprite(var34);
               var23 -= 0.001F;
               var24 -= 0.001F;
               var25 -= 0.001F;
               var26 -= 0.001F;
               float var36;
               float var37;
               float var38;
               float var39;
               float var40;
               float var41;
               float var42;
               float var43;
               if (var35 < -999.0F) {
                  var36 = var34.getInterpolatedU(0.0);
                  var40 = var34.getInterpolatedV(0.0);
                  var37 = var36;
                  var41 = var34.getInterpolatedV(16.0);
                  var38 = var34.getInterpolatedU(16.0);
                  var42 = var41;
                  var39 = var38;
                  var43 = var40;
               } else {
                  float var44 = MathHelper.sin(var35) * 0.25F;
                  float var45 = MathHelper.cos(var35) * 0.25F;
                  var36 = var34.getInterpolatedU(8.0F + (-var45 - var44) * 16.0F);
                  var40 = var34.getInterpolatedV(8.0F + (-var45 + var44) * 16.0F);
                  var37 = var34.getInterpolatedU(8.0F + (-var45 + var44) * 16.0F);
                  var41 = var34.getInterpolatedV(8.0F + (var45 + var44) * 16.0F);
                  var38 = var34.getInterpolatedU(8.0F + (var45 + var44) * 16.0F);
                  var42 = var34.getInterpolatedV(8.0F + (var45 - var44) * 16.0F);
                  var39 = var34.getInterpolatedU(8.0F + (var45 - var44) * 16.0F);
                  var43 = var34.getInterpolatedV(8.0F + (-var45 - var44) * 16.0F);
               }

               int var81 = var8.getMixedBrightnessForBlock(iBlockAccess, blockPos);
               int var82 = var81 >> 16 & 65535;
               int var46 = var81 & 65535;
               float var47 = 1.0F * var12;
               float var48 = 1.0F * var13;
               float var49 = 1.0F * var14;
               worldRenderer.pos(var27 + 0.0, var29 + var23, var31 + 0.0).color(var47, var48, var49, 1.0F).tex(var36, var40).lightmap(var82, var46).endVertex();
               worldRenderer.pos(var27 + 0.0, var29 + var24, var31 + 1.0).color(var47, var48, var49, 1.0F).tex(var37, var41).lightmap(var82, var46).endVertex();
               worldRenderer.pos(var27 + 1.0, var29 + var25, var31 + 1.0).color(var47, var48, var49, 1.0F).tex(var38, var42).lightmap(var82, var46).endVertex();
               worldRenderer.pos(var27 + 1.0, var29 + var26, var31 + 0.0).color(var47, var48, var49, 1.0F).tex(var39, var43).lightmap(var82, var46).endVertex();
               if (var8.shouldRenderSides(iBlockAccess, blockPos.up())) {
                  worldRenderer.pos(var27 + 0.0, var29 + var23, var31 + 0.0)
                     .color(var47, var48, var49, 1.0F)
                     .tex(var36, var40)
                     .lightmap(var82, var46)
                     .endVertex();
                  worldRenderer.pos(var27 + 1.0, var29 + var26, var31 + 0.0)
                     .color(var47, var48, var49, 1.0F)
                     .tex(var39, var43)
                     .lightmap(var82, var46)
                     .endVertex();
                  worldRenderer.pos(var27 + 1.0, var29 + var25, var31 + 1.0)
                     .color(var47, var48, var49, 1.0F)
                     .tex(var38, var42)
                     .lightmap(var82, var46)
                     .endVertex();
                  worldRenderer.pos(var27 + 0.0, var29 + var24, var31 + 1.0)
                     .color(var47, var48, var49, 1.0F)
                     .tex(var37, var41)
                     .lightmap(var82, var46)
                     .endVertex();
               }
            }

            if (var16) {
               worldRenderer.setSprite(var9[0]);
               float var64 = var9[0].getMinU();
               float var66 = var9[0].getMaxU();
               float var68 = var9[0].getMinV();
               float var70 = var9[0].getMaxV();
               int var72 = var8.getMixedBrightnessForBlock(iBlockAccess, blockPos.down());
               int var74 = var72 >> 16 & 65535;
               int var76 = var72 & 65535;
               float var78 = FaceBakery.getFaceBrightness(EnumFacing.DOWN);
               worldRenderer.pos(var27, var29, var31 + 1.0)
                  .color(var12 * var78, var13 * var78, var14 * var78, 1.0F)
                  .tex(var64, var70)
                  .lightmap(var74, var76)
                  .endVertex();
               worldRenderer.pos(var27, var29, var31)
                  .color(var12 * var78, var13 * var78, var14 * var78, 1.0F)
                  .tex(var64, var68)
                  .lightmap(var74, var76)
                  .endVertex();
               worldRenderer.pos(var27 + 1.0, var29, var31)
                  .color(var12 * var78, var13 * var78, var14 * var78, 1.0F)
                  .tex(var66, var68)
                  .lightmap(var74, var76)
                  .endVertex();
               worldRenderer.pos(var27 + 1.0, var29, var31 + 1.0)
                  .color(var12 * var78, var13 * var78, var14 * var78, 1.0F)
                  .tex(var66, var70)
                  .lightmap(var74, var76)
                  .endVertex();
               var7 = true;
            }

            for (int var65 = 0; var65 < 4; var65++) {
               int var67 = 0;
               int var69 = 0;
               if (var65 == 0) {
                  var69--;
               }

               if (var65 == 1) {
                  var69++;
               }

               if (var65 == 2) {
                  var67--;
               }

               if (var65 == 3) {
                  var67++;
               }

               BlockPos var71 = blockPos.add(var67, 0, var69);
               TextureAtlasSprite var73 = var9[1];
               worldRenderer.setSprite(var73);
               if (var17[var65]) {
                  float var75;
                  float var77;
                  double var79;
                  double var80;
                  double var83;
                  double var84;
                  if (var65 == 0) {
                     var75 = var23;
                     var77 = var26;
                     var79 = var27;
                     var83 = var27 + 1.0;
                     var80 = var31 + 0.001F;
                     var84 = var31 + 0.001F;
                  } else if (var65 == 1) {
                     var75 = var25;
                     var77 = var24;
                     var79 = var27 + 1.0;
                     var83 = var27;
                     var80 = var31 + 1.0 - 0.001F;
                     var84 = var31 + 1.0 - 0.001F;
                  } else if (var65 == 2) {
                     var75 = var24;
                     var77 = var23;
                     var79 = var27 + 0.001F;
                     var83 = var27 + 0.001F;
                     var80 = var31 + 1.0;
                     var84 = var31;
                  } else {
                     var75 = var26;
                     var77 = var25;
                     var79 = var27 + 1.0 - 0.001F;
                     var83 = var27 + 1.0 - 0.001F;
                     var80 = var31;
                     var84 = var31 + 1.0;
                  }

                  var7 = true;
                  float var85 = var73.getInterpolatedU(0.0);
                  float var50 = var73.getInterpolatedU(8.0);
                  float var51 = var73.getInterpolatedV((1.0F - var75) * 16.0F * 0.5F);
                  float var52 = var73.getInterpolatedV((1.0F - var77) * 16.0F * 0.5F);
                  float var53 = var73.getInterpolatedV(8.0);
                  int var54 = var8.getMixedBrightnessForBlock(iBlockAccess, var71);
                  int var55 = var54 >> 16 & 65535;
                  int var56 = var54 & 65535;
                  float var57 = var65 < 2 ? FaceBakery.getFaceBrightness(EnumFacing.NORTH) : FaceBakery.getFaceBrightness(EnumFacing.WEST);
                  float var58 = 1.0F * var57 * var12;
                  float var59 = 1.0F * var57 * var13;
                  float var60 = 1.0F * var57 * var14;
                  worldRenderer.pos(var79, var29 + var75, var80).color(var58, var59, var60, 1.0F).tex(var85, var51).lightmap(var55, var56).endVertex();
                  worldRenderer.pos(var83, var29 + var77, var84).color(var58, var59, var60, 1.0F).tex(var50, var52).lightmap(var55, var56).endVertex();
                  worldRenderer.pos(var83, var29 + 0.0, var84).color(var58, var59, var60, 1.0F).tex(var50, var53).lightmap(var55, var56).endVertex();
                  worldRenderer.pos(var79, var29 + 0.0, var80).color(var58, var59, var60, 1.0F).tex(var85, var53).lightmap(var55, var56).endVertex();
                  worldRenderer.pos(var79, var29 + 0.0, var80).color(var58, var59, var60, 1.0F).tex(var85, var53).lightmap(var55, var56).endVertex();
                  worldRenderer.pos(var83, var29 + 0.0, var84).color(var58, var59, var60, 1.0F).tex(var50, var53).lightmap(var55, var56).endVertex();
                  worldRenderer.pos(var83, var29 + var77, var84).color(var58, var59, var60, 1.0F).tex(var50, var52).lightmap(var55, var56).endVertex();
                  worldRenderer.pos(var79, var29 + var75, var80).color(var58, var59, var60, 1.0F).tex(var85, var51).lightmap(var55, var56).endVertex();
               }
            }

            worldRenderer.setSprite(null);
            return var7;
         }
      } finally {
         if (Config.isShaders()) {
            SVertexBuilder.popEntity(worldRenderer);
         }
      }

      return false;
   }

   private float getFluidHeight(IBlockAccess iBlockAccess, BlockPos blockPos, Material material) {
      int var6 = 0;
      float var7 = 0.0F;

      for (int var8 = 0; var8 < 4; var8++) {
         BlockPos var9 = blockPos.add(-(var8 & 1), 0, -(var8 >> 1 & 1));
         if (iBlockAccess.getBlockState(var9.up()).getBlock().getMaterial() == material) {
            return 1.0F;
         }

         IBlockState var10 = iBlockAccess.getBlockState(var9);
         Material var11 = var10.getBlock().getMaterial();
         if (var11 != material) {
            if (!var11.isSolid()) {
               var7++;
               var6++;
            }
         } else {
            int var12 = var10.getValue(BlockLiquid.LEVEL);
            if (var12 >= 8 || var12 == 0) {
               var7 += BlockLiquid.getLiquidHeightPercent(var12) * 10.0F;
               var6 += 10;
            }

            var7 += BlockLiquid.getLiquidHeightPercent(var12);
            var6++;
         }
      }

      return 1.0F - var7 / var6;
   }
}
