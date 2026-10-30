package net.optifine.entity.model;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderHorse;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityHorse;

public class ModelAdapterHorse extends ModelAdapter {
   private static Map<String, Integer> mapPartFields = null;

   public ModelAdapterHorse() {
      super(EntityHorse.class, "horse", 0.75F);
   }

   protected ModelAdapterHorse(Class class1, String string, float float3) {
      super(class1, string, float3);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelHorse();
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelHorse var6) {
         Map var7 = getMapPartFields();
         if (var7.containsKey(string)) {
            int var8 = (Integer)var7.get(string);
            Object[] var9 = Arrays.stream(ModelHorse.class.getDeclaredFields()).filter(field -> field.getType() == ModelRenderer.class).toArray();

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
      return new String[]{
         "head",
         "upper_mouth",
         "lower_mouth",
         "horse_left_ear",
         "horse_right_ear",
         "mule_left_ear",
         "mule_right_ear",
         "neck",
         "horse_face_ropes",
         "mane",
         "body",
         "tail_base",
         "tail_middle",
         "tail_tip",
         "back_left_leg",
         "back_left_shin",
         "back_left_hoof",
         "back_right_leg",
         "back_right_shin",
         "back_right_hoof",
         "front_left_leg",
         "front_left_shin",
         "front_left_hoof",
         "front_right_leg",
         "front_right_shin",
         "front_right_hoof",
         "mule_left_chest",
         "mule_right_chest",
         "horse_saddle_bottom",
         "horse_saddle_front",
         "horse_saddle_back",
         "horse_left_saddle_rope",
         "horse_left_saddle_metal",
         "horse_right_saddle_rope",
         "horse_right_saddle_metal",
         "horse_left_face_metal",
         "horse_right_face_metal",
         "horse_left_rein",
         "horse_right_rein"
      };
   }

   private static Map<String, Integer> getMapPartFields() {
      if (mapPartFields == null) {
         mapPartFields = new HashMap<>();
         mapPartFields.put("head", 0);
         mapPartFields.put("upper_mouth", 1);
         mapPartFields.put("lower_mouth", 2);
         mapPartFields.put("horse_left_ear", 3);
         mapPartFields.put("horse_right_ear", 4);
         mapPartFields.put("mule_left_ear", 5);
         mapPartFields.put("mule_right_ear", 6);
         mapPartFields.put("neck", 7);
         mapPartFields.put("horse_face_ropes", 8);
         mapPartFields.put("mane", 9);
         mapPartFields.put("body", 10);
         mapPartFields.put("tail_base", 11);
         mapPartFields.put("tail_middle", 12);
         mapPartFields.put("tail_tip", 13);
         mapPartFields.put("back_left_leg", 14);
         mapPartFields.put("back_left_shin", 15);
         mapPartFields.put("back_left_hoof", 16);
         mapPartFields.put("back_right_leg", 17);
         mapPartFields.put("back_right_shin", 18);
         mapPartFields.put("back_right_hoof", 19);
         mapPartFields.put("front_left_leg", 20);
         mapPartFields.put("front_left_shin", 21);
         mapPartFields.put("front_left_hoof", 22);
         mapPartFields.put("front_right_leg", 23);
         mapPartFields.put("front_right_shin", 24);
         mapPartFields.put("front_right_hoof", 25);
         mapPartFields.put("mule_left_chest", 26);
         mapPartFields.put("mule_right_chest", 27);
         mapPartFields.put("horse_saddle_bottom", 28);
         mapPartFields.put("horse_saddle_front", 29);
         mapPartFields.put("horse_saddle_back", 30);
         mapPartFields.put("horse_left_saddle_rope", 31);
         mapPartFields.put("horse_left_saddle_metal", 32);
         mapPartFields.put("horse_right_saddle_rope", 33);
         mapPartFields.put("horse_right_saddle_metal", 34);
         mapPartFields.put("horse_left_face_metal", 35);
         mapPartFields.put("horse_right_face_metal", 36);
         mapPartFields.put("horse_left_rein", 37);
         mapPartFields.put("horse_right_rein", 38);
      }

      return mapPartFields;
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      return new RenderHorse(var3, (ModelHorse)modelBase, float2);
   }
}
