package net.minecraft.util;

import com.moon.Moon;
import com.moon.events.impl.player.TimerUpdateEvent;
import net.minecraft.client.Minecraft;

public class Timer {
   public int elapsedTicks;
   public float renderPartialTicks;
   public float timerSpeed = 1.0F;
   public float elapsedPartialTicks;
   public float ticksPerSecond;
   private double lastHRTime;
   private long lastSyncSysClock;
   private long lastSyncHRClock;
   private long counter;
   private double timeSyncAdjustment = 1.0;
   private double k = 0.0;

   public Timer(float float1) {
      this.ticksPerSecond = float1;
      this.lastSyncSysClock = Minecraft.getSystemTime();
      this.lastSyncHRClock = System.nanoTime() / 1000000L;
   }

   public void updateTimer() {
      long var4 = Minecraft.getSystemTime();
      long var6 = var4 - this.lastSyncSysClock;
      long var8 = System.nanoTime() / 1000000L;
      double var10 = var8 / 1000.0;
      TimerUpdateEvent var12 = new TimerUpdateEvent(this.k);
      Moon.getInstance().getEventBus().publish(var12);
      if (var6 <= 1000L && var6 >= 0L) {
         this.counter += var6;
         if (this.counter > 1000L) {
            long var13 = var8 - this.lastSyncHRClock;
            double var15 = (double)this.counter / var13;
            this.timeSyncAdjustment = this.timeSyncAdjustment + (var15 - this.timeSyncAdjustment) * 0.2F;
            this.lastSyncHRClock = var8;
            this.counter = 0L;
         }

         if (this.counter < 0L) {
            this.lastSyncHRClock = var8;
         }
      } else {
         this.lastHRTime = var10;
      }

      this.lastSyncSysClock = var4;
      double var17 = (var10 - this.lastHRTime) * this.timeSyncAdjustment;
      this.lastHRTime = var10;
      var17 = MathHelper.clamp_double(var17, 0.0, 1.0);
      this.elapsedPartialTicks = (float)(this.elapsedPartialTicks + var17 * this.timerSpeed * this.ticksPerSecond);
      if (this.k > this.elapsedPartialTicks) {
         this.k = this.k - this.elapsedPartialTicks;
         this.elapsedPartialTicks = 0.0F;
      } else {
         this.elapsedPartialTicks = (float)(this.elapsedPartialTicks - this.k);
         this.k = 0.0;
      }

      this.elapsedPartialTicks = this.elapsedPartialTicks + var12.getExtraTicks();
      this.k = Math.max(var12.getBalance(), this.k);
      this.elapsedTicks = (int)this.elapsedPartialTicks;
      this.elapsedPartialTicks = this.elapsedPartialTicks - this.elapsedTicks;
      if (this.elapsedTicks > 10 + var12.getExtraTicks()) {
         this.elapsedTicks = 10 + var12.getExtraTicks();
      }

      this.renderPartialTicks = this.elapsedPartialTicks;
   }
}
