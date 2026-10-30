package org.lwjgl.opengl;

import java.awt.Canvas;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.Sys;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWCursorEnterCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowFocusCallback;
import org.lwjgl.glfw.GLFWWindowIconifyCallback;
import org.lwjgl.glfw.GLFWWindowPosCallback;
import org.lwjgl.glfw.GLFWWindowRefreshCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Display {
   private static final long polls = System.currentTimeMillis();
   private static String windowTitle = "Moon Client";
   private static GLContext context;
   private static DisplayImplementation display_impl;
   private static boolean displayCreated = false;
   private static boolean displayFocused = true;
   private static boolean displayVisible = true;
   private static boolean displayDirty = false;
   private static boolean displayResizable = false;
   private static DisplayMode mode;
   private static DisplayMode desktopDisplayMode;
   private static int latestEventKey = 0;
   private static int displayX = -1;
   private static int displayY = -1;
   private static boolean displayResized = false;
   private static int displayWidth = 854;
   private static int displayHeight = 480;
   private static int displayFramebufferWidth = 0;
   private static int displayFramebufferHeight = 0;
   private static boolean latestResized = false;
   private static int latestWidth = 0;
   private static int latestHeight = 0;
   private static boolean vsyncEnabled = false;
   private static boolean displayFullscreen = false;
   private static float fps;
   private static boolean window_created;
   private static DrawableLWJGL drawable;
   private static Canvas parent;
   private static GLFWImage.Buffer icons;
   private static int swap_interval;
   private static boolean isCreated = false;

   public static void setSwapInterval(int value) {
      synchronized (GlobalLock.lock) {
         swap_interval = value;
         if (isCreated()) {
            drawable.setSwapInterval(swap_interval);
         }
      }
   }

   private static void makeCurrentAndSetSwapInterval() throws LWJGLException {
      makeCurrent();

      try {
         drawable.checkGLError();
      } catch (OpenGLException e) {
         LWJGLUtil.log("OpenGL error during context creation: " + e.getMessage());
      }

      setSwapInterval(swap_interval);
   }

   private static void initContext() {
      drawable.initContext(0.0F, 0.0F, 0.0F);
      update();
   }

   private static void initControls() {
      if (!Mouse.isCreated()) {
         try {
            Mouse.create();
         } catch (LWJGLException e) {
            if (LWJGLUtil.DEBUG) {
               e.printStackTrace(System.err);
            } else {
               LWJGLUtil.log("Failed to create Mouse: " + e);
            }
         }
      }

      if (!Keyboard.isCreated()) {
         try {
            Keyboard.create();
         } catch (LWJGLException e) {
            if (LWJGLUtil.DEBUG) {
               e.printStackTrace(System.err);
            } else {
               LWJGLUtil.log("Failed to create Keyboard: " + e);
            }
         }
      }
   }

   private static void releaseDrawable() {
      try {
         Context context = drawable.getContext();
         if (context != null && context.isCurrent()) {
            context.releaseCurrent();
            context.releaseDrawable();
         }
      } catch (LWJGLException e) {
         LWJGLUtil.log("Exception occurred while trying to release context: " + e);
      }
   }

   private static void destroyWindow() {
      if (window_created) {
         releaseDrawable();
         if (Mouse.isCreated()) {
            Mouse.destroy();
         }

         if (Keyboard.isCreated()) {
            Keyboard.destroy();
         }

         display_impl.destroyWindow();
         window_created = false;
      }
   }

   private static void reset() {
      display_impl.resetDisplayMode();
   }

   private static void createWindow() throws LWJGLException {
      if (!window_created) {
         DisplayMode mode = getDisplayMode();
         display_impl.createWindow(drawable, mode, null, 0, 0);
         window_created = true;
         displayWidth = mode.getWidth();
         displayHeight = mode.getHeight();
         initControls();
         setIcon(new ByteBuffer[]{LWJGLUtil.LWJGLIcon32x32, LWJGLUtil.LWJGLIcon16x16});
      }
   }

   public static void create(PixelFormat pixel_format, Drawable shared_drawable) throws LWJGLException {
      create(pixel_format);

      try {
         drawable.setPixelFormat(pixel_format, null);

         try {
            createWindow();

            try {
               ((DrawableGL)drawable).context = new ContextGL(
                  ((DrawableGL)drawable).peer_info, null, shared_drawable != null ? ((DrawableGL)shared_drawable).getContext() : null
               );

               try {
                  makeCurrentAndSetSwapInterval();
                  initContext();
               } catch (LWJGLException e) {
                  throw e;
               }
            } catch (LWJGLException e) {
               destroyWindow();
               throw e;
            }
         } catch (LWJGLException e) {
            drawable.destroy();
            throw e;
         }
      } catch (LWJGLException e) {
         display_impl.resetDisplayMode();
         throw e;
      }
   }

   public static void create(PixelFormat pixel_format, ContextAttribs attribs) throws LWJGLException {
      create(pixel_format);
   }

   public static void create(PixelFormat format) throws LWJGLException {
      GLFW.glfwWindowHint(135178, format.getAccumulationBitsPerPixel());
      GLFW.glfwWindowHint(135172, format.getAlphaBits());
      GLFW.glfwWindowHint(135179, format.getAuxBuffers());
      GLFW.glfwWindowHint(135173, format.getDepthBits());
      GLFW.glfwWindowHint(135181, format.getSamples());
      GLFW.glfwWindowHint(135174, format.getStencilBits());
      create();
   }

   public static void create() throws LWJGLException {
      if (!isCreated) {
         isCreated = true;
         if (Window.handle != 0L) {
            GLFW.glfwDestroyWindow(Window.handle);
         }

         long monitor = GLFW.glfwGetPrimaryMonitor();
         GLFWVidMode vidmode = GLFW.glfwGetVideoMode(monitor);
         int monitorWidth = vidmode.width();
         int monitorHeight = vidmode.height();
         int monitorBitPerPixel = vidmode.redBits() + vidmode.greenBits() + vidmode.blueBits();
         int monitorRefreshRate = vidmode.refreshRate();
         desktopDisplayMode = new DisplayMode(monitorWidth, monitorHeight, monitorBitPerPixel, monitorRefreshRate);
         GLFW.glfwDefaultWindowHints();
         GLFW.glfwWindowHint(131076, 0);
         GLFW.glfwWindowHint(131075, displayResizable ? 1 : 0);
         GLFW.glfwWindowHint(139271, 1);
         GLFW.glfwWindowHint(143361, 0);
         GLFW.glfwWindowHint(208901, 1);
         Window.handle = GLFW.glfwCreateWindow(854, 480, windowTitle, 0L, 0L);
         if (Window.handle == 0L) {
            throw new LWJGLException("Failed to create Display window");
         }

         int[] monitorX = new int[1];
         int[] monitorY = new int[1];
         GLFW.glfwGetMonitorPos(monitor, monitorX, monitorY);
         Window.keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
               Display.latestEventKey = key;
               Keyboard.addKeyEvent(key, action);
            }
         };
         Window.charCallback = new GLFWCharCallback() {
            @Override
            public void invoke(long window, int codepoint) {
               Keyboard.addCharEvent(Display.latestEventKey, (char)codepoint);
            }
         };
         Window.cursorEnterCallback = new GLFWCursorEnterCallback() {
            @Override
            public void invoke(long window, boolean entered) {
               Mouse.setMouseInsideWindow(entered);
            }
         };
         Window.cursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double x, double y) {
               Mouse.addMoveEvent(x, y);
            }
         };
         Window.mouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
               Mouse.addButtonEvent(button, action == 1);
            }
         };
         Window.windowFocusCallback = new GLFWWindowFocusCallback() {
            @Override
            public void invoke(long window, boolean focused) {
               Display.displayFocused = focused;
            }
         };
         Window.windowIconifyCallback = new GLFWWindowIconifyCallback() {
            @Override
            public void invoke(long window, boolean iconified) {
               Display.displayVisible = !iconified;
            }
         };
         Window.windowSizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
               Display.latestResized = true;
               Display.latestWidth = width;
               Display.latestHeight = height;
            }
         };
         Window.windowPosCallback = new GLFWWindowPosCallback() {
            @Override
            public void invoke(long window, int xpos, int ypos) {
               Display.displayX = xpos;
               Display.displayY = ypos;
            }
         };
         Window.windowRefreshCallback = new GLFWWindowRefreshCallback() {
            @Override
            public void invoke(long window) {
               Display.displayDirty = true;
            }
         };
         Window.framebufferSizeCallback = new GLFWFramebufferSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
               Display.displayFramebufferWidth = width;
               Display.displayFramebufferHeight = height;
            }
         };
         Window.scrollCallback = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
               Mouse.addWheelEvent((int)(yoffset * 120.0));
            }
         };
         Window.setCallbacks();
         displayWidth = mode.getWidth();
         displayHeight = mode.getHeight();
         IntBuffer fbw = BufferUtils.createIntBuffer(1);
         IntBuffer fbh = BufferUtils.createIntBuffer(1);
         GLFW.glfwGetFramebufferSize(Window.handle, fbw, fbh);
         displayFramebufferWidth = fbw.get(0);
         displayFramebufferHeight = fbh.get(0);
         GLFW.glfwSetWindowPos(Window.handle, monitorX[0] + (monitorWidth - mode.getWidth()) / 2, monitorY[0] + (monitorHeight - mode.getHeight()) / 2);
         if (displayX == -1) {
            displayX = (monitorWidth - mode.getWidth()) / 2;
         }

         if (displayY == -1) {
            displayY = (monitorHeight - mode.getHeight()) / 2;
         }

         DrawableGL drawable = new DrawableGL() {
            @Override
            public void destroy() {
               synchronized (GlobalLock.lock) {
                  if (Display.isCreated()) {
                     Display.releaseDrawable();
                     super.destroy();
                     Display.destroyWindow();
                     Display.reset();
                  }
               }
            }
         };
         drawable.context = new ContextGL(null, null, null);
         drawable.context.makeCurrent();
         Display.drawable = drawable;
         context = GLContext.createFromCurrent();
         GLFW.glfwSwapInterval(0);
         GLFW.glfwShowWindow(Window.handle);
         Mouse.create();
         Keyboard.create();
         display_impl = new DisplayImplementation() {
            @Override
            public void setNativeCursor(Object handle) throws LWJGLException {
               try {
                  Mouse.setNativeCursor((Cursor)handle);
               } catch (ClassCastException e) {
                  throw new LWJGLException("Handle is not an instance of cursor");
               }
            }

            @Override
            public void setCursorPosition(int x, int y) {
               Mouse.setCursorPosition(x, y);
            }

            @Override
            public void readMouse(ByteBuffer buffer) {
            }

            @Override
            public void readKeyboard(ByteBuffer buffer) {
            }

            @Override
            public void pollMouse(IntBuffer coord_buffer, ByteBuffer buttons) {
            }

            @Override
            public void pollKeyboard(ByteBuffer keyDownBuffer) {
            }

            @Override
            public boolean isInsideWindow() {
               return false;
            }

            @Override
            public boolean hasWheel() {
               return false;
            }

            @Override
            public void grabMouse(boolean grab) {
               Mouse.setGrabbed(grab);
            }

            @Override
            public int getNativeCursorCapabilities() {
               return 0;
            }

            @Override
            public int getMinCursorSize() {
               return 0;
            }

            @Override
            public int getMaxCursorSize() {
               return 0;
            }

            @Override
            public int getButtonCount() {
               return 0;
            }

            @Override
            public void destroyMouse() {
            }

            @Override
            public void destroyKeyboard() {
            }

            @Override
            public void destroyCursor(Object cursor_handle) {
            }

            @Override
            public void createMouse() throws LWJGLException {
            }

            @Override
            public void createKeyboard() throws LWJGLException {
            }

            @Override
            public Object createCursor(int width, int height, int xHotspot, int yHotspot, int numImages, IntBuffer images, IntBuffer delays) throws LWJGLException {
               return null;
            }

            @Override
            public boolean wasResized() {
               return false;
            }

            @Override
            public void update() {
               Display.update();
            }

            @Override
            public void switchDisplayMode(DisplayMode mode) throws LWJGLException {
               Display.setDisplayMode(mode);
            }

            @Override
            public void setTitle(String title) {
               Display.windowTitle = title;
            }

            @Override
            public void setResizable(boolean resizable) {
               Display.setResizable(resizable);
            }

            @Override
            public void setPbufferAttrib(PeerInfo handle, int attrib, int value) {
            }

            @Override
            public int setIcon(ByteBuffer[] icons) {
               Display.setIcon(icons);
               return 0;
            }

            @Override
            public void setGammaRamp(FloatBuffer gammaRamp) throws LWJGLException {
            }

            @Override
            public void reshape(int x, int y, int width, int height) {
            }

            @Override
            public void resetDisplayMode() {
               try {
                  Display.setDisplayMode(Display.desktopDisplayMode);
               } catch (LWJGLException var2) {
               }
            }

            @Override
            public void releaseTexImageFromPbuffer(PeerInfo handle, int buffer) {
            }

            @Override
            public boolean isVisible() {
               return Display.displayVisible;
            }

            @Override
            public boolean isDirty() {
               return Display.displayDirty;
            }

            @Override
            public boolean isCloseRequested() {
               return GLFW.glfwWindowShouldClose(Window.handle);
            }

            @Override
            public boolean isBufferLost(PeerInfo handle) {
               return false;
            }

            @Override
            public boolean isActive() {
               return Display.displayFocused;
            }

            @Override
            public DisplayMode init() throws LWJGLException {
               return Display.desktopDisplayMode;
            }

            @Override
            public int getY() {
               return Display.displayY;
            }

            @Override
            public int getX() {
               return Display.displayX;
            }

            @Override
            public int getWidth() {
               return Display.latestWidth;
            }

            @Override
            public String getVersion() {
               return Sys.getVersion();
            }

            @Override
            public float getPixelScaleFactor() {
               return 0.0F;
            }

            @Override
            public int getPbufferCapabilities() {
               return 0;
            }

            @Override
            public int getHeight() {
               return Display.latestHeight;
            }

            @Override
            public int getGammaRampLength() {
               return 0;
            }

            @Override
            public DisplayMode[] getAvailableDisplayModes() throws LWJGLException {
               return Display.getAvailableDisplayModes();
            }

            @Override
            public String getAdapter() {
               return Display.getAdapter();
            }

            @Override
            public void destroyWindow() {
               Display.destroy();
            }

            @Override
            public void createWindow(DrawableLWJGL drawable, DisplayMode mode, Canvas parent, int x, int y) throws LWJGLException {
            }

            @Override
            public PeerInfo createPeerInfo(PixelFormat pixel_format, ContextAttribs attribs) throws LWJGLException {
               return null;
            }

            @Override
            public PeerInfo createPbuffer(
               int width, int height, PixelFormat pixel_format, ContextAttribs attribs, IntBuffer pixelFormatCaps, IntBuffer pBufferAttribs
            ) throws LWJGLException {
               return null;
            }

            @Override
            public void bindTexImageToPbuffer(PeerInfo handle, int buffer) {
            }
         };
         displayCreated = true;
      }
   }

   public static boolean isCreated() {
      return displayCreated;
   }

   public static boolean isActive() {
      return displayFocused;
   }

   public static boolean isVisible() {
      return displayVisible;
   }

   public static GLContext getContext() {
      return context;
   }

   public static void setLocation(int new_x, int new_y) {
      if (Window.handle == 0L) {
         displayX = new_x;
         displayY = new_y;
      } else {
         GLFW.glfwSetWindowPos(Window.handle, new_x, new_y);
      }
   }

   public static void setVSyncEnabled(boolean sync) {
      vsyncEnabled = sync;
      GLFW.glfwSwapInterval(vsyncEnabled ? 1 : 0);
   }

   public static long getWindow() {
      return Window.handle;
   }

   public static void update() {
      update(true);
   }

   public static void update(boolean processMessages) {
      try {
         swapBuffers();
         displayDirty = false;
      } catch (LWJGLException e) {
         throw new RuntimeException(e);
      }

      if (processMessages) {
         processMessages();
      }
   }

   public static void processMessages() {
      GLFW.glfwPollEvents();
      Keyboard.poll();
      Mouse.poll();
      if (latestResized) {
         latestResized = false;
         displayResized = true;
         displayWidth = latestWidth;
         displayHeight = latestHeight;
      } else {
         displayResized = false;
      }
   }

   public static Canvas getParent() {
      return parent;
   }

   public static void setParent(Canvas parent) throws LWJGLException {
      if (Display.parent != parent) {
         Display.parent = parent;
      }
   }

   public static void swapBuffers() throws LWJGLException {
      GLFW.glfwSwapBuffers(Window.handle);
   }

   public static void destroy() {
      Window.releaseCallbacks();
      GLFW.glfwDestroyWindow(Window.handle);
      displayCreated = false;
   }

   public static DisplayMode getDisplayMode() {
      return mode;
   }

   public static void setDisplayMode(DisplayMode dm) throws LWJGLException {
      mode = dm;
   }

   public static DisplayMode[] getAvailableDisplayModes() throws LWJGLException {
      GLFWVidMode.Buffer modes = GLFW.glfwGetVideoModes(GLFW.glfwGetPrimaryMonitor());
      DisplayMode[] displayModes = new DisplayMode[modes.capacity()];

      for (int i = 0; i < modes.capacity(); i++) {
         modes.position(i);
         int w = modes.width();
         int h = modes.height();
         int b = modes.redBits() + modes.greenBits() + modes.blueBits();
         int r = modes.refreshRate();
         displayModes[i] = new DisplayMode(w, h, b, r);
      }

      return displayModes;
   }

   public static DisplayMode getDesktopDisplayMode() {
      long mon = GLFW.glfwGetPrimaryMonitor();
      GLFWVidMode mode = GLFW.glfwGetVideoMode(mon);
      return new DisplayMode(mode.width(), mode.height(), mode.redBits() + mode.greenBits() + mode.blueBits(), mode.refreshRate());
   }

   public static boolean wasResized() {
      return displayResized;
   }

   public static int getX() {
      return displayX;
   }

   public static int getY() {
      return displayY;
   }

   public static int getWidth() {
      return displayWidth;
   }

   public static int getHeight() {
      return displayHeight;
   }

   public static int getFramebufferWidth() {
      return displayFramebufferWidth;
   }

   public static int getFramebufferHeight() {
      return displayFramebufferHeight;
   }

   public static void setTitle(String title) {
      windowTitle = title;
      if (Window.handle != 0L) {
         GLFW.glfwSetWindowTitle(getWindow(), windowTitle);
      }
   }

   public static String getTitle() {
      return windowTitle;
   }

   public static boolean isCloseRequested() {
      return GLFW.glfwWindowShouldClose(Window.handle);
   }

   public static boolean isDirty() {
      return displayDirty;
   }

   public static void setInitialBackground(float red, float green, float blue) {
      if (Window.handle != 0L) {
         GL11.glClearColor(red, green, blue, 1.0F);
         GL11.glClear(16384);
      }
   }

   public static int setIcon(ByteBuffer[] icons) {
      try {
         if (Window.handle == 0L) {
            Display.icons = new GLFWImage.Buffer(icons[1]);
         } else {
            GLFW.glfwSetWindowIcon(Window.handle, new GLFWImage.Buffer(icons[0]));
         }
      } catch (NullPointerException e) {
         LWJGLUtil.log("Couldn't set icon");
         e.printStackTrace();
      }

      return 0;
   }

   public static boolean isResizable() {
      return displayResizable;
   }

   public static void setResizable(boolean resizable) {
      displayResizable = resizable;
      if (displayResizable ^ resizable && Window.handle != 0L) {
         IntBuffer width = BufferUtils.createIntBuffer(1);
         IntBuffer height = BufferUtils.createIntBuffer(1);
         GLFW.glfwGetWindowSize(Window.handle, width, height);
         width.rewind();
         height.rewind();
         GLFW.glfwDefaultWindowHints();
         GLFW.glfwWindowHint(131076, displayVisible ? 1 : 0);
         GLFW.glfwWindowHint(131075, displayResizable ? 1 : 0);
         GLFW.glfwWindowHint(139271, 1);
         GLFW.glfwSetWindowSize(Window.handle, width.get(), height.get());
      }

      displayResizable = resizable;
   }

   public static void setDisplayModeAndFullscreen(DisplayMode mode) throws LWJGLException {
      Display.mode = mode;
      newCurrentWindow(
         GLFW.glfwCreateWindow(mode.getWidth(), mode.getHeight(), windowTitle, mode.isFullscreenCapable() ? GLFW.glfwGetPrimaryMonitor() : 0L, 0L)
      );
   }

   public static boolean isFullscreen() {
      return displayFullscreen;
   }

   public static void setFullscreen(boolean fullscreen) throws LWJGLException {
      if (isFullscreen() || fullscreen) {
         DisplayMode currentMode = getDisplayMode();
         int windowPosX = fullscreen ? 0 : desktopDisplayMode.getWidth() / 2 - currentMode.getWidth() / 2;
         int windowPosZ = fullscreen ? 0 : desktopDisplayMode.getHeight() / 2 - currentMode.getHeight() / 2;
         GLFW.glfwSetWindowMonitor(
            getWindow(),
            fullscreen ? GLFW.glfwGetPrimaryMonitor() : 0L,
            windowPosX,
            windowPosZ,
            currentMode.getWidth(),
            currentMode.getHeight(),
            currentMode.getFrequency()
         );
         displayFullscreen = fullscreen;
      }
   }

   public static void releaseContext() throws LWJGLException {
      GLFW.glfwMakeContextCurrent(0L);
   }

   public static boolean isCurrent() throws LWJGLException {
      return GLFW.glfwGetCurrentContext() == Window.handle;
   }

   public static void makeCurrent() throws LWJGLException {
      if (!isCurrent()) {
      }
   }

   public static String getAdapter() {
      return "GeNotSupportedAdapter";
   }

   public static String getVersion() {
      return "1.0 NOT SUPPORTED";
   }

   public static void sync(int fps) {
      Sync.sync(fps);
   }

   public static Drawable getDrawable() {
      return drawable;
   }

   static DisplayImplementation getImplementation() {
      return display_impl;
   }

   private static void newCurrentWindow(long newWindow) {
      if (Window.handle != 0L) {
         GLFW.glfwDestroyWindow(Window.handle);
      }

      Window.handle = newWindow;

      try {
         Mouse.setNativeCursor(Mouse.getCurrentCursor());
      } catch (LWJGLException e) {
         System.err.println("Failed to set new window cursor!");
         e.printStackTrace();
      }

      GLFW.glfwSetWindowTitle(newWindow, windowTitle);
      Window.setCallbacks();
      context = GLContext.createFromCurrent();
      GLFW.glfwSwapInterval(0);
      GLFW.glfwShowWindow(Window.handle);
   }

   static {
      Sys.initialize();
      long monitor = GLFW.glfwGetPrimaryMonitor();
      GLFWVidMode vidmode = GLFW.glfwGetVideoMode(monitor);
      int monitorWidth = displayWidth = displayFramebufferWidth = vidmode.width();
      int monitorHeight = displayHeight = displayFramebufferHeight = vidmode.height();
      displayX = monitorWidth / 2 - 427;
      displayY = monitorHeight / 2 - 240;
   }

   static class Window {
      static long handle;
      static GLFWKeyCallback keyCallback;
      static GLFWCharCallback charCallback;
      static GLFWCursorEnterCallback cursorEnterCallback;
      static GLFWCursorPosCallback cursorPosCallback;
      static GLFWMouseButtonCallback mouseButtonCallback;
      static GLFWWindowFocusCallback windowFocusCallback;
      static GLFWWindowIconifyCallback windowIconifyCallback;
      static GLFWWindowSizeCallback windowSizeCallback;
      static GLFWWindowPosCallback windowPosCallback;
      static GLFWWindowRefreshCallback windowRefreshCallback;
      static GLFWFramebufferSizeCallback framebufferSizeCallback;
      static GLFWScrollCallback scrollCallback;

      public static void setCallbacks() {
         GLFW.glfwSetKeyCallback(handle, keyCallback);
         GLFW.glfwSetCharCallback(handle, charCallback);
         GLFW.glfwSetCursorEnterCallback(handle, cursorEnterCallback);
         GLFW.glfwSetCursorPosCallback(handle, cursorPosCallback);
         GLFW.glfwSetMouseButtonCallback(handle, mouseButtonCallback);
         GLFW.glfwSetWindowFocusCallback(handle, windowFocusCallback);
         GLFW.glfwSetWindowIconifyCallback(handle, windowIconifyCallback);
         GLFW.glfwSetWindowSizeCallback(handle, windowSizeCallback);
         GLFW.glfwSetWindowPosCallback(handle, windowPosCallback);
         GLFW.glfwSetWindowRefreshCallback(handle, windowRefreshCallback);
         GLFW.glfwSetFramebufferSizeCallback(handle, framebufferSizeCallback);
         GLFW.glfwSetScrollCallback(handle, scrollCallback);
      }

      public static void releaseCallbacks() {
         Callbacks.glfwFreeCallbacks(handle);
         keyCallback = null;
         charCallback = null;
         cursorEnterCallback = null;
         cursorPosCallback = null;
         mouseButtonCallback = null;
         windowFocusCallback = null;
         windowIconifyCallback = null;
         windowSizeCallback = null;
         windowPosCallback = null;
         windowRefreshCallback = null;
         framebufferSizeCallback = null;
         scrollCallback = null;
      }
   }
}
