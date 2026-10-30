package net.minecraft.item;

import net.minecraft.block.Block;

public class ItemAnvilBlock extends ItemMultiTexture {
   public ItemAnvilBlock(Block block) {
      super(block, block, new String[]{"intact", "slightlyDamaged", "veryDamaged"});
   }

   @Override
   public int getMetadata(int integer) {
      return integer << 2;
   }
}
