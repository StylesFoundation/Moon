package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSnowMan;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowMan;
import net.minecraft.entity.monster.EntitySnowman;

public class ModelAdapterSnowman extends ModelAdapter {
   public ModelAdapterSnowman() {
      super(EntitySnowman.class, "snow_golem", 0.5F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelSnowMan();
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelSnowMan var6) {
         return string.equals("body")
            ? var6.body
            : (
               string.equals("body_bottom")
                  ? var6.bottomBody
                  : (string.equals("head") ? var6.head : (string.equals("left_hand") ? var6.leftHand : (string.equals("right_hand") ? var6.rightHand : null)))
            );
      } else {
         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body", "body_bottom", "head", "right_hand", "left_hand"};
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      RenderSnowMan var4 = new RenderSnowMan(var3);
      var4.mainModel = modelBase;
      var4.shadowSize = float2;
      return var4;
   }
}
