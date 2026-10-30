package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockTNT extends Block {
   public static final PropertyBool EXPLODE = PropertyBool.create("explode");

   public BlockTNT() {
      super(Material.tnt);
      this.setDefaultState(this.blockState.getBaseState().withProperty(EXPLODE, Boolean.FALSE));
      this.setCreativeTab(CreativeTabs.tabRedstone);
   }

   @Override
   public void onBlockAdded(World world, BlockPos blockPos, IBlockState iBlockState) {
      super.onBlockAdded(world, blockPos, iBlockState);
      if (world.isBlockPowered(blockPos)) {
         this.onBlockDestroyedByPlayer(world, blockPos, iBlockState.withProperty(EXPLODE, Boolean.TRUE));
         world.setBlockToAir(blockPos);
      }
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      if (world.isBlockPowered(blockPos)) {
         this.onBlockDestroyedByPlayer(world, blockPos, iBlockState.withProperty(EXPLODE, Boolean.TRUE));
         world.setBlockToAir(blockPos);
      }
   }

   @Override
   public void onBlockDestroyedByExplosion(World world, BlockPos blockPos, Explosion explosion) {
      if (!world.isRemote) {
         EntityTNTPrimed var6 = new EntityTNTPrimed(world, blockPos.getX() + 0.5F, blockPos.getY(), blockPos.getZ() + 0.5F, explosion.getExplosivePlacedBy());
         var6.fuse = world.rand.nextInt(var6.fuse / 4) + var6.fuse / 8;
         world.spawnEntityInWorld(var6);
      }
   }

   @Override
   public void onBlockDestroyedByPlayer(World world, BlockPos blockPos, IBlockState iBlockState) {
      this.explode(world, blockPos, iBlockState, null);
   }

   public void explode(World world, BlockPos blockPos, IBlockState iBlockState, EntityLivingBase entityLivingBase) {
      if (!world.isRemote && iBlockState.getValue(EXPLODE)) {
         EntityTNTPrimed var8 = new EntityTNTPrimed(world, blockPos.getX() + 0.5F, blockPos.getY(), blockPos.getZ() + 0.5F, entityLivingBase);
         world.spawnEntityInWorld(var8);
         world.playSoundAtEntity(var8, "game.tnt.primed", 1.0F, 1.0F);
      }
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      if (entityPlayer.getCurrentEquippedItem() != null) {
         Item var12 = entityPlayer.getCurrentEquippedItem().getItem();
         if (var12 == Items.flint_and_steel || var12 == Items.fire_charge) {
            this.explode(world, blockPos, iBlockState.withProperty(EXPLODE, Boolean.TRUE), entityPlayer);
            world.setBlockToAir(blockPos);
            if (var12 == Items.flint_and_steel) {
               entityPlayer.getCurrentEquippedItem().damageItem(1, entityPlayer);
            } else if (!entityPlayer.capabilities.isCreativeMode) {
               entityPlayer.getCurrentEquippedItem().stackSize--;
            }

            return true;
         }
      }

      return super.onBlockActivated(world, blockPos, iBlockState, entityPlayer, enumFacing, float6, float7, float8);
   }

   @Override
   public void onEntityCollidedWithBlock(World world, BlockPos blockPos, IBlockState iBlockState, Entity entity) {
      if (!world.isRemote && entity instanceof EntityArrow var8 && var8.isBurning()) {
         this.explode(
            world,
            blockPos,
            world.getBlockState(blockPos).withProperty(EXPLODE, Boolean.TRUE),
            var8.shootingEntity instanceof EntityLivingBase ? (EntityLivingBase)var8.shootingEntity : null
         );
         world.setBlockToAir(blockPos);
      }
   }

   @Override
   public boolean canDropFromExplosion(Explosion explosion) {
      return false;
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(EXPLODE, (integer & 1) > 0);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(EXPLODE) ? 1 : 0;
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, EXPLODE);
   }
}
