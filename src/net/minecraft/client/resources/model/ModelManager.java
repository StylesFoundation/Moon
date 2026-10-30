package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.IRegistry;

public class ModelManager implements IResourceManagerReloadListener {
   private IRegistry<ModelResourceLocation, IBakedModel> modelRegistry;
   private final TextureMap texMap;
   private final BlockModelShapes modelProvider;
   private IBakedModel defaultModel;

   public ModelManager(TextureMap textureMap) {
      this.texMap = textureMap;
      this.modelProvider = new BlockModelShapes(this);
   }

   @Override
   public void onResourceManagerReload(IResourceManager iResourceManager) {
      ModelBakery var2 = new ModelBakery(iResourceManager, this.texMap, this.modelProvider);
      this.modelRegistry = var2.setupModelRegistry();
      this.defaultModel = this.modelRegistry.getObject(ModelBakery.MODEL_MISSING);
      this.modelProvider.reloadModels();
   }

   public IBakedModel getModel(ModelResourceLocation modelResourceLocation) {
      if (modelResourceLocation == null) {
         return this.defaultModel;
      }

      IBakedModel var5 = this.modelRegistry.getObject(modelResourceLocation);
      return var5 == null ? this.defaultModel : var5;
   }

   public IBakedModel getMissingModel() {
      return this.defaultModel;
   }

   public TextureMap getTextureMap() {
      return this.texMap;
   }

   public BlockModelShapes getBlockModelShapes() {
      return this.modelProvider;
   }
}
