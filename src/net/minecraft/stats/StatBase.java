package net.minecraft.stats;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import net.minecraft.event.HoverEvent;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IJsonSerializable;

public class StatBase {
   public final String statId;
   private final IChatComponent statName;
   public boolean isIndependent;
   private final IStatType type;
   private final IScoreObjectiveCriteria objectiveCriteria;
   private Class<? extends IJsonSerializable> field_150956_d;
   private static final NumberFormat numberFormat = NumberFormat.getIntegerInstance(Locale.US);
   public static IStatType simpleStatType = numberFormat::format;
   private static final DecimalFormat decimalFormat = new DecimalFormat("########0.00");
   public static IStatType timeStatType = integer -> {
      double var4 = integer / 20.0;
      double var6 = var4 / 60.0;
      double var8 = var6 / 60.0;
      double var10 = var8 / 24.0;
      double var12 = var10 / 365.0;
      return var12 > 0.5
         ? decimalFormat.format(var12) + " y"
         : (
            var10 > 0.5
               ? decimalFormat.format(var10) + " d"
               : (var8 > 0.5 ? decimalFormat.format(var8) + " h" : (var6 > 0.5 ? decimalFormat.format(var6) + " m" : var4 + " s"))
         );
   };
   public static IStatType distanceStatType = integer -> {
      double var4 = integer / 100.0;
      double var6 = var4 / 1000.0;
      return var6 > 0.5 ? decimalFormat.format(var6) + " km" : (var4 > 0.5 ? decimalFormat.format(var4) + " m" : integer + " cm");
   };
   public static IStatType field_111202_k = integer -> decimalFormat.format(integer * 0.1);

   public StatBase(String string, IChatComponent iChatComponent, IStatType iStatType) {
      this.statId = string;
      this.statName = iChatComponent;
      this.type = iStatType;
      this.objectiveCriteria = new ObjectiveStat(this);
      IScoreObjectiveCriteria.INSTANCES.put(this.objectiveCriteria.getName(), this.objectiveCriteria);
   }

   public StatBase(String string, IChatComponent iChatComponent) {
      this(string, iChatComponent, simpleStatType);
   }

   public StatBase initIndependentStat() {
      this.isIndependent = true;
      return this;
   }

   public StatBase registerStat() {
      if (StatList.oneShotStats.containsKey(this.statId)) {
         throw new RuntimeException(
            "Duplicate stat id: \"" + StatList.oneShotStats.get(this.statId).statName + "\" and \"" + this.statName + "\" at id " + this.statId
         );
      }

      StatList.allStats.add(this);
      StatList.oneShotStats.put(this.statId, this);
      return this;
   }

   public boolean isAchievement() {
      return false;
   }

   public String format(int integer) {
      return this.type.format(integer);
   }

   public IChatComponent getStatName() {
      IChatComponent var4 = this.statName.createCopy();
      var4.getChatStyle().setColor(EnumChatFormatting.GRAY);
      var4.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ACHIEVEMENT, new ChatComponentText(this.statId)));
      return var4;
   }

   public IChatComponent createChatComponent() {
      IChatComponent var1 = this.getStatName();
      IChatComponent var2 = new ChatComponentText("[").appendSibling(var1).appendText("]");
      var2.setChatStyle(var1.getChatStyle());
      return var2;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (object != null && this.getClass() == object.getClass()) {
         StatBase var5 = (StatBase)object;
         return this.statId.equals(var5.statId);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.statId.hashCode();
   }

   @Override
   public String toString() {
      return "Stat{id="
         + this.statId
         + ", nameId="
         + this.statName
         + ", awardLocallyOnly="
         + this.isIndependent
         + ", formatter="
         + this.type
         + ", objectiveCriteria="
         + this.objectiveCriteria
         + "}";
   }

   public IScoreObjectiveCriteria getCriteria() {
      return this.objectiveCriteria;
   }

   public Class<? extends IJsonSerializable> func_150954_l() {
      return this.field_150956_d;
   }

   public StatBase func_150953_b(Class<? extends IJsonSerializable> class1) {
      this.field_150956_d = class1;
      return this;
   }
}
