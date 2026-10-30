package net.optifine.shaders;

import java.util.Arrays;
import java.util.Iterator;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.ViewFrustum;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class ShadowUtils {
   public static Iterator<RenderChunk> makeShadowChunkIterator(WorldClient worldClient, double double2, Entity entity, int integer, ViewFrustum viewFrustum) {
      float var9 = Shaders.getShadowRenderDistance();
      if (var9 > 0.0F && var9 < (integer - 1) * 16) {
         int var10 = MathHelper.ceiling_float_int(var9 / 16.0F) + 1;
         float var11 = worldClient.getCelestialAngleRadians((float)double2);
         float var12 = Shaders.sunPathRotation * MathHelper.deg2Rad;
         float var13 = var11 > MathHelper.PId2 && var11 < 3.0F * MathHelper.PId2 ? var11 + MathHelper.PI : var11;
         float var14 = -MathHelper.sin(var13);
         float var15 = MathHelper.cos(var13) * MathHelper.cos(var12);
         float var16 = -MathHelper.cos(var13) * MathHelper.sin(var12);
         BlockPos var17 = new BlockPos(
            MathHelper.floor_double(entity.posX) >> 4, MathHelper.floor_double(entity.posY) >> 4, MathHelper.floor_double(entity.posZ) >> 4
         );
         BlockPos var18 = var17.add(-var14 * var10, -var15 * var10, -var16 * var10);
         BlockPos var19 = var17.add(var14 * integer, var15 * integer, var16 * integer);
         return new IteratorRenderChunks(viewFrustum, var18, var19, var10, var10);
      } else {
         return Arrays.asList(viewFrustum.renderChunks).iterator();
      }
   }
}
