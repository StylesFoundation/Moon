package com.moon.module.impl.player;

import com.moon.events.impl.player.UpdateEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.FloatSetting;
import com.moon.util.math.Vector3f;
import com.moon.viamcp.ViaMCP;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class AntiFireball extends Module {
   public static float RADIANS_TO_DEGREES = 180.0F / (float)Math.PI;
   private final FloatSetting range = FloatSetting.builder("range").defaults("Range", 5.0F, 3.0F, 7.0F, 0.1F).build();
   private final BooleanSetting rotate = BooleanSetting.builder("rotate").defaults("Rotate", true).build();
   private final List<Entity> attackedFireballs = new ArrayList<>();

   public AntiFireball(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      if (event.getSequence() == UpdateEvent.Sequence.PRE) {
         if (mc.thePlayer == null || mc.theWorld == null) {
            return;
         }

         if (mc.thePlayer.ticksExisted <= 1) {
            this.attackedFireballs.clear();
         }

         Entity targetFireball = mc.theWorld
            .loadedEntityList
            .stream()
            .filter(entity -> entity instanceof EntityFireball)
            .filter(entity -> !this.attackedFireballs.contains(entity))
            .filter(entity -> entity.getDistanceToEntity(mc.thePlayer) < this.range.getValue())
            .min(Comparator.comparingDouble(entity -> entity.getDistanceToEntity(mc.thePlayer)))
            .orElse(null);
         if (targetFireball == null) {
            return;
         }

         if (this.rotate.getValue()) {
            EntityPlayerSP player = mc.thePlayer;
            Vector3f eyePos = new Vector3f((float)player.posX, (float)player.posY + player.getEyeHeight(), (float)player.posZ);
            Vec3 targetPoint = mc.thePlayer.getPositionEyes(1.0F);
            AxisAlignedBB targetBox = new AxisAlignedBB(targetFireball.posX - 0.4F, targetFireball.posY, targetFireball.posZ - 0.4F, targetFireball.posX + 0.4F, targetFireball.posY + targetFireball.height, targetFireball.posZ + 0.4F);
            if (targetPoint.xCoord > targetBox.maxX) {
               targetPoint.xCoord = targetBox.maxX;
            } else {
               targetPoint.xCoord = Math.max(targetPoint.xCoord, targetBox.minX);
            }

            if (targetPoint.yCoord > targetBox.maxY) {
               targetPoint.yCoord = targetBox.maxY;
            } else {
               targetPoint.yCoord = Math.max(targetPoint.yCoord, targetBox.minY);
            }

            if (targetPoint.zCoord > targetBox.maxZ) {
               targetPoint.zCoord = targetBox.maxZ;
            } else {
               targetPoint.zCoord = Math.max(targetPoint.zCoord, targetBox.minZ);
            }

            Vector3f diff = new Vector3f((float)targetPoint.xCoord, (float)targetPoint.yCoord, (float)targetPoint.zCoord).sub(eyePos);
            float horizontalDist = MathHelper.sqrt_float(diff.Method519() * diff.Method519() + diff.Method521() * diff.Method521());
            float yaw = MathHelper.Method565(diff.Method521(), diff.Method519()) * RADIANS_TO_DEGREES - 90.0F;
            float pitch = -MathHelper.Method565(diff.Method520(), horizontalDist) * RADIANS_TO_DEGREES;
            event.setYaw(yaw);
            event.setPitch(pitch);
         }

         if (ViaMCP.getInstance().getVersion() <= 47) {
            mc.thePlayer.swingItem();
         }

         mc.playerController.attackEntity(mc.thePlayer, targetFireball);
         if (ViaMCP.getInstance().getVersion() > 47) {
            mc.thePlayer.swingItem();
         }
      }
   }
}
