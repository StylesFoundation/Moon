package net.minecraft.entity.projectile;

import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityFishHook extends Entity {
   private static final List<WeightedRandomFishable> JUNK = Arrays.asList(
      new WeightedRandomFishable(new ItemStack(Items.leather_boots), 10).setMaxDamagePercent(0.9F),
      new WeightedRandomFishable(new ItemStack(Items.leather), 10),
      new WeightedRandomFishable(new ItemStack(Items.bone), 10),
      new WeightedRandomFishable(new ItemStack(Items.potionitem), 10),
      new WeightedRandomFishable(new ItemStack(Items.string), 5),
      new WeightedRandomFishable(new ItemStack(Items.fishing_rod), 2).setMaxDamagePercent(0.9F),
      new WeightedRandomFishable(new ItemStack(Items.bowl), 10),
      new WeightedRandomFishable(new ItemStack(Items.stick), 5),
      new WeightedRandomFishable(new ItemStack(Items.dye, 10, EnumDyeColor.BLACK.getDyeDamage()), 1),
      new WeightedRandomFishable(new ItemStack(Blocks.tripwire_hook), 10),
      new WeightedRandomFishable(new ItemStack(Items.rotten_flesh), 10)
   );
   private static final List<WeightedRandomFishable> TREASURE = Arrays.asList(
      new WeightedRandomFishable(new ItemStack(Blocks.waterlily), 1),
      new WeightedRandomFishable(new ItemStack(Items.name_tag), 1),
      new WeightedRandomFishable(new ItemStack(Items.saddle), 1),
      new WeightedRandomFishable(new ItemStack(Items.bow), 1).setMaxDamagePercent(0.25F).setEnchantable(),
      new WeightedRandomFishable(new ItemStack(Items.fishing_rod), 1).setMaxDamagePercent(0.25F).setEnchantable(),
      new WeightedRandomFishable(new ItemStack(Items.book), 1).setEnchantable()
   );
   private static final List<WeightedRandomFishable> FISH = Arrays.asList(
      new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.COD.getMetadata()), 60),
      new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.SALMON.getMetadata()), 25),
      new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.CLOWNFISH.getMetadata()), 2),
      new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.PUFFERFISH.getMetadata()), 13)
   );
   private int xTile = -1;
   private int yTile = -1;
   private int zTile = -1;
   private Block inTile;
   private boolean inGround;
   public int shake;
   public EntityPlayer angler;
   private int ticksInGround;
   private int ticksInAir;
   private int ticksCatchable;
   private int ticksCaughtDelay;
   private int ticksCatchableDelay;
   private float fishApproachAngle;
   public Entity caughtEntity;
   private int fishPosRotationIncrements;
   private double fishX;
   private double fishY;
   private double fishZ;
   private double fishYaw;
   private double fishPitch;
   private double clientMotionX;
   private double clientMotionY;
   private double clientMotionZ;

   public static List<WeightedRandomFishable> func_174855_j() {
      return FISH;
   }

   public EntityFishHook(World world) {
      super(world);
      this.setSize(0.25F, 0.25F);
      this.ignoreFrustumCheck = true;
   }

   public EntityFishHook(World world, double double2, double double3, double double4, EntityPlayer entityPlayer) {
      this(world);
      this.setPosition(double2, double3, double4);
      this.ignoreFrustumCheck = true;
      this.angler = entityPlayer;
      entityPlayer.fishEntity = this;
   }

   public EntityFishHook(World world, EntityPlayer entityPlayer) {
      super(world);
      this.ignoreFrustumCheck = true;
      this.angler = entityPlayer;
      this.angler.fishEntity = this;
      this.setSize(0.25F, 0.25F);
      this.setLocationAndAngles(
         entityPlayer.posX, entityPlayer.posY + entityPlayer.getEyeHeight(), entityPlayer.posZ, entityPlayer.rotationYaw, entityPlayer.rotationPitch
      );
      this.posX = this.posX - MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
      this.posY -= 0.1F;
      this.posZ = this.posZ - MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
      this.setPosition(this.posX, this.posY, this.posZ);
      this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI) * 0.4F;
      this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI) * 0.4F;
      this.motionY = -MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI) * 0.4F;
      this.handleHookCasting(this.motionX, this.motionY, this.motionZ, 1.5F, 1.0F);
   }

   @Override
   protected void entityInit() {
   }

   @Override
   public boolean isInRangeToRenderDist(double double1) {
      double var6 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0;
      if (Double.isNaN(var6)) {
         var6 = 4.0;
      }

      var6 *= 64.0;
      return double1 < var6 * var6;
   }

   public void handleHookCasting(double double1, double double2, double double3, float float4, float float5) {
      float var9 = MathHelper.sqrt_double(double1 * double1 + double2 * double2 + double3 * double3);
      double1 /= var9;
      double2 /= var9;
      double3 /= var9;
      double1 += this.rand.nextGaussian() * 0.0075F * float5;
      double2 += this.rand.nextGaussian() * 0.0075F * float5;
      double3 += this.rand.nextGaussian() * 0.0075F * float5;
      double1 *= float4;
      double2 *= float4;
      double3 *= float4;
      this.motionX = double1;
      this.motionY = double2;
      this.motionZ = double3;
      float var10 = MathHelper.sqrt_double(double1 * double1 + double3 * double3);
      this.prevRotationYaw = this.rotationYaw = (float)(MathHelper.atan2(double1, double3) * 180.0 / Math.PI);
      this.prevRotationPitch = this.rotationPitch = (float)(MathHelper.atan2(double2, var10) * 180.0 / Math.PI);
      this.ticksInGround = 0;
   }

   @Override
   public void setPositionAndRotation2(double double1, double double2, double double3, float float4, float float5, int integer, boolean boolean7) {
      this.fishX = double1;
      this.fishY = double2;
      this.fishZ = double3;
      this.fishYaw = float4;
      this.fishPitch = float5;
      this.fishPosRotationIncrements = integer;
      this.motionX = this.clientMotionX;
      this.motionY = this.clientMotionY;
      this.motionZ = this.clientMotionZ;
   }

   @Override
   public void setVelocity(double double1, double double2, double double3) {
      this.clientMotionX = this.motionX = double1;
      this.clientMotionY = this.motionY = double2;
      this.clientMotionZ = this.motionZ = double3;
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.fishPosRotationIncrements > 0) {
         double var4 = this.posX + (this.fishX - this.posX) / this.fishPosRotationIncrements;
         double var6 = this.posY + (this.fishY - this.posY) / this.fishPosRotationIncrements;
         double var8 = this.posZ + (this.fishZ - this.posZ) / this.fishPosRotationIncrements;
         double var10 = MathHelper.wrapAngleTo180_double(this.fishYaw - this.rotationYaw);
         this.rotationYaw = (float)(this.rotationYaw + var10 / this.fishPosRotationIncrements);
         this.rotationPitch = (float)(this.rotationPitch + (this.fishPitch - this.rotationPitch) / this.fishPosRotationIncrements);
         this.fishPosRotationIncrements--;
         this.setPosition(var4, var6, var8);
         this.setRotation(this.rotationYaw, this.rotationPitch);
      } else {
         if (!this.worldObj.isRemote) {
            ItemStack var31 = this.angler.getCurrentEquippedItem();
            if (this.angler.isDead
               || !this.angler.isEntityAlive()
               || var31 == null
               || var31.getItem() != Items.fishing_rod
               || this.getDistanceSqToEntity(this.angler) > 1024.0) {
               this.setDead();
               this.angler.fishEntity = null;
               return;
            }

            if (this.caughtEntity != null) {
               if (!this.caughtEntity.isDead) {
                  this.posX = this.caughtEntity.posX;
                  double var35 = this.caughtEntity.height;
                  this.posY = this.caughtEntity.getEntityBoundingBox().minY + var35 * 0.8;
                  this.posZ = this.caughtEntity.posZ;
                  return;
               }

               this.caughtEntity = null;
            }
         }

         if (this.shake > 0) {
            this.shake--;
         }

         if (this.inGround) {
            if (this.worldObj.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock() == this.inTile) {
               this.ticksInGround++;
               if (this.ticksInGround == 1200) {
                  this.setDead();
               }

               return;
            }

            this.inGround = false;
            this.motionX = this.motionX * (this.rand.nextFloat() * 0.2F);
            this.motionY = this.motionY * (this.rand.nextFloat() * 0.2F);
            this.motionZ = this.motionZ * (this.rand.nextFloat() * 0.2F);
            this.ticksInGround = 0;
            this.ticksInAir = 0;
         } else {
            this.ticksInAir++;
         }

         Vec3 var32 = new Vec3(this.posX, this.posY, this.posZ);
         Vec3 var5 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
         MovingObjectPosition var36 = this.worldObj.rayTraceBlocks(var32, var5);
         var32 = new Vec3(this.posX, this.posY, this.posZ);
         var5 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
         if (var36 != null) {
            var5 = new Vec3(var36.hitVec.xCoord, var36.hitVec.yCoord, var36.hitVec.zCoord);
         }

         Entity var7 = null;
         List<Entity> var37 = this.worldObj
            .getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
         double var9 = 0.0;

         for (Entity var12 : var37) {
            if (var12.canBeCollidedWith() && (var12 != this.angler || this.ticksInAir >= 5)) {
               AxisAlignedBB var14 = var12.getEntityBoundingBox().expand(0.3F, 0.3F, 0.3F);
               MovingObjectPosition var15 = var14.calculateIntercept(var32, var5);
               if (var15 != null) {
                  double var16 = var32.squareDistanceTo(var15.hitVec);
                  if (var16 < var9 || var9 == 0.0) {
                     var7 = var12;
                     var9 = var16;
                  }
               }
            }
         }

         if (var7 != null) {
            var36 = new MovingObjectPosition(var7);
         }

         if (var36 != null) {
            if (var36.entityHit != null) {
               if (var36.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.angler), 0.0F)) {
                  this.caughtEntity = var36.entityHit;
               }
            } else {
               this.inGround = true;
            }
         }

         if (!this.inGround) {
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            float var38 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
            this.rotationPitch = (float)(MathHelper.atan2(this.motionY, var38) * 180.0 / Math.PI);

            while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
               this.prevRotationPitch -= 360.0F;
            }

            while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
               this.prevRotationPitch += 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
               this.prevRotationYaw -= 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
               this.prevRotationYaw += 360.0F;
            }

            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
            float var39 = 0.92F;
            if (this.onGround || this.isCollidedHorizontally) {
               var39 = 0.5F;
            }

            byte var13 = 5;
            double var40 = 0.0;

            for (int var41 = 0; var41 < var13; var41++) {
               AxisAlignedBB var17 = this.getEntityBoundingBox();
               double var18 = var17.maxY - var17.minY;
               double var20 = var17.minY + var18 * var41 / 5.0;
               double var22 = var17.minY + var18 * (var41 + 1) / 5.0;
               AxisAlignedBB var24 = new AxisAlignedBB(var17.minX, var20, var17.minZ, var17.maxX, var22, var17.maxZ);
               if (this.worldObj.isAABBInMaterial(var24, Material.water)) {
                  var40 += 0.2;
               }
            }

            if (!this.worldObj.isRemote && var40 > 0.0) {
               WorldServer var42 = (WorldServer)this.worldObj;
               int var44 = 1;
               BlockPos var45 = new BlockPos(this).up();
               if (this.rand.nextFloat() < 0.25F && this.worldObj.isRainingAt(var45)) {
                  var44 = 2;
               }

               if (this.rand.nextFloat() < 0.5F && !this.worldObj.canSeeSky(var45)) {
                  var44--;
               }

               if (this.ticksCatchable > 0) {
                  this.ticksCatchable--;
                  if (this.ticksCatchable <= 0) {
                     this.ticksCaughtDelay = 0;
                     this.ticksCatchableDelay = 0;
                  }
               } else if (this.ticksCatchableDelay > 0) {
                  this.ticksCatchableDelay -= var44;
                  if (this.ticksCatchableDelay <= 0) {
                     this.motionY -= 0.2F;
                     this.playSound("random.splash", 0.25F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
                     float var19 = MathHelper.floor_double(this.getEntityBoundingBox().minY);
                     var42.spawnParticle(
                        EnumParticleTypes.WATER_BUBBLE, this.posX, var19 + 1.0F, this.posZ, (int)(1.0F + this.width * 20.0F), this.width, 0.0, this.width, 0.2F
                     );
                     var42.spawnParticle(
                        EnumParticleTypes.WATER_WAKE, this.posX, var19 + 1.0F, this.posZ, (int)(1.0F + this.width * 20.0F), this.width, 0.0, this.width, 0.2F
                     );
                     this.ticksCatchable = MathHelper.getRandomIntegerInRange(this.rand, 10, 30);
                  } else {
                     this.fishApproachAngle = (float)(this.fishApproachAngle + this.rand.nextGaussian() * 4.0);
                     float var46 = this.fishApproachAngle * (float) (Math.PI / 180.0);
                     float var48 = MathHelper.sin(var46);
                     float var21 = MathHelper.cos(var46);
                     double var51 = this.posX + var48 * this.ticksCatchableDelay * 0.1F;
                     double var53 = MathHelper.floor_double(this.getEntityBoundingBox().minY) + 1.0F;
                     double var26 = this.posZ + var21 * this.ticksCatchableDelay * 0.1F;
                     Block var28 = var42.getBlockState(new BlockPos((int)var51, (int)var53 - 1, (int)var26)).getBlock();
                     if (var28 == Blocks.water || var28 == Blocks.flowing_water) {
                        if (this.rand.nextFloat() < 0.15F) {
                           var42.spawnParticle(EnumParticleTypes.WATER_BUBBLE, var51, var53 - 0.1F, var26, 1, var48, 0.1, var21, 0.0);
                        }

                        float var29 = var48 * 0.04F;
                        float var30 = var21 * 0.04F;
                        var42.spawnParticle(EnumParticleTypes.WATER_WAKE, var51, var53, var26, 0, var30, 0.01, -var29, 1.0);
                        var42.spawnParticle(EnumParticleTypes.WATER_WAKE, var51, var53, var26, 0, -var30, 0.01, var29, 1.0);
                     }
                  }
               } else if (this.ticksCaughtDelay > 0) {
                  this.ticksCaughtDelay -= var44;
                  float var47 = 0.15F;
                  if (this.ticksCaughtDelay < 20) {
                     var47 = (float)(0.15F + (20 - this.ticksCaughtDelay) * 0.05);
                  } else if (this.ticksCaughtDelay < 40) {
                     var47 = (float)(0.15F + (40 - this.ticksCaughtDelay) * 0.02);
                  } else if (this.ticksCaughtDelay < 60) {
                     var47 = (float)(0.15F + (60 - this.ticksCaughtDelay) * 0.01);
                  }

                  if (this.rand.nextFloat() < var47) {
                     float var49 = MathHelper.randomFloatClamp(this.rand, 0.0F, 360.0F) * (float) (Math.PI / 180.0);
                     float var50 = MathHelper.randomFloatClamp(this.rand, 25.0F, 60.0F);
                     double var52 = this.posX + MathHelper.sin(var49) * var50 * 0.1F;
                     double var54 = MathHelper.floor_double(this.getEntityBoundingBox().minY) + 1.0F;
                     double var55 = this.posZ + MathHelper.cos(var49) * var50 * 0.1F;
                     Block var56 = var42.getBlockState(new BlockPos((int)var52, (int)var54 - 1, (int)var55)).getBlock();
                     if (var56 == Blocks.water || var56 == Blocks.flowing_water) {
                        var42.spawnParticle(EnumParticleTypes.WATER_SPLASH, var52, var54, var55, 2 + this.rand.nextInt(2), 0.1F, 0.0, 0.1F, 0.0);
                     }
                  }

                  if (this.ticksCaughtDelay <= 0) {
                     this.fishApproachAngle = MathHelper.randomFloatClamp(this.rand, 0.0F, 360.0F);
                     this.ticksCatchableDelay = MathHelper.getRandomIntegerInRange(this.rand, 20, 80);
                  }
               } else {
                  this.ticksCaughtDelay = MathHelper.getRandomIntegerInRange(this.rand, 100, 900);
                  this.ticksCaughtDelay = this.ticksCaughtDelay - EnchantmentHelper.getLureModifier(this.angler) * 20 * 5;
               }

               if (this.ticksCatchable > 0) {
                  this.motionY = this.motionY - this.rand.nextFloat() * this.rand.nextFloat() * this.rand.nextFloat() * 0.2;
               }
            }

            double var43 = var40 * 2.0 - 1.0;
            this.motionY += 0.04F * var43;
            if (var40 > 0.0) {
               var39 = (float)(var39 * 0.9);
               this.motionY *= 0.8;
            }

            this.motionX *= var39;
            this.motionY *= var39;
            this.motionZ *= var39;
            this.setPosition(this.posX, this.posY, this.posZ);
         }
      }
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      nBTTagCompound.setShort("xTile", (short)this.xTile);
      nBTTagCompound.setShort("yTile", (short)this.yTile);
      nBTTagCompound.setShort("zTile", (short)this.zTile);
      ResourceLocation var5 = Block.blockRegistry.getNameForObject(this.inTile);
      nBTTagCompound.setString("inTile", var5 == null ? "" : var5.toString());
      nBTTagCompound.setByte("shake", (byte)this.shake);
      nBTTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      this.xTile = nBTTagCompound.getShort("xTile");
      this.yTile = nBTTagCompound.getShort("yTile");
      this.zTile = nBTTagCompound.getShort("zTile");
      if (nBTTagCompound.hasKey("inTile", 8)) {
         this.inTile = Block.getBlockFromName(nBTTagCompound.getString("inTile"));
      } else {
         this.inTile = Block.getBlockById(nBTTagCompound.getByte("inTile") & 255);
      }

      this.shake = nBTTagCompound.getByte("shake") & 255;
      this.inGround = nBTTagCompound.getByte("inGround") == 1;
   }

   public int handleHookRetraction() {
      if (this.worldObj.isRemote) {
         return 0;
      }

      byte var4 = 0;
      if (this.caughtEntity != null) {
         double var5 = this.angler.posX - this.posX;
         double var7 = this.angler.posY - this.posY;
         double var9 = this.angler.posZ - this.posZ;
         double var11 = MathHelper.sqrt_double(var5 * var5 + var7 * var7 + var9 * var9);
         this.caughtEntity.motionX += var5 * 0.1;
         this.caughtEntity.motionY = this.caughtEntity.motionY + (var7 * 0.1 + MathHelper.sqrt_double(var11) * 0.08);
         this.caughtEntity.motionZ += var9 * 0.1;
         var4 = 3;
      } else if (this.ticksCatchable > 0) {
         EntityItem var14 = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, this.getFishingResult());
         double var6 = this.angler.posX - this.posX;
         double var8 = this.angler.posY - this.posY;
         double var10 = this.angler.posZ - this.posZ;
         double var12 = MathHelper.sqrt_double(var6 * var6 + var8 * var8 + var10 * var10);
         var14.motionX = var6 * 0.1;
         var14.motionY = var8 * 0.1 + MathHelper.sqrt_double(var12) * 0.08;
         var14.motionZ = var10 * 0.1;
         this.worldObj.spawnEntityInWorld(var14);
         this.angler
            .worldObj
            .spawnEntityInWorld(
               new EntityXPOrb(this.angler.worldObj, this.angler.posX, this.angler.posY + 0.5, this.angler.posZ + 0.5, this.rand.nextInt(6) + 1)
            );
         var4 = 1;
      }

      if (this.inGround) {
         var4 = 2;
      }

      this.setDead();
      this.angler.fishEntity = null;
      return var4;
   }

   private ItemStack getFishingResult() {
      float var4 = this.worldObj.rand.nextFloat();
      int var5 = EnchantmentHelper.getLuckOfSeaModifier(this.angler);
      int var6 = EnchantmentHelper.getLureModifier(this.angler);
      float var7 = 0.1F - var5 * 0.025F - var6 * 0.01F;
      float var8 = 0.05F + var5 * 0.01F - var6 * 0.01F;
      var7 = MathHelper.clamp_float(var7, 0.0F, 1.0F);
      var8 = MathHelper.clamp_float(var8, 0.0F, 1.0F);
      if (var4 < var7) {
         this.angler.triggerAchievement(StatList.junkFishedStat);
         return WeightedRandom.getRandomItem(this.rand, JUNK).getItemStack(this.rand);
      } else {
         var4 -= var7;
         if (var4 < var8) {
            this.angler.triggerAchievement(StatList.treasureFishedStat);
            return WeightedRandom.getRandomItem(this.rand, TREASURE).getItemStack(this.rand);
         } else {
            float var10000 = var4 - var8;
            this.angler.triggerAchievement(StatList.fishCaughtStat);
            return WeightedRandom.getRandomItem(this.rand, FISH).getItemStack(this.rand);
         }
      }
   }

   @Override
   public void setDead() {
      super.setDead();
      if (this.angler != null) {
         this.angler.fishEntity = null;
      }
   }
}
