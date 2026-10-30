package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

public class BlockHugeMushroom extends Block {
   public static final PropertyEnum<BlockHugeMushroom.EnumType> VARIANT = PropertyEnum.create("variant", BlockHugeMushroom.EnumType.class);
   private final Block smallBlock;

   public BlockHugeMushroom(Material material, MapColor mapColor, Block block) {
      super(material, mapColor);
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockHugeMushroom.EnumType.ALL_OUTSIDE));
      this.smallBlock = block;
   }

   @Override
   public int quantityDropped(Random random) {
      return Math.max(0, random.nextInt(10) - 7);
   }

   @Override
   public MapColor getMapColor(IBlockState iBlockState) {
      switch ((BlockHugeMushroom.EnumType)iBlockState.getValue(VARIANT)) {
         case ALL_STEM:
            return MapColor.clothColor;
         case ALL_INSIDE:
         case STEM:
            return MapColor.sandColor;
         default:
            return super.getMapColor(iBlockState);
      }
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Item.getItemFromBlock(this.smallBlock);
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return Item.getItemFromBlock(this.smallBlock);
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      return this.getDefaultState();
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(VARIANT, BlockHugeMushroom.EnumType.byMetadata(integer));
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
      NORTH_WEST(1, "north_west"),
      NORTH(2, "north"),
      NORTH_EAST(3, "north_east"),
      WEST(4, "west"),
      CENTER(5, "center"),
      EAST(6, "east"),
      SOUTH_WEST(7, "south_west"),
      SOUTH(8, "south"),
      SOUTH_EAST(9, "south_east"),
      STEM(10, "stem"),
      ALL_INSIDE(0, "all_inside"),
      ALL_OUTSIDE(14, "all_outside"),
      ALL_STEM(15, "all_stem");

      private static final BlockHugeMushroom.EnumType[] META_LOOKUP = new BlockHugeMushroom.EnumType[16];
      private final int meta;
      private final String name;

      EnumType(int integer3, String string4) {
         this.meta = integer3;
         this.name = string4;
      }

      public int getMetadata() {
         return this.meta;
      }

      @Override
      public String toString() {
         return this.name;
      }

      public static BlockHugeMushroom.EnumType byMetadata(int integer) {
         if (integer < 0 || integer >= META_LOOKUP.length) {
            integer = 0;
         }

         BlockHugeMushroom.EnumType var4 = META_LOOKUP[integer];
         return var4 == null ? META_LOOKUP[0] : var4;
      }

      @Override
      public String getName() {
         return this.name;
      }

      private static BlockHugeMushroom.EnumType[] Method552() {
         return new BlockHugeMushroom.EnumType[]{
            NORTH_WEST, NORTH, NORTH_EAST, WEST, CENTER, EAST, SOUTH_WEST, SOUTH, SOUTH_EAST, STEM, ALL_INSIDE, ALL_OUTSIDE, ALL_STEM
         };
      }

      static {
         for (BlockHugeMushroom.EnumType var16 : values()) {
            META_LOOKUP[var16.getMetadata()] = var16;
         }
      }
   }
}
