package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

public class GLXStereoNotifyEventEXT extends Struct {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TYPE;
   public static final int SERIAL;
   public static final int SEND_EVENT;
   public static final int DISPLAY;
   public static final int EXTENSION;
   public static final int EVTYPE;
   public static final int WINDOW;
   public static final int STEREO_TREE;

   public GLXStereoNotifyEventEXT(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   public int type() {
      return ntype(this.address());
   }

   @NativeType("unsigned long")
   public long serial() {
      return nserial(this.address());
   }

   @NativeType("Bool")
   public boolean send_event() {
      return nsend_event(this.address()) != 0;
   }

   @NativeType("Display *")
   public long display() {
      return ndisplay(this.address());
   }

   public int extension() {
      return nextension(this.address());
   }

   public int evtype() {
      return nevtype(this.address());
   }

   @NativeType("GLXDrawable")
   public long window() {
      return nwindow(this.address());
   }

   @NativeType("Bool")
   public boolean stereo_tree() {
      return nstereo_tree(this.address()) != 0;
   }

   public static GLXStereoNotifyEventEXT create(long address) {
      return wrap(GLXStereoNotifyEventEXT.class, address);
   }

   @Nullable
   public static GLXStereoNotifyEventEXT createSafe(long address) {
      return address == 0L ? null : wrap(GLXStereoNotifyEventEXT.class, address);
   }

   public static Buffer create(long address, int capacity) {
      return wrap(Buffer.class, address, capacity);
   }

   @Nullable
   public static Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(Buffer.class, address, capacity);
   }

   public static int ntype(long struct) {
      return UNSAFE.getInt(null, struct + TYPE);
   }

   public static long nserial(long struct) {
      return MemoryUtil.memGetCLong(struct + SERIAL);
   }

   public static int nsend_event(long struct) {
      return UNSAFE.getInt(null, struct + SEND_EVENT);
   }

   public static long ndisplay(long struct) {
      return MemoryUtil.memGetAddress(struct + DISPLAY);
   }

   public static int nextension(long struct) {
      return UNSAFE.getInt(null, struct + EXTENSION);
   }

   public static int nevtype(long struct) {
      return UNSAFE.getInt(null, struct + EVTYPE);
   }

   public static long nwindow(long struct) {
      return MemoryUtil.memGetAddress(struct + WINDOW);
   }

   public static int nstereo_tree(long struct) {
      return UNSAFE.getInt(null, struct + STEREO_TREE);
   }

   static {
      Layout layout = __struct(
         __member(4), __member(CLONG_SIZE), __member(4), __member(POINTER_SIZE), __member(4), __member(4), __member(POINTER_SIZE), __member(4)
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      TYPE = layout.offsetof(0);
      SERIAL = layout.offsetof(1);
      SEND_EVENT = layout.offsetof(2);
      DISPLAY = layout.offsetof(3);
      EXTENSION = layout.offsetof(4);
      EVTYPE = layout.offsetof(5);
      WINDOW = layout.offsetof(6);
      STEREO_TREE = layout.offsetof(7);
   }

   public static class Buffer extends StructBuffer<GLXStereoNotifyEventEXT, Buffer> {
      private static final GLXStereoNotifyEventEXT ELEMENT_FACTORY = GLXStereoNotifyEventEXT.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / GLXStereoNotifyEventEXT.SIZEOF);
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

      protected GLXStereoNotifyEventEXT getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return GLXStereoNotifyEventEXT.ntype(this.address());
      }

      @NativeType("unsigned long")
      public long serial() {
         return GLXStereoNotifyEventEXT.nserial(this.address());
      }

      @NativeType("Bool")
      public boolean send_event() {
         return GLXStereoNotifyEventEXT.nsend_event(this.address()) != 0;
      }

      @NativeType("Display *")
      public long display() {
         return GLXStereoNotifyEventEXT.ndisplay(this.address());
      }

      public int extension() {
         return GLXStereoNotifyEventEXT.nextension(this.address());
      }

      public int evtype() {
         return GLXStereoNotifyEventEXT.nevtype(this.address());
      }

      @NativeType("GLXDrawable")
      public long window() {
         return GLXStereoNotifyEventEXT.nwindow(this.address());
      }

      @NativeType("Bool")
      public boolean stereo_tree() {
         return GLXStereoNotifyEventEXT.nstereo_tree(this.address()) != 0;
      }
   }
}
