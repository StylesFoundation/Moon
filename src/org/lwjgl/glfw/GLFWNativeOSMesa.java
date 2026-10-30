package org.lwjgl.glfw;

import java.nio.IntBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.FunctionProvider;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;
import org.lwjgl.system.SharedLibrary;

public class GLFWNativeOSMesa {
   protected GLFWNativeOSMesa() {
      throw new UnsupportedOperationException();
   }

   public static int nglfwGetOSMesaColorBuffer(long window, long width, long height, long format, long buffer) {
      long __functionAddress = Functions.GetOSMesaColorBuffer;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePPPPPI(window, width, height, format, buffer, __functionAddress);
   }

   @NativeType("int")
   public static boolean glfwGetOSMesaColorBuffer(
      @NativeType("GLFWwindow *") long window,
      @Nullable @NativeType("int *") IntBuffer width,
      @Nullable @NativeType("int *") IntBuffer height,
      @Nullable @NativeType("int *") IntBuffer format,
      @Nullable @NativeType("void **") PointerBuffer buffer
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(width, 1);
         Checks.checkSafe(height, 1);
         Checks.checkSafe(format, 1);
         Checks.checkSafe(buffer, 1);
      }

      return nglfwGetOSMesaColorBuffer(
            window, MemoryUtil.memAddressSafe(width), MemoryUtil.memAddressSafe(height), MemoryUtil.memAddressSafe(format), MemoryUtil.memAddressSafe(buffer)
         )
         != 0;
   }

   public static int nglfwGetOSMesaDepthBuffer(long window, long width, long height, long bytesPerValue, long buffer) {
      long __functionAddress = Functions.GetOSMesaDepthBuffer;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePPPPPI(window, width, height, bytesPerValue, buffer, __functionAddress);
   }

   public static int glfwGetOSMesaDepthBuffer(
      @NativeType("GLFWwindow *") long window,
      @Nullable @NativeType("int *") IntBuffer width,
      @Nullable @NativeType("int *") IntBuffer height,
      @Nullable @NativeType("int *") IntBuffer bytesPerValue,
      @Nullable @NativeType("void **") PointerBuffer buffer
   ) {
      if (Checks.CHECKS) {
         Checks.checkSafe(width, 1);
         Checks.checkSafe(height, 1);
         Checks.checkSafe(bytesPerValue, 1);
         Checks.checkSafe(buffer, 1);
      }

      return nglfwGetOSMesaDepthBuffer(
         window,
         MemoryUtil.memAddressSafe(width),
         MemoryUtil.memAddressSafe(height),
         MemoryUtil.memAddressSafe(bytesPerValue),
         MemoryUtil.memAddressSafe(buffer)
      );
   }

   @NativeType("OSMesaContext")
   public static long glfwGetOSMesaContext(@NativeType("GLFWwindow *") long window) {
      long __functionAddress = Functions.GetOSMesaContext;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePP(window, __functionAddress);
   }

   @NativeType("int")
   public static boolean glfwGetOSMesaColorBuffer(
      @NativeType("GLFWwindow *") long window,
      @Nullable @NativeType("int *") int[] width,
      @Nullable @NativeType("int *") int[] height,
      @Nullable @NativeType("int *") int[] format,
      @Nullable @NativeType("void **") PointerBuffer buffer
   ) {
      long __functionAddress = Functions.GetOSMesaColorBuffer;
      if (Checks.CHECKS) {
         Checks.check(window);
         Checks.checkSafe(width, 1);
         Checks.checkSafe(height, 1);
         Checks.checkSafe(format, 1);
         Checks.checkSafe(buffer, 1);
      }

      return JNI.invokePPPPPI(window, width, height, format, MemoryUtil.memAddressSafe(buffer), __functionAddress) != 0;
   }

   public static int glfwGetOSMesaDepthBuffer(
      @NativeType("GLFWwindow *") long window,
      @Nullable @NativeType("int *") int[] width,
      @Nullable @NativeType("int *") int[] height,
      @Nullable @NativeType("int *") int[] bytesPerValue,
      @Nullable @NativeType("void **") PointerBuffer buffer
   ) {
      long __functionAddress = Functions.GetOSMesaDepthBuffer;
      if (Checks.CHECKS) {
         Checks.check(window);
         Checks.checkSafe(width, 1);
         Checks.checkSafe(height, 1);
         Checks.checkSafe(bytesPerValue, 1);
         Checks.checkSafe(buffer, 1);
      }

      return JNI.invokePPPPPI(window, width, height, bytesPerValue, MemoryUtil.memAddressSafe(buffer), __functionAddress);
   }

   public static void setPath(FunctionProvider sharedLibrary) {
      if (!(sharedLibrary instanceof SharedLibrary)) {
         APIUtil.apiLog("GLFW OSMesa path override not set: Function provider is not a shared library.");
      } else {
         String path = ((SharedLibrary)sharedLibrary).getPath();
         if (path == null) {
            APIUtil.apiLog("GLFW OSMesa path override not set: Could not resolve the OSMesa shared library path.");
         } else {
            setPath(path);
         }
      }
   }

   public static void setPath(@Nullable String path) {
      long override = GLFW.getLibrary().getFunctionAddress("_glfw_mesa_library");
      if (override == 0L) {
         APIUtil.apiLog("GLFW OSMesa path override not set: Could not resolve override symbol.");
      } else {
         long a = MemoryUtil.memGetAddress(override);
         if (a != 0L) {
            MemoryUtil.nmemFree(a);
         }

         MemoryUtil.memPutAddress(override, path == null ? 0L : MemoryUtil.memAddress(MemoryUtil.memUTF8(path)));
      }
   }

   public static final class Functions {
      public static final long GetOSMesaColorBuffer = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetOSMesaColorBuffer");
      public static final long GetOSMesaDepthBuffer = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetOSMesaDepthBuffer");
      public static final long GetOSMesaContext = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetOSMesaContext");

      private Functions() {
      }
   }
}
