package net.minecraft.potion;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;

public class Potion {
   public static final Potion[] potionTypes = new Potion[32];
   private static final Map<ResourceLocation, Potion> field_180150_I = Maps.newHashMap();
   public static final Potion field_180151_b = null;
   public static final Potion moveSpeed = new Potion(1, new ResourceLocation("speed"), false, 8171462)
      .setPotionName("potion.moveSpeed")
      .setIconIndex(0, 0)
      .registerPotionAttributeModifier(SharedMonsterAttributes.movementSpeed, "91AEAA56-376B-4498-935B-2F7F68070635", 0.2F, 2);
   public static final Potion moveSlowdown = new Potion(2, new ResourceLocation("slowness"), true, 5926017)
      .setPotionName("potion.moveSlowdown")
      .setIconIndex(1, 0)
      .registerPotionAttributeModifier(SharedMonsterAttributes.movementSpeed, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15F, 2);
   public static final Potion digSpeed = new Potion(3, new ResourceLocation("haste"), false, 14270531)
      .setPotionName("potion.digSpeed")
      .setIconIndex(2, 0)
      .setEffectiveness(1.5);
   public static final Potion digSlowdown = new Potion(4, new ResourceLocation("mining_fatigue"), true, 4866583)
      .setPotionName("potion.digSlowDown")
      .setIconIndex(3, 0);
   public static final Potion damageBoost = new PotionAttackDamage(5, new ResourceLocation("strength"), false, 9643043)
      .setPotionName("potion.damageBoost")
      .setIconIndex(4, 0)
      .registerPotionAttributeModifier(SharedMonsterAttributes.attackDamage, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 2.5, 2);
   public static final Potion heal = new PotionHealth(6, new ResourceLocation("instant_health"), false, 16262179).setPotionName("potion.heal");
   public static final Potion harm = new PotionHealth(7, new ResourceLocation("instant_damage"), true, 4393481).setPotionName("potion.harm");
   public static final Potion jump = new Potion(8, new ResourceLocation("jump_boost"), false, 2293580).setPotionName("potion.jump").setIconIndex(2, 1);
   public static final Potion confusion = new Potion(9, new ResourceLocation("nausea"), true, 5578058)
      .setPotionName("potion.confusion")
      .setIconIndex(3, 1)
      .setEffectiveness(0.25);
   public static final Potion regeneration = new Potion(10, new ResourceLocation("regeneration"), false, 13458603)
      .setPotionName("potion.regeneration")
      .setIconIndex(7, 0)
      .setEffectiveness(0.25);
   public static final Potion resistance = new Potion(11, new ResourceLocation("resistance"), false, 10044730)
      .setPotionName("potion.resistance")
      .setIconIndex(6, 1);
   public static final Potion fireResistance = new Potion(12, new ResourceLocation("fire_resistance"), false, 14981690)
      .setPotionName("potion.fireResistance")
      .setIconIndex(7, 1);
   public static final Potion waterBreathing = new Potion(13, new ResourceLocation("water_breathing"), false, 3035801)
      .setPotionName("potion.waterBreathing")
      .setIconIndex(0, 2);
   public static final Potion invisibility = new Potion(14, new ResourceLocation("invisibility"), false, 8356754)
      .setPotionName("potion.invisibility")
      .setIconIndex(0, 1);
   public static final Potion blindness = new Potion(15, new ResourceLocation("blindness"), true, 2039587)
      .setPotionName("potion.blindness")
      .setIconIndex(5, 1)
      .setEffectiveness(0.25);
   public static final Potion nightVision = new Potion(16, new ResourceLocation("night_vision"), false, 2039713)
      .setPotionName("potion.nightVision")
      .setIconIndex(4, 1);
   public static final Potion hunger = new Potion(17, new ResourceLocation("hunger"), true, 5797459).setPotionName("potion.hunger").setIconIndex(1, 1);
   public static final Potion weakness = new PotionAttackDamage(18, new ResourceLocation("weakness"), true, 4738376)
      .setPotionName("potion.weakness")
      .setIconIndex(5, 0)
      .registerPotionAttributeModifier(SharedMonsterAttributes.attackDamage, "22653B89-116E-49DC-9B6B-9971489B5BE5", 2.0, 0);
   public static final Potion poison = new Potion(19, new ResourceLocation("poison"), true, 5149489)
      .setPotionName("potion.poison")
      .setIconIndex(6, 0)
      .setEffectiveness(0.25);
   public static final Potion wither = new Potion(20, new ResourceLocation("wither"), true, 3484199)
      .setPotionName("potion.wither")
      .setIconIndex(1, 2)
      .setEffectiveness(0.25);
   public static final Potion healthBoost = new PotionHealthBoost(21, new ResourceLocation("health_boost"), false, 16284963)
      .setPotionName("potion.healthBoost")
      .setIconIndex(2, 2)
      .registerPotionAttributeModifier(SharedMonsterAttributes.maxHealth, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0, 0);
   public static final Potion absorption = new PotionAbsorption(22, new ResourceLocation("absorption"), false, 2445989)
      .setPotionName("potion.absorption")
      .setIconIndex(2, 2);
   public static final Potion saturation = new PotionHealth(23, new ResourceLocation("saturation"), false, 16262179).setPotionName("potion.saturation");
   public static final Potion field_180153_z = null;
   public static final Potion field_180147_A = null;
   public static final Potion field_180148_B = null;
   public static final Potion field_180149_C = null;
   public static final Potion field_180143_D = null;
   public static final Potion field_180144_E = null;
   public static final Potion field_180145_F = null;
   public static final Potion field_180146_G = null;
   public final int id;
   private final Map<IAttribute, AttributeModifier> attributeModifierMap = Maps.newHashMap();
   private final boolean isBadEffect;
   private final int liquidColor;
   private String name = "";
   private int statusIconIndex = -1;
   private double effectiveness;
   private boolean usable;

   protected Potion(int integer1, ResourceLocation resourceLocation, boolean boolean3, int integer4) {
      this.id = integer1;
      potionTypes[integer1] = this;
      field_180150_I.put(resourceLocation, this);
      this.isBadEffect = boolean3;
      if (boolean3) {
         this.effectiveness = 0.5;
      } else {
         this.effectiveness = 1.0;
      }

      this.liquidColor = integer4;
   }

   public static Potion getPotionFromResourceLocation(String string) {
      return field_180150_I.get(new ResourceLocation(string));
   }

   public static Set<ResourceLocation> getPotionLocations() {
      return field_180150_I.keySet();
   }

   protected Potion setIconIndex(int integer1, int integer2) {
      this.statusIconIndex = integer1 + integer2 * 8;
      return this;
   }

   public int getId() {
      return this.id;
   }

   public void performEffect(EntityLivingBase entityLivingBase, int integer) {
      if (this.id == regeneration.id) {
         if (entityLivingBase.getHealth() < entityLivingBase.getMaxHealth()) {
            entityLivingBase.heal(1.0F);
         }
      } else if (this.id == poison.id) {
         if (entityLivingBase.getHealth() > 1.0F) {
            entityLivingBase.attackEntityFrom(DamageSource.magic, 1.0F);
         }
      } else if (this.id == wither.id) {
         entityLivingBase.attackEntityFrom(DamageSource.wither, 1.0F);
      } else if (this.id == hunger.id && entityLivingBase instanceof EntityPlayer) {
         ((EntityPlayer)entityLivingBase).addExhaustion(0.025F * (integer + 1));
      } else if (this.id == saturation.id && entityLivingBase instanceof EntityPlayer) {
         if (!entityLivingBase.worldObj.isRemote) {
            ((EntityPlayer)entityLivingBase).getFoodStats().addStats(integer + 1, 1.0F);
         }
      } else if ((this.id != heal.id || entityLivingBase.isEntityUndead()) && (this.id != harm.id || !entityLivingBase.isEntityUndead())) {
         if (this.id == harm.id && !entityLivingBase.isEntityUndead() || this.id == heal.id && entityLivingBase.isEntityUndead()) {
            entityLivingBase.attackEntityFrom(DamageSource.magic, 6 << integer);
         }
      } else {
         entityLivingBase.heal(Math.max(4 << integer, 0));
      }
   }

   public void affectEntity(Entity entity1, Entity entity2, EntityLivingBase entityLivingBase, int integer, double double5) {
      if ((this.id != heal.id || entityLivingBase.isEntityUndead()) && (this.id != harm.id || !entityLivingBase.isEntityUndead())) {
         if (this.id == harm.id && !entityLivingBase.isEntityUndead() || this.id == heal.id && entityLivingBase.isEntityUndead()) {
            int var11 = (int)(double5 * (6 << integer) + 0.5);
            if (entity1 == null) {
               entityLivingBase.attackEntityFrom(DamageSource.magic, var11);
            } else {
               entityLivingBase.attackEntityFrom(DamageSource.causeIndirectMagicDamage(entity1, entity2), var11);
            }
         }
      } else {
         int var10 = (int)(double5 * (4 << integer) + 0.5);
         entityLivingBase.heal(var10);
      }
   }

   public boolean isInstant() {
      return false;
   }

   public boolean isReady(int integer1, int integer2) {
      if (this.id == regeneration.id) {
         int var8 = 50 >> integer2;
         return var8 == 0 || integer1 % var8 == 0;
      }

      if (this.id == poison.id) {
         int var7 = 25 >> integer2;
         return var7 == 0 || integer1 % var7 == 0;
      }

      if (this.id != wither.id) {
         return this.id == hunger.id;
      }

      int var6 = 40 >> integer2;
      return var6 == 0 || integer1 % var6 == 0;
   }

   public Potion setPotionName(String string) {
      this.name = string;
      return this;
   }

   public String getName() {
      return this.name;
   }

   public boolean hasStatusIcon() {
      return this.statusIconIndex >= 0;
   }

   public int getStatusIconIndex() {
      return this.statusIconIndex;
   }

   public boolean isBadEffect() {
      return this.isBadEffect;
   }

   public static String getDurationString(PotionEffect potionEffect) {
      return potionEffect.getIsPotionDurationMax() ? "**:**" : StringUtils.Method604(potionEffect.getDuration());
   }

   protected Potion setEffectiveness(double double1) {
      this.effectiveness = double1;
      return this;
   }

   public double getEffectiveness() {
      return this.effectiveness;
   }

   public boolean isUsable() {
      return this.usable;
   }

   public int getLiquidColor() {
      return this.liquidColor;
   }

   public Potion registerPotionAttributeModifier(IAttribute iAttribute, String string, double double3, int integer) {
      AttributeModifier var6 = new AttributeModifier(UUID.fromString(string), this.getName(), double3, integer);
      this.attributeModifierMap.put(iAttribute, var6);
      return this;
   }

   public Map<IAttribute, AttributeModifier> getAttributeModifierMap() {
      return this.attributeModifierMap;
   }

   public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBase, BaseAttributeMap baseAttributeMap, int integer) {
      for (Entry var8 : this.attributeModifierMap.entrySet()) {
         IAttributeInstance var9 = baseAttributeMap.getAttributeInstance((IAttribute)var8.getKey());
         if (var9 != null) {
            var9.removeModifier((AttributeModifier)var8.getValue());
         }
      }
   }

   public void applyAttributesModifiersToEntity(EntityLivingBase entityLivingBase, BaseAttributeMap baseAttributeMap, int integer) {
      for (Entry var8 : this.attributeModifierMap.entrySet()) {
         IAttributeInstance var9 = baseAttributeMap.getAttributeInstance((IAttribute)var8.getKey());
         if (var9 != null) {
            AttributeModifier var10 = (AttributeModifier)var8.getValue();
            var9.removeModifier(var10);
            var9.applyModifier(
               new AttributeModifier(var10.getID(), this.getName() + " " + integer, this.getAttributeModifierAmount(integer, var10), var10.getOperation())
            );
         }
      }
   }

   public double getAttributeModifierAmount(int integer, AttributeModifier attributeModifier) {
      return attributeModifier.getAmount() * (integer + 1);
   }
}
