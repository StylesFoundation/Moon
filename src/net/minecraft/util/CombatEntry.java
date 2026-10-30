package net.minecraft.util;

import net.minecraft.entity.EntityLivingBase;

public class CombatEntry {
   private final DamageSource damageSrc;
   private final int field_94567_b;
   private final float damage;
   private final float health;
   private final String field_94566_e;
   private final float fallDistance;

   public CombatEntry(DamageSource damageSource, int integer, float float3, float float4, String string, float float6) {
      this.damageSrc = damageSource;
      this.field_94567_b = integer;
      this.damage = float4;
      this.health = float3;
      this.field_94566_e = string;
      this.fallDistance = float6;
   }

   public DamageSource getDamageSrc() {
      return this.damageSrc;
   }

   public float func_94563_c() {
      return this.damage;
   }

   public boolean isLivingDamageSrc() {
      return this.damageSrc.getEntity() instanceof EntityLivingBase;
   }

   public String func_94562_g() {
      return this.field_94566_e;
   }

   public IChatComponent getDamageSrcDisplayName() {
      return this.getDamageSrc().getEntity() == null ? null : this.getDamageSrc().getEntity().getDisplayName();
   }

   public float getDamageAmount() {
      return this.damageSrc == DamageSource.outOfWorld ? Float.MAX_VALUE : this.fallDistance;
   }
}
