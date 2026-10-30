package net.minecraft.item;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

public class ItemSpade extends ItemTool {
   private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(
      new Block[]{
         Blocks.clay, Blocks.dirt, Blocks.farmland, Blocks.grass, Blocks.gravel, Blocks.mycelium, Blocks.sand, Blocks.snow, Blocks.snow_layer, Blocks.soul_sand
      }
   );

   public ItemSpade(Item.ToolMaterial toolMaterial) {
      super(1.0F, toolMaterial, EFFECTIVE_ON);
   }

   @Override
   public boolean canHarvestBlock(Block block) {
      return block == Blocks.snow_layer || block == Blocks.snow;
   }
}
