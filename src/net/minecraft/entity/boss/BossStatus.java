package net.minecraft.entity.boss;

public final class BossStatus {
   public static float healthScale;
   public static int statusBarTime;
   public static String bossName;
   public static boolean hasColorModifier;

   public static void setBossStatus(IBossDisplayData iBossDisplayData, boolean boolean2) {
      healthScale = iBossDisplayData.getHealth() / iBossDisplayData.getMaxHealth();
      statusBarTime = 100;
      bossName = iBossDisplayData.getDisplayName().getFormattedText();
      hasColorModifier = boolean2;
   }
}
