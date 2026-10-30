package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSquid;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSquid;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.src.Config;

public class ModelAdapterSquid extends ModelAdapter {
   public ModelAdapterSquid() {
      super(EntitySquid.class, "squid", 0.7F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelSquid();
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (modelBase instanceof ModelSquid var6) {
         if (string.equals("body")) {
            return var6.squidBody;
         }

         String var7 = "tentacle";
         if (string.startsWith(var7)) {
            ModelRenderer[] var8 = var6.squidTentacles;
            if (var8 == null) {
               return null;
            }

            int var10 = Config.parseInt(string.substring(8), -1);
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
      return new String[]{"body", "tentacle1", "tentacle2", "tentacle3", "tentacle4", "tentacle5", "tentacle6", "tentacle7", "tentacle8"};
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      return new RenderSquid(var3, modelBase, float2);
   }
}
