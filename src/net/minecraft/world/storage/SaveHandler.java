package net.minecraft.world.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.storage.IChunkLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveHandler implements ISaveHandler, IPlayerFileData {
   private static final Logger logger = LogManager.getLogger();
   private final File worldDirectory;
   private final File playersDirectory;
   private final File mapDataDir;
   private final long initializationTime = MinecraftServer.getCurrentTimeMillis();
   private final String saveDirectoryName;

   public SaveHandler(File file, String string, boolean boolean3) {
      this.worldDirectory = new File(file, string);
      this.worldDirectory.mkdirs();
      this.playersDirectory = new File(this.worldDirectory, "playerdata");
      this.mapDataDir = new File(this.worldDirectory, "data");
      this.mapDataDir.mkdirs();
      this.saveDirectoryName = string;
      if (boolean3) {
         this.playersDirectory.mkdirs();
      }

      this.setSessionLock();
   }

   private void setSessionLock() {
      try {
         File var1 = new File(this.worldDirectory, "session.lock");

         try (DataOutputStream var2 = new DataOutputStream(new FileOutputStream(var1))) {
            var2.writeLong(this.initializationTime);
         }
      } catch (IOException var7) {
         var7.printStackTrace();
         throw new RuntimeException("Failed to check session lock, aborting");
      }
   }

   @Override
   public File getWorldDirectory() {
      return this.worldDirectory;
   }

   @Override
   public void checkSessionLock() throws MinecraftException {
      try {
         File var4 = new File(this.worldDirectory, "session.lock");

         try (DataInputStream var5 = new DataInputStream(new FileInputStream(var4))) {
            if (var5.readLong() != this.initializationTime) {
               throw new MinecraftException("The save is being accessed from another location, aborting");
            }
         }
      } catch (IOException var10) {
         throw new MinecraftException("Failed to check session lock, aborting");
      }
   }

   @Override
   public IChunkLoader getChunkLoader(WorldProvider worldProvider) {
      throw new RuntimeException("Old Chunk Storage is no longer supported.");
   }

   @Override
   public WorldInfo loadWorldInfo() {
      File var4 = new File(this.worldDirectory, "level.dat");
      if (var4.exists()) {
         try {
            NBTTagCompound var10 = CompressedStreamTools.readCompressed(new FileInputStream(var4)).getCompoundTag("Data");
            return new WorldInfo(var10);
         } catch (Exception var8) {
            var8.printStackTrace();
         }
      }

      var4 = new File(this.worldDirectory, "level.dat_old");
      if (var4.exists()) {
         try {
            NBTTagCompound var6 = CompressedStreamTools.readCompressed(new FileInputStream(var4)).getCompoundTag("Data");
            return new WorldInfo(var6);
         } catch (Exception var7) {
            var7.printStackTrace();
         }
      }

      return null;
   }

   @Override
   public void saveWorldInfoWithPlayer(WorldInfo worldInfo, NBTTagCompound nBTTagCompound) {
      NBTTagCompound var6 = worldInfo.cloneNBTCompound(nBTTagCompound);
      NBTTagCompound var7 = new NBTTagCompound();
      var7.setTag("Data", var6);

      try {
         File var8 = new File(this.worldDirectory, "level.dat_new");
         File var9 = new File(this.worldDirectory, "level.dat_old");
         File var10 = new File(this.worldDirectory, "level.dat");
         CompressedStreamTools.writeCompressed(var7, new FileOutputStream(var8));
         if (var9.exists()) {
            var9.delete();
         }

         var10.renameTo(var9);
         if (var10.exists()) {
            var10.delete();
         }

         var8.renameTo(var10);
         if (var8.exists()) {
            var8.delete();
         }
      } catch (Exception var11) {
         var11.printStackTrace();
      }
   }

   @Override
   public void saveWorldInfo(WorldInfo worldInfo) {
      NBTTagCompound var5 = worldInfo.getNBTTagCompound();
      NBTTagCompound var6 = new NBTTagCompound();
      var6.setTag("Data", var5);

      try {
         File var7 = new File(this.worldDirectory, "level.dat_new");
         File var8 = new File(this.worldDirectory, "level.dat_old");
         File var9 = new File(this.worldDirectory, "level.dat");
         CompressedStreamTools.writeCompressed(var6, new FileOutputStream(var7));
         if (var8.exists()) {
            var8.delete();
         }

         var9.renameTo(var8);
         if (var9.exists()) {
            var9.delete();
         }

         var7.renameTo(var9);
         if (var7.exists()) {
            var7.delete();
         }
      } catch (Exception var10) {
         var10.printStackTrace();
      }
   }

   @Override
   public void writePlayerData(EntityPlayer entityPlayer) {
      try {
         NBTTagCompound var5 = new NBTTagCompound();
         entityPlayer.writeToNBT(var5);
         File var6 = new File(this.playersDirectory, entityPlayer.getUniqueID().toString() + ".dat.tmp");
         File var7 = new File(this.playersDirectory, entityPlayer.getUniqueID().toString() + ".dat");
         CompressedStreamTools.writeCompressed(var5, new FileOutputStream(var6));
         if (var7.exists()) {
            var7.delete();
         }

         var6.renameTo(var7);
      } catch (Exception var8) {
         logger.warn("Failed to save player data for " + entityPlayer.getName());
      }
   }

   @Override
   public NBTTagCompound readPlayerData(EntityPlayer entityPlayer) {
      NBTTagCompound var5 = null;

      try {
         File var6 = new File(this.playersDirectory, entityPlayer.getUniqueID().toString() + ".dat");
         if (var6.exists() && var6.isFile()) {
            var5 = CompressedStreamTools.readCompressed(new FileInputStream(var6));
         }
      } catch (Exception var7) {
         logger.warn("Failed to load player data for " + entityPlayer.getName());
      }

      if (var5 != null) {
         entityPlayer.readFromNBT(var5);
      }

      return var5;
   }

   @Override
   public IPlayerFileData getPlayerNBTManager() {
      return this;
   }

   @Override
   public String[] getAvailablePlayerDat() {
      String[] var4 = this.playersDirectory.list();
      if (var4 == null) {
         var4 = new String[0];
      }

      for (int var5 = 0; var5 < var4.length; var5++) {
         if (var4[var5].endsWith(".dat")) {
            var4[var5] = var4[var5].substring(0, var4[var5].length() - 4);
         }
      }

      return var4;
   }

   @Override
   public void flush() {
   }

   @Override
   public File getMapFileFromName(String string) {
      return new File(this.mapDataDir, string + ".dat");
   }

   @Override
   public String getWorldDirectoryName() {
      return this.saveDirectoryName;
   }
}
