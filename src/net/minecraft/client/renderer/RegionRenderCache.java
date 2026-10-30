package net.minecraft.client.renderer;

import java.util.ArrayDeque;
import java.util.Arrays;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.src.Config;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.optifine.DynamicLights;

public class RegionRenderCache extends ChunkCache {
   private static final IBlockState DEFAULT_STATE = Blocks.air.getDefaultState();
   private final BlockPos position;
   private final int[] combinedLights;
   private final IBlockState[] blockStates;
   private static final ArrayDeque<int[]> cacheLights = new ArrayDeque<>();
   private static final ArrayDeque<IBlockState[]> cacheStates = new ArrayDeque<>();
   private static final int maxCacheSize = Config.Method255(Runtime.getRuntime().availableProcessors(), 1, 32);

   public RegionRenderCache(World world, BlockPos blockPos2, BlockPos blockPos3, int integer) {
      super(world, blockPos2, blockPos3, integer);
      this.position = blockPos2.subtract(new Vec3i(integer, integer, integer));
      this.combinedLights = allocateLights(8000);
      Arrays.fill(this.combinedLights, -1);
      this.blockStates = allocateStates(8000);
   }

   @Override
   public TileEntity getTileEntity(BlockPos blockPos) {
      int var2 = (blockPos.getX() >> 4) - this.chunkX;
      int var3 = (blockPos.getZ() >> 4) - this.chunkZ;
      return this.chunkArray[var2][var3].getTileEntity(blockPos, Chunk.EnumCreateEntityType.QUEUED);
   }

   @Override
   public int getCombinedLight(BlockPos blockPos, int integer) {
      int var3 = this.getPositionIndex(blockPos);
      int var4 = this.combinedLights[var3];
      if (var4 == -1) {
         var4 = super.getCombinedLight(blockPos, integer);
         if (Config.isDynamicLights() && !this.getBlockState(blockPos).getBlock().isOpaqueCube()) {
            var4 = DynamicLights.getCombinedLight(blockPos, var4);
         }

         this.combinedLights[var3] = var4;
      }

      return var4;
   }

   @Override
   public IBlockState getBlockState(BlockPos blockPos) {
      int var2 = this.getPositionIndex(blockPos);
      IBlockState var3 = this.blockStates[var2];
      if (var3 == null) {
         var3 = this.getBlockStateRaw(blockPos);
         this.blockStates[var2] = var3;
      }

      return var3;
   }

   private IBlockState getBlockStateRaw(BlockPos blockPos) {
      return super.getBlockState(blockPos);
   }

   private int getPositionIndex(BlockPos blockPos) {
      int var4 = blockPos.getX() - this.position.getX();
      int var5 = blockPos.getY() - this.position.getY();
      int var6 = blockPos.getZ() - this.position.getZ();
      return var4 * 400 + var6 * 20 + var5;
   }

   public void freeBuffers() {
      freeLights(this.combinedLights);
      freeStates(this.blockStates);
   }

   private static int[] allocateLights(int integer) {
      synchronized (cacheLights) {
         int[] var2 = cacheLights.pollLast();
         if (var2 == null || var2.length < integer) {
            var2 = new int[integer];
         }

         return var2;
      }
   }

   public static void freeLights(int[] arr) {
      synchronized (cacheLights) {
         if (cacheLights.size() < maxCacheSize) {
            cacheLights.add(arr);
         }
      }
   }

   private static IBlockState[] allocateStates(int integer) {
      synchronized (cacheStates) {
         IBlockState[] var2 = cacheStates.pollLast();
         if (var2 != null && var2.length >= integer) {
            Arrays.fill(var2, null);
         } else {
            var2 = new IBlockState[integer];
         }

         return var2;
      }
   }

   public static void freeStates(IBlockState[] arr) {
      synchronized (cacheStates) {
         if (cacheStates.size() < maxCacheSize) {
            cacheStates.add(arr);
         }
      }
   }
}
