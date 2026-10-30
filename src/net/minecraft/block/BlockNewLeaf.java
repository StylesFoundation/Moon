package net.minecraft.block;

import java.util.List;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockNewLeaf extends BlockLeaves {
   public static final PropertyEnum<BlockPlanks.EnumType> VARIANT = PropertyEnum.create(
      "variant", BlockPlanks.EnumType.class, enumType -> enumType.getMetadata() >= 4
   );

   public BlockNewLeaf() {
      this.setDefaultState(
         this.blockState
            .getBaseState()
            .withProperty(VARIANT, BlockPlanks.EnumType.ACACIA)
            .withProperty(CHECK_DECAY, Boolean.TRUE)
            .withProperty(DECAYABLE, Boolean.TRUE)
      );
   }

   @Override
   protected void dropApple(World world, BlockPos blockPos, IBlockState iBlockState, int integer) {
      if (iBlockState.getValue(VARIANT) == BlockPlanks.EnumType.DARK_OAK && world.rand.nextInt(integer) == 0) {
         spawnAsEntity(world, blockPos, new ItemStack(Items.apple, 1, 0));
      }
   }

   @Override
   public int damageDropped(IBlockState iBlockState) {
      return iBlockState.getValue(VARIANT).getMetadata();
   }

   @Override
   public int getDamageValue(World world, BlockPos blockPos) {
      IBlockState var3 = world.getBlockState(blockPos);
      return var3.getBlock().getMetaFromState(var3) & 3;
   }

   @Override
   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      list.add(new ItemStack(item, 1, 0));
      list.add(new ItemStack(item, 1, 1));
   }

   @Override
   protected ItemStack createStackedBlock(IBlockState iBlockState) {
      return new ItemStack(Item.getItemFromBlock(this), 1, iBlockState.getValue(VARIANT).getMetadata() - 4);
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState()
         .withProperty(VARIANT, this.getWoodType(integer))
         .withProperty(DECAYABLE, (integer & 4) == 0)
         .withProperty(CHECK_DECAY, (integer & 8) > 0);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      int var5 = 0 | iBlockState.getValue(VARIANT).getMetadata() - 4;
      if (!iBlockState.getValue(DECAYABLE)) {
         var5 |= 4;
      }

      if (iBlockState.getValue(CHECK_DECAY)) {
         var5 |= 8;
      }

      return var5;
   }

   @Override
   public BlockPlanks.EnumType getWoodType(int integer) {
      return BlockPlanks.EnumType.byMetadata((integer & 3) + 4);
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, VARIANT, CHECK_DECAY, DECAYABLE);
   }

   @Override
   public void harvestBlock(World world, EntityPlayer entityPlayer, BlockPos blockPos, IBlockState iBlockState, TileEntity tileEntity) {
      if (!world.isRemote && entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() == Items.shears) {
         entityPlayer.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
         spawnAsEntity(world, blockPos, new ItemStack(Item.getItemFromBlock(this), 1, iBlockState.getValue(VARIANT).getMetadata() - 4));
      } else {
         super.harvestBlock(world, entityPlayer, blockPos, iBlockState, tileEntity);
      }
   }
}
