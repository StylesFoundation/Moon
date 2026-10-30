package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockOldLog extends BlockLog {
   public static final PropertyEnum<BlockPlanks.EnumType> VARIANT = PropertyEnum.create(
      "variant", BlockPlanks.EnumType.class, enumType -> enumType.getMetadata() < 4
   );

   public BlockOldLog() {
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockPlanks.EnumType.OAK).withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
   }

   @Override
   public MapColor getMapColor(IBlockState iBlockState) {
      BlockPlanks.EnumType var5 = iBlockState.getValue(VARIANT);
      switch (iBlockState.getValue(LOG_AXIS)) {
         case X :
         case Z :
         case NONE :
         default:
            switch (var5) {
               case OAK:
               default:
                  return BlockPlanks.EnumType.SPRUCE.getMapColor();
               case SPRUCE:
                  return BlockPlanks.EnumType.DARK_OAK.getMapColor();
               case BIRCH:
                  return MapColor.quartzColor;
            }
         case Y :
            return var5.getMapColor();
      }
   }

   @Override
   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      list.add(new ItemStack(item, 1, BlockPlanks.EnumType.OAK.getMetadata()));
      list.add(new ItemStack(item, 1, BlockPlanks.EnumType.SPRUCE.getMetadata()));
      list.add(new ItemStack(item, 1, BlockPlanks.EnumType.BIRCH.getMetadata()));
      list.add(new ItemStack(item, 1, BlockPlanks.EnumType.JUNGLE.getMetadata()));
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      IBlockState var5 = this.getDefaultState().withProperty(VARIANT, BlockPlanks.EnumType.byMetadata((integer & 3) % 4));

      return switch (integer & 12) {
         case 0 -> var5.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
         case 4 -> var5.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
         case 8 -> var5.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
         default -> var5.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
      };
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      int var5 = 0 | iBlockState.getValue(VARIANT).getMetadata();
      switch (iBlockState.getValue(LOG_AXIS)) {
         case X :
            var5 |= 4;
            break;
         case Z :
            var5 |= 8;
            break;
         case NONE :
            var5 |= 12;
      }

      return var5;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, VARIANT, LOG_AXIS);
   }

   @Override
   protected ItemStack createStackedBlock(IBlockState iBlockState) {
      return new ItemStack(Item.getItemFromBlock(this), 1, iBlockState.getValue(VARIANT).getMetadata());
   }

   @Override
   public int damageDropped(IBlockState iBlockState) {
      return iBlockState.getValue(VARIANT).getMetadata();
   }
}
