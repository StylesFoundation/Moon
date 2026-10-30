package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderSnowball<T extends Entity> extends Render<T> {
   protected final Item field_177084_a;
   private final RenderItem field_177083_e;

   public RenderSnowball(RenderManager renderManager, Item item, RenderItem renderItem) {
      super(renderManager);
      this.field_177084_a = item;
      this.field_177083_e = renderItem;
   }

   @Override
   public void doRender(T entity, double double2, double double3, double double4, float float5, float float6) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)double2, (float)double3, (float)double4);
      GlStateManager.enableRescaleNormal();
      GlStateManager.scale(0.5F, 0.5F, 0.5F);
      GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
      this.bindTexture(TextureMap.locationBlocksTexture);
      this.field_177083_e.renderItem(this.func_177082_d((T)entity), ItemCameraTransforms.TransformType.GROUND);
      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
      super.doRender((T)entity, double2, double3, double4, float5, float6);
   }

   public ItemStack func_177082_d(T entity) {
      return new ItemStack(this.field_177084_a, 1, 0);
   }

   @Override
   protected ResourceLocation getEntityTexture(Entity entity) {
      return TextureMap.locationBlocksTexture;
   }
}
