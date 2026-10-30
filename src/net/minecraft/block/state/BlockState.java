package net.minecraft.block.state;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.util.Cartesian;
import net.minecraft.util.MapPopulator;

public class BlockState {
   private static final Joiner COMMA_JOINER = Joiner.on(", ");
   private static final Function<IProperty, String> GET_NAME_FUNC = iProperty -> iProperty == null ? "<NULL>" : iProperty.getName();
   private final Block block;
   private final ImmutableList<IProperty> properties;
   private final ImmutableList<IBlockState> validStates;

   public BlockState(Block block, IProperty... arr) {
      this.block = block;
      Arrays.sort(arr, Comparator.comparing(IProperty::getName));
      this.properties = ImmutableList.copyOf(arr);
      Map<Map<IProperty, Comparable>, BlockState.StateImplementation> var6 = Maps.newLinkedHashMap();
      List<BlockState.StateImplementation> var7 = Lists.newArrayList();

      for (List var9 : Cartesian.cartesianProduct(this.getAllowedValues())) {
         Map var10 = MapPopulator.createMap(this.properties, var9);
         BlockState.StateImplementation var11 = new BlockState.StateImplementation(block, ImmutableMap.copyOf(var10));
         var6.put(var10, var11);
         var7.add(var11);
      }

      for (BlockState.StateImplementation var13 : var7) {
         var13.buildPropertyValueTable(var6);
      }

      this.validStates = ImmutableList.copyOf(var7);
   }

   public ImmutableList<IBlockState> getValidStates() {
      return this.validStates;
   }

   private List<Iterable<Comparable>> getAllowedValues() {
      ArrayList var4 = Lists.newArrayList();

      for (int var5 = 0; var5 < this.properties.size(); var5++) {
         var4.add(((IProperty)this.properties.get(var5)).getAllowedValues());
      }

      return var4;
   }

   public IBlockState getBaseState() {
      return (IBlockState)this.validStates.get(0);
   }

   public Block getBlock() {
      return this.block;
   }

   public Collection<IProperty> getProperties() {
      return this.properties;
   }

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this)
         .add("block", Block.blockRegistry.getNameForObject(this.block))
         .add("properties", this.properties.stream().map(GET_NAME_FUNC::apply).collect(Collectors.toList()))
         .toString();
   }

   static class StateImplementation extends BlockStateBase {
      private final Block block;
      private final ImmutableMap<IProperty, Comparable> properties;
      private ImmutableTable<IProperty, Comparable, IBlockState> propertyValueTable;

      private StateImplementation(Block block, ImmutableMap<IProperty, Comparable> immutableMap) {
         this.block = block;
         this.properties = immutableMap;
      }

      @Override
      public Collection<IProperty> getPropertyNames() {
         return Collections.unmodifiableCollection(this.properties.keySet());
      }

      @Override
      public <T extends Comparable<T>> T getValue(IProperty<T> iProperty) {
         if (!this.properties.containsKey(iProperty)) {
            throw new IllegalArgumentException("Cannot get property " + iProperty + " as it does not exist in " + this.block.getBlockState());
         } else {
            return (T)iProperty.getValueClass().cast(this.properties.get(iProperty));
         }
      }

      @Override
      public <T extends Comparable<T>, V extends T> IBlockState withProperty(IProperty<T> iProperty, V comparable) {
         if (!this.properties.containsKey(iProperty)) {
            throw new IllegalArgumentException("Cannot set property " + iProperty + " as it does not exist in " + this.block.getBlockState());
         } else if (!iProperty.getAllowedValues().contains(comparable)) {
            throw new IllegalArgumentException(
               "Cannot set property "
                  + iProperty
                  + " to "
                  + comparable
                  + " on block "
                  + Block.blockRegistry.getNameForObject(this.block)
                  + ", it is not an allowed value"
            );
         } else {
            return this.properties.get(iProperty) == comparable ? this : (IBlockState)this.propertyValueTable.get(iProperty, comparable);
         }
      }

      @Override
      public ImmutableMap<IProperty, Comparable> getProperties() {
         return this.properties;
      }

      @Override
      public Block getBlock() {
         return this.block;
      }

      @Override
      public boolean equals(Object object) {
         return this == object;
      }

      @Override
      public int hashCode() {
         return this.properties.hashCode();
      }

      public void buildPropertyValueTable(Map<Map<IProperty, Comparable>, BlockState.StateImplementation> map) {
         if (this.propertyValueTable != null) {
            throw new IllegalStateException();
         }

         HashBasedTable var5 = HashBasedTable.create();

         for (IProperty<? extends Comparable> var7 : this.properties.keySet()) {
            for (Comparable var9 : var7.getAllowedValues()) {
               if (var9 != this.properties.get(var7)) {
                  var5.put(var7, var9, (IBlockState)map.get(this.getPropertiesWithValue(var7, var9)));
               }
            }
         }

         this.propertyValueTable = ImmutableTable.copyOf(var5);
      }

      private Map<IProperty, Comparable> getPropertiesWithValue(IProperty iProperty, Comparable comparable) {
         HashMap var3 = Maps.newHashMap(this.properties);
         var3.put(iProperty, comparable);
         return var3;
      }
   }
}
