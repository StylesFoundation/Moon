package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelArmorStand;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.src.Config;

public class ModelAdapterArmorStand extends ModelAdapterBiped {
   public ModelAdapterArmorStand() {
      super(EntityArmorStand.class, "armor_stand", 0.0F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelArmorStand();
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelArmorStand var6) {
         return string.equals("right")
            ? var6.standRightSide
            : (
               string.equals("left")
                  ? var6.standLeftSide
                  : (string.equals("waist") ? var6.standWaist : (string.equals("base") ? var6.standBase : super.getModelRenderer(var6, string)))
            );
      } else {
         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return (String[])Config.addObjectsToArray(super.getModelRendererNames(), new String[]{"right", "left", "waist", "base"});
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      ArmorStandRenderer var4 = new ArmorStandRenderer(var3);
      var4.mainModel = modelBase;
      var4.shadowSize = float2;
      return var4;
   }
}
