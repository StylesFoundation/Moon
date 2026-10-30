package net.minecraft.command;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandHandler implements ICommandManager {
   private static final Logger logger = LogManager.getLogger();
   private final Map<String, ICommand> commandMap = Maps.newHashMap();
   private final Set<ICommand> commandSet = Sets.newHashSet();

   @Override
   public int executeCommand(ICommandSender iCommandSender, String string) {
      string = string.trim();
      if (string.startsWith("/")) {
         string = string.substring(1);
      }

      String[] var6 = string.split(" ");
      String var7 = var6[0];
      var6 = dropFirstString(var6);
      ICommand var8 = this.commandMap.get(var7);
      int var9 = this.getUsernameIndex(var8, var6);
      int var10 = 0;
      if (var8 == null) {
         ChatComponentTranslation var11 = new ChatComponentTranslation("commands.generic.notFound");
         var11.getChatStyle().setColor(EnumChatFormatting.RED);
         iCommandSender.addChatMessage(var11);
      } else if (var8.canCommandSenderUseCommand(iCommandSender)) {
         if (var9 > -1) {
            List<Entity> var17 = PlayerSelector.matchEntities(iCommandSender, var6[var9], Entity.class);
            String var12 = var6[var9];
            iCommandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, var17.size());

            for (Entity var14 : var17) {
               var6[var9] = var14.getUniqueID().toString();
               if (this.tryExecute(iCommandSender, var6, var8, string)) {
                  var10++;
               }
            }

            var6[var9] = var12;
         } else {
            iCommandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, 1);
            if (this.tryExecute(iCommandSender, var6, var8, string)) {
               var10++;
            }
         }
      } else {
         ChatComponentTranslation var18 = new ChatComponentTranslation("commands.generic.permission");
         var18.getChatStyle().setColor(EnumChatFormatting.RED);
         iCommandSender.addChatMessage(var18);
      }

      iCommandSender.setCommandStat(CommandResultStats.Type.SUCCESS_COUNT, var10);
      return var10;
   }

   protected boolean tryExecute(ICommandSender iCommandSender, String[] arr, ICommand iCommand, String string) {
      try {
         iCommand.processCommand(iCommandSender, arr);
         return true;
      } catch (WrongUsageException var7) {
         ChatComponentTranslation var11 = new ChatComponentTranslation(
            "commands.generic.usage", new ChatComponentTranslation(var7.getMessage(), var7.getErrorObjects())
         );
         var11.getChatStyle().setColor(EnumChatFormatting.RED);
         iCommandSender.addChatMessage(var11);
      } catch (CommandException var8) {
         ChatComponentTranslation var10 = new ChatComponentTranslation(var8.getMessage(), var8.getErrorObjects());
         var10.getChatStyle().setColor(EnumChatFormatting.RED);
         iCommandSender.addChatMessage(var10);
      } catch (Throwable var9) {
         ChatComponentTranslation var6 = new ChatComponentTranslation("commands.generic.exception");
         var6.getChatStyle().setColor(EnumChatFormatting.RED);
         iCommandSender.addChatMessage(var6);
         logger.warn("Couldn't process command: '" + string + "'");
      }

      return false;
   }

   public ICommand registerCommand(ICommand iCommand) {
      this.commandMap.put(iCommand.getCommandName(), iCommand);
      this.commandSet.add(iCommand);

      for (String var6 : iCommand.getCommandAliases()) {
         ICommand var7 = this.commandMap.get(var6);
         if (var7 == null || !var7.getCommandName().equals(var6)) {
            this.commandMap.put(var6, iCommand);
         }
      }

      return iCommand;
   }

   private static String[] dropFirstString(String[] arr) {
      String[] var1 = new String[arr.length - 1];
      System.arraycopy(arr, 1, var1, 0, arr.length - 1);
      return var1;
   }

   @Override
   public List<String> getTabCompletionOptions(ICommandSender iCommandSender, String string, BlockPos blockPos) {
      String[] var7 = string.split(" ", -1);
      String var8 = var7[0];
      if (var7.length == 1) {
         ArrayList var12 = Lists.newArrayList();

         for (Entry var11 : this.commandMap.entrySet()) {
            if (CommandBase.doesStringStartWith(var8, (String)var11.getKey()) && ((ICommand)var11.getValue()).canCommandSenderUseCommand(iCommandSender)) {
               var12.add((String)var11.getKey());
            }
         }

         return var12;
      } else {
         if (var7.length > 1) {
            ICommand var9 = this.commandMap.get(var8);
            if (var9 != null && var9.canCommandSenderUseCommand(iCommandSender)) {
               return var9.addTabCompletionOptions(iCommandSender, dropFirstString(var7), blockPos);
            }
         }

         return null;
      }
   }

   @Override
   public List<ICommand> getPossibleCommands(ICommandSender iCommandSender) {
      ArrayList var5 = Lists.newArrayList();

      for (ICommand var7 : this.commandSet) {
         if (var7.canCommandSenderUseCommand(iCommandSender)) {
            var5.add(var7);
         }
      }

      return var5;
   }

   @Override
   public Map<String, ICommand> getCommands() {
      return this.commandMap;
   }

   private int getUsernameIndex(ICommand iCommand, String[] arr) {
      if (iCommand != null) {
         for (int var6 = 0; var6 < arr.length; var6++) {
            if (iCommand.isUsernameIndex(arr, var6) && PlayerSelector.matchesMultiplePlayers(arr[var6])) {
               return var6;
            }
         }
      }

      return -1;
   }
}
