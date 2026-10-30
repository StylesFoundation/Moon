package net.minecraft.world;

public enum EnumDifficulty {
   PEACEFUL(0, "options.difficulty.peaceful"),
   EASY(1, "options.difficulty.easy"),
   NORMAL(2, "options.difficulty.normal"),
   HARD(3, "options.difficulty.hard");

   private static final EnumDifficulty[] difficultyEnums = new EnumDifficulty[values().length];
   private final int difficultyId;
   private final String difficultyResourceKey;

   EnumDifficulty(int integer3, String string4) {
      this.difficultyId = integer3;
      this.difficultyResourceKey = string4;
   }

   public int getDifficultyId() {
      return this.difficultyId;
   }

   public static EnumDifficulty getDifficultyEnum(int integer) {
      return difficultyEnums[integer % difficultyEnums.length];
   }

   public String getDifficultyResourceKey() {
      return this.difficultyResourceKey;
   }

   static {
      for (EnumDifficulty var3 : values()) {
         difficultyEnums[var3.difficultyId] = var3;
      }
   }
}
