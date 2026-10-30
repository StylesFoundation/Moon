package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelMinecart;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderMinecart;
import net.minecraft.entity.item.EntityMinecart;

public class ModelAdapterMinecart extends ModelAdapter {
   public ModelAdapterMinecart() {
      super(EntityMinecart.class, "minecart", 0.5F);
   }

   protected ModelAdapterMinecart(Class class1, String string, float float3) {
      super(class1, string, float3);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelMinecart();
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelMinecart var6) {
         return string.equals("bottom")
            ? var6.sideModels[0]
            : (
               string.equals("back")
                  ? var6.sideModels[1]
                  : (
                     string.equals("front")
                        ? var6.sideModels[2]
                        : (
                           string.equals("right")
                              ? var6.sideModels[3]
                              : (string.equals("left") ? var6.sideModels[4] : (string.equals("dirt") ? var6.sideModels[5] : null))
                        )
                  )
            );
      } else {
         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"bottom", "back", "front", "right", "left", "dirt"};
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      RenderMinecart var4 = new RenderMinecart(var3);
      var4.i = modelBase;
      var4.shadowSize = float2;
      return var4;
   }
}
