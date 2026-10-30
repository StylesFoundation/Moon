package net.minecraft.command;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class CommandStats extends CommandBase {
   @Override
   public String getCommandName() {
      return "stats";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.stats.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length < 1) {
         throw new WrongUsageException("commands.stats.usage");
      }

      boolean var6;
      if (arr[0].equals("entity")) {
         var6 = false;
      } else {
         if (!arr[0].equals("block")) {
            throw new WrongUsageException("commands.stats.usage");
         }

         var6 = true;
      }

      int var7;
      if (var6) {
         if (arr.length < 5) {
            throw new WrongUsageException("commands.stats.block.usage");
         }

         var7 = 4;
      } else {
         if (arr.length < 3) {
            throw new WrongUsageException("commands.stats.entity.usage");
         }

         var7 = 2;
      }

      String var8 = arr[var7++];
      if ("set".equals(var8)) {
         if (arr.length < var7 + 3) {
            if (var7 == 5) {
               throw new WrongUsageException("commands.stats.block.set.usage");
            }

            throw new WrongUsageException("commands.stats.entity.set.usage");
         }
      } else {
         if (!"clear".equals(var8)) {
            throw new WrongUsageException("commands.stats.usage");
         }

         if (arr.length < var7 + 1) {
            if (var7 == 5) {
               throw new WrongUsageException("commands.stats.block.clear.usage");
            }

            throw new WrongUsageException("commands.stats.entity.clear.usage");
         }
      }

      CommandResultStats.Type var9 = CommandResultStats.Type.getTypeByName(arr[var7++]);
      if (var9 == null) {
         throw new CommandException("commands.stats.failed");
      }

      World var10 = iCommandSender.getEntityWorld();
      CommandResultStats var11;
      if (var6) {
         BlockPos var12 = parseBlockPos(iCommandSender, arr, 1, false);
         TileEntity var13 = var10.getTileEntity(var12);
         if (var13 == null) {
            throw new CommandException("commands.stats.noCompatibleBlock", var12.getX(), var12.getY(), var12.getZ());
         }

         if (var13 instanceof TileEntityCommandBlock) {
            var11 = ((TileEntityCommandBlock)var13).getCommandResultStats();
         } else {
            if (!(var13 instanceof TileEntitySign)) {
               throw new CommandException("commands.stats.noCompatibleBlock", var12.getX(), var12.getY(), var12.getZ());
            }

            var11 = ((TileEntitySign)var13).getStats();
         }
      } else {
         var11 = getEntity(iCommandSender, arr[1]).getCommandStats();
      }

      if ("set".equals(var8)) {
         String var17 = arr[var7++];
         String var19 = arr[var7];
         if (var17.length() == 0 || var19.length() == 0) {
            throw new CommandException("commands.stats.failed");
         }

         CommandResultStats.setScoreBoardStat(var11, var9, var17, var19);
         notifyOperators(iCommandSender, this, "commands.stats.success", var9.getTypeName(), var19, var17);
      } else if ("clear".equals(var8)) {
         CommandResultStats.setScoreBoardStat(var11, var9, null, null);
         notifyOperators(iCommandSender, this, "commands.stats.cleared", var9.getTypeName());
      }

      if (var6) {
         BlockPos var18 = parseBlockPos(iCommandSender, arr, 1, false);
         var10.getTileEntity(var18).markDirty();
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length == 1
         ? getListOfStringsMatchingLastWord(arr, "entity", "block")
         : (
            arr.length == 2 && arr[0].equals("entity")
               ? getListOfStringsMatchingLastWord(arr, this.func_175776_d())
               : (
                  arr.length >= 2 && arr.length <= 4 && arr[0].equals("block")
                     ? func_175771_a(arr, 1, blockPos)
                     : (
                        (arr.length != 3 || !arr[0].equals("entity")) && (arr.length != 5 || !arr[0].equals("block"))
                           ? (
                              (arr.length != 4 || !arr[0].equals("entity")) && (arr.length != 6 || !arr[0].equals("block"))
                                 ? (
                                    arr.length == 6 && arr[0].equals("entity") || arr.length == 8 && arr[0].equals("block")
                                       ? getListOfStringsMatchingLastWord(arr, this.func_175777_e())
                                       : null
                                 )
                                 : getListOfStringsMatchingLastWord(arr, CommandResultStats.Type.getTypeNames())
                           )
                           : getListOfStringsMatchingLastWord(arr, "set", "clear")
                     )
               )
         );
   }

   protected String[] func_175776_d() {
      return MinecraftServer.getServer().getAllUsernames();
   }

   protected List<String> func_175777_e() {
      Collection<ScoreObjective> var4 = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard().getScoreObjectives();
      ArrayList<String> var5 = Lists.newArrayList();

      for (ScoreObjective var7 : var4) {
         if (!var7.getCriteria().isReadOnly()) {
            var5.add(var7.getName());
         }
      }

      return var5;
   }

   @Override
   public boolean isUsernameIndex(String[] arr, int integer) {
      return arr.length > 0 && arr[0].equals("entity") && integer == 1;
   }
}
