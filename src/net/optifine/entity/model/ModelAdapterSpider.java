package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.monster.EntitySpider;

public class ModelAdapterSpider extends ModelAdapter {
   public ModelAdapterSpider() {
      super(EntitySpider.class, "spider", 1.0F);
   }

   protected ModelAdapterSpider(Class class1, String string, float float3) {
      super(class1, string, float3);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelSpider();
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelSpider var6) {
         return string.equals("head")
            ? var6.spiderHead
            : (
               string.equals("neck")
                  ? var6.spiderNeck
                  : (
                     string.equals("body")
                        ? var6.spiderBody
                        : (
                           string.equals("leg1")
                              ? var6.spiderLeg1
                              : (
                                 string.equals("leg2")
                                    ? var6.spiderLeg2
                                    : (
                                       string.equals("leg3")
                                          ? var6.spiderLeg3
                                          : (
                                             string.equals("leg4")
                                                ? var6.spiderLeg4
                                                : (
                                                   string.equals("leg5")
                                                      ? var6.spiderLeg5
                                                      : (
                                                         string.equals("leg6")
                                                            ? var6.spiderLeg6
                                                            : (string.equals("leg7") ? var6.spiderLeg7 : (string.equals("leg8") ? var6.spiderLeg8 : null))
                                                      )
                                                )
                                          )
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
      return new String[]{"head", "neck", "body", "leg1", "leg2", "leg3", "leg4", "leg5", "leg6", "leg7", "leg8"};
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      RenderSpider var4 = new RenderSpider(var3);
      var4.mainModel = modelBase;
      var4.shadowSize = float2;
      return var4;
   }
}
