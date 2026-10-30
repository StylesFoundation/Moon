package com.moon.util.animation;

public class Animation {
   private final Easing easing;
   private final long duration;
   private boolean increasing = false;
   private float endValue;
   private float startValue;
   private boolean started;
   private float value;
   private long startTime;
   private long pauseDuration;
   private boolean paused;
   private boolean finished;

   public Animation(long duration, Easing easing) {
      this.duration = duration;
      this.startTime = System.currentTimeMillis();
      this.easing = easing;
   }

   public void setValue(float value) {
      this.value = value;
      this.startTime = System.currentTimeMillis();
      this.finished = false;
   }

   public void pause() {
      this.pauseDuration = this.getElapsedTime();
      if (this.pauseDuration < this.duration) {
         this.paused = true;
      }

      this.startTime = System.currentTimeMillis();
      this.finished = false;
   }

   public long getElapsedTime() {
      return Math.max(Math.min(this.duration - (this.startTime + this.duration - System.currentTimeMillis()), this.duration), 0L);
   }

   public void animate(float startValue, float target, boolean increasing) {
      if (!this.started) {
         this.startTime = System.currentTimeMillis();
         this.started = true;
      }

      if (!this.isDone() && !this.finished) {
         if (increasing) {
            this.value = startValue + (this.value + (target - startValue - this.value)) * this.getEasedFactor();
         } else {
            this.value = target - (this.value + (target - startValue - this.value)) * this.getEasedFactor();
         }

         this.increasing = increasing;
         this.endValue = target;
         this.startValue = startValue;
      }
   }

   public boolean isDone() {
      long currentDuration = this.paused ? this.pauseDuration : this.duration;
      if (System.currentTimeMillis() >= this.startTime + currentDuration) {
         this.paused = false;
      }

      return System.currentTimeMillis() >= this.startTime + currentDuration;
   }

   private float getEasedFactor() {
      float progress = this.getLinearProgress();
      switch (this.easing) {
         case EASE_IN_OUT_CUBIC:
            return progress < 0.5 ? 4.0F * progress * progress * progress : (float)(1.0 - Math.pow(-2.0F * progress + 2.0F, 3.0) / 2.0);
         case EASE_OUT_EXPO:
            return progress == 1.0F ? 1.0F : (float)(1.0 - Math.pow(2.0, -10.0F * progress));
         case EASE_IN_BACK:
            return (float)(2.70158 * progress * progress * progress - 1.70158 * progress * progress);
         case EASE_OUT_BACK:
            return (float)(1.0 + 2.70158F * Math.pow(progress - 1.0F, 3.0) + 1.70158F * Math.pow(progress - 1.0F, 2.0));
         case EASE_OUT_ELASTIC:
            return progress == 0.0F
               ? 0.0F
               : (float)(progress == 1.0F ? 1.0 : Math.pow(2.0, -10.0F * progress) * Math.sin((progress * 10.0F - 0.75) * (float) (Math.PI * 2.0 / 3.0)) + 1.0);
         case EASE_IN_SINE_OUT:
            return (float)(-(Math.cos(Math.PI * progress) - 1.0) / 2.0);
         case QUINT_SIN:
            return (float)(1.0 - Math.pow(1.0F - progress, 5.0));
         case EASE_OUT_CIRC:
            return 1.0F - (1.0F - progress) * (1.0F - progress);
         case TEST:
            return (float)Math.sqrt(1.0 - Math.pow(progress - 1.0F, 2.0));
         case EASE_IN_OUT_BACK:
            return (float)(
               progress < 0.5
                  ? Math.pow(2.0F * progress, 2.0) * (7.1898193F * progress - 2.5949097F) / 2.0
                  : (Math.pow(2.0F * progress - 2.0F, 2.0) * (3.5949097F * (progress * 2.0F - 2.0F) + 2.5949097F) + 2.0) / 2.0
            );
         default:
            return this.getLinearProgress();
      }
   }

   private float getLinearProgress() {
      long currentDuration = this.paused ? this.pauseDuration : this.duration;
      return (float)((double)(currentDuration - (this.startTime + currentDuration - System.currentTimeMillis())) / currentDuration);
   }

   public float getValue() {
      if (this.isDone() && !this.finished) {
         return this.increasing ? this.endValue : this.startValue;
      } else {
         return this.value;
      }
   }

   public void forceValue(float value) {
      this.value = value;
   }

   public long getDuration() {
      return this.duration;
   }

   public Easing getEasing() {
      return this.easing;
   }

   public void finish() {
      this.finished = true;
   }

   public long getStartTime() {
      return this.startTime;
   }

   public Animation setStartTime(long startTime) {
      this.startTime = startTime;
      return this;
   }
}
