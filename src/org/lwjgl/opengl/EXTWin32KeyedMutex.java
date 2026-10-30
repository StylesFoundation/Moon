package org.lwjgl.opengl;

import org.lwjgl.system.NativeType;

public class EXTWin32KeyedMutex {
   protected EXTWin32KeyedMutex() {
      throw new UnsupportedOperationException();
   }

   @NativeType("GLboolean")
   public static native boolean glAcquireKeyedMutexWin32EXT(@NativeType("GLuint") int var0, @NativeType("GLuint64") long var1, @NativeType("GLuint") int var3);

   @NativeType("GLboolean")
   public static native boolean glReleaseKeyedMutexWin32EXT(@NativeType("GLuint") int var0, @NativeType("GLuint64") long var1);

   static {
      GL.initialize();
   }
}
