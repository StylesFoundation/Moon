package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockNewLog extends BlockLog {
   public static final PropertyEnum<BlockPlanks.EnumType> VARIANT = PropertyEnum.create(
      "variant", BlockPlanks.EnumType.class, enumType -> enumType.getMetadata() >= 4
   );

   public BlockNewLog() {
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockPlanks.EnumType.ACACIA).withProperty(LOG_AXIS, BlockLog.EnumAxis.Y));
   }


   public MapColor getMapColor(IBlockState state)
   {
      BlockPlanks.EnumType blockplanks$enumtype = state.getValue(VARIANT);

      switch (state.getValue(LOG_AXIS))
      {
         case X:
         case Z:
         case NONE:
         default:
            switch (blockplanks$enumtype)
            {
               case ACACIA:
               default:
                  return MapColor.stoneColor;

               case DARK_OAK:
                  return BlockPlanks.EnumType.DARK_OAK.getMapColor();
            }

         case Y:
            return blockplanks$enumtype.getMapColor();
      }
   }

   @Override
   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      list.add(new ItemStack(item, 1, BlockPlanks.EnumType.ACACIA.getMetadata() - 4));
      list.add(new ItemStack(item, 1, BlockPlanks.EnumType.DARK_OAK.getMetadata() - 4));
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      IBlockState var5 = this.getDefaultState().withProperty(VARIANT, BlockPlanks.EnumType.byMetadata((integer & 3) + 4));

      return switch (integer & 12) {
         case 0 -> var5.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
         case 4 -> var5.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
         case 8 -> var5.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
         default -> var5.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
      };
   }

   @Override
   public int getMetaFromState(IBlockState state)
   {
      int i = 0;
      i = i | state.getValue(VARIANT).getMetadata() - 4;

      switch (state.getValue(LOG_AXIS))
      {
         case X:
            i |= 4;
            break;

         case Z:
            i |= 8;
            break;

         case NONE:
            i |= 12;
      }

      return i;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, VARIANT, LOG_AXIS);
   }

   @Override
   protected ItemStack createStackedBlock(IBlockState iBlockState) {
      return new ItemStack(Item.getItemFromBlock(this), 1, iBlockState.getValue(VARIANT).getMetadata() - 4);
   }

   @Override
   public int damageDropped(IBlockState iBlockState) {
      return iBlockState.getValue(VARIANT).getMetadata() - 4;
   }
}
