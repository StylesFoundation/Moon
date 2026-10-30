package net.minecraft.stats;

import net.minecraft.util.IChatComponent;

public class StatBasic extends StatBase {
   public StatBasic(String string, IChatComponent iChatComponent, IStatType iStatType) {
      super(string, iChatComponent, iStatType);
   }

   public StatBasic(String string, IChatComponent iChatComponent) {
      super(string, iChatComponent);
   }

   @Override
   public StatBase registerStat() {
      super.registerStat();
      StatList.generalStats.add(this);
      return this;
   }
}
