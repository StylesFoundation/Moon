package net.optifine.util;

import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.MathHelper;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class RenderChunkUtils {
   public static int getCountBlocks(RenderChunk renderChunk) {
      ExtendedBlockStorage[] var4 = renderChunk.getChunk().getBlockStorageArray();
      if (var4 == null) {
         return 0;
      }

      int var5 = renderChunk.getPosition().getY() >> 4;
      ExtendedBlockStorage var6 = var4[var5];
      return var6 == null ? 0 : var6.getBlockRefCount();
   }

   public static double getRelativeBufferSize(RenderChunk renderChunk) {
      return getRelativeBufferSize(getCountBlocks(renderChunk));
   }

   public static double getRelativeBufferSize(int integer) {
      double var3 = MathHelper.clamp_double(integer / 4096.0 * 0.995 * 2.0 - 1.0, -1.0, 1.0);
      return MathHelper.sqrt_double(1.0 - var3 * var3);
   }
}
