package com.moon.render.color;

import java.awt.Color;
import java.awt.color.ColorSpace;

public class RenderColor extends Color {
   public RenderColor(int red, int green, int blue) {
      super(red, green, blue);
   }

   public RenderColor(int red, int green, int blue, int alpha) {
      super(red, green, blue, alpha);
   }

   public RenderColor(int rgb) {
      super(rgb, true);
   }

   public RenderColor(int rgba, boolean hasAlpha) {
      super(rgba, hasAlpha);
   }

   public RenderColor(float red, float green, float blue) {
      super(red, green, blue);
   }

   public RenderColor(float red, float green, float blue, float alpha) {
      super(red, green, blue, alpha);
   }

   public RenderColor(ColorSpace colorSpace, float[] components, float alpha) {
      super(colorSpace, components, alpha);
   }

   public static RenderColor fromHue(double hue) {
      int rgb = Color.HSBtoRGB((float)(1.0 - hue / 360.0), 1.0F, 1.0F);
      return new RenderColor(new Color(rgb).getRGB());
   }

   public RenderColor brighter() {
      return new RenderColor(super.brighter().getRGB());
   }

   public RenderColor darker() {
      return new RenderColor(super.darker().getRGB());
   }

   public RenderColor withAlpha(int alpha) {
      alpha = (int)this.clamp(alpha, 0.0, 255.0);
      return new RenderColor(this.getRed(), this.getGreen(), this.getBlue(), alpha);
   }

   public RenderColor withAlphaFraction(float alpha) {
      int clampedAlpha = (int)this.clamp(alpha * 255.0F, 0.0, 255.0);
      return new RenderColor(this.getRed(), this.getGreen(), this.getBlue(), clampedAlpha);
   }

   public RenderColor withSaturation(float saturation) {
      float[] hsb = new float[3];
      Color.RGBtoHSB(this.getRed(), this.getGreen(), this.getBlue(), hsb);
      return new RenderColor(new Color(Color.HSBtoRGB(hsb[0], saturation, hsb[2])).getRGB());
   }

   public RenderColor withBrightness(float brightness) {
      float[] hsb = new float[3];
      Color.RGBtoHSB(this.getRed(), this.getGreen(), this.getBlue(), hsb);
      return new RenderColor(new Color(Color.HSBtoRGB(hsb[0], hsb[1], brightness)).getRGB());
   }

   public int getRGBWithBrightnessPercent(int brightnessPercent) {
      float brightness = brightnessPercent / 100.0F;
      float[] hsb = new float[3];
      Color.RGBtoHSB(this.getRed(), this.getGreen(), this.getBlue(), hsb);
      return new Color(Color.HSBtoRGB(hsb[0], hsb[1], brightness)).getRGB();
   }

   private double clamp(double value, double min, double max) {
      return value > max ? max : Math.max(value, min);
   }
}
