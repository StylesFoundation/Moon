package com.moon.module.impl.movement;

import com.moon.Moon;
import com.moon.events.impl.player.MoveEvent;
import com.moon.events.impl.player.SemiStrafeEvent;
import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.impl.render.OverlayRenderEvent;
import com.moon.events.priority.Priority;
import com.moon.events.subscription.Subscribe;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.combat.KillAura;
import com.moon.module.impl.movement.targetstrafe.StrafePoint;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.FloatSetting;
import com.moon.module.setting.impl.IntegerSetting;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.color.RenderColor;
import com.moon.render.command.impl.world.WorldBoxCommand;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;

public class TargetStrafe extends Module {
   private final FloatSetting radius = FloatSetting.builder("radius").defaults("Radius", 1.5F, 0.5F, 4.0F, 0.1F).build();
   private final IntegerSetting points = IntegerSetting.builder("points").defaults("Points", 15, 5, 45, 1).build();
   private final BooleanSetting onlyWithSpace = BooleanSetting.builder("only-space").defaults("Space required", true).build();
   private final BooleanSetting drawPoints = BooleanSetting.builder("draw-points").defaults("Draw points", true).build();
   private final List<StrafePoint> strafePoints = new ArrayList<>();
   private StrafePoint currentPoint;
   private EntityLivingBase target;
   private int direction = 1;
   private KillAura killAura;

   public TargetStrafe(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE) {
         this.resolveKillAura();
         this.target = this.killAura.getTargetSelector().getTarget();
         if (this.target != null) {
            if (mc.gameSettings.keyBindLeft.isPressed()) {
               this.direction = 1;
            }

            if (mc.gameSettings.keyBindRight.isPressed()) {
               this.direction = -1;
            }

            this.generatePoints(this.radius.getValue(), this.points.getValue(), this.target);
            this.currentPoint = this.selectNextPoint(this.strafePoints);
         }
      }
   }

   @Subscribe(priority = Priority.HIGHEST)
   public void onMove(MoveEvent event) {
      boolean spaceRequirementMet = !this.onlyWithSpace.getValue() || mc.gameSettings.keyBindJump.isKeyDown();
      if (this.target != null && this.currentPoint != null && spaceRequirementMet) {
         float strafeYaw = this.getYawTo(this.target.getPositionVector()) + 135 * -this.direction;
         double targetX = -MathHelper.sin((float)Math.toRadians(strafeYaw)) * this.radius.getValue() + this.target.posX;
         double targetZ = MathHelper.cos((float)Math.toRadians(strafeYaw)) * this.radius.getValue() + this.target.posZ;
         event.setForward(1.0F);
         event.setStrafe(0.0F);
         event.setYaw(this.getYawTo(new Vec3(targetX, this.target.posY, targetZ)));
      }
   }

   @Subscribe(priority = Priority.HIGHEST)
   public void onSemiStrafe(SemiStrafeEvent event) {
      boolean spaceRequirementMet = !this.onlyWithSpace.getValue() || mc.gameSettings.keyBindJump.isKeyDown();
      if (this.target != null && this.currentPoint != null && spaceRequirementMet) {
         float strafeYaw = this.getYawTo(this.target.getPositionVector()) + 135 * -this.direction;
         double targetX = -MathHelper.sin((float)Math.toRadians(strafeYaw)) * this.radius.getValue() + this.target.posX;
         double targetZ = MathHelper.cos((float)Math.toRadians(strafeYaw)) * this.radius.getValue() + this.target.posZ;
         event.setForward(1.0F);
         event.setStrafe(0.0F);
         event.setYaw(this.getYawTo(new Vec3(targetX, this.target.posY, targetZ)));
      }
   }

   @Subscribe
   public void onOverlayRender(OverlayRenderEvent event) {
      if (event.getSequence() == OverlayRenderEvent.Sequence.POST && this.drawPoints.getValue() && this.target != null && this.currentPoint != null) {
         GlStateManager.enableCull();
         GlStateManager.disableDepth();
         RenderTask renderTask = RenderTask.builder().withRenderType(RenderType.WORLD).withActiveRenderMatrices().build();
         double renderPosX = mc.getRenderManager().Method588();
         double renderPosY = mc.getRenderManager().Method589();
         double renderPosZ = mc.getRenderManager().Method590();

         for (StrafePoint strafePoint : this.strafePoints) {
            Vec3 pos = strafePoint.getInterpolatedPosition();
            double x = pos.xCoord;
            double y = pos.yCoord;
            double z = pos.zCoord;
            AxisAlignedBB box = new AxisAlignedBB(
               x - renderPosX - 0.02F, y - renderPosY, z - renderPosZ - 0.02F, x - renderPosX + 0.02F, y - renderPosY + 0.04F, z - renderPosZ + 0.02F
            );
            int colorRgb = !strafePoint.isValid() ? Color.RED.getRGB() : (strafePoint == this.currentPoint ? Color.GREEN.getRGB() : Color.WHITE.getRGB());
            RenderColor renderColor = new RenderColor(colorRgb);
            renderTask.addCommand(WorldBoxCommand.of(box, renderColor.withAlphaFraction(0.5F).getRGB()));
         }

         renderTask.render();
         GlStateManager.enableDepth();
      }
   }

   private void generatePoints(float radius, int points, @NotNull EntityLivingBase target) {
      this.strafePoints.clear();

      for (int i = 0; i < points; i++) {
         double x = radius * Math.cos(i * (Math.PI * 2) / points);
         double z = radius * Math.sin(i * (Math.PI * 2) / points);
         Vec3 offset = new Vec3(x, 0.0, z);
         Vec3 pointPos = target.getPositionVector().add(offset);
         StrafePoint strafePoint = new StrafePoint(target, offset, pointPos, this.isValidPoint(pointPos));
         this.strafePoints.add(strafePoint);
      }
   }

   private boolean isValidPoint(Vec3 point) {
      MovingObjectPosition raytrace = mc.theWorld.rayTraceBlocks(mc.thePlayer.getPositionVector(), point, false, true, false);
      if (raytrace != null && raytrace.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
         return false;
      }

      BlockPos blockPos = new BlockPos(point);
      IBlockState state = mc.theWorld.getBlockState(blockPos);
      if (state.getBlock().canCollideCheck(state, false) && !state.getBlock().isPassable(mc.theWorld, blockPos)) {
         return false;
      }

      BlockPos upperPos = blockPos.add(0, 1, 0);
      IBlockState upperState = mc.theWorld.getBlockState(upperPos);
      return upperState.getBlock().canCollideCheck(state, false) && !upperState.getBlock().isPassable(mc.theWorld, upperPos) ? false : !this.isOverVoid(point);
   }

   private boolean isOverVoid(Vec3 point) {
      double startY = point.yCoord;

      for (double y = startY - 0.1; y > 0.0; y--) {
         IBlockState state = mc.theWorld.getBlockState(new BlockPos(point.xCoord, Math.floor(y), point.zCoord));
         if (state.getBlock().canCollideCheck(state, false)) {
            return startY - y > 3.0;
         }
      }

      return true;
   }

   private StrafePoint selectNextPoint(List<StrafePoint> points) {
      StrafePoint closestPoint = this.getClosestPoint(points);
      if (closestPoint == null) {
         return null;
      }

      int size = points.size();
      if (size == 1) {
         return closestPoint;
      }

      int currentIndex = points.indexOf(closestPoint);
      int pointsCount = points.size();
      int attempts = 0;

      while (attempts <= pointsCount) {
         int nextIndex = currentIndex + this.direction;
         if (nextIndex < 0) {
            nextIndex = pointsCount - 1;
         } else if (nextIndex >= pointsCount) {
            nextIndex = 0;
         }

         StrafePoint candidatePoint = (StrafePoint)points.get(nextIndex);
         if (!candidatePoint.isValid()) {
            this.direction = -this.direction;
         }

         attempts++;
         if (candidatePoint.isValid()) {
            return candidatePoint;
         }
      }

      return null;
   }

   private StrafePoint getClosestPoint(List<StrafePoint> points) {
      double minDistance = 2.147483647E9;
      StrafePoint closestPoint = null;

      for (StrafePoint strafePoint : points) {
         if (strafePoint.isValid()) {
            double distance = strafePoint.getPosition().distanceTo(mc.thePlayer.getPositionVector());
            if (distance < minDistance) {
               closestPoint = strafePoint;
               minDistance = distance;
            }
         }
      }

      return closestPoint;
   }

   public float getYawTo(Vec3 point) {
      Vec3 diff = point.subtract(mc.thePlayer.getPositionVector().addVector(0.0, mc.thePlayer.getEyeHeight(), 0.0));
      return (float)(MathHelper.atan2(diff.zCoord, diff.xCoord) * (180.0 / Math.PI)) - 90.0F;
   }

   private void resolveKillAura() {
      if (this.killAura == null) {
         this.killAura = Moon.getInstance().getModuleManager().getModule(KillAura.class);
      }
   }
}
