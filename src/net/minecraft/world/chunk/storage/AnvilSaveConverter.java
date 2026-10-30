package net.minecraft.world.chunk.storage;

import com.google.common.collect.Lists;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveFormatComparator;
import net.minecraft.world.storage.SaveFormatOld;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnvilSaveConverter extends SaveFormatOld {
   private static final Logger logger = LogManager.getLogger();

   public AnvilSaveConverter(File file) {
      super(file);
   }

   @Override
   public String getName() {
      return "Anvil";
   }

   @Override
   public List<SaveFormatComparator> getSaveList() throws AnvilConverterException {
      if (this.savesDirectory != null && this.savesDirectory.exists() && this.savesDirectory.isDirectory()) {
         ArrayList var4 = Lists.newArrayList();

         for (File var9 : this.savesDirectory.listFiles()) {
            if (var9.isDirectory()) {
               String var10 = var9.getName();
               WorldInfo var11 = this.getWorldInfo(var10);
               if (var11 != null && (var11.getSaveVersion() == 19132 || var11.getSaveVersion() == 19133)) {
                  boolean var12 = var11.getSaveVersion() != this.getSaveVersion();
                  String var13 = var11.getWorldName();
                  if (StringUtils.isEmpty(var13)) {
                     var13 = var10;
                  }

                  var4.add(
                     new SaveFormatComparator(
                        var10, var13, var11.getLastTimePlayed(), 0L, var11.getGameType(), var12, var11.isHardcoreModeEnabled(), var11.areCommandsAllowed()
                     )
                  );
               }
            }
         }

         return var4;
      } else {
         throw new AnvilConverterException("Unable to read or access folder where game worlds are saved!");
      }
   }

   protected int getSaveVersion() {
      return 19133;
   }

   @Override
   public void flushCache() {
      RegionFileCache.clearRegionFileReferences();
   }

   @Override
   public ISaveHandler getSaveLoader(String string, boolean boolean2) {
      return new AnvilSaveHandler(this.savesDirectory, string, boolean2);
   }

   @Override
   public boolean isConvertible(String string) {
      WorldInfo var5 = this.getWorldInfo(string);
      return var5 != null && var5.getSaveVersion() == 19132;
   }

   @Override
   public boolean isOldMapFormat(String string) {
      WorldInfo var5 = this.getWorldInfo(string);
      return var5 != null && var5.getSaveVersion() != this.getSaveVersion();
   }

   @Override
   public boolean convertMapFormat(String string, IProgressUpdate iProgressUpdate) {
      iProgressUpdate.setLoadingProgress(0);
      ArrayList var6 = Lists.newArrayList();
      ArrayList var7 = Lists.newArrayList();
      ArrayList var8 = Lists.newArrayList();
      File var9 = new File(this.savesDirectory, string);
      File var10 = new File(var9, "DIM-1");
      File var11 = new File(var9, "DIM1");
      logger.info("Scanning folders...");
      this.addRegionFilesToCollection(var9, var6);
      if (var10.exists()) {
         this.addRegionFilesToCollection(var10, var7);
      }

      if (var11.exists()) {
         this.addRegionFilesToCollection(var11, var8);
      }

      int var12 = var6.size() + var7.size() + var8.size();
      logger.info("Total conversion count is " + var12);
      WorldInfo var13 = this.getWorldInfo(string);
      WorldChunkManager var14;
      if (var13.getTerrainType() == WorldType.FLAT) {
         var14 = new WorldChunkManagerHell(BiomeGenBase.plains, 0.5F);
      } else {
         var14 = new WorldChunkManager(var13.getSeed(), var13.getTerrainType(), var13.getGeneratorOptions());
      }

      this.convertFile(new File(var9, "region"), var6, var14, 0, var12, iProgressUpdate);
      this.convertFile(new File(var10, "region"), var7, new WorldChunkManagerHell(BiomeGenBase.hell, 0.0F), var6.size(), var12, iProgressUpdate);
      this.convertFile(new File(var11, "region"), var8, new WorldChunkManagerHell(BiomeGenBase.sky, 0.0F), var6.size() + var7.size(), var12, iProgressUpdate);
      var13.setSaveVersion(19133);
      if (var13.getTerrainType() == WorldType.DEFAULT_1_1) {
         var13.setTerrainType(WorldType.DEFAULT);
      }

      this.createFile(string);
      ISaveHandler var15 = this.getSaveLoader(string, false);
      var15.saveWorldInfo(var13);
      return true;
   }

   private void createFile(String string) {
      File var5 = new File(this.savesDirectory, string);
      if (!var5.exists()) {
         logger.warn("Unable to create level.dat_mcr backup");
      } else {
         File var6 = new File(var5, "level.dat");
         if (!var6.exists()) {
            logger.warn("Unable to create level.dat_mcr backup");
         } else {
            File var7 = new File(var5, "level.dat_mcr");
            if (!var6.renameTo(var7)) {
               logger.warn("Unable to create level.dat_mcr backup");
            }
         }
      }
   }

   private void convertFile(
      File file, Iterable<File> iterable, WorldChunkManager worldChunkManager, int integer4, int integer5, IProgressUpdate iProgressUpdate
   ) {
      for (File var11 : iterable) {
         this.convertChunks(file, var11, worldChunkManager, integer4, integer5, iProgressUpdate);
         integer4++;
         int var12 = (int)Math.round(100.0 * integer4 / integer5);
         iProgressUpdate.setLoadingProgress(var12);
      }
   }

   private void convertChunks(File file1, File file2, WorldChunkManager worldChunkManager, int integer4, int integer5, IProgressUpdate iProgressUpdate) {
      try {
         String var10 = file2.getName();
         RegionFile var11 = new RegionFile(file2);
         RegionFile var12 = new RegionFile(new File(file1, var10.substring(0, var10.length() - 4) + ".mca"));

         for (int var13 = 0; var13 < 32; var13++) {
            for (int var14 = 0; var14 < 32; var14++) {
               if (var11.isChunkSaved(var13, var14) && !var12.isChunkSaved(var13, var14)) {
                  DataInputStream var15 = var11.getChunkDataInputStream(var13, var14);
                  if (var15 == null) {
                     logger.warn("Failed to fetch input stream");
                  } else {
                     NBTTagCompound var16 = CompressedStreamTools.read(var15);
                     var15.close();
                     ChunkLoader.AnvilConverterData var18 = ChunkLoader.load(var16.getCompoundTag("Level"));
                     NBTTagCompound var19 = new NBTTagCompound();
                     NBTTagCompound var20 = new NBTTagCompound();
                     var19.setTag("Level", var20);
                     ChunkLoader.convertToAnvilFormat(var18, var20, worldChunkManager);
                     DataOutputStream var21 = var12.getChunkDataOutputStream(var13, var14);
                     CompressedStreamTools.write(var19, var21);
                     var21.close();
                  }
               }
            }

            int var23 = (int)Math.round(100.0 * (integer4 * 1024) / (integer5 * 1024));
            int var24 = (int)Math.round(100.0 * ((var13 + 1) * 32 + integer4 * 1024) / (integer5 * 1024));
            if (var24 > var23) {
               iProgressUpdate.setLoadingProgress(var24);
            }
         }

         var11.close();
         var12.close();
      } catch (IOException var22) {
         var22.printStackTrace();
      }
   }

   private void addRegionFilesToCollection(File file, Collection<File> collection) {
      File[] var6 = new File(file, "region").listFiles((dir, string) -> string.endsWith(".mcr"));
      if (var6 != null) {
         Collections.addAll(collection, var6);
      }
   }
}
