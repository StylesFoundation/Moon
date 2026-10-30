package org.lwjgl.system.linux;

import java.nio.ByteBuffer;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

@NativeType("struct epoll_event")
public class EpollEvent extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int EVENTS;
   public static final int DATA;

   public EpollEvent(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   @NativeType("uint32_t")
   public int events() {
      return nevents(this.address());
   }

   @NativeType("epoll_data_t")
   public EpollData data() {
      return ndata(this.address());
   }

   public EpollEvent events(@NativeType("uint32_t") int value) {
      nevents(this.address(), value);
      return this;
   }

   public EpollEvent data(@NativeType("epoll_data_t") EpollData value) {
      ndata(this.address(), value);
      return this;
   }

   public EpollEvent data(Consumer<EpollData> consumer) {
      consumer.accept(this.data());
      return this;
   }

   public EpollEvent set(int events, EpollData data) {
      this.events(events);
      this.data(data);
      return this;
   }

   public EpollEvent set(EpollEvent src) {
      MemoryUtil.memCopy(src.address(), this.address(), SIZEOF);
      return this;
   }

   public static EpollEvent malloc() {
      return wrap(EpollEvent.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static EpollEvent calloc() {
      return wrap(EpollEvent.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static EpollEvent create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(EpollEvent.class, MemoryUtil.memAddress(container), container);
   }

   public static EpollEvent create(long address) {
      return wrap(EpollEvent.class, address);
   }

   @Nullable
   public static EpollEvent createSafe(long address) {
      return address == 0L ? null : wrap(EpollEvent.class, address);
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

   public static EpollEvent malloc(MemoryStack stack) {
      return wrap(EpollEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static EpollEvent calloc(MemoryStack stack) {
      return wrap(EpollEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static int nevents(long struct) {
      return UNSAFE.getInt(null, struct + EVENTS);
   }

   public static EpollData ndata(long struct) {
      return EpollData.create(struct + DATA);
   }

   public static void nevents(long struct, int value) {
      UNSAFE.putInt(null, struct + EVENTS, value);
   }

   public static void ndata(long struct, EpollData value) {
      MemoryUtil.memCopy(value.address(), struct + DATA, EpollData.SIZEOF);
   }

   static {
      Layout layout = __struct(__member(4), __member(EpollData.SIZEOF, EpollData.ALIGNOF));
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      EVENTS = layout.offsetof(0);
      DATA = layout.offsetof(1);
   }

   public static class Buffer extends StructBuffer<EpollEvent, Buffer> implements NativeResource {
      private static final EpollEvent ELEMENT_FACTORY = EpollEvent.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / EpollEvent.SIZEOF);
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

      protected EpollEvent getElementFactory() {
         return ELEMENT_FACTORY;
      }

      @NativeType("uint32_t")
      public int events() {
         return EpollEvent.nevents(this.address());
      }

      @NativeType("epoll_data_t")
      public EpollData data() {
         return EpollEvent.ndata(this.address());
      }

      public Buffer events(@NativeType("uint32_t") int value) {
         EpollEvent.nevents(this.address(), value);
         return this;
      }

      public Buffer data(@NativeType("epoll_data_t") EpollData value) {
         EpollEvent.ndata(this.address(), value);
         return this;
      }

      public Buffer data(Consumer<EpollData> consumer) {
         consumer.accept(this.data());
         return this;
      }
   }
}
