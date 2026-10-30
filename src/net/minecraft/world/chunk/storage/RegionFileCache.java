package net.minecraft.world.chunk.storage;

import com.google.common.collect.Maps;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class RegionFileCache {
   private static final Map<File, RegionFile> regionsByFilename = Maps.newHashMap();

   public static synchronized RegionFile createOrLoadRegionFile(File file, int integer2, int integer3) {
      File var6 = new File(file, "region");
      File var7 = new File(var6, "r." + (integer2 >> 5) + "." + (integer3 >> 5) + ".mca");
      RegionFile var8 = regionsByFilename.get(var7);
      if (var8 != null) {
         return var8;
      }

      if (!var6.exists()) {
         var6.mkdirs();
      }

      if (regionsByFilename.size() >= 256) {
         clearRegionFileReferences();
      }

      RegionFile var9 = new RegionFile(var7);
      regionsByFilename.put(var7, var9);
      return var9;
   }

   public static synchronized void clearRegionFileReferences() {
      for (RegionFile var4 : regionsByFilename.values()) {
         try {
            if (var4 != null) {
               var4.close();
            }
         } catch (IOException var5) {
            var5.printStackTrace();
         }
      }

      regionsByFilename.clear();
   }

   public static DataInputStream getChunkInputStream(File file, int integer2, int integer3) {
      return createOrLoadRegionFile(file, integer2, integer3).getChunkDataInputStream(integer2 & 31, integer3 & 31);
   }

   public static DataOutputStream getChunkOutputStream(File file, int integer2, int integer3) {
      return createOrLoadRegionFile(file, integer2, integer3).getChunkDataOutputStream(integer2 & 31, integer3 & 31);
   }
}
