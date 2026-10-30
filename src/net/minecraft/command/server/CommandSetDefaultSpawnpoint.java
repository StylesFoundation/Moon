package net.minecraft.command.server;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandSetDefaultSpawnpoint extends CommandBase {
   @Override
   public String getCommandName() {
      return "setworldspawn";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.setworldspawn.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      BlockPos var6;
      if (arr.length == 0) {
         var6 = getCommandSenderAsPlayer(iCommandSender).getPosition();
      } else {
         if (arr.length != 3 || iCommandSender.getEntityWorld() == null) {
            throw new WrongUsageException("commands.setworldspawn.usage");
         }

         var6 = parseBlockPos(iCommandSender, arr, 0, true);
      }

      iCommandSender.getEntityWorld().setSpawnPoint(var6);
      MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(new S05PacketSpawnPosition(var6));
      notifyOperators(iCommandSender, this, "commands.setworldspawn.success", var6.getX(), var6.getY(), var6.getZ());
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length > 0 && arr.length <= 3 ? func_175771_a(arr, 0, blockPos) : null;
   }
}
