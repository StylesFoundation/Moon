package org.lwjgl.system.macosx;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

public class CGEventTapInformation extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int EVENTTAPID;
   public static final int TAPPOINT;
   public static final int OPTIONS;
   public static final int EVENTSOFINTEREST;
   public static final int TAPPINGPROCESS;
   public static final int PROCESSBEINGTAPPED;
   public static final int ENABLED;
   public static final int MINUSECLATENCY;
   public static final int AVGUSECLATENCY;
   public static final int MAXUSECLATENCY;

   public CGEventTapInformation(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("uint32_t")
   public int eventTapID() {
      return neventTapID(this.address());
   }

   @NativeType("CGEventTapLocation")
   public int tapPoint() {
      return ntapPoint(this.address());
   }

   @NativeType("CGEventTapOptions")
   public int options() {
      return noptions(this.address());
   }

   @NativeType("CGEventMask")
   public long eventsOfInterest() {
      return neventsOfInterest(this.address());
   }

   @NativeType("pid_t")
   public long tappingProcess() {
      return ntappingProcess(this.address());
   }

   @NativeType("pid_t")
   public long processBeingTapped() {
      return nprocessBeingTapped(this.address());
   }

   @NativeType("bool")
   public boolean enabled() {
      return nenabled(this.address());
   }

   public float minUsecLatency() {
      return nminUsecLatency(this.address());
   }

   public float avgUsecLatency() {
      return navgUsecLatency(this.address());
   }

   public float maxUsecLatency() {
      return nmaxUsecLatency(this.address());
   }

   public static CGEventTapInformation malloc() {
      return wrap(CGEventTapInformation.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static CGEventTapInformation calloc() {
      return wrap(CGEventTapInformation.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static CGEventTapInformation create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(CGEventTapInformation.class, MemoryUtil.memAddress(container), container);
   }

   public static CGEventTapInformation create(long address) {
      return wrap(CGEventTapInformation.class, address);
   }

   @Nullable
   public static CGEventTapInformation createSafe(long address) {
      return address == 0L ? null : wrap(CGEventTapInformation.class, address);
   }

   public static CGEventTapInformation.Buffer malloc(int capacity) {
      return wrap(CGEventTapInformation.Buffer.class, MemoryUtil.nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
   }

   public static CGEventTapInformation.Buffer calloc(int capacity) {
      return wrap(CGEventTapInformation.Buffer.class, MemoryUtil.nmemCallocChecked(capacity, SIZEOF), capacity);
   }

   public static CGEventTapInformation.Buffer create(int capacity) {
      ByteBuffer container = __create(capacity, SIZEOF);
      return wrap(CGEventTapInformation.Buffer.class, MemoryUtil.memAddress(container), capacity, container);
   }

   public static CGEventTapInformation.Buffer create(long address, int capacity) {
      return wrap(CGEventTapInformation.Buffer.class, address, capacity);
   }

   @Nullable
   public static CGEventTapInformation.Buffer createSafe(long address, int capacity) {
      return address == 0L ? null : wrap(CGEventTapInformation.Buffer.class, address, capacity);
   }

   @Deprecated
   public static CGEventTapInformation mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static CGEventTapInformation callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static CGEventTapInformation mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static CGEventTapInformation callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static CGEventTapInformation.Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static CGEventTapInformation.Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static CGEventTapInformation.Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static CGEventTapInformation.Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static CGEventTapInformation malloc(MemoryStack stack) {
      return wrap(CGEventTapInformation.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static CGEventTapInformation calloc(MemoryStack stack) {
      return wrap(CGEventTapInformation.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static CGEventTapInformation.Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(CGEventTapInformation.Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static CGEventTapInformation.Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(CGEventTapInformation.Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static int neventTapID(long struct) {
      return UNSAFE.getInt(null, struct + EVENTTAPID);
   }

   public static int ntapPoint(long struct) {
      return UNSAFE.getInt(null, struct + TAPPOINT);
   }

   public static int noptions(long struct) {
      return UNSAFE.getInt(null, struct + OPTIONS);
   }

   public static long neventsOfInterest(long struct) {
      return UNSAFE.getLong(null, struct + EVENTSOFINTEREST);
   }

   public static long ntappingProcess(long struct) {
      return MemoryUtil.memGetAddress(struct + TAPPINGPROCESS);
   }

   public static long nprocessBeingTapped(long struct) {
      return MemoryUtil.memGetAddress(struct + PROCESSBEINGTAPPED);
   }

   public static boolean nenabled(long struct) {
      return UNSAFE.getByte(null, struct + ENABLED) != 0;
   }

   public static float nminUsecLatency(long struct) {
      return UNSAFE.getFloat(null, struct + MINUSECLATENCY);
   }

   public static float navgUsecLatency(long struct) {
      return UNSAFE.getFloat(null, struct + AVGUSECLATENCY);
   }

   public static float nmaxUsecLatency(long struct) {
      return UNSAFE.getFloat(null, struct + MAXUSECLATENCY);
   }

   static {
      Struct.Layout layout = __struct(
         __member(4), __member(4), __member(4), __member(8), __member(POINTER_SIZE), __member(POINTER_SIZE), __member(1), __member(4), __member(4), __member(4)
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      EVENTTAPID = layout.offsetof(0);
      TAPPOINT = layout.offsetof(1);
      OPTIONS = layout.offsetof(2);
      EVENTSOFINTEREST = layout.offsetof(3);
      TAPPINGPROCESS = layout.offsetof(4);
      PROCESSBEINGTAPPED = layout.offsetof(5);
      ENABLED = layout.offsetof(6);
      MINUSECLATENCY = layout.offsetof(7);
      AVGUSECLATENCY = layout.offsetof(8);
      MAXUSECLATENCY = layout.offsetof(9);
   }

   public static class Buffer extends StructBuffer<CGEventTapInformation, CGEventTapInformation.Buffer> implements NativeResource {
      private static final CGEventTapInformation ELEMENT_FACTORY = CGEventTapInformation.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / CGEventTapInformation.SIZEOF);
      }

      public Buffer(long address, int cap) {
         super(address, null, -1, 0, cap, cap);
      }

      Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
         super(address, container, mark, pos, lim, cap);
      }

      protected CGEventTapInformation.Buffer self() {
         return this;
      }

      protected CGEventTapInformation getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("uint32_t")
      public int eventTapID() {
         return CGEventTapInformation.neventTapID(this.address());
      }

      @NativeType("CGEventTapLocation")
      public int tapPoint() {
         return CGEventTapInformation.ntapPoint(this.address());
      }

      @NativeType("CGEventTapOptions")
      public int options() {
         return CGEventTapInformation.noptions(this.address());
      }

      @NativeType("CGEventMask")
      public long eventsOfInterest() {
         return CGEventTapInformation.neventsOfInterest(this.address());
      }

      @NativeType("pid_t")
      public long tappingProcess() {
         return CGEventTapInformation.ntappingProcess(this.address());
      }

      @NativeType("pid_t")
      public long processBeingTapped() {
         return CGEventTapInformation.nprocessBeingTapped(this.address());
      }

      @NativeType("bool")
      public boolean enabled() {
         return CGEventTapInformation.nenabled(this.address());
      }

      public float minUsecLatency() {
         return CGEventTapInformation.nminUsecLatency(this.address());
      }

      public float avgUsecLatency() {
         return CGEventTapInformation.navgUsecLatency(this.address());
      }

      public float maxUsecLatency() {
         return CGEventTapInformation.nmaxUsecLatency(this.address());
      }
   }
}
