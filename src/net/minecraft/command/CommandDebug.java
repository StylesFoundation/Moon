package net.minecraft.command;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandDebug extends CommandBase {
   private static final Logger logger = LogManager.getLogger();
   private long profileStartTime;
   private int profileStartTick;

   @Override
   public String getCommandName() {
      return "debug";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 3;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.debug.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length < 1) {
         throw new WrongUsageException("commands.debug.usage");
      }

      if (arr[0].equals("start")) {
         if (arr.length != 1) {
            throw new WrongUsageException("commands.debug.usage");
         }

         notifyOperators(iCommandSender, this, "commands.debug.start");
         MinecraftServer.getServer().enableProfiling();
         this.profileStartTime = MinecraftServer.getCurrentTimeMillis();
         this.profileStartTick = MinecraftServer.getServer().getTickCounter();
      } else {
         if (!arr[0].equals("stop")) {
            throw new WrongUsageException("commands.debug.usage");
         }

         if (arr.length != 1) {
            throw new WrongUsageException("commands.debug.usage");
         }

         if (!MinecraftServer.getServer().theProfiler.profilingEnabled) {
            throw new CommandException("commands.debug.notStarted");
         }

         long var6 = MinecraftServer.getCurrentTimeMillis();
         int var8 = MinecraftServer.getServer().getTickCounter();
         long var9 = var6 - this.profileStartTime;
         int var11 = var8 - this.profileStartTick;
         this.saveProfileResults(var9, var11);
         MinecraftServer.getServer().theProfiler.profilingEnabled = false;
         notifyOperators(iCommandSender, this, "commands.debug.stop", (float)var9 / 1000.0F, var11);
      }
   }

   private void saveProfileResults(long long1, int integer) {
      File var4 = new File(
         MinecraftServer.getServer().getFile("debug"), "profile-results-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + ".txt"
      );
      var4.getParentFile().mkdirs();

      try {
         FileWriter var5 = new FileWriter(var4);
         var5.write(this.getProfileResults(long1, integer));
         var5.close();
      } catch (Throwable var6) {
         logger.error("Could not save profiler results to " + var4, var6);
      }
   }

   private String getProfileResults(long long1, int integer) {
      StringBuilder var6 = new StringBuilder();
      var6.append("---- Minecraft Profiler Results ----\n");
      var6.append("// ");
      var6.append(getWittyComment());
      var6.append("\n\n");
      var6.append("Time span: ").append(long1).append(" ms\n");
      var6.append("Tick span: ").append(integer).append(" ticks\n");
      var6.append("// This is approximately ")
         .append(String.format("%.2f", integer / ((float)long1 / 1000.0F)))
         .append(" ticks per second. It should be ")
         .append(20)
         .append(" ticks per second\n\n");
      var6.append("--- BEGIN PROFILE DUMP ---\n\n");
      this.func_147202_a(0, "root", var6);
      var6.append("--- END PROFILE DUMP ---\n\n");
      return var6.toString();
   }

   private void func_147202_a(int integer, String string, StringBuilder stringBuilder) {
      List var7 = MinecraftServer.getServer().theProfiler.getProfilingData(string);
      if (var7 != null && var7.size() >= 3) {
         for (int var8 = 1; var8 < var7.size(); var8++) {
            Profiler.Result var9 = (Profiler.Result)var7.get(var8);
            stringBuilder.append(String.format("[%02d] ", integer));
            stringBuilder.append(" ".repeat(Math.max(0, integer)));
            stringBuilder.append(var9.field_76331_c)
               .append(" - ")
               .append(String.format("%.2f", var9.field_76332_a))
               .append("%/")
               .append(String.format("%.2f", var9.field_76330_b))
               .append("%\n");
            if (!var9.field_76331_c.equals("unspecified")) {
               try {
                  this.func_147202_a(integer + 1, string + "." + var9.field_76331_c, stringBuilder);
               } catch (Exception var11) {
                  stringBuilder.append("[[ EXCEPTION ").append(var11).append(" ]]");
               }
            }
         }
      }
   }

   private static String getWittyComment() {
      String[] var2 = new String[]{
         "Shiny numbers!",
         "Am I not running fast enough? :(",
         "I'm working as hard as I can!",
         "Will I ever be good enough for you? :(",
         "Speedy. Zoooooom!",
         "Hello world",
         "40% better than a crash report.",
         "Now with extra numbers",
         "Now with less numbers",
         "Now with the same numbers",
         "You should add flames to things, it makes them go faster!",
         "Do you feel the need for... optimization?",
         "*cracks redstone whip*",
         "Maybe if you treated it better then it'll have more motivation to work faster! Poor server."
      };

      try {
         return var2[(int)(System.nanoTime() % var2.length)];
      } catch (Throwable var3) {
         return "Witty comment unavailable :(";
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length == 1 ? getListOfStringsMatchingLastWord(arr, "start", "stop") : null;
   }
}
