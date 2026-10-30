package net.minecraft.entity;

import com.google.common.base.Predicates;
import com.google.common.collect.Maps;
import com.moon.Moon;
import com.moon.events.impl.player.JumpDelayEvent;
import com.moon.events.impl.player.JumpEvent;
import com.moon.module.impl.movement.Sprint;
import com.moon.module.impl.visual.Animations;
import com.moon.viamcp.ViaMCP;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.ServersideAttributeMap;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.realms.Tezzelator;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class EntityLivingBase extends Entity {
   public static final UUID sprintingSpeedBoostModifierUUID = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
   public static final AttributeModifier sprintingSpeedBoostModifier = new AttributeModifier(sprintingSpeedBoostModifierUUID, "Sprinting speed boost", 0.3F, 2)
      .setSaved(false);
   private BaseAttributeMap attributeMap;
   private final CombatTracker _combatTracker = new CombatTracker(this);
   private final Map<Integer, PotionEffect> activePotionsMap = Maps.newHashMap();
   private final ItemStack[] previousEquipment = new ItemStack[5];
   public boolean isSwingInProgress;
   public int swingProgressInt;
   public int arrowHitTimer;
   public int hurtTime;
   public int maxHurtTime;
   public float attackedAtYaw;
   public int deathTime;
   public float prevSwingProgress;
   public float swingProgress;
   public float prevLimbSwingAmount;
   public float limbSwingAmount;
   public float limbSwing;
   public int maxHurtResistantTime = 20;
   public float prevCameraPitch;
   public float cameraPitch;
   public float randomUnused2;
   public float randomUnused1;
   public float renderYawOffset;
   public float prevRenderYawOffset;
   public float rotationYawHead;
   public float prevRotationYawHead;
   public float jumpMovementFactor = 0.02F;
   protected EntityPlayer attackingPlayer;
   protected int recentlyHit;
   protected boolean dead;
   protected int entityAge;
   protected float prevOnGroundSpeedFactor;
   protected float onGroundSpeedFactor;
   protected float movedDistance;
   protected float prevMovedDistance;
   protected float unused180;
   protected int scoreValue;
   protected float lastDamage;
   public boolean bh;
   protected boolean isJumping;
   public float moveStrafing;
   public float moveForward;
   protected float randomYawVelocity;
   public int newPosRotationIncrements;
   public double newPosX;
   public double newPosY;
   public double newPosZ;
   protected double newRotationYaw;
   protected double newRotationPitch;
   private boolean potionsNeedUpdate = true;
   private EntityLivingBase entityLivingToAttack;
   private int revengeTimer;
   private EntityLivingBase lastAttacker;
   private int lastAttackerTime;
   private float landMovementFactor;
   public int jumpTicks;
   private float absorptionAmount;
   private Animations bA;

   @Override
   public void onKillCommand() {
      this.attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
   }

   public EntityLivingBase(World world) {
      super(world);
      this.applyEntityAttributes();
      this.setHealth(this.getMaxHealth());
      this.preventEntitySpawning = true;
      this.randomUnused1 = (float)((Math.random() + 1.0) * 0.01F);
      this.setPosition(this.posX, this.posY, this.posZ);
      this.randomUnused2 = (float)Math.random() * 12398.0F;
      this.rotationYaw = (float)(Math.random() * Math.PI * 2.0);
      this.rotationYawHead = this.rotationYaw;
      this.stepHeight = 0.6F;
   }

   @Override
   protected void entityInit() {
      this.dataWatcher.addObject(7, 0);
      this.dataWatcher.addObject(8, (byte)0);
      this.dataWatcher.addObject(9, (byte)0);
      this.dataWatcher.addObject(6, 1.0F);
   }

   protected void applyEntityAttributes() {
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.maxHealth);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.knockbackResistance);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.movementSpeed);
   }

   @Override
   protected void updateFallState(double double1, boolean boolean2, Block block, BlockPos blockPos) {
      if (!this.isInWater()) {
         this.handleWaterMovement();
      }

      if (!this.worldObj.isRemote && this.fallDistance > 3.0F && boolean2) {
         IBlockState var9 = this.worldObj.getBlockState(blockPos);
         Block var10 = var9.getBlock();
         float var11 = MathHelper.ceiling_float_int(this.fallDistance - 3.0F);
         if (var10.getMaterial() != Material.air) {
            double var12 = Math.min(0.2F + var11 / 15.0F, 10.0F);
            if (var12 > 2.5) {
               var12 = 2.5;
            }

            int var14 = (int)(150.0 * var12);
            ((WorldServer)this.worldObj)
               .spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX, this.posY, this.posZ, var14, 0.0, 0.0, 0.0, 0.15F, Block.getStateId(var9));
         }
      }

      super.updateFallState(double1, boolean2, block, blockPos);
   }

   public boolean canBreatheUnderwater() {
      return false;
   }

   @Override
   public void onEntityUpdate() {
      this.prevSwingProgress = this.swingProgress;
      super.onEntityUpdate();
      this.worldObj.theProfiler.startSection("livingEntityBaseTick");
      boolean var4 = this instanceof EntityPlayer;
      if (this.isEntityAlive()) {
         if (this.isEntityInsideOpaqueBlock()) {
            this.attackEntityFrom(DamageSource.inWall, 1.0F);
         } else if (var4 && !this.worldObj.getWorldBorder().contains(this.getEntityBoundingBox())) {
            double var5 = this.worldObj.getWorldBorder().getClosestDistance(this) + this.worldObj.getWorldBorder().getDamageBuffer();
            if (var5 < 0.0) {
               this.attackEntityFrom(DamageSource.inWall, Math.max(1, MathHelper.floor_double(-var5 * this.worldObj.getWorldBorder().getDamageAmount())));
            }
         }
      }

      if (this.isImmuneToFire() || this.worldObj.isRemote) {
         this.extinguish();
      }

      boolean var10 = var4 && ((EntityPlayer)this).capabilities.disableDamage;
      if (this.isEntityAlive()) {
         if (this.isInsideOfMaterial(Material.water)) {
            if (!this.canBreatheUnderwater() && !this.isPotionActive(Potion.waterBreathing.id) && !var10) {
               this.setAir(this.decreaseAirSupply(this.getAir()));
               if (this.getAir() == -20) {
                  this.setAir(0);

                  for (int var6 = 0; var6 < 8; var6++) {
                     float var7 = this.rand.nextFloat() - this.rand.nextFloat();
                     float var8 = this.rand.nextFloat() - this.rand.nextFloat();
                     float var9 = this.rand.nextFloat() - this.rand.nextFloat();
                     this.worldObj
                        .spawnParticle(
                           EnumParticleTypes.WATER_BUBBLE, this.posX + var7, this.posY + var8, this.posZ + var9, this.motionX, this.motionY, this.motionZ
                        );
                  }

                  this.attackEntityFrom(DamageSource.drown, 2.0F);
               }
            }

            if (!this.worldObj.isRemote && this.isRiding() && this.ridingEntity instanceof EntityLivingBase) {
               this.mountEntity(null);
            }
         } else {
            this.setAir(300);
         }
      }

      if (this.isEntityAlive() && this.isWet()) {
         this.extinguish();
      }

      this.prevCameraPitch = this.cameraPitch;
      if (this.hurtTime > 0) {
         this.hurtTime--;
      }

      if (this.hurtResistantTime > 0 && !(this instanceof EntityPlayerMP)) {
         this.hurtResistantTime--;
      }

      if (this.getHealth() <= 0.0F) {
         this.onDeathUpdate();
      }

      if (this.recentlyHit > 0) {
         this.recentlyHit--;
      } else {
         this.attackingPlayer = null;
      }

      if (this.lastAttacker != null && !this.lastAttacker.isEntityAlive()) {
         this.lastAttacker = null;
      }

      if (this.entityLivingToAttack != null) {
         if (!this.entityLivingToAttack.isEntityAlive()) {
            this.setRevengeTarget(null);
         } else if (this.ticksExisted - this.revengeTimer > 100) {
            this.setRevengeTarget(null);
         }
      }

      this.updatePotionEffects();
      this.prevMovedDistance = this.movedDistance;
      this.prevRenderYawOffset = this.renderYawOffset;
      this.prevRotationYawHead = this.rotationYawHead;
      this.prevRotationYaw = this.rotationYaw;
      this.prevRotationPitch = this.rotationPitch;
      this.worldObj.theProfiler.endSection();
   }

   public boolean isChild() {
      return false;
   }

   protected void onDeathUpdate() {
      this.deathTime++;
      if (this.deathTime == 20) {
         if (!this.worldObj.isRemote && (this.recentlyHit > 0 || this.isPlayer()) && this.canDropLoot() && this.worldObj.getGameRules().getBoolean("doMobLoot")
            )
          {
            int var4 = this.getExperiencePoints(this.attackingPlayer);

            while (var4 > 0) {
               int var5 = EntityXPOrb.getXPSplit(var4);
               var4 -= var5;
               this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var5));
            }
         }

         this.setDead();

         for (int var11 = 0; var11 < 20; var11++) {
            double var12 = this.rand.nextGaussian() * 0.02;
            double var7 = this.rand.nextGaussian() * 0.02;
            double var9 = this.rand.nextGaussian() * 0.02;
            this.worldObj
               .spawnParticle(
                  EnumParticleTypes.EXPLOSION_NORMAL,
                  this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width,
                  this.posY + this.rand.nextFloat() * this.height,
                  this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width,
                  var12,
                  var7,
                  var9
               );
         }
      }
   }

   protected boolean canDropLoot() {
      return !this.isChild();
   }

   protected int decreaseAirSupply(int integer) {
      int var5 = EnchantmentHelper.getRespiration(this);
      return var5 > 0 && this.rand.nextInt(var5 + 1) > 0 ? integer : integer - 1;
   }

   protected int getExperiencePoints(EntityPlayer entityPlayer) {
      return 0;
   }

   protected boolean isPlayer() {
      return false;
   }

   public Random getRNG() {
      return this.rand;
   }

   public EntityLivingBase getAITarget() {
      return this.entityLivingToAttack;
   }

   public int getRevengeTimer() {
      return this.revengeTimer;
   }

   public void setRevengeTarget(EntityLivingBase entityLivingBase) {
      this.entityLivingToAttack = entityLivingBase;
      this.revengeTimer = this.ticksExisted;
   }

   public EntityLivingBase getLastAttacker() {
      return this.lastAttacker;
   }

   public int getLastAttackerTime() {
      return this.lastAttackerTime;
   }

   public void setLastAttacker(Entity entity) {
      if (entity instanceof EntityLivingBase) {
         this.lastAttacker = (EntityLivingBase)entity;
      } else {
         this.lastAttacker = null;
      }

      this.lastAttackerTime = this.ticksExisted;
   }

   public int getAge() {
      return this.entityAge;
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      nBTTagCompound.setFloat("HealF", this.getHealth());
      nBTTagCompound.setShort("Health", (short)Math.ceil(this.getHealth()));
      nBTTagCompound.setShort("HurtTime", (short)this.hurtTime);
      nBTTagCompound.setInteger("HurtByTimestamp", this.revengeTimer);
      nBTTagCompound.setShort("DeathTime", (short)this.deathTime);
      nBTTagCompound.setFloat("AbsorptionAmount", this.getAbsorptionAmount());

      for (ItemStack var8 : this.getInventory()) {
         if (var8 != null) {
            this.attributeMap.removeAttributeModifiers(var8.getAttributeModifiers());
         }
      }

      nBTTagCompound.setTag("Attributes", SharedMonsterAttributes.writeBaseAttributeMapToNBT(this.getAttributeMap()));

      for (ItemStack var15 : this.getInventory()) {
         if (var15 != null) {
            this.attributeMap.applyAttributeModifiers(var15.getAttributeModifiers());
         }
      }

      if (!this.activePotionsMap.isEmpty()) {
         NBTTagList var10 = new NBTTagList();

         for (PotionEffect var14 : this.activePotionsMap.values()) {
            var10.appendTag(var14.writeCustomPotionEffectToNBT(new NBTTagCompound()));
         }

         nBTTagCompound.setTag("ActiveEffects", var10);
      }
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      this.setAbsorptionAmount(nBTTagCompound.getFloat("AbsorptionAmount"));
      if (nBTTagCompound.hasKey("Attributes", 9) && this.worldObj != null && !this.worldObj.isRemote) {
         SharedMonsterAttributes.setAttributeModifiers(this.getAttributeMap(), nBTTagCompound.getTagList("Attributes", 10));
      }

      if (nBTTagCompound.hasKey("ActiveEffects", 9)) {
         NBTTagList var5 = nBTTagCompound.getTagList("ActiveEffects", 10);

         for (int var6 = 0; var6 < var5.tagCount(); var6++) {
            PotionEffect var8 = PotionEffect.readCustomPotionEffectFromNBT(var5.getCompoundTagAt(var6));
            if (var8 != null) {
               this.activePotionsMap.put(var8.getPotionID(), var8);
            }
         }
      }

      if (nBTTagCompound.hasKey("HealF", 99)) {
         this.setHealth(nBTTagCompound.getFloat("HealF"));
      } else {
         NBTBase var9 = nBTTagCompound.getTag("Health");
         if (var9 == null) {
            this.setHealth(this.getMaxHealth());
         } else if (var9.getId() == 5) {
            this.setHealth(((NBTTagFloat)var9).getFloat());
         } else if (var9.getId() == 2) {
            this.setHealth(((NBTTagShort)var9).getShort());
         }
      }

      this.hurtTime = nBTTagCompound.getShort("HurtTime");
      this.deathTime = nBTTagCompound.getShort("DeathTime");
      this.revengeTimer = nBTTagCompound.getInteger("HurtByTimestamp");
   }

   protected void updatePotionEffects() {
      Iterator var4 = this.activePotionsMap.keySet().iterator();

      while (var4.hasNext()) {
         Integer var5 = (Integer)var4.next();
         PotionEffect var6 = this.activePotionsMap.get(var5);
         if (!var6.onUpdate(this)) {
            if (!this.worldObj.isRemote) {
               var4.remove();
               this.onFinishedPotionEffect(var6);
            }
         } else if (var6.getDuration() % 600 == 0) {
            this.onChangedPotionEffect(var6, false);
         }
      }

      if (this.potionsNeedUpdate) {
         if (!this.worldObj.isRemote) {
            this.updatePotionMetadata();
         }

         this.potionsNeedUpdate = false;
      }

      int var14 = this.dataWatcher.getWatchableObjectInt(7);
      boolean var15 = this.dataWatcher.getWatchableObjectByte(8) > 0;
      if (var14 > 0) {
         boolean var7;
         if (!this.isInvisible()) {
            var7 = this.rand.nextBoolean();
         } else {
            var7 = this.rand.nextInt(15) == 0;
         }

         if (var15) {
            var7 &= this.rand.nextInt(5) == 0;
         }

         if (var7 && var14 > 0) {
            double var8 = (var14 >> 16 & 0xFF) / 255.0;
            double var10 = (var14 >> 8 & 0xFF) / 255.0;
            double var12 = (var14 & 0xFF) / 255.0;
            this.worldObj
               .spawnParticle(
                  var15 ? EnumParticleTypes.SPELL_MOB_AMBIENT : EnumParticleTypes.SPELL_MOB,
                  this.posX + (this.rand.nextDouble() - 0.5) * this.width,
                  this.posY + this.rand.nextDouble() * this.height,
                  this.posZ + (this.rand.nextDouble() - 0.5) * this.width,
                  var8,
                  var10,
                  var12
               );
         }
      }
   }

   protected void updatePotionMetadata() {
      if (this.activePotionsMap.isEmpty()) {
         this.resetPotionEffectMetadata();
         this.setInvisible(false);
      } else {
         int var4 = PotionHelper.calcPotionLiquidColor(this.activePotionsMap.values());
         this.dataWatcher.updateObject(8, (byte)(PotionHelper.getAreAmbient(this.activePotionsMap.values()) ? 1 : 0));
         this.dataWatcher.updateObject(7, var4);
         this.setInvisible(this.isPotionActive(Potion.invisibility.id));
      }
   }

   protected void resetPotionEffectMetadata() {
      this.dataWatcher.updateObject(8, (byte)0);
      this.dataWatcher.updateObject(7, 0);
   }

   public void clearActivePotions() {
      Iterator var4 = this.activePotionsMap.keySet().iterator();

      while (var4.hasNext()) {
         Integer var5 = (Integer)var4.next();
         PotionEffect var6 = this.activePotionsMap.get(var5);
         if (!this.worldObj.isRemote) {
            var4.remove();
            this.onFinishedPotionEffect(var6);
         }
      }
   }

   public Collection<PotionEffect> getActivePotionEffects() {
      return this.activePotionsMap.values();
   }

   public boolean isPotionActive(int integer) {
      return this.activePotionsMap.containsKey(integer);
   }

   public boolean isPotionActive(Potion potion) {
      return this.activePotionsMap.containsKey(potion.id);
   }

   public PotionEffect getActivePotionEffect(Potion potion) {
      return this.activePotionsMap.get(potion.id);
   }

   public void addPotionEffect(PotionEffect potionEffect) {
      if (potionEffect.getPotionID() < 0) {
         System.out
            .println("Blocked invalid potion effect: " + potionEffect.getEffectName() + ", " + potionEffect.getPotionID() + ", " + potionEffect.getAmplifier());
      } else {
         if (this.isPotionApplicable(potionEffect)) {
            if (this.activePotionsMap.containsKey(potionEffect.getPotionID())) {
               this.activePotionsMap.get(potionEffect.getPotionID()).combine(potionEffect);
               this.onChangedPotionEffect(this.activePotionsMap.get(potionEffect.getPotionID()), true);
            } else {
               this.activePotionsMap.put(potionEffect.getPotionID(), potionEffect);
               this.onNewPotionEffect(potionEffect);
            }
         }
      }
   }

   public boolean isPotionApplicable(PotionEffect potionEffect) {
      if (this.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD) {
         return true;
      }

      int var5 = potionEffect.getPotionID();
      return var5 != Potion.regeneration.id && var5 != Potion.poison.id;
   }

   public boolean isEntityUndead() {
      return this.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD;
   }

   public void removePotionEffectClient(int integer) {
      this.activePotionsMap.remove(integer);
   }

   public void removePotionEffect(int integer) {
      PotionEffect var4 = this.activePotionsMap.remove(integer);
      if (var4 != null) {
         this.onFinishedPotionEffect(var4);
      }
   }

   protected void onNewPotionEffect(PotionEffect potionEffect) {
      this.potionsNeedUpdate = true;
      if (!this.worldObj.isRemote) {
         Potion.potionTypes[potionEffect.getPotionID()].applyAttributesModifiersToEntity(this, this.getAttributeMap(), potionEffect.getAmplifier());
      }
   }

   protected void onChangedPotionEffect(PotionEffect potionEffect, boolean boolean2) {
      this.potionsNeedUpdate = true;
      if (boolean2 && !this.worldObj.isRemote) {
         Potion.potionTypes[potionEffect.getPotionID()].removeAttributesModifiersFromEntity(this, this.getAttributeMap(), potionEffect.getAmplifier());
         Potion.potionTypes[potionEffect.getPotionID()].applyAttributesModifiersToEntity(this, this.getAttributeMap(), potionEffect.getAmplifier());
      }
   }

   protected void onFinishedPotionEffect(PotionEffect potionEffect) {
      this.potionsNeedUpdate = true;
      if (!this.worldObj.isRemote) {
         Potion.potionTypes[potionEffect.getPotionID()].removeAttributesModifiersFromEntity(this, this.getAttributeMap(), potionEffect.getAmplifier());
      }
   }

   public void heal(float float1) {
      float var4 = this.getHealth();
      if (var4 > 0.0F) {
         this.setHealth(var4 + float1);
      }
   }

   public final float getHealth() {
      return this.dataWatcher.getWatchableObjectFloat(6);
   }

   public void setHealth(float float1) {
      this.dataWatcher.updateObject(6, MathHelper.clamp_float(float1, 0.0F, this.getMaxHealth()));
   }

   @Override
   public boolean attackEntityFrom(DamageSource damageSource, float float2) {
      if (this.isEntityInvulnerable(damageSource)) {
         return false;
      }

      if (this.worldObj.isRemote) {
         return false;
      }

      this.entityAge = 0;
      if (this.getHealth() <= 0.0F) {
         return false;
      }

      if (damageSource.isFireDamage() && this.isPotionActive(Potion.fireResistance)) {
         return false;
      }

      if ((damageSource == DamageSource.anvil || damageSource == DamageSource.fallingBlock) && this.getEquipmentInSlot(4) != null) {
         this.getEquipmentInSlot(4).damageItem((int)(float2 * 4.0F + this.rand.nextFloat() * float2 * 2.0F), this);
         float2 *= 0.75F;
      }

      this.limbSwingAmount = 1.5F;
      boolean var6 = true;
      if (this.hurtResistantTime > this.maxHurtResistantTime / 2.0F) {
         if (float2 <= this.lastDamage) {
            return false;
         }

         this.damageEntity(damageSource, float2 - this.lastDamage);
         this.lastDamage = float2;
         var6 = false;
      } else {
         this.lastDamage = float2;
         this.hurtResistantTime = this.maxHurtResistantTime;
         this.damageEntity(damageSource, float2);
         this.hurtTime = this.maxHurtTime = 10;
      }

      this.attackedAtYaw = 0.0F;
      Entity var7 = damageSource.getEntity();
      if (var7 != null) {
         if (var7 instanceof EntityLivingBase) {
            this.setRevengeTarget((EntityLivingBase)var7);
         }

         if (var7 instanceof EntityPlayer) {
            this.recentlyHit = 100;
            this.attackingPlayer = (EntityPlayer)var7;
         } else if (var7 instanceof EntityWolf var8 && var8.isTamed()) {
            this.recentlyHit = 100;
            this.attackingPlayer = null;
         }
      }

      if (var6) {
         this.worldObj.setEntityState(this, (byte)2);
         if (damageSource != DamageSource.drown) {
            this.setBeenAttacked();
         }

         if (var7 != null) {
            double var12 = var7.posX - this.posX;

            double var10;
            for (var10 = var7.posZ - this.posZ; var12 * var12 + var10 * var10 < 1.0E-4; var10 = (Math.random() - Math.random()) * 0.01) {
               var12 = (Math.random() - Math.random()) * 0.01;
            }

            this.attackedAtYaw = (float)(MathHelper.atan2(var10, var12) * 180.0 / Math.PI - this.rotationYaw);
            this.knockBack(var7, float2, var12, var10);
         } else {
            this.attackedAtYaw = (int)(Math.random() * 2.0) * 180;
         }
      }

      if (this.getHealth() <= 0.0F) {
         String var13 = this.getDeathSound();
         if (var6 && var13 != null) {
            this.playSound(var13, this.getSoundVolume(), this.getSoundPitch());
         }

         this.onDeath(damageSource);
      } else {
         String var14 = this.getHurtSound();
         if (var6 && var14 != null) {
            this.playSound(var14, this.getSoundVolume(), this.getSoundPitch());
         }
      }

      return true;
   }

   public void renderBrokenItemStack(ItemStack itemStack) {
      this.playSound("random.break", 0.8F, 0.8F + this.worldObj.rand.nextFloat() * 0.4F);

      for (int var5 = 0; var5 < 5; var5++) {
         Vec3 var6 = new Vec3((this.rand.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0)
            .rotatePitch(-this.rotationPitch * (float) Math.PI / 180.0F)
            .rotateYaw(-this.rotationYaw * (float) Math.PI / 180.0F);
         double var7 = -this.rand.nextFloat() * 0.6 - 0.3;
         Vec3 var9 = new Vec3((this.rand.nextFloat() - 0.5) * 0.3, var7, 0.6)
            .rotatePitch(-this.rotationPitch * (float) Math.PI / 180.0F)
            .rotateYaw(-this.rotationYaw * (float) Math.PI / 180.0F)
            .addVector(this.posX, this.posY + this.getEyeHeight(), this.posZ);
         this.worldObj
            .spawnParticle(
               EnumParticleTypes.ITEM_CRACK,
               var9.xCoord,
               var9.yCoord,
               var9.zCoord,
               var6.xCoord,
               var6.yCoord + 0.05,
               var6.zCoord,
               Item.getIdFromItem(itemStack.getItem())
            );
      }
   }

   public void onDeath(DamageSource damageSource) {
      Entity var5 = damageSource.getEntity();
      EntityLivingBase var6 = this.getAttackingEntity();
      if (this.scoreValue >= 0 && var6 != null) {
         var6.addToPlayerScore(this, this.scoreValue);
      }

      if (var5 != null) {
         var5.onKillEntity(this);
      }

      this.dead = true;
      this.getCombatTracker().reset();
      if (!this.worldObj.isRemote) {
         int var7 = 0;
         if (var5 instanceof EntityPlayer) {
            var7 = EnchantmentHelper.getLootingModifier((EntityLivingBase)var5);
         }

         if (this.canDropLoot() && this.worldObj.getGameRules().getBoolean("doMobLoot")) {
            this.dropFewItems(this.recentlyHit > 0, var7);
            this.dropEquipment(this.recentlyHit > 0, var7);
            if (this.recentlyHit > 0 && this.rand.nextFloat() < 0.025F + var7 * 0.01F) {
               this.addRandomDrop();
            }
         }
      }

      this.worldObj.setEntityState(this, (byte)3);
   }

   protected void dropEquipment(boolean boolean1, int integer) {
   }

   public void knockBack(Entity entity, float float2, double double3, double double4) {
      if (this.rand.nextDouble() >= this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue()) {
         this.isAirBorne = true;
         float var10 = MathHelper.sqrt_double(double3 * double3 + double4 * double4);
         this.motionX /= 2.0;
         this.motionY /= 2.0;
         this.motionZ /= 2.0;
         this.motionX -= double3 / var10 * 0.4F;
         this.motionY += 0.4F;
         this.motionZ -= double4 / var10 * 0.4F;
         if (this.motionY > 0.4F) {
            this.motionY = 0.4F;
         }
      }
   }

   protected String getHurtSound() {
      return "game.neutral.hurt";
   }

   protected String getDeathSound() {
      return "game.neutral.die";
   }

   protected void addRandomDrop() {
   }

   protected void dropFewItems(boolean boolean1, int integer) {
   }

   public boolean isOnLadder() {
      int var4 = MathHelper.floor_double(this.posX);
      int var5 = MathHelper.floor_double(this.getEntityBoundingBox().minY);
      int var6 = MathHelper.floor_double(this.posZ);
      Block var7 = this.worldObj.getBlockState(new BlockPos(var4, var5, var6)).getBlock();
      return (var7 == Blocks.ladder || var7 == Blocks.vine) && (!(this instanceof EntityPlayer) || !((EntityPlayer)this).isSpectator());
   }

   @Override
   public boolean isEntityAlive() {
      return !this.isDead && this.getHealth() > 0.0F;
   }

   @Override
   public void fall(float float1, float float2) {
      super.fall(float1, float2);
      PotionEffect var6 = this.getActivePotionEffect(Potion.jump);
      float var7 = var6 != null ? var6.getAmplifier() + 1 : 0.0F;
      int var8 = MathHelper.ceiling_float_int((float1 - 3.0F - var7) * float2);
      if (var8 > 0) {
         this.playSound(this.getFallSoundString(var8), 1.0F, 1.0F);
         this.attackEntityFrom(DamageSource.fall, var8);
         int var9 = MathHelper.floor_double(this.posX);
         int var10 = MathHelper.floor_double(this.posY - 0.2F);
         int var11 = MathHelper.floor_double(this.posZ);
         Block var12 = this.worldObj.getBlockState(new BlockPos(var9, var10, var11)).getBlock();
         if (var12.getMaterial() != Material.air) {
            Block.SoundType var13 = var12.stepSound;
            this.playSound(var13.getStepSound(), var13.getVolume() * 0.5F, var13.getFrequency() * 0.75F);
         }
      }
   }

   protected String getFallSoundString(int integer) {
      return integer > 4 ? "game.neutral.hurt.fall.big" : "game.neutral.hurt.fall.small";
   }

   @Override
   public void performHurtAnimation() {
      this.hurtTime = this.maxHurtTime = 10;
      this.attackedAtYaw = 0.0F;
   }

   public int getTotalArmorValue() {
      int var4 = 0;

      for (ItemStack var8 : this.getInventory()) {
         if (var8 != null && var8.getItem() instanceof ItemArmor) {
            int var9 = ((ItemArmor)var8.getItem()).damageReduceAmount;
            var4 += var9;
         }
      }

      return var4;
   }

   protected void damageArmor(float float1) {
   }

   protected float applyArmorCalculations(DamageSource damageSource, float float2) {
      if (!damageSource.isUnblockable()) {
         int var6 = 25 - this.getTotalArmorValue();
         float var7 = float2 * var6;
         this.damageArmor(float2);
         float2 = var7 / 25.0F;
      }

      return float2;
   }

   protected float applyPotionDamageCalculations(DamageSource damageSource, float float2) {
      if (damageSource.isDamageAbsolute()) {
         return float2;
      }

      if (this.isPotionActive(Potion.resistance) && damageSource != DamageSource.outOfWorld) {
         int var6 = (this.getActivePotionEffect(Potion.resistance).getAmplifier() + 1) * 5;
         int var7 = 25 - var6;
         float2 = float2 * var7 / 25.0F;
      }

      if (float2 <= 0.0F) {
         return 0.0F;
      }

      int var8 = EnchantmentHelper.getEnchantmentModifierDamage(this.getInventory(), damageSource);
      if (var8 > 20) {
         var8 = 20;
      }

      if (var8 > 0 && var8 <= 20) {
         int var9 = 25 - var8;
         float2 = float2 * var9 / 25.0F;
      }

      return float2;
   }

   protected void damageEntity(DamageSource damageSource, float float2) {
      if (!this.isEntityInvulnerable(damageSource)) {
         float2 = this.applyArmorCalculations(damageSource, float2);
         float2 = this.applyPotionDamageCalculations(damageSource, float2);
         float var6 = float2;
         float2 = Math.max(float2 - this.getAbsorptionAmount(), 0.0F);
         this.setAbsorptionAmount(this.getAbsorptionAmount() - (var6 - float2));
         if (float2 != 0.0F) {
            float var7 = this.getHealth();
            this.setHealth(var7 - float2);
            this.getCombatTracker().trackDamage(damageSource, var7, float2);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - float2);
         }
      }
   }

   public CombatTracker getCombatTracker() {
      return this._combatTracker;
   }

   public EntityLivingBase getAttackingEntity() {
      return this._combatTracker.func_94550_c() != null
         ? this._combatTracker.func_94550_c()
         : (this.attackingPlayer != null ? this.attackingPlayer : (this.entityLivingToAttack != null ? this.entityLivingToAttack : null));
   }

   public final float getMaxHealth() {
      return (float)this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
   }

   public final int getArrowCountInEntity() {
      return this.dataWatcher.getWatchableObjectByte(9);
   }

   public final void setArrowCountInEntity(int integer) {
      this.dataWatcher.updateObject(9, (byte)integer);
   }

   private int Method1030() {
      this.Method1031();
      if (this.bA.isEnabled() && this.bA.getSlowAnimationDown().getValue()) {
         if (this == Minecraft.getMinecraft().thePlayer) {
            return 10 + this.bA.getSlowDownStrength().getValue();
         } else {
            return this.isPotionActive(Potion.digSpeed)
               ? 6 - (1 + this.getActivePotionEffect(Potion.digSpeed).getAmplifier())
               : (this.isPotionActive(Potion.digSlowdown) ? 6 + (1 + this.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2 : 6);
         }
      } else {
         return this.isPotionActive(Potion.digSpeed)
            ? 6 - (1 + this.getActivePotionEffect(Potion.digSpeed).getAmplifier())
            : (this.isPotionActive(Potion.digSlowdown) ? 6 + (1 + this.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2 : 6);
      }
   }

   public void swingItem() {
      if (!this.isSwingInProgress || this.swingProgressInt >= this.Method1030() / 2 || this.swingProgressInt < 0) {
         this.swingProgressInt = -1;
         this.isSwingInProgress = true;
         if (this.worldObj instanceof WorldServer) {
            ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, new S0BPacketAnimation(this, 0));
         }
      }
   }

   @Override
   public void handleStatusUpdate(byte byte1) {
      if (byte1 == 2) {
         this.limbSwingAmount = 1.5F;
         this.hurtResistantTime = this.maxHurtResistantTime;
         this.hurtTime = this.maxHurtTime = 10;
         this.attackedAtYaw = 0.0F;
         String var5 = this.getHurtSound();
         if (var5 != null) {
            this.playSound(this.getHurtSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
         }

         this.attackEntityFrom(DamageSource.generic, 0.0F);
      } else if (byte1 == 3) {
         String var6 = this.getDeathSound();
         if (var6 != null) {
            this.playSound(this.getDeathSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
         }

         this.setHealth(0.0F);
         this.onDeath(DamageSource.generic);
      } else {
         super.handleStatusUpdate(byte1);
      }
   }

   @Override
   protected void kill() {
      this.attackEntityFrom(DamageSource.outOfWorld, 4.0F);
   }

   protected void updateArmSwingProgress() {
      int var4 = this.Method1030();
      if (this.isSwingInProgress) {
         this.swingProgressInt++;
         if (this.swingProgressInt >= var4) {
            this.swingProgressInt = 0;
            this.isSwingInProgress = false;
         }
      } else {
         this.swingProgressInt = 0;
      }

      this.swingProgress = (float)this.swingProgressInt / var4;
   }

   public IAttributeInstance getEntityAttribute(IAttribute iAttribute) {
      return this.getAttributeMap().getAttributeInstance(iAttribute);
   }

   public BaseAttributeMap getAttributeMap() {
      if (this.attributeMap == null) {
         this.attributeMap = new ServersideAttributeMap();
      }

      return this.attributeMap;
   }

   public EnumCreatureAttribute getCreatureAttribute() {
      return EnumCreatureAttribute.UNDEFINED;
   }

   public abstract ItemStack getHeldItem();

   public abstract ItemStack getEquipmentInSlot(int integer);

   public abstract ItemStack getCurrentArmor(int integer);

   @Override
   public abstract void setCurrentItemOrArmor(int integer, ItemStack itemStack);

   @Override
   public void setSprinting(boolean boolean1) {
      super.setSprinting(boolean1);
      IAttributeInstance var5 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
      if (var5.getModifier(sprintingSpeedBoostModifierUUID) != null) {
         var5.removeModifier(sprintingSpeedBoostModifier);
      }

      if (boolean1) {
         var5.applyModifier(sprintingSpeedBoostModifier);
      }
   }

   @Override
   public abstract ItemStack[] getInventory();

   protected float getSoundVolume() {
      return 1.0F;
   }

   protected float getSoundPitch() {
      return this.isChild() ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.5F : (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F;
   }

   protected boolean isMovementBlocked() {
      return this.getHealth() <= 0.0F;
   }

   public void dismountEntity(Entity entity) {
      double var5 = entity.posX;
      double var7 = entity.getEntityBoundingBox().minY + entity.height;
      double var9 = entity.posZ;

      for (int var12 = -1; var12 <= 1; var12++) {
         for (int var13 = -1; var13 < 1; var13++) {
            if (var12 != 0 || var13 != 0) {
               int var14 = (int)(this.posX + var12);
               int var15 = (int)(this.posZ + var13);
               AxisAlignedBB var16 = this.getEntityBoundingBox().offset(var12, 1.0, var13);
               if (this.worldObj.getCollisionBoxes(var16).isEmpty()) {
                  if (World.doesBlockHaveSolidTopSurface(this.worldObj, new BlockPos(var14, (int)this.posY, var15))) {
                     this.setPositionAndUpdate(this.posX + var12, this.posY + 1.0, this.posZ + var13);
                     return;
                  }

                  if (World.doesBlockHaveSolidTopSurface(this.worldObj, new BlockPos(var14, (int)this.posY - 1, var15))
                     || this.worldObj.getBlockState(new BlockPos(var14, (int)this.posY - 1, var15)).getBlock().getMaterial() == Material.water) {
                     var5 = this.posX + var12;
                     var7 = this.posY + 1.0;
                     var9 = this.posZ + var13;
                  }
               }
            }
         }
      }

      this.setPositionAndUpdate(var5, var7, var9);
   }

   @Override
   public boolean getAlwaysRenderNameTagForRender() {
      return false;
   }

   protected float getJumpUpwardsMotion()
   {
      return 0.42F;
   }

   protected void jump() {
      this.motionY = this.getJumpUpwardsMotion();
      if (this.isPotionActive(Potion.jump)) {
         this.motionY = this.motionY + (this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F;
      }

      if (this.isSprinting()) {
         Sprint var4 = Moon.getInstance().getModuleManager().getModule(Sprint.class);
         boolean var5 = var4.isEnabled() && var4.getOmni().getValue();
         boolean var6 = this.moveForward < 0.0F && this.moveStrafing == 0.0F;
         boolean var7 = this.moveStrafing != 0.0F && this.moveForward < 0.0F;
         boolean var8 = this.moveStrafing != 0.0F && this.moveForward == 0.0F;
         float var9 = var5 ? (var6 ? 180 : (var7 ? (this.moveStrafing < 0.0F ? -135 : 135) : (var8 ? (this.moveStrafing < 0.0F ? -90 : 90) : 0))) : 0.0F;
         JumpEvent var10 = new JumpEvent(this.rotationYaw - var9);
         Moon.getInstance().getEventBus().publish(var10);
         float var11 = var10.getYaw() * (float) (Math.PI / 180.0);
         this.motionX = this.motionX - MathHelper.sin(var11) * 0.2F;
         this.motionZ = this.motionZ + MathHelper.cos(var11) * 0.2F;
      }

      this.isAirBorne = true;
   }

   protected void updateAITick() {
      this.motionY += 0.04F;
   }

   protected void handleJumpLava() {
      this.motionY += 0.04F;
   }

   public void moveEntityWithHeading(float float1, float float2) {
      if (this.isServerWorld()) {
         if (!this.isInWater() || this instanceof EntityPlayer && ((EntityPlayer)this).capabilities.isFlying) {
            if (!this.isInLava() || this instanceof EntityPlayer && ((EntityPlayer)this).capabilities.isFlying) {
               float var12 = 0.91F;
               if (this.onGround) {
                  var12 = this.worldObj
                        .getBlockState(
                           new BlockPos(
                              MathHelper.floor_double(this.posX),
                              MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1,
                              MathHelper.floor_double(this.posZ)
                           )
                        )
                        .getBlock()
                        .slipperiness
                     * 0.91F;
               }

               float var7 = 0.16277136F / (var12 * var12 * var12);
               float var15;
               if (this.onGround) {
                  var15 = this.getAIMoveSpeed() * var7;
               } else {
                  var15 = this.jumpMovementFactor;
               }

               this.moveFlying(float1, float2, var15);
               var12 = 0.91F;
               if (this.onGround) {
                  var12 = this.worldObj
                        .getBlockState(
                           new BlockPos(
                              MathHelper.floor_double(this.posX),
                              MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1,
                              MathHelper.floor_double(this.posZ)
                           )
                        )
                        .getBlock()
                        .slipperiness
                     * 0.91F;
               }

               if (this.isOnLadder()) {
                  this.motionX = MathHelper.clamp_double(this.motionX, -0.15F, 0.15F);
                  this.motionZ = MathHelper.clamp_double(this.motionZ, -0.15F, 0.15F);
                  this.fallDistance = 0.0F;
                  if (this.motionY < -0.15) {
                     this.motionY = -0.15;
                  }

                  boolean var17 = this.isSneaking() && this instanceof EntityPlayer;
                  if (var17 && this.motionY < 0.0) {
                     this.motionY = 0.0;
                  }
               }

               this.moveEntity(this.motionX, this.motionY, this.motionZ);
               if (this.isCollidedHorizontally && this.isOnLadder()) {
                  this.motionY = 0.2;
               }

               if (this.worldObj.isRemote
                  && (
                     !this.worldObj.Method322(new BlockPos((int)this.posX, 0, (int)this.posZ))
                        || !this.worldObj.getChunkFromBlockCoords(new BlockPos((int)this.posX, 0, (int)this.posZ)).isLoaded()
                  )) {
                  if (this.posY > 0.0) {
                     this.motionY = -0.1;
                  } else {
                     this.motionY = 0.0;
                  }
               } else {
                  this.motionY -= 0.08;
               }

               this.motionY *= 0.98F;
               this.motionX *= var12;
               this.motionZ *= var12;
            } else {
               double var11 = this.posY;
               this.moveFlying(float1, float2, 0.02F);
               this.moveEntity(this.motionX, this.motionY, this.motionZ);
               this.motionX *= 0.5;
               this.motionY *= 0.5;
               this.motionZ *= 0.5;
               this.motionY -= 0.02;
               if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6F - this.posY + var11, this.motionZ)) {
                  this.motionY = 0.3F;
               }
            }
         } else {
            double var6 = this.posY;
            float var8 = 0.8F;
            float var9 = 0.02F;
            float var10 = EnchantmentHelper.getDepthStriderModifier(this);
            if (var10 > 3.0F) {
               var10 = 3.0F;
            }

            if (!this.onGround) {
               var10 *= 0.5F;
            }

            if (var10 > 0.0F) {
               var8 = 0.8F + -0.25399995F * var10 / 3.0F;
               var9 = 0.02F + (this.getAIMoveSpeed() - 0.02F) * var10 / 3.0F;
            }

            this.moveFlying(float1, float2, var9);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= var8;
            this.motionY *= 0.8F;
            this.motionZ *= var8;
            this.motionY -= 0.02;
            if (this.isCollidedHorizontally && this.isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6F - this.posY + var6, this.motionZ)) {
               this.motionY = 0.3F;
            }
         }
      }

      this.prevLimbSwingAmount = this.limbSwingAmount;
      double var14 = this.posX - this.prevPosX;
      double var16 = this.posZ - this.prevPosZ;
      float var18 = MathHelper.sqrt_double(var14 * var14 + var16 * var16) * 4.0F;
      if (var18 > 1.0F) {
         var18 = 1.0F;
      }

      this.limbSwingAmount = this.limbSwingAmount + (var18 - this.limbSwingAmount) * 0.4F;
      this.limbSwing = this.limbSwing + this.limbSwingAmount;
   }

   public float getAIMoveSpeed() {
      return this.landMovementFactor;
   }

   public void setAIMoveSpeed(float float1) {
      this.landMovementFactor = float1;
   }

   public boolean attackEntityAsMob(Entity entity) {
      this.setLastAttacker(entity);
      return false;
   }

   public boolean isPlayerSleeping() {
      return false;
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (!this.worldObj.isRemote) {
         int var4 = this.getArrowCountInEntity();
         if (var4 > 0) {
            if (this.arrowHitTimer <= 0) {
               this.arrowHitTimer = 20 * (30 - var4);
            }

            this.arrowHitTimer--;
            if (this.arrowHitTimer <= 0) {
               this.setArrowCountInEntity(var4 - 1);
            }
         }

         for (int var5 = 0; var5 < 5; var5++) {
            ItemStack var6 = this.previousEquipment[var5];
            ItemStack var7 = this.getEquipmentInSlot(var5);
            if (!ItemStack.areItemStacksEqual(var7, var6)) {
               ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, new S04PacketEntityEquipment(this.getEntityId(), var5, var7));
               if (var6 != null) {
                  this.attributeMap.removeAttributeModifiers(var6.getAttributeModifiers());
               }

               if (var7 != null) {
                  this.attributeMap.applyAttributeModifiers(var7.getAttributeModifiers());
               }

               this.previousEquipment[var5] = var7 == null ? null : var7.copy();
            }
         }

         if (this.ticksExisted % 20 == 0) {
            this.getCombatTracker().reset();
         }
      }

      this.onLivingUpdate();
      double var14 = this.posX - this.prevPosX;
      double var15 = this.posZ - this.prevPosZ;
      float var8 = (float)(var14 * var14 + var15 * var15);
      float var9 = this.renderYawOffset;
      float var10 = 0.0F;
      this.prevOnGroundSpeedFactor = this.onGroundSpeedFactor;
      float var11 = 0.0F;
      if (var8 > 0.0025000002F) {
         var11 = 1.0F;
         var10 = (float)Math.sqrt(var8) * 3.0F;
         var9 = (float)MathHelper.atan2(var15, var14) * 180.0F / (float) Math.PI - 90.0F;
      }

      if (this.swingProgress > 0.0F) {
         EntityLivingBase var13 = this;
         if (var13 instanceof EntityPlayerSP) {
            var9 = ((EntityPlayerSP)var13).cM;
         } else {
            var9 = this.rotationYaw;
         }
      }

      if (!this.onGround) {
         var11 = 0.0F;
      }

      this.onGroundSpeedFactor = this.onGroundSpeedFactor + (var11 - this.onGroundSpeedFactor) * 0.3F;
      this.worldObj.theProfiler.startSection("headTurn");
      var10 = this.updateDistance(var9, var10);
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("rangeChecks");

      while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
         this.prevRotationYaw -= 360.0F;
      }

      while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
         this.prevRotationYaw += 360.0F;
      }

      while (this.renderYawOffset - this.prevRenderYawOffset < -180.0F) {
         this.prevRenderYawOffset -= 360.0F;
      }

      while (this.renderYawOffset - this.prevRenderYawOffset >= 180.0F) {
         this.prevRenderYawOffset += 360.0F;
      }

      while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
         this.prevRotationPitch -= 360.0F;
      }

      while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
         this.prevRotationPitch += 360.0F;
      }

      while (this.rotationYawHead - this.prevRotationYawHead < -180.0F) {
         this.prevRotationYawHead -= 360.0F;
      }

      while (this.rotationYawHead - this.prevRotationYawHead >= 180.0F) {
         this.prevRotationYawHead += 360.0F;
      }

      this.worldObj.theProfiler.endSection();
      this.movedDistance += var10;
   }

   protected float updateDistance(float float1, float float2) {
      float var6 = MathHelper.wrapAngleTo180_float(float1 - this.renderYawOffset);
      this.renderYawOffset += var6 * 0.3F;
      float var7 = this.rotationYaw;
      EntityLivingBase var9 = this;
      if (var9 instanceof EntityPlayerSP) {
         var7 = ((EntityPlayerSP)var9).cM;
      }

      float var8 = MathHelper.wrapAngleTo180_float(var7 - this.renderYawOffset);
      boolean var10 = var8 < -90.0F || var8 >= 90.0F;
      if (var8 < -75.0F) {
         var8 = -75.0F;
      }

      if (var8 >= 75.0F) {
         var8 = 75.0F;
      }

      this.renderYawOffset = var7 - var8;
      if (var8 * var8 > 2500.0F) {
         this.renderYawOffset += var8 * 0.2F;
      }

      if (var10) {
         float2 *= -1.0F;
      }

      return float2;
   }

   public void onLivingUpdate() {
      this.q = this.motionX;
      this.r = this.motionY;
      this.s = this.motionZ;
      if (this.jumpTicks > 0) {
         this.jumpTicks--;
      }

      if (this.newPosRotationIncrements > 0) {
         double var4 = this.posX + (this.newPosX - this.posX) / this.newPosRotationIncrements;
         double var6 = this.posY + (this.newPosY - this.posY) / this.newPosRotationIncrements;
         double var8 = this.posZ + (this.newPosZ - this.posZ) / this.newPosRotationIncrements;
         double var10 = MathHelper.wrapAngleTo180_double(this.newRotationYaw - this.rotationYaw);
         this.rotationYaw = (float)(this.rotationYaw + var10 / this.newPosRotationIncrements);
         this.rotationPitch = (float)(this.rotationPitch + (this.newRotationPitch - this.rotationPitch) / this.newPosRotationIncrements);
         this.newPosRotationIncrements--;
         this.setPosition(var4, var6, var8);
         this.setRotation(this.rotationYaw, this.rotationPitch);
      } else if (!this.isServerWorld()) {
         this.motionX *= 0.98;
         this.motionY *= 0.98;
         this.motionZ *= 0.98;
      }

      double var12 = ViaMCP.getInstance().getVersion() <= 47 ? 0.005 : 0.003;
      if (Math.abs(this.motionX) < var12) {
         this.motionX = 0.0;
      }

      if (Math.abs(this.motionY) < var12) {
         this.motionY = 0.0;
      }

      if (Math.abs(this.motionZ) < var12) {
         this.motionZ = 0.0;
      }

      this.worldObj.theProfiler.startSection("ai");
      if (this.isMovementBlocked()) {
         this.isJumping = false;
         this.moveStrafing = 0.0F;
         this.moveForward = 0.0F;
         this.randomYawVelocity = 0.0F;
      } else if (this.isServerWorld()) {
         this.worldObj.theProfiler.startSection("newAi");
         this.updateEntityActionState();
         this.worldObj.theProfiler.endSection();
      }

      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("jump");
      if (!this.isJumping && !this.bh) {
         this.jumpTicks = 0;
      } else {
         if (this.isInWater()) {
            this.updateAITick();
         } else if (this.isInLava()) {
            this.handleJumpLava();
         } else if (this.onGround && this.jumpTicks == 0) {
            JumpDelayEvent var13 = new JumpDelayEvent();
            Moon.getInstance().getEventBus().publish(var13);
            if (!var13.isCancelled()) {
               this.jump();
               this.jumpTicks = 10;
            }
         }

         this.bh = false;
      }

      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("travel");
      this.moveStrafing *= 0.98F;
      this.moveForward *= 0.98F;
      this.randomYawVelocity *= 0.9F;
      this.moveEntityWithHeading(this.moveStrafing, this.moveForward);
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("push");
      if (!this.worldObj.isRemote) {
         this.collideWithNearbyEntities();
      }

      this.worldObj.theProfiler.endSection();
   }

   protected void updateEntityActionState() {
   }

   protected void collideWithNearbyEntities() {
      List<Entity> var4 = this.worldObj
         .getEntitiesInAABBexcluding(this, this.getEntityBoundingBox().expand(0.2F, 0.0, 0.2F), Predicates.and(Tezzelator.d, Entity::canBePushed));
      if (!var4.isEmpty()) {
         for (Entity var6 : var4) {
            this.collideWithEntity(var6);
         }
      }
   }

   protected void collideWithEntity(Entity entity) {
      entity.applyEntityCollision(this);
   }

   @Override
   public void mountEntity(Entity entity) {
      if (this.ridingEntity != null && entity == null) {
         if (!this.worldObj.isRemote) {
            this.dismountEntity(this.ridingEntity);
         }

         if (this.ridingEntity != null) {
            this.ridingEntity.riddenByEntity = null;
         }

         this.ridingEntity = null;
      } else {
         super.mountEntity(entity);
      }
   }

   @Override
   public void updateRidden() {
      super.updateRidden();
      this.prevOnGroundSpeedFactor = this.onGroundSpeedFactor;
      this.onGroundSpeedFactor = 0.0F;
      this.fallDistance = 0.0F;
   }

   @Override
   public void setPositionAndRotation2(double double1, double double2, double double3, float float4, float float5, int integer, boolean boolean7) {
      this.newPosX = double1;
      this.newPosY = double2;
      this.newPosZ = double3;
      this.newRotationYaw = float4;
      this.newRotationPitch = float5;
      this.newPosRotationIncrements = integer;
   }

   public void setJumping(boolean boolean1) {
      this.isJumping = boolean1;
   }

   public void onItemPickup(Entity entity, int integer) {
      if (!entity.isDead && !this.worldObj.isRemote) {
         EntityTracker var6 = ((WorldServer)this.worldObj).getEntityTracker();
         if (entity instanceof EntityItem) {
            var6.sendToAllTrackingEntity(entity, new S0DPacketCollectItem(entity.getEntityId(), this.getEntityId()));
         }

         if (entity instanceof EntityArrow) {
            var6.sendToAllTrackingEntity(entity, new S0DPacketCollectItem(entity.getEntityId(), this.getEntityId()));
         }

         if (entity instanceof EntityXPOrb) {
            var6.sendToAllTrackingEntity(entity, new S0DPacketCollectItem(entity.getEntityId(), this.getEntityId()));
         }
      }
   }

   public boolean canEntityBeSeen(Entity entity) {
      return this.worldObj
            .rayTraceBlocks(
               new Vec3(this.posX, this.posY + this.getEyeHeight(), this.posZ), new Vec3(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ)
            )
         == null;
   }

   @Override
   public Vec3 getLookVec() {
      return this.getLook(1.0F);
   }

   @Override
   public Vec3 getLook(float float1) {
      if (float1 == 1.0F) {
         return this.getVectorForRotation(this.rotationPitch, this.rotationYawHead);
      }

      float var5 = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * float1;
      float var6 = this.prevRotationYawHead + (this.rotationYawHead - this.prevRotationYawHead) * float1;
      return this.getVectorForRotation(var5, var6);
   }

   public float getSwingProgress(float float1) {
      float var5 = this.swingProgress - this.prevSwingProgress;
      if (var5 < 0.0F) {
         var5++;
      }

      return this.prevSwingProgress + var5 * float1;
   }

   public boolean isServerWorld() {
      return !this.worldObj.isRemote;
   }

   @Override
   public boolean canBeCollidedWith() {
      return !this.isDead;
   }

   @Override
   public boolean canBePushed() {
      return !this.isDead;
   }

   @Override
   protected void setBeenAttacked() {
      this.velocityChanged = this.rand.nextDouble() >= this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue();
   }

   @Override
   public float getRotationYawHead() {
      return this.rotationYawHead;
   }

   @Override
   public void setRotationYawHead(float float1) {
      this.rotationYawHead = float1;
   }

   @Override
   public void setRenderYawOffset(float float1) {
      this.renderYawOffset = float1;
   }

   public float getAbsorptionAmount() {
      return this.absorptionAmount;
   }

   public void setAbsorptionAmount(float float1) {
      if (float1 < 0.0F) {
         float1 = 0.0F;
      }

      this.absorptionAmount = float1;
   }

   public Team getTeam() {
      return this.worldObj.getScoreboard().getPlayersTeam(this.getUniqueID().toString());
   }

   public boolean isOnSameTeam(EntityLivingBase entityLivingBase) {
      return this.isOnTeam(entityLivingBase.getTeam());
   }

   public boolean isOnTeam(Team team) {
      return this.getTeam() != null && this.getTeam().isSameTeam(team);
   }

   public void sendEnterCombat() {
   }

   public void sendEndCombat() {
   }

   protected void markPotionsDirty() {
      this.potionsNeedUpdate = true;
   }

   private void Method1031() {
      if (this.bA == null) {
         this.bA = Moon.getInstance().getModuleManager().getModule(Animations.class);
      }
   }

   public Map<Integer, PotionEffect> Method1032() {
      return this.activePotionsMap;
   }
}
