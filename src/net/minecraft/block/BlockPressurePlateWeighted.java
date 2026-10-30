package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockPressurePlateWeighted extends BlockBasePressurePlate {
   public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);
   private final int field_150068_a;

   protected BlockPressurePlateWeighted(Material material, int integer) {
      this(material, integer, material.getMaterialMapColor());
   }

   protected BlockPressurePlateWeighted(Material material, int integer, MapColor mapColor) {
      super(material, mapColor);
      this.setDefaultState(this.blockState.getBaseState().withProperty(POWER, 0));
      this.field_150068_a = integer;
   }

   @Override
   protected int computeRedstoneStrength(World world, BlockPos blockPos) {
      int var6 = Math.min(world.getEntitiesWithinAABB(Entity.class, this.getSensitiveAABB(blockPos)).size(), this.field_150068_a);
      return var6 > 0 ? MathHelper.ceiling_float_int((float)Math.min(this.field_150068_a, var6) / this.field_150068_a * 15.0F) : 0;
   }

   @Override
   protected int getRedstoneStrength(IBlockState iBlockState) {
      return iBlockState.getValue(POWER);
   }

   @Override
   protected IBlockState setRedstoneStrength(IBlockState iBlockState, int integer) {
      return iBlockState.withProperty(POWER, integer);
   }

   @Override
   public int tickRate(World world) {
      return 10;
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(POWER, integer);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(POWER);
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, POWER);
   }
}
