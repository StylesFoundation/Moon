package net.minecraft.item;

import net.minecraft.block.Block;

public class ItemColored extends ItemBlock {
   private final Block coloredBlock;
   private String[] subtypeNames;

   public ItemColored(Block block, boolean boolean2) {
      super(block);
      this.coloredBlock = block;
      if (boolean2) {
         this.setMaxDamage(0);
         this.setHasSubtypes(true);
      }
   }

   @Override
   public int getColorFromItemStack(ItemStack itemStack, int integer) {
      return this.coloredBlock.getRenderColor(this.coloredBlock.getStateFromMeta(itemStack.getMetadata()));
   }

   @Override
   public int getMetadata(int integer) {
      return integer;
   }

   public ItemColored setSubtypeNames(String[] arr) {
      this.subtypeNames = arr;
      return this;
   }

   @Override
   public String getUnlocalizedName(ItemStack itemStack) {
      if (this.subtypeNames == null) {
         return super.getUnlocalizedName(itemStack);
      }

      int var5 = itemStack.getMetadata();
      return var5 >= 0 && var5 < this.subtypeNames.length
         ? super.getUnlocalizedName(itemStack) + "." + this.subtypeNames[var5]
         : super.getUnlocalizedName(itemStack);
   }
}
