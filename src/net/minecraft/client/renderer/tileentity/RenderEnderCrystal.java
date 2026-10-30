package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderEnderCrystal extends Render<EntityEnderCrystal> {
   private static final ResourceLocation enderCrystalTextures = new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
   public ModelBase i = new ModelEnderCrystal(0.0F, true);

   public RenderEnderCrystal(RenderManager renderManager) {
      super(renderManager);
      this.shadowSize = 0.5F;
   }

   public void doRender(EntityEnderCrystal entityEnderCrystal, double double2, double double3, double double4, float float5, float float6) {
      float var10 = entityEnderCrystal.innerRotation + float6;
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)double2, (float)double3, (float)double4);
      this.bindTexture(enderCrystalTextures);
      float var11 = MathHelper.sin(var10 * 0.2F) / 2.0F + 0.5F;
      var11 = var11 * var11 + var11;
      this.i.render(entityEnderCrystal, 0.0F, var10 * 3.0F, var11 * 0.2F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.popMatrix();
      super.doRender(entityEnderCrystal, double2, double3, double4, float5, float6);
   }

   protected ResourceLocation getEntityTexture(EntityEnderCrystal entityEnderCrystal) {
      return enderCrystalTextures;
   }
}
