package net.optifine.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntitySkull;

public class ModelAdapterHeadSkeleton extends ModelAdapter {
   public ModelAdapterHeadSkeleton() {
      super(TileEntitySkull.class, "head_skeleton", 0.0F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelSkeletonHead(0, 0, 64, 32);
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelSkeletonHead var6) {
         return string.equals("head") ? var6.skeletonHead : null;
      } else {
         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head"};
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      TileEntityRendererDispatcher var6 = TileEntityRendererDispatcher.instance;
      TileEntitySpecialRenderer var7 = var6.getSpecialRendererByClass(TileEntitySkull.class);
      if (!(var7 instanceof TileEntitySkullRenderer)) {
         return null;
      }

      if (var7.getEntityClass() == null) {
         var7 = new TileEntitySkullRenderer();
         var7.setRendererDispatcher(var6);
      }

      ((TileEntitySkullRenderer)var7).l = (ModelSkeletonHead)modelBase;
      return var7;
   }
}
