package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.entity.RenderCow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityCow;

public class ModelAdapterCow extends ModelAdapterQuadruped {
   public ModelAdapterCow() {
      super(EntityCow.class, "cow", 0.7F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelCow();
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      return new RenderCow(var3, modelBase, float2);
   }
}
