package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderman;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.monster.EntityEnderman;

public class ModelAdapterEnderman extends ModelAdapterBiped {
   public ModelAdapterEnderman() {
      super(EntityEnderman.class, "enderman", 0.5F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelEnderman(0.0F);
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      RenderEnderman var4 = new RenderEnderman(var3);
      var4.mainModel = modelBase;
      var4.shadowSize = float2;
      return var4;
   }
}
