package net.minecraft.client.renderer.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;

public class RenderTntMinecart extends RenderMinecart<EntityMinecartTNT> {
   public RenderTntMinecart(RenderManager renderManager) {
      super(renderManager);
   }

   protected void func_180560_a(EntityMinecartTNT entityMinecartTNT, float float2, IBlockState iBlockState) {
      int var6 = entityMinecartTNT.getFuseTicks();
      if (var6 > -1 && var6 - float2 + 1.0F < 10.0F) {
         float var7 = MathHelper.clamp_float(1.0F - (var6 - float2 + 1.0F) / 10.0F, 0.0F, 1.0F);
         var7 *= var7;
         var7 *= var7;
         float var8 = 1.0F + var7 * 0.3F;
         GlStateManager.scale(var8, var8, var8);
      }

      super.func_180560_a(entityMinecartTNT, float2, iBlockState);
      if (var6 > -1 && var6 / 5 % 2 == 0) {
         BlockRendererDispatcher var11 = Minecraft.getMinecraft().getBlockRendererDispatcher();
         GlStateManager.disableTexture2D();
         GlStateManager.disableLighting();
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(770, 772);
         GlStateManager.color(1.0F, 1.0F, 1.0F, (1.0F - (var6 - float2 + 1.0F) / 100.0F) * 0.8F);
         GlStateManager.pushMatrix();
         var11.renderBlockBrightness(Blocks.tnt.getDefaultState(), 1.0F);
         GlStateManager.popMatrix();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.disableBlend();
         GlStateManager.enableLighting();
         GlStateManager.enableTexture2D();
      }
   }
}
