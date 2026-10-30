package net.optifine.entity.model.anim;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.src.Config;
import net.minecraft.tileentity.TileEntity;
import net.optifine.entity.model.CustomModelRenderer;
import net.optifine.entity.model.ModelAdapter;
import net.optifine.expr.IExpression;

public class ModelResolver implements IModelResolver {
   private final ModelAdapter modelAdapter;
   private final ModelBase model;
   private final CustomModelRenderer[] customModelRenderers;
   private ModelRenderer thisModelRenderer;
   private ModelRenderer partModelRenderer;
   private final IRenderResolver renderResolver;

   public ModelResolver(ModelAdapter modelAdapter, ModelBase modelBase, CustomModelRenderer[] arr) {
      this.modelAdapter = modelAdapter;
      this.model = modelBase;
      this.customModelRenderers = arr;
      Class var7 = modelAdapter.getEntityClass();
      if (TileEntity.class.isAssignableFrom(var7)) {
         this.renderResolver = new RenderResolverTileEntity();
      } else {
         this.renderResolver = new RenderResolverEntity();
      }
   }

   @Override
   public IExpression getExpression(String string) {
      ModelVariableFloat var5 = this.getModelVariable(string);
      return var5 != null ? var5 : this.renderResolver.getParameter(string);
   }

   @Override
   public ModelRenderer getModelRenderer(String string) {
      if (string == null) {
         return null;
      }

      if (string.contains(":")) {
         String[] var12 = Config.tokenize(string, ":");
         ModelRenderer var13 = this.getModelRenderer(var12[0]);

         for (int var14 = 1; var14 < var12.length; var14++) {
            String var15 = var12[var14];
            ModelRenderer var9 = var13.getChildDeep(var15);
            if (var9 == null) {
               return null;
            }

            var13 = var9;
         }

         return var13;
      } else {
         if (this.thisModelRenderer != null && string.equals("this")) {
            return this.thisModelRenderer;
         }

         if (this.partModelRenderer != null && string.equals("part")) {
            return this.partModelRenderer;
         }

         ModelRenderer var5 = this.modelAdapter.getModelRenderer(this.model, string);
         if (var5 != null) {
            return var5;
         }

         CustomModelRenderer[] var6 = this.customModelRenderers;
         int var7 = var6.length;

         for (int var8 = 0; var8 < var7; var8++) {
            ModelRenderer var10 = var6[var8].getModelRenderer();
            if (string.equals(var10.getId())) {
               return var10;
            }

            ModelRenderer var11 = var10.getChildDeep(string);
            if (var11 != null) {
               return var11;
            }
         }

         return null;
      }
   }

   @Override
   public ModelVariableFloat getModelVariable(String string) {
      String[] var5 = Config.tokenize(string, ".");
      if (var5.length != 2) {
         return null;
      }

      String var6 = var5[0];
      String var7 = var5[1];
      ModelRenderer var8 = this.getModelRenderer(var6);
      if (var8 == null) {
         return null;
      }

      ModelVariableType var9 = ModelVariableType.parse(var7);
      return var9 == null ? null : new ModelVariableFloat(string, var8, var9);
   }

   public void setPartModelRenderer(ModelRenderer modelRenderer) {
      this.partModelRenderer = modelRenderer;
   }

   public void setThisModelRenderer(ModelRenderer modelRenderer) {
      this.thisModelRenderer = modelRenderer;
   }
}
