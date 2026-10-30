package org.lwjgl.system.linux;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

public class XTimeCoord extends Struct {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TIME;
   public static final int X;
   public static final int Y;

   public XTimeCoord(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("Time")
   public long time() {
      return ntime(this.address());
   }

   public short x() {
      return nx(this.address());
   }

   public short y() {
      return ny(this.address());
   }

   public static XTimeCoord create(long address) {
      return wrap(XTimeCoord.class, address);
   }

   @Nullable
   public static XTimeCoord createSafe(long address) {
      return address == 0L ? null : wrap(XTimeCoord.class, address);
   }

   public static Buffer create(long address, int capacity) {
      return wrap(Buffer.class, address, capacity);
   }

   @Nullable
   public static Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(Buffer.class, address, capacity);
   }

   public static long ntime(long struct) {
      return MemoryUtil.memGetCLong(struct + TIME);
   }

   public static short nx(long struct) {
      return UNSAFE.getShort(null, struct + X);
   }

   public static short ny(long struct) {
      return UNSAFE.getShort(null, struct + Y);
   }

   static {
      Layout layout = __struct(__member(CLONG_SIZE), __member(2), __member(2));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      TIME = layout.offsetof(0);
      X = layout.offsetof(1);
      Y = layout.offsetof(2);
   }

   public static class Buffer extends StructBuffer<XTimeCoord, Buffer> {
      private static final XTimeCoord ELEMENT_FACTORY = XTimeCoord.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XTimeCoord.SIZEOF);
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

      protected XTimeCoord getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("Time")
      public long time() {
         return XTimeCoord.ntime(this.address());
      }

      public short x() {
         return XTimeCoord.nx(this.address());
      }

      public short y() {
         return XTimeCoord.ny(this.address());
      }
   }
}
