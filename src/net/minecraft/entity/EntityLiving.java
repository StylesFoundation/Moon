package net.minecraft.entity;

import java.util.Arrays;
import java.util.UUID;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.scoreboard.Team;
import net.minecraft.src.Config;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class EntityLiving extends EntityLivingBase {
   public int livingSoundTime;
   protected int experienceValue;
   private final EntityLookHelper lookHelper;
   protected EntityMoveHelper moveHelper;
   protected EntityJumpHelper jumpHelper;
   private final EntityBodyHelper bodyHelper;
   protected PathNavigate navigator;
   protected final EntityAITasks tasks;
   protected final EntityAITasks targetTasks;
   private EntityLivingBase attackTarget;
   private final EntitySenses senses;
   private final ItemStack[] equipment = new ItemStack[5];
   protected float[] equipmentDropChances = new float[5];
   private boolean canPickUpLoot;
   private boolean persistenceRequired;
   private boolean isLeashed;
   private Entity leashedToEntity;
   private NBTTagCompound leashNBTTag;
   private UUID teamUuid = null;
   private String teamUuidString = null;

   public EntityLiving(World world) {
      super(world);
      this.tasks = new EntityAITasks(world != null && world.theProfiler != null ? world.theProfiler : null);
      this.targetTasks = new EntityAITasks(world != null && world.theProfiler != null ? world.theProfiler : null);
      this.lookHelper = new EntityLookHelper(this);
      this.moveHelper = new EntityMoveHelper(this);
      this.jumpHelper = new EntityJumpHelper(this);
      this.bodyHelper = new EntityBodyHelper(this);
      this.navigator = this.getNewNavigator(world);
      this.senses = new EntitySenses(this);
      Arrays.fill(this.equipmentDropChances, 0.085F);
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0);
   }

   protected PathNavigate getNewNavigator(World world) {
      return new PathNavigateGround(this, world);
   }

   public EntityLookHelper getLookHelper() {
      return this.lookHelper;
   }

   public EntityMoveHelper getMoveHelper() {
      return this.moveHelper;
   }

   public EntityJumpHelper getJumpHelper() {
      return this.jumpHelper;
   }

   public PathNavigate getNavigator() {
      return this.navigator;
   }

   public EntitySenses getEntitySenses() {
      return this.senses;
   }

   public EntityLivingBase getAttackTarget() {
      return this.attackTarget;
   }

   public void setAttackTarget(EntityLivingBase entityLivingBase) {
      this.attackTarget = entityLivingBase;
   }

   public boolean canAttackClass(Class<? extends EntityLivingBase> class1) {
      return class1 != EntityGhast.class;
   }

   public void eatGrassBonus() {
   }

   @Override
   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(15, (byte)0);
   }

   public int getTalkInterval() {
      return 80;
   }

   public void playLivingSound() {
      String var3 = this.getLivingSound();
      if (var3 != null) {
         this.playSound(var3, this.getSoundVolume(), this.getSoundPitch());
      }
   }

   @Override
   public void onEntityUpdate() {
      super.onEntityUpdate();
      this.worldObj.theProfiler.startSection("mobBaseTick");
      if (this.isEntityAlive() && this.rand.nextInt(1000) < this.livingSoundTime++) {
         this.livingSoundTime = -this.getTalkInterval();
         this.playLivingSound();
      }

      this.worldObj.theProfiler.endSection();
   }

   @Override
   protected int getExperiencePoints(EntityPlayer entityPlayer) {
      if (this.experienceValue > 0) {
         int var5 = this.experienceValue;
         ItemStack[] var6 = this.getInventory();

         for (int var7 = 0; var7 < var6.length; var7++) {
            if (var6[var7] != null && this.equipmentDropChances[var7] <= 1.0F) {
               var5 += 1 + this.rand.nextInt(3);
            }
         }

         return var5;
      } else {
         return this.experienceValue;
      }
   }

   public void spawnExplosionParticle() {
      if (this.worldObj.isRemote) {
         for (int var4 = 0; var4 < 20; var4++) {
            double var5 = this.rand.nextGaussian() * 0.02;
            double var7 = this.rand.nextGaussian() * 0.02;
            double var9 = this.rand.nextGaussian() * 0.02;
            this.worldObj
               .spawnParticle(
                  EnumParticleTypes.EXPLOSION_NORMAL,
                  this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width - var5 * 10.0,
                  this.posY + this.rand.nextFloat() * this.height - var7 * 10.0,
                  this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width - var9 * 10.0,
                  var5,
                  var7,
                  var9
               );
         }
      } else {
         this.worldObj.setEntityState(this, (byte)20);
      }
   }

   @Override
   public void handleStatusUpdate(byte byte1) {
      if (byte1 == 20) {
         this.spawnExplosionParticle();
      } else {
         super.handleStatusUpdate(byte1);
      }
   }

   @Override
   public void onUpdate() {
      if (Config.isSmoothWorld() && this.canSkipUpdate()) {
         this.onUpdateMinimal();
      } else {
         super.onUpdate();
         if (!this.worldObj.isRemote) {
            this.updateLeashedState();
         }
      }
   }

   @Override
   protected float updateDistance(float float1, float float2) {
      this.bodyHelper.updateRenderAngles();
      return float2;
   }

   protected String getLivingSound() {
      return null;
   }

   protected Item getDropItem() {
      return null;
   }

   @Override
   protected void dropFewItems(boolean boolean1, int integer) {
      Item var6 = this.getDropItem();
      if (var6 != null) {
         int var7 = this.rand.nextInt(3);
         if (integer > 0) {
            var7 += this.rand.nextInt(integer + 1);
         }

         for (int var8 = 0; var8 < var7; var8++) {
            this.dropItem(var6, 1);
         }
      }
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      super.writeEntityToNBT(nBTTagCompound);
      nBTTagCompound.setBoolean("CanPickUpLoot", this.canPickUpLoot());
      nBTTagCompound.setBoolean("PersistenceRequired", this.persistenceRequired);
      NBTTagList var5 = new NBTTagList();

      for (ItemStack var9 : this.equipment) {
         NBTTagCompound var10 = new NBTTagCompound();
         if (var9 != null) {
            var9.writeToNBT(var10);
         }

         var5.appendTag(var10);
      }

      nBTTagCompound.setTag("Equipment", var5);
      NBTTagList var11 = new NBTTagList();

      for (float var17 : this.equipmentDropChances) {
         var11.appendTag(new NBTTagFloat(var17));
      }

      nBTTagCompound.setTag("DropChances", var11);
      nBTTagCompound.setBoolean("Leashed", this.isLeashed);
      if (this.leashedToEntity != null) {
         NBTTagCompound var13 = new NBTTagCompound();
         if (this.leashedToEntity instanceof EntityLivingBase) {
            var13.setLong("UUIDMost", this.leashedToEntity.getUniqueID().getMostSignificantBits());
            var13.setLong("UUIDLeast", this.leashedToEntity.getUniqueID().getLeastSignificantBits());
         } else if (this.leashedToEntity instanceof EntityHanging) {
            BlockPos var15 = ((EntityHanging)this.leashedToEntity).getHangingPosition();
            var13.setInteger("X", var15.getX());
            var13.setInteger("Y", var15.getY());
            var13.setInteger("Z", var15.getZ());
         }

         nBTTagCompound.setTag("Leash", var13);
      }

      if (this.isAIDisabled()) {
         nBTTagCompound.setBoolean("NoAI", this.isAIDisabled());
      }
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      super.readEntityFromNBT(nBTTagCompound);
      if (nBTTagCompound.hasKey("CanPickUpLoot", 1)) {
         this.setCanPickUpLoot(nBTTagCompound.getBoolean("CanPickUpLoot"));
      }

      this.persistenceRequired = nBTTagCompound.getBoolean("PersistenceRequired");
      if (nBTTagCompound.hasKey("Equipment", 9)) {
         NBTTagList var5 = nBTTagCompound.getTagList("Equipment", 10);

         for (int var6 = 0; var6 < this.equipment.length; var6++) {
            this.equipment[var6] = ItemStack.loadItemStackFromNBT(var5.getCompoundTagAt(var6));
         }
      }

      if (nBTTagCompound.hasKey("DropChances", 9)) {
         NBTTagList var7 = nBTTagCompound.getTagList("DropChances", 5);

         for (int var8 = 0; var8 < var7.tagCount(); var8++) {
            this.equipmentDropChances[var8] = var7.getFloatAt(var8);
         }
      }

      this.isLeashed = nBTTagCompound.getBoolean("Leashed");
      if (this.isLeashed && nBTTagCompound.hasKey("Leash", 10)) {
         this.leashNBTTag = nBTTagCompound.getCompoundTag("Leash");
      }

      this.setNoAI(nBTTagCompound.getBoolean("NoAI"));
   }

   public void setMoveForward(float float1) {
      this.moveForward = float1;
   }

   @Override
   public void setAIMoveSpeed(float float1) {
      super.setAIMoveSpeed(float1);
      this.setMoveForward(float1);
   }

   @Override
   public void onLivingUpdate() {
      super.onLivingUpdate();
      this.worldObj.theProfiler.startSection("looting");
      if (!this.worldObj.isRemote && this.canPickUpLoot() && !this.dead && this.worldObj.getGameRules().getBoolean("mobGriefing")) {
         for (EntityItem var5 : this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.getEntityBoundingBox().expand(1.0, 0.0, 1.0))) {
            if (!var5.isDead && var5.Method464() != null && !var5.cannotPickup()) {
               this.updateEquipmentIfNeeded(var5);
            }
         }
      }

      this.worldObj.theProfiler.endSection();
   }

   protected void updateEquipmentIfNeeded(EntityItem entityItem) {
      ItemStack var5 = entityItem.Method464();
      int var6 = getArmorPosition(var5);
      if (var6 > -1) {
         boolean var7 = true;
         ItemStack var8 = this.getEquipmentInSlot(var6);
         if (var8 != null) {
            if (var6 == 0) {
               if (var5.getItem() instanceof ItemSword && !(var8.getItem() instanceof ItemSword)) {
                  var7 = true;
               } else if (var5.getItem() instanceof ItemSword var16 && var8.getItem() instanceof ItemSword var12) {
                  if (var16.getDamageVsEntity() != var12.getDamageVsEntity()) {
                     var7 = var16.getDamageVsEntity() > var12.getDamageVsEntity();
                  } else {
                     var7 = var5.getMetadata() > var8.getMetadata() || var5.hasTagCompound() && !var8.hasTagCompound();
                  }
               } else if (var5.getItem() instanceof ItemBow && var8.getItem() instanceof ItemBow) {
                  var7 = var5.hasTagCompound() && !var8.hasTagCompound();
               } else {
                  var7 = false;
               }
            } else if (var5.getItem() instanceof ItemArmor && !(var8.getItem() instanceof ItemArmor)) {
               var7 = true;
            } else if (!(var5.getItem() instanceof ItemArmor var9 && var8.getItem() instanceof ItemArmor var10)) {
               var7 = false;
            } else if (var9.damageReduceAmount != var10.damageReduceAmount) {
               var7 = var9.damageReduceAmount > var10.damageReduceAmount;
            } else {
               var7 = var5.getMetadata() > var8.getMetadata() || var5.hasTagCompound() && !var8.hasTagCompound();
            }
         }

         if (var7 && this.func_175448_a(var5)) {
            if (var8 != null && this.rand.nextFloat() - 0.1F < this.equipmentDropChances[var6]) {
               this.entityDropItem(var8, 0.0F);
            }

            if (var5.getItem() == Items.diamond && entityItem.getThrower() != null) {
               EntityPlayer var14 = this.worldObj.getPlayerEntityByName(entityItem.getThrower());
               if (var14 != null) {
                  var14.triggerAchievement(AchievementList.diamondsToYou);
               }
            }

            this.setCurrentItemOrArmor(var6, var5);
            this.equipmentDropChances[var6] = 2.0F;
            this.persistenceRequired = true;
            this.onItemPickup(entityItem, 1);
            entityItem.setDead();
         }
      }
   }

   protected boolean func_175448_a(ItemStack itemStack) {
      return true;
   }

   protected boolean canDespawn() {
      return true;
   }

   protected void despawnEntity() {
      if (this.persistenceRequired) {
         this.entityAge = 0;
      } else {
         EntityPlayer var4 = this.worldObj.getClosestPlayerToEntity(this, -1.0);
         if (var4 != null) {
            double var5 = var4.posX - this.posX;
            double var7 = var4.posY - this.posY;
            double var9 = var4.posZ - this.posZ;
            double var11 = var5 * var5 + var7 * var7 + var9 * var9;
            if (this.canDespawn() && var11 > 16384.0) {
               this.setDead();
            }

            if (this.entityAge > 600 && this.rand.nextInt(800) == 0 && var11 > 1024.0 && this.canDespawn()) {
               this.setDead();
            } else if (var11 < 1024.0) {
               this.entityAge = 0;
            }
         }
      }
   }

   @Override
   protected final void updateEntityActionState() {
      this.entityAge++;
      this.worldObj.theProfiler.startSection("checkDespawn");
      this.despawnEntity();
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("sensing");
      this.senses.clearSensingCache();
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("targetSelector");
      this.targetTasks.onUpdateTasks();
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("goalSelector");
      this.tasks.onUpdateTasks();
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("navigation");
      this.navigator.onUpdateNavigation();
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("mob tick");
      this.updateAITasks();
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.startSection("controls");
      this.worldObj.theProfiler.startSection("move");
      this.moveHelper.onUpdateMoveHelper();
      this.worldObj.theProfiler.endStartSection("look");
      this.lookHelper.onUpdateLook();
      this.worldObj.theProfiler.endStartSection("jump");
      this.jumpHelper.doJump();
      this.worldObj.theProfiler.endSection();
      this.worldObj.theProfiler.endSection();
   }

   protected void updateAITasks() {
   }

   public int getVerticalFaceSpeed() {
      return 40;
   }

   public void faceEntity(Entity entity, float float2, float float3) {
      double var7 = entity.posX - this.posX;
      double var9 = entity.posZ - this.posZ;
      double var11;
      if (entity instanceof EntityLivingBase var13) {
         var11 = var13.posY + var13.getEyeHeight() - (this.posY + this.getEyeHeight());
      } else {
         var11 = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (this.posY + this.getEyeHeight());
      }

      double var17 = MathHelper.sqrt_double(var7 * var7 + var9 * var9);
      float var15 = (float)(MathHelper.atan2(var9, var7) * 180.0 / Math.PI) - 90.0F;
      float var16 = (float)(-(MathHelper.atan2(var11, var17) * 180.0 / Math.PI));
      this.rotationPitch = this.updateRotation(this.rotationPitch, var16, float3);
      this.rotationYaw = this.updateRotation(this.rotationYaw, var15, float2);
   }

   private float updateRotation(float float1, float float2, float float3) {
      float var7 = MathHelper.wrapAngleTo180_float(float2 - float1);
      if (var7 > float3) {
         var7 = float3;
      }

      if (var7 < -float3) {
         var7 = -float3;
      }

      return float1 + var7;
   }

   public boolean getCanSpawnHere() {
      return true;
   }

   public boolean isNotColliding() {
      return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this)
         && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty()
         && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox());
   }

   public float getRenderSizeModifier() {
      return 1.0F;
   }

   public int getMaxSpawnedInChunk() {
      return 4;
   }

   @Override
   public int getMaxFallHeight() {
      if (this.getAttackTarget() == null) {
         return 3;
      }

      int var4 = (int)(this.getHealth() - this.getMaxHealth() * 0.33F) - (3 - this.worldObj.getDifficulty().getDifficultyId()) * 4;
      if (var4 < 0) {
         var4 = 0;
      }

      return var4 + 3;
   }

   @Override
   public ItemStack getHeldItem() {
      return this.equipment[0];
   }

   @Override
   public ItemStack getEquipmentInSlot(int integer) {
      return this.equipment[integer];
   }

   @Override
   public ItemStack getCurrentArmor(int integer) {
      return this.equipment[integer + 1];
   }

   @Override
   public void setCurrentItemOrArmor(int integer, ItemStack itemStack) {
      this.equipment[integer] = itemStack;
   }

   @Override
   public ItemStack[] getInventory() {
      return this.equipment;
   }

   @Override
   protected void dropEquipment(boolean boolean1, int integer) {
      for (int var6 = 0; var6 < this.getInventory().length; var6++) {
         ItemStack var7 = this.getEquipmentInSlot(var6);
         boolean var8 = this.equipmentDropChances[var6] > 1.0F;
         if (var7 != null && (boolean1 || var8) && this.rand.nextFloat() - integer * 0.01F < this.equipmentDropChances[var6]) {
            if (!var8 && var7.isItemStackDamageable()) {
               int var9 = Math.max(var7.getMaxDamage() - 25, 1);
               int var10 = var7.getMaxDamage() - this.rand.nextInt(this.rand.nextInt(var9) + 1);
               if (var10 > var9) {
                  var10 = var9;
               }

               if (var10 < 1) {
                  var10 = 1;
               }

               var7.setItemDamage(var10);
            }

            this.entityDropItem(var7, 0.0F);
         }
      }
   }

   protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficultyInstance) {
      if (this.rand.nextFloat() < 0.15F * difficultyInstance.getClampedAdditionalDifficulty()) {
         int var5 = this.rand.nextInt(2);
         float var6 = this.worldObj.getDifficulty() == EnumDifficulty.HARD ? 0.1F : 0.25F;
         if (this.rand.nextFloat() < 0.095F) {
            var5++;
         }

         if (this.rand.nextFloat() < 0.095F) {
            var5++;
         }

         if (this.rand.nextFloat() < 0.095F) {
            var5++;
         }

         for (int var7 = 3; var7 >= 0; var7--) {
            ItemStack var8 = this.getCurrentArmor(var7);
            if (var7 < 3 && this.rand.nextFloat() < var6) {
               break;
            }

            if (var8 == null) {
               Item var9 = getArmorItemForSlot(var7 + 1, var5);
               if (var9 != null) {
                  this.setCurrentItemOrArmor(var7 + 1, new ItemStack(var9));
               }
            }
         }
      }
   }

   public static int getArmorPosition(ItemStack itemStack) {
      if (itemStack.getItem() == Item.getItemFromBlock(Blocks.pumpkin) || itemStack.getItem() == Items.skull) {
         return 4;
      }

      if (itemStack.getItem() instanceof ItemArmor) {
         switch (((ItemArmor)itemStack.getItem()).armorType) {
            case 0:
               return 4;
            case 1:
               return 3;
            case 2:
               return 2;
            case 3:
               return 1;
         }
      }

      return 0;
   }

   public static Item getArmorItemForSlot(int integer1, int integer2) {
      switch (integer1) {
         case 4:
            if (integer2 == 0) {
               return Items.leather_helmet;
            } else if (integer2 == 1) {
               return Items.golden_helmet;
            } else if (integer2 == 2) {
               return Items.chainmail_helmet;
            } else if (integer2 == 3) {
               return Items.iron_helmet;
            } else if (integer2 == 4) {
               return Items.diamond_helmet;
            }
         case 3:
            if (integer2 == 0) {
               return Items.leather_chestplate;
            } else if (integer2 == 1) {
               return Items.golden_chestplate;
            } else if (integer2 == 2) {
               return Items.chainmail_chestplate;
            } else if (integer2 == 3) {
               return Items.iron_chestplate;
            } else if (integer2 == 4) {
               return Items.diamond_chestplate;
            }
         case 2:
            if (integer2 == 0) {
               return Items.leather_leggings;
            } else if (integer2 == 1) {
               return Items.golden_leggings;
            } else if (integer2 == 2) {
               return Items.chainmail_leggings;
            } else if (integer2 == 3) {
               return Items.iron_leggings;
            } else if (integer2 == 4) {
               return Items.diamond_leggings;
            }
         case 1:
            if (integer2 == 0) {
               return Items.leather_boots;
            } else if (integer2 == 1) {
               return Items.golden_boots;
            } else if (integer2 == 2) {
               return Items.chainmail_boots;
            } else if (integer2 == 3) {
               return Items.iron_boots;
            } else if (integer2 == 4) {
               return Items.diamond_boots;
            }
         default:
            return null;
      }
   }

   protected void setEnchantmentBasedOnDifficulty(DifficultyInstance difficultyInstance) {
      float var5 = difficultyInstance.getClampedAdditionalDifficulty();
      if (this.getHeldItem() != null && this.rand.nextFloat() < 0.25F * var5) {
         EnchantmentHelper.addRandomEnchantment(this.rand, this.getHeldItem(), (int)(5.0F + var5 * this.rand.nextInt(18)));
      }

      for (int var6 = 0; var6 < 4; var6++) {
         ItemStack var7 = this.getCurrentArmor(var6);
         if (var7 != null && this.rand.nextFloat() < 0.5F * var5) {
            EnchantmentHelper.addRandomEnchantment(this.rand, var7, (int)(5.0F + var5 * this.rand.nextInt(18)));
         }
      }
   }

   public IEntityLivingData onInitialSpawn(DifficultyInstance difficultyInstance, IEntityLivingData iEntityLivingData) {
      this.getEntityAttribute(SharedMonsterAttributes.followRange)
         .applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextGaussian() * 0.05, 1));
      return iEntityLivingData;
   }

   public boolean canBeSteered() {
      return false;
   }

   public void enablePersistence() {
      this.persistenceRequired = true;
   }

   public void setEquipmentDropChance(int integer, float float2) {
      this.equipmentDropChances[integer] = float2;
   }

   public boolean canPickUpLoot() {
      return this.canPickUpLoot;
   }

   public void setCanPickUpLoot(boolean boolean1) {
      this.canPickUpLoot = boolean1;
   }

   public boolean isNoDespawnRequired() {
      return this.persistenceRequired;
   }

   @Override
   public final boolean interactFirst(EntityPlayer entityPlayer) {
      if (this.getLeashed() && this.getLeashedToEntity() == entityPlayer) {
         this.clearLeashed(true, !entityPlayer.capabilities.isCreativeMode);
         return true;
      }

      ItemStack var5 = entityPlayer.inventory.getCurrentItem();
      if (var5 != null && var5.getItem() == Items.lead && this.allowLeashing()) {
         if (!(this instanceof EntityTameable) || !((EntityTameable)this).isTamed()) {
            this.setLeashedToEntity(entityPlayer, true);
            var5.stackSize--;
            return true;
         }

         if (((EntityTameable)this).isOwner(entityPlayer)) {
            this.setLeashedToEntity(entityPlayer, true);
            var5.stackSize--;
            return true;
         }
      }

      return this.interact(entityPlayer) ? true : super.interactFirst(entityPlayer);
   }

   protected boolean interact(EntityPlayer entityPlayer) {
      return false;
   }

   protected void updateLeashedState() {
      if (this.leashNBTTag != null) {
         this.recreateLeash();
      }

      if (this.isLeashed) {
         if (!this.isEntityAlive()) {
            this.clearLeashed(true, true);
         }

         if (this.leashedToEntity == null || this.leashedToEntity.isDead) {
            this.clearLeashed(true, true);
         }
      }
   }

   public void clearLeashed(boolean boolean1, boolean boolean2) {
      if (this.isLeashed) {
         this.isLeashed = false;
         this.leashedToEntity = null;
         if (!this.worldObj.isRemote && boolean2) {
            this.dropItem(Items.lead, 1);
         }

         if (!this.worldObj.isRemote && boolean1 && this.worldObj instanceof WorldServer) {
            ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, new S1BPacketEntityAttach(1, this, null));
         }
      }
   }

   public boolean allowLeashing() {
      return !this.getLeashed() && !(this instanceof IMob);
   }

   public boolean getLeashed() {
      return this.isLeashed;
   }

   public Entity getLeashedToEntity() {
      return this.leashedToEntity;
   }

   public void setLeashedToEntity(Entity entity, boolean boolean2) {
      this.isLeashed = true;
      this.leashedToEntity = entity;
      if (!this.worldObj.isRemote && boolean2 && this.worldObj instanceof WorldServer) {
         ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, new S1BPacketEntityAttach(1, this, this.leashedToEntity));
      }
   }

   private void recreateLeash() {
      if (this.isLeashed && this.leashNBTTag != null) {
         if (this.leashNBTTag.hasKey("UUIDMost", 4) && this.leashNBTTag.hasKey("UUIDLeast", 4)) {
            UUID var7 = new UUID(this.leashNBTTag.getLong("UUIDMost"), this.leashNBTTag.getLong("UUIDLeast"));

            for (EntityLivingBase var6 : this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(10.0, 10.0, 10.0))) {
               if (var6.getUniqueID().equals(var7)) {
                  this.leashedToEntity = var6;
                  break;
               }
            }
         } else if (this.leashNBTTag.hasKey("X", 99) && this.leashNBTTag.hasKey("Y", 99) && this.leashNBTTag.hasKey("Z", 99)) {
            BlockPos var4 = new BlockPos(this.leashNBTTag.getInteger("X"), this.leashNBTTag.getInteger("Y"), this.leashNBTTag.getInteger("Z"));
            EntityLeashKnot var5 = EntityLeashKnot.getKnotForPosition(this.worldObj, var4);
            if (var5 == null) {
               var5 = EntityLeashKnot.createKnot(this.worldObj, var4);
            }

            this.leashedToEntity = var5;
         } else {
            this.clearLeashed(false, true);
         }
      }

      this.leashNBTTag = null;
   }

   @Override
   public boolean replaceItemInInventory(int integer, ItemStack itemStack) {
      int var6;
      if (integer == 99) {
         var6 = 0;
      } else {
         var6 = integer - 100 + 1;
         if (var6 < 0 || var6 >= this.equipment.length) {
            return false;
         }
      }

      if (itemStack != null && getArmorPosition(itemStack) != var6 && (var6 != 4 || !(itemStack.getItem() instanceof ItemBlock))) {
         return false;
      }

      this.setCurrentItemOrArmor(var6, itemStack);
      return true;
   }

   @Override
   public boolean isServerWorld() {
      return super.isServerWorld() && !this.isAIDisabled();
   }

   public void setNoAI(boolean boolean1) {
      this.dataWatcher.updateObject(15, (byte)(boolean1 ? 1 : 0));
   }

   public boolean isAIDisabled() {
      return this.dataWatcher.getWatchableObjectByte(15) != 0;
   }

   private boolean canSkipUpdate() {
      if (this.isChild()) {
         return false;
      }

      if (this.hurtTime > 0) {
         return false;
      }

      if (this.ticksExisted < 20) {
         return false;
      }

      World var4 = this.getEntityWorld();
      if (var4 == null) {
         return false;
      }

      if (var4.playerEntities.size() != 1) {
         return false;
      }

      Entity var5 = var4.playerEntities.get(0);
      double var6 = Math.max(Math.abs(this.posX - var5.posX) - 16.0, 0.0);
      double var8 = Math.max(Math.abs(this.posZ - var5.posZ) - 16.0, 0.0);
      double var10 = var6 * var6 + var8 * var8;
      return !this.isInRangeToRenderDist(var10);
   }

   private void onUpdateMinimal() {
      this.entityAge++;
      if (this instanceof EntityMob) {
         float var4 = this.getBrightness(1.0F);
         if (var4 > 0.5F) {
            this.entityAge += 2;
         }
      }

      this.despawnEntity();
   }

   @Override
   public Team getTeam() {
      UUID var4 = this.getUniqueID();
      if (this.teamUuid != var4) {
         this.teamUuid = var4;
         this.teamUuidString = var4.toString();
      }

      return this.worldObj.getScoreboard().getPlayersTeam(this.teamUuidString);
   }

   public enum SpawnPlacementType {
      ON_GROUND,
      IN_AIR,
      IN_WATER;

      private static EntityLiving.SpawnPlacementType[] Method399() {
         return new EntityLiving.SpawnPlacementType[]{ON_GROUND, IN_AIR, IN_WATER};
      }
   }
}
