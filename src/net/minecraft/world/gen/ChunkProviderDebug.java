package net.minecraft.world.gen;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;

public class ChunkProviderDebug implements IChunkProvider {
   private static final List<IBlockState> field_177464_a = Lists.newArrayList();
   private static final int field_177462_b;
   private static final int field_181039_c;
   private final World world;

   public ChunkProviderDebug(World world) {
      this.world = world;
   }

   @Override
   public Chunk provideChunk(int integer1, int integer2) {
      ChunkPrimer var6 = new ChunkPrimer();

      for (int var7 = 0; var7 < 16; var7++) {
         for (int var8 = 0; var8 < 16; var8++) {
            int var9 = integer1 * 16 + var7;
            int var10 = integer2 * 16 + var8;
            var6.setBlockState(var7, 60, var8, Blocks.barrier.getDefaultState());
            IBlockState var11 = func_177461_b(var9, var10);
            if (var11 != null) {
               var6.setBlockState(var7, 70, var8, var11);
            }
         }
      }

      Chunk var12 = new Chunk(this.world, var6, integer1, integer2);
      var12.generateSkylightMap();
      BiomeGenBase[] var13 = this.world.getWorldChunkManager().loadBlockGeneratorData(null, integer1 * 16, integer2 * 16, 16, 16);
      byte[] var14 = var12.getBiomeArray();

      for (int var15 = 0; var15 < var14.length; var15++) {
         var14[var15] = (byte)var13[var15].biomeID;
      }

      var12.generateSkylightMap();
      return var12;
   }

   public static IBlockState func_177461_b(int integer1, int integer2) {
      IBlockState var5 = null;
      if (integer1 > 0 && integer2 > 0 && integer1 % 2 != 0 && integer2 % 2 != 0) {
         integer1 /= 2;
         integer2 /= 2;
         if (integer1 <= field_177462_b && integer2 <= field_181039_c) {
            int var6 = MathHelper.abs_int(integer1 * field_177462_b + integer2);
            if (var6 < field_177464_a.size()) {
               var5 = field_177464_a.get(var6);
            }
         }
      }

      return var5;
   }

   @Override
   public boolean chunkExists(int integer1, int integer2) {
      return true;
   }

   @Override
   public void populate(IChunkProvider iChunkProvider, int integer2, int integer3) {
   }

   @Override
   public boolean populateChunk(IChunkProvider iChunkProvider, Chunk chunk, int integer3, int integer4) {
      return false;
   }

   @Override
   public boolean saveChunks(boolean boolean1, IProgressUpdate iProgressUpdate) {
      return true;
   }

   @Override
   public void saveExtraData() {
   }

   @Override
   public boolean unloadQueuedChunks() {
      return false;
   }

   @Override
   public boolean canSave() {
      return true;
   }

   @Override
   public String makeString() {
      return "DebugLevelSource";
   }

   @Override
   public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType enumCreatureType, BlockPos blockPos) {
      return this.world.getBiomeGenForCoords(blockPos).getSpawnableList(enumCreatureType);
   }

   @Override
   public BlockPos getStrongholdGen(World world, String string, BlockPos blockPos) {
      return null;
   }

   @Override
   public int getLoadedChunkCount() {
      return 0;
   }

   @Override
   public void recreateStructures(Chunk chunk, int integer2, int integer3) {
   }

   @Override
   public Chunk provideChunk(BlockPos blockPos) {
      return this.provideChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
   }

   static {
      for (Block var14 : Block.blockRegistry) {
         field_177464_a.addAll(var14.getBlockState().getValidStates());
      }

      field_177462_b = MathHelper.ceiling_float_int(MathHelper.sqrt_float(field_177464_a.size()));
      field_181039_c = MathHelper.ceiling_float_int((float)field_177464_a.size() / field_177462_b);
   }
}
