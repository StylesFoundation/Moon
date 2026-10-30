package org.lwjgl.system.windows;

import org.lwjgl.system.APIUtil;
import org.lwjgl.system.CallbackI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.libffi.FFICIF;
import org.lwjgl.system.libffi.LibFFI;

@FunctionalInterface
@NativeType("WNDPROC")
public interface WindowProcI extends CallbackI {
   FFICIF CIF = APIUtil.apiCreateCIF(
      APIUtil.apiStdcall(), LibFFI.ffi_type_pointer, LibFFI.ffi_type_pointer, LibFFI.ffi_type_uint32, LibFFI.ffi_type_pointer, LibFFI.ffi_type_pointer
   );

   @Override
   default FFICIF getCallInterface() {
      return CIF;
   }

   @Override
   default void callback(long ret, long args) {
      long __result = this.invoke(
         MemoryUtil.memGetAddress(MemoryUtil.memGetAddress(args)),
         MemoryUtil.memGetInt(MemoryUtil.memGetAddress(args + POINTER_SIZE)),
         MemoryUtil.memGetAddress(MemoryUtil.memGetAddress(args + 2 * POINTER_SIZE)),
         MemoryUtil.memGetAddress(MemoryUtil.memGetAddress(args + 3 * POINTER_SIZE))
      );
      APIUtil.apiClosureRetP(ret, __result);
   }

   @NativeType("LRESULT")
   long invoke(@NativeType("HWND") long var1, @NativeType("UINT") int var3, @NativeType("WPARAM") long var4, @NativeType("LPARAM") long var6);
}
