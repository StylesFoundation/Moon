package net.minecraft.command.server;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandTestFor extends CommandBase {
   @Override
   public String getCommandName() {
      return "testfor";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.testfor.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length < 1) {
         throw new WrongUsageException("commands.testfor.usage");
      }

      Entity var6 = getEntity(iCommandSender, arr[0]);
      NBTTagCompound var7 = null;
      if (arr.length >= 2) {
         try {
            var7 = JsonToNBT.getTagFromJson(buildString(arr, 1));
         } catch (NBTException var9) {
            throw new CommandException("commands.testfor.tagError", var9.getMessage());
         }
      }

      if (var7 != null) {
         NBTTagCompound var8 = new NBTTagCompound();
         var6.writeToNBT(var8);
         if (!NBTUtil.func_181123_a(var7, var8, true)) {
            throw new CommandException("commands.testfor.failure", var6.getName());
         }
      }

      notifyOperators(iCommandSender, this, "commands.testfor.success", var6.getName());
   }

   @Override
   public boolean isUsernameIndex(String[] arr, int integer) {
      return integer == 0;
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length == 1 ? getListOfStringsMatchingLastWord(arr, MinecraftServer.getServer().getAllUsernames()) : null;
   }
}
