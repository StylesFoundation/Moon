package net.minecraft.world.gen.structure;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemDoor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public abstract class StructureComponent {
   protected StructureBoundingBox boundingBox;
   protected EnumFacing coordBaseMode;
   protected int componentType;

   public StructureComponent() {
   }

   protected StructureComponent(int integer) {
      this.componentType = integer;
   }

   public NBTTagCompound createStructureBaseNBT() {
      NBTTagCompound var4 = new NBTTagCompound();
      var4.setString("id", MapGenStructureIO.getStructureComponentName(this));
      var4.setTag("BB", this.boundingBox.toNBTTagIntArray());
      var4.setInteger("O", this.coordBaseMode == null ? -1 : this.coordBaseMode.getHorizontalIndex());
      var4.setInteger("GD", this.componentType);
      this.writeStructureToNBT(var4);
      return var4;
   }

   protected abstract void writeStructureToNBT(NBTTagCompound nBTTagCompound);

   public void readStructureBaseNBT(World world, NBTTagCompound nBTTagCompound) {
      if (nBTTagCompound.hasKey("BB")) {
         this.boundingBox = new StructureBoundingBox(nBTTagCompound.getIntArray("BB"));
      }

      int var6 = nBTTagCompound.getInteger("O");
      this.coordBaseMode = var6 == -1 ? null : EnumFacing.getHorizontal(var6);
      this.componentType = nBTTagCompound.getInteger("GD");
      this.readStructureFromNBT(nBTTagCompound);
   }

   protected abstract void readStructureFromNBT(NBTTagCompound nBTTagCompound);

   public void buildComponent(StructureComponent structureComponent, List<StructureComponent> list, Random random) {
   }

   public abstract boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox);

   public StructureBoundingBox getBoundingBox() {
      return this.boundingBox;
   }

   public int getComponentType() {
      return this.componentType;
   }

   public static StructureComponent findIntersecting(List<StructureComponent> list, StructureBoundingBox structureBoundingBox) {
      for (StructureComponent var6 : list) {
         if (var6.getBoundingBox() != null && var6.getBoundingBox().intersectsWith(structureBoundingBox)) {
            return var6;
         }
      }

      return null;
   }

   public BlockPos getBoundingBoxCenter() {
      return new BlockPos(this.boundingBox.getCenter());
   }

   protected boolean isLiquidInStructureBoundingBox(World world, StructureBoundingBox structureBoundingBox) {
      int var6 = Math.max(this.boundingBox.minX - 1, structureBoundingBox.minX);
      int var7 = Math.max(this.boundingBox.minY - 1, structureBoundingBox.minY);
      int var8 = Math.max(this.boundingBox.minZ - 1, structureBoundingBox.minZ);
      int var9 = Math.min(this.boundingBox.maxX + 1, structureBoundingBox.maxX);
      int var10 = Math.min(this.boundingBox.maxY + 1, structureBoundingBox.maxY);
      int var11 = Math.min(this.boundingBox.maxZ + 1, structureBoundingBox.maxZ);
      BlockPos.MutableBlockPos var12 = new BlockPos.MutableBlockPos();

      for (int var13 = var6; var13 <= var9; var13++) {
         for (int var14 = var8; var14 <= var11; var14++) {
            if (world.getBlockState(var12.set(var13, var7, var14)).getBlock().getMaterial().isLiquid()) {
               return true;
            }

            if (world.getBlockState(var12.set(var13, var10, var14)).getBlock().getMaterial().isLiquid()) {
               return true;
            }
         }
      }

      for (int var15 = var6; var15 <= var9; var15++) {
         for (int var17 = var7; var17 <= var10; var17++) {
            if (world.getBlockState(var12.set(var15, var17, var8)).getBlock().getMaterial().isLiquid()) {
               return true;
            }

            if (world.getBlockState(var12.set(var15, var17, var11)).getBlock().getMaterial().isLiquid()) {
               return true;
            }
         }
      }

      for (int var16 = var8; var16 <= var11; var16++) {
         for (int var18 = var7; var18 <= var10; var18++) {
            if (world.getBlockState(var12.set(var6, var18, var16)).getBlock().getMaterial().isLiquid()) {
               return true;
            }

            if (world.getBlockState(var12.set(var9, var18, var16)).getBlock().getMaterial().isLiquid()) {
               return true;
            }
         }
      }

      return false;
   }

   protected int getXWithOffset(int integer1, int integer2) {
      if (this.coordBaseMode == null) {
         return integer1;
      }

      switch (this.coordBaseMode) {
         case NORTH:
         case SOUTH:
            return this.boundingBox.minX + integer1;
         case WEST:
            return this.boundingBox.maxX - integer2;
         case EAST:
            return this.boundingBox.minX + integer2;
         default:
            return integer1;
      }
   }

   protected int getYWithOffset(int integer) {
      return this.coordBaseMode == null ? integer : integer + this.boundingBox.minY;
   }

   protected int getZWithOffset(int integer1, int integer2) {
      if (this.coordBaseMode == null) {
         return integer2;
      }

      switch (this.coordBaseMode) {
         case NORTH:
            return this.boundingBox.maxZ - integer2;
         case SOUTH:
            return this.boundingBox.minZ + integer2;
         case WEST:
         case EAST:
            return this.boundingBox.minZ + integer1;
         default:
            return integer2;
      }
   }

   protected int getMetadataWithOffset(Block block, int integer) {
      if (block == Blocks.rail) {
         if (this.coordBaseMode == EnumFacing.WEST || this.coordBaseMode == EnumFacing.EAST) {
            if (integer == 1) {
               return 0;
            }

            return 1;
         }
      } else if (block instanceof BlockDoor) {
         if (this.coordBaseMode == EnumFacing.SOUTH) {
            if (integer == 0) {
               return 2;
            }

            if (integer == 2) {
               return 0;
            }
         } else {
            if (this.coordBaseMode == EnumFacing.WEST) {
               return integer + 1 & 3;
            }

            if (this.coordBaseMode == EnumFacing.EAST) {
               return integer + 3 & 3;
            }
         }
      } else if (block != Blocks.stone_stairs
         && block != Blocks.oak_stairs
         && block != Blocks.nether_brick_stairs
         && block != Blocks.stone_brick_stairs
         && block != Blocks.sandstone_stairs) {
         if (block == Blocks.ladder) {
            if (this.coordBaseMode == EnumFacing.SOUTH) {
               if (integer == EnumFacing.NORTH.getIndex()) {
                  return EnumFacing.SOUTH.getIndex();
               }

               if (integer == EnumFacing.SOUTH.getIndex()) {
                  return EnumFacing.NORTH.getIndex();
               }
            } else if (this.coordBaseMode == EnumFacing.WEST) {
               if (integer == EnumFacing.NORTH.getIndex()) {
                  return EnumFacing.WEST.getIndex();
               }

               if (integer == EnumFacing.SOUTH.getIndex()) {
                  return EnumFacing.EAST.getIndex();
               }

               if (integer == EnumFacing.WEST.getIndex()) {
                  return EnumFacing.NORTH.getIndex();
               }

               if (integer == EnumFacing.EAST.getIndex()) {
                  return EnumFacing.SOUTH.getIndex();
               }
            } else if (this.coordBaseMode == EnumFacing.EAST) {
               if (integer == EnumFacing.NORTH.getIndex()) {
                  return EnumFacing.EAST.getIndex();
               }

               if (integer == EnumFacing.SOUTH.getIndex()) {
                  return EnumFacing.WEST.getIndex();
               }

               if (integer == EnumFacing.WEST.getIndex()) {
                  return EnumFacing.NORTH.getIndex();
               }

               if (integer == EnumFacing.EAST.getIndex()) {
                  return EnumFacing.SOUTH.getIndex();
               }
            }
         } else if (block == Blocks.stone_button) {
            if (this.coordBaseMode == EnumFacing.SOUTH) {
               if (integer == 3) {
                  return 4;
               }

               if (integer == 4) {
                  return 3;
               }
            } else if (this.coordBaseMode == EnumFacing.WEST) {
               if (integer == 3) {
                  return 1;
               }

               if (integer == 4) {
                  return 2;
               }

               if (integer == 2) {
                  return 3;
               }

               if (integer == 1) {
                  return 4;
               }
            } else if (this.coordBaseMode == EnumFacing.EAST) {
               if (integer == 3) {
                  return 2;
               }

               if (integer == 4) {
                  return 1;
               }

               if (integer == 2) {
                  return 3;
               }

               if (integer == 1) {
                  return 4;
               }
            }
         } else if (block == Blocks.tripwire_hook || block instanceof BlockDirectional) {
            EnumFacing var6 = EnumFacing.getHorizontal(integer);
            if (this.coordBaseMode == EnumFacing.SOUTH) {
               if (var6 == EnumFacing.SOUTH || var6 == EnumFacing.NORTH) {
                  return var6.getOpposite().getHorizontalIndex();
               }
            } else if (this.coordBaseMode == EnumFacing.WEST) {
               if (var6 == EnumFacing.NORTH) {
                  return EnumFacing.WEST.getHorizontalIndex();
               }

               if (var6 == EnumFacing.SOUTH) {
                  return EnumFacing.EAST.getHorizontalIndex();
               }

               if (var6 == EnumFacing.WEST) {
                  return EnumFacing.NORTH.getHorizontalIndex();
               }

               if (var6 == EnumFacing.EAST) {
                  return EnumFacing.SOUTH.getHorizontalIndex();
               }
            } else if (this.coordBaseMode == EnumFacing.EAST) {
               if (var6 == EnumFacing.NORTH) {
                  return EnumFacing.EAST.getHorizontalIndex();
               }

               if (var6 == EnumFacing.SOUTH) {
                  return EnumFacing.WEST.getHorizontalIndex();
               }

               if (var6 == EnumFacing.WEST) {
                  return EnumFacing.NORTH.getHorizontalIndex();
               }

               if (var6 == EnumFacing.EAST) {
                  return EnumFacing.SOUTH.getHorizontalIndex();
               }
            }
         } else if (block == Blocks.piston || block == Blocks.sticky_piston || block == Blocks.lever || block == Blocks.dispenser) {
            if (this.coordBaseMode == EnumFacing.SOUTH) {
               if (integer == EnumFacing.NORTH.getIndex() || integer == EnumFacing.SOUTH.getIndex()) {
                  return EnumFacing.getFront(integer).getOpposite().getIndex();
               }
            } else if (this.coordBaseMode == EnumFacing.WEST) {
               if (integer == EnumFacing.NORTH.getIndex()) {
                  return EnumFacing.WEST.getIndex();
               }

               if (integer == EnumFacing.SOUTH.getIndex()) {
                  return EnumFacing.EAST.getIndex();
               }

               if (integer == EnumFacing.WEST.getIndex()) {
                  return EnumFacing.NORTH.getIndex();
               }

               if (integer == EnumFacing.EAST.getIndex()) {
                  return EnumFacing.SOUTH.getIndex();
               }
            } else if (this.coordBaseMode == EnumFacing.EAST) {
               if (integer == EnumFacing.NORTH.getIndex()) {
                  return EnumFacing.EAST.getIndex();
               }

               if (integer == EnumFacing.SOUTH.getIndex()) {
                  return EnumFacing.WEST.getIndex();
               }

               if (integer == EnumFacing.WEST.getIndex()) {
                  return EnumFacing.NORTH.getIndex();
               }

               if (integer == EnumFacing.EAST.getIndex()) {
                  return EnumFacing.SOUTH.getIndex();
               }
            }
         }
      } else if (this.coordBaseMode == EnumFacing.SOUTH) {
         if (integer == 2) {
            return 3;
         }

         if (integer == 3) {
            return 2;
         }
      } else if (this.coordBaseMode == EnumFacing.WEST) {
         if (integer == 0) {
            return 2;
         }

         if (integer == 1) {
            return 3;
         }

         if (integer == 2) {
            return 0;
         }

         if (integer == 3) {
            return 1;
         }
      } else if (this.coordBaseMode == EnumFacing.EAST) {
         if (integer == 0) {
            return 2;
         }

         if (integer == 1) {
            return 3;
         }

         if (integer == 2) {
            return 1;
         }

         if (integer == 3) {
            return 0;
         }
      }

      return integer;
   }

   protected void setBlockState(World world, IBlockState iBlockState, int integer3, int integer4, int integer5, StructureBoundingBox structureBoundingBox) {
      BlockPos var10 = new BlockPos(this.getXWithOffset(integer3, integer5), this.getYWithOffset(integer4), this.getZWithOffset(integer3, integer5));
      if (structureBoundingBox.isVecInside(var10)) {
         world.setBlockState(var10, iBlockState, 2);
      }
   }

   protected IBlockState getBlockStateFromPos(World world, int integer2, int integer3, int integer4, StructureBoundingBox structureBoundingBox) {
      int var8 = this.getXWithOffset(integer2, integer4);
      int var9 = this.getYWithOffset(integer3);
      int var10 = this.getZWithOffset(integer2, integer4);
      BlockPos var11 = new BlockPos(var8, var9, var10);
      return !structureBoundingBox.isVecInside(var11) ? Blocks.air.getDefaultState() : world.getBlockState(var11);
   }

   protected void fillWithAir(
      World world, StructureBoundingBox structureBoundingBox, int integer3, int integer4, int integer5, int integer6, int integer7, int integer8
   ) {
      for (int var12 = integer4; var12 <= integer7; var12++) {
         for (int var13 = integer3; var13 <= integer6; var13++) {
            for (int var14 = integer5; var14 <= integer8; var14++) {
               this.setBlockState(world, Blocks.air.getDefaultState(), var13, var12, var14, structureBoundingBox);
            }
         }
      }
   }

   protected void fillWithBlocks(
      World world,
      StructureBoundingBox structureBoundingBox,
      int integer3,
      int integer4,
      int integer5,
      int integer6,
      int integer7,
      int integer8,
      IBlockState iBlockState9,
      IBlockState iBlockState10,
      boolean boolean11
   ) {
      for (int var15 = integer4; var15 <= integer7; var15++) {
         for (int var16 = integer3; var16 <= integer6; var16++) {
            for (int var17 = integer5; var17 <= integer8; var17++) {
               if (!boolean11 || this.getBlockStateFromPos(world, var16, var15, var17, structureBoundingBox).getBlock().getMaterial() != Material.air) {
                  if (var15 != integer4 && var15 != integer7 && var16 != integer3 && var16 != integer6 && var17 != integer5 && var17 != integer8) {
                     this.setBlockState(world, iBlockState10, var16, var15, var17, structureBoundingBox);
                  } else {
                     this.setBlockState(world, iBlockState9, var16, var15, var17, structureBoundingBox);
                  }
               }
            }
         }
      }
   }

   protected void fillWithRandomizedBlocks(
      World world,
      StructureBoundingBox structureBoundingBox,
      int integer3,
      int integer4,
      int integer5,
      int integer6,
      int integer7,
      int integer8,
      boolean boolean9,
      Random random,
      StructureComponent.BlockSelector blockSelector
   ) {
      for (int var15 = integer4; var15 <= integer7; var15++) {
         for (int var16 = integer3; var16 <= integer6; var16++) {
            for (int var17 = integer5; var17 <= integer8; var17++) {
               if (!boolean9 || this.getBlockStateFromPos(world, var16, var15, var17, structureBoundingBox).getBlock().getMaterial() != Material.air) {
                  blockSelector.selectBlocks(
                     random,
                     var16,
                     var15,
                     var17,
                     var15 == integer4 || var15 == integer7 || var16 == integer3 || var16 == integer6 || var17 == integer5 || var17 == integer8
                  );
                  this.setBlockState(world, blockSelector.getBlockState(), var16, var15, var17, structureBoundingBox);
               }
            }
         }
      }
   }

   protected void func_175805_a(
      World world,
      StructureBoundingBox structureBoundingBox,
      Random random,
      float float4,
      int integer5,
      int integer6,
      int integer7,
      int integer8,
      int integer9,
      int integer10,
      IBlockState iBlockState11,
      IBlockState iBlockState12,
      boolean boolean13
   ) {
      for (int var17 = integer6; var17 <= integer9; var17++) {
         for (int var18 = integer5; var18 <= integer8; var18++) {
            for (int var19 = integer7; var19 <= integer10; var19++) {
               if (random.nextFloat() <= float4
                  && (!boolean13 || this.getBlockStateFromPos(world, var18, var17, var19, structureBoundingBox).getBlock().getMaterial() != Material.air)) {
                  if (var17 != integer6 && var17 != integer9 && var18 != integer5 && var18 != integer8 && var19 != integer7 && var19 != integer10) {
                     this.setBlockState(world, iBlockState12, var18, var17, var19, structureBoundingBox);
                  } else {
                     this.setBlockState(world, iBlockState11, var18, var17, var19, structureBoundingBox);
                  }
               }
            }
         }
      }
   }

   protected void randomlyPlaceBlock(
      World world, StructureBoundingBox structureBoundingBox, Random random, float float4, int integer5, int integer6, int integer7, IBlockState iBlockState
   ) {
      if (random.nextFloat() < float4) {
         this.setBlockState(world, iBlockState, integer5, integer6, integer7, structureBoundingBox);
      }
   }

   protected void randomlyRareFillWithBlocks(
      World world,
      StructureBoundingBox structureBoundingBox,
      int integer3,
      int integer4,
      int integer5,
      int integer6,
      int integer7,
      int integer8,
      IBlockState iBlockState,
      boolean boolean10
   ) {
      float var14 = integer6 - integer3 + 1;
      float var15 = integer7 - integer4 + 1;
      float var16 = integer8 - integer5 + 1;
      float var17 = integer3 + var14 / 2.0F;
      float var18 = integer5 + var16 / 2.0F;

      for (int var19 = integer4; var19 <= integer7; var19++) {
         float var20 = (var19 - integer4) / var15;

         for (int var21 = integer3; var21 <= integer6; var21++) {
            float var22 = (var21 - var17) / (var14 * 0.5F);

            for (int var23 = integer5; var23 <= integer8; var23++) {
               float var24 = (var23 - var18) / (var16 * 0.5F);
               if (!boolean10 || this.getBlockStateFromPos(world, var21, var19, var23, structureBoundingBox).getBlock().getMaterial() != Material.air) {
                  float var25 = var22 * var22 + var20 * var20 + var24 * var24;
                  if (var25 <= 1.05F) {
                     this.setBlockState(world, iBlockState, var21, var19, var23, structureBoundingBox);
                  }
               }
            }
         }
      }
   }

   protected void clearCurrentPositionBlocksUpwards(World world, int integer2, int integer3, int integer4, StructureBoundingBox structureBoundingBox) {
      BlockPos var9 = new BlockPos(this.getXWithOffset(integer2, integer4), this.getYWithOffset(integer3), this.getZWithOffset(integer2, integer4));
      if (structureBoundingBox.isVecInside(var9)) {
         while (!world.isAirBlock(var9) && var9.getY() < 255) {
            world.setBlockState(var9, Blocks.air.getDefaultState(), 2);
            var9 = var9.up();
         }
      }
   }

   protected void replaceAirAndLiquidDownwards(
      World world, IBlockState iBlockState, int integer3, int integer4, int integer5, StructureBoundingBox structureBoundingBox
   ) {
      int var10 = this.getXWithOffset(integer3, integer5);
      int var11 = this.getYWithOffset(integer4);
      int var12 = this.getZWithOffset(integer3, integer5);
      if (structureBoundingBox.isVecInside(new BlockPos(var10, var11, var12))) {
         while (
            (world.isAirBlock(new BlockPos(var10, var11, var12)) || world.getBlockState(new BlockPos(var10, var11, var12)).getBlock().getMaterial().isLiquid())
               && var11 > 1
         ) {
            world.setBlockState(new BlockPos(var10, var11, var12), iBlockState, 2);
            var11--;
         }
      }
   }

   protected boolean generateChestContents(
      World world,
      StructureBoundingBox structureBoundingBox,
      Random random,
      int integer4,
      int integer5,
      int integer6,
      List<WeightedRandomChestContent> list,
      int integer8
   ) {
      BlockPos var12 = new BlockPos(this.getXWithOffset(integer4, integer6), this.getYWithOffset(integer5), this.getZWithOffset(integer4, integer6));
      if (structureBoundingBox.isVecInside(var12) && world.getBlockState(var12).getBlock() != Blocks.chest) {
         IBlockState var13 = Blocks.chest.getDefaultState();
         world.setBlockState(var12, Blocks.chest.correctFacing(world, var12, var13), 2);
         TileEntity var14 = world.getTileEntity(var12);
         if (var14 instanceof TileEntityChest) {
            WeightedRandomChestContent.generateChestContents(random, list, (TileEntityChest)var14, integer8);
         }

         return true;
      } else {
         return false;
      }
   }

   protected boolean generateDispenserContents(
      World world,
      StructureBoundingBox structureBoundingBox,
      Random random,
      int integer4,
      int integer5,
      int integer6,
      int integer7,
      List<WeightedRandomChestContent> list,
      int integer9
   ) {
      BlockPos var13 = new BlockPos(this.getXWithOffset(integer4, integer6), this.getYWithOffset(integer5), this.getZWithOffset(integer4, integer6));
      if (structureBoundingBox.isVecInside(var13) && world.getBlockState(var13).getBlock() != Blocks.dispenser) {
         world.setBlockState(var13, Blocks.dispenser.getStateFromMeta(this.getMetadataWithOffset(Blocks.dispenser, integer7)), 2);
         TileEntity var14 = world.getTileEntity(var13);
         if (var14 instanceof TileEntityDispenser) {
            WeightedRandomChestContent.generateDispenserContents(random, list, (TileEntityDispenser)var14, integer9);
         }

         return true;
      } else {
         return false;
      }
   }

   protected void placeDoorCurrentPosition(
      World world, StructureBoundingBox structureBoundingBox, Random random, int integer4, int integer5, int integer6, EnumFacing enumFacing
   ) {
      BlockPos var10 = new BlockPos(this.getXWithOffset(integer4, integer6), this.getYWithOffset(integer5), this.getZWithOffset(integer4, integer6));
      if (structureBoundingBox.isVecInside(var10)) {
         ItemDoor.placeDoor(world, var10, enumFacing.rotateYCCW(), Blocks.oak_door);
      }
   }

   public void func_181138_a(int integer1, int integer2, int integer3) {
      this.boundingBox.offset(integer1, integer2, integer3);
   }

   public abstract static class BlockSelector {
      protected IBlockState blockstate = Blocks.air.getDefaultState();

      public abstract void selectBlocks(Random random, int integer2, int integer3, int integer4, boolean boolean5);

      public IBlockState getBlockState() {
         return this.blockstate;
      }
   }
}
