package net.optifine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.src.Config;
import net.optifine.http.FileUploadThread;
import net.optifine.http.IFileUploadListener;
import net.optifine.shaders.Shaders;

public class CrashReporter {
   public static void onCrashReport(CrashReport crashReport, CrashReportCategory crashReportCategory) {
      try {
         Throwable var5 = crashReport.getCrashCause();
         if (var5 == null) {
            return;
         }

         if (var5.getClass().getName().contains(".fml.client.SplashProgress")) {
            return;
         }

         if (var5.getClass() == Throwable.class) {
            return;
         }

         extendCrashReport(crashReportCategory);
         GameSettings var6 = Config.getGameSettings();
         if (var6 == null) {
            return;
         }

         if (!var6.snooperEnabled) {
            return;
         }

         String var7 = "http://optifine.net/crashReport";
         byte[] var9 = makeReport(crashReport).getBytes(StandardCharsets.US_ASCII);
         IFileUploadListener var10 = (string, arr, throwable) -> {};
         HashMap var11 = new HashMap();
         var11.put("OF-Version", Config.getVersion());
         var11.put("OF-Summary", makeSummary(crashReport));
         FileUploadThread var12 = new FileUploadThread(var7, var11, var9, var10);
         var12.setPriority(10);
         var12.start();
         Thread.sleep(1000L);
      } catch (Exception var13) {
         Config.Method251(var13.getClass().getName() + ": " + var13.getMessage());
      }
   }

   private static String makeReport(CrashReport crashReport) {
      String var10000 = Config.getVersion();
      String var3 = crashReport.getCompleteReport();
      String var4 = makeSummary(crashReport);
      String var5 = var10000;
      return "OptiFineVersion: " + var5 + "\nSummary: " + var4 + "\n\n" + var3 + "\n";
   }

   private static String makeSummary(CrashReport crashReport) {
      Throwable var4 = crashReport.getCrashCause();
      if (var4 == null) {
         return "Unknown";
      }

      StackTraceElement[] var5 = var4.getStackTrace();
      String var6 = "unknown";
      if (var5.length > 0) {
         var6 = var5[0].toString().trim();
      }

      String var10000 = var4.getClass().getName();
      String var10001 = var4.getMessage();
      String var10002 = crashReport.getDescription();
      String var7 = var6;
      String var8 = var10002;
      String var9 = var10001;
      return var10000 + ": " + var9 + " (" + var8 + ") [" + var7 + "]";
   }

   public static void extendCrashReport(CrashReportCategory crashReportCategory) {
      crashReportCategory.addCrashSection("OptiFine Version", Config.getVersion());
      crashReportCategory.addCrashSection("OptiFine Build", Config.getBuild());
      if (Config.getGameSettings() != null) {
         crashReportCategory.addCrashSection("Render Distance Chunks", Config.getChunkViewDistance() + "");
         crashReportCategory.addCrashSection("Mipmaps", Config.getMipmapLevels() + "");
         crashReportCategory.addCrashSection("Anisotropic Filtering", Config.getAnisotropicFilterLevel() + "");
         crashReportCategory.addCrashSection("Antialiasing", Config.getAntialiasingLevel() + "");
         crashReportCategory.addCrashSection("Multitexture", Config.isMultiTexture() + "");
      }

      crashReportCategory.addCrashSection("Shaders", Shaders.getShaderPackName() + "");
      crashReportCategory.addCrashSection("OpenGlVersion", Config.d + "");
      crashReportCategory.addCrashSection("OpenGlRenderer", Config.e + "");
      crashReportCategory.addCrashSection("OpenGlVendor", Config.f + "");
      crashReportCategory.addCrashSection("CpuCount", Config.getAvailableProcessors() + "");
   }
}
