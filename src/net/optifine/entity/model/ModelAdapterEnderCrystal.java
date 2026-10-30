package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.RenderEnderCrystal;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.src.Config;

public class ModelAdapterEnderCrystal extends ModelAdapter {
   public ModelAdapterEnderCrystal() {
      this("end_crystal");
   }

   protected ModelAdapterEnderCrystal(String string) {
      super(EntityEnderCrystal.class, string, 0.5F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelEnderCrystal(0.0F, true);
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelEnderCrystal var6) {
         return string.equals("cube") ? var6.cube : (string.equals("glass") ? var6.glass : (string.equals("base") ? var6.base : null));
      } else {
         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"cube", "glass", "base"};
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var6 = Minecraft.getMinecraft().getRenderManager();
      Render var7 = var6.getEntityRenderMap().get(EntityEnderCrystal.class);
      if (var7 instanceof RenderEnderCrystal var8) {
         var8.i = modelBase;
         var8.shadowSize = float2;
         return var8;
      } else {
         Config.Method252("Not an instance of RenderEnderCrystal: " + var7);
         return null;
      }
   }
}
