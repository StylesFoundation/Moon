package net.minecraft.world.chunk.storage;

import java.io.IOException;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public interface IChunkLoader {
   Chunk loadChunk(World world, int integer2, int integer3) throws IOException;

   void saveChunk(World world, Chunk chunk) throws MinecraftException, IOException;

   void saveExtraChunkData(World world, Chunk chunk) throws IOException;

   void chunkTick();

   void saveExtraData();
}
