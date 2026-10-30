package net.minecraft.client.resources.data;

import com.google.common.collect.Sets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnimationMetadataSection implements IMetadataSection {
   private final List<AnimationFrame> animationFrames;
   private final int frameWidth;
   private final int frameHeight;
   private final int frameTime;
   private final boolean interpolate;

   public AnimationMetadataSection(List<AnimationFrame> list, int integer2, int integer3, int integer4, boolean boolean5) {
      this.animationFrames = list;
      this.frameWidth = integer2;
      this.frameHeight = integer3;
      this.frameTime = integer4;
      this.interpolate = boolean5;
   }

   public int getFrameHeight() {
      return this.frameHeight;
   }

   public int getFrameWidth() {
      return this.frameWidth;
   }

   public int getFrameCount() {
      return this.animationFrames.size();
   }

   public int getFrameTime() {
      return this.frameTime;
   }

   public boolean isInterpolate() {
      return this.interpolate;
   }

   private AnimationFrame getAnimationFrame(int integer) {
      return this.animationFrames.get(integer);
   }

   public int getFrameTimeSingle(int integer) {
      AnimationFrame var5 = this.getAnimationFrame(integer);
      return var5.hasNoTime() ? this.frameTime : var5.getFrameTime();
   }

   public boolean frameHasTime(int integer) {
      return !this.animationFrames.get(integer).hasNoTime();
   }

   public int getFrameIndex(int integer) {
      return this.animationFrames.get(integer).getFrameIndex();
   }

   public Set<Integer> getFrameIndexSet() {
      HashSet var4 = Sets.newHashSet();

      for (AnimationFrame var6 : this.animationFrames) {
         var4.add(var6.getFrameIndex());
      }

      return var4;
   }
}
