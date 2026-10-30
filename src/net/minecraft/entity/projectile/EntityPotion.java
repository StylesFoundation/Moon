package net.minecraft.entity.projectile;

import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityPotion extends EntityThrowable {
   private ItemStack potionDamage;

   public EntityPotion(World world) {
      super(world);
   }

   public EntityPotion(World world, EntityLivingBase entityLivingBase, int integer) {
      this(world, entityLivingBase, new ItemStack(Items.potionitem, 1, integer));
   }

   public EntityPotion(World world, EntityLivingBase entityLivingBase, ItemStack itemStack) {
      super(world, entityLivingBase);
      this.potionDamage = itemStack;
   }

   public EntityPotion(World world, double double2, double double3, double double4, int integer) {
      this(world, double2, double3, double4, new ItemStack(Items.potionitem, 1, integer));
   }

   public EntityPotion(World world, double double2, double double3, double double4, ItemStack itemStack) {
      super(world, double2, double3, double4);
      this.potionDamage = itemStack;
   }

   @Override
   protected float getGravityVelocity() {
      return 0.05F;
   }

   @Override
   protected float getVelocity() {
      return 0.5F;
   }

   @Override
   protected float getInaccuracy() {
      return -20.0F;
   }

   public void setPotionDamage(int integer) {
      if (this.potionDamage == null) {
         this.potionDamage = new ItemStack(Items.potionitem, 1, 0);
      }

      this.potionDamage.setItemDamage(integer);
   }

   public int getPotionDamage() {
      if (this.potionDamage == null) {
         this.potionDamage = new ItemStack(Items.potionitem, 1, 0);
      }

      return this.potionDamage.getMetadata();
   }

   @Override
   protected void onImpact(MovingObjectPosition movingObjectPosition) {
      if (!this.worldObj.isRemote) {
         List<PotionEffect> var5 = Items.potionitem.getEffects(this.potionDamage);
         if (var5 != null && !var5.isEmpty()) {
            AxisAlignedBB var6 = this.getEntityBoundingBox().expand(4.0, 2.0, 4.0);
            List<EntityLivingBase> var7 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, var6);
            if (!var7.isEmpty()) {
               for (EntityLivingBase var9 : var7) {
                  double var10 = this.getDistanceSqToEntity(var9);
                  if (var10 < 16.0) {
                     double var12 = 1.0 - Math.sqrt(var10) / 4.0;
                     if (var9 == movingObjectPosition.entityHit) {
                        var12 = 1.0;
                     }

                     for (PotionEffect var15 : var5) {
                        int var16 = var15.getPotionID();
                        if (Potion.potionTypes[var16].isInstant()) {
                           Potion.potionTypes[var16].affectEntity(this, this.getThrower(), var9, var15.getAmplifier(), var12);
                        } else {
                           int var17 = (int)(var12 * var15.getDuration() + 0.5);
                           if (var17 > 20) {
                              var9.addPotionEffect(new PotionEffect(var16, var17, var15.getAmplifier()));
                           }
                        }
                     }
                  }
               }
            }
         }

         this.worldObj.playAuxSFX(2002, new BlockPos(this), this.getPotionDamage());
         this.setDead();
      }
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      super.readEntityFromNBT(nBTTagCompound);
      if (nBTTagCompound.hasKey("Potion", 10)) {
         this.potionDamage = ItemStack.loadItemStackFromNBT(nBTTagCompound.getCompoundTag("Potion"));
      } else {
         this.setPotionDamage(nBTTagCompound.getInteger("potionValue"));
      }

      if (this.potionDamage == null) {
         this.setDead();
      }
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      super.writeEntityToNBT(nBTTagCompound);
      if (this.potionDamage != null) {
         nBTTagCompound.setTag("Potion", this.potionDamage.writeToNBT(new NBTTagCompound()));
      }
   }
}
