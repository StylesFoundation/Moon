package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSilverfish;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSilverfish;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.src.Config;

public class ModelAdapterSilverfish extends ModelAdapter {
   public ModelAdapterSilverfish() {
      super(EntitySilverfish.class, "silverfish", 0.3F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelSilverfish();
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (!(modelBase instanceof ModelSilverfish var6)) {
         return null;
      } else {
         String var7 = "body";
         if (string.startsWith(var7)) {
            ModelRenderer[] var12 = var6.silverfishBodyParts;
            if (var12 == null) {
               return null;
            }

            int var10 = Config.parseInt(string.substring(4), -1);
            var10--;
            return var10 >= 0 && var10 < var12.length ? var12[var10] : null;
         } else {
            String var8 = "wing";
            if (string.startsWith(var8)) {
               ModelRenderer[] var9 = var6.silverfishWings;
               if (var9 == null) {
                  return null;
               }

               int var11 = Config.parseInt(string.substring(4), -1);
               var11--;
               return var11 >= 0 && var11 < var9.length ? var9[var11] : null;
            } else {
               return null;
            }
         }
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body1", "body2", "body3", "body4", "body5", "body6", "body7", "wing1", "wing2", "wing3"};
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      RenderSilverfish var4 = new RenderSilverfish(var3);
      var4.mainModel = modelBase;
      var4.shadowSize = float2;
      return var4;
   }
}
