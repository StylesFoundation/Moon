package net.minecraft.client.renderer.tileentity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBanner;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.LayeredColorMaskTexture;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class TileEntityBannerRenderer extends TileEntitySpecialRenderer<TileEntityBanner> {
   private static final Map<String, TileEntityBannerRenderer.TimedBannerTexture> DESIGNS = Maps.newHashMap();
   private static final ResourceLocation BANNERTEXTURES = new ResourceLocation("textures/entity/banner_base.png");
   public ModelBanner bannerModel = new ModelBanner();

   public void renderTileEntityAt(TileEntityBanner tileEntityBanner, double double2, double double3, double double4, float float5, int integer) {
      boolean var12 = tileEntityBanner.getWorld() != null;
      boolean var13 = !var12 || tileEntityBanner.getBlockType() == Blocks.standing_banner;
      int var14 = var12 ? tileEntityBanner.getBlockMetadata() : 0;
      long var15 = var12 ? tileEntityBanner.getWorld().getTotalWorldTime() : 0L;
      GlStateManager.pushMatrix();
      if (var13) {
         GlStateManager.translate((float)double2 + 0.5F, (float)double3 + 0.5F, (float)double4 + 0.5F);
         GlStateManager.rotate(-(var14 * 360 / 16.0F), 0.0F, 1.0F, 0.0F);
         this.bannerModel.bannerStand.showModel = true;
      } else {
         float var18 = 0.0F;
         if (var14 == 2) {
            var18 = 180.0F;
         }

         if (var14 == 4) {
            var18 = 90.0F;
         }

         if (var14 == 5) {
            var18 = -90.0F;
         }

         GlStateManager.translate((float)double2 + 0.5F, (float)double3 - 0.16666667F, (float)double4 + 0.5F);
         GlStateManager.rotate(-var18, 0.0F, 1.0F, 0.0F);
         GlStateManager.translate(0.0F, -0.3125F, -0.4375F);
         this.bannerModel.bannerStand.showModel = false;
      }

      BlockPos var21 = tileEntityBanner.getPos();
      float var19 = var21.getX() * 7 + var21.getY() * 9 + var21.getZ() * 13 + (float)var15 + float5;
      this.bannerModel.bannerSlate.rotateAngleX = (-0.0125F + 0.01F * MathHelper.cos(var19 * (float) Math.PI * 0.02F)) * (float) Math.PI;
      GlStateManager.enableRescaleNormal();
      ResourceLocation var20 = this.func_178463_a(tileEntityBanner);
      if (var20 != null) {
         this.bindTexture(var20);
         GlStateManager.pushMatrix();
         GlStateManager.scale(0.6666667F, -0.6666667F, -0.6666667F);
         this.bannerModel.renderBanner();
         GlStateManager.popMatrix();
      }

      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
   }

   private ResourceLocation func_178463_a(TileEntityBanner tileEntityBanner) {
      String var4 = tileEntityBanner.getPatternResourceLocation();
      if (var4.isEmpty()) {
         return null;
      }

      TileEntityBannerRenderer.TimedBannerTexture var5 = DESIGNS.get(var4);
      if (var5 == null) {
         if (DESIGNS.size() >= 256) {
            long var6 = System.currentTimeMillis();
            Iterator var8 = DESIGNS.keySet().iterator();

            while (var8.hasNext()) {
               String var9 = (String)var8.next();
               TileEntityBannerRenderer.TimedBannerTexture var10 = DESIGNS.get(var9);
               if (var6 - var10.systemTime > 60000L) {
                  Minecraft.getMinecraft().getTextureManager().deleteTexture(var10.bannerTexture);
                  var8.remove();
               }
            }

            if (DESIGNS.size() >= 256) {
               return null;
            }
         }

         List<TileEntityBanner.EnumBannerPattern> var11 = tileEntityBanner.getPatternList();
         List<EnumDyeColor> var7 = tileEntityBanner.getColorList();
         List<String> var12 = Lists.newArrayList();

         for (TileEntityBanner.EnumBannerPattern var14 : var11) {
            var12.add("textures/entity/banner/" + var14.getPatternName() + ".png");
         }

         var5 = new TileEntityBannerRenderer.TimedBannerTexture();
         var5.bannerTexture = new ResourceLocation(var4);
         Minecraft.getMinecraft().getTextureManager().loadTexture(var5.bannerTexture, new LayeredColorMaskTexture(BANNERTEXTURES, var12, var7));
         DESIGNS.put(var4, var5);
      }

      var5.systemTime = System.currentTimeMillis();
      return var5.bannerTexture;
   }

   static class TimedBannerTexture {
      public long systemTime;
      public ResourceLocation bannerTexture;

      private TimedBannerTexture() {
      }
   }
}
