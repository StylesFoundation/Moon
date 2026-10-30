package org.lwjgl.system.linux;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.Struct;
import org.lwjgl.system.StructBuffer;

public class XEvent extends Struct implements NativeResource {
   public static final int SIZEOF;
   public static final int ALIGNOF;
   public static final int TYPE;
   public static final int XANY;
   public static final int XKEY;
   public static final int XBUTTON;
   public static final int XMOTION;
   public static final int XCROSSING;
   public static final int XFOCUS;
   public static final int XEXPOSE;
   public static final int XGRAPHICSEXPOSE;
   public static final int XNOEXPOSE;
   public static final int XVISIBILITY;
   public static final int XCREATEWINDOW;
   public static final int XDESTROYWINDOW;
   public static final int XUNMAP;
   public static final int XMAP;
   public static final int XMAPREQUEST;
   public static final int XREPARENT;
   public static final int XCONFIGURE;
   public static final int XGRAVITY;
   public static final int XRESIZEREQUEST;
   public static final int XCONFIGUREREQUEST;
   public static final int XCIRCULATE;
   public static final int XCIRCULATEREQUEST;
   public static final int XPROPERTY;
   public static final int XSELECTIONCLEAR;
   public static final int XSELECTIONREQUEST;
   public static final int XSELECTION;
   public static final int XCOLORMAP;
   public static final int XCLIENT;
   public static final int XMAPPING;
   public static final int XERROR;
   public static final int XKEYMAP;
   public static final int XGENERIC;
   public static final int XCOOKIE;

   public XEvent(ByteBuffer container) {
      super(MemoryUtil.memAddress(container), __checkContainer(container, SIZEOF));
   }

   @Override
   public int sizeof() {
      return SIZEOF;
   }

   public int type() {
      return ntype(this.address());
   }

   public XAnyEvent xany() {
      return nxany(this.address());
   }

   public XKeyEvent xkey() {
      return nxkey(this.address());
   }

   public XButtonEvent xbutton() {
      return nxbutton(this.address());
   }

   public XMotionEvent xmotion() {
      return nxmotion(this.address());
   }

   public XCrossingEvent xcrossing() {
      return nxcrossing(this.address());
   }

   public XFocusChangeEvent xfocus() {
      return nxfocus(this.address());
   }

   public XExposeEvent xexpose() {
      return nxexpose(this.address());
   }

   public XGraphicsExposeEvent xgraphicsexpose() {
      return nxgraphicsexpose(this.address());
   }

   public XNoExposeEvent xnoexpose() {
      return nxnoexpose(this.address());
   }

   public XVisibilityEvent xvisibility() {
      return nxvisibility(this.address());
   }

   public XCreateWindowEvent xcreatewindow() {
      return nxcreatewindow(this.address());
   }

   public XDestroyWindowEvent xdestroywindow() {
      return nxdestroywindow(this.address());
   }

   public XUnmapEvent xunmap() {
      return nxunmap(this.address());
   }

   public XMapEvent xmap() {
      return nxmap(this.address());
   }

   public XMapRequestEvent xmaprequest() {
      return nxmaprequest(this.address());
   }

   public XReparentEvent xreparent() {
      return nxreparent(this.address());
   }

   public XConfigureEvent xconfigure() {
      return nxconfigure(this.address());
   }

   public XGravityEvent xgravity() {
      return nxgravity(this.address());
   }

   public XResizeRequestEvent xresizerequest() {
      return nxresizerequest(this.address());
   }

   public XConfigureRequestEvent xconfigurerequest() {
      return nxconfigurerequest(this.address());
   }

   public XCirculateEvent xcirculate() {
      return nxcirculate(this.address());
   }

   public XCirculateRequestEvent xcirculaterequest() {
      return nxcirculaterequest(this.address());
   }

   public XPropertyEvent xproperty() {
      return nxproperty(this.address());
   }

   public XSelectionClearEvent xselectionclear() {
      return nxselectionclear(this.address());
   }

   public XSelectionRequestEvent xselectionrequest() {
      return nxselectionrequest(this.address());
   }

   public XSelectionEvent xselection() {
      return nxselection(this.address());
   }

   public XColormapEvent xcolormap() {
      return nxcolormap(this.address());
   }

   public XClientMessageEvent xclient() {
      return nxclient(this.address());
   }

   public XMappingEvent xmapping() {
      return nxmapping(this.address());
   }

   public XErrorEvent xerror() {
      return nxerror(this.address());
   }

   public XKeymapEvent xkeymap() {
      return nxkeymap(this.address());
   }

   public XGenericEvent xgeneric() {
      return nxgeneric(this.address());
   }

   public XGenericEventCookie xcookie() {
      return nxcookie(this.address());
   }

   public static XEvent malloc() {
      return wrap(XEvent.class, MemoryUtil.nmemAllocChecked(SIZEOF));
   }

   public static XEvent calloc() {
      return wrap(XEvent.class, MemoryUtil.nmemCallocChecked(1L, SIZEOF));
   }

   public static XEvent create() {
      ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
      return wrap(XEvent.class, MemoryUtil.memAddress(container), container);
   }

   public static XEvent create(long address) {
      return wrap(XEvent.class, address);
   }

   @Nullable
   public static XEvent createSafe(long address) {
      return address == 0L ? null : wrap(XEvent.class, address);
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

   @Deprecated
   public static XEvent mallocStack() {
      return malloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XEvent callocStack() {
      return calloc(MemoryStack.stackGet());
   }

   @Deprecated
   public static XEvent mallocStack(MemoryStack stack) {
      return malloc(stack);
   }

   @Deprecated
   public static XEvent callocStack(MemoryStack stack) {
      return calloc(stack);
   }

   @Deprecated
   public static Buffer mallocStack(int capacity) {
      return malloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static Buffer callocStack(int capacity) {
      return calloc(capacity, MemoryStack.stackGet());
   }

   @Deprecated
   public static Buffer mallocStack(int capacity, MemoryStack stack) {
      return malloc(capacity, stack);
   }

   @Deprecated
   public static Buffer callocStack(int capacity, MemoryStack stack) {
      return calloc(capacity, stack);
   }

   public static XEvent malloc(MemoryStack stack) {
      return wrap(XEvent.class, stack.nmalloc(ALIGNOF, SIZEOF));
   }

   public static XEvent calloc(MemoryStack stack) {
      return wrap(XEvent.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
   }

   public static Buffer malloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
   }

   public static Buffer calloc(int capacity, MemoryStack stack) {
      return wrap(Buffer.class, stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
   }

   public static int ntype(long struct) {
      return UNSAFE.getInt(null, struct + TYPE);
   }

   public static XAnyEvent nxany(long struct) {
      return XAnyEvent.create(struct + XANY);
   }

   public static XKeyEvent nxkey(long struct) {
      return XKeyEvent.create(struct + XKEY);
   }

   public static XButtonEvent nxbutton(long struct) {
      return XButtonEvent.create(struct + XBUTTON);
   }

   public static XMotionEvent nxmotion(long struct) {
      return XMotionEvent.create(struct + XMOTION);
   }

   public static XCrossingEvent nxcrossing(long struct) {
      return XCrossingEvent.create(struct + XCROSSING);
   }

   public static XFocusChangeEvent nxfocus(long struct) {
      return XFocusChangeEvent.create(struct + XFOCUS);
   }

   public static XExposeEvent nxexpose(long struct) {
      return XExposeEvent.create(struct + XEXPOSE);
   }

   public static XGraphicsExposeEvent nxgraphicsexpose(long struct) {
      return XGraphicsExposeEvent.create(struct + XGRAPHICSEXPOSE);
   }

   public static XNoExposeEvent nxnoexpose(long struct) {
      return XNoExposeEvent.create(struct + XNOEXPOSE);
   }

   public static XVisibilityEvent nxvisibility(long struct) {
      return XVisibilityEvent.create(struct + XVISIBILITY);
   }

   public static XCreateWindowEvent nxcreatewindow(long struct) {
      return XCreateWindowEvent.create(struct + XCREATEWINDOW);
   }

   public static XDestroyWindowEvent nxdestroywindow(long struct) {
      return XDestroyWindowEvent.create(struct + XDESTROYWINDOW);
   }

   public static XUnmapEvent nxunmap(long struct) {
      return XUnmapEvent.create(struct + XUNMAP);
   }

   public static XMapEvent nxmap(long struct) {
      return XMapEvent.create(struct + XMAP);
   }

   public static XMapRequestEvent nxmaprequest(long struct) {
      return XMapRequestEvent.create(struct + XMAPREQUEST);
   }

   public static XReparentEvent nxreparent(long struct) {
      return XReparentEvent.create(struct + XREPARENT);
   }

   public static XConfigureEvent nxconfigure(long struct) {
      return XConfigureEvent.create(struct + XCONFIGURE);
   }

   public static XGravityEvent nxgravity(long struct) {
      return XGravityEvent.create(struct + XGRAVITY);
   }

   public static XResizeRequestEvent nxresizerequest(long struct) {
      return XResizeRequestEvent.create(struct + XRESIZEREQUEST);
   }

   public static XConfigureRequestEvent nxconfigurerequest(long struct) {
      return XConfigureRequestEvent.create(struct + XCONFIGUREREQUEST);
   }

   public static XCirculateEvent nxcirculate(long struct) {
      return XCirculateEvent.create(struct + XCIRCULATE);
   }

   public static XCirculateRequestEvent nxcirculaterequest(long struct) {
      return XCirculateRequestEvent.create(struct + XCIRCULATEREQUEST);
   }

   public static XPropertyEvent nxproperty(long struct) {
      return XPropertyEvent.create(struct + XPROPERTY);
   }

   public static XSelectionClearEvent nxselectionclear(long struct) {
      return XSelectionClearEvent.create(struct + XSELECTIONCLEAR);
   }

   public static XSelectionRequestEvent nxselectionrequest(long struct) {
      return XSelectionRequestEvent.create(struct + XSELECTIONREQUEST);
   }

   public static XSelectionEvent nxselection(long struct) {
      return XSelectionEvent.create(struct + XSELECTION);
   }

   public static XColormapEvent nxcolormap(long struct) {
      return XColormapEvent.create(struct + XCOLORMAP);
   }

   public static XClientMessageEvent nxclient(long struct) {
      return XClientMessageEvent.create(struct + XCLIENT);
   }

   public static XMappingEvent nxmapping(long struct) {
      return XMappingEvent.create(struct + XMAPPING);
   }

   public static XErrorEvent nxerror(long struct) {
      return XErrorEvent.create(struct + XERROR);
   }

   public static XKeymapEvent nxkeymap(long struct) {
      return XKeymapEvent.create(struct + XKEYMAP);
   }

   public static XGenericEvent nxgeneric(long struct) {
      return XGenericEvent.create(struct + XGENERIC);
   }

   public static XGenericEventCookie nxcookie(long struct) {
      return XGenericEventCookie.create(struct + XCOOKIE);
   }

   static {
      Layout layout = __union(
         __member(4),
         __member(XAnyEvent.SIZEOF, XAnyEvent.ALIGNOF),
         __member(XKeyEvent.SIZEOF, XKeyEvent.ALIGNOF),
         __member(XButtonEvent.SIZEOF, XButtonEvent.ALIGNOF),
         __member(XMotionEvent.SIZEOF, XMotionEvent.ALIGNOF),
         __member(XCrossingEvent.SIZEOF, XCrossingEvent.ALIGNOF),
         __member(XFocusChangeEvent.SIZEOF, XFocusChangeEvent.ALIGNOF),
         __member(XExposeEvent.SIZEOF, XExposeEvent.ALIGNOF),
         __member(XGraphicsExposeEvent.SIZEOF, XGraphicsExposeEvent.ALIGNOF),
         __member(XNoExposeEvent.SIZEOF, XNoExposeEvent.ALIGNOF),
         __member(XVisibilityEvent.SIZEOF, XVisibilityEvent.ALIGNOF),
         __member(XCreateWindowEvent.SIZEOF, XCreateWindowEvent.ALIGNOF),
         __member(XDestroyWindowEvent.SIZEOF, XDestroyWindowEvent.ALIGNOF),
         __member(XUnmapEvent.SIZEOF, XUnmapEvent.ALIGNOF),
         __member(XMapEvent.SIZEOF, XMapEvent.ALIGNOF),
         __member(XMapRequestEvent.SIZEOF, XMapRequestEvent.ALIGNOF),
         __member(XReparentEvent.SIZEOF, XReparentEvent.ALIGNOF),
         __member(XConfigureEvent.SIZEOF, XConfigureEvent.ALIGNOF),
         __member(XGravityEvent.SIZEOF, XGravityEvent.ALIGNOF),
         __member(XResizeRequestEvent.SIZEOF, XResizeRequestEvent.ALIGNOF),
         __member(XConfigureRequestEvent.SIZEOF, XConfigureRequestEvent.ALIGNOF),
         __member(XCirculateEvent.SIZEOF, XCirculateEvent.ALIGNOF),
         __member(XCirculateRequestEvent.SIZEOF, XCirculateRequestEvent.ALIGNOF),
         __member(XPropertyEvent.SIZEOF, XPropertyEvent.ALIGNOF),
         __member(XSelectionClearEvent.SIZEOF, XSelectionClearEvent.ALIGNOF),
         __member(XSelectionRequestEvent.SIZEOF, XSelectionRequestEvent.ALIGNOF),
         __member(XSelectionEvent.SIZEOF, XSelectionEvent.ALIGNOF),
         __member(XColormapEvent.SIZEOF, XColormapEvent.ALIGNOF),
         __member(XClientMessageEvent.SIZEOF, XClientMessageEvent.ALIGNOF),
         __member(XMappingEvent.SIZEOF, XMappingEvent.ALIGNOF),
         __member(XErrorEvent.SIZEOF, XErrorEvent.ALIGNOF),
         __member(XKeymapEvent.SIZEOF, XKeymapEvent.ALIGNOF),
         __member(XGenericEvent.SIZEOF, XGenericEvent.ALIGNOF),
         __member(XGenericEventCookie.SIZEOF, XGenericEventCookie.ALIGNOF),
         __padding(24, CLONG_SIZE, true)
      );
      SIZEOF = layout.getSize();
      ALIGNOF = layout.getAlignment();
      TYPE = layout.offsetof(0);
      XANY = layout.offsetof(1);
      XKEY = layout.offsetof(2);
      XBUTTON = layout.offsetof(3);
      XMOTION = layout.offsetof(4);
      XCROSSING = layout.offsetof(5);
      XFOCUS = layout.offsetof(6);
      XEXPOSE = layout.offsetof(7);
      XGRAPHICSEXPOSE = layout.offsetof(8);
      XNOEXPOSE = layout.offsetof(9);
      XVISIBILITY = layout.offsetof(10);
      XCREATEWINDOW = layout.offsetof(11);
      XDESTROYWINDOW = layout.offsetof(12);
      XUNMAP = layout.offsetof(13);
      XMAP = layout.offsetof(14);
      XMAPREQUEST = layout.offsetof(15);
      XREPARENT = layout.offsetof(16);
      XCONFIGURE = layout.offsetof(17);
      XGRAVITY = layout.offsetof(18);
      XRESIZEREQUEST = layout.offsetof(19);
      XCONFIGUREREQUEST = layout.offsetof(20);
      XCIRCULATE = layout.offsetof(21);
      XCIRCULATEREQUEST = layout.offsetof(22);
      XPROPERTY = layout.offsetof(23);
      XSELECTIONCLEAR = layout.offsetof(24);
      XSELECTIONREQUEST = layout.offsetof(25);
      XSELECTION = layout.offsetof(26);
      XCOLORMAP = layout.offsetof(27);
      XCLIENT = layout.offsetof(28);
      XMAPPING = layout.offsetof(29);
      XERROR = layout.offsetof(30);
      XKEYMAP = layout.offsetof(31);
      XGENERIC = layout.offsetof(32);
      XCOOKIE = layout.offsetof(33);
   }

   public static class Buffer extends StructBuffer<XEvent, Buffer> implements NativeResource {
      private static final XEvent ELEMENT_FACTORY = XEvent.create(-1L);

      public Buffer(ByteBuffer container) {
         super(container, container.remaining() / XEvent.SIZEOF);
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

      protected XEvent getElementFactory() {
         return ELEMENT_FACTORY;
      }

      public int type() {
         return XEvent.ntype(this.address());
      }

      public XAnyEvent xany() {
         return XEvent.nxany(this.address());
      }

      public XKeyEvent xkey() {
         return XEvent.nxkey(this.address());
      }

      public XButtonEvent xbutton() {
         return XEvent.nxbutton(this.address());
      }

      public XMotionEvent xmotion() {
         return XEvent.nxmotion(this.address());
      }

      public XCrossingEvent xcrossing() {
         return XEvent.nxcrossing(this.address());
      }

      public XFocusChangeEvent xfocus() {
         return XEvent.nxfocus(this.address());
      }

      public XExposeEvent xexpose() {
         return XEvent.nxexpose(this.address());
      }

      public XGraphicsExposeEvent xgraphicsexpose() {
         return XEvent.nxgraphicsexpose(this.address());
      }

      public XNoExposeEvent xnoexpose() {
         return XEvent.nxnoexpose(this.address());
      }

      public XVisibilityEvent xvisibility() {
         return XEvent.nxvisibility(this.address());
      }

      public XCreateWindowEvent xcreatewindow() {
         return XEvent.nxcreatewindow(this.address());
      }

      public XDestroyWindowEvent xdestroywindow() {
         return XEvent.nxdestroywindow(this.address());
      }

      public XUnmapEvent xunmap() {
         return XEvent.nxunmap(this.address());
      }

      public XMapEvent xmap() {
         return XEvent.nxmap(this.address());
      }

      public XMapRequestEvent xmaprequest() {
         return XEvent.nxmaprequest(this.address());
      }

      public XReparentEvent xreparent() {
         return XEvent.nxreparent(this.address());
      }

      public XConfigureEvent xconfigure() {
         return XEvent.nxconfigure(this.address());
      }

      public XGravityEvent xgravity() {
         return XEvent.nxgravity(this.address());
      }

      public XResizeRequestEvent xresizerequest() {
         return XEvent.nxresizerequest(this.address());
      }

      public XConfigureRequestEvent xconfigurerequest() {
         return XEvent.nxconfigurerequest(this.address());
      }

      public XCirculateEvent xcirculate() {
         return XEvent.nxcirculate(this.address());
      }

      public XCirculateRequestEvent xcirculaterequest() {
         return XEvent.nxcirculaterequest(this.address());
      }

      public XPropertyEvent xproperty() {
         return XEvent.nxproperty(this.address());
      }

      public XSelectionClearEvent xselectionclear() {
         return XEvent.nxselectionclear(this.address());
      }

      public XSelectionRequestEvent xselectionrequest() {
         return XEvent.nxselectionrequest(this.address());
      }

      public XSelectionEvent xselection() {
         return XEvent.nxselection(this.address());
      }

      public XColormapEvent xcolormap() {
         return XEvent.nxcolormap(this.address());
      }

      public XClientMessageEvent xclient() {
         return XEvent.nxclient(this.address());
      }

      public XMappingEvent xmapping() {
         return XEvent.nxmapping(this.address());
      }

      public XErrorEvent xerror() {
         return XEvent.nxerror(this.address());
      }

      public XKeymapEvent xkeymap() {
         return XEvent.nxkeymap(this.address());
      }

      public XGenericEvent xgeneric() {
         return XEvent.nxgeneric(this.address());
      }

      public XGenericEventCookie xcookie() {
         return XEvent.nxcookie(this.address());
      }
   }
}
