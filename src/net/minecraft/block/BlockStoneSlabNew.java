package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public abstract class BlockStoneSlabNew extends BlockSlab {
   public static final PropertyBool SEAMLESS = PropertyBool.create("seamless");
   public static final PropertyEnum<BlockStoneSlabNew.EnumType> VARIANT = PropertyEnum.create("variant", BlockStoneSlabNew.EnumType.class);

   public BlockStoneSlabNew() {
      super(Material.rock);
      IBlockState var4 = this.blockState.getBaseState();
      if (this.isDouble()) {
         var4 = var4.withProperty(SEAMLESS, Boolean.FALSE);
      } else {
         var4 = var4.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
      }

      this.setDefaultState(var4.withProperty(VARIANT, BlockStoneSlabNew.EnumType.RED_SANDSTONE));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   @Override
   public String getLocalizedName() {
      return StatCollector.translateToLocal(this.Method440() + ".red_sandstone.name");
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Item.getItemFromBlock(Blocks.stone_slab2);
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return Item.getItemFromBlock(Blocks.stone_slab2);
   }

   @Override
   public String getUnlocalizedName(int integer) {
      return super.Method440() + "." + BlockStoneSlabNew.EnumType.byMetadata(integer).getUnlocalizedName();
   }

   @Override
   public IProperty<?> getVariantProperty() {
      return VARIANT;
   }

   @Override
   public Object getVariant(ItemStack itemStack) {
      return BlockStoneSlabNew.EnumType.byMetadata(itemStack.getMetadata() & 7);
   }

   @Override
   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      if (item != Item.getItemFromBlock(Blocks.double_stone_slab2)) {
         for (BlockStoneSlabNew.EnumType var10 : BlockStoneSlabNew.EnumType.values()) {
            list.add(new ItemStack(item, 1, var10.getMetadata()));
         }
      }
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      IBlockState var5 = this.getDefaultState().withProperty(VARIANT, BlockStoneSlabNew.EnumType.byMetadata(integer & 7));
      if (this.isDouble()) {
         var5 = var5.withProperty(SEAMLESS, (integer & 8) != 0);
      } else {
         var5 = var5.withProperty(HALF, (integer & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
      }

      return var5;
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      int var5 = 0 | iBlockState.getValue(VARIANT).getMetadata();
      if (this.isDouble()) {
         if (iBlockState.getValue(SEAMLESS)) {
            var5 |= 8;
         }
      } else if (iBlockState.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) {
         var5 |= 8;
      }

      return var5;
   }

   @Override
   protected BlockState createBlockState() {
      return this.isDouble() ? new BlockState(this, SEAMLESS, VARIANT) : new BlockState(this, HALF, VARIANT);
   }

   @Override
   public MapColor getMapColor(IBlockState iBlockState) {
      return iBlockState.getValue(VARIANT).func_181068_c();
   }

   @Override
   public int damageDropped(IBlockState iBlockState) {
      return iBlockState.getValue(VARIANT).getMetadata();
   }

   public enum EnumType implements IStringSerializable {
      RED_SANDSTONE(0, "red_sandstone", BlockSand.EnumType.RED_SAND.getMapColor());

      private static final BlockStoneSlabNew.EnumType[] META_LOOKUP = new BlockStoneSlabNew.EnumType[values().length];
      private final int meta;
      private final String name;
      private final MapColor field_181069_e;

      EnumType(int integer3, String string4, MapColor mapColor) {
         this.meta = integer3;
         this.name = string4;
         this.field_181069_e = mapColor;
      }

      public int getMetadata() {
         return this.meta;
      }

      public MapColor func_181068_c() {
         return this.field_181069_e;
      }

      @Override
      public String toString() {
         return this.name;
      }

      public static BlockStoneSlabNew.EnumType byMetadata(int integer) {
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
         return this.name;
      }

      private static BlockStoneSlabNew.EnumType[] Method999() {
         return new BlockStoneSlabNew.EnumType[]{RED_SANDSTONE};
      }

      static {
         for (BlockStoneSlabNew.EnumType var3 : values()) {
            META_LOOKUP[var3.getMetadata()] = var3;
         }
      }
   }
}
