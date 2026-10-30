package net.optifine.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntitySign;

public class ModelAdapterSign extends ModelAdapter {
   public ModelAdapterSign() {
      super(TileEntitySign.class, "sign", 0.0F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelSign();
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelSign var6) {
         return string.equals("board") ? var6.signBoard : (string.equals("stick") ? var6.signStick : null);
      } else {
         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"board", "stick"};
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      TileEntityRendererDispatcher var6 = TileEntityRendererDispatcher.instance;
      TileEntitySpecialRenderer var7 = var6.getSpecialRendererByClass(TileEntitySign.class);
      if (!(var7 instanceof TileEntitySignRenderer)) {
         return null;
      }

      if (var7.getEntityClass() == null) {
         var7 = new TileEntitySignRenderer();
         var7.setRendererDispatcher(var6);
      }

      ((TileEntitySignRenderer)var7).g = (ModelSign)modelBase;
      return var7;
   }
}
