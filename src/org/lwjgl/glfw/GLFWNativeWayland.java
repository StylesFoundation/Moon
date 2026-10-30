package org.lwjgl.glfw;

import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.NativeType;

public class GLFWNativeWayland {
   protected GLFWNativeWayland() {
      throw new UnsupportedOperationException();
   }

   @NativeType("struct wl_display *")
   public static long glfwGetWaylandDisplay() {
      long __functionAddress = Functions.GetWaylandDisplay;
      return JNI.invokeP(__functionAddress);
   }

   @NativeType("struct wl_output *")
   public static long glfwGetWaylandMonitor(@NativeType("GLFWmonitor *") long monitor) {
      long __functionAddress = Functions.GetWaylandMonitor;
      if (Checks.CHECKS) {
         Checks.check(monitor);
      }

      return JNI.invokePP(monitor, __functionAddress);
   }

   @NativeType("struct wl_surface *")
   public static long glfwGetWaylandWindow(@NativeType("GLFWwindow *") long window) {
      long __functionAddress = Functions.GetWaylandWindow;
      if (Checks.CHECKS) {
         Checks.check(window);
      }

      return JNI.invokePP(window, __functionAddress);
   }

   public static final class Functions {
      public static final long GetWaylandDisplay = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetWaylandDisplay");
      public static final long GetWaylandMonitor = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetWaylandMonitor");
      public static final long GetWaylandWindow = APIUtil.apiGetFunctionAddress(GLFW.getLibrary(), "glfwGetWaylandWindow");

      private Functions() {
      }
   }
}
