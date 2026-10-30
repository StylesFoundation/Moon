package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

public class CommandShowSeed extends CommandBase {
   @Override
   public boolean canCommandSenderUseCommand(ICommandSender iCommandSender) {
      return MinecraftServer.getServer().isSinglePlayer() || super.canCommandSenderUseCommand(iCommandSender);
   }

   @Override
   public String getCommandName() {
      return "seed";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.seed.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      World var6 = iCommandSender instanceof EntityPlayer ? ((EntityPlayer)iCommandSender).worldObj : MinecraftServer.getServer().worldServerForDimension(0);
      iCommandSender.addChatMessage(new ChatComponentTranslation("commands.seed.success", var6.getSeed()));
   }
}
