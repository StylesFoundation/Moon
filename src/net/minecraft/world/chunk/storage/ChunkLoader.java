package net.minecraft.world.chunk.storage;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.NibbleArray;

public class ChunkLoader {
   public static ChunkLoader.AnvilConverterData load(NBTTagCompound nBTTagCompound) {
      int var3 = nBTTagCompound.getInteger("xPos");
      int var4 = nBTTagCompound.getInteger("zPos");
      ChunkLoader.AnvilConverterData var5 = new ChunkLoader.AnvilConverterData(var3, var4);
      var5.blocks = nBTTagCompound.getByteArray("Blocks");
      var5.data = new NibbleArrayReader(nBTTagCompound.getByteArray("Data"), 7);
      var5.skyLight = new NibbleArrayReader(nBTTagCompound.getByteArray("SkyLight"), 7);
      var5.blockLight = new NibbleArrayReader(nBTTagCompound.getByteArray("BlockLight"), 7);
      var5.heightmap = nBTTagCompound.getByteArray("HeightMap");
      var5.terrainPopulated = nBTTagCompound.getBoolean("TerrainPopulated");
      var5.entities = nBTTagCompound.getTagList("Entities", 10);
      var5.tileEntities = nBTTagCompound.getTagList("TileEntities", 10);
      var5.tileTicks = nBTTagCompound.getTagList("TileTicks", 10);

      try {
         var5.lastUpdated = nBTTagCompound.getLong("LastUpdate");
      } catch (ClassCastException var6) {
         var5.lastUpdated = nBTTagCompound.getInteger("LastUpdate");
      }

      return var5;
   }

   public static void convertToAnvilFormat(
      ChunkLoader.AnvilConverterData anvilConverterData, NBTTagCompound nBTTagCompound, WorldChunkManager worldChunkManager
   ) {
      nBTTagCompound.setInteger("xPos", anvilConverterData.x);
      nBTTagCompound.setInteger("zPos", anvilConverterData.z);
      nBTTagCompound.setLong("LastUpdate", anvilConverterData.lastUpdated);
      int[] var6 = new int[anvilConverterData.heightmap.length];

      for (int var7 = 0; var7 < anvilConverterData.heightmap.length; var7++) {
         var6[var7] = anvilConverterData.heightmap[var7];
      }

      nBTTagCompound.setIntArray("HeightMap", var6);
      nBTTagCompound.setBoolean("TerrainPopulated", anvilConverterData.terrainPopulated);
      NBTTagList var19 = new NBTTagList();

      for (int var8 = 0; var8 < 8; var8++) {
         boolean var9 = true;

         for (int var10 = 0; var10 < 16 && var9; var10++) {
            for (int var11 = 0; var11 < 16 && var9; var11++) {
               for (int var12 = 0; var12 < 16; var12++) {
                  int var13 = var10 << 11 | var12 << 7 | var11 + (var8 << 4);
                  byte var14 = anvilConverterData.blocks[var13];
                  if (var14 != 0) {
                     var9 = false;
                     break;
                  }
               }
            }
         }

         if (!var9) {
            byte[] var22 = new byte[4096];
            NibbleArray var24 = new NibbleArray();
            NibbleArray var26 = new NibbleArray();
            NibbleArray var27 = new NibbleArray();

            for (int var28 = 0; var28 < 16; var28++) {
               for (int var15 = 0; var15 < 16; var15++) {
                  for (int var16 = 0; var16 < 16; var16++) {
                     int var17 = var28 << 11 | var16 << 7 | var15 + (var8 << 4);
                     byte var18 = anvilConverterData.blocks[var17];
                     var22[var15 << 8 | var16 << 4 | var28] = (byte)(var18 & 0xFF);
                     var24.set(var28, var15, var16, anvilConverterData.data.get(var28, var15 + (var8 << 4), var16));
                     var26.set(var28, var15, var16, anvilConverterData.skyLight.get(var28, var15 + (var8 << 4), var16));
                     var27.set(var28, var15, var16, anvilConverterData.blockLight.get(var28, var15 + (var8 << 4), var16));
                  }
               }
            }

            NBTTagCompound var29 = new NBTTagCompound();
            var29.setByte("Y", (byte)(var8 & 0xFF));
            var29.setByteArray("Blocks", var22);
            var29.setByteArray("Data", var24.getData());
            var29.setByteArray("SkyLight", var26.getData());
            var29.setByteArray("BlockLight", var27.getData());
            var19.appendTag(var29);
         }
      }

      nBTTagCompound.setTag("Sections", var19);
      byte[] var20 = new byte[256];
      BlockPos.MutableBlockPos var21 = new BlockPos.MutableBlockPos();

      for (int var23 = 0; var23 < 16; var23++) {
         for (int var25 = 0; var25 < 16; var25++) {
            var21.set(anvilConverterData.x << 4 | var23, 0, anvilConverterData.z << 4 | var25);
            var20[var25 << 4 | var23] = (byte)(worldChunkManager.getBiomeGenerator(var21, BiomeGenBase.field_180279_ad).biomeID & 0xFF);
         }
      }

      nBTTagCompound.setByteArray("Biomes", var20);
      nBTTagCompound.setTag("Entities", anvilConverterData.entities);
      nBTTagCompound.setTag("TileEntities", anvilConverterData.tileEntities);
      if (anvilConverterData.tileTicks != null) {
         nBTTagCompound.setTag("TileTicks", anvilConverterData.tileTicks);
      }
   }

   public static class AnvilConverterData {
      public long lastUpdated;
      public boolean terrainPopulated;
      public byte[] heightmap;
      public NibbleArrayReader blockLight;
      public NibbleArrayReader skyLight;
      public NibbleArrayReader data;
      public byte[] blocks;
      public NBTTagList entities;
      public NBTTagList tileEntities;
      public NBTTagList tileTicks;
      public final int x;
      public final int z;

      public AnvilConverterData(int integer1, int integer2) {
         this.x = integer1;
         this.z = integer2;
      }
   }
}
