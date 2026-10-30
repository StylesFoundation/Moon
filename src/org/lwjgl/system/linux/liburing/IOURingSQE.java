package org.lwjgl.system.linux.liburing;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

@NativeType("struct io_uring_sqe")
public class IOURingSQE extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int OPCODE;
   public static final int FLAGS;
   public static final int IOPRIO;
   public static final int FD;
   public static final int OFF;
   public static final int ADDR2;
   public static final int ADDR;
   public static final int SPLICE_OFF_IN;
   public static final int LEN;
   public static final int RW_FLAGS;
   public static final int FSYNC_FLAGS;
   public static final int POLL_EVENTS;
   public static final int POLL32_EVENTS;
   public static final int SYNC_RANGE_FLAGS;
   public static final int MSG_FLAGS;
   public static final int TIMEOUT_FLAGS;
   public static final int ACCEPT_FLAGS;
   public static final int CANCEL_FLAGS;
   public static final int OPEN_FLAGS;
   public static final int STATX_FLAGS;
   public static final int FADVISE_ADVICE;
   public static final int SPLICE_FLAGS;
   public static final int RENAME_FLAGS;
   public static final int UNLINK_FLAGS;
   public static final int HARDLINK_FLAGS;
   public static final int USER_DATA;
   public static final int BUF_INDEX;
   public static final int BUF_GROUP;
   public static final int PERSONALITY;
   public static final int SPLICE_FD_IN;
   public static final int FILE_INDEX;
   public static final int __PAD2;

   public IOURingSQE(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("__u8")
   public byte opcode() {
      return nopcode(this.address());
   }

   @NativeType("__u8")
   public byte flags() {
      return nflags(this.address());
   }

   @NativeType("__u16")
   public short ioprio() {
      return nioprio(this.address());
   }

   @NativeType("__s32")
   public int fd() {
      return nfd(this.address());
   }

   @NativeType("__u64")
   public long off() {
      return noff(this.address());
   }

   @NativeType("__u64")
   public long addr2() {
      return naddr2(this.address());
   }

   @NativeType("__u64")
   public long addr() {
      return naddr(this.address());
   }

   @NativeType("__u64")
   public long splice_off_in() {
      return nsplice_off_in(this.address());
   }

   @NativeType("__u32")
   public int len() {
      return nlen(this.address());
   }

   @NativeType("__kernel_rwf_t")
   public int rw_flags() {
      return nrw_flags(this.address());
   }

   @NativeType("__u32")
   public int fsync_flags() {
      return nfsync_flags(this.address());
   }

   @NativeType("__u16")
   public short poll_events() {
      return npoll_events(this.address());
   }

   @NativeType("__u32")
   public int poll32_events() {
      return npoll32_events(this.address());
   }

   @NativeType("__u32")
   public int sync_range_flags() {
      return nsync_range_flags(this.address());
   }

   @NativeType("__u32")
   public int msg_flags() {
      return nmsg_flags(this.address());
   }

   @NativeType("__u32")
   public int timeout_flags() {
      return ntimeout_flags(this.address());
   }

   @NativeType("__u32")
   public int accept_flags() {
      return naccept_flags(this.address());
   }

   @NativeType("__u32")
   public int cancel_flags() {
      return ncancel_flags(this.address());
   }

   @NativeType("__u32")
   public int open_flags() {
      return nopen_flags(this.address());
   }

   @NativeType("__u32")
   public int statx_flags() {
      return nstatx_flags(this.address());
   }

   @NativeType("__u32")
   public int fadvise_advice() {
      return nfadvise_advice(this.address());
   }

   @NativeType("__u32")
   public int splice_flags() {
      return nsplice_flags(this.address());
   }

   @NativeType("__u32")
   public int rename_flags() {
      return nrename_flags(this.address());
   }

   @NativeType("__u32")
   public int unlink_flags() {
      return nunlink_flags(this.address());
   }

   @NativeType("__u32")
   public int hardlink_flags() {
      return nhardlink_flags(this.address());
   }

   @NativeType("__u64")
   public long user_data() {
      return nuser_data(this.address());
   }

   @NativeType("__u16")
   public short buf_index() {
      return nbuf_index(this.address());
   }

   @NativeType("__u16")
   public short buf_group() {
      return nbuf_group(this.address());
   }

   @NativeType("__u16")
   public short personality() {
      return npersonality(this.address());
   }

   @NativeType("__s32")
   public int splice_fd_in() {
      return nsplice_fd_in(this.address());
   }

   @NativeType("__u32")
   public int file_index() {
      return nfile_index(this.address());
   }

   public IOURingSQE opcode(@NativeType("__u8") byte value) {
      nopcode(this.address(), value);
      return this;
   }

   public IOURingSQE flags(@NativeType("__u8") byte value) {
      nflags(this.address(), value);
      return this;
   }

   public IOURingSQE ioprio(@NativeType("__u16") short value) {
      nioprio(this.address(), value);
      return this;
   }

   public IOURingSQE fd(@NativeType("__s32") int value) {
      nfd(this.address(), value);
      return this;
   }

   public IOURingSQE off(@NativeType("__u64") long value) {
      noff(this.address(), value);
      return this;
   }

   public IOURingSQE addr2(@NativeType("__u64") long value) {
      naddr2(this.address(), value);
      return this;
   }

   public IOURingSQE addr(@NativeType("__u64") long value) {
      naddr(this.address(), value);
      return this;
   }

   public IOURingSQE splice_off_in(@NativeType("__u64") long value) {
      nsplice_off_in(this.address(), value);
      return this;
   }

   public IOURingSQE len(@NativeType("__u32") int value) {
      nlen(this.address(), value);
      return this;
   }

   public IOURingSQE rw_flags(@NativeType("__kernel_rwf_t") int value) {
      nrw_flags(this.address(), value);
      return this;
   }

   public IOURingSQE fsync_flags(@NativeType("__u32") int value) {
      nfsync_flags(this.address(), value);
      return this;
   }

   public IOURingSQE poll_events(@NativeType("__u16") short value) {
      npoll_events(this.address(), value);
      return this;
   }

   public IOURingSQE poll32_events(@NativeType("__u32") int value) {
      npoll32_events(this.address(), value);
      return this;
   }

   public IOURingSQE sync_range_flags(@NativeType("__u32") int value) {
      nsync_range_flags(this.address(), value);
      return this;
   }

   public IOURingSQE msg_flags(@NativeType("__u32") int value) {
      nmsg_flags(this.address(), value);
      return this;
   }

   public IOURingSQE timeout_flags(@NativeType("__u32") int value) {
      ntimeout_flags(this.address(), value);
      return this;
   }

   public IOURingSQE accept_flags(@NativeType("__u32") int value) {
      naccept_flags(this.address(), value);
      return this;
   }

   public IOURingSQE cancel_flags(@NativeType("__u32") int value) {
      ncancel_flags(this.address(), value);
      return this;
   }

   public IOURingSQE open_flags(@NativeType("__u32") int value) {
      nopen_flags(this.address(), value);
      return this;
   }

   public IOURingSQE statx_flags(@NativeType("__u32") int value) {
      nstatx_flags(this.address(), value);
      return this;
   }

   public IOURingSQE fadvise_advice(@NativeType("__u32") int value) {
      nfadvise_advice(this.address(), value);
      return this;
   }

   public IOURingSQE splice_flags(@NativeType("__u32") int value) {
      nsplice_flags(this.address(), value);
      return this;
   }

   public IOURingSQE rename_flags(@NativeType("__u32") int value) {
      nrename_flags(this.address(), value);
      return this;
   }

   public IOURingSQE unlink_flags(@NativeType("__u32") int value) {
      nunlink_flags(this.address(), value);
      return this;
   }

   public IOURingSQE hardlink_flags(@NativeType("__u32") int value) {
      nhardlink_flags(this.address(), value);
      return this;
   }

   public IOURingSQE user_data(@NativeType("__u64") long value) {
      nuser_data(this.address(), value);
      return this;
   }

   public IOURingSQE buf_index(@NativeType("__u16") short value) {
      nbuf_index(this.address(), value);
      return this;
   }

   public IOURingSQE buf_group(@NativeType("__u16") short value) {
      nbuf_group(this.address(), value);
      return this;
   }

   public IOURingSQE personality(@NativeType("__u16") short value) {
      npersonality(this.address(), value);
      return this;
   }

   public IOURingSQE splice_fd_in(@NativeType("__s32") int value) {
      nsplice_fd_in(this.address(), value);
      return this;
   }

   public IOURingSQE file_index(@NativeType("__u32") int value) {
      nfile_index(this.address(), value);
      return this;
   }

   public IOURingSQE set(IOURingSQE src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static IOURingSQE malloc() {
      return wrap(IOURingSQE.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static IOURingSQE calloc() {
      return wrap(IOURingSQE.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static IOURingSQE create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(IOURingSQE.class, MemoryUtil.memAddress(container), container);
   }

   public static IOURingSQE create(long address) {
      return wrap(IOURingSQE.class, address);
   }

   @Nullable
   public static IOURingSQE createSafe(long address) {
      return address == 0L ? null : wrap(IOURingSQE.class, address);
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

   public static IOURingSQE malloc(MemoryStack stack) {
      return wrap(IOURingSQE.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static IOURingSQE calloc(MemoryStack stack) {
      return wrap(IOURingSQE.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static byte nopcode(long struct) {
      return UNSAFE.getByte(null, struct + OPCODE);
   }

   public static byte nflags(long struct) {
      return UNSAFE.getByte(null, struct + FLAGS);
   }

   public static short nioprio(long struct) {
      return UNSAFE.getShort(null, struct + IOPRIO);
   }

   public static int nfd(long struct) {
      return UNSAFE.getInt(null, struct + FD);
   }

   public static long noff(long struct) {
      return UNSAFE.getLong(null, struct + OFF);
   }

   public static long naddr2(long struct) {
      return UNSAFE.getLong(null, struct + ADDR2);
   }

   public static long naddr(long struct) {
      return UNSAFE.getLong(null, struct + ADDR);
   }

   public static long nsplice_off_in(long struct) {
      return UNSAFE.getLong(null, struct + SPLICE_OFF_IN);
   }

   public static int nlen(long struct) {
      return UNSAFE.getInt(null, struct + LEN);
   }

   public static int nrw_flags(long struct) {
      return UNSAFE.getInt(null, struct + RW_FLAGS);
   }

   public static int nfsync_flags(long struct) {
      return UNSAFE.getInt(null, struct + FSYNC_FLAGS);
   }

   public static short npoll_events(long struct) {
      return UNSAFE.getShort(null, struct + POLL_EVENTS);
   }

   public static int npoll32_events(long struct) {
      return UNSAFE.getInt(null, struct + POLL32_EVENTS);
   }

   public static int nsync_range_flags(long struct) {
      return UNSAFE.getInt(null, struct + SYNC_RANGE_FLAGS);
   }

   public static int nmsg_flags(long struct) {
      return UNSAFE.getInt(null, struct + MSG_FLAGS);
   }

   public static int ntimeout_flags(long struct) {
      return UNSAFE.getInt(null, struct + TIMEOUT_FLAGS);
   }

   public static int naccept_flags(long struct) {
      return UNSAFE.getInt(null, struct + ACCEPT_FLAGS);
   }

   public static int ncancel_flags(long struct) {
      return UNSAFE.getInt(null, struct + CANCEL_FLAGS);
   }

   public static int nopen_flags(long struct) {
      return UNSAFE.getInt(null, struct + OPEN_FLAGS);
   }

   public static int nstatx_flags(long struct) {
      return UNSAFE.getInt(null, struct + STATX_FLAGS);
   }

   public static int nfadvise_advice(long struct) {
      return UNSAFE.getInt(null, struct + FADVISE_ADVICE);
   }

   public static int nsplice_flags(long struct) {
      return UNSAFE.getInt(null, struct + SPLICE_FLAGS);
   }

   public static int nrename_flags(long struct) {
      return UNSAFE.getInt(null, struct + RENAME_FLAGS);
   }

   public static int nunlink_flags(long struct) {
      return UNSAFE.getInt(null, struct + UNLINK_FLAGS);
   }

   public static int nhardlink_flags(long struct) {
      return UNSAFE.getInt(null, struct + HARDLINK_FLAGS);
   }

   public static long nuser_data(long struct) {
      return UNSAFE.getLong(null, struct + USER_DATA);
   }

   public static short nbuf_index(long struct) {
      return UNSAFE.getShort(null, struct + BUF_INDEX);
   }

   public static short nbuf_group(long struct) {
      return UNSAFE.getShort(null, struct + BUF_GROUP);
   }

   public static short npersonality(long struct) {
      return UNSAFE.getShort(null, struct + PERSONALITY);
   }

   public static int nsplice_fd_in(long struct) {
      return UNSAFE.getInt(null, struct + SPLICE_FD_IN);
   }

   public static int nfile_index(long struct) {
      return UNSAFE.getInt(null, struct + FILE_INDEX);
   }

   public static LongBuffer n__pad2(long struct) {
      return MemoryUtil.memLongBuffer(struct + __PAD2, 2);
   }

   public static long n__pad2(long struct, int index) {
      return UNSAFE.getLong(null, struct + __PAD2 + Checks.check(index, 2) * 8L);
   }

   public static void nopcode(long struct, byte value) {
      UNSAFE.putByte(null, struct + OPCODE, value);
   }

   public static void nflags(long struct, byte value) {
      UNSAFE.putByte(null, struct + FLAGS, value);
   }

   public static void nioprio(long struct, short value) {
      UNSAFE.putShort(null, struct + IOPRIO, value);
   }

   public static void nfd(long struct, int value) {
      UNSAFE.putInt(null, struct + FD, value);
   }

   public static void noff(long struct, long value) {
      UNSAFE.putLong(null, struct + OFF, value);
   }

   public static void naddr2(long struct, long value) {
      UNSAFE.putLong(null, struct + ADDR2, value);
   }

   public static void naddr(long struct, long value) {
      UNSAFE.putLong(null, struct + ADDR, value);
   }

   public static void nsplice_off_in(long struct, long value) {
      UNSAFE.putLong(null, struct + SPLICE_OFF_IN, value);
   }

   public static void nlen(long struct, int value) {
      UNSAFE.putInt(null, struct + LEN, value);
   }

   public static void nrw_flags(long struct, int value) {
      UNSAFE.putInt(null, struct + RW_FLAGS, value);
   }

   public static void nfsync_flags(long struct, int value) {
      UNSAFE.putInt(null, struct + FSYNC_FLAGS, value);
   }

   public static void npoll_events(long struct, short value) {
      UNSAFE.putShort(null, struct + POLL_EVENTS, value);
   }

   public static void npoll32_events(long struct, int value) {
      UNSAFE.putInt(null, struct + POLL32_EVENTS, value);
   }

   public static void nsync_range_flags(long struct, int value) {
      UNSAFE.putInt(null, struct + SYNC_RANGE_FLAGS, value);
   }

   public static void nmsg_flags(long struct, int value) {
      UNSAFE.putInt(null, struct + MSG_FLAGS, value);
   }

   public static void ntimeout_flags(long struct, int value) {
      UNSAFE.putInt(null, struct + TIMEOUT_FLAGS, value);
   }

   public static void naccept_flags(long struct, int value) {
      UNSAFE.putInt(null, struct + ACCEPT_FLAGS, value);
   }

   public static void ncancel_flags(long struct, int value) {
      UNSAFE.putInt(null, struct + CANCEL_FLAGS, value);
   }

   public static void nopen_flags(long struct, int value) {
      UNSAFE.putInt(null, struct + OPEN_FLAGS, value);
   }

   public static void nstatx_flags(long struct, int value) {
      UNSAFE.putInt(null, struct + STATX_FLAGS, value);
   }

   public static void nfadvise_advice(long struct, int value) {
      UNSAFE.putInt(null, struct + FADVISE_ADVICE, value);
   }

   public static void nsplice_flags(long struct, int value) {
      UNSAFE.putInt(null, struct + SPLICE_FLAGS, value);
   }

   public static void nrename_flags(long struct, int value) {
      UNSAFE.putInt(null, struct + RENAME_FLAGS, value);
   }

   public static void nunlink_flags(long struct, int value) {
      UNSAFE.putInt(null, struct + UNLINK_FLAGS, value);
   }

   public static void nhardlink_flags(long struct, int value) {
      UNSAFE.putInt(null, struct + HARDLINK_FLAGS, value);
   }

   public static void nuser_data(long struct, long value) {
      UNSAFE.putLong(null, struct + USER_DATA, value);
   }

   public static void nbuf_index(long struct, short value) {
      UNSAFE.putShort(null, struct + BUF_INDEX, value);
   }

   public static void nbuf_group(long struct, short value) {
      UNSAFE.putShort(null, struct + BUF_GROUP, value);
   }

   public static void npersonality(long struct, short value) {
      UNSAFE.putShort(null, struct + PERSONALITY, value);
   }

   public static void nsplice_fd_in(long struct, int value) {
      UNSAFE.putInt(null, struct + SPLICE_FD_IN, value);
   }

   public static void nfile_index(long struct, int value) {
      UNSAFE.putInt(null, struct + FILE_INDEX, value);
   }

   public static void n__pad2(long struct, LongBuffer value) {
      if (Checks.CHECKS) {
         Checks.checkGT(value, 2);
      }

      MemoryUtil.memCopy(MemoryUtil.memAddress(value), struct + __PAD2, value.remaining() * 8);
   }

   public static void n__pad2(long struct, int index, long value) {
      UNSAFE.putLong(null, struct + __PAD2 + Checks.check(index, 2) * 8L, value);
   }

   static {
      Layout layout = __struct(
         __member(1),
         __member(1),
         __member(2),
         __member(4),
         __union(__member(8), __member(8)),
         __union(__member(8), __member(8)),
         __member(4),
         __union(
            __member(4),
            __member(4),
            __member(2),
            __member(4),
            __member(4),
            __member(4),
            __member(4),
            __member(4),
            __member(4),
            __member(4),
            __member(4),
            __member(4),
            __member(4),
            __member(4),
            __member(4),
            __member(4)
         ),
         __member(8),
         __union(__member(2), __member(2)),
         __member(2),
         __union(__member(4), __member(4)),
         __array(8, 2)
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      OPCODE = layout.offsetof(0);
      FLAGS = layout.offsetof(1);
      IOPRIO = layout.offsetof(2);
      FD = layout.offsetof(3);
      OFF = layout.offsetof(5);
      ADDR2 = layout.offsetof(6);
      ADDR = layout.offsetof(8);
      SPLICE_OFF_IN = layout.offsetof(9);
      LEN = layout.offsetof(10);
      RW_FLAGS = layout.offsetof(12);
      FSYNC_FLAGS = layout.offsetof(13);
      POLL_EVENTS = layout.offsetof(14);
      POLL32_EVENTS = layout.offsetof(15);
      SYNC_RANGE_FLAGS = layout.offsetof(16);
      MSG_FLAGS = layout.offsetof(17);
      TIMEOUT_FLAGS = layout.offsetof(18);
      ACCEPT_FLAGS = layout.offsetof(19);
      CANCEL_FLAGS = layout.offsetof(20);
      OPEN_FLAGS = layout.offsetof(21);
      STATX_FLAGS = layout.offsetof(22);
      FADVISE_ADVICE = layout.offsetof(23);
      SPLICE_FLAGS = layout.offsetof(24);
      RENAME_FLAGS = layout.offsetof(25);
      UNLINK_FLAGS = layout.offsetof(26);
      HARDLINK_FLAGS = layout.offsetof(27);
      USER_DATA = layout.offsetof(28);
      BUF_INDEX = layout.offsetof(30);
      BUF_GROUP = layout.offsetof(31);
      PERSONALITY = layout.offsetof(32);
      SPLICE_FD_IN = layout.offsetof(34);
      FILE_INDEX = layout.offsetof(35);
      __PAD2 = layout.offsetof(36);
   }

   public static class Buffer extends StructBuffer<IOURingSQE, Buffer> implements NativeResource {
      private static final IOURingSQE ELEMENT_FACTORY = IOURingSQE.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / IOURingSQE.SIZEOF);
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

      protected IOURingSQE getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("__u8")
      public byte opcode() {
         return IOURingSQE.nopcode(this.address());
      }

      @NativeType("__u8")
      public byte flags() {
         return IOURingSQE.nflags(this.address());
      }

      @NativeType("__u16")
      public short ioprio() {
         return IOURingSQE.nioprio(this.address());
      }

      @NativeType("__s32")
      public int fd() {
         return IOURingSQE.nfd(this.address());
      }

      @NativeType("__u64")
      public long off() {
         return IOURingSQE.noff(this.address());
      }

      @NativeType("__u64")
      public long addr2() {
         return IOURingSQE.naddr2(this.address());
      }

      @NativeType("__u64")
      public long addr() {
         return IOURingSQE.naddr(this.address());
      }

      @NativeType("__u64")
      public long splice_off_in() {
         return IOURingSQE.nsplice_off_in(this.address());
      }

      @NativeType("__u32")
      public int len() {
         return IOURingSQE.nlen(this.address());
      }

      @NativeType("__kernel_rwf_t")
      public int rw_flags() {
         return IOURingSQE.nrw_flags(this.address());
      }

      @NativeType("__u32")
      public int fsync_flags() {
         return IOURingSQE.nfsync_flags(this.address());
      }

      @NativeType("__u16")
      public short poll_events() {
         return IOURingSQE.npoll_events(this.address());
      }

      @NativeType("__u32")
      public int poll32_events() {
         return IOURingSQE.npoll32_events(this.address());
      }

      @NativeType("__u32")
      public int sync_range_flags() {
         return IOURingSQE.nsync_range_flags(this.address());
      }

      @NativeType("__u32")
      public int msg_flags() {
         return IOURingSQE.nmsg_flags(this.address());
      }

      @NativeType("__u32")
      public int timeout_flags() {
         return IOURingSQE.ntimeout_flags(this.address());
      }

      @NativeType("__u32")
      public int accept_flags() {
         return IOURingSQE.naccept_flags(this.address());
      }

      @NativeType("__u32")
      public int cancel_flags() {
         return IOURingSQE.ncancel_flags(this.address());
      }

      @NativeType("__u32")
      public int open_flags() {
         return IOURingSQE.nopen_flags(this.address());
      }

      @NativeType("__u32")
      public int statx_flags() {
         return IOURingSQE.nstatx_flags(this.address());
      }

      @NativeType("__u32")
      public int fadvise_advice() {
         return IOURingSQE.nfadvise_advice(this.address());
      }

      @NativeType("__u32")
      public int splice_flags() {
         return IOURingSQE.nsplice_flags(this.address());
      }

      @NativeType("__u32")
      public int rename_flags() {
         return IOURingSQE.nrename_flags(this.address());
      }

      @NativeType("__u32")
      public int unlink_flags() {
         return IOURingSQE.nunlink_flags(this.address());
      }

      @NativeType("__u32")
      public int hardlink_flags() {
         return IOURingSQE.nhardlink_flags(this.address());
      }

      @NativeType("__u64")
      public long user_data() {
         return IOURingSQE.nuser_data(this.address());
      }

      @NativeType("__u16")
      public short buf_index() {
         return IOURingSQE.nbuf_index(this.address());
      }

      @NativeType("__u16")
      public short buf_group() {
         return IOURingSQE.nbuf_group(this.address());
      }

      @NativeType("__u16")
      public short personality() {
         return IOURingSQE.npersonality(this.address());
      }

      @NativeType("__s32")
      public int splice_fd_in() {
         return IOURingSQE.nsplice_fd_in(this.address());
      }

      @NativeType("__u32")
      public int file_index() {
         return IOURingSQE.nfile_index(this.address());
      }

      public Buffer opcode(@NativeType("__u8") byte value) {
         IOURingSQE.nopcode(this.address(), value);
         return this;
      }

      public Buffer flags(@NativeType("__u8") byte value) {
         IOURingSQE.nflags(this.address(), value);
         return this;
      }

      public Buffer ioprio(@NativeType("__u16") short value) {
         IOURingSQE.nioprio(this.address(), value);
         return this;
      }

      public Buffer fd(@NativeType("__s32") int value) {
         IOURingSQE.nfd(this.address(), value);
         return this;
      }

      public Buffer off(@NativeType("__u64") long value) {
         IOURingSQE.noff(this.address(), value);
         return this;
      }

      public Buffer addr2(@NativeType("__u64") long value) {
         IOURingSQE.naddr2(this.address(), value);
         return this;
      }

      public Buffer addr(@NativeType("__u64") long value) {
         IOURingSQE.naddr(this.address(), value);
         return this;
      }

      public Buffer splice_off_in(@NativeType("__u64") long value) {
         IOURingSQE.nsplice_off_in(this.address(), value);
         return this;
      }

      public Buffer len(@NativeType("__u32") int value) {
         IOURingSQE.nlen(this.address(), value);
         return this;
      }

      public Buffer rw_flags(@NativeType("__kernel_rwf_t") int value) {
         IOURingSQE.nrw_flags(this.address(), value);
         return this;
      }

      public Buffer fsync_flags(@NativeType("__u32") int value) {
         IOURingSQE.nfsync_flags(this.address(), value);
         return this;
      }

      public Buffer poll_events(@NativeType("__u16") short value) {
         IOURingSQE.npoll_events(this.address(), value);
         return this;
      }

      public Buffer poll32_events(@NativeType("__u32") int value) {
         IOURingSQE.npoll32_events(this.address(), value);
         return this;
      }

      public Buffer sync_range_flags(@NativeType("__u32") int value) {
         IOURingSQE.nsync_range_flags(this.address(), value);
         return this;
      }

      public Buffer msg_flags(@NativeType("__u32") int value) {
         IOURingSQE.nmsg_flags(this.address(), value);
         return this;
      }

      public Buffer timeout_flags(@NativeType("__u32") int value) {
         IOURingSQE.ntimeout_flags(this.address(), value);
         return this;
      }

      public Buffer accept_flags(@NativeType("__u32") int value) {
         IOURingSQE.naccept_flags(this.address(), value);
         return this;
      }

      public Buffer cancel_flags(@NativeType("__u32") int value) {
         IOURingSQE.ncancel_flags(this.address(), value);
         return this;
      }

      public Buffer open_flags(@NativeType("__u32") int value) {
         IOURingSQE.nopen_flags(this.address(), value);
         return this;
      }

      public Buffer statx_flags(@NativeType("__u32") int value) {
         IOURingSQE.nstatx_flags(this.address(), value);
         return this;
      }

      public Buffer fadvise_advice(@NativeType("__u32") int value) {
         IOURingSQE.nfadvise_advice(this.address(), value);
         return this;
      }

      public Buffer splice_flags(@NativeType("__u32") int value) {
         IOURingSQE.nsplice_flags(this.address(), value);
         return this;
      }

      public Buffer rename_flags(@NativeType("__u32") int value) {
         IOURingSQE.nrename_flags(this.address(), value);
         return this;
      }

      public Buffer unlink_flags(@NativeType("__u32") int value) {
         IOURingSQE.nunlink_flags(this.address(), value);
         return this;
      }

      public Buffer hardlink_flags(@NativeType("__u32") int value) {
         IOURingSQE.nhardlink_flags(this.address(), value);
         return this;
      }

      public Buffer user_data(@NativeType("__u64") long value) {
         IOURingSQE.nuser_data(this.address(), value);
         return this;
      }

      public Buffer buf_index(@NativeType("__u16") short value) {
         IOURingSQE.nbuf_index(this.address(), value);
         return this;
      }

      public Buffer buf_group(@NativeType("__u16") short value) {
         IOURingSQE.nbuf_group(this.address(), value);
         return this;
      }

      public Buffer personality(@NativeType("__u16") short value) {
         IOURingSQE.npersonality(this.address(), value);
         return this;
      }

      public Buffer splice_fd_in(@NativeType("__s32") int value) {
         IOURingSQE.nsplice_fd_in(this.address(), value);
         return this;
      }

      public Buffer file_index(@NativeType("__u32") int value) {
         IOURingSQE.nfile_index(this.address(), value);
         return this;
      }
   }
}
