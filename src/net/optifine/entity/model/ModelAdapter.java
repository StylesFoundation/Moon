package net.optifine.entity.model;

import java.util.ArrayList;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public abstract class ModelAdapter {
   private final Class entityClass;
   private final String name;
   private final float shadowSize;
   private String[] aliases;

   public ModelAdapter(Class class1, String string, float float3) {
      this.entityClass = class1;
      this.name = string;
      this.shadowSize = float3;
   }

   public ModelAdapter(Class class1, String string, float float3, String[] arr) {
      this.entityClass = class1;
      this.name = string;
      this.shadowSize = float3;
      this.aliases = arr;
   }

   public Class getEntityClass() {
      return this.entityClass;
   }

   public String getName() {
      return this.name;
   }

   public String[] getAliases() {
      return this.aliases;
   }

   public float getShadowSize() {
      return this.shadowSize;
   }

   public abstract ModelBase makeModel();

   public abstract ModelRenderer getModelRenderer(ModelBase modelBase, String string);

   public abstract String[] getModelRendererNames();

   public abstract IEntityRenderer makeEntityRender(ModelBase modelBase, float float2);

   public ModelRenderer[] getModelRenderers(ModelBase modelBase) {
      String[] var5 = this.getModelRendererNames();
      ArrayList var6 = new ArrayList();

      for (String var10 : var5) {
         ModelRenderer var11 = this.getModelRenderer(modelBase, var10);
         if (var11 != null) {
            var6.add(var11);
         }
      }

      return (ModelRenderer[])var6.toArray(new ModelRenderer[0]);
   }
}
