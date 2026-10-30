package com.moon.module.impl.visual;

import com.moon.events.impl.render.ShaderEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.setting.impl.ColorSetting;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.vertex.VertexDataBuffer;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vector3d;
import org.lwjgl.opengl.GL11;

public class Trajectories extends Module {
   private ColorSetting color = ColorSetting.builder("color").defaults("Color", -1).build();

   public Trajectories(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onShaderRender(ShaderEvent event) {
      EntityPlayerSP player = mc.thePlayer;
      if (mc.thePlayer.getCurrentEquippedItem() != null && this.isThrowable(mc.thePlayer.getCurrentEquippedItem().getItem())) {
         double renderPosX = mc.getRenderManager().Method588();
         double renderPosY = mc.getRenderManager().Method589();
         double renderPosZ = mc.getRenderManager().Method590();
         GL11.glEnable(GL11.GL_LINE_SMOOTH);
         RenderTask lineTask = RenderTask.builder().withRenderType(RenderType.WORLD).withActiveRenderMatrices().withDrawMode(3).build();
         RenderTask markerTask = RenderTask.builder().withRenderType(RenderType.WORLD).withActiveRenderMatrices().withDrawMode(3).build();
         ItemStack stack = mc.thePlayer.getCurrentEquippedItem();
         float yaw = mc.thePlayer.rotationYaw;
         float pitch = mc.thePlayer.rotationPitch;
         double posX = player.lastTickPosX
            + (player.posX - player.lastTickPosX) * mc.timer.renderPartialTicks
            - MathHelper.cos((float)Math.toRadians(yaw)) * 0.16F;
         double posY = player.lastTickPosY + (player.posY - player.lastTickPosY) * mc.timer.renderPartialTicks + player.getEyeHeight() - 0.100149011612;
         double posZ = player.lastTickPosZ
            + (player.posZ - player.lastTickPosZ) * mc.timer.renderPartialTicks
            - MathHelper.sin((float)Math.toRadians(yaw)) * 0.16F;
         float motionFactor = 1.0F;
         if (!(player.inventory.getCurrentItem().getItem() instanceof ItemBow)) {
            motionFactor = 0.4F;
         }

         double motionX = -MathHelper.sin((float)Math.toRadians(yaw)) * MathHelper.cos((float)Math.toRadians(pitch)) * motionFactor;
         double motionZ = MathHelper.cos((float)Math.toRadians(yaw)) * MathHelper.cos((float)Math.toRadians(pitch)) * motionFactor;
         double motionY = -MathHelper.sin((float)Math.toRadians(pitch)) * motionFactor;
         double magnitude = Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
         motionX /= magnitude;
         motionY /= magnitude;
         motionZ /= magnitude;
         float throwStrength = this.getThrowStrength(stack, mc.thePlayer);
         motionX *= throwStrength;
         motionY *= throwStrength;
         motionZ *= throwStrength;
         double gravity = this.getGravity(player.inventory.getCurrentItem().getItem());
         Vec3 eyePos = new Vec3(player.posX, player.posY + player.getEyeHeight(), player.posZ);
         Vector3d hitPoint = null;
         MovingObjectPosition hitResult = null;
         boolean hitEntity = false;
         boolean hasHit = false;
         VertexDataBuffer vertexData = lineTask.getState().getVertexData();
         int colorValue = this.color.getValue();

         for (int i = 0; i < 1000; i++) {
            vertexData.appendVector3(posX - renderPosX, posY - renderPosY, posZ - renderPosZ).appendColor(colorValue);
            lineTask.getState().setVertexCount(lineTask.getState().getVertexCount() + 1);
            Vec3 currentPos = new Vec3(posX, posY, posZ);
            hitResult = Minecraft.getMinecraft().theWorld.rayTraceBlocks(eyePos, currentPos);
            if (hitResult != null) {
               hitPoint = new Vector3d(posX, posY, posZ);
               hasHit = true;
            }

            AxisAlignedBB searchBox = new AxisAlignedBB(posX - 0.3F, posY - 0.3F, posZ - 0.3F, posX + 0.3F, posY + 0.3F, posZ + 0.3F)
               .addCoord(motionX, motionY, motionZ)
               .expand(1.0, 1.0, 1.0);
            int minChunkX = (int)Math.floor((searchBox.minX - 2.0) / 16.0);
            int maxChunkX = (int)Math.floor((searchBox.maxX + 2.0) / 16.0);
            int minChunkZ = (int)Math.floor((searchBox.minZ - 2.0) / 16.0);
            int maxChunkZ = (int)Math.floor((searchBox.maxZ + 2.0) / 16.0);
            ArrayList<Entity> entities = new ArrayList<>();

            for (int chunkX = minChunkX; chunkX <= maxChunkX; chunkX++) {
               for (int chunkZ = minChunkZ; chunkZ <= maxChunkZ; chunkZ++) {
                  mc.theWorld.getChunkFromChunkCoords(chunkX, chunkZ).getEntitiesWithinAABBForEntity(mc.thePlayer, searchBox, entities, null);
               }
            }

            for (Entity entity : entities) {
               if (entity.canBeCollidedWith() && entity != mc.thePlayer) {
                  MovingObjectPosition intercept = entity.getEntityBoundingBox()
                     .expand(0.3F, 0.3F, 0.3F)
                     .calculateIntercept(new Vec3(posX, posY, posZ), new Vec3(posX + motionX, posY + motionY, posZ + motionZ));
                  if (intercept != null && !(mc.thePlayer.getDistance(posX, posY, posZ) < 3.0)) {
                     hitResult = intercept;
                     hitEntity = true;
                     hasHit = true;
                     hitPoint = new Vector3d(posX, posY, posZ);
                     break;
                  }
               }
            }

            posX += motionX;
            posY += motionY;
            posZ += motionZ;
            motionX *= 0.99;
            motionY *= 0.99;
            motionZ *= 0.99;
            motionY -= gravity;
            if (hitEntity || hasHit) {
               break;
            }
         }

         if (hitPoint != null) {
            vertexData = markerTask.getState().getVertexData();
            float hitX = (float)(hitPoint.x - renderPosX);
            float hitY = (float)(hitPoint.y - renderPosY);
            float hitZ = (float)(hitPoint.z - renderPosZ);
            Vector3d outer1 = new Vector3d(hitX - 0.4F, hitY, hitZ - 0.4F);
            Vector3d outer2 = new Vector3d(hitX - 0.4F, hitY, hitZ + 0.4F);
            Vector3d outer3 = new Vector3d(hitX + 0.4F, hitY, hitZ + 0.4F);
            Vector3d outer4 = new Vector3d(hitX + 0.4F, hitY, hitZ - 0.4F);
            Vector3d inner1 = new Vector3d(hitX - 0.2F, hitY, hitZ - 0.2F);
            Vector3d inner2 = new Vector3d(hitX - 0.2F, hitY, hitZ + 0.2F);
            Vector3d inner3 = new Vector3d(hitX + 0.2F, hitY, hitZ + 0.2F);
            Vector3d inner4 = new Vector3d(hitX + 0.2F, hitY, hitZ - 0.2F);
            if (hitResult != null) {
               Vector3d center = new Vector3d(hitX, hitY, hitZ);
               switch (hitResult.sideHit.getAxis()) {
                  case X:
                     outer1 = this.rotateAroundAxis(outer1, center, 90.0F, new Vector3d(0.0, 0.0, 1.0));
                     outer2 = this.rotateAroundAxis(outer2, center, 90.0F, new Vector3d(0.0, 0.0, 1.0));
                     outer3 = this.rotateAroundAxis(outer3, center, 90.0F, new Vector3d(0.0, 0.0, 1.0));
                     outer4 = this.rotateAroundAxis(outer4, center, 90.0F, new Vector3d(0.0, 0.0, 1.0));
                     inner1 = this.rotateAroundAxis(inner1, center, 90.0F, new Vector3d(0.0, 0.0, 1.0));
                     inner2 = this.rotateAroundAxis(inner2, center, 90.0F, new Vector3d(0.0, 0.0, 1.0));
                     inner3 = this.rotateAroundAxis(inner3, center, 90.0F, new Vector3d(0.0, 0.0, 1.0));
                     inner4 = this.rotateAroundAxis(inner4, center, 90.0F, new Vector3d(0.0, 0.0, 1.0));
                     break;
                  case Z:
                     outer1 = this.rotateAroundAxis(outer1, center, 90.0F, new Vector3d(1.0, 0.0, 0.0));
                     outer2 = this.rotateAroundAxis(outer2, center, 90.0F, new Vector3d(1.0, 0.0, 0.0));
                     outer3 = this.rotateAroundAxis(outer3, center, 90.0F, new Vector3d(1.0, 0.0, 0.0));
                     outer4 = this.rotateAroundAxis(outer4, center, 90.0F, new Vector3d(1.0, 0.0, 0.0));
                     inner1 = this.rotateAroundAxis(inner1, center, 90.0F, new Vector3d(1.0, 0.0, 0.0));
                     inner2 = this.rotateAroundAxis(inner2, center, 90.0F, new Vector3d(1.0, 0.0, 0.0));
                     inner3 = this.rotateAroundAxis(inner3, center, 90.0F, new Vector3d(1.0, 0.0, 0.0));
                     inner4 = this.rotateAroundAxis(inner4, center, 90.0F, new Vector3d(1.0, 0.0, 0.0));
               }
            }

            vertexData.appendVector3(outer1.x, outer1.y, outer1.z).appendColor(colorValue);
            vertexData.appendVector3(outer2.x, outer2.y, outer2.z).appendColor(colorValue);
            vertexData.appendVector3(outer3.x, outer3.y, outer3.z).appendColor(colorValue);
            vertexData.appendVector3(outer4.x, outer4.y, outer4.z).appendColor(colorValue);
            vertexData.appendVector3(outer1.x, outer1.y, outer1.z).appendColor(colorValue);
            vertexData.appendVector3(inner1.x, inner1.y, inner1.z).appendColor(colorValue);
            vertexData.appendVector3(inner2.x, inner2.y, inner2.z).appendColor(colorValue);
            vertexData.appendVector3(inner3.x, inner3.y, inner3.z).appendColor(colorValue);
            vertexData.appendVector3(inner4.x, inner4.y, inner4.z).appendColor(colorValue);
            vertexData.appendVector3(inner1.x, inner1.y, inner1.z).appendColor(colorValue);
            vertexData.appendVector3(outer1.x, outer1.y, outer1.z).appendColor(colorValue);
            vertexData.appendVector3(outer2.x, outer2.y, outer2.z).appendColor(colorValue);
            vertexData.appendVector3(inner2.x, inner2.y, inner2.z).appendColor(colorValue);
            vertexData.appendVector3(inner3.x, inner3.y, inner3.z).appendColor(colorValue);
            vertexData.appendVector3(outer3.x, outer3.y, outer3.z).appendColor(colorValue);
            vertexData.appendVector3(outer4.x, outer4.y, outer4.z).appendColor(colorValue);
            vertexData.appendVector3(inner4.x, inner4.y, inner4.z).appendColor(colorValue);
            markerTask.getState().setVertexCount(markerTask.getState().getVertexCount() + 17);
         }

         lineTask.render();
         markerTask.render();
         GL11.glDisable(GL11.GL_LINE_SMOOTH);
      }
   }

   private float getThrowStrength(ItemStack stack, EntityPlayer player) {
      if (stack.getItem() instanceof ItemBow) {
         int duration = player.getItemInUseCount();
         float bowPower = ItemBow.Method567(Items.bow.getMaxItemUseDuration(stack) - duration);
         return 3.0F * bowPower;
      } else if (stack.getItem() instanceof ItemSnowball || stack.getItem() instanceof ItemEnderPearl || stack.getItem() instanceof ItemEgg) {
         return 1.5F;
      } else if (stack.getItem() instanceof ItemExpBottle) {
         return 0.7F;
      } else {
         return stack.getItem() instanceof ItemPotion ? 0.5F : 0.0F;
      }
   }

   private double getGravity(Item item) {
      return item instanceof ItemBow ? 0.05F : 0.03;
   }

   private boolean isThrowable(Item item) {
      if (item instanceof ItemBow && mc.thePlayer.getItemInUseCount() == 0) {
         return false;
      } else if (item instanceof ItemPotion && !ItemPotion.isSplash(mc.thePlayer.getCurrentEquippedItem().getItemDamage())) {
         return false;
      } else {
         return item instanceof ItemBow && !mc.thePlayer.inventory.hasItem(Items.arrow)
            ? false
            : item instanceof ItemBow
               || item instanceof ItemSnowball
               || item instanceof ItemEgg
               || item instanceof ItemEnderPearl
               || item instanceof ItemPotion
               || item instanceof ItemExpBottle;
      }
   }

   private Vector3d rotateAroundAxis(Vector3d point, Vector3d center, float angle, Vector3d axis) {
      double radians = Math.toRadians(angle);
      double dx = point.x - center.x;
      double dy = point.y - center.y;
      double dz = point.z - center.z;
      double crossX = axis.y * dz - axis.z * dy;
      double crossY = axis.z * dx - axis.x * dz;
      double crossZ = axis.x * dy - axis.y * dx;
      double dot = axis.x * dx + axis.y * dy + axis.z * dz;
      double rotX = dx * Math.cos(radians) + crossX * Math.sin(radians) + dot * (1.0 - Math.cos(radians)) * axis.x;
      double rotY = dy * Math.cos(radians) + crossY * Math.sin(radians) + dot * (1.0 - Math.cos(radians)) * axis.y;
      double rotZ = dz * Math.cos(radians) + crossZ * Math.sin(radians) + dot * (1.0 - Math.cos(radians)) * axis.z;
      rotX += center.x;
      rotY += center.y;
      rotZ += center.z;
      return new Vector3d(rotX, rotY, rotZ);
   }
}
