package net.minecraft.command.server;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class CommandTestForBlock extends CommandBase {
   @Override
   public String getCommandName() {
      return "testforblock";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.testforblock.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length < 4) {
         throw new WrongUsageException("commands.testforblock.usage");
      }

      iCommandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
      BlockPos var6 = parseBlockPos(iCommandSender, arr, 0, false);
      Block var7 = Block.getBlockFromName(arr[3]);
      if (var7 == null) {
         throw new NumberInvalidException("commands.setblock.notFound", arr[3]);
      }

      int var8 = -1;
      if (arr.length >= 5) {
         var8 = parseInt(arr[4], -1, 15);
      }

      World var9 = iCommandSender.getEntityWorld();
      if (!var9.Method322(var6)) {
         throw new CommandException("commands.testforblock.outOfWorld");
      }

      NBTTagCompound var10 = new NBTTagCompound();
      boolean var11 = false;
      if (arr.length >= 6 && var7.hasTileEntity()) {
         String var12 = getChatComponentFromNthArg(iCommandSender, arr, 5).getUnformattedText();

         try {
            var10 = JsonToNBT.getTagFromJson(var12);
            var11 = true;
         } catch (NBTException var16) {
            throw new CommandException("commands.setblock.tagError", var16.getMessage());
         }
      }

      IBlockState var17 = var9.getBlockState(var6);
      Block var13 = var17.getBlock();
      if (var13 != var7) {
         throw new CommandException(
            "commands.testforblock.failed.tile", var6.getX(), var6.getY(), var6.getZ(), var13.getLocalizedName(), var7.getLocalizedName()
         );
      }

      if (var8 > -1) {
         int var14 = var17.getBlock().getMetaFromState(var17);
         if (var14 != var8) {
            throw new CommandException("commands.testforblock.failed.data", var6.getX(), var6.getY(), var6.getZ(), var14, var8);
         }
      }

      if (var11) {
         TileEntity var18 = var9.getTileEntity(var6);
         if (var18 == null) {
            throw new CommandException("commands.testforblock.failed.tileEntity", var6.getX(), var6.getY(), var6.getZ());
         }

         NBTTagCompound var15 = new NBTTagCompound();
         var18.writeToNBT(var15);
         if (!NBTUtil.func_181123_a(var10, var15, true)) {
            throw new CommandException("commands.testforblock.failed.nbt", var6.getX(), var6.getY(), var6.getZ());
         }
      }

      iCommandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 1);
      notifyOperators(iCommandSender, this, "commands.testforblock.success", var6.getX(), var6.getY(), var6.getZ());
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length > 0 && arr.length <= 3
         ? func_175771_a(arr, 0, blockPos)
         : (arr.length == 4 ? getListOfStringsMatchingLastWord(arr, Block.blockRegistry.getKeys()) : null);
   }
}
