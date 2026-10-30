package net.minecraft.client.particle;

import net.minecraft.world.World;

public interface IParticleFactory {
   EntityFX getEntityFX(int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr);
}
