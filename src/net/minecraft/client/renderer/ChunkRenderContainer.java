package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import java.util.BitSet;
import java.util.List;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.optifine.SmartAnimations;

public abstract class ChunkRenderContainer {
   private double viewEntityX;
   private double viewEntityY;
   private double viewEntityZ;
   protected List<RenderChunk> renderChunks = Lists.newArrayListWithCapacity(17424);
   protected boolean initialized;
   private BitSet animatedSpritesRendered;
   private final BitSet animatedSpritesCached = new BitSet();

   public void initialize(double double1, double double2, double double3) {
      this.initialized = true;
      this.renderChunks.clear();
      this.viewEntityX = double1;
      this.viewEntityY = double2;
      this.viewEntityZ = double3;
      if (SmartAnimations.isActive()) {
         if (this.animatedSpritesRendered != null) {
            SmartAnimations.spritesRendered(this.animatedSpritesRendered);
         } else {
            this.animatedSpritesRendered = this.animatedSpritesCached;
         }

         this.animatedSpritesRendered.clear();
      } else if (this.animatedSpritesRendered != null) {
         SmartAnimations.spritesRendered(this.animatedSpritesRendered);
         this.animatedSpritesRendered = null;
      }
   }

   public void preRenderChunk(RenderChunk renderChunk) {
      BlockPos var2 = renderChunk.getPosition();
      GlStateManager.translate((float)(var2.getX() - this.viewEntityX), (float)(var2.getY() - this.viewEntityY), (float)(var2.getZ() - this.viewEntityZ));
   }

   public void addRenderChunk(RenderChunk renderChunk, EnumWorldBlockLayer enumWorldBlockLayer) {
      this.renderChunks.add(renderChunk);
      if (this.animatedSpritesRendered != null) {
         BitSet var3 = renderChunk.compiledChunk.getAnimatedSprites(enumWorldBlockLayer);
         if (var3 != null) {
            this.animatedSpritesRendered.or(var3);
         }
      }
   }

   public abstract void renderChunkLayer(EnumWorldBlockLayer enumWorldBlockLayer);
}
