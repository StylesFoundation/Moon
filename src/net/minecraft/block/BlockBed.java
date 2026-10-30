package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class BlockBed extends BlockDirectional {
   public static final PropertyEnum<BlockBed.EnumPartType> PART = PropertyEnum.create("part", BlockBed.EnumPartType.class);
   public static final PropertyBool OCCUPIED = PropertyBool.create("occupied");

   public BlockBed() {
      super(Material.cloth);
      this.setDefaultState(this.blockState.getBaseState().withProperty(PART, BlockBed.EnumPartType.FOOT).withProperty(OCCUPIED, Boolean.FALSE));
      this.setBedBounds();
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (world.isRemote) {
         return true;
      }

      if (iBlockState.getValue(PART) != BlockBed.EnumPartType.HEAD) {
         blockPos = blockPos.offset(iBlockState.getValue(FACING));
         iBlockState = world.getBlockState(blockPos);
         if (iBlockState.getBlock() != this) {
            return true;
         }
      }

      if (world.provider.canRespawnHere() && world.getBiomeGenForCoords(blockPos) != BiomeGenBase.hell) {
         if (iBlockState.getValue(OCCUPIED)) {
            EntityPlayer var14 = this.getPlayerInBed(world, blockPos);
            if (var14 != null) {
               entityPlayer.addChatComponentMessage(new ChatComponentTranslation("tile.bed.occupied"));
               return true;
            }

            iBlockState = iBlockState.withProperty(OCCUPIED, Boolean.FALSE);
            world.setBlockState(blockPos, iBlockState, 4);
         }

         EntityPlayer.EnumStatus var15 = entityPlayer.trySleep(blockPos);
         if (var15 == EntityPlayer.EnumStatus.OK) {
            iBlockState = iBlockState.withProperty(OCCUPIED, Boolean.TRUE);
            world.setBlockState(blockPos, iBlockState, 4);
         } else if (var15 == EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW) {
            entityPlayer.addChatComponentMessage(new ChatComponentTranslation("tile.bed.noSleep"));
         } else if (var15 == EntityPlayer.EnumStatus.NOT_SAFE) {
            entityPlayer.addChatComponentMessage(new ChatComponentTranslation("tile.bed.notSafe"));
         }

         return true;
      } else {
         world.setBlockToAir(blockPos);
         BlockPos var12 = blockPos.offset(iBlockState.getValue(FACING).getOpposite());
         if (world.getBlockState(var12).getBlock() == this) {
            world.setBlockToAir(var12);
         }

         world.newExplosion(null, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, 5.0F, true, true);
         return true;
      }
   }

   private EntityPlayer getPlayerInBed(World world, BlockPos blockPos) {
      for (EntityPlayer var7 : world.playerEntities) {
         if (var7.isPlayerSleeping() && var7.playerLocation.equals(blockPos)) {
            return var7;
         }
      }

      return null;
   }

   @Override
   public boolean isFullCube() {
      return false;
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {
      this.setBedBounds();
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      EnumFacing var8 = iBlockState.getValue(FACING);
      if (iBlockState.getValue(PART) == BlockBed.EnumPartType.HEAD) {
         if (world.getBlockState(blockPos.offset(var8.getOpposite())).getBlock() != this) {
            world.setBlockToAir(blockPos);
         }
      } else if (world.getBlockState(blockPos.offset(var8)).getBlock() != this) {
         world.setBlockToAir(blockPos);
         if (!world.isRemote) {
            this.dropBlockAsItem(world, blockPos, iBlockState, 0);
         }
      }
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return iBlockState.getValue(PART) == BlockBed.EnumPartType.HEAD ? null : Items.bed;
   }

   private void setBedBounds() {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
   }

   public static BlockPos getSafeExitLocation(World world, BlockPos blockPos, int integer) {
      EnumFacing var6 = world.getBlockState(blockPos).getValue(FACING);
      int var7 = blockPos.getX();
      int var8 = blockPos.getY();
      int var9 = blockPos.getZ();

      for (int var10 = 0; var10 <= 1; var10++) {
         int var11 = var7 - var6.getFrontOffsetX() * var10 - 1;
         int var12 = var9 - var6.getFrontOffsetZ() * var10 - 1;
         int var13 = var11 + 2;
         int var14 = var12 + 2;

         for (int var15 = var11; var15 <= var13; var15++) {
            for (int var16 = var12; var16 <= var14; var16++) {
               BlockPos var17 = new BlockPos(var15, var8, var16);
               if (hasRoomForPlayer(world, var17)) {
                  if (integer <= 0) {
                     return var17;
                  }

                  integer--;
               }
            }
         }
      }

      return null;
   }

   protected static boolean hasRoomForPlayer(World world, BlockPos blockPos) {
      return World.doesBlockHaveSolidTopSurface(world, blockPos.down())
         && !world.getBlockState(blockPos).getBlock().getMaterial().isSolid()
         && !world.getBlockState(blockPos.up()).getBlock().getMaterial().isSolid();
   }

   @Override
   public void dropBlockAsItemWithChance(World world, BlockPos blockPos, IBlockState iBlockState, float float4, int integer) {
      if (iBlockState.getValue(PART) == BlockBed.EnumPartType.FOOT) {
         super.dropBlockAsItemWithChance(world, blockPos, iBlockState, float4, 0);
      }
   }

   @Override
   public int getMobilityFlag() {
      return 1;
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return Items.bed;
   }

   @Override
   public void onBlockHarvested(World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer) {
      if (entityPlayer.capabilities.isCreativeMode && iBlockState.getValue(PART) == BlockBed.EnumPartType.HEAD) {
         BlockPos var8 = blockPos.offset(iBlockState.getValue(FACING).getOpposite());
         if (world.getBlockState(var8).getBlock() == this) {
            world.setBlockToAir(var8);
         }
      }
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      EnumFacing var5 = EnumFacing.getHorizontal(integer);
      return (integer & 8) > 0
         ? this.getDefaultState().withProperty(PART, BlockBed.EnumPartType.HEAD).withProperty(FACING, var5).withProperty(OCCUPIED, (integer & 4) > 0)
         : this.getDefaultState().withProperty(PART, BlockBed.EnumPartType.FOOT).withProperty(FACING, var5);
   }

   @Override
   public IBlockState getActualState(IBlockState iBlockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
      if (iBlockState.getValue(PART) == BlockBed.EnumPartType.FOOT) {
         IBlockState var7 = iBlockAccess.getBlockState(blockPos.offset(iBlockState.getValue(FACING)));
         if (var7.getBlock() == this) {
            iBlockState = iBlockState.withProperty(OCCUPIED, var7.getValue(OCCUPIED));
         }
      }

      return iBlockState;
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      int var5 = 0 | iBlockState.getValue(FACING).getHorizontalIndex();
      if (iBlockState.getValue(PART) == BlockBed.EnumPartType.HEAD) {
         var5 |= 8;
         if (iBlockState.getValue(OCCUPIED)) {
            var5 |= 4;
         }
      }

      return var5;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, FACING, PART, OCCUPIED);
   }

   public enum EnumPartType implements IStringSerializable {
      HEAD("head"),
      FOOT("foot");

      private final String name;

      EnumPartType(String string3) {
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

      private static BlockBed.EnumPartType[] Method355() {
         return new BlockBed.EnumPartType[]{HEAD, FOOT};
      }
   }
}
