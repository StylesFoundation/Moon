package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityCreeper;

public class ModelAdapterCreeper extends ModelAdapter {
   public ModelAdapterCreeper() {
      super(EntityCreeper.class, "creeper", 0.5F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelCreeper();
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelCreeper var6) {
         return string.equals("head")
            ? var6.head
            : (
               string.equals("armor")
                  ? var6.creeperArmor
                  : (
                     string.equals("body")
                        ? var6.body
                        : (
                           string.equals("leg1")
                              ? var6.leg1
                              : (string.equals("leg2") ? var6.leg2 : (string.equals("leg3") ? var6.leg3 : (string.equals("leg4") ? var6.leg4 : null)))
                        )
                  )
            );
      } else {
         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "armor", "body", "leg1", "leg2", "leg3", "leg4"};
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      RenderCreeper var4 = new RenderCreeper(var3);
      var4.mainModel = modelBase;
      var4.shadowSize = float2;
      return var4;
   }
}
