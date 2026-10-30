package net.minecraft.command;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class CommandExecuteAt extends CommandBase {
   @Override
   public String getCommandName() {
      return "execute";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.execute.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length < 5) {
         throw new WrongUsageException("commands.execute.usage");
      }

      final Entity var6 = getEntity(iCommandSender, arr[0], Entity.class);
      final double var7 = parseDouble(var6.posX, arr[1], false);
      final double var9 = parseDouble(var6.posY, arr[2], false);
      final double var11 = parseDouble(var6.posZ, arr[3], false);
      final BlockPos var13 = new BlockPos(var7, var9, var11);
      byte var14 = 4;
      if ("detect".equals(arr[4]) && arr.length > 10) {
         World var15 = var6.getEntityWorld();
         double var16 = parseDouble(var7, arr[5], false);
         double var18 = parseDouble(var9, arr[6], false);
         double var20 = parseDouble(var11, arr[7], false);
         Block var22 = getBlockByText(iCommandSender, arr[8]);
         int var23 = parseInt(arr[9], -1, 15);
         BlockPos var24 = new BlockPos(var16, var18, var20);
         IBlockState var25 = var15.getBlockState(var24);
         if (var25.getBlock() != var22 || var23 >= 0 && var25.getBlock().getMetaFromState(var25) != var23) {
            throw new CommandException("commands.execute.failed", "detect", var6.getName());
         }

         var14 = 10;
      }

      String var27 = buildString(arr, var14);
      ICommandSender var28 = new ICommandSender() {
         @Override
         public String getName() {
            return var6.getName();
         }

         @Override
         public IChatComponent getDisplayName() {
            return var6.getDisplayName();
         }

         @Override
         public void addChatMessage(IChatComponent iChatComponent) {
            iCommandSender.addChatMessage(iChatComponent);
         }

         @Override
         public boolean canCommandSenderUseCommand(int integer, String string) {
            return iCommandSender.canCommandSenderUseCommand(integer, string);
         }

         @Override
         public BlockPos getPosition() {
            return var13;
         }

         @Override
         public Vec3 getPositionVector() {
            return new Vec3(var7, var9, var11);
         }

         @Override
         public World getEntityWorld() {
            return var6.worldObj;
         }

         @Override
         public Entity getCommandSenderEntity() {
            return var6;
         }

         @Override
         public boolean sendCommandFeedback() {
            MinecraftServer var4 = MinecraftServer.getServer();
            return var4 == null || var4.worldServers[0].getGameRules().getBoolean("commandBlockOutput");
         }

         @Override
         public void setCommandStat(CommandResultStats.Type type, int integer) {
            var6.setCommandStat(type, integer);
         }
      };
      ICommandManager var17 = MinecraftServer.getServer().getCommandManager();

      try {
         int var29 = var17.executeCommand(var28, var27);
         if (var29 < 1) {
            throw new CommandException("commands.execute.allInvocationsFailed", var27);
         }
      } catch (Throwable var26) {
         throw new CommandException("commands.execute.failed", var27, var6.getName());
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length == 1
         ? getListOfStringsMatchingLastWord(arr, MinecraftServer.getServer().getAllUsernames())
         : (
            arr.length > 1 && arr.length <= 4
               ? func_175771_a(arr, 1, blockPos)
               : (
                  arr.length > 5 && arr.length <= 8 && "detect".equals(arr[4])
                     ? func_175771_a(arr, 5, blockPos)
                     : (arr.length == 9 && "detect".equals(arr[4]) ? getListOfStringsMatchingLastWord(arr, Block.blockRegistry.getKeys()) : null)
               )
         );
   }

   @Override
   public boolean isUsernameIndex(String[] arr, int integer) {
      return integer == 0;
   }
}
