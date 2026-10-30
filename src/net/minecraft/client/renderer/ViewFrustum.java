package net.minecraft.client.renderer;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.optifine.render.VboRegion;

public class ViewFrustum {
   protected final RenderGlobal renderGlobal;
   protected final World world;
   protected int countChunksY;
   protected int countChunksX;
   protected int countChunksZ;
   public RenderChunk[] renderChunks;
   private final Map<ChunkCoordIntPair, VboRegion[]> mapVboRegions = new HashMap<>();

   public ViewFrustum(World world, int integer, RenderGlobal renderGlobal, IRenderChunkFactory iRenderChunkFactory) {
      this.renderGlobal = renderGlobal;
      this.world = world;
      this.setCountChunksXYZ(integer);
      this.createRenderChunks(iRenderChunkFactory);
   }

   protected void createRenderChunks(IRenderChunkFactory iRenderChunkFactory) {
      int var4 = this.countChunksX * this.countChunksY * this.countChunksZ;
      this.renderChunks = new RenderChunk[var4];
      int var5 = 0;

      for (int var6 = 0; var6 < this.countChunksX; var6++) {
         for (int var7 = 0; var7 < this.countChunksY; var7++) {
            for (int var8 = 0; var8 < this.countChunksZ; var8++) {
               int var9 = (var8 * this.countChunksY + var7) * this.countChunksX + var6;
               BlockPos var10 = new BlockPos(var6 * 16, var7 * 16, var8 * 16);
               this.renderChunks[var9] = iRenderChunkFactory.makeRenderChunk(this.world, this.renderGlobal, var10, var5++);
               if (Config.isVbo() && Config.isRenderRegions()) {
                  this.updateVboRegion(this.renderChunks[var9]);
               }
            }
         }
      }

      for (RenderChunk var17 : this.renderChunks) {
         for (int var18 = 0; var18 < EnumFacing.VALUES.length; var18++) {
            EnumFacing var11 = EnumFacing.VALUES[var18];
            BlockPos var12 = var17.getBlockPosOffset16(var11);
            RenderChunk var13 = this.getRenderChunk(var12);
            var17.setRenderChunkNeighbour(var11, var13);
         }
      }
   }

   public void deleteGlResources() {
      RenderChunk[] var1 = this.renderChunks;
      int var2 = var1.length;

      for (int var3 = 0; var3 < var2; var3++) {
         var1[var3].deleteGlResources();
      }

      this.deleteVboRegions();
   }

   protected void setCountChunksXYZ(int integer) {
      int var4 = integer * 2 + 1;
      this.countChunksX = var4;
      this.countChunksY = 16;
      this.countChunksZ = var4;
   }

   public void updateChunkPositions(double double1, double double2) {
      int var7 = MathHelper.floor_double(double1) - 8;
      int var8 = MathHelper.floor_double(double2) - 8;
      int var9 = this.countChunksX * 16;

      for (int var10 = 0; var10 < this.countChunksX; var10++) {
         int var11 = this.func_178157_a(var7, var9, var10);

         for (int var12 = 0; var12 < this.countChunksZ; var12++) {
            int var13 = this.func_178157_a(var8, var9, var12);

            for (int var14 = 0; var14 < this.countChunksY; var14++) {
               int var15 = var14 * 16;
               RenderChunk var16 = this.renderChunks[(var12 * this.countChunksY + var14) * this.countChunksX + var10];
               BlockPos var17 = var16.getPosition();
               if (var17.getX() != var11 || var17.getY() != var15 || var17.getZ() != var13) {
                  BlockPos var18 = new BlockPos(var11, var15, var13);
                  if (!var18.equals(var16.getPosition())) {
                     var16.setPosition(var18);
                  }
               }
            }
         }
      }
   }

   private int func_178157_a(int integer1, int integer2, int integer3) {
      int var6 = integer3 * 16;
      int var7 = var6 - integer1 + integer2 / 2;
      if (var7 < 0) {
         var7 -= integer2 - 1;
      }

      return var6 - var7 / integer2 * integer2;
   }

   public void markBlocksForUpdate(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
      int var9 = MathHelper.bucketInt(integer1, 16);
      int var10 = MathHelper.bucketInt(integer2, 16);
      int var11 = MathHelper.bucketInt(integer3, 16);
      int var12 = MathHelper.bucketInt(integer4, 16);
      int var13 = MathHelper.bucketInt(integer5, 16);
      int var14 = MathHelper.bucketInt(integer6, 16);

      for (int var15 = var9; var15 <= var12; var15++) {
         int var16 = var15 % this.countChunksX;
         if (var16 < 0) {
            var16 += this.countChunksX;
         }

         for (int var17 = var10; var17 <= var13; var17++) {
            int var18 = var17 % this.countChunksY;
            if (var18 < 0) {
               var18 += this.countChunksY;
            }

            for (int var19 = var11; var19 <= var14; var19++) {
               int var20 = var19 % this.countChunksZ;
               if (var20 < 0) {
                  var20 += this.countChunksZ;
               }

               int var21 = (var20 * this.countChunksY + var18) * this.countChunksX + var16;
               this.renderChunks[var21].setNeedsUpdate(true);
            }
         }
      }
   }

   public RenderChunk getRenderChunk(BlockPos blockPos) {
      int var2 = blockPos.getX() >> 4;
      int var3 = blockPos.getY() >> 4;
      int var4 = blockPos.getZ() >> 4;
      if (var3 >= 0 && var3 < this.countChunksY) {
         var2 %= this.countChunksX;
         if (var2 < 0) {
            var2 += this.countChunksX;
         }

         var4 %= this.countChunksZ;
         if (var4 < 0) {
            var4 += this.countChunksZ;
         }

         int var5 = (var4 * this.countChunksY + var3) * this.countChunksX + var2;
         return this.renderChunks[var5];
      } else {
         return null;
      }
   }

   private void updateVboRegion(RenderChunk renderChunk) {
      BlockPos var4 = renderChunk.getPosition();
      int var5 = var4.getX() >> 8 << 8;
      int var6 = var4.getZ() >> 8 << 8;
      ChunkCoordIntPair var7 = new ChunkCoordIntPair(var5, var6);
      EnumWorldBlockLayer[] var8 = RenderChunk.ENUM_WORLD_BLOCK_LAYERS;
      VboRegion[] var9 = this.mapVboRegions.get(var7);
      if (var9 == null) {
         var9 = new VboRegion[var8.length];

         for (int var10 = 0; var10 < var8.length; var10++) {
            var9[var10] = new VboRegion(var8[var10]);
         }

         this.mapVboRegions.put(var7, var9);
      }

      for (int var12 = 0; var12 < var8.length; var12++) {
         VboRegion var11 = var9[var12];
         if (var11 != null) {
            renderChunk.getVertexBufferByLayer(var12).setVboRegion(var11);
         }
      }
   }

   public void deleteVboRegions() {
      for (ChunkCoordIntPair var2 : this.mapVboRegions.keySet()) {
         VboRegion[] var3 = this.mapVboRegions.get(var2);

         for (int var4 = 0; var4 < var3.length; var4++) {
            VboRegion var5 = var3[var4];
            if (var5 != null) {
               var5.deleteGlBuffers();
            }

            var3[var4] = null;
         }
      }

      this.mapVboRegions.clear();
   }
}
