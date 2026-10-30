package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.monster.EntityZombie;

public class ModelAdapterZombie extends ModelAdapterBiped {
   public ModelAdapterZombie() {
      super(EntityZombie.class, "zombie", 0.5F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelZombie();
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      RenderZombie var4 = new RenderZombie(var3);
      Render.setModelBipedMain(var4, (ModelBiped)modelBase);
      var4.mainModel = modelBase;
      var4.shadowSize = float2;
      return var4;
   }
}
