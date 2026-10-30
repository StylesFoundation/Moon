package net.minecraft.crash;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import net.minecraft.util.ReportedException;
import net.minecraft.world.gen.layer.IntCache;
import net.optifine.CrashReporter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CrashReport {
   private static final Logger logger = LogManager.getLogger();
   private final String description;
   private final Throwable cause;
   private final CrashReportCategory theReportCategory = new CrashReportCategory(this, "System Details");
   private final List<CrashReportCategory> crashReportSections = Lists.newArrayList();
   private File crashReportFile;
   private boolean firstCategoryInCrashReport = true;
   private StackTraceElement[] stacktrace = new StackTraceElement[0];
   private boolean reported = false;

   public CrashReport(String string, Throwable throwable) {
      this.description = string;
      this.cause = throwable;
      this.populateEnvironment();
   }

   private void populateEnvironment() {
      this.theReportCategory.addCrashSectionCallable("Minecraft Version", () -> "1.8.9");
      this.theReportCategory
         .addCrashSectionCallable(
            "Operating System", () -> System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version")
         );
      this.theReportCategory.addCrashSectionCallable("Java Version", () -> System.getProperty("java.version") + ", " + System.getProperty("java.vendor"));
      this.theReportCategory
         .addCrashSectionCallable(
            "Java VM Version",
            () -> System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor")
         );
      this.theReportCategory.addCrashSectionCallable("Memory", () -> {
         Runtime var2 = Runtime.getRuntime();
         long var3 = var2.maxMemory();
         long var5 = var2.totalMemory();
         long var7 = var2.freeMemory();
         long var9 = var3 / 1024L / 1024L;
         long var11 = var5 / 1024L / 1024L;
         long var13 = var7 / 1024L / 1024L;
         return var7 + " bytes (" + var13 + " MB) / " + var5 + " bytes (" + var11 + " MB) up to " + var3 + " bytes (" + var9 + " MB)";
      });
      this.theReportCategory.addCrashSectionCallable("JVM Flags", () -> {
         List<String> var4 = ManagementFactory.getRuntimeMXBean().getInputArguments();
         int var5 = 0;
         StringBuilder var6 = new StringBuilder();

         for (String var8 : var4) {
            if (var8.startsWith("-X")) {
               if (var5++ > 0) {
                  var6.append(" ");
               }

               var6.append(var8);
            }
         }

         return String.format("%d total; %s", var5, var6);
      });
      this.theReportCategory.addCrashSectionCallable("IntCache", IntCache::getCacheSizes);
   }

   public String getDescription() {
      return this.description;
   }

   public Throwable getCrashCause() {
      return this.cause;
   }

   public void getSectionsInStringBuilder(StringBuilder stringBuilder) {
      if ((this.stacktrace == null || this.stacktrace.length == 0) && this.crashReportSections.size() > 0) {
         this.stacktrace = (StackTraceElement[])ArrayUtils.subarray(this.crashReportSections.get(0).getStackTrace(), 0, 1);
      }

      if (this.stacktrace != null && this.stacktrace.length > 0) {
         stringBuilder.append("-- Head --\n");
         stringBuilder.append("Stacktrace:\n");

         for (StackTraceElement var8 : this.stacktrace) {
            stringBuilder.append("\t").append("at ").append(var8.toString());
            stringBuilder.append("\n");
         }

         stringBuilder.append("\n");
      }

      for (CrashReportCategory var10 : this.crashReportSections) {
         var10.appendToStringBuilder(stringBuilder);
         stringBuilder.append("\n\n");
      }

      this.theReportCategory.appendToStringBuilder(stringBuilder);
   }

   public String getCauseStackTraceOrString() {
      StringWriter var4 = null;
      PrintWriter var5 = null;
      Throwable var6 = this.cause;
      if (var6.getMessage() == null) {
         if (var6 instanceof NullPointerException) {
            var6 = new NullPointerException(this.description);
         } else if (var6 instanceof StackOverflowError) {
            var6 = new StackOverflowError(this.description);
         } else if (var6 instanceof OutOfMemoryError) {
            var6 = new OutOfMemoryError(this.description);
         }

         var6.setStackTrace(this.cause.getStackTrace());
      }

      var6.toString();

      String var7;
      try {
         var4 = new StringWriter();
         var5 = new PrintWriter(var4);
         var6.printStackTrace(var5);
         var7 = var4.toString();
      } finally {
         IOUtils.closeQuietly(var4);
         IOUtils.closeQuietly(var5);
      }

      return var7;
   }

   public String getCompleteReport() {
      if (!this.reported) {
         this.reported = true;
         CrashReporter.onCrashReport(this, this.theReportCategory);
      }

      StringBuilder var4 = new StringBuilder();
      var4.append("---- Minecraft Crash Report ----\n");
      var4.append("// ");
      var4.append(getWittyComment());
      var4.append("\n\n");
      var4.append("Time: ");
      var4.append(new SimpleDateFormat().format(new Date()));
      var4.append("\n");
      var4.append("Description: ");
      var4.append(this.description);
      var4.append("\n\n");
      var4.append(this.getCauseStackTraceOrString());
      var4.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");
      var4.append("-".repeat(87));
      var4.append("\n\n");
      this.getSectionsInStringBuilder(var4);
      return var4.toString();
   }

   public File getFile() {
      return this.crashReportFile;
   }

   public boolean saveToFile(File file) {
      if (this.crashReportFile != null) {
         return false;
      }

      if (file.getParentFile() != null) {
         file.getParentFile().mkdirs();
      }

      try {
         FileWriter var5 = new FileWriter(file);
         var5.write(this.getCompleteReport());
         var5.close();
         this.crashReportFile = file;
         return true;
      } catch (Throwable var6) {
         logger.error("Could not save crash report to " + file, var6);
         return false;
      }
   }

   public CrashReportCategory getCategory() {
      return this.theReportCategory;
   }

   public CrashReportCategory makeCategory(String string) {
      return this.makeCategoryDepth(string, 1);
   }

   public CrashReportCategory makeCategoryDepth(String string, int integer) {
      CrashReportCategory var6 = new CrashReportCategory(this, string);
      if (this.firstCategoryInCrashReport) {
         int var7 = var6.getPrunedStackTrace(integer);
         StackTraceElement[] var8 = this.cause.getStackTrace();
         StackTraceElement var9 = null;
         StackTraceElement var10 = null;
         int var11 = var8.length - var7;
         if (var11 < 0) {
            System.out.println("Negative index in crash report handler (" + var8.length + "/" + var7 + ")");
         }

         if (var8 != null && 0 <= var11 && var11 < var8.length) {
            var9 = var8[var11];
            if (var8.length + 1 - var7 < var8.length) {
               var10 = var8[var8.length + 1 - var7];
            }
         }

         this.firstCategoryInCrashReport = var6.firstTwoElementsOfStackTraceMatch(var9, var10);
         if (var7 > 0 && !this.crashReportSections.isEmpty()) {
            CrashReportCategory var12 = this.crashReportSections.get(this.crashReportSections.size() - 1);
            var12.trimStackTraceEntriesFromBottom(var7);
         } else if (var8 != null && var8.length >= var7 && 0 <= var11 && var11 < var8.length) {
            this.stacktrace = new StackTraceElement[var11];
            System.arraycopy(var8, 0, this.stacktrace, 0, this.stacktrace.length);
         } else {
            this.firstCategoryInCrashReport = false;
         }
      }

      this.crashReportSections.add(var6);
      return var6;
   }

   private static String getWittyComment() {
      String[] var2 = new String[]{
         "Who set us up the TNT?",
         "Everything's going to plan. No, really, that was supposed to happen.",
         "Uh... Did I do that?",
         "Oops.",
         "Why did you do that?",
         "I feel sad now :(",
         "My bad.",
         "I'm sorry, Dave.",
         "I let you down. Sorry :(",
         "On the bright side, I bought you a teddy bear!",
         "Daisy, daisy...",
         "Oh - I know what I did wrong!",
         "Hey, that tickles! Hehehe!",
         "I blame Dinnerbone.",
         "You should try our sister game, Minceraft!",
         "Don't be sad. I'll do better next time, I promise!",
         "Don't be sad, have a hug! <3",
         "I just don't know what went wrong :(",
         "Shall we play a game?",
         "Quite honestly, I wouldn't worry myself about that.",
         "I bet Cylons wouldn't have this problem.",
         "Sorry :(",
         "Surprise! Haha. Well, this is awkward.",
         "Would you like a cupcake?",
         "Hi. I'm Minecraft, and I'm a crashaholic.",
         "Ooh. Shiny.",
         "This doesn't make any sense!",
         "Why is it breaking :(",
         "Don't do that.",
         "Ouch. That hurt :(",
         "You're mean.",
         "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]",
         "There are four lights!",
         "But it works on my machine."
      };

      try {
         return var2[(int)(System.nanoTime() % var2.length)];
      } catch (Throwable var3) {
         return "Witty comment unavailable :(";
      }
   }

   public static CrashReport makeCrashReport(Throwable throwable, String string) {
      CrashReport var5;
      if (throwable instanceof ReportedException) {
         var5 = ((ReportedException)throwable).getCrashReport();
      } else {
         var5 = new CrashReport(string, throwable);
      }

      return var5;
   }
}
