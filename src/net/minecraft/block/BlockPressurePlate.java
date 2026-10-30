package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockPressurePlate extends BlockBasePressurePlate {
   public static final PropertyBool POWERED = PropertyBool.create("powered");
   private final BlockPressurePlate.Sensitivity sensitivity;

   protected BlockPressurePlate(Material material, BlockPressurePlate.Sensitivity sensitivity) {
      super(material);
      this.setDefaultState(this.blockState.getBaseState().withProperty(POWERED, Boolean.FALSE));
      this.sensitivity = sensitivity;
   }

   @Override
   protected int getRedstoneStrength(IBlockState iBlockState) {
      return iBlockState.getValue(POWERED) ? 15 : 0;
   }

   @Override
   protected IBlockState setRedstoneStrength(IBlockState iBlockState, int integer) {
      return iBlockState.withProperty(POWERED, integer > 0);
   }

   @Override
   protected int computeRedstoneStrength(World world, BlockPos blockPos) {
      AxisAlignedBB var6 = this.getSensitiveAABB(blockPos);
      List<? extends Entity> var7;
      switch (this.sensitivity) {
         case EVERYTHING:
            var7 = world.getEntitiesWithinAABBExcludingEntity(null, var6);
            break;
         case MOBS:
            var7 = world.getEntitiesWithinAABB(EntityLivingBase.class, var6);
            break;
         default:
            return 0;
      }

      if (!var7.isEmpty()) {
         for (Entity var9 : var7) {
            if (!var9.doesEntityNotTriggerPressurePlate()) {
               return 15;
            }
         }
      }

      return 0;
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(POWERED, integer == 1);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(POWERED) ? 1 : 0;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, POWERED);
   }

   public enum Sensitivity {
      EVERYTHING,
      MOBS;

      private static BlockPressurePlate.Sensitivity[] Method725() {
         return new BlockPressurePlate.Sensitivity[]{EVERYTHING, MOBS};
      }
   }
}
