package net.minecraft.world.gen.structure;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

public class MapGenStructureData extends WorldSavedData {
   private NBTTagCompound tagCompound = new NBTTagCompound();

   public MapGenStructureData(String string) {
      super(string);
   }

   @Override
   public void readFromNBT(NBTTagCompound nBTTagCompound) {
      this.tagCompound = nBTTagCompound.getCompoundTag("Features");
   }

   @Override
   public void writeToNBT(NBTTagCompound nBTTagCompound) {
      nBTTagCompound.setTag("Features", this.tagCompound);
   }

   public void writeInstance(NBTTagCompound nBTTagCompound, int integer2, int integer3) {
      this.tagCompound.setTag(formatChunkCoords(integer2, integer3), nBTTagCompound);
   }

   public static String formatChunkCoords(int integer1, int integer2) {
      return "[" + integer1 + "," + integer2 + "]";
   }

   public NBTTagCompound getTagCompound() {
      return this.tagCompound;
   }
}
