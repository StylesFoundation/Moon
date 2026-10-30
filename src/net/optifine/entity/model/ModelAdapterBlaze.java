package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderBlaze;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.src.Config;

public class ModelAdapterBlaze extends ModelAdapter {
   public ModelAdapterBlaze() {
      super(EntityBlaze.class, "blaze", 0.5F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelBlaze();
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelBlaze var6) {
         if (string.equals("head")) {
            return var6.blazeHead;
         }

         String var7 = "stick";
         if (string.startsWith(var7)) {
            ModelRenderer[] var8 = var6.blazeSticks;
            if (var8 == null) {
               return null;
            }

            int var10 = Config.parseInt(string.substring(5), -1);
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
      return new String[]{"head", "stick1", "stick2", "stick3", "stick4", "stick5", "stick6", "stick7", "stick8", "stick9", "stick10", "stick11", "stick12"};
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      RenderBlaze var4 = new RenderBlaze(var3);
      var4.mainModel = modelBase;
      var4.shadowSize = float2;
      return var4;
   }
}
