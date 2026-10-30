package net.minecraft.client.audio;

import com.google.common.collect.Maps;
import java.util.Map;

public enum SoundCategory {
   MASTER("master", 0),
   MUSIC("music", 1),
   RECORDS("record", 2),
   WEATHER("weather", 3),
   BLOCKS("block", 4),
   MOBS("hostile", 5),
   ANIMALS("neutral", 6),
   PLAYERS("player", 7),
   AMBIENT("ambient", 8);

   private static final Map<String, SoundCategory> NAME_CATEGORY_MAP = Maps.newHashMap();
   private static final Map<Integer, SoundCategory> ID_CATEGORY_MAP = Maps.newHashMap();
   private final String categoryName;
   private final int categoryId;

   SoundCategory(String string3, int integer4) {
      this.categoryName = string3;
      this.categoryId = integer4;
   }

   public String getCategoryName() {
      return this.categoryName;
   }

   public int getCategoryId() {
      return this.categoryId;
   }

   public static SoundCategory getCategory(String string) {
      return NAME_CATEGORY_MAP.get(string);
   }

   private static SoundCategory[] Method362() {
      return new SoundCategory[]{MASTER, MUSIC, RECORDS, WEATHER, BLOCKS, MOBS, ANIMALS, PLAYERS, AMBIENT};
   }

   static {
      for (SoundCategory var16 : values()) {
         if (NAME_CATEGORY_MAP.containsKey(var16.getCategoryName()) || ID_CATEGORY_MAP.containsKey(var16.getCategoryId())) {
            throw new Error("Clash in Sound Category ID & Name pools! Cannot insert " + var16);
         }

         NAME_CATEGORY_MAP.put(var16.getCategoryName(), var16);
         ID_CATEGORY_MAP.put(var16.getCategoryId(), var16);
      }
   }
}
