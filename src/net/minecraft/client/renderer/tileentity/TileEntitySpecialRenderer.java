package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.optifine.entity.model.IEntityRenderer;

public abstract class TileEntitySpecialRenderer<T extends TileEntity> implements IEntityRenderer {
   protected static final ResourceLocation[] DESTROY_STAGES = new ResourceLocation[]{
      new ResourceLocation("textures/blocks/destroy_stage_0.png"),
      new ResourceLocation("textures/blocks/destroy_stage_1.png"),
      new ResourceLocation("textures/blocks/destroy_stage_2.png"),
      new ResourceLocation("textures/blocks/destroy_stage_3.png"),
      new ResourceLocation("textures/blocks/destroy_stage_4.png"),
      new ResourceLocation("textures/blocks/destroy_stage_5.png"),
      new ResourceLocation("textures/blocks/destroy_stage_6.png"),
      new ResourceLocation("textures/blocks/destroy_stage_7.png"),
      new ResourceLocation("textures/blocks/destroy_stage_8.png"),
      new ResourceLocation("textures/blocks/destroy_stage_9.png")
   };
   protected TileEntityRendererDispatcher rendererDispatcher;
   private Class tileEntityClass = null;
   private ResourceLocation locationTextureCustom = null;

   public abstract void renderTileEntityAt(T tileEntity, double double2, double double3, double double4, float float5, int integer);

   protected void bindTexture(ResourceLocation resourceLocation) {
      TextureManager var2 = this.rendererDispatcher.renderEngine;
      if (var2 != null) {
         var2.bindTexture(resourceLocation);
      }
   }

   protected World getWorld() {
      return this.rendererDispatcher.worldObj;
   }

   public void setRendererDispatcher(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
      this.rendererDispatcher = tileEntityRendererDispatcher;
   }

   public FontRenderer getFontRenderer() {
      return this.rendererDispatcher.getFontRenderer();
   }

   public boolean forceTileEntityRender() {
      return false;
   }

   public void renderTileEntityFast(T tileEntity, double double2, double double3, double double4, float float5, int integer, WorldRenderer worldRenderer) {
   }

   @Override
   public Class getEntityClass() {
      return this.tileEntityClass;
   }

   @Override
   public void setEntityClass(Class class1) {
      this.tileEntityClass = class1;
   }

   @Override
   public ResourceLocation getLocationTextureCustom() {
      return this.locationTextureCustom;
   }

   @Override
   public void setLocationTextureCustom(ResourceLocation resourceLocation) {
      this.locationTextureCustom = resourceLocation;
   }
}
