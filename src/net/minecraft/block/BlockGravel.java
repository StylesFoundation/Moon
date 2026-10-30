package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class BlockGravel extends BlockFalling {
   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      if (integer > 3) {
         integer = 3;
      }

      return random.nextInt(10 - integer * 3) == 0 ? Items.flint : Item.getItemFromBlock(this);
   }

   @Override
   public MapColor getMapColor(IBlockState iBlockState) {
      return MapColor.stoneColor;
   }
}
