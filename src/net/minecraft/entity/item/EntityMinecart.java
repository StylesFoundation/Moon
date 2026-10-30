package net.minecraft.entity.item;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailPowered;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.IWorldNameable;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class EntityMinecart extends Entity implements IWorldNameable {
   private boolean isInReverse;
   private String entityName;
   private static final int[][][] matrix = new int[][][]{
      {{0, 0, -1}, {0, 0, 1}},
      {{-1, 0, 0}, {1, 0, 0}},
      {{-1, -1, 0}, {1, 0, 0}},
      {{-1, 0, 0}, {1, -1, 0}},
      {{0, 0, -1}, {0, -1, 1}},
      {{0, -1, -1}, {0, 0, 1}},
      {{0, 0, 1}, {1, 0, 0}},
      {{0, 0, 1}, {-1, 0, 0}},
      {{0, 0, -1}, {-1, 0, 0}},
      {{0, 0, -1}, {1, 0, 0}}
   };
   private int turnProgress;
   private double minecartX;
   private double minecartY;
   private double minecartZ;
   private double minecartYaw;
   private double minecartPitch;
   private double velocityX;
   private double velocityY;
   private double velocityZ;

   public EntityMinecart(World world) {
      super(world);
      this.preventEntitySpawning = true;
      this.setSize(0.98F, 0.7F);
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static EntityMinecart getMinecart(World world, double double2, double double3, double double4, EntityMinecart.EnumMinecartType enumMinecartType) {
      switch (enumMinecartType) {
         case CHEST:
            return new EntityMinecartChest(world, double2, double3, double4);
         case FURNACE:
            return new EntityMinecartFurnace(world, double2, double3, double4);
         case TNT:
            return new EntityMinecartTNT(world, double2, double3, double4);
         case SPAWNER:
            return new EntityMinecartMobSpawner(world, double2, double3, double4);
         case HOPPER:
            return new EntityMinecartHopper(world, double2, double3, double4);
         case COMMAND_BLOCK:
            return new EntityMinecartCommandBlock(world, double2, double3, double4);
         default:
            return new EntityMinecartEmpty(world, double2, double3, double4);
      }
   }

   @Override
   protected boolean canTriggerWalking() {
      return false;
   }

   @Override
   protected void entityInit() {
      this.dataWatcher.addObject(17, 0);
      this.dataWatcher.addObject(18, 1);
      this.dataWatcher.addObject(19, 0.0F);
      this.dataWatcher.addObject(20, 0);
      this.dataWatcher.addObject(21, 6);
      this.dataWatcher.addObject(22, (byte)0);
   }

   @Override
   public AxisAlignedBB getCollisionBox(Entity entity) {
      return entity.canBePushed() ? entity.getEntityBoundingBox() : null;
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox() {
      return null;
   }

   @Override
   public boolean canBePushed() {
      return true;
   }

   public EntityMinecart(World world, double double2, double double3, double double4) {
      this(world);
      this.setPosition(double2, double3, double4);
      this.motionX = 0.0;
      this.motionY = 0.0;
      this.motionZ = 0.0;
      this.prevPosX = double2;
      this.prevPosY = double3;
      this.prevPosZ = double4;
   }

   @Override
   public double getMountedYOffset() {
      return 0.0;
   }

   @Override
   public boolean attackEntityFrom(DamageSource damageSource, float float2) {
      if (this.worldObj.isRemote || this.isDead) {
         return true;
      }

      if (this.isEntityInvulnerable(damageSource)) {
         return false;
      }

      this.setRollingDirection(-this.getRollingDirection());
      this.setRollingAmplitude(10);
      this.setBeenAttacked();
      this.setDamage(this.getDamage() + float2 * 10.0F);
      boolean var6 = damageSource.getEntity() instanceof EntityPlayer && ((EntityPlayer)damageSource.getEntity()).capabilities.isCreativeMode;
      if (var6 || this.getDamage() > 40.0F) {
         if (this.riddenByEntity != null) {
            this.riddenByEntity.mountEntity(null);
         }

         if (var6 && !this.hasCustomName()) {
            this.setDead();
         } else {
            this.killMinecart(damageSource);
         }
      }

      return true;
   }

   public void killMinecart(DamageSource damageSource) {
      this.setDead();
      if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
         ItemStack var5 = new ItemStack(Items.minecart, 1);
         if (this.entityName != null) {
            var5.setStackDisplayName(this.entityName);
         }

         this.entityDropItem(var5, 0.0F);
      }
   }

   @Override
   public void performHurtAnimation() {
      this.setRollingDirection(-this.getRollingDirection());
      this.setRollingAmplitude(10);
      this.setDamage(this.getDamage() + this.getDamage() * 10.0F);
   }

   @Override
   public boolean canBeCollidedWith() {
      return !this.isDead;
   }

   @Override
   public void setDead() {
      super.setDead();
   }

   @Override
   public void onUpdate() {
      if (this.getRollingAmplitude() > 0) {
         this.setRollingAmplitude(this.getRollingAmplitude() - 1);
      }

      if (this.getDamage() > 0.0F) {
         this.setDamage(this.getDamage() - 1.0F);
      }

      if (this.posY < -64.0) {
         this.kill();
      }

      if (!this.worldObj.isRemote && this.worldObj instanceof WorldServer) {
         this.worldObj.theProfiler.startSection("portal");
         MinecraftServer var4 = ((WorldServer)this.worldObj).getMinecraftServer();
         int var5 = this.getMaxInPortalTime();
         if (this.inPortal) {
            if (var4.getAllowNether()) {
               if (this.ridingEntity == null && this.portalCounter++ >= var5) {
                  this.portalCounter = var5;
                  this.timeUntilPortal = this.getPortalCooldown();
                  byte var6;
                  if (this.worldObj.provider.getDimensionId() == -1) {
                     var6 = 0;
                  } else {
                     var6 = -1;
                  }

                  this.travelToDimension(var6);
               }

               this.inPortal = false;
            }
         } else {
            if (this.portalCounter > 0) {
               this.portalCounter -= 4;
            }

            if (this.portalCounter < 0) {
               this.portalCounter = 0;
            }
         }

         if (this.timeUntilPortal > 0) {
            this.timeUntilPortal--;
         }

         this.worldObj.theProfiler.endSection();
      }

      if (this.worldObj.isRemote) {
         if (this.turnProgress > 0) {
            double var17 = this.posX + (this.minecartX - this.posX) / this.turnProgress;
            double var20 = this.posY + (this.minecartY - this.posY) / this.turnProgress;
            double var8 = this.posZ + (this.minecartZ - this.posZ) / this.turnProgress;
            double var10 = MathHelper.wrapAngleTo180_double(this.minecartYaw - this.rotationYaw);
            this.rotationYaw = (float)(this.rotationYaw + var10 / this.turnProgress);
            this.rotationPitch = (float)(this.rotationPitch + (this.minecartPitch - this.rotationPitch) / this.turnProgress);
            this.turnProgress--;
            this.setPosition(var17, var20, var8);
            this.setRotation(this.rotationYaw, this.rotationPitch);
         } else {
            this.setPosition(this.posX, this.posY, this.posZ);
            this.setRotation(this.rotationYaw, this.rotationPitch);
         }
      } else {
         this.prevPosX = this.posX;
         this.prevPosY = this.posY;
         this.prevPosZ = this.posZ;
         this.motionY -= 0.04F;
         int var18 = MathHelper.floor_double(this.posX);
         int var19 = MathHelper.floor_double(this.posY);
         int var21 = MathHelper.floor_double(this.posZ);
         if (BlockRailBase.isRailBlock(this.worldObj, new BlockPos(var18, var19 - 1, var21))) {
            var19--;
         }

         BlockPos var7 = new BlockPos(var18, var19, var21);
         IBlockState var22 = this.worldObj.getBlockState(var7);
         if (BlockRailBase.isRailBlock(var22)) {
            this.func_180460_a(var7, var22);
            if (var22.getBlock() == Blocks.activator_rail) {
               this.onActivatorRailPass(var18, var19, var21, var22.getValue(BlockRailPowered.POWERED));
            }
         } else {
            this.moveDerailedMinecart();
         }

         this.doBlockCollisions();
         this.rotationPitch = 0.0F;
         double var9 = this.prevPosX - this.posX;
         double var11 = this.prevPosZ - this.posZ;
         if (var9 * var9 + var11 * var11 > 0.001) {
            this.rotationYaw = (float)(MathHelper.atan2(var11, var9) * 180.0 / Math.PI);
            if (this.isInReverse) {
               this.rotationYaw += 180.0F;
            }
         }

         double var13 = MathHelper.wrapAngleTo180_float(this.rotationYaw - this.prevRotationYaw);
         if (var13 < -170.0 || var13 >= 170.0) {
            this.rotationYaw += 180.0F;
            this.isInReverse = !this.isInReverse;
         }

         this.setRotation(this.rotationYaw, this.rotationPitch);

         for (Entity var16 : this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(0.2F, 0.0, 0.2F))) {
            if (var16 != this.riddenByEntity && var16.canBePushed() && var16 instanceof EntityMinecart) {
               var16.applyEntityCollision(this);
            }
         }

         if (this.riddenByEntity != null && this.riddenByEntity.isDead) {
            if (this.riddenByEntity.ridingEntity == this) {
               this.riddenByEntity.ridingEntity = null;
            }

            this.riddenByEntity = null;
         }

         this.handleWaterMovement();
      }
   }

   protected double getMaximumSpeed() {
      return 0.4;
   }

   public void onActivatorRailPass(int integer1, int integer2, int integer3, boolean boolean4) {
   }

   protected void moveDerailedMinecart() {
      double var4 = this.getMaximumSpeed();
      this.motionX = MathHelper.clamp_double(this.motionX, -var4, var4);
      this.motionZ = MathHelper.clamp_double(this.motionZ, -var4, var4);
      if (this.onGround) {
         this.motionX *= 0.5;
         this.motionY *= 0.5;
         this.motionZ *= 0.5;
      }

      this.moveEntity(this.motionX, this.motionY, this.motionZ);
      if (!this.onGround) {
         this.motionX *= 0.95F;
         this.motionY *= 0.95F;
         this.motionZ *= 0.95F;
      }
   }

   protected void func_180460_a(BlockPos blockPos, IBlockState iBlockState) {
      this.fallDistance = 0.0F;
      Vec3 var6 = this.func_70489_a(this.posX, this.posY, this.posZ);
      this.posY = blockPos.getY();
      boolean var7 = false;
      boolean var8 = false;
      BlockRailBase var9 = (BlockRailBase)iBlockState.getBlock();
      if (var9 == Blocks.golden_rail) {
         var7 = iBlockState.getValue(BlockRailPowered.POWERED);
         var8 = !var7;
      }

      BlockRailBase.EnumRailDirection var12 = iBlockState.getValue(var9.getShapeProperty());
      switch (var12) {
         case ASCENDING_EAST :
            this.motionX -= 0.0078125;
            this.posY++;
            break;
         case ASCENDING_WEST :
            this.motionX += 0.0078125;
            this.posY++;
            break;
         case ASCENDING_NORTH :
            this.motionZ += 0.0078125;
            this.posY++;
            break;
         case ASCENDING_SOUTH :
            this.motionZ -= 0.0078125;
            this.posY++;
      }

      int[][] var13 = matrix[var12.getMetadata()];
      double var14 = var13[1][0] - var13[0][0];
      double var16 = var13[1][2] - var13[0][2];
      double var18 = Math.sqrt(var14 * var14 + var16 * var16);
      double var20 = this.motionX * var14 + this.motionZ * var16;
      if (var20 < 0.0) {
         var14 = -var14;
         var16 = -var16;
      }

      double var22 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
      if (var22 > 2.0) {
         var22 = 2.0;
      }

      this.motionX = var22 * var14 / var18;
      this.motionZ = var22 * var16 / var18;
      if (this.riddenByEntity instanceof EntityLivingBase) {
         double var24 = ((EntityLivingBase)this.riddenByEntity).moveForward;
         if (var24 > 0.0) {
            double var26 = -Math.sin(this.riddenByEntity.rotationYaw * (float) Math.PI / 180.0F);
            double var28 = Math.cos(this.riddenByEntity.rotationYaw * (float) Math.PI / 180.0F);
            double var30 = this.motionX * this.motionX + this.motionZ * this.motionZ;
            if (var30 < 0.01) {
               this.motionX += var26 * 0.1;
               this.motionZ += var28 * 0.1;
               var8 = false;
            }
         }
      }

      if (var8) {
         double var51 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
         if (var51 < 0.03) {
            this.motionX *= 0.0;
            this.motionY *= 0.0;
            this.motionZ *= 0.0;
         } else {
            this.motionX *= 0.5;
            this.motionY *= 0.0;
            this.motionZ *= 0.5;
         }
      }

      double var53 = blockPos.getX() + 0.5 + var13[0][0] * 0.5;
      double var54 = blockPos.getZ() + 0.5 + var13[0][2] * 0.5;
      double var55 = blockPos.getX() + 0.5 + var13[1][0] * 0.5;
      double var32 = blockPos.getZ() + 0.5 + var13[1][2] * 0.5;
      var14 = var55 - var53;
      var16 = var32 - var54;
      double var52;
      if (var14 == 0.0) {
         this.posX = blockPos.getX() + 0.5;
         var52 = this.posZ - blockPos.getZ();
      } else if (var16 == 0.0) {
         this.posZ = blockPos.getZ() + 0.5;
         var52 = this.posX - blockPos.getX();
      } else {
         double var34 = this.posX - var53;
         double var36 = this.posZ - var54;
         var52 = (var34 * var14 + var36 * var16) * 2.0;
      }

      this.posX = var53 + var14 * var52;
      this.posZ = var54 + var16 * var52;
      this.setPosition(this.posX, this.posY, this.posZ);
      double var56 = this.motionX;
      double var58 = this.motionZ;
      if (this.riddenByEntity != null) {
         var56 *= 0.75;
         var58 *= 0.75;
      }

      double var38 = this.getMaximumSpeed();
      var56 = MathHelper.clamp_double(var56, -var38, var38);
      var58 = MathHelper.clamp_double(var58, -var38, var38);
      this.moveEntity(var56, 0.0, var58);
      if (var13[0][1] != 0
         && MathHelper.floor_double(this.posX) - blockPos.getX() == var13[0][0]
         && MathHelper.floor_double(this.posZ) - blockPos.getZ() == var13[0][2]) {
         this.setPosition(this.posX, this.posY + var13[0][1], this.posZ);
      } else if (var13[1][1] != 0
         && MathHelper.floor_double(this.posX) - blockPos.getX() == var13[1][0]
         && MathHelper.floor_double(this.posZ) - blockPos.getZ() == var13[1][2]) {
         this.setPosition(this.posX, this.posY + var13[1][1], this.posZ);
      }

      this.applyDrag();
      Vec3 var40 = this.func_70489_a(this.posX, this.posY, this.posZ);
      if (var40 != null && var6 != null) {
         double var41 = (var6.yCoord - var40.yCoord) * 0.05;
         var22 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
         if (var22 > 0.0) {
            this.motionX = this.motionX / var22 * (var22 + var41);
            this.motionZ = this.motionZ / var22 * (var22 + var41);
         }

         this.setPosition(this.posX, var40.yCoord, this.posZ);
      }

      int var60 = MathHelper.floor_double(this.posX);
      int var42 = MathHelper.floor_double(this.posZ);
      if (var60 != blockPos.getX() || var42 != blockPos.getZ()) {
         var22 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
         this.motionX = var22 * (var60 - blockPos.getX());
         this.motionZ = var22 * (var42 - blockPos.getZ());
      }

      if (var7) {
         double var43 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
         if (var43 > 0.01) {
            double var45 = 0.06;
            this.motionX = this.motionX + this.motionX / var43 * var45;
            this.motionZ = this.motionZ + this.motionZ / var43 * var45;
         } else if (var12 == BlockRailBase.EnumRailDirection.EAST_WEST) {
            if (this.worldObj.getBlockState(blockPos.west()).getBlock().isNormalCube()) {
               this.motionX = 0.02;
            } else if (this.worldObj.getBlockState(blockPos.east()).getBlock().isNormalCube()) {
               this.motionX = -0.02;
            }
         } else if (var12 == BlockRailBase.EnumRailDirection.NORTH_SOUTH) {
            if (this.worldObj.getBlockState(blockPos.north()).getBlock().isNormalCube()) {
               this.motionZ = 0.02;
            } else if (this.worldObj.getBlockState(blockPos.south()).getBlock().isNormalCube()) {
               this.motionZ = -0.02;
            }
         }
      }
   }

   protected void applyDrag() {
      if (this.riddenByEntity != null) {
         this.motionX *= 0.997F;
         this.motionY *= 0.0;
         this.motionZ *= 0.997F;
      } else {
         this.motionX *= 0.96F;
         this.motionY *= 0.0;
         this.motionZ *= 0.96F;
      }
   }

   @Override
   public void setPosition(double double1, double double2, double double3) {
      this.posX = double1;
      this.posY = double2;
      this.posZ = double3;
      float var7 = this.width / 2.0F;
      float var8 = this.height;
      this.setEntityBoundingBox(new AxisAlignedBB(double1 - var7, double2, double3 - var7, double1 + var7, double2 + var8, double3 + var7));
   }

   public Vec3 func_70495_a(double double1, double double2, double double3, double double4) {
      int var12 = MathHelper.floor_double(double1);
      int var13 = MathHelper.floor_double(double2);
      int var14 = MathHelper.floor_double(double3);
      if (BlockRailBase.isRailBlock(this.worldObj, new BlockPos(var12, var13 - 1, var14))) {
         var13--;
      }

      IBlockState var15 = this.worldObj.getBlockState(new BlockPos(var12, var13, var14));
      if (BlockRailBase.isRailBlock(var15)) {
         BlockRailBase.EnumRailDirection var16 = var15.getValue(((BlockRailBase)var15.getBlock()).getShapeProperty());
         double2 = var13;
         if (var16.isAscending()) {
            double2 = var13 + 1;
         }

         int[][] var17 = matrix[var16.getMetadata()];
         double var18 = var17[1][0] - var17[0][0];
         double var20 = var17[1][2] - var17[0][2];
         double var22 = Math.sqrt(var18 * var18 + var20 * var20);
         var18 /= var22;
         var20 /= var22;
         double1 += var18 * double4;
         double3 += var20 * double4;
         if (var17[0][1] != 0 && MathHelper.floor_double(double1) - var12 == var17[0][0] && MathHelper.floor_double(double3) - var14 == var17[0][2]) {
            double2 += var17[0][1];
         } else if (var17[1][1] != 0 && MathHelper.floor_double(double1) - var12 == var17[1][0] && MathHelper.floor_double(double3) - var14 == var17[1][2]) {
            double2 += var17[1][1];
         }

         return this.func_70489_a(double1, double2, double3);
      } else {
         return null;
      }
   }

   public Vec3 func_70489_a(double double1, double double2, double double3) {
      int var10 = MathHelper.floor_double(double1);
      int var11 = MathHelper.floor_double(double2);
      int var12 = MathHelper.floor_double(double3);
      if (BlockRailBase.isRailBlock(this.worldObj, new BlockPos(var10, var11 - 1, var12))) {
         var11--;
      }

      IBlockState var13 = this.worldObj.getBlockState(new BlockPos(var10, var11, var12));
      if (BlockRailBase.isRailBlock(var13)) {
         BlockRailBase.EnumRailDirection var14 = var13.getValue(((BlockRailBase)var13.getBlock()).getShapeProperty());
         int[][] var15 = matrix[var14.getMetadata()];
         double var18 = var10 + 0.5 + var15[0][0] * 0.5;
         double var20 = var11 + 0.0625 + var15[0][1] * 0.5;
         double var22 = var12 + 0.5 + var15[0][2] * 0.5;
         double var24 = var10 + 0.5 + var15[1][0] * 0.5;
         double var26 = var11 + 0.0625 + var15[1][1] * 0.5;
         double var28 = var12 + 0.5 + var15[1][2] * 0.5;
         double var30 = var24 - var18;
         double var32 = (var26 - var20) * 2.0;
         double var34 = var28 - var22;
         double var16;
         if (var30 == 0.0) {
            double var10000 = var10 + 0.5;
            var16 = double3 - var12;
         } else if (var34 == 0.0) {
            double var43 = var12 + 0.5;
            var16 = double1 - var10;
         } else {
            double var36 = double1 - var18;
            double var38 = double3 - var22;
            var16 = (var36 * var30 + var38 * var34) * 2.0;
         }

         double1 = var18 + var30 * var16;
         double2 = var20 + var32 * var16;
         double3 = var22 + var34 * var16;
         if (var32 < 0.0) {
            double2++;
         }

         if (var32 > 0.0) {
            double2 += 0.5;
         }

         return new Vec3(double1, double2, double3);
      } else {
         return null;
      }
   }

   @Override
   protected void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      if (nBTTagCompound.getBoolean("CustomDisplayTile")) {
         int var5 = nBTTagCompound.getInteger("DisplayData");
         if (nBTTagCompound.hasKey("DisplayTile", 8)) {
            Block var6 = Block.getBlockFromName(nBTTagCompound.getString("DisplayTile"));
            if (var6 == null) {
               this.func_174899_a(Blocks.air.getDefaultState());
            } else {
               this.func_174899_a(var6.getStateFromMeta(var5));
            }
         } else {
            Block var7 = Block.getBlockById(nBTTagCompound.getInteger("DisplayTile"));
            if (var7 == null) {
               this.func_174899_a(Blocks.air.getDefaultState());
            } else {
               this.func_174899_a(var7.getStateFromMeta(var5));
            }
         }

         this.setDisplayTileOffset(nBTTagCompound.getInteger("DisplayOffset"));
      }

      if (nBTTagCompound.hasKey("CustomName", 8) && nBTTagCompound.getString("CustomName").length() > 0) {
         this.entityName = nBTTagCompound.getString("CustomName");
      }
   }

   @Override
   protected void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      if (this.hasDisplayTile()) {
         nBTTagCompound.setBoolean("CustomDisplayTile", true);
         IBlockState var5 = this.getDisplayTile();
         ResourceLocation var6 = Block.blockRegistry.getNameForObject(var5.getBlock());
         nBTTagCompound.setString("DisplayTile", var6 == null ? "" : var6.toString());
         nBTTagCompound.setInteger("DisplayData", var5.getBlock().getMetaFromState(var5));
         nBTTagCompound.setInteger("DisplayOffset", this.getDisplayTileOffset());
      }

      if (this.entityName != null && this.entityName.length() > 0) {
         nBTTagCompound.setString("CustomName", this.entityName);
      }
   }

   @Override
   public void applyEntityCollision(Entity entity) {
      if (!this.worldObj.isRemote && !entity.noClip && !this.noClip && entity != this.riddenByEntity) {
         if (entity instanceof EntityLivingBase
            && !(entity instanceof EntityPlayer)
            && !(entity instanceof EntityIronGolem)
            && this.getMinecartType() == EntityMinecart.EnumMinecartType.RIDEABLE
            && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.01
            && this.riddenByEntity == null
            && entity.ridingEntity == null) {
            entity.mountEntity(this);
         }

         double var5 = entity.posX - this.posX;
         double var7 = entity.posZ - this.posZ;
         double var9 = var5 * var5 + var7 * var7;
         if (var9 >= 1.0E-4F) {
            var9 = MathHelper.sqrt_double(var9);
            var5 /= var9;
            var7 /= var9;
            double var11 = 1.0 / var9;
            if (var11 > 1.0) {
               var11 = 1.0;
            }

            var5 *= var11;
            var7 *= var11;
            var5 *= 0.1F;
            var7 *= 0.1F;
            var5 *= 1.0F - this.entityCollisionReduction;
            var7 *= 1.0F - this.entityCollisionReduction;
            var5 *= 0.5;
            var7 *= 0.5;
            if (entity instanceof EntityMinecart) {
               double var13 = entity.posX - this.posX;
               double var15 = entity.posZ - this.posZ;
               Vec3 var17 = new Vec3(var13, 0.0, var15).normalize();
               Vec3 var18 = new Vec3(
                     MathHelper.cos(this.rotationYaw * (float) Math.PI / 180.0F), 0.0, MathHelper.sin(this.rotationYaw * (float) Math.PI / 180.0F)
                  )
                  .normalize();
               double var19 = Math.abs(var17.dotProduct(var18));
               if (var19 < 0.8F) {
                  return;
               }

               double var21 = entity.motionX + this.motionX;
               double var23 = entity.motionZ + this.motionZ;
               if (((EntityMinecart)entity).getMinecartType() == EntityMinecart.EnumMinecartType.FURNACE
                  && this.getMinecartType() != EntityMinecart.EnumMinecartType.FURNACE) {
                  this.motionX *= 0.2F;
                  this.motionZ *= 0.2F;
                  this.addVelocity(entity.motionX - var5, 0.0, entity.motionZ - var7);
                  entity.motionX *= 0.95F;
                  entity.motionZ *= 0.95F;
               } else if (((EntityMinecart)entity).getMinecartType() != EntityMinecart.EnumMinecartType.FURNACE
                  && this.getMinecartType() == EntityMinecart.EnumMinecartType.FURNACE) {
                  entity.motionX *= 0.2F;
                  entity.motionZ *= 0.2F;
                  entity.addVelocity(this.motionX + var5, 0.0, this.motionZ + var7);
                  this.motionX *= 0.95F;
                  this.motionZ *= 0.95F;
               } else {
                  var21 /= 2.0;
                  var23 /= 2.0;
                  this.motionX *= 0.2F;
                  this.motionZ *= 0.2F;
                  this.addVelocity(var21 - var5, 0.0, var23 - var7);
                  entity.motionX *= 0.2F;
                  entity.motionZ *= 0.2F;
                  entity.addVelocity(var21 + var5, 0.0, var23 + var7);
               }
            } else {
               this.addVelocity(-var5, 0.0, -var7);
               entity.addVelocity(var5 / 4.0, 0.0, var7 / 4.0);
            }
         }
      }
   }

   @Override
   public void setPositionAndRotation2(double double1, double double2, double double3, float float4, float float5, int integer, boolean boolean7) {
      this.minecartX = double1;
      this.minecartY = double2;
      this.minecartZ = double3;
      this.minecartYaw = float4;
      this.minecartPitch = float5;
      this.turnProgress = integer + 2;
      this.motionX = this.velocityX;
      this.motionY = this.velocityY;
      this.motionZ = this.velocityZ;
   }

   @Override
   public void setVelocity(double double1, double double2, double double3) {
      this.velocityX = this.motionX = double1;
      this.velocityY = this.motionY = double2;
      this.velocityZ = this.motionZ = double3;
   }

   public void setDamage(float float1) {
      this.dataWatcher.updateObject(19, float1);
   }

   public float getDamage() {
      return this.dataWatcher.getWatchableObjectFloat(19);
   }

   public void setRollingAmplitude(int integer) {
      this.dataWatcher.updateObject(17, integer);
   }

   public int getRollingAmplitude() {
      return this.dataWatcher.getWatchableObjectInt(17);
   }

   public void setRollingDirection(int integer) {
      this.dataWatcher.updateObject(18, integer);
   }

   public int getRollingDirection() {
      return this.dataWatcher.getWatchableObjectInt(18);
   }

   public abstract EntityMinecart.EnumMinecartType getMinecartType();

   public IBlockState getDisplayTile() {
      return !this.hasDisplayTile() ? this.getDefaultDisplayTile() : Block.getStateById(this.getDataWatcher().getWatchableObjectInt(20));
   }

   public IBlockState getDefaultDisplayTile() {
      return Blocks.air.getDefaultState();
   }

   public int getDisplayTileOffset() {
      return !this.hasDisplayTile() ? this.getDefaultDisplayTileOffset() : this.getDataWatcher().getWatchableObjectInt(21);
   }

   public int getDefaultDisplayTileOffset() {
      return 6;
   }

   public void func_174899_a(IBlockState iBlockState) {
      this.getDataWatcher().updateObject(20, Block.getStateId(iBlockState));
      this.setHasDisplayTile(true);
   }

   public void setDisplayTileOffset(int integer) {
      this.getDataWatcher().updateObject(21, integer);
      this.setHasDisplayTile(true);
   }

   public boolean hasDisplayTile() {
      return this.getDataWatcher().getWatchableObjectByte(22) == 1;
   }

   public void setHasDisplayTile(boolean boolean1) {
      this.getDataWatcher().updateObject(22, (byte)(boolean1 ? 1 : 0));
   }

   @Override
   public void setCustomNameTag(String string) {
      this.entityName = string;
   }

   @Override
   public String getName() {
      return this.entityName != null ? this.entityName : super.getName();
   }

   @Override
   public boolean hasCustomName() {
      return this.entityName != null;
   }

   @Override
   public String getCustomNameTag() {
      return this.entityName;
   }

   @Override
   public IChatComponent getDisplayName() {
      if (this.hasCustomName()) {
         ChatComponentText var4 = new ChatComponentText(this.entityName);
         var4.getChatStyle().setChatHoverEvent(this.getHoverEvent());
         var4.getChatStyle().setInsertion(this.getUniqueID().toString());
         return var4;
      } else {
         ChatComponentTranslation var3 = new ChatComponentTranslation(this.getName());
         var3.getChatStyle().setChatHoverEvent(this.getHoverEvent());
         var3.getChatStyle().setInsertion(this.getUniqueID().toString());
         return var3;
      }
   }

   public enum EnumMinecartType {
      RIDEABLE(0, "MinecartRideable"),
      CHEST(1, "MinecartChest"),
      FURNACE(2, "MinecartFurnace"),
      TNT(3, "MinecartTNT"),
      SPAWNER(4, "MinecartSpawner"),
      HOPPER(5, "MinecartHopper"),
      COMMAND_BLOCK(6, "MinecartCommandBlock");

      private static final Map<Integer, EntityMinecart.EnumMinecartType> ID_LOOKUP = Maps.newHashMap();
      private final int networkID;
      private final String name;

      EnumMinecartType(int integer3, String string4) {
         this.networkID = integer3;
         this.name = string4;
      }

      public int getNetworkID() {
         return this.networkID;
      }

      public String getName() {
         return this.name;
      }

      public static EntityMinecart.EnumMinecartType byNetworkID(int integer) {
         EntityMinecart.EnumMinecartType var4 = ID_LOOKUP.get(integer);
         return var4 == null ? RIDEABLE : var4;
      }

      private static EntityMinecart.EnumMinecartType[] Method443() {
         return new EntityMinecart.EnumMinecartType[]{RIDEABLE, CHEST, FURNACE, TNT, SPAWNER, HOPPER, COMMAND_BLOCK};
      }

      static {
         for (EntityMinecart.EnumMinecartType var16 : values()) {
            ID_LOOKUP.put(var16.getNetworkID(), var16);
         }
      }

      public static int Method444() {
         return 0;
      }
   }
}
