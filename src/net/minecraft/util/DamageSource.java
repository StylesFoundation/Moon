package net.minecraft.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.world.Explosion;

public class DamageSource {
   public static DamageSource inFire = new DamageSource("inFire").setFireDamage();
   public static DamageSource lightningBolt = new DamageSource("lightningBolt");
   public static DamageSource onFire = new DamageSource("onFire").setDamageBypassesArmor().setFireDamage();
   public static DamageSource lava = new DamageSource("lava").setFireDamage();
   public static DamageSource inWall = new DamageSource("inWall").setDamageBypassesArmor();
   public static DamageSource drown = new DamageSource("drown").setDamageBypassesArmor();
   public static DamageSource starve = new DamageSource("starve").setDamageBypassesArmor().setDamageIsAbsolute();
   public static DamageSource cactus = new DamageSource("cactus");
   public static DamageSource fall = new DamageSource("fall").setDamageBypassesArmor();
   public static DamageSource outOfWorld = new DamageSource("outOfWorld").setDamageBypassesArmor().setDamageAllowedInCreativeMode();
   public static DamageSource generic = new DamageSource("generic").setDamageBypassesArmor();
   public static DamageSource magic = new DamageSource("magic").setDamageBypassesArmor().setMagicDamage();
   public static DamageSource wither = new DamageSource("wither").setDamageBypassesArmor();
   public static DamageSource anvil = new DamageSource("anvil");
   public static DamageSource fallingBlock = new DamageSource("fallingBlock");
   private boolean isUnblockable;
   private boolean isDamageAllowedInCreativeMode;
   private boolean damageIsAbsolute;
   private float hungerDamage = 0.3F;
   private boolean fireDamage;
   private boolean projectile;
   private boolean difficultyScaled;
   private boolean magicDamage;
   private boolean explosion;
   public String damageType;

   public static DamageSource causeMobDamage(EntityLivingBase entityLivingBase) {
      return new EntityDamageSource("mob", entityLivingBase);
   }

   public static DamageSource causePlayerDamage(EntityPlayer entityPlayer) {
      return new EntityDamageSource("player", entityPlayer);
   }

   public static DamageSource causeArrowDamage(EntityArrow entityArrow, Entity entity) {
      return new EntityDamageSourceIndirect("arrow", entityArrow, entity).setProjectile();
   }

   public static DamageSource causeFireballDamage(EntityFireball entityFireball, Entity entity) {
      return entity == null
         ? new EntityDamageSourceIndirect("onFire", entityFireball, entityFireball).setFireDamage().setProjectile()
         : new EntityDamageSourceIndirect("fireball", entityFireball, entity).setFireDamage().setProjectile();
   }

   public static DamageSource causeThrownDamage(Entity entity1, Entity entity2) {
      return new EntityDamageSourceIndirect("thrown", entity1, entity2).setProjectile();
   }

   public static DamageSource causeIndirectMagicDamage(Entity entity1, Entity entity2) {
      return new EntityDamageSourceIndirect("indirectMagic", entity1, entity2).setDamageBypassesArmor().setMagicDamage();
   }

   public static DamageSource causeThornsDamage(Entity entity) {
      return new EntityDamageSource("thorns", entity).setIsThornsDamage().setMagicDamage();
   }

   public static DamageSource setExplosionSource(Explosion explosion) {
      return explosion != null && explosion.getExplosivePlacedBy() != null
         ? new EntityDamageSource("explosion.player", explosion.getExplosivePlacedBy()).setDifficultyScaled().setExplosion()
         : new DamageSource("explosion").setDifficultyScaled().setExplosion();
   }

   public boolean isProjectile() {
      return this.projectile;
   }

   public DamageSource setProjectile() {
      this.projectile = true;
      return this;
   }

   public boolean isExplosion() {
      return this.explosion;
   }

   public DamageSource setExplosion() {
      this.explosion = true;
      return this;
   }

   public boolean isUnblockable() {
      return this.isUnblockable;
   }

   public float getHungerDamage() {
      return this.hungerDamage;
   }

   public boolean canHarmInCreative() {
      return this.isDamageAllowedInCreativeMode;
   }

   public boolean isDamageAbsolute() {
      return this.damageIsAbsolute;
   }

   protected DamageSource(String string) {
      this.damageType = string;
   }

   public Entity getSourceOfDamage() {
      return this.getEntity();
   }

   public Entity getEntity() {
      return null;
   }

   protected DamageSource setDamageBypassesArmor() {
      this.isUnblockable = true;
      this.hungerDamage = 0.0F;
      return this;
   }

   protected DamageSource setDamageAllowedInCreativeMode() {
      this.isDamageAllowedInCreativeMode = true;
      return this;
   }

   protected DamageSource setDamageIsAbsolute() {
      this.damageIsAbsolute = true;
      this.hungerDamage = 0.0F;
      return this;
   }

   protected DamageSource setFireDamage() {
      this.fireDamage = true;
      return this;
   }

   public IChatComponent getDeathMessage(EntityLivingBase entityLivingBase) {
      EntityLivingBase var4 = entityLivingBase.getAttackingEntity();
      String var5 = "death.attack." + this.damageType;
      String var6 = var5 + ".player";
      return var4 != null && StatCollector.canTranslate(var6)
         ? new ChatComponentTranslation(var6, entityLivingBase.getDisplayName(), var4.getDisplayName())
         : new ChatComponentTranslation(var5, entityLivingBase.getDisplayName());
   }

   public boolean isFireDamage() {
      return this.fireDamage;
   }

   public String getDamageType() {
      return this.damageType;
   }

   public DamageSource setDifficultyScaled() {
      this.difficultyScaled = true;
      return this;
   }

   public boolean isDifficultyScaled() {
      return this.difficultyScaled;
   }

   public boolean isMagicDamage() {
      return this.magicDamage;
   }

   public DamageSource setMagicDamage() {
      this.magicDamage = true;
      return this;
   }

   public boolean isCreativePlayer() {
      Entity var4 = this.getEntity();
      return var4 instanceof EntityPlayer && ((EntityPlayer)var4).capabilities.isCreativeMode;
   }
}
