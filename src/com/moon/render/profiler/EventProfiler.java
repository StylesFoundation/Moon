package com.moon.render.profiler;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventProfiler {
   private final String id;
   private final String displayName;
   private final Map<String, ProfilerTimer> timers = new HashMap<>();

   public Optional<ProfilerTimer> getTimer(String id) {
      return Optional.ofNullable(this.timers.get(id));
   }

   public ProfilerTimer start(String id) {
      Optional<ProfilerTimer> timer = this.getTimer(id);
      if (timer.isEmpty()) {
         ProfilerTimer newTimer = ProfilerTimer.of(id, id);
         this.timers.put(id, newTimer);
         newTimer.start();
         return newTimer;
      } else {
         ProfilerTimer existingTimer = timer.get();
         existingTimer.start();
         return existingTimer;
      }
   }

   public LinkedHashSet<ProfilerTimer> getSortedTimers(boolean descending) {
      return this.timers
         .values()
         .stream()
         .sorted(Comparator.comparingLong(timer -> descending ? -timer.getStatistics().getAverage() : -timer.getStatistics().getLastFrameNanos()))
         .collect(Collectors.toCollection(LinkedHashSet::new));
   }

   public void endFrame() {
      for (ProfilerTimer timer : this.timers.values()) {
         timer.endFrame();
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

   private EventProfiler(String id, String displayName) {
      this.id = id;
      this.displayName = displayName;
   }

   public static EventProfiler of(String id, String displayName) {
      return new EventProfiler(id, displayName);
   }
}
