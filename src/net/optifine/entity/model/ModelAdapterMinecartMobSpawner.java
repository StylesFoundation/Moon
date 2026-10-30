package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderMinecartMobSpawner;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;

public class ModelAdapterMinecartMobSpawner extends ModelAdapterMinecart {
   public ModelAdapterMinecartMobSpawner() {
      super(EntityMinecartMobSpawner.class, "spawner_minecart", 0.5F);
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var3 = Minecraft.getMinecraft().getRenderManager();
      RenderMinecartMobSpawner var4 = new RenderMinecartMobSpawner(var3);
      var4.i = modelBase;
      var4.shadowSize = float2;
      return var4;
   }
}
