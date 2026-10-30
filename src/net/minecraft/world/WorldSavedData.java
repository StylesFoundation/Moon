package net.minecraft.world;

import net.minecraft.nbt.NBTTagCompound;

public abstract class WorldSavedData {
   public final String mapName;
   private boolean dirty;

   public WorldSavedData(String string) {
      this.mapName = string;
   }

   public abstract void readFromNBT(NBTTagCompound nBTTagCompound);

   public abstract void writeToNBT(NBTTagCompound nBTTagCompound);

   public void markDirty() {
      this.setDirty(true);
   }

   public void setDirty(boolean boolean1) {
      this.dirty = boolean1;
   }

   public boolean isDirty() {
      return this.dirty;
   }
}
