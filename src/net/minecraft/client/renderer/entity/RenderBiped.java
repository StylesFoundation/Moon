package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderBiped<T extends EntityLiving> extends RenderLiving<T> {
   private static final ResourceLocation DEFAULT_RES_LOC = new ResourceLocation("textures/entity/steve.png");
   protected ModelBiped modelBipedMain;
   protected float field_77070_b;

   public RenderBiped(RenderManager renderManager, ModelBiped modelBiped, float float3) {
      this(renderManager, modelBiped, float3, 1.0F);
      this.addLayer(new LayerHeldItem(this));
   }

   public RenderBiped(RenderManager renderManager, ModelBiped modelBiped, float float3, float float4) {
      super(renderManager, modelBiped, float3);
      this.modelBipedMain = modelBiped;
      this.field_77070_b = float4;
      this.addLayer(new LayerCustomHead(modelBiped.bipedHead));
   }

   protected ResourceLocation getEntityTexture(T entityLiving) {
      return DEFAULT_RES_LOC;
   }

   @Override
   public void transformHeldFull3DItemLayer() {
      GlStateManager.translate(0.0F, 0.1875F, 0.0F);
   }
}
