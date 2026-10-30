package net.minecraft.entity;

import com.moon.Moon;
import com.moon.events.impl.player.LookEvent;
import com.moon.events.impl.player.MoveEvent;
import com.moon.events.impl.player.SafeWalkEvent;
import com.moon.events.impl.player.StepEvent;
import com.moon.events.impl.player.StrafeEvent;
import com.moon.module.impl.visual.Camera;
import com.moon.util.math.Position;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.event.HoverEvent;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.lwjgl.util.vector.Vector4f;

public abstract class Entity implements ICommandSender {
   private static final AxisAlignedBB ZERO_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
   private static int nextEntityID;
   private final CommandResultStats cmdResultStats;
   public double renderDistanceWeight;
   public boolean preventEntitySpawning;
   public Entity riddenByEntity;
   public Entity ridingEntity;
   public boolean forceSpawn;
   public World worldObj;
   public double j;
   public double prevPosX;
   public double prevPosY;
   public double prevPosZ;
   public double posX;
   public double posY;
   public double posZ;
   public double q;
   public double r;
   public double s;
   public double motionX;
   public double motionY;
   public double motionZ;
   public float rotationYaw;
   public float rotationPitch;
   public float prevRotationYaw;
   public float prevRotationPitch;
   public boolean onGround;
   public boolean isCollidedHorizontally;
   public boolean isCollidedVertically;
   public boolean isCollided;
   public boolean velocityChanged;
   public boolean isDead;
   public float width;
   public float height;
   public float prevDistanceWalkedModified;
   public float distanceWalkedModified;
   public float distanceWalkedOnStepModified;
   public float fallDistance;
   public double lastTickPosX;
   public double lastTickPosY;
   public double lastTickPosZ;
   public float stepHeight;
   public boolean noClip;
   public float entityCollisionReduction;
   public int ticksExisted;
   public int fireResistance;
   public int hurtResistantTime;
   public boolean addedToChunk;
   public int W;
   public int chunkCoordY;
   public int Y;
   public int serverPosX;
   public int serverPosY;
   public int serverPosZ;
   public boolean ignoreFrustumCheck;
   public boolean isAirBorne;
   public int timeUntilPortal;
   public int dimension;
   protected boolean isInWeb;
   protected Random rand;
   protected boolean inWater;
   protected boolean firstUpdate;
   protected boolean isImmuneToFire;
   protected DataWatcher dataWatcher;
   protected boolean inPortal;
   protected int portalCounter;
   protected BlockPos lastPortalPos;
   protected Vec3 lastPortalVec;
   protected EnumFacing teleportDirection;
   protected UUID entityUniqueID;
   private int entityId;
   private AxisAlignedBB boundingBox;
   private boolean isOutsideBorder;
   private int nextStepDistance;
   private int fire;
   private double entityRiderPitchDelta;
   private double entityRiderYawDelta;
   private boolean invulnerable;
   private Vector4f aA;
   protected static Camera aB;
   public boolean aC;

   public Entity(World world) {
      this.entityId = nextEntityID++;
      this.renderDistanceWeight = 1.0;
      this.boundingBox = ZERO_AABB;
      this.width = 0.6F;
      this.height = 1.8F;
      this.nextStepDistance = 1;
      this.rand = new Random();
      this.fireResistance = 1;
      this.firstUpdate = true;
      this.entityUniqueID = MathHelper.getRandomUuid(this.rand);
      this.cmdResultStats = new CommandResultStats();
      this.worldObj = world;
      this.setPosition(0.0, 0.0, 0.0);
      if (world != null) {
         this.dimension = world.provider.getDimensionId();
      }

      this.dataWatcher = new DataWatcher(this);
      this.dataWatcher.addObject(0, (byte)0);
      this.dataWatcher.addObject(1, (short)300);
      this.dataWatcher.addObject(3, (byte)0);
      this.dataWatcher.addObject(2, "");
      this.dataWatcher.addObject(4, (byte)0);
      this.entityInit();
   }

   public int getEntityId() {
      return this.entityId;
   }

   public void setEntityId(int integer) {
      this.entityId = integer;
   }

   public void onKillCommand() {
      this.setDead();
   }

   protected abstract void entityInit();

   public DataWatcher getDataWatcher() {
      return this.dataWatcher;
   }

   @Override
   public boolean equals(Object object) {
      return object instanceof Entity && ((Entity)object).entityId == this.entityId;
   }

   @Override
   public int hashCode() {
      return this.entityId;
   }

   protected void preparePlayerToSpawn() {
      if (this.worldObj != null) {
         while (true) {
            if (this.posY > 0.0 && this.posY < 256.0) {
               this.setPosition(this.posX, this.posY, this.posZ);
               if (!this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty()) {
                  this.posY++;
                  continue;
               }
            }

            this.motionX = this.motionY = this.motionZ = 0.0;
            this.rotationPitch = 0.0F;
            break;
         }
      }
   }

   public void setDead() {
      this.isDead = true;
   }

   protected void setSize(float float1, float float2) {
      if (float1 != this.width || float2 != this.height) {
         float var6 = this.width;
         this.width = float1;
         this.height = float2;
         this.setEntityBoundingBox(
            new AxisAlignedBB(
               this.getEntityBoundingBox().minX,
               this.getEntityBoundingBox().minY,
               this.getEntityBoundingBox().minZ,
               this.getEntityBoundingBox().minX + this.width,
               this.getEntityBoundingBox().minY + this.height,
               this.getEntityBoundingBox().minZ + this.width
            )
         );
         if (this.width > var6 && !this.firstUpdate && !this.worldObj.isRemote) {
            this.moveEntity(var6 - this.width, 0.0, var6 - this.width);
         }
      }
   }

   protected void setRotation(float float1, float float2) {
      this.rotationYaw = float1 % 360.0F;
      this.rotationPitch = float2 % 360.0F;
   }

   public void setPosition(double double1, double double2, double double3) {
      this.posX = double1;
      this.posY = double2;
      this.posZ = double3;
      float var7 = this.width / 2.0F;
      float var8 = this.height;
      this.setEntityBoundingBox(new AxisAlignedBB(double1 - var7, double2, double3 - var7, double1 + var7, double2 + var8, double3 + var7));
   }

   public void setAngles(float float1, float float2) {
      float var3 = this.rotationPitch;
      float var4 = this.rotationYaw;
      this.rotationYaw = (float)(this.rotationYaw + float1 * 0.15);
      this.rotationPitch = (float)(this.rotationPitch - float2 * 0.15);
      this.rotationPitch = MathHelper.clamp_float(this.rotationPitch, -90.0F, 90.0F);
      this.prevRotationPitch = this.prevRotationPitch + (this.rotationPitch - var3);
      this.prevRotationYaw = this.prevRotationYaw + (this.rotationYaw - var4);
   }

   public void onUpdate() {
      this.onEntityUpdate();
   }

   public void onEntityUpdate() {
      this.worldObj.theProfiler.startSection("entityBaseTick");
      if (this.ridingEntity != null && this.ridingEntity.isDead) {
         this.ridingEntity = null;
      }

      this.prevDistanceWalkedModified = this.distanceWalkedModified;
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      this.prevRotationPitch = this.rotationPitch;
      this.prevRotationYaw = this.rotationYaw;
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

      this.spawnRunningParticles();
      this.handleWaterMovement();
      if (this.worldObj.isRemote) {
         this.fire = 0;
      } else if (this.fire > 0) {
         if (this.isImmuneToFire) {
            this.fire -= 4;
            if (this.fire < 0) {
               this.fire = 0;
            }
         } else {
            if (this.fire % 20 == 0) {
               this.attackEntityFrom(DamageSource.onFire, 1.0F);
            }

            this.fire--;
         }
      }

      if (this.isInLava()) {
         this.setOnFireFromLava();
         this.fallDistance *= 0.5F;
      }

      if (this.posY < -64.0) {
         this.kill();
      }

      if (!this.worldObj.isRemote) {
         this.setFlag(0, this.fire > 0);
      }

      this.firstUpdate = false;
      this.worldObj.theProfiler.endSection();
   }

   public int getMaxInPortalTime() {
      return 0;
   }

   protected void setOnFireFromLava() {
      if (!this.isImmuneToFire) {
         this.attackEntityFrom(DamageSource.lava, 4.0F);
         this.setFire(15);
      }
   }

   public void setFire(int integer) {
      int var5 = integer * 20;
      var5 = EnchantmentProtection.getFireTimeForEntity(this, var5);
      if (this.fire < var5) {
         this.fire = var5;
      }
   }

   public void extinguish() {
      this.fire = 0;
   }

   protected void kill() {
      this.setDead();
   }

   public boolean isOffsetPositionInLiquid(double double1, double double2, double double3) {
      AxisAlignedBB var7 = this.getEntityBoundingBox().offset(double1, double2, double3);
      return this.isLiquidPresentInAABB(var7);
   }

   private boolean isLiquidPresentInAABB(AxisAlignedBB axisAlignedBB) {
      return this.worldObj.getCollidingBoundingBoxes(this, axisAlignedBB).isEmpty() && !this.worldObj.isAnyLiquid(axisAlignedBB);
   }

   public void moveEntity(double double1, double double2, double double3) {
      if (this instanceof EntityPlayerSP var10) {
         MoveEvent var59 = new MoveEvent(double1, double2, double3, var10.moveForward, var10.moveStrafing, var10.rotationYaw);
         Moon.getInstance().getEventBus().publish(var59);
         double1 = var59.getX();
         double2 = var59.getY();
         double3 = var59.getZ();
      }

      if (this.noClip) {
         this.setEntityBoundingBox(this.getEntityBoundingBox().offset(double1, double2, double3));
         this.resetPositionToBB();
      } else {
         this.worldObj.theProfiler.startSection("move");
         double var58 = this.posX;
         double var12 = this.posY;
         double var14 = this.posZ;
         if (this.isInWeb) {
            this.isInWeb = false;
            double1 *= 0.25;
            double2 *= 0.05F;
            double3 *= 0.25;
            this.motionX = 0.0;
            this.motionY = 0.0;
            this.motionZ = 0.0;
         }

         double var16 = double1;
         double var18 = double2;
         double var20 = double3;
         boolean var22 = this.onGround && this.isSneaking() && this instanceof EntityPlayer;
         SafeWalkEvent var23 = new SafeWalkEvent();
         if (!var22 && this == Minecraft.getMinecraft().thePlayer) {
            Moon.getInstance().getEventBus().publish(var23);
         }

         if (var22 || var23.isCancelled() && this instanceof EntityPlayer) {
            for (;
               double1 != 0.0 && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().offset(double1, -1.0, 0.0)).isEmpty();
               var16 = double1
            ) {
               if (double1 < 0.05 && double1 >= -0.05) {
                  double1 = 0.0;
               } else if (double1 > 0.0) {
                  double1 -= 0.05;
               } else {
                  double1 += 0.05;
               }
            }

            for (;
               double3 != 0.0 && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().offset(0.0, -1.0, double3)).isEmpty();
               var20 = double3
            ) {
               if (double3 < 0.05 && double3 >= -0.05) {
                  double3 = 0.0;
               } else if (double3 > 0.0) {
                  double3 -= 0.05;
               } else {
                  double3 += 0.05;
               }
            }

            for (;
               double1 != 0.0
                  && double3 != 0.0
                  && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().offset(double1, -1.0, double3)).isEmpty();
               var20 = double3
            ) {
               if (double1 < 0.05 && double1 >= -0.05) {
                  double1 = 0.0;
               } else if (double1 > 0.0) {
                  double1 -= 0.05;
               } else {
                  double1 += 0.05;
               }

               var16 = double1;
               if (double3 < 0.05 && double3 >= -0.05) {
                  double3 = 0.0;
               } else if (double3 > 0.0) {
                  double3 -= 0.05;
               } else {
                  double3 += 0.05;
               }
            }
         }

         List var24 = this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().addCoord(double1, double2, double3));
         AxisAlignedBB var25 = this.getEntityBoundingBox();
         Iterator var26 = var24.iterator();

         while (var26.hasNext()) {
            double2 = ((AxisAlignedBB)var26.next()).calculateYOffset(this.getEntityBoundingBox(), double2);
         }

         this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, double2, 0.0));
         boolean var60 = this.onGround || var18 != double2 && var18 < 0.0;
         Iterator var27 = var24.iterator();

         while (var27.hasNext()) {
            double1 = ((AxisAlignedBB)var27.next()).calculateXOffset(this.getEntityBoundingBox(), double1);
         }

         this.setEntityBoundingBox(this.getEntityBoundingBox().offset(double1, 0.0, 0.0));
         var27 = var24.iterator();

         while (var27.hasNext()) {
            double3 = ((AxisAlignedBB)var27.next()).calculateZOffset(this.getEntityBoundingBox(), double3);
         }

         this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, 0.0, double3));
         if (this.stepHeight > 0.0F && var60 && (var16 != double1 || var20 != double3)) {
            StepEvent var62 = new StepEvent(this, this.stepHeight, true);
            Moon.getInstance().getEventBus().publish(var62);
            this.stepHeight = var62.getHeight();
            if (var62.isCancelled()) {
               return;
            }

            double var28 = double1;
            double var30 = double2;
            double var32 = double3;
            AxisAlignedBB var34 = this.getEntityBoundingBox();
            this.setEntityBoundingBox(var25);
            double2 = this.stepHeight;
            List<AxisAlignedBB> var35 = this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().addCoord(var16, double2, var20));
            AxisAlignedBB var36 = this.getEntityBoundingBox();
            AxisAlignedBB var37 = var36.addCoord(var16, 0.0, var20);
            double var38 = double2;

            for (AxisAlignedBB var41 : var35) {
               var38 = var41.calculateYOffset(var37, var38);
            }

            var36 = var36.offset(0.0, var38, 0.0);
            double var76 = var16;
            Iterator var42 = var35.iterator();

            while (var42.hasNext()) {
               var76 = ((AxisAlignedBB)var42.next()).calculateXOffset(var36, var76);
            }

            var36 = var36.offset(var76, 0.0, 0.0);
            double var77 = var20;
            Iterator var44 = var35.iterator();

            while (var44.hasNext()) {
               var77 = ((AxisAlignedBB)var44.next()).calculateZOffset(var36, var77);
            }

            var36 = var36.offset(0.0, 0.0, var77);
            AxisAlignedBB var78 = this.getEntityBoundingBox();
            double var45 = double2;

            for (AxisAlignedBB var48 : var35) {
               var45 = var48.calculateYOffset(var78, var45);
            }

            AxisAlignedBB var79 = var78.offset(0.0, var45, 0.0);
            double var82 = var16;
            Iterator var49 = var35.iterator();

            while (var49.hasNext()) {
               var82 = ((AxisAlignedBB)var49.next()).calculateXOffset(var79, var82);
            }

            AxisAlignedBB var80 = var79.offset(var82, 0.0, 0.0);
            double var83 = var20;
            Iterator var51 = var35.iterator();

            while (var51.hasNext()) {
               var83 = ((AxisAlignedBB)var51.next()).calculateZOffset(var80, var83);
            }

            AxisAlignedBB var81 = var80.offset(0.0, 0.0, var83);
            double var84 = var76 * var76 + var77 * var77;
            double var53 = var82 * var82 + var83 * var83;
            if (var84 > var53) {
               double1 = var76;
               double3 = var77;
               double2 = -var38;
               this.setEntityBoundingBox(var36);
            } else {
               double1 = var82;
               double3 = var83;
               double2 = -var45;
               this.setEntityBoundingBox(var81);
            }

            Iterator var55 = var35.iterator();

            while (var55.hasNext()) {
               double2 = ((AxisAlignedBB)var55.next()).calculateYOffset(this.getEntityBoundingBox(), double2);
            }

            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0, double2, 0.0));
            if (var28 * var28 + var32 * var32 >= double1 * double1 + double3 * double3) {
               double1 = var28;
               double2 = var30;
               double3 = var32;
               this.setEntityBoundingBox(var34);
            } else {
               Moon.getInstance().getEventBus().publish(new StepEvent(this, this.stepHeight, false));
            }
         }

         this.worldObj.theProfiler.endSection();
         this.worldObj.theProfiler.startSection("rest");
         this.resetPositionToBB();
         this.isCollidedHorizontally = var16 != double1 || var20 != double3;
         this.isCollidedVertically = var18 != double2;
         this.onGround = this.isCollidedVertically && var18 < 0.0;
         this.isCollided = this.isCollidedHorizontally || this.isCollidedVertically;
         int var63 = MathHelper.floor_double(this.posX);
         int var64 = MathHelper.floor_double(this.posY - 0.2F);
         int var29 = MathHelper.floor_double(this.posZ);
         BlockPos var65 = new BlockPos(var63, var64, var29);
         Block var31 = this.worldObj.getBlockState(var65).getBlock();
         if (var31.getMaterial() == Material.air) {
            Block var66 = this.worldObj.getBlockState(var65.down()).getBlock();
            if (var66 instanceof BlockFence || var66 instanceof BlockWall || var66 instanceof BlockFenceGate) {
               var31 = var66;
               var65 = var65.down();
            }
         }

         this.updateFallState(double2, this.onGround, var31, var65);
         if (var16 != double1) {
            this.motionX = 0.0;
         }

         if (var20 != double3) {
            this.motionZ = 0.0;
         }

         if (var18 != double2) {
            var31.onLanded(this.worldObj, this);
         }

         if (this.canTriggerWalking() && !var22 && this.ridingEntity == null) {
            double var67 = this.posX - var58;
            double var69 = this.posY - var12;
            double var74 = this.posZ - var14;
            if (var31 != Blocks.ladder) {
               var69 = 0.0;
            }

            if (var31 != null && this.onGround) {
               var31.onEntityCollidedWithBlock(this.worldObj, var65, this);
            }

            this.distanceWalkedModified = (float)(this.distanceWalkedModified + MathHelper.sqrt_double(var67 * var67 + var74 * var74) * 0.6);
            this.distanceWalkedOnStepModified = (float)(
               this.distanceWalkedOnStepModified + MathHelper.sqrt_double(var67 * var67 + var69 * var69 + var74 * var74) * 0.6
            );
            if (this.distanceWalkedOnStepModified > this.nextStepDistance && var31.getMaterial() != Material.air) {
               this.nextStepDistance = (int)this.distanceWalkedOnStepModified + 1;
               if (this.isInWater()) {
                  float var75 = MathHelper.sqrt_double(this.motionX * this.motionX * 0.2F + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.2F)
                     * 0.35F;
                  if (var75 > 1.0F) {
                     var75 = 1.0F;
                  }

                  this.playSound(this.getSwimSound(), var75, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
               }

               this.playStepSound(var65, var31);
            }
         }

         try {
            this.doBlockCollisions();
         } catch (Throwable var56) {
            CrashReport var33 = CrashReport.makeCrashReport(var56, "Checking entity block collision");
            CrashReportCategory var70 = var33.makeCategory("Entity being checked for collision");
            this.addEntityCrashInfo(var70);
            throw new ReportedException(var33);
         }

         boolean var68 = this.isWet();
         if (this.worldObj.isFlammableWithin(this.getEntityBoundingBox().contract(0.001, 0.001, 0.001))) {
            this.dealFireDamage(1);
            if (!var68) {
               this.fire++;
               if (this.fire == 0) {
                  this.setFire(8);
               }
            }
         } else if (this.fire <= 0) {
            this.fire = -this.fireResistance;
         }

         if (var68 && this.fire > 0) {
            this.playSound("random.fizz", 0.7F, 1.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
            this.fire = -this.fireResistance;
         }

         this.worldObj.theProfiler.endSection();
      }
   }

   private void resetPositionToBB() {
      this.posX = (this.getEntityBoundingBox().minX + this.getEntityBoundingBox().maxX) / 2.0;
      this.posY = this.getEntityBoundingBox().minY;
      this.posZ = (this.getEntityBoundingBox().minZ + this.getEntityBoundingBox().maxZ) / 2.0;
   }

   protected String getSwimSound() {
      return "game.neutral.swim";
   }

   protected void doBlockCollisions() {
      BlockPos var4 = new BlockPos(this.getEntityBoundingBox().minX + 0.001, this.getEntityBoundingBox().minY + 0.001, this.getEntityBoundingBox().minZ + 0.001);
      BlockPos var5 = new BlockPos(this.getEntityBoundingBox().maxX - 0.001, this.getEntityBoundingBox().maxY - 0.001, this.getEntityBoundingBox().maxZ - 0.001);
      if (this.worldObj.Method326(var4, var5)) {
         for (int var6 = var4.getX(); var6 <= var5.getX(); var6++) {
            for (int var7 = var4.getY(); var7 <= var5.getY(); var7++) {
               for (int var8 = var4.getZ(); var8 <= var5.getZ(); var8++) {
                  BlockPos var9 = new BlockPos(var6, var7, var8);
                  IBlockState var10 = this.worldObj.getBlockState(var9);

                  try {
                     var10.getBlock().onEntityCollidedWithBlock(this.worldObj, var9, var10, this);
                  } catch (Throwable var14) {
                     CrashReport var12 = CrashReport.makeCrashReport(var14, "Colliding entity with block");
                     CrashReportCategory var13 = var12.makeCategory("Block being collided with");
                     CrashReportCategory.addBlockInfo(var13, var9, var10);
                     throw new ReportedException(var12);
                  }
               }
            }
         }
      }
   }

   protected void playStepSound(BlockPos blockPos, Block block) {
      Block.SoundType var6 = block.stepSound;
      if (this.worldObj.getBlockState(blockPos.up()).getBlock() == Blocks.snow_layer) {
         var6 = Blocks.snow_layer.stepSound;
         this.playSound(var6.getStepSound(), var6.getVolume() * 0.15F, var6.getFrequency());
      } else if (!block.getMaterial().isLiquid()) {
         this.playSound(var6.getStepSound(), var6.getVolume() * 0.15F, var6.getFrequency());
      }
   }

   public void playSound(String string, float float2, float float3) {
      if (!this.isSilent()) {
         this.worldObj.playSoundAtEntity(this, string, float2, float3);
      }
   }

   public boolean isSilent() {
      return this.dataWatcher.getWatchableObjectByte(4) == 1;
   }

   public void setSilent(boolean boolean1) {
      this.dataWatcher.updateObject(4, (byte)(boolean1 ? 1 : 0));
   }

   protected boolean canTriggerWalking() {
      return true;
   }

   protected void updateFallState(double double1, boolean boolean2, Block block, BlockPos blockPos) {
      if (boolean2) {
         if (this.fallDistance > 0.0F) {
            if (block != null) {
               block.onFallenUpon(this.worldObj, blockPos, this, this.fallDistance);
            } else {
               this.fall(this.fallDistance, 1.0F);
            }

            this.fallDistance = 0.0F;
         }
      } else if (double1 < 0.0) {
         this.fallDistance = (float)(this.fallDistance - double1);
      }
   }

   public AxisAlignedBB getCollisionBoundingBox() {
      return null;
   }

   protected void dealFireDamage(int integer) {
      if (!this.isImmuneToFire) {
         this.attackEntityFrom(DamageSource.inFire, integer);
      }
   }

   public final boolean isImmuneToFire() {
      return this.isImmuneToFire;
   }

   public void fall(float float1, float float2) {
      if (this.riddenByEntity != null) {
         this.riddenByEntity.fall(float1, float2);
      }
   }

   public boolean isWet() {
      return this.inWater
         || this.worldObj.isRainingAt(new BlockPos(this.posX, this.posY, this.posZ))
         || this.worldObj.isRainingAt(new BlockPos(this.posX, this.posY + this.height, this.posZ));
   }

   public boolean isInWater() {
      return this.inWater;
   }

   public boolean handleWaterMovement() {
      if (this.worldObj.handleMaterialAcceleration(this.getEntityBoundingBox().expand(0.0, -0.4F, 0.0).contract(0.001, 0.001, 0.001), Material.water, this)) {
         if (!this.inWater && !this.firstUpdate) {
            this.resetHeight();
         }

         this.fallDistance = 0.0F;
         this.inWater = true;
         this.fire = 0;
      } else {
         this.inWater = false;
      }

      return this.inWater;
   }

   protected void resetHeight() {
      float var4 = MathHelper.sqrt_double(this.motionX * this.motionX * 0.2F + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.2F) * 0.2F;
      if (var4 > 1.0F) {
         var4 = 1.0F;
      }

      this.playSound(this.getSplashSound(), var4, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
      float var5 = MathHelper.floor_double(this.getEntityBoundingBox().minY);

      for (int var6 = 0; var6 < 1.0F + this.width * 20.0F; var6++) {
         float var7 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
         float var8 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
         this.worldObj
            .spawnParticle(
               EnumParticleTypes.WATER_BUBBLE,
               this.posX + var7,
               var5 + 1.0F,
               this.posZ + var8,
               this.motionX,
               this.motionY - this.rand.nextFloat() * 0.2F,
               this.motionZ
            );
      }

      for (int var9 = 0; var9 < 1.0F + this.width * 20.0F; var9++) {
         float var10 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
         float var11 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
         this.worldObj
            .spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + var10, var5 + 1.0F, this.posZ + var11, this.motionX, this.motionY, this.motionZ);
      }
   }

   public void spawnRunningParticles() {
      if (this.isSprinting() && !this.isInWater()) {
         this.createRunningParticles();
      }
   }

   protected void createRunningParticles() {
      int var3 = MathHelper.floor_double(this.posX);
      int var4 = MathHelper.floor_double(this.posY - 0.2F);
      int var5 = MathHelper.floor_double(this.posZ);
      BlockPos var6 = new BlockPos(var3, var4, var5);
      IBlockState var7 = this.worldObj.getBlockState(var6);
      Block var8 = var7.getBlock();
      if (var8.getRenderType() != -1) {
         this.worldObj
            .spawnParticle(
               EnumParticleTypes.BLOCK_CRACK,
               this.posX + (this.rand.nextFloat() - 0.5) * this.width,
               this.getEntityBoundingBox().minY + 0.1,
               this.posZ + (this.rand.nextFloat() - 0.5) * this.width,
               -this.motionX * 4.0,
               1.5,
               -this.motionZ * 4.0,
               Block.getStateId(var7)
            );
      }
   }

   protected String getSplashSound() {
      return "game.neutral.swim.splash";
   }

   public boolean isInsideOfMaterial(Material material) {
      double var5 = this.posY + this.getEyeHeight();
      BlockPos var7 = new BlockPos(this.posX, var5, this.posZ);
      IBlockState var8 = this.worldObj.getBlockState(var7);
      Block var9 = var8.getBlock();
      if (var9.getMaterial() == material) {
         float var10 = BlockLiquid.getLiquidHeightPercent(var8.getBlock().getMetaFromState(var8)) - 0.11111111F;
         float var11 = var7.getY() + 1 - var10;
         return var5 < var11;
      } else {
         return false;
      }
   }

   public boolean isInLava() {
      return this.worldObj.isMaterialInBB(this.getEntityBoundingBox().expand(-0.1F, -0.4F, -0.1F), Material.lava);
   }

   public void moveFlying(float strafe, float forward, float float3) {
      float var7 = this.rotationYaw;
      if (this instanceof EntityPlayerSP) {
         StrafeEvent var8 = new StrafeEvent(this.rotationYaw, forward, strafe);
         Moon.getInstance().getEventBus().publish(var8);
         var7 = var8.getYaw();
         forward = var8.getForward();
         strafe = var8.getStrafe();
      }

      float var13 = strafe * strafe + forward * forward;
      if (var13 >= 1.0E-4F) {
         var13 = MathHelper.sqrt_float(var13);
         if (var13 < 1.0F) {
            var13 = 1.0F;
         }

         var13 = float3 / var13;
         strafe *= var13;
         forward *= var13;
         float var9 = MathHelper.sin(var7 * (float) Math.PI / 180.0F);
         float var10 = MathHelper.cos(var7 * (float) Math.PI / 180.0F);
         this.motionX += strafe * var10 - forward * var9;
         this.motionZ += forward * var10 + strafe * var9;
      }
   }

   public int getBrightnessForRender(float float1) {
      BlockPos var5 = new BlockPos(this.posX, this.posY + this.getEyeHeight(), this.posZ);
      return this.worldObj.Method322(var5) ? this.worldObj.getCombinedLight(var5, 0) : 0;
   }

   public float getBrightness(float float1) {
      BlockPos var5 = new BlockPos(this.posX, this.posY + this.getEyeHeight(), this.posZ);
      return this.worldObj.Method322(var5) ? this.worldObj.getLightBrightness(var5) : 0.0F;
   }

   public void setWorld(World world) {
      this.worldObj = world;
   }

   public void setPositionAndRotation(double double1, double double2, double double3, float float4, float float5) {
      this.prevPosX = this.posX = double1;
      this.prevPosY = this.posY = double2;
      this.prevPosZ = this.posZ = double3;
      this.prevRotationYaw = this.rotationYaw = float4;
      this.prevRotationPitch = this.rotationPitch = float5;
      double var12 = this.prevRotationYaw - float4;
      if (var12 < -180.0) {
         this.prevRotationYaw += 360.0F;
      }

      if (var12 >= 180.0) {
         this.prevRotationYaw -= 360.0F;
      }

      this.setPosition(this.posX, this.posY, this.posZ);
      this.setRotation(float4, float5);
   }

   public void moveToBlockPosAndAngles(BlockPos blockPos, float float2, float float3) {
      this.setLocationAndAngles(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, float2, float3);
   }

   public void setLocationAndAngles(double double1, double double2, double double3, float float4, float float5) {
      this.lastTickPosX = this.prevPosX = this.posX = double1;
      this.lastTickPosY = this.prevPosY = this.posY = double2;
      this.lastTickPosZ = this.prevPosZ = this.posZ = double3;
      this.rotationYaw = float4;
      this.rotationPitch = float5;
      this.setPosition(this.posX, this.posY, this.posZ);
   }

   public float getDistanceToEntity(Entity entity) {
      float var2 = (float)(this.posX - entity.posX);
      float var3 = (float)(this.posY - entity.posY);
      float var4 = (float)(this.posZ - entity.posZ);
      return MathHelper.sqrt_float(var2 * var2 + var3 * var3 + var4 * var4);
   }

   public double getDistanceSq(double double1, double double2, double double3) {
      double var7 = this.posX - double1;
      double var9 = this.posY - double2;
      double var11 = this.posZ - double3;
      return var7 * var7 + var9 * var9 + var11 * var11;
   }

   public double getDistanceSq(BlockPos blockPos) {
      return blockPos.distanceSq(this.posX, this.posY, this.posZ);
   }

   public double getDistanceSqToCenter(BlockPos blockPos) {
      return blockPos.distanceSqToCenter(this.posX, this.posY, this.posZ);
   }

   public double getDistance(double double1, double double2, double double3) {
      double var7 = this.posX - double1;
      double var9 = this.posY - double2;
      double var11 = this.posZ - double3;
      return MathHelper.sqrt_double(var7 * var7 + var9 * var9 + var11 * var11);
   }

   public double getDistanceSqToEntity(Entity entity) {
      double var2 = this.posX - entity.posX;
      double var4 = this.posY - entity.posY;
      double var6 = this.posZ - entity.posZ;
      return var2 * var2 + var4 * var4 + var6 * var6;
   }

   public void onCollideWithPlayer(EntityPlayer entityPlayer) {
   }

   public void applyEntityCollision(Entity entity) {
      if (entity.riddenByEntity != this && entity.ridingEntity != this && !entity.noClip && !this.noClip) {
         double var5 = entity.posX - this.posX;
         double var7 = entity.posZ - this.posZ;
         double var9 = MathHelper.abs_max(var5, var7);
         if (var9 >= 0.01F) {
            var9 = MathHelper.sqrt_double(var9);
            var5 /= var9;
            var7 /= var9;
            double var11 = 1.0 / var9;
            if (var11 > 1.0) {
               var11 = 1.0;
            }

            var5 *= var11;
            var7 *= var11;
            var5 *= 0.05F;
            var7 *= 0.05F;
            var5 *= 1.0F - this.entityCollisionReduction;
            var7 *= 1.0F - this.entityCollisionReduction;
            if (this.riddenByEntity == null) {
               this.addVelocity(-var5, 0.0, -var7);
            }

            if (entity.riddenByEntity == null) {
               entity.addVelocity(var5, 0.0, var7);
            }
         }
      }
   }

   public void addVelocity(double double1, double double2, double double3) {
      this.motionX += double1;
      this.motionY += double2;
      this.motionZ += double3;
      this.isAirBorne = true;
   }

   public void Method936(Position n5) {
      this.motionX = this.motionX + n5.getX();
      this.motionY = this.motionY + n5.getY();
      this.motionZ = this.motionZ + n5.getZ();
   }

   protected void setBeenAttacked() {
      this.velocityChanged = true;
   }

   public boolean attackEntityFrom(DamageSource damageSource, float float2) {
      if (this.isEntityInvulnerable(damageSource)) {
         return false;
      }

      this.setBeenAttacked();
      return false;
   }

   public Vec3 getLook(float float1) {
      if (float1 == 1.0F) {
         Entity var7 = this;
         if (var7 instanceof EntityPlayerSP) {
            LookEvent var8 = new LookEvent(this.rotationPitch, this.rotationYaw);
            return this.getVectorForRotation(var8.getYaw(), var8.getPitch());
         } else {
            return this.getVectorForRotation(this.rotationPitch, this.rotationYaw);
         }
      } else {
         float var5 = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * float1;
         float var6 = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * float1;
         return this.getVectorForRotation(var5, var6);
      }
   }

   protected final Vec3 getVectorForRotation(float float1, float float2) {
      float var3 = MathHelper.cos(-float2 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var4 = MathHelper.sin(-float2 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var5 = -MathHelper.cos(-float1 * (float) (Math.PI / 180.0));
      float var6 = MathHelper.sin(-float1 * (float) (Math.PI / 180.0));
      return new Vec3(var4 * var5, var6, var3 * var5);
   }

   public Vec3 getPositionEyes(float float1) {
      if (float1 == 1.0F) {
         return new Vec3(this.posX, this.posY + this.getEyeHeight(), this.posZ);
      }

      double var4 = this.prevPosX + (this.posX - this.prevPosX) * float1;
      double var6 = this.prevPosY + (this.posY - this.prevPosY) * float1 + this.getEyeHeight();
      double var8 = this.prevPosZ + (this.posZ - this.prevPosZ) * float1;
      return new Vec3(var4, var6, var8);
   }

   public MovingObjectPosition rayTrace(double blockReachDistance, float partialTicks) {
      Vec3 start = this.getPositionEyes(partialTicks);
      Vec3 look = this.getLook(partialTicks);
      Vec3 end = start.addVector(look.xCoord * blockReachDistance, look.yCoord * blockReachDistance, look.zCoord * blockReachDistance);
      return this.worldObj.rayTraceBlocks(start, end, false, false, true);
   }

   public boolean canBeCollidedWith() {
      return false;
   }

   public boolean canBePushed() {
      return false;
   }

   public void addToPlayerScore(Entity entity, int integer) {
   }

   public boolean isInRangeToRender3d(double double1, double double2, double double3) {
      double var7 = this.posX - double1;
      double var9 = this.posY - double2;
      double var11 = this.posZ - double3;
      double var13 = var7 * var7 + var9 * var9 + var11 * var11;
      return this.isInRangeToRenderDist(var13);
   }

   public boolean isInRangeToRenderDist(double double1) {
      double var6 = this.getEntityBoundingBox().getAverageEdgeLength();
      if (Double.isNaN(var6)) {
         var6 = 1.0;
      }

      var6 = var6 * 64.0 * this.renderDistanceWeight;
      return double1 < var6 * var6;
   }

   public boolean writeMountToNBT(NBTTagCompound nBTTagCompound) {
      String var5 = this.getEntityString();
      if (!this.isDead && var5 != null) {
         nBTTagCompound.setString("id", var5);
         this.writeToNBT(nBTTagCompound);
         return true;
      } else {
         return false;
      }
   }

   public boolean writeToNBTOptional(NBTTagCompound nBTTagCompound) {
      String var5 = this.getEntityString();
      if (!this.isDead && var5 != null && this.riddenByEntity == null) {
         nBTTagCompound.setString("id", var5);
         this.writeToNBT(nBTTagCompound);
         return true;
      } else {
         return false;
      }
   }

   public void writeToNBT(NBTTagCompound nBTTagCompound) {
      try {
         nBTTagCompound.setTag("Pos", this.newDoubleNBTList(this.posX, this.posY, this.posZ));
         nBTTagCompound.setTag("Motion", this.newDoubleNBTList(this.motionX, this.motionY, this.motionZ));
         nBTTagCompound.setTag("Rotation", this.newFloatNBTList(this.rotationYaw, this.rotationPitch));
         nBTTagCompound.setFloat("FallDistance", this.fallDistance);
         nBTTagCompound.setShort("Fire", (short)this.fire);
         nBTTagCompound.setShort("Air", (short)this.getAir());
         nBTTagCompound.setBoolean("OnGround", this.onGround);
         nBTTagCompound.setInteger("Dimension", this.dimension);
         nBTTagCompound.setBoolean("Invulnerable", this.invulnerable);
         nBTTagCompound.setInteger("PortalCooldown", this.timeUntilPortal);
         nBTTagCompound.setLong("UUIDMost", this.getUniqueID().getMostSignificantBits());
         nBTTagCompound.setLong("UUIDLeast", this.getUniqueID().getLeastSignificantBits());
         if (this.getCustomNameTag() != null && this.getCustomNameTag().length() > 0) {
            nBTTagCompound.setString("CustomName", this.getCustomNameTag());
            nBTTagCompound.setBoolean("CustomNameVisible", this.getAlwaysRenderNameTag());
         }

         this.cmdResultStats.writeStatsToNBT(nBTTagCompound);
         if (this.isSilent()) {
            nBTTagCompound.setBoolean("Silent", this.isSilent());
         }

         this.writeEntityToNBT(nBTTagCompound);
         if (this.ridingEntity != null) {
            NBTTagCompound var5 = new NBTTagCompound();
            if (this.ridingEntity.writeMountToNBT(var5)) {
               nBTTagCompound.setTag("Riding", var5);
            }
         }
      } catch (Throwable var8) {
         CrashReport var6 = CrashReport.makeCrashReport(var8, "Saving entity NBT");
         CrashReportCategory var7 = var6.makeCategory("Entity being saved");
         this.addEntityCrashInfo(var7);
         throw new ReportedException(var6);
      }
   }

   public void readFromNBT(NBTTagCompound nBTTagCompound) {
      try {
         NBTTagList var5 = nBTTagCompound.getTagList("Pos", 6);
         NBTTagList var9 = nBTTagCompound.getTagList("Motion", 6);
         NBTTagList var10 = nBTTagCompound.getTagList("Rotation", 5);
         this.motionX = var9.getDoubleAt(0);
         this.motionY = var9.getDoubleAt(1);
         this.motionZ = var9.getDoubleAt(2);
         if (Math.abs(this.motionX) > 10.0) {
            this.motionX = 0.0;
         }

         if (Math.abs(this.motionY) > 10.0) {
            this.motionY = 0.0;
         }

         if (Math.abs(this.motionZ) > 10.0) {
            this.motionZ = 0.0;
         }

         this.prevPosX = this.lastTickPosX = this.posX = var5.getDoubleAt(0);
         this.prevPosY = this.lastTickPosY = this.posY = var5.getDoubleAt(1);
         this.prevPosZ = this.lastTickPosZ = this.posZ = var5.getDoubleAt(2);
         this.prevRotationYaw = this.rotationYaw = var10.getFloatAt(0);
         this.prevRotationPitch = this.rotationPitch = var10.getFloatAt(1);
         this.setRotationYawHead(this.rotationYaw);
         this.setRenderYawOffset(this.rotationYaw);
         this.fallDistance = nBTTagCompound.getFloat("FallDistance");
         this.fire = nBTTagCompound.getShort("Fire");
         this.setAir(nBTTagCompound.getShort("Air"));
         this.onGround = nBTTagCompound.getBoolean("OnGround");
         this.dimension = nBTTagCompound.getInteger("Dimension");
         this.invulnerable = nBTTagCompound.getBoolean("Invulnerable");
         this.timeUntilPortal = nBTTagCompound.getInteger("PortalCooldown");
         if (nBTTagCompound.hasKey("UUIDMost", 4) && nBTTagCompound.hasKey("UUIDLeast", 4)) {
            this.entityUniqueID = new UUID(nBTTagCompound.getLong("UUIDMost"), nBTTagCompound.getLong("UUIDLeast"));
         } else if (nBTTagCompound.hasKey("UUID", 8)) {
            this.entityUniqueID = UUID.fromString(nBTTagCompound.getString("UUID"));
         }

         this.setPosition(this.posX, this.posY, this.posZ);
         this.setRotation(this.rotationYaw, this.rotationPitch);
         if (nBTTagCompound.hasKey("CustomName", 8) && nBTTagCompound.getString("CustomName").length() > 0) {
            this.setCustomNameTag(nBTTagCompound.getString("CustomName"));
         }

         this.setAlwaysRenderNameTag(nBTTagCompound.getBoolean("CustomNameVisible"));
         this.cmdResultStats.readStatsFromNBT(nBTTagCompound);
         this.setSilent(nBTTagCompound.getBoolean("Silent"));
         this.readEntityFromNBT(nBTTagCompound);
         if (this.shouldSetPosAfterLoading()) {
            this.setPosition(this.posX, this.posY, this.posZ);
         }
      } catch (Throwable var8) {
         CrashReport var6 = CrashReport.makeCrashReport(var8, "Loading entity NBT");
         CrashReportCategory var7 = var6.makeCategory("Entity being loaded");
         this.addEntityCrashInfo(var7);
         throw new ReportedException(var6);
      }
   }

   protected boolean shouldSetPosAfterLoading() {
      return true;
   }

   protected final String getEntityString() {
      return EntityList.getEntityString(this);
   }

   protected abstract void readEntityFromNBT(NBTTagCompound nBTTagCompound);

   protected abstract void writeEntityToNBT(NBTTagCompound nBTTagCompound);

   public void onChunkLoad() {
   }

   protected NBTTagList newDoubleNBTList(double... arr) {
      NBTTagList var5 = new NBTTagList();

      for (double var9 : arr) {
         var5.appendTag(new NBTTagDouble(var9));
      }

      return var5;
   }

   protected NBTTagList newFloatNBTList(float... arr) {
      NBTTagList var5 = new NBTTagList();

      for (float var9 : arr) {
         var5.appendTag(new NBTTagFloat(var9));
      }

      return var5;
   }

   public EntityItem dropItem(Item item, int integer) {
      return this.dropItemWithOffset(item, integer, 0.0F);
   }

   public EntityItem dropItemWithOffset(Item item, int integer, float float3) {
      return this.entityDropItem(new ItemStack(item, integer, 0), float3);
   }

   public EntityItem entityDropItem(ItemStack itemStack, float float2) {
      if (itemStack.stackSize != 0 && itemStack.getItem() != null) {
         EntityItem var6 = new EntityItem(this.worldObj, this.posX, this.posY + float2, this.posZ, itemStack);
         var6.setDefaultPickupDelay();
         this.worldObj.spawnEntityInWorld(var6);
         return var6;
      } else {
         return null;
      }
   }

   public boolean isEntityAlive() {
      return !this.isDead;
   }

   public boolean isEntityInsideOpaqueBlock() {
      if (!this.noClip) {
         BlockPos.MutableBlockPos var4 = new BlockPos.MutableBlockPos(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);

         for (int var5 = 0; var5 < 8; var5++) {
            int var6 = MathHelper.floor_double(this.posY + (var5 % 2 - 0.5F) * 0.1F + this.getEyeHeight());
            int var7 = MathHelper.floor_double(this.posX + ((var5 >> 1) % 2 - 0.5F) * this.width * 0.8F);
            int var8 = MathHelper.floor_double(this.posZ + ((var5 >> 2) % 2 - 0.5F) * this.width * 0.8F);
            if (var4.getX() != var7 || var4.getY() != var6 || var4.getZ() != var8) {
               var4.set(var7, var6, var8);
               if (this.worldObj.getBlockState(var4).getBlock().isVisuallyOpaque()) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public boolean interactFirst(EntityPlayer entityPlayer) {
      return false;
   }

   public AxisAlignedBB getCollisionBox(Entity entity) {
      return null;
   }

   public void updateRidden() {
      if (this.ridingEntity.isDead) {
         this.ridingEntity = null;
      } else {
         this.motionX = 0.0;
         this.motionY = 0.0;
         this.motionZ = 0.0;
         this.onUpdate();
         if (this.ridingEntity != null) {
            this.ridingEntity.updateRiderPosition();
            this.entityRiderYawDelta = this.entityRiderYawDelta + (this.ridingEntity.rotationYaw - this.ridingEntity.prevRotationYaw);
            this.entityRiderPitchDelta = this.entityRiderPitchDelta + (this.ridingEntity.rotationPitch - this.ridingEntity.prevRotationPitch);

            while (this.entityRiderYawDelta >= 180.0) {
               this.entityRiderYawDelta -= 360.0;
            }

            while (this.entityRiderYawDelta < -180.0) {
               this.entityRiderYawDelta += 360.0;
            }

            while (this.entityRiderPitchDelta >= 180.0) {
               this.entityRiderPitchDelta -= 360.0;
            }

            while (this.entityRiderPitchDelta < -180.0) {
               this.entityRiderPitchDelta += 360.0;
            }

            double var4 = this.entityRiderYawDelta * 0.5;
            double var6 = this.entityRiderPitchDelta * 0.5;
            if (var4 > 10.0) {
               var4 = 10.0;
            }

            if (var4 < -10.0) {
               var4 = -10.0;
            }

            if (var6 > 10.0) {
               var6 = 10.0;
            }

            if (var6 < -10.0) {
               var6 = -10.0;
            }

            this.entityRiderYawDelta -= var4;
            this.entityRiderPitchDelta -= var6;
         }
      }
   }

   public void updateRiderPosition() {
      if (this.riddenByEntity != null) {
         this.riddenByEntity.setPosition(this.posX, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ);
      }
   }

   public double getYOffset() {
      return 0.0;
   }

   public double getMountedYOffset() {
      return this.height * 0.75;
   }

   public void mountEntity(Entity entity) {
      this.entityRiderPitchDelta = 0.0;
      this.entityRiderYawDelta = 0.0;
      if (entity == null) {
         if (this.ridingEntity != null) {
            this.setLocationAndAngles(
               this.ridingEntity.posX,
               this.ridingEntity.getEntityBoundingBox().minY + this.ridingEntity.height,
               this.ridingEntity.posZ,
               this.rotationYaw,
               this.rotationPitch
            );
            this.ridingEntity.riddenByEntity = null;
         }

         this.ridingEntity = null;
      } else {
         if (this.ridingEntity != null) {
            this.ridingEntity.riddenByEntity = null;
         }

         if (entity != null) {
            for (Entity var5 = entity.ridingEntity; var5 != null; var5 = var5.ridingEntity) {
               if (var5 == this) {
                  return;
               }
            }
         }

         this.ridingEntity = entity;
         entity.riddenByEntity = this;
      }
   }

   public void setPositionAndRotation2(double double1, double double2, double double3, float float4, float float5, int integer, boolean boolean7) {
      this.setPosition(double1, double2, double3);
      this.setRotation(float4, float5);
      List<AxisAlignedBB> var14 = this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox().contract(0.03125, 0.0, 0.03125));
      if (!var14.isEmpty()) {
         double var15 = 0.0;

         for (AxisAlignedBB var18 : var14) {
            if (var18.maxY > var15) {
               var15 = var18.maxY;
            }
         }

         double2 += var15 - this.getEntityBoundingBox().minY;
         this.setPosition(double1, double2, double3);
      }
   }

   public float getCollisionBorderSize() {
      return 0.1F;
   }

   public Vec3 getLookVec() {
      return null;
   }

   public void setPortal(BlockPos blockPos) {
      if (this.timeUntilPortal > 0) {
         this.timeUntilPortal = this.getPortalCooldown();
      } else {
         if (!this.worldObj.isRemote && !blockPos.equals(this.lastPortalPos)) {
            this.lastPortalPos = blockPos;
            BlockPattern.PatternHelper var5 = Blocks.portal.func_181089_f(this.worldObj, blockPos);
            double var6 = var5.getFinger().getAxis() == EnumFacing.Axis.X ? var5.getPos().getZ() : var5.getPos().getX();
            double var8 = var5.getFinger().getAxis() == EnumFacing.Axis.X ? this.posZ : this.posX;
            var8 = Math.abs(
               MathHelper.func_181160_c(
                  var8 - (var5.getFinger().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE ? 1 : 0), var6, var6 - var5.func_181118_d()
               )
            );
            double var10 = MathHelper.func_181160_c(this.posY - 1.0, var5.getPos().getY(), var5.getPos().getY() - var5.func_181119_e());
            this.lastPortalVec = new Vec3(var8, var10, 0.0);
            this.teleportDirection = var5.getFinger();
         }

         this.inPortal = true;
      }
   }

   public int getPortalCooldown() {
      return 300;
   }

   public void setVelocity(double double1, double double2, double double3) {
      this.motionX = double1;
      this.motionY = double2;
      this.motionZ = double3;
   }

   public void Method937(Position n5) {
      this.motionX = n5.getX();
      this.motionY = n5.getY();
      this.motionZ = n5.getZ();
   }

   public void handleStatusUpdate(byte byte1) {
   }

   public void performHurtAnimation() {
   }

   public ItemStack[] getInventory() {
      return null;
   }

   public void setCurrentItemOrArmor(int integer, ItemStack itemStack) {
   }

   public boolean isBurning() {
      boolean var4 = this.worldObj != null && this.worldObj.isRemote;
      return !this.isImmuneToFire && (this.fire > 0 || var4 && this.getFlag(0));
   }

   public boolean isRiding() {
      return this.ridingEntity != null;
   }

   public boolean isSneaking() {
      return this.getFlag(1);
   }

   public void setSneaking(boolean boolean1) {
      this.setFlag(1, boolean1);
   }

   public boolean isSprinting() {
      return this.getFlag(3);
   }

   public void setSprinting(boolean boolean1) {
      this.setFlag(3, boolean1);
   }

   public boolean isInvisible() {
      return this.getFlag(5);
   }

   public void setInvisible(boolean boolean1) {
      this.setFlag(5, boolean1);
   }

   public boolean isInvisibleToPlayer(EntityPlayer entityPlayer) {
      return !entityPlayer.isSpectator() && this.isInvisible();
   }

   public boolean isEating() {
      return this.getFlag(4);
   }

   public void setEating(boolean boolean1) {
      this.setFlag(4, boolean1);
   }

   protected boolean getFlag(int integer) {
      return (this.dataWatcher.getWatchableObjectByte(0) & 1 << integer) != 0;
   }

   public void setFlag(int integer, boolean boolean2) {
      byte var6 = this.dataWatcher.getWatchableObjectByte(0);
      if (boolean2) {
         this.dataWatcher.updateObject(0, (byte)(var6 | 1 << integer));
      } else {
         this.dataWatcher.updateObject(0, (byte)(var6 & ~(1 << integer)));
      }
   }

   public int getAir() {
      return this.dataWatcher.getWatchableObjectShort(1);
   }

   public void setAir(int integer) {
      this.dataWatcher.updateObject(1, (short)integer);
   }

   public void onStruckByLightning(EntityLightningBolt entityLightningBolt) {
      this.attackEntityFrom(DamageSource.lightningBolt, 5.0F);
      this.fire++;
      if (this.fire == 0) {
         this.setFire(8);
      }
   }

   public void onKillEntity(EntityLivingBase entityLivingBase) {
   }

   protected boolean pushOutOfBlocks(double double1, double double2, double double3) {
      BlockPos var10 = new BlockPos(double1, double2, double3);
      double var11 = double1 - var10.getX();
      double var13 = double2 - var10.getY();
      double var15 = double3 - var10.getZ();
      List var17 = this.worldObj.getCollisionBoxes(this.getEntityBoundingBox());
      if (var17.isEmpty() && !this.worldObj.isBlockFullCube(var10)) {
         return false;
      }

      byte var18 = 3;
      double var19 = 9999.0;
      if (!this.worldObj.isBlockFullCube(var10.west()) && var11 < 9999.0) {
         var19 = var11;
         var18 = 0;
      }

      if (!this.worldObj.isBlockFullCube(var10.east()) && 1.0 - var11 < var19) {
         var19 = 1.0 - var11;
         var18 = 1;
      }

      if (!this.worldObj.isBlockFullCube(var10.up()) && 1.0 - var13 < var19) {
         var19 = 1.0 - var13;
         var18 = 3;
      }

      if (!this.worldObj.isBlockFullCube(var10.north()) && var15 < var19) {
         var19 = var15;
         var18 = 4;
      }

      if (!this.worldObj.isBlockFullCube(var10.south()) && 1.0 - var15 < var19) {
         double var10000 = 1.0 - var15;
         var18 = 5;
      }

      float var21 = this.rand.nextFloat() * 0.2F + 0.1F;
      if (var18 == 0) {
         this.motionX = -var21;
      }

      if (var18 == 1) {
         this.motionX = var21;
      }

      if (var18 == 3) {
         this.motionY = var21;
      }

      if (var18 == 4) {
         this.motionZ = -var21;
      }

      if (var18 == 5) {
         this.motionZ = var21;
      }

      return true;
   }

   public void setInWeb() {
      this.isInWeb = true;
      this.fallDistance = 0.0F;
   }

   @Override
   public String getName() {
      if (this.hasCustomName()) {
         return this.getCustomNameTag();
      }

      String var4 = EntityList.getEntityString(this);
      if (var4 == null) {
         var4 = "generic";
      }

      return StatCollector.translateToLocal("entity." + var4 + ".name");
   }

   public Entity[] getParts() {
      return null;
   }

   public boolean isEntityEqual(Entity entity) {
      return this == entity;
   }

   public float getRotationYawHead() {
      return 0.0F;
   }

   public void setRotationYawHead(float float1) {
   }

   public void setRenderYawOffset(float float1) {
   }

   public boolean canAttackWithItem() {
      return true;
   }

   public boolean hitByEntity(Entity entity) {
      return false;
   }

   @Override
   public String toString() {
      return String.format(
         "%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]",
         this.getClass().getSimpleName(),
         this.getName(),
         this.entityId,
         this.worldObj == null ? "~NULL~" : this.worldObj.getWorldInfo().getWorldName(),
         this.posX,
         this.posY,
         this.posZ
      );
   }

   public boolean isEntityInvulnerable(DamageSource damageSource) {
      return this.invulnerable && damageSource != DamageSource.outOfWorld && !damageSource.isCreativePlayer();
   }

   public void copyLocationAndAnglesFrom(Entity entity) {
      this.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
   }

   public void copyDataFromOld(Entity entity) {
      NBTTagCompound var2 = new NBTTagCompound();
      entity.writeToNBT(var2);
      this.readFromNBT(var2);
      this.timeUntilPortal = entity.timeUntilPortal;
      this.lastPortalPos = entity.lastPortalPos;
      this.lastPortalVec = entity.lastPortalVec;
      this.teleportDirection = entity.teleportDirection;
   }

   public void travelToDimension(int integer) {
      if (!this.worldObj.isRemote && !this.isDead) {
         this.worldObj.theProfiler.startSection("changeDimension");
         MinecraftServer var5 = MinecraftServer.getServer();
         int var6 = this.dimension;
         WorldServer var7 = var5.worldServerForDimension(var6);
         WorldServer var8 = var5.worldServerForDimension(integer);
         this.dimension = integer;
         if (var6 == 1 && integer == 1) {
            var8 = var5.worldServerForDimension(0);
            this.dimension = 0;
         }

         this.worldObj.removeEntity(this);
         this.isDead = false;
         this.worldObj.theProfiler.startSection("reposition");
         var5.getConfigurationManager().transferEntityToWorld(this, var6, var7, var8);
         this.worldObj.theProfiler.endStartSection("reloading");
         Entity var9 = EntityList.createEntityByName(EntityList.getEntityString(this), var8);
         if (var9 != null) {
            var9.copyDataFromOld(this);
            if (var6 == 1 && integer == 1) {
               BlockPos var10 = this.worldObj.getTopSolidOrLiquidBlock(var8.getSpawnPoint());
               var9.moveToBlockPosAndAngles(var10, var9.rotationYaw, var9.rotationPitch);
            }

            var8.spawnEntityInWorld(var9);
         }

         this.isDead = true;
         this.worldObj.theProfiler.endSection();
         var7.resetUpdateEntityTick();
         var8.resetUpdateEntityTick();
         this.worldObj.theProfiler.endSection();
      }
   }

   public float getExplosionResistance(Explosion explosion, World world, BlockPos blockPos, IBlockState iBlockState) {
      return iBlockState.getBlock().getExplosionResistance(this);
   }

   public boolean verifyExplosion(Explosion explosion, World world, BlockPos blockPos, IBlockState iBlockState, float float5) {
      return true;
   }

   public int getMaxFallHeight() {
      return 3;
   }

   public Vec3 func_181014_aG() {
      return this.lastPortalVec;
   }

   public EnumFacing getTeleportDirection() {
      return this.teleportDirection;
   }

   public boolean doesEntityNotTriggerPressurePlate() {
      return false;
   }

   public void addEntityCrashInfo(CrashReportCategory crashReportCategory) {
      crashReportCategory.addCrashSectionCallable("Entity Type", () -> EntityList.getEntityString(this) + " (" + this.getClass().getCanonicalName() + ")");
      crashReportCategory.addCrashSection("Entity ID", this.entityId);
      crashReportCategory.addCrashSectionCallable("Entity Name", this::getName);
      crashReportCategory.addCrashSection("Entity's Exact location", String.format("%.2f, %.2f, %.2f", this.posX, this.posY, this.posZ));
      crashReportCategory.addCrashSection(
         "Entity's Block location",
         CrashReportCategory.getCoordinateInfo(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ))
      );
      crashReportCategory.addCrashSection("Entity's Momentum", String.format("%.2f, %.2f, %.2f", this.motionX, this.motionY, this.motionZ));
      crashReportCategory.addCrashSectionCallable("Entity's Rider", () -> this.riddenByEntity.toString());
      crashReportCategory.addCrashSectionCallable("Entity's Vehicle", () -> this.ridingEntity.toString());
   }

   public boolean canRenderOnFire() {
      return this.isBurning();
   }

   public UUID getUniqueID() {
      return this.entityUniqueID;
   }

   public boolean isPushedByWater() {
      return true;
   }

   @Override
   public IChatComponent getDisplayName() {
      ChatComponentText var1 = new ChatComponentText(this.getName());
      var1.getChatStyle().setChatHoverEvent(this.getHoverEvent());
      var1.getChatStyle().setInsertion(this.getUniqueID().toString());
      return var1;
   }

   public String getCustomNameTag() {
      return this.dataWatcher.getWatchableObjectString(2);
   }

   public void setCustomNameTag(String string) {
      this.dataWatcher.updateObject(2, string);
   }

   public boolean hasCustomName() {
      return this.dataWatcher.getWatchableObjectString(2).length() > 0;
   }

   public boolean getAlwaysRenderNameTag() {
      return this.dataWatcher.getWatchableObjectByte(3) == 1;
   }

   public void setAlwaysRenderNameTag(boolean boolean1) {
      this.dataWatcher.updateObject(3, (byte)(boolean1 ? 1 : 0));
   }

   public void setPositionAndUpdate(double double1, double double2, double double3) {
      this.setLocationAndAngles(double1, double2, double3, this.rotationYaw, this.rotationPitch);
   }

   public boolean getAlwaysRenderNameTagForRender() {
      return this.getAlwaysRenderNameTag();
   }

   public void onDataWatcherUpdate(int integer) {
   }

   public EnumFacing getHorizontalFacing() {
      return EnumFacing.getHorizontal(MathHelper.floor_double(this.rotationYaw * 4.0F / 360.0F + 0.5) & 3);
   }

   protected HoverEvent getHoverEvent() {
      NBTTagCompound var4 = new NBTTagCompound();
      String var5 = EntityList.getEntityString(this);
      var4.setString("id", this.getUniqueID().toString());
      if (var5 != null) {
         var4.setString("type", var5);
      }

      var4.setString("name", this.getName());
      return new HoverEvent(HoverEvent.Action.SHOW_ENTITY, new ChatComponentText(var4.toString()));
   }

   public boolean isSpectatedByPlayer(EntityPlayerMP entityPlayerMP) {
      return true;
   }

   public AxisAlignedBB getEntityBoundingBox() {
      return this.boundingBox;
   }

   public void setEntityBoundingBox(AxisAlignedBB axisAlignedBB) {
      this.boundingBox = axisAlignedBB;
   }

   public float getEyeHeight() {
      return this.height * 0.85F;
   }

   public boolean isOutsideBorder() {
      return this.isOutsideBorder;
   }

   public void setOutsideBorder(boolean boolean1) {
      this.isOutsideBorder = boolean1;
   }

   public boolean replaceItemInInventory(int integer, ItemStack itemStack) {
      return false;
   }

   @Override
   public void addChatMessage(IChatComponent iChatComponent) {
   }

   @Override
   public boolean canCommandSenderUseCommand(int integer, String string) {
      return true;
   }

   @Override
   public BlockPos getPosition() {
      return new BlockPos(this.posX, this.posY + 0.5, this.posZ);
   }

   @Override
   public Vec3 getPositionVector() {
      return new Vec3(this.posX, this.posY, this.posZ);
   }

   @Override
   public World getEntityWorld() {
      return this.worldObj;
   }

   @Override
   public Entity getCommandSenderEntity() {
      return this;
   }

   @Override
   public boolean sendCommandFeedback() {
      return false;
   }

   @Override
   public void setCommandStat(CommandResultStats.Type type, int integer) {
      this.cmdResultStats.setCommandStatScore(this, type, integer);
   }

   public CommandResultStats getCommandStats() {
      return this.cmdResultStats;
   }

   public void setCommandStats(Entity entity) {
      this.cmdResultStats.addAllStats(entity.getCommandStats());
   }

   public NBTTagCompound getNBTTagCompound() {
      return null;
   }

   public void clientUpdateEntityNBT(NBTTagCompound nBTTagCompound) {
   }

   public boolean interactAt(EntityPlayer entityPlayer, Vec3 vec3) {
      return false;
   }

   public boolean isImmuneToExplosions() {
      return false;
   }

   protected void applyEnchantments(EntityLivingBase entityLivingBase, Entity entity) {
      if (entity instanceof EntityLivingBase) {
         EnchantmentHelper.applyThornEnchantments((EntityLivingBase)entity, entityLivingBase);
      }

      EnchantmentHelper.applyArthropodEnchantments(entityLivingBase, entity);
   }

   public boolean Method938() {
      return this.isInWeb;
   }

   public Entity Method939(boolean boolean1) {
      this.isImmuneToFire = boolean1;
      return this;
   }

   public Vector4f Method940() {
      return this.aA;
   }

   public Entity Method941(Vector4f vector4f) {
      this.aA = vector4f;
      return this;
   }
}
