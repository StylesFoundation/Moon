package net.minecraft.entity.ai;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityMinecartMobSpawner extends EntityMinecart {
   private final MobSpawnerBaseLogic mobSpawnerLogic = new MobSpawnerBaseLogic() {
      @Override
      public void func_98267_a(int integer) {
         EntityMinecartMobSpawner.this.worldObj.setEntityState(EntityMinecartMobSpawner.this, (byte)integer);
      }

      @Override
      public World getSpawnerWorld() {
         return EntityMinecartMobSpawner.this.worldObj;
      }

      @Override
      public BlockPos getSpawnerPosition() {
         return new BlockPos(EntityMinecartMobSpawner.this);
      }
   };

   public EntityMinecartMobSpawner(World world) {
      super(world);
   }

   public EntityMinecartMobSpawner(World world, double double2, double double3, double double4) {
      super(world, double2, double3, double4);
   }

   @Override
   public EntityMinecart.EnumMinecartType getMinecartType() {
      return EntityMinecart.EnumMinecartType.SPAWNER;
   }

   @Override
   public IBlockState getDefaultDisplayTile() {
      return Blocks.mob_spawner.getDefaultState();
   }

   @Override
   protected void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      super.readEntityFromNBT(nBTTagCompound);
      this.mobSpawnerLogic.readFromNBT(nBTTagCompound);
   }

   @Override
   protected void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      super.writeEntityToNBT(nBTTagCompound);
      this.mobSpawnerLogic.writeToNBT(nBTTagCompound);
   }

   @Override
   public void handleStatusUpdate(byte byte1) {
      this.mobSpawnerLogic.setDelayToMin(byte1);
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      this.mobSpawnerLogic.updateSpawner();
   }

   public MobSpawnerBaseLogic func_98039_d() {
      return this.mobSpawnerLogic;
   }
}
