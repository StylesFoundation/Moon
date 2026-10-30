package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderBoat;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityBoat;

public class ModelAdapterBoat extends ModelAdapter {
   public ModelAdapterBoat() {
      super(EntityBoat.class, "boat", 0.5F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelBoat();
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelBoat var6) {
         return string.equals("bottom")
            ? var6.boatSides[0]
            : (
               string.equals("back")
                  ? var6.boatSides[1]
                  : (
                     string.equals("front")
                        ? var6.boatSides[2]
                        : (string.equals("right") ? var6.boatSides[3] : (string.equals("left") ? var6.boatSides[4] : null))
                  )
            );
      } else {
         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"bottom", "back", "front", "right", "left"};
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      RenderBoat var4 = new RenderBoat(var3);
      var4.i = modelBase;
      var4.shadowSize = float2;
      return var4;
   }
}
