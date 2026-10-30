package net.optifine.util;

import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.src.Config;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

public class IntegratedServerUtils {
   public static WorldServer getWorldServer() {
      Minecraft var3 = Config.getMinecraft();
      WorldClient var4 = var3.theWorld;
      if (var4 == null) {
         return null;
      }

      if (!var3.isIntegratedServerRunning()) {
         return null;
      }

      IntegratedServer var5 = var3.getIntegratedServer();
      if (var5 == null) {
         return null;
      }

      WorldProvider var6 = var4.provider;
      if (var6 == null) {
         return null;
      }

      int var7 = var6.getDimensionId();

      try {
         return var5.worldServerForDimension(var7);
      } catch (NullPointerException var8) {
         return null;
      }
   }

   public static Entity getEntity(UUID uUID) {
      WorldServer var4 = getWorldServer();
      return var4 == null ? null : var4.getEntityFromUuid(uUID);
   }

   public static TileEntity getTileEntity(BlockPos blockPos) {
      WorldServer var4 = getWorldServer();
      if (var4 == null) {
         return null;
      }

      Chunk var5 = var4.getChunkProvider().provideChunk(blockPos.getX() >> 4, blockPos.getZ() >> 4);
      return var5 == null ? null : var5.getTileEntity(blockPos, Chunk.EnumCreateEntityType.CHECK);
   }
}
