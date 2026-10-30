package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public class BlockStoneBrick extends Block {
   public static final PropertyEnum<BlockStoneBrick.EnumType> VARIANT = PropertyEnum.create("variant", BlockStoneBrick.EnumType.class);
   public static final int DEFAULT_META = BlockStoneBrick.EnumType.DEFAULT.getMetadata();
   public static final int MOSSY_META = BlockStoneBrick.EnumType.MOSSY.getMetadata();
   public static final int CRACKED_META = BlockStoneBrick.EnumType.CRACKED.getMetadata();
   public static final int CHISELED_META = BlockStoneBrick.EnumType.CHISELED.getMetadata();

   public BlockStoneBrick() {
      super(Material.rock);
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockStoneBrick.EnumType.DEFAULT));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   @Override
   public int damageDropped(IBlockState iBlockState) {
      return iBlockState.getValue(VARIANT).getMetadata();
   }

   @Override
   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      for (BlockStoneBrick.EnumType var10 : BlockStoneBrick.EnumType.values()) {
         list.add(new ItemStack(item, 1, var10.getMetadata()));
      }
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(VARIANT, BlockStoneBrick.EnumType.byMetadata(integer));
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
      DEFAULT(0, "stonebrick", "default"),
      MOSSY(1, "mossy_stonebrick", "mossy"),
      CRACKED(2, "cracked_stonebrick", "cracked"),
      CHISELED(3, "chiseled_stonebrick", "chiseled");

      private static final BlockStoneBrick.EnumType[] META_LOOKUP = new BlockStoneBrick.EnumType[values().length];
      private final int meta;
      private final String name;
      private final String unlocalizedName;

      EnumType(int integer3, String string4, String string5) {
         this.meta = integer3;
         this.name = string4;
         this.unlocalizedName = string5;
      }

      public int getMetadata() {
         return this.meta;
      }

      @Override
      public String toString() {
         return this.name;
      }

      public static BlockStoneBrick.EnumType byMetadata(int integer) {
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

      private static BlockStoneBrick.EnumType[] Method603() {
         return new BlockStoneBrick.EnumType[]{DEFAULT, MOSSY, CRACKED, CHISELED};
      }

      static {
         for (BlockStoneBrick.EnumType var3 : values()) {
            META_LOOKUP[var3.getMetadata()] = var3;
         }
      }
   }
}
