package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C0BPacketEntityAction implements Packet<INetHandlerPlayServer> {
   private int entityID;
   private C0BPacketEntityAction.Action action;
   private int auxData;

   public C0BPacketEntityAction() {
   }

   public C0BPacketEntityAction(Entity entity, C0BPacketEntityAction.Action action) {
      this(entity, action, 0);
   }

   public C0BPacketEntityAction(Entity entity, C0BPacketEntityAction.Action action, int integer) {
      this.entityID = entity.getEntityId();
      this.action = action;
      this.auxData = integer;
   }

   public C0BPacketEntityAction(int integer1, C0BPacketEntityAction.Action action, int integer3) {
      this.entityID = integer1;
      this.action = action;
      this.auxData = integer3;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.entityID = buffer.readVarIntFromBuffer();
      this.action = buffer.readEnumValue(C0BPacketEntityAction.Action.class);
      this.auxData = buffer.readVarIntFromBuffer();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.entityID);
      buffer.writeEnumValue(this.action);
      buffer.writeVarIntToBuffer(this.auxData);
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.processEntityAction(this);
   }

   public C0BPacketEntityAction.Action getAction() {
      return this.action;
   }

   public int getAuxData() {
      return this.auxData;
   }

   public int Method606() {
      return this.entityID;
   }

   public int Method607() {
      return this.entityID;
   }

   public C0BPacketEntityAction.Action Method608() {
      return this.action;
   }

   public int Method609() {
      return this.auxData;
   }

   public C0BPacketEntityAction Method610(int integer) {
      this.entityID = integer;
      return this;
   }

   public C0BPacketEntityAction Method611(C0BPacketEntityAction.Action action) {
      this.action = action;
      return this;
   }

   public C0BPacketEntityAction Method612(int integer) {
      this.auxData = integer;
      return this;
   }

   public enum Action {
      START_SNEAKING,
      STOP_SNEAKING,
      STOP_SLEEPING,
      START_SPRINTING,
      STOP_SPRINTING,
      RIDING_JUMP,
      OPEN_INVENTORY;

      private static C0BPacketEntityAction.Action[] Method721() {
         return new C0BPacketEntityAction.Action[]{START_SNEAKING, STOP_SNEAKING, STOP_SLEEPING, START_SPRINTING, STOP_SPRINTING, RIDING_JUMP, OPEN_INVENTORY};
      }
   }
}
