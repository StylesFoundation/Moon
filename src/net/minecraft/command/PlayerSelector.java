package net.minecraft.command;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.realms.Tezzelator;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;

public class PlayerSelector {
   private static final Pattern tokenPattern = Pattern.compile("^@([pare])(?:\\[([\\w=,!-]*)])?$");
   private static final Pattern intListPattern = Pattern.compile("\\G([-!]?[\\w-]*)(?:$|,)");
   private static final Pattern keyValueListPattern = Pattern.compile("\\G(\\w+)=([-!]?[\\w-]*)(?:$|,)");
   private static final Set<String> WORLD_BINDING_ARGS = Sets.newHashSet(new String[]{"x", "y", "z", "dx", "dy", "dz", "rm", "r"});

   public static EntityPlayerMP matchOnePlayer(ICommandSender iCommandSender, String string) {
      return matchOneEntity(iCommandSender, string, EntityPlayerMP.class);
   }

   public static <T extends Entity> T matchOneEntity(ICommandSender iCommandSender, String string, Class<? extends T> class3) {
      List<T> var6 = matchEntities(iCommandSender, string, class3);
      return (T)(var6.size() == 1 ? var6.get(0) : null);
   }

   public static IChatComponent matchEntitiesToChatComponent(ICommandSender iCommandSender, String string) {
      List<Entity> var5 = matchEntities(iCommandSender, string, Entity.class);
      if (var5.isEmpty()) {
         return null;
      }

      ArrayList<IChatComponent> var6 = Lists.newArrayList();

      for (Entity var8 : var5) {
         var6.add(var8.getDisplayName());
      }

      return CommandBase.join(var6);
   }

   public static <T extends Entity> List<T> matchEntities(ICommandSender iCommandSender, String string, Class<? extends T> class3) {
      Matcher var6 = tokenPattern.matcher(string);
      if (var6.matches() && iCommandSender.canCommandSenderUseCommand(1, "@")) {
         Map var7 = getArgumentMap(var6.group(2));
         if (!isEntityTypeValid(iCommandSender, var7)) {
            return Collections.emptyList();
         }

         String var8 = var6.group(1);
         BlockPos var9 = func_179664_b(var7, iCommandSender.getPosition());
         List<World> var10 = getWorlds(iCommandSender, var7);
         ArrayList<T> var11 = Lists.newArrayList();

         for (World var13 : var10) {
            if (var13 != null) {
               ArrayList<Predicate<Entity>> var14 = Lists.newArrayList();
               var14.addAll(func_179663_a(var7, var8));
               var14.addAll(getXpLevelPredicates(var7));
               var14.addAll(getGamemodePredicates(var7));
               var14.addAll(getTeamPredicates(var7));
               var14.addAll(getScorePredicates(var7));
               var14.addAll(getNamePredicates(var7));
               var14.addAll(func_180698_a(var7, var9));
               var14.addAll(getRotationsPredicates(var7));
               var11.addAll(filterResults(var7, class3, var14, var8, var13, var9));
            }
         }

         return func_179658_a(var11, var7, iCommandSender, class3, var8, var9);
      } else {
         return Collections.emptyList();
      }
   }

   private static List<World> getWorlds(ICommandSender iCommandSender, Map<String, String> map) {
      ArrayList var5 = Lists.newArrayList();
      if (func_179665_h(map)) {
         var5.add(iCommandSender.getEntityWorld());
      } else {
         Collections.addAll(var5, MinecraftServer.getServer().worldServers);
      }

      return var5;
   }

   private static <T extends Entity> boolean isEntityTypeValid(ICommandSender iCommandSender, Map<String, String> map) {
      String var5 = func_179651_b(map, "type");
      var5 = var5 != null && var5.startsWith("!") ? var5.substring(1) : var5;
      if (var5 != null && !EntityList.isStringValidEntityName(var5)) {
         ChatComponentTranslation var6 = new ChatComponentTranslation("commands.generic.entity.invalidType", var5);
         var6.getChatStyle().setColor(EnumChatFormatting.RED);
         iCommandSender.addChatMessage(var6);
         return false;
      } else {
         return true;
      }
   }

   private static List<Predicate<Entity>> func_179663_a(Map<String, String> map, String string) {
      ArrayList<Predicate<Entity>> var5 = Lists.newArrayList();
      String var6 = func_179651_b(map, "type");
      boolean var7 = var6 != null && var6.startsWith("!");
      if (var7) {
         var6 = var6.substring(1);
      }

      boolean var8 = !string.equals("e");
      boolean var9 = string.equals("r") && var6 != null;
      if ((var6 == null || !string.equals("e")) && !var9) {
         if (var8) {
            var5.add(entity -> entity instanceof EntityPlayer);
         }
      } else {
         String var10 = var6;
         var5.add(entity -> EntityList.isStringEntityName(entity, var10) != var7);
      }

      return var5;
   }

   private static List<Predicate<Entity>> getXpLevelPredicates(Map<String, String> map) {
      ArrayList<Predicate<Entity>> var4 = Lists.newArrayList();
      int var5 = parseIntWithDefault(map, "lm", -1);
      int var6 = parseIntWithDefault(map, "l", -1);
      if (var5 > -1 || var6 > -1) {
         var4.add(
            entity -> {
               if (!(entity instanceof EntityPlayerMP)) {
                  return false;
               } else {
                  EntityPlayerMP player = (EntityPlayerMP)entity;
                  return (var5 <= -1 || player.experienceLevel >= var5) && (var6 <= -1 || player.experienceLevel <= var6);
               }
            }
         );
      }

      return var4;
   }

   private static List<Predicate<Entity>> getGamemodePredicates(Map<String, String> map) {
      ArrayList<Predicate<Entity>> var4 = Lists.newArrayList();
      int var5 = parseIntWithDefault(map, "m", WorldSettings.GameType.NOT_SET.getID());
      if (var5 != WorldSettings.GameType.NOT_SET.getID()) {
         var4.add(entity -> {
            if (!(entity instanceof EntityPlayerMP)) {
               return false;
            } else {
               EntityPlayerMP player = (EntityPlayerMP)entity;
               return player.theItemInWorldManager.getGameType().getID() == var5;
            }
         });
      }

      return var4;
   }

   private static List<Predicate<Entity>> getTeamPredicates(Map<String, String> map) {
      ArrayList<Predicate<Entity>> var4 = Lists.newArrayList();
      String var5 = func_179651_b(map, "team");
      boolean var6 = var5 != null && var5.startsWith("!");
      if (var6) {
         var5 = var5.substring(1);
      }

      if (var5 != null) {
         String var7 = var5;
         var4.add(entity -> {
            if (entity instanceof EntityLivingBase) {
               Team team = ((EntityLivingBase)entity).getTeam();
               String var8 = team == null ? "" : team.getRegisteredName();
               return var8.equals(var7) != var6;
            } else {
               return false;
            }
         });
      }

      return var4;
   }

   private static List<Predicate<Entity>> getScorePredicates(Map<String, String> map) {
      ArrayList<Predicate<Entity>> var4 = Lists.newArrayList();
      Map<String, Integer> scoreMap = func_96560_a(map);
      if (scoreMap != null && scoreMap.size() > 0) {
         var4.add(entity -> {
            Scoreboard scoreboard = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();

            for (Entry<String, Integer> entry : scoreMap.entrySet()) {
               String s = entry.getKey();
               boolean isMin = false;
               if (s.endsWith("_min") && s.length() > 4) {
                  isMin = true;
                  s = s.substring(0, s.length() - 4);
               }

               ScoreObjective obj = scoreboard.getObjective(s);
               if (obj == null) {
                  return false;
               }

               String entityName = entity instanceof EntityPlayerMP ? entity.getName() : entity.getUniqueID().toString();
               if (!scoreboard.entityHasObjective(entityName, obj)) {
                  return false;
               }

               int score = scoreboard.Method281(entityName, obj).getScorePoints();
               if (score < entry.getValue() && isMin) {
                  return false;
               }

               if (score > entry.getValue() && !isMin) {
                  return false;
               }
            }

            return true;
         });
      }

      return var4;
   }

   private static List<Predicate<Entity>> getNamePredicates(Map<String, String> map) {
      ArrayList<Predicate<Entity>> var4 = Lists.newArrayList();
      String var5 = func_179651_b(map, "name");
      boolean var6 = var5 != null && var5.startsWith("!");
      if (var6) {
         var5 = var5.substring(1);
      }

      if (var5 != null) {
         String var7 = var5;
         var4.add(entity -> entity.getName().equals(var7) != var6);
      }

      return var4;
   }

   private static List<Predicate<Entity>> func_180698_a(Map<String, String> map, BlockPos blockPos) {
      ArrayList<Predicate<Entity>> var5 = Lists.newArrayList();
      int minRadius = parseIntWithDefault(map, "rm", -1);
      int maxRadius = parseIntWithDefault(map, "r", -1);
      if (blockPos != null && (minRadius >= 0 || maxRadius >= 0)) {
         int minSq = minRadius * minRadius;
         int maxSq = maxRadius * maxRadius;
         var5.add(entity -> {
            int dist = (int)entity.getDistanceSqToCenter(blockPos);
            return (minRadius < 0 || dist >= minSq) && (maxRadius < 0 || dist <= maxSq);
         });
      }

      return var5;
   }

   private static List<Predicate<Entity>> getRotationsPredicates(Map<String, String> map) {
      ArrayList<Predicate<Entity>> var4 = Lists.newArrayList();
      if (map.containsKey("rym") || map.containsKey("ry")) {
         int var5 = func_179650_a(parseIntWithDefault(map, "rym", 0));
         int var6 = func_179650_a(parseIntWithDefault(map, "ry", 359));
         var4.add(entity -> {
            int yaw = func_179650_a((int)Math.floor(entity.rotationYaw));
            return var5 > var6 ? yaw >= var5 || yaw <= var6 : yaw >= var5 && yaw <= var6;
         });
      }

      if (map.containsKey("rxm") || map.containsKey("rx")) {
         int var7 = func_179650_a(parseIntWithDefault(map, "rxm", 0));
         int var8 = func_179650_a(parseIntWithDefault(map, "rx", 359));
         var4.add(entity -> {
            int pitch = func_179650_a((int)Math.floor(entity.rotationPitch));
            return var7 > var8 ? pitch >= var7 || pitch <= var8 : pitch >= var7 && pitch <= var8;
         });
      }

      return var4;
   }

   private static <T extends Entity> List<T> filterResults(
      Map<String, String> map, Class<? extends T> class2, List<Predicate<Entity>> list, String string, World world, BlockPos blockPos
   ) {
      ArrayList var9 = Lists.newArrayList();
      String var10 = func_179651_b(map, "type");
      var10 = var10 != null && var10.startsWith("!") ? var10.substring(1) : var10;
      boolean var11 = !string.equals("e");
      boolean var12 = string.equals("r") && var10 != null;
      int var13 = parseIntWithDefault(map, "dx", 0);
      int var14 = parseIntWithDefault(map, "dy", 0);
      int var15 = parseIntWithDefault(map, "dz", 0);
      int var16 = parseIntWithDefault(map, "r", -1);
      Predicate var17 = Predicates.and(list);
      Predicate var18 = Predicates.and(Tezzelator.a, var17);
      if (blockPos != null) {
         int var19 = world.playerEntities.size();
         int var20 = world.loadedEntityList.size();
         boolean var21 = var19 < var20 / 16;
         if (map.containsKey("dx") || map.containsKey("dy") || map.containsKey("dz")) {
            AxisAlignedBB var25 = func_179661_a(blockPos, var13, var14, var15);
            if (var11 && var21 && !var12) {
               Predicate<Entity> var23 = entity -> entity.posX >= var25.minX
                  && entity.posY >= var25.minY
                  && entity.posZ >= var25.minZ
                  && entity.posX < var25.maxX
                  && entity.posY < var25.maxY
                  && entity.posZ < var25.maxZ;
               var9.addAll(world.getPlayers(class2, Predicates.and(var18, var23)));
            } else {
               var9.addAll(world.getEntitiesWithinAABB(class2, var25, var18));
            }
         } else if (var16 >= 0) {
            AxisAlignedBB var22 = new AxisAlignedBB(
               blockPos.getX() - var16,
               blockPos.getY() - var16,
               blockPos.getZ() - var16,
               blockPos.getX() + var16 + 1,
               blockPos.getY() + var16 + 1,
               blockPos.getZ() + var16 + 1
            );
            if (var11 && var21 && !var12) {
               var9.addAll(world.getPlayers(class2, var18));
            } else {
               var9.addAll(world.getEntitiesWithinAABB(class2, var22, var18));
            }
         } else if (string.equals("a")) {
            var9.addAll(world.getPlayers(class2, var17));
         } else if (string.equals("p") || string.equals("r") && !var12) {
            var9.addAll(world.getPlayers(class2, var18));
         } else {
            var9.addAll(world.getEntities(class2, var18));
         }
      } else if (string.equals("a")) {
         var9.addAll(world.getPlayers(class2, var17));
      } else if (string.equals("p") || string.equals("r") && !var12) {
         var9.addAll(world.getPlayers(class2, var18));
      } else {
         var9.addAll(world.getEntities(class2, var18));
      }

      return var9;
   }

   private static <T extends Entity> List<T> func_179658_a(
      List<T> list, Map<String, String> map, ICommandSender iCommandSender, Class<? extends T> class4, String string, BlockPos blockPos
   ) {
      int var9 = parseIntWithDefault(map, "c", !string.equals("a") && !string.equals("e") ? 1 : 0);
      if (!string.equals("p") && !string.equals("a") && !string.equals("e")) {
         if (string.equals("r")) {
            Collections.shuffle(list);
         }
      } else if (blockPos != null) {
         list.sort((entity2, entity3) -> ComparisonChain.start().compare(entity2.getDistanceSq(blockPos), entity3.getDistanceSq(blockPos)).result());
      }

      Entity var10 = iCommandSender.getCommandSenderEntity();
      if (var10 != null && class4.isAssignableFrom(var10.getClass()) && var9 == 1 && list.contains(var10) && !"r".equals(string)) {
         list = (List<T>) Lists.newArrayList(new Entity[]{var10});
      }

      if (var9 != 0) {
         if (var9 < 0) {
            Collections.reverse(list);
         }

         list = list.subList(0, Math.min(Math.abs(var9), list.size()));
      }

      return list;
   }

   private static AxisAlignedBB func_179661_a(BlockPos blockPos, int integer2, int integer3, int integer4) {
      boolean var7 = integer2 < 0;
      boolean var8 = integer3 < 0;
      boolean var9 = integer4 < 0;
      int var10 = blockPos.getX() + (var7 ? integer2 : 0);
      int var11 = blockPos.getY() + (var8 ? integer3 : 0);
      int var12 = blockPos.getZ() + (var9 ? integer4 : 0);
      int var13 = blockPos.getX() + (var7 ? 0 : integer2) + 1;
      int var14 = blockPos.getY() + (var8 ? 0 : integer3) + 1;
      int var15 = blockPos.getZ() + (var9 ? 0 : integer4) + 1;
      return new AxisAlignedBB(var10, var11, var12, var13, var14, var15);
   }

   public static int func_179650_a(int integer) {
      integer %= 360;
      if (integer >= 160) {
         integer -= 360;
      }

      if (integer < 0) {
         integer += 360;
      }

      return integer;
   }

   private static BlockPos func_179664_b(Map<String, String> map, BlockPos blockPos) {
      return new BlockPos(
         parseIntWithDefault(map, "x", blockPos.getX()), parseIntWithDefault(map, "y", blockPos.getY()), parseIntWithDefault(map, "z", blockPos.getZ())
      );
   }

   private static boolean func_179665_h(Map<String, String> map) {
      for (String var5 : WORLD_BINDING_ARGS) {
         if (map.containsKey(var5)) {
            return true;
         }
      }

      return false;
   }

   private static int parseIntWithDefault(Map<String, String> map, String string, int integer) {
      return map.containsKey(string) ? MathHelper.parseIntWithDefault((String)map.get(string), integer) : integer;
   }

   private static String func_179651_b(Map<String, String> map, String string) {
      return (String)map.get(string);
   }

   public static Map<String, Integer> func_96560_a(Map<String, String> map) {
      HashMap var4 = Maps.newHashMap();

      for (String var6 : map.keySet()) {
         if (var6.startsWith("score_") && var6.length() > 6) {
            var4.put(var6.substring(6), MathHelper.parseIntWithDefault((String)map.get(var6), 1));
         }
      }

      return var4;
   }

   public static boolean matchesMultiplePlayers(String string) {
      Matcher var4 = tokenPattern.matcher(string);
      if (!var4.matches()) {
         return false;
      }

      Map var5 = getArgumentMap(var4.group(2));
      String var6 = var4.group(1);
      int var7 = !"a".equals(var6) && !"e".equals(var6) ? 1 : 0;
      return parseIntWithDefault(var5, "c", var7) != 1;
   }

   public static boolean hasArguments(String string) {
      return tokenPattern.matcher(string).matches();
   }

   private static Map<String, String> getArgumentMap(String string) {
      HashMap var4 = Maps.newHashMap();
      if (string != null) {
         int var5 = 0;
         int var6 = -1;

         for (Matcher var7 = intListPattern.matcher(string); var7.find(); var6 = var7.end()) {
            String var8 = null;
            switch (var5++) {
               case 0:
                  var8 = "x";
                  break;
               case 1:
                  var8 = "y";
                  break;
               case 2:
                  var8 = "z";
                  break;
               case 3:
                  var8 = "r";
            }

            if (var8 != null && var7.group(1).length() > 0) {
               var4.put(var8, var7.group(1));
            }
         }

         if (var6 < string.length()) {
            Matcher var9 = keyValueListPattern.matcher(var6 == -1 ? string : string.substring(var6));

            while (var9.find()) {
               var4.put(var9.group(1), var9.group(2));
            }
         }
      }

      return var4;
   }
}
