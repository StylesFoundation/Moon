package net.minecraft.item;

import net.minecraft.block.Block;

public class ItemCloth extends ItemBlock {
   public ItemCloth(Block block) {
      super(block);
      this.setMaxDamage(0);
      this.setHasSubtypes(true);
   }

   @Override
   public int getMetadata(int integer) {
      return integer;
   }

   @Override
   public String getUnlocalizedName(ItemStack itemStack) {
      return super.getUnlocalizedName() + "." + EnumDyeColor.byMetadata(itemStack.getMetadata()).getUnlocalizedName();
   }
}
