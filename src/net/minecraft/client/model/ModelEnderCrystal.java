package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelEnderCrystal extends ModelBase {
   public ModelRenderer cube;
   public ModelRenderer glass = new ModelRenderer(this, "glass");
   public ModelRenderer base;

   public ModelEnderCrystal(float float1, boolean boolean2) {
      this.glass.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
      this.cube = new ModelRenderer(this, "cube");
      this.cube.setTextureOffset(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
      if (boolean2) {
         this.base = new ModelRenderer(this, "base");
         this.base.setTextureOffset(0, 16).addBox(-6.0F, 0.0F, -6.0F, 12, 4, 12);
      }
   }

   @Override
   public void render(Entity entity, float float2, float float3, float float4, float float5, float float6, float float7) {
      GlStateManager.pushMatrix();
      GlStateManager.scale(2.0F, 2.0F, 2.0F);
      GlStateManager.translate(0.0F, -0.5F, 0.0F);
      if (this.base != null) {
         this.base.render(float7);
      }

      GlStateManager.rotate(float3, 0.0F, 1.0F, 0.0F);
      GlStateManager.translate(0.0F, 0.8F + float4, 0.0F);
      GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
      this.glass.render(float7);
      GlStateManager.scale(0.875F, 0.875F, 0.875F);
      GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
      GlStateManager.rotate(float3, 0.0F, 1.0F, 0.0F);
      this.glass.render(float7);
      GlStateManager.scale(0.875F, 0.875F, 0.875F);
      GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
      GlStateManager.rotate(float3, 0.0F, 1.0F, 0.0F);
      this.cube.render(float7);
      GlStateManager.popMatrix();
   }
}
