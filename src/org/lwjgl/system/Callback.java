package org.lwjgl.system;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.jni.JNINativeInterface;
import org.lwjgl.system.libffi.FFICIF;
import org.lwjgl.system.libffi.FFIClosure;
import org.lwjgl.system.libffi.LibFFI;

public abstract class Callback implements Pointer, NativeResource {
   private static final boolean DEBUG_ALLOCATOR = Configuration.DEBUG_MEMORY_ALLOCATOR.get(false);
   private static final Callback.ClosureRegistry CLOSURE_REGISTRY;
   private static final long CALLBACK_HANDLER;
   private long address;

   protected Callback(FFICIF cif) {
      this.address = create(cif, this);
   }

   protected Callback(long address) {
      if (Checks.CHECKS) {
         Checks.check(address);
      }

      this.address = address;
   }

   @Override
   public long address() {
      return this.address;
   }

   @Override
   public void free() {
      free(this.address());
   }

   private static native long getCallbackHandler(Method var0);

   static long create(FFICIF cif, Object instance) {
      FFIClosure closure;
      long executableAddress;
      try (MemoryStack stack = MemoryStack.stackPush()) {
         PointerBuffer code = stack.mallocPointer(1);
         closure = LibFFI.ffi_closure_alloc(FFIClosure.SIZEOF, code);
         if (closure == null) {
            throw new OutOfMemoryError();
         }

         executableAddress = code.get(0);
         if (DEBUG_ALLOCATOR) {
            MemoryManage.DebugAllocator.track(executableAddress, FFIClosure.SIZEOF);
         }
      }

      long user_data = JNINativeInterface.NewGlobalRef(instance);
      int errcode = LibFFI.ffi_prep_closure_loc(closure, cif, CALLBACK_HANDLER, user_data, executableAddress);
      if (errcode != 0) {
         JNINativeInterface.DeleteGlobalRef(user_data);
         LibFFI.ffi_closure_free(closure);
         throw new RuntimeException("Failed to prepare the libffi closure");
      } else {
         CLOSURE_REGISTRY.put(executableAddress, closure);
         return executableAddress;
      }
   }

   public static <T extends CallbackI> T get(long functionPointer) {
      return MemoryUtil.memGlobalRefToObject(CLOSURE_REGISTRY.get(functionPointer).user_data());
   }

   @Nullable
   public static <T extends CallbackI> T getSafe(long functionPointer) {
      return functionPointer == 0L ? null : get(functionPointer);
   }

   public static void free(long functionPointer) {
      if (DEBUG_ALLOCATOR) {
         MemoryManage.DebugAllocator.untrack(functionPointer);
      }

      FFIClosure closure = CLOSURE_REGISTRY.get(functionPointer);
      JNINativeInterface.DeleteGlobalRef(closure.user_data());
      LibFFI.ffi_closure_free(closure);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }

      if (!(o instanceof Callback)) {
         return false;
      }

      Callback that = (Callback)o;
      return this.address == that.address();
   }

   @Override
   public int hashCode() {
      return (int)(this.address ^ this.address >>> 32);
   }

   @Override
   public String toString() {
      return String.format("%s pointer [0x%X]", this.getClass().getSimpleName(), this.address);
   }

   static {
      try (MemoryStack stack = MemoryStack.stackPush()) {
         PointerBuffer code = stack.mallocPointer(1);
         FFIClosure closure = LibFFI.ffi_closure_alloc(FFIClosure.SIZEOF, code);
         if (closure == null) {
            throw new OutOfMemoryError();
         }

         if (code.get(0) == closure.address()) {
            APIUtil.apiLog("Closure Registry: simple");
            CLOSURE_REGISTRY = new Callback.ClosureRegistry() {
               @Override
               public void put(long executableAddress, FFIClosure closure) {
               }

               @Override
               public FFIClosure get(long executableAddress) {
                  return FFIClosure.create(executableAddress);
               }

               @Override
               public FFIClosure remove(long executableAddress) {
                  return this.get(executableAddress);
               }
            };
         } else {
            APIUtil.apiLog("Closure Registry: ConcurrentHashMap");
            CLOSURE_REGISTRY = new Callback.ClosureRegistry() {
               private final ConcurrentHashMap<Long, FFIClosure> map = new ConcurrentHashMap<>();

               @Override
               public void put(long executableAddress, FFIClosure closure) {
                  this.map.put(executableAddress, closure);
               }

               @Override
               public FFIClosure get(long executableAddress) {
                  return this.map.get(executableAddress);
               }

               @Override
               public FFIClosure remove(long executableAddress) {
                  return this.map.remove(executableAddress);
               }
            };
         }

         LibFFI.ffi_closure_free(closure);
      }

      try {
         CALLBACK_HANDLER = getCallbackHandler(CallbackI.class.getDeclaredMethod("callback", long.class, long.class));
      } catch (Exception e) {
         throw new IllegalStateException("Failed to initialize the native callback handler.", e);
      }

      MemoryUtil.getAllocator();
   }

   private interface ClosureRegistry {
      void put(long var1, FFIClosure var3);

      FFIClosure get(long var1);

      FFIClosure remove(long var1);
   }
}
