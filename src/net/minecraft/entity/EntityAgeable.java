package net.minecraft.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityAgeable extends EntityCreature {
   protected int growingAge;
   protected int field_175502_b;
   protected int field_175503_c;
   private float ageWidth = -1.0F;
   private float ageHeight;

   public EntityAgeable(World world) {
      super(world);
   }

   public abstract EntityAgeable createChild(EntityAgeable entityAgeable);

   @Override
   public boolean interact(EntityPlayer entityPlayer) {
      ItemStack var5 = entityPlayer.inventory.getCurrentItem();
      if (var5 != null && var5.getItem() == Items.spawn_egg) {
         if (!this.worldObj.isRemote) {
            Class var6 = EntityList.getClassFromID(var5.getMetadata());
            if (var6 != null && this.getClass() == var6) {
               EntityAgeable var7 = this.createChild(this);
               if (var7 != null) {
                  var7.setGrowingAge(-24000);
                  var7.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
                  this.worldObj.spawnEntityInWorld(var7);
                  if (var5.hasDisplayName()) {
                     var7.setCustomNameTag(var5.getDisplayName());
                  }

                  if (!entityPlayer.capabilities.isCreativeMode) {
                     var5.stackSize--;
                     if (var5.stackSize <= 0) {
                        entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
                     }
                  }
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   protected void entityInit() {
      super.entityInit();
      this.dataWatcher.addObject(12, (byte)0);
   }

   public int getGrowingAge() {
      return this.worldObj.isRemote ? this.dataWatcher.getWatchableObjectByte(12) : this.growingAge;
   }

   public void func_175501_a(int integer, boolean boolean2) {
      int var6 = this.getGrowingAge();
      int var7 = var6;
      var6 += integer * 20;
      if (var6 > 0) {
         var6 = 0;
         if (var7 < 0) {
            this.onGrowingAdult();
         }
      }

      int var8 = var6 - var7;
      this.setGrowingAge(var6);
      if (boolean2) {
         this.field_175502_b += var8;
         if (this.field_175503_c == 0) {
            this.field_175503_c = 40;
         }
      }

      if (this.getGrowingAge() == 0) {
         this.setGrowingAge(this.field_175502_b);
      }
   }

   public void addGrowth(int integer) {
      this.func_175501_a(integer, false);
   }

   public void setGrowingAge(int integer) {
      this.dataWatcher.updateObject(12, (byte)MathHelper.clamp_int(integer, -1, 1));
      this.growingAge = integer;
      this.setScaleForAge(this.isChild());
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      super.writeEntityToNBT(nBTTagCompound);
      nBTTagCompound.setInteger("Age", this.getGrowingAge());
      nBTTagCompound.setInteger("ForcedAge", this.field_175502_b);
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      super.readEntityFromNBT(nBTTagCompound);
      this.setGrowingAge(nBTTagCompound.getInteger("Age"));
      this.field_175502_b = nBTTagCompound.getInteger("ForcedAge");
   }

   @Override
   public void onLivingUpdate() {
      super.onLivingUpdate();
      if (this.worldObj.isRemote) {
         if (this.field_175503_c > 0) {
            if (this.field_175503_c % 4 == 0) {
               this.worldObj
                  .spawnParticle(
                     EnumParticleTypes.VILLAGER_HAPPY,
                     this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width,
                     this.posY + 0.5 + this.rand.nextFloat() * this.height,
                     this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width,
                     0.0,
                     0.0,
                     0.0
                  );
            }

            this.field_175503_c--;
         }

         this.setScaleForAge(this.isChild());
      } else {
         int var4 = this.getGrowingAge();
         if (var4 < 0) {
            this.setGrowingAge(++var4);
            if (var4 == 0) {
               this.onGrowingAdult();
            }
         } else if (var4 > 0) {
            this.setGrowingAge(--var4);
         }
      }
   }

   protected void onGrowingAdult() {
   }

   @Override
   public boolean isChild() {
      return this.getGrowingAge() < 0;
   }

   public void setScaleForAge(boolean boolean1) {
      this.setScale(boolean1 ? 0.5F : 1.0F);
   }

   @Override
   protected final void setSize(float float1, float float2) {
      boolean var6 = this.ageWidth > 0.0F;
      this.ageWidth = float1;
      this.ageHeight = float2;
      if (!var6) {
         this.setScale(1.0F);
      }
   }

   protected final void setScale(float float1) {
      super.setSize(this.ageWidth * float1, this.ageHeight * float1);
   }
}
