package net.minecraft.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
import net.minecraft.network.play.server.S28PacketEffect;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class WorldManager implements IWorldAccess {
   private final MinecraftServer mcServer;
   private final WorldServer theWorldServer;

   public WorldManager(MinecraftServer minecraftServer, WorldServer worldServer) {
      this.mcServer = minecraftServer;
      this.theWorldServer = worldServer;
   }

   @Override
   public void spawnParticle(
      int integer, boolean boolean2, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
   ) {
   }

   @Override
   public void onEntityAdded(Entity entity) {
      this.theWorldServer.getEntityTracker().trackEntity(entity);
   }

   @Override
   public void onEntityRemoved(Entity entity) {
      this.theWorldServer.getEntityTracker().untrackEntity(entity);
      this.theWorldServer.getScoreboard().func_181140_a(entity);
   }

   @Override
   public void playSound(String string, double double2, double double3, double double4, float float5, float float6) {
      this.mcServer
         .getConfigurationManager()
         .sendToAllNear(
            double2,
            double3,
            double4,
            float5 > 1.0F ? 16.0F * float5 : 16.0,
            this.theWorldServer.provider.getDimensionId(),
            new S29PacketSoundEffect(string, double2, double3, double4, float5, float6)
         );
   }

   @Override
   public void playSoundToNearExcept(EntityPlayer entityPlayer, String string, double double3, double double4, double double5, float float6, float float7) {
      this.mcServer
         .getConfigurationManager()
         .sendToAllNearExcept(
            entityPlayer,
            double3,
            double4,
            double5,
            float6 > 1.0F ? 16.0F * float6 : 16.0,
            this.theWorldServer.provider.getDimensionId(),
            new S29PacketSoundEffect(string, double3, double4, double5, float6, float7)
         );
   }

   @Override
   public void markBlockRangeForRenderUpdate(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
   }

   @Override
   public void markBlockForUpdate(BlockPos blockPos) {
      this.theWorldServer.getPlayerManager().markBlockForUpdate(blockPos);
   }

   @Override
   public void notifyLightSet(BlockPos blockPos) {
   }

   @Override
   public void playRecord(String string, BlockPos blockPos) {
   }

   @Override
   public void playAuxSFX(EntityPlayer entityPlayer, int integer2, BlockPos blockPos, int integer4) {
      this.mcServer
         .getConfigurationManager()
         .sendToAllNearExcept(
            entityPlayer,
            blockPos.getX(),
            blockPos.getY(),
            blockPos.getZ(),
            64.0,
            this.theWorldServer.provider.getDimensionId(),
            new S28PacketEffect(integer2, blockPos, integer4, false)
         );
   }

   @Override
   public void broadcastSound(int integer1, BlockPos blockPos, int integer3) {
      this.mcServer.getConfigurationManager().sendPacketToAllPlayers(new S28PacketEffect(integer1, blockPos, integer3, true));
   }

   @Override
   public void sendBlockBreakProgress(int integer1, BlockPos blockPos, int integer3) {
      for (EntityPlayerMP var8 : this.mcServer.getConfigurationManager().getPlayerList()) {
         if (var8 != null && var8.worldObj == this.theWorldServer && var8.getEntityId() != integer1) {
            double var9 = blockPos.getX() - var8.posX;
            double var11 = blockPos.getY() - var8.posY;
            double var13 = blockPos.getZ() - var8.posZ;
            if (var9 * var9 + var11 * var11 + var13 * var13 < 1024.0) {
               var8.playerNetServerHandler.sendPacket(new S25PacketBlockBreakAnim(integer1, blockPos, integer3));
            }
         }
      }
   }
}
