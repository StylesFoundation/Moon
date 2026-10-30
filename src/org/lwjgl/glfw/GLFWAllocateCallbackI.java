package org.lwjgl.glfw;

import org.lwjgl.system.APIUtil;
import org.lwjgl.system.CallbackI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.libffi.FFICIF;
import org.lwjgl.system.libffi.LibFFI;

@FunctionalInterface
@NativeType("GLFWallocatefun")
public interface GLFWAllocateCallbackI extends CallbackI {
   FFICIF CIF = APIUtil.apiCreateCIF(LibFFI.FFI_DEFAULT_ABI, LibFFI.ffi_type_pointer, LibFFI.ffi_type_pointer, LibFFI.ffi_type_pointer);

   @Override
   default FFICIF getCallInterface() {
      return CIF;
   }

   @Override
   default void callback(long ret, long args) {
      long __result = this.invoke(
         MemoryUtil.memGetAddress(MemoryUtil.memGetAddress(args)), MemoryUtil.memGetAddress(MemoryUtil.memGetAddress(args + POINTER_SIZE))
      );
      APIUtil.apiClosureRetP(ret, __result);
   }

   @NativeType("void *")
   long invoke(@NativeType("size_t") long var1, @NativeType("void *") long var3);
}
