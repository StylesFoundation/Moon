package net.minecraft.world.chunk;

import com.google.common.base.Predicate;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class EmptyChunk extends Chunk {
   public EmptyChunk(World world, int integer2, int integer3) {
      super(world, integer2, integer3);
   }

   @Override
   public boolean isAtLocation(int integer1, int integer2) {
      return integer1 == this.xPosition && integer2 == this.zPosition;
   }

   @Override
   public int getHeightValue(int integer1, int integer2) {
      return 0;
   }

   @Override
   public void generateHeightMap() {
   }

   @Override
   public void generateSkylightMap() {
   }

   @Override
   public Block getBlock(BlockPos blockPos) {
      return Blocks.air;
   }

   @Override
   public int getBlockLightOpacity(BlockPos blockPos) {
      return 255;
   }

   @Override
   public int getBlockMetadata(BlockPos blockPos) {
      return 0;
   }

   @Override
   public int getLightFor(EnumSkyBlock enumSkyBlock, BlockPos blockPos) {
      return enumSkyBlock.defaultLightValue;
   }

   @Override
   public void setLightFor(EnumSkyBlock enumSkyBlock, BlockPos blockPos, int integer) {
   }

   @Override
   public int getLightSubtracted(BlockPos blockPos, int integer) {
      return 0;
   }

   @Override
   public void addEntity(Entity entity) {
   }

   @Override
   public void removeEntity(Entity entity) {
   }

   @Override
   public void removeEntityAtIndex(Entity entity, int integer) {
   }

   @Override
   public boolean canSeeSky(BlockPos blockPos) {
      return false;
   }

   @Override
   public TileEntity getTileEntity(BlockPos blockPos, Chunk.EnumCreateEntityType enumCreateEntityType) {
      return null;
   }

   @Override
   public void addTileEntity(TileEntity tileEntity) {
   }

   @Override
   public void addTileEntity(BlockPos blockPos, TileEntity tileEntity) {
   }

   @Override
   public void removeTileEntity(BlockPos blockPos) {
   }

   @Override
   public void onChunkLoad() {
   }

   @Override
   public void onChunkUnload() {
   }

   @Override
   public void setChunkModified() {
   }

   @Override
   public void getEntitiesWithinAABBForEntity(Entity entity, AxisAlignedBB axisAlignedBB, List<Entity> list, Predicate<? super Entity> predicate) {
   }

   @Override
   public <T extends Entity> void getEntitiesOfTypeWithinAAAB(
      Class<? extends T> class1, AxisAlignedBB axisAlignedBB, List<T> list, Predicate<? super T> predicate
   ) {
   }

   @Override
   public boolean needsSaving(boolean boolean1) {
      return false;
   }

   @Override
   public Random getRandomWithSeed(long long1) {
      return new Random(
         this.getWorld().getSeed()
               + (long)this.xPosition * this.xPosition * 4987142L
               + this.xPosition * 5947611L
               + (long)this.zPosition * this.zPosition * 4392871L
               + this.zPosition * 389711L
            ^ long1
      );
   }

   @Override
   public boolean isEmpty() {
      return true;
   }

   @Override
   public boolean getAreLevelsEmpty(int integer1, int integer2) {
      return true;
   }
}
