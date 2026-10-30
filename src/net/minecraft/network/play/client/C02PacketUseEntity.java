package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class C02PacketUseEntity implements Packet<INetHandlerPlayServer> {
   private int entityId;
   private C02PacketUseEntity.Action action;
   private Vec3 hitVec;

   public C02PacketUseEntity() {
   }

   public C02PacketUseEntity(Entity entity, C02PacketUseEntity.Action action) {
      this.entityId = entity.getEntityId();
      this.action = action;
      this.hitVec = new Vec3(0.0, 0.0, 0.0);
   }

   public C02PacketUseEntity(int integer, C02PacketUseEntity.Action action) {
      this.entityId = integer;
      this.action = action;
      this.hitVec = new Vec3(0.0, 0.0, 0.0);
   }

   public C02PacketUseEntity(int integer, Vec3 vec3) {
      this(integer, C02PacketUseEntity.Action.INTERACT_AT);
      this.hitVec = vec3;
   }

   public C02PacketUseEntity(Entity entity, Vec3 vec3) {
      this(entity, C02PacketUseEntity.Action.INTERACT_AT);
      this.hitVec = vec3;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.entityId = buffer.readVarIntFromBuffer();
      this.action = buffer.readEnumValue(C02PacketUseEntity.Action.class);
      if (this.action == C02PacketUseEntity.Action.INTERACT_AT) {
         this.hitVec = new Vec3(buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
      }
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.entityId);
      buffer.writeEnumValue(this.action);
      if (this.action == C02PacketUseEntity.Action.INTERACT_AT) {
         buffer.writeFloat((float)this.hitVec.xCoord);
         buffer.writeFloat((float)this.hitVec.yCoord);
         buffer.writeFloat((float)this.hitVec.zCoord);
      }
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.processUseEntity(this);
   }

   public Entity getEntityFromWorld(World world) {
      return world.getEntityByID(this.entityId);
   }

   public C02PacketUseEntity.Action getAction() {
      return this.action;
   }

   public Vec3 getHitVec() {
      return this.hitVec;
   }

   public C02PacketUseEntity Method927(int integer) {
      this.entityId = integer;
      return this;
   }

   public C02PacketUseEntity Method928(C02PacketUseEntity.Action action) {
      this.action = action;
      return this;
   }

   public C02PacketUseEntity Method929(Vec3 vec3) {
      this.hitVec = vec3;
      return this;
   }

   public Vec3 Method930() {
      return this.hitVec;
   }

   public int Method931() {
      return this.entityId;
   }

   public C02PacketUseEntity.Action Method932() {
      return this.action;
   }

   public enum Action {
      INTERACT,
      ATTACK,
      INTERACT_AT;

      private static C02PacketUseEntity.Action[] Method971() {
         return new C02PacketUseEntity.Action[]{INTERACT, ATTACK, INTERACT_AT};
      }
   }
}
