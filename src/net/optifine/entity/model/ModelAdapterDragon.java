package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelDragon;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.boss.EntityDragon;

public class ModelAdapterDragon extends ModelAdapter {
   public ModelAdapterDragon() {
      super(EntityDragon.class, "dragon", 0.5F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelDragon(0.0F);
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelDragon var6) {
         return string.equals("head")
            ? var6.head
            : (
               string.equals("spine")
                  ? var6.spine
                  : (
                     string.equals("jaw")
                        ? var6.jaw
                        : (
                           string.equals("body")
                              ? var6.body
                              : (
                                 string.equals("rear_leg")
                                    ? var6.rearLeg
                                    : (
                                       string.equals("front_leg")
                                          ? var6.frontLeg
                                          : (
                                             string.equals("rear_leg_tip")
                                                ? var6.rearLegTip
                                                : (
                                                   string.equals("front_leg_tip")
                                                      ? var6.frontLegTip
                                                      : (
                                                         string.equals("rear_foot")
                                                            ? var6.rearFoot
                                                            : (
                                                               string.equals("front_foot")
                                                                  ? var6.frontFoot
                                                                  : (string.equals("wing") ? var6.wing : (string.equals("wing_tip") ? var6.wingTip : null))
                                                            )
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
      return new String[]{
         "head", "spine", "jaw", "body", "rear_leg", "front_leg", "rear_leg_tip", "front_leg_tip", "rear_foot", "front_foot", "wing", "wing_tip"
      };
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      RenderDragon var4 = new RenderDragon(var3);
      var4.mainModel = modelBase;
      var4.shadowSize = float2;
      return var4;
   }
}
