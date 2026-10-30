package org.lwjgl.glfw;

import org.lwjgl.system.APIUtil;
import org.lwjgl.system.CallbackI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.libffi.FFICIF;
import org.lwjgl.system.libffi.LibFFI;

@FunctionalInterface
@NativeType("GLFWerrorfun")
public interface GLFWErrorCallbackI extends CallbackI {
   FFICIF CIF = APIUtil.apiCreateCIF(LibFFI.FFI_DEFAULT_ABI, LibFFI.ffi_type_void, LibFFI.ffi_type_sint32, LibFFI.ffi_type_pointer);

   @Override
   default FFICIF getCallInterface() {
      return CIF;
   }

   @Override
   default void callback(long ret, long args) {
      this.invoke(MemoryUtil.memGetInt(MemoryUtil.memGetAddress(args)), MemoryUtil.memGetAddress(MemoryUtil.memGetAddress(args + POINTER_SIZE)));
   }

   void invoke(int var1, @NativeType("char *") long var2);
}
