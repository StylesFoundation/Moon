package net.minecraft.item;

import net.minecraft.block.BlockLeaves;

public class ItemLeaves extends ItemBlock {
   private final BlockLeaves leaves;

   public ItemLeaves(BlockLeaves blockLeaves) {
      super(blockLeaves);
      this.leaves = blockLeaves;
      this.setMaxDamage(0);
      this.setHasSubtypes(true);
   }

   @Override
   public int getMetadata(int integer) {
      return integer | 4;
   }

   @Override
   public int getColorFromItemStack(ItemStack itemStack, int integer) {
      return this.leaves.getRenderColor(this.leaves.getStateFromMeta(itemStack.getMetadata()));
   }

   @Override
   public String getUnlocalizedName(ItemStack itemStack) {
      return super.getUnlocalizedName() + "." + this.leaves.getWoodType(itemStack.getMetadata()).getUnlocalizedName();
   }
}
