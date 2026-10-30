package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityAIFollowOwner extends EntityAIBase {
   private final EntityTameable thePet;
   private EntityLivingBase theOwner;
   World theWorld;
   private final double followSpeed;
   private final PathNavigate petPathfinder;
   private int field_75343_h;
   float maxDist;
   float minDist;
   private boolean field_75344_i;

   public EntityAIFollowOwner(EntityTameable entityTameable, double double2, float float3, float float4) {
      this.thePet = entityTameable;
      this.theWorld = entityTameable.worldObj;
      this.followSpeed = double2;
      this.petPathfinder = entityTameable.getNavigator();
      this.minDist = float3;
      this.maxDist = float4;
      this.setMutexBits(3);
      if (!(entityTameable.getNavigator() instanceof PathNavigateGround)) {
         throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
      }
   }

   @Override
   public boolean shouldExecute() {
      EntityLivingBase var4 = this.thePet.getOwner();
      if (var4 == null) {
         return false;
      }

      if (var4 instanceof EntityPlayer && ((EntityPlayer)var4).isSpectator()) {
         return false;
      }

      if (this.thePet.isSitting()) {
         return false;
      }

      if (this.thePet.getDistanceSqToEntity(var4) < this.minDist * this.minDist) {
         return false;
      }

      this.theOwner = var4;
      return true;
   }

   @Override
   public boolean continueExecuting() {
      return !this.petPathfinder.noPath() && this.thePet.getDistanceSqToEntity(this.theOwner) > this.maxDist * this.maxDist && !this.thePet.isSitting();
   }

   @Override
   public void startExecuting() {
      this.field_75343_h = 0;
      this.field_75344_i = ((PathNavigateGround)this.thePet.getNavigator()).getAvoidsWater();
      ((PathNavigateGround)this.thePet.getNavigator()).setAvoidsWater(false);
   }

   @Override
   public void resetTask() {
      this.theOwner = null;
      this.petPathfinder.clearPathEntity();
      ((PathNavigateGround)this.thePet.getNavigator()).setAvoidsWater(true);
   }

   private boolean func_181065_a(BlockPos blockPos) {
      IBlockState var5 = this.theWorld.getBlockState(blockPos);
      Block var6 = var5.getBlock();
      return var6 == Blocks.air || !var6.isFullCube();
   }

   @Override
   public void updateTask() {
      this.thePet.getLookHelper().setLookPositionWithEntity(this.theOwner, 10.0F, this.thePet.getVerticalFaceSpeed());
      if (!this.thePet.isSitting() && --this.field_75343_h <= 0) {
         this.field_75343_h = 10;
         if (!this.petPathfinder.tryMoveToEntityLiving(this.theOwner, this.followSpeed)
            && !this.thePet.getLeashed()
            && this.thePet.getDistanceSqToEntity(this.theOwner) >= 144.0) {
            int var4 = MathHelper.floor_double(this.theOwner.posX) - 2;
            int var5 = MathHelper.floor_double(this.theOwner.posZ) - 2;
            int var6 = MathHelper.floor_double(this.theOwner.getEntityBoundingBox().minY);

            for (int var7 = 0; var7 <= 4; var7++) {
               for (int var8 = 0; var8 <= 4; var8++) {
                  if ((var7 < 1 || var8 < 1 || var7 > 3 || var8 > 3)
                     && World.doesBlockHaveSolidTopSurface(this.theWorld, new BlockPos(var4 + var7, var6 - 1, var5 + var8))
                     && this.func_181065_a(new BlockPos(var4 + var7, var6, var5 + var8))
                     && this.func_181065_a(new BlockPos(var4 + var7, var6 + 1, var5 + var8))) {
                     this.thePet.setLocationAndAngles(var4 + var7 + 0.5F, var6, var5 + var8 + 0.5F, this.thePet.rotationYaw, this.thePet.rotationPitch);
                     this.petPathfinder.clearPathEntity();
                     return;
                  }
               }
            }
         }
      }
   }
}
