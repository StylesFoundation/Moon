package net.minecraft.crash;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.Callable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;

public class CrashReportCategory {
   private final CrashReport crashReport;
   private final String name;
   private final List<CrashReportCategory.Entry> children = Lists.newArrayList();
   private StackTraceElement[] stackTrace = new StackTraceElement[0];

   public CrashReportCategory(CrashReport crashReport, String string) {
      this.crashReport = crashReport;
      this.name = string;
   }

   public static String getCoordinateInfo(double double1, double double2, double double3) {
      return String.format("%.2f,%.2f,%.2f - %s", double1, double2, double3, getCoordinateInfo(new BlockPos(double1, double2, double3)));
   }

   public static String getCoordinateInfo(BlockPos blockPos) {
      int var3 = blockPos.getX();
      int var4 = blockPos.getY();
      int var5 = blockPos.getZ();
      StringBuilder var6 = new StringBuilder();

      try {
         var6.append(String.format("World: (%d,%d,%d)", var3, var4, var5));
      } catch (Throwable var19) {
         var6.append("(Error finding world loc)");
      }

      var6.append(", ");

      try {
         int var7 = var3 >> 4;
         int var8 = var5 >> 4;
         int var9 = var3 & 15;
         int var10 = var4 >> 4;
         int var11 = var5 & 15;
         int var12 = var7 << 4;
         int var13 = var8 << 4;
         int var14 = (var7 + 1 << 4) - 1;
         int var15 = (var8 + 1 << 4) - 1;
         var6.append(
            String.format("Chunk: (at %d,%d,%d in %d,%d; contains blocks %d,0,%d to %d,255,%d)", var9, var10, var11, var7, var8, var12, var13, var14, var15)
         );
      } catch (Throwable var18) {
         var6.append("(Error finding chunk loc)");
      }

      var6.append(", ");

      try {
         int var20 = var3 >> 9;
         int var21 = var5 >> 9;
         int var22 = var20 << 5;
         int var23 = var21 << 5;
         int var24 = (var20 + 1 << 5) - 1;
         int var25 = (var21 + 1 << 5) - 1;
         int var26 = var20 << 9;
         int var27 = var21 << 9;
         int var28 = (var20 + 1 << 9) - 1;
         int var16 = (var21 + 1 << 9) - 1;
         var6.append(
            String.format(
               "Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,0,%d to %d,255,%d)",
               var20,
               var21,
               var22,
               var23,
               var24,
               var25,
               var26,
               var27,
               var28,
               var16
            )
         );
      } catch (Throwable var17) {
         var6.append("(Error finding world loc)");
      }

      return var6.toString();
   }

   public void addCrashSectionCallable(String string, Callable<String> callable) {
      try {
         this.addCrashSection(string, callable.call());
      } catch (Throwable var4) {
         this.addCrashSectionThrowable(string, var4);
      }
   }

   public void addCrashSection(String string, Object object) {
      this.children.add(new CrashReportCategory.Entry(string, object));
   }

   public void addCrashSectionThrowable(String string, Throwable throwable) {
      this.addCrashSection(string, throwable);
   }

   public int getPrunedStackTrace(int integer) {
      StackTraceElement[] var5 = Thread.currentThread().getStackTrace();
      if (var5.length == 0) {
         return 0;
      }

      this.stackTrace = new StackTraceElement[var5.length - 3 - integer];
      System.arraycopy(var5, 3 + integer, this.stackTrace, 0, this.stackTrace.length);
      return this.stackTrace.length;
   }

   public boolean firstTwoElementsOfStackTraceMatch(StackTraceElement stackTraceElement1, StackTraceElement stackTraceElement2) {
      if (this.stackTrace.length != 0 && stackTraceElement1 != null) {
         StackTraceElement var6 = this.stackTrace[0];
         if (var6.isNativeMethod() == stackTraceElement1.isNativeMethod()
            && var6.getClassName().equals(stackTraceElement1.getClassName())
            && var6.getFileName().equals(stackTraceElement1.getFileName())
            && var6.getMethodName().equals(stackTraceElement1.getMethodName())) {
            if (stackTraceElement2 == null == this.stackTrace.length > 1) {
               return false;
            }

            if (stackTraceElement2 != null && !this.stackTrace[1].equals(stackTraceElement2)) {
               return false;
            }

            this.stackTrace[0] = stackTraceElement1;
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void trimStackTraceEntriesFromBottom(int integer) {
      StackTraceElement[] var2 = new StackTraceElement[this.stackTrace.length - integer];
      System.arraycopy(this.stackTrace, 0, var2, 0, var2.length);
      this.stackTrace = var2;
   }

   public void appendToStringBuilder(StringBuilder stringBuilder) {
      stringBuilder.append("-- ").append(this.name).append(" --\n");
      stringBuilder.append("Details:");

      for (CrashReportCategory.Entry var6 : this.children) {
         stringBuilder.append("\n\t");
         stringBuilder.append(var6.getKey());
         stringBuilder.append(": ");
         stringBuilder.append(var6.Method492());
      }

      if (this.stackTrace != null && this.stackTrace.length > 0) {
         stringBuilder.append("\nStacktrace:");

         for (StackTraceElement var8 : this.stackTrace) {
            stringBuilder.append("\n\tat ");
            stringBuilder.append(var8.toString());
         }
      }
   }

   public StackTraceElement[] getStackTrace() {
      return this.stackTrace;
   }

   public static void addBlockInfo(CrashReportCategory crashReportCategory, BlockPos blockPos, Block block, int integer) {
      int var4 = Block.getIdFromBlock(block);
      crashReportCategory.addCrashSectionCallable("Block type", () -> {
         try {
            return String.format("ID #%d (%s // %s)", var4, block.Method440(), block.getClass().getCanonicalName());
         } catch (Throwable var2) {
            return "ID #" + var4;
         }
      });
      crashReportCategory.addCrashSectionCallable("Block data value", () -> {
         if (integer < 0) {
            return "Unknown? (Got " + integer + ")";
         }

         String s = String.format("%4s", Integer.toBinaryString(integer)).replace(" ", "0");
         return String.format("%1$d / 0x%1$X / 0b%2$s", integer, s);
      });
      crashReportCategory.addCrashSectionCallable("Block location", () -> getCoordinateInfo(blockPos));
   }

   public static void addBlockInfo(CrashReportCategory crashReportCategory, BlockPos blockPos, IBlockState iBlockState) {
      crashReportCategory.addCrashSectionCallable("Block", iBlockState::toString);
      crashReportCategory.addCrashSectionCallable("Block location", () -> getCoordinateInfo(blockPos));
   }

   static class Entry {
      private final String key;
      private final String value;

      public Entry(String string, Object object) {
         this.key = string;
         if (object == null) {
            this.value = "~~NULL~~";
         } else if (object instanceof Throwable var6) {
            this.value = "~~ERROR~~ " + var6.getClass().getSimpleName() + ": " + var6.getMessage();
         } else {
            this.value = object.toString();
         }
      }

      public String getKey() {
         return this.key;
      }

      public String Method492() {
         return this.value;
      }
   }
}
