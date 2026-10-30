package org.lwjgl;

import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.CheckIntrinsics;
import org.lwjgl.system.Checks;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.Pointer;

public class PointerBuffer extends CustomBuffer<PointerBuffer> implements Comparable<PointerBuffer> {
   protected PointerBuffer(long address, @Nullable ByteBuffer container, int mark, int position, int limit, int capacity) {
      super(address, container, mark, position, limit, capacity);
   }

   public static PointerBuffer allocateDirect(int capacity) {
      ByteBuffer source = BufferUtils.createByteBuffer(BufferUtils.getAllocationSize(capacity, POINTER_SHIFT));
      return wrap(PointerBuffer.class, MemoryUtil.memAddress(source), capacity, source);
   }

   public static PointerBuffer create(long address, int capacity) {
      return wrap(PointerBuffer.class, address, capacity);
   }

   public static PointerBuffer create(ByteBuffer source) {
      int capacity = source.remaining() >> POINTER_SHIFT;
      return wrap(PointerBuffer.class, MemoryUtil.memAddress(source), capacity, source);
   }

   protected PointerBuffer self() {
      return this;
   }

   @Override
   public int sizeof() {
      return POINTER_SIZE;
   }

   public long get() {
      return MemoryUtil.memGetAddress(this.address + Integer.toUnsignedLong(this.nextGetIndex()) * POINTER_SIZE);
   }

   public static long get(ByteBuffer source) {
      if (source.remaining() < POINTER_SIZE) {
         throw new BufferUnderflowException();
      }

      try {
         return MemoryUtil.memGetAddress(MemoryUtil.memAddress(source));
      } finally {
         ((Buffer)source).position(source.position() + POINTER_SIZE);
      }
   }

   public PointerBuffer put(long p) {
      MemoryUtil.memPutAddress(this.address + Integer.toUnsignedLong(this.nextPutIndex()) * POINTER_SIZE, p);
      return this;
   }

   public static void put(ByteBuffer target, long p) {
      if (target.remaining() < POINTER_SIZE) {
         throw new BufferOverflowException();
      }

      try {
         MemoryUtil.memPutAddress(MemoryUtil.memAddress(target), p);
      } finally {
         ((Buffer)target).position(target.position() + POINTER_SIZE);
      }
   }

   public long get(int index) {
      return MemoryUtil.memGetAddress(this.address + Checks.check(index, this.limit) * POINTER_SIZE);
   }

   public static long get(ByteBuffer source, int index) {
      CheckIntrinsics.checkFromIndexSize(index, POINTER_SIZE, source.limit());
      return MemoryUtil.memGetAddress(MemoryUtil.memAddress0(source) + index);
   }

   public PointerBuffer put(int index, long p) {
      MemoryUtil.memPutAddress(this.address + Checks.check(index, this.limit) * POINTER_SIZE, p);
      return this;
   }

   public static void put(ByteBuffer target, int index, long p) {
      CheckIntrinsics.checkFromIndexSize(index, POINTER_SIZE, target.limit());
      MemoryUtil.memPutAddress(MemoryUtil.memAddress0(target) + index, p);
   }

   public PointerBuffer put(Pointer pointer) {
      this.put(pointer.address());
      return this;
   }

   public PointerBuffer put(int index, Pointer pointer) {
      this.put(index, pointer.address());
      return this;
   }

   public PointerBuffer put(ByteBuffer buffer) {
      this.put(MemoryUtil.memAddress(buffer));
      return this;
   }

   public PointerBuffer put(ShortBuffer buffer) {
      this.put(MemoryUtil.memAddress(buffer));
      return this;
   }

   public PointerBuffer put(IntBuffer buffer) {
      this.put(MemoryUtil.memAddress(buffer));
      return this;
   }

   public PointerBuffer put(LongBuffer buffer) {
      this.put(MemoryUtil.memAddress(buffer));
      return this;
   }

   public PointerBuffer put(FloatBuffer buffer) {
      this.put(MemoryUtil.memAddress(buffer));
      return this;
   }

   public PointerBuffer put(DoubleBuffer buffer) {
      this.put(MemoryUtil.memAddress(buffer));
      return this;
   }

   public PointerBuffer putAddressOf(CustomBuffer<?> buffer) {
      this.put(MemoryUtil.memAddress(buffer));
      return this;
   }

   public PointerBuffer put(int index, ByteBuffer buffer) {
      this.put(index, MemoryUtil.memAddress(buffer));
      return this;
   }

   public PointerBuffer put(int index, ShortBuffer buffer) {
      this.put(index, MemoryUtil.memAddress(buffer));
      return this;
   }

   public PointerBuffer put(int index, IntBuffer buffer) {
      this.put(index, MemoryUtil.memAddress(buffer));
      return this;
   }

   public PointerBuffer put(int index, LongBuffer buffer) {
      this.put(index, MemoryUtil.memAddress(buffer));
      return this;
   }

   public PointerBuffer put(int index, FloatBuffer buffer) {
      this.put(index, MemoryUtil.memAddress(buffer));
      return this;
   }

   public PointerBuffer put(int index, DoubleBuffer buffer) {
      this.put(index, MemoryUtil.memAddress(buffer));
      return this;
   }

   public PointerBuffer putAddressOf(int index, CustomBuffer<?> buffer) {
      this.put(index, MemoryUtil.memAddress(buffer));
      return this;
   }

   public ByteBuffer getByteBuffer(int size) {
      return MemoryUtil.memByteBuffer(this.get(), size);
   }

   public ShortBuffer getShortBuffer(int size) {
      return MemoryUtil.memShortBuffer(this.get(), size);
   }

   public IntBuffer getIntBuffer(int size) {
      return MemoryUtil.memIntBuffer(this.get(), size);
   }

   public LongBuffer getLongBuffer(int size) {
      return MemoryUtil.memLongBuffer(this.get(), size);
   }

   public FloatBuffer getFloatBuffer(int size) {
      return MemoryUtil.memFloatBuffer(this.get(), size);
   }

   public DoubleBuffer getDoubleBuffer(int size) {
      return MemoryUtil.memDoubleBuffer(this.get(), size);
   }

   public PointerBuffer getPointerBuffer(int size) {
      return MemoryUtil.memPointerBuffer(this.get(), size);
   }

   public String getStringASCII() {
      return MemoryUtil.memASCII(this.get());
   }

   public String getStringUTF8() {
      return MemoryUtil.memUTF8(this.get());
   }

   public String getStringUTF16() {
      return MemoryUtil.memUTF16(this.get());
   }

   public ByteBuffer getByteBuffer(int index, int size) {
      return MemoryUtil.memByteBuffer(this.get(index), size);
   }

   public ShortBuffer getShortBuffer(int index, int size) {
      return MemoryUtil.memShortBuffer(this.get(index), size);
   }

   public IntBuffer getIntBuffer(int index, int size) {
      return MemoryUtil.memIntBuffer(this.get(index), size);
   }

   public LongBuffer getLongBuffer(int index, int size) {
      return MemoryUtil.memLongBuffer(this.get(index), size);
   }

   public FloatBuffer getFloatBuffer(int index, int size) {
      return MemoryUtil.memFloatBuffer(this.get(index), size);
   }

   public DoubleBuffer getDoubleBuffer(int index, int size) {
      return MemoryUtil.memDoubleBuffer(this.get(index), size);
   }

   public PointerBuffer getPointerBuffer(int index, int size) {
      return MemoryUtil.memPointerBuffer(this.get(index), size);
   }

   public String getStringASCII(int index) {
      return MemoryUtil.memASCII(this.get(index));
   }

   public String getStringUTF8(int index) {
      return MemoryUtil.memUTF8(this.get(index));
   }

   public String getStringUTF16(int index) {
      return MemoryUtil.memUTF16(this.get(index));
   }

   public PointerBuffer get(long[] dst) {
      return this.get(dst, 0, dst.length);
   }

   public PointerBuffer get(long[] dst, int offset, int length) {
      if (BITS64) {
         MemoryUtil.memLongBuffer(this.address(), this.remaining()).get(dst, offset, length);
         this.position(this.position() + length);
      } else {
         this.get32(dst, offset, length);
      }

      return this;
   }

   private void get32(long[] dst, int offset, int length) {
      CheckIntrinsics.checkFromIndexSize(offset, length, dst.length);
      if (this.remaining() < length) {
         throw new BufferUnderflowException();
      }

      int i = offset;

      for (int end = offset + length; i < end; i++) {
         dst[i] = this.get();
      }
   }

   public PointerBuffer put(long[] src) {
      return this.put(src, 0, src.length);
   }

   public PointerBuffer put(long[] src, int offset, int length) {
      if (BITS64) {
         MemoryUtil.memLongBuffer(this.address(), this.remaining()).put(src, offset, length);
         this.position(this.position() + length);
      } else {
         this.put32(src, offset, length);
      }

      return this;
   }

   private void put32(long[] src, int offset, int length) {
      CheckIntrinsics.checkFromIndexSize(offset, length, src.length);
      if (this.remaining() < length) {
         throw new BufferOverflowException();
      }

      int end = offset + length;

      for (int i = offset; i < end; i++) {
         this.put(src[i]);
      }
   }

   @Override
   public int hashCode() {
      int h = 1;
      int p = this.position();

      for (int i = this.limit() - 1; i >= p; i--) {
         h = 31 * h + (int)this.get(i);
      }

      return h;
   }

   @Override
   public boolean equals(Object ob) {
      if (!(ob instanceof PointerBuffer)) {
         return false;
      }

      PointerBuffer that = (PointerBuffer)ob;
      if (this.remaining() != that.remaining()) {
         return false;
      }

      int p = this.position();
      int i = this.limit() - 1;

      for (int j = that.limit() - 1; i >= p; j--) {
         long v1 = this.get(i);
         long v2 = that.get(j);
         if (v1 != v2) {
            return false;
         }

         i--;
      }

      return true;
   }

   public int compareTo(PointerBuffer that) {
      int n = this.position() + Math.min(this.remaining(), that.remaining());
      int i = this.position();

      for (int j = that.position(); i < n; j++) {
         long v1 = this.get(i);
         long v2 = that.get(j);
         if (v1 != v2) {
            if (v1 < v2) {
               return -1;
            }

            return 1;
         }

         i++;
      }

      return this.remaining() - that.remaining();
   }
}
