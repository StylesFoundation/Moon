package net.minecraft.client.renderer.entity;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.model.ModelZombieVillager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerVillagerArmor;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;

public class RenderZombie extends RenderBiped<EntityZombie> {
   private static final ResourceLocation zombieTextures = new ResourceLocation("textures/entity/zombie/zombie.png");
   private static final ResourceLocation zombieVillagerTextures = new ResourceLocation("textures/entity/zombie/zombie_villager.png");
   private final ModelBiped field_82434_o;
   private final ModelZombieVillager zombieVillagerModel;
   private final List<LayerRenderer<EntityZombie>> field_177121_n;
   private final List<LayerRenderer<EntityZombie>> field_177122_o;

   public RenderZombie(RenderManager renderManager) {
      super(renderManager, new ModelZombie(), 0.5F, 1.0F);
      LayerRenderer var2 = this.layerRenderers.get(0);
      this.field_82434_o = this.modelBipedMain;
      this.zombieVillagerModel = new ModelZombieVillager();
      this.addLayer(new LayerHeldItem(this));
      LayerBipedArmor var3 = new LayerBipedArmor(this) {
         @Override
         protected void initArmor() {
            this.modelLeggings = new ModelZombie(0.5F, true);
            this.modelArmor = new ModelZombie(1.0F, true);
         }
      };
      this.addLayer(var3);
      this.field_177122_o = Lists.newArrayList(this.layerRenderers);
      if (var2 instanceof LayerCustomHead) {
         this.removeLayer(var2);
         this.addLayer(new LayerCustomHead(this.zombieVillagerModel.bipedHead));
      }

      this.removeLayer(var3);
      this.addLayer(new LayerVillagerArmor(this));
      this.field_177121_n = Lists.newArrayList(this.layerRenderers);
   }

   public void doRender(EntityZombie entityZombie, double double2, double double3, double double4, float float5, float float6) {
      this.func_82427_a(entityZombie);
      super.doRender(entityZombie, double2, double3, double4, float5, float6);
   }

   protected ResourceLocation getEntityTexture(EntityZombie entityZombie) {
      return entityZombie.isVillager() ? zombieVillagerTextures : zombieTextures;
   }

   private void func_82427_a(EntityZombie entityZombie) {
      if (entityZombie.isVillager()) {
         this.mainModel = this.zombieVillagerModel;
         this.layerRenderers = this.field_177121_n;
      } else {
         this.mainModel = this.field_82434_o;
         this.layerRenderers = this.field_177122_o;
      }

      this.modelBipedMain = (ModelBiped)this.mainModel;
   }

   protected void rotateCorpse(EntityZombie entityZombie, float float2, float float3, float float4) {
      if (entityZombie.isConverting()) {
         float3 += (float)(Math.cos(entityZombie.ticksExisted * 3.25) * Math.PI * 0.25);
      }

      super.rotateCorpse(entityZombie, float2, float3, float4);
   }
}
