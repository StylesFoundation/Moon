package org.lwjgl.system.linux;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

@NativeType("struct statx")
public class Statx extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int STX_MASK;
   public static final int STX_BLKSIZE;
   public static final int STX_ATTRIBUTES;
   public static final int STX_NLINK;
   public static final int STX_UID;
   public static final int STX_GID;
   public static final int STX_MODE;
   public static final int __SPARE0;
   public static final int STX_INO;
   public static final int STX_SIZE;
   public static final int STX_BLOCKS;
   public static final int STX_ATTRIBUTES_MASK;
   public static final int STX_ATIME;
   public static final int STX_BTIME;
   public static final int STX_CTIME;
   public static final int STX_MTIME;
   public static final int STX_RDEV_MAJOR;
   public static final int STX_RDEV_MINOR;
   public static final int STX_DEV_MAJOR;
   public static final int STX_DEV_MINOR;
   public static final int STX_MNT_ID;
   public static final int __SPARE2;
   public static final int __SPARE3;

   public Statx(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("__u32")
   public int stx_mask() {
      return nstx_mask(this.address());
   }

   @NativeType("__u32")
   public int stx_blksize() {
      return nstx_blksize(this.address());
   }

   @NativeType("__u64")
   public long stx_attributes() {
      return nstx_attributes(this.address());
   }

   @NativeType("__u32")
   public int stx_nlink() {
      return nstx_nlink(this.address());
   }

   @NativeType("__u32")
   public int stx_uid() {
      return nstx_uid(this.address());
   }

   @NativeType("__u32")
   public int stx_gid() {
      return nstx_gid(this.address());
   }

   @NativeType("__u16")
   public short stx_mode() {
      return nstx_mode(this.address());
   }

   @NativeType("__u64")
   public long stx_ino() {
      return nstx_ino(this.address());
   }

   @NativeType("__u64")
   public long stx_size() {
      return nstx_size(this.address());
   }

   @NativeType("__u64")
   public long stx_blocks() {
      return nstx_blocks(this.address());
   }

   @NativeType("__u64")
   public long stx_attributes_mask() {
      return nstx_attributes_mask(this.address());
   }

   @NativeType("struct statx_timestamp")
   public StatxTimestamp stx_atime() {
      return nstx_atime(this.address());
   }

   @NativeType("struct statx_timestamp")
   public StatxTimestamp stx_btime() {
      return nstx_btime(this.address());
   }

   @NativeType("struct statx_timestamp")
   public StatxTimestamp stx_ctime() {
      return nstx_ctime(this.address());
   }

   @NativeType("struct statx_timestamp")
   public StatxTimestamp stx_mtime() {
      return nstx_mtime(this.address());
   }

   @NativeType("__u32")
   public int stx_rdev_major() {
      return nstx_rdev_major(this.address());
   }

   @NativeType("__u32")
   public int stx_rdev_minor() {
      return nstx_rdev_minor(this.address());
   }

   @NativeType("__u32")
   public int stx_dev_major() {
      return nstx_dev_major(this.address());
   }

   @NativeType("__u32")
   public int stx_dev_minor() {
      return nstx_dev_minor(this.address());
   }

   @NativeType("__u64")
   public long stx_mnt_id() {
      return nstx_mnt_id(this.address());
   }

   public Statx stx_mask(@NativeType("__u32") int value) {
      nstx_mask(this.address(), value);
      return this;
   }

   public Statx stx_blksize(@NativeType("__u32") int value) {
      nstx_blksize(this.address(), value);
      return this;
   }

   public Statx stx_attributes(@NativeType("__u64") long value) {
      nstx_attributes(this.address(), value);
      return this;
   }

   public Statx stx_nlink(@NativeType("__u32") int value) {
      nstx_nlink(this.address(), value);
      return this;
   }

   public Statx stx_uid(@NativeType("__u32") int value) {
      nstx_uid(this.address(), value);
      return this;
   }

   public Statx stx_gid(@NativeType("__u32") int value) {
      nstx_gid(this.address(), value);
      return this;
   }

   public Statx stx_mode(@NativeType("__u16") short value) {
      nstx_mode(this.address(), value);
      return this;
   }

   public Statx stx_ino(@NativeType("__u64") long value) {
      nstx_ino(this.address(), value);
      return this;
   }

   public Statx stx_size(@NativeType("__u64") long value) {
      nstx_size(this.address(), value);
      return this;
   }

   public Statx stx_blocks(@NativeType("__u64") long value) {
      nstx_blocks(this.address(), value);
      return this;
   }

   public Statx stx_attributes_mask(@NativeType("__u64") long value) {
      nstx_attributes_mask(this.address(), value);
      return this;
   }

   public Statx stx_atime(@NativeType("struct statx_timestamp") StatxTimestamp value) {
      nstx_atime(this.address(), value);
      return this;
   }

   public Statx stx_atime(Consumer<StatxTimestamp> consumer) {
      consumer.accept(this.stx_atime());
      return this;
   }

   public Statx stx_btime(@NativeType("struct statx_timestamp") StatxTimestamp value) {
      nstx_btime(this.address(), value);
      return this;
   }

   public Statx stx_btime(Consumer<StatxTimestamp> consumer) {
      consumer.accept(this.stx_btime());
      return this;
   }

   public Statx stx_ctime(@NativeType("struct statx_timestamp") StatxTimestamp value) {
      nstx_ctime(this.address(), value);
      return this;
   }

   public Statx stx_ctime(Consumer<StatxTimestamp> consumer) {
      consumer.accept(this.stx_ctime());
      return this;
   }

   public Statx stx_mtime(@NativeType("struct statx_timestamp") StatxTimestamp value) {
      nstx_mtime(this.address(), value);
      return this;
   }

   public Statx stx_mtime(Consumer<StatxTimestamp> consumer) {
      consumer.accept(this.stx_mtime());
      return this;
   }

   public Statx stx_rdev_major(@NativeType("__u32") int value) {
      nstx_rdev_major(this.address(), value);
      return this;
   }

   public Statx stx_rdev_minor(@NativeType("__u32") int value) {
      nstx_rdev_minor(this.address(), value);
      return this;
   }

   public Statx stx_dev_major(@NativeType("__u32") int value) {
      nstx_dev_major(this.address(), value);
      return this;
   }

   public Statx stx_dev_minor(@NativeType("__u32") int value) {
      nstx_dev_minor(this.address(), value);
      return this;
   }

   public Statx stx_mnt_id(@NativeType("__u64") long value) {
      nstx_mnt_id(this.address(), value);
      return this;
   }

   public Statx set(
      int stx_mask,
      int stx_blksize,
      long stx_attributes,
      int stx_nlink,
      int stx_uid,
      int stx_gid,
      short stx_mode,
      long stx_ino,
      long stx_size,
      long stx_blocks,
      long stx_attributes_mask,
      StatxTimestamp stx_atime,
      StatxTimestamp stx_btime,
      StatxTimestamp stx_ctime,
      StatxTimestamp stx_mtime,
      int stx_rdev_major,
      int stx_rdev_minor,
      int stx_dev_major,
      int stx_dev_minor,
      long stx_mnt_id
   ) {
      this.stx_mask(stx_mask);
      this.stx_blksize(stx_blksize);
      this.stx_attributes(stx_attributes);
      this.stx_nlink(stx_nlink);
      this.stx_uid(stx_uid);
      this.stx_gid(stx_gid);
      this.stx_mode(stx_mode);
      this.stx_ino(stx_ino);
      this.stx_size(stx_size);
      this.stx_blocks(stx_blocks);
      this.stx_attributes_mask(stx_attributes_mask);
      this.stx_atime(stx_atime);
      this.stx_btime(stx_btime);
      this.stx_ctime(stx_ctime);
      this.stx_mtime(stx_mtime);
      this.stx_rdev_major(stx_rdev_major);
      this.stx_rdev_minor(stx_rdev_minor);
      this.stx_dev_major(stx_dev_major);
      this.stx_dev_minor(stx_dev_minor);
      this.stx_mnt_id(stx_mnt_id);
      return this;
   }

   public Statx set(Statx src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static Statx malloc() {
      return wrap(Statx.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static Statx calloc() {
      return wrap(Statx.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static Statx create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(Statx.class, MemoryUtil.memAddress(container), container);
   }

   public static Statx create(long address) {
      return wrap(Statx.class, address);
   }

   @Nullable
   public static Statx createSafe(long address) {
      return address == 0L ? null : wrap(Statx.class, address);
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

   public static Statx malloc(MemoryStack stack) {
      return wrap(Statx.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static Statx calloc(MemoryStack stack) {
      return wrap(Statx.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static int nstx_mask(long struct) {
      return UNSAFE.getInt(null, struct + STX_MASK);
   }

   public static int nstx_blksize(long struct) {
      return UNSAFE.getInt(null, struct + STX_BLKSIZE);
   }

   public static long nstx_attributes(long struct) {
      return UNSAFE.getLong(null, struct + STX_ATTRIBUTES);
   }

   public static int nstx_nlink(long struct) {
      return UNSAFE.getInt(null, struct + STX_NLINK);
   }

   public static int nstx_uid(long struct) {
      return UNSAFE.getInt(null, struct + STX_UID);
   }

   public static int nstx_gid(long struct) {
      return UNSAFE.getInt(null, struct + STX_GID);
   }

   public static short nstx_mode(long struct) {
      return UNSAFE.getShort(null, struct + STX_MODE);
   }

   public static ShortBuffer n__spare0(long struct) {
      return MemoryUtil.memShortBuffer(struct + __SPARE0, 1);
   }

   public static short n__spare0(long struct, int index) {
      return UNSAFE.getShort(null, struct + __SPARE0 + Checks.check(index, 1) * 2L);
   }

   public static long nstx_ino(long struct) {
      return UNSAFE.getLong(null, struct + STX_INO);
   }

   public static long nstx_size(long struct) {
      return UNSAFE.getLong(null, struct + STX_SIZE);
   }

   public static long nstx_blocks(long struct) {
      return UNSAFE.getLong(null, struct + STX_BLOCKS);
   }

   public static long nstx_attributes_mask(long struct) {
      return UNSAFE.getLong(null, struct + STX_ATTRIBUTES_MASK);
   }

   public static StatxTimestamp nstx_atime(long struct) {
      return StatxTimestamp.create(struct + STX_ATIME);
   }

   public static StatxTimestamp nstx_btime(long struct) {
      return StatxTimestamp.create(struct + STX_BTIME);
   }

   public static StatxTimestamp nstx_ctime(long struct) {
      return StatxTimestamp.create(struct + STX_CTIME);
   }

   public static StatxTimestamp nstx_mtime(long struct) {
      return StatxTimestamp.create(struct + STX_MTIME);
   }

   public static int nstx_rdev_major(long struct) {
      return UNSAFE.getInt(null, struct + STX_RDEV_MAJOR);
   }

   public static int nstx_rdev_minor(long struct) {
      return UNSAFE.getInt(null, struct + STX_RDEV_MINOR);
   }

   public static int nstx_dev_major(long struct) {
      return UNSAFE.getInt(null, struct + STX_DEV_MAJOR);
   }

   public static int nstx_dev_minor(long struct) {
      return UNSAFE.getInt(null, struct + STX_DEV_MINOR);
   }

   public static long nstx_mnt_id(long struct) {
      return UNSAFE.getLong(null, struct + STX_MNT_ID);
   }

   public static long n__spare2(long struct) {
      return UNSAFE.getLong(null, struct + __SPARE2);
   }

   public static LongBuffer n__spare3(long struct) {
      return MemoryUtil.memLongBuffer(struct + __SPARE3, 12);
   }

   public static long n__spare3(long struct, int index) {
      return UNSAFE.getLong(null, struct + __SPARE3 + Checks.check(index, 12) * 8L);
   }

   public static void nstx_mask(long struct, int value) {
      UNSAFE.putInt(null, struct + STX_MASK, value);
   }

   public static void nstx_blksize(long struct, int value) {
      UNSAFE.putInt(null, struct + STX_BLKSIZE, value);
   }

   public static void nstx_attributes(long struct, long value) {
      UNSAFE.putLong(null, struct + STX_ATTRIBUTES, value);
   }

   public static void nstx_nlink(long struct, int value) {
      UNSAFE.putInt(null, struct + STX_NLINK, value);
   }

   public static void nstx_uid(long struct, int value) {
      UNSAFE.putInt(null, struct + STX_UID, value);
   }

   public static void nstx_gid(long struct, int value) {
      UNSAFE.putInt(null, struct + STX_GID, value);
   }

   public static void nstx_mode(long struct, short value) {
      UNSAFE.putShort(null, struct + STX_MODE, value);
   }

   public static void n__spare0(long struct, ShortBuffer value) {
      if (Checks.CHECKS) {
         Checks.checkGT(value, 1);
      }

      MemoryUtil.memCopy(MemoryUtil.memAddress(value), struct + __SPARE0, value.remaining() * 2);
   }

   public static void n__spare0(long struct, int index, short value) {
      UNSAFE.putShort(null, struct + __SPARE0 + Checks.check(index, 1) * 2L, value);
   }

   public static void nstx_ino(long struct, long value) {
      UNSAFE.putLong(null, struct + STX_INO, value);
   }

   public static void nstx_size(long struct, long value) {
      UNSAFE.putLong(null, struct + STX_SIZE, value);
   }

   public static void nstx_blocks(long struct, long value) {
      UNSAFE.putLong(null, struct + STX_BLOCKS, value);
   }

   public static void nstx_attributes_mask(long struct, long value) {
      UNSAFE.putLong(null, struct + STX_ATTRIBUTES_MASK, value);
   }

   public static void nstx_atime(long struct, StatxTimestamp value) {
      MemoryUtil.memCopy(value.address(), struct + STX_ATIME, StatxTimestamp.SIZEOF);
   }

   public static void nstx_btime(long struct, StatxTimestamp value) {
      MemoryUtil.memCopy(value.address(), struct + STX_BTIME, StatxTimestamp.SIZEOF);
   }

   public static void nstx_ctime(long struct, StatxTimestamp value) {
      MemoryUtil.memCopy(value.address(), struct + STX_CTIME, StatxTimestamp.SIZEOF);
   }

   public static void nstx_mtime(long struct, StatxTimestamp value) {
      MemoryUtil.memCopy(value.address(), struct + STX_MTIME, StatxTimestamp.SIZEOF);
   }

   public static void nstx_rdev_major(long struct, int value) {
      UNSAFE.putInt(null, struct + STX_RDEV_MAJOR, value);
   }

   public static void nstx_rdev_minor(long struct, int value) {
      UNSAFE.putInt(null, struct + STX_RDEV_MINOR, value);
   }

   public static void nstx_dev_major(long struct, int value) {
      UNSAFE.putInt(null, struct + STX_DEV_MAJOR, value);
   }

   public static void nstx_dev_minor(long struct, int value) {
      UNSAFE.putInt(null, struct + STX_DEV_MINOR, value);
   }

   public static void nstx_mnt_id(long struct, long value) {
      UNSAFE.putLong(null, struct + STX_MNT_ID, value);
   }

   public static void n__spare2(long struct, long value) {
      UNSAFE.putLong(null, struct + __SPARE2, value);
   }

   public static void n__spare3(long struct, LongBuffer value) {
      if (Checks.CHECKS) {
         Checks.checkGT(value, 12);
      }

      MemoryUtil.memCopy(MemoryUtil.memAddress(value), struct + __SPARE3, value.remaining() * 8);
   }

   public static void n__spare3(long struct, int index, long value) {
      UNSAFE.putLong(null, struct + __SPARE3 + Checks.check(index, 12) * 8L, value);
   }

   static {
      Layout layout = __struct(
         __member(4),
         __member(4),
         __member(8),
         __member(4),
         __member(4),
         __member(4),
         __member(2),
         __array(2, 1),
         __member(8),
         __member(8),
         __member(8),
         __member(8),
         __member(StatxTimestamp.SIZEOF, StatxTimestamp.ALIGNOF),
         __member(StatxTimestamp.SIZEOF, StatxTimestamp.ALIGNOF),
         __member(StatxTimestamp.SIZEOF, StatxTimestamp.ALIGNOF),
         __member(StatxTimestamp.SIZEOF, StatxTimestamp.ALIGNOF),
         __member(4),
         __member(4),
         __member(4),
         __member(4),
         __member(8),
         __member(8),
         __array(8, 12)
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      STX_MASK = layout.offsetof(0);
      STX_BLKSIZE = layout.offsetof(1);
      STX_ATTRIBUTES = layout.offsetof(2);
      STX_NLINK = layout.offsetof(3);
      STX_UID = layout.offsetof(4);
      STX_GID = layout.offsetof(5);
      STX_MODE = layout.offsetof(6);
      __SPARE0 = layout.offsetof(7);
      STX_INO = layout.offsetof(8);
      STX_SIZE = layout.offsetof(9);
      STX_BLOCKS = layout.offsetof(10);
      STX_ATTRIBUTES_MASK = layout.offsetof(11);
      STX_ATIME = layout.offsetof(12);
      STX_BTIME = layout.offsetof(13);
      STX_CTIME = layout.offsetof(14);
      STX_MTIME = layout.offsetof(15);
      STX_RDEV_MAJOR = layout.offsetof(16);
      STX_RDEV_MINOR = layout.offsetof(17);
      STX_DEV_MAJOR = layout.offsetof(18);
      STX_DEV_MINOR = layout.offsetof(19);
      STX_MNT_ID = layout.offsetof(20);
      __SPARE2 = layout.offsetof(21);
      __SPARE3 = layout.offsetof(22);
   }

   public static class Buffer extends StructBuffer<Statx, Buffer> implements NativeResource {
      private static final Statx ELEMENT_FACTORY = Statx.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / Statx.SIZEOF);
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

      protected Statx getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("__u32")
      public int stx_mask() {
         return Statx.nstx_mask(this.address());
      }

      @NativeType("__u32")
      public int stx_blksize() {
         return Statx.nstx_blksize(this.address());
      }

      @NativeType("__u64")
      public long stx_attributes() {
         return Statx.nstx_attributes(this.address());
      }

      @NativeType("__u32")
      public int stx_nlink() {
         return Statx.nstx_nlink(this.address());
      }

      @NativeType("__u32")
      public int stx_uid() {
         return Statx.nstx_uid(this.address());
      }

      @NativeType("__u32")
      public int stx_gid() {
         return Statx.nstx_gid(this.address());
      }

      @NativeType("__u16")
      public short stx_mode() {
         return Statx.nstx_mode(this.address());
      }

      @NativeType("__u64")
      public long stx_ino() {
         return Statx.nstx_ino(this.address());
      }

      @NativeType("__u64")
      public long stx_size() {
         return Statx.nstx_size(this.address());
      }

      @NativeType("__u64")
      public long stx_blocks() {
         return Statx.nstx_blocks(this.address());
      }

      @NativeType("__u64")
      public long stx_attributes_mask() {
         return Statx.nstx_attributes_mask(this.address());
      }

      @NativeType("struct statx_timestamp")
      public StatxTimestamp stx_atime() {
         return Statx.nstx_atime(this.address());
      }

      @NativeType("struct statx_timestamp")
      public StatxTimestamp stx_btime() {
         return Statx.nstx_btime(this.address());
      }

      @NativeType("struct statx_timestamp")
      public StatxTimestamp stx_ctime() {
         return Statx.nstx_ctime(this.address());
      }

      @NativeType("struct statx_timestamp")
      public StatxTimestamp stx_mtime() {
         return Statx.nstx_mtime(this.address());
      }

      @NativeType("__u32")
      public int stx_rdev_major() {
         return Statx.nstx_rdev_major(this.address());
      }

      @NativeType("__u32")
      public int stx_rdev_minor() {
         return Statx.nstx_rdev_minor(this.address());
      }

      @NativeType("__u32")
      public int stx_dev_major() {
         return Statx.nstx_dev_major(this.address());
      }

      @NativeType("__u32")
      public int stx_dev_minor() {
         return Statx.nstx_dev_minor(this.address());
      }

      @NativeType("__u64")
      public long stx_mnt_id() {
         return Statx.nstx_mnt_id(this.address());
      }

      public Buffer stx_mask(@NativeType("__u32") int value) {
         Statx.nstx_mask(this.address(), value);
         return this;
      }

      public Buffer stx_blksize(@NativeType("__u32") int value) {
         Statx.nstx_blksize(this.address(), value);
         return this;
      }

      public Buffer stx_attributes(@NativeType("__u64") long value) {
         Statx.nstx_attributes(this.address(), value);
         return this;
      }

      public Buffer stx_nlink(@NativeType("__u32") int value) {
         Statx.nstx_nlink(this.address(), value);
         return this;
      }

      public Buffer stx_uid(@NativeType("__u32") int value) {
         Statx.nstx_uid(this.address(), value);
         return this;
      }

      public Buffer stx_gid(@NativeType("__u32") int value) {
         Statx.nstx_gid(this.address(), value);
         return this;
      }

      public Buffer stx_mode(@NativeType("__u16") short value) {
         Statx.nstx_mode(this.address(), value);
         return this;
      }

      public Buffer stx_ino(@NativeType("__u64") long value) {
         Statx.nstx_ino(this.address(), value);
         return this;
      }

      public Buffer stx_size(@NativeType("__u64") long value) {
         Statx.nstx_size(this.address(), value);
         return this;
      }

      public Buffer stx_blocks(@NativeType("__u64") long value) {
         Statx.nstx_blocks(this.address(), value);
         return this;
      }

      public Buffer stx_attributes_mask(@NativeType("__u64") long value) {
         Statx.nstx_attributes_mask(this.address(), value);
         return this;
      }

      public Buffer stx_atime(@NativeType("struct statx_timestamp") StatxTimestamp value) {
         Statx.nstx_atime(this.address(), value);
         return this;
      }

      public Buffer stx_atime(Consumer<StatxTimestamp> consumer) {
         consumer.accept(this.stx_atime());
         return this;
      }

      public Buffer stx_btime(@NativeType("struct statx_timestamp") StatxTimestamp value) {
         Statx.nstx_btime(this.address(), value);
         return this;
      }

      public Buffer stx_btime(Consumer<StatxTimestamp> consumer) {
         consumer.accept(this.stx_btime());
         return this;
      }

      public Buffer stx_ctime(@NativeType("struct statx_timestamp") StatxTimestamp value) {
         Statx.nstx_ctime(this.address(), value);
         return this;
      }

      public Buffer stx_ctime(Consumer<StatxTimestamp> consumer) {
         consumer.accept(this.stx_ctime());
         return this;
      }

      public Buffer stx_mtime(@NativeType("struct statx_timestamp") StatxTimestamp value) {
         Statx.nstx_mtime(this.address(), value);
         return this;
      }

      public Buffer stx_mtime(Consumer<StatxTimestamp> consumer) {
         consumer.accept(this.stx_mtime());
         return this;
      }

      public Buffer stx_rdev_major(@NativeType("__u32") int value) {
         Statx.nstx_rdev_major(this.address(), value);
         return this;
      }

      public Buffer stx_rdev_minor(@NativeType("__u32") int value) {
         Statx.nstx_rdev_minor(this.address(), value);
         return this;
      }

      public Buffer stx_dev_major(@NativeType("__u32") int value) {
         Statx.nstx_dev_major(this.address(), value);
         return this;
      }

      public Buffer stx_dev_minor(@NativeType("__u32") int value) {
         Statx.nstx_dev_minor(this.address(), value);
         return this;
      }

      public Buffer stx_mnt_id(@NativeType("__u64") long value) {
         Statx.nstx_mnt_id(this.address(), value);
         return this;
      }
   }
}
