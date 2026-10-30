package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.Validate;

public class S29PacketSoundEffect implements Packet<INetHandlerPlayClient> {
   private String soundName;
   private int posX;
   private int posY = Integer.MAX_VALUE;
   private int posZ;
   private float soundVolume;
   private int soundPitch;

   public S29PacketSoundEffect() {
   }

   public S29PacketSoundEffect(String string, double double2, double double3, double double4, float float5, float float6) {
      Validate.notNull(string, "name", new Object[0]);
      this.soundName = string;
      this.posX = (int)(double2 * 8.0);
      this.posY = (int)(double3 * 8.0);
      this.posZ = (int)(double4 * 8.0);
      this.soundVolume = float5;
      this.soundPitch = (int)(float6 * 63.0F);
      MathHelper.clamp_float(float6, 0.0F, 255.0F);
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.soundName = buffer.readStringFromBuffer(256);
      this.posX = buffer.readInt();
      this.posY = buffer.readInt();
      this.posZ = buffer.readInt();
      this.soundVolume = buffer.readFloat();
      this.soundPitch = buffer.readUnsignedByte();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeString(this.soundName);
      buffer.writeInt(this.posX);
      buffer.writeInt(this.posY);
      buffer.writeInt(this.posZ);
      buffer.writeFloat(this.soundVolume);
      buffer.writeByte(this.soundPitch);
   }

   public String getSoundName() {
      return this.soundName;
   }

   public double getX() {
      return this.posX / 8.0F;
   }

   public double getY() {
      return this.posY / 8.0F;
   }

   public double getZ() {
      return this.posZ / 8.0F;
   }

   public float getVolume() {
      return this.soundVolume;
   }

   public float getPitch() {
      return this.soundPitch / 63.0F;
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleSoundEffect(this);
   }
}
