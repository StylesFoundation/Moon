package net.minecraft.command;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class CommandClone extends CommandBase {
   @Override
   public String getCommandName() {
      return "clone";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.clone.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length < 9) {
         throw new WrongUsageException("commands.clone.usage");
      }

      iCommandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, 0);
      BlockPos var6 = parseBlockPos(iCommandSender, arr, 0, false);
      BlockPos var7 = parseBlockPos(iCommandSender, arr, 3, false);
      BlockPos var8 = parseBlockPos(iCommandSender, arr, 6, false);
      StructureBoundingBox var9 = new StructureBoundingBox(var6, var7);
      StructureBoundingBox var10 = new StructureBoundingBox(var8, var8.add(var9.func_175896_b()));
      int var11 = var9.getXSize() * var9.getYSize() * var9.getZSize();
      if (var11 > 32768) {
         throw new CommandException("commands.clone.tooManyBlocks", var11, 32768);
      }

      boolean var12 = false;
      Block var13 = null;
      int var14 = -1;
      if ((arr.length < 11 || !arr[10].equals("force") && !arr[10].equals("move")) && var9.intersectsWith(var10)) {
         throw new CommandException("commands.clone.noOverlap");
      }

      if (arr.length >= 11 && arr[10].equals("move")) {
         var12 = true;
      }

      if (var9.minY >= 0 && var9.maxY < 256 && var10.minY >= 0 && var10.maxY < 256) {
         World var15 = iCommandSender.getEntityWorld();
         if (var15.Method328(var9) && var15.Method328(var10)) {
            boolean var16 = false;
            if (arr.length >= 10) {
               if (arr[9].equals("masked")) {
                  var16 = true;
               } else if (arr[9].equals("filtered")) {
                  if (arr.length < 12) {
                     throw new WrongUsageException("commands.clone.usage");
                  }

                  var13 = getBlockByText(iCommandSender, arr[11]);
                  if (arr.length >= 13) {
                     var14 = parseInt(arr[12], 0, 15);
                  }
               }
            }

            ArrayList<CommandClone.StaticCloneData> var17 = Lists.newArrayList();
            ArrayList<CommandClone.StaticCloneData> var18 = Lists.newArrayList();
            ArrayList<CommandClone.StaticCloneData> var19 = Lists.newArrayList();
            LinkedList<BlockPos> var20 = Lists.newLinkedList();
            BlockPos var21 = new BlockPos(var10.minX - var9.minX, var10.minY - var9.minY, var10.minZ - var9.minZ);

            for (int var22 = var9.minZ; var22 <= var9.maxZ; var22++) {
               for (int var23 = var9.minY; var23 <= var9.maxY; var23++) {
                  for (int var24 = var9.minX; var24 <= var9.maxX; var24++) {
                     BlockPos var25 = new BlockPos(var24, var23, var22);
                     BlockPos var26 = var25.add(var21);
                     IBlockState var27 = var15.getBlockState(var25);
                     if ((!var16 || var27.getBlock() != Blocks.air)
                        && (var13 == null || var27.getBlock() == var13 && (var14 < 0 || var27.getBlock().getMetaFromState(var27) == var14))) {
                        TileEntity var28 = var15.getTileEntity(var25);
                        if (var28 != null) {
                           NBTTagCompound var29 = new NBTTagCompound();
                           var28.writeToNBT(var29);
                           var18.add(new CommandClone.StaticCloneData(var26, var27, var29));
                           var20.addLast(var25);
                        } else if (!var27.getBlock().isFullBlock() && !var27.getBlock().isFullCube()) {
                           var19.add(new CommandClone.StaticCloneData(var26, var27, null));
                           var20.addFirst(var25);
                        } else {
                           var17.add(new CommandClone.StaticCloneData(var26, var27, null));
                           var20.addLast(var25);
                        }
                     }
                  }
               }
            }

            if (var12) {
               for (BlockPos var34 : var20) {
                  TileEntity var37 = var15.getTileEntity(var34);
                  if (var37 instanceof IInventory) {
                     ((IInventory)var37).clear();
                  }

                  var15.setBlockState(var34, Blocks.barrier.getDefaultState(), 2);
               }

               for (BlockPos var35 : var20) {
                  var15.setBlockState(var35, Blocks.air.getDefaultState(), 3);
               }
            }

            ArrayList<CommandClone.StaticCloneData> var33 = Lists.newArrayList();
            var33.addAll(var17);
            var33.addAll(var18);
            var33.addAll(var19);
            List<CommandClone.StaticCloneData> var36 = Lists.reverse(var33);

            for (CommandClone.StaticCloneData var43 : var36) {
               TileEntity var48 = var15.getTileEntity(var43.pos);
               if (var48 instanceof IInventory) {
                  ((IInventory)var48).clear();
               }

               var15.setBlockState(var43.pos, Blocks.barrier.getDefaultState(), 2);
            }

            var11 = 0;

            for (CommandClone.StaticCloneData var44 : var33) {
               if (var15.setBlockState(var44.pos, var44.blockState, 2)) {
                  var11++;
               }
            }

            for (CommandClone.StaticCloneData var45 : var18) {
               TileEntity var49 = var15.getTileEntity(var45.pos);
               if (var45.compound != null && var49 != null) {
                  var45.compound.setInteger("x", var45.pos.getX());
                  var45.compound.setInteger("y", var45.pos.getY());
                  var45.compound.setInteger("z", var45.pos.getZ());
                  var49.readFromNBT(var45.compound);
                  var49.markDirty();
               }

               var15.setBlockState(var45.pos, var45.blockState, 2);
            }

            for (CommandClone.StaticCloneData var46 : var36) {
               var15.notifyNeighborsRespectDebug(var46.pos, var46.blockState.getBlock());
            }

            List<NextTickListEntry> var42 = var15.func_175712_a(var9, false);
            if (var42 != null) {
               for (NextTickListEntry var50 : var42) {
                  if (var9.isVecInside(var50.position)) {
                     BlockPos var51 = var50.position.add(var21);
                     var15.scheduleBlockUpdate(var51, var50.getBlock(), (int)(var50.scheduledTime - var15.getWorldInfo().getWorldTotalTime()), var50.priority);
                  }
               }
            }

            if (var11 <= 0) {
               throw new CommandException("commands.clone.failed");
            }

            iCommandSender.setCommandStat(CommandResultStats.Type.AFFECTED_BLOCKS, var11);
            notifyOperators(iCommandSender, this, "commands.clone.success", var11);
         } else {
            throw new CommandException("commands.clone.outOfWorld");
         }
      } else {
         throw new CommandException("commands.clone.outOfWorld");
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length > 0 && arr.length <= 3
         ? func_175771_a(arr, 0, blockPos)
         : (
            arr.length > 3 && arr.length <= 6
               ? func_175771_a(arr, 3, blockPos)
               : (
                  arr.length > 6 && arr.length <= 9
                     ? func_175771_a(arr, 6, blockPos)
                     : (
                        arr.length == 10
                           ? getListOfStringsMatchingLastWord(arr, "replace", "masked", "filtered")
                           : (
                              arr.length == 11
                                 ? getListOfStringsMatchingLastWord(arr, "normal", "force", "move")
                                 : (arr.length == 12 && "filtered".equals(arr[9]) ? getListOfStringsMatchingLastWord(arr, Block.blockRegistry.getKeys()) : null)
                           )
                     )
               )
         );
   }

   static class StaticCloneData {
      public final BlockPos pos;
      public final IBlockState blockState;
      public final NBTTagCompound compound;

      public StaticCloneData(BlockPos blockPos, IBlockState iBlockState, NBTTagCompound nBTTagCompound) {
         this.pos = blockPos;
         this.blockState = iBlockState;
         this.compound = nBTTagCompound;
      }
   }
}
