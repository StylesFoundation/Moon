package net.minecraft.scoreboard;

public class ScoreObjective {
   private final Scoreboard theScoreboard;
   private final String name;
   private final IScoreObjectiveCriteria objectiveCriteria;
   private IScoreObjectiveCriteria.EnumRenderType renderType;
   private String displayName;

   public ScoreObjective(Scoreboard scoreboard, String string, IScoreObjectiveCriteria iScoreObjectiveCriteria) {
      this.theScoreboard = scoreboard;
      this.name = string;
      this.objectiveCriteria = iScoreObjectiveCriteria;
      this.displayName = string;
      this.renderType = iScoreObjectiveCriteria.getRenderType();
   }

   public Scoreboard getScoreboard() {
      return this.theScoreboard;
   }

   public String getName() {
      return this.name;
   }

   public IScoreObjectiveCriteria getCriteria() {
      return this.objectiveCriteria;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public void setDisplayName(String string) {
      this.displayName = string;
      this.theScoreboard.onObjectiveDisplayNameChanged(this);
   }

   public IScoreObjectiveCriteria.EnumRenderType getRenderType() {
      return this.renderType;
   }

   public void setRenderType(IScoreObjectiveCriteria.EnumRenderType enumRenderType) {
      this.renderType = enumRenderType;
      this.theScoreboard.onObjectiveDisplayNameChanged(this);
   }
}
