package net.minecraft.client.gui;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;
import net.optifine.SmartAnimations;
import net.optifine.TextureAnimations;
import net.optifine.util.MemoryMonitor;
import net.optifine.util.NativeMemory;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class GuiOverlayDebug extends Gui {
   private final Minecraft mc;
   private final FontRenderer fontRenderer;
   private String debugOF = null;
   private List<String> debugInfoLeft = null;
   private List<String> debugInfoRight = null;
   private long updateInfoLeftTimeMs = 0L;
   private long updateInfoRightTimeMs = 0L;

   public GuiOverlayDebug(Minecraft minecraft) {
      this.mc = minecraft;
      this.fontRenderer = minecraft.fontRendererObj;
   }

   public void renderDebugInfo(ScaledResolution scaledResolution) {
      this.mc.mcProfiler.startSection("debug");
      GlStateManager.pushMatrix();
      this.renderDebugInfoLeft();
      this.renderDebugInfoRight(scaledResolution);
      GlStateManager.popMatrix();
      if (this.mc.gameSettings.showLagometer) {
         this.Method545();
      }

      this.mc.mcProfiler.endSection();
   }

   private boolean isReducedDebug() {
      return this.mc.thePlayer.hasReducedDebug() || this.mc.gameSettings.reducedDebugInfo;
   }

   protected void renderDebugInfoLeft() {
      List var3 = this.debugInfoLeft;
      if (var3 == null || System.currentTimeMillis() > this.updateInfoLeftTimeMs) {
         var3 = this.call();
         this.debugInfoLeft = var3;
         this.updateInfoLeftTimeMs = System.currentTimeMillis() + 100L;
      }

      for (int var4 = 0; var4 < var3.size(); var4++) {
         String var5 = (String)var3.get(var4);
         if (!Strings.isNullOrEmpty(var5)) {
            int var6 = this.fontRenderer.FONT_HEIGHT;
            int var7 = this.fontRenderer.getStringWidth(var5);
            int var9 = 2 + var6 * var4;
            drawRect(1, var9 - 1, 2 + var7 + 1, var9 + var6 - 1, -1873784752);
            this.fontRenderer.drawString(var5, 2.0F, var9, 14737632);
         }
      }
   }

   protected void renderDebugInfoRight(ScaledResolution scaledResolution) {
      List var4 = this.debugInfoRight;
      if (var4 == null || System.currentTimeMillis() > this.updateInfoRightTimeMs) {
         var4 = this.getDebugInfoRight();
         this.debugInfoRight = var4;
         this.updateInfoRightTimeMs = System.currentTimeMillis() + 100L;
      }

      for (int var5 = 0; var5 < var4.size(); var5++) {
         String var6 = (String)var4.get(var5);
         if (!Strings.isNullOrEmpty(var6)) {
            int var7 = this.fontRenderer.FONT_HEIGHT;
            int var8 = this.fontRenderer.getStringWidth(var6);
            int var9 = scaledResolution.Method237() - 2 - var8;
            int var10 = 2 + var7 * var5;
            drawRect(var9 - 1, var10 - 1, var9 + var8 + 1, var10 + var7 - 1, -1873784752);
            this.fontRenderer.drawString(var6, var9, var10, 14737632);
         }
      }
   }

   protected List<String> call() {
      BlockPos var3 = new BlockPos(
         this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().getEntityBoundingBox().minY, this.mc.getRenderViewEntity().posZ
      );
      if (this.mc.debug != this.debugOF) {
         StringBuilder var4 = new StringBuilder(this.mc.debug);
         int var5 = Config.getFpsMin();
         int var6 = this.mc.debug.indexOf(" fps ");
         if (var6 >= 0) {
            var4.insert(var6, "/" + var5);
         }

         if (Config.aF()) {
            var4.append(" sf");
         }

         if (Config.isAnisotropicFiltering()) {
            var4.append(" af");
         }

         if (Config.isAntialiasing()) {
            var4.append(" aa");
         }

         if (Config.isRenderRegions()) {
            var4.append(" reg");
         }

         if (Config.isShaders()) {
            var4.append(" sh");
         }

         this.mc.debug = var4.toString();
         this.debugOF = this.mc.debug;
      }

      StringBuilder var15 = new StringBuilder();
      TextureMap var16 = Config.getTextureMap();
      var15.append(", A: ");
      if (SmartAnimations.isActive()) {
         var15.append(var16.getCountAnimationsActive() + TextureAnimations.getCountAnimationsActive());
         var15.append("/");
      }

      var15.append(var16.getCountAnimations() + TextureAnimations.getCountAnimations());
      String var17 = var15.toString();
      if (this.isReducedDebug()) {
         return Lists.newArrayList(
            new String[]{
               "Minecraft 1.8.9 (" + this.mc.getVersion() + "/" + ClientBrandRetriever.getClientModName() + ")",
               this.mc.debug,
               this.mc.renderGlobal.getDebugInfoRenders(),
               this.mc.renderGlobal.Method720(),
               "P: " + this.mc.effectRenderer.getStatistics() + ". T: " + this.mc.theWorld.Method331() + var17,
               this.mc.theWorld.getProviderName(),
               "",
               String.format("Chunk-relative: %d %d %d", var3.getX() & 15, var3.getY() & 15, var3.getZ() & 15)
            }
         );
      }

      Entity var7 = this.mc.getRenderViewEntity();
      EnumFacing var8 = var7.getHorizontalFacing();
      String var9 = "Invalid";
      switch (var8) {
         case NORTH:
            var9 = "Towards negative Z";
            break;
         case SOUTH:
            var9 = "Towards positive Z";
            break;
         case WEST:
            var9 = "Towards negative X";
            break;
         case EAST:
            var9 = "Towards positive X";
      }

      ArrayList var10 = Lists.newArrayList(
         new String[]{
            "Moon Client (" + this.mc.getVersion() + "/" + ClientBrandRetriever.getClientModName() + ")",
            this.mc.debug,
            this.mc.renderGlobal.getDebugInfoRenders(),
            this.mc.renderGlobal.Method720(),
            "P: " + this.mc.effectRenderer.getStatistics() + ". T: " + this.mc.theWorld.Method331() + var17,
            this.mc.theWorld.getProviderName(),
            "",
            String.format(
               "XYZ: %.3f / %.5f / %.3f",
               this.mc.getRenderViewEntity().posX,
               this.mc.getRenderViewEntity().getEntityBoundingBox().minY,
               this.mc.getRenderViewEntity().posZ
            ),
            String.format("Block: %d %d %d", var3.getX(), var3.getY(), var3.getZ()),
            String.format(
               "Chunk: %d %d %d in %d %d %d", var3.getX() & 15, var3.getY() & 15, var3.getZ() & 15, var3.getX() >> 4, var3.getY() >> 4, var3.getZ() >> 4
            ),
            String.format(
               "Facing: %s (%s) (%.1f / %.1f)",
               var8,
               var9,
               MathHelper.wrapAngleTo180_float(var7.rotationYaw),
               MathHelper.wrapAngleTo180_float(var7.rotationPitch)
            )
         }
      );
      if (this.mc.theWorld != null && this.mc.theWorld.Method322(var3)) {
         Chunk var11 = this.mc.theWorld.getChunkFromBlockCoords(var3);
         var10.add("Biome: " + var11.getBiome(var3, this.mc.theWorld.getWorldChunkManager()).biomeName);
         var10.add(
            "Light: "
               + var11.getLightSubtracted(var3, 0)
               + " ("
               + var11.getLightFor(EnumSkyBlock.SKY, var3)
               + " sky, "
               + var11.getLightFor(EnumSkyBlock.BLOCK, var3)
               + " block)"
         );
         DifficultyInstance var12 = this.mc.theWorld.getDifficultyForLocation(var3);
         if (this.mc.isIntegratedServerRunning() && this.mc.getIntegratedServer() != null) {
            EntityPlayerMP var13 = this.mc.getIntegratedServer().getConfigurationManager().getPlayerByUUID(this.mc.thePlayer.getUniqueID());
            if (var13 != null) {
               DifficultyInstance var14 = this.mc.getIntegratedServer().getDifficultyAsync(var13.worldObj, new BlockPos(var13));
               if (var14 != null) {
                  var12 = var14;
               }
            }
         }

         var10.add(String.format("Local Difficulty: %.2f (Day %d)", var12.getAdditionalDifficulty(), this.mc.theWorld.getWorldTime() / 24000L));
      }

      if (this.mc.entityRenderer != null && this.mc.entityRenderer.isShaderActive()) {
         var10.add("Shader: " + this.mc.entityRenderer.getShaderGroup().getShaderGroupName());
      }

      if (this.mc.objectMouseOver != null
         && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
         && this.mc.objectMouseOver.getBlockPos() != null) {
         BlockPos var18 = this.mc.objectMouseOver.getBlockPos();
         var10.add(String.format("Looking at: %d %d %d", var18.getX(), var18.getY(), var18.getZ()));
      }

      return var10;
   }

   protected List<String> getDebugInfoRight() {
      long var3 = Runtime.getRuntime().maxMemory();
      long var5 = Runtime.getRuntime().totalMemory();
      long var7 = Runtime.getRuntime().freeMemory();
      long var9 = var5 - var7;
      ArrayList var11 = Lists.newArrayList(
         new String[]{
            String.format("Java: %s %dbit", System.getProperty("java.version"), this.mc.isJava64bit() ? 64 : 32),
            String.format("Mem: % 2d%% %03d/%03dMB", var9 * 100L / var3, bytesToMb(var9), bytesToMb(var3)),
            String.format("Allocated: % 2d%% %03dMB", var5 * 100L / var3, bytesToMb(var5)),
            "",
            String.format("CPU: %s", OpenGlHelper.getCpu()),
            "",
            String.format("Display: %dx%d (%s)", Display.getWidth(), Display.getHeight(), GL11.glGetString(GL11.GL_VENDOR)),
            GL11.glGetString(GL11.GL_RENDERER),
            GL11.glGetString(GL11.GL_VERSION)
         }
      );
      long var12 = NativeMemory.getBufferAllocated();
      long var14 = NativeMemory.getBufferMaximum();
      String var16 = "Native: " + bytesToMb(var12) + "/" + bytesToMb(var14) + "MB";
      var11.add(4, var16);
      var11.set(5, "GC: " + MemoryMonitor.getAllocationRateMb() + "MB/s");
      if (this.isReducedDebug()) {
         return var11;
      }

      if (this.mc.objectMouseOver != null
         && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
         && this.mc.objectMouseOver.getBlockPos() != null) {
         BlockPos var17 = this.mc.objectMouseOver.getBlockPos();
         IBlockState var18 = this.mc.theWorld.getBlockState(var17);
         if (this.mc.theWorld.getWorldType() != WorldType.DEBUG_WORLD) {
            var18 = var18.getBlock().getActualState(var18, this.mc.theWorld, var17);
         }

         var11.add("");
         var11.add(String.valueOf(Block.blockRegistry.getNameForObject(var18.getBlock())));

         for (Entry var20 : var18.getProperties().entrySet()) {
            String var21 = ((Comparable)var20.getValue()).toString();
            if (var20.getValue() == Boolean.TRUE) {
               var21 = EnumChatFormatting.GREEN + var21;
            } else if (var20.getValue() == Boolean.FALSE) {
               var21 = EnumChatFormatting.RED + var21;
            }

            var11.add(((IProperty)var20.getKey()).getName() + ": " + var21);
         }
      }

      return var11;
   }

   private void Method545() {
      GlStateManager.disableDepth();
      FrameTimer var3 = this.mc.getFrameTimer();
      int var4 = var3.getLastIndex();
      int var5 = var3.getIndex();
      long[] var6 = var3.getFrames();
      ScaledResolution var7 = new ScaledResolution(this.mc);
      int var8 = var4;
      int var9 = 0;
      drawRect(0, var7.Method238() - 60, 240, var7.Method238(), -1873784752);

      while (var8 != var5) {
         int var10 = var3.getLagometerValue(var6[var8], 30);
         int var11 = this.getFrameColor(MathHelper.clamp_int(var10, 0, 60), 0, 30, 60);
         this.drawVerticalLine(var9, var7.Method238(), var7.Method238() - var10, var11);
         var9++;
         var8 = var3.parseIndex(var8 + 1);
      }

      drawRect(1, var7.Method238() - 30 + 1, 14, var7.Method238() - 30 + 10, -1873784752);
      this.fontRenderer.drawString("60", 2.0F, var7.Method238() - 30 + 2, 14737632);
      this.drawHorizontalLine(0, 239, var7.Method238() - 30, -1);
      drawRect(1, var7.Method238() - 60 + 1, 14, var7.Method238() - 60 + 10, -1873784752);
      this.fontRenderer.drawString("30", 2.0F, var7.Method238() - 60 + 2, 14737632);
      this.drawHorizontalLine(0, 239, var7.Method238() - 60, -1);
      this.drawHorizontalLine(0, 239, var7.Method238() - 1, -1);
      this.drawVerticalLine(0, var7.Method238() - 60, var7.Method238(), -1);
      this.drawVerticalLine(239, var7.Method238() - 60, var7.Method238(), -1);
      if (this.mc.gameSettings.limitFramerate <= 120) {
         this.drawHorizontalLine(0, 239, var7.Method238() - 60 + this.mc.gameSettings.limitFramerate / 2, -16711681);
      }

      GlStateManager.enableDepth();
   }

   private int getFrameColor(int integer1, int integer2, int integer3, int integer4) {
      return integer1 < integer3
         ? this.blendColors(-16711936, -256, (float)integer1 / integer3)
         : this.blendColors(-256, -65536, (float)(integer1 - integer3) / (integer4 - integer3));
   }

   private int blendColors(int integer1, int integer2, float float3) {
      int var6 = integer1 >> 24 & 0xFF;
      int var7 = integer1 >> 16 & 0xFF;
      int var8 = integer1 >> 8 & 0xFF;
      int var9 = integer1 & 0xFF;
      int var10 = integer2 >> 24 & 0xFF;
      int var11 = integer2 >> 16 & 0xFF;
      int var12 = integer2 >> 8 & 0xFF;
      int var13 = integer2 & 0xFF;
      int var14 = MathHelper.clamp_int((int)(var6 + (var10 - var6) * float3), 0, 255);
      int var15 = MathHelper.clamp_int((int)(var7 + (var11 - var7) * float3), 0, 255);
      int var16 = MathHelper.clamp_int((int)(var8 + (var12 - var8) * float3), 0, 255);
      int var17 = MathHelper.clamp_int((int)(var9 + (var13 - var9) * float3), 0, 255);
      return var14 << 24 | var15 << 16 | var16 << 8 | var17;
   }

   private static long bytesToMb(long long1) {
      return long1 / 1024L / 1024L;
   }
}
