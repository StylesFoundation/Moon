package net.minecraft.client.audio;

public class SoundEventAccessor implements ISoundEventAccessor<SoundPoolEntry> {
   private final SoundPoolEntry entry;
   private final int weight;

   SoundEventAccessor(SoundPoolEntry soundPoolEntry, int integer) {
      this.entry = soundPoolEntry;
      this.weight = integer;
   }

   @Override
   public int getWeight() {
      return this.weight;
   }

   public SoundPoolEntry cloneEntry() {
      return new SoundPoolEntry(this.entry);
   }
}
