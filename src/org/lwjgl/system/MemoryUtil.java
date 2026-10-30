package org.lwjgl.system;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.function.LongPredicate;
import javax.annotation.Nullable;
import org.lwjgl.CLongBuffer;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.jni.JNINativeInterface;
import org.lwjgl.system.libc.LibCString;
import sun.misc.Unsafe;

public final class MemoryUtil {
   public static final long NULL = 0L;
   public static final int PAGE_SIZE;
   public static final int CACHE_LINE_SIZE;
   static final int ARRAY_TLC_SIZE = Configuration.ARRAY_TLC_SIZE.get(8192);
   static final ThreadLocal<byte[]> ARRAY_TLC_BYTE = ThreadLocal.withInitial(() -> new byte[ARRAY_TLC_SIZE]);
   static final ThreadLocal<char[]> ARRAY_TLC_CHAR = ThreadLocal.withInitial(() -> new char[ARRAY_TLC_SIZE]);
   static final Unsafe UNSAFE = getUnsafeInstance();
   static final ByteOrder NATIVE_ORDER = ByteOrder.nativeOrder();
   private static final Charset UTF16 = NATIVE_ORDER == ByteOrder.LITTLE_ENDIAN ? StandardCharsets.UTF_16LE : StandardCharsets.UTF_16BE;
   static final Class<? extends ByteBuffer> BUFFER_BYTE;
   static final Class<? extends ShortBuffer> BUFFER_SHORT;
   static final Class<? extends CharBuffer> BUFFER_CHAR;
   static final Class<? extends IntBuffer> BUFFER_INT;
   static final Class<? extends LongBuffer> BUFFER_LONG;
   static final Class<? extends FloatBuffer> BUFFER_FLOAT;
   static final Class<? extends DoubleBuffer> BUFFER_DOUBLE;
   private static final long MARK;
   private static final long POSITION;
   private static final long LIMIT;
   private static final long CAPACITY;
   private static final long ADDRESS;
   private static final long PARENT_BYTE;
   private static final long PARENT_SHORT;
   private static final long PARENT_CHAR;
   private static final long PARENT_INT;
   private static final long PARENT_LONG;
   private static final long PARENT_FLOAT;
   private static final long PARENT_DOUBLE;
   private static final MemoryUtil.NativeShift SHIFT;
   private static final int FILL_PATTERN_32;
   private static final long FILL_PATTERN_64;
   private static final int MAGIC_CAPACITY = 219540062;
   private static final int MAGIC_POSITION = 16435934;

   private MemoryUtil() {
   }

   public static MemoryUtil.MemoryAllocator getAllocator() {
      return getAllocator(false);
   }

   public static MemoryUtil.MemoryAllocator getAllocator(boolean tracked) {
      return tracked ? MemoryUtil.LazyInit.ALLOCATOR : MemoryUtil.LazyInit.ALLOCATOR_IMPL;
   }

   public static long nmemAlloc(long size) {
      return MemoryUtil.LazyInit.ALLOCATOR.malloc(size);
   }

   public static long nmemAllocChecked(long size) {
      long address = nmemAlloc(size != 0L ? size : 1L);
      if (Checks.CHECKS && address == 0L) {
         throw new OutOfMemoryError();
      } else {
         return address;
      }
   }

   private static long getAllocationSize(int elements, int elementShift) {
      return APIUtil.apiCheckAllocation(elements, Integer.toUnsignedLong(elements) << elementShift, Pointer.BITS64 ? Long.MAX_VALUE : 4294967295L);
   }

   public static ByteBuffer memAlloc(int size) {
      return wrap(BUFFER_BYTE, nmemAllocChecked(size), size).order(NATIVE_ORDER);
   }

   public static ShortBuffer memAllocShort(int size) {
      return wrap(BUFFER_SHORT, nmemAllocChecked(getAllocationSize(size, 1)), size);
   }

   public static IntBuffer memAllocInt(int size) {
      return wrap(BUFFER_INT, nmemAllocChecked(getAllocationSize(size, 2)), size);
   }

   public static FloatBuffer memAllocFloat(int size) {
      return wrap(BUFFER_FLOAT, nmemAllocChecked(getAllocationSize(size, 2)), size);
   }

   public static LongBuffer memAllocLong(int size) {
      return wrap(BUFFER_LONG, nmemAllocChecked(getAllocationSize(size, 3)), size);
   }

   public static CLongBuffer memAllocCLong(int size) {
      return Pointer.Default.wrap(CLongBuffer.class, nmemAllocChecked(getAllocationSize(size, Pointer.CLONG_SHIFT)), size);
   }

   public static DoubleBuffer memAllocDouble(int size) {
      return wrap(BUFFER_DOUBLE, nmemAllocChecked(getAllocationSize(size, 3)), size);
   }

   public static PointerBuffer memAllocPointer(int size) {
      return Pointer.Default.wrap(PointerBuffer.class, nmemAllocChecked(getAllocationSize(size, Pointer.POINTER_SHIFT)), size);
   }

   public static void nmemFree(long ptr) {
      MemoryUtil.LazyInit.ALLOCATOR.free(ptr);
   }

   public static void memFree(@Nullable Buffer ptr) {
      if (ptr != null) {
         nmemFree(UNSAFE.getLong(ptr, ADDRESS));
      }
   }

   public static void memFree(@Nullable CustomBuffer<?> ptr) {
      if (ptr != null) {
         nmemFree(ptr.address);
      }
   }

   public static long nmemCalloc(long num, long size) {
      return MemoryUtil.LazyInit.ALLOCATOR.calloc(num, size);
   }

   public static long nmemCallocChecked(long num, long size) {
      if (num == 0L || size == 0L) {
         num = 1L;
         size = 1L;
      }

      long address = nmemCalloc(num, size);
      if (Checks.CHECKS && address == 0L) {
         throw new OutOfMemoryError();
      } else {
         return address;
      }
   }

   public static ByteBuffer memCalloc(int num, int size) {
      return wrap(BUFFER_BYTE, nmemCallocChecked(num, size), num * size).order(NATIVE_ORDER);
   }

   public static ByteBuffer memCalloc(int num) {
      return wrap(BUFFER_BYTE, nmemCallocChecked(num, 1L), num).order(NATIVE_ORDER);
   }

   public static ShortBuffer memCallocShort(int num) {
      return wrap(BUFFER_SHORT, nmemCallocChecked(num, 2L), num);
   }

   public static IntBuffer memCallocInt(int num) {
      return wrap(BUFFER_INT, nmemCallocChecked(num, 4L), num);
   }

   public static FloatBuffer memCallocFloat(int num) {
      return wrap(BUFFER_FLOAT, nmemCallocChecked(num, 4L), num);
   }

   public static LongBuffer memCallocLong(int num) {
      return wrap(BUFFER_LONG, nmemCallocChecked(num, 8L), num);
   }

   public static CLongBuffer memCallocCLong(int num) {
      return Pointer.Default.wrap(CLongBuffer.class, nmemCallocChecked(num, Pointer.CLONG_SIZE), num);
   }

   public static DoubleBuffer memCallocDouble(int num) {
      return wrap(BUFFER_DOUBLE, nmemCallocChecked(num, 8L), num);
   }

   public static PointerBuffer memCallocPointer(int num) {
      return Pointer.Default.wrap(PointerBuffer.class, nmemCallocChecked(num, Pointer.POINTER_SIZE), num);
   }

   public static long nmemRealloc(long ptr, long size) {
      return MemoryUtil.LazyInit.ALLOCATOR.realloc(ptr, size);
   }

   public static long nmemReallocChecked(long ptr, long size) {
      long address = nmemRealloc(ptr, size != 0L ? size : 1L);
      if (Checks.CHECKS && address == 0L) {
         throw new OutOfMemoryError();
      } else {
         return address;
      }
   }

   private static <T extends Buffer> T realloc(@Nullable T old_p, T new_p, int size) {
      if (old_p != null) {
         new_p.position(Math.min(old_p.position(), size));
      }

      return new_p;
   }

   public static ByteBuffer memRealloc(@Nullable ByteBuffer ptr, int size) {
      return realloc(ptr, memByteBuffer(nmemReallocChecked(ptr == null ? 0L : UNSAFE.getLong(ptr, ADDRESS), size), size), size);
   }

   public static ShortBuffer memRealloc(@Nullable ShortBuffer ptr, int size) {
      return realloc(ptr, memShortBuffer(nmemReallocChecked(ptr == null ? 0L : UNSAFE.getLong(ptr, ADDRESS), getAllocationSize(size, 1)), size), size);
   }

   public static IntBuffer memRealloc(@Nullable IntBuffer ptr, int size) {
      return realloc(ptr, memIntBuffer(nmemReallocChecked(ptr == null ? 0L : UNSAFE.getLong(ptr, ADDRESS), getAllocationSize(size, 2)), size), size);
   }

   public static LongBuffer memRealloc(@Nullable LongBuffer ptr, int size) {
      return realloc(ptr, memLongBuffer(nmemReallocChecked(ptr == null ? 0L : UNSAFE.getLong(ptr, ADDRESS), getAllocationSize(size, 3)), size), size);
   }

   public static CLongBuffer memRealloc(@Nullable CLongBuffer ptr, int size) {
      CLongBuffer buffer = memCLongBuffer(nmemReallocChecked(ptr == null ? 0L : ptr.address, getAllocationSize(size, Pointer.CLONG_SIZE)), size);
      if (ptr != null) {
         buffer.position(Math.min(ptr.position(), size));
      }

      return buffer;
   }

   public static FloatBuffer memRealloc(@Nullable FloatBuffer ptr, int size) {
      return realloc(ptr, memFloatBuffer(nmemReallocChecked(ptr == null ? 0L : UNSAFE.getLong(ptr, ADDRESS), getAllocationSize(size, 2)), size), size);
   }

   public static DoubleBuffer memRealloc(@Nullable DoubleBuffer ptr, int size) {
      return realloc(ptr, memDoubleBuffer(nmemReallocChecked(ptr == null ? 0L : UNSAFE.getLong(ptr, ADDRESS), getAllocationSize(size, 3)), size), size);
   }

   public static PointerBuffer memRealloc(@Nullable PointerBuffer ptr, int size) {
      PointerBuffer buffer = memPointerBuffer(nmemReallocChecked(ptr == null ? 0L : ptr.address, getAllocationSize(size, Pointer.POINTER_SHIFT)), size);
      if (ptr != null) {
         buffer.position(Math.min(ptr.position(), size));
      }

      return buffer;
   }

   public static long nmemAlignedAlloc(long alignment, long size) {
      return MemoryUtil.LazyInit.ALLOCATOR.aligned_alloc(alignment, size);
   }

   public static long nmemAlignedAllocChecked(long alignment, long size) {
      long address = nmemAlignedAlloc(alignment, size != 0L ? size : 1L);
      if (Checks.CHECKS && address == 0L) {
         throw new OutOfMemoryError();
      } else {
         return address;
      }
   }

   public static ByteBuffer memAlignedAlloc(int alignment, int size) {
      return wrap(BUFFER_BYTE, nmemAlignedAllocChecked(alignment, size), size).order(NATIVE_ORDER);
   }

   public static void nmemAlignedFree(long ptr) {
      MemoryUtil.LazyInit.ALLOCATOR.aligned_free(ptr);
   }

   public static void memAlignedFree(@Nullable ByteBuffer ptr) {
      if (ptr != null) {
         nmemAlignedFree(UNSAFE.getLong(ptr, ADDRESS));
      }
   }

   public static void memReport(MemoryUtil.MemoryAllocationReport report) {
      MemoryManage.DebugAllocator.report(report);
   }

   public static void memReport(MemoryUtil.MemoryAllocationReport report, MemoryUtil.MemoryAllocationReport.Aggregate groupByStackTrace, boolean groupByThread) {
      MemoryManage.DebugAllocator.report(report, groupByStackTrace, groupByThread);
   }

   public static long memAddress0(Buffer buffer) {
      return UNSAFE.getLong(buffer, ADDRESS);
   }

   public static long memAddress(ByteBuffer buffer) {
      return buffer.position() + memAddress0(buffer);
   }

   public static long memAddress(ByteBuffer buffer, int position) {
      Objects.requireNonNull(buffer);
      return memAddress0(buffer) + Integer.toUnsignedLong(position);
   }

   private static long address(int position, int elementShift, long address) {
      return address + ((position & 4294967295L) << elementShift);
   }

   public static long memAddress(ShortBuffer buffer) {
      return address(buffer.position(), 1, memAddress0(buffer));
   }

   public static long memAddress(ShortBuffer buffer, int position) {
      Objects.requireNonNull(buffer);
      return address(position, 1, memAddress0(buffer));
   }

   public static long memAddress(CharBuffer buffer) {
      return address(buffer.position(), 1, memAddress0(buffer));
   }

   public static long memAddress(CharBuffer buffer, int position) {
      Objects.requireNonNull(buffer);
      return address(position, 1, memAddress0(buffer));
   }

   public static long memAddress(IntBuffer buffer) {
      return address(buffer.position(), 2, memAddress0(buffer));
   }

   public static long memAddress(IntBuffer buffer, int position) {
      Objects.requireNonNull(buffer);
      return address(position, 2, memAddress0(buffer));
   }

   public static long memAddress(FloatBuffer buffer) {
      return address(buffer.position(), 2, memAddress0(buffer));
   }

   public static long memAddress(FloatBuffer buffer, int position) {
      Objects.requireNonNull(buffer);
      return address(position, 2, memAddress0(buffer));
   }

   public static long memAddress(LongBuffer buffer) {
      return address(buffer.position(), 3, memAddress0(buffer));
   }

   public static long memAddress(LongBuffer buffer, int position) {
      Objects.requireNonNull(buffer);
      return address(position, 3, memAddress0(buffer));
   }

   public static long memAddress(DoubleBuffer buffer) {
      return address(buffer.position(), 3, memAddress0(buffer));
   }

   public static long memAddress(DoubleBuffer buffer, int position) {
      Objects.requireNonNull(buffer);
      return address(position, 3, memAddress0(buffer));
   }

   public static long memAddress(Buffer buffer) {
      int elementShift;
      if (buffer instanceof ByteBuffer) {
         elementShift = 0;
      } else if (buffer instanceof ShortBuffer || buffer instanceof CharBuffer) {
         elementShift = 1;
      } else if (!(buffer instanceof IntBuffer) && !(buffer instanceof FloatBuffer)) {
         elementShift = 3;
      } else {
         elementShift = 2;
      }

      return address(buffer.position(), elementShift, memAddress0(buffer));
   }

   public static long memAddress(CustomBuffer<?> buffer) {
      return buffer.address();
   }

   public static long memAddress(CustomBuffer<?> buffer, int position) {
      return buffer.address(position);
   }

   public static long memAddressSafe(@Nullable ByteBuffer buffer) {
      return buffer == null ? 0L : memAddress0(buffer) + buffer.position();
   }

   public static long memAddressSafe(@Nullable ShortBuffer buffer) {
      return buffer == null ? 0L : address(buffer.position(), 1, memAddress0(buffer));
   }

   public static long memAddressSafe(@Nullable CharBuffer buffer) {
      return buffer == null ? 0L : address(buffer.position(), 1, memAddress0(buffer));
   }

   public static long memAddressSafe(@Nullable IntBuffer buffer) {
      return buffer == null ? 0L : address(buffer.position(), 2, memAddress0(buffer));
   }

   public static long memAddressSafe(@Nullable FloatBuffer buffer) {
      return buffer == null ? 0L : address(buffer.position(), 2, memAddress0(buffer));
   }

   public static long memAddressSafe(@Nullable LongBuffer buffer) {
      return buffer == null ? 0L : address(buffer.position(), 3, memAddress0(buffer));
   }

   public static long memAddressSafe(@Nullable DoubleBuffer buffer) {
      return buffer == null ? 0L : address(buffer.position(), 3, memAddress0(buffer));
   }

   public static long memAddressSafe(@Nullable Pointer pointer) {
      return pointer == null ? 0L : pointer.address();
   }

   public static ByteBuffer memByteBuffer(long address, int capacity) {
      if (Checks.CHECKS) {
         Checks.check(address);
      }

      return wrap(BUFFER_BYTE, address, capacity).order(NATIVE_ORDER);
   }

   @Nullable
   public static ByteBuffer memByteBufferSafe(long address, int capacity) {
      return address == 0L ? null : wrap(BUFFER_BYTE, address, capacity).order(NATIVE_ORDER);
   }

   public static ByteBuffer memByteBuffer(ShortBuffer buffer) {
      if (Checks.CHECKS && 1073741823 < buffer.remaining()) {
         throw new IllegalArgumentException("The source buffer range is too wide");
      } else {
         return wrap(BUFFER_BYTE, memAddress(buffer), buffer.remaining() << 1).order(NATIVE_ORDER);
      }
   }

   public static ByteBuffer memByteBuffer(CharBuffer buffer) {
      if (Checks.CHECKS && 1073741823 < buffer.remaining()) {
         throw new IllegalArgumentException("The source buffer range is too wide");
      } else {
         return wrap(BUFFER_BYTE, memAddress(buffer), buffer.remaining() << 1).order(NATIVE_ORDER);
      }
   }

   public static ByteBuffer memByteBuffer(IntBuffer buffer) {
      if (Checks.CHECKS && 536870911 < buffer.remaining()) {
         throw new IllegalArgumentException("The source buffer range is too wide");
      } else {
         return wrap(BUFFER_BYTE, memAddress(buffer), buffer.remaining() << 2).order(NATIVE_ORDER);
      }
   }

   public static ByteBuffer memByteBuffer(LongBuffer buffer) {
      if (Checks.CHECKS && 268435455 < buffer.remaining()) {
         throw new IllegalArgumentException("The source buffer range is too wide");
      } else {
         return wrap(BUFFER_BYTE, memAddress(buffer), buffer.remaining() << 3).order(NATIVE_ORDER);
      }
   }

   public static ByteBuffer memByteBuffer(FloatBuffer buffer) {
      if (Checks.CHECKS && 536870911 < buffer.remaining()) {
         throw new IllegalArgumentException("The source buffer range is too wide");
      } else {
         return wrap(BUFFER_BYTE, memAddress(buffer), buffer.remaining() << 2).order(NATIVE_ORDER);
      }
   }

   public static ByteBuffer memByteBuffer(DoubleBuffer buffer) {
      if (Checks.CHECKS && 268435455 < buffer.remaining()) {
         throw new IllegalArgumentException("The source buffer range is too wide");
      } else {
         return wrap(BUFFER_BYTE, memAddress(buffer), buffer.remaining() << 3).order(NATIVE_ORDER);
      }
   }

   public static ByteBuffer memByteBuffer(CustomBuffer<?> buffer) {
      if (Checks.CHECKS && Integer.MAX_VALUE / buffer.sizeof() < buffer.remaining()) {
         throw new IllegalArgumentException("The source buffer range is too wide");
      } else {
         return wrap(BUFFER_BYTE, memAddress(buffer), buffer.remaining() * buffer.sizeof()).order(NATIVE_ORDER);
      }
   }

   public static ShortBuffer memShortBuffer(long address, int capacity) {
      if (Checks.CHECKS) {
         Checks.check(address);
      }

      return wrap(BUFFER_SHORT, address, capacity);
   }

   @Nullable
   public static ShortBuffer memShortBufferSafe(long address, int capacity) {
      return address == 0L ? null : wrap(BUFFER_SHORT, address, capacity);
   }

   public static CharBuffer memCharBuffer(long address, int capacity) {
      if (Checks.CHECKS) {
         Checks.check(address);
      }

      return wrap(BUFFER_CHAR, address, capacity);
   }

   @Nullable
   public static CharBuffer memCharBufferSafe(long address, int capacity) {
      return address == 0L ? null : wrap(BUFFER_CHAR, address, capacity);
   }

   public static IntBuffer memIntBuffer(long address, int capacity) {
      if (Checks.CHECKS) {
         Checks.check(address);
      }

      return wrap(BUFFER_INT, address, capacity);
   }

   @Nullable
   public static IntBuffer memIntBufferSafe(long address, int capacity) {
      return address == 0L ? null : wrap(BUFFER_INT, address, capacity);
   }

   public static LongBuffer memLongBuffer(long address, int capacity) {
      if (Checks.CHECKS) {
         Checks.check(address);
      }

      return wrap(BUFFER_LONG, address, capacity);
   }

   @Nullable
   public static LongBuffer memLongBufferSafe(long address, int capacity) {
      return address == 0L ? null : wrap(BUFFER_LONG, address, capacity);
   }

   public static CLongBuffer memCLongBuffer(long address, int capacity) {
      if (Checks.CHECKS) {
         Checks.check(address);
      }

      return Pointer.Default.wrap(CLongBuffer.class, address, capacity);
   }

   @Nullable
   public static CLongBuffer memCLongBufferSafe(long address, int capacity) {
      return address == 0L ? null : Pointer.Default.wrap(CLongBuffer.class, address, capacity);
   }

   public static FloatBuffer memFloatBuffer(long address, int capacity) {
      if (Checks.CHECKS) {
         Checks.check(address);
      }

      return wrap(BUFFER_FLOAT, address, capacity);
   }

   @Nullable
   public static FloatBuffer memFloatBufferSafe(long address, int capacity) {
      return address == 0L ? null : wrap(BUFFER_FLOAT, address, capacity);
   }

   public static DoubleBuffer memDoubleBuffer(long address, int capacity) {
      if (Checks.CHECKS) {
         Checks.check(address);
      }

      return wrap(BUFFER_DOUBLE, address, capacity);
   }

   @Nullable
   public static DoubleBuffer memDoubleBufferSafe(long address, int capacity) {
      return address == 0L ? null : wrap(BUFFER_DOUBLE, address, capacity);
   }

   public static PointerBuffer memPointerBuffer(long address, int capacity) {
      if (Checks.CHECKS) {
         Checks.check(address);
      }

      return Pointer.Default.wrap(PointerBuffer.class, address, capacity);
   }

   @Nullable
   public static PointerBuffer memPointerBufferSafe(long address, int capacity) {
      return address == 0L ? null : Pointer.Default.wrap(PointerBuffer.class, address, capacity);
   }

   public static ByteBuffer memDuplicate(ByteBuffer buffer) {
      ByteBuffer target;
      try {
         target = (ByteBuffer)UNSAFE.allocateInstance(BUFFER_BYTE);
      } catch (InstantiationException e) {
         throw new UnsupportedOperationException(e);
      }

      UNSAFE.putLong(target, ADDRESS, UNSAFE.getLong(buffer, ADDRESS));
      UNSAFE.putInt(target, MARK, UNSAFE.getInt(buffer, MARK));
      UNSAFE.putInt(target, POSITION, UNSAFE.getInt(buffer, POSITION));
      UNSAFE.putInt(target, LIMIT, UNSAFE.getInt(buffer, LIMIT));
      UNSAFE.putInt(target, CAPACITY, UNSAFE.getInt(buffer, CAPACITY));
      Object attachment = UNSAFE.getObject(buffer, PARENT_BYTE);
      UNSAFE.putObject(target, PARENT_BYTE, attachment == null ? buffer : attachment);
      return target.order(buffer.order());
   }

   public static ShortBuffer memDuplicate(ShortBuffer buffer) {
      return duplicate(BUFFER_SHORT, buffer, PARENT_SHORT);
   }

   public static CharBuffer memDuplicate(CharBuffer buffer) {
      return duplicate(BUFFER_CHAR, buffer, PARENT_CHAR);
   }

   public static IntBuffer memDuplicate(IntBuffer buffer) {
      return duplicate(BUFFER_INT, buffer, PARENT_INT);
   }

   public static LongBuffer memDuplicate(LongBuffer buffer) {
      return duplicate(BUFFER_LONG, buffer, PARENT_LONG);
   }

   public static FloatBuffer memDuplicate(FloatBuffer buffer) {
      return duplicate(BUFFER_FLOAT, buffer, PARENT_FLOAT);
   }

   public static DoubleBuffer memDuplicate(DoubleBuffer buffer) {
      return duplicate(BUFFER_DOUBLE, buffer, PARENT_DOUBLE);
   }

   public static ByteBuffer memSlice(ByteBuffer buffer) {
      return slice(buffer, memAddress0(buffer) + buffer.position(), buffer.remaining());
   }

   public static ShortBuffer memSlice(ShortBuffer buffer) {
      return slice(BUFFER_SHORT, buffer, address(buffer.position(), 1, memAddress0(buffer)), buffer.remaining(), PARENT_SHORT);
   }

   public static CharBuffer memSlice(CharBuffer buffer) {
      return slice(BUFFER_CHAR, buffer, address(buffer.position(), 1, memAddress0(buffer)), buffer.remaining(), PARENT_CHAR);
   }

   public static IntBuffer memSlice(IntBuffer buffer) {
      return slice(BUFFER_INT, buffer, address(buffer.position(), 2, memAddress0(buffer)), buffer.remaining(), PARENT_INT);
   }

   public static LongBuffer memSlice(LongBuffer buffer) {
      return slice(BUFFER_LONG, buffer, address(buffer.position(), 3, memAddress0(buffer)), buffer.remaining(), PARENT_LONG);
   }

   public static FloatBuffer memSlice(FloatBuffer buffer) {
      return slice(BUFFER_FLOAT, buffer, address(buffer.position(), 2, memAddress0(buffer)), buffer.remaining(), PARENT_FLOAT);
   }

   public static DoubleBuffer memSlice(DoubleBuffer buffer) {
      return slice(BUFFER_DOUBLE, buffer, address(buffer.position(), 3, memAddress0(buffer)), buffer.remaining(), PARENT_DOUBLE);
   }

   public static ByteBuffer memSlice(ByteBuffer buffer, int offset, int capacity) {
      int position = buffer.position() + offset;
      if (offset < 0 || buffer.limit() < position) {
         throw new IllegalArgumentException();
      } else if (capacity >= 0 && buffer.capacity() - position >= capacity) {
         return slice(buffer, memAddress0(buffer) + position, capacity);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public static ShortBuffer memSlice(ShortBuffer buffer, int offset, int capacity) {
      int position = buffer.position() + offset;
      if (offset < 0 || buffer.limit() < position) {
         throw new IllegalArgumentException();
      } else if (capacity >= 0 && buffer.capacity() - position >= capacity) {
         return slice(BUFFER_SHORT, buffer, address(position, 1, memAddress0(buffer)), capacity, PARENT_SHORT);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public static CharBuffer memSlice(CharBuffer buffer, int offset, int capacity) {
      int position = buffer.position() + offset;
      if (offset < 0 || buffer.limit() < position) {
         throw new IllegalArgumentException();
      } else if (capacity >= 0 && buffer.capacity() - position >= capacity) {
         return slice(BUFFER_CHAR, buffer, address(position, 1, memAddress0(buffer)), capacity, PARENT_CHAR);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public static IntBuffer memSlice(IntBuffer buffer, int offset, int capacity) {
      int position = buffer.position() + offset;
      if (offset < 0 || buffer.limit() < position) {
         throw new IllegalArgumentException();
      } else if (capacity >= 0 && buffer.capacity() - position >= capacity) {
         return slice(BUFFER_INT, buffer, address(position, 2, memAddress0(buffer)), capacity, PARENT_INT);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public static LongBuffer memSlice(LongBuffer buffer, int offset, int capacity) {
      int position = buffer.position() + offset;
      if (offset < 0 || buffer.limit() < position) {
         throw new IllegalArgumentException();
      } else if (capacity >= 0 && buffer.capacity() - position >= capacity) {
         return slice(BUFFER_LONG, buffer, address(position, 3, memAddress0(buffer)), capacity, PARENT_LONG);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public static FloatBuffer memSlice(FloatBuffer buffer, int offset, int capacity) {
      int position = buffer.position() + offset;
      if (offset < 0 || buffer.limit() < position) {
         throw new IllegalArgumentException();
      } else if (capacity >= 0 && buffer.capacity() - position >= capacity) {
         return slice(BUFFER_FLOAT, buffer, address(position, 2, memAddress0(buffer)), capacity, PARENT_FLOAT);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public static DoubleBuffer memSlice(DoubleBuffer buffer, int offset, int capacity) {
      int position = buffer.position() + offset;
      if (offset < 0 || buffer.limit() < position) {
         throw new IllegalArgumentException();
      } else if (capacity >= 0 && buffer.capacity() - position >= capacity) {
         return slice(BUFFER_DOUBLE, buffer, address(position, 3, memAddress0(buffer)), capacity, PARENT_DOUBLE);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public static <T extends CustomBuffer<T>> T memSlice(T buffer, int offset, int capacity) {
      return buffer.slice(offset, capacity);
   }

   public static void memSet(ByteBuffer ptr, int value) {
      memSet(memAddress(ptr), value, ptr.remaining());
   }

   public static void memSet(ShortBuffer ptr, int value) {
      memSet(memAddress(ptr), value, APIUtil.apiGetBytes(ptr.remaining(), 1));
   }

   public static void memSet(CharBuffer ptr, int value) {
      memSet(memAddress(ptr), value, APIUtil.apiGetBytes(ptr.remaining(), 1));
   }

   public static void memSet(IntBuffer ptr, int value) {
      memSet(memAddress(ptr), value, APIUtil.apiGetBytes(ptr.remaining(), 2));
   }

   public static void memSet(LongBuffer ptr, int value) {
      memSet(memAddress(ptr), value, APIUtil.apiGetBytes(ptr.remaining(), 3));
   }

   public static void memSet(FloatBuffer ptr, int value) {
      memSet(memAddress(ptr), value, APIUtil.apiGetBytes(ptr.remaining(), 2));
   }

   public static void memSet(DoubleBuffer ptr, int value) {
      memSet(memAddress(ptr), value, APIUtil.apiGetBytes(ptr.remaining(), 3));
   }

   public static <T extends CustomBuffer<T>> void memSet(T ptr, int value) {
      memSet(memAddress(ptr), value, Integer.toUnsignedLong(ptr.remaining()) * ptr.sizeof());
   }

   public static <T extends Struct> void memSet(T ptr, int value) {
      memSet(ptr.address, value, ptr.sizeof());
   }

   public static void memCopy(ByteBuffer src, ByteBuffer dst) {
      if (Checks.CHECKS) {
         Checks.check(dst, src.remaining());
      }

      MultiReleaseMemCopy.copy(memAddress(src), memAddress(dst), src.remaining());
   }

   public static void memCopy(ShortBuffer src, ShortBuffer dst) {
      if (Checks.CHECKS) {
         Checks.check(dst, src.remaining());
      }

      MultiReleaseMemCopy.copy(memAddress(src), memAddress(dst), APIUtil.apiGetBytes(src.remaining(), 1));
   }

   public static void memCopy(CharBuffer src, CharBuffer dst) {
      if (Checks.CHECKS) {
         Checks.check((Buffer)dst, src.remaining());
      }

      MultiReleaseMemCopy.copy(memAddress(src), memAddress(dst), APIUtil.apiGetBytes(src.remaining(), 1));
   }

   public static void memCopy(IntBuffer src, IntBuffer dst) {
      if (Checks.CHECKS) {
         Checks.check(dst, src.remaining());
      }

      MultiReleaseMemCopy.copy(memAddress(src), memAddress(dst), APIUtil.apiGetBytes(src.remaining(), 2));
   }

   public static void memCopy(LongBuffer src, LongBuffer dst) {
      if (Checks.CHECKS) {
         Checks.check(dst, src.remaining());
      }

      MultiReleaseMemCopy.copy(memAddress(src), memAddress(dst), APIUtil.apiGetBytes(src.remaining(), 3));
   }

   public static void memCopy(FloatBuffer src, FloatBuffer dst) {
      if (Checks.CHECKS) {
         Checks.check(dst, src.remaining());
      }

      MultiReleaseMemCopy.copy(memAddress(src), memAddress(dst), APIUtil.apiGetBytes(src.remaining(), 2));
   }

   public static void memCopy(DoubleBuffer src, DoubleBuffer dst) {
      if (Checks.CHECKS) {
         Checks.check(dst, src.remaining());
      }

      MultiReleaseMemCopy.copy(memAddress(src), memAddress(dst), APIUtil.apiGetBytes(src.remaining(), 3));
   }

   public static <T extends CustomBuffer<T>> void memCopy(T src, T dst) {
      if (Checks.CHECKS) {
         Checks.check(dst, src.remaining());
      }

      MultiReleaseMemCopy.copy(memAddress(src), memAddress(dst), Integer.toUnsignedLong(src.remaining()) * src.sizeof());
   }

   public static <T extends Struct> void memCopy(T src, T dst) {
      MultiReleaseMemCopy.copy(src.address, dst.address, src.sizeof());
   }

   public static void memSet(long ptr, int value, long bytes) {
      if (!Checks.DEBUG || ptr != 0L && bytes >= 0L) {
         if (bytes < 256L) {
            int p = (int)ptr;
            if (Pointer.BITS64) {
               if ((p & 7) == 0) {
                  memSet64(ptr, value, (int)bytes & 0xFF);
                  return;
               }
            } else if ((p & 3) == 0) {
               memSet32(p, value, (int)bytes & 0xFF);
               return;
            }
         }

         LibCString.nmemset(ptr, value, bytes);
      } else {
         throw new IllegalArgumentException();
      }
   }

   private static void memSet64(long ptr, int value, int bytes) {
      long fill = (value & 0xFF) * FILL_PATTERN_64;
      int remaining = bytes;

      while (8 <= remaining) {
         UNSAFE.putLong(null, ptr, fill);
         remaining -= 8;
         ptr += 8L;
      }

      if (remaining != 0) {
         long mask = SHIFT.right(-1L, remaining);
         UNSAFE.putLong(null, ptr, fill ^ (fill ^ UNSAFE.getLong(null, ptr)) & mask);
      }
   }

   private static void memSet32(int ptr, int value, int bytes) {
      int fill = (value & 0xFF) * FILL_PATTERN_32;

      int i;
      for (i = 0; i <= bytes - 4; i += 4) {
         UNSAFE.putInt(null, ptr + i, fill);
      }

      while (i < bytes) {
         UNSAFE.putByte(null, ptr + i, (byte)fill);
         i++;
      }
   }

   private static long merge(long a, long b, long mask) {
      return a ^ (a ^ b) & mask;
   }

   public static void memCopy(long src, long dst, long bytes) {
      if (!Checks.DEBUG || src != 0L && dst != 0L && bytes >= 0L) {
         MultiReleaseMemCopy.copy(src, dst, bytes);
      } else {
         throw new IllegalArgumentException();
      }
   }

   static void memCopyAligned64(long src, long dst, int bytes) {
      int i;
      for (i = 0; i <= bytes - 8; i += 8) {
         UNSAFE.putLong(null, dst + i, UNSAFE.getLong(null, src + i));
      }

      if (i < bytes) {
         UNSAFE.putLong(null, dst + i, merge(UNSAFE.getLong(null, src + i), UNSAFE.getLong(null, dst + i), SHIFT.right(-1L, bytes - i)));
      }
   }

   static void memCopyAligned32(int src, int dst, int bytes) {
      int i = 0;

      while (i <= bytes - 4) {
         UNSAFE.putInt(null, dst, UNSAFE.getInt(null, src));
         i += 4;
         dst += 4;
         src += 4;
      }

      while (i < bytes) {
         UNSAFE.putByte(null, dst, UNSAFE.getByte(null, src));
         i++;
         dst++;
         src++;
      }
   }

   public static boolean memGetBoolean(long ptr) {
      return UNSAFE.getByte(null, ptr) != 0;
   }

   public static byte memGetByte(long ptr) {
      return UNSAFE.getByte(null, ptr);
   }

   public static short memGetShort(long ptr) {
      return UNSAFE.getShort(null, ptr);
   }

   public static int memGetInt(long ptr) {
      return UNSAFE.getInt(null, ptr);
   }

   public static long memGetLong(long ptr) {
      return UNSAFE.getLong(null, ptr);
   }

   public static float memGetFloat(long ptr) {
      return UNSAFE.getFloat(null, ptr);
   }

   public static double memGetDouble(long ptr) {
      return UNSAFE.getDouble(null, ptr);
   }

   public static long memGetCLong(long ptr) {
      return Pointer.CLONG_SIZE == 8 ? UNSAFE.getLong(null, ptr) : UNSAFE.getInt(null, ptr);
   }

   public static long memGetAddress(long ptr) {
      return Pointer.BITS64 ? UNSAFE.getLong(null, ptr) : UNSAFE.getInt(null, ptr) & 4294967295L;
   }

   public static void memPutByte(long ptr, byte value) {
      UNSAFE.putByte(null, ptr, value);
   }

   public static void memPutShort(long ptr, short value) {
      UNSAFE.putShort(null, ptr, value);
   }

   public static void memPutInt(long ptr, int value) {
      UNSAFE.putInt(null, ptr, value);
   }

   public static void memPutLong(long ptr, long value) {
      UNSAFE.putLong(null, ptr, value);
   }

   public static void memPutFloat(long ptr, float value) {
      UNSAFE.putFloat(null, ptr, value);
   }

   public static void memPutDouble(long ptr, double value) {
      UNSAFE.putDouble(null, ptr, value);
   }

   public static void memPutCLong(long ptr, long value) {
      if (Pointer.CLONG_SIZE == 8) {
         UNSAFE.putLong(null, ptr, value);
      } else {
         UNSAFE.putInt(null, ptr, (int)value);
      }
   }

   public static void memPutAddress(long ptr, long value) {
      if (Pointer.BITS64) {
         UNSAFE.putLong(null, ptr, value);
      } else {
         UNSAFE.putInt(null, ptr, (int)value);
      }
   }

   public static native <T> T memGlobalRefToObject(long var0);

   private static int write8(long target, int offset, int value) {
      UNSAFE.putByte(null, target + Integer.toUnsignedLong(offset), (byte)value);
      return offset + 1;
   }

   private static int write8Safe(long target, int offset, int maxLength, int value) {
      if (offset == maxLength) {
         throw new BufferOverflowException();
      }

      UNSAFE.putByte(null, target + Integer.toUnsignedLong(offset), (byte)value);
      return offset + 1;
   }

   private static int write16(long target, int offset, char value) {
      UNSAFE.putShort(null, target + Integer.toUnsignedLong(offset), (short)value);
      return offset + 2;
   }

   public static ByteBuffer memASCII(CharSequence text) {
      return memASCII(text, true);
   }

   @Nullable
   public static ByteBuffer memASCIISafe(@Nullable CharSequence text) {
      return text == null ? null : memASCII(text, true);
   }

   public static ByteBuffer memASCII(CharSequence text, boolean nullTerminated) {
      int length = memLengthASCII(text, nullTerminated);
      long target = nmemAlloc(length);
      if (Checks.CHECKS && target == 0L) {
         throw new OutOfMemoryError();
      }

      encodeASCIIUnsafe(text, nullTerminated, target);
      return wrap(BUFFER_BYTE, target, length).order(NATIVE_ORDER);
   }

   @Nullable
   public static ByteBuffer memASCIISafe(@Nullable CharSequence text, boolean nullTerminated) {
      return text == null ? null : memASCII(text, nullTerminated);
   }

   public static int memASCII(CharSequence text, boolean nullTerminated, ByteBuffer target) {
      if (target.remaining() < memLengthASCII(text, nullTerminated)) {
         throw new BufferOverflowException();
      }

      long address = memAddress(target);
      return encodeASCIIUnsafe(text, nullTerminated, address);
   }

   public static int memASCII(CharSequence text, boolean nullTerminated, ByteBuffer target, int offset) {
      if (target.capacity() - offset < memLengthASCII(text, nullTerminated)) {
         throw new BufferOverflowException();
      } else {
         return encodeASCIIUnsafe(text, nullTerminated, memAddress(target, offset));
      }
   }

   public static int encodeASCIIUnsafe(CharSequence text, boolean nullTerminated, long target) {
      int i = 0;
      int len = text.length();

      while (i < len) {
         i = write8(target, i, text.charAt(i));
      }

      if (nullTerminated) {
         i = write8(target, i, 0);
      }

      return i;
   }

   public static int memLengthASCII(CharSequence value, boolean nullTerminated) {
      int len = value.length() + (nullTerminated ? 1 : 0);
      if (len < 0) {
         throw new BufferOverflowException();
      } else {
         return len;
      }
   }

   public static ByteBuffer memUTF8(CharSequence text) {
      return memUTF8(text, true);
   }

   @Nullable
   public static ByteBuffer memUTF8Safe(@Nullable CharSequence text) {
      return text == null ? null : memUTF8(text, true);
   }

   public static ByteBuffer memUTF8(CharSequence text, boolean nullTerminated) {
      int length = memLengthUTF8(text, nullTerminated);
      long target = nmemAlloc(length);
      if (Checks.CHECKS && target == 0L) {
         throw new OutOfMemoryError();
      }

      encodeUTF8Unsafe(text, nullTerminated, target);
      return wrap(BUFFER_BYTE, target, length).order(NATIVE_ORDER);
   }

   @Nullable
   public static ByteBuffer memUTF8Safe(@Nullable CharSequence text, boolean nullTerminated) {
      return text == null ? null : memUTF8(text, nullTerminated);
   }

   public static int memUTF8(CharSequence text, boolean nullTerminated, ByteBuffer target) {
      if (target.remaining() < memLengthASCII(text, nullTerminated)) {
         throw new BufferOverflowException();
      } else {
         return encodeUTF8Safe(text, nullTerminated, memAddress(target), target.remaining());
      }
   }

   public static int memUTF8(CharSequence text, boolean nullTerminated, ByteBuffer target, int offset) {
      if (target.capacity() - offset < memLengthASCII(text, nullTerminated)) {
         throw new BufferOverflowException();
      } else {
         return encodeUTF8Safe(text, nullTerminated, memAddress(target, offset), target.capacity() - offset);
      }
   }

   public static int encodeUTF8Unsafe(CharSequence text, boolean nullTerminated, long target) {
      int p = 0;
      int i = 0;
      int len = text.length();

      while (i < len) {
         char c = text.charAt(i++);
         if (c < 128) {
            p = write8(target, p, c);
         } else {
            int cp = c;
            if (c < 2048) {
               p = write8(target, p, 192 | cp >> 6);
            } else {
               if (!Character.isHighSurrogate(c)) {
                  p = write8(target, p, 224 | cp >> 12);
               } else {
                  cp = Character.toCodePoint(c, text.charAt(i++));
                  p = write8(target, p, 240 | cp >> 18);
                  p = write8(target, p, 128 | cp >> 12 & 63);
               }

               p = write8(target, p, 128 | cp >> 6 & 63);
            }

            p = write8(target, p, 128 | cp & 63);
         }
      }

      if (nullTerminated) {
         p = write8(target, p, 0);
      }

      return p;
   }

   static int encodeUTF8Safe(CharSequence text, boolean nullTerminated, long target, int maxLength) {
      int p = 0;
      int i = 0;

      int length;
      for (length = text.length(); i < length; i++) {
         char c = text.charAt(i);
         if (128 <= c) {
            break;
         }

         p = write8(target, p, c);
      }

      while (i < length) {
         char c = text.charAt(i++);
         if (c < 128) {
            p = write8Safe(target, p, maxLength, c);
         } else {
            int cp = c;
            if (c < 2048) {
               p = write8Safe(target, p, maxLength, 192 | cp >> 6);
            } else {
               if (!Character.isHighSurrogate(c)) {
                  p = write8Safe(target, p, maxLength, 224 | cp >> 12);
               } else {
                  cp = Character.toCodePoint(c, text.charAt(i++));
                  p = write8Safe(target, p, maxLength, 240 | cp >> 18);
                  p = write8Safe(target, p, maxLength, 128 | cp >> 12 & 63);
               }

               p = write8Safe(target, p, maxLength, 128 | cp >> 6 & 63);
            }

            p = write8Safe(target, p, maxLength, 128 | cp & 63);
         }
      }

      if (nullTerminated) {
         p = write8Safe(target, p, maxLength, 0);
      }

      return p;
   }

   public static int memLengthUTF8(CharSequence value, boolean nullTerminated) {
      int len = value.length();
      int bytes = len + (nullTerminated ? 1 : 0);

      for (int i = 0; i < len; i++) {
         char c = value.charAt(i);
         if (c >= 128) {
            if (c < 2048) {
               bytes += 127 - c >>> 31;
            } else {
               bytes += 2;
               if (Character.isHighSurrogate(c)) {
                  i++;
               }
            }

            if (bytes < 0) {
               throw new BufferOverflowException();
            }
         }
      }

      if (bytes < 0) {
         throw new BufferOverflowException();
      } else {
         return bytes;
      }
   }

   public static ByteBuffer memUTF16(CharSequence text) {
      return memUTF16(text, true);
   }

   @Nullable
   public static ByteBuffer memUTF16Safe(@Nullable CharSequence text) {
      return text == null ? null : memUTF16(text, true);
   }

   public static ByteBuffer memUTF16(CharSequence text, boolean nullTerminated) {
      int length = memLengthUTF16(text, nullTerminated);
      long target = nmemAlloc(length);
      if (Checks.CHECKS && target == 0L) {
         throw new OutOfMemoryError();
      }

      encodeUTF16Unsafe(text, nullTerminated, target);
      return wrap(BUFFER_BYTE, target, length).order(NATIVE_ORDER);
   }

   @Nullable
   public static ByteBuffer memUTF16Safe(@Nullable CharSequence text, boolean nullTerminated) {
      return text == null ? null : memUTF16(text, nullTerminated);
   }

   public static int memUTF16(CharSequence text, boolean nullTerminated, ByteBuffer target) {
      if (target.remaining() < memLengthUTF16(text, nullTerminated)) {
         throw new BufferOverflowException();
      }

      long address = memAddress(target);
      return encodeUTF16Unsafe(text, nullTerminated, address);
   }

   public static int memUTF16(CharSequence text, boolean nullTerminated, ByteBuffer target, int offset) {
      if (target.capacity() - offset < memLengthUTF16(text, nullTerminated)) {
         throw new BufferOverflowException();
      }

      long address = memAddress(target, offset);
      return encodeUTF16Unsafe(text, nullTerminated, address);
   }

   public static int encodeUTF16Unsafe(CharSequence text, boolean nullTerminated, long target) {
      int p = 0;
      int i = 0;
      int len = text.length();

      while (i < len) {
         p = write16(target, p, text.charAt(i++));
      }

      if (nullTerminated) {
         p = write16(target, p, '\u0000');
      }

      return p;
   }

   public static int memLengthUTF16(CharSequence value, boolean nullTerminated) {
      int len = value.length() + (nullTerminated ? 1 : 0);
      if (len >= 0 && 1073741823 >= len) {
         return len << 1;
      } else {
         throw new BufferOverflowException();
      }
   }

   private static int memLengthNT1(long address, int maxLength) {
      if (Checks.CHECKS) {
         Checks.check(address);
      }

      return Pointer.BITS64 ? strlen64NT1(address, maxLength) : strlen32NT1(address, maxLength);
   }

   private static int strlen64NT1(long address, int maxLength) {
      int i = 0;
      if (8 <= maxLength) {
         int misalignment = (int)address & 7;
         if (misalignment != 0) {
            for (int len = 8 - misalignment; i < len; i++) {
               if (UNSAFE.getByte(null, address + i) == 0) {
                  return i;
               }
            }
         }

         while (i <= maxLength - 8 && !MathUtil.mathHasZeroByte(UNSAFE.getLong(null, address + i))) {
            i += 8;
         }
      }

      while (i < maxLength && UNSAFE.getByte(null, address + i) != 0) {
         i++;
      }

      return i;
   }

   private static int strlen32NT1(long address, int maxLength) {
      int i = 0;
      if (4 <= maxLength) {
         int misalignment = (int)address & 3;
         if (misalignment != 0) {
            for (int len = 4 - misalignment; i < len; i++) {
               if (UNSAFE.getByte(null, address + i) == 0) {
                  return i;
               }
            }
         }

         while (i <= maxLength - 4 && !MathUtil.mathHasZeroByte(UNSAFE.getInt(null, address + i))) {
            i += 4;
         }
      }

      while (i < maxLength && UNSAFE.getByte(null, address + i) != 0) {
         i++;
      }

      return i;
   }

   public static int memLengthNT1(ByteBuffer buffer) {
      return memLengthNT1(memAddress(buffer), buffer.remaining());
   }

   private static int memLengthNT2(long address, int maxLength) {
      if (Checks.CHECKS) {
         Checks.check(address);
      }

      return Pointer.BITS64 ? strlen64NT2(address, maxLength) : strlen32NT2((int)address, maxLength);
   }

   private static int strlen64NT2(long address, int maxLength) {
      int i = 0;
      if (8 <= maxLength) {
         int misalignment = (int)address & 7;
         if (misalignment != 0) {
            for (int len = 8 - misalignment; i < len; i += 2) {
               if (UNSAFE.getShort(null, address + i) == 0) {
                  return i;
               }
            }
         }

         while (i <= maxLength - 8 && !MathUtil.mathHasZeroShort(UNSAFE.getLong(null, address + i))) {
            i += 8;
         }
      }

      while (i < maxLength && UNSAFE.getShort(null, address + i) != 0) {
         i += 2;
      }

      return i;
   }

   private static int strlen32NT2(long address, int maxLength) {
      int i = 0;
      if (4 <= maxLength) {
         int misalignment = (int)address & 3;
         if (misalignment != 0) {
            for (int len = 4 - misalignment; i < len; i += 2) {
               if (UNSAFE.getShort(null, address + i) == 0) {
                  return i;
               }
            }
         }

         while (i <= maxLength - 4 && !MathUtil.mathHasZeroShort(UNSAFE.getInt(null, address + i))) {
            i += 4;
         }
      }

      while (i < maxLength && UNSAFE.getShort(null, address + i) != 0) {
         i += 2;
      }

      return i;
   }

   public static int memLengthNT2(ByteBuffer buffer) {
      return memLengthNT2(memAddress(buffer), buffer.remaining());
   }

   public static ByteBuffer memByteBufferNT1(long address) {
      return memByteBuffer(address, memLengthNT1(address, Integer.MAX_VALUE));
   }

   public static ByteBuffer memByteBufferNT1(long address, int maxLength) {
      return memByteBuffer(address, memLengthNT1(address, maxLength));
   }

   @Nullable
   public static ByteBuffer memByteBufferNT1Safe(long address) {
      return address == 0L ? null : memByteBuffer(address, memLengthNT1(address, Integer.MAX_VALUE));
   }

   @Nullable
   public static ByteBuffer memByteBufferNT1Safe(long address, int maxLength) {
      return address == 0L ? null : memByteBuffer(address, memLengthNT1(address, maxLength));
   }

   public static ByteBuffer memByteBufferNT2(long address) {
      return memByteBufferNT2(address, 2147483646);
   }

   public static ByteBuffer memByteBufferNT2(long address, int maxLength) {
      if (Checks.DEBUG && (maxLength & 1) != 0) {
         throw new IllegalArgumentException("The maximum length must be an even number.");
      } else {
         return memByteBuffer(address, memLengthNT2(address, maxLength));
      }
   }

   @Nullable
   public static ByteBuffer memByteBufferNT2Safe(long address) {
      return address == 0L ? null : memByteBufferNT2(address, 2147483646);
   }

   @Nullable
   public static ByteBuffer memByteBufferNT2Safe(long address, int maxLength) {
      return address == 0L ? null : memByteBufferNT2(address, maxLength);
   }

   public static String memASCII(long address) {
      return memASCII(address, memLengthNT1(address, Integer.MAX_VALUE));
   }

   public static String memASCII(long address, int length) {
      if (length <= 0) {
         return "";
      }

      byte[] ascii = length <= ARRAY_TLC_SIZE ? ARRAY_TLC_BYTE.get() : new byte[length];
      memByteBuffer(address, length).get(ascii, 0, length);
      return new String(ascii, 0, 0, length);
   }

   public static String memASCII(ByteBuffer buffer) {
      return memASCII(memAddress(buffer), buffer.remaining());
   }

   @Nullable
   public static String memASCIISafe(long address) {
      return address == 0L ? null : memASCII(address, memLengthNT1(address, Integer.MAX_VALUE));
   }

   @Nullable
   public static String memASCIISafe(long address, int length) {
      return address == 0L ? null : memASCII(address, length);
   }

   @Nullable
   public static String memASCIISafe(@Nullable ByteBuffer buffer) {
      return buffer == null ? null : memASCII(memAddress(buffer), buffer.remaining());
   }

   public static String memASCII(ByteBuffer buffer, int length) {
      return memASCII(memAddress(buffer), length);
   }

   public static String memASCII(ByteBuffer buffer, int length, int offset) {
      return memASCII(memAddress(buffer, offset), length);
   }

   public static String memUTF8(long address) {
      return MultiReleaseTextDecoding.decodeUTF8(address, memLengthNT1(address, Integer.MAX_VALUE));
   }

   public static String memUTF8(long address, int length) {
      return MultiReleaseTextDecoding.decodeUTF8(address, length);
   }

   public static String memUTF8(ByteBuffer buffer) {
      return MultiReleaseTextDecoding.decodeUTF8(memAddress(buffer), buffer.remaining());
   }

   @Nullable
   public static String memUTF8Safe(long address) {
      return address == 0L ? null : MultiReleaseTextDecoding.decodeUTF8(address, memLengthNT1(address, Integer.MAX_VALUE));
   }

   @Nullable
   public static String memUTF8Safe(long address, int length) {
      return address == 0L ? null : MultiReleaseTextDecoding.decodeUTF8(address, length);
   }

   @Nullable
   public static String memUTF8Safe(@Nullable ByteBuffer buffer) {
      return buffer == null ? null : MultiReleaseTextDecoding.decodeUTF8(memAddress(buffer), buffer.remaining());
   }

   public static String memUTF8(ByteBuffer buffer, int length) {
      return MultiReleaseTextDecoding.decodeUTF8(memAddress(buffer), length);
   }

   public static String memUTF8(ByteBuffer buffer, int length, int offset) {
      return MultiReleaseTextDecoding.decodeUTF8(memAddress(buffer, offset), length);
   }

   public static String memUTF16(long address) {
      return memUTF16(address, memLengthNT2(address, 2147483646) >> 1);
   }

   public static String memUTF16(long address, int length) {
      if (length <= 0) {
         return "";
      } else if (Checks.DEBUG) {
         int len = length << 1;
         byte[] bytes = len <= ARRAY_TLC_SIZE ? ARRAY_TLC_BYTE.get() : new byte[len];
         memByteBuffer(address, len).get(bytes, 0, len);
         return new String(bytes, 0, len, UTF16);
      } else {
         char[] chars = length <= ARRAY_TLC_SIZE ? ARRAY_TLC_CHAR.get() : new char[length];
         memCharBuffer(address, length).get(chars, 0, length);
         return new String(chars, 0, length);
      }
   }

   public static String memUTF16(ByteBuffer buffer) {
      return memUTF16(memAddress(buffer), buffer.remaining() >> 1);
   }

   @Nullable
   public static String memUTF16Safe(long address) {
      return address == 0L ? null : memUTF16(address, memLengthNT2(address, 2147483646) >> 1);
   }

   @Nullable
   public static String memUTF16Safe(long address, int length) {
      return address == 0L ? null : memUTF16(address, length);
   }

   @Nullable
   public static String memUTF16Safe(@Nullable ByteBuffer buffer) {
      return buffer == null ? null : memUTF16(memAddress(buffer), buffer.remaining() >> 1);
   }

   public static String memUTF16(ByteBuffer buffer, int length) {
      return memUTF16(memAddress(buffer), length);
   }

   public static String memUTF16(ByteBuffer buffer, int length, int offset) {
      return memUTF16(memAddress(buffer, offset), length);
   }

   private static Unsafe getUnsafeInstance() {
      Field[] fields = Unsafe.class.getDeclaredFields();

      for (Field field : fields) {
         if (field.getType().equals(Unsafe.class)) {
            int modifiers = field.getModifiers();
            if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
               try {
                  field.setAccessible(true);
                  return (Unsafe)field.get(null);
               } catch (Exception var7) {
                  break;
               }
            }
         }
      }

      throw new UnsupportedOperationException("LWJGL requires sun.misc.Unsafe to be available.");
   }

   private static long getFieldOffset(Class<?> containerType, Class<?> fieldType, LongPredicate predicate) {
      for (Class<?> c = containerType; c != Object.class; c = c.getSuperclass()) {
         Field[] fields = c.getDeclaredFields();

         for (Field field : fields) {
            if (field.getType().isAssignableFrom(fieldType) && !Modifier.isStatic(field.getModifiers()) && !field.isSynthetic()) {
               long offset = UNSAFE.objectFieldOffset(field);
               if (predicate.test(offset)) {
                  return offset;
               }
            }
         }
      }

      throw new UnsupportedOperationException("Failed to find field offset in class.");
   }

   private static long getFieldOffsetInt(Object container, int value) {
      return getFieldOffset(container.getClass(), int.class, offset -> UNSAFE.getInt(container, offset) == value);
   }

   private static long getFieldOffsetObject(Object container, Object value) {
      return getFieldOffset(container.getClass(), value.getClass(), offset -> UNSAFE.getObject(container, offset) == value);
   }

   private static long getAddressOffset() {
      long MAGIC_ADDRESS = -2401053090268712947L & (Pointer.BITS32 ? 4294967295L : -1L);
      ByteBuffer bb = Objects.requireNonNull(JNINativeInterface.NewDirectByteBuffer(MAGIC_ADDRESS, 0L));
      return getFieldOffset(bb.getClass(), long.class, offset -> UNSAFE.getLong(bb, offset) == MAGIC_ADDRESS);
   }

   private static long getMarkOffset() {
      ByteBuffer bb = Objects.requireNonNull(JNINativeInterface.NewDirectByteBuffer(1L, 0L));
      return getFieldOffsetInt(bb, -1);
   }

   private static long getPositionOffset() {
      ByteBuffer bb = Objects.requireNonNull(JNINativeInterface.NewDirectByteBuffer(-1L, 219540062L));
      ((Buffer)bb).position(16435934);
      return getFieldOffsetInt(bb, 16435934);
   }

   private static long getLimitOffset() {
      ByteBuffer bb = Objects.requireNonNull(JNINativeInterface.NewDirectByteBuffer(-1L, 219540062L));
      ((Buffer)bb).limit(16435934);
      return getFieldOffsetInt(bb, 16435934);
   }

   private static long getCapacityOffset() {
      ByteBuffer bb = Objects.requireNonNull(JNINativeInterface.NewDirectByteBuffer(-1L, 219540062L));
      ((Buffer)bb).limit(0);
      return getFieldOffsetInt(bb, 219540062);
   }

   static <T extends Buffer> T wrap(Class<? extends T> clazz, long address, int capacity) {
      T buffer;
      try {
         buffer = (T)UNSAFE.allocateInstance(clazz);
      } catch (InstantiationException e) {
         throw new UnsupportedOperationException(e);
      }

      UNSAFE.putLong(buffer, ADDRESS, address);
      UNSAFE.putInt(buffer, MARK, -1);
      UNSAFE.putInt(buffer, LIMIT, capacity);
      UNSAFE.putInt(buffer, CAPACITY, capacity);
      return buffer;
   }

   static ByteBuffer slice(ByteBuffer source, long address, int capacity) {
      ByteBuffer target;
      try {
         target = (ByteBuffer)UNSAFE.allocateInstance(BUFFER_BYTE);
      } catch (InstantiationException e) {
         throw new UnsupportedOperationException(e);
      }

      UNSAFE.putLong(target, ADDRESS, address);
      UNSAFE.putInt(target, MARK, -1);
      UNSAFE.putInt(target, LIMIT, capacity);
      UNSAFE.putInt(target, CAPACITY, capacity);
      Object attachment = UNSAFE.getObject(source, PARENT_BYTE);
      UNSAFE.putObject(target, PARENT_BYTE, attachment == null ? source : attachment);
      return target.order(source.order());
   }

   static <T extends Buffer> T slice(Class<? extends T> clazz, T source, long address, int capacity, long attachmentOffset) {
      T target;
      try {
         target = (T)UNSAFE.allocateInstance(clazz);
      } catch (InstantiationException e) {
         throw new UnsupportedOperationException(e);
      }

      UNSAFE.putLong(target, ADDRESS, address);
      UNSAFE.putInt(target, MARK, -1);
      UNSAFE.putInt(target, LIMIT, capacity);
      UNSAFE.putInt(target, CAPACITY, capacity);
      UNSAFE.putObject(target, attachmentOffset, UNSAFE.getObject(source, attachmentOffset));
      return target;
   }

   static <T extends Buffer> T duplicate(Class<? extends T> clazz, T source, long attachmentOffset) {
      T target;
      try {
         target = (T)UNSAFE.allocateInstance(clazz);
      } catch (InstantiationException e) {
         throw new UnsupportedOperationException(e);
      }

      UNSAFE.putLong(target, ADDRESS, UNSAFE.getLong(source, ADDRESS));
      UNSAFE.putInt(target, MARK, UNSAFE.getInt(source, MARK));
      UNSAFE.putInt(target, POSITION, UNSAFE.getInt(source, POSITION));
      UNSAFE.putInt(target, LIMIT, UNSAFE.getInt(source, LIMIT));
      UNSAFE.putInt(target, CAPACITY, UNSAFE.getInt(source, CAPACITY));
      UNSAFE.putObject(target, attachmentOffset, UNSAFE.getObject(source, attachmentOffset));
      return target;
   }

   static {
      Library.initialize();
      ByteBuffer bb = ByteBuffer.allocateDirect(0).order(NATIVE_ORDER);
      BUFFER_BYTE = (Class<? extends ByteBuffer>)bb.getClass();
      BUFFER_SHORT = (Class<? extends ShortBuffer>)bb.asShortBuffer().getClass();
      BUFFER_CHAR = (Class<? extends CharBuffer>)bb.asCharBuffer().getClass();
      BUFFER_INT = (Class<? extends IntBuffer>)bb.asIntBuffer().getClass();
      BUFFER_LONG = (Class<? extends LongBuffer>)bb.asLongBuffer().getClass();
      BUFFER_FLOAT = (Class<? extends FloatBuffer>)bb.asFloatBuffer().getClass();
      BUFFER_DOUBLE = (Class<? extends DoubleBuffer>)bb.asDoubleBuffer().getClass();

      try {
         MARK = getMarkOffset();
         POSITION = getPositionOffset();
         LIMIT = getLimitOffset();
         CAPACITY = getCapacityOffset();
         ADDRESS = getAddressOffset();
         PARENT_BYTE = getFieldOffsetObject(bb.duplicate().order(bb.order()), bb);
         PARENT_SHORT = getFieldOffsetObject(bb.asShortBuffer(), bb);
         PARENT_CHAR = getFieldOffsetObject(bb.asCharBuffer(), bb);
         PARENT_INT = getFieldOffsetObject(bb.asIntBuffer(), bb);
         PARENT_LONG = getFieldOffsetObject(bb.asLongBuffer(), bb);
         PARENT_FLOAT = getFieldOffsetObject(bb.asFloatBuffer(), bb);
         PARENT_DOUBLE = getFieldOffsetObject(bb.asDoubleBuffer(), bb);
      } catch (Throwable t) {
         throw new UnsupportedOperationException(t);
      }

      PAGE_SIZE = UNSAFE.pageSize();
      CACHE_LINE_SIZE = 64;
      SHIFT = NATIVE_ORDER == ByteOrder.BIG_ENDIAN ? new MemoryUtil.NativeShift() {
         @Override
         public long left(long value, int bytes) {
            return value << (bytes << 3);
         }

         @Override
         public long right(long value, int bytes) {
            return value >>> (bytes << 3);
         }
      } : new MemoryUtil.NativeShift() {
         @Override
         public long left(long value, int bytes) {
            return value >>> (bytes << 3);
         }

         @Override
         public long right(long value, int bytes) {
            return value << (bytes << 3);
         }
      };
      FILL_PATTERN_32 = Integer.divideUnsigned(-1, 255);
      FILL_PATTERN_64 = Long.divideUnsigned(-1L, 255L);
   }

   static final class LazyInit {
      static final MemoryUtil.MemoryAllocator ALLOCATOR_IMPL = MemoryManage.getInstance();
      static final MemoryUtil.MemoryAllocator ALLOCATOR = Configuration.DEBUG_MEMORY_ALLOCATOR.get(false)
         ? new MemoryManage.DebugAllocator(ALLOCATOR_IMPL)
         : ALLOCATOR_IMPL;

      private LazyInit() {
      }

      static {
         APIUtil.apiLog("MemoryUtil allocator: " + ALLOCATOR.getClass().getSimpleName());
      }
   }

   public interface MemoryAllocationReport {
      void invoke(long var1, long var3, long var5, @Nullable String var7, @Nullable StackTraceElement... var8);

      enum Aggregate {
         ALL,
         GROUP_BY_METHOD,
         GROUP_BY_STACKTRACE;
      }
   }

   public interface MemoryAllocator {
      long getMalloc();

      long getCalloc();

      long getRealloc();

      long getFree();

      long getAlignedAlloc();

      long getAlignedFree();

      long malloc(long var1);

      long calloc(long var1, long var3);

      long realloc(long var1, long var3);

      void free(long var1);

      long aligned_alloc(long var1, long var3);

      void aligned_free(long var1);
   }

   private interface NativeShift {
      long left(long var1, int var3);

      long right(long var1, int var3);
   }
}
