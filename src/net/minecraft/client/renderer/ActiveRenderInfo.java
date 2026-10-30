package net.minecraft.client.renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class ActiveRenderInfo {
   public static final IntBuffer VIEWPORT = GLAllocation.createDirectIntBuffer(16);
   public static final FloatBuffer MODELVIEW = GLAllocation.createDirectFloatBuffer(16);
   public static final FloatBuffer PROJECTION = GLAllocation.createDirectFloatBuffer(16);
   public static final FloatBuffer OBJECTCOORDS = GLAllocation.createDirectFloatBuffer(3);
   private static Vec3 position = new Vec3(0.0, 0.0, 0.0);
   private static float rotationX;
   private static float rotationXZ;
   private static float rotationZ;
   private static float rotationYZ;
   private static float rotationXY;

   public static void updateRenderInfo(EntityPlayer entityPlayer, boolean boolean2) {
      GlStateManager.getFloat(2982, MODELVIEW);
      GlStateManager.getFloat(2983, PROJECTION);
      GL11.glGetInteger(2978, VIEWPORT);
      float var4 = (VIEWPORT.get(0) + VIEWPORT.get(2)) / 2;
      float var5 = (VIEWPORT.get(1) + VIEWPORT.get(3)) / 2;
      GLU.gluUnProject(var4, var5, 0.0F, MODELVIEW, PROJECTION, VIEWPORT, OBJECTCOORDS);
      position = new Vec3(OBJECTCOORDS.get(0), OBJECTCOORDS.get(1), OBJECTCOORDS.get(2));
      int var6 = boolean2 ? 1 : 0;
      float var7 = entityPlayer.rotationPitch;
      float var8 = entityPlayer.rotationYaw;
      rotationX = MathHelper.cos(var8 * (float) Math.PI / 180.0F) * (1 - var6 * 2);
      rotationZ = MathHelper.sin(var8 * (float) Math.PI / 180.0F) * (1 - var6 * 2);
      rotationYZ = -rotationZ * MathHelper.sin(var7 * (float) Math.PI / 180.0F) * (1 - var6 * 2);
      rotationXY = rotationX * MathHelper.sin(var7 * (float) Math.PI / 180.0F) * (1 - var6 * 2);
      rotationXZ = MathHelper.cos(var7 * (float) Math.PI / 180.0F);
   }

   public static Vec3 projectViewFromEntity(Entity entity, double double2) {
      double var3 = entity.prevPosX + (entity.posX - entity.prevPosX) * double2;
      double var5 = entity.prevPosY + (entity.posY - entity.prevPosY) * double2;
      double var7 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * double2;
      double var9 = var3 + position.xCoord;
      double var11 = var5 + position.yCoord;
      double var13 = var7 + position.zCoord;
      return new Vec3(var9, var11, var13);
   }

   public static Block getBlockAtEntityViewpoint(World world, Entity entity, float float3) {
      Vec3 var3 = projectViewFromEntity(entity, float3);
      BlockPos var4 = new BlockPos(var3);
      IBlockState var5 = world.getBlockState(var4);
      Block var6 = var5.getBlock();
      if (var6.getMaterial().isLiquid()) {
         float var7 = 0.0F;
         if (var5.getBlock() instanceof BlockLiquid) {
            var7 = BlockLiquid.getLiquidHeightPercent(var5.getValue(BlockLiquid.LEVEL)) - 0.11111111F;
         }

         float var8 = var4.getY() + 1 - var7;
         if (var3.yCoord >= var8) {
            var6 = world.getBlockState(var4.up()).getBlock();
         }
      }

      return var6;
   }

   public static Vec3 getPosition() {
      return position;
   }

   public static float Method339() {
      return rotationX;
   }

   public static float Method340() {
      return rotationXZ;
   }

   public static float Method341() {
      return rotationZ;
   }

   public static float Method342() {
      return rotationYZ;
   }

   public static float Method343() {
      return rotationXY;
   }
}
