package net.optifine.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;

public abstract class ModelAdapterQuadruped extends ModelAdapter {
   public ModelAdapterQuadruped(Class class1, String string, float float3) {
      super(class1, string, float3);
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelQuadruped var6) {
         return string.equals("head")
            ? var6.head
            : (
               string.equals("body")
                  ? var6.body
                  : (
                     string.equals("leg1")
                        ? var6.leg1
                        : (string.equals("leg2") ? var6.leg2 : (string.equals("leg3") ? var6.leg3 : (string.equals("leg4") ? var6.leg4 : null)))
                  )
            );
      } else {
         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "body", "leg1", "leg2", "leg3", "leg4"};
   }
}
