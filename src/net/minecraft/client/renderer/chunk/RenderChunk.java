package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Sets;
import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.BitSet;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RegionRenderCache;
import net.minecraft.client.renderer.RegionRenderCacheBuilder;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.ViewFrustum;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.src.Config;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.optifine.BlockPosM;
import net.optifine.CustomBlockLayers;
import net.optifine.override.ChunkCacheOF;
import net.optifine.render.AabbFrame;
import net.optifine.render.RenderEnv;
import net.optifine.shaders.SVertexBuilder;

public class RenderChunk {
   private final World world;
   private final RenderGlobal renderGlobal;
   public static int renderChunksUpdated;
   private BlockPos position;
   public CompiledChunk compiledChunk = CompiledChunk.DUMMY;
   private final ReentrantLock lockCompileTask = new ReentrantLock();
   private final ReentrantLock lockCompiledChunk = new ReentrantLock();
   private ChunkCompileTaskGenerator compileTask = null;
   private final Set<TileEntity> setTileEntities = Sets.newHashSet();
   private final int index;
   private final FloatBuffer modelviewMatrix = GLAllocation.createDirectFloatBuffer(16);
   private final VertexBuffer[] vertexBuffers = new VertexBuffer[EnumWorldBlockLayer.values().length];
   public AxisAlignedBB boundingBox;
   private int frameIndex = -1;
   private boolean needsUpdate = true;
   private final EnumMap<EnumFacing, BlockPos> mapEnumFacing = null;
   private final BlockPos[] positionOffsets16 = new BlockPos[EnumFacing.VALUES.length];
   public static final EnumWorldBlockLayer[] ENUM_WORLD_BLOCK_LAYERS = EnumWorldBlockLayer.values();
   private final EnumWorldBlockLayer[] blockLayersSingle = new EnumWorldBlockLayer[1];
   private final boolean fixBlockLayer = Config.isMipmaps();
   private boolean playerUpdate = false;
   public int regionX;
   public int regionZ;
   private final RenderChunk[] renderChunksOfset16 = new RenderChunk[6];
   private boolean renderChunksOffset16Updated = false;
   private Chunk chunk;
   private final RenderChunk[] renderChunkNeighbours = new RenderChunk[EnumFacing.VALUES.length];
   private final RenderChunk[] renderChunkNeighboursValid = new RenderChunk[EnumFacing.VALUES.length];
   private boolean renderChunkNeighboursUpated = false;
   private final RenderGlobal.ContainerLocalRenderInformation renderInfo = new RenderGlobal.ContainerLocalRenderInformation(this, null, 0);
   public AabbFrame boundingBoxParent;

   public RenderChunk(World world, RenderGlobal renderGlobal, BlockPos blockPos, int integer) {
      this.world = world;
      this.renderGlobal = renderGlobal;
      this.index = integer;
      if (!blockPos.equals(this.getPosition())) {
         this.setPosition(blockPos);
      }

      if (OpenGlHelper.useVbo()) {
         for (int var7 = 0; var7 < EnumWorldBlockLayer.values().length; var7++) {
            this.vertexBuffers[var7] = new VertexBuffer(DefaultVertexFormats.BLOCK);
         }
      }
   }

   public boolean setFrameIndex(int integer) {
      if (this.frameIndex == integer) {
         return false;
      }

      this.frameIndex = integer;
      return true;
   }

   public VertexBuffer getVertexBufferByLayer(int integer) {
      return this.vertexBuffers[integer];
   }

   public void setPosition(BlockPos blockPos) {
      this.stopCompileTask();
      this.position = blockPos;
      byte var4 = 8;
      this.regionX = blockPos.getX() >> var4 << var4;
      this.regionZ = blockPos.getZ() >> var4 << var4;
      this.boundingBox = new AxisAlignedBB(blockPos, blockPos.add(16, 16, 16));
      this.initModelviewMatrix();
      Arrays.fill(this.positionOffsets16, null);
      this.renderChunksOffset16Updated = false;
      this.renderChunkNeighboursUpated = false;

      for (RenderChunk var8 : this.renderChunkNeighbours) {
         if (var8 != null) {
            var8.renderChunkNeighboursUpated = false;
         }
      }

      this.chunk = null;
      this.boundingBoxParent = null;
   }

   public void resortTransparency(float float1, float float2, float float3, ChunkCompileTaskGenerator chunkCompileTaskGenerator) {
      CompiledChunk var5 = chunkCompileTaskGenerator.getCompiledChunk();
      if (var5.getState() != null && !var5.isLayerEmpty(EnumWorldBlockLayer.TRANSLUCENT)) {
         WorldRenderer var6 = chunkCompileTaskGenerator.getRegionRenderCacheBuilder().getWorldRendererByLayer(EnumWorldBlockLayer.TRANSLUCENT);
         this.preRenderBlocks(var6, this.position);
         var6.setVertexState(var5.getState());
         this.postRenderBlocks(EnumWorldBlockLayer.TRANSLUCENT, float1, float2, float3, var6, var5);
      }
   }

   public void rebuildChunk(float float1, float float2, float float3, ChunkCompileTaskGenerator chunkCompileTaskGenerator) {
      CompiledChunk var7 = new CompiledChunk();
      BlockPos var9 = new BlockPos(this.position);
      BlockPos var10 = var9.add(15, 15, 15);
      chunkCompileTaskGenerator.getLock().lock();

      try {
         if (chunkCompileTaskGenerator.getStatus() != ChunkCompileTaskGenerator.Status.COMPILING) {
            return;
         }

         chunkCompileTaskGenerator.setCompiledChunk(var7);
      } finally {
         chunkCompileTaskGenerator.getLock().unlock();
      }

      VisGraph var11 = new VisGraph();
      HashSet var12 = Sets.newHashSet();
      if (!this.isChunkRegionEmpty(var9)) {
         renderChunksUpdated++;
         ChunkCacheOF var13 = this.makeChunkCacheOF(var9);
         var13.renderStart();
         boolean[] var14 = new boolean[ENUM_WORLD_BLOCK_LAYERS.length];
         BlockRendererDispatcher var15 = Minecraft.getMinecraft().getBlockRendererDispatcher();

         for (BlockPosM var17 : (Iterable<BlockPosM>) BlockPosM.getAllInBoxMutable(var9, var10)) {
            IBlockState var18 = var13.getBlockState(var17);
            Block var19 = var18.getBlock();
            if (var19.isOpaqueCube()) {
               var11.func_178606_a(var17);
            }

            if (var18.getBlock().hasTileEntity()) {
               TileEntity var20 = var13.getTileEntity(new BlockPos(var17));
               TileEntitySpecialRenderer var21 = TileEntityRendererDispatcher.instance.getSpecialRenderer(var20);
               if (var20 != null && var21 != null) {
                  var7.addTileEntity(var20);
                  if (var21.forceTileEntityRender()) {
                     var12.add(var20);
                  }
               }
            }

            EnumWorldBlockLayer[] var42 = this.blockLayersSingle;
            var42[0] = var19.getBlockLayer();

            for (EnumWorldBlockLayer var25 : var42) {
               var25 = this.fixBlockLayer(var18, var25);
               int var26 = var25.ordinal();
               if (var19.getRenderType() != -1) {
                  WorldRenderer var27 = chunkCompileTaskGenerator.getRegionRenderCacheBuilder().getWorldRendererByLayerId(var26);
                  var27.setBlockLayer(var25);
                  RenderEnv var28 = var27.getRenderEnv(var18, var17);
                  var28.setRegionRenderCacheBuilder(chunkCompileTaskGenerator.getRegionRenderCacheBuilder());
                  if (!var7.isLayerStarted(var25)) {
                     var7.setLayerStarted(var25);
                     this.preRenderBlocks(var27, var9);
                  }

                  var14[var26] |= var15.renderBlock(var18, var17, var13, var27);
                  if (var28.isOverlaysRendered()) {
                     this.postRenderOverlays(chunkCompileTaskGenerator.getRegionRenderCacheBuilder(), var7, var14);
                     var28.setOverlaysRendered(false);
                  }
               }
            }
         }

         for (EnumWorldBlockLayer var41 : ENUM_WORLD_BLOCK_LAYERS) {
            if (var14[var41.ordinal()]) {
               var7.setLayerUsed(var41);
            }

            if (var7.isLayerStarted(var41)) {
               if (Config.isShaders()) {
                  SVertexBuilder.calcNormalChunkLayer(chunkCompileTaskGenerator.getRegionRenderCacheBuilder().getWorldRendererByLayer(var41));
               }

               WorldRenderer var43 = chunkCompileTaskGenerator.getRegionRenderCacheBuilder().getWorldRendererByLayer(var41);
               this.postRenderBlocks(var41, float1, float2, float3, var43, var7);
               if (var43.animatedSprites != null) {
                  var7.setAnimatedSprites(var41, (BitSet)var43.animatedSprites.clone());
               }
            } else {
               var7.setAnimatedSprites(var41, null);
            }
         }

         var13.renderFinish();
      }

      var7.setVisibility(var11.computeVisibility());
      this.lockCompileTask.lock();

      try {
         HashSet var36 = Sets.newHashSet(var12);
         HashSet var37 = Sets.newHashSet(this.setTileEntities);
         var36.removeAll(this.setTileEntities);
         var37.removeAll(var12);
         this.setTileEntities.clear();
         this.setTileEntities.addAll(var12);
         this.renderGlobal.updateTileEntities(var37, var36);
      } finally {
         this.lockCompileTask.unlock();
      }
   }

   protected void finishCompileTask() {
      this.lockCompileTask.lock();

      try {
         if (this.compileTask != null && this.compileTask.getStatus() != ChunkCompileTaskGenerator.Status.DONE) {
            this.compileTask.finish();
            this.compileTask = null;
         }
      } finally {
         this.lockCompileTask.unlock();
      }
   }

   public ReentrantLock getLockCompileTask() {
      return this.lockCompileTask;
   }

   public ChunkCompileTaskGenerator makeCompileTaskChunk() {
      this.lockCompileTask.lock();

      ChunkCompileTaskGenerator var1;
      try {
         this.finishCompileTask();
         this.compileTask = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator.Type.REBUILD_CHUNK);
         var1 = this.compileTask;
      } finally {
         this.lockCompileTask.unlock();
      }

      return var1;
   }

   public ChunkCompileTaskGenerator makeCompileTaskTransparency() {
      this.lockCompileTask.lock();

      try {
         if (this.compileTask == null || this.compileTask.getStatus() != ChunkCompileTaskGenerator.Status.PENDING) {
            if (this.compileTask != null && this.compileTask.getStatus() != ChunkCompileTaskGenerator.Status.DONE) {
               this.compileTask.finish();
               this.compileTask = null;
            }

            this.compileTask = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY);
            this.compileTask.setCompiledChunk(this.compiledChunk);
            return this.compileTask;
         }
      } finally {
         this.lockCompileTask.unlock();
      }

      return null;
   }

   private void preRenderBlocks(WorldRenderer worldRenderer, BlockPos blockPos) {
      worldRenderer.begin(7, DefaultVertexFormats.BLOCK);
      if (Config.isRenderRegions()) {
         byte var5 = 8;
         int var10000 = blockPos.getX() >> var5 << var5;
         int var7 = blockPos.getY() >> var5 << var5;
         var10000 = blockPos.getZ() >> var5 << var5;
         int var6 = this.regionX;
         int var8 = this.regionZ;
         worldRenderer.setTranslation(-var6, -var7, -var8);
      } else {
         worldRenderer.setTranslation(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
      }
   }

   private void postRenderBlocks(
      EnumWorldBlockLayer enumWorldBlockLayer, float float2, float float3, float float4, WorldRenderer worldRenderer, CompiledChunk compiledChunk
   ) {
      if (enumWorldBlockLayer == EnumWorldBlockLayer.TRANSLUCENT && !compiledChunk.isLayerEmpty(enumWorldBlockLayer)) {
         worldRenderer.sortVertexData(float2, float3, float4);
         compiledChunk.setState(worldRenderer.getVertexState());
      }

      worldRenderer.finishDrawing();
   }

   private void initModelviewMatrix() {
      GlStateManager.pushMatrix();
      GlStateManager.loadIdentity();
      GlStateManager.translate(-8.0F, -8.0F, -8.0F);
      GlStateManager.scale(1.000001F, 1.000001F, 1.000001F);
      GlStateManager.translate(8.0F, 8.0F, 8.0F);
      GlStateManager.getFloat(2982, this.modelviewMatrix);
      GlStateManager.popMatrix();
   }

   public void multModelviewMatrix() {
      GlStateManager.multMatrix(this.modelviewMatrix);
   }

   public CompiledChunk getCompiledChunk() {
      return this.compiledChunk;
   }

   public void setCompiledChunk(CompiledChunk compiledChunk) {
      this.lockCompiledChunk.lock();

      try {
         this.compiledChunk = compiledChunk;
      } finally {
         this.lockCompiledChunk.unlock();
      }
   }

   public void stopCompileTask() {
      this.finishCompileTask();
      this.compiledChunk = CompiledChunk.DUMMY;
   }

   public void deleteGlResources() {
      this.stopCompileTask();

      for (int var1 = 0; var1 < EnumWorldBlockLayer.values().length; var1++) {
         if (this.vertexBuffers[var1] != null) {
            this.vertexBuffers[var1].deleteGlBuffers();
         }
      }
   }

   public BlockPos getPosition() {
      return this.position;
   }

   public void setNeedsUpdate(boolean boolean1) {
      this.needsUpdate = boolean1;
      if (boolean1) {
         if (this.isWorldPlayerUpdate()) {
            this.playerUpdate = true;
         }
      } else {
         this.playerUpdate = false;
      }
   }

   public boolean isNeedsUpdate() {
      return this.needsUpdate;
   }

   public BlockPos getBlockPosOffset16(EnumFacing enumFacing) {
      return this.getPositionOffset16(enumFacing);
   }

   public BlockPos getPositionOffset16(EnumFacing enumFacing) {
      int var4 = enumFacing.getIndex();
      BlockPos var5 = this.positionOffsets16[var4];
      if (var5 == null) {
         var5 = this.getPosition().offset(enumFacing, 16);
         this.positionOffsets16[var4] = var5;
      }

      return var5;
   }

   private boolean isWorldPlayerUpdate() {
      World var2 = this.world;
      return var2 instanceof WorldClient ? ((WorldClient)var2).isPlayerUpdate() : false;
   }

   public boolean isPlayerUpdate() {
      return this.playerUpdate;
   }

   protected RegionRenderCache createRegionRenderCache(World world, BlockPos blockPos2, BlockPos blockPos3, int integer) {
      return new RegionRenderCache(world, blockPos2, blockPos3, integer);
   }

   private EnumWorldBlockLayer fixBlockLayer(IBlockState iBlockState, EnumWorldBlockLayer enumWorldBlockLayer) {
      if (CustomBlockLayers.isActive()) {
         EnumWorldBlockLayer var3 = CustomBlockLayers.getRenderLayer(iBlockState);
         if (var3 != null) {
            return var3;
         }
      }

      if (this.fixBlockLayer) {
         if (enumWorldBlockLayer == EnumWorldBlockLayer.CUTOUT) {
            Block var4 = iBlockState.getBlock();
            if (var4 instanceof BlockRedstoneWire) {
               return enumWorldBlockLayer;
            }

            if (var4 instanceof BlockCactus) {
               return enumWorldBlockLayer;
            }

            return EnumWorldBlockLayer.CUTOUT_MIPPED;
         }
      } else if (enumWorldBlockLayer == EnumWorldBlockLayer.CUTOUT_MIPPED) {
         return EnumWorldBlockLayer.CUTOUT;
      }

      return enumWorldBlockLayer;
   }

   private void postRenderOverlays(RegionRenderCacheBuilder regionRenderCacheBuilder, CompiledChunk compiledChunk, boolean[] arr) {
      this.postRenderOverlay(EnumWorldBlockLayer.CUTOUT, regionRenderCacheBuilder, compiledChunk, arr);
      this.postRenderOverlay(EnumWorldBlockLayer.CUTOUT_MIPPED, regionRenderCacheBuilder, compiledChunk, arr);
      this.postRenderOverlay(EnumWorldBlockLayer.TRANSLUCENT, regionRenderCacheBuilder, compiledChunk, arr);
   }

   private void postRenderOverlay(
      EnumWorldBlockLayer enumWorldBlockLayer, RegionRenderCacheBuilder regionRenderCacheBuilder, CompiledChunk compiledChunk, boolean[] arr
   ) {
      WorldRenderer var5 = regionRenderCacheBuilder.getWorldRendererByLayer(enumWorldBlockLayer);
      if (var5.isDrawing()) {
         compiledChunk.setLayerStarted(enumWorldBlockLayer);
         arr[enumWorldBlockLayer.ordinal()] = true;
      }
   }

   private ChunkCacheOF makeChunkCacheOF(BlockPos blockPos) {
      BlockPos var4 = blockPos.add(-1, -1, -1);
      BlockPos var5 = blockPos.add(16, 16, 16);
      RegionRenderCache var6 = this.createRegionRenderCache(this.world, var4, var5, 1);
      return new ChunkCacheOF(var6, var4, var5, 1);
   }

   public RenderChunk getRenderChunkOffset16(ViewFrustum viewFrustum, EnumFacing enumFacing) {
      if (!this.renderChunksOffset16Updated) {
         for (int var3 = 0; var3 < EnumFacing.VALUES.length; var3++) {
            EnumFacing var4 = EnumFacing.VALUES[var3];
            BlockPos var5 = this.getBlockPosOffset16(var4);
            this.renderChunksOfset16[var3] = viewFrustum.getRenderChunk(var5);
         }

         this.renderChunksOffset16Updated = true;
      }

      return this.renderChunksOfset16[enumFacing.ordinal()];
   }

   public Chunk getChunk() {
      return this.getChunk(this.position);
   }

   private Chunk getChunk(BlockPos blockPos) {
      Chunk var2 = this.chunk;
      if (var2 == null || !var2.isLoaded()) {
         var2 = this.world.getChunkFromBlockCoords(blockPos);
         this.chunk = var2;
      }

      return var2;
   }

   public boolean isChunkRegionEmpty() {
      return this.isChunkRegionEmpty(this.position);
   }

   private boolean isChunkRegionEmpty(BlockPos blockPos) {
      int var4 = blockPos.getY();
      int var5 = var4 + 15;
      return this.getChunk(blockPos).getAreLevelsEmpty(var4, var5);
   }

   public void setRenderChunkNeighbour(EnumFacing enumFacing, RenderChunk renderChunk) {
      this.renderChunkNeighbours[enumFacing.ordinal()] = renderChunk;
      this.renderChunkNeighboursValid[enumFacing.ordinal()] = renderChunk;
   }

   public RenderChunk getRenderChunkNeighbour(EnumFacing enumFacing) {
      if (!this.renderChunkNeighboursUpated) {
         this.updateRenderChunkNeighboursValid();
      }

      return this.renderChunkNeighboursValid[enumFacing.ordinal()];
   }

   public RenderGlobal.ContainerLocalRenderInformation getRenderInfo() {
      return this.renderInfo;
   }

   private void updateRenderChunkNeighboursValid() {
      int var3 = this.getPosition().getX();
      int var4 = this.getPosition().getZ();
      int var5 = EnumFacing.NORTH.ordinal();
      int var6 = EnumFacing.SOUTH.ordinal();
      int var7 = EnumFacing.WEST.ordinal();
      int var8 = EnumFacing.EAST.ordinal();
      this.renderChunkNeighboursValid[var5] = this.renderChunkNeighbours[var5].getPosition().getZ() == var4 - 16 ? this.renderChunkNeighbours[var5] : null;
      this.renderChunkNeighboursValid[var6] = this.renderChunkNeighbours[var6].getPosition().getZ() == var4 + 16 ? this.renderChunkNeighbours[var6] : null;
      this.renderChunkNeighboursValid[var7] = this.renderChunkNeighbours[var7].getPosition().getX() == var3 - 16 ? this.renderChunkNeighbours[var7] : null;
      this.renderChunkNeighboursValid[var8] = this.renderChunkNeighbours[var8].getPosition().getX() == var3 + 16 ? this.renderChunkNeighbours[var8] : null;
      this.renderChunkNeighboursUpated = true;
   }

   public boolean isBoundingBoxInFrustum(ICamera iCamera, int integer) {
      return this.getBoundingBoxParent().isBoundingBoxInFrustumFully(iCamera, integer) || iCamera.isBoundingBoxInFrustum(this.boundingBox);
   }

   public AabbFrame getBoundingBoxParent() {
      if (this.boundingBoxParent == null) {
         BlockPos var1 = this.getPosition();
         int var2 = var1.getX();
         int var3 = var1.getY();
         int var4 = var1.getZ();
         int var6 = var2 >> 5 << 5;
         int var7 = var3 >> 5 << 5;
         int var8 = var4 >> 5 << 5;
         if (var6 != var2 || var7 != var3 || var8 != var4) {
            AabbFrame var9 = this.renderGlobal.getRenderChunk(new BlockPos(var6, var7, var8)).getBoundingBoxParent();
            if (var9 != null && var9.minX == var6 && var9.minY == var7 && var9.minZ == var8) {
               this.boundingBoxParent = var9;
            }
         }

         if (this.boundingBoxParent == null) {
            this.boundingBoxParent = new AabbFrame(var6, var7, var8, var6 + 32, var7 + 32, var8 + 32);
         }
      }

      return this.boundingBoxParent;
   }

   @Override
   public String toString() {
      return "pos: " + this.getPosition() + ", frameIndex: " + this.frameIndex;
   }
}
