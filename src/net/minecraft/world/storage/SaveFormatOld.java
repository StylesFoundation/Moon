package net.minecraft.world.storage;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IProgressUpdate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SaveFormatOld implements ISaveFormat {
   private static final Logger logger = LogManager.getLogger();
   protected final File savesDirectory;

   public SaveFormatOld(File file) {
      if (!file.exists()) {
         file.mkdirs();
      }

      this.savesDirectory = file;
   }

   @Override
   public String getName() {
      return "Old Format";
   }

   @Override
   public List<SaveFormatComparator> getSaveList() throws AnvilConverterException {
      ArrayList var4 = Lists.newArrayList();

      for (int var5 = 0; var5 < 5; var5++) {
         String var6 = "World" + (var5 + 1);
         WorldInfo var7 = this.getWorldInfo(var6);
         if (var7 != null) {
            var4.add(
               new SaveFormatComparator(
                  var6, "", var7.getLastTimePlayed(), var7.getSizeOnDisk(), var7.getGameType(), false, var7.isHardcoreModeEnabled(), var7.areCommandsAllowed()
               )
            );
         }
      }

      return var4;
   }

   @Override
   public void flushCache() {
   }

   @Override
   public WorldInfo getWorldInfo(String string) {
      File var5 = new File(this.savesDirectory, string);
      if (!var5.exists()) {
         return null;
      }

      File var6 = new File(var5, "level.dat");
      if (var6.exists()) {
         try {
            NBTTagCompound var12 = CompressedStreamTools.readCompressed(new FileInputStream(var6)).getCompoundTag("Data");
            return new WorldInfo(var12);
         } catch (Exception var10) {
            logger.error("Exception reading " + var6, var10);
         }
      }

      var6 = new File(var5, "level.dat_old");
      if (var6.exists()) {
         try {
            NBTTagCompound var8 = CompressedStreamTools.readCompressed(new FileInputStream(var6)).getCompoundTag("Data");
            return new WorldInfo(var8);
         } catch (Exception var9) {
            logger.error("Exception reading " + var6, var9);
         }
      }

      return null;
   }

   @Override
   public void renameWorld(String string1, String string2) {
      File var6 = new File(this.savesDirectory, string1);
      if (var6.exists()) {
         File var7 = new File(var6, "level.dat");
         if (var7.exists()) {
            try {
               NBTTagCompound var8 = CompressedStreamTools.readCompressed(new FileInputStream(var7));
               var8.getCompoundTag("Data").setString("LevelName", string2);
               CompressedStreamTools.writeCompressed(var8, new FileOutputStream(var7));
            } catch (Exception var9) {
               var9.printStackTrace();
            }
         }
      }
   }

   @Override
   public boolean isNewLevelIdAcceptable(String string) {
      File var5 = new File(this.savesDirectory, string);
      if (var5.exists()) {
         return false;
      }

      try {
         var5.mkdir();
         var5.delete();
         return true;
      } catch (Throwable var7) {
         logger.warn("Couldn't make new level", var7);
         return false;
      }
   }

   @Override
   public boolean deleteWorldDirectory(String string) {
      File var5 = new File(this.savesDirectory, string);
      if (!var5.exists()) {
         return true;
      }

      logger.info("Deleting level " + string);

      for (int var6 = 1; var6 <= 5; var6++) {
         logger.info("Attempt " + var6 + "...");
         if (Method986(var5.listFiles())) {
            break;
         }

         logger.warn("Unsuccessful in deleting contents.");
         if (var6 < 5) {
            try {
               Thread.sleep(500L);
            } catch (InterruptedException var7) {
            }
         }
      }

      return var5.delete();
   }

   protected static boolean Method986(File[] arr) {
      for (File var7 : arr) {
         logger.debug("Deleting " + var7);
         if (var7.isDirectory() && !Method986(var7.listFiles())) {
            logger.warn("Couldn't delete directory " + var7);
            return false;
         }

         if (!var7.delete()) {
            logger.warn("Couldn't delete file " + var7);
            return false;
         }
      }

      return true;
   }

   @Override
   public ISaveHandler getSaveLoader(String string, boolean boolean2) {
      return new SaveHandler(this.savesDirectory, string, boolean2);
   }

   @Override
   public boolean isConvertible(String string) {
      return false;
   }

   @Override
   public boolean isOldMapFormat(String string) {
      return false;
   }

   @Override
   public boolean convertMapFormat(String string, IProgressUpdate iProgressUpdate) {
      return false;
   }

   @Override
   public boolean canLoadWorld(String string) {
      return new File(this.savesDirectory, string).isDirectory();
   }
}
