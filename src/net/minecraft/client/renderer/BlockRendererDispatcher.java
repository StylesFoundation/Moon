package net.minecraft.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.client.resources.model.WeightedBakedModel;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.WorldType;

public class BlockRendererDispatcher implements IResourceManagerReloadListener {
   private final BlockModelShapes blockModelShapes;
   private final GameSettings gameSettings;
   private final BlockModelRenderer blockModelRenderer = new BlockModelRenderer();
   private final ChestRenderer chestRenderer = new ChestRenderer();
   private final BlockFluidRenderer fluidRenderer = new BlockFluidRenderer();

   public BlockRendererDispatcher(BlockModelShapes blockModelShapes, GameSettings gameSettings) {
      this.blockModelShapes = blockModelShapes;
      this.gameSettings = gameSettings;
   }

   public BlockModelShapes getBlockModelShapes() {
      return this.blockModelShapes;
   }

   public void renderBlockDamage(IBlockState iBlockState, BlockPos blockPos, TextureAtlasSprite textureAtlasSprite, IBlockAccess iBlockAccess) {
      Block var5 = iBlockState.getBlock();
      if (var5.getRenderType() == 3) {
         iBlockState = var5.getActualState(iBlockState, iBlockAccess, blockPos);
         IBakedModel var7 = this.blockModelShapes.getModelForState(iBlockState);
         IBakedModel var8 = new SimpleBakedModel.Builder(var7, textureAtlasSprite).makeBakedModel();
         this.blockModelRenderer.renderModel(iBlockAccess, var8, iBlockState, blockPos, Tessellator.getInstance().getWorldRenderer());
      }
   }

   public boolean renderBlock(IBlockState iBlockState, BlockPos blockPos, IBlockAccess iBlockAccess, WorldRenderer worldRenderer) {
      try {
         int var5 = iBlockState.getBlock().getRenderType();
         if (var5 == -1) {
            return false;
         }

         switch (var5) {
            case 1:
               return this.fluidRenderer.renderFluid(iBlockAccess, iBlockState, blockPos, worldRenderer);
            case 3:
               IBakedModel var8 = this.getModelFromBlockState(iBlockState, iBlockAccess, blockPos);
               return this.blockModelRenderer.renderModel(iBlockAccess, var8, iBlockState, blockPos, worldRenderer);
            default:
               return false;
         }
      } catch (Throwable var7) {
         CrashReport var6 = CrashReport.makeCrashReport(var7, "Tesselating block in world");
         CrashReportCategory.addBlockInfo(
            var6.makeCategory("Block being tesselated"), blockPos, iBlockState.getBlock(), iBlockState.getBlock().getMetaFromState(iBlockState)
         );
         throw new ReportedException(var6);
      }
   }

   public BlockModelRenderer getBlockModelRenderer() {
      return this.blockModelRenderer;
   }

   private IBakedModel getBakedModel(IBlockState iBlockState, BlockPos blockPos) {
      IBakedModel var3 = this.blockModelShapes.getModelForState(iBlockState);
      if (blockPos != null && this.gameSettings.allowBlockAlternatives && var3 instanceof WeightedBakedModel) {
         var3 = ((WeightedBakedModel)var3).getAlternativeModel(MathHelper.getPositionRandom(blockPos));
      }

      return var3;
   }

   public IBakedModel getModelFromBlockState(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
      Block var4 = iBlockState.getBlock();
      if (iBlockAccess.getWorldType() != WorldType.DEBUG_WORLD) {
         try {
            iBlockState = var4.getActualState(iBlockState, iBlockAccess, blockPos);
         } catch (Exception var6) {
         }
      }

      IBakedModel var5 = this.blockModelShapes.getModelForState(iBlockState);
      if (blockPos != null && this.gameSettings.allowBlockAlternatives && var5 instanceof WeightedBakedModel) {
         var5 = ((WeightedBakedModel)var5).getAlternativeModel(MathHelper.getPositionRandom(blockPos));
      }

      return var5;
   }

   public void renderBlockBrightness(IBlockState iBlockState, float float2) {
      int var3 = iBlockState.getBlock().getRenderType();
      if (var3 != -1) {
         switch (var3) {
            case 1:
            default:
               break;
            case 2:
               this.chestRenderer.renderChestBrightness(iBlockState.getBlock(), float2);
               break;
            case 3:
               IBakedModel var4 = this.getBakedModel(iBlockState, null);
               this.blockModelRenderer.renderModelBrightness(var4, iBlockState, float2, true);
         }
      }
   }

   public boolean isRenderTypeChest(Block block, int integer) {
      if (block == null) {
         return false;
      }

      int var3 = block.getRenderType();
      return var3 == 2;
   }

   @Override
   public void onResourceManagerReload(IResourceManager iResourceManager) {
      this.fluidRenderer.initAtlasSprites();
   }
}
