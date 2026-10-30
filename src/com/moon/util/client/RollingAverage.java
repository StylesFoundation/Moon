package com.moon.util.client;

class RollingAverage {
   private final long[] samples;
   private int index;

   public RollingAverage(int size) {
      this.samples = new long[size];
      this.index = 0;
   }

   public void fill(long value) {
      while (this.index < this.samples.length) {
         this.samples[this.index++] = value;
      }
   }

   public void push(long value) {
      this.samples[this.index++ % this.samples.length] = value;
      this.index = this.index % this.samples.length;
   }

   public long average() {
      long sum = 0L;

      for (long sample : this.samples) {
         sum += sample;
      }

      return sum / this.samples.length;
   }

   public void decay() {
      if (this.average() > 10000000L) {
         for (int i = 0; i < this.samples.length; i++) {
            this.samples[i] = (long)((float)this.samples[i] * 0.9F);
         }
      }
   }
}
