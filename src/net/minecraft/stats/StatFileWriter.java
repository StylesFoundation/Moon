package net.minecraft.stats;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IJsonSerializable;
import net.minecraft.util.TupleIntJsonSerializable;

public class StatFileWriter {
   protected final Map<StatBase, TupleIntJsonSerializable> statsData = Maps.newConcurrentMap();

   public boolean hasAchievementUnlocked(Achievement achievement) {
      return this.readStat(achievement) > 0;
   }

   public boolean canUnlockAchievement(Achievement achievement) {
      return achievement.parentAchievement == null || this.hasAchievementUnlocked(achievement.parentAchievement);
   }

   public int func_150874_c(Achievement achievement) {
      if (this.hasAchievementUnlocked(achievement)) {
         return 0;
      }

      int var5 = 0;

      for (Achievement var6 = achievement.parentAchievement; var6 != null && !this.hasAchievementUnlocked(var6); var5++) {
         var6 = var6.parentAchievement;
      }

      return var5;
   }

   public void increaseStat(EntityPlayer entityPlayer, StatBase statBase, int integer) {
      if (!statBase.isAchievement() || this.canUnlockAchievement((Achievement)statBase)) {
         this.unlockAchievement(entityPlayer, statBase, this.readStat(statBase) + integer);
      }
   }

   public void unlockAchievement(EntityPlayer entityPlayer, StatBase statBase, int integer) {
      TupleIntJsonSerializable var7 = this.statsData.get(statBase);
      if (var7 == null) {
         var7 = new TupleIntJsonSerializable();
         this.statsData.put(statBase, var7);
      }

      var7.setIntegerValue(integer);
   }

   public int readStat(StatBase statBase) {
      TupleIntJsonSerializable var5 = this.statsData.get(statBase);
      return var5 == null ? 0 : var5.getIntegerValue();
   }

   public <T extends IJsonSerializable> T func_150870_b(StatBase statBase) {
      TupleIntJsonSerializable var5 = this.statsData.get(statBase);
      return var5 != null ? var5.getJsonSerializableValue() : null;
   }

   public <T extends IJsonSerializable> T func_150872_a(StatBase statBase, T iJsonSerializable) {
      TupleIntJsonSerializable var6 = this.statsData.get(statBase);
      if (var6 == null) {
         var6 = new TupleIntJsonSerializable();
         this.statsData.put(statBase, var6);
      }

      var6.setJsonSerializableValue(iJsonSerializable);
      return (T)iJsonSerializable;
   }
}
