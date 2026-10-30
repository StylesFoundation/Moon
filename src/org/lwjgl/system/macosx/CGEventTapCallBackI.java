package org.lwjgl.system.macosx;

import org.lwjgl.system.APIUtil;
import org.lwjgl.system.CallbackI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.libffi.FFICIF;
import org.lwjgl.system.libffi.LibFFI;

@FunctionalInterface
@NativeType("CGEventRef (*) (CGEventTapProxy, CGEventType, CGEventRef, void *)")
public interface CGEventTapCallBackI extends CallbackI {
   FFICIF CIF = APIUtil.apiCreateCIF(
      LibFFI.FFI_DEFAULT_ABI, LibFFI.ffi_type_pointer, LibFFI.ffi_type_pointer, LibFFI.ffi_type_uint32, LibFFI.ffi_type_pointer, LibFFI.ffi_type_pointer
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

   @NativeType("CGEventRef")
   long invoke(
      @NativeType("CGEventTapProxy") long var1, @NativeType("CGEventType") int var3, @NativeType("CGEventRef") long var4, @NativeType("void *") long var6
   );
}
