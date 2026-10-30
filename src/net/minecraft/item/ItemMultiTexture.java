package net.minecraft.item;

import com.google.common.base.Function;
import net.minecraft.block.Block;

public class ItemMultiTexture extends ItemBlock {
   protected final Block theBlock;
   protected final Function<ItemStack, String> nameFunction;

   public ItemMultiTexture(Block block1, Block block2, Function<ItemStack, String> function) {
      super(block1);
      this.theBlock = block2;
      this.nameFunction = function;
      this.setMaxDamage(0);
      this.setHasSubtypes(true);
   }

   public ItemMultiTexture(Block block1, Block block2, String[] arr) {
      this(block1, block2, itemStack -> {
         int var5 = itemStack.getMetadata();
         if (var5 < 0 || var5 >= arr.length) {
            var5 = 0;
         }

         return arr[var5];
      });
   }

   @Override
   public int getMetadata(int integer) {
      return integer;
   }

   @Override
   public String getUnlocalizedName(ItemStack itemStack) {
      return super.getUnlocalizedName() + "." + this.nameFunction.apply(itemStack);
   }
}
