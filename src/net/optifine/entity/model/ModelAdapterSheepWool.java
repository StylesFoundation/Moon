package net.optifine.entity.model;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.model.ModelSheep2;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerSheepWool;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.src.Config;

public class ModelAdapterSheepWool extends ModelAdapterQuadruped {
   public ModelAdapterSheepWool() {
      super(EntitySheep.class, "sheep_wool", 0.7F);
   }

   @Override
   public ModelBase makeModel() {
      return new ModelSheep1();
   }

   @Override
   public IEntityRenderer makeEntityRender(ModelBase modelBase, float float2) {
      RenderManager var6 = Minecraft.getMinecraft().getRenderManager();
      Render var7 = var6.getEntityRenderMap().get(EntitySheep.class);
      if (var7 instanceof RenderSheep var8) {
         if (var7.getEntityClass() == null) {
            new RenderSheep(var6, new ModelSheep2(), 0.7F);
         }

         Iterator var10 = var8.getLayerRenderers().iterator();

         while (var10.hasNext()) {
            LayerRenderer var11 = (LayerRenderer)var10.next();
            if (var11 instanceof LayerSheepWool) {
               var10.remove();
            }
         }

         LayerSheepWool var12 = new LayerSheepWool(var8);
         var12.sheepModel = (ModelSheep1)modelBase;
         var8.addLayer(var12);
         return var8;
      } else {
         Config.Method252("Not a RenderSheep: " + var7);
         return null;
      }
   }
}
