package net.minecraft.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

public interface IWorldAccess {
   void markBlockForUpdate(BlockPos blockPos);

   void notifyLightSet(BlockPos blockPos);

   void markBlockRangeForRenderUpdate(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6);

   void playSound(String string, double double2, double double3, double double4, float float5, float float6);

   void playSoundToNearExcept(EntityPlayer entityPlayer, String string, double double3, double double4, double double5, float float6, float float7);

   void spawnParticle(int integer, boolean boolean2, double double3, double double4, double double5, double double6, double double7, double double8, int... arr);

   void onEntityAdded(Entity entity);

   void onEntityRemoved(Entity entity);

   void playRecord(String string, BlockPos blockPos);

   void broadcastSound(int integer1, BlockPos blockPos, int integer3);

   void playAuxSFX(EntityPlayer entityPlayer, int integer2, BlockPos blockPos, int integer4);

   void sendBlockBreakProgress(int integer1, BlockPos blockPos, int integer3);
}
