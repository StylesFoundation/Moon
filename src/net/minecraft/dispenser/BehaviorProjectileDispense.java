package net.minecraft.dispenser;

import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public abstract class BehaviorProjectileDispense extends BehaviorDefaultDispenseItem {
   @Override
   public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
      World var6 = iBlockSource.getWorld();
      IPosition var7 = BlockDispenser.getDispensePosition(iBlockSource);
      EnumFacing var8 = BlockDispenser.getFacing(iBlockSource.getBlockMetadata());
      IProjectile var9 = this.getProjectileEntity(var6, var7);
      var9.setThrowableHeading(var8.getFrontOffsetX(), var8.getFrontOffsetY() + 0.1F, var8.getFrontOffsetZ(), this.func_82500_b(), this.func_82498_a());
      var6.spawnEntityInWorld((Entity)var9);
      itemStack.splitStack(1);
      return itemStack;
   }

   @Override
   protected void playDispenseSound(IBlockSource iBlockSource) {
      iBlockSource.getWorld().playAuxSFX(1002, iBlockSource.getBlockPos(), 0);
   }

   protected abstract IProjectile getProjectileEntity(World world, IPosition iPosition);

   protected float func_82498_a() {
      return 6.0F;
   }

   protected float func_82500_b() {
      return 1.1F;
   }
}
