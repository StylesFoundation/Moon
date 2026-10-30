package org.lwjgl.opengl;

public final class DisplayMode {
   private final int width;
   private final int height;
   private final int bpp;
   private final int freq;
   private final boolean fullscreen;

   public DisplayMode(int width, int height) {
      this(width, height, 0, 0, false);
   }

   DisplayMode(int width, int height, int bpp, int freq) {
      this(width, height, bpp, freq, true);
   }

   private DisplayMode(int width, int height, int bpp, int freq, boolean fullscreen) {
      this.width = width;
      this.height = height;
      this.bpp = bpp;
      this.freq = freq;
      this.fullscreen = fullscreen;
   }

   public boolean isFullscreenCapable() {
      return this.fullscreen;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public int getBitsPerPixel() {
      return this.bpp;
   }

   public int getFrequency() {
      return this.freq;
   }

   @Override
   public boolean equals(Object obj) {
      return !(obj instanceof DisplayMode dm) ? false : dm.width == this.width && dm.height == this.height && dm.bpp == this.bpp && dm.freq == this.freq;
   }

   @Override
   public int hashCode() {
      return this.width ^ this.height ^ this.freq ^ this.bpp;
   }

   @Override
   public String toString() {
      return this.width + " x " + this.height + " x " + this.bpp + " @" + this.freq + "Hz";
   }
}
