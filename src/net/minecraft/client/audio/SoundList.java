package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import java.util.List;

public class SoundList {
   private final List<SoundList.SoundEntry> soundList = Lists.newArrayList();
   private boolean replaceExisting;
   private SoundCategory category;

   public List<SoundList.SoundEntry> getSoundList() {
      return this.soundList;
   }

   public boolean canReplaceExisting() {
      return this.replaceExisting;
   }

   public void setReplaceExisting(boolean boolean1) {
      this.replaceExisting = boolean1;
   }

   public SoundCategory getSoundCategory() {
      return this.category;
   }

   public void setSoundCategory(SoundCategory soundCategory) {
      this.category = soundCategory;
   }

   public static class SoundEntry {
      private String name;
      private float volume = 1.0F;
      private float pitch = 1.0F;
      private int weight = 1;
      private SoundList.SoundEntry.Type type = SoundList.SoundEntry.Type.FILE;
      private boolean streaming = false;

      public String getSoundEntryName() {
         return this.name;
      }

      public void setSoundEntryName(String string) {
         this.name = string;
      }

      public float getSoundEntryVolume() {
         return this.volume;
      }

      public void setSoundEntryVolume(float float1) {
         this.volume = float1;
      }

      public float getSoundEntryPitch() {
         return this.pitch;
      }

      public void setSoundEntryPitch(float float1) {
         this.pitch = float1;
      }

      public int getSoundEntryWeight() {
         return this.weight;
      }

      public void setSoundEntryWeight(int integer) {
         this.weight = integer;
      }

      public SoundList.SoundEntry.Type getSoundEntryType() {
         return this.type;
      }

      public void setSoundEntryType(SoundList.SoundEntry.Type type) {
         this.type = type;
      }

      public boolean isStreaming() {
         return this.streaming;
      }

      public void setStreaming(boolean boolean1) {
         this.streaming = boolean1;
      }

      public enum Type {
         FILE("file"),
         SOUND_EVENT("event");

         private final String field_148583_c;

         Type(String string3) {
            this.field_148583_c = string3;
         }

         public static SoundList.SoundEntry.Type getType(String string) {
            for (SoundList.SoundEntry.Type var7 : values()) {
               if (var7.field_148583_c.equals(string)) {
                  return var7;
               }
            }

            return null;
         }

         private static SoundList.SoundEntry.Type[] Method934() {
            return new SoundList.SoundEntry.Type[]{FILE, SOUND_EVENT};
         }
      }
   }
}
