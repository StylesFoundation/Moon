package com.moon.util.client;

import org.lwjgl.opengl.GL45;
import org.lwjgl.opengl.GL11;

public class GpuUtil {
   public static GPUType getGPUType() {
      String vendor = GL45.glGetString(GL11.GL_VENDOR);
      if (vendor == null) {
         return GPUType.FALLBACK;
      } else {
         String lowerVendor = vendor.toLowerCase();
         if (lowerVendor.contains("nvidia")) {
            return GPUType.NVIDIA;
         } else if (lowerVendor.contains("amd")) {
            return GPUType.AMD;
         } else {
            return lowerVendor.contains("apple") ? GPUType.APPLE : GPUType.FALLBACK;
         }
      }
   }
}
