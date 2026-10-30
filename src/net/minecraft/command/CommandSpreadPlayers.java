package net.minecraft.command;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class CommandSpreadPlayers extends CommandBase {
   @Override
   public String getCommandName() {
      return "spreadplayers";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.spreadplayers.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length < 6) {
         throw new WrongUsageException("commands.spreadplayers.usage");
      }

      int var6 = 0;
      BlockPos var7 = iCommandSender.getPosition();
      double var8 = parseDouble(var7.getX(), arr[var6++], true);
      double var10 = parseDouble(var7.getZ(), arr[var6++], true);
      double var12 = parseDouble(arr[var6++], 0.0);
      double var14 = parseDouble(arr[var6++], var12 + 1.0);
      boolean var16 = parseBoolean(arr[var6++]);
      ArrayList var17 = Lists.newArrayList();

      while (var6 < arr.length) {
         String var18 = arr[var6++];
         if (PlayerSelector.hasArguments(var18)) {
            List var19 = PlayerSelector.matchEntities(iCommandSender, var18, Entity.class);
            if (var19.size() == 0) {
               throw new EntityNotFoundException();
            }

            var17.addAll(var19);
         } else {
            EntityPlayerMP var25 = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(var18);
            if (var25 == null) {
               throw new PlayerNotFoundException();
            }

            var17.add(var25);
         }
      }

      iCommandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, var17.size());
      if (var17.isEmpty()) {
         throw new EntityNotFoundException();
      }

      iCommandSender.addChatMessage(
         new ChatComponentTranslation("commands.spreadplayers.spreading." + (var16 ? "teams" : "players"), var17.size(), var14, var8, var10, var12)
      );
      this.func_110669_a(iCommandSender, var17, new CommandSpreadPlayers.Position(var8, var10), var12, var14, ((Entity)var17.get(0)).worldObj, var16);
   }

   private void func_110669_a(
      ICommandSender iCommandSender, List<Entity> list, CommandSpreadPlayers.Position position, double double4, double double5, World world, boolean boolean7
   ) throws CommandException {
      Random var13 = new Random();
      double var14 = position.field_111101_a - double5;
      double var16 = position.field_111100_b - double5;
      double var18 = position.field_111101_a + double5;
      double var20 = position.field_111100_b + double5;
      CommandSpreadPlayers.Position[] var22 = this.func_110670_a(var13, boolean7 ? this.func_110667_a(list) : list.size(), var14, var16, var18, var20);
      int var23 = this.func_110668_a(position, double4, world, var13, var14, var16, var18, var20, var22, boolean7);
      double var24 = this.func_110671_a(list, world, var22, boolean7);
      notifyOperators(
         iCommandSender,
         this,
         "commands.spreadplayers.success." + (boolean7 ? "teams" : "players"),
         var22.length,
         position.field_111101_a,
         position.field_111100_b
      );
      if (var22.length > 1) {
         iCommandSender.addChatMessage(
            new ChatComponentTranslation("commands.spreadplayers.info." + (boolean7 ? "teams" : "players"), String.format("%.2f", var24), var23)
         );
      }
   }

   private int func_110667_a(List<Entity> list) {
      HashSet var5 = Sets.newHashSet();

      for (Entity var7 : list) {
         if (var7 instanceof EntityPlayer) {
            var5.add(((EntityPlayer)var7).getTeam());
         } else {
            var5.add(null);
         }
      }

      return var5.size();
   }

   private int func_110668_a(
      CommandSpreadPlayers.Position position,
      double double2,
      World world,
      Random random,
      double double5,
      double double6,
      double double7,
      double double8,
      CommandSpreadPlayers.Position[] arr,
      boolean boolean10
   ) throws CommandException {
      boolean var19 = true;
      double var20 = Float.MAX_VALUE;

      int var22;
      for (var22 = 0; var22 < 10000 && var19; var22++) {
         var19 = false;
         var20 = Float.MAX_VALUE;

         for (int var23 = 0; var23 < arr.length; var23++) {
            CommandSpreadPlayers.Position var24 = arr[var23];
            int var25 = 0;
            CommandSpreadPlayers.Position var26 = new CommandSpreadPlayers.Position();

            for (int var27 = 0; var27 < arr.length; var27++) {
               if (var23 != var27) {
                  CommandSpreadPlayers.Position var28 = arr[var27];
                  double var29 = var24.func_111099_a(var28);
                  var20 = Math.min(var29, var20);
                  if (var29 < double2) {
                     var25++;
                     var26.field_111101_a = var26.field_111101_a + (var28.field_111101_a - var24.field_111101_a);
                     var26.field_111100_b = var26.field_111100_b + (var28.field_111100_b - var24.field_111100_b);
                  }
               }
            }

            if (var25 > 0) {
               var26.field_111101_a /= var25;
               var26.field_111100_b /= var25;
               double var35 = var26.func_111096_b();
               if (var35 > 0.0) {
                  var26.func_111095_a();
                  var24.func_111094_b(var26);
               } else {
                  var24.func_111097_a(random, double5, double6, double7, double8);
               }

               var19 = true;
            }

            if (var24.func_111093_a(double5, double6, double7, double8)) {
               var19 = true;
            }
         }

         if (!var19) {
            for (CommandSpreadPlayers.Position var34 : arr) {
               if (!var34.func_111098_b(world)) {
                  var34.func_111097_a(random, double5, double6, double7, double8);
                  var19 = true;
               }
            }
         }
      }

      if (var22 >= 10000) {
         throw new CommandException(
            "commands.spreadplayers.failure." + (boolean10 ? "teams" : "players"),
            arr.length,
            position.field_111101_a,
            position.field_111100_b,
            String.format("%.2f", var20)
         );
      } else {
         return var22;
      }
   }

   private double func_110671_a(List<Entity> list, World world, CommandSpreadPlayers.Position[] arr, boolean boolean4) {
      double var8 = 0.0;
      int var10 = 0;
      HashMap var11 = Maps.newHashMap();

      for (Entity var13 : list) {
         CommandSpreadPlayers.Position var14;
         if (boolean4) {
            Team var15 = var13 instanceof EntityPlayer ? ((EntityPlayer)var13).getTeam() : null;
            if (!var11.containsKey(var15)) {
               var11.put(var15, arr[var10++]);
            }

            var14 = (CommandSpreadPlayers.Position)var11.get(var15);
         } else {
            var14 = arr[var10++];
         }

         var13.setPositionAndUpdate(
            MathHelper.floor_double(var14.field_111101_a) + 0.5F, var14.func_111092_a(world), MathHelper.floor_double(var14.field_111100_b) + 0.5
         );
         double var21 = Double.MAX_VALUE;

         for (CommandSpreadPlayers.Position var20 : arr) {
            if (var14 != var20) {
               var21 = Math.min(var14.func_111099_a(var20), var21);
            }
         }

         var8 += var21;
      }

      return var8 / list.size();
   }

   private CommandSpreadPlayers.Position[] func_110670_a(Random random, int integer, double double3, double double4, double double5, double double6) {
      CommandSpreadPlayers.Position[] var14 = new CommandSpreadPlayers.Position[integer];

      for (int var15 = 0; var15 < var14.length; var15++) {
         CommandSpreadPlayers.Position var16 = new CommandSpreadPlayers.Position();
         var16.func_111097_a(random, double3, double4, double5, double6);
         var14[var15] = var16;
      }

      return var14;
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length >= 1 && arr.length <= 2 ? func_181043_b(arr, 0, blockPos) : null;
   }

   static class Position {
      double field_111101_a;
      double field_111100_b;

      Position() {
      }

      Position(double double1, double double2) {
         this.field_111101_a = double1;
         this.field_111100_b = double2;
      }

      double func_111099_a(CommandSpreadPlayers.Position position) {
         double var2 = this.field_111101_a - position.field_111101_a;
         double var4 = this.field_111100_b - position.field_111100_b;
         return Math.sqrt(var2 * var2 + var4 * var4);
      }

      void func_111095_a() {
         double var1 = this.func_111096_b();
         this.field_111101_a /= var1;
         this.field_111100_b /= var1;
      }

      float func_111096_b() {
         return MathHelper.sqrt_double(this.field_111101_a * this.field_111101_a + this.field_111100_b * this.field_111100_b);
      }

      public void func_111094_b(CommandSpreadPlayers.Position position) {
         this.field_111101_a = this.field_111101_a - position.field_111101_a;
         this.field_111100_b = this.field_111100_b - position.field_111100_b;
      }

      public boolean func_111093_a(double double1, double double2, double double3, double double4) {
         boolean var12 = false;
         if (this.field_111101_a < double1) {
            this.field_111101_a = double1;
            var12 = true;
         } else if (this.field_111101_a > double3) {
            this.field_111101_a = double3;
            var12 = true;
         }

         if (this.field_111100_b < double2) {
            this.field_111100_b = double2;
            var12 = true;
         } else if (this.field_111100_b > double4) {
            this.field_111100_b = double4;
            var12 = true;
         }

         return var12;
      }

      public int func_111092_a(World world) {
         BlockPos var5 = new BlockPos(this.field_111101_a, 256.0, this.field_111100_b);

         while (var5.getY() > 0) {
            var5 = var5.down();
            if (world.getBlockState(var5).getBlock().getMaterial() != Material.air) {
               return var5.getY() + 1;
            }
         }

         return 257;
      }

      public boolean func_111098_b(World world) {
         BlockPos var5 = new BlockPos(this.field_111101_a, 256.0, this.field_111100_b);

         while (var5.getY() > 0) {
            var5 = var5.down();
            Material var6 = world.getBlockState(var5).getBlock().getMaterial();
            if (var6 != Material.air) {
               return !var6.isLiquid() && var6 != Material.fire;
            }
         }

         return false;
      }

      public void func_111097_a(Random random, double double2, double double3, double double4, double double5) {
         this.field_111101_a = MathHelper.getRandomDoubleInRange(random, double2, double4);
         this.field_111100_b = MathHelper.getRandomDoubleInRange(random, double3, double5);
      }
   }
}
