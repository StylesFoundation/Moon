package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;

public class BlockDoublePlant extends BlockBush implements IGrowable {
   public static final PropertyEnum<BlockDoublePlant.EnumPlantType> VARIANT = PropertyEnum.create("variant", BlockDoublePlant.EnumPlantType.class);
   public static final PropertyEnum<BlockDoublePlant.EnumBlockHalf> HALF = PropertyEnum.create("half", BlockDoublePlant.EnumBlockHalf.class);
   public static final PropertyEnum<EnumFacing> FACING = BlockDirectional.FACING;

   public BlockDoublePlant() {
      super(Material.vine);
      this.setDefaultState(
         this.blockState
            .getBaseState()
            .withProperty(VARIANT, BlockDoublePlant.EnumPlantType.SUNFLOWER)
            .withProperty(HALF, BlockDoublePlant.EnumBlockHalf.LOWER)
            .withProperty(FACING, EnumFacing.NORTH)
      );
      this.setHardness(0.0F);
      this.setStepSound(soundTypeGrass);
      this.setUnlocalizedName("doublePlant");
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   public BlockDoublePlant.EnumPlantType getVariant(IBlockAccess iBlockAccess, BlockPos blockPos) {
      IBlockState var6 = iBlockAccess.getBlockState(blockPos);
      return var6.getBlock() == this ? this.getActualState(var6, iBlockAccess, blockPos).getValue(VARIANT) : BlockDoublePlant.EnumPlantType.FERN;
   }

   @Override
   public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
      return super.canPlaceBlockAt(world, blockPos) && world.isAirBlock(blockPos.up());
   }

   @Override
   public boolean isReplaceable(World world, BlockPos blockPos) {
      IBlockState var6 = world.getBlockState(blockPos);
      if (var6.getBlock() != this) {
         return true;
      }

      BlockDoublePlant.EnumPlantType var7 = this.getActualState(var6, world, blockPos).getValue(VARIANT);
      return var7 == BlockDoublePlant.EnumPlantType.FERN || var7 == BlockDoublePlant.EnumPlantType.GRASS;
   }

   @Override
   protected void checkAndDropBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (!this.canBlockStay(world, blockPos, iBlockState)) {
         boolean var7 = iBlockState.getValue(HALF) == BlockDoublePlant.EnumBlockHalf.UPPER;
         BlockPos var8 = var7 ? blockPos : blockPos.up();
         BlockPos var9 = var7 ? blockPos.down() : blockPos;
         Block var10 = var7 ? this : world.getBlockState(var8).getBlock();
         Block var11 = var7 ? world.getBlockState(var9).getBlock() : this;
         if (var10 == this) {
            world.setBlockState(var8, Blocks.air.getDefaultState(), 2);
         }

         if (var11 == this) {
            world.setBlockState(var9, Blocks.air.getDefaultState(), 3);
            if (!var7) {
               this.dropBlockAsItem(world, var9, iBlockState, 0);
            }
         }
      }
   }

   @Override
   public boolean canBlockStay(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (iBlockState.getValue(HALF) == BlockDoublePlant.EnumBlockHalf.UPPER) {
         return world.getBlockState(blockPos.down()).getBlock() == this;
      }

      IBlockState var7 = world.getBlockState(blockPos.up());
      return var7.getBlock() == this && super.canBlockStay(world, blockPos, var7);
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      if (iBlockState.getValue(HALF) == BlockDoublePlant.EnumBlockHalf.UPPER) {
         return null;
      }

      BlockDoublePlant.EnumPlantType var7 = iBlockState.getValue(VARIANT);
      return var7 == BlockDoublePlant.EnumPlantType.FERN
         ? null
         : (var7 == BlockDoublePlant.EnumPlantType.GRASS ? (random.nextInt(8) == 0 ? Items.wheat_seeds : null) : Item.getItemFromBlock(this));
   }

   @Override
   public int damageDropped(IBlockState iBlockState) {
      return iBlockState.getValue(HALF) != BlockDoublePlant.EnumBlockHalf.UPPER && iBlockState.getValue(VARIANT) != BlockDoublePlant.EnumPlantType.GRASS
         ? iBlockState.getValue(VARIANT).getMeta()
         : 0;
   }

   @Override
   public int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos, int integer) {
      BlockDoublePlant.EnumPlantType var7 = this.getVariant(iBlockAccess, blockPos);
      return var7 != BlockDoublePlant.EnumPlantType.GRASS && var7 != BlockDoublePlant.EnumPlantType.FERN
         ? 16777215
         : BiomeColorHelper.getGrassColorAtPos(iBlockAccess, blockPos);
   }

   public void placeAt(World world, BlockPos blockPos, BlockDoublePlant.EnumPlantType enumPlantType, int integer) {
      world.setBlockState(
         blockPos, this.getDefaultState().withProperty(HALF, BlockDoublePlant.EnumBlockHalf.LOWER).withProperty(VARIANT, enumPlantType), integer
      );
      world.setBlockState(blockPos.up(), this.getDefaultState().withProperty(HALF, BlockDoublePlant.EnumBlockHalf.UPPER), integer);
   }

   @Override
   public void onBlockPlacedBy(World world, BlockPos blockPos, IBlockState iBlockState, EntityLivingBase entityLivingBase, ItemStack itemStack) {
      world.setBlockState(blockPos.up(), this.getDefaultState().withProperty(HALF, BlockDoublePlant.EnumBlockHalf.UPPER), 2);
   }

   @Override
   public void harvestBlock(World world, EntityPlayer entityPlayer, BlockPos blockPos, IBlockState iBlockState, TileEntity tileEntity) {
      if (world.isRemote
         || entityPlayer.getCurrentEquippedItem() == null
         || entityPlayer.getCurrentEquippedItem().getItem() != Items.shears
         || iBlockState.getValue(HALF) != BlockDoublePlant.EnumBlockHalf.LOWER
         || !this.onHarvest(world, blockPos, iBlockState, entityPlayer)) {
         super.harvestBlock(world, entityPlayer, blockPos, iBlockState, tileEntity);
      }
   }

   @Override
   public void onBlockHarvested(World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer) {
      if (iBlockState.getValue(HALF) == BlockDoublePlant.EnumBlockHalf.UPPER) {
         if (world.getBlockState(blockPos.down()).getBlock() == this) {
            if (!entityPlayer.capabilities.isCreativeMode) {
               IBlockState var8 = world.getBlockState(blockPos.down());
               BlockDoublePlant.EnumPlantType var9 = var8.getValue(VARIANT);
               if (var9 != BlockDoublePlant.EnumPlantType.FERN && var9 != BlockDoublePlant.EnumPlantType.GRASS) {
                  world.Method329(blockPos.down(), true);
               } else if (!world.isRemote) {
                  if (entityPlayer.getCurrentEquippedItem() != null && entityPlayer.getCurrentEquippedItem().getItem() == Items.shears) {
                     this.onHarvest(world, blockPos, var8, entityPlayer);
                     world.setBlockToAir(blockPos.down());
                  } else {
                     world.Method329(blockPos.down(), true);
                  }
               } else {
                  world.setBlockToAir(blockPos.down());
               }
            } else {
               world.setBlockToAir(blockPos.down());
            }
         }
      } else if (entityPlayer.capabilities.isCreativeMode && world.getBlockState(blockPos.up()).getBlock() == this) {
         world.setBlockState(blockPos.up(), Blocks.air.getDefaultState(), 2);
      }

      super.onBlockHarvested(world, blockPos, iBlockState, entityPlayer);
   }

   private boolean onHarvest(World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer) {
      BlockDoublePlant.EnumPlantType var8 = iBlockState.getValue(VARIANT);
      if (var8 != BlockDoublePlant.EnumPlantType.FERN && var8 != BlockDoublePlant.EnumPlantType.GRASS) {
         return false;
      }

      entityPlayer.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
      int var9 = (var8 == BlockDoublePlant.EnumPlantType.GRASS ? BlockTallGrass.EnumType.GRASS : BlockTallGrass.EnumType.FERN).getMeta();
      spawnAsEntity(world, blockPos, new ItemStack(Blocks.tallgrass, 2, var9));
      return true;
   }

   @Override
   public void getSubBlocks(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
      for (BlockDoublePlant.EnumPlantType var10 : BlockDoublePlant.EnumPlantType.values()) {
         list.add(new ItemStack(item, 1, var10.getMeta()));
      }
   }

   @Override
   public int getDamageValue(World world, BlockPos blockPos) {
      return this.getVariant(world, blockPos).getMeta();
   }

   @Override
   public boolean canGrow(World world, BlockPos blockPos, IBlockState iBlockState, boolean boolean4) {
      BlockDoublePlant.EnumPlantType var8 = this.getVariant(world, blockPos);
      return var8 != BlockDoublePlant.EnumPlantType.GRASS && var8 != BlockDoublePlant.EnumPlantType.FERN;
   }

   @Override
   public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {
      return true;
   }

   @Override
   public void grow(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {
      spawnAsEntity(world, blockPos, new ItemStack(this, 1, this.getVariant(world, blockPos).getMeta()));
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return (integer & 8) > 0
         ? this.getDefaultState().withProperty(HALF, BlockDoublePlant.EnumBlockHalf.UPPER)
         : this.getDefaultState()
            .withProperty(HALF, BlockDoublePlant.EnumBlockHalf.LOWER)
            .withProperty(VARIANT, BlockDoublePlant.EnumPlantType.byMetadata(integer & 7));
   }

   @Override
   public IBlockState getActualState(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
      if (iBlockState.getValue(HALF) == BlockDoublePlant.EnumBlockHalf.UPPER) {
         IBlockState var7 = iBlockAccess.getBlockState(blockPos.down());
         if (var7.getBlock() == this) {
            iBlockState = iBlockState.withProperty(VARIANT, var7.getValue(VARIANT));
         }
      }

      return iBlockState;
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(HALF) == BlockDoublePlant.EnumBlockHalf.UPPER
         ? 8 | iBlockState.getValue(FACING).getHorizontalIndex()
         : iBlockState.getValue(VARIANT).getMeta();
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, HALF, VARIANT, FACING);
   }

   @Override
   public Block.EnumOffsetType getOffsetType() {
      return Block.EnumOffsetType.XZ;
   }

   public enum EnumBlockHalf implements IStringSerializable {
      UPPER,
      LOWER;

      @Override
      public String toString() {
         return this.getName();
      }

      @Override
      public String getName() {
         return this == UPPER ? "upper" : "lower";
      }

      private static BlockDoublePlant.EnumBlockHalf[] Method994() {
         return new BlockDoublePlant.EnumBlockHalf[]{UPPER, LOWER};
      }
   }

   public enum EnumPlantType implements IStringSerializable {
      SUNFLOWER(0, "sunflower"),
      SYRINGA(1, "syringa"),
      GRASS(2, "double_grass", "grass"),
      FERN(3, "double_fern", "fern"),
      ROSE(4, "double_rose", "rose"),
      PAEONIA(5, "paeonia");

      private static final BlockDoublePlant.EnumPlantType[] META_LOOKUP = new BlockDoublePlant.EnumPlantType[values().length];
      private final int meta;
      private final String name;
      private final String unlocalizedName;

      EnumPlantType(int integer3, String string4) {
         this(integer3, string4, string4);
      }

      EnumPlantType(int integer3, String string4, String string5) {
         this.meta = integer3;
         this.name = string4;
         this.unlocalizedName = string5;
      }

      public int getMeta() {
         return this.meta;
      }

      @Override
      public String toString() {
         return this.name;
      }

      public static BlockDoublePlant.EnumPlantType byMetadata(int integer) {
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

      private static BlockDoublePlant.EnumPlantType[] Method715() {
         return new BlockDoublePlant.EnumPlantType[]{SUNFLOWER, SYRINGA, GRASS, FERN, ROSE, PAEONIA};
      }

      static {
         for (BlockDoublePlant.EnumPlantType var10 : values()) {
            META_LOOKUP[var10.getMeta()] = var10;
         }
      }
   }
}
