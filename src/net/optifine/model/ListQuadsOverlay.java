package net.optifine.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.init.Blocks;

public class ListQuadsOverlay {
   private final List<BakedQuad> listQuads = new ArrayList<>();
   private final List<IBlockState> listBlockStates = new ArrayList<>();
   private final List<BakedQuad> listQuadsSingle = Arrays.asList();

   public void addQuad(BakedQuad bakedQuad, IBlockState iBlockState) {
      if (bakedQuad != null) {
         this.listQuads.add(bakedQuad);
         this.listBlockStates.add(iBlockState);
      }
   }

   public int size() {
      return this.listQuads.size();
   }

   public BakedQuad getQuad(int integer) {
      return this.listQuads.get(integer);
   }

   public IBlockState getBlockState(int integer) {
      return integer >= 0 && integer < this.listBlockStates.size() ? this.listBlockStates.get(integer) : Blocks.air.getDefaultState();
   }

   public List<BakedQuad> getListQuadsSingle(BakedQuad bakedQuad) {
      this.listQuadsSingle.set(0, bakedQuad);
      return this.listQuadsSingle;
   }

   public void clear() {
      this.listQuads.clear();
      this.listBlockStates.clear();
   }
}
