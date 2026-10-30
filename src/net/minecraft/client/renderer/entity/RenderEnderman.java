package net.minecraft.client.renderer.entity;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelEnderman;
import net.minecraft.client.renderer.entity.layers.LayerEndermanEyes;
import net.minecraft.client.renderer.entity.layers.LayerHeldBlock;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.ResourceLocation;

public class RenderEnderman extends RenderLiving<EntityEnderman> {
   private static final ResourceLocation endermanTextures = new ResourceLocation("textures/entity/enderman/enderman.png");
   private final ModelEnderman endermanModel;
   private final Random rnd = new Random();

   public RenderEnderman(RenderManager renderManager) {
      super(renderManager, new ModelEnderman(0.0F), 0.5F);
      this.endermanModel = (ModelEnderman)super.mainModel;
      this.addLayer(new LayerEndermanEyes(this));
      this.addLayer(new LayerHeldBlock(this));
   }

   public void doRender(EntityEnderman entityEnderman, double double2, double double3, double double4, float float5, float float6) {
      this.endermanModel.isCarrying = entityEnderman.getHeldBlockState().getBlock().getMaterial() != Material.air;
      this.endermanModel.isAttacking = entityEnderman.isScreaming();
      if (entityEnderman.isScreaming()) {
         double2 += this.rnd.nextGaussian() * 0.02;
         double4 += this.rnd.nextGaussian() * 0.02;
      }

      super.doRender(entityEnderman, double2, double3, double4, float5, float6);
   }

   protected ResourceLocation getEntityTexture(EntityEnderman entityEnderman) {
      return endermanTextures;
   }
}
