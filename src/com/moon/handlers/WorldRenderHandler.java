package com.moon.handlers;

import com.moon.events.impl.render.WorldRenderEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.visual.esp.projection.EspProjectionTask;
import com.moon.render.WorldRenderTask;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WorldRenderHandler {
   private final List<WorldRenderTask> tasks = new ArrayList<>();

   @Subscribe
   public void onWorldRender(WorldRenderEvent event) {
      Iterator iterator = this.tasks.iterator();

      while (iterator.hasNext()) {
         ((WorldRenderTask)iterator.next()).run();
      }
   }

   public void registerDefaults() {
      this.tasks.add(new EspProjectionTask());
   }
}
