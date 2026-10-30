package net.minecraft.entity.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityExpBottle extends EntityThrowable {
   public EntityExpBottle(World world) {
      super(world);
   }

   public EntityExpBottle(World world, EntityLivingBase entityLivingBase) {
      super(world, entityLivingBase);
   }

   public EntityExpBottle(World world, double double2, double double3, double double4) {
      super(world, double2, double3, double4);
   }

   @Override
   protected float getGravityVelocity() {
      return 0.07F;
   }

   @Override
   protected float getVelocity() {
      return 0.7F;
   }

   @Override
   protected float getInaccuracy() {
      return -20.0F;
   }

   @Override
   protected void onImpact(MovingObjectPosition movingObjectPosition) {
      if (!this.worldObj.isRemote) {
         this.worldObj.playAuxSFX(2002, new BlockPos(this), 0);
         int var5 = 3 + this.worldObj.rand.nextInt(5) + this.worldObj.rand.nextInt(5);

         while (var5 > 0) {
            int var6 = EntityXPOrb.getXPSplit(var5);
            var5 -= var6;
            this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var6));
         }

         this.setDead();
      }
   }
}
