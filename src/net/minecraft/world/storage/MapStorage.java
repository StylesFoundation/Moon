package net.minecraft.world.storage;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.world.WorldSavedData;

public class MapStorage {
   private final ISaveHandler saveHandler;
   protected Map<String, WorldSavedData> loadedDataMap = Maps.newHashMap();
   private final List<WorldSavedData> loadedDataList = Lists.newArrayList();
   private final Map<String, Short> idCounts = Maps.newHashMap();

   public MapStorage(ISaveHandler iSaveHandler) {
      this.saveHandler = iSaveHandler;
      this.loadIdCounts();
   }

   public WorldSavedData loadData(Class<? extends WorldSavedData> class1, String string) {
      WorldSavedData var6 = this.loadedDataMap.get(string);
      if (var6 == null) {
         if (this.saveHandler != null) {
            try {
               File var7 = this.saveHandler.getMapFileFromName(string);
               if (var7 != null && var7.exists()) {
                  try {
                     var6 = (WorldSavedData)class1.getConstructor(String.class).newInstance(string);
                  } catch (Exception var10) {
                     throw new RuntimeException("Failed to instantiate " + class1.toString(), var10);
                  }

                  FileInputStream var8 = new FileInputStream(var7);
                  NBTTagCompound var9 = CompressedStreamTools.readCompressed(var8);
                  var8.close();
                  var6.readFromNBT(var9.getCompoundTag("data"));
               }
            } catch (Exception var11) {
               var11.printStackTrace();
            }
         }

         if (var6 != null) {
            this.loadedDataMap.put(string, var6);
            this.loadedDataList.add(var6);
         }
      }

      return var6;
   }

   public void setData(String string, WorldSavedData worldSavedData) {
      if (this.loadedDataMap.containsKey(string)) {
         this.loadedDataList.remove(this.loadedDataMap.remove(string));
      }

      this.loadedDataMap.put(string, worldSavedData);
      this.loadedDataList.add(worldSavedData);
   }

   public void saveAllData() {
      for (WorldSavedData var5 : this.loadedDataList) {
         if (var5.isDirty()) {
            this.saveData(var5);
            var5.setDirty(false);
         }
      }
   }

   private void saveData(WorldSavedData worldSavedData) {
      if (this.saveHandler != null) {
         try {
            File var5 = this.saveHandler.getMapFileFromName(worldSavedData.mapName);
            if (var5 != null) {
               NBTTagCompound var6 = new NBTTagCompound();
               worldSavedData.writeToNBT(var6);
               NBTTagCompound var7 = new NBTTagCompound();
               var7.setTag("data", var6);
               FileOutputStream var8 = new FileOutputStream(var5);
               CompressedStreamTools.writeCompressed(var7, var8);
               var8.close();
            }
         } catch (Exception var9) {
            var9.printStackTrace();
         }
      }
   }

   private void loadIdCounts() {
      try {
         this.idCounts.clear();
         if (this.saveHandler == null) {
            return;
         }

         File var4 = this.saveHandler.getMapFileFromName("idcounts");
         if (var4 != null && var4.exists()) {
            DataInputStream var5 = new DataInputStream(new FileInputStream(var4));
            NBTTagCompound var6 = CompressedStreamTools.read(var5);
            var5.close();

            for (String var8 : var6.getKeySet()) {
               NBTBase var9 = var6.getTag(var8);
               if (var9 instanceof NBTTagShort) {
                  short var11 = ((NBTTagShort)var9).getShort();
                  this.idCounts.put(var8, var11);
               }
            }
         }
      } catch (Exception var12) {
         var12.printStackTrace();
      }
   }

   public int getUniqueDataId(String string) {
      Short var5 = this.idCounts.get(string);
      if (var5 == null) {
         var5 = (short)0;
      } else {
         var5 = (short)(var5 + 1);
      }

      this.idCounts.put(string, var5);
      if (this.saveHandler != null) {
         try {
            File var6 = this.saveHandler.getMapFileFromName("idcounts");
            if (var6 != null) {
               NBTTagCompound var7 = new NBTTagCompound();

               for (String var9 : this.idCounts.keySet()) {
                  short var10 = this.idCounts.get(var9);
                  var7.setShort(var9, var10);
               }

               DataOutputStream var13 = new DataOutputStream(new FileOutputStream(var6));
               CompressedStreamTools.write(var7, var13);
               var13.close();
            }
         } catch (Exception var11) {
            var11.printStackTrace();
         }
      }

      return var5;
   }
}
