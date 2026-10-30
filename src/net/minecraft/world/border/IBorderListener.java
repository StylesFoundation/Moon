package net.minecraft.world.border;

public interface IBorderListener {
   void onSizeChanged(WorldBorder worldBorder, double double2);

   void onTransitionStarted(WorldBorder worldBorder, double double2, double double3, long long4);

   void onCenterChanged(WorldBorder worldBorder, double double2, double double3);

   void onWarningTimeChanged(WorldBorder worldBorder, int integer);

   void onWarningDistanceChanged(WorldBorder worldBorder, int integer);

   void onDamageAmountChanged(WorldBorder worldBorder, double double2);

   void onDamageBufferChanged(WorldBorder worldBorder, double double2);
}
