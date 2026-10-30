package net.minecraft.enchantment;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class EnchantmentThorns extends Enchantment {
   public EnchantmentThorns(int integer1, ResourceLocation resourceLocation, int integer3) {
      super(integer1, resourceLocation, integer3, EnumEnchantmentType.ARMOR_TORSO);
      this.setName("thorns");
   }

   @Override
   public int getMinEnchantability(int integer) {
      return 10 + 20 * (integer - 1);
   }

   @Override
   public int getMaxEnchantability(int integer) {
      return super.getMinEnchantability(integer) + 50;
   }

   @Override
   public int getMaxLevel() {
      return 3;
   }

   @Override
   public boolean canApply(ItemStack itemStack) {
      return itemStack.getItem() instanceof ItemArmor || super.canApply(itemStack);
   }

   @Override
   public void onUserHurt(EntityLivingBase entityLivingBase, Entity entity, int integer) {
      Random var7 = entityLivingBase.getRNG();
      ItemStack var8 = EnchantmentHelper.getEnchantedItem(Enchantment.thorns, entityLivingBase);
      if (func_92094_a(integer, var7)) {
         if (entity != null) {
            entity.attackEntityFrom(DamageSource.causeThornsDamage(entityLivingBase), func_92095_b(integer, var7));
            entity.playSound("damage.thorns", 0.5F, 1.0F);
         }

         if (var8 != null) {
            var8.damageItem(3, entityLivingBase);
         }
      } else if (var8 != null) {
         var8.damageItem(1, entityLivingBase);
      }
   }

   public static boolean func_92094_a(int integer, Random random) {
      return integer > 0 && random.nextFloat() < 0.15F * integer;
   }

   public static int func_92095_b(int integer, Random random) {
      return integer > 10 ? integer - 10 : 1 + random.nextInt(4);
   }
}
