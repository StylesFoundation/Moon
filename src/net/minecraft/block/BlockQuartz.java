package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

public class BlockQuartz extends Block {
   public static final PropertyEnum<BlockQuartz.EnumType> VARIANT = PropertyEnum.create("variant", BlockQuartz.EnumType.class);

   public BlockQuartz() {
      super(Material.rock);
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockQuartz.EnumType.DEFAULT));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      if (integer == BlockQuartz.EnumType.LINES_Y.getMetadata()) {
         switch (enumFacing.getAxis()) {
            case Z:
               return this.getDefaultState().withProperty(VARIANT, BlockQuartz.EnumType.LINES_Z);
            case X:
               return this.getDefaultState().withProperty(VARIANT, BlockQuartz.EnumType.LINES_X);
            case Y:
            default:
               return this.getDefaultState().withProperty(VARIANT, BlockQuartz.EnumType.LINES_Y);
         }
      } else {
         return integer == BlockQuartz.EnumType.CHISELED.getMetadata()
            ? this.getDefaultState().withProperty(VARIANT, BlockQuartz.EnumType.CHISELED)
            : this.getDefaultState().withProperty(VARIANT, BlockQuartz.EnumType.DEFAULT);
      }
   }

   @Override
   public int damageDropped(IBlockState iBlockState) {
      BlockQuartz.EnumType var5 = iBlockState.getValue(VARIANT);
      return var5 != BlockQuartz.EnumType.LINES_X && var5 != BlockQuartz.EnumType.LINES_Z ? var5.getMetadata() : BlockQuartz.EnumType.LINES_Y.getMetadata();
   }

   @Override
   protected ItemStack createStackedBlock(IBlockState iBlockState) {
      BlockQuartz.EnumType var5 = iBlockState.getValue(VARIANT);
      return var5 != BlockQuartz.EnumType.LINES_X && var5 != BlockQuartz.EnumType.LINES_Z
         ? super.createStackedBlock(iBlockState)
         : new ItemStack(Item.getItemFromBlock(this), 1, BlockQuartz.EnumType.LINES_Y.getMetadata());
   }

   @Override
   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      list.add(new ItemStack(item, 1, BlockQuartz.EnumType.DEFAULT.getMetadata()));
      list.add(new ItemStack(item, 1, BlockQuartz.EnumType.CHISELED.getMetadata()));
      list.add(new ItemStack(item, 1, BlockQuartz.EnumType.LINES_Y.getMetadata()));
   }

   @Override
   public MapColor getMapColor(IBlockState iBlockState) {
      return MapColor.quartzColor;
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(VARIANT, BlockQuartz.EnumType.byMetadata(integer));
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
      DEFAULT(0, "default", "default"),
      CHISELED(1, "chiseled", "chiseled"),
      LINES_Y(2, "lines_y", "lines"),
      LINES_X(3, "lines_x", "lines"),
      LINES_Z(4, "lines_z", "lines");

      private static final BlockQuartz.EnumType[] META_LOOKUP = new BlockQuartz.EnumType[values().length];
      private final int meta;
      private final String field_176805_h;
      private final String unlocalizedName;

      EnumType(int integer3, String string4, String string5) {
         this.meta = integer3;
         this.field_176805_h = string4;
         this.unlocalizedName = string5;
      }

      public int getMetadata() {
         return this.meta;
      }

      @Override
      public String toString() {
         return this.unlocalizedName;
      }

      public static BlockQuartz.EnumType byMetadata(int integer) {
         if (integer < 0 || integer >= META_LOOKUP.length) {
            integer = 0;
         }

         return META_LOOKUP[integer];
      }

      @Override
      public String getName() {
         return this.field_176805_h;
      }

      private static BlockQuartz.EnumType[] Method214() {
         return new BlockQuartz.EnumType[]{DEFAULT, CHISELED, LINES_Y, LINES_X, LINES_Z};
      }

      static {
         for (BlockQuartz.EnumType var3 : values()) {
            META_LOOKUP[var3.getMetadata()] = var3;
         }
      }
   }
}
