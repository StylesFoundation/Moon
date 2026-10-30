package net.minecraft.world.chunk.storage;

import java.io.File;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.storage.SaveHandler;
import net.minecraft.world.storage.ThreadedFileIOBase;
import net.minecraft.world.storage.WorldInfo;

public class AnvilSaveHandler extends SaveHandler {
   public AnvilSaveHandler(File file, String string, boolean boolean3) {
      super(file, string, boolean3);
   }

   @Override
   public IChunkLoader getChunkLoader(WorldProvider worldProvider) {
      File var5 = this.getWorldDirectory();
      if (worldProvider instanceof WorldProviderHell) {
         File var7 = new File(var5, "DIM-1");
         var7.mkdirs();
         return new AnvilChunkLoader(var7);
      } else if (worldProvider instanceof WorldProviderEnd) {
         File var6 = new File(var5, "DIM1");
         var6.mkdirs();
         return new AnvilChunkLoader(var6);
      } else {
         return new AnvilChunkLoader(var5);
      }
   }

   @Override
   public void saveWorldInfoWithPlayer(WorldInfo worldInfo, NBTTagCompound nBTTagCompound) {
      worldInfo.setSaveVersion(19133);
      super.saveWorldInfoWithPlayer(worldInfo, nBTTagCompound);
   }

   @Override
   public void flush() {
      try {
         ThreadedFileIOBase.getThreadedIOInstance().waitForFinish();
      } catch (InterruptedException var1) {
         var1.printStackTrace();
      }

      RegionFileCache.clearRegionFileReferences();
   }
}
