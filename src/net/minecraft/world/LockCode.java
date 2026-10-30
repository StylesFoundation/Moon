package net.minecraft.world;

import net.minecraft.nbt.NBTTagCompound;

public class LockCode {
   public static final LockCode EMPTY_CODE = new LockCode("");
   private final String lock;

   public LockCode(String string) {
      this.lock = string;
   }

   public boolean isEmpty() {
      return this.lock == null || this.lock.isEmpty();
   }

   public String getLock() {
      return this.lock;
   }

   public void toNBT(NBTTagCompound nBTTagCompound) {
      nBTTagCompound.setString("Lock", this.lock);
   }

   public static LockCode fromNBT(NBTTagCompound nBTTagCompound) {
      if (nBTTagCompound.hasKey("Lock", 8)) {
         String var4 = nBTTagCompound.getString("Lock");
         return new LockCode(var4);
      } else {
         return EMPTY_CODE;
      }
   }
}
