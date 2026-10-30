package net.minecraft.stats;

import net.minecraft.item.Item;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.util.IChatComponent;

public class StatCrafting extends StatBase {
   private final Item field_150960_a;

   public StatCrafting(String string1, String string2, IChatComponent iChatComponent, Item item) {
      super(string1 + string2, iChatComponent);
      this.field_150960_a = item;
      int var7 = Item.getIdFromItem(item);
      if (var7 != 0) {
         IScoreObjectiveCriteria.INSTANCES.put(string1 + var7, this.getCriteria());
      }
   }

   public Item func_150959_a() {
      return this.field_150960_a;
   }
}
