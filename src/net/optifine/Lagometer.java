package net.optifine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.profiler.Profiler;
import net.minecraft.src.Config;
import net.optifine.util.MemoryMonitor;
import org.lwjgl.opengl.GL11;

public class Lagometer {
   private static Minecraft mc;
   private static GameSettings gameSettings;
   private static Profiler profiler;
   public static boolean active = false;
   public static Lagometer.TimerNano timerTick = new Lagometer.TimerNano();
   public static Lagometer.TimerNano timerScheduledExecutables = new Lagometer.TimerNano();
   public static Lagometer.TimerNano timerChunkUpload = new Lagometer.TimerNano();
   public static Lagometer.TimerNano timerChunkUpdate = new Lagometer.TimerNano();
   public static Lagometer.TimerNano timerVisibility = new Lagometer.TimerNano();
   public static Lagometer.TimerNano timerTerrain = new Lagometer.TimerNano();
   public static Lagometer.TimerNano timerServer = new Lagometer.TimerNano();
   private static final long[] timesFrame = new long[512];
   private static final long[] timesTick = new long[512];
   private static final long[] timesScheduledExecutables = new long[512];
   private static final long[] timesChunkUpload = new long[512];
   private static final long[] timesChunkUpdate = new long[512];
   private static final long[] timesVisibility = new long[512];
   private static final long[] timesTerrain = new long[512];
   private static final long[] timesServer = new long[512];
   private static final boolean[] gcs = new boolean[512];
   private static int numRecordedFrameTimes = 0;
   private static long prevFrameTimeNano = -1L;
   private static long renderTimeNano = 0L;

   public static void updateLagometer() {
      if (mc == null) {
         mc = Minecraft.getMinecraft();
         gameSettings = mc.gameSettings;
         profiler = mc.mcProfiler;
      }

      if (gameSettings.showDebugInfo && (gameSettings.ofLagometer || gameSettings.showLagometer)) {
         active = true;
         long var3 = System.nanoTime();
         if (prevFrameTimeNano == -1L) {
            prevFrameTimeNano = var3;
         } else {
            int var5 = numRecordedFrameTimes & timesFrame.length - 1;
            numRecordedFrameTimes++;
            boolean var6 = MemoryMonitor.isGcEvent();
            timesFrame[var5] = var3 - prevFrameTimeNano - renderTimeNano;
            timesTick[var5] = timerTick.timeNano;
            timesScheduledExecutables[var5] = timerScheduledExecutables.timeNano;
            timesChunkUpload[var5] = timerChunkUpload.timeNano;
            timesChunkUpdate[var5] = timerChunkUpdate.timeNano;
            timesVisibility[var5] = timerVisibility.timeNano;
            timesTerrain[var5] = timerTerrain.timeNano;
            timesServer[var5] = timerServer.timeNano;
            gcs[var5] = var6;
            timerTick.reset();
            timerScheduledExecutables.reset();
            timerVisibility.reset();
            timerChunkUpdate.reset();
            timerChunkUpload.reset();
            timerTerrain.reset();
            timerServer.reset();
            prevFrameTimeNano = System.nanoTime();
         }
      } else {
         active = false;
         prevFrameTimeNano = -1L;
      }
   }

   public static void showLagometer(ScaledResolution scaledResolution) {
      if (gameSettings != null && (gameSettings.ofLagometer || gameSettings.showLagometer)) {
         long var4 = System.nanoTime();
         GlStateManager.clear(256);
         GlStateManager.matrixMode(5889);
         GlStateManager.pushMatrix();
         GlStateManager.enableColorMaterial();
         GlStateManager.loadIdentity();
         GlStateManager.ortho(0.0, mc.displayWidth, mc.displayHeight, 0.0, 1000.0, 3000.0);
         GlStateManager.matrixMode(5888);
         GlStateManager.pushMatrix();
         GlStateManager.loadIdentity();
         GlStateManager.translate(0.0F, 0.0F, -2000.0F);
         GL11.glLineWidth(1.0F);
         GlStateManager.disableTexture2D();
         Tessellator var6 = Tessellator.getInstance();
         WorldRenderer var7 = var6.getWorldRenderer();
         var7.begin(1, DefaultVertexFormats.POSITION_COLOR);

         for (int var8 = 0; var8 < timesFrame.length; var8++) {
            int var9 = (var8 - numRecordedFrameTimes & timesFrame.length - 1) * 100 / timesFrame.length + 155;
            float var10 = mc.displayHeight;
            if (gcs[var8]) {
               renderTime(var8, timesFrame[var8], var9, var9 / 2, 0, var10, var7);
            } else {
               renderTime(var8, timesFrame[var8], var9, var9, var9, var10, var7);
               var10 -= (float)renderTime(var8, timesServer[var8], var9 / 2, var9 / 2, var9 / 2, var10, var7);
               var10 -= (float)renderTime(var8, timesTerrain[var8], 0, var9, 0, var10, var7);
               var10 -= (float)renderTime(var8, timesVisibility[var8], var9, var9, 0, var10, var7);
               var10 -= (float)renderTime(var8, timesChunkUpdate[var8], var9, 0, 0, var10, var7);
               var10 -= (float)renderTime(var8, timesChunkUpload[var8], var9, 0, var9, var10, var7);
               var10 -= (float)renderTime(var8, timesScheduledExecutables[var8], 0, 0, var9, var10, var7);
               float var10000 = var10 - (float)renderTime(var8, timesTick[var8], 0, var9, var9, var10, var7);
            }
         }

         renderTimeDivider(0, timesFrame.length, 33333333L, 196, 196, 196, mc.displayHeight, var7);
         renderTimeDivider(0, timesFrame.length, 16666666L, 196, 196, 196, mc.displayHeight, var7);
         var6.draw();
         GlStateManager.enableTexture2D();
         int var17 = mc.displayHeight - 80;
         int var18 = mc.displayHeight - 160;
         mc.fontRendererObj.drawString("30", 2.0F, var18 + 1, -8947849);
         mc.fontRendererObj.drawString("30", 1.0F, var18, -3881788);
         mc.fontRendererObj.drawString("60", 2.0F, var17 + 1, -8947849);
         mc.fontRendererObj.drawString("60", 1.0F, var17, -3881788);
         GlStateManager.matrixMode(5889);
         GlStateManager.popMatrix();
         GlStateManager.matrixMode(5888);
         GlStateManager.popMatrix();
         GlStateManager.enableTexture2D();
         float var25 = Config.Method256(1.0F - (float)((System.currentTimeMillis() - MemoryMonitor.getStartTimeMs()) / 1000.0), 0.0F, 1.0F);
         int var11 = (int)(170.0F + var25 * 85.0F);
         int var12 = (int)(100.0F + var25 * 55.0F);
         int var13 = (int)(10.0F + var25 * 10.0F);
         int var14 = var11 << 16 | var12 << 8 | var13;
         int var15 = 512 / scaledResolution.getScaleFactor() + 2;
         int var16 = mc.displayHeight / scaledResolution.getScaleFactor() - 8;
         GuiIngame.drawRect(var15 - 1, var16 - 1, var15 + 50, var16 + 10, -1605349296);
         mc.fontRendererObj.drawString(" " + MemoryMonitor.getAllocationRateMb() + " MB/s", var15, var16, var14);
         renderTimeNano = System.nanoTime() - var4;
      }
   }

   private static long renderTime(int integer1, long long2, int integer3, int integer4, int integer5, float float6, WorldRenderer worldRenderer) {
      long var11 = long2 / 200000L;
      if (var11 < 3L) {
         return 0L;
      }

      worldRenderer.pos(integer1 + 0.5F, float6 - (float)var11 + 0.5F, 0.0).color(integer3, integer4, integer5, 255).endVertex();
      worldRenderer.pos(integer1 + 0.5F, float6 + 0.5F, 0.0).color(integer3, integer4, integer5, 255).endVertex();
      return var11;
   }

   private static long renderTimeDivider(
      int integer1, int integer2, long long3, int integer4, int integer5, int integer6, float float7, WorldRenderer worldRenderer
   ) {
      long var12 = long3 / 200000L;
      if (var12 < 3L) {
         return 0L;
      }

      worldRenderer.pos(integer1 + 0.5F, float7 - (float)var12 + 0.5F, 0.0).color(integer4, integer5, integer6, 255).endVertex();
      worldRenderer.pos(integer2 + 0.5F, float7 - (float)var12 + 0.5F, 0.0).color(integer4, integer5, integer6, 255).endVertex();
      return var12;
   }

   public static boolean isActive() {
      return active;
   }

   public static class TimerNano {
      public long timeStartNano = 0L;
      public long timeNano = 0L;

      public void start() {
         if (Lagometer.active && this.timeStartNano == 0L) {
            this.timeStartNano = System.nanoTime();
         }
      }

      public void end() {
         if (Lagometer.active && this.timeStartNano != 0L) {
            this.timeNano = this.timeNano + (System.nanoTime() - this.timeStartNano);
            this.timeStartNano = 0L;
         }
      }

      private void reset() {
         this.timeNano = 0L;
         this.timeStartNano = 0L;
      }
   }
}
