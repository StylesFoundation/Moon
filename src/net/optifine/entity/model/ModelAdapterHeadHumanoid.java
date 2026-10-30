package net.optifine.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelHumanoidHead;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntitySkull;

public class ModelAdapterHeadHumanoid extends ModelAdapter {
   public ModelAdapterHeadHumanoid() {
      super(TileEntitySkull.class, "head_humanoid", 0.0F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelHumanoidHead();
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelHumanoidHead var6) {
         return string.equals("head") ? var6.skeletonHead : (string.equals("head2") ? var6.head : null);
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
