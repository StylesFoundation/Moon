package net.minecraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ITickable;

public class TileEntityEnderChest extends TileEntity implements ITickable {
   public float lidAngle;
   public float prevLidAngle;
   public int numPlayersUsing;
   private int ticksSinceSync;

   @Override
   public void update() {
      if (++this.ticksSinceSync % 20 * 4 == 0) {
         this.worldObj.addBlockEvent(this.pos, Blocks.ender_chest, 1, this.numPlayersUsing);
      }

      this.prevLidAngle = this.lidAngle;
      int var4 = this.pos.getX();
      int var5 = this.pos.getY();
      int var6 = this.pos.getZ();
      if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F) {
         double var8 = var4 + 0.5;
         double var10 = var6 + 0.5;
         this.worldObj.playSoundEffect(var8, var5 + 0.5, var10, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
      }

      if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F) {
         float var14 = this.lidAngle;
         if (this.numPlayersUsing > 0) {
            this.lidAngle += 0.1F;
         } else {
            this.lidAngle -= 0.1F;
         }

         if (this.lidAngle > 1.0F) {
            this.lidAngle = 1.0F;
         }

         float var9 = 0.5F;
         if (this.lidAngle < var9 && var14 >= var9) {
            double var15 = var4 + 0.5;
            double var12 = var6 + 0.5;
            this.worldObj.playSoundEffect(var15, var5 + 0.5, var12, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
         }

         if (this.lidAngle < 0.0F) {
            this.lidAngle = 0.0F;
         }
      }
   }

   @Override
   public boolean receiveClientEvent(int integer1, int integer2) {
      if (integer1 == 1) {
         this.numPlayersUsing = integer2;
         return true;
      } else {
         return super.receiveClientEvent(integer1, integer2);
      }
   }

   @Override
   public void invalidate() {
      this.updateContainingBlockInfo();
      super.invalidate();
   }

   public void openChest() {
      this.numPlayersUsing++;
      this.worldObj.addBlockEvent(this.pos, Blocks.ender_chest, 1, this.numPlayersUsing);
   }

   public void closeChest() {
      this.numPlayersUsing--;
      this.worldObj.addBlockEvent(this.pos, Blocks.ender_chest, 1, this.numPlayersUsing);
   }

   public boolean canBeUsed(EntityPlayer entityPlayer) {
      return this.worldObj.getTileEntity(this.pos) == this
         && entityPlayer.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0;
   }
}
