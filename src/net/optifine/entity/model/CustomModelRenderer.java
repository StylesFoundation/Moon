package net.optifine.entity.model;

import net.minecraft.client.model.ModelRenderer;
import net.optifine.entity.model.anim.ModelUpdater;

public class CustomModelRenderer {
   private final String modelPart;
   private final boolean attach;
   private final ModelRenderer modelRenderer;
   private final ModelUpdater modelUpdater;

   public CustomModelRenderer(String string, boolean boolean2, ModelRenderer modelRenderer, ModelUpdater modelUpdater) {
      this.modelPart = string;
      this.attach = boolean2;
      this.modelRenderer = modelRenderer;
      this.modelUpdater = modelUpdater;
   }

   public ModelRenderer getModelRenderer() {
      return this.modelRenderer;
   }

   public String getModelPart() {
      return this.modelPart;
   }

   public boolean isAttach() {
      return this.attach;
   }

   public ModelUpdater getModelUpdater() {
      return this.modelUpdater;
   }
}
