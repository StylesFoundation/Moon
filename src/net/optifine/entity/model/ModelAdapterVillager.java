package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderVillager;
import net.minecraft.entity.passive.EntityVillager;

public class ModelAdapterVillager extends ModelAdapter {
   public ModelAdapterVillager() {
      super(EntityVillager.class, "villager", 0.5F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelVillager(0.0F);
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelVillager var6) {
         return string.equals("head")
            ? var6.villagerHead
            : (
               string.equals("body")
                  ? var6.villagerBody
                  : (
                     string.equals("arms")
                        ? var6.villagerArms
                        : (
                           string.equals("left_leg")
                              ? var6.leftVillagerLeg
                              : (string.equals("right_leg") ? var6.rightVillagerLeg : (string.equals("nose") ? var6.villagerNose : null))
                        )
                  )
            );
      } else {
         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "body", "arms", "right_leg", "left_leg", "nose"};
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      RenderVillager var4 = new RenderVillager(var3);
      var4.mainModel = modelBase;
      var4.shadowSize = float2;
      return var4;
   }
}
