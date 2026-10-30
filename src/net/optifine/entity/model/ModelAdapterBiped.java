package net.optifine.entity.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

public abstract class ModelAdapterBiped extends ModelAdapter {
   public ModelAdapterBiped(Class class1, String string, float float3) {
      super(class1, string, float3);
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelBiped var6) {
         return string.equals("head")
            ? var6.bipedHead
            : (
               string.equals("headwear")
                  ? var6.bipedHeadwear
                  : (
                     string.equals("body")
                        ? var6.bipedBody
                        : (
                           string.equals("left_arm")
                              ? var6.bipedLeftArm
                              : (
                                 string.equals("right_arm")
                                    ? var6.bipedRightArm
                                    : (string.equals("left_leg") ? var6.bipedLeftLeg : (string.equals("right_leg") ? var6.bipedRightLeg : null))
                              )
                        )
                  )
            );
      } else {
         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "headwear", "body", "left_arm", "right_arm", "left_leg", "right_leg"};
   }
}
