package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTallGrass extends BlockBush implements IGrowable {
   public static final PropertyEnum<BlockTallGrass.EnumType> TYPE = PropertyEnum.create("type", BlockTallGrass.EnumType.class);

   protected BlockTallGrass() {
      super(Material.vine);
      this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, BlockTallGrass.EnumType.DEAD_BUSH));
      this.setBlockBounds(0.099999994F, 0.0F, 0.099999994F, 0.9F, 0.8F, 0.9F);
   }

   @Override
   public int getBlockColor() {
      return ColorizerGrass.getGrassColor(0.5, 1.0);
   }

   @Override
   public boolean canBlockStay(World world, BlockPos blockPos, IBlockState iBlockState) {
      return this.canPlaceBlockOn(world.getBlockState(blockPos.down()).getBlock());
   }

   @Override
   public boolean isReplaceable(World world, BlockPos blockPos) {
      return true;
   }

   @Override
   public int getRenderColor(IBlockState iBlockState) {
      if (iBlockState.getBlock() != this) {
         return super.getRenderColor(iBlockState);
      }

      BlockTallGrass.EnumType var5 = iBlockState.getValue(TYPE);
      return var5 == BlockTallGrass.EnumType.DEAD_BUSH ? 16777215 : ColorizerGrass.getGrassColor(0.5, 1.0);
   }

   @Override
   public int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos, int integer) {
      return iBlockAccess.getBiomeGenForCoords(blockPos).getGrassColorAtPos(blockPos);
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return random.nextInt(8) == 0 ? Items.wheat_seeds : null;
   }

   @Override
   public int quantityDroppedWithBonus(int integer, Random random) {
      return 1 + random.nextInt(integer * 2 + 1);
   }

   @Override
   public void harvestBlock(World world, EntityPlayer entityPlayer, BlockPos blockPos, IBlockState iBlockState, TileEntity tileEntity) {
      if (!world.isRemote && entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() == Items.shears) {
         entityPlayer.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
         spawnAsEntity(world, blockPos, new ItemStack(Blocks.tallgrass, 1, iBlockState.getValue(TYPE).getMeta()));
      } else {
         super.harvestBlock(world, entityPlayer, blockPos, iBlockState, tileEntity);
      }
   }

   @Override
   public int getDamageValue(World world, BlockPos blockPos) {
      IBlockState var3 = world.getBlockState(blockPos);
      return var3.getBlock().getMetaFromState(var3);
   }

   @Override
   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      for (int var7 = 1; var7 < 3; var7++) {
         list.add(new ItemStack(item, 1, var7));
      }
   }

   @Override
   public boolean canGrow(World world, BlockPos blockPos, IBlockState iBlockState, boolean boolean4) {
      return iBlockState.getValue(TYPE) != BlockTallGrass.EnumType.DEAD_BUSH;
   }

   @Override
   public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {
      return true;
   }

   @Override
   public void grow(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {
      BlockDoublePlant.EnumPlantType var8 = BlockDoublePlant.EnumPlantType.GRASS;
      if (iBlockState.getValue(TYPE) == BlockTallGrass.EnumType.FERN) {
         var8 = BlockDoublePlant.EnumPlantType.FERN;
      }

      if (Blocks.double_plant.canPlaceBlockAt(world, blockPos)) {
         Blocks.double_plant.placeAt(world, blockPos, var8, 2);
      }
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(TYPE, BlockTallGrass.EnumType.byMetadata(integer));
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(TYPE).getMeta();
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, TYPE);
   }

   @Override
   public Block.EnumOffsetType getOffsetType() {
      return Block.EnumOffsetType.XYZ;
   }

   public enum EnumType implements IStringSerializable {
      DEAD_BUSH(0, "dead_bush"),
      GRASS(1, "tall_grass"),
      FERN(2, "fern");

      private static final BlockTallGrass.EnumType[] META_LOOKUP = new BlockTallGrass.EnumType[values().length];
      private final int meta;
      private final String name;

      EnumType(int integer3, String string4) {
         this.meta = integer3;
         this.name = string4;
      }

      public int getMeta() {
         return this.meta;
      }

      @Override
      public String toString() {
         return this.name;
      }

      public static BlockTallGrass.EnumType byMetadata(int integer) {
         if (integer < 0 || integer >= META_LOOKUP.length) {
            integer = 0;
         }

         return META_LOOKUP[integer];
      }

      @Override
      public String getName() {
         return this.name;
      }

      private static BlockTallGrass.EnumType[] Method935() {
         return new BlockTallGrass.EnumType[]{DEAD_BUSH, GRASS, FERN};
      }

      static {
         for (BlockTallGrass.EnumType var3 : values()) {
            META_LOOKUP[var3.getMeta()] = var3;
         }
      }
   }
}
