package net.minecraft.tileentity;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TileEntityNote extends TileEntity {
   public byte note;
   public boolean previousRedstoneState;

   @Override
   public void writeToNBT(NBTTagCompound nBTTagCompound) {
      super.writeToNBT(nBTTagCompound);
      nBTTagCompound.setByte("note", this.note);
   }

   @Override
   public void readFromNBT(NBTTagCompound nBTTagCompound) {
      super.readFromNBT(nBTTagCompound);
      this.note = nBTTagCompound.getByte("note");
      this.note = (byte)MathHelper.clamp_int(this.note, 0, 24);
   }

   public void changePitch() {
      this.note = (byte)((this.note + 1) % 25);
      this.markDirty();
   }

   public void triggerNote(World world, BlockPos blockPos) {
      if (world.getBlockState(blockPos.up()).getBlock().getMaterial() == Material.air) {
         Material var6 = world.getBlockState(blockPos.down()).getBlock().getMaterial();
         byte var7 = 0;
         if (var6 == Material.rock) {
            var7 = 1;
         }

         if (var6 == Material.sand) {
            var7 = 2;
         }

         if (var6 == Material.glass) {
            var7 = 3;
         }

         if (var6 == Material.wood) {
            var7 = 4;
         }

         world.addBlockEvent(blockPos, Blocks.noteblock, var7, this.note);
      }
   }
}
