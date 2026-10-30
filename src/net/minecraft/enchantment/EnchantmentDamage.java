package net.minecraft.enchantment;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class EnchantmentDamage extends Enchantment {
   private static final String[] protectionName = new String[]{"all", "undead", "arthropods"};
   private static final int[] baseEnchantability = new int[]{1, 5, 5};
   private static final int[] levelEnchantability = new int[]{11, 8, 8};
   private static final int[] thresholdEnchantability = new int[]{20, 20, 20};
   public final int damageType;

   public EnchantmentDamage(int integer1, ResourceLocation resourceLocation, int integer3, int integer4) {
      super(integer1, resourceLocation, integer3, EnumEnchantmentType.WEAPON);
      this.damageType = integer4;
   }

   @Override
   public int getMinEnchantability(int integer) {
      return baseEnchantability[this.damageType] + (integer - 1) * levelEnchantability[this.damageType];
   }

   @Override
   public int getMaxEnchantability(int integer) {
      return this.getMinEnchantability(integer) + thresholdEnchantability[this.damageType];
   }

   @Override
   public int getMaxLevel() {
      return 5;
   }

   @Override
   public float calcDamageByCreature(int integer, EnumCreatureAttribute enumCreatureAttribute) {
      return this.damageType == 0
         ? integer * 1.25F
         : (
            this.damageType == 1 && enumCreatureAttribute == EnumCreatureAttribute.UNDEAD
               ? integer * 2.5F
               : (this.damageType == 2 && enumCreatureAttribute == EnumCreatureAttribute.ARTHROPOD ? integer * 2.5F : 0.0F)
         );
   }

   @Override
   public String Method244() {
      return "enchantment.damage." + protectionName[this.damageType];
   }

   @Override
   public boolean canApplyTogether(Enchantment enchantment) {
      return !(enchantment instanceof EnchantmentDamage);
   }

   @Override
   public boolean canApply(ItemStack itemStack) {
      return itemStack.getItem() instanceof ItemAxe || super.canApply(itemStack);
   }

   @Override
   public void onEntityDamaged(EntityLivingBase entityLivingBase, Entity entity, int integer) {
      if (entity instanceof EntityLivingBase var7 && this.damageType == 2 && var7.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD) {
         int var8 = 20 + entityLivingBase.getRNG().nextInt(10 * integer);
         var7.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, var8, 3));
      }
   }
}
