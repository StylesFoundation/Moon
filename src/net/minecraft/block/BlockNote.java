package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class BlockNote extends BlockContainer {
   private static final List<String> INSTRUMENTS = Lists.newArrayList(new String[]{"harp", "bd", "snare", "hat", "bassattack"});

   public BlockNote() {
      super(Material.wood);
      this.setCreativeTab(CreativeTabs.tabRedstone);
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      boolean var8 = world.isBlockPowered(blockPos);
      if (world.getTileEntity(blockPos) instanceof TileEntityNote var10 && var10.previousRedstoneState != var8) {
         if (var8) {
            var10.triggerNote(world, blockPos);
         }

         var10.previousRedstoneState = var8;
      }
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (!world.isRemote && world.getTileEntity(blockPos) instanceof TileEntityNote var13) {
         var13.changePitch();
         var13.triggerNote(world, blockPos);
         entityPlayer.triggerAchievement(StatList.field_181735_S);
      }

      return true;
   }

   @Override
   public void onBlockClicked(World world, BlockPos blockPos, EntityPlayer entityPlayer) {
      if (!world.isRemote) {
         TileEntity var7 = world.getTileEntity(blockPos);
         if (var7 instanceof TileEntityNote) {
            ((TileEntityNote)var7).triggerNote(world, blockPos);
            entityPlayer.triggerAchievement(StatList.field_181734_R);
         }
      }
   }

   @Override
   public TileEntity createNewTileEntity(World world, int integer) {
      return new TileEntityNote();
   }

   private String getInstrument(int integer) {
      if (integer < 0 || integer >= INSTRUMENTS.size()) {
         integer = 0;
      }

      return INSTRUMENTS.get(integer);
   }

   @Override
   public boolean onBlockEventReceived(World world, BlockPos blockPos, IBlockState iBlockState, int integer4, int integer5) {
      float var8 = (float)Math.pow(2.0, (integer5 - 12) / 12.0);
      world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "note." + this.getInstrument(integer4), 3.0F, var8);
      world.spawnParticle(EnumParticleTypes.NOTE, blockPos.getX() + 0.5, blockPos.getY() + 1.2, blockPos.getZ() + 0.5, integer5 / 24.0, 0.0, 0.0);
      return true;
   }

   @Override
   public int getRenderType() {
      return 3;
   }
}
