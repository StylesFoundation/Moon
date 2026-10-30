package org.lwjgl.system;

import org.lwjgl.system.libffi.FFICIF;

public interface CallbackI extends Pointer {
   FFICIF getCallInterface();

   @Override
   default long address() {
      return Callback.create(this.getCallInterface(), this);
   }

   void callback(long var1, long var3);
}
