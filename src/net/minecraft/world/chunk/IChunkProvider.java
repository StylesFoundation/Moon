package net.minecraft.world.chunk;

import java.util.List;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public interface IChunkProvider {
   boolean chunkExists(int integer1, int integer2);

   Chunk provideChunk(int integer1, int integer2);

   Chunk provideChunk(BlockPos blockPos);

   void populate(IChunkProvider iChunkProvider, int integer2, int integer3);

   boolean populateChunk(IChunkProvider iChunkProvider, Chunk chunk, int integer3, int integer4);

   boolean saveChunks(boolean boolean1, IProgressUpdate iProgressUpdate);

   boolean unloadQueuedChunks();

   boolean canSave();

   String makeString();

   List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType enumCreatureType, BlockPos blockPos);

   BlockPos getStrongholdGen(World world, String string, BlockPos blockPos);

   int getLoadedChunkCount();

   void recreateStructures(Chunk chunk, int integer2, int integer3);

   void saveExtraData();
}
