package net.minecraft.enchantment;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.moon.Moon;
import com.moon.events.impl.player.DamageBlockEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.WeightedRandom;

public class EnchantmentHelper {
   private static final Random enchantmentRand = new Random();
   private static final EnchantmentHelper.ModifierDamage enchantmentModifierDamage = new EnchantmentHelper.ModifierDamage();
   private static final EnchantmentHelper.ModifierLiving enchantmentModifierLiving = new EnchantmentHelper.ModifierLiving();
   private static final EnchantmentHelper.HurtIterator ENCHANTMENT_ITERATOR_HURT = new EnchantmentHelper.HurtIterator();
   private static final EnchantmentHelper.DamageIterator ENCHANTMENT_ITERATOR_DAMAGE = new EnchantmentHelper.DamageIterator();

   public static int getEnchantmentLevel(int integer, ItemStack itemStack) {
      if (itemStack != null) {
         NBTTagList var5 = itemStack.getEnchantmentTagList();
         if (var5 != null) {
            for (int var6 = 0; var6 < var5.tagCount(); var6++) {
               short var7 = var5.getCompoundTagAt(var6).getShort("id");
               short var8 = var5.getCompoundTagAt(var6).getShort("lvl");
               if (var7 == integer) {
                  return var8;
               }
            }
         }
      }

      return 0;
   }

   public static Map<Integer, Integer> getEnchantments(ItemStack itemStack) {
      LinkedHashMap var4 = Maps.newLinkedHashMap();
      NBTTagList var5 = itemStack.getItem() == Items.enchanted_book ? Items.enchanted_book.getEnchantments(itemStack) : itemStack.getEnchantmentTagList();
      if (var5 != null) {
         for (int var6 = 0; var6 < var5.tagCount(); var6++) {
            short var7 = var5.getCompoundTagAt(var6).getShort("id");
            short var8 = var5.getCompoundTagAt(var6).getShort("lvl");
            var4.put(Integer.valueOf(var7), Integer.valueOf(var8));
         }
      }

      return var4;
   }

   public static void setEnchantments(Map<Integer, Integer> map, ItemStack itemStack) {
      NBTTagList var5 = new NBTTagList();

      for (int var7 : map.keySet()) {
         Enchantment var8 = Enchantment.getEnchantmentById(var7);
         if (var8 != null) {
            NBTTagCompound var9 = new NBTTagCompound();
            var9.setShort("id", (short)var7);
            var9.setShort("lvl", (short)((Integer)map.get(var7)).intValue());
            var5.appendTag(var9);
            if (itemStack.getItem() == Items.enchanted_book) {
               Items.enchanted_book.addEnchantment(itemStack, new EnchantmentData(var8, (Integer)map.get(var7)));
            }
         }
      }

      if (var5.tagCount() > 0) {
         if (itemStack.getItem() != Items.enchanted_book) {
            itemStack.setTagInfo("ench", var5);
         }
      } else if (itemStack.hasTagCompound()) {
         itemStack.getTagCompound().removeTag("ench");
      }
   }

   public static int getMaxEnchantmentLevel(int integer, ItemStack[] arr) {
      if (arr == null) {
         return 0;
      }

      int var5 = 0;

      for (ItemStack var9 : arr) {
         int var10 = getEnchantmentLevel(integer, var9);
         if (var10 > var5) {
            var5 = var10;
         }
      }

      return var5;
   }

   private static void applyEnchantmentModifier(EnchantmentHelper.IModifier iModifier, ItemStack itemStack) {
      if (itemStack != null) {
         NBTTagList var5 = itemStack.getEnchantmentTagList();
         if (var5 != null) {
            for (int var6 = 0; var6 < var5.tagCount(); var6++) {
               short var7 = var5.getCompoundTagAt(var6).getShort("id");
               short var8 = var5.getCompoundTagAt(var6).getShort("lvl");
               if (Enchantment.getEnchantmentById(var7) != null) {
                  iModifier.calculateModifier(Enchantment.getEnchantmentById(var7), var8);
               }
            }
         }
      }
   }

   private static void applyEnchantmentModifierArray(EnchantmentHelper.IModifier iModifier, ItemStack[] arr) {
      for (ItemStack var8 : arr) {
         applyEnchantmentModifier(iModifier, var8);
      }
   }

   public static int getEnchantmentModifierDamage(ItemStack[] arr, DamageSource damageSource) {
      enchantmentModifierDamage.damageModifier = 0;
      enchantmentModifierDamage.source = damageSource;
      applyEnchantmentModifierArray(enchantmentModifierDamage, arr);
      if (enchantmentModifierDamage.damageModifier > 25) {
         enchantmentModifierDamage.damageModifier = 25;
      } else if (enchantmentModifierDamage.damageModifier < 0) {
         enchantmentModifierDamage.damageModifier = 0;
      }

      return (enchantmentModifierDamage.damageModifier + 1 >> 1) + enchantmentRand.nextInt((enchantmentModifierDamage.damageModifier >> 1) + 1);
   }

   public static float getModifierForCreature(ItemStack itemStack, EnumCreatureAttribute enumCreatureAttribute) {
      enchantmentModifierLiving.livingModifier = 0.0F;
      enchantmentModifierLiving.entityLiving = enumCreatureAttribute;
      applyEnchantmentModifier(enchantmentModifierLiving, itemStack);
      return enchantmentModifierLiving.livingModifier;
   }

   public static void applyThornEnchantments(EntityLivingBase entityLivingBase, Entity entity) {
      ENCHANTMENT_ITERATOR_HURT.attacker = entity;
      ENCHANTMENT_ITERATOR_HURT.user = entityLivingBase;
      if (entityLivingBase != null) {
         applyEnchantmentModifierArray(ENCHANTMENT_ITERATOR_HURT, entityLivingBase.getInventory());
      }

      if (entity instanceof EntityPlayer) {
         applyEnchantmentModifier(ENCHANTMENT_ITERATOR_HURT, entityLivingBase.getHeldItem());
      }
   }

   public static void applyArthropodEnchantments(EntityLivingBase entityLivingBase, Entity entity) {
      ENCHANTMENT_ITERATOR_DAMAGE.user = entityLivingBase;
      ENCHANTMENT_ITERATOR_DAMAGE.target = entity;
      if (entityLivingBase != null) {
         applyEnchantmentModifierArray(ENCHANTMENT_ITERATOR_DAMAGE, entityLivingBase.getInventory());
      }

      if (entityLivingBase instanceof EntityPlayer) {
         applyEnchantmentModifier(ENCHANTMENT_ITERATOR_DAMAGE, entityLivingBase.getHeldItem());
      }
   }

   public static int getKnockbackModifier(EntityLivingBase entityLivingBase) {
      return getEnchantmentLevel(Enchantment.knockback.effectId, entityLivingBase.getHeldItem());
   }

   public static int getFireAspectModifier(EntityLivingBase entityLivingBase) {
      return getEnchantmentLevel(Enchantment.fireAspect.effectId, entityLivingBase.getHeldItem());
   }

   public static int getRespiration(Entity entity) {
      return getMaxEnchantmentLevel(Enchantment.respiration.effectId, entity.getInventory());
   }

   public static int getDepthStriderModifier(Entity entity) {
      return getMaxEnchantmentLevel(Enchantment.depthStrider.effectId, entity.getInventory());
   }

   public static int Method396(EntityLivingBase entityLivingBase) {
      if (entityLivingBase instanceof EntityPlayerSP var4) {
         DamageBlockEvent var5 = new DamageBlockEvent(var4.inventory.currentItem);
         Moon.getInstance().getEventBus().publish(var5);
         return getEnchantmentLevel(Enchantment.efficiency.effectId, var4.inventory.getStackInSlot(var5.getSlot()));
      } else {
         return getEnchantmentLevel(Enchantment.efficiency.effectId, entityLivingBase.getHeldItem());
      }
   }

   public static int Method397(EntityLivingBase entityLivingBase, int integer) {
      return entityLivingBase instanceof EntityPlayerSP var5
         ? getEnchantmentLevel(Enchantment.efficiency.effectId, var5.inventory.getStackInSlot(integer))
         : getEnchantmentLevel(Enchantment.efficiency.effectId, entityLivingBase.getHeldItem());
   }

   public static boolean getSilkTouchModifier(EntityLivingBase entityLivingBase) {
      return getEnchantmentLevel(Enchantment.silkTouch.effectId, entityLivingBase.getHeldItem()) > 0;
   }

   public static int getFortuneModifier(EntityLivingBase entityLivingBase) {
      return getEnchantmentLevel(Enchantment.fortune.effectId, entityLivingBase.getHeldItem());
   }

   public static int getLuckOfSeaModifier(EntityLivingBase entityLivingBase) {
      return getEnchantmentLevel(Enchantment.luckOfTheSea.effectId, entityLivingBase.getHeldItem());
   }

   public static int getLureModifier(EntityLivingBase entityLivingBase) {
      return getEnchantmentLevel(Enchantment.lure.effectId, entityLivingBase.getHeldItem());
   }

   public static int getLootingModifier(EntityLivingBase entityLivingBase) {
      return getEnchantmentLevel(Enchantment.looting.effectId, entityLivingBase.getHeldItem());
   }

   public static boolean getAquaAffinityModifier(EntityLivingBase entityLivingBase) {
      return getMaxEnchantmentLevel(Enchantment.aquaAffinity.effectId, entityLivingBase.getInventory()) > 0;
   }

   public static ItemStack getEnchantedItem(Enchantment enchantment, EntityLivingBase entityLivingBase) {
      for (ItemStack var8 : entityLivingBase.getInventory()) {
         if (var8 != null && getEnchantmentLevel(enchantment.effectId, var8) > 0) {
            return var8;
         }
      }

      return null;
   }

   public static int calcItemStackEnchantability(Random random, int integer2, int integer3, ItemStack itemStack) {
      Item var7 = itemStack.getItem();
      int var8 = var7.getItemEnchantability();
      if (var8 <= 0) {
         return 0;
      }

      if (integer3 > 15) {
         integer3 = 15;
      }

      int var9 = random.nextInt(8) + 1 + (integer3 >> 1) + random.nextInt(integer3 + 1);
      return integer2 == 0 ? Math.max(var9 / 3, 1) : (integer2 == 1 ? var9 * 2 / 3 + 1 : Math.max(var9, integer3 * 2));
   }

   public static ItemStack addRandomEnchantment(Random random, ItemStack itemStack, int integer) {
      List<EnchantmentData> var6 = buildEnchantmentList(random, itemStack, integer);
      boolean var7 = itemStack.getItem() == Items.book;
      if (var7) {
         itemStack.setItem(Items.enchanted_book);
      }

      if (var6 != null) {
         for (EnchantmentData var9 : var6) {
            if (var7) {
               Items.enchanted_book.addEnchantment(itemStack, var9);
            } else {
               itemStack.addEnchantment(var9.enchantmentobj, var9.enchantmentLevel);
            }
         }
      }

      return itemStack;
   }

   public static List<EnchantmentData> buildEnchantmentList(Random random, ItemStack itemStack, int integer) {
      Item var6 = itemStack.getItem();
      int var7 = var6.getItemEnchantability();
      if (var7 <= 0) {
         return null;
      }

      var7 /= 2;
      int var8 = 1 + random.nextInt((var7 >> 1) + 1) + random.nextInt((var7 >> 1) + 1) + integer;
      float var9 = (random.nextFloat() + random.nextFloat() - 1.0F) * 0.15F;
      int var10 = (int)(var8 * (1.0F + var9) + 0.5F);
      if (var10 < 1) {
         var10 = 1;
      }

      List<EnchantmentData> var11 = null;
      Map<Integer, EnchantmentData> var12 = mapEnchantmentData(var10, itemStack);
      if (var12 != null && !var12.isEmpty()) {
         EnchantmentData var13 = (EnchantmentData)WeightedRandom.getRandomItem(random, var12.values());
         if (var13 != null) {
            var11 = Lists.newArrayList();
            var11.add(var13);

            for (int var14 = var10; random.nextInt(50) <= var14; var14 >>= 1) {
               Iterator<Integer> var15 = var12.keySet().iterator();

               while (var15.hasNext()) {
                  Integer var16 = (Integer)var15.next();
                  boolean var17 = true;

                  for (EnchantmentData var19 : var11) {
                     if (!var19.enchantmentobj.canApplyTogether(Enchantment.getEnchantmentById(var16))) {
                        var17 = false;
                        break;
                     }
                  }

                  if (!var17) {
                     var15.remove();
                  }
               }

               if (!var12.isEmpty()) {
                  EnchantmentData var21 = (EnchantmentData)WeightedRandom.getRandomItem(random, var12.values());
                  var11.add(var21);
               }
            }
         }
      }

      return var11;
   }

   public static Map<Integer, EnchantmentData> mapEnchantmentData(int integer, ItemStack itemStack) {
      Item var5 = itemStack.getItem();
      Map<Integer, EnchantmentData> var6 = null;
      boolean var7 = itemStack.getItem() == Items.book;

      for (Enchantment var11 : Enchantment.enchantmentsBookList) {
         if (var11 != null && (var11.type.canEnchantItem(var5) || var7)) {
            for (int var12 = var11.getMinLevel(); var12 <= var11.getMaxLevel(); var12++) {
               if (integer >= var11.getMinEnchantability(var12) && integer <= var11.getMaxEnchantability(var12)) {
                  if (var6 == null) {
                     var6 = Maps.newHashMap();
                  }

                  var6.put(var11.effectId, new EnchantmentData(var11, var12));
               }
            }
         }
      }

      return var6;
   }

   static final class DamageIterator implements EnchantmentHelper.IModifier {
      public EntityLivingBase user;
      public Entity target;

      private DamageIterator() {
      }

      @Override
      public void calculateModifier(Enchantment enchantment, int integer) {
         enchantment.onEntityDamaged(this.user, this.target, integer);
      }
   }

   static final class HurtIterator implements EnchantmentHelper.IModifier {
      public EntityLivingBase user;
      public Entity attacker;

      private HurtIterator() {
      }

      @Override
      public void calculateModifier(Enchantment enchantment, int integer) {
         enchantment.onUserHurt(this.user, this.attacker, integer);
      }
   }

   interface IModifier {
      void calculateModifier(Enchantment enchantment, int integer);
   }

   static final class ModifierDamage implements EnchantmentHelper.IModifier {
      public int damageModifier;
      public DamageSource source;

      private ModifierDamage() {
      }

      @Override
      public void calculateModifier(Enchantment enchantment, int integer) {
         this.damageModifier = this.damageModifier + enchantment.calcModifierDamage(integer, this.source);
      }
   }

   static final class ModifierLiving implements EnchantmentHelper.IModifier {
      public float livingModifier;
      public EnumCreatureAttribute entityLiving;

      private ModifierLiving() {
      }

      @Override
      public void calculateModifier(Enchantment enchantment, int integer) {
         this.livingModifier = this.livingModifier + enchantment.calcDamageByCreature(integer, this.entityLiving);
      }
   }
}
