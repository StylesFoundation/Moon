package net.minecraft.item;

import com.google.common.base.Function;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.world.ColorizerGrass;

public class ItemDoublePlant extends ItemMultiTexture {
   public ItemDoublePlant(Block block1, Block block2, Function<ItemStack, String> function) {
      super(block1, block2, function);
   }

   @Override
   public int getColorFromItemStack(ItemStack itemStack, int integer) {
      BlockDoublePlant.EnumPlantType var6 = BlockDoublePlant.EnumPlantType.byMetadata(itemStack.getMetadata());
      return var6 != BlockDoublePlant.EnumPlantType.GRASS && var6 != BlockDoublePlant.EnumPlantType.FERN
         ? super.getColorFromItemStack(itemStack, integer)
         : ColorizerGrass.getGrassColor(0.5, 1.0);
   }
}
