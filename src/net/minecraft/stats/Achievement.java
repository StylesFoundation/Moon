package net.minecraft.stats;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IJsonSerializable;
import net.minecraft.util.StatCollector;

public class Achievement extends StatBase {
   public final int displayColumn;
   public final int displayRow;
   public final Achievement parentAchievement;
   private final String achievementDescription;
   private IStatStringFormat statStringFormatter;
   public final ItemStack theItemStack;
   private boolean isSpecial;

   public Achievement(String string1, String string2, int integer3, int integer4, Item item, Achievement achievement) {
      this(string1, string2, integer3, integer4, new ItemStack(item), achievement);
   }

   public Achievement(String string1, String string2, int integer3, int integer4, Block block, Achievement achievement) {
      this(string1, string2, integer3, integer4, new ItemStack(block), achievement);
   }

   public Achievement(String string1, String string2, int integer3, int integer4, ItemStack itemStack, Achievement achievement) {
      super(string1, new ChatComponentTranslation("achievement." + string2));
      this.theItemStack = itemStack;
      this.achievementDescription = "achievement." + string2 + ".desc";
      this.displayColumn = integer3;
      this.displayRow = integer4;
      if (integer3 < AchievementList.a) {
         AchievementList.a = integer3;
      }

      if (integer4 < AchievementList.b) {
         AchievementList.b = integer4;
      }

      if (integer3 > AchievementList.c) {
         AchievementList.c = integer3;
      }

      if (integer4 > AchievementList.d) {
         AchievementList.d = integer4;
      }

      this.parentAchievement = achievement;
   }

   public Achievement initIndependentStat() {
      this.isIndependent = true;
      return this;
   }

   public Achievement setSpecial() {
      this.isSpecial = true;
      return this;
   }

   public Achievement registerStat() {
      super.registerStat();
      AchievementList.achievementList.add(this);
      return this;
   }

   @Override
   public boolean isAchievement() {
      return true;
   }

   @Override
   public IChatComponent getStatName() {
      IChatComponent var3 = super.getStatName();
      var3.getChatStyle().setColor(this.getSpecial() ? EnumChatFormatting.DARK_PURPLE : EnumChatFormatting.GREEN);
      return var3;
   }

   public Achievement func_150953_b(Class<? extends IJsonSerializable> class1) {
      return (Achievement)super.func_150953_b(class1);
   }

   public String getDescription() {
      return this.statStringFormatter != null
         ? this.statStringFormatter.formatString(StatCollector.translateToLocal(this.achievementDescription))
         : StatCollector.translateToLocal(this.achievementDescription);
   }

   public Achievement setStatStringFormatter(IStatStringFormat iStatStringFormat) {
      this.statStringFormatter = iStatStringFormat;
      return this;
   }

   public boolean getSpecial() {
      return this.isSpecial;
   }
}
