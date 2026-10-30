package org.lwjgl.glfw;

import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GLFWNativeX11 {
   protected GLFWNativeX11() {
      throw new UnsupportedOperationException();
   }

   @NativeType("Display *")
   public static long glfwGetX11Display() {
      long __functionAddress = Functions.GetX11Display;
      return JNI.invokeP(__functionAddress);
   }

   @NativeType("RRCrtc")
   public static long glfwGetX11Adapter(@NativeType("GLFWmonitor *") long monitor) {
      long __functionAddress = Functions.GetX11Adapter;
      if (Checks.CHECKS) {
         Checks.check(monitor);
      }

      return JNI.invokePN(monitor, __functionAddress);
   }

   @NativeType("RROutput")
   public static long glfwGetX11Monitor(@NativeType("GLFWmonitor *") long monitor) {
      long __functionAddress = Functions.GetX11Monitor;
      if (Checks.CHECKS) {
         Checks.check(monitor);
      }

      return JNI.invokePN(monitor, __functionAddress);
   }

   @NativeType("Window")
   public static long glfwGetX11Window(@NativeType("GLFWwindow *") long window) {
      long __functionAddress = Functions.GetX11Window;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePN(window, __functionAddress);
   }

   public static void nglfwSetX11SelectionString(long string) {
      long __functionAddress = Functions.SetX11SelectionString;
      JNI.invokePV(string, __functionAddress);
   }

   public static void glfwSetX11SelectionString(@NativeType("char const *") ByteBuffer string) {
      if (Checks.CHECKS) {
         Checks.checkNT1(string);
      }

      nglfwSetX11SelectionString(MemoryUtil.memAddress(string));
   }

   public static void glfwSetX11SelectionString(@NativeType("char const *") CharSequence string) {
      MemoryStack stack = MemoryStack.stackGet();
      int stackPointer = stack.getPointer();

      try {
         stack.nUTF8(string, true);
         long stringEncoded = stack.getPointerAddress();
         nglfwSetX11SelectionString(stringEncoded);
      } finally {
         stack.setPointer(stackPointer);
      }
   }

   public static long nglfwGetX11SelectionString() {
      long __functionAddress = Functions.GetX11SelectionString;
      return JNI.invokeP(__functionAddress);
   }

   @Nullable
   @NativeType("char const *")
   public static String glfwGetX11SelectionString() {
      long __result = nglfwGetX11SelectionString();
      return MemoryUtil.memUTF8Safe(__result);
   }

   public static final class Functions {
      public static final long GetX11Display = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetX11Display");
      public static final long GetX11Adapter = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetX11Adapter");
      public static final long GetX11Monitor = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetX11Monitor");
      public static final long GetX11Window = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetX11Window");
      public static final long SetX11SelectionString = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwSetX11SelectionString");
      public static final long GetX11SelectionString = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetX11SelectionString");

      private Functions() {
      }
   }
}
