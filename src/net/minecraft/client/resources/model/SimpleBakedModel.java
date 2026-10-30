package net.minecraft.client.resources.model;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BreakingFour;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

public class SimpleBakedModel implements IBakedModel {
   protected final List<BakedQuad> generalQuads;
   protected final List<List<BakedQuad>> faceQuads;
   protected final boolean ambientOcclusion;
   protected final boolean gui3d;
   protected final TextureAtlasSprite texture;
   protected final ItemCameraTransforms cameraTransforms;

   public SimpleBakedModel(
      List<BakedQuad> list1,
      List<List<BakedQuad>> list2,
      boolean boolean3,
      boolean boolean4,
      TextureAtlasSprite textureAtlasSprite,
      ItemCameraTransforms itemCameraTransforms
   ) {
      this.generalQuads = list1;
      this.faceQuads = list2;
      this.ambientOcclusion = boolean3;
      this.gui3d = boolean4;
      this.texture = textureAtlasSprite;
      this.cameraTransforms = itemCameraTransforms;
   }

   @Override
   public List<BakedQuad> getFaceQuads(EnumFacing enumFacing) {
      return this.faceQuads.get(enumFacing.ordinal());
   }

   @Override
   public List<BakedQuad> getGeneralQuads() {
      return this.generalQuads;
   }

   @Override
   public boolean isAmbientOcclusion() {
      return this.ambientOcclusion;
   }

   @Override
   public boolean isGui3d() {
      return this.gui3d;
   }

   @Override
   public boolean isBuiltInRenderer() {
      return false;
   }

   @Override
   public TextureAtlasSprite getParticleTexture() {
      return this.texture;
   }

   @Override
   public ItemCameraTransforms getItemCameraTransforms() {
      return this.cameraTransforms;
   }

   public static class Builder {
      private final List<BakedQuad> builderGeneralQuads = Lists.newArrayList();
      private final List<List<BakedQuad>> builderFaceQuads = Lists.newArrayListWithCapacity(6);
      private final boolean builderAmbientOcclusion;
      private TextureAtlasSprite builderTexture;
      private final boolean builderGui3d;
      private final ItemCameraTransforms builderCameraTransforms;

      public Builder(ModelBlock modelBlock) {
         this(modelBlock.isAmbientOcclusion(), modelBlock.isGui3d(), modelBlock.getAllTransforms());
      }

      public Builder(IBakedModel iBakedModel, TextureAtlasSprite textureAtlasSprite) {
         this(iBakedModel.isAmbientOcclusion(), iBakedModel.isGui3d(), iBakedModel.getItemCameraTransforms());
         this.builderTexture = iBakedModel.getParticleTexture();

         for (EnumFacing var9 : EnumFacing.values()) {
            this.addFaceBreakingFours(iBakedModel, textureAtlasSprite, var9);
         }

         this.addGeneralBreakingFours(iBakedModel, textureAtlasSprite);
      }

      private void addFaceBreakingFours(IBakedModel iBakedModel, TextureAtlasSprite textureAtlasSprite, EnumFacing enumFacing) {
         for (BakedQuad var8 : iBakedModel.getFaceQuads(enumFacing)) {
            this.addFaceQuad(enumFacing, new BreakingFour(var8, textureAtlasSprite));
         }
      }

      private void addGeneralBreakingFours(IBakedModel iBakedModel, TextureAtlasSprite textureAtlasSprite) {
         for (BakedQuad var7 : iBakedModel.getGeneralQuads()) {
            this.addGeneralQuad(new BreakingFour(var7, textureAtlasSprite));
         }
      }

      private Builder(boolean boolean1, boolean boolean2, ItemCameraTransforms itemCameraTransforms) {
         for (EnumFacing var10000 : EnumFacing.values()) {
            this.builderFaceQuads.add(Lists.newArrayList());
         }

         this.builderAmbientOcclusion = boolean1;
         this.builderGui3d = boolean2;
         this.builderCameraTransforms = itemCameraTransforms;
      }

      public SimpleBakedModel.Builder addFaceQuad(EnumFacing enumFacing, BakedQuad bakedQuad) {
         this.builderFaceQuads.get(enumFacing.ordinal()).add(bakedQuad);
         return this;
      }

      public SimpleBakedModel.Builder addGeneralQuad(BakedQuad bakedQuad) {
         this.builderGeneralQuads.add(bakedQuad);
         return this;
      }

      public SimpleBakedModel.Builder setTexture(TextureAtlasSprite textureAtlasSprite) {
         this.builderTexture = textureAtlasSprite;
         return this;
      }

      public IBakedModel makeBakedModel() {
         if (this.builderTexture == null) {
            throw new RuntimeException("Missing particle!");
         } else {
            return new SimpleBakedModel(
               this.builderGeneralQuads,
               this.builderFaceQuads,
               this.builderAmbientOcclusion,
               this.builderGui3d,
               this.builderTexture,
               this.builderCameraTransforms
            );
         }
      }
   }
}
