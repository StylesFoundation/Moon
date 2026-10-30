package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDirt extends Block {
   public static final PropertyEnum<BlockDirt.DirtType> VARIANT = PropertyEnum.create("variant", BlockDirt.DirtType.class);
   public static final PropertyBool SNOWY = PropertyBool.create("snowy");

   protected BlockDirt() {
      super(Material.ground);
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockDirt.DirtType.DIRT).withProperty(SNOWY, Boolean.FALSE));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   @Override
   public MapColor getMapColor(IBlockState iBlockState) {
      return iBlockState.getValue(VARIANT).func_181066_d();
   }

   @Override
   public IBlockState getActualState(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
      if (iBlockState.getValue(VARIANT) == BlockDirt.DirtType.PODZOL) {
         Block var7 = iBlockAccess.getBlockState(blockPos.up()).getBlock();
         iBlockState = iBlockState.withProperty(SNOWY, var7 == Blocks.snow || var7 == Blocks.snow_layer);
      }

      return iBlockState;
   }

   @Override
   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      list.add(new ItemStack(this, 1, BlockDirt.DirtType.DIRT.getMetadata()));
      list.add(new ItemStack(this, 1, BlockDirt.DirtType.COARSE_DIRT.getMetadata()));
      list.add(new ItemStack(this, 1, BlockDirt.DirtType.PODZOL.getMetadata()));
   }

   @Override
   public int getDamageValue(World world, BlockPos blockPos) {
      IBlockState var6 = world.getBlockState(blockPos);
      return var6.getBlock() != this ? 0 : var6.getValue(VARIANT).getMetadata();
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(VARIANT, BlockDirt.DirtType.byMetadata(integer));
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(VARIANT).getMetadata();
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, VARIANT, SNOWY);
   }

   @Override
   public int damageDropped(IBlockState iBlockState) {
      BlockDirt.DirtType var5 = iBlockState.getValue(VARIANT);
      if (var5 == BlockDirt.DirtType.PODZOL) {
         var5 = BlockDirt.DirtType.DIRT;
      }

      return var5.getMetadata();
   }

   public enum DirtType implements IStringSerializable {
      DIRT(0, "dirt", "default", MapColor.dirtColor),
      COARSE_DIRT(1, "coarse_dirt", "coarse", MapColor.dirtColor),
      PODZOL(2, "podzol", MapColor.obsidianColor);

      private static final BlockDirt.DirtType[] METADATA_LOOKUP = new BlockDirt.DirtType[values().length];
      private final int metadata;
      private final String name;
      private final String unlocalizedName;
      private final MapColor field_181067_h;

      DirtType(int integer3, String string4, MapColor mapColor) {
         this(integer3, string4, string4, mapColor);
      }

      DirtType(int integer3, String string4, String string5, MapColor mapColor) {
         this.metadata = integer3;
         this.name = string4;
         this.unlocalizedName = string5;
         this.field_181067_h = mapColor;
      }

      public int getMetadata() {
         return this.metadata;
      }

      public String getUnlocalizedName() {
         return this.unlocalizedName;
      }

      public MapColor func_181066_d() {
         return this.field_181067_h;
      }

      @Override
      public String toString() {
         return this.name;
      }

      public static BlockDirt.DirtType byMetadata(int integer) {
         if (integer < 0 || integer >= METADATA_LOOKUP.length) {
            integer = 0;
         }

         return METADATA_LOOKUP[integer];
      }

      @Override
      public String getName() {
         return this.name;
      }

      private static BlockDirt.DirtType[] Method429() {
         return new BlockDirt.DirtType[]{DIRT, COARSE_DIRT, PODZOL};
      }

      static {
         for (BlockDirt.DirtType var3 : values()) {
            METADATA_LOOKUP[var3.getMetadata()] = var3;
         }
      }
   }
}
