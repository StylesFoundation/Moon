package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenCanopyTree;
import net.minecraft.world.gen.feature.WorldGenForest;
import net.minecraft.world.gen.feature.WorldGenMegaJungle;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class BlockSapling extends BlockBush implements IGrowable {
   public static final PropertyEnum<BlockPlanks.EnumType> TYPE = PropertyEnum.create("type", BlockPlanks.EnumType.class);
   public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);

   protected BlockSapling() {
      this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, BlockPlanks.EnumType.OAK).withProperty(STAGE, 0));
      this.setBlockBounds(0.099999994F, 0.0F, 0.099999994F, 0.9F, 0.8F, 0.9F);
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   @Override
   public String getLocalizedName() {
      return StatCollector.translateToLocal(this.Method440() + "." + BlockPlanks.EnumType.OAK.getUnlocalizedName() + ".name");
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (!world.isRemote) {
         super.updateTick(world, blockPos, iBlockState, random);
         if (world.getLightFromNeighbors(blockPos.up()) >= 9 && random.nextInt(7) == 0) {
            this.grow(world, blockPos, iBlockState, random);
         }
      }
   }

   public void grow(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      if (iBlockState.getValue(STAGE) == 0) {
         world.setBlockState(blockPos, iBlockState.cycleProperty(STAGE), 4);
      } else {
         this.generateTree(world, blockPos, iBlockState, random);
      }
   }

   public void generateTree(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      WorldGenAbstractTree var8 = random.nextInt(10) == 0 ? new WorldGenBigTree(true) : new WorldGenTrees(true);
      int var9 = 0;
      int var10 = 0;
      boolean var11 = false;
      switch ((BlockPlanks.EnumType)iBlockState.getValue(TYPE)) {
         case SPRUCE:
            label68:
            for (var9 = 0; var9 >= -1; var9--) {
               for (var10 = 0; var10 >= -1; var10--) {
                  if (this.func_181624_a(world, blockPos, var9, var10, BlockPlanks.EnumType.SPRUCE)) {
                     var8 = new WorldGenMegaPineTree(false, random.nextBoolean());
                     var11 = true;
                     break label68;
                  }
               }
            }

            if (!var11) {
               var10 = 0;
               var9 = 0;
               var8 = new WorldGenTaiga2(true);
            }
            break;
         case BIRCH:
            var8 = new WorldGenForest(true, false);
            break;
         case JUNGLE:
            IBlockState var12 = Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE);
            IBlockState var13 = Blocks.leaves
               .getDefaultState()
               .withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.JUNGLE)
               .withProperty(BlockLeaves.CHECK_DECAY, Boolean.FALSE);

            label82:
            for (var9 = 0; var9 >= -1; var9--) {
               for (var10 = 0; var10 >= -1; var10--) {
                  if (this.func_181624_a(world, blockPos, var9, var10, BlockPlanks.EnumType.JUNGLE)) {
                     var8 = new WorldGenMegaJungle(true, 10, 20, var12, var13);
                     var11 = true;
                     break label82;
                  }
               }
            }

            if (!var11) {
               var10 = 0;
               var9 = 0;
               var8 = new WorldGenTrees(true, 4 + random.nextInt(7), var12, var13, false);
            }
            break;
         case ACACIA:
            var8 = new WorldGenSavannaTree(true);
            break;
         case DARK_OAK:
            label96:
            for (var9 = 0; var9 >= -1; var9--) {
               for (var10 = 0; var10 >= -1; var10--) {
                  if (this.func_181624_a(world, blockPos, var9, var10, BlockPlanks.EnumType.DARK_OAK)) {
                     var8 = new WorldGenCanopyTree(true);
                     var11 = true;
                     break label96;
                  }
               }
            }

            if (!var11) {
               return;
            }
         case OAK:
      }

      IBlockState var14 = Blocks.air.getDefaultState();
      if (var11) {
         world.setBlockState(blockPos.add(var9, 0, var10), var14, 4);
         world.setBlockState(blockPos.add(var9 + 1, 0, var10), var14, 4);
         world.setBlockState(blockPos.add(var9, 0, var10 + 1), var14, 4);
         world.setBlockState(blockPos.add(var9 + 1, 0, var10 + 1), var14, 4);
      } else {
         world.setBlockState(blockPos, var14, 4);
      }

      if (!var8.generate(world, random, blockPos.add(var9, 0, var10))) {
         if (var11) {
            world.setBlockState(blockPos.add(var9, 0, var10), iBlockState, 4);
            world.setBlockState(blockPos.add(var9 + 1, 0, var10), iBlockState, 4);
            world.setBlockState(blockPos.add(var9, 0, var10 + 1), iBlockState, 4);
            world.setBlockState(blockPos.add(var9 + 1, 0, var10 + 1), iBlockState, 4);
         } else {
            world.setBlockState(blockPos, iBlockState, 4);
         }
      }
   }

   private boolean func_181624_a(World world, BlockPos blockPos, int integer3, int integer4, BlockPlanks.EnumType enumType) {
      return this.isTypeAt(world, blockPos.add(integer3, 0, integer4), enumType)
         && this.isTypeAt(world, blockPos.add(integer3 + 1, 0, integer4), enumType)
         && this.isTypeAt(world, blockPos.add(integer3, 0, integer4 + 1), enumType)
         && this.isTypeAt(world, blockPos.add(integer3 + 1, 0, integer4 + 1), enumType);
   }

   public boolean isTypeAt(World world, BlockPos blockPos, BlockPlanks.EnumType enumType) {
      IBlockState var7 = world.getBlockState(blockPos);
      return var7.getBlock() == this && var7.getValue(TYPE) == enumType;
   }

   @Override
   public int damageDropped(IBlockState iBlockState) {
      return iBlockState.getValue(TYPE).getMetadata();
   }

   @Override
   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      for (BlockPlanks.EnumType var10 : BlockPlanks.EnumType.values()) {
         list.add(new ItemStack(item, 1, var10.getMetadata()));
      }
   }

   @Override
   public boolean canGrow(World world, BlockPos blockPos, IBlockState iBlockState, boolean boolean4) {
      return true;
   }

   @Override
   public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {
      return world.rand.nextFloat() < 0.45;
   }

   @Override
   public void grow(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {
      this.grow(world, blockPos, iBlockState, random);
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(TYPE, BlockPlanks.EnumType.byMetadata(integer & 7)).withProperty(STAGE, (integer & 8) >> 3);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return 0 | iBlockState.getValue(TYPE).getMetadata() | iBlockState.getValue(STAGE) << 3;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, TYPE, STAGE);
   }
}
