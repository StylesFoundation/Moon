package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityEgg extends EntityThrowable {
   public EntityEgg(World world) {
      super(world);
   }

   public EntityEgg(World world, EntityLivingBase entityLivingBase) {
      super(world, entityLivingBase);
   }

   public EntityEgg(World world, double double2, double double3, double double4) {
      super(world, double2, double3, double4);
   }

   @Override
   protected void onImpact(MovingObjectPosition movingObjectPosition) {
      if (movingObjectPosition.entityHit != null) {
         movingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
      }

      if (!this.worldObj.isRemote && this.rand.nextInt(8) == 0) {
         byte var5 = 1;
         if (this.rand.nextInt(32) == 0) {
            var5 = 4;
         }

         for (int var6 = 0; var6 < var5; var6++) {
            EntityChicken var7 = new EntityChicken(this.worldObj);
            var7.setGrowingAge(-24000);
            var7.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
            this.worldObj.spawnEntityInWorld(var7);
         }
      }

      for (int var8 = 0; var8 < 8; var8++) {
         this.worldObj
            .spawnParticle(
               EnumParticleTypes.ITEM_CRACK,
               this.posX,
               this.posY,
               this.posZ,
               (this.rand.nextFloat() - 0.5) * 0.08,
               (this.rand.nextFloat() - 0.5) * 0.08,
               (this.rand.nextFloat() - 0.5) * 0.08,
               Item.getIdFromItem(Items.egg)
            );
      }

      if (!this.worldObj.isRemote) {
         this.setDead();
      }
   }
}
