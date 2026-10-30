package net.minecraft.dispenser;

import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BehaviorDefaultDispenseItem implements IBehaviorDispenseItem {
   @Override
   public final ItemStack dispense(IBlockSource iBlockSource, ItemStack itemStack) {
      ItemStack var3 = this.dispenseStack(iBlockSource, itemStack);
      this.playDispenseSound(iBlockSource);
      this.spawnDispenseParticles(iBlockSource, BlockDispenser.getFacing(iBlockSource.getBlockMetadata()));
      return var3;
   }

   protected ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
      EnumFacing var5 = BlockDispenser.getFacing(iBlockSource.getBlockMetadata());
      IPosition var6 = BlockDispenser.getDispensePosition(iBlockSource);
      ItemStack var7 = itemStack.splitStack(1);
      doDispense(iBlockSource.getWorld(), var7, 6, var5, var6);
      return itemStack;
   }

   public static void doDispense(World world, ItemStack itemStack, int integer, EnumFacing enumFacing, IPosition iPosition) {
      double var8 = iPosition.getX();
      double var10 = iPosition.getY();
      double var12 = iPosition.getZ();
      if (enumFacing.getAxis() == EnumFacing.Axis.Y) {
         var10 -= 0.125;
      } else {
         var10 -= 0.15625;
      }

      EntityItem var14 = new EntityItem(world, var8, var10, var12, itemStack);
      double var15 = world.rand.nextDouble() * 0.1 + 0.2;
      var14.motionX = enumFacing.getFrontOffsetX() * var15;
      var14.motionY = 0.2F;
      var14.motionZ = enumFacing.getFrontOffsetZ() * var15;
      var14.motionX = var14.motionX + world.rand.nextGaussian() * 0.0075F * integer;
      var14.motionY = var14.motionY + world.rand.nextGaussian() * 0.0075F * integer;
      var14.motionZ = var14.motionZ + world.rand.nextGaussian() * 0.0075F * integer;
      world.spawnEntityInWorld(var14);
   }

   protected void playDispenseSound(IBlockSource iBlockSource) {
      iBlockSource.getWorld().playAuxSFX(1000, iBlockSource.getBlockPos(), 0);
   }

   protected void spawnDispenseParticles(IBlockSource iBlockSource, EnumFacing enumFacing) {
      iBlockSource.getWorld().playAuxSFX(2000, iBlockSource.getBlockPos(), this.func_82488_a(enumFacing));
   }

   private int func_82488_a(EnumFacing enumFacing) {
      return enumFacing.getFrontOffsetX() + 1 + (enumFacing.getFrontOffsetZ() + 1) * 3;
   }
}
