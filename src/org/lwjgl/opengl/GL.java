package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.function.IntFunction;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.Configuration;
import org.lwjgl.system.FunctionProvider;
import org.lwjgl.system.JNI;
import org.lwjgl.system.Library;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeResource;
import org.lwjgl.system.Platform;
import org.lwjgl.system.SharedLibrary;
import org.lwjgl.system.ThreadLocalUtil;
import org.lwjgl.system.linux.X11;
import org.lwjgl.system.macosx.MacOSXLibrary;
import org.lwjgl.system.windows.GDI32;
import org.lwjgl.system.windows.PIXELFORMATDESCRIPTOR;
import org.lwjgl.system.windows.User32;
import org.lwjgl.system.windows.WNDCLASSEX;
import org.lwjgl.system.windows.WindowsLibrary;
import org.lwjgl.system.windows.WindowsUtil;

public final class GL {
   @Nullable
   private static final APIUtil.APIVersion MAX_VERSION = APIUtil.apiParseVersion(Configuration.OPENGL_MAXVERSION);
   @Nullable
   private static FunctionProvider functionProvider;
   private static final ThreadLocal<GLCapabilities> capabilitiesTLS = new ThreadLocal<>();
   private static ICD icd = new ICDStatic();
   @Nullable
   private static WGLCapabilities capabilitiesWGL;
   @Nullable
   private static GLXCapabilities capabilitiesGLXClient;
   @Nullable
   private static GLXCapabilities capabilitiesGLX;

   private GL() {
   }

   static void initialize() {
   }

   public static void create() {
      SharedLibrary GL;
      switch (Platform.get()) {
         case LINUX:
            GL = Library.loadNative(GL.class, "org.lwjgl.opengl", Configuration.OPENGL_LIBRARY_NAME, "libGL.so.1", "libGL.so");
            break;
         case MACOSX:
            String override = Configuration.OPENGL_LIBRARY_NAME.get();
            GL = override != null ? Library.loadNative(GL.class, "org.lwjgl.opengl", override) : MacOSXLibrary.getWithIdentifier("com.apple.opengl");
            break;
         case WINDOWS:
            GL = Library.loadNative(GL.class, "org.lwjgl.opengl", Configuration.OPENGL_LIBRARY_NAME, "opengl32");
            break;
         default:
            throw new IllegalStateException();
      }

      create(GL);
   }

   public static void create(String libName) {
      create(Library.loadNative(GL.class, "org.lwjgl.opengl", libName));
   }

   private static void create(SharedLibrary OPENGL) {
      try {
         FunctionProvider provider = new SharedLibrary.Delegate(OPENGL) {
            private final long GetProcAddress;

            {
               long GetProcAddress = 0L;
               switch (Platform.get()) {
                  case LINUX:
                     GetProcAddress = this.library.getFunctionAddress("glXGetProcAddress");
                     if (GetProcAddress == 0L) {
                        GetProcAddress = this.library.getFunctionAddress("glXGetProcAddressARB");
                     }
                     break;
                  case WINDOWS:
                     GetProcAddress = this.library.getFunctionAddress("wglGetProcAddress");
               }

               if (GetProcAddress == 0L) {
                  GetProcAddress = this.library.getFunctionAddress("OSMesaGetProcAddress");
               }

               this.GetProcAddress = GetProcAddress;
            }

            @Override
            public long getFunctionAddress(ByteBuffer functionName) {
               long address = this.GetProcAddress == 0L
                  ? 0L
                  : (
                     Platform.get() == Platform.WINDOWS
                        ? WGL.nwglGetProcAddress(MemoryUtil.memAddress(functionName), this.GetProcAddress)
                        : JNI.callPP(MemoryUtil.memAddress(functionName), this.GetProcAddress)
                  );
               if (address == 0L) {
                  address = this.library.getFunctionAddress(functionName);
                  if (address == 0L && Checks.DEBUG_FUNCTIONS) {
                     APIUtil.apiLog("Failed to locate address for GL function " + MemoryUtil.memASCII(functionName));
                  }
               }

               return address;
            }
         };
         create(provider);
      } catch (RuntimeException e) {
         OPENGL.free();
         throw e;
      }
   }

   public static void create(FunctionProvider functionProvider) {
      if (GL.functionProvider != null) {
         throw new IllegalStateException("OpenGL library has already been loaded.");
      }

      GL.functionProvider = functionProvider;
      ThreadLocalUtil.setFunctionMissingAddresses(2226);
   }

   public static void destroy() {
      if (functionProvider != null) {
         ThreadLocalUtil.setFunctionMissingAddresses(0);
         capabilitiesWGL = null;
         capabilitiesGLX = null;
         if (functionProvider instanceof NativeResource) {
            ((NativeResource)functionProvider).free();
         }

         functionProvider = null;
      }
   }

   @Nullable
   public static FunctionProvider getFunctionProvider() {
      return functionProvider;
   }

   public static void setCapabilities(@Nullable GLCapabilities caps) {
      capabilitiesTLS.set(caps);
      ThreadLocalUtil.setCapabilities(caps == null ? 0L : MemoryUtil.memAddress(caps.addresses));
      icd.set(caps);
   }

   public static GLCapabilities getCapabilities() {
      return checkCapabilities(capabilitiesTLS.get());
   }

   private static GLCapabilities checkCapabilities(@Nullable GLCapabilities caps) {
      if (Checks.CHECKS && caps == null) {
         throw new IllegalStateException(
            "No GLCapabilities instance set for the current thread. Possible solutions:\n\ta) Call GL.createCapabilities() after making a context current in the current thread.\n\tb) Call GL.setCapabilities() if a GLCapabilities instance already exists for the current context."
         );
      } else {
         return caps;
      }
   }

   public static WGLCapabilities getCapabilitiesWGL() {
      if (capabilitiesWGL == null) {
         capabilitiesWGL = createCapabilitiesWGLDummy();
      }

      return capabilitiesWGL;
   }

   static GLXCapabilities getCapabilitiesGLXClient() {
      if (capabilitiesGLXClient == null) {
         capabilitiesGLXClient = initCapabilitiesGLX(true);
      }

      return capabilitiesGLXClient;
   }

   public static GLXCapabilities getCapabilitiesGLX() {
      if (capabilitiesGLX == null) {
         capabilitiesGLX = initCapabilitiesGLX(false);
      }

      return capabilitiesGLX;
   }

   private static GLXCapabilities initCapabilitiesGLX(boolean client) {
      long display = X11.nXOpenDisplay(0L);

      try {
         return createCapabilitiesGLX(display, client ? -1 : X11.XDefaultScreen(display));
      } finally {
         X11.XCloseDisplay(display);
      }
   }

   public static GLCapabilities createCapabilities() {
      return createCapabilities(null);
   }

   public static GLCapabilities createCapabilities(@Nullable IntFunction<PointerBuffer> bufferFactory) {
      return createCapabilities(false, bufferFactory);
   }

   public static GLCapabilities createCapabilities(boolean forwardCompatible) {
      return createCapabilities(forwardCompatible, null);
   }

   public static GLCapabilities createCapabilities(boolean forwardCompatible, @Nullable IntFunction<PointerBuffer> bufferFactory) {
      FunctionProvider functionProvider = GL.functionProvider;
      if (functionProvider == null) {
         throw new IllegalStateException("OpenGL library has not been loaded.");
      }

      long GetError = functionProvider.getFunctionAddress("glGetError");
      long GetString = functionProvider.getFunctionAddress("glGetString");
      long GetIntegerv = functionProvider.getFunctionAddress("glGetIntegerv");
      if (GetError != 0L && GetString != 0L && GetIntegerv != 0L) {
         int errorCode = JNI.callI(GetError);
         if (errorCode != 0) {
            APIUtil.apiLog(String.format("An OpenGL context was in an error state before the creation of its capabilities instance. Error: 0x%X", errorCode));
         }

         int majorVersion;
         int minorVersion;
         try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer version = stack.ints(0);
            JNI.callPV(33307, MemoryUtil.memAddress(version), GetIntegerv);
            if (JNI.callI(GetError) == 0 && 3 <= (majorVersion = version.get(0))) {
               JNI.callPV(33308, MemoryUtil.memAddress(version), GetIntegerv);
               minorVersion = version.get(0);
            } else {
               String versionString = MemoryUtil.memUTF8Safe(JNI.callP(7938, GetString));
               if (versionString == null || JNI.callI(GetError) != 0) {
                  throw new IllegalStateException("There is no OpenGL context current in the current thread.");
               }

               APIUtil.APIVersion apiVersion = APIUtil.apiParseVersion(versionString);
               majorVersion = apiVersion.major;
               minorVersion = apiVersion.minor;
            }
         }

         if (majorVersion >= 1 && (majorVersion != 1 || minorVersion >= 1)) {
            int[] GL_VERSIONS = new int[]{5, 1, 3, 6};
            Set<String> supportedExtensions = new HashSet<>(512);
            int maxMajor = Math.min(majorVersion, GL_VERSIONS.length);
            if (MAX_VERSION != null) {
               maxMajor = Math.min(MAX_VERSION.major, maxMajor);
            }

            for (int M = 1; M <= maxMajor; M++) {
               int maxMinor = GL_VERSIONS[M - 1];
               if (M == majorVersion) {
                  maxMinor = Math.min(minorVersion, maxMinor);
               }

               if (MAX_VERSION != null && M == MAX_VERSION.major) {
                  maxMinor = Math.min(MAX_VERSION.minor, maxMinor);
               }

               for (int m = M == 1 ? 1 : 0; m <= maxMinor; m++) {
                  supportedExtensions.add(String.format("OpenGL%d%d", M, m));
               }
            }

            if (majorVersion < 3) {
               String extensionsString = MemoryUtil.memASCIISafe(JNI.callP(7939, GetString));
               if (extensionsString != null) {
                  StringTokenizer tokenizer = new StringTokenizer(extensionsString);

                  while (tokenizer.hasMoreTokens()) {
                     supportedExtensions.add(tokenizer.nextToken());
                  }
               }
            } else {
               try (MemoryStack stack = MemoryStack.stackPush()) {
                  IntBuffer pi = stack.ints(0);
                  JNI.callPV(33309, MemoryUtil.memAddress(pi), GetIntegerv);
                  int extensionCount = pi.get(0);
                  long GetStringi = APIUtil.apiGetFunctionAddress(functionProvider, "glGetStringi");

                  for (int i = 0; i < extensionCount; i++) {
                     supportedExtensions.add(MemoryUtil.memASCII(JNI.callP(7939, i, GetStringi)));
                  }

                  JNI.callPV(33310, MemoryUtil.memAddress(pi), GetIntegerv);
                  if ((pi.get(0) & 1) != 0) {
                     forwardCompatible = true;
                  } else if (3 < majorVersion || 1 <= minorVersion) {
                     if (3 >= majorVersion && 2 > minorVersion) {
                        forwardCompatible = !supportedExtensions.contains("GL_ARB_compatibility");
                     } else {
                        JNI.callPV(37158, MemoryUtil.memAddress(pi), GetIntegerv);
                        if ((pi.get(0) & 1) != 0) {
                           forwardCompatible = true;
                        }
                     }
                  }
               }
            }

            APIUtil.apiFilterExtensions(supportedExtensions, Configuration.OPENGL_EXTENSION_FILTER);
            GLCapabilities caps = new GLCapabilities(
               functionProvider, supportedExtensions, forwardCompatible, bufferFactory == null ? BufferUtils::createPointerBuffer : bufferFactory
            );
            setCapabilities(caps);
            return caps;
         } else {
            throw new IllegalStateException("OpenGL 1.1 is required.");
         }
      } else {
         throw new IllegalStateException("Core OpenGL functions could not be found. Make sure that the OpenGL library has been loaded correctly.");
      }
   }

   private static WGLCapabilities createCapabilitiesWGLDummy() {
      long hdc = WGL.wglGetCurrentDC();
      if (hdc != 0L) {
         return createCapabilitiesWGL(hdc);
      }

      short classAtom = 0;
      long hwnd = 0L;
      long hglrc = 0L;

      try (MemoryStack stack = MemoryStack.stackPush()) {
         WNDCLASSEX wc = WNDCLASSEX.calloc(stack).cbSize(WNDCLASSEX.SIZEOF).style(3).hInstance(WindowsLibrary.HINSTANCE).lpszClassName(stack.UTF16("WGL"));
         MemoryUtil.memPutAddress(wc.address() + WNDCLASSEX.LPFNWNDPROC, User32.Functions.DefWindowProc);
         classAtom = User32.RegisterClassEx(wc);
         if (classAtom == 0) {
            throw new IllegalStateException("Failed to register WGL window class");
         }

         hwnd = Checks.check(User32.nCreateWindowEx(0, classAtom & 65535, 0L, 114229248, 0, 0, 1, 1, 0L, 0L, 0L, 0L));
         hdc = Checks.check(User32.GetDC(hwnd));
         PIXELFORMATDESCRIPTOR pfd = PIXELFORMATDESCRIPTOR.calloc(stack).nSize((short)PIXELFORMATDESCRIPTOR.SIZEOF).nVersion((short)1).dwFlags(32);
         int pixelFormat = GDI32.ChoosePixelFormat(hdc, pfd);
         if (pixelFormat == 0) {
            WindowsUtil.windowsThrowException("Failed to choose an OpenGL-compatible pixel format");
         }

         if (GDI32.DescribePixelFormat(hdc, pixelFormat, pfd) == 0) {
            WindowsUtil.windowsThrowException("Failed to obtain pixel format information");
         }

         if (!GDI32.SetPixelFormat(hdc, pixelFormat, pfd)) {
            WindowsUtil.windowsThrowException("Failed to set the pixel format");
         }

         hglrc = Checks.check(WGL.wglCreateContext(hdc));
         WGL.wglMakeCurrent(hdc, hglrc);
         return createCapabilitiesWGL(hdc);
      } finally {
         if (hglrc != 0L) {
            WGL.wglMakeCurrent(0L, 0L);
            WGL.wglDeleteContext(hglrc);
         }

         if (hwnd != 0L) {
            User32.DestroyWindow(hwnd);
         }

         if (classAtom != 0) {
            User32.nUnregisterClass(classAtom & 65535, WindowsLibrary.HINSTANCE);
         }
      }
   }

   public static WGLCapabilities createCapabilitiesWGL() {
      long hdc = WGL.wglGetCurrentDC();
      if (hdc == 0L) {
         throw new IllegalStateException("Failed to retrieve the device context of the current OpenGL context");
      } else {
         return createCapabilitiesWGL(hdc);
      }
   }

   private static WGLCapabilities createCapabilitiesWGL(long hdc) {
      FunctionProvider functionProvider = GL.functionProvider;
      if (functionProvider == null) {
         throw new IllegalStateException("OpenGL library has not been loaded.");
      }

      String extensionsString = null;
      long wglGetExtensionsString = functionProvider.getFunctionAddress("wglGetExtensionsStringARB");
      if (wglGetExtensionsString != 0L) {
         extensionsString = MemoryUtil.memASCII(JNI.callPP(hdc, wglGetExtensionsString));
      } else {
         wglGetExtensionsString = functionProvider.getFunctionAddress("wglGetExtensionsStringEXT");
         if (wglGetExtensionsString != 0L) {
            extensionsString = MemoryUtil.memASCII(JNI.callP(wglGetExtensionsString));
         }
      }

      Set<String> supportedExtensions = new HashSet<>(32);
      if (extensionsString != null) {
         StringTokenizer tokenizer = new StringTokenizer(extensionsString);

         while (tokenizer.hasMoreTokens()) {
            supportedExtensions.add(tokenizer.nextToken());
         }
      }

      APIUtil.apiFilterExtensions(supportedExtensions, Configuration.OPENGL_EXTENSION_FILTER);
      return new WGLCapabilities(functionProvider, supportedExtensions);
   }

   public static GLXCapabilities createCapabilitiesGLX(long display) {
      return createCapabilitiesGLX(display, X11.XDefaultScreen(display));
   }

   public static GLXCapabilities createCapabilitiesGLX(long display, int screen) {
      FunctionProvider functionProvider = GL.functionProvider;
      if (functionProvider == null) {
         throw new IllegalStateException("OpenGL library has not been loaded.");
      }

      int majorVersion;
      int minorVersion;
      try (MemoryStack stack = MemoryStack.stackPush()) {
         IntBuffer piMajor = stack.ints(0);
         IntBuffer piMinor = stack.ints(0);
         if (!GLX11.glXQueryVersion(display, piMajor, piMinor)) {
            throw new IllegalStateException("Failed to query GLX version");
         }

         majorVersion = piMajor.get(0);
         minorVersion = piMinor.get(0);
         if (majorVersion != 1) {
            throw new IllegalStateException("Invalid GLX major version: " + majorVersion);
         }
      }

      Set<String> supportedExtensions = new HashSet<>(32);
      int[][] GLX_VERSIONS = new int[][]{{1, 2, 3, 4}};

      for (int major = 1; major <= GLX_VERSIONS.length; major++) {
         int[] minors = GLX_VERSIONS[major - 1];

         for (int minor : minors) {
            if (major < majorVersion || major == majorVersion && minor <= minorVersion) {
               supportedExtensions.add("GLX" + major + minor);
            }
         }
      }

      if (1 <= minorVersion) {
         String extensionsString;
         if (screen == -1) {
            long glXGetClientString = functionProvider.getFunctionAddress("glXGetClientString");
            extensionsString = MemoryUtil.memASCIISafe(JNI.callPP(display, 3, glXGetClientString));
         } else {
            long glXQueryExtensionsString = functionProvider.getFunctionAddress("glXQueryExtensionsString");
            extensionsString = MemoryUtil.memASCIISafe(JNI.callPP(display, screen, glXQueryExtensionsString));
         }

         if (extensionsString != null) {
            StringTokenizer tokenizer = new StringTokenizer(extensionsString);

            while (tokenizer.hasMoreTokens()) {
               supportedExtensions.add(tokenizer.nextToken());
            }
         }
      }

      APIUtil.apiFilterExtensions(supportedExtensions, Configuration.OPENGL_EXTENSION_FILTER);
      return new GLXCapabilities(functionProvider, supportedExtensions);
   }

   static GLCapabilities getICD() {
      return checkCapabilities(icd.get());
   }

   static {
      Library.loadSystem(System::load, System::loadLibrary, GL.class, "org.lwjgl.opengl", Platform.mapLibraryNameBundled("lwjgl_opengl"));
      if (!Configuration.OPENGL_EXPLICIT_INIT.get(false)) {
         create();
      }
   }

   private interface ICD {
      default void set(@Nullable GLCapabilities caps) {
      }

      @Nullable
      GLCapabilities get();
   }

   private static class ICDStatic implements ICD {
      @Nullable
      private static GLCapabilities tempCaps;

      private ICDStatic() {
      }

      @Override
      public void set(@Nullable GLCapabilities caps) {
         if (tempCaps == null) {
            tempCaps = caps;
         } else if (caps != null && caps != tempCaps && ThreadLocalUtil.areCapabilitiesDifferent(tempCaps.addresses, caps.addresses)) {
            APIUtil.apiLog("[WARNING] Incompatible context detected. Falling back to thread-local lookup for GL contexts.");
            GL.icd = GL::getCapabilities;
         }
      }

      @Override
      public GLCapabilities get() {
         return WriteOnce.caps;
      }

      private static final class WriteOnce {
         static final GLCapabilities caps;

         static {
            GLCapabilities tempCaps = ICDStatic.tempCaps;
            if (tempCaps == null) {
               throw new IllegalStateException("No GLCapabilities instance has been set");
            }

            caps = tempCaps;
         }
      }
   }
}
