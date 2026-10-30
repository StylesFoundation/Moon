package org.lwjgl.opengl;

import org.lwjgl.system.APIUtil;
import org.lwjgl.system.CallbackI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.libffi.FFICIF;
import org.lwjgl.system.libffi.LibFFI;

@FunctionalInterface
@NativeType("GLDEBUGPROCARB")
public interface GLDebugMessageARBCallbackI extends CallbackI {
   FFICIF CIF = APIUtil.apiCreateCIF(
      APIUtil.apiStdcall(),
      LibFFI.ffi_type_void,
      LibFFI.ffi_type_uint32,
      LibFFI.ffi_type_uint32,
      LibFFI.ffi_type_uint32,
      LibFFI.ffi_type_uint32,
      LibFFI.ffi_type_sint32,
      LibFFI.ffi_type_pointer,
      LibFFI.ffi_type_pointer
   );

   @Override
   default FFICIF getCallInterface() {
      return CIF;
   }

   @Override
   default void callback(long ret, long args) {
      this.invoke(
         MemoryUtil.memGetInt(MemoryUtil.memGetAddress(args)),
         MemoryUtil.memGetInt(MemoryUtil.memGetAddress(args + POINTER_SIZE)),
         MemoryUtil.memGetInt(MemoryUtil.memGetAddress(args + 2 * POINTER_SIZE)),
         MemoryUtil.memGetInt(MemoryUtil.memGetAddress(args + 3 * POINTER_SIZE)),
         MemoryUtil.memGetInt(MemoryUtil.memGetAddress(args + 4 * POINTER_SIZE)),
         MemoryUtil.memGetAddress(MemoryUtil.memGetAddress(args + 5 * POINTER_SIZE)),
         MemoryUtil.memGetAddress(MemoryUtil.memGetAddress(args + 6 * POINTER_SIZE))
      );
   }

   void invoke(
      @NativeType("GLenum") int var1,
      @NativeType("GLenum") int var2,
      @NativeType("GLuint") int var3,
      @NativeType("GLenum") int var4,
      @NativeType("GLsizei") int var5,
      @NativeType("GLchar const *") long var6,
      @NativeType("void const *") long var8
   );
}
