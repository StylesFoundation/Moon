package net.minecraft.world.chunk.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.NibbleArray;
import net.minecraft.world.storage.IThreadedFileIO;
import net.minecraft.world.storage.ThreadedFileIOBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnvilChunkLoader implements IChunkLoader, IThreadedFileIO {
   private static final Logger logger = LogManager.getLogger();
   private final Map<ChunkCoordIntPair, NBTTagCompound> chunksToRemove = new ConcurrentHashMap<>();
   private final Set<ChunkCoordIntPair> pendingAnvilChunksCoordinates = Collections.newSetFromMap(new ConcurrentHashMap<>());
   private final File chunkSaveLocation;
   private boolean field_183014_e = false;

   public AnvilChunkLoader(File file) {
      this.chunkSaveLocation = file;
   }

   @Override
   public Chunk loadChunk(World world, int integer2, int integer3) throws IOException {
      ChunkCoordIntPair var7 = new ChunkCoordIntPair(integer2, integer3);
      NBTTagCompound var8 = this.chunksToRemove.get(var7);
      if (var8 == null) {
         DataInputStream var9 = RegionFileCache.getChunkInputStream(this.chunkSaveLocation, integer2, integer3);
         if (var9 == null) {
            return null;
         }

         var8 = CompressedStreamTools.read(var9);
      }

      return this.checkedReadChunkFromNBT(world, integer2, integer3, var8);
   }

   protected Chunk checkedReadChunkFromNBT(World world, int integer2, int integer3, NBTTagCompound nBTTagCompound) {
      if (!nBTTagCompound.hasKey("Level", 10)) {
         logger.error("Chunk file at " + integer2 + "," + integer3 + " is missing level data, skipping");
         return null;
      }

      NBTTagCompound var8 = nBTTagCompound.getCompoundTag("Level");
      if (!var8.hasKey("Sections", 9)) {
         logger.error("Chunk file at " + integer2 + "," + integer3 + " is missing block data, skipping");
         return null;
      }

      Chunk var9 = this.readChunkFromNBT(world, var8);
      if (!var9.isAtLocation(integer2, integer3)) {
         logger.error(
            "Chunk file at "
               + integer2
               + ","
               + integer3
               + " is in the wrong location; relocating. (Expected "
               + integer2
               + ", "
               + integer3
               + ", got "
               + var9.xPosition
               + ", "
               + var9.zPosition
               + ")"
         );
         var8.setInteger("xPos", integer2);
         var8.setInteger("zPos", integer3);
         var9 = this.readChunkFromNBT(world, var8);
      }

      return var9;
   }

   @Override
   public void saveChunk(World world, Chunk chunk) throws MinecraftException, IOException {
      world.checkSessionLock();

      try {
         NBTTagCompound var3 = new NBTTagCompound();
         NBTTagCompound var4 = new NBTTagCompound();
         var3.setTag("Level", var4);
         this.writeChunkToNBT(chunk, world, var4);
         this.addChunkToPending(chunk.getChunkCoordIntPair(), var3);
      } catch (Exception var5) {
         logger.error("Failed to save chunk", var5);
      }
   }

   protected void addChunkToPending(ChunkCoordIntPair chunkCoordIntPair, NBTTagCompound nBTTagCompound) {
      if (!this.pendingAnvilChunksCoordinates.contains(chunkCoordIntPair)) {
         this.chunksToRemove.put(chunkCoordIntPair, nBTTagCompound);
      }

      ThreadedFileIOBase.getThreadedIOInstance().queueIO(this);
   }

   @Override
   public boolean writeNextIO() {
      if (this.chunksToRemove.isEmpty()) {
         if (this.field_183014_e) {
            logger.info("ThreadedAnvilChunkStorage ({}): All chunks are saved", new Object[]{this.chunkSaveLocation.getName()});
         }

         return false;
      } else {
         ChunkCoordIntPair var4 = this.chunksToRemove.keySet().iterator().next();

         try {
            this.pendingAnvilChunksCoordinates.add(var4);
            NBTTagCompound var6 = this.chunksToRemove.remove(var4);
            if (var6 != null) {
               try {
                  this.func_183013_b(var4, var6);
               } catch (Exception var11) {
                  logger.error("Failed to save chunk", var11);
               }
            }
         } finally {
            this.pendingAnvilChunksCoordinates.remove(var4);
         }

         return true;
      }
   }

   private void func_183013_b(ChunkCoordIntPair chunkCoordIntPair, NBTTagCompound nBTTagCompound) throws IOException {
      DataOutputStream var3 = RegionFileCache.getChunkOutputStream(this.chunkSaveLocation, chunkCoordIntPair.chunkXPos, chunkCoordIntPair.chunkZPos);
      CompressedStreamTools.write(nBTTagCompound, var3);
      var3.close();
   }

   @Override
   public void saveExtraChunkData(World world, Chunk chunk) throws IOException {
   }

   @Override
   public void chunkTick() {
   }

   @Override
   public void saveExtraData() {
      try {
         this.field_183014_e = true;

         while (true) {
            if (this.writeNextIO()) {
            }
         }
      } finally {
         this.field_183014_e = false;
      }
   }

   private void writeChunkToNBT(Chunk chunk, World world, NBTTagCompound nBTTagCompound) {
      nBTTagCompound.setByte("V", (byte)1);
      nBTTagCompound.setInteger("xPos", chunk.xPosition);
      nBTTagCompound.setInteger("zPos", chunk.zPosition);
      nBTTagCompound.setLong("LastUpdate", world.getTotalWorldTime());
      nBTTagCompound.setIntArray("HeightMap", chunk.getHeightMap());
      nBTTagCompound.setBoolean("TerrainPopulated", chunk.isTerrainPopulated());
      nBTTagCompound.setBoolean("LightPopulated", chunk.isLightPopulated());
      nBTTagCompound.setLong("InhabitedTime", chunk.getInhabitedTime());
      ExtendedBlockStorage[] var7 = chunk.getBlockStorageArray();
      NBTTagList var8 = new NBTTagList();
      boolean var9 = !world.provider.getHasNoSky();

      for (ExtendedBlockStorage var13 : var7) {
         if (var13 != null) {
            NBTTagCompound var14 = new NBTTagCompound();
            var14.setByte("Y", (byte)(var13.getYLocation() >> 4 & 0xFF));
            byte[] var15 = new byte[var13.getData().length];
            NibbleArray var16 = new NibbleArray();
            NibbleArray var17 = null;

            for (int var18 = 0; var18 < var13.getData().length; var18++) {
               char var19 = var13.getData()[var18];
               int var20 = var18 & 15;
               int var21 = var18 >> 8 & 15;
               int var22 = var18 >> 4 & 15;
               if (var19 >> '\f' != 0) {
                  if (var17 == null) {
                     var17 = new NibbleArray();
                  }

                  var17.set(var20, var21, var22, var19 >> '\f');
               }

               var15[var18] = (byte)(var19 >> 4 & 0xFF);
               var16.set(var20, var21, var22, var19 & 15);
            }

            var14.setByteArray("Blocks", var15);
            var14.setByteArray("Data", var16.getData());
            if (var17 != null) {
               var14.setByteArray("Add", var17.getData());
            }

            var14.setByteArray("BlockLight", var13.getBlocklightArray().getData());
            if (var9) {
               var14.setByteArray("SkyLight", var13.getSkylightArray().getData());
            } else {
               var14.setByteArray("SkyLight", new byte[var13.getBlocklightArray().getData().length]);
            }

            var8.appendTag(var14);
         }
      }

      nBTTagCompound.setTag("Sections", var8);
      nBTTagCompound.setByteArray("Biomes", chunk.getBiomeArray());
      chunk.setHasEntities(false);
      NBTTagList var23 = new NBTTagList();

      for (int var24 = 0; var24 < chunk.getEntityLists().length; var24++) {
         for (Entity var29 : chunk.getEntityLists()[var24]) {
            NBTTagCompound var32 = new NBTTagCompound();
            if (var29.writeToNBTOptional(var32)) {
               chunk.setHasEntities(true);
               var23.appendTag(var32);
            }
         }
      }

      nBTTagCompound.setTag("Entities", var23);
      NBTTagList var25 = new NBTTagList();

      for (TileEntity var30 : chunk.getTileEntityMap().values()) {
         NBTTagCompound var33 = new NBTTagCompound();
         var30.writeToNBT(var33);
         var25.appendTag(var33);
      }

      nBTTagCompound.setTag("TileEntities", var25);
      List<NextTickListEntry> var28 = world.getPendingBlockUpdates(chunk, false);
      if (var28 != null) {
         long var31 = world.getTotalWorldTime();
         NBTTagList var34 = new NBTTagList();

         for (NextTickListEntry var36 : var28) {
            NBTTagCompound var37 = new NBTTagCompound();
            ResourceLocation var38 = Block.blockRegistry.getNameForObject(var36.getBlock());
            var37.setString("i", var38 == null ? "" : var38.toString());
            var37.setInteger("x", var36.position.getX());
            var37.setInteger("y", var36.position.getY());
            var37.setInteger("z", var36.position.getZ());
            var37.setInteger("t", (int)(var36.scheduledTime - var31));
            var37.setInteger("p", var36.priority);
            var34.appendTag(var37);
         }

         nBTTagCompound.setTag("TileTicks", var34);
      }
   }

   private Chunk readChunkFromNBT(World world, NBTTagCompound nBTTagCompound) {
      int var6 = nBTTagCompound.getInteger("xPos");
      int var7 = nBTTagCompound.getInteger("zPos");
      Chunk var8 = new Chunk(world, var6, var7);
      var8.setHeightMap(nBTTagCompound.getIntArray("HeightMap"));
      var8.setTerrainPopulated(nBTTagCompound.getBoolean("TerrainPopulated"));
      var8.setLightPopulated(nBTTagCompound.getBoolean("LightPopulated"));
      var8.setInhabitedTime(nBTTagCompound.getLong("InhabitedTime"));
      NBTTagList var9 = nBTTagCompound.getTagList("Sections", 10);
      ExtendedBlockStorage[] var11 = new ExtendedBlockStorage[16];
      boolean var12 = !world.provider.getHasNoSky();

      for (int var13 = 0; var13 < var9.tagCount(); var13++) {
         NBTTagCompound var14 = var9.getCompoundTagAt(var13);
         byte var15 = var14.getByte("Y");
         ExtendedBlockStorage var16 = new ExtendedBlockStorage(var15 << 4, var12);
         byte[] var17 = var14.getByteArray("Blocks");
         NibbleArray var18 = new NibbleArray(var14.getByteArray("Data"));
         NibbleArray var19 = var14.hasKey("Add", 7) ? new NibbleArray(var14.getByteArray("Add")) : null;
         char[] var20 = new char[var17.length];

         for (int var21 = 0; var21 < var20.length; var21++) {
            int var22 = var21 & 15;
            int var23 = var21 >> 8 & 15;
            int var24 = var21 >> 4 & 15;
            int var25 = var19 != null ? var19.get(var22, var23, var24) : 0;
            var20[var21] = (char)(var25 << 12 | (var17[var21] & 255) << 4 | var18.get(var22, var23, var24));
         }

         var16.setData(var20);
         var16.setBlocklightArray(new NibbleArray(var14.getByteArray("BlockLight")));
         if (var12) {
            var16.setSkylightArray(new NibbleArray(var14.getByteArray("SkyLight")));
         }

         var16.removeInvalidBlocks();
         var11[var15] = var16;
      }

      var8.setStorageArrays(var11);
      if (nBTTagCompound.hasKey("Biomes", 7)) {
         var8.setBiomeArray(nBTTagCompound.getByteArray("Biomes"));
      }

      NBTTagList var26 = nBTTagCompound.getTagList("Entities", 10);
      if (var26 != null) {
         for (int var27 = 0; var27 < var26.tagCount(); var27++) {
            NBTTagCompound var29 = var26.getCompoundTagAt(var27);
            Entity var32 = EntityList.createEntityFromNBT(var29, world);
            var8.setHasEntities(true);
            if (var32 != null) {
               var8.addEntity(var32);
               Entity var34 = var32;

               for (NBTTagCompound var37 = var29; var37.hasKey("Riding", 10); var37 = var37.getCompoundTag("Riding")) {
                  Entity var39 = EntityList.createEntityFromNBT(var37.getCompoundTag("Riding"), world);
                  if (var39 != null) {
                     var8.addEntity(var39);
                     var34.mountEntity(var39);
                  }

                  var34 = var39;
               }
            }
         }
      }

      NBTTagList var28 = nBTTagCompound.getTagList("TileEntities", 10);
      if (var28 != null) {
         for (int var30 = 0; var30 < var28.tagCount(); var30++) {
            TileEntity var35 = TileEntity.createAndLoadEntity(var28.getCompoundTagAt(var30));
            if (var35 != null) {
               var8.addTileEntity(var35);
            }
         }
      }

      if (nBTTagCompound.hasKey("TileTicks", 9)) {
         NBTTagList var31 = nBTTagCompound.getTagList("TileTicks", 10);
         if (var31 != null) {
            for (int var33 = 0; var33 < var31.tagCount(); var33++) {
               NBTTagCompound var36 = var31.getCompoundTagAt(var33);
               Block var38;
               if (var36.hasKey("i", 8)) {
                  var38 = Block.getBlockFromName(var36.getString("i"));
               } else {
                  var38 = Block.getBlockById(var36.getInteger("i"));
               }

               world.scheduleBlockUpdate(
                  new BlockPos(var36.getInteger("x"), var36.getInteger("y"), var36.getInteger("z")), var38, var36.getInteger("t"), var36.getInteger("p")
               );
            }
         }
      }

      return var8;
   }
}
