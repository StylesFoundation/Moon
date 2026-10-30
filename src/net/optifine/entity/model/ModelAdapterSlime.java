package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.entity.monster.EntitySlime;

public class ModelAdapterSlime extends ModelAdapter {
   public ModelAdapterSlime() {
      super(EntitySlime.class, "slime", 0.25F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelSlime(16);
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelSlime var6) {
         return string.equals("body")
            ? var6.slimeBodies
            : (
               string.equals("left_eye")
                  ? var6.slimeLeftEye
                  : (string.equals("right_eye") ? var6.slimeRightEye : (string.equals("mouth") ? var6.slimeMouth : null))
            );
      } else {
         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body", "left_eye", "right_eye", "mouth"};
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      return new RenderSlime(var3, modelBase, float2);
   }
}
