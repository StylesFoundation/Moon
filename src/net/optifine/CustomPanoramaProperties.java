package net.optifine;

import java.util.Properties;
import net.minecraft.util.ResourceLocation;
import net.optifine.config.ConnectedParser;

public class CustomPanoramaProperties {
   private final String path;
   private final ResourceLocation[] panoramaLocations;
   private int weight = 1;
   private int blur1 = 64;
   private int blur2 = 3;
   private int blur3 = 3;
   private int overlay1Top = -2130706433;
   private int overlay1Bottom = 16777215;
   private int overlay2Top = 0;
   private int overlay2Bottom = Integer.MIN_VALUE;

   public CustomPanoramaProperties(String string, Properties properties) {
      ConnectedParser var6 = new ConnectedParser("CustomPanorama");
      this.path = string;
      this.panoramaLocations = new ResourceLocation[6];

      for (int var7 = 0; var7 < this.panoramaLocations.length; var7++) {
         this.panoramaLocations[var7] = new ResourceLocation(string + "/panorama_" + var7 + ".png");
      }

      this.weight = var6.parseInt(properties.getProperty("weight"), 1);
      this.blur1 = var6.parseInt(properties.getProperty("blur1"), 64);
      this.blur2 = var6.parseInt(properties.getProperty("blur2"), 3);
      this.blur3 = var6.parseInt(properties.getProperty("blur3"), 3);
      this.overlay1Top = ConnectedParser.parseColor4(properties.getProperty("overlay1.top"), -2130706433);
      this.overlay1Bottom = ConnectedParser.parseColor4(properties.getProperty("overlay1.bottom"), 16777215);
      this.overlay2Top = ConnectedParser.parseColor4(properties.getProperty("overlay2.top"), 0);
      this.overlay2Bottom = ConnectedParser.parseColor4(properties.getProperty("overlay2.bottom"), Integer.MIN_VALUE);
   }

   public ResourceLocation[] getPanoramaLocations() {
      return this.panoramaLocations;
   }

   public int getWeight() {
      return this.weight;
   }

   public int getBlur1() {
      return this.blur1;
   }

   public int getBlur2() {
      return this.blur2;
   }

   public int getBlur3() {
      return this.blur3;
   }

   public int getOverlay1Top() {
      return this.overlay1Top;
   }

   public int getOverlay1Bottom() {
      return this.overlay1Bottom;
   }

   public int getOverlay2Top() {
      return this.overlay2Top;
   }

   public int getOverlay2Bottom() {
      return this.overlay2Bottom;
   }

   @Override
   public String toString() {
      int var3 = this.overlay2Bottom;
      int var4 = this.overlay2Top;
      int var5 = this.overlay1Bottom;
      int var6 = this.overlay1Top;
      int var7 = this.blur3;
      int var8 = this.blur2;
      int var9 = this.blur1;
      int var10 = this.weight;
      return this.path + ", weight: " + var10 + ", blur: " + var9 + " " + var8 + " " + var7 + ", overlay: " + var6 + " " + var5 + " " + var4 + " " + var3;
   }
}
