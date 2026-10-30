package org.lwjgl.util;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.opengl.DisplayMode;

public final class Display {
   private static final boolean DEBUG = false;

   public static DisplayMode[] getAvailableDisplayModes(
      int minWidth, int minHeight, int maxWidth, int maxHeight, int minBPP, int maxBPP, int minFreq, int maxFreq
   ) throws LWJGLException {
      DisplayMode[] modes = org.lwjgl.opengl.Display.getAvailableDisplayModes();
      if (LWJGLUtil.DEBUG) {
         System.out.println("Available screen modes:");

         for (DisplayMode mode : modes) {
            System.out.println(mode);
         }
      }

      ArrayList<DisplayMode> matches = new ArrayList<>(modes.length);

      for (int i = 0; i < modes.length; i++) {
         assert modes[i] != null : i + " " + modes.length;
         if ((minWidth == -1 || modes[i].getWidth() >= minWidth)
            && (maxWidth == -1 || modes[i].getWidth() <= maxWidth)
            && (minHeight == -1 || modes[i].getHeight() >= minHeight)
            && (maxHeight == -1 || modes[i].getHeight() <= maxHeight)
            && (minBPP == -1 || modes[i].getBitsPerPixel() >= minBPP)
            && (maxBPP == -1 || modes[i].getBitsPerPixel() <= maxBPP)
            && (modes[i].getFrequency() == 0 || (minFreq == -1 || modes[i].getFrequency() >= minFreq) && (maxFreq == -1 || modes[i].getFrequency() <= maxFreq))
            )
          {
            matches.add(modes[i]);
         }
      }

      DisplayMode[] ret = new DisplayMode[matches.size()];
      matches.toArray(ret);
      if (LWJGLUtil.DEBUG) {
      }

      return ret;
   }

}
