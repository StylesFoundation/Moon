package com.moon.util.client;

import org.lwjgl.Sys;

public class PreciseTicker {
   private long nextTickNanos = 0L;
   private boolean initialized = false;
   private final RollingAverage sleepBudget = new RollingAverage(10);
   private final RollingAverage yieldBudget = new RollingAverage(10);

   public void runAt(int tickRate) {
      if (tickRate > 0) {
         if (!this.initialized) {
            this.init();
         }

         try {
            long now = this.nanotime();

            while (this.nextTickNanos - now > this.sleepBudget.average()) {
               Thread.sleep(1L);
               long sleepEnd;
               this.sleepBudget.push((sleepEnd = this.nanotime()) - now);
               now = sleepEnd;
            }

            this.sleepBudget.decay();
            now = this.nanotime();

            while (this.nextTickNanos - now > this.yieldBudget.average()) {
               Thread.yield();
               long yieldEnd;
               this.yieldBudget.push((yieldEnd = this.nanotime()) - now);
               now = yieldEnd;
            }
         } catch (InterruptedException e) {
            e.printStackTrace();
         }

         this.nextTickNanos = Math.max(this.nextTickNanos + 1000000000L / tickRate, this.nanotime());
      }
   }

   private void init() {
      this.initialized = true;
      this.sleepBudget.fill(1000000L);
      this.yieldBudget.fill((int)(-(this.nanotime() - this.nanotime()) * 1.333));
      this.nextTickNanos = this.nanotime();
      if (System.getProperty("os.name").startsWith("Win")) {
         Thread timerThread = new Thread(() -> {
            try {
               Thread.sleep(Long.MAX_VALUE);
            } catch (Exception ignored) {
            }
         });
         timerThread.setName("LWJGL Timer");
         timerThread.setDaemon(true);
         timerThread.start();
      }
   }

   private long nanotime() {
      return System.currentTimeMillis() * 1000000000L / Sys.getTimerResolution();
   }
}
