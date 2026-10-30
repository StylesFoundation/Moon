package net.minecraft.entity;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.attributes.ServersideAttributeMap;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S0APacketUseBed;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S0EPacketSpawnObject;
import net.minecraft.network.play.server.S0FPacketSpawnMob;
import net.minecraft.network.play.server.S10PacketSpawnPainting;
import net.minecraft.network.play.server.S11PacketSpawnExperienceOrb;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S19PacketEntityHeadLook;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.network.play.server.S1CPacketEntityMetadata;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S20PacketEntityProperties;
import net.minecraft.network.play.server.S49PacketUpdateEntityNBT;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.storage.MapData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityTrackerEntry {
   private static final Logger logger = LogManager.getLogger();
   public Entity trackedEntity;
   public int trackingDistanceThreshold;
   public int updateFrequency;
   public int encodedPosX;
   public int encodedPosY;
   public int encodedPosZ;
   public int encodedRotationYaw;
   public int encodedRotationPitch;
   public int lastHeadMotion;
   public double lastTrackedEntityMotionX;
   public double lastTrackedEntityMotionY;
   public double motionZ;
   public int updateCounter;
   private double lastTrackedEntityPosX;
   private double lastTrackedEntityPosY;
   private double lastTrackedEntityPosZ;
   private boolean firstUpdateDone;
   private final boolean sendVelocityUpdates;
   private int ticksSinceLastForcedTeleport;
   private Entity field_85178_v;
   private boolean ridingEntity;
   private boolean onGround;
   public boolean playerEntitiesUpdated;
   public Set<EntityPlayerMP> trackingPlayers = Sets.newHashSet();

   public EntityTrackerEntry(Entity entity, int integer2, int integer3, boolean boolean4) {
      this.trackedEntity = entity;
      this.trackingDistanceThreshold = integer2;
      this.updateFrequency = integer3;
      this.sendVelocityUpdates = boolean4;
      this.encodedPosX = MathHelper.floor_double(entity.posX * 32.0);
      this.encodedPosY = MathHelper.floor_double(entity.posY * 32.0);
      this.encodedPosZ = MathHelper.floor_double(entity.posZ * 32.0);
      this.encodedRotationYaw = MathHelper.floor_float(entity.rotationYaw * 256.0F / 360.0F);
      this.encodedRotationPitch = MathHelper.floor_float(entity.rotationPitch * 256.0F / 360.0F);
      this.lastHeadMotion = MathHelper.floor_float(entity.getRotationYawHead() * 256.0F / 360.0F);
      this.onGround = entity.onGround;
   }

   @Override
   public boolean equals(Object object) {
      return object instanceof EntityTrackerEntry && ((EntityTrackerEntry)object).trackedEntity.getEntityId() == this.trackedEntity.getEntityId();
   }

   @Override
   public int hashCode() {
      return this.trackedEntity.getEntityId();
   }

   public void updatePlayerList(List<EntityPlayer> list) {
      this.playerEntitiesUpdated = false;
      if (!this.firstUpdateDone || this.trackedEntity.getDistanceSq(this.lastTrackedEntityPosX, this.lastTrackedEntityPosY, this.lastTrackedEntityPosZ) > 16.0) {
         this.lastTrackedEntityPosX = this.trackedEntity.posX;
         this.lastTrackedEntityPosY = this.trackedEntity.posY;
         this.lastTrackedEntityPosZ = this.trackedEntity.posZ;
         this.firstUpdateDone = true;
         this.playerEntitiesUpdated = true;
         this.updatePlayerEntities(list);
      }

      if (this.field_85178_v != this.trackedEntity.ridingEntity || this.trackedEntity.ridingEntity != null && this.updateCounter % 60 == 0) {
         this.field_85178_v = this.trackedEntity.ridingEntity;
         this.sendPacketToTrackedPlayers(new S1BPacketEntityAttach(0, this.trackedEntity, this.trackedEntity.ridingEntity));
      }

      if (this.trackedEntity instanceof EntityItemFrame var5 && this.updateCounter % 10 == 0) {
         ItemStack var29 = var5.getDisplayedItem();
         if (var29 != null && var29.getItem() instanceof ItemMap) {
            MapData var7 = Items.filled_map.getMapData(var29, this.trackedEntity.worldObj);

            for (EntityPlayer var10_ : list) {
               EntityPlayerMP var10 = (EntityPlayerMP)var10_;
               var7.updateVisiblePlayers(var10, var29);
               Packet var11 = Items.filled_map.createMapDataPacket(var29, this.trackedEntity.worldObj, var10);
               if (var11 != null) {
                  var10.playerNetServerHandler.sendPacket(var11);
               }
            }
         }

         this.sendMetadataToAllAssociatedPlayers();
      }

      if (this.updateCounter % this.updateFrequency == 0 || this.trackedEntity.isAirBorne || this.trackedEntity.getDataWatcher().hasObjectChanged()) {
         if (this.trackedEntity.ridingEntity == null) {
            this.ticksSinceLastForcedTeleport++;
            int var27 = MathHelper.floor_double(this.trackedEntity.posX * 32.0);
            int var31 = MathHelper.floor_double(this.trackedEntity.posY * 32.0);
            int var33 = MathHelper.floor_double(this.trackedEntity.posZ * 32.0);
            int var34 = MathHelper.floor_float(this.trackedEntity.rotationYaw * 256.0F / 360.0F);
            int var9 = MathHelper.floor_float(this.trackedEntity.rotationPitch * 256.0F / 360.0F);
            int var35 = var27 - this.encodedPosX;
            int var36 = var31 - this.encodedPosY;
            int var12 = var33 - this.encodedPosZ;
            Packet var13 = null;
            boolean var14 = Math.abs(var35) >= 4 || Math.abs(var36) >= 4 || Math.abs(var12) >= 4 || this.updateCounter % 60 == 0;
            boolean var15 = Math.abs(var34 - this.encodedRotationYaw) >= 4 || Math.abs(var9 - this.encodedRotationPitch) >= 4;
            if (this.updateCounter > 0 || this.trackedEntity instanceof EntityArrow) {
               if (var35 >= -128
                  && var35 < 128
                  && var36 >= -128
                  && var36 < 128
                  && var12 >= -128
                  && var12 < 128
                  && this.ticksSinceLastForcedTeleport <= 400
                  && !this.ridingEntity
                  && this.onGround == this.trackedEntity.onGround) {
                  if ((!var14 || !var15) && !(this.trackedEntity instanceof EntityArrow)) {
                     if (var14) {
                        var13 = new S14PacketEntity.S15PacketEntityRelMove(
                           this.trackedEntity.getEntityId(), (byte)var35, (byte)var36, (byte)var12, this.trackedEntity.onGround
                        );
                     } else if (var15) {
                        var13 = new S14PacketEntity.S16PacketEntityLook(this.trackedEntity.getEntityId(), (byte)var34, (byte)var9, this.trackedEntity.onGround);
                     }
                  } else {
                     var13 = new S14PacketEntity.S17PacketEntityLookMove(
                        this.trackedEntity.getEntityId(), (byte)var35, (byte)var36, (byte)var12, (byte)var34, (byte)var9, this.trackedEntity.onGround
                     );
                  }
               } else {
                  this.onGround = this.trackedEntity.onGround;
                  this.ticksSinceLastForcedTeleport = 0;
                  var13 = new S18PacketEntityTeleport(
                     this.trackedEntity.getEntityId(), var27, var31, var33, (byte)var34, (byte)var9, this.trackedEntity.onGround
                  );
               }
            }

            if (this.sendVelocityUpdates) {
               double var16 = this.trackedEntity.motionX - this.lastTrackedEntityMotionX;
               double var18 = this.trackedEntity.motionY - this.lastTrackedEntityMotionY;
               double var20 = this.trackedEntity.motionZ - this.motionZ;
               double var24 = var16 * var16 + var18 * var18 + var20 * var20;
               if (var24 > 4.0E-4 || var24 > 0.0 && this.trackedEntity.motionX == 0.0 && this.trackedEntity.motionY == 0.0 && this.trackedEntity.motionZ == 0.0
                  )
                {
                  this.lastTrackedEntityMotionX = this.trackedEntity.motionX;
                  this.lastTrackedEntityMotionY = this.trackedEntity.motionY;
                  this.motionZ = this.trackedEntity.motionZ;
                  this.sendPacketToTrackedPlayers(
                     new S12PacketEntityVelocity(this.trackedEntity.getEntityId(), this.lastTrackedEntityMotionX, this.lastTrackedEntityMotionY, this.motionZ)
                  );
               }
            }

            if (var13 != null) {
               this.sendPacketToTrackedPlayers(var13);
            }

            this.sendMetadataToAllAssociatedPlayers();
            if (var14) {
               this.encodedPosX = var27;
               this.encodedPosY = var31;
               this.encodedPosZ = var33;
            }

            if (var15) {
               this.encodedRotationYaw = var34;
               this.encodedRotationPitch = var9;
            }

            this.ridingEntity = false;
         } else {
            int var26 = MathHelper.floor_float(this.trackedEntity.rotationYaw * 256.0F / 360.0F);
            int var30 = MathHelper.floor_float(this.trackedEntity.rotationPitch * 256.0F / 360.0F);
            boolean var32 = Math.abs(var26 - this.encodedRotationYaw) >= 4 || Math.abs(var30 - this.encodedRotationPitch) >= 4;
            if (var32) {
               this.sendPacketToTrackedPlayers(
                  new S14PacketEntity.S16PacketEntityLook(this.trackedEntity.getEntityId(), (byte)var26, (byte)var30, this.trackedEntity.onGround)
               );
               this.encodedRotationYaw = var26;
               this.encodedRotationPitch = var30;
            }

            this.encodedPosX = MathHelper.floor_double(this.trackedEntity.posX * 32.0);
            this.encodedPosY = MathHelper.floor_double(this.trackedEntity.posY * 32.0);
            this.encodedPosZ = MathHelper.floor_double(this.trackedEntity.posZ * 32.0);
            this.sendMetadataToAllAssociatedPlayers();
            this.ridingEntity = true;
         }

         int var28 = MathHelper.floor_float(this.trackedEntity.getRotationYawHead() * 256.0F / 360.0F);
         if (Math.abs(var28 - this.lastHeadMotion) >= 4) {
            this.sendPacketToTrackedPlayers(new S19PacketEntityHeadLook(this.trackedEntity, (byte)var28));
            this.lastHeadMotion = var28;
         }

         this.trackedEntity.isAirBorne = false;
      }

      this.updateCounter++;
      if (this.trackedEntity.velocityChanged) {
         this.func_151261_b(new S12PacketEntityVelocity(this.trackedEntity));
         this.trackedEntity.velocityChanged = false;
      }
   }

   private void sendMetadataToAllAssociatedPlayers() {
      DataWatcher var4 = this.trackedEntity.getDataWatcher();
      if (var4.hasObjectChanged()) {
         this.func_151261_b(new S1CPacketEntityMetadata(this.trackedEntity.getEntityId(), var4, false));
      }

      if (this.trackedEntity instanceof EntityLivingBase) {
         Set var6 = ((ServersideAttributeMap)((EntityLivingBase)this.trackedEntity).getAttributeMap()).getAttributeInstanceSet();
         if (!var6.isEmpty()) {
            this.func_151261_b(new S20PacketEntityProperties(this.trackedEntity.getEntityId(), var6));
         }

         var6.clear();
      }
   }

   public void sendPacketToTrackedPlayers(Packet packet) {
      Iterator var5 = this.trackingPlayers.iterator();

      while (var5.hasNext()) {
         ((EntityPlayerMP)var5.next()).playerNetServerHandler.sendPacket(packet);
      }
   }

   public void func_151261_b(Packet packet) {
      this.sendPacketToTrackedPlayers(packet);
      if (this.trackedEntity instanceof EntityPlayerMP) {
         ((EntityPlayerMP)this.trackedEntity).playerNetServerHandler.sendPacket(packet);
      }
   }

   public void sendDestroyEntityPacketToTrackedPlayers() {
      Iterator var4 = this.trackingPlayers.iterator();

      while (var4.hasNext()) {
         ((EntityPlayerMP)var4.next()).removeEntity(this.trackedEntity);
      }
   }

   public void removeFromTrackedPlayers(EntityPlayerMP entityPlayerMP) {
      if (this.trackingPlayers.contains(entityPlayerMP)) {
         entityPlayerMP.removeEntity(this.trackedEntity);
         this.trackingPlayers.remove(entityPlayerMP);
      }
   }

   public void updatePlayerEntity(EntityPlayerMP entityPlayerMP) {
      if (entityPlayerMP != this.trackedEntity) {
         if (this.func_180233_c(entityPlayerMP)) {
            if (!this.trackingPlayers.contains(entityPlayerMP) && (this.isPlayerWatchingThisChunk(entityPlayerMP) || this.trackedEntity.forceSpawn)) {
               this.trackingPlayers.add(entityPlayerMP);
               Packet var5 = this.createSpawnPacket();
               entityPlayerMP.playerNetServerHandler.sendPacket(var5);
               if (!this.trackedEntity.getDataWatcher().getIsBlank()) {
                  entityPlayerMP.playerNetServerHandler
                     .sendPacket(new S1CPacketEntityMetadata(this.trackedEntity.getEntityId(), this.trackedEntity.getDataWatcher(), true));
               }

               NBTTagCompound var6 = this.trackedEntity.getNBTTagCompound();
               if (var6 != null) {
                  entityPlayerMP.playerNetServerHandler.sendPacket(new S49PacketUpdateEntityNBT(this.trackedEntity.getEntityId(), var6));
               }

               if (this.trackedEntity instanceof EntityLivingBase) {
                  Collection var8 = ((ServersideAttributeMap)((EntityLivingBase)this.trackedEntity).getAttributeMap()).getWatchedAttributes();
                  if (!var8.isEmpty()) {
                     entityPlayerMP.playerNetServerHandler.sendPacket(new S20PacketEntityProperties(this.trackedEntity.getEntityId(), var8));
                  }
               }

               this.lastTrackedEntityMotionX = this.trackedEntity.motionX;
               this.lastTrackedEntityMotionY = this.trackedEntity.motionY;
               this.motionZ = this.trackedEntity.motionZ;
               if (this.sendVelocityUpdates && !(var5 instanceof S0FPacketSpawnMob)) {
                  entityPlayerMP.playerNetServerHandler
                     .sendPacket(
                        new S12PacketEntityVelocity(
                           this.trackedEntity.getEntityId(), this.trackedEntity.motionX, this.trackedEntity.motionY, this.trackedEntity.motionZ
                        )
                     );
               }

               if (this.trackedEntity.ridingEntity != null) {
                  entityPlayerMP.playerNetServerHandler.sendPacket(new S1BPacketEntityAttach(0, this.trackedEntity, this.trackedEntity.ridingEntity));
               }

               if (this.trackedEntity instanceof EntityLiving && ((EntityLiving)this.trackedEntity).getLeashedToEntity() != null) {
                  entityPlayerMP.playerNetServerHandler
                     .sendPacket(new S1BPacketEntityAttach(1, this.trackedEntity, ((EntityLiving)this.trackedEntity).getLeashedToEntity()));
               }

               if (this.trackedEntity instanceof EntityLivingBase) {
                  for (int var7 = 0; var7 < 5; var7++) {
                     ItemStack var11 = ((EntityLivingBase)this.trackedEntity).getEquipmentInSlot(var7);
                     if (var11 != null) {
                        entityPlayerMP.playerNetServerHandler.sendPacket(new S04PacketEntityEquipment(this.trackedEntity.getEntityId(), var7, var11));
                     }
                  }
               }

               if (this.trackedEntity instanceof EntityPlayer var10 && var10.isPlayerSleeping()) {
                  entityPlayerMP.playerNetServerHandler.sendPacket(new S0APacketUseBed(var10, new BlockPos(this.trackedEntity)));
               }

               Entity var13 = this.trackedEntity;
               if (var13 instanceof EntityLivingBase) {
                  for (PotionEffect var9 : ((EntityLivingBase)var13).getActivePotionEffects()) {
                     entityPlayerMP.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(this.trackedEntity.getEntityId(), var9));
                  }
               }
            }
         } else if (this.trackingPlayers.contains(entityPlayerMP)) {
            this.trackingPlayers.remove(entityPlayerMP);
            entityPlayerMP.removeEntity(this.trackedEntity);
         }
      }
   }

   public boolean func_180233_c(EntityPlayerMP entityPlayerMP) {
      double var5 = entityPlayerMP.posX - this.encodedPosX / 32;
      double var7 = entityPlayerMP.posZ - this.encodedPosZ / 32;
      return var5 >= -this.trackingDistanceThreshold
         && var5 <= this.trackingDistanceThreshold
         && var7 >= -this.trackingDistanceThreshold
         && var7 <= this.trackingDistanceThreshold
         && this.trackedEntity.isSpectatedByPlayer(entityPlayerMP);
   }

   private boolean isPlayerWatchingThisChunk(EntityPlayerMP entityPlayerMP) {
      return entityPlayerMP.getServerForPlayer().getPlayerManager().isPlayerWatchingChunk(entityPlayerMP, this.trackedEntity.W, this.trackedEntity.Y);
   }

   public void updatePlayerEntities(List<EntityPlayer> list) {
      for (EntityPlayer var6 : list) {
         this.updatePlayerEntity((EntityPlayerMP)var6);
      }
   }

   private Packet createSpawnPacket() {
      if (this.trackedEntity.isDead) {
         logger.warn("Fetching addPacket for removed entity");
      }

      if (this.trackedEntity instanceof EntityItem) {
         return new S0EPacketSpawnObject(this.trackedEntity, 2, 1);
      } else if (this.trackedEntity instanceof EntityPlayerMP) {
         return new S0CPacketSpawnPlayer((EntityPlayer)this.trackedEntity);
      } else if (this.trackedEntity instanceof EntityMinecart var4) {
         return new S0EPacketSpawnObject(this.trackedEntity, 10, var4.getMinecartType().getNetworkID());
      } else {
         if (this.trackedEntity instanceof EntityBoat) {
            return new S0EPacketSpawnObject(this.trackedEntity, 1);
         }

         if (this.trackedEntity instanceof IAnimals) {
            this.lastHeadMotion = MathHelper.floor_float(this.trackedEntity.getRotationYawHead() * 256.0F / 360.0F);
            return new S0FPacketSpawnMob((EntityLivingBase)this.trackedEntity);
         }

         if (this.trackedEntity instanceof EntityFishHook) {
            EntityPlayer var19 = ((EntityFishHook)this.trackedEntity).angler;
            return new S0EPacketSpawnObject(this.trackedEntity, 90, var19 != null ? var19.getEntityId() : this.trackedEntity.getEntityId());
         }

         if (this.trackedEntity instanceof EntityArrow) {
            Entity var18 = ((EntityArrow)this.trackedEntity).shootingEntity;
            return new S0EPacketSpawnObject(this.trackedEntity, 60, var18 != null ? var18.getEntityId() : this.trackedEntity.getEntityId());
         }

         if (this.trackedEntity instanceof EntitySnowball) {
            return new S0EPacketSpawnObject(this.trackedEntity, 61);
         }

         if (this.trackedEntity instanceof EntityPotion) {
            return new S0EPacketSpawnObject(this.trackedEntity, 73, ((EntityPotion)this.trackedEntity).getPotionDamage());
         }

         if (this.trackedEntity instanceof EntityExpBottle) {
            return new S0EPacketSpawnObject(this.trackedEntity, 75);
         }

         if (this.trackedEntity instanceof EntityEnderPearl) {
            return new S0EPacketSpawnObject(this.trackedEntity, 65);
         }

         if (this.trackedEntity instanceof EntityEnderEye) {
            return new S0EPacketSpawnObject(this.trackedEntity, 72);
         }

         if (this.trackedEntity instanceof EntityFireworkRocket) {
            return new S0EPacketSpawnObject(this.trackedEntity, 76);
         }

         if (this.trackedEntity instanceof EntityFireball var5) {
            byte var21 = 63;
            if (this.trackedEntity instanceof EntitySmallFireball) {
               var21 = 64;
            } else if (this.trackedEntity instanceof EntityWitherSkull) {
               var21 = 66;
            }

            S0EPacketSpawnObject var17;
            if (var5.shootingEntity != null) {
               var17 = new S0EPacketSpawnObject(this.trackedEntity, var21, ((EntityFireball)this.trackedEntity).shootingEntity.getEntityId());
            } else {
               var17 = new S0EPacketSpawnObject(this.trackedEntity, var21, 0);
            }

            var17.setSpeedX((int)(var5.accelerationX * 8000.0));
            var17.setSpeedY((int)(var5.accelerationY * 8000.0));
            var17.setSpeedZ((int)(var5.accelerationZ * 8000.0));
            return var17;
         } else if (this.trackedEntity instanceof EntityEgg) {
            return new S0EPacketSpawnObject(this.trackedEntity, 62);
         } else if (this.trackedEntity instanceof EntityTNTPrimed) {
            return new S0EPacketSpawnObject(this.trackedEntity, 50);
         } else if (this.trackedEntity instanceof EntityEnderCrystal) {
            return new S0EPacketSpawnObject(this.trackedEntity, 51);
         } else if (this.trackedEntity instanceof EntityFallingBlock var6) {
            return new S0EPacketSpawnObject(this.trackedEntity, 70, Block.getStateId(var6.getBlock()));
         } else if (this.trackedEntity instanceof EntityArmorStand) {
            return new S0EPacketSpawnObject(this.trackedEntity, 78);
         } else if (this.trackedEntity instanceof EntityPainting) {
            return new S10PacketSpawnPainting((EntityPainting)this.trackedEntity);
         } else if (this.trackedEntity instanceof EntityItemFrame var7) {
            S0EPacketSpawnObject var16 = new S0EPacketSpawnObject(this.trackedEntity, 71, var7.facingDirection.getHorizontalIndex());
            BlockPos var20 = var7.getHangingPosition();
            var16.setX(MathHelper.floor_float(var20.getX() * 32));
            var16.setY(MathHelper.floor_float(var20.getY() * 32));
            var16.setZ(MathHelper.floor_float(var20.getZ() * 32));
            return var16;
         } else if (this.trackedEntity instanceof EntityLeashKnot var8) {
            S0EPacketSpawnObject var15 = new S0EPacketSpawnObject(this.trackedEntity, 77);
            BlockPos var10 = var8.getHangingPosition();
            var15.setX(MathHelper.floor_float(var10.getX() * 32));
            var15.setY(MathHelper.floor_float(var10.getY() * 32));
            var15.setZ(MathHelper.floor_float(var10.getZ() * 32));
            return var15;
         } else if (this.trackedEntity instanceof EntityXPOrb) {
            return new S11PacketSpawnExperienceOrb((EntityXPOrb)this.trackedEntity);
         } else {
            throw new IllegalArgumentException("Don't know how to add " + this.trackedEntity.getClass() + "!");
         }
      }
   }

   public void removeTrackedPlayerSymmetric(EntityPlayerMP entityPlayerMP) {
      if (this.trackingPlayers.contains(entityPlayerMP)) {
         this.trackingPlayers.remove(entityPlayerMP);
         entityPlayerMP.removeEntity(this.trackedEntity);
      }
   }
}
