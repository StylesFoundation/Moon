package net.minecraft.command;

import java.util.List;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class CommandBlockData extends CommandBase {
   @Override
   public String getCommandName() {
      return "blockdata";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.blockdata.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length < 4) {
         throw new WrongUsageException("commands.blockdata.usage");
      }

      iCommandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
      BlockPos var6 = parseBlockPos(iCommandSender, arr, 0, false);
      World var7 = iCommandSender.getEntityWorld();
      if (!var7.Method322(var6)) {
         throw new CommandException("commands.blockdata.outOfWorld");
      }

      TileEntity var8 = var7.getTileEntity(var6);
      if (var8 == null) {
         throw new CommandException("commands.blockdata.notValid");
      }

      NBTTagCompound var9 = new NBTTagCompound();
      var8.writeToNBT(var9);
      NBTTagCompound var10 = (NBTTagCompound)var9.copy();

      NBTTagCompound var11;
      try {
         var11 = JsonToNBT.getTagFromJson(getChatComponentFromNthArg(iCommandSender, arr, 3).getUnformattedText());
      } catch (NBTException var13) {
         throw new CommandException("commands.blockdata.tagError", var13.getMessage());
      }

      var9.merge(var11);
      var9.setInteger("x", var6.getX());
      var9.setInteger("y", var6.getY());
      var9.setInteger("z", var6.getZ());
      if (var9.equals(var10)) {
         throw new CommandException("commands.blockdata.failed", var9.toString());
      }

      var8.readFromNBT(var9);
      var8.markDirty();
      var7.markBlockForUpdate(var6);
      iCommandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
      notifyOperators(iCommandSender, this, "commands.blockdata.success", var9.toString());
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length > 0 && arr.length <= 3 ? func_175771_a(arr, 0, blockPos) : null;
   }
}
