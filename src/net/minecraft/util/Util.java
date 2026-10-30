package net.minecraft.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import org.apache.logging.log4j.Logger;

public class Util {
   public static Util.EnumOS getOSType() {
      String var2 = System.getProperty("os.name").toLowerCase();
      return var2.contains("win")
         ? Util.EnumOS.WINDOWS
         : (
            var2.contains("mac")
               ? Util.EnumOS.OSX
               : (
                  var2.contains("solaris")
                     ? Util.EnumOS.SOLARIS
                     : (
                        var2.contains("sunos")
                           ? Util.EnumOS.SOLARIS
                           : (var2.contains("linux") ? Util.EnumOS.LINUX : (var2.contains("unix") ? Util.EnumOS.LINUX : Util.EnumOS.UNKNOWN))
                     )
               )
         );
   }

   public static <V> V runTask(FutureTask<V> futureTask, Logger logger) {
      try {
         futureTask.run();
         return (V)futureTask.get();
      } catch (ExecutionException var8) {
         Throwable var7 = var8.getCause();
         if (var7 instanceof OutOfMemoryError) {
            throw (OutOfMemoryError)var7;
         }
      } catch (InterruptedException var9) {
      }

      return null;
   }

   public enum EnumOS {
      LINUX,
      SOLARIS,
      WINDOWS,
      OSX,
      UNKNOWN;

      private static Util.EnumOS[] Method616() {
         return new Util.EnumOS[]{LINUX, SOLARIS, WINDOWS, OSX, UNKNOWN};
      }
   }
}
