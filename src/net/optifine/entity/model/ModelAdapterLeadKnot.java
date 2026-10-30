package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelLeashKnot;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderLeashKnot;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLeashKnot;

public class ModelAdapterLeadKnot extends ModelAdapter {
   public ModelAdapterLeadKnot() {
      super(EntityLeashKnot.class, "lead_knot", 0.0F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelLeashKnot();
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelLeashKnot var6) {
         return string.equals("knot") ? var6.field_110723_a : null;
      } else {
         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"knot"};
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      RenderLeashKnot var4 = new RenderLeashKnot(var3);
      var4.i = (ModelLeashKnot)modelBase;
      var4.shadowSize = float2;
      return var4;
   }
}
