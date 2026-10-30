package net.optifine.util;

import java.util.Comparator;
import org.lwjgl.opengl.DisplayMode;

public class DisplayModeComparator implements Comparator {
   @Override
   public int compare(Object object1, Object object2) {
      DisplayMode var6 = (DisplayMode)object1;
      DisplayMode var7 = (DisplayMode)object2;
      return var6.getWidth() != var7.getWidth()
         ? var6.getWidth() - var7.getWidth()
         : (
            var6.getHeight() != var7.getHeight()
               ? var6.getHeight() - var7.getHeight()
               : (
                  var6.getBitsPerPixel() != var7.getBitsPerPixel()
                     ? var6.getBitsPerPixel() - var7.getBitsPerPixel()
                     : (var6.getFrequency() != var7.getFrequency() ? var6.getFrequency() - var7.getFrequency() : 0)
               )
         );
   }
}
