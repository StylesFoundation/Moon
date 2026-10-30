package org.lwjgl.system;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.Nullable;
import org.lwjgl.system.libc.LibCStdlib;
import org.lwjgl.system.libffi.FFICIF;
import org.lwjgl.system.libffi.LibFFI;

final class MemoryManage {
   private MemoryManage() {
   }

   static MemoryUtil.MemoryAllocator getInstance() {
      Object allocator = Configuration.MEMORY_ALLOCATOR.get();
      if (allocator instanceof MemoryUtil.MemoryAllocator) {
         return (MemoryUtil.MemoryAllocator)allocator;
      }

      if (!"system".equals(allocator)) {
         String className;
         if (allocator == null || "jemalloc".equals(allocator)) {
            className = "org.lwjgl.system.jemalloc.JEmallocAllocator";
         } else if ("rpmalloc".equals(allocator)) {
            className = "org.lwjgl.system.rpmalloc.RPmallocAllocator";
         } else {
            className = allocator.toString();
         }

         try {
            Class<?> allocatorClass = Class.forName(className);
            return (MemoryUtil.MemoryAllocator)allocatorClass.getConstructor().newInstance();
         } catch (Throwable t) {
            if (Checks.DEBUG && allocator != null) {
               t.printStackTrace(APIUtil.DEBUG_STREAM);
            }

            APIUtil.apiLog(String.format("Warning: Failed to instantiate memory allocator: %s. Using the system default.", className));
         }
      }

      return new MemoryManage.StdlibAllocator();
   }

   static class DebugAllocator implements MemoryUtil.MemoryAllocator {
      private static final ConcurrentMap<Long, MemoryManage.DebugAllocator.Allocation> ALLOCATIONS = new ConcurrentHashMap<>();
      private static final ConcurrentMap<Long, String> THREADS = new ConcurrentHashMap<>();
      private final MemoryUtil.MemoryAllocator allocator;
      private final long[] callbacks;

      DebugAllocator(MemoryUtil.MemoryAllocator allocator) {
         this.allocator = allocator;
         this.callbacks = new long[]{(new CallbackI() {
            @Override
            public FFICIF getCallInterface() {
               return APIUtil.apiCreateCIF(LibFFI.FFI_DEFAULT_ABI, LibFFI.ffi_type_pointer, LibFFI.ffi_type_pointer);
            }

            @Override
            public void callback(long ret, long args) {
               long size = MemoryUtil.memGetAddress(MemoryUtil.memGetAddress(args));
               MemoryUtil.memPutAddress(ret, DebugAllocator.this.malloc(size));
            }
         }).address(), (new CallbackI() {
            @Override
            public FFICIF getCallInterface() {
               return APIUtil.apiCreateCIF(LibFFI.FFI_DEFAULT_ABI, LibFFI.ffi_type_pointer, LibFFI.ffi_type_pointer, LibFFI.ffi_type_pointer);
            }

            @Override
            public void callback(long ret, long args) {
               long num = MemoryUtil.memGetAddress(MemoryUtil.memGetAddress(args));
               long size = MemoryUtil.memGetAddress(MemoryUtil.memGetAddress(args + POINTER_SIZE));
               MemoryUtil.memPutAddress(ret, DebugAllocator.this.calloc(num, size));
            }
         }).address(), (new CallbackI() {
            @Override
            public FFICIF getCallInterface() {
               return APIUtil.apiCreateCIF(LibFFI.FFI_DEFAULT_ABI, LibFFI.ffi_type_pointer, LibFFI.ffi_type_pointer, LibFFI.ffi_type_pointer);
            }

            @Override
            public void callback(long ret, long args) {
               long ptr = MemoryUtil.memGetAddress(MemoryUtil.memGetAddress(args));
               long size = MemoryUtil.memGetAddress(MemoryUtil.memGetAddress(args + POINTER_SIZE));
               MemoryUtil.memPutAddress(ret, DebugAllocator.this.realloc(ptr, size));
            }
         }).address(), (new CallbackI() {
            @Override
            public FFICIF getCallInterface() {
               return APIUtil.apiCreateCIF(LibFFI.FFI_DEFAULT_ABI, LibFFI.ffi_type_void, LibFFI.ffi_type_pointer);
            }

            @Override
            public void callback(long ret, long args) {
               long ptr = MemoryUtil.memGetAddress(MemoryUtil.memGetAddress(args));
               DebugAllocator.this.free(ptr);
            }
         }).address(), (new CallbackI() {
            @Override
            public FFICIF getCallInterface() {
               return APIUtil.apiCreateCIF(LibFFI.FFI_DEFAULT_ABI, LibFFI.ffi_type_pointer, LibFFI.ffi_type_pointer, LibFFI.ffi_type_pointer);
            }

            @Override
            public void callback(long ret, long args) {
               long alignment = MemoryUtil.memGetAddress(MemoryUtil.memGetAddress(args));
               long size = MemoryUtil.memGetAddress(MemoryUtil.memGetAddress(args + POINTER_SIZE));
               MemoryUtil.memPutAddress(ret, DebugAllocator.this.aligned_alloc(alignment, size));
            }
         }).address(), (new CallbackI() {
            @Override
            public FFICIF getCallInterface() {
               return APIUtil.apiCreateCIF(LibFFI.FFI_DEFAULT_ABI, LibFFI.ffi_type_void, LibFFI.ffi_type_pointer);
            }

            @Override
            public void callback(long ret, long args) {
               long ptr = MemoryUtil.memGetAddress(MemoryUtil.memGetAddress(args));
               DebugAllocator.this.aligned_free(ptr);
            }
         }).address()};
         Runtime.getRuntime()
            .addShutdownHook(
               new Thread(
                  () -> {
                     for (long callback : this.callbacks) {
                        Callback.free(callback);
                     }

                     if (!ALLOCATIONS.isEmpty()) {
                        for (Entry<Long, MemoryManage.DebugAllocator.Allocation> entry : ALLOCATIONS.entrySet()) {
                           Long address = entry.getKey();
                           MemoryManage.DebugAllocator.Allocation allocation = entry.getValue();
                           APIUtil.DEBUG_STREAM
                              .format(
                                 "[LWJGL] %d bytes leaked, thread %d (%s), address: 0x%s\n",
                                 allocation.size,
                                 allocation.threadId,
                                 THREADS.get(allocation.threadId),
                                 Long.toHexString(address).toUpperCase()
                              );

                           for (Object el : allocation.stackTrace) {
                              APIUtil.DEBUG_STREAM.format("\tat %s\n", el.toString());
                           }
                        }
                     }
                  }
               )
            );
      }

      @Override
      public long getMalloc() {
         return this.callbacks[0];
      }

      @Override
      public long getCalloc() {
         return this.callbacks[1];
      }

      @Override
      public long getRealloc() {
         return this.callbacks[2];
      }

      @Override
      public long getFree() {
         return this.callbacks[3];
      }

      @Override
      public long getAlignedAlloc() {
         return this.callbacks[4];
      }

      @Override
      public long getAlignedFree() {
         return this.callbacks[5];
      }

      @Override
      public long malloc(long size) {
         return track(this.allocator.malloc(size), size);
      }

      @Override
      public long calloc(long num, long size) {
         return track(this.allocator.calloc(num, size), num * size);
      }

      @Override
      public long realloc(long ptr, long size) {
         long oldSize = untrack(ptr);
         long address = this.allocator.realloc(ptr, size);
         if (address != 0L) {
            track(address, size);
         } else if (size != 0L) {
            track(ptr, oldSize);
         }

         return address;
      }

      @Override
      public void free(long ptr) {
         untrack(ptr);
         this.allocator.free(ptr);
      }

      @Override
      public long aligned_alloc(long alignment, long size) {
         return track(this.allocator.aligned_alloc(alignment, size), size);
      }

      @Override
      public void aligned_free(long ptr) {
         untrack(ptr);
         this.allocator.aligned_free(ptr);
      }

      static long track(long address, long size) {
         if (address != 0L) {
            Thread t = Thread.currentThread();
            Long threadId = t.getId();
            if (!THREADS.containsKey(threadId)) {
               THREADS.put(threadId, t.getName());
            }

            MemoryManage.DebugAllocator.Allocation allocation = ALLOCATIONS.put(
               address, new MemoryManage.DebugAllocator.Allocation(StackWalkUtil.stackWalkGetTrace(), size)
            );
            if (allocation != null) {
               throw new IllegalStateException("The memory address specified is already being tracked: 0x" + Long.toHexString(address).toUpperCase());
            }
         }

         return address;
      }

      static long untrack(long address) {
         if (address == 0L) {
            return 0L;
         } else {
            MemoryManage.DebugAllocator.Allocation allocation = ALLOCATIONS.remove(address);
            if (allocation == null) {
               throw new IllegalStateException("The memory address specified is not being tracked: 0x" + Long.toHexString(address).toUpperCase());
            } else {
               return allocation.size;
            }
         }
      }

      static void report(MemoryUtil.MemoryAllocationReport report) {
         for (Entry<Long, MemoryManage.DebugAllocator.Allocation> entry : ALLOCATIONS.entrySet()) {
            MemoryManage.DebugAllocator.Allocation allocation = entry.getValue();
            report.invoke(entry.getKey(), allocation.size, allocation.threadId, THREADS.get(allocation.threadId), allocation.getElements());
         }
      }

      private static <T> void aggregate(T t, long size, Map<T, AtomicLong> map) {
         AtomicLong node = map.computeIfAbsent(t, k -> new AtomicLong());
         node.set(node.get() + size);
      }

      static void report(MemoryUtil.MemoryAllocationReport report, MemoryUtil.MemoryAllocationReport.Aggregate groupByStackTrace, boolean groupByThread) {
         switch (groupByStackTrace) {
            case ALL:
               if (groupByThread) {
                  Map<Long, AtomicLong> mapThread = new HashMap<>();

                  for (MemoryManage.DebugAllocator.Allocation allocation : ALLOCATIONS.values()) {
                     aggregate(allocation.threadId, allocation.size, mapThread);
                  }

                  for (Entry<Long, AtomicLong> entry : mapThread.entrySet()) {
                     report.invoke(0L, entry.getValue().get(), entry.getKey(), THREADS.get(entry.getKey()), (StackTraceElement[])null);
                  }
               } else {
                  long total = 0L;

                  for (MemoryManage.DebugAllocator.Allocation allocation : ALLOCATIONS.values()) {
                     total += allocation.size;
                  }

                  report.invoke(0L, total, 0L, null, (StackTraceElement[])null);
               }
               break;
            case GROUP_BY_METHOD:
               if (groupByThread) {
                  Map<Long, Map<StackTraceElement, AtomicLong>> mapThreadMethod = new HashMap<>();

                  for (MemoryManage.DebugAllocator.Allocation allocation : ALLOCATIONS.values()) {
                     Map<StackTraceElement, AtomicLong> mapMethod = mapThreadMethod.computeIfAbsent(allocation.threadId, k -> new HashMap<>());
                     aggregate(allocation.getElements()[0], allocation.size, mapMethod);
                  }

                  for (Entry<Long, Map<StackTraceElement, AtomicLong>> tms : mapThreadMethod.entrySet()) {
                     long threadId = tms.getKey();
                     Map<StackTraceElement, AtomicLong> mapmapMethod = tms.getValue();

                     for (Entry<StackTraceElement, AtomicLong> ms : mapmapMethod.entrySet()) {
                        report.invoke(0L, ms.getValue().get(), threadId, THREADS.get(threadId), ms.getKey());
                     }
                  }
               } else {
                  Map<StackTraceElement, AtomicLong> mapMethod = new HashMap<>();

                  for (MemoryManage.DebugAllocator.Allocation allocation : ALLOCATIONS.values()) {
                     aggregate(allocation.getElements()[0], allocation.size, mapMethod);
                  }

                  for (Entry<StackTraceElement, AtomicLong> ms : mapMethod.entrySet()) {
                     report.invoke(0L, ms.getValue().get(), 0L, null, ms.getKey());
                  }
               }
               break;
            case GROUP_BY_STACKTRACE:
               if (groupByThread) {
                  Map<Long, Map<MemoryManage.DebugAllocator.Allocation, AtomicLong>> mapThreadStackTrace = new HashMap<>();

                  for (MemoryManage.DebugAllocator.Allocation allocation : ALLOCATIONS.values()) {
                     Map<MemoryManage.DebugAllocator.Allocation, AtomicLong> mapStackTrace = mapThreadStackTrace.computeIfAbsent(
                        allocation.threadId, k -> new HashMap<>()
                     );
                     aggregate(allocation, allocation.size, mapStackTrace);
                  }

                  for (Entry<Long, Map<MemoryManage.DebugAllocator.Allocation, AtomicLong>> tss : mapThreadStackTrace.entrySet()) {
                     long threadId = tss.getKey();
                     Map<MemoryManage.DebugAllocator.Allocation, AtomicLong> mapStackTrace = tss.getValue();

                     for (Entry<MemoryManage.DebugAllocator.Allocation, AtomicLong> ss : mapStackTrace.entrySet()) {
                        report.invoke(0L, ss.getValue().get(), threadId, THREADS.get(threadId), ss.getKey().getElements());
                     }
                  }
               } else {
                  Map<MemoryManage.DebugAllocator.Allocation, AtomicLong> mapStackTrace = new HashMap<>();

                  for (MemoryManage.DebugAllocator.Allocation allocation : ALLOCATIONS.values()) {
                     aggregate(allocation, allocation.size, mapStackTrace);
                  }

                  for (Entry<MemoryManage.DebugAllocator.Allocation, AtomicLong> ss : mapStackTrace.entrySet()) {
                     report.invoke(0L, ss.getValue().get(), 0L, null, ss.getKey().getElements());
                  }
               }
         }
      }

      private static class Allocation {
         private final Object[] stackTrace;
         @Nullable
         private StackTraceElement[] elements;
         final long size;
         final long threadId;

         Allocation(Object[] stackTrace, long size) {
            this.stackTrace = stackTrace;
            this.size = size;
            this.threadId = Thread.currentThread().getId();
         }

         private StackTraceElement[] getElements() {
            if (this.elements == null) {
               this.elements = StackWalkUtil.stackWalkArray(this.stackTrace);
            }

            return this.elements;
         }

         @Override
         public boolean equals(Object o) {
            if (this == o) {
               return true;
            } else if (o != null && this.getClass() == o.getClass()) {
               MemoryManage.DebugAllocator.Allocation that = (MemoryManage.DebugAllocator.Allocation)o;
               return Arrays.equals(this.getElements(), that.getElements());
            } else {
               return false;
            }
         }

         @Override
         public int hashCode() {
            return Arrays.hashCode(this.getElements());
         }
      }
   }

   private static class StdlibAllocator implements MemoryUtil.MemoryAllocator {
      private StdlibAllocator() {
      }

      @Override
      public long getMalloc() {
         return MemoryAccessJNI.malloc;
      }

      @Override
      public long getCalloc() {
         return MemoryAccessJNI.calloc;
      }

      @Override
      public long getRealloc() {
         return MemoryAccessJNI.realloc;
      }

      @Override
      public long getFree() {
         return MemoryAccessJNI.free;
      }

      @Override
      public long getAlignedAlloc() {
         return MemoryAccessJNI.aligned_alloc;
      }

      @Override
      public long getAlignedFree() {
         return MemoryAccessJNI.aligned_free;
      }

      @Override
      public long malloc(long size) {
         return LibCStdlib.nmalloc(size);
      }

      @Override
      public long calloc(long num, long size) {
         return LibCStdlib.ncalloc(num, size);
      }

      @Override
      public long realloc(long ptr, long size) {
         return LibCStdlib.nrealloc(ptr, size);
      }

      @Override
      public void free(long ptr) {
         LibCStdlib.nfree(ptr);
      }

      @Override
      public long aligned_alloc(long alignment, long size) {
         return LibCStdlib.naligned_alloc(alignment, size);
      }

      @Override
      public void aligned_free(long ptr) {
         LibCStdlib.naligned_free(ptr);
      }
   }
}
