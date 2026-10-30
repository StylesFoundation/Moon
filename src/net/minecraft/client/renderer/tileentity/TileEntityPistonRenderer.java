package net.minecraft.client.renderer.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class TileEntityPistonRenderer extends TileEntitySpecialRenderer<TileEntityPiston> {
   private final BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();

   public void renderTileEntityAt(TileEntityPiston tileEntityPiston, double double2, double double3, double double4, float float5, int integer) {
      BlockPos var12 = tileEntityPiston.getPos();
      IBlockState var13 = tileEntityPiston.getPistonState();
      Block var14 = var13.getBlock();
      if (var14.getMaterial() != Material.air && tileEntityPiston.getProgress(float5) < 1.0F) {
         Tessellator var15 = Tessellator.getInstance();
         WorldRenderer var16 = var15.getWorldRenderer();
         this.bindTexture(TextureMap.locationBlocksTexture);
         RenderHelper.disableStandardItemLighting();
         GlStateManager.blendFunc(770, 771);
         GlStateManager.enableBlend();
         GlStateManager.disableCull();
         if (Minecraft.isAmbientOcclusionEnabled()) {
            GlStateManager.shadeModel(7425);
         } else {
            GlStateManager.shadeModel(7424);
         }

         var16.begin(7, DefaultVertexFormats.BLOCK);
         var16.setTranslation(
            (float)double2 - var12.getX() + tileEntityPiston.getOffsetX(float5),
            (float)double3 - var12.getY() + tileEntityPiston.getOffsetY(float5),
            (float)double4 - var12.getZ() + tileEntityPiston.getOffsetZ(float5)
         );
         World var17 = this.getWorld();
         if (var14 == Blocks.piston_head && tileEntityPiston.getProgress(float5) < 0.5F) {
            var13 = var13.withProperty(BlockPistonExtension.SHORT, Boolean.TRUE);
            this.blockRenderer
               .getBlockModelRenderer()
               .renderModel(var17, this.blockRenderer.getModelFromBlockState(var13, var17, var12), var13, var12, var16, true);
         } else if (tileEntityPiston.shouldPistonHeadBeRendered() && !tileEntityPiston.isExtending()) {
            BlockPistonExtension.EnumPistonType var18 = var14 == Blocks.sticky_piston
               ? BlockPistonExtension.EnumPistonType.STICKY
               : BlockPistonExtension.EnumPistonType.DEFAULT;
            IBlockState var19 = Blocks.piston_head
               .getDefaultState()
               .withProperty(BlockPistonExtension.TYPE, var18)
               .withProperty(BlockPistonExtension.FACING, var13.getValue(BlockPistonBase.FACING));
            var19 = var19.withProperty(BlockPistonExtension.SHORT, tileEntityPiston.getProgress(float5) >= 0.5F);
            this.blockRenderer
               .getBlockModelRenderer()
               .renderModel(var17, this.blockRenderer.getModelFromBlockState(var19, var17, var12), var19, var12, var16, true);
            var16.setTranslation((float)double2 - var12.getX(), (float)double3 - var12.getY(), (float)double4 - var12.getZ());
            var13.withProperty(BlockPistonBase.EXTENDED, Boolean.TRUE);
            this.blockRenderer
               .getBlockModelRenderer()
               .renderModel(var17, this.blockRenderer.getModelFromBlockState(var13, var17, var12), var13, var12, var16, true);
         } else {
            this.blockRenderer
               .getBlockModelRenderer()
               .renderModel(var17, this.blockRenderer.getModelFromBlockState(var13, var17, var12), var13, var12, var16, false);
         }

         var16.setTranslation(0.0, 0.0, 0.0);
         var15.draw();
         RenderHelper.enableStandardItemLighting();
      }
   }
}
