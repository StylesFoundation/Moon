package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityAIOcelotSit extends EntityAIMoveToBlock {
   private final EntityOcelot ocelot;

   public EntityAIOcelotSit(EntityOcelot entityOcelot, double double2) {
      super(entityOcelot, double2, 8);
      this.ocelot = entityOcelot;
   }

   @Override
   public boolean shouldExecute() {
      return this.ocelot.isTamed() && !this.ocelot.isSitting() && super.shouldExecute();
   }

   @Override
   public boolean continueExecuting() {
      return super.continueExecuting();
   }

   @Override
   public void startExecuting() {
      super.startExecuting();
      this.ocelot.getAISit().setSitting(false);
   }

   @Override
   public void resetTask() {
      super.resetTask();
      this.ocelot.setSitting(false);
   }

   @Override
   public void updateTask() {
      super.updateTask();
      this.ocelot.getAISit().setSitting(false);
      if (!this.getIsAboveDestination()) {
         this.ocelot.setSitting(false);
      } else if (!this.ocelot.isSitting()) {
         this.ocelot.setSitting(true);
      }
   }

   @Override
   protected boolean shouldMoveTo(World world, BlockPos blockPos) {
      if (!world.isAirBlock(blockPos.up())) {
         return false;
      } else {
         IBlockState var6 = world.getBlockState(blockPos);
         Block var7 = var6.getBlock();
         if (var7 == Blocks.chest) {
            TileEntity var8 = world.getTileEntity(blockPos);
            return var8 instanceof TileEntityChest && ((TileEntityChest)var8).numPlayersUsing < 1;
         } else {
            return var7 == Blocks.lit_furnace ? true : var7 == Blocks.bed && var6.getValue(BlockBed.PART) != BlockBed.EnumPartType.HEAD;
         }
      }
   }
}
