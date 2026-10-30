package net.minecraft.scoreboard;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class ScoreDummyCriteria implements IScoreObjectiveCriteria {
   private final String dummyName;

   public ScoreDummyCriteria(String string) {
      this.dummyName = string;
      IScoreObjectiveCriteria.INSTANCES.put(string, this);
   }

   @Override
   public String getName() {
      return this.dummyName;
   }

   @Override
   public int setScore(List<EntityPlayer> list) {
      return 0;
   }

   @Override
   public boolean isReadOnly() {
      return false;
   }

   @Override
   public IScoreObjectiveCriteria.EnumRenderType getRenderType() {
      return IScoreObjectiveCriteria.EnumRenderType.INTEGER;
   }
}
