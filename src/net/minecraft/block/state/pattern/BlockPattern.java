package net.minecraft.block.state.pattern;

import com.google.common.base.MoreObjects;
import com.google.common.base.Predicate;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;

public class BlockPattern {
   private final Predicate<BlockWorldState>[][][] blockMatches;
   private final int fingerLength;
   private final int thumbLength;
   private final int palmLength;

   public BlockPattern(Predicate<BlockWorldState>[][][] arr) {
      this.blockMatches = arr;
      this.fingerLength = arr.length;
      if (this.fingerLength > 0) {
         this.thumbLength = arr[0].length;
         if (this.thumbLength > 0) {
            this.palmLength = arr[0][0].length;
         } else {
            this.palmLength = 0;
         }
      } else {
         this.thumbLength = 0;
         this.palmLength = 0;
      }
   }

   public int getThumbLength() {
      return this.thumbLength;
   }

   public int getPalmLength() {
      return this.palmLength;
   }

   private BlockPattern.PatternHelper checkPatternAt(
      BlockPos blockPos, EnumFacing enumFacing2, EnumFacing enumFacing3, LoadingCache<BlockPos, BlockWorldState> loadingCache
   ) {
      for (int var8 = 0; var8 < this.palmLength; var8++) {
         for (int var9 = 0; var9 < this.thumbLength; var9++) {
            for (int var10 = 0; var10 < this.fingerLength; var10++) {
               if (!this.blockMatches[var10][var9][var8]
                  .apply((BlockWorldState)loadingCache.getUnchecked(translateOffset(blockPos, enumFacing2, enumFacing3, var8, var9, var10)))) {
                  return null;
               }
            }
         }
      }

      return new BlockPattern.PatternHelper(blockPos, enumFacing2, enumFacing3, loadingCache, this.palmLength, this.thumbLength, this.fingerLength);
   }

   public BlockPattern.PatternHelper match(World world, BlockPos blockPos) {
      LoadingCache var6 = func_181627_a(world, false);
      int var7 = Math.max(Math.max(this.palmLength, this.thumbLength), this.fingerLength);

      for (BlockPos var9 : BlockPos.getAllInBox(blockPos, blockPos.add(var7 - 1, var7 - 1, var7 - 1))) {
         for (EnumFacing var13 : EnumFacing.values()) {
            for (EnumFacing var17 : EnumFacing.values()) {
               if (var17 != var13 && var17 != var13.getOpposite()) {
                  BlockPattern.PatternHelper var18 = this.checkPatternAt(var9, var13, var17, var6);
                  if (var18 != null) {
                     return var18;
                  }
               }
            }
         }
      }

      return null;
   }

   public static LoadingCache<BlockPos, BlockWorldState> func_181627_a(World world, boolean boolean2) {
      return CacheBuilder.newBuilder().build(new BlockPattern.CacheLoader(world, boolean2));
   }

   protected static BlockPos translateOffset(BlockPos blockPos, EnumFacing enumFacing2, EnumFacing enumFacing3, int integer4, int integer5, int integer6) {
      if (enumFacing2 != enumFacing3 && enumFacing2 != enumFacing3.getOpposite()) {
         Vec3i var9 = new Vec3i(enumFacing2.getFrontOffsetX(), enumFacing2.getFrontOffsetY(), enumFacing2.getFrontOffsetZ());
         Vec3i var10 = new Vec3i(enumFacing3.getFrontOffsetX(), enumFacing3.getFrontOffsetY(), enumFacing3.getFrontOffsetZ());
         Vec3i var11 = var9.crossProduct(var10);
         return blockPos.add(
            var10.getX() * -integer5 + var11.getX() * integer4 + var9.getX() * integer6,
            var10.getY() * -integer5 + var11.getY() * integer4 + var9.getY() * integer6,
            var10.getZ() * -integer5 + var11.getZ() * integer4 + var9.getZ() * integer6
         );
      } else {
         throw new IllegalArgumentException("Invalid forwards & up combination");
      }
   }

   static class CacheLoader extends com.google.common.cache.CacheLoader<BlockPos, BlockWorldState> {
      private final World world;
      private final boolean field_181626_b;

      public CacheLoader(World world, boolean boolean2) {
         this.world = world;
         this.field_181626_b = boolean2;
      }

      public BlockWorldState load(BlockPos blockPos) throws Exception {
         return new BlockWorldState(this.world, blockPos, this.field_181626_b);
      }
   }

   public static class PatternHelper {
      private final BlockPos pos;
      private final EnumFacing finger;
      private final EnumFacing thumb;
      private final LoadingCache<BlockPos, BlockWorldState> lcache;
      private final int field_181120_e;
      private final int field_181121_f;
      private final int field_181122_g;

      public PatternHelper(
         BlockPos blockPos,
         EnumFacing enumFacing2,
         EnumFacing enumFacing3,
         LoadingCache<BlockPos, BlockWorldState> loadingCache,
         int integer5,
         int integer6,
         int integer7
      ) {
         this.pos = blockPos;
         this.finger = enumFacing2;
         this.thumb = enumFacing3;
         this.lcache = loadingCache;
         this.field_181120_e = integer5;
         this.field_181121_f = integer6;
         this.field_181122_g = integer7;
      }

      public BlockPos getPos() {
         return this.pos;
      }

      public EnumFacing getFinger() {
         return this.finger;
      }

      public EnumFacing getThumb() {
         return this.thumb;
      }

      public int func_181118_d() {
         return this.field_181120_e;
      }

      public int func_181119_e() {
         return this.field_181121_f;
      }

      public BlockWorldState translateOffset(int integer1, int integer2, int integer3) {
         return (BlockWorldState)this.lcache
            .getUnchecked(BlockPattern.translateOffset(this.pos, this.getFinger(), this.getThumb(), integer1, integer2, integer3));
      }

      @Override
      public String toString() {
         return MoreObjects.toStringHelper(this).add("up", this.thumb).add("forwards", this.finger).add("frontTopLeft", this.pos).toString();
      }
   }
}
