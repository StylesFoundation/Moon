package com.moon.handlers;

import com.moon.Moon;
import com.moon.events.impl.render.EffectRenderEvent;
import com.moon.events.impl.render.PostFrameEvent;
import com.moon.events.impl.render.RenderFrameEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.impl.visual.Effects;
import com.moon.notification.Notification;
import com.moon.notification.NotificationManager;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.font.manager.FontType;
import com.moon.render.font.msdf.MsdfFontRenderer;
import com.moon.util.animation.Animation;
import com.moon.util.client.PreciseTicker;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class NotificationHandler {
   public static final MsdfFontRenderer textFont = (MsdfFontRenderer)Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
   public static final MsdfFontRenderer iconFont = (MsdfFontRenderer)Moon.getInstance().getFontManager().getFont(FontType.MATERIAL_ICONS);
   private final NotificationManager notificationManager = Moon.getInstance().getNotificationManager();
   private final Effects effects = Moon.getInstance().getModuleManager().getModule(Effects.class);
   private final PreciseTicker frameRateTracker = new PreciseTicker();
   private boolean needsRebuild = true;
   private RenderTask backgroundTask;
   private RenderTask iconTask;
   private RenderTask textTask;
   private RenderTask effectTask;
   private long lastActivityTime;

   public NotificationHandler() {
      new Thread(() -> {
         while (true) {
            this.frameRateTracker.runAt(Display.getDesktopDisplayMode().getFrequency());
            this.needsRebuild = true;
         }
      }, "Moon Notification Ticker").start();
   }

   @Subscribe
   public void onPostFrame(EffectRenderEvent event) {
      this.effectTask.render();
   }

   @Subscribe
   public void onRenderFrame(RenderFrameEvent event) {
      if (this.needsRebuild) {
         if (this.backgroundTask != null) {
            this.backgroundTask.getState().releaseGpuResources();
         }

         if (this.iconTask != null) {
            this.iconTask.getState().releaseGpuResources();
         }

         if (this.textTask != null) {
            this.textTask.getState().releaseGpuResources();
         }

         if (this.effectTask != null) {
            this.effectTask.getState().releaseGpuResources();
         }

         this.backgroundTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).withGpuBuffers().retainGpuBuffer().build();
         this.iconTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(iconFont).withGpuBuffers().retainGpuBuffer().build();
         this.textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(textFont).withGpuBuffers().retainGpuBuffer().build();
         this.effectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).withGpuBuffers().retainGpuBuffer().build();
         List<Notification> notifications = this.notificationManager.Method758();
         ArrayList<Notification> expiredNotifications = new ArrayList<>();
         float yOffset = 0.0F;

         for (Notification notification : notifications) {
            if (notification.getExpirationTime() < System.currentTimeMillis()) {
               if (!notification.isFinished()) {
                  Animation animation = notification.getAnimation();
                  animation.setValue(animation.getValue());
               }

               notification.setFinished(true);
            }

            if (notification.isExpired()) {
               expiredNotifications.add(notification);
            }

            notification.render(this.backgroundTask, this.iconTask, this.textTask, this.effectTask, yOffset);
            if (!notification.getAnimation().isDone()) {
               this.lastActivityTime = System.currentTimeMillis();
            }

            yOffset -= notification.getAnimatedHeight() + 2.0F;
         }

         for (Notification expired : expiredNotifications) {
            this.notificationManager.Method757(expired);
         }

         if (System.currentTimeMillis() - this.lastActivityTime < 250L) {
            this.effects.getGaussianRenderer().requestRender();
            this.effects.getKawaseRenderer().setNeedsRender(true);
         }

         this.needsRebuild = false;
      }
   }

   @Subscribe
   public void onEffectRender(PostFrameEvent event) {
      boolean blendEnabled = GL11.glIsEnabled(GL11.GL_BLEND);
      if (!blendEnabled) {
         GlStateManager.enableBlend();
      }

      this.backgroundTask.render();
      this.iconTask.render();
      this.textTask.render();
      if (!blendEnabled) {
         GlStateManager.disableBlend();
      }
   }
}
