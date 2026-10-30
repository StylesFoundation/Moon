package net.minecraft.block;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public abstract class BlockFlower extends BlockBush {
   protected PropertyEnum<BlockFlower.EnumFlowerType> type;

   protected BlockFlower() {
      this.setDefaultState(
         this.blockState
            .getBaseState()
            .withProperty(
               this.getTypeProperty(),
               this.getBlockType() == BlockFlower.EnumFlowerColor.RED ? BlockFlower.EnumFlowerType.POPPY : BlockFlower.EnumFlowerType.DANDELION
            )
      );
   }

   @Override
   public int damageDropped(IBlockState iBlockState) {
      return iBlockState.getValue(this.getTypeProperty()).getMeta();
   }

   @Override
   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      for (BlockFlower.EnumFlowerType var10 : BlockFlower.EnumFlowerType.getTypes(this.getBlockType())) {
         list.add(new ItemStack(item, 1, var10.getMeta()));
      }
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(this.getTypeProperty(), BlockFlower.EnumFlowerType.getType(this.getBlockType(), integer));
   }

   public abstract BlockFlower.EnumFlowerColor getBlockType();

   public IProperty<BlockFlower.EnumFlowerType> getTypeProperty() {
      if (this.type == null) {
         this.type = PropertyEnum.create("type", BlockFlower.EnumFlowerType.class, enumFlowerType -> enumFlowerType.getBlockType() == this.getBlockType());
      }

      return this.type;
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(this.getTypeProperty()).getMeta();
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, this.getTypeProperty());
   }

   @Override
   public Block.EnumOffsetType getOffsetType() {
      return Block.EnumOffsetType.XZ;
   }

   public enum EnumFlowerColor {
      YELLOW,
      RED;

      public BlockFlower getBlock() {
         return this == YELLOW ? Blocks.yellow_flower : Blocks.red_flower;
      }

      private static BlockFlower.EnumFlowerColor[] Method245() {
         return new BlockFlower.EnumFlowerColor[]{YELLOW, RED};
      }
   }

   public enum EnumFlowerType implements IStringSerializable {
      DANDELION(BlockFlower.EnumFlowerColor.YELLOW, 0, "dandelion"),
      POPPY(BlockFlower.EnumFlowerColor.RED, 0, "poppy"),
      BLUE_ORCHID(BlockFlower.EnumFlowerColor.RED, 1, "blue_orchid", "blueOrchid"),
      ALLIUM(BlockFlower.EnumFlowerColor.RED, 2, "allium"),
      HOUSTONIA(BlockFlower.EnumFlowerColor.RED, 3, "houstonia"),
      RED_TULIP(BlockFlower.EnumFlowerColor.RED, 4, "red_tulip", "tulipRed"),
      ORANGE_TULIP(BlockFlower.EnumFlowerColor.RED, 5, "orange_tulip", "tulipOrange"),
      WHITE_TULIP(BlockFlower.EnumFlowerColor.RED, 6, "white_tulip", "tulipWhite"),
      PINK_TULIP(BlockFlower.EnumFlowerColor.RED, 7, "pink_tulip", "tulipPink"),
      OXEYE_DAISY(BlockFlower.EnumFlowerColor.RED, 8, "oxeye_daisy", "oxeyeDaisy");

      private static final BlockFlower.EnumFlowerType[][] TYPES_FOR_BLOCK = new BlockFlower.EnumFlowerType[BlockFlower.EnumFlowerColor.values().length][];
      private final BlockFlower.EnumFlowerColor blockType;
      private final int meta;
      private final String name;
      private final String unlocalizedName;

      EnumFlowerType(BlockFlower.EnumFlowerColor enumFlowerColor, int integer4, String string5) {
         this(enumFlowerColor, integer4, string5, string5);
      }

      EnumFlowerType(BlockFlower.EnumFlowerColor enumFlowerColor, int integer4, String string5, String string6) {
         this.blockType = enumFlowerColor;
         this.meta = integer4;
         this.name = string5;
         this.unlocalizedName = string6;
      }

      public BlockFlower.EnumFlowerColor getBlockType() {
         return this.blockType;
      }

      public int getMeta() {
         return this.meta;
      }

      public static BlockFlower.EnumFlowerType getType(BlockFlower.EnumFlowerColor enumFlowerColor, int integer) {
         BlockFlower.EnumFlowerType[] var5 = TYPES_FOR_BLOCK[enumFlowerColor.ordinal()];
         if (integer < 0 || integer >= var5.length) {
            integer = 0;
         }

         return var5[integer];
      }

      public static BlockFlower.EnumFlowerType[] getTypes(BlockFlower.EnumFlowerColor enumFlowerColor) {
         return TYPES_FOR_BLOCK[enumFlowerColor.ordinal()];
      }

      @Override
      public String toString() {
         return this.name;
      }

      @Override
      public String getName() {
         return this.name;
      }

      public String getUnlocalizedName() {
         return this.unlocalizedName;
      }

      private static BlockFlower.EnumFlowerType[] Method216() {
         return new BlockFlower.EnumFlowerType[]{
            DANDELION, POPPY, BLUE_ORCHID, ALLIUM, HOUSTONIA, RED_TULIP, ORANGE_TULIP, WHITE_TULIP, PINK_TULIP, OXEYE_DAISY
         };
      }

      static {
         for (BlockFlower.EnumFlowerColor var16 : BlockFlower.EnumFlowerColor.values()) {
            Collection<BlockFlower.EnumFlowerType> var17 = Collections2.filter(Lists.newArrayList(values()), enumFlowerType -> enumFlowerType.getBlockType() == var16);
            TYPES_FOR_BLOCK[var16.ordinal()] = var17.toArray(new BlockFlower.EnumFlowerType[var17.size()]);
         }
      }
   }
}
