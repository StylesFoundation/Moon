package net.minecraft.entity.monster;

import java.util.List;
import java.util.UUID;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityWitch extends EntityMob implements IRangedAttackMob {
   private static final UUID MODIFIER_UUID = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
   private static final AttributeModifier MODIFIER = new AttributeModifier(MODIFIER_UUID, "Drinking speed penalty", -0.25, 0).setSaved(false);
   private static final Item[] witchDrops = new Item[]{
      Items.glowstone_dust, Items.sugar, Items.redstone, Items.spider_eye, Items.glass_bottle, Items.gunpowder, Items.stick, Items.stick
   };
   private int witchAttackTimer;

   public EntityWitch(World world) {
      super(world);
      this.setSize(0.6F, 1.95F);
      this.tasks.addTask(1, new EntityAISwimming(this));
      this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0, 60, 10.0F));
      this.tasks.addTask(2, new EntityAIWander(this, 1.0));
      this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(3, new EntityAILookIdle(this));
      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
      this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
   }

   @Override
   protected void entityInit() {
      super.entityInit();
      this.getDataWatcher().addObject(21, (byte)0);
   }

   @Override
   protected String getLivingSound() {
      return null;
   }

   @Override
   protected String getHurtSound() {
      return null;
   }

   @Override
   protected String getDeathSound() {
      return null;
   }

   public void setAggressive(boolean boolean1) {
      this.getDataWatcher().updateObject(21, (byte)(boolean1 ? 1 : 0));
   }

   public boolean getAggressive() {
      return this.getDataWatcher().getWatchableObjectByte(21) == 1;
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(26.0);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
   }

   @Override
   public void onLivingUpdate() {
      if (!this.worldObj.isRemote) {
         if (this.getAggressive()) {
            if (this.witchAttackTimer-- <= 0) {
               this.setAggressive(false);
               ItemStack var8 = this.getHeldItem();
               this.setCurrentItemOrArmor(0, null);
               if (var8 != null && var8.getItem() == Items.potionitem) {
                  List<PotionEffect> var9 = Items.potionitem.getEffects(var8);
                  if (var9 != null) {
                     for (PotionEffect var7 : var9) {
                        this.addPotionEffect(new PotionEffect(var7));
                     }
                  }
               }

               this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).removeModifier(MODIFIER);
            }
         } else {
            short var4 = -1;
            if (this.rand.nextFloat() < 0.15F && this.isInsideOfMaterial(Material.water) && !this.isPotionActive(Potion.waterBreathing)) {
               var4 = 8237;
            } else if (this.rand.nextFloat() < 0.15F && this.isBurning() && !this.isPotionActive(Potion.fireResistance)) {
               var4 = 16307;
            } else if (this.rand.nextFloat() < 0.05F && this.getHealth() < this.getMaxHealth()) {
               var4 = 16341;
            } else if (this.rand.nextFloat() < 0.25F
               && this.getAttackTarget() != null
               && !this.isPotionActive(Potion.moveSpeed)
               && this.getAttackTarget().getDistanceSqToEntity(this) > 121.0) {
               var4 = 16274;
            } else if (this.rand.nextFloat() < 0.25F
               && this.getAttackTarget() != null
               && !this.isPotionActive(Potion.moveSpeed)
               && this.getAttackTarget().getDistanceSqToEntity(this) > 121.0) {
               var4 = 16274;
            }

            if (var4 > -1) {
               this.setCurrentItemOrArmor(0, new ItemStack(Items.potionitem, 1, var4));
               this.witchAttackTimer = this.getHeldItem().getMaxItemUseDuration();
               this.setAggressive(true);
               IAttributeInstance var5 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
               var5.removeModifier(MODIFIER);
               var5.applyModifier(MODIFIER);
            }
         }

         if (this.rand.nextFloat() < 7.5E-4F) {
            this.worldObj.setEntityState(this, (byte)15);
         }
      }

      super.onLivingUpdate();
   }

   @Override
   public void handleStatusUpdate(byte byte1) {
      if (byte1 == 15) {
         for (int var5 = 0; var5 < this.rand.nextInt(35) + 10; var5++) {
            this.worldObj
               .spawnParticle(
                  EnumParticleTypes.SPELL_WITCH,
                  this.posX + this.rand.nextGaussian() * 0.13F,
                  this.getEntityBoundingBox().maxY + 0.5 + this.rand.nextGaussian() * 0.13F,
                  this.posZ + this.rand.nextGaussian() * 0.13F,
                  0.0,
                  0.0,
                  0.0
               );
         }
      } else {
         super.handleStatusUpdate(byte1);
      }
   }

   @Override
   protected float applyPotionDamageCalculations(DamageSource damageSource, float float2) {
      float2 = super.applyPotionDamageCalculations(damageSource, float2);
      if (damageSource.getEntity() == this) {
         float2 = 0.0F;
      }

      if (damageSource.isMagicDamage()) {
         float2 = (float)(float2 * 0.15);
      }

      return float2;
   }

   @Override
   protected void dropFewItems(boolean boolean1, int integer) {
      int var6 = this.rand.nextInt(3) + 1;

      for (int var7 = 0; var7 < var6; var7++) {
         int var8 = this.rand.nextInt(3);
         Item var9 = witchDrops[this.rand.nextInt(witchDrops.length)];
         if (integer > 0) {
            var8 += this.rand.nextInt(integer + 1);
         }

         for (int var10 = 0; var10 < var8; var10++) {
            this.dropItem(var9, 1);
         }
      }
   }

   @Override
   public void attackEntityWithRangedAttack(EntityLivingBase entityLivingBase, float float2) {
      if (!this.getAggressive()) {
         EntityPotion var6 = new EntityPotion(this.worldObj, this, 32732);
         double var7 = entityLivingBase.posY + entityLivingBase.getEyeHeight() - 1.1F;
         var6.rotationPitch -= -20.0F;
         double var9 = entityLivingBase.posX + entityLivingBase.motionX - this.posX;
         double var11 = var7 - this.posY;
         double var13 = entityLivingBase.posZ + entityLivingBase.motionZ - this.posZ;
         float var15 = MathHelper.sqrt_double(var9 * var9 + var13 * var13);
         if (var15 >= 8.0F && !entityLivingBase.isPotionActive(Potion.moveSlowdown)) {
            var6.setPotionDamage(32698);
         } else if (entityLivingBase.getHealth() >= 8.0F && !entityLivingBase.isPotionActive(Potion.poison)) {
            var6.setPotionDamage(32660);
         } else if (var15 <= 3.0F && !entityLivingBase.isPotionActive(Potion.weakness) && this.rand.nextFloat() < 0.25F) {
            var6.setPotionDamage(32696);
         }

         var6.setThrowableHeading(var9, var11 + var15 * 0.2F, var13, 0.75F, 8.0F);
         this.worldObj.spawnEntityInWorld(var6);
      }
   }

   @Override
   public float getEyeHeight() {
      return 1.62F;
   }
}
