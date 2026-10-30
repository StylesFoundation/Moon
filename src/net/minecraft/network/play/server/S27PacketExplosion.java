package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class S27PacketExplosion implements Packet<INetHandlerPlayClient> {
   private double posX;
   private double posY;
   private double posZ;
   private float strength;
   private List<BlockPos> affectedBlockPositions;
   private float field_149152_f;
   private float field_149153_g;
   private float field_149159_h;

   public S27PacketExplosion() {
   }

   public S27PacketExplosion(double double1, double double2, double double3, float float4, List<BlockPos> list, Vec3 vec3) {
      this.posX = double1;
      this.posY = double2;
      this.posZ = double3;
      this.strength = float4;
      this.affectedBlockPositions = Lists.newArrayList(list);
      if (vec3 != null) {
         this.field_149152_f = (float)vec3.xCoord;
         this.field_149153_g = (float)vec3.yCoord;
         this.field_149159_h = (float)vec3.zCoord;
      }
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.posX = buffer.readFloat();
      this.posY = buffer.readFloat();
      this.posZ = buffer.readFloat();
      this.strength = buffer.readFloat();
      int var5 = buffer.readInt();
      this.affectedBlockPositions = Lists.newArrayListWithCapacity(var5);
      int var6 = (int)this.posX;
      int var7 = (int)this.posY;
      int var8 = (int)this.posZ;

      for (int var9 = 0; var9 < var5; var9++) {
         int var10 = buffer.readByte() + var6;
         int var11 = buffer.readByte() + var7;
         int var12 = buffer.readByte() + var8;
         this.affectedBlockPositions.add(new BlockPos(var10, var11, var12));
      }

      this.field_149152_f = buffer.readFloat();
      this.field_149153_g = buffer.readFloat();
      this.field_149159_h = buffer.readFloat();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeFloat((float)this.posX);
      buffer.writeFloat((float)this.posY);
      buffer.writeFloat((float)this.posZ);
      buffer.writeFloat(this.strength);
      buffer.writeInt(this.affectedBlockPositions.size());
      int var5 = (int)this.posX;
      int var6 = (int)this.posY;
      int var7 = (int)this.posZ;

      for (BlockPos var9 : this.affectedBlockPositions) {
         int var10 = var9.getX() - var5;
         int var11 = var9.getY() - var6;
         int var12 = var9.getZ() - var7;
         buffer.writeByte(var10);
         buffer.writeByte(var11);
         buffer.writeByte(var12);
      }

      buffer.writeFloat(this.field_149152_f);
      buffer.writeFloat(this.field_149153_g);
      buffer.writeFloat(this.field_149159_h);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleExplosion(this);
   }

   public float func_149149_c() {
      return this.field_149152_f;
   }

   public float func_149144_d() {
      return this.field_149153_g;
   }

   public float func_149147_e() {
      return this.field_149159_h;
   }

   public void Method403(double double1) {
      this.posX = double1;
   }

   public void Method404(double double1) {
      this.posY = double1;
   }

   public void Method405(double double1) {
      this.posZ = double1;
   }

   public void Method406(float float1) {
      this.strength = float1;
   }

   public void Method407(List<BlockPos> list) {
      this.affectedBlockPositions = list;
   }

   public void Method408(float float1) {
      this.field_149152_f = float1;
   }

   public void Method409(float float1) {
      this.field_149153_g = float1;
   }

   public void Method410(float float1) {
      this.field_149159_h = float1;
   }

   public double getX() {
      return this.posX;
   }

   public double getY() {
      return this.posY;
   }

   public double getZ() {
      return this.posZ;
   }

   public float getStrength() {
      return this.strength;
   }

   public List<BlockPos> getAffectedBlockPositions() {
      return this.affectedBlockPositions;
   }
}
