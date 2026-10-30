package net.minecraft.entity.player;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.moon.Moon;
import com.moon.events.impl.player.SlowdownEvent;
import com.moon.util.game.SlotUtil;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.event.ClickEvent;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.FoodStats;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.LockCode;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;

public abstract class EntityPlayer extends EntityLivingBase {
   public final SlotUtil bC = new SlotUtil();
   public InventoryPlayer inventory = new InventoryPlayer(this);
   private InventoryEnderChest theInventoryEnderChest = new InventoryEnderChest();
   public Container inventoryContainer;
   public Container openContainer;
   protected FoodStats foodStats = new FoodStats();
   protected int flyToggleTimer;
   public float prevCameraYaw;
   public float cameraYaw;
   public int xpCooldown;
   public double prevChasingPosX;
   public double prevChasingPosY;
   public double prevChasingPosZ;
   public double chasingPosX;
   public double chasingPosY;
   public double chasingPosZ;
   protected boolean sleeping;
   public BlockPos playerLocation;
   private int sleepTimer;
   public float renderOffsetX;
   public float renderOffsetY;
   public float renderOffsetZ;
   private BlockPos spawnChunk;
   private boolean spawnForced;
   private BlockPos startMinecartRidingCoordinate;
   public PlayerCapabilities capabilities = new PlayerCapabilities();
   public int experienceLevel;
   public int experienceTotal;
   public float experience;
   private int xpSeed;
   private ItemStack itemInUse;
   private int itemInUseCount;
   protected float speedOnGround = 0.1F;
   protected float speedInAir = 0.02F;
   private int lastXPSound;
   private final GameProfile gameProfile;
   private boolean hasReducedDebug = false;
   public EntityFishHook fishEntity;

   public EntityPlayer(World world, GameProfile gameProfile) {
      super(world);
      this.entityUniqueID = getUUID(gameProfile);
      this.gameProfile = gameProfile;
      this.inventoryContainer = new ContainerPlayer(this.inventory, !world.isRemote, this);
      this.openContainer = this.inventoryContainer;
      BlockPos var5 = world.getSpawnPoint();
      this.setLocationAndAngles(var5.getX() + 0.5, var5.getY() + 1, var5.getZ() + 0.5, 0.0F, 0.0F);
      this.unused180 = 180.0F;
      this.fireResistance = 20;
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.1F);
   }

   @Override
   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(16, (byte)0);
      this.dataWatcher.addObject(17, 0.0F);
      this.dataWatcher.addObject(18, 0);
      this.dataWatcher.addObject(10, (byte)0);
   }

   public ItemStack getItemInUse() {
      return this.itemInUse;
   }

   public int getItemInUseCount() {
      return this.itemInUseCount;
   }

   public boolean isUsingItem() {
      return this.itemInUse != null;
   }

   public int getItemInUseDuration() {
      return this.isUsingItem() ? this.itemInUse.getMaxItemUseDuration() - this.itemInUseCount : 0;
   }

   public void stopUsingItem() {
      if (this.itemInUse != null) {
         this.itemInUse.onPlayerStoppedUsing(this.worldObj, this, this.itemInUseCount);
      }

      this.clearItemInUse();
   }

   public void clearItemInUse() {
      this.itemInUse = null;
      this.itemInUseCount = 0;
      if (!this.worldObj.isRemote) {
         this.setEating(false);
      }
   }

   public boolean isBlocking() {
      return this.isUsingItem() && this.itemInUse.getItem().getItemUseAction(this.itemInUse) == EnumAction.BLOCK;
   }

   @Override
   public void onUpdate() {
      this.noClip = this.isSpectator();
      if (this.isSpectator()) {
         this.onGround = false;
      }

      if (this.itemInUse != null) {
         ItemStack var4 = this.inventory.getCurrentItem();
         if (var4 == this.itemInUse) {
            if (this.itemInUseCount <= 25 && this.itemInUseCount % 4 == 0) {
               this.updateItemUse(var4, 5);
            }

            if (--this.itemInUseCount == 0 && !this.worldObj.isRemote) {
               this.onItemUseFinish();
            }
         } else {
            this.clearItemInUse();
         }
      }

      if (this.xpCooldown > 0) {
         this.xpCooldown--;
      }

      if (this.isPlayerSleeping()) {
         this.sleepTimer++;
         if (this.sleepTimer > 100) {
            this.sleepTimer = 100;
         }

         if (!this.worldObj.isRemote) {
            if (!this.isInBed()) {
               this.wakeUpPlayer(true, true, false);
            } else if (this.worldObj.isDaytime()) {
               this.wakeUpPlayer(false, true, true);
            }
         }
      } else if (this.sleepTimer > 0) {
         this.sleepTimer++;
         if (this.sleepTimer >= 110) {
            this.sleepTimer = 0;
         }
      }

      super.onUpdate();
      if (!this.worldObj.isRemote && this.openContainer != null && !this.openContainer.canInteractWith(this)) {
         this.closeScreen();
         this.openContainer = this.inventoryContainer;
      }

      if (this.isBurning() && this.capabilities.disableDamage) {
         this.extinguish();
      }

      this.prevChasingPosX = this.chasingPosX;
      this.prevChasingPosY = this.chasingPosY;
      this.prevChasingPosZ = this.chasingPosZ;
      double var17 = this.posX - this.chasingPosX;
      double var6 = this.posY - this.chasingPosY;
      double var8 = this.posZ - this.chasingPosZ;
      if (var17 > 10.0) {
         this.prevChasingPosX = this.chasingPosX = this.posX;
      }

      if (var8 > 10.0) {
         this.prevChasingPosZ = this.chasingPosZ = this.posZ;
      }

      if (var6 > 10.0) {
         this.prevChasingPosY = this.chasingPosY = this.posY;
      }

      if (var17 < -10.0) {
         this.prevChasingPosX = this.chasingPosX = this.posX;
      }

      if (var8 < -10.0) {
         this.prevChasingPosZ = this.chasingPosZ = this.posZ;
      }

      if (var6 < -10.0) {
         this.prevChasingPosY = this.chasingPosY = this.posY;
      }

      this.chasingPosX += var17 * 0.25;
      this.chasingPosZ += var8 * 0.25;
      this.chasingPosY += var6 * 0.25;
      if (this.ridingEntity == null) {
         this.startMinecartRidingCoordinate = null;
      }

      if (!this.worldObj.isRemote) {
         this.foodStats.onUpdate(this);
         this.triggerAchievement(StatList.minutesPlayedStat);
         if (this.isEntityAlive()) {
            this.triggerAchievement(StatList.timeSinceDeathStat);
         }
      }

      double var13 = MathHelper.clamp_double(this.posX, -2.9999999E7, 2.9999999E7);
      double var15 = MathHelper.clamp_double(this.posZ, -2.9999999E7, 2.9999999E7);
      if (var13 != this.posX || var15 != this.posZ) {
         this.setPosition(var13, this.posY, var15);
      }
   }

   @Override
   public int getMaxInPortalTime() {
      return this.capabilities.disableDamage ? 0 : 80;
   }

   @Override
   protected String getSwimSound() {
      return "game.player.swim";
   }

   @Override
   protected String getSplashSound() {
      return "game.player.swim.splash";
   }

   @Override
   public int getPortalCooldown() {
      return 10;
   }

   @Override
   public void playSound(String string, float float2, float float3) {
      this.worldObj.playSoundToNearExcept(this, string, float2, float3);
   }

   protected void updateItemUse(ItemStack itemStack, int integer) {
      if (itemStack.getItemUseAction() == EnumAction.DRINK) {
         this.playSound("random.drink", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
      }

      if (itemStack.getItemUseAction() == EnumAction.EAT) {
         for (int var6 = 0; var6 < integer; var6++) {
            Vec3 var7 = new Vec3((this.rand.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0)
               .rotatePitch(-this.rotationPitch * (float) Math.PI / 180.0F)
               .rotateYaw(-this.rotationYaw * (float) Math.PI / 180.0F);
            double var8 = -this.rand.nextFloat() * 0.6 - 0.3;
            Vec3 var10 = new Vec3((this.rand.nextFloat() - 0.5) * 0.3, var8, 0.6)
               .rotatePitch(-this.rotationPitch * (float) Math.PI / 180.0F)
               .rotateYaw(-this.rotationYaw * (float) Math.PI / 180.0F)
               .addVector(this.posX, this.posY + this.getEyeHeight(), this.posZ);
            if (itemStack.getHasSubtypes()) {
               this.worldObj
                  .spawnParticle(
                     EnumParticleTypes.ITEM_CRACK,
                     var10.xCoord,
                     var10.yCoord,
                     var10.zCoord,
                     var7.xCoord,
                     var7.yCoord + 0.05,
                     var7.zCoord,
                     Item.getIdFromItem(itemStack.getItem()),
                     itemStack.getMetadata()
                  );
            } else {
               this.worldObj
                  .spawnParticle(
                     EnumParticleTypes.ITEM_CRACK,
                     var10.xCoord,
                     var10.yCoord,
                     var10.zCoord,
                     var7.xCoord,
                     var7.yCoord + 0.05,
                     var7.zCoord,
                     Item.getIdFromItem(itemStack.getItem())
                  );
            }
         }

         this.playSound("random.eat", 0.5F + 0.5F * this.rand.nextInt(2), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
      }
   }

   protected void onItemUseFinish() {
      if (this.itemInUse != null) {
         this.updateItemUse(this.itemInUse, 16);
         int var4 = this.itemInUse.stackSize;
         ItemStack var5 = this.itemInUse.onItemUseFinish(this.worldObj, this);
         if (var5 != this.itemInUse || var5 != null && var5.stackSize != var4) {
            this.inventory.mainInventory[this.inventory.currentItem] = var5;
            if (var5.stackSize == 0) {
               this.inventory.mainInventory[this.inventory.currentItem] = null;
            }
         }

         this.clearItemInUse();
      }
   }

   @Override
   public void handleStatusUpdate(byte byte1) {
      if (byte1 == 9) {
         this.onItemUseFinish();
      } else if (byte1 == 23) {
         this.hasReducedDebug = false;
      } else if (byte1 == 22) {
         this.hasReducedDebug = true;
      } else {
         super.handleStatusUpdate(byte1);
      }
   }

   @Override
   protected boolean isMovementBlocked() {
      return this.getHealth() <= 0.0F || this.isPlayerSleeping();
   }

   protected void closeScreen() {
      this.openContainer = this.inventoryContainer;
   }

   @Override
   public void updateRidden() {
      if (!this.worldObj.isRemote && this.isSneaking()) {
         this.mountEntity(null);
         this.setSneaking(false);
      } else {
         double var4 = this.posX;
         double var6 = this.posY;
         double var8 = this.posZ;
         float var10 = this.rotationYaw;
         float var11 = this.rotationPitch;
         super.updateRidden();
         this.prevCameraYaw = this.cameraYaw;
         this.cameraYaw = 0.0F;
         this.addMountedMovementStat(this.posX - var4, this.posY - var6, this.posZ - var8);
         if (this.ridingEntity instanceof EntityPig) {
            this.rotationPitch = var11;
            this.rotationYaw = var10;
            this.renderYawOffset = ((EntityPig)this.ridingEntity).renderYawOffset;
         }
      }
   }

   @Override
   public void preparePlayerToSpawn() {
      this.setSize(0.6F, 1.8F);
      super.preparePlayerToSpawn();
      this.setHealth(this.getMaxHealth());
      this.deathTime = 0;
   }

   @Override
   protected void updateEntityActionState() {
      super.updateEntityActionState();
      this.updateArmSwingProgress();
      this.rotationYawHead = this.rotationYaw;
   }

   @Override
   public void onLivingUpdate() {
      if (this.flyToggleTimer > 0) {
         this.flyToggleTimer--;
      }

      if (this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL && this.worldObj.getGameRules().getBoolean("naturalRegeneration")) {
         if (this.getHealth() < this.getMaxHealth() && this.ticksExisted % 20 == 0) {
            this.heal(1.0F);
         }

         if (this.foodStats.needFood() && this.ticksExisted % 10 == 0) {
            this.foodStats.setFoodLevel(this.foodStats.getFoodLevel() + 1);
         }
      }

      this.inventory.decrementAnimations();
      this.prevCameraYaw = this.cameraYaw;
      super.onLivingUpdate();
      IAttributeInstance var4 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
      if (!this.worldObj.isRemote) {
         var4.setBaseValue(this.capabilities.getWalkSpeed());
      }

      this.jumpMovementFactor = this.speedInAir;
      if (this.isSprinting()) {
         this.jumpMovementFactor = (float)(this.jumpMovementFactor + this.speedInAir * 0.3);
      }

      this.setAIMoveSpeed((float)var4.getAttributeValue());
      float var5 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
      float var6 = (float)(Math.atan(-this.motionY * 0.2F) * 15.0);
      if (var5 > 0.1F) {
         var5 = 0.1F;
      }

      if (!this.onGround || this.getHealth() <= 0.0F) {
         var5 = 0.0F;
      }

      if (this.onGround || this.getHealth() <= 0.0F) {
         var6 = 0.0F;
      }

      this.cameraYaw = this.cameraYaw + (var5 - this.cameraYaw) * 0.4F;
      this.cameraPitch = this.cameraPitch + (var6 - this.cameraPitch) * 0.8F;
      if (this.getHealth() > 0.0F && !this.isSpectator()) {
         AxisAlignedBB var7;
         if (this.ridingEntity != null && !this.ridingEntity.isDead) {
            var7 = this.getEntityBoundingBox().union(this.ridingEntity.getEntityBoundingBox()).expand(1.0, 0.0, 1.0);
         } else {
            var7 = this.getEntityBoundingBox().expand(1.0, 0.5, 1.0);
         }

         for (Entity var10 : this.worldObj.getEntitiesWithinAABBExcludingEntity(this, var7)) {
            if (!var10.isDead) {
               this.collideWithPlayer(var10);
            }
         }
      }
   }

   private void collideWithPlayer(Entity entity) {
      entity.onCollideWithPlayer(this);
   }

   public int getScore() {
      return this.dataWatcher.getWatchableObjectInt(18);
   }

   public void setScore(int integer) {
      this.dataWatcher.updateObject(18, integer);
   }

   public void addScore(int integer) {
      int var4 = this.getScore();
      this.dataWatcher.updateObject(18, var4 + integer);
   }

   @Override
   public void onDeath(DamageSource damageSource) {
      super.onDeath(damageSource);
      this.setSize(0.2F, 0.2F);
      this.setPosition(this.posX, this.posY, this.posZ);
      this.motionY = 0.1F;
      if (this.getName().equals("Notch")) {
         this.dropItem(new ItemStack(Items.apple, 1), true, false);
      }

      if (!this.worldObj.getGameRules().getBoolean("keepInventory")) {
         this.inventory.dropAllItems();
      }

      if (damageSource != null) {
         this.motionX = -MathHelper.cos((this.attackedAtYaw + this.rotationYaw) * (float) Math.PI / 180.0F) * 0.1F;
         this.motionZ = -MathHelper.sin((this.attackedAtYaw + this.rotationYaw) * (float) Math.PI / 180.0F) * 0.1F;
      } else {
         this.motionX = this.motionZ = 0.0;
      }

      this.triggerAchievement(StatList.deathsStat);
      this.func_175145_a(StatList.timeSinceDeathStat);
   }

   @Override
   protected String getHurtSound() {
      return "game.player.hurt";
   }

   @Override
   protected String getDeathSound() {
      return "game.player.die";
   }

   @Override
   public void addToPlayerScore(Entity entity, int integer) {
      this.addScore(integer);
      Collection<ScoreObjective> var6 = this.getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.totalKillCount);
      if (entity instanceof EntityPlayer) {
         this.triggerAchievement(StatList.playerKillsStat);
         var6.addAll(this.getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.playerKillCount));
         var6.addAll(this.func_175137_e(entity));
      } else {
         this.triggerAchievement(StatList.mobKillsStat);
      }

      for (ScoreObjective var8 : var6) {
         this.getWorldScoreboard().Method281(this.getName(), var8).func_96648_a();
      }
   }

   private Collection<ScoreObjective> func_175137_e(Entity entity) {
      ScorePlayerTeam var5 = this.getWorldScoreboard().getPlayersTeam(this.getName());
      if (var5 != null) {
         int var6 = var5.getChatFormat().getColorIndex();
         if (var6 >= 0 && var6 < IScoreObjectiveCriteria.field_178793_i.length) {
            for (ScoreObjective var8 : this.getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.field_178793_i[var6])) {
               this.getWorldScoreboard().Method281(entity.getName(), var8).func_96648_a();
            }
         }
      }

      ScorePlayerTeam var9 = this.getWorldScoreboard().getPlayersTeam(entity.getName());
      if (var9 != null) {
         int var10 = var9.getChatFormat().getColorIndex();
         if (var10 >= 0 && var10 < IScoreObjectiveCriteria.field_178792_h.length) {
            return this.getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.field_178792_h[var10]);
         }
      }

      return Lists.newArrayList();
   }

   public EntityItem dropOneItem(boolean boolean1) {
      return this.dropItem(
         this.inventory
            .decrStackSize(this.inventory.currentItem, boolean1 && this.inventory.getCurrentItem() != null ? this.inventory.getCurrentItem().stackSize : 1),
         false,
         true
      );
   }

   public EntityItem dropPlayerItemWithRandomChoice(ItemStack itemStack, boolean boolean2) {
      return this.dropItem(itemStack, false, false);
   }

   public EntityItem dropItem(ItemStack itemStack, boolean boolean2, boolean boolean3) {
      if (itemStack == null) {
         return null;
      }

      if (itemStack.stackSize == 0) {
         return null;
      }

      double var7 = this.posY - 0.3F + this.getEyeHeight();
      EntityItem var9 = new EntityItem(this.worldObj, this.posX, var7, this.posZ, itemStack);
      var9.setPickupDelay(40);
      if (boolean3) {
         var9.setThrower(this.getName());
      }

      if (boolean2) {
         float var10 = this.rand.nextFloat() * 0.5F;
         float var11 = this.rand.nextFloat() * (float) Math.PI * 2.0F;
         var9.motionX = -MathHelper.sin(var11) * var10;
         var9.motionZ = MathHelper.cos(var11) * var10;
         var9.motionY = 0.2F;
      } else {
         var9.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI) * 0.3F;
         var9.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI) * 0.3F;
         var9.motionY = -MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI) * 0.3F + 0.1F;
         float var13 = this.rand.nextFloat() * (float) Math.PI * 2.0F;
         float var12 = 0.02F * this.rand.nextFloat();
         var9.motionX = var9.motionX + Math.cos(var13) * var12;
         var9.motionY = var9.motionY + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
         var9.motionZ = var9.motionZ + Math.sin(var13) * var12;
      }

      this.joinEntityItemWithWorld(var9);
      if (boolean3) {
         this.triggerAchievement(StatList.dropStat);
      }

      return var9;
   }

   protected void joinEntityItemWithWorld(EntityItem entityItem) {
      this.worldObj.spawnEntityInWorld(entityItem);
   }

   public float getToolDigEfficiency(Block block, int integer) {
      float var6 = this.inventory.getStrVsBlock(block, integer);
      if (var6 > 1.0F) {
         int var7 = EnchantmentHelper.Method396(this);
         ItemStack var8 = this.inventory.getCurrentItem();
         if (var7 > 0 && var8 != null) {
            var6 += var7 * var7 + 1;
         }
      }

      if (this.isPotionActive(Potion.digSpeed)) {
         var6 *= 1.0F + (this.getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2F;
      }

      if (this.isPotionActive(Potion.digSlowdown)) {
         var6 *= switch (this.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) {
            case 0 -> 0.3F;
            case 1 -> 0.09F;
            case 2 -> 0.0027F;
            default -> 8.1E-4F;
         };
      }

      if (this.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(this)) {
         var6 /= 5.0F;
      }

      if (!this.onGround) {
         var6 /= 5.0F;
      }

      return var6;
   }

   public float Method547(Block block, int integer, boolean boolean3) {
      float var7 = this.inventory.getStrVsBlock(block, integer);
      if (var7 > 1.0F) {
         int var8 = EnchantmentHelper.Method396(this);
         ItemStack var9 = this.inventory.getStackInSlot(integer);
         if (var8 > 0 && var9 != null) {
            var7 += var8 * var8 + 1;
         }
      }

      if (this.isPotionActive(Potion.digSpeed)) {
         var7 *= 1.0F + (this.getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2F;
      }

      if (this.isPotionActive(Potion.digSlowdown)) {
         var7 *= switch (this.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) {
            case 0 -> 0.3F;
            case 1 -> 0.09F;
            case 2 -> 0.0027F;
            default -> 8.1E-4F;
         };
      }

      if (this.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(this)) {
         var7 /= 5.0F;
      }

      if (!this.onGround && !boolean3) {
         var7 /= 5.0F;
      }

      return var7;
   }

   public float Method548(Block block, int integer, boolean boolean3) {
      float var7 = this.inventory.getStrVsBlock(block, integer);
      if (var7 > 1.0F) {
         int var8 = EnchantmentHelper.Method397(this, integer);
         ItemStack var9 = this.inventory.getStackInSlot(integer);
         if (var8 > 0 && var9 != null) {
            var7 += var8 * var8 + 1;
         }
      }

      if (this.isPotionActive(Potion.digSpeed)) {
         var7 *= 1.0F + (this.getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2F;
      }

      if (this.isPotionActive(Potion.digSlowdown)) {
         var7 *= switch (this.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) {
            case 0 -> 0.3F;
            case 1 -> 0.09F;
            case 2 -> 0.0027F;
            default -> 8.1E-4F;
         };
      }

      if (this.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(this)) {
         var7 /= 5.0F;
      }

      if (!this.onGround && !boolean3) {
         var7 /= 5.0F;
      }

      return var7;
   }

   public boolean canHarvestBlock(Block block, int integer) {
      return this.inventory.Method728(block, integer);
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      super.readEntityFromNBT(nBTTagCompound);
      this.entityUniqueID = getUUID(this.gameProfile);
      NBTTagList var5 = nBTTagCompound.getTagList("Inventory", 10);
      this.inventory.readFromNBT(var5);
      this.inventory.currentItem = nBTTagCompound.getInteger("SelectedItemSlot");
      this.sleeping = nBTTagCompound.getBoolean("Sleeping");
      this.sleepTimer = nBTTagCompound.getShort("SleepTimer");
      this.experience = nBTTagCompound.getFloat("XpP");
      this.experienceLevel = nBTTagCompound.getInteger("XpLevel");
      this.experienceTotal = nBTTagCompound.getInteger("XpTotal");
      this.xpSeed = nBTTagCompound.getInteger("XpSeed");
      if (this.xpSeed == 0) {
         this.xpSeed = this.rand.nextInt();
      }

      this.setScore(nBTTagCompound.getInteger("Score"));
      if (this.sleeping) {
         this.playerLocation = new BlockPos(this);
         this.wakeUpPlayer(true, true, false);
      }

      if (nBTTagCompound.hasKey("SpawnX", 99) && nBTTagCompound.hasKey("SpawnY", 99) && nBTTagCompound.hasKey("SpawnZ", 99)) {
         this.spawnChunk = new BlockPos(nBTTagCompound.getInteger("SpawnX"), nBTTagCompound.getInteger("SpawnY"), nBTTagCompound.getInteger("SpawnZ"));
         this.spawnForced = nBTTagCompound.getBoolean("SpawnForced");
      }

      this.foodStats.readNBT(nBTTagCompound);
      this.capabilities.readCapabilitiesFromNBT(nBTTagCompound);
      if (nBTTagCompound.hasKey("EnderItems", 9)) {
         NBTTagList var6 = nBTTagCompound.getTagList("EnderItems", 10);
         this.theInventoryEnderChest.loadInventoryFromNBT(var6);
      }
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      super.writeEntityToNBT(nBTTagCompound);
      nBTTagCompound.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
      nBTTagCompound.setInteger("SelectedItemSlot", this.inventory.currentItem);
      nBTTagCompound.setBoolean("Sleeping", this.sleeping);
      nBTTagCompound.setShort("SleepTimer", (short)this.sleepTimer);
      nBTTagCompound.setFloat("XpP", this.experience);
      nBTTagCompound.setInteger("XpLevel", this.experienceLevel);
      nBTTagCompound.setInteger("XpTotal", this.experienceTotal);
      nBTTagCompound.setInteger("XpSeed", this.xpSeed);
      nBTTagCompound.setInteger("Score", this.getScore());
      if (this.spawnChunk != null) {
         nBTTagCompound.setInteger("SpawnX", this.spawnChunk.getX());
         nBTTagCompound.setInteger("SpawnY", this.spawnChunk.getY());
         nBTTagCompound.setInteger("SpawnZ", this.spawnChunk.getZ());
         nBTTagCompound.setBoolean("SpawnForced", this.spawnForced);
      }

      this.foodStats.writeNBT(nBTTagCompound);
      this.capabilities.writeCapabilitiesToNBT(nBTTagCompound);
      nBTTagCompound.setTag("EnderItems", this.theInventoryEnderChest.saveInventoryToNBT());
      ItemStack var5 = this.inventory.getCurrentItem();
      if (var5 != null && var5.getItem() != null) {
         nBTTagCompound.setTag("SelectedItem", var5.writeToNBT(new NBTTagCompound()));
      }
   }

   @Override
   public boolean attackEntityFrom(DamageSource damageSource, float float2) {
      if (this.isEntityInvulnerable(damageSource)) {
         return false;
      }

      if (this.capabilities.disableDamage && !damageSource.canHarmInCreative()) {
         return false;
      }

      this.entityAge = 0;
      if (this.getHealth() <= 0.0F) {
         return false;
      }

      if (this.isPlayerSleeping() && !this.worldObj.isRemote) {
         this.wakeUpPlayer(true, true, false);
      }

      if (damageSource.isDifficultyScaled()) {
         if (this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) {
            float2 = 0.0F;
         }

         if (this.worldObj.getDifficulty() == EnumDifficulty.EASY) {
            float2 = float2 / 2.0F + 1.0F;
         }

         if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
            float2 = float2 * 3.0F / 2.0F;
         }
      }

      if (float2 == 0.0F) {
         return false;
      }

      Entity var6 = damageSource.getEntity();
      if (var6 instanceof EntityArrow && ((EntityArrow)var6).shootingEntity != null) {
         ;
      }

      return super.attackEntityFrom(damageSource, float2);
   }

   public boolean canAttackPlayer(EntityPlayer entityPlayer) {
      Team var5 = this.getTeam();
      Team var6 = entityPlayer.getTeam();
      return var5 == null || !var5.isSameTeam(var6) || var5.getAllowFriendlyFire();
   }

   @Override
   protected void damageArmor(float float1) {
      this.inventory.damageArmor(float1);
   }

   @Override
   public int getTotalArmorValue() {
      return this.inventory.getTotalArmorValue();
   }

   public float getArmorVisibility() {
      int var4 = 0;

      for (ItemStack var8 : this.inventory.armorInventory) {
         if (var8 != null) {
            var4++;
         }
      }

      return (float)var4 / this.inventory.armorInventory.length;
   }

   @Override
   protected void damageEntity(DamageSource damageSource, float float2) {
      if (!this.isEntityInvulnerable(damageSource)) {
         if (!damageSource.isUnblockable() && this.isBlocking() && float2 > 0.0F) {
            float2 = (1.0F + float2) * 0.5F;
         }

         float2 = this.applyArmorCalculations(damageSource, float2);
         float2 = this.applyPotionDamageCalculations(damageSource, float2);
         float var6 = float2;
         float2 = Math.max(float2 - this.getAbsorptionAmount(), 0.0F);
         this.setAbsorptionAmount(this.getAbsorptionAmount() - (var6 - float2));
         if (float2 != 0.0F) {
            this.addExhaustion(damageSource.getHungerDamage());
            float var7 = this.getHealth();
            this.setHealth(this.getHealth() - float2);
            this.getCombatTracker().trackDamage(damageSource, var7, float2);
            if (float2 < 3.4028235E37F) {
               this.addStat(StatList.damageTakenStat, Math.round(float2 * 10.0F));
            }
         }
      }
   }

   public void openEditSign(TileEntitySign tileEntitySign) {
   }

   public void openEditCommandBlock(CommandBlockLogic commandBlockLogic) {
   }

   public void displayVillagerTradeGui(IMerchant iMerchant) {
   }

   public void displayGUIChest(IInventory iInventory) {
   }

   public void displayGUIHorse(EntityHorse entityHorse, IInventory iInventory) {
   }

   public void displayGui(IInteractionObject iInteractionObject) {
   }

   public void displayGUIBook(ItemStack itemStack) {
   }

   public boolean interactWith(Entity entity) {
      if (this.isSpectator()) {
         if (entity instanceof IInventory) {
            this.displayGUIChest((IInventory)entity);
         }

         return false;
      } else {
         ItemStack var5 = this.getCurrentEquippedItem();
         ItemStack var6 = var5 != null ? var5.copy() : null;
         if (!entity.interactFirst(this)) {
            if (var5 != null && entity instanceof EntityLivingBase) {
               if (this.capabilities.isCreativeMode) {
                  var5 = var6;
               }

               if (var5.interactWithEntity(this, (EntityLivingBase)entity)) {
                  if (var5.stackSize <= 0 && !this.capabilities.isCreativeMode) {
                     this.destroyCurrentEquippedItem();
                  }

                  return true;
               }
            }

            return false;
         } else {
            if (var5 != null && var5 == this.getCurrentEquippedItem()) {
               if (var5.stackSize <= 0 && !this.capabilities.isCreativeMode) {
                  this.destroyCurrentEquippedItem();
               } else if (var5.stackSize < var6.stackSize && this.capabilities.isCreativeMode) {
                  var5.stackSize = var6.stackSize;
               }
            }

            return true;
         }
      }
   }

   public ItemStack getCurrentEquippedItem() {
      return this.inventory.getCurrentItem();
   }

   public void destroyCurrentEquippedItem() {
      this.inventory.setInventorySlotContents(this.inventory.currentItem, null);
   }

   @Override
   public double getYOffset() {
      return -0.35;
   }

   public void attackTargetEntityWithCurrentItem(Entity entity) {
      if (entity.canAttackWithItem() && !entity.hitByEntity(this)) {
         float var5 = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
         float var7;
         if (entity instanceof EntityLivingBase) {
            var7 = EnchantmentHelper.getModifierForCreature(this.getHeldItem(), ((EntityLivingBase)entity).getCreatureAttribute());
         } else {
            var7 = EnchantmentHelper.getModifierForCreature(this.getHeldItem(), EnumCreatureAttribute.UNDEFINED);
         }

         int var6 = 0 + EnchantmentHelper.getKnockbackModifier(this);
         if (this.isSprinting()) {
            var6++;
         }

         if (var5 > 0.0F || var7 > 0.0F) {
            boolean var8 = this.fallDistance > 0.0F
               && !this.onGround
               && !this.isOnLadder()
               && !this.isInWater()
               && !this.isPotionActive(Potion.blindness)
               && this.ridingEntity == null
               && entity instanceof EntityLivingBase;
            if (var8 && var5 > 0.0F) {
               var5 *= 1.5F;
            }

            var5 += var7;
            boolean var9 = false;
            int var10 = EnchantmentHelper.getFireAspectModifier(this);
            if (entity instanceof EntityLivingBase && var10 > 0 && !entity.isBurning()) {
               var9 = true;
               entity.setFire(1);
            }

            double var11 = entity.motionX;
            double var13 = entity.motionY;
            double var15 = entity.motionZ;
            boolean var17 = entity.attackEntityFrom(DamageSource.causePlayerDamage(this), var5);
            if (var17) {
               SlowdownEvent var18 = new SlowdownEvent(SlowdownEvent.Type.SPRINTING);
               Moon.getInstance().getEventBus().publish(var18);
               if (var6 > 0) {
                  entity.addVelocity(
                     -MathHelper.sin(this.rotationYaw * (float) Math.PI / 180.0F) * var6 * 0.5F,
                     0.1,
                     MathHelper.cos(this.rotationYaw * (float) Math.PI / 180.0F) * var6 * 0.5F
                  );
                  if (!var18.isCancelled()) {
                     this.motionX *= 0.6;
                     this.motionZ *= 0.6;
                     this.setSprinting(false);
                  }
               }

               if (entity instanceof EntityPlayerMP && entity.velocityChanged) {
                  ((EntityPlayerMP)entity).playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(entity));
                  entity.velocityChanged = false;
                  entity.motionX = var11;
                  entity.motionY = var13;
                  entity.motionZ = var15;
               }

               if (var8) {
                  this.onCriticalHit(entity);
               }

               if (var7 > 0.0F) {
                  this.onEnchantmentCritical(entity);
               }

               if (var5 >= 18.0F) {
                  this.triggerAchievement(AchievementList.overkill);
               }

               this.setLastAttacker(entity);
               if (entity instanceof EntityLivingBase) {
                  EnchantmentHelper.applyThornEnchantments((EntityLivingBase)entity, this);
               }

               EnchantmentHelper.applyArthropodEnchantments(this, entity);
               ItemStack var19 = this.getCurrentEquippedItem();
               Entity var20 = entity;
               if (entity instanceof EntityDragonPart) {
                  IEntityMultiPart var21 = ((EntityDragonPart)entity).entityDragonObj;
                  if (var21 instanceof EntityLivingBase) {
                     var20 = (EntityLivingBase)var21;
                  }
               }

               if (var19 != null && var20 instanceof EntityLivingBase) {
                  var19.hitEntity((EntityLivingBase)var20, this);
                  if (var19.stackSize <= 0) {
                     this.destroyCurrentEquippedItem();
                  }
               }

               if (entity instanceof EntityLivingBase) {
                  this.addStat(StatList.damageDealtStat, Math.round(var5 * 10.0F));
                  if (var10 > 0) {
                     entity.setFire(var10 * 4);
                  }
               }

               this.addExhaustion(0.3F);
            } else if (var9) {
               entity.extinguish();
            }
         }
      }
   }

   public void onCriticalHit(Entity entity) {
   }

   public void onEnchantmentCritical(Entity entity) {
   }

   public void respawnPlayer() {
   }

   @Override
   public void setDead() {
      super.setDead();
      this.inventoryContainer.onContainerClosed(this);
      if (this.openContainer != null) {
         this.openContainer.onContainerClosed(this);
      }
   }

   @Override
   public boolean isEntityInsideOpaqueBlock() {
      return !this.sleeping && super.isEntityInsideOpaqueBlock();
   }

   public boolean isUser() {
      return false;
   }

   public GameProfile getGameProfile() {
      return this.gameProfile;
   }

   public EntityPlayer.EnumStatus trySleep(BlockPos blockPos) {
      if (!this.worldObj.isRemote) {
         if (this.isPlayerSleeping() || !this.isEntityAlive()) {
            return EntityPlayer.EnumStatus.OTHER_PROBLEM;
         }

         if (!this.worldObj.provider.isSurfaceWorld()) {
            return EntityPlayer.EnumStatus.NOT_POSSIBLE_HERE;
         }

         if (this.worldObj.isDaytime()) {
            return EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW;
         }

         if (Math.abs(this.posX - blockPos.getX()) > 3.0 || Math.abs(this.posY - blockPos.getY()) > 2.0 || Math.abs(this.posZ - blockPos.getZ()) > 3.0) {
            return EntityPlayer.EnumStatus.TOO_FAR_AWAY;
         }

         double var5 = 8.0;
         List var9 = this.worldObj
            .getEntitiesWithinAABB(
               EntityMob.class,
               new AxisAlignedBB(
                  blockPos.getX() - var5, blockPos.getY() - 5.0, blockPos.getZ() - var5, blockPos.getX() + var5, blockPos.getY() + 5.0, blockPos.getZ() + var5
               )
            );
         if (!var9.isEmpty()) {
            return EntityPlayer.EnumStatus.NOT_SAFE;
         }
      }

      if (this.isRiding()) {
         this.mountEntity(null);
      }

      this.setSize(0.2F, 0.2F);
      if (this.worldObj.Method322(blockPos)) {
         EnumFacing var10 = this.worldObj.getBlockState(blockPos).getValue(BlockDirectional.FACING);
         float var6 = 0.5F;
         float var7 = 0.5F;
         switch (var10) {
            case SOUTH:
               var7 = 0.9F;
               break;
            case NORTH:
               var7 = 0.1F;
               break;
            case WEST:
               var6 = 0.1F;
               break;
            case EAST:
               var6 = 0.9F;
         }

         this.func_175139_a(var10);
         this.setPosition(blockPos.getX() + var6, blockPos.getY() + 0.6875F, blockPos.getZ() + var7);
      } else {
         this.setPosition(blockPos.getX() + 0.5F, blockPos.getY() + 0.6875F, blockPos.getZ() + 0.5F);
      }

      this.sleeping = true;
      this.sleepTimer = 0;
      this.playerLocation = blockPos;
      this.motionX = this.motionZ = this.motionY = 0.0;
      if (!this.worldObj.isRemote) {
         this.worldObj.updateAllPlayersSleepingFlag();
      }

      return EntityPlayer.EnumStatus.OK;
   }

   private void func_175139_a(EnumFacing enumFacing) {
      this.renderOffsetX = 0.0F;
      this.renderOffsetZ = 0.0F;
      switch (enumFacing) {
         case SOUTH:
            this.renderOffsetZ = -1.8F;
            break;
         case NORTH:
            this.renderOffsetZ = 1.8F;
            break;
         case WEST:
            this.renderOffsetX = 1.8F;
            break;
         case EAST:
            this.renderOffsetX = -1.8F;
      }
   }

   public void wakeUpPlayer(boolean boolean1, boolean boolean2, boolean boolean3) {
      this.setSize(0.6F, 1.8F);
      IBlockState var7 = this.worldObj.getBlockState(this.playerLocation);
      if (this.playerLocation != null && var7.getBlock() == Blocks.bed) {
         this.worldObj.setBlockState(this.playerLocation, var7.withProperty(BlockBed.OCCUPIED, Boolean.FALSE), 4);
         BlockPos var8 = BlockBed.getSafeExitLocation(this.worldObj, this.playerLocation, 0);
         if (var8 == null) {
            var8 = this.playerLocation.up();
         }

         this.setPosition(var8.getX() + 0.5F, var8.getY() + 0.1F, var8.getZ() + 0.5F);
      }

      this.sleeping = false;
      if (!this.worldObj.isRemote && boolean2) {
         this.worldObj.updateAllPlayersSleepingFlag();
      }

      this.sleepTimer = boolean1 ? 0 : 100;
      if (boolean3) {
         this.setSpawnPoint(this.playerLocation, false);
      }
   }

   private boolean isInBed() {
      return this.worldObj.getBlockState(this.playerLocation).getBlock() == Blocks.bed;
   }

   public static BlockPos getBedSpawnLocation(World world, BlockPos blockPos, boolean boolean3) {
      Block var6 = world.getBlockState(blockPos).getBlock();
      if (var6 != Blocks.bed) {
         if (!boolean3) {
            return null;
         }

         boolean var7 = var6.canSpawnInBlock();
         boolean var8 = world.getBlockState(blockPos.up()).getBlock().canSpawnInBlock();
         return var7 && var8 ? blockPos : null;
      } else {
         return BlockBed.getSafeExitLocation(world, blockPos, 0);
      }
   }

   public float getBedOrientationInDegrees() {
      if (this.playerLocation != null) {
         EnumFacing var4 = this.worldObj.getBlockState(this.playerLocation).getValue(BlockDirectional.FACING);
         switch (var4) {
            case SOUTH:
               return 90.0F;
            case NORTH:
               return 270.0F;
            case WEST:
               return 0.0F;
            case EAST:
               return 180.0F;
         }
      }

      return 0.0F;
   }

   @Override
   public boolean isPlayerSleeping() {
      return this.sleeping;
   }

   public boolean isPlayerFullyAsleep() {
      return this.sleeping && this.sleepTimer >= 100;
   }

   public int getSleepTimer() {
      return this.sleepTimer;
   }

   public void addChatComponentMessage(IChatComponent iChatComponent) {
   }

   public BlockPos getBedLocation() {
      return this.spawnChunk;
   }

   public boolean isSpawnForced() {
      return this.spawnForced;
   }

   public void setSpawnPoint(BlockPos blockPos, boolean boolean2) {
      if (blockPos != null) {
         this.spawnChunk = blockPos;
         this.spawnForced = boolean2;
      } else {
         this.spawnChunk = null;
         this.spawnForced = false;
      }
   }

   public void triggerAchievement(StatBase statBase) {
      this.addStat(statBase, 1);
   }

   public void addStat(StatBase statBase, int integer) {
   }

   public void func_175145_a(StatBase statBase) {
   }

   @Override
   public void jump() {
      super.jump();
      this.triggerAchievement(StatList.jumpStat);
      if (this.isSprinting()) {
         this.addExhaustion(0.8F);
      } else {
         this.addExhaustion(0.2F);
      }
   }

   @Override
   public void moveEntityWithHeading(float float1, float float2) {
      double var6 = this.posX;
      double var8 = this.posY;
      double var10 = this.posZ;
      if (this.capabilities.isFlying && this.ridingEntity == null) {
         double var12 = this.motionY;
         float var14 = this.jumpMovementFactor;
         this.jumpMovementFactor = this.capabilities.getFlySpeed() * (this.isSprinting() ? 2 : 1);
         super.moveEntityWithHeading(float1, float2);
         this.motionY = var12 * 0.6;
         this.jumpMovementFactor = var14;
      } else {
         super.moveEntityWithHeading(float1, float2);
      }

      this.addMovementStat(this.posX - var6, this.posY - var8, this.posZ - var10);
   }

   @Override
   public float getAIMoveSpeed() {
      return (float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
   }

   public void addMovementStat(double double1, double double2, double double3) {
      if (this.ridingEntity == null) {
         if (this.isInsideOfMaterial(Material.water)) {
            int var10 = Math.round(MathHelper.sqrt_double(double1 * double1 + double2 * double2 + double3 * double3) * 100.0F);
            if (var10 > 0) {
               this.addStat(StatList.distanceDoveStat, var10);
               this.addExhaustion(0.015F * var10 * 0.01F);
            }
         } else if (this.isInWater()) {
            int var11 = Math.round(MathHelper.sqrt_double(double1 * double1 + double3 * double3) * 100.0F);
            if (var11 > 0) {
               this.addStat(StatList.distanceSwumStat, var11);
               this.addExhaustion(0.015F * var11 * 0.01F);
            }
         } else if (this.isOnLadder()) {
            if (double2 > 0.0) {
               this.addStat(StatList.distanceClimbedStat, (int)Math.round(double2 * 100.0));
            }
         } else if (this.onGround) {
            int var12 = Math.round(MathHelper.sqrt_double(double1 * double1 + double3 * double3) * 100.0F);
            if (var12 > 0) {
               this.addStat(StatList.distanceWalkedStat, var12);
               if (this.isSprinting()) {
                  this.addStat(StatList.distanceSprintedStat, var12);
                  this.addExhaustion(0.099999994F * var12 * 0.01F);
               } else {
                  if (this.isSneaking()) {
                     this.addStat(StatList.distanceCrouchedStat, var12);
                  }

                  this.addExhaustion(0.01F * var12 * 0.01F);
               }
            }
         } else {
            int var13 = Math.round(MathHelper.sqrt_double(double1 * double1 + double3 * double3) * 100.0F);
            if (var13 > 25) {
               this.addStat(StatList.distanceFlownStat, var13);
            }
         }
      }
   }

   private void addMountedMovementStat(double double1, double double2, double double3) {
      if (this.ridingEntity != null) {
         int var10 = Math.round(MathHelper.sqrt_double(double1 * double1 + double2 * double2 + double3 * double3) * 100.0F);
         if (var10 > 0) {
            if (this.ridingEntity instanceof EntityMinecart) {
               this.addStat(StatList.distanceByMinecartStat, var10);
               if (this.startMinecartRidingCoordinate == null) {
                  this.startMinecartRidingCoordinate = new BlockPos(this);
               } else if (this.startMinecartRidingCoordinate
                     .distanceSq(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ))
                  >= 1000000.0) {
                  this.triggerAchievement(AchievementList.onARail);
               }
            } else if (this.ridingEntity instanceof EntityBoat) {
               this.addStat(StatList.distanceByBoatStat, var10);
            } else if (this.ridingEntity instanceof EntityPig) {
               this.addStat(StatList.distanceByPigStat, var10);
            } else if (this.ridingEntity instanceof EntityHorse) {
               this.addStat(StatList.distanceByHorseStat, var10);
            }
         }
      }
   }

   @Override
   public void fall(float float1, float float2) {
      if (!this.capabilities.allowFlying) {
         if (float1 >= 2.0F) {
            this.addStat(StatList.distanceFallenStat, (int)Math.round(float1 * 100.0));
         }

         super.fall(float1, float2);
      }
   }

   @Override
   protected void resetHeight() {
      if (!this.isSpectator()) {
         super.resetHeight();
      }
   }

   @Override
   protected String getFallSoundString(int integer) {
      return integer > 4 ? "game.player.hurt.fall.big" : "game.player.hurt.fall.small";
   }

   @Override
   public void onKillEntity(EntityLivingBase entityLivingBase) {
      if (entityLivingBase instanceof IMob) {
         this.triggerAchievement(AchievementList.killEnemy);
      }

      EntityList.EntityEggInfo var5 = EntityList.entityEggs.get(EntityList.getEntityID(entityLivingBase));
      if (var5 != null) {
         this.triggerAchievement(var5.field_151512_d);
      }
   }

   @Override
   public void setInWeb() {
      if (!this.capabilities.isFlying) {
         super.setInWeb();
      }
   }

   @Override
   public ItemStack getCurrentArmor(int integer) {
      return this.inventory.armorItemInSlot(integer);
   }

   public void addExperience(int integer) {
      this.addScore(integer);
      int var5 = Integer.MAX_VALUE - this.experienceTotal;
      if (integer > var5) {
         integer = var5;
      }

      this.experience = this.experience + (float)integer / this.xpBarCap();

      for (this.experienceTotal += integer; this.experience >= 1.0F; this.experience = this.experience / this.xpBarCap()) {
         this.experience = (this.experience - 1.0F) * this.xpBarCap();
         this.addExperienceLevel(1);
      }
   }

   public int getXPSeed() {
      return this.xpSeed;
   }

   public void removeExperienceLevel(int integer) {
      this.experienceLevel -= integer;
      if (this.experienceLevel < 0) {
         this.experienceLevel = 0;
         this.experience = 0.0F;
         this.experienceTotal = 0;
      }

      this.xpSeed = this.rand.nextInt();
   }

   public void addExperienceLevel(int integer) {
      this.experienceLevel += integer;
      if (this.experienceLevel < 0) {
         this.experienceLevel = 0;
         this.experience = 0.0F;
         this.experienceTotal = 0;
      }

      if (integer > 0 && this.experienceLevel % 5 == 0 && this.lastXPSound < this.ticksExisted - 100.0F) {
         float var5 = this.experienceLevel > 30 ? 1.0F : this.experienceLevel / 30.0F;
         this.worldObj.playSoundAtEntity(this, "random.levelup", var5 * 0.75F, 1.0F);
         this.lastXPSound = this.ticksExisted;
      }
   }

   public int xpBarCap() {
      return this.experienceLevel >= 30
         ? 112 + (this.experienceLevel - 30) * 9
         : (this.experienceLevel >= 15 ? 37 + (this.experienceLevel - 15) * 5 : 7 + this.experienceLevel * 2);
   }

   public void addExhaustion(float float1) {
      if (!this.capabilities.disableDamage && !this.worldObj.isRemote) {
         this.foodStats.addExhaustion(float1);
      }
   }

   public FoodStats getFoodStats() {
      return this.foodStats;
   }

   public boolean canEat(boolean boolean1) {
      return (boolean1 || this.foodStats.needFood()) && !this.capabilities.disableDamage;
   }

   public boolean shouldHeal() {
      return this.getHealth() > 0.0F && this.getHealth() < this.getMaxHealth();
   }

   public void setItemInUse(ItemStack itemStack, int integer) {
      if (itemStack != this.itemInUse) {
         this.itemInUse = itemStack;
         this.itemInUseCount = integer;
         if (!this.worldObj.isRemote) {
            this.setEating(true);
         }
      }
   }

   public boolean isAllowEdit() {
      return this.capabilities.allowEdit;
   }

   public boolean canPlayerEdit(BlockPos blockPos, EnumFacing enumFacing, ItemStack itemStack) {
      if (this.capabilities.allowEdit) {
         return true;
      }

      if (itemStack == null) {
         return false;
      }

      BlockPos var7 = blockPos.offset(enumFacing.getOpposite());
      Block var8 = this.worldObj.getBlockState(var7).getBlock();
      return itemStack.canPlaceOn(var8) || itemStack.canEditBlocks();
   }

   @Override
   protected int getExperiencePoints(EntityPlayer entityPlayer) {
      return this.worldObj.getGameRules().getBoolean("keepInventory") ? 0 : Math.min(this.experienceLevel * 7, 100);
   }

   @Override
   protected boolean isPlayer() {
      return true;
   }

   @Override
   public boolean getAlwaysRenderNameTagForRender() {
      return true;
   }

   public void clonePlayer(EntityPlayer entityPlayer, boolean boolean2) {
      if (boolean2) {
         this.inventory.copyInventory(entityPlayer.inventory);
         this.setHealth(entityPlayer.getHealth());
         this.foodStats = entityPlayer.foodStats;
         this.experienceLevel = entityPlayer.experienceLevel;
         this.experienceTotal = entityPlayer.experienceTotal;
         this.experience = entityPlayer.experience;
         this.setScore(entityPlayer.getScore());
         this.lastPortalPos = entityPlayer.lastPortalPos;
         this.lastPortalVec = entityPlayer.lastPortalVec;
         this.teleportDirection = entityPlayer.teleportDirection;
      } else if (this.worldObj.getGameRules().getBoolean("keepInventory")) {
         this.inventory.copyInventory(entityPlayer.inventory);
         this.experienceLevel = entityPlayer.experienceLevel;
         this.experienceTotal = entityPlayer.experienceTotal;
         this.experience = entityPlayer.experience;
         this.setScore(entityPlayer.getScore());
      }

      this.xpSeed = entityPlayer.xpSeed;
      this.theInventoryEnderChest = entityPlayer.theInventoryEnderChest;
      this.getDataWatcher().updateObject(10, entityPlayer.getDataWatcher().getWatchableObjectByte(10));
   }

   @Override
   protected boolean canTriggerWalking() {
      return !this.capabilities.isFlying;
   }

   public void sendPlayerAbilities() {
   }

   public void setGameType(WorldSettings.GameType gameType) {
   }

   @Override
   public String getName() {
      return this.gameProfile.getName();
   }

   public InventoryEnderChest getInventoryEnderChest() {
      return this.theInventoryEnderChest;
   }

   @Override
   public ItemStack getEquipmentInSlot(int integer) {
      return integer == 0 ? this.inventory.getCurrentItem() : this.inventory.armorInventory[integer - 1];
   }

   @Override
   public ItemStack getHeldItem() {
      return this.inventory.getCurrentItem();
   }

   @Override
   public void setCurrentItemOrArmor(int integer, ItemStack itemStack) {
      this.inventory.armorInventory[integer] = itemStack;
   }

   @Override
   public boolean isInvisibleToPlayer(EntityPlayer entityPlayer) {
      if (!this.isInvisible()) {
         return false;
      }

      if (entityPlayer.isSpectator()) {
         return false;
      }

      Team var5 = this.getTeam();
      return var5 == null || entityPlayer == null || entityPlayer.getTeam() != var5 || !var5.getSeeFriendlyInvisiblesEnabled();
   }

   public abstract boolean isSpectator();

   @Override
   public ItemStack[] getInventory() {
      return this.inventory.armorInventory;
   }

   @Override
   public boolean isPushedByWater() {
      return !this.capabilities.isFlying;
   }

   public Scoreboard getWorldScoreboard() {
      return this.worldObj.getScoreboard();
   }

   @Override
   public Team getTeam() {
      return this.getWorldScoreboard().getPlayersTeam(this.getName());
   }

   @Override
   public IChatComponent getDisplayName() {
      ChatComponentText var1 = new ChatComponentText(ScorePlayerTeam.formatPlayerName(this.getTeam(), this.getName()));
      var1.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + this.getName() + " "));
      var1.getChatStyle().setChatHoverEvent(this.getHoverEvent());
      var1.getChatStyle().setInsertion(this.getName());
      return var1;
   }

   @Override
   public float getEyeHeight() {
      float var4 = 1.62F;
      if (this.isPlayerSleeping()) {
         var4 = 0.2F;
      }

      if (this.isSneaking()) {
         var4 -= 0.08F;
      }

      return var4;
   }

   @Override
   public void setAbsorptionAmount(float float1) {
      if (float1 < 0.0F) {
         float1 = 0.0F;
      }

      this.getDataWatcher().updateObject(17, float1);
   }

   @Override
   public float getAbsorptionAmount() {
      return this.getDataWatcher().getWatchableObjectFloat(17);
   }

   public static UUID getUUID(GameProfile gameProfile) {
      UUID var4 = gameProfile.getId();
      if (var4 == null) {
         var4 = Method549(gameProfile.getName());
      }

      return var4;
   }

   public static UUID Method549(String string) {
      return UUID.nameUUIDFromBytes(("OfflinePlayer:" + string).getBytes(Charsets.UTF_8));
   }

   public boolean canOpen(LockCode lockCode) {
      if (lockCode.isEmpty()) {
         return true;
      }

      ItemStack var5 = this.getCurrentEquippedItem();
      return var5 != null && var5.hasDisplayName() && var5.getDisplayName().equals(lockCode.getLock());
   }

   public boolean isWearing(EnumPlayerModelParts enumPlayerModelParts) {
      return (this.getDataWatcher().getWatchableObjectByte(10) & enumPlayerModelParts.getPartMask()) == enumPlayerModelParts.getPartMask();
   }

   @Override
   public boolean sendCommandFeedback() {
      return MinecraftServer.getServer().worldServers[0].getGameRules().getBoolean("sendCommandFeedback");
   }

   @Override
   public boolean replaceItemInInventory(int integer, ItemStack itemStack) {
      if (integer >= 0 && integer < this.inventory.mainInventory.length) {
         this.inventory.setInventorySlotContents(integer, itemStack);
         return true;
      }

      int var6 = integer - 100;
      if (var6 >= 0 && var6 < this.inventory.armorInventory.length) {
         int var8 = var6 + 1;
         if (itemStack != null && itemStack.getItem() != null) {
            if (itemStack.getItem() instanceof ItemArmor) {
               if (EntityLiving.getArmorPosition(itemStack) != var8) {
                  return false;
               }
            } else if (var8 != 4 || itemStack.getItem() != Items.skull && !(itemStack.getItem() instanceof ItemBlock)) {
               return false;
            }
         }

         this.inventory.setInventorySlotContents(var6 + this.inventory.mainInventory.length, itemStack);
         return true;
      } else {
         int var7 = integer - 200;
         if (var7 >= 0 && var7 < this.theInventoryEnderChest.getSizeInventory()) {
            this.theInventoryEnderChest.setInventorySlotContents(var7, itemStack);
            return true;
         } else {
            return false;
         }
      }
   }

   public boolean hasReducedDebug() {
      return this.hasReducedDebug;
   }

   public void setReducedDebug(boolean boolean1) {
      this.hasReducedDebug = boolean1;
   }

   public enum EnumChatVisibility {
      FULL(0, "options.chat.visibility.full"),
      SYSTEM(1, "options.chat.visibility.system"),
      HIDDEN(2, "options.chat.visibility.hidden");

      private static final EntityPlayer.EnumChatVisibility[] ID_LOOKUP = new EntityPlayer.EnumChatVisibility[values().length];
      private final int chatVisibility;
      private final String resourceKey;

      EnumChatVisibility(int integer3, String string4) {
         this.chatVisibility = integer3;
         this.resourceKey = string4;
      }

      public int getChatVisibility() {
         return this.chatVisibility;
      }

      public static EntityPlayer.EnumChatVisibility getEnumChatVisibility(int integer) {
         return ID_LOOKUP[integer % ID_LOOKUP.length];
      }

      public String getResourceKey() {
         return this.resourceKey;
      }

      private static EntityPlayer.EnumChatVisibility[] Method957() {
         return new EntityPlayer.EnumChatVisibility[]{FULL, SYSTEM, HIDDEN};
      }

      static {
         for (EntityPlayer.EnumChatVisibility var3 : values()) {
            ID_LOOKUP[var3.chatVisibility] = var3;
         }
      }
   }

   public enum EnumStatus {
      OK,
      NOT_POSSIBLE_HERE,
      NOT_POSSIBLE_NOW,
      TOO_FAR_AWAY,
      OTHER_PROBLEM,
      NOT_SAFE;

      private static EntityPlayer.EnumStatus[] Method695() {
         return new EntityPlayer.EnumStatus[]{OK, NOT_POSSIBLE_HERE, NOT_POSSIBLE_NOW, TOO_FAR_AWAY, OTHER_PROBLEM, NOT_SAFE};
      }
   }
}
