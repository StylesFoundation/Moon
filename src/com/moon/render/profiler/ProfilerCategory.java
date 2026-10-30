package com.moon.render.profiler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public class ProfilerCategory {
   private final String id;
   private final String displayName;
   private final Map<String, ProfilerTimer> timers = new HashMap<>();

   public Optional<ProfilerTimer> getTimer(String id) {
      return Optional.ofNullable(this.timers.get(id));
   }

   public ProfilerTimer start(String id) {
      Optional<ProfilerTimer> timerOpt = this.getTimer(id);
      if (timerOpt.isEmpty()) {
         return null;
      }

      timerOpt.get().start();
      return timerOpt.get();
   }

   public void stopAll() {
      Iterator<ProfilerTimer> iterator = this.timers.values().iterator();

      while (iterator.hasNext()) {
         iterator.next().endFrame();
      }
   }

   public void register(ProfilerTimer timer) {
      this.timers.put(timer.getId(), timer);
   }

   public String getId() {
      return this.id;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public Map<String, ProfilerTimer> getTimers() {
      return this.timers;
   }

   private ProfilerCategory(String id, String displayName) {
      this.id = id;
      this.displayName = displayName;
   }

   public static ProfilerCategory of(String id, String displayName) {
      return new ProfilerCategory(id, displayName);
   }
}
