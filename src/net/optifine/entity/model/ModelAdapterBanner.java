package net.optifine.entity.model;

import net.minecraft.client.model.ModelBanner;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityBannerRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntityBanner;

public class ModelAdapterBanner extends ModelAdapter {
   public ModelAdapterBanner() {
      super(TileEntityBanner.class, "banner", 0.0F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelBanner();
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelBanner var6) {
         return string.equals("slate") ? var6.bannerSlate : (string.equals("stand") ? var6.bannerStand : (string.equals("top") ? var6.bannerTop : null));
      } else {
         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"slate", "stand", "top"};
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      TileEntityRendererDispatcher var6 = TileEntityRendererDispatcher.instance;
      TileEntitySpecialRenderer var7 = var6.getSpecialRendererByClass(TileEntityBanner.class);
      if (!(var7 instanceof TileEntityBannerRenderer)) {
         return null;
      }

      if (var7.getEntityClass() == null) {
         var7 = new TileEntityBannerRenderer();
         var7.setRendererDispatcher(var6);
      }

      ((TileEntityBannerRenderer)var7).bannerModel = (ModelBanner)modelBase;
      return var7;
   }
}
