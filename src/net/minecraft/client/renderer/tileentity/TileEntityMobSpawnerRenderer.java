package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntityMobSpawner;

public class TileEntityMobSpawnerRenderer extends TileEntitySpecialRenderer<TileEntityMobSpawner> {
   public void renderTileEntityAt(TileEntityMobSpawner tileEntityMobSpawner, double double2, double double3, double double4, float float5, int integer) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)double2 + 0.5F, (float)double3, (float)double4 + 0.5F);
      renderMob(tileEntityMobSpawner.getSpawnerBaseLogic(), double2, double3, double4, float5);
      GlStateManager.popMatrix();
   }

   public static void renderMob(MobSpawnerBaseLogic mobSpawnerBaseLogic, double double2, double double3, double double4, float float5) {
      Entity var8 = mobSpawnerBaseLogic.func_180612_a(mobSpawnerBaseLogic.getSpawnerWorld());
      if (var8 != null) {
         GlStateManager.translate(0.0F, 0.4F, 0.0F);
         GlStateManager.rotate(
            (float)(mobSpawnerBaseLogic.getPrevMobRotation() + (mobSpawnerBaseLogic.getMobRotation() - mobSpawnerBaseLogic.getPrevMobRotation()) * float5)
               * 10.0F,
            0.0F,
            1.0F,
            0.0F
         );
         GlStateManager.rotate(-30.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.translate(0.0F, -0.4F, 0.0F);
         GlStateManager.scale(0.4375F, 0.4375F, 0.4375F);
         var8.setLocationAndAngles(double2, double3, double4, 0.0F, 0.0F);
         Minecraft.getMinecraft().getRenderManager().renderEntityWithPosYaw(var8, 0.0, 0.0, 0.0, 0.0F, float5);
      }
   }
}
