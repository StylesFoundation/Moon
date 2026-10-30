package net.minecraft.entity.ai;

public abstract class EntityAIBase {
   private int mutexBits;

   public abstract boolean shouldExecute();

   public boolean continueExecuting() {
      return this.shouldExecute();
   }

   public boolean isInterruptible() {
      return true;
   }

   public void startExecuting() {
   }

   public void resetTask() {
   }

   public void updateTask() {
   }

   public void setMutexBits(int integer) {
      this.mutexBits = integer;
   }

   public int getMutexBits() {
      return this.mutexBits;
   }

   public static boolean Method739() {
      return false;
   }

   static {
      if (Method739()) {
      }
   }
}
