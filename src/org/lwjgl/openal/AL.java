package org.lwjgl.openal;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import javax.annotation.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.FunctionProvider;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.ThreadLocalUtil;

public final class AL {
   static long alContext;
   static ALCdevice alcDevice;
   static ALCCapabilities alContextCaps;
   static ALCapabilities alCaps;
   private static boolean created_lwjgl2 = false;
   @Nullable
   private static FunctionProvider functionProvider;
   @Nullable
   private static ALCapabilities processCaps;
   private static final ThreadLocal<ALCapabilities> capabilitiesTLS = new ThreadLocal<>();
   private static ICD icd = new ICDStatic();

   public static void create() throws LWJGLException {
      if (alContext == 0L) {
         long alDevice = ALC10.alcOpenDevice((ByteBuffer)null);
         if (alDevice == 0L) {
            throw new LWJGLException("Cannot open the device");
         }

         IntBuffer attribs = BufferUtils.createIntBuffer(16);
         attribs.put(4103);
         attribs.put(44100);
         attribs.put(4104);
         attribs.put(60);
         attribs.put(4105);
         attribs.put(0);
         attribs.put(0);
         attribs.flip();
         long contextHandle = ALC10.alcCreateContext(alDevice, attribs);
         ALC10.alcMakeContextCurrent(contextHandle);
         alContext = ALC10.alcCreateContext(contextHandle, (IntBuffer)null);
         alContextCaps = ALC.createCapabilities(alContext);
         alCaps = createCapabilities(alContextCaps);
         alcDevice = new ALCdevice(alDevice);
         created_lwjgl2 = true;
      }
   }

   public static boolean isCreated() {
      return created_lwjgl2;
   }

   public static ALCdevice getDevice() {
      return alcDevice;
   }

   private AL() {
   }

   static void init() {
      functionProvider = new FunctionProvider() {
         private final long alGetProcAddress = ALC.getFunctionProvider().getFunctionAddress("alGetProcAddress");

         @Override
         public long getFunctionAddress(ByteBuffer functionName) {
            long address = JNI.invokePP(MemoryUtil.memAddress(functionName), this.alGetProcAddress);
            if (address == 0L && Checks.DEBUG_FUNCTIONS) {
               APIUtil.apiLog("Failed to locate address for AL function " + MemoryUtil.memASCII(functionName));
            }

            return address;
         }
      };
   }

   public static void destroy() {
      if (functionProvider != null) {
         if (created_lwjgl2) {
            ALC10.alcMakeContextCurrent(0L);
            ALC10.alcDestroyContext(alContext);
            ALC10.alcCloseDevice(alcDevice.device);
            alContext = -1L;
            alcDevice = null;
            created_lwjgl2 = false;
         }

         setCurrentProcess(null);
         functionProvider = null;
      }
   }

   public static void setCurrentProcess(@Nullable ALCapabilities caps) {
      processCaps = caps;
      capabilitiesTLS.set(null);
      icd.set(caps);
   }

   public static void setCurrentThread(@Nullable ALCapabilities caps) {
      capabilitiesTLS.set(caps);
      icd.set(caps);
   }

   public static ALCapabilities getCapabilities() {
      ALCapabilities caps = capabilitiesTLS.get();
      if (caps == null) {
         caps = processCaps;
      }

      return checkCapabilities(caps);
   }

   private static ALCapabilities checkCapabilities(@Nullable ALCapabilities caps) {
      if (caps == null) {
         throw new IllegalStateException(
            "No ALCapabilities instance set for the current thread or process. Possible solutions:\n\ta) Call AL.createCapabilities() after making a context current.\n\tb) Call AL.setCurrentProcess() or AL.setCurrentThread() if an ALCapabilities instance already exists."
         );
      } else {
         return caps;
      }
   }

   public static ALCapabilities createCapabilities(ALCCapabilities alcCaps) {
      FunctionProvider functionProvider = (FunctionProvider)ALC.check(AL.functionProvider);
      ALCapabilities caps = null;

      try {
         long GetString = functionProvider.getFunctionAddress("alGetString");
         long GetError = functionProvider.getFunctionAddress("alGetError");
         long IsExtensionPresent = functionProvider.getFunctionAddress("alIsExtensionPresent");
         if (GetString != 0L && GetError != 0L && IsExtensionPresent != 0L) {
            String versionString = MemoryUtil.memASCIISafe(JNI.invokeP(45058, GetString));
            if (versionString != null && JNI.invokeI(GetError) == 0) {
               APIUtil.APIVersion apiVersion = APIUtil.apiParseVersion(versionString);
               int majorVersion = apiVersion.major;
               int minorVersion = apiVersion.minor;
               int[][] AL_VERSIONS = new int[][]{{0, 1}};
               Set<String> supportedExtensions = new HashSet<>(32);

               for (int major = 1; major <= AL_VERSIONS.length; major++) {
                  int[] minors = AL_VERSIONS[major - 1];

                  for (int minor : minors) {
                     if (major < majorVersion || major == majorVersion && minor <= minorVersion) {
                        supportedExtensions.add("OpenAL" + major + minor);
                     }
                  }
               }

               String extensionsString = MemoryUtil.memASCIISafe(JNI.invokeP(45060, GetString));
               if (extensionsString != null) {
                  MemoryStack stack = MemoryStack.stackGet();
                  StringTokenizer tokenizer = new StringTokenizer(extensionsString);

                  while (tokenizer.hasMoreTokens()) {
                     String extName = tokenizer.nextToken();

                     try (MemoryStack frame = stack.push()) {
                        if (JNI.invokePZ(MemoryUtil.memAddress(frame.ASCII(extName, true)), IsExtensionPresent)) {
                           supportedExtensions.add(extName);
                        }
                     }
                  }
               }

               if (alcCaps.ALC_EXT_EFX) {
                  supportedExtensions.add("ALC_EXT_EFX");
               }

               return caps = new ALCapabilities(functionProvider, supportedExtensions, BufferUtils::createPointerBuffer);
            } else {
               throw new IllegalStateException("There is no OpenAL context current in the current thread or process.");
            }
         } else {
            throw new IllegalStateException("Core OpenAL functions could not be found. Make sure that the OpenAL library has been loaded correctly.");
         }
      } finally {
         if (alcCaps.ALC_EXT_thread_local_context && EXTThreadLocalContext.alcGetThreadContext() != 0L) {
            setCurrentThread(caps);
         } else {
            setCurrentProcess(caps);
         }
      }
   }

   static ALCapabilities getICD() {
      return (ALCapabilities)ALC.check(icd.get());
   }

   private interface ICD {
      default void set(@Nullable ALCapabilities caps) {
      }

      @Nullable
      ALCapabilities get();
   }

   private static class ICDStatic implements ICD {
      @Nullable
      private static ALCapabilities tempCaps;

      @Override
      public void set(@Nullable ALCapabilities caps) {
         if (tempCaps == null) {
            tempCaps = caps;
         } else if (caps != null && caps != tempCaps && ThreadLocalUtil.areCapabilitiesDifferent(tempCaps.addresses, caps.addresses)) {
            APIUtil.apiLog("[WARNING] Incompatible context detected. Falling back to thread/process lookup for AL contexts.");
            AL.icd = AL::getCapabilities;
         }
      }

      @Nullable
      @Override
      public ALCapabilities get() {
         return WriteOnce.caps;
      }

      private static final class WriteOnce {
         @Nullable
         static final ALCapabilities caps = ICDStatic.tempCaps;

         static {
            if (caps == null) {
               throw new IllegalStateException("No ALCapabilities instance has been set");
            }
         }
      }
   }
}
