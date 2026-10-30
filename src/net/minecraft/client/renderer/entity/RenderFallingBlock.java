package net.minecraft.client.renderer.entity;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RenderFallingBlock extends Render<EntityFallingBlock> {
   public RenderFallingBlock(RenderManager renderManager) {
      super(renderManager);
      this.shadowSize = 0.5F;
   }

   public void doRender(EntityFallingBlock entityFallingBlock, double double2, double double3, double double4, float float5, float float6) {
      if (entityFallingBlock.getBlock() != null) {
         this.bindTexture(TextureMap.locationBlocksTexture);
         IBlockState var12 = entityFallingBlock.getBlock();
         Block var13 = var12.getBlock();
         BlockPos var14 = new BlockPos(entityFallingBlock);
         World var15 = entityFallingBlock.getWorldObj();
         if (var12 != var15.getBlockState(var14) && var13.getRenderType() != -1 && var13.getRenderType() == 3) {
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)double2, (float)double3, (float)double4);
            GlStateManager.disableLighting();
            Tessellator var16 = Tessellator.getInstance();
            WorldRenderer var17 = var16.getWorldRenderer();
            var17.begin(7, DefaultVertexFormats.BLOCK);
            int var18 = var14.getX();
            int var19 = var14.getY();
            int var20 = var14.getZ();
            var17.setTranslation(-var18 - 0.5F, -var19, -var20 - 0.5F);
            BlockRendererDispatcher var21 = Minecraft.getMinecraft().getBlockRendererDispatcher();
            IBakedModel var22 = var21.getModelFromBlockState(var12, var15, null);
            var21.getBlockModelRenderer().renderModel(var15, var22, var12, var14, var17, false);
            var17.setTranslation(0.0, 0.0, 0.0);
            var16.draw();
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
            super.doRender(entityFallingBlock, double2, double3, double4, float5, float6);
         }
      }
   }

   protected ResourceLocation getEntityTexture(EntityFallingBlock entityFallingBlock) {
      return TextureMap.locationBlocksTexture;
   }
}
