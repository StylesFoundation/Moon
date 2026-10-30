package net.minecraft.command;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class CommandResultStats {
   private static final int NUM_RESULT_TYPES = CommandResultStats.Type.values().length;
   private static final String[] STRING_RESULT_TYPES = new String[NUM_RESULT_TYPES];
   private String[] entitiesID = STRING_RESULT_TYPES;
   private String[] objectives = STRING_RESULT_TYPES;

   public void setCommandStatScore(ICommandSender iCommandSender, CommandResultStats.Type type, int integer) {
      String var7 = this.entitiesID[type.getTypeID()];
      if (var7 != null) {
         ICommandSender var8 = new ICommandSender() {
            @Override
            public String getName() {
               return iCommandSender.getName();
            }

            @Override
            public IChatComponent getDisplayName() {
               return iCommandSender.getDisplayName();
            }

            @Override
            public void addChatMessage(IChatComponent iChatComponent) {
               iCommandSender.addChatMessage(iChatComponent);
            }

            @Override
            public boolean canCommandSenderUseCommand(int integer, String string) {
               return true;
            }

            @Override
            public BlockPos getPosition() {
               return iCommandSender.getPosition();
            }

            @Override
            public Vec3 getPositionVector() {
               return iCommandSender.getPositionVector();
            }

            @Override
            public World getEntityWorld() {
               return iCommandSender.getEntityWorld();
            }

            @Override
            public Entity getCommandSenderEntity() {
               return iCommandSender.getCommandSenderEntity();
            }

            @Override
            public boolean sendCommandFeedback() {
               return iCommandSender.sendCommandFeedback();
            }

            @Override
            public void setCommandStat(CommandResultStats.Type type, int integer) {
               iCommandSender.setCommandStat(type, integer);
            }
         };

         String var9;
         try {
            var9 = CommandBase.getEntityName(var8, var7);
         } catch (EntityNotFoundException var13) {
            return;
         }

         String var10 = this.objectives[type.getTypeID()];
         if (var10 != null) {
            Scoreboard var11 = iCommandSender.getEntityWorld().getScoreboard();
            ScoreObjective var12 = var11.getObjective(var10);
            if (var12 != null && var11.entityHasObjective(var9, var12)) {
               var11.Method281(var9, var12).setScorePoints(integer);
            }
         }
      }
   }

   public void readStatsFromNBT(NBTTagCompound nBTTagCompound) {
      if (nBTTagCompound.hasKey("CommandStats", 10)) {
         NBTTagCompound var5 = nBTTagCompound.getCompoundTag("CommandStats");

         for (CommandResultStats.Type var9 : CommandResultStats.Type.values()) {
            String var10 = var9.getTypeName() + "Name";
            String var11 = var9.getTypeName() + "Objective";
            if (var5.hasKey(var10, 8) && var5.hasKey(var11, 8)) {
               String var12 = var5.getString(var10);
               String var13 = var5.getString(var11);
               setScoreBoardStat(this, var9, var12, var13);
            }
         }
      }
   }

   public void writeStatsToNBT(NBTTagCompound nBTTagCompound) {
      NBTTagCompound var5 = new NBTTagCompound();

      for (CommandResultStats.Type var9 : CommandResultStats.Type.values()) {
         String var10 = this.entitiesID[var9.getTypeID()];
         String var11 = this.objectives[var9.getTypeID()];
         if (var10 != null && var11 != null) {
            var5.setString(var9.getTypeName() + "Name", var10);
            var5.setString(var9.getTypeName() + "Objective", var11);
         }
      }

      if (!var5.hasNoTags()) {
         nBTTagCompound.setTag("CommandStats", var5);
      }
   }

   public static void setScoreBoardStat(CommandResultStats commandResultStats, CommandResultStats.Type type, String string3, String string4) {
      if (string3 != null && string3.length() != 0 && string4 != null && string4.length() != 0) {
         if (commandResultStats.entitiesID == STRING_RESULT_TYPES || commandResultStats.objectives == STRING_RESULT_TYPES) {
            commandResultStats.entitiesID = new String[NUM_RESULT_TYPES];
            commandResultStats.objectives = new String[NUM_RESULT_TYPES];
         }

         commandResultStats.entitiesID[type.getTypeID()] = string3;
         commandResultStats.objectives[type.getTypeID()] = string4;
      } else {
         removeScoreBoardStat(commandResultStats, type);
      }
   }

   private static void removeScoreBoardStat(CommandResultStats commandResultStats, CommandResultStats.Type type) {
      if (commandResultStats.entitiesID != STRING_RESULT_TYPES && commandResultStats.objectives != STRING_RESULT_TYPES) {
         commandResultStats.entitiesID[type.getTypeID()] = null;
         commandResultStats.objectives[type.getTypeID()] = null;
         boolean var5 = true;

         for (CommandResultStats.Type var9 : CommandResultStats.Type.values()) {
            if (commandResultStats.entitiesID[var9.getTypeID()] != null && commandResultStats.objectives[var9.getTypeID()] != null) {
               var5 = false;
               break;
            }
         }

         if (var5) {
            commandResultStats.entitiesID = STRING_RESULT_TYPES;
            commandResultStats.objectives = STRING_RESULT_TYPES;
         }
      }
   }

   public void addAllStats(CommandResultStats commandResultStats) {
      for (CommandResultStats.Type var8 : CommandResultStats.Type.values()) {
         setScoreBoardStat(this, var8, commandResultStats.entitiesID[var8.getTypeID()], commandResultStats.objectives[var8.getTypeID()]);
      }
   }

   public enum Type {
      SUCCESS_COUNT(0, "SuccessCount"),
      AFFECTED_BLOCKS(1, "AffectedBlocks"),
      AFFECTED_ENTITIES(2, "AffectedEntities"),
      AFFECTED_ITEMS(3, "AffectedItems"),
      QUERY_RESULT(4, "QueryResult");

      final int typeID;
      final String typeName;

      Type(int integer3, String string4) {
         this.typeID = integer3;
         this.typeName = string4;
      }

      public int getTypeID() {
         return this.typeID;
      }

      public String getTypeName() {
         return this.typeName;
      }

      public static String[] getTypeNames() {
         String[] var3 = new String[values().length];
         int var4 = 0;

         for (CommandResultStats.Type var8 : values()) {
            var3[var4++] = var8.getTypeName();
         }

         return var3;
      }

      public static CommandResultStats.Type getTypeByName(String string) {
         for (CommandResultStats.Type var7 : values()) {
            if (var7.getTypeName().equals(string)) {
               return var7;
            }
         }

         return null;
      }

      private static CommandResultStats.Type[] Method533() {
         return new CommandResultStats.Type[]{SUCCESS_COUNT, AFFECTED_BLOCKS, AFFECTED_ENTITIES, AFFECTED_ITEMS, QUERY_RESULT};
      }
   }
}
