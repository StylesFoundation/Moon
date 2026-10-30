package net.minecraft.profiler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.minecraft.src.Config;
import net.optifine.Lagometer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Profiler {
   private static final Logger logger = LogManager.getLogger();
   private final List<String> sectionList = Lists.newArrayList();
   private final List<Long> timestampList = Lists.newArrayList();
   public boolean profilingEnabled;
   private String profilingSection = "";
   private final Map<String, Long> profilingMap = Maps.newHashMap();
   public boolean profilerGlobalEnabled = true;
   private boolean profilerLocalEnabled = this.profilerGlobalEnabled;
   private static final int HASH_SCHEDULED_EXECUTABLES = 530493052;
   private static final int HASH_TICK = 3559837;
   private static final int HASH_PRE_RENDER_ERRORS = 477544164;
   private static final int HASH_RENDER = -934592106;
   private static final int HASH_DISPLAY = 1671764162;

   public void clearProfiling() {
      this.profilingMap.clear();
      this.profilingSection = "";
      this.sectionList.clear();
      this.profilerLocalEnabled = this.profilerGlobalEnabled;
   }

   public void startSection(String string) {
      if (Lagometer.isActive()) {
         int var5 = string.hashCode();
         if (var5 == HASH_SCHEDULED_EXECUTABLES && string.equals("scheduledExecutables")) {
            Lagometer.timerScheduledExecutables.start();
         } else if (var5 == HASH_TICK && string.equals("tick") && Config.isMinecraftThread()) {
            Lagometer.timerScheduledExecutables.end();
            Lagometer.timerTick.start();
         } else if (var5 == HASH_PRE_RENDER_ERRORS && string.equals("preRenderErrors")) {
            Lagometer.timerTick.end();
         }
      }

      if (this.profilerLocalEnabled && this.profilingEnabled) {
         if (this.profilingSection.length() > 0) {
            this.profilingSection = this.profilingSection + ".";
         }

         this.profilingSection = this.profilingSection + string;
         this.sectionList.add(this.profilingSection);
         this.timestampList.add(System.nanoTime());
      }
   }

   public void endSection() {
      if (this.profilerLocalEnabled && this.profilingEnabled) {
         long var4 = System.nanoTime();
         long var6 = this.timestampList.remove(this.timestampList.size() - 1);
         this.sectionList.remove(this.sectionList.size() - 1);
         long var8 = var4 - var6;
         if (this.profilingMap.containsKey(this.profilingSection)) {
            this.profilingMap.put(this.profilingSection, this.profilingMap.get(this.profilingSection) + var8);
         } else {
            this.profilingMap.put(this.profilingSection, var8);
         }

         if (var8 > 100000000L) {
            logger.warn("Something's taking too long! '" + this.profilingSection + "' took aprox " + var8 / 1000000.0 + " ms");
         }

         this.profilingSection = !this.sectionList.isEmpty() ? this.sectionList.get(this.sectionList.size() - 1) : "";
      }
   }

   public List<Profiler.Result> getProfilingData(String string) {
      if (!this.profilingEnabled) {
         return null;
      }

      long var5 = this.profilingMap.getOrDefault("root", 0L);
      long var7 = this.profilingMap.getOrDefault(string, -1L);
      ArrayList var9 = Lists.newArrayList();
      if (string.length() > 0) {
         string = string + ".";
      }

      long var10 = 0L;

      for (String var13 : this.profilingMap.keySet()) {
         if (var13.length() > string.length() && var13.startsWith(string) && var13.indexOf(".", string.length() + 1) < 0) {
            var10 += this.profilingMap.get(var13);
         }
      }

      float var22 = (float)var10;
      if (var10 < var7) {
         var10 = var7;
      }

      if (var5 < var10) {
         var5 = var10;
      }

      for (String var14 : this.profilingMap.keySet()) {
         if (var14.length() > string.length() && var14.startsWith(string) && var14.indexOf(".", string.length() + 1) < 0) {
            long var15 = this.profilingMap.get(var14);
            double var17 = var15 * 100.0 / var10;
            double var19 = var15 * 100.0 / var5;
            String var21 = var14.substring(string.length());
            var9.add(new Profiler.Result(var21, var17, var19));
         }
      }

      this.profilingMap.replaceAll((k, v) -> v * 950L / 1000L);
      if ((float)var10 > var22) {
         var9.add(new Profiler.Result("unspecified", ((float)var10 - var22) * 100.0 / var10, ((float)var10 - var22) * 100.0 / var5));
      }

      Collections.sort(var9);
      var9.add(0, new Profiler.Result(string, 100.0, var10 * 100.0 / var5));
      return var9;
   }

   public void endStartSection(String string) {
      if (this.profilerLocalEnabled) {
         this.endSection();
         this.startSection(string);
      }
   }

   public String getNameOfLastSection() {
      return this.sectionList.size() == 0 ? "[UNKNOWN]" : this.sectionList.get(this.sectionList.size() - 1);
   }

   public void startSection(Class<?> class1) {
      if (this.profilingEnabled) {
         this.startSection(class1.getSimpleName());
      }
   }

   public static final class Result implements Comparable<Profiler.Result> {
      public double field_76332_a;
      public double field_76330_b;
      public String field_76331_c;

      public Result(String string, double double2, double double3) {
         this.field_76331_c = string;
         this.field_76332_a = double2;
         this.field_76330_b = double3;
      }

      public int compareTo(Profiler.Result result) {
         return result.field_76332_a < this.field_76332_a
            ? -1
            : (result.field_76332_a > this.field_76332_a ? 1 : result.field_76331_c.compareTo(this.field_76331_c));
      }

      public int getColor() {
         return (this.field_76331_c.hashCode() & 11184810) + 4473924;
      }
   }
}
