package org.lwjgl.system;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.jni.JNINativeInterface;

public final class ThreadLocalUtil {
   private static final long JNI_NATIVE_INTERFACE = MemoryUtil.memGetAddress(getThreadJNIEnv());
   private static final int JNI_NATIVE_INTERFACE_FUNCTION_COUNT;
   private static final long FUNCTION_MISSING_ABORT = getFunctionMissingAbort();
   private static final int CAPABILITIES_OFFSET = 3 * Pointer.POINTER_SIZE;

   private ThreadLocalUtil() {
   }

   private static native long getThreadJNIEnv();

   private static native long getFunctionMissingAbort();

   private static native long nsetupEnvData(int var0);

   public static long setupEnvData() {
      return nsetupEnvData(JNI_NATIVE_INTERFACE_FUNCTION_COUNT);
   }

   public static void setCapabilities(long capabilities) {
      long env_pp = getThreadJNIEnv();
      long env_p = MemoryUtil.memGetAddress(env_pp);
      if (capabilities == 0L) {
         if (env_p != JNI_NATIVE_INTERFACE) {
            MemoryUtil.memPutAddress(env_p + CAPABILITIES_OFFSET, MemoryUtil.memGetAddress(JNI_NATIVE_INTERFACE + CAPABILITIES_OFFSET));
         }
      } else {
         if (env_p == JNI_NATIVE_INTERFACE) {
            setupEnvData();
            env_p = MemoryUtil.memGetAddress(env_pp);
         }

         MemoryUtil.memPutAddress(env_p + CAPABILITIES_OFFSET, capabilities);
      }
   }

   public static void setFunctionMissingAddresses(int functionCount) {
      long ptr = JNI_NATIVE_INTERFACE + CAPABILITIES_OFFSET;
      if (functionCount == 0) {
         long missingCaps = MemoryUtil.memGetAddress(ptr);
         if (missingCaps != 0L) {
            MemoryUtil.getAllocator().free(missingCaps);
            MemoryUtil.memPutAddress(ptr, 0L);
         }
      } else {
         long missingCaps = MemoryUtil.getAllocator().malloc(Integer.toUnsignedLong(functionCount) * Pointer.POINTER_SIZE);

         for (int i = 0; i < functionCount; i++) {
            MemoryUtil.memPutAddress(missingCaps + Integer.toUnsignedLong(i) * Pointer.POINTER_SIZE, FUNCTION_MISSING_ABORT);
         }

         MemoryUtil.memPutAddress(ptr, missingCaps);
      }
   }

   public static PointerBuffer setupAddressBuffer(PointerBuffer addresses) {
      for (int i = addresses.position(); i < addresses.limit(); i++) {
         if (addresses.get(i) == 0L) {
            addresses.put(i, FUNCTION_MISSING_ABORT);
         }
      }

      return addresses;
   }

   public static boolean areCapabilitiesDifferent(PointerBuffer ref, PointerBuffer caps) {
      for (int i = 0; i < ref.remaining(); i++) {
         if (ref.get(i) != caps.get(i) && caps.get(i) != 0L) {
            return true;
         }
      }

      return false;
   }

   static {
      int JNI_VERSION = JNINativeInterface.GetVersion();
      int reservedCount;
      switch (JNI_VERSION) {
         case 65537:
            reservedCount = 12;
            break;
         default:
            reservedCount = 4;
      }

      int jniCallCount;
      switch (JNI_VERSION) {
         case 65537:
            jniCallCount = 208;
            break;
         case 65538:
            jniCallCount = 225;
            break;
         case 65540:
            jniCallCount = 228;
            break;
         case 65542:
         case 65544:
            jniCallCount = 229;
            break;
         case 589824:
         case 655360:
            jniCallCount = 230;
            break;
         default:
            jniCallCount = 230;
            APIUtil.DEBUG_STREAM
               .println("[LWJGL] [ThreadLocalUtil] Unsupported JNI version detected, this may result in a crash. Please inform LWJGL developers.");
      }

      JNI_NATIVE_INTERFACE_FUNCTION_COUNT = reservedCount + jniCallCount;
   }
}
