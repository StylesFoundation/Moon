package com.moon.events.impl.render;

import com.moon.events.EventCancellable;
import net.minecraft.scoreboard.ScoreObjective;

public class ScoreboardRenderEvent extends EventCancellable {
   private final ScoreObjective objective;

   public ScoreObjective getObjective() {
      return this.objective;
   }

   public ScoreboardRenderEvent(ScoreObjective objective) {
      this.objective = objective;
   }
}
