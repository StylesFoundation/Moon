package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class BlockWoodSlab extends BlockSlab {
   public static final PropertyEnum<BlockPlanks.EnumType> VARIANT = PropertyEnum.create("variant", BlockPlanks.EnumType.class);

   public BlockWoodSlab() {
      super(Material.wood);
      IBlockState var4 = this.blockState.getBaseState();
      if (!this.isDouble()) {
         var4 = var4.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
      }

      this.setDefaultState(var4.withProperty(VARIANT, BlockPlanks.EnumType.OAK));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   @Override
   public MapColor getMapColor(IBlockState iBlockState) {
      return iBlockState.getValue(VARIANT).getMapColor();
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Item.getItemFromBlock(Blocks.wooden_slab);
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return Item.getItemFromBlock(Blocks.wooden_slab);
   }

   @Override
   public String getUnlocalizedName(int integer) {
      return super.Method440() + "." + BlockPlanks.EnumType.byMetadata(integer).getUnlocalizedName();
   }

   @Override
   public IProperty<?> getVariantProperty() {
      return VARIANT;
   }

   @Override
   public Object getVariant(ItemStack itemStack) {
      return BlockPlanks.EnumType.byMetadata(itemStack.getMetadata() & 7);
   }

   @Override
   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      if (item != Item.getItemFromBlock(Blocks.double_wooden_slab)) {
         for (BlockPlanks.EnumType var10 : BlockPlanks.EnumType.values()) {
            list.add(new ItemStack(item, 1, var10.getMetadata()));
         }
      }
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      IBlockState var4 = this.getDefaultState().withProperty(VARIANT, BlockPlanks.EnumType.byMetadata(integer & 7));
      if (!this.isDouble()) {
         var4 = var4.withProperty(HALF, (integer & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
      }

      return var4;
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      int var5 = 0 | iBlockState.getValue(VARIANT).getMetadata();
      if (!this.isDouble() && iBlockState.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) {
         var5 |= 8;
      }

      return var5;
   }

   @Override
   protected BlockState createBlockState() {
      return this.isDouble() ? new BlockState(this, VARIANT) : new BlockState(this, HALF, VARIANT);
   }

   @Override
   public int damageDropped(IBlockState iBlockState) {
      return iBlockState.getValue(VARIANT).getMetadata();
   }
}
