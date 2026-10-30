package net.optifine.shaders;

import java.nio.IntBuffer;
import java.util.Arrays;

public class FlipTextures {
   private final IntBuffer textures;
   private final int indexFlipped;
   private final boolean[] flips;
   private final boolean[] changed;

   public FlipTextures(IntBuffer intBuffer, int integer) {
      this.textures = intBuffer;
      this.indexFlipped = integer;
      this.flips = new boolean[intBuffer.capacity()];
      this.changed = new boolean[intBuffer.capacity()];
   }

   public int getA(int integer) {
      return this.get(integer, this.flips[integer]);
   }

   public int getB(int integer) {
      return this.get(integer, !this.flips[integer]);
   }

   private int get(int integer, boolean boolean2) {
      int var6 = boolean2 ? this.indexFlipped : 0;
      return this.textures.get(var6 + integer);
   }

   public void flip(int integer) {
      this.flips[integer] = !this.flips[integer];
      this.changed[integer] = true;
   }

   public boolean isChanged(int integer) {
      return this.changed[integer];
   }

   public void reset() {
      Arrays.fill(this.flips, false);
      Arrays.fill(this.changed, false);
   }
}
