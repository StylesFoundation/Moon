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
import net.minecraft.world.World;

public abstract class BlockStoneSlab extends BlockSlab {
   public static final PropertyBool SEAMLESS = PropertyBool.create("seamless");
   public static final PropertyEnum<BlockStoneSlab.EnumType> VARIANT = PropertyEnum.create("variant", BlockStoneSlab.EnumType.class);

   public BlockStoneSlab() {
      super(Material.rock);
      IBlockState var4 = this.blockState.getBaseState();
      if (this.isDouble()) {
         var4 = var4.withProperty(SEAMLESS, Boolean.FALSE);
      } else {
         var4 = var4.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
      }

      this.setDefaultState(var4.withProperty(VARIANT, BlockStoneSlab.EnumType.STONE));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Item.getItemFromBlock(Blocks.stone_slab);
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return Item.getItemFromBlock(Blocks.stone_slab);
   }

   @Override
   public String getUnlocalizedName(int integer) {
      return super.Method440() + "." + BlockStoneSlab.EnumType.byMetadata(integer).getUnlocalizedName();
   }

   @Override
   public IProperty<?> getVariantProperty() {
      return VARIANT;
   }

   @Override
   public Object getVariant(ItemStack itemStack) {
      return BlockStoneSlab.EnumType.byMetadata(itemStack.getMetadata() & 7);
   }

   @Override
   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      if (item != Item.getItemFromBlock(Blocks.double_stone_slab)) {
         for (BlockStoneSlab.EnumType var10 : BlockStoneSlab.EnumType.values()) {
            if (var10 != BlockStoneSlab.EnumType.WOOD) {
               list.add(new ItemStack(item, 1, var10.getMetadata()));
            }
         }
      }
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      IBlockState var5 = this.getDefaultState().withProperty(VARIANT, BlockStoneSlab.EnumType.byMetadata(integer & 7));
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
   public int damageDropped(IBlockState iBlockState) {
      return iBlockState.getValue(VARIANT).getMetadata();
   }

   @Override
   public MapColor getMapColor(IBlockState iBlockState) {
      return iBlockState.getValue(VARIANT).func_181074_c();
   }

   public enum EnumType implements IStringSerializable {
      STONE(0, MapColor.stoneColor, "stone"),
      SAND(1, MapColor.sandColor, "sandstone", "sand"),
      WOOD(2, MapColor.woodColor, "wood_old", "wood"),
      COBBLESTONE(3, MapColor.stoneColor, "cobblestone", "cobble"),
      BRICK(4, MapColor.redColor, "brick"),
      SMOOTHBRICK(5, MapColor.stoneColor, "stone_brick", "smoothStoneBrick"),
      NETHERBRICK(6, MapColor.netherrackColor, "nether_brick", "netherBrick"),
      QUARTZ(7, MapColor.quartzColor, "quartz");

      private static final BlockStoneSlab.EnumType[] META_LOOKUP = new BlockStoneSlab.EnumType[values().length];
      private final int meta;
      private final MapColor field_181075_k;
      private final String name;
      private final String unlocalizedName;

      EnumType(int integer3, MapColor mapColor, String string5) {
         this(integer3, mapColor, string5, string5);
      }

      EnumType(int integer3, MapColor mapColor, String string5, String string6) {
         this.meta = integer3;
         this.field_181075_k = mapColor;
         this.name = string5;
         this.unlocalizedName = string6;
      }

      public int getMetadata() {
         return this.meta;
      }

      public MapColor func_181074_c() {
         return this.field_181075_k;
      }

      @Override
      public String toString() {
         return this.name;
      }

      public static BlockStoneSlab.EnumType byMetadata(int integer) {
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

      private static BlockStoneSlab.EnumType[] Method531() {
         return new BlockStoneSlab.EnumType[]{STONE, SAND, WOOD, COBBLESTONE, BRICK, SMOOTHBRICK, NETHERBRICK, QUARTZ};
      }

      static {
         for (BlockStoneSlab.EnumType var16 : values()) {
            META_LOOKUP[var16.getMetadata()] = var16;
         }
      }
   }
}
