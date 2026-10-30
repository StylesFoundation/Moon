package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

public abstract class BlockLog extends BlockRotatedPillar {
   public static final PropertyEnum<BlockLog.EnumAxis> LOG_AXIS = PropertyEnum.create("axis", BlockLog.EnumAxis.class);

   public BlockLog() {
      super(Material.wood);
      this.setCreativeTab(CreativeTabs.tabBlock);
      this.setHardness(2.0F);
      this.setStepSound(soundTypeWood);
   }

   @Override
   public void breakBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (world.Method326(blockPos.add(-5, -5, -5), blockPos.add(5, 5, 5))) {
         for (BlockPos var10 : BlockPos.getAllInBox(blockPos.add(-4, -4, -4), blockPos.add(4, 4, 4))) {
            IBlockState var11 = world.getBlockState(var10);
            if (var11.getBlock().getMaterial() == Material.leaves && !var11.getValue(BlockLeaves.CHECK_DECAY)) {
               world.setBlockState(var10, var11.withProperty(BlockLeaves.CHECK_DECAY, Boolean.TRUE), 4);
            }
         }
      }
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      return super.onBlockPlaced(world, blockPos, enumFacing, float4, float5, float6, integer, entityLivingBase)
         .withProperty(LOG_AXIS, BlockLog.EnumAxis.fromFacingAxis(enumFacing.getAxis()));
   }

   public enum EnumAxis implements IStringSerializable {
      X("x"),
      Y("y"),
      Z("z"),
      NONE("none");

      private final String name;

      EnumAxis(String string3) {
         this.name = string3;
      }

      @Override
      public String toString() {
         return this.name;
      }

      public static BlockLog.EnumAxis fromFacingAxis(EnumFacing.Axis axis) {
         switch (axis) {
            case X:
               return X;
            case Y:
               return Y;
            case Z:
               return Z;
            default:
               return NONE;
         }
      }

      @Override
      public String getName() {
         return this.name;
      }

      private static BlockLog.EnumAxis[] Method148() {
         return new BlockLog.EnumAxis[]{X, Y, Z, NONE};
      }
   }
}
