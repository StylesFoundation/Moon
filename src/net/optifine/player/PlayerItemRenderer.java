package net.optifine.player;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;

public class PlayerItemRenderer {
   private int attachTo = 0;
   private ModelRenderer modelRenderer = null;

   public PlayerItemRenderer(int integer, ModelRenderer modelRenderer) {
      this.attachTo = integer;
      this.modelRenderer = modelRenderer;
   }

   public ModelRenderer getModelRenderer() {
      return this.modelRenderer;
   }

   public void render(ModelBiped modelBiped, float float2) {
      ModelRenderer var6 = PlayerItemModel.getAttachModel(modelBiped, this.attachTo);
      if (var6 != null) {
         var6.postRender(float2);
      }

      this.modelRenderer.render(float2);
   }
}
