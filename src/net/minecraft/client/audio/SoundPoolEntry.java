package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

public class SoundPoolEntry {
   private final ResourceLocation location;
   private final boolean streamingSound;
   private double pitch;
   private double volume;

   public SoundPoolEntry(ResourceLocation resourceLocation, double double2, double double3, boolean boolean4) {
      this.location = resourceLocation;
      this.pitch = double2;
      this.volume = double3;
      this.streamingSound = boolean4;
   }

   public SoundPoolEntry(SoundPoolEntry soundPoolEntry) {
      this.location = soundPoolEntry.location;
      this.pitch = soundPoolEntry.pitch;
      this.volume = soundPoolEntry.volume;
      this.streamingSound = soundPoolEntry.streamingSound;
   }

   public ResourceLocation getSoundPoolEntryLocation() {
      return this.location;
   }

   public double getPitch() {
      return this.pitch;
   }

   public void setPitch(double double1) {
      this.pitch = double1;
   }

   public double getVolume() {
      return this.volume;
   }

   public void setVolume(double double1) {
      this.volume = double1;
   }

   public boolean isStreamingSound() {
      return this.streamingSound;
   }
}
