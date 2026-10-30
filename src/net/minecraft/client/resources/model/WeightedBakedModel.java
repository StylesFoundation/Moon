package net.minecraft.client.resources.model;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandom;

public class WeightedBakedModel implements IBakedModel {
   private final int totalWeight;
   private final List<WeightedBakedModel.MyWeighedRandomItem> models;
   private final IBakedModel baseModel;

   public WeightedBakedModel(List<WeightedBakedModel.MyWeighedRandomItem> list) {
      this.models = list;
      this.totalWeight = WeightedRandom.getTotalWeight(list);
      this.baseModel = ((WeightedBakedModel.MyWeighedRandomItem)list.get(0)).model;
   }

   @Override
   public List<BakedQuad> getFaceQuads(EnumFacing enumFacing) {
      return this.baseModel.getFaceQuads(enumFacing);
   }

   @Override
   public List<BakedQuad> getGeneralQuads() {
      return this.baseModel.getGeneralQuads();
   }

   @Override
   public boolean isAmbientOcclusion() {
      return this.baseModel.isAmbientOcclusion();
   }

   @Override
   public boolean isGui3d() {
      return this.baseModel.isGui3d();
   }

   @Override
   public boolean isBuiltInRenderer() {
      return this.baseModel.isBuiltInRenderer();
   }

   @Override
   public TextureAtlasSprite getParticleTexture() {
      return this.baseModel.getParticleTexture();
   }

   @Override
   public ItemCameraTransforms getItemCameraTransforms() {
      return this.baseModel.getItemCameraTransforms();
   }

   public IBakedModel getAlternativeModel(long long1) {
      return WeightedRandom.getRandomItem(this.models, Math.abs((int)long1 >> 16) % this.totalWeight).model;
   }

   public static class Builder {
      private final List<WeightedBakedModel.MyWeighedRandomItem> listItems = Lists.newArrayList();

      public WeightedBakedModel.Builder add(IBakedModel iBakedModel, int integer) {
         this.listItems.add(new WeightedBakedModel.MyWeighedRandomItem(iBakedModel, integer));
         return this;
      }

      public WeightedBakedModel build() {
         Collections.sort(this.listItems);
         return new WeightedBakedModel(this.listItems);
      }

      public IBakedModel first() {
         return this.listItems.get(0).model;
      }
   }

   static class MyWeighedRandomItem extends WeightedRandom.Item implements Comparable<WeightedBakedModel.MyWeighedRandomItem> {
      protected final IBakedModel model;

      public MyWeighedRandomItem(IBakedModel iBakedModel, int integer) {
         super(integer);
         this.model = iBakedModel;
      }

      public int compareTo(WeightedBakedModel.MyWeighedRandomItem myWeighedRandomItem) {
         return ComparisonChain.start()
            .compare(myWeighedRandomItem.itemWeight, this.itemWeight)
            .compare(this.getCountQuads(), myWeighedRandomItem.getCountQuads())
            .result();
      }

      protected int getCountQuads() {
         int var4 = this.model.getGeneralQuads().size();

         for (EnumFacing var8 : EnumFacing.values()) {
            var4 += this.model.getFaceQuads(var8).size();
         }

         return var4;
      }

      @Override
      public String toString() {
         return "MyWeighedRandomItem{weight=" + this.itemWeight + ", model=" + this.model + "}";
      }
   }
}
