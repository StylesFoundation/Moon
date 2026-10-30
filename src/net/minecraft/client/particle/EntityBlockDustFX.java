package net.minecraft.client.particle;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public class EntityBlockDustFX extends EntityDiggingFX {
   protected EntityBlockDustFX(
      World world, double double2, double double3, double double4, double double5, double double6, double double7, IBlockState iBlockState
   ) {
      super(world, double2, double3, double4, double5, double6, double7, iBlockState);
      this.motionX = double5;
      this.motionY = double6;
      this.motionZ = double7;
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         IBlockState var16 = Block.getStateById(arr[0]);
         return var16.getBlock().getRenderType() == -1
            ? null
            : new EntityBlockDustFX(world, double3, double4, double5, double6, double7, double8, var16).func_174845_l();
      }
   }
}
