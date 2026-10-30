package net.minecraft.command.server;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class CommandSetBlock extends CommandBase {
   @Override
   public String getCommandName() {
      return "setblock";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.setblock.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length < 4) {
         throw new WrongUsageException("commands.setblock.usage");
      }

      iCommandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
      BlockPos var6 = parseBlockPos(iCommandSender, arr, 0, false);
      Block var7 = CommandBase.getBlockByText(iCommandSender, arr[3]);
      int var8 = 0;
      if (arr.length >= 5) {
         var8 = parseInt(arr[4], 0, 15);
      }

      World var9 = iCommandSender.getEntityWorld();
      if (!var9.Method322(var6)) {
         throw new CommandException("commands.setblock.outOfWorld");
      }

      NBTTagCompound var10 = new NBTTagCompound();
      boolean var11 = false;
      if (arr.length >= 7 && var7.hasTileEntity()) {
         String var12 = getChatComponentFromNthArg(iCommandSender, arr, 6).getUnformattedText();

         try {
            var10 = JsonToNBT.getTagFromJson(var12);
            var11 = true;
         } catch (NBTException var15) {
            throw new CommandException("commands.setblock.tagError", var15.getMessage());
         }
      }

      if (arr.length >= 6) {
         if (arr[5].equals("destroy")) {
            var9.Method329(var6, true);
            if (var7 == Blocks.air) {
               notifyOperators(iCommandSender, this, "commands.setblock.success");
               return;
            }
         } else if (arr[5].equals("keep") && !var9.isAirBlock(var6)) {
            throw new CommandException("commands.setblock.noChange");
         }
      }

      TileEntity var16 = var9.getTileEntity(var6);
      if (var16 != null) {
         if (var16 instanceof IInventory) {
            ((IInventory)var16).clear();
         }

         var9.setBlockState(var6, Blocks.air.getDefaultState(), var7 == Blocks.air ? 2 : 4);
      }

      IBlockState var13 = var7.getStateFromMeta(var8);
      if (!var9.setBlockState(var6, var13, 2)) {
         throw new CommandException("commands.setblock.noChange");
      }

      if (var11) {
         TileEntity var14 = var9.getTileEntity(var6);
         if (var14 != null) {
            var10.setInteger("x", var6.getX());
            var10.setInteger("y", var6.getY());
            var10.setInteger("z", var6.getZ());
            var14.readFromNBT(var10);
         }
      }

      var9.notifyNeighborsRespectDebug(var6, var13.getBlock());
      iCommandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
      notifyOperators(iCommandSender, this, "commands.setblock.success");
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length > 0 && arr.length <= 3
         ? func_175771_a(arr, 0, blockPos)
         : (
            arr.length == 4
               ? getListOfStringsMatchingLastWord(arr, Block.blockRegistry.getKeys())
               : (arr.length == 6 ? getListOfStringsMatchingLastWord(arr, "replace", "destroy", "keep") : null)
         );
   }
}
