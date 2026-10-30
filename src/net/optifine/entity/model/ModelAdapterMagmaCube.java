package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelMagmaCube;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderMagmaCube;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.src.Config;

public class ModelAdapterMagmaCube extends ModelAdapter {
   public ModelAdapterMagmaCube() {
      super(EntityMagmaCube.class, "magma_cube", 0.5F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelMagmaCube();
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelMagmaCube var6) {
         if (string.equals("core")) {
            return var6.core;
         }

         String var7 = "segment";
         if (string.startsWith(var7)) {
            ModelRenderer[] var8 = var6.segments;
            if (var8 == null) {
               return null;
            }

            int var10 = Config.parseInt(string.substring(7), -1);
            var10--;
            return var10 >= 0 && var10 < var8.length ? var8[var10] : null;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"core", "segment1", "segment2", "segment3", "segment4", "segment5", "segment6", "segment7", "segment8"};
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      RenderMagmaCube var4 = new RenderMagmaCube(var3);
      var4.mainModel = modelBase;
      var4.shadowSize = float2;
      return var4;
   }
}
