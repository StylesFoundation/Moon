package net.minecraft.scoreboard;

import java.util.Comparator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class Score {
   public static final Comparator<Score> a = (score1, score2) -> score1.getScorePoints() > score2.getScorePoints()
      ? 1
      : (score1.getScorePoints() < score2.getScorePoints() ? -1 : score2.getPlayerName().compareToIgnoreCase(score1.getPlayerName()));
   private final Scoreboard theScoreboard;
   private final ScoreObjective theScoreObjective;
   private final String scorePlayerName;
   private int scorePoints;
   private boolean locked;
   private boolean forceUpdate;

   public Score(Scoreboard scoreboard, ScoreObjective scoreObjective, String string) {
      this.theScoreboard = scoreboard;
      this.theScoreObjective = scoreObjective;
      this.scorePlayerName = string;
      this.forceUpdate = true;
   }

   public void increseScore(int integer) {
      if (this.theScoreObjective.getCriteria().isReadOnly()) {
         throw new IllegalStateException("Cannot modify read-only score");
      }

      this.setScorePoints(this.getScorePoints() + integer);
   }

   public void decreaseScore(int integer) {
      if (this.theScoreObjective.getCriteria().isReadOnly()) {
         throw new IllegalStateException("Cannot modify read-only score");
      }

      this.setScorePoints(this.getScorePoints() - integer);
   }

   public void func_96648_a() {
      if (this.theScoreObjective.getCriteria().isReadOnly()) {
         throw new IllegalStateException("Cannot modify read-only score");
      }

      this.increseScore(1);
   }

   public int getScorePoints() {
      return this.scorePoints;
   }

   public void setScorePoints(int integer) {
      int var5 = this.scorePoints;
      this.scorePoints = integer;
      if (var5 != integer || this.forceUpdate) {
         this.forceUpdate = false;
         this.getScoreScoreboard().func_96536_a(this);
      }
   }

   public ScoreObjective getObjective() {
      return this.theScoreObjective;
   }

   public String getPlayerName() {
      return this.scorePlayerName;
   }

   public Scoreboard getScoreScoreboard() {
      return this.theScoreboard;
   }

   public boolean isLocked() {
      return this.locked;
   }

   public void setLocked(boolean boolean1) {
      this.locked = boolean1;
   }

   public void func_96651_a(List<EntityPlayer> list) {
      this.setScorePoints(this.theScoreObjective.getCriteria().setScore(list));
   }
}
