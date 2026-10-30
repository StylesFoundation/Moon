package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelGuardian;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderGuardian;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.src.Config;

public class ModelAdapterGuardian extends ModelAdapter {
   public ModelAdapterGuardian() {
      super(EntityGuardian.class, "guardian", 0.5F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelGuardian();
   }

   @Override
   public ModelRenderer getModelRenderer(ModelBase modelBase, String string) {
      if (!(modelBase instanceof ModelGuardian var6)) {
         return null;
      } else {
         if (string.equals("body")) {
            return var6.guardianBody;
         }

         if (string.equals("eye")) {
            return var6.guardianEye;
         }

         String var7 = "spine";
         if (string.startsWith(var7)) {
            ModelRenderer[] var12 = var6.guardianSpines;
            if (var12 == null) {
               return null;
            }

            int var10 = Config.parseInt(string.substring(5), -1);
            var10--;
            return var10 >= 0 && var10 < var12.length ? var12[var10] : null;
         } else {
            String var8 = "tail";
            if (string.startsWith(var8)) {
               ModelRenderer[] var9 = var6.guardianTail;
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
      return new String[]{
         "body",
         "eye",
         "spine1",
         "spine2",
         "spine3",
         "spine4",
         "spine5",
         "spine6",
         "spine7",
         "spine8",
         "spine9",
         "spine10",
         "spine11",
         "spine12",
         "tail1",
         "tail2",
         "tail3"
      };
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      RenderGuardian var4 = new RenderGuardian(var3);
      var4.mainModel = modelBase;
      var4.shadowSize = float2;
      return var4;
   }
}
