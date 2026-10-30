package net.minecraft.block.state.pattern;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;

public class BlockStateHelper implements Predicate<IBlockState> {
   private final BlockState blockstate;
   private final Map<IProperty, Predicate> propertyPredicates = Maps.newHashMap();

   private BlockStateHelper(BlockState blockState) {
      this.blockstate = blockState;
   }

   public static BlockStateHelper forBlock(Block block) {
      return new BlockStateHelper(block.getBlockState());
   }

   public boolean apply(IBlockState iBlockState) {
      if (iBlockState != null && iBlockState.getBlock().equals(this.blockstate.getBlock())) {
         for (Entry var6 : this.propertyPredicates.entrySet()) {
            Object var7 = iBlockState.getValue((IProperty)var6.getKey());
            if (!((Predicate)var6.getValue()).apply(var7)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public <V extends Comparable<V>> BlockStateHelper where(IProperty<V> iProperty, Predicate<? extends V> predicate) {
      if (!this.blockstate.getProperties().contains(iProperty)) {
         throw new IllegalArgumentException(this.blockstate + " cannot support property " + iProperty);
      }

      this.propertyPredicates.put(iProperty, predicate);
      return this;
   }
}
