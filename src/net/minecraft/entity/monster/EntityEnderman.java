package net.minecraft.entity.monster;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityEnderman extends EntityMob {
   private static final UUID attackingSpeedBoostModifierUUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
   private static final AttributeModifier b6 = new AttributeModifier(attackingSpeedBoostModifierUUID, "Attacking speed boost", 0.15F, 0).setSaved(false);
   private static final Set<Block> carriableBlocks = Sets.newIdentityHashSet();
   private boolean isAggressive;

   public EntityEnderman(World world) {
      super(world);
      this.setSize(0.6F, 2.9F);
      this.stepHeight = 1.0F;
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(2, new EntityAIAttackOnCollide(this, 1.0, false));
      this.tasks.addTask(7, new EntityAIWander(this, 1.0));
      this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(8, new EntityAILookIdle(this));
      this.tasks.addTask(10, new EntityEnderman.AIPlaceBlock());
      this.tasks.addTask(11, new EntityEnderman.AITakeBlock());
      this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
      this.targetTasks.addTask(2, new EntityEnderman.AIFindPlayer());
      this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityEndermite.class, 10, true, false, EntityEndermite::isSpawnedByPlayer));
   }

   @Override
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3F);
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0);
      this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(64.0);
   }

   @Override
   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(16, (short)0);
      this.dataWatcher.addObject(17, (byte)0);
      this.dataWatcher.addObject(18, (byte)0);
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      super.writeEntityToNBT(nBTTagCompound);
      IBlockState var2 = this.getHeldBlockState();
      nBTTagCompound.setShort("carried", (short)Block.getIdFromBlock(var2.getBlock()));
      nBTTagCompound.setShort("carriedData", (short)var2.getBlock().getMetaFromState(var2));
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      super.readEntityFromNBT(nBTTagCompound);
      IBlockState var5;
      if (nBTTagCompound.hasKey("carried", 8)) {
         var5 = Block.getBlockFromName(nBTTagCompound.getString("carried")).getStateFromMeta(nBTTagCompound.getShort("carriedData") & 65535);
      } else {
         var5 = Block.getBlockById(nBTTagCompound.getShort("carried")).getStateFromMeta(nBTTagCompound.getShort("carriedData") & 65535);
      }

      this.setHeldBlockState(var5);
   }

   private boolean shouldAttackPlayer(EntityPlayer entityPlayer) {
      ItemStack var5 = entityPlayer.inventory.armorInventory[3];
      if (var5 != null && var5.getItem() == Item.getItemFromBlock(Blocks.pumpkin)) {
         return false;
      }

      Vec3 var6 = entityPlayer.getLook(1.0F).normalize();
      Vec3 var7 = new Vec3(
         this.posX - entityPlayer.posX,
         this.getEntityBoundingBox().minY + this.height / 2.0F - (entityPlayer.posY + entityPlayer.getEyeHeight()),
         this.posZ - entityPlayer.posZ
      );
      double var8 = var7.lengthVector();
      var7 = var7.normalize();
      double var10 = var6.dotProduct(var7);
      return var10 > 1.0 - 0.025 / var8 && entityPlayer.canEntityBeSeen(this);
   }

   @Override
   public float getEyeHeight() {
      return 2.55F;
   }

   @Override
   public void onLivingUpdate() {
      if (this.worldObj.isRemote) {
         for (int var4 = 0; var4 < 2; var4++) {
            this.worldObj
               .spawnParticle(
                  EnumParticleTypes.PORTAL,
                  this.posX + (this.rand.nextDouble() - 0.5) * this.width,
                  this.posY + this.rand.nextDouble() * this.height - 0.25,
                  this.posZ + (this.rand.nextDouble() - 0.5) * this.width,
                  (this.rand.nextDouble() - 0.5) * 2.0,
                  -this.rand.nextDouble(),
                  (this.rand.nextDouble() - 0.5) * 2.0
               );
         }
      }

      this.isJumping = false;
      super.onLivingUpdate();
   }

   @Override
   protected void updateAITasks() {
      if (this.isWet()) {
         this.attackEntityFrom(DamageSource.drown, 1.0F);
      }

      if (this.isScreaming() && !this.isAggressive && this.rand.nextInt(100) == 0) {
         this.setScreaming(false);
      }

      if (this.worldObj.isDaytime()) {
         float var4 = this.getBrightness(1.0F);
         if (var4 > 0.5F && this.worldObj.canSeeSky(new BlockPos(this)) && this.rand.nextFloat() * 30.0F < (var4 - 0.4F) * 2.0F) {
            this.setAttackTarget(null);
            this.setScreaming(false);
            this.isAggressive = false;
            this.teleportRandomly();
         }
      }

      super.updateAITasks();
   }

   protected boolean teleportRandomly() {
      double var3 = this.posX + (this.rand.nextDouble() - 0.5) * 64.0;
      double var5 = this.posY + (this.rand.nextInt(64) - 32);
      double var7 = this.posZ + (this.rand.nextDouble() - 0.5) * 64.0;
      return this.teleportTo(var3, var5, var7);
   }

   protected boolean teleportToEntity(Entity entity) {
      Vec3 var4 = new Vec3(
            this.posX - entity.posX, this.getEntityBoundingBox().minY + this.height / 2.0F - entity.posY + entity.getEyeHeight(), this.posZ - entity.posZ
         )
         .normalize();
      double var7 = this.posX + (this.rand.nextDouble() - 0.5) * 8.0 - var4.xCoord * 16.0;
      double var9 = this.posY + (this.rand.nextInt(16) - 8) - var4.yCoord * 16.0;
      double var11 = this.posZ + (this.rand.nextDouble() - 0.5) * 8.0 - var4.zCoord * 16.0;
      return this.teleportTo(var7, var9, var11);
   }

   protected boolean teleportTo(double double1, double double2, double double3) {
      double var10 = this.posX;
      double var12 = this.posY;
      double var14 = this.posZ;
      this.posX = double1;
      this.posY = double2;
      this.posZ = double3;
      boolean var16 = false;
      BlockPos var17 = new BlockPos(this.posX, this.posY, this.posZ);
      if (this.worldObj.Method322(var17)) {
         boolean var18 = false;

         while (!var18 && var17.getY() > 0) {
            BlockPos var19 = var17.down();
            Block var20 = this.worldObj.getBlockState(var19).getBlock();
            if (var20.getMaterial().blocksMovement()) {
               var18 = true;
            } else {
               this.posY--;
               var17 = var19;
            }
         }

         if (var18) {
            super.setPositionAndUpdate(this.posX, this.posY, this.posZ);
            if (this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox())
               )
             {
               var16 = true;
            }
         }
      }

      if (!var16) {
         this.setPosition(var10, var12, var14);
         return false;
      }

      short var31 = 128;

      for (int var32 = 0; var32 < var31; var32++) {
         double var33 = var32 / 127.0;
         float var22 = (this.rand.nextFloat() - 0.5F) * 0.2F;
         float var23 = (this.rand.nextFloat() - 0.5F) * 0.2F;
         float var24 = (this.rand.nextFloat() - 0.5F) * 0.2F;
         double var25 = var10 + (this.posX - var10) * var33 + (this.rand.nextDouble() - 0.5) * this.width * 2.0;
         double var27 = var12 + (this.posY - var12) * var33 + this.rand.nextDouble() * this.height;
         double var29 = var14 + (this.posZ - var14) * var33 + (this.rand.nextDouble() - 0.5) * this.width * 2.0;
         this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, var25, var27, var29, var22, var23, var24);
      }

      this.worldObj.playSoundEffect(var10, var12, var14, "mob.endermen.portal", 1.0F, 1.0F);
      this.playSound("mob.endermen.portal", 1.0F, 1.0F);
      return true;
   }

   @Override
   protected String getLivingSound() {
      return this.isScreaming() ? "mob.endermen.scream" : "mob.endermen.idle";
   }

   @Override
   protected String getHurtSound() {
      return "mob.endermen.hit";
   }

   @Override
   protected String getDeathSound() {
      return "mob.endermen.death";
   }

   @Override
   protected Item getDropItem() {
      return Items.ender_pearl;
   }

   @Override
   protected void dropFewItems(boolean boolean1, int integer) {
      Item var6 = this.getDropItem();
      if (var6 != null) {
         int var7 = this.rand.nextInt(2 + integer);

         for (int var8 = 0; var8 < var7; var8++) {
            this.dropItem(var6, 1);
         }
      }
   }

   public void setHeldBlockState(IBlockState iBlockState) {
      this.dataWatcher.updateObject(16, (short)(Block.getStateId(iBlockState) & 65535));
   }

   public IBlockState getHeldBlockState() {
      return Block.getStateById(this.dataWatcher.getWatchableObjectShort(16) & 65535);
   }

   @Override
   public boolean attackEntityFrom(DamageSource damageSource, float float2) {
      if (this.isEntityInvulnerable(damageSource)) {
         return false;
      }

      if (damageSource.getEntity() == null || !(damageSource.getEntity() instanceof EntityEndermite)) {
         if (!this.worldObj.isRemote) {
            this.setScreaming(true);
         }

         if (damageSource instanceof EntityDamageSource && damageSource.getEntity() instanceof EntityPlayer) {
            if (damageSource.getEntity() instanceof EntityPlayerMP && ((EntityPlayerMP)damageSource.getEntity()).theItemInWorldManager.isCreative()) {
               this.setScreaming(false);
            } else {
               this.isAggressive = true;
            }
         }

         if (damageSource instanceof EntityDamageSourceIndirect) {
            this.isAggressive = false;

            for (int var7 = 0; var7 < 64; var7++) {
               if (this.teleportRandomly()) {
                  return true;
               }
            }

            return false;
         }
      }

      boolean var6 = super.attackEntityFrom(damageSource, float2);
      if (damageSource.isUnblockable() && this.rand.nextInt(10) != 0) {
         this.teleportRandomly();
      }

      return var6;
   }

   public boolean isScreaming() {
      return this.dataWatcher.getWatchableObjectByte(18) > 0;
   }

   public void setScreaming(boolean boolean1) {
      this.dataWatcher.updateObject(18, (byte)(boolean1 ? 1 : 0));
   }

   static {
      carriableBlocks.add(Blocks.grass);
      carriableBlocks.add(Blocks.dirt);
      carriableBlocks.add(Blocks.sand);
      carriableBlocks.add(Blocks.gravel);
      carriableBlocks.add(Blocks.yellow_flower);
      carriableBlocks.add(Blocks.red_flower);
      carriableBlocks.add(Blocks.brown_mushroom);
      carriableBlocks.add(Blocks.red_mushroom);
      carriableBlocks.add(Blocks.tnt);
      carriableBlocks.add(Blocks.cactus);
      carriableBlocks.add(Blocks.clay);
      carriableBlocks.add(Blocks.pumpkin);
      carriableBlocks.add(Blocks.melon_block);
      carriableBlocks.add(Blocks.mycelium);
   }

   class AIFindPlayer extends EntityAINearestAttackableTarget {
      private EntityPlayer player;
      private int field_179450_h;
      private int field_179451_i;
      private final EntityEnderman enderman = EntityEnderman.this;

      public AIFindPlayer() {
         super(EntityEnderman.this, EntityPlayer.class, true);
      }

      @Override
      public boolean shouldExecute() {
         double var4 = this.getTargetDistance();
         List var6 = this.taskOwner
            .worldObj
            .getEntitiesWithinAABB(EntityPlayer.class, this.taskOwner.getEntityBoundingBox().expand(var4, 4.0, var4), this.targetEntitySelector);
         var6.sort(this.theNearestAttackableTargetSorter);
         if (var6.isEmpty()) {
            return false;
         }

         this.player = (EntityPlayer)var6.get(0);
         return true;
      }

      @Override
      public void startExecuting() {
         this.field_179450_h = 5;
         this.field_179451_i = 0;
      }

      @Override
      public void resetTask() {
         this.player = null;
         this.enderman.setScreaming(false);
         this.enderman.getEntityAttribute(SharedMonsterAttributes.movementSpeed).removeModifier(EntityEnderman.b6);
         super.resetTask();
      }

      @Override
      public boolean continueExecuting() {
         if (this.player != null) {
            if (!this.enderman.shouldAttackPlayer(this.player)) {
               return false;
            }

            this.enderman.isAggressive = true;
            this.enderman.faceEntity(this.player, 10.0F, 10.0F);
            return true;
         } else {
            return super.continueExecuting();
         }
      }

      @Override
      public void updateTask() {
         if (this.player != null) {
            if (--this.field_179450_h <= 0) {
               this.targetEntity = this.player;
               this.player = null;
               super.startExecuting();
               this.enderman.playSound("mob.endermen.stare", 1.0F, 1.0F);
               this.enderman.setScreaming(true);
               IAttributeInstance var4 = this.enderman.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
               var4.applyModifier(EntityEnderman.b6);
            }
         } else {
            if (this.targetEntity != null) {
               if (this.targetEntity instanceof EntityPlayer && this.enderman.shouldAttackPlayer((EntityPlayer)this.targetEntity)) {
                  if (this.targetEntity.getDistanceSqToEntity(this.enderman) < 16.0) {
                     this.enderman.teleportRandomly();
                  }

                  this.field_179451_i = 0;
               } else if (this.targetEntity.getDistanceSqToEntity(this.enderman) > 256.0
                  && this.field_179451_i++ >= 30
                  && this.enderman.teleportToEntity(this.targetEntity)) {
                  this.field_179451_i = 0;
               }
            }

            super.updateTask();
         }
      }
   }

   class AIPlaceBlock extends EntityAIBase {
      private final EntityEnderman enderman = EntityEnderman.this;

      public AIPlaceBlock() {
      }

      @Override
      public boolean shouldExecute() {
         return this.enderman.worldObj.getGameRules().getBoolean("mobGriefing")
            && this.enderman.getHeldBlockState().getBlock().getMaterial() != Material.air
            && this.enderman.getRNG().nextInt(2000) == 0;
      }

      @Override
      public void updateTask() {
         Random var4 = this.enderman.getRNG();
         World var5 = this.enderman.worldObj;
         int var6 = MathHelper.floor_double(this.enderman.posX - 1.0 + var4.nextDouble() * 2.0);
         int var7 = MathHelper.floor_double(this.enderman.posY + var4.nextDouble() * 2.0);
         int var8 = MathHelper.floor_double(this.enderman.posZ - 1.0 + var4.nextDouble() * 2.0);
         BlockPos var9 = new BlockPos(var6, var7, var8);
         Block var10 = var5.getBlockState(var9).getBlock();
         Block var11 = var5.getBlockState(var9.down()).getBlock();
         if (this.func_179474_a(var5, var9, this.enderman.getHeldBlockState().getBlock(), var10, var11)) {
            var5.setBlockState(var9, this.enderman.getHeldBlockState(), 3);
            this.enderman.setHeldBlockState(Blocks.air.getDefaultState());
         }
      }

      private boolean func_179474_a(World world, BlockPos blockPos, Block block3, Block block4, Block block5) {
         return block3.canPlaceBlockAt(world, blockPos) && block4.getMaterial() == Material.air && block5.getMaterial() != Material.air && block5.isFullCube();
      }
   }

   class AITakeBlock extends EntityAIBase {
      private final EntityEnderman enderman = EntityEnderman.this;

      public AITakeBlock() {
      }

      @Override
      public boolean shouldExecute() {
         return this.enderman.worldObj.getGameRules().getBoolean("mobGriefing")
            && this.enderman.getHeldBlockState().getBlock().getMaterial() == Material.air
            && this.enderman.getRNG().nextInt(20) == 0;
      }

      @Override
      public void updateTask() {
         Random var4 = this.enderman.getRNG();
         World var5 = this.enderman.worldObj;
         int var6 = MathHelper.floor_double(this.enderman.posX - 2.0 + var4.nextDouble() * 4.0);
         int var7 = MathHelper.floor_double(this.enderman.posY + var4.nextDouble() * 3.0);
         int var8 = MathHelper.floor_double(this.enderman.posZ - 2.0 + var4.nextDouble() * 4.0);
         BlockPos var9 = new BlockPos(var6, var7, var8);
         IBlockState var10 = var5.getBlockState(var9);
         Block var11 = var10.getBlock();
         if (EntityEnderman.carriableBlocks.contains(var11)) {
            this.enderman.setHeldBlockState(var10);
            var5.setBlockState(var9, Blocks.air.getDefaultState());
         }
      }
   }
}
