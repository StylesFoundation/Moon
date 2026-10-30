package net.minecraft.client.renderer.block.statemap;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class DefaultStateMapper extends StateMapperBase {
   @Override
   protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
      return new ModelResourceLocation(Block.blockRegistry.getNameForObject(iBlockState.getBlock()), this.getPropertyString(iBlockState.getProperties()));
   }
}
