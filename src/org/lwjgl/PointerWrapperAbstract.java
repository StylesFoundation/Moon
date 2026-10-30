package org.lwjgl;

public abstract class PointerWrapperAbstract implements PointerWrapper {
   protected final long pointer;

   protected PointerWrapperAbstract(long pointer) {
      this.pointer = pointer;
   }

   public boolean isValid() {
      return this.pointer != 0L;
   }

   public final void checkValid() {
      if (LWJGLUtil.DEBUG && !this.isValid()) {
         throw new IllegalStateException("This " + this.getClass().getSimpleName() + " pointer is not valid.");
      }
   }

   @Override
   public final long getPointer() {
      this.checkValid();
      return this.pointer;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else {
         return o instanceof PointerWrapperAbstract that ? this.pointer == that.pointer : false;
      }
   }

   @Override
   public int hashCode() {
      return (int)(this.pointer ^ this.pointer >>> 32);
   }

   @Override
   public String toString() {
      return this.getClass().getSimpleName() + " pointer (0x" + Long.toHexString(this.pointer).toUpperCase() + ")";
   }
}
