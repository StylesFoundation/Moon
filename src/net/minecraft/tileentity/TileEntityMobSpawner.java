package net.minecraft.tileentity;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class TileEntityMobSpawner extends TileEntity implements ITickable {
   private final MobSpawnerBaseLogic spawnerLogic = new MobSpawnerBaseLogic() {
      @Override
      public void func_98267_a(int integer) {
         TileEntityMobSpawner.this.worldObj.addBlockEvent(TileEntityMobSpawner.this.pos, Blocks.mob_spawner, integer, 0);
      }

      @Override
      public World getSpawnerWorld() {
         return TileEntityMobSpawner.this.worldObj;
      }

      @Override
      public BlockPos getSpawnerPosition() {
         return TileEntityMobSpawner.this.pos;
      }

      @Override
      public void setRandomEntity(MobSpawnerBaseLogic.WeightedRandomMinecart weightedRandomMinecart) {
         super.setRandomEntity(weightedRandomMinecart);
         if (this.getSpawnerWorld() != null) {
            this.getSpawnerWorld().markBlockForUpdate(TileEntityMobSpawner.this.pos);
         }
      }
   };

   @Override
   public void readFromNBT(NBTTagCompound nBTTagCompound) {
      super.readFromNBT(nBTTagCompound);
      this.spawnerLogic.readFromNBT(nBTTagCompound);
   }

   @Override
   public void writeToNBT(NBTTagCompound nBTTagCompound) {
      super.writeToNBT(nBTTagCompound);
      this.spawnerLogic.writeToNBT(nBTTagCompound);
   }

   @Override
   public void update() {
      this.spawnerLogic.updateSpawner();
   }

   @Override
   public Packet getDescriptionPacket() {
      NBTTagCompound var1 = new NBTTagCompound();
      this.writeToNBT(var1);
      var1.removeTag("SpawnPotentials");
      return new S35PacketUpdateTileEntity(this.pos, 1, var1);
   }

   @Override
   public boolean receiveClientEvent(int integer1, int integer2) {
      return this.spawnerLogic.setDelayToMin(integer1) || super.receiveClientEvent(integer1, integer2);
   }

   @Override
   public boolean func_183000_F() {
      return true;
   }

   public MobSpawnerBaseLogic getSpawnerBaseLogic() {
      return this.spawnerLogic;
   }
}
