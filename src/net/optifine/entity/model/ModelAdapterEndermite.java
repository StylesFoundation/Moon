package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderMite;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderEndermite;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.src.Config;

public class ModelAdapterEndermite extends ModelAdapter {
   public ModelAdapterEndermite() {
      super(EntityEndermite.class, "endermite", 0.3F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelEnderMite();
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (!(modelBase instanceof ModelEnderMite var6)) {
         return null;
      } else {
         String var7 = "body";
         if (string.startsWith(var7)) {
            ModelRenderer[] var8 = var6.field_178713_d;
            if (var8 == null) {
               return null;
            }

            int var10 = Config.parseInt(string.substring(4), -1);
            var10--;
            return var10 >= 0 && var10 < var8.length ? var8[var10] : null;
         } else {
            return null;
         }
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body1", "body2", "body3", "body4"};
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      RenderEndermite var4 = new RenderEndermite(var3);
      var4.mainModel = modelBase;
      var4.shadowSize = float2;
      return var4;
   }
}
