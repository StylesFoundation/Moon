package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWall extends Block {
   public static final PropertyBool UP = PropertyBool.create("up");
   public static final PropertyBool NORTH = PropertyBool.create("north");
   public static final PropertyBool EAST = PropertyBool.create("east");
   public static final PropertyBool SOUTH = PropertyBool.create("south");
   public static final PropertyBool WEST = PropertyBool.create("west");
   public static final PropertyEnum<BlockWall.EnumType> VARIANT = PropertyEnum.create("variant", BlockWall.EnumType.class);

   public BlockWall(Block block) {
      super(block.blockMaterial);
      this.setDefaultState(
         this.blockState
            .getBaseState()
            .withProperty(UP, Boolean.FALSE)
            .withProperty(NORTH, Boolean.FALSE)
            .withProperty(EAST, Boolean.FALSE)
            .withProperty(SOUTH, Boolean.FALSE)
            .withProperty(WEST, Boolean.FALSE)
            .withProperty(VARIANT, BlockWall.EnumType.NORMAL)
      );
      this.setHardness(block.blockHardness);
      this.setResistance(block.blockResistance / 3.0F);
      this.setStepSound(block.stepSound);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   @Override
   public String getLocalizedName() {
      return StatCollector.translateToLocal(this.Method440() + "." + BlockWall.EnumType.NORMAL.getUnlocalizedName() + ".name");
   }

   @Override
   public boolean isFullCube() {
      return false;
   }

   @Override
   public boolean isPassable(IBlockAccess iBlockAccess, BlockPos blockPos) {
      return false;
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      boolean var6 = this.canConnectTo(iBlockAccess, blockPos.north());
      boolean var7 = this.canConnectTo(iBlockAccess, blockPos.south());
      boolean var8 = this.canConnectTo(iBlockAccess, blockPos.west());
      boolean var9 = this.canConnectTo(iBlockAccess, blockPos.east());
      float var10 = 0.25F;
      float var11 = 0.75F;
      float var12 = 0.25F;
      float var13 = 0.75F;
      float var14 = 1.0F;
      if (var6) {
         var12 = 0.0F;
      }

      if (var7) {
         var13 = 1.0F;
      }

      if (var8) {
         var10 = 0.0F;
      }

      if (var9) {
         var11 = 1.0F;
      }

      if (var6 && var7 && !var8 && !var9) {
         var14 = 0.8125F;
         var10 = 0.3125F;
         var11 = 0.6875F;
      } else if (!var6 && !var7 && var8 && var9) {
         var14 = 0.8125F;
         var12 = 0.3125F;
         var13 = 0.6875F;
      }

      this.setBlockBounds(var10, 0.0F, var12, var11, var14, var13);
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockPos, IBlockState iBlockState) {
      this.setBlockBoundsBasedOnState(world, blockPos);
      this.maxY = 1.5;
      return super.getCollisionBoundingBox(world, blockPos, iBlockState);
   }

   public boolean canConnectTo(IBlockAccess iBlockAccess, BlockPos blockPos) {
      Block var6 = iBlockAccess.getBlockState(blockPos).getBlock();
      return var6 != Blocks.barrier
         && (var6 == this || var6 instanceof BlockFenceGate || var6.blockMaterial.isOpaque() && var6.isFullCube() && var6.blockMaterial != Material.gourd);
   }

   @Override
   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      for (BlockWall.EnumType var10 : BlockWall.EnumType.values()) {
         list.add(new ItemStack(item, 1, var10.getMetadata()));
      }
   }

   @Override
   public int damageDropped(IBlockState iBlockState) {
      return iBlockState.getValue(VARIANT).getMetadata();
   }

   @Override
   public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing) {
      return enumFacing != EnumFacing.DOWN || super.shouldSideBeRendered(iBlockAccess, blockPos, enumFacing);
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(VARIANT, BlockWall.EnumType.byMetadata(integer));
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(VARIANT).getMetadata();
   }

   @Override
   public IBlockState getActualState(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
      return iBlockState.withProperty(UP, !iBlockAccess.isAirBlock(blockPos.up()))
         .withProperty(NORTH, this.canConnectTo(iBlockAccess, blockPos.north()))
         .withProperty(EAST, this.canConnectTo(iBlockAccess, blockPos.east()))
         .withProperty(SOUTH, this.canConnectTo(iBlockAccess, blockPos.south()))
         .withProperty(WEST, this.canConnectTo(iBlockAccess, blockPos.west()));
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, UP, NORTH, EAST, WEST, SOUTH, VARIANT);
   }

   public enum EnumType implements IStringSerializable {
      NORMAL(0, "cobblestone", "normal"),
      MOSSY(1, "mossy_cobblestone", "mossy");

      private static final BlockWall.EnumType[] META_LOOKUP = new BlockWall.EnumType[values().length];
      private final int meta;
      private final String name;
      private final String unlocalizedName;

      EnumType(int integer3, String string4, String string5) {
         this.meta = integer3;
         this.name = string4;
         this.unlocalizedName = string5;
      }

      public int getMetadata() {
         return this.meta;
      }

      @Override
      public String toString() {
         return this.name;
      }

      public static BlockWall.EnumType byMetadata(int integer) {
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

      private static BlockWall.EnumType[] Method985() {
         return new BlockWall.EnumType[]{NORMAL, MOSSY};
      }

      static {
         for (BlockWall.EnumType var3 : values()) {
            META_LOOKUP[var3.getMetadata()] = var3;
         }
      }
   }
}
