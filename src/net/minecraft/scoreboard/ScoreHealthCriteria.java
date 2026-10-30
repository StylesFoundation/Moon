package net.minecraft.scoreboard;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class ScoreHealthCriteria extends ScoreDummyCriteria {
   public ScoreHealthCriteria(String string) {
      super(string);
   }

   @Override
   public int setScore(List<EntityPlayer> list) {
      float var5 = 0.0F;

      for (EntityPlayer var7 : list) {
         var5 += var7.getHealth() + var7.getAbsorptionAmount();
      }

      if (list.size() > 0) {
         var5 /= list.size();
      }

      return MathHelper.ceiling_float_int(var5);
   }

   @Override
   public boolean isReadOnly() {
      return true;
   }

   @Override
   public IScoreObjectiveCriteria.EnumRenderType getRenderType() {
      return IScoreObjectiveCriteria.EnumRenderType.HEARTS;
   }
}
