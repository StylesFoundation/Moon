package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelIronGolem;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderIronGolem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityIronGolem;

public class ModelAdapterIronGolem extends ModelAdapter {
   public ModelAdapterIronGolem() {
      super(EntityIronGolem.class, "iron_golem", 0.5F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelIronGolem();
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelIronGolem var6) {
         return string.equals("head")
            ? var6.ironGolemHead
            : (
               string.equals("body")
                  ? var6.ironGolemBody
                  : (
                     string.equals("left_arm")
                        ? var6.ironGolemLeftArm
                        : (
                           string.equals("right_arm")
                              ? var6.ironGolemRightArm
                              : (string.equals("left_leg") ? var6.ironGolemLeftLeg : (string.equals("right_leg") ? var6.ironGolemRightLeg : null))
                        )
                  )
            );
      } else {
         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "body", "right_arm", "left_arm", "left_leg", "right_leg"};
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      RenderIronGolem var4 = new RenderIronGolem(var3);
      var4.mainModel = modelBase;
      var4.shadowSize = float2;
      return var4;
   }
}
