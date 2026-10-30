package net.minecraft.world.storage;

import net.minecraft.world.WorldSavedData;

public class SaveDataMemoryStorage extends MapStorage {
   public SaveDataMemoryStorage() {
      super(null);
   }

   @Override
   public WorldSavedData loadData(Class<? extends WorldSavedData> class1, String string) {
      return this.loadedDataMap.get(string);
   }

   @Override
   public void setData(String string, WorldSavedData worldSavedData) {
      this.loadedDataMap.put(string, worldSavedData);
   }

   @Override
   public void saveAllData() {
   }

   @Override
   public int getUniqueDataId(String string) {
      return 0;
   }
}
