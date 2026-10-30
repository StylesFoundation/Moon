package org.lwjgl.system.linux;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

@NativeType("struct flock64")
public class Flock extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int L_TYPE;
   public static final int L_WHENCE;
   public static final int L_START;
   public static final int L_LEN;
   public static final int L_PID;

   public Flock(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   public short l_type() {
      return nl_type(this.address());
   }

   public short l_whence() {
      return nl_whence(this.address());
   }

   @NativeType("off_t")
   public long l_start() {
      return nl_start(this.address());
   }

   @NativeType("off_t")
   public long l_len() {
      return nl_len(this.address());
   }

   @NativeType("pid_t")
   public int l_pid() {
      return nl_pid(this.address());
   }

   public Flock l_type(short value) {
      nl_type(this.address(), value);
      return this;
   }

   public Flock l_whence(short value) {
      nl_whence(this.address(), value);
      return this;
   }

   public Flock l_start(@NativeType("off_t") long value) {
      nl_start(this.address(), value);
      return this;
   }

   public Flock l_len(@NativeType("off_t") long value) {
      nl_len(this.address(), value);
      return this;
   }

   public Flock l_pid(@NativeType("pid_t") int value) {
      nl_pid(this.address(), value);
      return this;
   }

   public Flock set(short l_type, short l_whence, long l_start, long l_len, int l_pid) {
      this.l_type(l_type);
      this.l_whence(l_whence);
      this.l_start(l_start);
      this.l_len(l_len);
      this.l_pid(l_pid);
      return this;
   }

   public Flock set(Flock src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static Flock malloc() {
      return wrap(Flock.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static Flock calloc() {
      return wrap(Flock.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static Flock create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(Flock.class, MemoryUtil.memAddress(container), container);
   }

   public static Flock create(long address) {
      return wrap(Flock.class, address);
   }

   @Nullable
   public static Flock createSafe(long address) {
      return address == 0L ? null : wrap(Flock.class, address);
   }

   public static Buffer malloc(int capacity) {
      return wrap(Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static Buffer calloc(int capacity) {
      return wrap(Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static Buffer create(long address, int capacity) {
      return wrap(Buffer.class, address, capacity);
   }

   @Nullable
   public static Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(Buffer.class, address, capacity);
   }

   public static Flock malloc(MemoryStack stack) {
      return wrap(Flock.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static Flock calloc(MemoryStack stack) {
      return wrap(Flock.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static short nl_type(long struct) {
      return UNSAFE.getShort(null, struct + L_TYPE);
   }

   public static short nl_whence(long struct) {
      return UNSAFE.getShort(null, struct + L_WHENCE);
   }

   public static long nl_start(long struct) {
      return UNSAFE.getLong(null, struct + L_START);
   }

   public static long nl_len(long struct) {
      return UNSAFE.getLong(null, struct + L_LEN);
   }

   public static int nl_pid(long struct) {
      return UNSAFE.getInt(null, struct + L_PID);
   }

   public static void nl_type(long struct, short value) {
      UNSAFE.putShort(null, struct + L_TYPE, value);
   }

   public static void nl_whence(long struct, short value) {
      UNSAFE.putShort(null, struct + L_WHENCE, value);
   }

   public static void nl_start(long struct, long value) {
      UNSAFE.putLong(null, struct + L_START, value);
   }

   public static void nl_len(long struct, long value) {
      UNSAFE.putLong(null, struct + L_LEN, value);
   }

   public static void nl_pid(long struct, int value) {
      UNSAFE.putInt(null, struct + L_PID, value);
   }

   static {
      Layout layout = __struct(__member(2), __member(2), __member(8), __member(8), __member(4));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      L_TYPE = layout.offsetof(0);
      L_WHENCE = layout.offsetof(1);
      L_START = layout.offsetof(2);
      L_LEN = layout.offsetof(3);
      L_PID = layout.offsetof(4);
   }

   public static class Buffer extends StructBuffer<Flock, Buffer> implements NativeResource {
      private static final Flock ELEMENT_FACTORY = Flock.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / Flock.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected Buffer self() {
         return this;
      }

      protected Flock getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public short l_type() {
         return Flock.nl_type(this.address());
      }

      public short l_whence() {
         return Flock.nl_whence(this.address());
      }

      @NativeType("off_t")
      public long l_start() {
         return Flock.nl_start(this.address());
      }

      @NativeType("off_t")
      public long l_len() {
         return Flock.nl_len(this.address());
      }

      @NativeType("pid_t")
      public int l_pid() {
         return Flock.nl_pid(this.address());
      }

      public Buffer l_type(short value) {
         Flock.nl_type(this.address(), value);
         return this;
      }

      public Buffer l_whence(short value) {
         Flock.nl_whence(this.address(), value);
         return this;
      }

      public Buffer l_start(@NativeType("off_t") long value) {
         Flock.nl_start(this.address(), value);
         return this;
      }

      public Buffer l_len(@NativeType("off_t") long value) {
         Flock.nl_len(this.address(), value);
         return this;
      }

      public Buffer l_pid(@NativeType("pid_t") int value) {
         Flock.nl_pid(this.address(), value);
         return this;
      }
   }
}
