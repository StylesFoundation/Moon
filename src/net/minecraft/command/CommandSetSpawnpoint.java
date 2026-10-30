package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandSetSpawnpoint extends CommandBase {
   @Override
   public String getCommandName() {
      return "spawnpoint";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.spawnpoint.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length > 1 && arr.length < 4) {
         throw new WrongUsageException("commands.spawnpoint.usage");
      }

      EntityPlayerMP var6 = arr.length > 0 ? getPlayer(iCommandSender, arr[0]) : getCommandSenderAsPlayer(iCommandSender);
      BlockPos var7 = arr.length > 3 ? parseBlockPos(iCommandSender, arr, 1, true) : var6.getPosition();
      if (var6.worldObj != null) {
         var6.setSpawnPoint(var7, true);
         notifyOperators(iCommandSender, this, "commands.spawnpoint.success", var6.getName(), var7.getX(), var7.getY(), var7.getZ());
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length == 1
         ? getListOfStringsMatchingLastWord(arr, MinecraftServer.getServer().getAllUsernames())
         : (arr.length > 1 && arr.length <= 4 ? func_175771_a(arr, 1, blockPos) : null);
   }

   @Override
   public boolean isUsernameIndex(String[] arr, int integer) {
      return integer == 0;
   }
}
