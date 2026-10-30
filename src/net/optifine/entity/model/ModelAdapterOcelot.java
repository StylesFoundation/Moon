package net.optifine.entity.model;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelOcelot;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderOcelot;
import net.minecraft.entity.passive.EntityOcelot;

public class ModelAdapterOcelot extends ModelAdapter {
   private static Map<String, Integer> mapPartFields = null;

   public ModelAdapterOcelot() {
      super(EntityOcelot.class, "ocelot", 0.4F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelOcelot();
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelOcelot var6) {
         Map var7 = getMapPartFields();
         if (var7.containsKey(string)) {
            int var8 = (Integer)var7.get(string);
            Object[] var9 = Arrays.stream(ModelOcelot.class.getDeclaredFields()).filter(field -> field.getType() == ModelRenderer.class).toArray();

            try {
               return (ModelRenderer)((Field)var9[var8]).get(var6);
            } catch (IllegalAccessException var10) {
               return null;
            }
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"back_left_leg", "back_right_leg", "front_left_leg", "front_right_leg", "tail", "tail2", "head", "body"};
   }

   private static Map<String, Integer> getMapPartFields() {
      if (mapPartFields == null) {
         mapPartFields = new HashMap<>();
         mapPartFields.put("back_left_leg", 0);
         mapPartFields.put("back_right_leg", 1);
         mapPartFields.put("front_left_leg", 2);
         mapPartFields.put("front_right_leg", 3);
         mapPartFields.put("tail", 4);
         mapPartFields.put("tail2", 5);
         mapPartFields.put("head", 6);
         mapPartFields.put("body", 7);
      }

      return mapPartFields;
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      return new RenderOcelot(var3, modelBase, float2);
   }
}
