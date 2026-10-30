package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class VboChunkFactory implements IRenderChunkFactory {
   @Override
   public RenderChunk makeRenderChunk(World world, RenderGlobal renderGlobal, BlockPos blockPos, int integer) {
      return new RenderChunk(world, renderGlobal, blockPos, integer);
   }
}
