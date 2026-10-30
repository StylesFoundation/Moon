package net.minecraft.client.renderer.block.statemap;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class StateMap extends StateMapperBase {
   private final IProperty<?> name;
   private final String suffix;
   private final List<IProperty<?>> ignored;

   private StateMap(IProperty<?> iProperty, String string, List<IProperty<?>> list) {
      this.name = iProperty;
      this.suffix = string;
      this.ignored = list;
   }

   @Override
   protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
      LinkedHashMap var2 = Maps.newLinkedHashMap(iBlockState.getProperties());
      String var3;
      if (this.name == null) {
         var3 = Block.blockRegistry.getNameForObject(iBlockState.getBlock()).toString();
      } else {
         var3 = ((IProperty)this.name).getName((Comparable)var2.remove(this.name));
      }

      if (this.suffix != null) {
         var3 = var3 + this.suffix;
      }

      for (IProperty var5 : this.ignored) {
         var2.remove(var5);
      }

      return new ModelResourceLocation(var3, this.getPropertyString(var2));
   }

   public static class Builder {
      private IProperty<?> name;
      private String suffix;
      private final List<IProperty<?>> ignored = Lists.newArrayList();

      public StateMap.Builder withName(IProperty<?> iProperty) {
         this.name = iProperty;
         return this;
      }

      public StateMap.Builder withSuffix(String string) {
         this.suffix = string;
         return this;
      }

      public StateMap.Builder ignore(IProperty<?>... arr) {
         Collections.addAll(this.ignored, arr);
         return this;
      }

      public StateMap build() {
         return new StateMap(this.name, this.suffix, this.ignored);
      }
   }
}
