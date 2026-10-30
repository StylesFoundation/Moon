package com.moon.render.profiler;

public class ProfilerTimer {
   private final String id;
   private final String displayName;
   private final RunningStatistics statistics = RunningStatistics.create();
   private long startTime;

   public void start() {
      this.startTime = System.nanoTime();
   }

   public void stop() {
      this.statistics.record(System.nanoTime() - this.startTime);
   }

   public void endFrame() {
      this.statistics.endFrame();
   }

   public String getId() {
      return this.id;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public RunningStatistics getStatistics() {
      return this.statistics;
   }

   public long getStartTime() {
      return this.startTime;
   }

   public ProfilerTimer withStartTime(long startTime) {
      this.startTime = startTime;
      return this;
   }

   private ProfilerTimer(String id, String displayName) {
      this.id = id;
      this.displayName = displayName;
   }

   public static ProfilerTimer of(String id, String displayName) {
      return new ProfilerTimer(id, displayName);
   }
}
