package net.minecraft.client.renderer.block.statemap;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class BlockStateMapper {
   private final Map<Block, IStateMapper> blockStateMap = Maps.newIdentityHashMap();
   private final Set<Block> setBuiltInBlocks = Sets.newIdentityHashSet();

   public void registerBlockStateMapper(Block block, IStateMapper iStateMapper) {
      this.blockStateMap.put(block, iStateMapper);
   }

   public void registerBuiltInBlocks(Block... arr) {
      Collections.addAll(this.setBuiltInBlocks, arr);
   }

   public Map<IBlockState, ModelResourceLocation> putAllStateModelLocations() {
      IdentityHashMap var1 = Maps.newIdentityHashMap();

      for (Block var3 : Block.blockRegistry) {
         if (!this.setBuiltInBlocks.contains(var3)) {
            var1.putAll(((IStateMapper)MoreObjects.firstNonNull(this.blockStateMap.get(var3), new DefaultStateMapper())).putStateModelLocations(var3));
         }
      }

      return var1;
   }
}
