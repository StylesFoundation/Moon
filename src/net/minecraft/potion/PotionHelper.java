package net.minecraft.potion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import net.minecraft.src.Config;
import net.minecraft.util.IntegerCache;
import net.optifine.CustomColors;

public class PotionHelper {
   public static final String unusedString = null;
   private static final Map<Integer, String> potionRequirements = Maps.newHashMap();
   private static final Map<Integer, String> potionAmplifiers = Maps.newHashMap();
   private static final Map<Integer, Integer> DATAVALUE_COLORS = Maps.newHashMap();
   private static final String[] potionPrefixes = new String[]{
      "potion.prefix.mundane",
      "potion.prefix.uninteresting",
      "potion.prefix.bland",
      "potion.prefix.clear",
      "potion.prefix.milky",
      "potion.prefix.diffuse",
      "potion.prefix.artless",
      "potion.prefix.thin",
      "potion.prefix.awkward",
      "potion.prefix.flat",
      "potion.prefix.bulky",
      "potion.prefix.bungling",
      "potion.prefix.buttered",
      "potion.prefix.smooth",
      "potion.prefix.suave",
      "potion.prefix.debonair",
      "potion.prefix.thick",
      "potion.prefix.elegant",
      "potion.prefix.fancy",
      "potion.prefix.charming",
      "potion.prefix.dashing",
      "potion.prefix.refined",
      "potion.prefix.cordial",
      "potion.prefix.sparkling",
      "potion.prefix.potent",
      "potion.prefix.foul",
      "potion.prefix.odorless",
      "potion.prefix.rank",
      "potion.prefix.harsh",
      "potion.prefix.acrid",
      "potion.prefix.gross",
      "potion.prefix.stinky"
   };

   public static boolean checkFlag(int integer1, int integer2) {
      return (integer1 & 1 << integer2) != 0;
   }

   private static int isFlagSet(int integer1, int integer2) {
      return checkFlag(integer1, integer2) ? 1 : 0;
   }

   private static int isFlagUnset(int integer1, int integer2) {
      return checkFlag(integer1, integer2) ? 0 : 1;
   }

   public static int getPotionPrefixIndex(int integer) {
      return getPotionPrefixIndexFlags(integer, 5, 4, 3, 2, 1);
   }

   public static int calcPotionLiquidColor(Collection<PotionEffect> collection) {
      int var4 = 3694022;
      if (collection != null && !collection.isEmpty()) {
         float var5 = 0.0F;
         float var6 = 0.0F;
         float var7 = 0.0F;
         float var8 = 0.0F;

         for (PotionEffect var10 : collection) {
            if (var10.getIsShowParticles()) {
               int var11 = Potion.potionTypes[var10.getPotionID()].getLiquidColor();
               if (Config.isCustomColors()) {
                  var11 = CustomColors.getPotionColor(var10.getPotionID(), var11);
               }

               for (int var12 = 0; var12 <= var10.getAmplifier(); var12++) {
                  var5 += (var11 >> 16 & 0xFF) / 255.0F;
                  var6 += (var11 >> 8 & 0xFF) / 255.0F;
                  var7 += (var11 & 0xFF) / 255.0F;
                  var8++;
               }
            }
         }

         if (var8 == 0.0F) {
            return 0;
         }

         var5 = var5 / var8 * 255.0F;
         var6 = var6 / var8 * 255.0F;
         var7 = var7 / var8 * 255.0F;
         return (int)var5 << 16 | (int)var6 << 8 | (int)var7;
      } else {
         return Config.isCustomColors() ? CustomColors.getPotionColor(0, var4) : var4;
      }
   }

   public static boolean getAreAmbient(Collection<PotionEffect> collection) {
      for (PotionEffect var5 : collection) {
         if (!var5.getIsAmbient()) {
            return false;
         }
      }

      return true;
   }

   public static int getLiquidColor(int integer, boolean boolean2) {
      Integer var5 = IntegerCache.getInteger(integer);
      if (!boolean2) {
         if (DATAVALUE_COLORS.containsKey(var5)) {
            return DATAVALUE_COLORS.get(var5);
         }

         int var6 = calcPotionLiquidColor(getPotionEffects(var5, false));
         DATAVALUE_COLORS.put(var5, var6);
         return var6;
      } else {
         return calcPotionLiquidColor(getPotionEffects(var5, true));
      }
   }

   public static String getPotionPrefix(int integer) {
      int var1 = getPotionPrefixIndex(integer);
      return potionPrefixes[var1];
   }

   private static int getPotionEffect(boolean boolean1, boolean boolean2, boolean boolean3, int integer4, int integer5, int integer6, int integer7) {
      int var10 = 0;
      if (boolean1) {
         var10 = isFlagUnset(integer7, integer5);
      } else if (integer4 != -1) {
         if (integer4 == 0 && countSetFlags(integer7) == integer5) {
            var10 = 1;
         } else if (integer4 == 1 && countSetFlags(integer7) > integer5) {
            var10 = 1;
         } else if (integer4 == 2 && countSetFlags(integer7) < integer5) {
            var10 = 1;
         }
      } else {
         var10 = isFlagSet(integer7, integer5);
      }

      if (boolean2) {
         var10 *= integer6;
      }

      if (boolean3) {
         var10 *= -1;
      }

      return var10;
   }

   private static int countSetFlags(int integer) {
      int var4;
      for (var4 = 0; integer > 0; var4++) {
         integer &= integer - 1;
      }

      return var4;
   }

   private static int parsePotionEffects(String string, int integer2, int integer3, int integer4) {
      if (integer2 < string.length() && integer3 >= 0 && integer2 < integer3) {
         int var7 = string.indexOf(124, integer2);
         if (var7 >= 0 && var7 < integer3) {
            int var20 = parsePotionEffects(string, integer2, var7 - 1, integer4);
            return var20 > 0 ? var20 : Math.max(parsePotionEffects(string, var7 + 1, integer3, integer4), 0);
         }

         int var8 = string.indexOf(38, integer2);
         if (var8 >= 0 && var8 < integer3) {
            int var21 = parsePotionEffects(string, integer2, var8 - 1, integer4);
            if (var21 <= 0) {
               return 0;
            }

            int var22 = parsePotionEffects(string, var8 + 1, integer3, integer4);
            return var22 <= 0 ? 0 : Math.max(var21, var22);
         } else {
            boolean var9 = false;
            boolean var10 = false;
            boolean var11 = false;
            boolean var12 = false;
            boolean var13 = false;
            byte var14 = -1;
            int var15 = 0;
            int var16 = 0;
            int var17 = 0;

            for (int var18 = integer2; var18 < integer3; var18++) {
               char var19 = string.charAt(var18);
               if (var19 >= '0' && var19 <= '9') {
                  if (var9) {
                     var16 = var19 - '0';
                     var10 = true;
                  } else {
                     var15 *= 10;
                     var15 += var19 - '0';
                     var11 = true;
                  }
               } else if (var19 == '*') {
                  var9 = true;
               } else if (var19 == '!') {
                  if (var11) {
                     var17 += getPotionEffect(var12, var10, var13, var14, var15, var16, integer4);
                     var13 = false;
                     var9 = false;
                     var10 = false;
                     var11 = false;
                     var16 = 0;
                     var15 = 0;
                     var14 = -1;
                  }

                  var12 = true;
               } else if (var19 == '-') {
                  if (var11) {
                     var17 += getPotionEffect(var12, var10, var13, var14, var15, var16, integer4);
                     var12 = false;
                     var9 = false;
                     var10 = false;
                     var11 = false;
                     var16 = 0;
                     var15 = 0;
                     var14 = -1;
                  }

                  var13 = true;
               } else if (var19 != '=' && var19 != '<' && var19 != '>') {
                  if (var19 == '+' && var11) {
                     var17 += getPotionEffect(var12, var10, var13, var14, var15, var16, integer4);
                     var12 = false;
                     var13 = false;
                     var9 = false;
                     var10 = false;
                     var11 = false;
                     var16 = 0;
                     var15 = 0;
                     var14 = -1;
                  }
               } else {
                  if (var11) {
                     var17 += getPotionEffect(var12, var10, var13, var14, var15, var16, integer4);
                     var12 = false;
                     var13 = false;
                     var9 = false;
                     var10 = false;
                     var11 = false;
                     var16 = 0;
                     var15 = 0;
                     var14 = -1;
                  }

                  if (var19 == '=') {
                     var14 = 0;
                  } else if (var19 == '<') {
                     var14 = 2;
                  } else if (var19 == '>') {
                     var14 = 1;
                  }
               }
            }

            if (var11) {
               var17 += getPotionEffect(var12, var10, var13, var14, var15, var16, integer4);
            }

            return var17;
         }
      } else {
         return 0;
      }
   }

   public static List<PotionEffect> getPotionEffects(int integer, boolean boolean2) {
      ArrayList var5 = null;

      for (Potion var9 : Potion.potionTypes) {
         if (var9 != null && (!var9.isUsable() || boolean2)) {
            String var10 = potionRequirements.get(var9.getId());
            if (var10 != null) {
               int var11 = parsePotionEffects(var10, 0, var10.length(), integer);
               if (var11 > 0) {
                  int var12 = 0;
                  String var13 = potionAmplifiers.get(var9.getId());
                  if (var13 != null) {
                     var12 = parsePotionEffects(var13, 0, var13.length(), integer);
                     if (var12 < 0) {
                        var12 = 0;
                     }
                  }

                  if (var9.isInstant()) {
                     var11 = 1;
                  } else {
                     var11 = (int)Math.round((1200 * (var11 * 3 + (var11 - 1) * 2) >> var12) * var9.getEffectiveness());
                     if ((integer & 16384) != 0) {
                        var11 = (int)Math.round(var11 * 0.75 + 0.5);
                     }
                  }

                  if (var5 == null) {
                     var5 = Lists.newArrayList();
                  }

                  PotionEffect var14 = new PotionEffect(var9.getId(), var11, var12);
                  if ((integer & 16384) != 0) {
                     var14.setSplashPotion(true);
                  }

                  var5.add(var14);
               }
            }
         }
      }

      return var5;
   }

   private static int brewBitOperations(int integer1, int integer2, boolean boolean3, boolean boolean4, boolean boolean5) {
      if (boolean5) {
         if (!checkFlag(integer1, integer2)) {
            return 0;
         }
      } else if (boolean3) {
         integer1 &= ~(1 << integer2);
      } else if (boolean4) {
         if ((integer1 & 1 << integer2) == 0) {
            integer1 |= 1 << integer2;
         } else {
            integer1 &= ~(1 << integer2);
         }
      } else {
         integer1 |= 1 << integer2;
      }

      return integer1;
   }

   public static int applyIngredient(int integer, String string) {
      int var6 = string.length();
      boolean var7 = false;
      boolean var8 = false;
      boolean var9 = false;
      boolean var10 = false;
      int var11 = 0;

      for (int var12 = 0; var12 < var6; var12++) {
         char var13 = string.charAt(var12);
         if (var13 >= '0' && var13 <= '9') {
            var11 *= 10;
            var11 += var13 - '0';
            var7 = true;
         } else if (var13 == '!') {
            if (var7) {
               integer = brewBitOperations(integer, var11, var9, var8, var10);
               var10 = false;
               var9 = false;
               var7 = false;
               var11 = 0;
            }

            var8 = true;
         } else if (var13 == '-') {
            if (var7) {
               integer = brewBitOperations(integer, var11, var9, var8, var10);
               var10 = false;
               var8 = false;
               var7 = false;
               var11 = 0;
            }

            var9 = true;
         } else if (var13 == '+') {
            if (var7) {
               integer = brewBitOperations(integer, var11, var9, var8, var10);
               var10 = false;
               var8 = false;
               var9 = false;
               var7 = false;
               var11 = 0;
            }
         } else if (var13 == '&') {
            if (var7) {
               integer = brewBitOperations(integer, var11, var9, var8, var10);
               var8 = false;
               var9 = false;
               var7 = false;
               var11 = 0;
            }

            var10 = true;
         }
      }

      if (var7) {
         integer = brewBitOperations(integer, var11, var9, var8, var10);
      }

      return integer & 32767;
   }

   public static int getPotionPrefixIndexFlags(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
      return (checkFlag(integer1, integer2) ? 16 : 0)
         | (checkFlag(integer1, integer3) ? 8 : 0)
         | (checkFlag(integer1, integer4) ? 4 : 0)
         | (checkFlag(integer1, integer5) ? 2 : 0)
         | (checkFlag(integer1, integer6) ? 1 : 0);
   }

   static {
      potionRequirements.put(Potion.regeneration.getId(), "0 & !1 & !2 & !3 & 0+6");
      potionRequirements.put(Potion.moveSpeed.getId(), "!0 & 1 & !2 & !3 & 1+6");
      potionRequirements.put(Potion.fireResistance.getId(), "0 & 1 & !2 & !3 & 0+6");
      potionRequirements.put(Potion.heal.getId(), "0 & !1 & 2 & !3");
      potionRequirements.put(Potion.poison.getId(), "!0 & !1 & 2 & !3 & 2+6");
      potionRequirements.put(Potion.weakness.getId(), "!0 & !1 & !2 & 3 & 3+6");
      potionRequirements.put(Potion.harm.getId(), "!0 & !1 & 2 & 3");
      potionRequirements.put(Potion.moveSlowdown.getId(), "!0 & 1 & !2 & 3 & 3+6");
      potionRequirements.put(Potion.damageBoost.getId(), "0 & !1 & !2 & 3 & 3+6");
      potionRequirements.put(Potion.nightVision.getId(), "!0 & 1 & 2 & !3 & 2+6");
      potionRequirements.put(Potion.invisibility.getId(), "!0 & 1 & 2 & 3 & 2+6");
      potionRequirements.put(Potion.waterBreathing.getId(), "0 & !1 & 2 & 3 & 2+6");
      potionRequirements.put(Potion.jump.getId(), "0 & 1 & !2 & 3 & 3+6");
      potionAmplifiers.put(Potion.moveSpeed.getId(), "5");
      potionAmplifiers.put(Potion.digSpeed.getId(), "5");
      potionAmplifiers.put(Potion.damageBoost.getId(), "5");
      potionAmplifiers.put(Potion.regeneration.getId(), "5");
      potionAmplifiers.put(Potion.harm.getId(), "5");
      potionAmplifiers.put(Potion.heal.getId(), "5");
      potionAmplifiers.put(Potion.resistance.getId(), "5");
      potionAmplifiers.put(Potion.poison.getId(), "5");
      potionAmplifiers.put(Potion.jump.getId(), "5");
   }
}
