package net.minecraft.stats;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S37PacketStatistics;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IJsonSerializable;
import net.minecraft.util.TupleIntJsonSerializable;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StatisticsFile extends StatFileWriter {
   private static final Logger logger = LogManager.getLogger();
   private final MinecraftServer mcServer;
   private final File statsFile;
   private final Set<StatBase> field_150888_e = Sets.newHashSet();
   private int field_150885_f = -300;
   private boolean field_150886_g = false;

   public StatisticsFile(MinecraftServer minecraftServer, File file) {
      this.mcServer = minecraftServer;
      this.statsFile = file;
   }

   public void Method559() {
      if (this.statsFile.isFile()) {
         try {
            this.statsData.clear();
            this.statsData.putAll(this.parseJson(FileUtils.readFileToString(this.statsFile)));
         } catch (IOException var5) {
            logger.error("Couldn't read statistics file " + this.statsFile, var5);
         } catch (JsonParseException var6) {
            logger.error("Couldn't parse statistics file " + this.statsFile, var6);
         }
      }
   }

   public void saveStatFile() {
      try {
         FileUtils.writeStringToFile(this.statsFile, dumpJson(this.statsData));
      } catch (IOException var2) {
         logger.error("Couldn't save stats", var2);
      }
   }

   @Override
   public void unlockAchievement(EntityPlayer entityPlayer, StatBase statBase, int integer) {
      int var7 = statBase.isAchievement() ? this.readStat(statBase) : 0;
      super.unlockAchievement(entityPlayer, statBase, integer);
      this.field_150888_e.add(statBase);
      if (statBase.isAchievement() && var7 == 0 && integer > 0) {
         this.field_150886_g = true;
         if (this.mcServer.isAnnouncingPlayerAchievements()) {
            this.mcServer
               .getConfigurationManager()
               .sendChatMsg(new ChatComponentTranslation("chat.type.achievement", entityPlayer.getDisplayName(), statBase.createChatComponent()));
         }
      }

      if (statBase.isAchievement() && var7 > 0 && integer == 0) {
         this.field_150886_g = true;
         if (this.mcServer.isAnnouncingPlayerAchievements()) {
            this.mcServer
               .getConfigurationManager()
               .sendChatMsg(new ChatComponentTranslation("chat.type.achievement.taken", entityPlayer.getDisplayName(), statBase.createChatComponent()));
         }
      }
   }

   public Set<StatBase> func_150878_c() {
      HashSet var1 = Sets.newHashSet(this.field_150888_e);
      this.field_150888_e.clear();
      this.field_150886_g = false;
      return var1;
   }

   public Map<StatBase, TupleIntJsonSerializable> parseJson(String string) {
      JsonElement var5 = JsonParser.parseString(string);
      if (!var5.isJsonObject()) {
         return Maps.newHashMap();
      }

      JsonObject var6 = var5.getAsJsonObject();
      HashMap var7 = Maps.newHashMap();

      for (Entry var9 : var6.entrySet()) {
         StatBase var10 = StatList.getOneShotStat((String)var9.getKey());
         if (var10 != null) {
            TupleIntJsonSerializable var11 = new TupleIntJsonSerializable();
            if (((JsonElement)var9.getValue()).isJsonPrimitive() && ((JsonElement)var9.getValue()).getAsJsonPrimitive().isNumber()) {
               var11.setIntegerValue(((JsonElement)var9.getValue()).getAsInt());
            } else if (((JsonElement)var9.getValue()).isJsonObject()) {
               JsonObject var12 = ((JsonElement)var9.getValue()).getAsJsonObject();
               if (var12.has("value") && var12.get("value").isJsonPrimitive() && var12.get("value").getAsJsonPrimitive().isNumber()) {
                  var11.setIntegerValue(var12.getAsJsonPrimitive("value").getAsInt());
               }

               if (var12.has("progress") && var10.func_150954_l() != null) {
                  try {
                     IJsonSerializable var14 = var10.func_150954_l().getConstructor().newInstance();
                     var14.fromJson(var12.get("progress"));
                     var11.setJsonSerializableValue(var14);
                  } catch (Throwable var15) {
                     logger.warn("Invalid statistic progress in " + this.statsFile, var15);
                  }
               }
            }

            var7.put(var10, var11);
         } else {
            logger.warn("Invalid statistic in " + this.statsFile + ": Don't know what " + (String)var9.getKey() + " is");
         }
      }

      return var7;
   }

   public static String dumpJson(Map<StatBase, TupleIntJsonSerializable> map) {
      JsonObject var4 = new JsonObject();

      for (Entry var6 : map.entrySet()) {
         if (((TupleIntJsonSerializable)var6.getValue()).getJsonSerializableValue() != null) {
            JsonObject var7 = new JsonObject();
            var7.addProperty("value", ((TupleIntJsonSerializable)var6.getValue()).getIntegerValue());

            try {
               var7.add("progress", ((TupleIntJsonSerializable)var6.getValue()).<IJsonSerializable>getJsonSerializableValue().getSerializableElement());
            } catch (Throwable var9) {
               logger.warn("Couldn't save statistic " + ((StatBase)var6.getKey()).getStatName() + ": error serializing progress", var9);
            }

            var4.add(((StatBase)var6.getKey()).statId, var7);
         } else {
            var4.addProperty(((StatBase)var6.getKey()).statId, ((TupleIntJsonSerializable)var6.getValue()).getIntegerValue());
         }
      }

      return var4.toString();
   }

   public void func_150877_d() {
      this.field_150888_e.addAll(this.statsData.keySet());
   }

   public void func_150876_a(EntityPlayerMP entityPlayerMP) {
      int var5 = this.mcServer.getTickCounter();
      HashMap var6 = Maps.newHashMap();
      if (this.field_150886_g || var5 - this.field_150885_f > 300) {
         this.field_150885_f = var5;

         for (StatBase var8 : this.func_150878_c()) {
            var6.put(var8, this.readStat(var8));
         }
      }

      entityPlayerMP.playerNetServerHandler.sendPacket(new S37PacketStatistics(var6));
   }

   public void sendAchievements(EntityPlayerMP entityPlayerMP) {
      HashMap var5 = Maps.newHashMap();

      for (Achievement var7 : AchievementList.achievementList) {
         if (this.hasAchievementUnlocked(var7)) {
            var5.put(var7, this.readStat(var7));
            this.field_150888_e.remove(var7);
         }
      }

      entityPlayerMP.playerNetServerHandler.sendPacket(new S37PacketStatistics(var5));
   }

   public boolean func_150879_e() {
      return this.field_150886_g;
   }
}
