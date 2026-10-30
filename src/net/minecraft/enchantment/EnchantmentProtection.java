package net.minecraft.enchantment;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class EnchantmentProtection extends Enchantment {
   private static final String[] protectionName = new String[]{"all", "fire", "fall", "explosion", "projectile"};
   private static final int[] baseEnchantability = new int[]{1, 10, 5, 5, 3};
   private static final int[] levelEnchantability = new int[]{11, 8, 6, 8, 6};
   private static final int[] thresholdEnchantability = new int[]{20, 12, 10, 12, 15};
   public final int protectionType;

   public EnchantmentProtection(int integer1, ResourceLocation resourceLocation, int integer3, int integer4) {
      super(integer1, resourceLocation, integer3, EnumEnchantmentType.ARMOR);
      this.protectionType = integer4;
      if (integer4 == 2) {
         this.type = EnumEnchantmentType.ARMOR_FEET;
      }
   }

   @Override
   public int getMinEnchantability(int integer) {
      return baseEnchantability[this.protectionType] + (integer - 1) * levelEnchantability[this.protectionType];
   }

   @Override
   public int getMaxEnchantability(int integer) {
      return this.getMinEnchantability(integer) + thresholdEnchantability[this.protectionType];
   }

   @Override
   public int getMaxLevel() {
      return 4;
   }

   @Override
   public int calcModifierDamage(int integer, DamageSource damageSource) {
      if (damageSource.canHarmInCreative()) {
         return 0;
      }

      float var6 = (6 + integer * integer) / 3.0F;
      return this.protectionType == 0
         ? MathHelper.floor_float(var6 * 0.75F)
         : (
            this.protectionType == 1 && damageSource.isFireDamage()
               ? MathHelper.floor_float(var6 * 1.25F)
               : (
                  this.protectionType == 2 && damageSource == DamageSource.fall
                     ? MathHelper.floor_float(var6 * 2.5F)
                     : (
                        this.protectionType == 3 && damageSource.isExplosion()
                           ? MathHelper.floor_float(var6 * 1.5F)
                           : (this.protectionType == 4 && damageSource.isProjectile() ? MathHelper.floor_float(var6 * 1.5F) : 0)
                     )
               )
         );
   }

   @Override
   public String Method244() {
      return "enchantment.protect." + protectionName[this.protectionType];
   }

   @Override
   public boolean canApplyTogether(Enchantment enchantment) {
      return !(enchantment instanceof EnchantmentProtection var5)
         ? super.canApplyTogether(enchantment)
         : var5.protectionType != this.protectionType && (this.protectionType == 2 || var5.protectionType == 2);
   }

   public static int getFireTimeForEntity(Entity entity, int integer) {
      int var5 = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.fireProtection.effectId, entity.getInventory());
      if (var5 > 0) {
         integer -= MathHelper.floor_float((float)integer * var5 * 0.15F);
      }

      return integer;
   }

   public static double func_92092_a(Entity entity, double double2) {
      int var5 = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.blastProtection.effectId, entity.getInventory());
      if (var5 > 0) {
         double2 -= MathHelper.floor_double(double2 * (var5 * 0.15F));
      }

      return double2;
   }
}
