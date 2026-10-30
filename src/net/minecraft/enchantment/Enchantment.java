package net.minecraft.enchantment;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public abstract class Enchantment {
   private static final Enchantment[] enchantmentsList = new Enchantment[256];
   public static final Enchantment[] enchantmentsBookList;
   private static final Map<ResourceLocation, Enchantment> locationEnchantments = Maps.newHashMap();
   public static final Enchantment protection = new EnchantmentProtection(0, new ResourceLocation("protection"), 10, 0);
   public static final Enchantment fireProtection = new EnchantmentProtection(1, new ResourceLocation("fire_protection"), 5, 1);
   public static final Enchantment featherFalling = new EnchantmentProtection(2, new ResourceLocation("feather_falling"), 5, 2);
   public static final Enchantment blastProtection = new EnchantmentProtection(3, new ResourceLocation("blast_protection"), 2, 3);
   public static final Enchantment projectileProtection = new EnchantmentProtection(4, new ResourceLocation("projectile_protection"), 5, 4);
   public static final Enchantment respiration = new EnchantmentOxygen(5, new ResourceLocation("respiration"), 2);
   public static final Enchantment aquaAffinity = new EnchantmentWaterWorker(6, new ResourceLocation("aqua_affinity"), 2);
   public static final Enchantment thorns = new EnchantmentThorns(7, new ResourceLocation("thorns"), 1);
   public static final Enchantment depthStrider = new EnchantmentWaterWalker(8, new ResourceLocation("depth_strider"), 2);
   public static final Enchantment sharpness = new EnchantmentDamage(16, new ResourceLocation("sharpness"), 10, 0);
   public static final Enchantment smite = new EnchantmentDamage(17, new ResourceLocation("smite"), 5, 1);
   public static final Enchantment baneOfArthropods = new EnchantmentDamage(18, new ResourceLocation("bane_of_arthropods"), 5, 2);
   public static final Enchantment knockback = new EnchantmentKnockback(19, new ResourceLocation("knockback"), 5);
   public static final Enchantment fireAspect = new EnchantmentFireAspect(20, new ResourceLocation("fire_aspect"), 2);
   public static final Enchantment looting = new EnchantmentLootBonus(21, new ResourceLocation("looting"), 2, EnumEnchantmentType.WEAPON);
   public static final Enchantment efficiency = new EnchantmentDigging(32, new ResourceLocation("efficiency"), 10);
   public static final Enchantment silkTouch = new EnchantmentUntouching(33, new ResourceLocation("silk_touch"), 1);
   public static final Enchantment unbreaking = new EnchantmentDurability(34, new ResourceLocation("unbreaking"), 5);
   public static final Enchantment fortune = new EnchantmentLootBonus(35, new ResourceLocation("fortune"), 2, EnumEnchantmentType.DIGGER);
   public static final Enchantment power = new EnchantmentArrowDamage(48, new ResourceLocation("power"), 10);
   public static final Enchantment punch = new EnchantmentArrowKnockback(49, new ResourceLocation("punch"), 2);
   public static final Enchantment flame = new EnchantmentArrowFire(50, new ResourceLocation("flame"), 2);
   public static final Enchantment infinity = new EnchantmentArrowInfinite(51, new ResourceLocation("infinity"), 1);
   public static final Enchantment luckOfTheSea = new EnchantmentLootBonus(61, new ResourceLocation("luck_of_the_sea"), 2, EnumEnchantmentType.FISHING_ROD);
   public static final Enchantment lure = new EnchantmentFishingSpeed(62, new ResourceLocation("lure"), 2, EnumEnchantmentType.FISHING_ROD);
   public final int effectId;
   private final int weight;
   public EnumEnchantmentType type;
   protected String name;

   public static Enchantment getEnchantmentById(int integer) {
      return integer >= 0 && integer < enchantmentsList.length ? enchantmentsList[integer] : null;
   }

   protected Enchantment(int integer1, ResourceLocation resourceLocation, int integer3, EnumEnchantmentType enumEnchantmentType) {
      this.effectId = integer1;
      this.weight = integer3;
      this.type = enumEnchantmentType;
      if (enchantmentsList[integer1] != null) {
         throw new IllegalArgumentException("Duplicate enchantment id!");
      }

      enchantmentsList[integer1] = this;
      locationEnchantments.put(resourceLocation, this);
   }

   public static Enchantment getEnchantmentByLocation(String string) {
      return locationEnchantments.get(new ResourceLocation(string));
   }

   public static Set<ResourceLocation> func_181077_c() {
      return locationEnchantments.keySet();
   }

   public int getWeight() {
      return this.weight;
   }

   public int getMinLevel() {
      return 1;
   }

   public int getMaxLevel() {
      return 1;
   }

   public int getMinEnchantability(int integer) {
      return 1 + integer * 10;
   }

   public int getMaxEnchantability(int integer) {
      return this.getMinEnchantability(integer) + 5;
   }

   public int calcModifierDamage(int integer, DamageSource damageSource) {
      return 0;
   }

   public float calcDamageByCreature(int integer, EnumCreatureAttribute enumCreatureAttribute) {
      return 0.0F;
   }

   public boolean canApplyTogether(Enchantment enchantment) {
      return this != enchantment;
   }

   public Enchantment setName(String string) {
      this.name = string;
      return this;
   }

   public String Method244() {
      return "enchantment." + this.name;
   }

   public String Method532(int integer) {
      return StatCollector.translateToLocal(this.Method244()) + " " + StatCollector.translateToLocal("enchantment.level." + integer);
   }

   public boolean canApply(ItemStack itemStack) {
      return this.type.canEnchantItem(itemStack.getItem());
   }

   public void onEntityDamaged(EntityLivingBase entityLivingBase, Entity entity, int integer) {
   }

   public void onUserHurt(EntityLivingBase entityLivingBase, Entity entity, int integer) {
   }

   static {
      ArrayList<Enchantment> var13 = Lists.newArrayList();

      for (Enchantment var17 : enchantmentsList) {
         if (var17 != null) {
            var13.add(var17);
         }
      }

      enchantmentsBookList = var13.toArray(new Enchantment[0]);
   }
}
