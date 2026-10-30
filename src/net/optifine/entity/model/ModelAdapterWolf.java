package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.passive.EntityWolf;

public class ModelAdapterWolf extends ModelAdapter {
   public ModelAdapterWolf() {
      super(EntityWolf.class, "wolf", 0.5F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelWolf();
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelWolf var6) {
         return string.equals("head")
            ? var6.wolfHeadMain
            : (
               string.equals("body")
                  ? var6.wolfBody
                  : (
                     string.equals("leg1")
                        ? var6.wolfLeg1
                        : (
                           string.equals("leg2")
                              ? var6.wolfLeg2
                              : (
                                 string.equals("leg3")
                                    ? var6.wolfLeg3
                                    : (
                                       string.equals("leg4")
                                          ? var6.wolfLeg4
                                          : (string.equals("tail") ? var6.wolfTail : (string.equals("mane") ? var6.wolfMane : null))
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
      return new String[]{"head", "body", "leg1", "leg2", "leg3", "leg4", "tail", "mane"};
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      return new RenderWolf(var3, modelBase, float2);
   }
}
