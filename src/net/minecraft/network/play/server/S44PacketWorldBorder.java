package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.border.WorldBorder;

public class S44PacketWorldBorder implements Packet<INetHandlerPlayClient> {
   private S44PacketWorldBorder.Action action;
   private int size;
   private double centerX;
   private double centerZ;
   private double targetSize;
   private double diameter;
   private long timeUntilTarget;
   private int warningTime;
   private int warningDistance;

   public S44PacketWorldBorder() {
   }

   public S44PacketWorldBorder(WorldBorder worldBorder, S44PacketWorldBorder.Action action) {
      this.action = action;
      this.centerX = worldBorder.getCenterX();
      this.centerZ = worldBorder.getCenterZ();
      this.diameter = worldBorder.getDiameter();
      this.targetSize = worldBorder.getTargetSize();
      this.timeUntilTarget = worldBorder.getTimeUntilTarget();
      this.size = worldBorder.getSize();
      this.warningDistance = worldBorder.getWarningDistance();
      this.warningTime = worldBorder.getWarningTime();
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.action = buffer.readEnumValue(S44PacketWorldBorder.Action.class);
      switch (this.action) {
         case SET_SIZE:
            this.targetSize = buffer.readDouble();
            break;
         case LERP_SIZE:
            this.diameter = buffer.readDouble();
            this.targetSize = buffer.readDouble();
            this.timeUntilTarget = buffer.readVarLong();
            break;
         case SET_CENTER:
            this.centerX = buffer.readDouble();
            this.centerZ = buffer.readDouble();
            break;
         case SET_WARNING_BLOCKS:
            this.warningDistance = buffer.readVarIntFromBuffer();
            break;
         case SET_WARNING_TIME:
            this.warningTime = buffer.readVarIntFromBuffer();
            break;
         case INITIALIZE:
            this.centerX = buffer.readDouble();
            this.centerZ = buffer.readDouble();
            this.diameter = buffer.readDouble();
            this.targetSize = buffer.readDouble();
            this.timeUntilTarget = buffer.readVarLong();
            this.size = buffer.readVarIntFromBuffer();
            this.warningDistance = buffer.readVarIntFromBuffer();
            this.warningTime = buffer.readVarIntFromBuffer();
      }
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeEnumValue(this.action);
      switch (this.action) {
         case SET_SIZE:
            buffer.writeDouble(this.targetSize);
            break;
         case LERP_SIZE:
            buffer.writeDouble(this.diameter);
            buffer.writeDouble(this.targetSize);
            buffer.writeVarLong(this.timeUntilTarget);
            break;
         case SET_CENTER:
            buffer.writeDouble(this.centerX);
            buffer.writeDouble(this.centerZ);
            break;
         case SET_WARNING_BLOCKS:
            buffer.writeVarIntToBuffer(this.warningDistance);
            break;
         case SET_WARNING_TIME:
            buffer.writeVarIntToBuffer(this.warningTime);
            break;
         case INITIALIZE:
            buffer.writeDouble(this.centerX);
            buffer.writeDouble(this.centerZ);
            buffer.writeDouble(this.diameter);
            buffer.writeDouble(this.targetSize);
            buffer.writeVarLong(this.timeUntilTarget);
            buffer.writeVarIntToBuffer(this.size);
            buffer.writeVarIntToBuffer(this.warningDistance);
            buffer.writeVarIntToBuffer(this.warningTime);
      }
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleWorldBorder(this);
   }

   public void func_179788_a(WorldBorder worldBorder) {
      switch (this.action) {
         case SET_SIZE:
            worldBorder.setTransition(this.targetSize);
            break;
         case LERP_SIZE:
            worldBorder.setTransition(this.diameter, this.targetSize, this.timeUntilTarget);
            break;
         case SET_CENTER:
            worldBorder.setCenter(this.centerX, this.centerZ);
            break;
         case SET_WARNING_BLOCKS:
            worldBorder.setWarningDistance(this.warningDistance);
            break;
         case SET_WARNING_TIME:
            worldBorder.setWarningTime(this.warningTime);
            break;
         case INITIALIZE:
            worldBorder.setCenter(this.centerX, this.centerZ);
            if (this.timeUntilTarget > 0L) {
               worldBorder.setTransition(this.diameter, this.targetSize, this.timeUntilTarget);
            } else {
               worldBorder.setTransition(this.targetSize);
            }

            worldBorder.setSize(this.size);
            worldBorder.setWarningDistance(this.warningDistance);
            worldBorder.setWarningTime(this.warningTime);
      }
   }

   public enum Action {
      SET_SIZE,
      LERP_SIZE,
      SET_CENTER,
      INITIALIZE,
      SET_WARNING_TIME,
      SET_WARNING_BLOCKS;

      private static S44PacketWorldBorder.Action[] Method551() {
         return new S44PacketWorldBorder.Action[]{SET_SIZE, LERP_SIZE, SET_CENTER, INITIALIZE, SET_WARNING_TIME, SET_WARNING_BLOCKS};
      }
   }
}
