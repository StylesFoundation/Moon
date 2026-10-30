package net.minecraft.util;

import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;

public class Rotations {
   protected final float x;
   protected final float y;
   protected final float z;

   public Rotations(float float1, float float2, float float3) {
      this.x = float1;
      this.y = float2;
      this.z = float3;
   }

   public Rotations(NBTTagList nBTTagList) {
      this.x = nBTTagList.getFloatAt(0);
      this.y = nBTTagList.getFloatAt(1);
      this.z = nBTTagList.getFloatAt(2);
   }

   public NBTTagList writeToNBT() {
      NBTTagList var1 = new NBTTagList();
      var1.appendTag(new NBTTagFloat(this.x));
      var1.appendTag(new NBTTagFloat(this.y));
      var1.appendTag(new NBTTagFloat(this.z));
      return var1;
   }

   @Override
   public boolean equals(Object object) {
      return !(object instanceof Rotations var5) ? false : this.x == var5.x && this.y == var5.y && this.z == var5.z;
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   public float getZ() {
      return this.z;
   }
}
