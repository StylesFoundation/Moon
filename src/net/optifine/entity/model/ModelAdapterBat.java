package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBat;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderBat;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityBat;

public class ModelAdapterBat extends ModelAdapter {
   public ModelAdapterBat() {
      super(EntityBat.class, "bat", 0.25F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelBat();
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelBat var6) {
         return string.equals("head")
            ? var6.batHead
            : (
               string.equals("body")
                  ? var6.batBody
                  : (
                     string.equals("right_wing")
                        ? var6.batRightWing
                        : (
                           string.equals("left_wing")
                              ? var6.batLeftWing
                              : (string.equals("outer_right_wing") ? var6.batOuterRightWing : (string.equals("outer_left_wing") ? var6.batOuterLeftWing : null))
                        )
                  )
            );
      } else {
         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "body", "right_wing", "left_wing", "outer_right_wing", "outer_left_wing"};
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      RenderBat var4 = new RenderBat(var3);
      var4.mainModel = modelBase;
      var4.shadowSize = float2;
      return var4;
   }
}
