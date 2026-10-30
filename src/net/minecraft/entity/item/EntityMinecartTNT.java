package net.minecraft.entity.item;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityMinecartTNT extends EntityMinecart {
   private int minecartTNTFuse = -1;

   public EntityMinecartTNT(World world) {
      super(world);
   }

   public EntityMinecartTNT(World world, double double2, double double3, double double4) {
      super(world, double2, double3, double4);
   }

   @Override
   public EntityMinecart.EnumMinecartType getMinecartType() {
      return EntityMinecart.EnumMinecartType.TNT;
   }

   @Override
   public IBlockState getDefaultDisplayTile() {
      return Blocks.tnt.getDefaultState();
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (this.minecartTNTFuse > 0) {
         this.minecartTNTFuse--;
         this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 0.5, this.posZ, 0.0, 0.0, 0.0);
      } else if (this.minecartTNTFuse == 0) {
         this.explodeCart(this.motionX * this.motionX + this.motionZ * this.motionZ);
      }

      if (this.isCollidedHorizontally) {
         double var4 = this.motionX * this.motionX + this.motionZ * this.motionZ;
         if (var4 >= 0.01F) {
            this.explodeCart(var4);
         }
      }
   }

   @Override
   public boolean attackEntityFrom(DamageSource damageSource, float float2) {
      if (damageSource.getSourceOfDamage() instanceof EntityArrow var7 && var7.isBurning()) {
         this.explodeCart(var7.motionX * var7.motionX + var7.motionY * var7.motionY + var7.motionZ * var7.motionZ);
      }

      return super.attackEntityFrom(damageSource, float2);
   }

   @Override
   public void killMinecart(DamageSource damageSource) {
      super.killMinecart(damageSource);
      double var5 = this.motionX * this.motionX + this.motionZ * this.motionZ;
      if (!damageSource.isExplosion() && this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
         this.entityDropItem(new ItemStack(Blocks.tnt, 1), 0.0F);
      }

      if (damageSource.isFireDamage() || damageSource.isExplosion() || var5 >= 0.01F) {
         this.explodeCart(var5);
      }
   }

   protected void explodeCart(double double1) {
      if (!this.worldObj.isRemote) {
         double var6 = Math.sqrt(double1);
         if (var6 > 5.0) {
            var6 = 5.0;
         }

         this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)(4.0 + this.rand.nextDouble() * 1.5 * var6), true);
         this.setDead();
      }
   }

   @Override
   public void fall(float float1, float float2) {
      if (float1 >= 3.0F) {
         float var6 = float1 / 10.0F;
         this.explodeCart(var6 * var6);
      }

      super.fall(float1, float2);
   }

   @Override
   public void onActivatorRailPass(int integer1, int integer2, int integer3, boolean boolean4) {
      if (boolean4 && this.minecartTNTFuse < 0) {
         this.ignite();
      }
   }

   @Override
   public void handleStatusUpdate(byte byte1) {
      if (byte1 == 10) {
         this.ignite();
      } else {
         super.handleStatusUpdate(byte1);
      }
   }

   public void ignite() {
      this.minecartTNTFuse = 80;
      if (!this.worldObj.isRemote) {
         this.worldObj.setEntityState(this, (byte)10);
         if (!this.isSilent()) {
            this.worldObj.playSoundAtEntity(this, "game.tnt.primed", 1.0F, 1.0F);
         }
      }
   }

   public int getFuseTicks() {
      return this.minecartTNTFuse;
   }

   public boolean isIgnited() {
      return this.minecartTNTFuse > -1;
   }

   @Override
   public float getExplosionResistance(Explosion explosion, World world, BlockPos blockPos, IBlockState iBlockState) {
      return this.isIgnited() && (BlockRailBase.isRailBlock(iBlockState) || BlockRailBase.isRailBlock(world, blockPos.up()))
         ? 0.0F
         : super.getExplosionResistance(explosion, world, blockPos, iBlockState);
   }

   @Override
   public boolean verifyExplosion(Explosion explosion, World world, BlockPos blockPos, IBlockState iBlockState, float float5) {
      return (!this.isIgnited() || !BlockRailBase.isRailBlock(iBlockState) && !BlockRailBase.isRailBlock(world, blockPos.up()))
         && super.verifyExplosion(explosion, world, blockPos, iBlockState, float5);
   }

   @Override
   protected void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      super.readEntityFromNBT(nBTTagCompound);
      if (nBTTagCompound.hasKey("TNTFuse", 99)) {
         this.minecartTNTFuse = nBTTagCompound.getInteger("TNTFuse");
      }
   }

   @Override
   protected void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      super.writeEntityToNBT(nBTTagCompound);
      nBTTagCompound.setInteger("TNTFuse", this.minecartTNTFuse);
   }
}
