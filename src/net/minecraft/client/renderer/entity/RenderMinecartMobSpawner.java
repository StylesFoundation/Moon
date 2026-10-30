package net.minecraft.client.renderer.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntityMobSpawnerRenderer;
import net.minecraft.entity.ai.EntityMinecartMobSpawner;
import net.minecraft.init.Blocks;

public class RenderMinecartMobSpawner extends RenderMinecart<EntityMinecartMobSpawner> {
   public RenderMinecartMobSpawner(RenderManager renderManager) {
      super(renderManager);
   }

   protected void func_180560_a(EntityMinecartMobSpawner entityMinecartMobSpawner, float float2, IBlockState iBlockState) {
      super.func_180560_a(entityMinecartMobSpawner, float2, iBlockState);
      if (iBlockState.getBlock() == Blocks.mob_spawner) {
         TileEntityMobSpawnerRenderer.renderMob(
            entityMinecartMobSpawner.func_98039_d(), entityMinecartMobSpawner.posX, entityMinecartMobSpawner.posY, entityMinecartMobSpawner.posZ, float2
         );
      }
   }
}
