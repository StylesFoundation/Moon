package net.minecraft.client.renderer;

import java.util.BitSet;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.optifine.BetterSnow;
import net.optifine.CustomColors;
import net.optifine.model.BlockModelCustomizer;
import net.optifine.model.ListQuadsOverlay;
import net.optifine.render.RenderEnv;
import net.optifine.shaders.SVertexBuilder;
import net.optifine.shaders.Shaders;

public class BlockModelRenderer {
   private static float aoLightValueOpaque = 0.2F;
   private static boolean separateAoLightValue = false;
   private static final EnumWorldBlockLayer[] OVERLAY_LAYERS = new EnumWorldBlockLayer[]{
      EnumWorldBlockLayer.CUTOUT, EnumWorldBlockLayer.CUTOUT_MIPPED, EnumWorldBlockLayer.TRANSLUCENT
   };

   public boolean renderModel(IBlockAccess iBlockAccess, IBakedModel iBakedModel, IBlockState iBlockState, BlockPos blockPos, WorldRenderer worldRenderer) {
      iBlockState.getBlock().setBlockBoundsBasedOnState(iBlockAccess, blockPos);
      return this.renderModel(iBlockAccess, iBakedModel, iBlockState, blockPos, worldRenderer, true);
   }

   public boolean renderModel(
      IBlockAccess iBlockAccess, IBakedModel iBakedModel, IBlockState iBlockState, BlockPos blockPos, WorldRenderer worldRenderer, boolean boolean6
   ) {
      boolean var7 = Minecraft.isAmbientOcclusionEnabled() && iBlockState.getBlock().getLightValue() == 0 && iBakedModel.isAmbientOcclusion();

      try {
         if (Config.isShaders()) {
            SVertexBuilder.pushEntity(iBlockState, blockPos, iBlockAccess, worldRenderer);
         }

         RenderEnv var8 = worldRenderer.getRenderEnv(iBlockState, blockPos);
         iBakedModel = BlockModelCustomizer.getRenderModel(iBakedModel, iBlockState, var8);
         boolean var13 = var7
            ? this.renderModelSmooth(iBlockAccess, iBakedModel, iBlockState, blockPos, worldRenderer, boolean6)
            : this.renderModelFlat(iBlockAccess, iBakedModel, iBlockState, blockPos, worldRenderer, boolean6);
         if (var13) {
            this.renderOverlayModels(iBlockAccess, iBakedModel, iBlockState, blockPos, worldRenderer, boolean6, 0L, var8, var7);
         }

         if (Config.isShaders()) {
            SVertexBuilder.popEntity(worldRenderer);
         }

         return var13;
      } catch (Throwable var11) {
         CrashReport var9 = CrashReport.makeCrashReport(var11, "Tesselating block model");
         CrashReportCategory var10 = var9.makeCategory("Block model being tesselated");
         CrashReportCategory.addBlockInfo(var10, blockPos, iBlockState);
         var10.addCrashSection("Using AO", var7);
         throw new ReportedException(var9);
      }
   }

   public boolean Method373(IBlockAccess iBlockAccess, IBakedModel iBakedModel, Block block, BlockPos blockPos, WorldRenderer worldRenderer, boolean boolean6) {
      IBlockState var7 = iBlockAccess.getBlockState(blockPos);
      return this.renderModelSmooth(iBlockAccess, iBakedModel, var7, blockPos, worldRenderer, boolean6);
   }

   private boolean renderModelSmooth(
      IBlockAccess iBlockAccess, IBakedModel iBakedModel, IBlockState iBlockState, BlockPos blockPos, WorldRenderer worldRenderer, boolean boolean6
   ) {
      boolean var7 = false;
      Block var8 = iBlockState.getBlock();
      RenderEnv var9 = worldRenderer.getRenderEnv(iBlockState, blockPos);
      EnumWorldBlockLayer var10 = worldRenderer.getBlockLayer();

      for (EnumFacing var14 : EnumFacing.VALUES) {
         List var15 = iBakedModel.getFaceQuads(var14);
         if (!var15.isEmpty()) {
            BlockPos var16 = blockPos.offset(var14);
            if (!boolean6 || var8.shouldSideBeRendered(iBlockAccess, var16, var14)) {
               var15 = BlockModelCustomizer.getRenderQuads(var15, iBlockAccess, iBlockState, blockPos, var14, var10, 0L, var9);
               this.renderQuadsSmooth(iBlockAccess, iBlockState, blockPos, worldRenderer, var15, var9);
               var7 = true;
            }
         }
      }

      List var17 = iBakedModel.getGeneralQuads();
      if (var17.size() > 0) {
         var17 = BlockModelCustomizer.getRenderQuads(var17, iBlockAccess, iBlockState, blockPos, null, var10, 0L, var9);
         this.renderQuadsSmooth(iBlockAccess, iBlockState, blockPos, worldRenderer, var17, var9);
         var7 = true;
      }

      return var7;
   }

   public boolean Method374(IBlockAccess iBlockAccess, IBakedModel iBakedModel, Block block, BlockPos blockPos, WorldRenderer worldRenderer, boolean boolean6) {
      IBlockState var7 = iBlockAccess.getBlockState(blockPos);
      return this.renderModelFlat(iBlockAccess, iBakedModel, var7, blockPos, worldRenderer, boolean6);
   }

   public boolean renderModelFlat(
      IBlockAccess iBlockAccess, IBakedModel iBakedModel, IBlockState iBlockState, BlockPos blockPos, WorldRenderer worldRenderer, boolean boolean6
   ) {
      boolean var7 = false;
      Block var8 = iBlockState.getBlock();
      RenderEnv var9 = worldRenderer.getRenderEnv(iBlockState, blockPos);
      EnumWorldBlockLayer var10 = worldRenderer.getBlockLayer();

      for (EnumFacing var14 : EnumFacing.VALUES) {
         List var15 = iBakedModel.getFaceQuads(var14);
         if (!var15.isEmpty()) {
            BlockPos var16 = blockPos.offset(var14);
            if (!boolean6 || var8.shouldSideBeRendered(iBlockAccess, var16, var14)) {
               int var17 = var8.getMixedBrightnessForBlock(iBlockAccess, var16);
               var15 = BlockModelCustomizer.getRenderQuads(var15, iBlockAccess, iBlockState, blockPos, var14, var10, 0L, var9);
               this.renderQuadsFlat(iBlockAccess, iBlockState, blockPos, var14, var17, false, worldRenderer, var15, var9);
               var7 = true;
            }
         }
      }

      List var18 = iBakedModel.getGeneralQuads();
      if (var18.size() > 0) {
         var18 = BlockModelCustomizer.getRenderQuads(var18, iBlockAccess, iBlockState, blockPos, null, var10, 0L, var9);
         this.renderQuadsFlat(iBlockAccess, iBlockState, blockPos, null, -1, true, worldRenderer, var18, var9);
         var7 = true;
      }

      return var7;
   }

   private void renderQuadsSmooth(
      IBlockAccess iBlockAccess, IBlockState iBlockState, BlockPos blockPos, WorldRenderer worldRenderer, List<BakedQuad> list, RenderEnv renderEnv
   ) {
      Block var9 = iBlockState.getBlock();
      float[] var10 = renderEnv.getQuadBounds();
      BitSet var11 = renderEnv.getBoundsFlags();
      BlockModelRenderer.AmbientOcclusionFace var12 = renderEnv.getAoFace();
      double var13 = blockPos.getX();
      double var15 = blockPos.getY();
      double var17 = blockPos.getZ();
      Block.EnumOffsetType var19 = var9.getOffsetType();
      if (var19 != Block.EnumOffsetType.NONE) {
         long var20 = MathHelper.getPositionRandom(blockPos);
         var13 += ((float)(var20 >> 16 & 15L) / 15.0F - 0.5) * 0.5;
         var17 += ((float)(var20 >> 24 & 15L) / 15.0F - 0.5) * 0.5;
         if (var19 == Block.EnumOffsetType.XYZ) {
            var15 += ((float)(var20 >> 20 & 15L) / 15.0F - 1.0) * 0.2;
         }
      }

      for (BakedQuad var21 : list) {
         this.fillQuadBounds(var9, var21.getVertexData(), var21.getFace(), var10, var11);
         var12.updateVertexBrightness(iBlockAccess, var9, blockPos, var21.getFace(), var10, var11);
         if (var21.getSprite().isEmissive) {
            var12.setMaxBlockLight();
         }

         if (worldRenderer.isMultiTexture()) {
            worldRenderer.addVertexData(var21.getVertexDataSingle());
         } else {
            worldRenderer.addVertexData(var21.getVertexData());
         }

         worldRenderer.putSprite(var21.getSprite());
         worldRenderer.putBrightness4(var12.vertexBrightness[0], var12.vertexBrightness[1], var12.vertexBrightness[2], var12.vertexBrightness[3]);
         int var22 = CustomColors.getColorMultiplier(var21, iBlockState, iBlockAccess, blockPos, renderEnv);
         if (!var21.hasTintIndex() && var22 == -1) {
            if (separateAoLightValue) {
               worldRenderer.putColorMultiplierRgba(1.0F, 1.0F, 1.0F, var12.vertexColorMultiplier[0], 4);
               worldRenderer.putColorMultiplierRgba(1.0F, 1.0F, 1.0F, var12.vertexColorMultiplier[1], 3);
               worldRenderer.putColorMultiplierRgba(1.0F, 1.0F, 1.0F, var12.vertexColorMultiplier[2], 2);
               worldRenderer.putColorMultiplierRgba(1.0F, 1.0F, 1.0F, var12.vertexColorMultiplier[3], 1);
            } else {
               worldRenderer.putColorMultiplier(var12.vertexColorMultiplier[0], var12.vertexColorMultiplier[0], var12.vertexColorMultiplier[0], 4);
               worldRenderer.putColorMultiplier(var12.vertexColorMultiplier[1], var12.vertexColorMultiplier[1], var12.vertexColorMultiplier[1], 3);
               worldRenderer.putColorMultiplier(var12.vertexColorMultiplier[2], var12.vertexColorMultiplier[2], var12.vertexColorMultiplier[2], 2);
               worldRenderer.putColorMultiplier(var12.vertexColorMultiplier[3], var12.vertexColorMultiplier[3], var12.vertexColorMultiplier[3], 1);
            }
         } else {
            int var23;
            if (var22 != -1) {
               var23 = var22;
            } else {
               var23 = var9.colorMultiplier(iBlockAccess, blockPos, var21.getTintIndex());
            }

            if (EntityRenderer.anaglyphEnable) {
               var23 = TextureUtil.anaglyphColor(var23);
            }

            float var24 = (var23 >> 16 & 0xFF) / 255.0F;
            float var25 = (var23 >> 8 & 0xFF) / 255.0F;
            float var26 = (var23 & 0xFF) / 255.0F;
            if (separateAoLightValue) {
               worldRenderer.putColorMultiplierRgba(var24, var25, var26, var12.vertexColorMultiplier[0], 4);
               worldRenderer.putColorMultiplierRgba(var24, var25, var26, var12.vertexColorMultiplier[1], 3);
               worldRenderer.putColorMultiplierRgba(var24, var25, var26, var12.vertexColorMultiplier[2], 2);
               worldRenderer.putColorMultiplierRgba(var24, var25, var26, var12.vertexColorMultiplier[3], 1);
            } else {
               worldRenderer.putColorMultiplier(
                  var12.vertexColorMultiplier[0] * var24, var12.vertexColorMultiplier[0] * var25, var12.vertexColorMultiplier[0] * var26, 4
               );
               worldRenderer.putColorMultiplier(
                  var12.vertexColorMultiplier[1] * var24, var12.vertexColorMultiplier[1] * var25, var12.vertexColorMultiplier[1] * var26, 3
               );
               worldRenderer.putColorMultiplier(
                  var12.vertexColorMultiplier[2] * var24, var12.vertexColorMultiplier[2] * var25, var12.vertexColorMultiplier[2] * var26, 2
               );
               worldRenderer.putColorMultiplier(
                  var12.vertexColorMultiplier[3] * var24, var12.vertexColorMultiplier[3] * var25, var12.vertexColorMultiplier[3] * var26, 1
               );
            }
         }

         worldRenderer.putPosition(var13, var15, var17);
      }
   }

   private void fillQuadBounds(Block block, int[] arr, EnumFacing enumFacing, float[] quadBounds, BitSet bitSet) {
      float var6 = 32.0F;
      float var7 = 32.0F;
      float var8 = 32.0F;
      float var9 = -32.0F;
      float var10 = -32.0F;
      float var11 = -32.0F;
      int var12 = arr.length / 4;

      for (int var13 = 0; var13 < 4; var13++) {
         float var14 = Float.intBitsToFloat(arr[var13 * var12]);
         float var15 = Float.intBitsToFloat(arr[var13 * var12 + 1]);
         float var16 = Float.intBitsToFloat(arr[var13 * var12 + 2]);
         var6 = Math.min(var6, var14);
         var7 = Math.min(var7, var15);
         var8 = Math.min(var8, var16);
         var9 = Math.max(var9, var14);
         var10 = Math.max(var10, var15);
         var11 = Math.max(var11, var16);
      }

      if (quadBounds != null) {
         quadBounds[EnumFacing.WEST.getIndex()] = var6;
         quadBounds[EnumFacing.EAST.getIndex()] = var9;
         quadBounds[EnumFacing.DOWN.getIndex()] = var7;
         quadBounds[EnumFacing.UP.getIndex()] = var10;
         quadBounds[EnumFacing.NORTH.getIndex()] = var8;
         quadBounds[EnumFacing.SOUTH.getIndex()] = var11;
         int var17 = EnumFacing.VALUES.length;
         quadBounds[EnumFacing.WEST.getIndex() + var17] = 1.0F - var6;
         quadBounds[EnumFacing.EAST.getIndex() + var17] = 1.0F - var9;
         quadBounds[EnumFacing.DOWN.getIndex() + var17] = 1.0F - var7;
         quadBounds[EnumFacing.UP.getIndex() + var17] = 1.0F - var10;
         quadBounds[EnumFacing.NORTH.getIndex() + var17] = 1.0F - var8;
         quadBounds[EnumFacing.SOUTH.getIndex() + var17] = 1.0F - var11;
      }

      switch (enumFacing) {
         case DOWN:
            bitSet.set(1, var6 >= 1.0E-4F || var8 >= 1.0E-4F || var9 <= 0.9999F || var11 <= 0.9999F);
            bitSet.set(0, (var7 < 1.0E-4F || block.isFullCube()) && var7 == var10);
            break;
         case UP:
            bitSet.set(1, var6 >= 1.0E-4F || var8 >= 1.0E-4F || var9 <= 0.9999F || var11 <= 0.9999F);
            bitSet.set(0, (var10 > 0.9999F || block.isFullCube()) && var7 == var10);
            break;
         case NORTH:
            bitSet.set(1, var6 >= 1.0E-4F || var7 >= 1.0E-4F || var9 <= 0.9999F || var10 <= 0.9999F);
            bitSet.set(0, (var8 < 1.0E-4F || block.isFullCube()) && var8 == var11);
            break;
         case SOUTH:
            bitSet.set(1, var6 >= 1.0E-4F || var7 >= 1.0E-4F || var9 <= 0.9999F || var10 <= 0.9999F);
            bitSet.set(0, (var11 > 0.9999F || block.isFullCube()) && var8 == var11);
            break;
         case WEST:
            bitSet.set(1, var7 >= 1.0E-4F || var8 >= 1.0E-4F || var10 <= 0.9999F || var11 <= 0.9999F);
            bitSet.set(0, (var6 < 1.0E-4F || block.isFullCube()) && var6 == var9);
            break;
         case EAST:
            bitSet.set(1, var7 >= 1.0E-4F || var8 >= 1.0E-4F || var10 <= 0.9999F || var11 <= 0.9999F);
            bitSet.set(0, (var9 > 0.9999F || block.isFullCube()) && var6 == var9);
      }
   }

   private void renderQuadsFlat(
      IBlockAccess iBlockAccess,
      IBlockState iBlockState,
      BlockPos blockPos,
      EnumFacing enumFacing,
      int integer,
      boolean boolean6,
      WorldRenderer worldRenderer,
      List<BakedQuad> list,
      RenderEnv renderEnv
   ) {
      Block var12 = iBlockState.getBlock();
      BitSet var13 = renderEnv.getBoundsFlags();
      double var14 = blockPos.getX();
      double var16 = blockPos.getY();
      double var18 = blockPos.getZ();
      Block.EnumOffsetType var20 = var12.getOffsetType();
      if (var20 != Block.EnumOffsetType.NONE) {
         int var21 = blockPos.getX();
         int var22 = blockPos.getZ();
         long var23 = var21 * 3129871L ^ var22 * 116129781L;
         var23 = var23 * var23 * 42317861L + var23 * 11L;
         var14 += ((float)(var23 >> 16 & 15L) / 15.0F - 0.5) * 0.5;
         var18 += ((float)(var23 >> 24 & 15L) / 15.0F - 0.5) * 0.5;
         if (var20 == Block.EnumOffsetType.XYZ) {
            var16 += ((float)(var23 >> 20 & 15L) / 15.0F - 1.0) * 0.2;
         }
      }

      for (BakedQuad var29 : list) {
         if (boolean6) {
            this.fillQuadBounds(var12, var29.getVertexData(), var29.getFace(), null, var13);
            integer = var13.get(0)
               ? var12.getMixedBrightnessForBlock(iBlockAccess, blockPos.offset(var29.getFace()))
               : var12.getMixedBrightnessForBlock(iBlockAccess, blockPos);
         }

         if (var29.getSprite().isEmissive) {
            integer |= 240;
         }

         if (worldRenderer.isMultiTexture()) {
            worldRenderer.addVertexData(var29.getVertexDataSingle());
         } else {
            worldRenderer.addVertexData(var29.getVertexData());
         }

         worldRenderer.putSprite(var29.getSprite());
         worldRenderer.putBrightness4(integer, integer, integer, integer);
         int var31 = CustomColors.getColorMultiplier(var29, iBlockState, iBlockAccess, blockPos, renderEnv);
         if (var29.hasTintIndex() || var31 != -1) {
            int var24;
            if (var31 != -1) {
               var24 = var31;
            } else {
               var24 = var12.colorMultiplier(iBlockAccess, blockPos, var29.getTintIndex());
            }

            if (EntityRenderer.anaglyphEnable) {
               var24 = TextureUtil.anaglyphColor(var24);
            }

            float var25 = (var24 >> 16 & 0xFF) / 255.0F;
            float var26 = (var24 >> 8 & 0xFF) / 255.0F;
            float var27 = (var24 & 0xFF) / 255.0F;
            worldRenderer.putColorMultiplier(var25, var26, var27, 4);
            worldRenderer.putColorMultiplier(var25, var26, var27, 3);
            worldRenderer.putColorMultiplier(var25, var26, var27, 2);
            worldRenderer.putColorMultiplier(var25, var26, var27, 1);
         }

         worldRenderer.putPosition(var14, var16, var18);
      }
   }

   public void renderModelBrightnessColor(IBakedModel iBakedModel, float float2, float float3, float float4, float float5) {
      for (EnumFacing var9 : EnumFacing.VALUES) {
         this.renderModelBrightnessColorQuads(float2, float3, float4, float5, iBakedModel.getFaceQuads(var9));
      }

      this.renderModelBrightnessColorQuads(float2, float3, float4, float5, iBakedModel.getGeneralQuads());
   }

   public void renderModelBrightness(IBakedModel iBakedModel, IBlockState iBlockState, float float3, boolean boolean4) {
      Block var7 = iBlockState.getBlock();
      var7.setBlockBoundsForItemRender();
      GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
      int var8 = var7.getRenderColor(var7.getStateForEntityRender(iBlockState));
      if (EntityRenderer.anaglyphEnable) {
         var8 = TextureUtil.anaglyphColor(var8);
      }

      float var9 = (var8 >> 16 & 0xFF) / 255.0F;
      float var10 = (var8 >> 8 & 0xFF) / 255.0F;
      float var11 = (var8 & 0xFF) / 255.0F;
      if (!boolean4) {
         GlStateManager.color(float3, float3, float3, 1.0F);
      }

      this.renderModelBrightnessColor(iBakedModel, float3, var9, var10, var11);
   }

   private void renderModelBrightnessColorQuads(float float1, float float2, float float3, float float4, List<BakedQuad> list) {
      Tessellator var8 = Tessellator.getInstance();
      WorldRenderer var9 = var8.getWorldRenderer();

      for (BakedQuad var11 : list) {
         var9.begin(7, DefaultVertexFormats.ITEM);
         var9.addVertexData(var11.getVertexData());
         var9.putSprite(var11.getSprite());
         if (var11.hasTintIndex()) {
            var9.putColorRGB_F4(float2 * float1, float3 * float1, float4 * float1);
         } else {
            var9.putColorRGB_F4(float1, float1, float1);
         }

         Vec3i var12 = var11.getFace().getDirectionVec();
         var9.putNormal(var12.getX(), var12.getY(), var12.getZ());
         var8.draw();
      }
   }

   public static float fixAoLightValue(float float1) {
      return float1 == 0.2F ? aoLightValueOpaque : float1;
   }

   public static void updateAoLightValue() {
      aoLightValueOpaque = 1.0F - Config.getAmbientOcclusionLevel() * 0.8F;
      separateAoLightValue = Config.isShaders() && Shaders.isSeparateAo();
   }

   private void renderOverlayModels(
      IBlockAccess iBlockAccess,
      IBakedModel iBakedModel,
      IBlockState iBlockState,
      BlockPos blockPos,
      WorldRenderer worldRenderer,
      boolean boolean6,
      long long7,
      RenderEnv renderEnv,
      boolean boolean9
   ) {
      if (renderEnv.isOverlaysRendered()) {
         for (EnumWorldBlockLayer var16 : OVERLAY_LAYERS) {
            ListQuadsOverlay var17 = renderEnv.getListQuadsOverlay(var16);
            if (var17.size() > 0) {
               RegionRenderCacheBuilder var18 = renderEnv.getRegionRenderCacheBuilder();
               if (var18 != null) {
                  WorldRenderer var19 = var18.getWorldRendererByLayer(var16);
                  if (!var19.isDrawing()) {
                     var19.begin(7, DefaultVertexFormats.BLOCK);
                     var19.setTranslation(worldRenderer.getXOffset(), worldRenderer.getYOffset(), worldRenderer.getZOffset());
                  }

                  for (int var20 = 0; var20 < var17.size(); var20++) {
                     BakedQuad var21 = var17.getQuad(var20);
                     List var22 = var17.getListQuadsSingle(var21);
                     IBlockState var23 = var17.getBlockState(var20);
                     if (var21.getQuadEmissive() != null) {
                        var17.addQuad(var21.getQuadEmissive(), var23);
                     }

                     renderEnv.reset(var23, blockPos);
                     if (boolean9) {
                        this.renderQuadsSmooth(iBlockAccess, var23, blockPos, var19, var22, renderEnv);
                     } else {
                        int var24 = var23.getBlock().getMixedBrightnessForBlock(iBlockAccess, blockPos.offset(var21.getFace()));
                        this.renderQuadsFlat(iBlockAccess, var23, blockPos, var21.getFace(), var24, false, var19, var22, renderEnv);
                     }
                  }
               }

               var17.clear();
            }
         }
      }

      if (Config.isBetterSnow() && !renderEnv.isBreakingAnimation() && BetterSnow.shouldRender(iBlockAccess, iBlockState, blockPos)) {
         IBakedModel var25 = BetterSnow.getModelSnowLayer();
         IBlockState var26 = BetterSnow.getStateSnowLayer();
         this.renderModel(iBlockAccess, var25, var26, blockPos, worldRenderer, boolean6);
      }
   }

   public static class AmbientOcclusionFace {
      private final float[] vertexColorMultiplier = new float[4];
      private final int[] vertexBrightness = new int[4];

      public AmbientOcclusionFace() {
         this(null);
      }

      public AmbientOcclusionFace(BlockModelRenderer blockModelRenderer) {
      }

      public void setMaxBlockLight() {
         short var3 = 240;
         this.vertexBrightness[0] = this.vertexBrightness[0] | var3;
         this.vertexBrightness[1] = this.vertexBrightness[1] | var3;
         this.vertexBrightness[2] = this.vertexBrightness[2] | var3;
         this.vertexBrightness[3] = this.vertexBrightness[3] | var3;
         this.vertexColorMultiplier[0] = 1.0F;
         this.vertexColorMultiplier[1] = 1.0F;
         this.vertexColorMultiplier[2] = 1.0F;
         this.vertexColorMultiplier[3] = 1.0F;
      }

      public void updateVertexBrightness(IBlockAccess iBlockAccess, Block block, BlockPos blockPos, EnumFacing enumFacing, float[] arr, BitSet bitSet) {
         BlockPos var9 = bitSet.get(0) ? blockPos.offset(enumFacing) : blockPos;
         BlockModelRenderer.EnumNeighborInfo var10 = BlockModelRenderer.EnumNeighborInfo.getNeighbourInfo(enumFacing);
         BlockPos var11 = var9.offset(var10.field_178276_g[0]);
         BlockPos var12 = var9.offset(var10.field_178276_g[1]);
         BlockPos var13 = var9.offset(var10.field_178276_g[2]);
         BlockPos var14 = var9.offset(var10.field_178276_g[3]);
         int var15 = block.getMixedBrightnessForBlock(iBlockAccess, var11);
         int var16 = block.getMixedBrightnessForBlock(iBlockAccess, var12);
         int var17 = block.getMixedBrightnessForBlock(iBlockAccess, var13);
         int var18 = block.getMixedBrightnessForBlock(iBlockAccess, var14);
         float var19 = BlockModelRenderer.fixAoLightValue(iBlockAccess.getBlockState(var11).getBlock().getAmbientOcclusionLightValue());
         float var20 = BlockModelRenderer.fixAoLightValue(iBlockAccess.getBlockState(var12).getBlock().getAmbientOcclusionLightValue());
         float var21 = BlockModelRenderer.fixAoLightValue(iBlockAccess.getBlockState(var13).getBlock().getAmbientOcclusionLightValue());
         float var22 = BlockModelRenderer.fixAoLightValue(iBlockAccess.getBlockState(var14).getBlock().getAmbientOcclusionLightValue());
         boolean var23 = iBlockAccess.getBlockState(var11.offset(enumFacing)).getBlock().isTranslucent();
         boolean var24 = iBlockAccess.getBlockState(var12.offset(enumFacing)).getBlock().isTranslucent();
         boolean var25 = iBlockAccess.getBlockState(var13.offset(enumFacing)).getBlock().isTranslucent();
         boolean var26 = iBlockAccess.getBlockState(var14.offset(enumFacing)).getBlock().isTranslucent();
         float var27;
         int var28;
         if (!var25 && !var23) {
            var27 = var19;
            var28 = var15;
         } else {
            BlockPos var29 = var11.offset(var10.field_178276_g[2]);
            var27 = BlockModelRenderer.fixAoLightValue(iBlockAccess.getBlockState(var29).getBlock().getAmbientOcclusionLightValue());
            var28 = block.getMixedBrightnessForBlock(iBlockAccess, var29);
         }

         float var30;
         int var62;
         if (!var26 && !var23) {
            var30 = var19;
            var62 = var15;
         } else {
            BlockPos var31 = var11.offset(var10.field_178276_g[3]);
            var30 = BlockModelRenderer.fixAoLightValue(iBlockAccess.getBlockState(var31).getBlock().getAmbientOcclusionLightValue());
            var62 = block.getMixedBrightnessForBlock(iBlockAccess, var31);
         }

         float var32;
         int var63;
         if (!var25 && !var24) {
            var32 = var20;
            var63 = var16;
         } else {
            BlockPos var33 = var12.offset(var10.field_178276_g[2]);
            var32 = BlockModelRenderer.fixAoLightValue(iBlockAccess.getBlockState(var33).getBlock().getAmbientOcclusionLightValue());
            var63 = block.getMixedBrightnessForBlock(iBlockAccess, var33);
         }

         float var34;
         int var64;
         if (!var26 && !var24) {
            var34 = var20;
            var64 = var16;
         } else {
            BlockPos var35 = var12.offset(var10.field_178276_g[3]);
            var34 = BlockModelRenderer.fixAoLightValue(iBlockAccess.getBlockState(var35).getBlock().getAmbientOcclusionLightValue());
            var64 = block.getMixedBrightnessForBlock(iBlockAccess, var35);
         }

         int var65 = block.getMixedBrightnessForBlock(iBlockAccess, blockPos);
         if (bitSet.get(0) || !iBlockAccess.getBlockState(blockPos.offset(enumFacing)).getBlock().isOpaqueCube()) {
            var65 = block.getMixedBrightnessForBlock(iBlockAccess, blockPos.offset(enumFacing));
         }

         float var36 = BlockModelRenderer.fixAoLightValue(
            bitSet.get(0)
               ? iBlockAccess.getBlockState(var9).getBlock().getAmbientOcclusionLightValue()
               : iBlockAccess.getBlockState(blockPos).getBlock().getAmbientOcclusionLightValue()
         );
         BlockModelRenderer.VertexTranslations var37 = BlockModelRenderer.VertexTranslations.getVertexTranslations(enumFacing);
         if (bitSet.get(1) && var10.field_178289_i) {
            float var66 = (var22 + var19 + var30 + var36) * 0.25F;
            float var67 = (var21 + var19 + var27 + var36) * 0.25F;
            float var68 = (var21 + var20 + var32 + var36) * 0.25F;
            float var69 = (var22 + var20 + var34 + var36) * 0.25F;
            float var42 = arr[var10.field_178286_j[0].field_178229_m] * arr[var10.field_178286_j[1].field_178229_m];
            float var43 = arr[var10.field_178286_j[2].field_178229_m] * arr[var10.field_178286_j[3].field_178229_m];
            float var44 = arr[var10.field_178286_j[4].field_178229_m] * arr[var10.field_178286_j[5].field_178229_m];
            float var45 = arr[var10.field_178286_j[6].field_178229_m] * arr[var10.field_178286_j[7].field_178229_m];
            float var46 = arr[var10.field_178287_k[0].field_178229_m] * arr[var10.field_178287_k[1].field_178229_m];
            float var47 = arr[var10.field_178287_k[2].field_178229_m] * arr[var10.field_178287_k[3].field_178229_m];
            float var48 = arr[var10.field_178287_k[4].field_178229_m] * arr[var10.field_178287_k[5].field_178229_m];
            float var49 = arr[var10.field_178287_k[6].field_178229_m] * arr[var10.field_178287_k[7].field_178229_m];
            float var50 = arr[var10.field_178284_l[0].field_178229_m] * arr[var10.field_178284_l[1].field_178229_m];
            float var51 = arr[var10.field_178284_l[2].field_178229_m] * arr[var10.field_178284_l[3].field_178229_m];
            float var52 = arr[var10.field_178284_l[4].field_178229_m] * arr[var10.field_178284_l[5].field_178229_m];
            float var53 = arr[var10.field_178284_l[6].field_178229_m] * arr[var10.field_178284_l[7].field_178229_m];
            float var54 = arr[var10.field_178285_m[0].field_178229_m] * arr[var10.field_178285_m[1].field_178229_m];
            float var55 = arr[var10.field_178285_m[2].field_178229_m] * arr[var10.field_178285_m[3].field_178229_m];
            float var56 = arr[var10.field_178285_m[4].field_178229_m] * arr[var10.field_178285_m[5].field_178229_m];
            float var57 = arr[var10.field_178285_m[6].field_178229_m] * arr[var10.field_178285_m[7].field_178229_m];
            this.vertexColorMultiplier[var37.field_178191_g] = var66 * var42 + var67 * var43 + var68 * var44 + var69 * var45;
            this.vertexColorMultiplier[var37.field_178200_h] = var66 * var46 + var67 * var47 + var68 * var48 + var69 * var49;
            this.vertexColorMultiplier[var37.field_178201_i] = var66 * var50 + var67 * var51 + var68 * var52 + var69 * var53;
            this.vertexColorMultiplier[var37.field_178198_j] = var66 * var54 + var67 * var55 + var68 * var56 + var69 * var57;
            int var58 = this.getAoBrightness(var18, var15, var62, var65);
            int var59 = this.getAoBrightness(var17, var15, var28, var65);
            int var60 = this.getAoBrightness(var17, var16, var63, var65);
            int var61 = this.getAoBrightness(var18, var16, var64, var65);
            this.vertexBrightness[var37.field_178191_g] = this.getVertexBrightness(var58, var59, var60, var61, var42, var43, var44, var45);
            this.vertexBrightness[var37.field_178200_h] = this.getVertexBrightness(var58, var59, var60, var61, var46, var47, var48, var49);
            this.vertexBrightness[var37.field_178201_i] = this.getVertexBrightness(var58, var59, var60, var61, var50, var51, var52, var53);
            this.vertexBrightness[var37.field_178198_j] = this.getVertexBrightness(var58, var59, var60, var61, var54, var55, var56, var57);
         } else {
            float var38 = (var22 + var19 + var30 + var36) * 0.25F;
            float var39 = (var21 + var19 + var27 + var36) * 0.25F;
            float var40 = (var21 + var20 + var32 + var36) * 0.25F;
            float var41 = (var22 + var20 + var34 + var36) * 0.25F;
            this.vertexBrightness[var37.field_178191_g] = this.getAoBrightness(var18, var15, var62, var65);
            this.vertexBrightness[var37.field_178200_h] = this.getAoBrightness(var17, var15, var28, var65);
            this.vertexBrightness[var37.field_178201_i] = this.getAoBrightness(var17, var16, var63, var65);
            this.vertexBrightness[var37.field_178198_j] = this.getAoBrightness(var18, var16, var64, var65);
            this.vertexColorMultiplier[var37.field_178191_g] = var38;
            this.vertexColorMultiplier[var37.field_178200_h] = var39;
            this.vertexColorMultiplier[var37.field_178201_i] = var40;
            this.vertexColorMultiplier[var37.field_178198_j] = var41;
         }
      }

      private int getAoBrightness(int integer1, int integer2, int integer3, int integer4) {
         if (integer1 == 0) {
            integer1 = integer4;
         }

         if (integer2 == 0) {
            integer2 = integer4;
         }

         if (integer3 == 0) {
            integer3 = integer4;
         }

         return integer1 + integer2 + integer3 + integer4 >> 2 & 16711935;
      }

      private int getVertexBrightness(int integer1, int integer2, int integer3, int integer4, float float5, float float6, float float7, float float8) {
         int var11 = (int)(
               (integer1 >> 16 & 0xFF) * float5 + (integer2 >> 16 & 0xFF) * float6 + (integer3 >> 16 & 0xFF) * float7 + (integer4 >> 16 & 0xFF) * float8
            )
            & 0xFF;
         int var12 = (int)((integer1 & 0xFF) * float5 + (integer2 & 0xFF) * float6 + (integer3 & 0xFF) * float7 + (integer4 & 0xFF) * float8) & 0xFF;
         return var11 << 16 | var12;
      }
   }

   public enum EnumNeighborInfo {
      DOWN(
         new EnumFacing[]{EnumFacing.WEST, EnumFacing.EAST, EnumFacing.NORTH, EnumFacing.SOUTH},
         0.5F,
         false,
         new BlockModelRenderer.Orientation[0],
         new BlockModelRenderer.Orientation[0],
         new BlockModelRenderer.Orientation[0],
         new BlockModelRenderer.Orientation[0]
      ),
      UP(
         new EnumFacing[]{EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.SOUTH},
         1.0F,
         false,
         new BlockModelRenderer.Orientation[0],
         new BlockModelRenderer.Orientation[0],
         new BlockModelRenderer.Orientation[0],
         new BlockModelRenderer.Orientation[0]
      ),
      NORTH(
         new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.EAST, EnumFacing.WEST},
         0.8F,
         true,
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.FLIP_WEST,
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.WEST,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.WEST,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.FLIP_WEST
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.FLIP_EAST,
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.EAST,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.EAST,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.FLIP_EAST
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.FLIP_EAST,
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.EAST,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.EAST,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.FLIP_EAST
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.FLIP_WEST,
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.WEST,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.WEST,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.FLIP_WEST
         }
      ),
      SOUTH(
         new EnumFacing[]{EnumFacing.WEST, EnumFacing.EAST, EnumFacing.DOWN, EnumFacing.UP},
         0.8F,
         true,
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.FLIP_WEST,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.FLIP_WEST,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.WEST,
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.WEST
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.FLIP_WEST,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.FLIP_WEST,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.WEST,
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.WEST
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.FLIP_EAST,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.FLIP_EAST,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.EAST,
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.EAST
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.FLIP_EAST,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.FLIP_EAST,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.EAST,
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.EAST
         }
      ),
      WEST(
         new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.SOUTH},
         0.6F,
         true,
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.SOUTH,
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.FLIP_SOUTH,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.FLIP_SOUTH,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.SOUTH
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.NORTH,
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.FLIP_NORTH,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.FLIP_NORTH,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.NORTH
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.NORTH,
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.FLIP_NORTH,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.FLIP_NORTH,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.NORTH
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.SOUTH,
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.FLIP_SOUTH,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.FLIP_SOUTH,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.SOUTH
         }
      ),
      EAST(
         new EnumFacing[]{EnumFacing.DOWN, EnumFacing.UP, EnumFacing.NORTH, EnumFacing.SOUTH},
         0.6F,
         true,
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.SOUTH,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.FLIP_SOUTH,
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.FLIP_SOUTH,
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.SOUTH
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.NORTH,
            BlockModelRenderer.Orientation.FLIP_DOWN,
            BlockModelRenderer.Orientation.FLIP_NORTH,
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.FLIP_NORTH,
            BlockModelRenderer.Orientation.DOWN,
            BlockModelRenderer.Orientation.NORTH
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.NORTH,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.FLIP_NORTH,
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.FLIP_NORTH,
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.NORTH
         },
         new BlockModelRenderer.Orientation[]{
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.SOUTH,
            BlockModelRenderer.Orientation.FLIP_UP,
            BlockModelRenderer.Orientation.FLIP_SOUTH,
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.FLIP_SOUTH,
            BlockModelRenderer.Orientation.UP,
            BlockModelRenderer.Orientation.SOUTH
         }
      );

      private final EnumFacing[] field_178276_g;
      private final float field_178288_h;
      private final boolean field_178289_i;
      private final BlockModelRenderer.Orientation[] field_178286_j;
      private final BlockModelRenderer.Orientation[] field_178287_k;
      private final BlockModelRenderer.Orientation[] field_178284_l;
      private final BlockModelRenderer.Orientation[] field_178285_m;

      EnumNeighborInfo(
         EnumFacing[] arr,
         float float4,
         boolean boolean5,
         BlockModelRenderer.Orientation[] arr6,
         BlockModelRenderer.Orientation[] arr7,
         BlockModelRenderer.Orientation[] arr8,
         BlockModelRenderer.Orientation[] arr9
      ) {
         this.field_178276_g = arr;
         this.field_178288_h = float4;
         this.field_178289_i = boolean5;
         this.field_178286_j = arr6;
         this.field_178287_k = arr7;
         this.field_178284_l = arr8;
         this.field_178285_m = arr9;
      }

      private static final BlockModelRenderer.EnumNeighborInfo[] VALUES = new BlockModelRenderer.EnumNeighborInfo[6];

      public static BlockModelRenderer.EnumNeighborInfo getNeighbourInfo(EnumFacing enumFacing) {
         return VALUES[enumFacing.getIndex()];
      }

      private static BlockModelRenderer.EnumNeighborInfo[] Method976() {
         return new BlockModelRenderer.EnumNeighborInfo[]{DOWN, UP, NORTH, SOUTH, WEST, EAST};
      }

      static {
         VALUES[EnumFacing.DOWN.getIndex()] = DOWN;
         VALUES[EnumFacing.UP.getIndex()] = UP;
         VALUES[EnumFacing.NORTH.getIndex()] = NORTH;
         VALUES[EnumFacing.SOUTH.getIndex()] = SOUTH;
         VALUES[EnumFacing.WEST.getIndex()] = WEST;
         VALUES[EnumFacing.EAST.getIndex()] = EAST;
      }
   }

   public enum Orientation {
      DOWN(EnumFacing.DOWN, false),
      UP(EnumFacing.UP, false),
      NORTH(EnumFacing.NORTH, false),
      SOUTH(EnumFacing.SOUTH, false),
      WEST(EnumFacing.WEST, false),
      EAST(EnumFacing.EAST, false),
      FLIP_DOWN(EnumFacing.DOWN, true),
      FLIP_UP(EnumFacing.UP, true),
      FLIP_NORTH(EnumFacing.NORTH, true),
      FLIP_SOUTH(EnumFacing.SOUTH, true),
      FLIP_WEST(EnumFacing.WEST, true),
      FLIP_EAST(EnumFacing.EAST, true);

      private final int field_178229_m;

      Orientation(EnumFacing enumFacing, boolean boolean4) {
         this.field_178229_m = enumFacing.getIndex() + (boolean4 ? EnumFacing.values().length : 0);
      }

      private static BlockModelRenderer.Orientation[] Method377() {
         return new BlockModelRenderer.Orientation[]{DOWN, UP, NORTH, SOUTH, WEST, EAST, FLIP_DOWN, FLIP_UP, FLIP_NORTH, FLIP_SOUTH, FLIP_WEST, FLIP_EAST};
      }
   }

   enum VertexTranslations {
      DOWN(0, 1, 2, 3),
      UP(2, 3, 0, 1),
      NORTH(3, 0, 1, 2),
      SOUTH(0, 1, 2, 3),
      WEST(3, 0, 1, 2),
      EAST(1, 2, 3, 0);

      private final int field_178191_g;
      private final int field_178200_h;
      private final int field_178201_i;
      private final int field_178198_j;

      VertexTranslations(int integer3, int integer4, int integer5, int integer6) {
         this.field_178191_g = integer3;
         this.field_178200_h = integer4;
         this.field_178201_i = integer5;
         this.field_178198_j = integer6;
      }

      private static final BlockModelRenderer.VertexTranslations[] VALUES = new BlockModelRenderer.VertexTranslations[6];

      public static BlockModelRenderer.VertexTranslations getVertexTranslations(EnumFacing enumFacing) {
         return VALUES[enumFacing.getIndex()];
      }

      private static BlockModelRenderer.VertexTranslations[] Method1029() {
         return new BlockModelRenderer.VertexTranslations[]{DOWN, UP, NORTH, SOUTH, WEST, EAST};
      }

      static {
         VALUES[EnumFacing.DOWN.getIndex()] = DOWN;
         VALUES[EnumFacing.UP.getIndex()] = UP;
         VALUES[EnumFacing.NORTH.getIndex()] = NORTH;
         VALUES[EnumFacing.SOUTH.getIndex()] = SOUTH;
         VALUES[EnumFacing.WEST.getIndex()] = WEST;
         VALUES[EnumFacing.EAST.getIndex()] = EAST;
      }
   }
}
