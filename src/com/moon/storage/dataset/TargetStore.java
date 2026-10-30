package com.moon.storage.dataset;

import java.util.Arrays;

public class TargetStore {
   private Target[] targets;

   @Override
   public boolean equals(Object object) {
      if (object == this) {
         return true;
      } else if (!(object instanceof TargetStore other)) {
         return false;
      } else {
         return !other.canEqual(this) ? false : Arrays.deepEquals(this.getTargets(), other.getTargets());
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof TargetStore;
   }

   @Override
   public int hashCode() {
      return 59 + Arrays.deepHashCode(this.getTargets());
   }

   @Override
   public String toString() {
      return "TargetStore(targets=" + Arrays.deepToString(this.getTargets()) + ")";
   }

   public Target[] getTargets() {
      return this.targets;
   }

   public TargetStore setTargets(Target[] targets) {
      this.targets = targets;
      return this;
   }

   public TargetStore() {
   }

   public TargetStore(Target[] targets) {
      this.targets = targets;
   }
}
