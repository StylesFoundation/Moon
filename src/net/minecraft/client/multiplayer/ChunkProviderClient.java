package net.minecraft.client.multiplayer;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.LongHashMap;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkProviderClient implements IChunkProvider {
   private static final Logger logger = LogManager.getLogger();
   private final Chunk blankChunk;
   public LongHashMap<Chunk> chunkMapping = new LongHashMap<>();
   private final List<Chunk> chunkListing = Lists.newArrayList();
   private final World worldObj;

   public ChunkProviderClient(World world) {
      this.blankChunk = new EmptyChunk(world, 0, 0);
      this.worldObj = world;
   }

   @Override
   public boolean chunkExists(int integer1, int integer2) {
      return true;
   }

   public void unloadChunk(int integer1, int integer2) {
      Chunk var6 = this.provideChunk(integer1, integer2);
      if (!var6.isEmpty()) {
         var6.onChunkUnload();
      }

      this.chunkMapping.remove(ChunkCoordIntPair.chunkXZ2Int(integer1, integer2));
      this.chunkListing.remove(var6);
   }

   public Chunk loadChunk(int integer1, int integer2) {
      Chunk var3 = new Chunk(this.worldObj, integer1, integer2);
      this.chunkMapping.add(ChunkCoordIntPair.chunkXZ2Int(integer1, integer2), var3);
      this.chunkListing.add(var3);
      var3.setChunkLoaded(true);
      return var3;
   }

   @Override
   public Chunk provideChunk(int integer1, int integer2) {
      Chunk var6 = this.chunkMapping.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(integer1, integer2));
      return var6 == null ? this.blankChunk : var6;
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
      long var4 = System.currentTimeMillis();

      for (Chunk var7 : this.chunkListing) {
         var7.func_150804_b(System.currentTimeMillis() - var4 > 5L);
      }

      if (System.currentTimeMillis() - var4 > 100L) {
         logger.info("Warning: Clientside chunk ticking took {} ms", new Object[]{System.currentTimeMillis() - var4});
      }

      return false;
   }

   @Override
   public boolean canSave() {
      return false;
   }

   @Override
   public void populate(IChunkProvider iChunkProvider, int integer2, int integer3) {
   }

   @Override
   public boolean populateChunk(IChunkProvider iChunkProvider, Chunk chunk, int integer3, int integer4) {
      return false;
   }

   @Override
   public String makeString() {
      return "MultiplayerChunkCache: " + this.chunkMapping.getNumHashElements() + ", " + this.chunkListing.size();
   }

   @Override
   public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType enumCreatureType, BlockPos blockPos) {
      return null;
   }

   @Override
   public BlockPos getStrongholdGen(World world, String string, BlockPos blockPos) {
      return null;
   }

   @Override
   public int getLoadedChunkCount() {
      return this.chunkListing.size();
   }

   @Override
   public void recreateStructures(Chunk chunk, int integer2, int integer3) {
   }

   @Override
   public Chunk provideChunk(BlockPos blockPos) {
      return this.provideChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
   }
}
