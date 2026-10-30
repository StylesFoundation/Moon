package net.minecraft.block.state;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.util.ResourceLocation;

public abstract class BlockStateBase implements IBlockState {
   private static final Joiner COMMA_JOINER = Joiner.on(',');
   private static final Function<Entry<IProperty, Comparable>, String> MAP_ENTRY_TO_STRING = entry -> {
      if (entry == null) {
         return "<NULL>";
      }

      IProperty var4 = entry.getKey();
      return var4.getName() + "=" + var4.getName(entry.getValue());
   };
   private int blockId = -1;
   private int blockStateId = -1;
   private int metadata = -1;
   private ResourceLocation blockLocation = null;

   public int getBlockId() {
      if (this.blockId < 0) {
         this.blockId = Block.getIdFromBlock(this.getBlock());
      }

      return this.blockId;
   }

   public int getBlockStateId() {
      if (this.blockStateId < 0) {
         this.blockStateId = Block.getStateId(this);
      }

      return this.blockStateId;
   }

   public int getMetadata() {
      if (this.metadata < 0) {
         this.metadata = this.getBlock().getMetaFromState(this);
      }

      return this.metadata;
   }

   public ResourceLocation getBlockLocation() {
      if (this.blockLocation == null) {
         this.blockLocation = Block.blockRegistry.getNameForObject(this.getBlock());
      }

      return this.blockLocation;
   }

   @Override
   public <T extends Comparable<T>> IBlockState cycleProperty(IProperty<T> iProperty) {
      return this.withProperty(iProperty, cyclePropertyValue(iProperty.getAllowedValues(), this.getValue(iProperty)));
   }

   protected static <T> T cyclePropertyValue(Collection<T> collection, T object) {
      Iterator var5 = collection.iterator();

      while (var5.hasNext()) {
         if (var5.next().equals(object)) {
            if (var5.hasNext()) {
               return (T)var5.next();
            }

            return (T)collection.iterator().next();
         }
      }

      return (T)var5.next();
   }

   @Override
   public String toString() {
      StringBuilder var4 = new StringBuilder();
      var4.append(Block.blockRegistry.getNameForObject(this.getBlock()));
      if (!this.getProperties().isEmpty()) {
         var4.append("[");
         COMMA_JOINER.appendTo(var4, this.getProperties().entrySet().stream().map(MAP_ENTRY_TO_STRING::apply).collect(Collectors.toList()));
         var4.append("]");
      }

      return var4.toString();
   }
}
