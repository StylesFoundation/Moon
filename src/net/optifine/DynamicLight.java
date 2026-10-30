package net.optifine;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class DynamicLight {
   private Entity entity = null;
   private double offsetY = 0.0;
   private double lastPosX = -2.1474836E9F;
   private double lastPosY = -2.1474836E9F;
   private double lastPosZ = -2.1474836E9F;
   private int lastLightLevel = 0;
   private boolean underwater = false;
   private long timeCheckMs = 0L;
   private Set<BlockPos> setLitChunkPos = new HashSet<>();
   private final BlockPos.MutableBlockPos blockPosMutable = new BlockPos.MutableBlockPos();

   public DynamicLight(Entity entity) {
      this.entity = entity;
      this.offsetY = entity.getEyeHeight();
   }

   public void update(RenderGlobal renderGlobal) {
      if (Config.isDynamicLightsFast()) {
         long var5 = System.currentTimeMillis();
         if (var5 < this.timeCheckMs + 500L) {
            return;
         }

         this.timeCheckMs = var5;
      }

      double var41 = this.entity.posX - 0.5;
      double var7 = this.entity.posY - 0.5 + this.offsetY;
      double var9 = this.entity.posZ - 0.5;
      int var11 = DynamicLights.getLightLevel(this.entity);
      double var12 = var41 - this.lastPosX;
      double var14 = var7 - this.lastPosY;
      double var16 = var9 - this.lastPosZ;
      if (Math.abs(var12) > 0.1 || Math.abs(var14) > 0.1 || Math.abs(var16) > 0.1 || this.lastLightLevel != var11) {
         this.lastPosX = var41;
         this.lastPosY = var7;
         this.lastPosZ = var9;
         this.lastLightLevel = var11;
         this.underwater = false;
         WorldClient var20 = renderGlobal.getWorld();
         if (var20 != null) {
            this.blockPosMutable.set(MathHelper.floor_double(var41), MathHelper.floor_double(var7), MathHelper.floor_double(var9));
            Block var22 = var20.getBlockState(this.blockPosMutable).getBlock();
            this.underwater = var22 == Blocks.water;
         }

         HashSet var21 = new HashSet();
         if (var11 > 0) {
            EnumFacing var42 = (MathHelper.floor_double(var41) & 15) >= 8 ? EnumFacing.EAST : EnumFacing.WEST;
            EnumFacing var23 = (MathHelper.floor_double(var7) & 15) >= 8 ? EnumFacing.UP : EnumFacing.DOWN;
            EnumFacing var24 = (MathHelper.floor_double(var9) & 15) >= 8 ? EnumFacing.SOUTH : EnumFacing.NORTH;
            BlockPos var25 = new BlockPos(var41, var7, var9);
            RenderChunk var26 = renderGlobal.getRenderChunk(var25);
            BlockPos var27 = this.getChunkPos(var26, var25, var42);
            RenderChunk var28 = renderGlobal.getRenderChunk(var27);
            BlockPos var29 = this.getChunkPos(var26, var25, var24);
            RenderChunk var30 = renderGlobal.getRenderChunk(var29);
            BlockPos var31 = this.getChunkPos(var28, var27, var24);
            RenderChunk var32 = renderGlobal.getRenderChunk(var31);
            BlockPos var33 = this.getChunkPos(var26, var25, var23);
            RenderChunk var34 = renderGlobal.getRenderChunk(var33);
            BlockPos var35 = this.getChunkPos(var34, var33, var42);
            RenderChunk var36 = renderGlobal.getRenderChunk(var35);
            BlockPos var37 = this.getChunkPos(var34, var33, var24);
            RenderChunk var38 = renderGlobal.getRenderChunk(var37);
            BlockPos var39 = this.getChunkPos(var36, var35, var24);
            RenderChunk var40 = renderGlobal.getRenderChunk(var39);
            this.updateChunkLight(var26, this.setLitChunkPos, var21);
            this.updateChunkLight(var28, this.setLitChunkPos, var21);
            this.updateChunkLight(var30, this.setLitChunkPos, var21);
            this.updateChunkLight(var32, this.setLitChunkPos, var21);
            this.updateChunkLight(var34, this.setLitChunkPos, var21);
            this.updateChunkLight(var36, this.setLitChunkPos, var21);
            this.updateChunkLight(var38, this.setLitChunkPos, var21);
            this.updateChunkLight(var40, this.setLitChunkPos, var21);
         }

         this.updateLitChunks(renderGlobal);
         this.setLitChunkPos = var21;
      }
   }

   private BlockPos getChunkPos(RenderChunk renderChunk, BlockPos blockPos, EnumFacing enumFacing) {
      return renderChunk != null ? renderChunk.getBlockPosOffset16(enumFacing) : blockPos.offset(enumFacing, 16);
   }

   private void updateChunkLight(RenderChunk renderChunk, Set<BlockPos> set2, Set<BlockPos> set3) {
      if (renderChunk != null) {
         CompiledChunk var7 = renderChunk.getCompiledChunk();
         if (var7 != null && !var7.isEmpty()) {
            renderChunk.setNeedsUpdate(true);
         }

         BlockPos var8 = renderChunk.getPosition();
         if (set2 != null) {
            set2.remove(var8);
         }

         if (set3 != null) {
            set3.add(var8);
         }
      }
   }

   public void updateLitChunks(RenderGlobal renderGlobal) {
      for (BlockPos var6 : this.setLitChunkPos) {
         RenderChunk var7 = renderGlobal.getRenderChunk(var6);
         this.updateChunkLight(var7, null, null);
      }
   }

   public Entity getEntity() {
      return this.entity;
   }

   public double getLastPosX() {
      return this.lastPosX;
   }

   public double getLastPosY() {
      return this.lastPosY;
   }

   public double getLastPosZ() {
      return this.lastPosZ;
   }

   public int getLastLightLevel() {
      return this.lastLightLevel;
   }

   public boolean isUnderwater() {
      return this.underwater;
   }

   public double getOffsetY() {
      return this.offsetY;
   }

   @Override
   public String toString() {
      double var3 = this.offsetY;
      Entity var5 = this.entity;
      return "Entity: " + var5 + ", offsetY: " + var3;
   }
}
