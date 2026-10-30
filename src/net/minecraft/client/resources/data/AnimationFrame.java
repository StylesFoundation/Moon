package net.minecraft.client.resources.data;

public class AnimationFrame {
   private final int frameIndex;
   private final int frameTime;

   public AnimationFrame(int integer) {
      this(integer, -1);
   }

   public AnimationFrame(int integer1, int integer2) {
      this.frameIndex = integer1;
      this.frameTime = integer2;
   }

   public boolean hasNoTime() {
      return this.frameTime == -1;
   }

   public int getFrameTime() {
      return this.frameTime;
   }

   public int getFrameIndex() {
      return this.frameIndex;
   }
}
