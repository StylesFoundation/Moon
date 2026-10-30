package net.minecraft.util;

public class FrameTimer {
   private final long[] frames = new long[240];
   private int lastIndex;
   private int counter;
   private int index;

   public void addFrame(long long1) {
      this.frames[this.index] = long1;
      this.index++;
      if (this.index == 240) {
         this.index = 0;
      }

      if (this.counter < 240) {
         this.lastIndex = 0;
         this.counter++;
      } else {
         this.lastIndex = this.parseIndex(this.index + 1);
      }
   }

   public int getLagometerValue(long long1, int integer) {
      return (int)(long1 / 1.6666666E7 * integer);
   }

   public int getLastIndex() {
      return this.lastIndex;
   }

   public int getIndex() {
      return this.index;
   }

   public int parseIndex(int integer) {
      return integer % 240;
   }

   public long[] getFrames() {
      return this.frames;
   }
}
