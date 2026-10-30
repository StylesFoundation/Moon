package com.moon.render.profiler;

public class RunningStatistics {
   private long totalNanos;
   private long lastNanos;
   private long frameNanos;
   private long lastFrameNanos;
   private int sampleCount;

   public void record(long nanos) {
      this.withTotalNanos(this.totalNanos + nanos);
      this.withFrameNanos(this.frameNanos + nanos);
      this.withLastNanos(nanos);
      this.sampleCount++;
   }

   public void reset() {
      this.withSampleCount(0);
      this.withTotalNanos(0L);
      this.withLastNanos(0L);
   }

   public void endFrame() {
      this.withLastFrameNanos(this.frameNanos);
      this.withFrameNanos(0L);
   }

   public long getAverage() {
      return this.totalNanos / this.sampleCount;
   }

   public long getTotalNanos() {
      return this.totalNanos;
   }

   public long getLastNanos() {
      return this.lastNanos;
   }

   public long getFrameNanos() {
      return this.frameNanos;
   }

   public long getLastFrameNanos() {
      return this.lastFrameNanos;
   }

   public int getSampleCount() {
      return this.sampleCount;
   }

   public RunningStatistics withTotalNanos(long totalNanos) {
      this.totalNanos = totalNanos;
      return this;
   }

   public RunningStatistics withLastNanos(long lastNanos) {
      this.lastNanos = lastNanos;
      return this;
   }

   public RunningStatistics withFrameNanos(long frameNanos) {
      this.frameNanos = frameNanos;
      return this;
   }

   public RunningStatistics withLastFrameNanos(long lastFrameNanos) {
      this.lastFrameNanos = lastFrameNanos;
      return this;
   }

   public RunningStatistics withSampleCount(int sampleCount) {
      this.sampleCount = sampleCount;
      return this;
   }

   private RunningStatistics() {
   }

   public static RunningStatistics create() {
      return new RunningStatistics();
   }
}
