package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.RenderWitherSkull;
import net.minecraft.entity.projectile.EntityWitherSkull;

public class ModelAdapterWitherSkull extends ModelAdapter {
   public ModelAdapterWitherSkull() {
      super(EntityWitherSkull.class, "wither_skull", 0.0F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelSkeletonHead();
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
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      RenderWitherSkull var4 = new RenderWitherSkull(var3);
      var4.j = (ModelSkeletonHead)modelBase;
      var4.shadowSize = float2;
      return var4;
   }
}
