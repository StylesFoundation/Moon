package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateHelper;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StatCollector;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSkull extends BlockContainer {
   public static final PropertyDirection FACING = PropertyDirection.create("facing");
   public static final PropertyBool NODROP = PropertyBool.create("nodrop");
   private static final Predicate<BlockWorldState> T = blockWorldState -> blockWorldState.getBlockState() != null
      && blockWorldState.getBlockState().getBlock() == Blocks.skull
      && blockWorldState.getTileEntity() instanceof TileEntitySkull
      && ((TileEntitySkull)blockWorldState.getTileEntity()).getSkullType() == 1;
   private BlockPattern witherBasePattern;
   private BlockPattern witherPattern;

   protected BlockSkull() {
      super(Material.circuits);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(NODROP, Boolean.FALSE));
      this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
   }

   @Override
   public String getLocalizedName() {
      return StatCollector.translateToLocal("tile.skull.skeleton.name");
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public boolean isFullCube() {
      return false;
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      switch ((EnumFacing)iBlockAccess.getBlockState(blockPos).getValue(FACING)) {
         case UP:
         default:
            this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
            break;
         case NORTH:
            this.setBlockBounds(0.25F, 0.25F, 0.5F, 0.75F, 0.75F, 1.0F);
            break;
         case SOUTH:
            this.setBlockBounds(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.5F);
            break;
         case WEST:
            this.setBlockBounds(0.5F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
            break;
         case EAST:
            this.setBlockBounds(0.0F, 0.25F, 0.25F, 0.5F, 0.75F, 0.75F);
      }
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockPos, IBlockState iBlockState) {
      this.setBlockBoundsBasedOnState(world, blockPos);
      return super.getCollisionBoundingBox(world, blockPos, iBlockState);
   }

   @Override
   public IBlockState onBlockPlaced(
      World world, BlockPos blockPos, EnumFacing enumFacing, float float4, float float5, float float6, int integer, EntityLivingBase entityLivingBase
   ) {
      return this.getDefaultState().withProperty(FACING, entityLivingBase.getHorizontalFacing()).withProperty(NODROP, Boolean.FALSE);
   }

   @Override
   public TileEntity createNewTileEntity(World world, int integer) {
      return new TileEntitySkull();
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return Items.skull;
   }

   @Override
   public int getDamageValue(World world, BlockPos blockPos) {
      TileEntity var6 = world.getTileEntity(blockPos);
      return var6 instanceof TileEntitySkull ? ((TileEntitySkull)var6).getSkullType() : super.getDamageValue(world, blockPos);
   }

   @Override
   public void dropBlockAsItemWithChance(World world, BlockPos blockPos, IBlockState iBlockState, float float4, int integer) {
   }

   @Override
   public void onBlockHarvested(World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer) {
      if (entityPlayer.capabilities.isCreativeMode) {
         iBlockState = iBlockState.withProperty(NODROP, Boolean.TRUE);
         world.setBlockState(blockPos, iBlockState, 4);
      }

      super.onBlockHarvested(world, blockPos, iBlockState, entityPlayer);
   }

   @Override
   public void breakBlock(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (!world.isRemote) {
         if (!iBlockState.getValue(NODROP) && world.getTileEntity(blockPos) instanceof TileEntitySkull var8) {
            ItemStack var9 = new ItemStack(Items.skull, 1, this.getDamageValue(world, blockPos));
            if (var8.getSkullType() == 3 && var8.getPlayerProfile() != null) {
               var9.setTagCompound(new NBTTagCompound());
               NBTTagCompound var10 = new NBTTagCompound();
               NBTUtil.writeGameProfile(var10, var8.getPlayerProfile());
               var9.getTagCompound().setTag("SkullOwner", var10);
            }

            spawnAsEntity(world, blockPos, var9);
         }

         super.breakBlock(world, blockPos, iBlockState);
      }
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return Items.skull;
   }

   public boolean canDispenserPlace(World world, BlockPos blockPos, ItemStack itemStack) {
      return itemStack.getMetadata() == 1
         && blockPos.getY() >= 2
         && world.getDifficulty() != EnumDifficulty.PEACEFUL
         && !world.isRemote
         && this.getWitherBasePattern().match(world, blockPos) != null;
   }

   public void checkWitherSpawn(World world, BlockPos blockPos, TileEntitySkull tileEntitySkull) {
      if (tileEntitySkull.getSkullType() == 1 && blockPos.getY() >= 2 && world.getDifficulty() != EnumDifficulty.PEACEFUL && !world.isRemote) {
         BlockPattern var7 = this.getWitherPattern();
         BlockPattern.PatternHelper var8 = var7.match(world, blockPos);
         if (var8 != null) {
            for (int var9 = 0; var9 < 3; var9++) {
               BlockWorldState var10 = var8.translateOffset(var9, 0, 0);
               world.setBlockState(var10.getPos(), var10.getBlockState().withProperty(NODROP, Boolean.TRUE), 2);
            }

            for (int var15 = 0; var15 < var7.getPalmLength(); var15++) {
               for (int var17 = 0; var17 < var7.getThumbLength(); var17++) {
                  BlockWorldState var11 = var8.translateOffset(var15, var17, 0);
                  world.setBlockState(var11.getPos(), Blocks.air.getDefaultState(), 2);
               }
            }

            BlockPos var16 = var8.translateOffset(1, 0, 0).getPos();
            EntityWither var18 = new EntityWither(world);
            BlockPos var19 = var8.translateOffset(1, 2, 0).getPos();
            var18.setLocationAndAngles(
               var19.getX() + 0.5, var19.getY() + 0.55, var19.getZ() + 0.5, var8.getFinger().getAxis() == EnumFacing.Axis.X ? 0.0F : 90.0F, 0.0F
            );
            var18.renderYawOffset = var8.getFinger().getAxis() == EnumFacing.Axis.X ? 0.0F : 90.0F;
            var18.func_82206_m();

            for (EntityPlayer var13 : world.getEntitiesWithinAABB(EntityPlayer.class, var18.getEntityBoundingBox().expand(50.0, 50.0, 50.0))) {
               var13.triggerAchievement(AchievementList.spawnWither);
            }

            world.spawnEntityInWorld(var18);

            for (int var20 = 0; var20 < 120; var20++) {
               world.spawnParticle(
                  EnumParticleTypes.SNOWBALL,
                  var16.getX() + world.rand.nextDouble(),
                  var16.getY() - 2 + world.rand.nextDouble() * 3.9,
                  var16.getZ() + world.rand.nextDouble(),
                  0.0,
                  0.0,
                  0.0
               );
            }

            for (int var21 = 0; var21 < var7.getPalmLength(); var21++) {
               for (int var22 = 0; var22 < var7.getThumbLength(); var22++) {
                  BlockWorldState var14 = var8.translateOffset(var21, var22, 0);
                  world.notifyNeighborsRespectDebug(var14.getPos(), Blocks.air);
               }
            }
         }
      }
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(integer & 7)).withProperty(NODROP, (integer & 8) > 0);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      int var5 = 0 | iBlockState.getValue(FACING).getIndex();
      if (iBlockState.getValue(NODROP)) {
         var5 |= 8;
      }

      return var5;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, FACING, NODROP);
   }

   protected BlockPattern getWitherBasePattern() {
      if (this.witherBasePattern == null) {
         this.witherBasePattern = FactoryBlockPattern.start()
            .aisle("   ", "###", "~#~")
            .where('#', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.soul_sand)))
            .where('~', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.air)))
            .build();
      }

      return this.witherBasePattern;
   }

   protected BlockPattern getWitherPattern() {
      if (this.witherPattern == null) {
         this.witherPattern = FactoryBlockPattern.start()
            .aisle("^^^", "###", "~#~")
            .where('#', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.soul_sand)))
            .where('^', T)
            .where('~', BlockWorldState.hasState(BlockStateHelper.forBlock(Blocks.air)))
            .build();
      }

      return this.witherPattern;
   }
}
