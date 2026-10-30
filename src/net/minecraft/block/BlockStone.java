package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.StatCollector;

public class BlockStone extends Block {
   public static final PropertyEnum<BlockStone.EnumType> VARIANT = PropertyEnum.create("variant", BlockStone.EnumType.class);

   public BlockStone() {
      super(Material.rock);
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockStone.EnumType.STONE));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   @Override
   public String getLocalizedName() {
      return StatCollector.translateToLocal(this.Method440() + "." + BlockStone.EnumType.STONE.getUnlocalizedName() + ".name");
   }

   @Override
   public MapColor getMapColor(IBlockState iBlockState) {
      return iBlockState.getValue(VARIANT).func_181072_c();
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return iBlockState.getValue(VARIANT) == BlockStone.EnumType.STONE ? Item.getItemFromBlock(Blocks.cobblestone) : Item.getItemFromBlock(Blocks.stone);
   }

   @Override
   public int damageDropped(IBlockState iBlockState) {
      return iBlockState.getValue(VARIANT).getMetadata();
   }

   @Override
   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      for (BlockStone.EnumType var10 : BlockStone.EnumType.values()) {
         list.add(new ItemStack(item, 1, var10.getMetadata()));
      }
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(VARIANT, BlockStone.EnumType.byMetadata(integer));
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(VARIANT).getMetadata();
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, VARIANT);
   }

   public enum EnumType implements IStringSerializable {
      STONE(0, MapColor.stoneColor, "stone"),
      GRANITE(1, MapColor.dirtColor, "granite"),
      GRANITE_SMOOTH(2, MapColor.dirtColor, "smooth_granite", "graniteSmooth"),
      DIORITE(3, MapColor.quartzColor, "diorite"),
      DIORITE_SMOOTH(4, MapColor.quartzColor, "smooth_diorite", "dioriteSmooth"),
      ANDESITE(5, MapColor.stoneColor, "andesite"),
      ANDESITE_SMOOTH(6, MapColor.stoneColor, "smooth_andesite", "andesiteSmooth");

      private static final BlockStone.EnumType[] META_LOOKUP = new BlockStone.EnumType[values().length];
      private final int meta;
      private final String name;
      private final String unlocalizedName;
      private final MapColor field_181073_l;

      EnumType(int integer3, MapColor mapColor, String string5) {
         this(integer3, mapColor, string5, string5);
      }

      EnumType(int integer3, MapColor mapColor, String string5, String string6) {
         this.meta = integer3;
         this.name = string5;
         this.unlocalizedName = string6;
         this.field_181073_l = mapColor;
      }

      public int getMetadata() {
         return this.meta;
      }

      public MapColor func_181072_c() {
         return this.field_181073_l;
      }

      @Override
      public String toString() {
         return this.name;
      }

      public static BlockStone.EnumType byMetadata(int integer) {
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

      private static BlockStone.EnumType[] Method887() {
         return new BlockStone.EnumType[]{STONE, GRANITE, GRANITE_SMOOTH, DIORITE, DIORITE_SMOOTH, ANDESITE, ANDESITE_SMOOTH};
      }

      static {
         for (BlockStone.EnumType var16 : values()) {
            META_LOOKUP[var16.getMetadata()] = var16;
         }
      }
   }
}
