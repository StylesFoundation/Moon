package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFlowerPot extends BlockContainer {
   public static final PropertyInteger LEGACY_DATA = PropertyInteger.create("legacy_data", 0, 15);
   public static final PropertyEnum<BlockFlowerPot.EnumFlowerType> CONTENTS = PropertyEnum.create("contents", BlockFlowerPot.EnumFlowerType.class);

   public BlockFlowerPot() {
      super(Material.circuits);
      this.setDefaultState(this.blockState.getBaseState().withProperty(CONTENTS, BlockFlowerPot.EnumFlowerType.EMPTY).withProperty(LEGACY_DATA, 0));
      this.setBlockBoundsForItemRender();
   }

   @Override
   public String getLocalizedName() {
      return StatCollector.translateToLocal("item.flowerPot.name");
   }

   @Override
   public void setBlockBoundsForItemRender() {
      this.setBlockBounds(0.3125F, 0.0F, 0.3125F, 0.6875F, 0.375F, 0.6875F);
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public int getRenderType() {
      return 3;
   }

   @Override
   public boolean isFullCube() {
      return false;
   }

   @Override
   public int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos, int integer) {
      TileEntity var7 = iBlockAccess.getTileEntity(blockPos);
      if (var7 instanceof TileEntityFlowerPot) {
         Item var8 = ((TileEntityFlowerPot)var7).getFlowerPotItem();
         if (var8 instanceof ItemBlock) {
            return Block.getBlockFromItem(var8).colorMultiplier(iBlockAccess, blockPos, integer);
         }
      }

      return 16777215;
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      ItemStack var12 = entityPlayer.inventory.getCurrentItem();
      if (var12 != null && var12.getItem() instanceof ItemBlock) {
         TileEntityFlowerPot var13 = this.getTileEntity(world, blockPos);
         if (var13 == null) {
            return false;
         }

         if (var13.getFlowerPotItem() != null) {
            return false;
         }

         Block var14 = Block.getBlockFromItem(var12.getItem());
         if (!this.canNotContain(var14, var12.getMetadata())) {
            return false;
         }

         var13.setFlowerPotData(var12.getItem(), var12.getMetadata());
         var13.markDirty();
         world.markBlockForUpdate(blockPos);
         entityPlayer.triggerAchievement(StatList.field_181736_T);
         if (!entityPlayer.capabilities.isCreativeMode && --var12.stackSize <= 0) {
            entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean canNotContain(Block block, int integer) {
      return block == Blocks.yellow_flower
         || block == Blocks.red_flower
         || block == Blocks.cactus
         || block == Blocks.brown_mushroom
         || block == Blocks.red_mushroom
         || block == Blocks.sapling
         || block == Blocks.deadbush
         || block == Blocks.tallgrass && integer == BlockTallGrass.EnumType.FERN.getMeta();
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      TileEntityFlowerPot var6 = this.getTileEntity(world, blockPos);
      return var6 != null && var6.getFlowerPotItem() != null ? var6.getFlowerPotItem() : Items.flower_pot;
   }

   @Override
   public int getDamageValue(World world, BlockPos blockPos) {
      TileEntityFlowerPot var6 = this.getTileEntity(world, blockPos);
      return var6 != null && var6.getFlowerPotItem() != null ? var6.getFlowerPotData() : 0;
   }

   @Override
   public boolean isFlowerPot() {
      return true;
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      return super.canPlaceBlockAt(world, blockPos) && World.doesBlockHaveSolidTopSurface(world, blockPos.down());
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      if (!World.doesBlockHaveSolidTopSurface(world, blockPos.down())) {
         this.dropBlockAsItem(world, blockPos, iBlockState, 0);
         world.setBlockToAir(blockPos);
      }
   }

   @Override
   public void breakBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      TileEntityFlowerPot var7 = this.getTileEntity(world, blockPos);
      if (var7 != null && var7.getFlowerPotItem() != null) {
         spawnAsEntity(world, blockPos, new ItemStack(var7.getFlowerPotItem(), 1, var7.getFlowerPotData()));
      }

      super.breakBlock(world, blockPos, iBlockState);
   }

   @Override
   public void onBlockHarvested(World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer) {
      super.onBlockHarvested(world, blockPos, iBlockState, entityPlayer);
      if (entityPlayer.capabilities.isCreativeMode) {
         TileEntityFlowerPot var8 = this.getTileEntity(world, blockPos);
         if (var8 != null) {
            var8.setFlowerPotData(null, 0);
         }
      }
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Items.flower_pot;
   }

   private TileEntityFlowerPot getTileEntity(World world, BlockPos blockPos) {
      TileEntity var6 = world.getTileEntity(blockPos);
      return var6 instanceof TileEntityFlowerPot ? (TileEntityFlowerPot)var6 : null;
   }

   @Override
   public TileEntity createNewTileEntity(World world, int integer) {
      Block var5 = null;
      int var6 = 0;
      switch (integer) {
         case 1:
            var5 = Blocks.red_flower;
            var6 = BlockFlower.EnumFlowerType.POPPY.getMeta();
            break;
         case 2:
            var5 = Blocks.yellow_flower;
            break;
         case 3:
            var5 = Blocks.sapling;
            var6 = BlockPlanks.EnumType.OAK.getMetadata();
            break;
         case 4:
            var5 = Blocks.sapling;
            var6 = BlockPlanks.EnumType.SPRUCE.getMetadata();
            break;
         case 5:
            var5 = Blocks.sapling;
            var6 = BlockPlanks.EnumType.BIRCH.getMetadata();
            break;
         case 6:
            var5 = Blocks.sapling;
            var6 = BlockPlanks.EnumType.JUNGLE.getMetadata();
            break;
         case 7:
            var5 = Blocks.red_mushroom;
            break;
         case 8:
            var5 = Blocks.brown_mushroom;
            break;
         case 9:
            var5 = Blocks.cactus;
            break;
         case 10:
            var5 = Blocks.deadbush;
            break;
         case 11:
            var5 = Blocks.tallgrass;
            var6 = BlockTallGrass.EnumType.FERN.getMeta();
            break;
         case 12:
            var5 = Blocks.sapling;
            var6 = BlockPlanks.EnumType.ACACIA.getMetadata();
            break;
         case 13:
            var5 = Blocks.sapling;
            var6 = BlockPlanks.EnumType.DARK_OAK.getMetadata();
      }

      return new TileEntityFlowerPot(Item.getItemFromBlock(var5), var6);
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, CONTENTS, LEGACY_DATA);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(LEGACY_DATA);
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @Override
   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
   {
      BlockFlowerPot.EnumFlowerType blockflowerpot$enumflowertype = BlockFlowerPot.EnumFlowerType.EMPTY;
      TileEntity tileentity = worldIn.getTileEntity(pos);

      if (tileentity instanceof TileEntityFlowerPot)
      {
         TileEntityFlowerPot tileentityflowerpot = (TileEntityFlowerPot)tileentity;
         Item item = tileentityflowerpot.getFlowerPotItem();

         if (item instanceof ItemBlock)
         {
            int i = tileentityflowerpot.getFlowerPotData();
            Block block = Block.getBlockFromItem(item);

            if (block == Blocks.sapling)
            {
               switch (BlockPlanks.EnumType.byMetadata(i))
               {
                  case OAK:
                     blockflowerpot$enumflowertype = BlockFlowerPot.EnumFlowerType.OAK_SAPLING;
                     break;

                  case SPRUCE:
                     blockflowerpot$enumflowertype = BlockFlowerPot.EnumFlowerType.SPRUCE_SAPLING;
                     break;

                  case BIRCH:
                     blockflowerpot$enumflowertype = BlockFlowerPot.EnumFlowerType.BIRCH_SAPLING;
                     break;

                  case JUNGLE:
                     blockflowerpot$enumflowertype = BlockFlowerPot.EnumFlowerType.JUNGLE_SAPLING;
                     break;

                  case ACACIA:
                     blockflowerpot$enumflowertype = BlockFlowerPot.EnumFlowerType.ACACIA_SAPLING;
                     break;

                  case DARK_OAK:
                     blockflowerpot$enumflowertype = BlockFlowerPot.EnumFlowerType.DARK_OAK_SAPLING;
                     break;

                  default:
                     blockflowerpot$enumflowertype = BlockFlowerPot.EnumFlowerType.EMPTY;
               }
            }
            else if (block == Blocks.tallgrass)
            {
               switch (i)
               {
                  case 0:
                     blockflowerpot$enumflowertype = BlockFlowerPot.EnumFlowerType.DEAD_BUSH;
                     break;

                  case 2:
                     blockflowerpot$enumflowertype = BlockFlowerPot.EnumFlowerType.FERN;
                     break;

                  default:
                     blockflowerpot$enumflowertype = BlockFlowerPot.EnumFlowerType.EMPTY;
               }
            }
            else if (block == Blocks.yellow_flower)
            {
               blockflowerpot$enumflowertype = BlockFlowerPot.EnumFlowerType.DANDELION;
            }
            else if (block == Blocks.red_flower)
            {
               switch (BlockFlower.EnumFlowerType.getType(BlockFlower.EnumFlowerColor.RED, i))
               {
                  case POPPY:
                     blockflowerpot$enumflowertype = BlockFlowerPot.EnumFlowerType.POPPY;
                     break;

                  case BLUE_ORCHID:
                     blockflowerpot$enumflowertype = BlockFlowerPot.EnumFlowerType.BLUE_ORCHID;
                     break;

                  case ALLIUM:
                     blockflowerpot$enumflowertype = BlockFlowerPot.EnumFlowerType.ALLIUM;
                     break;

                  case HOUSTONIA:
                     blockflowerpot$enumflowertype = BlockFlowerPot.EnumFlowerType.HOUSTONIA;
                     break;

                  case RED_TULIP:
                     blockflowerpot$enumflowertype = BlockFlowerPot.EnumFlowerType.RED_TULIP;
                     break;

                  case ORANGE_TULIP:
                     blockflowerpot$enumflowertype = BlockFlowerPot.EnumFlowerType.ORANGE_TULIP;
                     break;

                  case WHITE_TULIP:
                     blockflowerpot$enumflowertype = BlockFlowerPot.EnumFlowerType.WHITE_TULIP;
                     break;

                  case PINK_TULIP:
                     blockflowerpot$enumflowertype = BlockFlowerPot.EnumFlowerType.PINK_TULIP;
                     break;

                  case OXEYE_DAISY:
                     blockflowerpot$enumflowertype = BlockFlowerPot.EnumFlowerType.OXEYE_DAISY;
                     break;

                  default:
                     blockflowerpot$enumflowertype = BlockFlowerPot.EnumFlowerType.EMPTY;
               }
            }
            else if (block == Blocks.red_mushroom)
            {
               blockflowerpot$enumflowertype = BlockFlowerPot.EnumFlowerType.MUSHROOM_RED;
            }
            else if (block == Blocks.brown_mushroom)
            {
               blockflowerpot$enumflowertype = BlockFlowerPot.EnumFlowerType.MUSHROOM_BROWN;
            }
            else if (block == Blocks.deadbush)
            {
               blockflowerpot$enumflowertype = BlockFlowerPot.EnumFlowerType.DEAD_BUSH;
            }
            else if (block == Blocks.cactus)
            {
               blockflowerpot$enumflowertype = BlockFlowerPot.EnumFlowerType.CACTUS;
            }
         }
      }

      return state.withProperty(CONTENTS, blockflowerpot$enumflowertype);
   }


   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   public enum EnumFlowerType implements IStringSerializable {
      EMPTY("empty"),
      POPPY("rose"),
      BLUE_ORCHID("blue_orchid"),
      ALLIUM("allium"),
      HOUSTONIA("houstonia"),
      RED_TULIP("red_tulip"),
      ORANGE_TULIP("orange_tulip"),
      WHITE_TULIP("white_tulip"),
      PINK_TULIP("pink_tulip"),
      OXEYE_DAISY("oxeye_daisy"),
      DANDELION("dandelion"),
      OAK_SAPLING("oak_sapling"),
      SPRUCE_SAPLING("spruce_sapling"),
      BIRCH_SAPLING("birch_sapling"),
      JUNGLE_SAPLING("jungle_sapling"),
      ACACIA_SAPLING("acacia_sapling"),
      DARK_OAK_SAPLING("dark_oak_sapling"),
      MUSHROOM_RED("mushroom_red"),
      MUSHROOM_BROWN("mushroom_brown"),
      DEAD_BUSH("dead_bush"),
      FERN("fern"),
      CACTUS("cactus");

      private final String name;

      EnumFlowerType(String string3) {
         this.name = string3;
      }

      @Override
      public String toString() {
         return this.name;
      }

      @Override
      public String getName() {
         return this.name;
      }

      private static BlockFlowerPot.EnumFlowerType[] Method241() {
         return new BlockFlowerPot.EnumFlowerType[]{
            EMPTY,
            POPPY,
            BLUE_ORCHID,
            ALLIUM,
            HOUSTONIA,
            RED_TULIP,
            ORANGE_TULIP,
            WHITE_TULIP,
            PINK_TULIP,
            OXEYE_DAISY,
            DANDELION,
            OAK_SAPLING,
            SPRUCE_SAPLING,
            BIRCH_SAPLING,
            JUNGLE_SAPLING,
            ACACIA_SAPLING,
            DARK_OAK_SAPLING,
            MUSHROOM_RED,
            MUSHROOM_BROWN,
            DEAD_BUSH,
            FERN,
            CACTUS
         };
      }
   }
}
