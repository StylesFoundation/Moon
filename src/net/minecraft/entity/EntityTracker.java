package net.minecraft.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.network.Packet;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityTracker {
   private static final Logger logger = LogManager.getLogger();
   private final WorldServer theWorld;
   private final Set<EntityTrackerEntry> trackedEntities = Sets.newHashSet();
   private final IntHashMap<EntityTrackerEntry> trackedEntityHashTable = new IntHashMap<>();
   private final int maxTrackingDistanceThreshold;

   public EntityTracker(WorldServer worldServer) {
      this.theWorld = worldServer;
      this.maxTrackingDistanceThreshold = worldServer.getMinecraftServer().getConfigurationManager().getEntityViewDistance();
   }

   public void trackEntity(Entity entity) {
      if (entity instanceof EntityPlayerMP var5) {
         this.trackEntity(entity, 512, 2);

         for (EntityTrackerEntry var7 : this.trackedEntities) {
            if (var7.trackedEntity != var5) {
               var7.updatePlayerEntity(var5);
            }
         }
      } else if (entity instanceof EntityFishHook) {
         this.addEntityToTracker(entity, 64, 5, true);
      } else if (entity instanceof EntityArrow) {
         this.addEntityToTracker(entity, 64, 20, false);
      } else if (entity instanceof EntitySmallFireball) {
         this.addEntityToTracker(entity, 64, 10, false);
      } else if (entity instanceof EntityFireball) {
         this.addEntityToTracker(entity, 64, 10, false);
      } else if (entity instanceof EntitySnowball) {
         this.addEntityToTracker(entity, 64, 10, true);
      } else if (entity instanceof EntityEnderPearl) {
         this.addEntityToTracker(entity, 64, 10, true);
      } else if (entity instanceof EntityEnderEye) {
         this.addEntityToTracker(entity, 64, 4, true);
      } else if (entity instanceof EntityEgg) {
         this.addEntityToTracker(entity, 64, 10, true);
      } else if (entity instanceof EntityPotion) {
         this.addEntityToTracker(entity, 64, 10, true);
      } else if (entity instanceof EntityExpBottle) {
         this.addEntityToTracker(entity, 64, 10, true);
      } else if (entity instanceof EntityFireworkRocket) {
         this.addEntityToTracker(entity, 64, 10, true);
      } else if (entity instanceof EntityItem) {
         this.addEntityToTracker(entity, 64, 20, true);
      } else if (entity instanceof EntityMinecart) {
         this.addEntityToTracker(entity, 80, 3, true);
      } else if (entity instanceof EntityBoat) {
         this.addEntityToTracker(entity, 80, 3, true);
      } else if (entity instanceof EntitySquid) {
         this.addEntityToTracker(entity, 64, 3, true);
      } else if (entity instanceof EntityWither) {
         this.addEntityToTracker(entity, 80, 3, false);
      } else if (entity instanceof EntityBat) {
         this.addEntityToTracker(entity, 80, 3, false);
      } else if (entity instanceof EntityDragon) {
         this.addEntityToTracker(entity, 160, 3, true);
      } else if (entity instanceof IAnimals) {
         this.addEntityToTracker(entity, 80, 3, true);
      } else if (entity instanceof EntityTNTPrimed) {
         this.addEntityToTracker(entity, 160, 10, true);
      } else if (entity instanceof EntityFallingBlock) {
         this.addEntityToTracker(entity, 160, 20, true);
      } else if (entity instanceof EntityHanging) {
         this.addEntityToTracker(entity, 160, Integer.MAX_VALUE, false);
      } else if (entity instanceof EntityArmorStand) {
         this.addEntityToTracker(entity, 160, 3, true);
      } else if (entity instanceof EntityXPOrb) {
         this.addEntityToTracker(entity, 160, 20, true);
      } else if (entity instanceof EntityEnderCrystal) {
         this.addEntityToTracker(entity, 256, Integer.MAX_VALUE, false);
      }
   }

   public void trackEntity(Entity entity, int integer2, int integer3) {
      this.addEntityToTracker(entity, integer2, integer3, false);
   }

   public void addEntityToTracker(Entity entity, int integer2, int integer3, boolean boolean4) {
      if (integer2 > this.maxTrackingDistanceThreshold) {
         integer2 = this.maxTrackingDistanceThreshold;
      }

      try {
         if (this.trackedEntityHashTable.containsItem(entity.getEntityId())) {
            throw new IllegalStateException("Entity is already tracked!");
         }

         EntityTrackerEntry var8 = new EntityTrackerEntry(entity, integer2, integer3, boolean4);
         this.trackedEntities.add(var8);
         this.trackedEntityHashTable.addKey(entity.getEntityId(), var8);
         var8.updatePlayerEntities(this.theWorld.playerEntities);
      } catch (Throwable var14) {
         CrashReport var9 = CrashReport.makeCrashReport(var14, "Adding entity to track");
         CrashReportCategory var10 = var9.makeCategory("Entity To Track");
         var10.addCrashSection("Tracking range", integer2 + " blocks");
         var10.addCrashSectionCallable("Update interval", () -> {
            String var3 = "Once per " + integer3 + " ticks";
            if (integer3 == Integer.MAX_VALUE) {
               var3 = "Maximum (" + var3 + ")";
            }

            return var3;
         });
         entity.addEntityCrashInfo(var10);
         CrashReportCategory var11 = var9.makeCategory("Entity That Is Already Tracked");
         this.trackedEntityHashTable.lookup(entity.getEntityId()).trackedEntity.addEntityCrashInfo(var11);

         try {
            throw new ReportedException(var9);
         } catch (ReportedException var13) {
            logger.error("\"Silently\" catching entity tracking error.", var13);
         }
      }
   }

   public void untrackEntity(Entity entity) {
      if (entity instanceof EntityPlayerMP var5) {
         for (EntityTrackerEntry var7 : this.trackedEntities) {
            var7.removeFromTrackedPlayers(var5);
         }
      }

      EntityTrackerEntry var8 = this.trackedEntityHashTable.removeObject(entity.getEntityId());
      if (var8 != null) {
         this.trackedEntities.remove(var8);
         var8.sendDestroyEntityPacketToTrackedPlayers();
      }
   }

   public void updateTrackedEntities() {
      ArrayList var4 = Lists.newArrayList();

      for (EntityTrackerEntry var6 : this.trackedEntities) {
         var6.updatePlayerList(this.theWorld.playerEntities);
         if (var6.playerEntitiesUpdated && var6.trackedEntity instanceof EntityPlayerMP) {
            var4.add((EntityPlayerMP)var6.trackedEntity);
         }
      }

      for (int var9 = 0; var9 < var4.size(); var9++) {
         EntityPlayerMP var10 = (EntityPlayerMP)var4.get(var9);

         for (EntityTrackerEntry var8 : this.trackedEntities) {
            if (var8.trackedEntity != var10) {
               var8.updatePlayerEntity(var10);
            }
         }
      }
   }

   public void func_180245_a(EntityPlayerMP entityPlayerMP) {
      for (EntityTrackerEntry var6 : this.trackedEntities) {
         if (var6.trackedEntity == entityPlayerMP) {
            var6.updatePlayerEntities(this.theWorld.playerEntities);
         } else {
            var6.updatePlayerEntity(entityPlayerMP);
         }
      }
   }

   public void sendToAllTrackingEntity(Entity entity, Packet packet) {
      EntityTrackerEntry var6 = this.trackedEntityHashTable.lookup(entity.getEntityId());
      if (var6 != null) {
         var6.sendPacketToTrackedPlayers(packet);
      }
   }

   public void func_151248_b(Entity entity, Packet packet) {
      EntityTrackerEntry var6 = this.trackedEntityHashTable.lookup(entity.getEntityId());
      if (var6 != null) {
         var6.func_151261_b(packet);
      }
   }

   public void removePlayerFromTrackers(EntityPlayerMP entityPlayerMP) {
      Iterator var5 = this.trackedEntities.iterator();

      while (var5.hasNext()) {
         ((EntityTrackerEntry)var5.next()).removeTrackedPlayerSymmetric(entityPlayerMP);
      }
   }

   public void func_85172_a(EntityPlayerMP entityPlayerMP, Chunk chunk) {
      for (EntityTrackerEntry var7 : this.trackedEntities) {
         if (var7.trackedEntity != entityPlayerMP && var7.trackedEntity.W == chunk.xPosition && var7.trackedEntity.Y == chunk.zPosition) {
            var7.updatePlayerEntity(entityPlayerMP);
         }
      }
   }
}
