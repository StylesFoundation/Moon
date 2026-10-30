package org.lwjgl.glfw;

import org.lwjgl.system.APIUtil;
import org.lwjgl.system.CallbackI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.libffi.FFICIF;
import org.lwjgl.system.libffi.LibFFI;

@FunctionalInterface
@NativeType("GLFWwindowposfun")
public interface GLFWWindowPosCallbackI extends CallbackI {
   FFICIF CIF = APIUtil.apiCreateCIF(LibFFI.FFI_DEFAULT_ABI, LibFFI.ffi_type_void, LibFFI.ffi_type_pointer, LibFFI.ffi_type_sint32, LibFFI.ffi_type_sint32);

   @Override
   default FFICIF getCallInterface() {
      return CIF;
   }

   @Override
   default void callback(long ret, long args) {
      this.invoke(
         MemoryUtil.memGetAddress(MemoryUtil.memGetAddress(args)),
         MemoryUtil.memGetInt(MemoryUtil.memGetAddress(args + POINTER_SIZE)),
         MemoryUtil.memGetInt(MemoryUtil.memGetAddress(args + 2 * POINTER_SIZE))
      );
   }

   void invoke(@NativeType("GLFWwindow *") long var1, int var3, int var4);
}
