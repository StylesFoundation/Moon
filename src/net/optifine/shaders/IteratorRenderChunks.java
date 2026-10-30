package net.optifine.shaders;

import java.util.Iterator;
import net.minecraft.client.renderer.ViewFrustum;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockPos;
import net.optifine.BlockPosM;

public class IteratorRenderChunks implements Iterator<RenderChunk> {
   private final ViewFrustum viewFrustum;
   private final Iterator3d Iterator3d;
   private final BlockPosM posBlock = new BlockPosM(0, 0, 0);

   public IteratorRenderChunks(ViewFrustum viewFrustum, BlockPos blockPos2, BlockPos blockPos3, int integer4, int integer5) {
      this.viewFrustum = viewFrustum;
      this.Iterator3d = new Iterator3d(blockPos2, blockPos3, integer4, integer5);
   }

   @Override
   public boolean hasNext() {
      return this.Iterator3d.hasNext();
   }

   public RenderChunk next() {
      BlockPos var1 = this.Iterator3d.next();
      this.posBlock.setXyz(var1.getX() << 4, var1.getY() << 4, var1.getZ() << 4);
      return this.viewFrustum.getRenderChunk(this.posBlock);
   }

   @Override
   public void remove() {
      throw new RuntimeException("Not implemented");
   }
}
