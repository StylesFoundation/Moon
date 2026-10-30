package com.moon.module.impl.visual;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.moon.Moon;
import com.moon.events.impl.render.OverlayRenderEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.handlers.ScaleHandler;
import com.moon.module.LabelEnum;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.ColorSetting;
import com.moon.module.setting.impl.FloatSetting;
import com.moon.module.setting.impl.GroupSetting;
import com.moon.module.setting.impl.MultipleChoiceSetting;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.color.RenderColor;
import com.moon.render.command.impl.shape.RectangleCommand;
import com.moon.render.command.impl.shape.TriangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.util.client.PreciseTicker;
import com.moon.util.render.FrustumUtil;
import com.moon.util.render.LegacyGlUtil;
import java.awt.Color;
import java.time.Duration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Pointers extends Module {
   private final MultipleChoiceSetting<Pointers.Targets> targets = MultipleChoiceSetting.builder("targets", Pointers.Targets.class)
      .defaults("Targets", Pointers.Targets.PLAYER)
      .build();
   private final FloatSetting size = FloatSetting.builder("size").defaults("Size", 10.0F, 5.0F, 25.0F, 0.1F).build();
   private final FloatSetting radius = FloatSetting.builder("radius").defaults("Radius", 45.0F, 10.0F, 200.0F, 1.0F).build();
   private final BooleanSetting stretch = BooleanSetting.builder("stretch").defaults("Stretch", false).build();
   private final BooleanSetting distanceColored = BooleanSetting.builder("distance-colored").defaults("Distance colored", true).build();
   private final ColorSetting color = ColorSetting.builder("color").defaults("Color", -1).visibleWhen(() -> !this.distanceColored.getValue()).build();
   private final FloatSetting opacity = FloatSetting.builder("opacity").defaults("Opacity", 0.5F, 0.0F, 1.0F, 0.05F).build();
   private final GroupSetting arrowSettings = GroupSetting.builder()
      .defaults("Arrow Settings", this.size, this.radius, this.stretch, this.distanceColored, this.color, this.opacity)
      .build();
   private final BooleanSetting showBox = BooleanSetting.builder("box").defaults("Box", false).build();
   private final BooleanSetting showName = BooleanSetting.builder("name").defaults("Name", true).build();
   private final BooleanSetting showDistance = BooleanSetting.builder("distance").defaults("Distance", true).build();
   private final BooleanSetting showHealth = BooleanSetting.builder("health").defaults("Health", true).build();
   private final GroupSetting infoSettings = GroupSetting.builder()
      .defaults("Info Settings", this.showBox, this.showName, this.showHealth, this.showDistance)
      .build();
   private final PreciseTicker ticker = new PreciseTicker();
   private boolean needsRebuild = true;
   private RenderTask rectangleTask;
   private RenderTask triangleTask;
   private RenderTask textTask;
   private final Cache<EntityLivingBase, Double> healthCache = CacheBuilder.newBuilder().expireAfterWrite(Duration.ofMinutes(2L)).build();

   public Pointers(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
      new Thread(() -> {
         while (true) {
            this.needsRebuild = true;
            this.ticker.runAt(this.getTargetFps());
         }
      }, "Moon Offscreen Ticking").start();
   }

   @Subscribe
   public void onOverlayRender(OverlayRenderEvent event) {
      if (event.getSequence() == OverlayRenderEvent.Sequence.PRE) {
         FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.VERDANA);
         GlStateManager.enableBlend();
         if (this.needsRebuild || mc.gameSettings.limitFramerate != 260.0F || mc.gameSettings.enableVsync || Minecraft.getDebugFPS() < this.getTargetFps()) {
            if (this.rectangleTask != null) {
               this.rectangleTask.getState().releaseGpuResources();
            }

            if (this.triangleTask != null) {
               this.triangleTask.getState().releaseGpuResources();
            }

            if (this.textTask != null) {
               this.textTask.getState().releaseGpuResources();
            }

            float arrowSize = this.size.getValue();
            this.triangleTask = RenderTask.builder().withRenderType(RenderType.TRIANGLE).withGpuBuffers().retainGpuBuffer().build();
            this.rectangleTask = RenderTask.builder().withRenderType(RenderType.RECTANGLE).withGpuBuffers().retainGpuBuffer().build();
            this.textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withGpuBuffers().withFontRenderer(fontRenderer).retainGpuBuffer().build();

            for (Entity entity : mc.theWorld.loadedEntityList) {
               if (entity instanceof EntityLivingBase livingBase && this.isValidTarget(entity) && !FrustumUtil.isVisible(entity)) {
                  int arrowColor = new RenderColor(this.distanceColored.getValue() ? this.getDistanceColor(livingBase) : this.color.getValue())
                     .withAlphaFraction(this.opacity.getValue())
                     .getRGB();
                  double entityX = LegacyGlUtil.lerp(livingBase.posX, livingBase.lastTickPosX, mc.timer.renderPartialTicks);
                  double entityY = LegacyGlUtil.lerp(livingBase.posY, livingBase.lastTickPosY, mc.timer.renderPartialTicks);
                  double entityZ = LegacyGlUtil.lerp(livingBase.posZ, livingBase.lastTickPosZ, mc.timer.renderPartialTicks);
                  double playerX = LegacyGlUtil.lerp(mc.thePlayer.posX, mc.thePlayer.lastTickPosX, mc.timer.renderPartialTicks);
                  double playerY = LegacyGlUtil.lerp(mc.thePlayer.posY, mc.thePlayer.lastTickPosY, mc.timer.renderPartialTicks);
                  double playerZ = LegacyGlUtil.lerp(mc.thePlayer.posZ, mc.thePlayer.lastTickPosZ, mc.timer.renderPartialTicks);
                  Vector3f diffVec = new Vector3f((float)(entityZ - playerZ), (float)(entityX - playerX), (float)(entityY - playerY));
                  diffVec = this.normalize(diffVec);
                  Vector3f screenPos = this.calculateScreenPosition(diffVec);
                  Vector2f center = new Vector2f(screenPos.x, screenPos.y);
                  Vector2f right = new Vector2f(screenPos.x + arrowSize / 2.0F, screenPos.y + arrowSize / 2.0F);
                  Vector2f left = new Vector2f(screenPos.x - arrowSize / 2.0F, screenPos.y + arrowSize / 2.0F);
                  Vector2f tip = new Vector2f(screenPos.x, screenPos.y - arrowSize / 2.0F);
                  Vector2f edge = new Vector2f(tip.x - left.x, tip.y - left.y);
                  float sideLength = (float)Math.sqrt(edge.x * edge.x + edge.y * edge.y);
                  double angle = Math.asin(arrowSize / sideLength);
                  float circumRadius = (float)(sideLength / (2.0 * Math.sin(angle)));
                  float rotation = screenPos.z;
                  Vector2f rotatedTip = this.rotatePoint(center, tip, rotation);
                  Vector2f rotatedLeft = this.rotatePoint(center, left, rotation);
                  Vector2f rotatedRight = this.rotatePoint(center, right, rotation);
                  this.triangleTask.addCommand(TriangleCommand.of(rotatedTip.x, rotatedTip.y, rotatedLeft.x, rotatedLeft.y, rotatedRight.x, rotatedRight.y, arrowColor));
                  float x1 = rotatedLeft.x;
                  float y1 = rotatedLeft.y;
                  float x2 = rotatedRight.x;
                  float y2 = rotatedRight.y;
                  float x3 = rotatedTip.x;
                  float y3 = rotatedTip.y;
                  double midX1 = (x1 + x2) / 2.0F;
                  double midY1 = (y1 + y2) / 2.0F;
                  double midX2 = (x2 + x3) / 2.0F;
                  double midY2 = (y2 + y3) / 2.0F;
                  double slope1 = -(x2 - x1) / (y2 - y1);
                  double slope2 = -(x3 - x2) / (y3 - y2);
                  float centerX = (float)((midY2 - midY1 - slope2 * midX2 + slope1 * midX1) / (slope1 - slope2));
                  float centerY = (float)(midY1 + slope1 * (centerX - midX1));
                  float boxSize = circumRadius * 2.0F + 4.0F;
                  float boxX = centerX - circumRadius - 2.0F;
                  float boxY = centerY - circumRadius - 2.0F;
                  float boxMaxX = boxX + boxSize;
                  float boxMaxY = boxY + boxSize;
                  if (this.showBox.getValue()) {
                     int black = Color.BLACK.getRGB();
                     int white = Color.WHITE.getRGB();
                     this.rectangleTask.addCommand(RectangleCommand.of(boxX, boxY, boxSize, 1.5F, black));
                     this.rectangleTask.addCommand(RectangleCommand.of(boxX, boxY, 1.5F, boxSize, black));
                     this.rectangleTask.addCommand(RectangleCommand.of(boxMaxX - boxSize, boxY, boxSize, 1.5F, black));
                     this.rectangleTask.addCommand(RectangleCommand.of(boxMaxX - 1.5F, boxY, 1.5F, boxSize, black));
                     this.rectangleTask.addCommand(RectangleCommand.of(boxX, boxMaxY - 1.5F, boxSize, 1.5F, black));
                     this.rectangleTask.addCommand(RectangleCommand.of(boxX, boxMaxY - boxSize, 1.5F, boxSize, black));
                     this.rectangleTask.addCommand(RectangleCommand.of(boxMaxX - boxSize, boxMaxY - 1.5F, boxSize, 1.5F, black));
                     this.rectangleTask.addCommand(RectangleCommand.of(boxMaxX - 1.5F, boxMaxY - boxSize, 1.5F, boxSize, black));
                     this.rectangleTask.addCommand(RectangleCommand.of(boxX + 0.5F, boxY + 0.5F, boxSize - 1.0F, 0.5F, white));
                     this.rectangleTask.addCommand(RectangleCommand.of(boxX + 0.5F, boxY + 0.5F, 0.5F, boxSize - 1.0F, white));
                     this.rectangleTask.addCommand(RectangleCommand.of(boxMaxX - boxSize + 0.5F, boxY + 0.5F, boxSize - 1.0F, 0.5F, white));
                     this.rectangleTask.addCommand(RectangleCommand.of(boxMaxX - 1.0F, boxY + 0.5F, 0.5F, boxSize - 1.0F, white));
                     this.rectangleTask.addCommand(RectangleCommand.of(boxX + 0.5F, boxMaxY - 1.0F, boxSize - 1.0F, 0.5F, white));
                     this.rectangleTask.addCommand(RectangleCommand.of(boxX + 0.5F, boxMaxY - boxSize + 0.5F, 0.5F, boxSize - 1.0F, white));
                     this.rectangleTask.addCommand(RectangleCommand.of(boxMaxX - boxSize + 0.5F, boxMaxY - 1.0F, boxSize - 1.0F, 0.5F, white));
                     this.rectangleTask.addCommand(RectangleCommand.of(boxMaxX - 1.0F, boxMaxY - boxSize + 0.5F, 0.5F, boxSize - 1.0F, white));
                  }

                  if (this.showHealth.getValue()) {
                     float smoothedHealth = this.getSmoothedHealth(livingBase);
                     int healthColor = this.getHealthColor(livingBase);
                     float healthBarX = boxX - 2.0F - 0.5F;
                     float healthBarHeight = smoothedHealth * boxSize;
                     this.rectangleTask.addCommand(RectangleCommand.of(healthBarX, boxY, 0.5F, boxSize, Integer.MIN_VALUE));
                     this.rectangleTask.addCommand(RectangleCommand.of(healthBarX, boxY, 2.0F, 0.5F, Integer.MIN_VALUE));
                     this.rectangleTask.addCommand(RectangleCommand.of(healthBarX + 2.0F - 0.5F, boxY, 0.5F, boxSize, Integer.MIN_VALUE));
                     this.rectangleTask.addCommand(RectangleCommand.of(healthBarX, boxY + boxSize - 0.5F, 2.0F, 0.5F, Integer.MIN_VALUE));
                     this.rectangleTask.addCommand(RectangleCommand.of(healthBarX, boxY, 2.0F, boxSize, Integer.MIN_VALUE));
                     this.rectangleTask.addCommand(RectangleCommand.of(healthBarX + 0.5F, boxY + 0.5F + (boxSize - healthBarHeight), 1.0F, healthBarHeight - 1.0F, healthColor));
                  }

                  if (this.showName.getValue()) {
                     fontRenderer.drawWithShadow(
                        this.textTask,
                        entity.getName(),
                        6.0F,
                        boxX + boxSize / 2.0F - fontRenderer.getTextWidth(entity.getName(), 6.0F) / 2.0F,
                        boxY - fontRenderer.getLineHeight(6.0F),
                        -1
                     );
                  }

                  if (this.showDistance.getValue()) {
                     String distanceText = Math.round(mc.thePlayer.getDistanceToEntity(entity)) + "m";
                     fontRenderer.drawWithShadow(this.textTask, distanceText, 6.0F, boxX + boxSize / 2.0F - fontRenderer.getTextWidth(distanceText, 6.0F) / 2.0F, boxY + boxSize, -1);
                  }
               }
            }
         }

         if (this.triangleTask != null) {
            GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
            this.triangleTask.render();
            GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
         }

         if (this.rectangleTask != null) {
            this.rectangleTask.render();
         }

         if (this.textTask != null) {
            this.textTask.render();
         }

         this.needsRebuild = false;
      }
   }

   private Vector3f calculateScreenPosition(Vector3f vector) {
      ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
      float scaledWidth = scaledResolution.Method237();
      float scaledHeight = scaledResolution.Method238();
      float scaledRadius = this.radius.getValue() * (scaledHeight / 480.0F);
      Vector3f playerYawVec = new Vector3f(0.0F, 360.0F - MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw), 0.0F);
      Vector3f forwardVec = new Vector3f(0.0F, 0.0F, 1.0F);
      Vector3f dirVec = this.toDirectionVector(playerYawVec);
      dirVec.setZ(0.0F);
      dirVec = this.normalize(dirVec);
      Vector3f rightVec = this.crossProduct(forwardVec, dirVec);
      float dotForward = this.dotProduct(vector, dirVec);
      float dotRight = this.dotProduct(vector, rightVec);
      Vector2f screenOffset = new Vector2f(scaledRadius * -dotRight, scaledRadius * -dotForward);
      float angleDeg = (float)Math.toDegrees(Math.atan2(screenOffset.x, screenOffset.y) + Math.PI);
      float angleRad = (float)(-Math.toRadians(angleDeg));
      float sin = (float)Math.sin(angleRad) * (this.stretch.getValue() ? 2 : 1);
      float cos = (float)Math.cos(angleRad);
      screenOffset.setX(scaledWidth / 2.0F + scaledRadius * sin);
      screenOffset.setY(scaledHeight / 2.0F - scaledRadius * cos);
      return new Vector3f(screenOffset.x, screenOffset.y, -angleDeg);
   }

   private float dotProduct(Vector3f first, Vector3f second) {
      return first.x * second.x + first.y * second.y + first.z * second.z;
   }

   private Vector3f crossProduct(Vector3f first, Vector3f second) {
      return new Vector3f(first.y * second.z - first.z * second.y, first.z * second.x - first.x * second.z, first.x * second.y - first.y * second.x);
   }

   private Vector3f toDirectionVector(Vector3f vector) {
      float pitchRad = (float)Math.toRadians(vector.getX());
      float yawRad = (float)Math.toRadians(vector.getY());
      float cosPitch = (float)Math.cos(pitchRad);
      float sinPitch = (float)Math.sin(pitchRad);
      float cosYaw = (float)Math.cos(yawRad);
      float sinYaw = (float)Math.sin(yawRad);
      return new Vector3f(cosPitch * cosYaw, cosPitch * sinYaw, -sinPitch);
   }

   private Vector3f normalize(Vector3f vector) {
      float length = this.getLength(vector);
      return new Vector3f(vector.x / length, vector.y / length, vector.z / length);
   }

   private float getLength(Vector3f vector) {
      return (float)Math.sqrt(vector.x * vector.x + vector.y * vector.y + vector.z * vector.z);
   }

   private int getHealthColor(EntityLivingBase entity) {
      return Color.HSBtoRGB(Math.max(0.0F, Math.min(entity.getHealth(), entity.getMaxHealth()) / entity.getMaxHealth()) / 3.0F, 1.0F, 1.0F) | 0xFF000000;
   }

   private float getSmoothedHealth(EntityLivingBase entity) {
      Double cachedHealth = (Double)this.healthCache.getIfPresent(entity);
      double currentHealth = Math.min(entity.getHealth() / entity.getMaxHealth(), 1.0F);
      if (cachedHealth == null) {
         cachedHealth = currentHealth;
      }

      double smoothed = LegacyGlUtil.lerp(currentHealth, cachedHealth, 0.1F);
      this.healthCache.put(entity, smoothed);
      return (float)smoothed;
   }

   private int getDistanceColor(EntityLivingBase entity) {
      float distance = mc.thePlayer.getDistanceToEntity(entity);
      float fraction = Math.max(0.0F, Math.min(distance, 40.0F) / 40.0F);
      return new Color(Color.HSBtoRGB(fraction / 3.0F, 1.0F, 1.0F) | 0xFF000000).getRGB();
   }

   private float calculateYawToEntity(EntityLivingBase entity) {
      double diffX = LegacyGlUtil.lerp(entity.posX, entity.lastTickPosX, mc.timer.renderPartialTicks)
         - LegacyGlUtil.lerp(mc.thePlayer.posX, mc.thePlayer.lastTickPosX, mc.timer.renderPartialTicks);
      double diffZ = LegacyGlUtil.lerp(entity.posZ, entity.lastTickPosZ, mc.timer.renderPartialTicks)
         - LegacyGlUtil.lerp(mc.thePlayer.posZ, mc.thePlayer.lastTickPosZ, mc.timer.renderPartialTicks);
      return (float)(-(Math.atan2(diffX, diffZ) * 180.0 / Math.PI));
   }

   public boolean isValidTarget(Entity entity) {
      if (entity == mc.thePlayer) {
         return false;
      } else if (entity.getEntityId() == -84 || entity.getEntityId() == -42) {
         return false;
      } else if (entity.isInvisible() && !this.targets.hasChecked(Pointers.Targets.INVISIBLE)) {
         return false;
      } else if (entity instanceof EntityMob && this.targets.hasChecked(Pointers.Targets.MOB)) {
         return true;
      } else {
         return (entity instanceof EntityAnimal || entity instanceof EntityVillager || entity instanceof EntitySquid)
               && this.targets.hasChecked(Pointers.Targets.ANIMAL)
            ? true
            : entity instanceof EntityPlayer && this.targets.hasChecked(Pointers.Targets.PLAYER);
      }
   }

   private int getTargetFps() {
      return Math.min(Math.max(Minecraft.getDebugFPS() / 3, Display.getDesktopDisplayMode().getFrequency()), 240);
   }

   private Vector2f rotatePoint(Vector2f center, Vector2f point, float angle) {
      double radians = Math.toRadians(angle);
      float cos = (float)Math.cos(radians);
      float sin = (float)Math.sin(radians);
      return new Vector2f(
         center.x + (point.x - center.x) * cos - (point.y - center.y) * sin, center.y + (point.x - center.x) * sin + (point.y - center.y) * cos
      );
   }

   public enum Targets implements LabelEnum {
      PLAYER("Players"),
      ANIMAL("Animals"),
      MOB("Mobs"),
      INVISIBLE("Invisibles");

      private final String label;

      @Override
      public String label() {
         return this.label;
      }

      Targets(String label) {
         this.label = label;
      }
   }
}
