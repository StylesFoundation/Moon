package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public class BlockSandStone extends Block {
   public static final PropertyEnum<BlockSandStone.EnumType> TYPE = PropertyEnum.create("type", BlockSandStone.EnumType.class);

   public BlockSandStone() {
      super(Material.rock);
      this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, BlockSandStone.EnumType.DEFAULT));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   @Override
   public int damageDropped(IBlockState iBlockState) {
      return iBlockState.getValue(TYPE).getMetadata();
   }

   @Override
   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      for (BlockSandStone.EnumType var10 : BlockSandStone.EnumType.values()) {
         list.add(new ItemStack(item, 1, var10.getMetadata()));
      }
   }

   @Override
   public MapColor getMapColor(IBlockState iBlockState) {
      return MapColor.sandColor;
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(TYPE, BlockSandStone.EnumType.byMetadata(integer));
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(TYPE).getMetadata();
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, TYPE);
   }

   public enum EnumType implements IStringSerializable {
      DEFAULT(0, "sandstone", "default"),
      CHISELED(1, "chiseled_sandstone", "chiseled"),
      SMOOTH(2, "smooth_sandstone", "smooth");

      private static final BlockSandStone.EnumType[] META_LOOKUP = new BlockSandStone.EnumType[values().length];
      private final int metadata;
      private final String name;
      private final String unlocalizedName;

      EnumType(int integer3, String string4, String string5) {
         this.metadata = integer3;
         this.name = string4;
         this.unlocalizedName = string5;
      }

      public int getMetadata() {
         return this.metadata;
      }

      @Override
      public String toString() {
         return this.name;
      }

      public static BlockSandStone.EnumType byMetadata(int integer) {
         if (integer < 0 || integer >= META_LOOKUP.length) {
            integer = 0;
         }

         return META_LOOKUP[integer];
      }

      @Override
      public String getName() {
         return this.name;
      }

      public String getUnlocalizedName() {
         return this.unlocalizedName;
      }

      private static BlockSandStone.EnumType[] Method516() {
         return new BlockSandStone.EnumType[]{DEFAULT, CHISELED, SMOOTH};
      }

      static {
         for (BlockSandStone.EnumType var3 : values()) {
            META_LOOKUP[var3.getMetadata()] = var3;
         }
      }
   }
}
