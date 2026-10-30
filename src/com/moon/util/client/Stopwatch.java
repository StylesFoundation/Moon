package com.moon.util.client;

public final class Stopwatch {
   private long lastTime = System.nanoTime() / 1000000L;

   public boolean hasElapsed(long milliseconds) {
      return this.getElapsedTime() >= milliseconds;
   }

   public void reset() {
      this.lastTime = System.nanoTime() / 1000000L;
   }

   public boolean hasElapsedAndReset(long milliseconds) {
      if (this.getElapsedTime() >= milliseconds) {
         this.reset();
         return true;
      } else {
         return false;
      }
   }

   public long getElapsedTime() {
      return System.nanoTime() / 1000000L - this.lastTime;
   }
}
