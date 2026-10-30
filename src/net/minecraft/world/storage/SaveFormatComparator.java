package net.minecraft.world.storage;

import net.minecraft.world.WorldSettings;

public class SaveFormatComparator implements Comparable<SaveFormatComparator> {
   private final String fileName;
   private final String displayName;
   private final long lastTimePlayed;
   private final long sizeOnDisk;
   private final boolean requiresConversion;
   private final WorldSettings.GameType theEnumGameType;
   private final boolean hardcore;
   private final boolean cheatsEnabled;

   public SaveFormatComparator(
      String string1, String string2, long long3, long long4, WorldSettings.GameType gameType, boolean boolean6, boolean boolean7, boolean boolean8
   ) {
      this.fileName = string1;
      this.displayName = string2;
      this.lastTimePlayed = long3;
      this.sizeOnDisk = long4;
      this.theEnumGameType = gameType;
      this.requiresConversion = boolean6;
      this.hardcore = boolean7;
      this.cheatsEnabled = boolean8;
   }

   public String getFileName() {
      return this.fileName;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public long getSizeOnDisk() {
      return this.sizeOnDisk;
   }

   public boolean Method1018() {
      return this.requiresConversion;
   }

   public long getLastTimePlayed() {
      return this.lastTimePlayed;
   }

   public int compareTo(SaveFormatComparator saveFormatComparator) {
      return this.lastTimePlayed < saveFormatComparator.lastTimePlayed
         ? 1
         : (this.lastTimePlayed > saveFormatComparator.lastTimePlayed ? -1 : this.fileName.compareTo(saveFormatComparator.fileName));
   }

   public WorldSettings.GameType getEnumGameType() {
      return this.theEnumGameType;
   }

   public boolean Method1019() {
      return this.hardcore;
   }

   public boolean Method1020() {
      return this.cheatsEnabled;
   }
}
