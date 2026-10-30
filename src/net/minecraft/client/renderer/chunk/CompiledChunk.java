package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import java.util.BitSet;
import java.util.List;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;

public class CompiledChunk {
   public static final CompiledChunk DUMMY = new CompiledChunk() {
      @Override
      protected void setLayerUsed(EnumWorldBlockLayer enumWorldBlockLayer) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void setLayerStarted(EnumWorldBlockLayer enumWorldBlockLayer) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean isVisible(EnumFacing enumFacing1, EnumFacing enumFacing2) {
         return false;
      }

      @Override
      public void setAnimatedSprites(EnumWorldBlockLayer enumWorldBlockLayer, BitSet bitSet) {
         throw new UnsupportedOperationException();
      }
   };
   private final boolean[] layersUsed = new boolean[RenderChunk.ENUM_WORLD_BLOCK_LAYERS.length];
   private final boolean[] layersStarted = new boolean[RenderChunk.ENUM_WORLD_BLOCK_LAYERS.length];
   private boolean empty = true;
   private final List<TileEntity> tileEntities = Lists.newArrayList();
   private SetVisibility setVisibility = new SetVisibility();
   private WorldRenderer.State state;
   private final BitSet[] animatedSprites = new BitSet[RenderChunk.ENUM_WORLD_BLOCK_LAYERS.length];

   public boolean isEmpty() {
      return this.empty;
   }

   protected void setLayerUsed(EnumWorldBlockLayer enumWorldBlockLayer) {
      this.empty = false;
      this.layersUsed[enumWorldBlockLayer.ordinal()] = true;
   }

   public boolean isLayerEmpty(EnumWorldBlockLayer enumWorldBlockLayer) {
      return !this.layersUsed[enumWorldBlockLayer.ordinal()];
   }

   public void setLayerStarted(EnumWorldBlockLayer enumWorldBlockLayer) {
      this.layersStarted[enumWorldBlockLayer.ordinal()] = true;
   }

   public boolean isLayerStarted(EnumWorldBlockLayer enumWorldBlockLayer) {
      return this.layersStarted[enumWorldBlockLayer.ordinal()];
   }

   public List<TileEntity> getTileEntities() {
      return this.tileEntities;
   }

   public void addTileEntity(TileEntity tileEntity) {
      this.tileEntities.add(tileEntity);
   }

   public boolean isVisible(EnumFacing enumFacing1, EnumFacing enumFacing2) {
      return this.setVisibility.isVisible(enumFacing1, enumFacing2);
   }

   public void setVisibility(SetVisibility setVisibility) {
      this.setVisibility = setVisibility;
   }

   public WorldRenderer.State getState() {
      return this.state;
   }

   public void setState(WorldRenderer.State state) {
      this.state = state;
   }

   public BitSet getAnimatedSprites(EnumWorldBlockLayer enumWorldBlockLayer) {
      return this.animatedSprites[enumWorldBlockLayer.ordinal()];
   }

   public void setAnimatedSprites(EnumWorldBlockLayer enumWorldBlockLayer, BitSet bitSet) {
      this.animatedSprites[enumWorldBlockLayer.ordinal()] = bitSet;
   }
}
