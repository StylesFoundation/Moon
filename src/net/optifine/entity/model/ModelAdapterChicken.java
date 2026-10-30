package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityChicken;

public class ModelAdapterChicken extends ModelAdapter {
   public ModelAdapterChicken() {
      super(EntityChicken.class, "chicken", 0.3F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelChicken();
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelChicken var6) {
         return string.equals("head")
            ? var6.head
            : (
               string.equals("body")
                  ? var6.body
                  : (
                     string.equals("right_leg")
                        ? var6.rightLeg
                        : (
                           string.equals("left_leg")
                              ? var6.leftLeg
                              : (
                                 string.equals("right_wing")
                                    ? var6.rightWing
                                    : (
                                       string.equals("left_wing")
                                          ? var6.leftWing
                                          : (string.equals("bill") ? var6.bill : (string.equals("chin") ? var6.chin : null))
                                    )
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
      return new String[]{"head", "body", "right_leg", "left_leg", "right_wing", "left_wing", "bill", "chin"};
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      return new RenderChicken(var3, modelBase, float2);
   }
}
