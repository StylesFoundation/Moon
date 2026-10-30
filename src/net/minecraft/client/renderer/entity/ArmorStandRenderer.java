package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelArmorStand;
import net.minecraft.client.model.ModelArmorStandArmor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.ResourceLocation;

public class ArmorStandRenderer extends RendererLivingEntity<EntityArmorStand> {
   public static final ResourceLocation TEXTURE_ARMOR_STAND = new ResourceLocation("textures/entity/armorstand/wood.png");

   public ArmorStandRenderer(RenderManager renderManager) {
      super(renderManager, new ModelArmorStand(), 0.0F);
      LayerBipedArmor var2 = new LayerBipedArmor(this) {
         @Override
         protected void initArmor() {
            this.modelLeggings = new ModelArmorStandArmor(0.5F);
            this.modelArmor = new ModelArmorStandArmor(1.0F);
         }
      };
      this.addLayer(var2);
      this.addLayer(new LayerHeldItem(this));
      this.addLayer(new LayerCustomHead(this.getMainModel().bipedHead));
   }

   protected ResourceLocation getEntityTexture(EntityArmorStand entityArmorStand) {
      return TEXTURE_ARMOR_STAND;
   }

   public ModelArmorStand getMainModel() {
      return (ModelArmorStand)super.getMainModel();
   }

   protected void rotateCorpse(EntityArmorStand entityArmorStand, float float2, float float3, float float4) {
      GlStateManager.rotate(180.0F - float3, 0.0F, 1.0F, 0.0F);
   }

   protected boolean canRenderName(EntityArmorStand entityArmorStand) {
      return entityArmorStand.getAlwaysRenderNameTag();
   }
}
