package com.moon.handlers;

import com.moon.Moon;
import com.moon.events.impl.lifecycle.LoopEvent;
import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.impl.render.RenderEntityEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.visual.Camera;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GLContext;

public class EntityCullingHandler {
   private static final Minecraft mc = Minecraft.getMinecraft();
   private static final RenderManager renderManager = mc.getRenderManager();
   private static final ConcurrentHashMap<UUID, EntityOcclusionQuery> QUERIES = new ConcurrentHashMap<>();
   private static final boolean OPENGL33 = GLContext.getCapabilities().OpenGL33;
   public static boolean enabled = false;
   private int cleanupCounter;
   private static Camera cameraModule;

   public EntityCullingHandler() {
      cameraModule = Moon.getInstance().getModuleManager().getModule(Camera.class);
   }

   public static void renderOcclusionBox(AxisAlignedBB box) {
      GlStateManager.disableAlpha();
      GlStateManager.disableCull();
      GlStateManager.depthMask(false);
      GlStateManager.colorMask(false, false, false, false);
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldRenderer = tessellator.getWorldRenderer();
      worldRenderer.begin(8, DefaultVertexFormats.POSITION);
      worldRenderer.pos(box.maxX, box.maxY, box.maxZ).endVertex();
      worldRenderer.pos(box.maxX, box.maxY, box.minZ).endVertex();
      worldRenderer.pos(box.minX, box.maxY, box.maxZ).endVertex();
      worldRenderer.pos(box.minX, box.maxY, box.minZ).endVertex();
      worldRenderer.pos(box.minX, box.minY, box.maxZ).endVertex();
      worldRenderer.pos(box.minX, box.minY, box.minZ).endVertex();
      worldRenderer.pos(box.minX, box.maxY, box.minZ).endVertex();
      worldRenderer.pos(box.minX, box.minY, box.minZ).endVertex();
      worldRenderer.pos(box.maxX, box.maxY, box.minZ).endVertex();
      worldRenderer.pos(box.maxX, box.minY, box.minZ).endVertex();
      worldRenderer.pos(box.maxX, box.maxY, box.maxZ).endVertex();
      worldRenderer.pos(box.maxX, box.minY, box.maxZ).endVertex();
      worldRenderer.pos(box.minX, box.maxY, box.maxZ).endVertex();
      worldRenderer.pos(box.minX, box.minY, box.maxZ).endVertex();
      worldRenderer.pos(box.minX, box.minY, box.maxZ).endVertex();
      worldRenderer.pos(box.maxX, box.minY, box.maxZ).endVertex();
      worldRenderer.pos(box.minX, box.minY, box.minZ).endVertex();
      worldRenderer.pos(box.maxX, box.minY, box.minZ).endVertex();
      tessellator.draw();
      GlStateManager.depthMask(true);
      GlStateManager.colorMask(true, true, true, true);
      GlStateManager.enableAlpha();
   }

   public static boolean isEntityCulled(Entity entity) {
      return enabled && cameraModule.entityCulling.getValue() && entity.worldObj == mc.thePlayer.worldObj && queryOcclusion(entity);
   }

   private static boolean queryOcclusion(Entity entity) {
      EntityOcclusionQuery query = QUERIES.computeIfAbsent(entity.getUniqueID(), EntityOcclusionQuery::new);
      if (query.isNeedsQuery()) {
         query.setQueryId(GL15.glGenQueries());
         query.setNeedsQuery(false);
         int queryTarget = OPENGL33 ? 35887 : 35092;
         GL15.glBeginQuery(queryTarget, query.getQueryId());
         renderOcclusionBox(
            entity.getEntityBoundingBox().expand(0.2, 0.2, 0.2).offset(-renderManager.Method588(), -renderManager.Method589(), -renderManager.Method590())
         );
         GL15.glEndQuery(queryTarget);
      }

      return query.isCulled();
   }

   @Subscribe
   public void onRenderEntity(RenderEntityEvent event) {
      if (cameraModule.entityCulling.getValue() && enabled) {
         EntityLivingBase entity = (EntityLivingBase)event.getEntity();
         if (queryOcclusion(entity)) {
            event.setCancelled(true);
         }
      }
   }

   private void pollQueryResults() {
      long intervalMs = 50L;
      long currentTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
      for (EntityOcclusionQuery query : QUERIES.values()) {
         if (query.getQueryId() != 0) {
            long available = GL15.glGetQueryObjecti(query.getQueryId(), GL15.GL_QUERY_RESULT_AVAILABLE);
            if (available != 0L) {
               query.setCulled(GL15.glGetQueryObjecti(query.getQueryId(), GL15.GL_QUERY_RESULT) == 0);
               GL15.glDeleteQueries(query.getQueryId());
               query.setQueryId(0);
            }
         }

         if (query.getQueryId() == 0 && currentTime - query.getLastQueryTime() > intervalMs) {
            query.setLastQueryTime(currentTime);
            query.setNeedsQuery(true);
         }
      }
   }

   @Subscribe
   public void onLoop(LoopEvent event) {
      if (event.getSequence() == LoopEvent.Sequence.POST && cameraModule.entityCulling.getValue()) {
         Minecraft.getMinecraft().addScheduledTask(this::pollQueryResults);
      }
   }

   @Subscribe
   public void onTick(TickEvent event) {
      if (event.getSequence() == TickEvent.Sequence.POST && cameraModule.entityCulling.getValue() && this.cleanupCounter++ >= 120) {
         this.cleanupCounter = 0;
         WorldClient world = mc.theWorld;
         if (world != null) {
            ArrayList<UUID> toRemove = new ArrayList<>();
            HashSet loadedUuids = new HashSet();

            for (Entity entity : world.loadedEntityList) {
               loadedUuids.add(entity.getUniqueID());
            }

            for (EntityOcclusionQuery query : QUERIES.values()) {
               if (!loadedUuids.contains(query.getEntityUuid())) {
                  toRemove.add(query.getEntityUuid());
                  if (query.getQueryId() != 0) {
                     GL15.glDeleteQueries(query.getQueryId());
                  }
               }
            }

            for (UUID uuid : toRemove) {
               QUERIES.remove(uuid);
            }
         }
      }
   }
}
