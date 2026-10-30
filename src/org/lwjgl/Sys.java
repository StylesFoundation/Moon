package org.lwjgl;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.net.URI;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.lwjgl.glfw.GLFW;

public class Sys {
   private Sys() {
   }

   public static String getVersion() {
      return Version.getVersion();
   }

   public static void initialize() {
      if (!GLFW.glfwInit()) {
         throw new IllegalStateException("Unable to initialize GLFW");
      }
   }

   public static long getTimerResolution() {
      return 1000L;
   }

   public static long getTime() {
      return GLFW.glfwGetTimerValue();
   }

   public static long getNanoTime() {
      return System.nanoTime();
   }

   public static boolean openURL(String url) {
      if (!Desktop.isDesktopSupported()) {
         return false;
      }

      Desktop desktop = Desktop.getDesktop();
      if (!desktop.isSupported(Action.BROWSE)) {
         return false;
      }

      try {
         desktop.browse(new URI(url));
         return true;
      } catch (Exception ex) {
         ex.printStackTrace();
         return false;
      }
   }

   public static void alert(String title, String message) {
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception e) {
         LWJGLUtil.log("Caught exception while setting Look-and-Feel: " + e);
      }

      JOptionPane.showMessageDialog(null, message, title, 2);
   }

   public static String getClipboard() {
      return GLFW.glfwGetClipboardString(GLFW.glfwGetPrimaryMonitor());
   }
}
