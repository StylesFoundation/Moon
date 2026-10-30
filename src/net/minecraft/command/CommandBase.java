package net.minecraft.command;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;

public abstract class CommandBase implements ICommand {
   private static IAdminCommand theAdmin;

   public int getRequiredPermissionLevel() {
      return 4;
   }

   @Override
   public List<String> getCommandAliases() {
      return Collections.emptyList();
   }

   @Override
   public boolean canCommandSenderUseCommand(ICommandSender iCommandSender) {
      return iCommandSender.canCommandSenderUseCommand(this.getRequiredPermissionLevel(), this.getCommandName());
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return null;
   }

   public static int parseInt(String string) throws NumberInvalidException {
      try {
         return Integer.parseInt(string);
      } catch (NumberFormatException var1) {
         throw new NumberInvalidException("commands.generic.num.invalid", string);
      }
   }

   public static int parseInt(String string, int integer) throws NumberInvalidException {
      return parseInt(string, integer, Integer.MAX_VALUE);
   }

   public static int parseInt(String string, int integer2, int integer3) throws NumberInvalidException {
      int var6 = parseInt(string);
      if (var6 < integer2) {
         throw new NumberInvalidException("commands.generic.num.tooSmall", var6, integer2);
      } else if (var6 > integer3) {
         throw new NumberInvalidException("commands.generic.num.tooBig", var6, integer3);
      } else {
         return var6;
      }
   }

   public static long parseLong(String string) throws NumberInvalidException {
      try {
         return Long.parseLong(string);
      } catch (NumberFormatException var1) {
         throw new NumberInvalidException("commands.generic.num.invalid", string);
      }
   }

   public static long parseLong(String string, long long2, long long3) throws NumberInvalidException {
      long var8 = parseLong(string);
      if (var8 < long2) {
         throw new NumberInvalidException("commands.generic.num.tooSmall", var8, long2);
      } else if (var8 > long3) {
         throw new NumberInvalidException("commands.generic.num.tooBig", var8, long3);
      } else {
         return var8;
      }
   }

   public static BlockPos parseBlockPos(ICommandSender iCommandSender, String[] arr, int integer, boolean boolean4) throws NumberInvalidException {
      BlockPos var6 = iCommandSender.getPosition();
      return new BlockPos(
         parseDouble(var6.getX(), arr[integer], -30000000, 30000000, boolean4),
         parseDouble(var6.getY(), arr[integer + 1], 0, 256, false),
         parseDouble(var6.getZ(), arr[integer + 2], -30000000, 30000000, boolean4)
      );
   }

   public static double parseDouble(String string) throws NumberInvalidException {
      try {
         double var4 = Double.parseDouble(string);
         if (!Doubles.isFinite(var4)) {
            throw new NumberInvalidException("commands.generic.num.invalid", string);
         } else {
            return var4;
         }
      } catch (NumberFormatException var6) {
         throw new NumberInvalidException("commands.generic.num.invalid", string);
      }
   }

   public static double parseDouble(String string, double double2) throws NumberInvalidException {
      return parseDouble(string, double2, Double.MAX_VALUE);
   }

   public static double parseDouble(String string, double double2, double double3) throws NumberInvalidException {
      double var8 = parseDouble(string);
      if (var8 < double2) {
         throw new NumberInvalidException("commands.generic.double.tooSmall", var8, double2);
      } else if (var8 > double3) {
         throw new NumberInvalidException("commands.generic.double.tooBig", var8, double3);
      } else {
         return var8;
      }
   }

   public static boolean parseBoolean(String string) throws CommandException {
      if (string.equals("true") || string.equals("1")) {
         return true;
      } else if (!string.equals("false") && !string.equals("0")) {
         throw new CommandException("commands.generic.boolean.invalid", string);
      } else {
         return false;
      }
   }

   public static EntityPlayerMP getCommandSenderAsPlayer(ICommandSender iCommandSender) throws PlayerNotFoundException {
      if (iCommandSender instanceof EntityPlayerMP) {
         return (EntityPlayerMP)iCommandSender;
      } else {
         throw new PlayerNotFoundException("You must specify which player you wish to perform this action on.");
      }
   }

   public static EntityPlayerMP getPlayer(ICommandSender iCommandSender, String string) throws PlayerNotFoundException {
      EntityPlayerMP var5 = PlayerSelector.matchOnePlayer(iCommandSender, string);
      if (var5 == null) {
         try {
            var5 = MinecraftServer.getServer().getConfigurationManager().getPlayerByUUID(UUID.fromString(string));
         } catch (IllegalArgumentException var6) {
         }
      }

      if (var5 == null) {
         var5 = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(string);
      }

      if (var5 == null) {
         throw new PlayerNotFoundException();
      } else {
         return var5;
      }
   }

   public static Entity getEntity(ICommandSender iCommandSender, String string) throws EntityNotFoundException {
      return getEntity(iCommandSender, string, Entity.class);
   }

   public static <T extends Entity> T getEntity(ICommandSender iCommandSender, String string, Class<? extends T> class3) throws EntityNotFoundException {
      Entity var6 = PlayerSelector.matchOneEntity(iCommandSender, string, class3);
      MinecraftServer var7 = MinecraftServer.getServer();
      if (var6 == null) {
         var6 = var7.getConfigurationManager().getPlayerByUsername(string);
      }

      if (var6 == null) {
         try {
            UUID var8 = UUID.fromString(string);
            var6 = var7.getEntityFromUuid(var8);
            if (var6 == null) {
               var6 = var7.getConfigurationManager().getPlayerByUUID(var8);
            }
         } catch (IllegalArgumentException var9) {
            throw new EntityNotFoundException("commands.generic.entity.invalidUuid");
         }
      }

      if (var6 != null && class3.isAssignableFrom(var6.getClass())) {
         return (T)var6;
      } else {
         throw new EntityNotFoundException();
      }
   }

   public static List<Entity> func_175763_c(ICommandSender iCommandSender, String string) throws EntityNotFoundException {
      return PlayerSelector.hasArguments(string)
         ? PlayerSelector.matchEntities(iCommandSender, string, Entity.class)
         : Lists.newArrayList(new Entity[]{getEntity(iCommandSender, string)});
   }

   public static String getPlayerName(ICommandSender iCommandSender, String string) throws PlayerNotFoundException {
      try {
         return getPlayer(iCommandSender, string).getName();
      } catch (PlayerNotFoundException var6) {
         if (PlayerSelector.hasArguments(string)) {
            throw var6;
         } else {
            return string;
         }
      }
   }

   public static String getEntityName(ICommandSender iCommandSender, String string) throws EntityNotFoundException {
      try {
         return getPlayer(iCommandSender, string).getName();
      } catch (PlayerNotFoundException var8) {
         try {
            return getEntity(iCommandSender, string).getUniqueID().toString();
         } catch (EntityNotFoundException var7) {
            if (PlayerSelector.hasArguments(string)) {
               throw var7;
            } else {
               return string;
            }
         }
      }
   }

   public static IChatComponent getChatComponentFromNthArg(ICommandSender iCommandSender, String[] arr, int integer) throws CommandException {
      return getChatComponentFromNthArg(iCommandSender, arr, integer, false);
   }

   public static IChatComponent getChatComponentFromNthArg(ICommandSender iCommandSender, String[] arr, int integer, boolean boolean4) throws PlayerNotFoundException {
      ChatComponentText var7 = new ChatComponentText("");

      for (int var8 = integer; var8 < arr.length; var8++) {
         if (var8 > integer) {
            var7.appendText(" ");
         }

         IChatComponent var9 = new ChatComponentText(arr[var8]);
         if (boolean4) {
            IChatComponent var10 = PlayerSelector.matchEntitiesToChatComponent(iCommandSender, arr[var8]);
            if (var10 == null) {
               if (PlayerSelector.hasArguments(arr[var8])) {
                  throw new PlayerNotFoundException();
               }
            } else {
               var9 = var10;
            }
         }

         var7.appendSibling(var9);
      }

      return var7;
   }

   public static String buildString(String[] arr, int integer) {
      StringBuilder var5 = new StringBuilder();

      for (int var6 = integer; var6 < arr.length; var6++) {
         if (var6 > integer) {
            var5.append(" ");
         }

         String var7 = arr[var6];
         var5.append(var7);
      }

      return var5.toString();
   }

   public static CommandBase.CoordinateArg parseCoordinate(double double1, String string, boolean boolean3) throws NumberInvalidException {
      return parseCoordinate(double1, string, -30000000, 30000000, boolean3);
   }

   public static CommandBase.CoordinateArg parseCoordinate(double double1, String string, int integer3, int integer4, boolean boolean5) throws NumberInvalidException {
      boolean var9 = string.startsWith("~");
      if (var9 && Double.isNaN(double1)) {
         throw new NumberInvalidException("commands.generic.num.invalid", double1);
      }

      double var10 = 0.0;
      if (!var9 || string.length() > 1) {
         boolean var12 = string.contains(".");
         if (var9) {
            string = string.substring(1);
         }

         var10 += parseDouble(string);
         if (!var12 && !var9 && boolean5) {
            var10 += 0.5;
         }
      }

      if (integer3 != 0 || integer4 != 0) {
         if (var10 < integer3) {
            throw new NumberInvalidException("commands.generic.double.tooSmall", var10, integer3);
         }

         if (var10 > integer4) {
            throw new NumberInvalidException("commands.generic.double.tooBig", var10, integer4);
         }
      }

      return new CommandBase.CoordinateArg(var10 + (var9 ? double1 : 0.0), var10, var9);
   }

   public static double parseDouble(double double1, String string, boolean boolean3) throws NumberInvalidException {
      return parseDouble(double1, string, -30000000, 30000000, boolean3);
   }

   public static double parseDouble(double double1, String string, int integer3, int integer4, boolean boolean5) throws NumberInvalidException {
      boolean var9 = string.startsWith("~");
      if (var9 && Double.isNaN(double1)) {
         throw new NumberInvalidException("commands.generic.num.invalid", double1);
      }

      double var10 = var9 ? double1 : 0.0;
      if (!var9 || string.length() > 1) {
         boolean var12 = string.contains(".");
         if (var9) {
            string = string.substring(1);
         }

         var10 += parseDouble(string);
         if (!var12 && !var9 && boolean5) {
            var10 += 0.5;
         }
      }

      if (integer3 != 0 || integer4 != 0) {
         if (var10 < integer3) {
            throw new NumberInvalidException("commands.generic.double.tooSmall", var10, integer3);
         }

         if (var10 > integer4) {
            throw new NumberInvalidException("commands.generic.double.tooBig", var10, integer4);
         }
      }

      return var10;
   }

   public static Item getItemByText(ICommandSender iCommandSender, String string) throws NumberInvalidException {
      ResourceLocation var5 = new ResourceLocation(string);
      Item var6 = Item.itemRegistry.getObject(var5);
      if (var6 == null) {
         throw new NumberInvalidException("commands.give.item.notFound", var5);
      } else {
         return var6;
      }
   }

   public static Block getBlockByText(ICommandSender iCommandSender, String string) throws NumberInvalidException {
      ResourceLocation var5 = new ResourceLocation(string);
      if (!Block.blockRegistry.containsKey(var5)) {
         throw new NumberInvalidException("commands.give.block.notFound", var5);
      } else {
         Block var6 = Block.blockRegistry.getObject(var5);
         if (var6 == null) {
            throw new NumberInvalidException("commands.give.block.notFound", var5);
         } else {
            return var6;
         }
      }
   }

   public static String joinNiceString(Object[] arr) {
      StringBuilder var4 = new StringBuilder();

      for (int var5 = 0; var5 < arr.length; var5++) {
         String var6 = arr[var5].toString();
         if (var5 > 0) {
            if (var5 == arr.length - 1) {
               var4.append(" and ");
            } else {
               var4.append(", ");
            }
         }

         var4.append(var6);
      }

      return var4.toString();
   }

   public static IChatComponent join(List<IChatComponent> list) {
      ChatComponentText var4 = new ChatComponentText("");

      for (int var5 = 0; var5 < list.size(); var5++) {
         if (var5 > 0) {
            if (var5 == list.size() - 1) {
               var4.appendText(" and ");
            } else if (var5 > 0) {
               var4.appendText(", ");
            }
         }

         var4.appendSibling((IChatComponent)list.get(var5));
      }

      return var4;
   }

   public static String joinNiceStringFromCollection(Collection<String> collection) {
      return joinNiceString(collection.toArray(new String[0]));
   }

   public static List<String> func_175771_a(String[] arr, int integer, BlockPos blockPos) {
      if (blockPos == null) {
         return null;
      }

      int var6 = arr.length - 1;
      String var7;
      if (var6 == integer) {
         var7 = Integer.toString(blockPos.getX());
      } else if (var6 == integer + 1) {
         var7 = Integer.toString(blockPos.getY());
      } else {
         if (var6 != integer + 2) {
            return null;
         }

         var7 = Integer.toString(blockPos.getZ());
      }

      return Lists.newArrayList(new String[]{var7});
   }

   public static List<String> func_181043_b(String[] arr, int integer, BlockPos blockPos) {
      if (blockPos == null) {
         return null;
      }

      int var6 = arr.length - 1;
      String var7;
      if (var6 == integer) {
         var7 = Integer.toString(blockPos.getX());
      } else {
         if (var6 != integer + 1) {
            return null;
         }

         var7 = Integer.toString(blockPos.getZ());
      }

      return Lists.newArrayList(new String[]{var7});
   }

   public static boolean doesStringStartWith(String string1, String string2) {
      return string2.regionMatches(true, 0, string1, 0, string1.length());
   }

   public static List<String> getListOfStringsMatchingLastWord(String[] arr1, String... arr2) {
      return getListOfStringsMatchingLastWord(arr1, Arrays.asList(arr2));
   }

   public static List<String> getListOfStringsMatchingLastWord(String[] arr, Collection<?> collection) {
      String var5 = arr[arr.length - 1];
      ArrayList var6 = Lists.newArrayList();
      if (!collection.isEmpty()) {
         for (String var8 : (List<String>)collection.stream().map(Functions.toStringFunction()::apply).collect(Collectors.toList())) {
            if (doesStringStartWith(var5, var8)) {
               var6.add(var8);
            }
         }

         if (var6.isEmpty()) {
            for (Object var10 : collection) {
               if (var10 instanceof ResourceLocation && doesStringStartWith(var5, ((ResourceLocation)var10).getResourcePath())) {
                  var6.add(String.valueOf(var10));
               }
            }
         }
      }

      return var6;
   }

   @Override
   public boolean isUsernameIndex(String[] arr, int integer) {
      return false;
   }

   public static void notifyOperators(ICommandSender iCommandSender, ICommand iCommand, String string, Object... arr) {
      notifyOperators(iCommandSender, iCommand, 0, string, arr);
   }

   public static void notifyOperators(ICommandSender iCommandSender, ICommand iCommand, int integer, String string, Object... arr) {
      if (theAdmin != null) {
         theAdmin.notifyOperators(iCommandSender, iCommand, integer, string, arr);
      }
   }

   public static void setAdminCommander(IAdminCommand iAdminCommand) {
      theAdmin = iAdminCommand;
   }

   public int compareTo(ICommand iCommand) {
      return this.getCommandName().compareTo(iCommand.getCommandName());
   }

   public static class CoordinateArg {
      private final double field_179633_a;
      private final double field_179631_b;
      private final boolean field_179632_c;

      protected CoordinateArg(double double1, double double2, boolean boolean3) {
         this.field_179633_a = double1;
         this.field_179631_b = double2;
         this.field_179632_c = boolean3;
      }

      public double func_179628_a() {
         return this.field_179633_a;
      }

      public double func_179629_b() {
         return this.field_179631_b;
      }

      public boolean func_179630_c() {
         return this.field_179632_c;
      }
   }
}
