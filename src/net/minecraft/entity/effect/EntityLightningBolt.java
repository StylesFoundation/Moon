package net.minecraft.entity.effect;

import java.util.Iterator;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityLightningBolt extends EntityWeatherEffect {
   private int lightningState;
   public long boltVertex;
   private int boltLivingTime;

   public EntityLightningBolt(World world, double double2, double double3, double double4) {
      super(world);
      this.setLocationAndAngles(double2, double3, double4, 0.0F, 0.0F);
      this.lightningState = 2;
      this.boltVertex = this.rand.nextLong();
      this.boltLivingTime = this.rand.nextInt(3) + 1;
      BlockPos var11 = new BlockPos(this);
      if (!world.isRemote
         && world.getGameRules().getBoolean("doFireTick")
         && (world.getDifficulty() == EnumDifficulty.NORMAL || world.getDifficulty() == EnumDifficulty.HARD)
         && world.Method324(var11, 10)) {
         if (world.getBlockState(var11).getBlock().getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(world, var11)) {
            world.setBlockState(var11, Blocks.fire.getDefaultState());
         }

         for (int var12 = 0; var12 < 4; var12++) {
            BlockPos var13 = var11.add(this.rand.nextInt(3) - 1, this.rand.nextInt(3) - 1, this.rand.nextInt(3) - 1);
            if (world.getBlockState(var13).getBlock().getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(world, var13)) {
               world.setBlockState(var13, Blocks.fire.getDefaultState());
            }
         }
      }
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.lightningState == 2) {
         this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
         this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 2.0F, 0.5F + this.rand.nextFloat() * 0.2F);
      }

      this.lightningState--;
      if (this.lightningState < 0) {
         if (this.boltLivingTime == 0) {
            this.setDead();
         } else if (this.lightningState < -this.rand.nextInt(10)) {
            this.boltLivingTime--;
            this.lightningState = 1;
            this.boltVertex = this.rand.nextLong();
            BlockPos var4 = new BlockPos(this);
            if (!this.worldObj.isRemote
               && this.worldObj.getGameRules().getBoolean("doFireTick")
               && this.worldObj.Method324(var4, 10)
               && this.worldObj.getBlockState(var4).getBlock().getMaterial() == Material.air
               && Blocks.fire.canPlaceBlockAt(this.worldObj, var4)) {
               this.worldObj.setBlockState(var4, Blocks.fire.getDefaultState());
            }
         }
      }

      if (this.lightningState >= 0) {
         if (this.worldObj.isRemote) {
            this.worldObj.setLastLightningBolt(2);
         } else {
            Iterator var7 = this.worldObj
               .getEntitiesWithinAABBExcludingEntity(
                  this, new AxisAlignedBB(this.posX - 3.0, this.posY - 3.0, this.posZ - 3.0, this.posX + 3.0, this.posY + 6.0 + 3.0, this.posZ + 3.0)
               )
               .iterator();

            while (var7.hasNext()) {
               ((Entity)var7.next()).onStruckByLightning(this);
            }
         }
      }
   }

   @Override
   protected void entityInit() {
   }

   @Override
   protected void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
   }

   @Override
   protected void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
   }
}
