package net.minecraft.world.gen;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.EmptyChunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkProviderServer implements IChunkProvider {
   private static final Logger logger = LogManager.getLogger();
   private final Set<Long> droppedChunksSet = Collections.newSetFromMap(new ConcurrentHashMap<>());
   private final Chunk dummyChunk;
   private final IChunkProvider serverChunkGenerator;
   private final IChunkLoader chunkLoader;
   public boolean chunkLoadOverride = true;
   private final LongHashMap<Chunk> id2ChunkMap = new LongHashMap<>();
   private final List<Chunk> loadedChunks = Lists.newArrayList();
   private final WorldServer worldObj;

   public ChunkProviderServer(WorldServer worldServer, IChunkLoader iChunkLoader, IChunkProvider iChunkProvider) {
      this.dummyChunk = new EmptyChunk(worldServer, 0, 0);
      this.worldObj = worldServer;
      this.chunkLoader = iChunkLoader;
      this.serverChunkGenerator = iChunkProvider;
   }

   @Override
   public boolean chunkExists(int integer1, int integer2) {
      return this.id2ChunkMap.containsItem(ChunkCoordIntPair.chunkXZ2Int(integer1, integer2));
   }

   public List<Chunk> func_152380_a() {
      return this.loadedChunks;
   }

   public void dropChunk(int integer1, int integer2) {
      if (this.worldObj.provider.canRespawnHere()) {
         if (!this.worldObj.isSpawnChunk(integer1, integer2)) {
            this.droppedChunksSet.add(ChunkCoordIntPair.chunkXZ2Int(integer1, integer2));
         }
      } else {
         this.droppedChunksSet.add(ChunkCoordIntPair.chunkXZ2Int(integer1, integer2));
      }
   }

   public void unloadAllChunks() {
      for (Chunk var5 : this.loadedChunks) {
         this.dropChunk(var5.xPosition, var5.zPosition);
      }
   }

   public Chunk loadChunk(int integer1, int integer2) {
      long var6 = ChunkCoordIntPair.chunkXZ2Int(integer1, integer2);
      this.droppedChunksSet.remove(var6);
      Chunk var8 = this.id2ChunkMap.getValueByKey(var6);
      if (var8 == null) {
         var8 = this.loadChunkFromFile(integer1, integer2);
         if (var8 == null) {
            if (this.serverChunkGenerator == null) {
               var8 = this.dummyChunk;
            } else {
               try {
                  var8 = this.serverChunkGenerator.provideChunk(integer1, integer2);
               } catch (Throwable var12) {
                  CrashReport var10 = CrashReport.makeCrashReport(var12, "Exception generating new chunk");
                  CrashReportCategory var11 = var10.makeCategory("Chunk to be generated");
                  var11.addCrashSection("Location", String.format("%d,%d", integer1, integer2));
                  var11.addCrashSection("Position hash", var6);
                  var11.addCrashSection("Generator", this.serverChunkGenerator.makeString());
                  throw new ReportedException(var10);
               }
            }
         }

         this.id2ChunkMap.add(var6, var8);
         this.loadedChunks.add(var8);
         var8.onChunkLoad();
         var8.populateChunk(this, this, integer1, integer2);
      }

      return var8;
   }

   @Override
   public Chunk provideChunk(int integer1, int integer2) {
      Chunk var6 = this.id2ChunkMap.getValueByKey(ChunkCoordIntPair.chunkXZ2Int(integer1, integer2));
      return var6 == null ? (!this.worldObj.isFindingSpawnPoint() && !this.chunkLoadOverride ? this.dummyChunk : this.loadChunk(integer1, integer2)) : var6;
   }

   private Chunk loadChunkFromFile(int integer1, int integer2) {
      if (this.chunkLoader == null) {
         return null;
      }

      try {
         Chunk var6 = this.chunkLoader.loadChunk(this.worldObj, integer1, integer2);
         if (var6 != null) {
            var6.setLastSaveTime(this.worldObj.getTotalWorldTime());
            if (this.serverChunkGenerator != null) {
               this.serverChunkGenerator.recreateStructures(var6, integer1, integer2);
            }
         }

         return var6;
      } catch (Exception var7) {
         logger.error("Couldn't load chunk", var7);
         return null;
      }
   }

   private void saveChunkExtraData(Chunk chunk) {
      if (this.chunkLoader != null) {
         try {
            this.chunkLoader.saveExtraChunkData(this.worldObj, chunk);
         } catch (Exception var6) {
            logger.error("Couldn't save entities", var6);
         }
      }
   }

   private void saveChunkData(Chunk chunk) {
      if (this.chunkLoader != null) {
         try {
            chunk.setLastSaveTime(this.worldObj.getTotalWorldTime());
            this.chunkLoader.saveChunk(this.worldObj, chunk);
         } catch (IOException var6) {
            logger.error("Couldn't save chunk", var6);
         } catch (MinecraftException var7) {
            logger.error("Couldn't save chunk; already in use by another instance of Minecraft?", var7);
         }
      }
   }

   @Override
   public void populate(IChunkProvider iChunkProvider, int integer2, int integer3) {
      Chunk var7 = this.provideChunk(integer2, integer3);
      if (!var7.isTerrainPopulated()) {
         var7.func_150809_p();
         if (this.serverChunkGenerator != null) {
            this.serverChunkGenerator.populate(iChunkProvider, integer2, integer3);
            var7.setChunkModified();
         }
      }
   }

   @Override
   public boolean populateChunk(IChunkProvider iChunkProvider, Chunk chunk, int integer3, int integer4) {
      if (this.serverChunkGenerator != null && this.serverChunkGenerator.populateChunk(iChunkProvider, chunk, integer3, integer4)) {
         this.provideChunk(integer3, integer4).setChunkModified();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean saveChunks(boolean boolean1, IProgressUpdate iProgressUpdate) {
      int var6 = 0;
      ArrayList var7 = Lists.newArrayList(this.loadedChunks);

      for (int var8 = 0; var8 < var7.size(); var8++) {
         Chunk var9 = (Chunk)var7.get(var8);
         if (boolean1) {
            this.saveChunkExtraData(var9);
         }

         if (var9.needsSaving(boolean1)) {
            this.saveChunkData(var9);
            var9.setModified(false);
            if (++var6 == 24 && !boolean1) {
               return false;
            }
         }
      }

      return true;
   }

   @Override
   public void saveExtraData() {
      if (this.chunkLoader != null) {
         this.chunkLoader.saveExtraData();
      }
   }

   @Override
   public boolean unloadQueuedChunks() {
      if (!this.worldObj.disableLevelSaving) {
         for (int var4 = 0; var4 < 100; var4++) {
            if (!this.droppedChunksSet.isEmpty()) {
               Long var5 = this.droppedChunksSet.iterator().next();
               Chunk var6 = this.id2ChunkMap.getValueByKey(var5);
               if (var6 != null) {
                  var6.onChunkUnload();
                  this.saveChunkData(var6);
                  this.saveChunkExtraData(var6);
                  this.id2ChunkMap.remove(var5);
                  this.loadedChunks.remove(var6);
               }

               this.droppedChunksSet.remove(var5);
            }
         }

         if (this.chunkLoader != null) {
            this.chunkLoader.chunkTick();
         }
      }

      return this.serverChunkGenerator.unloadQueuedChunks();
   }

   @Override
   public boolean canSave() {
      return !this.worldObj.disableLevelSaving;
   }

   @Override
   public String makeString() {
      return "ServerChunkCache: " + this.id2ChunkMap.getNumHashElements() + " Drop: " + this.droppedChunksSet.size();
   }

   @Override
   public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType enumCreatureType, BlockPos blockPos) {
      return this.serverChunkGenerator.getPossibleCreatures(enumCreatureType, blockPos);
   }

   @Override
   public BlockPos getStrongholdGen(World world, String string, BlockPos blockPos) {
      return this.serverChunkGenerator.getStrongholdGen(world, string, blockPos);
   }

   @Override
   public int getLoadedChunkCount() {
      return this.id2ChunkMap.getNumHashElements();
   }

   @Override
   public void recreateStructures(Chunk chunk, int integer2, int integer3) {
   }

   @Override
   public Chunk provideChunk(BlockPos blockPos) {
      return this.provideChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
   }
}
