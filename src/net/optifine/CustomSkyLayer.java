package net.optifine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.optifine.config.ConnectedParser;
import net.optifine.config.Matches;
import net.optifine.config.RangeListInt;
import net.optifine.render.Blender;
import net.optifine.util.NumUtils;
import net.optifine.util.SmoothFloat;
import net.optifine.util.TextureUtils;

public class CustomSkyLayer {
   public String source = null;
   private int startFadeIn = -1;
   private int endFadeIn = -1;
   private int startFadeOut = -1;
   private int endFadeOut = -1;
   private int blend = 1;
   private boolean rotate = false;
   private float speed = 1.0F;
   private float[] axis;
   private RangeListInt days;
   private int daysLoop;
   private boolean weatherClear;
   private boolean weatherRain;
   private boolean weatherThunder;
   public BiomeGenBase[] biomes;
   public RangeListInt heights;
   private float transition;
   private SmoothFloat smoothPositionBrightness;
   public int textureId;
   private World lastWorld;
   public static final float[] DEFAULT_AXIS = new float[]{1.0F, 0.0F, 0.0F};

   public CustomSkyLayer(Properties properties, String string) {
      this.axis = DEFAULT_AXIS;
      this.days = null;
      this.daysLoop = 8;
      this.weatherClear = true;
      this.weatherRain = false;
      this.weatherThunder = false;
      this.biomes = null;
      this.heights = null;
      this.transition = 1.0F;
      this.smoothPositionBrightness = null;
      this.textureId = -1;
      this.lastWorld = null;
      ConnectedParser var5 = new ConnectedParser("CustomSky");
      this.source = properties.getProperty("source", string);
      this.startFadeIn = this.parseTime(properties.getProperty("startFadeIn"));
      this.endFadeIn = this.parseTime(properties.getProperty("endFadeIn"));
      this.startFadeOut = this.parseTime(properties.getProperty("startFadeOut"));
      this.endFadeOut = this.parseTime(properties.getProperty("endFadeOut"));
      this.blend = Blender.parseBlend(properties.getProperty("blend"));
      this.rotate = this.parseBoolean(properties.getProperty("rotate"), true);
      this.speed = this.parseFloat(properties.getProperty("speed"), 1.0F);
      this.axis = this.parseAxis(properties.getProperty("axis"), DEFAULT_AXIS);
      this.days = var5.parseRangeListInt(properties.getProperty("days"));
      this.daysLoop = var5.parseInt(properties.getProperty("daysLoop"), 8);
      List var6 = this.parseWeatherList(properties.getProperty("weather", "clear"));
      this.weatherClear = var6.contains("clear");
      this.weatherRain = var6.contains("rain");
      this.weatherThunder = var6.contains("thunder");
      this.biomes = var5.parseBiomes(properties.getProperty("biomes"));
      this.heights = var5.parseRangeListInt(properties.getProperty("heights"));
      this.transition = this.parseFloat(properties.getProperty("transition"), 1.0F);
   }

   private List<String> parseWeatherList(String string) {
      List var5 = Arrays.asList("clear", "rain", "thunder");
      ArrayList var6 = new ArrayList();
      String[] var7 = Config.tokenize(string, " ");

      for (String var11 : var7) {
         if (!var5.contains(var11)) {
            Config.Method252("Unknown weather: " + var11);
         } else {
            var6.add(var11);
         }
      }

      return var6;
   }

   private int parseTime(String string) {
      if (string == null) {
         return -1;
      }

      String[] var5 = Config.tokenize(string, ":");
      if (var5.length != 2) {
         Config.Method252("Invalid time: " + string);
         return -1;
      }

      String var6 = var5[0];
      String var7 = var5[1];
      int var8 = Config.parseInt(var6, -1);
      int var9 = Config.parseInt(var7, -1);
      if (var8 >= 0 && var8 <= 23 && var9 >= 0 && var9 <= 59) {
         var8 -= 6;
         if (var8 < 0) {
            var8 += 24;
         }

         return var8 * 1000 + (int)(var9 / 60.0 * 1000.0);
      } else {
         Config.Method252("Invalid time: " + string);
         return -1;
      }
   }

   private boolean parseBoolean(String string, boolean boolean2) {
      if (string == null) {
         return boolean2;
      }

      if (string.equalsIgnoreCase("true")) {
         return true;
      }

      if (string.equalsIgnoreCase("false")) {
         return false;
      }

      Config.Method252("Unknown boolean: " + string);
      return boolean2;
   }

   private float parseFloat(String string, float float2) {
      if (string == null) {
         return float2;
      } else {
         float var6 = Config.parseFloat(string, Float.MIN_VALUE);
         if (var6 == Float.MIN_VALUE) {
            Config.Method252("Invalid value: " + string);
            return float2;
         } else {
            return var6;
         }
      }
   }

   private float[] parseAxis(String string, float[] arr) {
      if (string == null) {
         return arr;
      }

      String[] var6 = Config.tokenize(string, " ");
      if (var6.length != 3) {
         Config.Method252("Invalid axis: " + string);
         return arr;
      }

      float[] var7 = new float[3];

      for (int var8 = 0; var8 < var6.length; var8++) {
         var7[var8] = Config.parseFloat(var6[var8], Float.MIN_VALUE);
         if (var7[var8] == Float.MIN_VALUE) {
            Config.Method252("Invalid axis: " + string);
            return arr;
         }

         if (var7[var8] < -1.0F || var7[var8] > 1.0F) {
            Config.Method252("Invalid axis values: " + string);
            return arr;
         }
      }

      float var11 = var7[0];
      float var9 = var7[1];
      float var10 = var7[2];
      if (var11 * var11 + var9 * var9 + var10 * var10 < 1.0E-5F) {
         Config.Method252("Invalid axis values: " + string);
         return arr;
      } else {
         return new float[]{var10, var9, -var11};
      }
   }

   public boolean isValid(String string) {
      if (this.source == null) {
         Config.Method252("No source texture: " + string);
         return false;
      }

      this.source = TextureUtils.fixResourcePath(this.source, TextureUtils.getBasePath(string));
      if (this.startFadeIn >= 0 && this.endFadeIn >= 0 && this.endFadeOut >= 0) {
         int var5 = this.normalizeTime(this.endFadeIn - this.startFadeIn);
         if (this.startFadeOut < 0) {
            this.startFadeOut = this.normalizeTime(this.endFadeOut - var5);
            if (this.timeBetween(this.startFadeOut, this.startFadeIn, this.endFadeIn)) {
               this.startFadeOut = this.endFadeIn;
            }
         }

         int var6 = this.normalizeTime(this.startFadeOut - this.endFadeIn);
         int var7 = this.normalizeTime(this.endFadeOut - this.startFadeOut);
         int var8 = this.normalizeTime(this.startFadeIn - this.endFadeOut);
         int var9 = var5 + var6 + var7 + var8;
         if (var9 != 24000) {
            Config.Method252("Invalid fadeIn/fadeOut times, sum is not 24h: " + var9);
            return false;
         } else if (this.speed < 0.0F) {
            Config.Method252("Invalid speed: " + this.speed);
            return false;
         } else if (this.daysLoop <= 0) {
            Config.Method252("Invalid daysLoop: " + this.daysLoop);
            return false;
         } else {
            return true;
         }
      } else {
         Config.Method252("Invalid times, required are: startFadeIn, endFadeIn and endFadeOut.");
         return false;
      }
   }

   private int normalizeTime(int integer) {
      while (integer >= 24000) {
         integer -= 24000;
      }

      while (integer < 0) {
         integer += 24000;
      }

      return integer;
   }

   public void render(World world, int integer, float float3, float float4, float float5) {
      float var9 = this.getPositionBrightness(world);
      float var10 = this.getWeatherBrightness(float4, float5);
      float var11 = this.getFadeBrightness(integer);
      float var12 = Config.Method256(var9 * var10 * var11, 0.0F, 1.0F);
      if (var12 >= 1.0E-4F) {
         GlStateManager.bindTexture(this.textureId);
         Blender.setupBlend(this.blend, var12);
         GlStateManager.pushMatrix();
         if (this.rotate) {
            float var13 = 0.0F;
            if (this.speed != Math.round(this.speed)) {
               long var14 = (world.getWorldTime() + 18000L) / 24000L;
               double var16 = this.speed % 1.0F;
               var13 = (float)(var14 * var16 % 1.0);
            }

            GlStateManager.rotate(360.0F * (var13 + float3 * this.speed), this.axis[0], this.axis[1], this.axis[2]);
         }

         Tessellator var18 = Tessellator.getInstance();
         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
         this.renderSide(var18, 4);
         GlStateManager.pushMatrix();
         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
         this.renderSide(var18, 1);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
         this.renderSide(var18, 0);
         GlStateManager.popMatrix();
         GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
         this.renderSide(var18, 5);
         GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
         this.renderSide(var18, 2);
         GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
         this.renderSide(var18, 3);
         GlStateManager.popMatrix();
      }
   }

   private float getPositionBrightness(World world) {
      if (this.biomes == null && this.heights == null) {
         return 1.0F;
      }

      float var5 = this.getPositionBrightnessRaw(world);
      if (this.smoothPositionBrightness == null) {
         this.smoothPositionBrightness = new SmoothFloat(var5, this.transition);
      }

      return this.smoothPositionBrightness.getSmoothValue(var5);
   }

   private float getPositionBrightnessRaw(World world) {
      Entity var5 = Minecraft.getMinecraft().getRenderViewEntity();
      if (var5 == null) {
         return 0.0F;
      }

      BlockPos var6 = var5.getPosition();
      if (this.biomes != null) {
         BiomeGenBase var7 = world.getBiomeGenForCoords(var6);
         if (var7 == null) {
            return 0.0F;
         }

         if (!Matches.biome(var7, this.biomes)) {
            return 0.0F;
         }
      }

      return this.heights != null && !this.heights.isInRange(var6.getY()) ? 0.0F : 1.0F;
   }

   private float getWeatherBrightness(float float1, float float2) {
      float var6 = 1.0F - float1;
      float var7 = float1 - float2;
      float var8 = 0.0F;
      if (this.weatherClear) {
         var8 = 0.0F + var6;
      }

      if (this.weatherRain) {
         var8 += var7;
      }

      if (this.weatherThunder) {
         var8 += float2;
      }

      return NumUtils.Method970(var8, 0.0F, 1.0F);
   }

   private float getFadeBrightness(int integer) {
      if (this.timeBetween(integer, this.startFadeIn, this.endFadeIn)) {
         int var7 = this.normalizeTime(this.endFadeIn - this.startFadeIn);
         return (float)this.normalizeTime(integer - this.startFadeIn) / var7;
      } else if (this.timeBetween(integer, this.endFadeIn, this.startFadeOut)) {
         return 1.0F;
      } else if (this.timeBetween(integer, this.startFadeOut, this.endFadeOut)) {
         int var5 = this.normalizeTime(this.endFadeOut - this.startFadeOut);
         int var6 = this.normalizeTime(integer - this.startFadeOut);
         return 1.0F - (float)var6 / var5;
      } else {
         return 0.0F;
      }
   }

   private void renderSide(Tessellator tessellator, int integer) {
      WorldRenderer var5 = tessellator.getWorldRenderer();
      double var6 = integer % 3 / 3.0;
      double var8 = integer / 3 / 2.0;
      var5.begin(7, DefaultVertexFormats.POSITION_TEX);
      var5.pos(-100.0, -100.0, -100.0).tex(var6, var8).endVertex();
      var5.pos(-100.0, -100.0, 100.0).tex(var6, var8 + 0.5).endVertex();
      var5.pos(100.0, -100.0, 100.0).tex(var6 + 0.3333333333333333, var8 + 0.5).endVertex();
      var5.pos(100.0, -100.0, -100.0).tex(var6 + 0.3333333333333333, var8).endVertex();
      tessellator.draw();
   }

   public boolean isActive(World world, int integer) {
      if (world != this.lastWorld) {
         this.lastWorld = world;
         this.smoothPositionBrightness = null;
      }

      if (this.timeBetween(integer, this.endFadeOut, this.startFadeIn)) {
         return false;
      }

      if (this.days == null) {
         return true;
      }

      long var8 = world.getWorldTime() - this.startFadeIn;

      while (var8 < 0L) {
         var8 += 24000L * this.daysLoop;
      }

      int var11 = (int)(var8 / 24000L) % this.daysLoop;
      return this.days.isInRange(var11);
   }

   private boolean timeBetween(int integer1, int integer2, int integer3) {
      return integer2 <= integer3 ? integer1 >= integer2 && integer1 <= integer3 : integer1 >= integer2 || integer1 <= integer3;
   }

   @Override
   public String toString() {
      int var3 = this.endFadeOut;
      int var4 = this.startFadeOut;
      int var5 = this.endFadeIn;
      int var6 = this.startFadeIn;
      return this.source + ", " + var6 + "-" + var5 + " " + var4 + "-" + var3;
   }
}
