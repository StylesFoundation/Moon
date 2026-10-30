package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.BlockPos;

public class C08PacketPlayerBlockPlacement implements Packet<INetHandlerPlayServer> {
   public static BlockPos field_179726_a = new BlockPos(1, 1, 1);
   private BlockPos position;
   private int placedBlockDirection;
   private ItemStack stack;
   private ItemStack e;
   private float f;
   private float g;
   private float h;

   public C08PacketPlayerBlockPlacement() {
   }

   public C08PacketPlayerBlockPlacement(ItemStack itemStack) {
      this(field_179726_a, 255, itemStack, 0.0F, 0.0F, 0.0F);
   }

   public C08PacketPlayerBlockPlacement(BlockPos blockPos, int integer, ItemStack itemStack, float float4, float float5, float float6) {
      this.position = blockPos;
      this.placedBlockDirection = integer;
      this.stack = itemStack != null ? itemStack.copy() : null;
      this.e = itemStack;
      this.f = float4;
      this.g = float5;
      this.h = float6;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.position = buffer.readBlockPos();
      this.placedBlockDirection = buffer.readUnsignedByte();
      this.stack = buffer.readItemStackFromBuffer();
      this.f = buffer.readUnsignedByte() / 16.0F;
      this.g = buffer.readUnsignedByte() / 16.0F;
      this.h = buffer.readUnsignedByte() / 16.0F;
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeBlockPos(this.position);
      buffer.writeByte(this.placedBlockDirection);
      buffer.writeItemStackToBuffer(this.stack);
      buffer.writeByte((int)(this.f * 16.0F));
      buffer.writeByte((int)(this.g * 16.0F));
      buffer.writeByte((int)(this.h * 16.0F));
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.processPlayerBlockPlacement(this);
   }

   public BlockPos getPosition() {
      return this.position;
   }

   public int getPlacedBlockDirection() {
      return this.placedBlockDirection;
   }

   public ItemStack getStack() {
      return this.stack;
   }

   public float Method302() {
      return this.f;
   }

   public float Method303() {
      return this.g;
   }

   public float Method304() {
      return this.h;
   }

   public BlockPos Method305() {
      return this.position;
   }

   public int Method306() {
      return this.placedBlockDirection;
   }

   public ItemStack Method307() {
      return this.stack;
   }

   public ItemStack Method308() {
      return this.e;
   }

   public float Method309() {
      return this.f;
   }

   public float Method310() {
      return this.g;
   }

   public float Method311() {
      return this.h;
   }

   public C08PacketPlayerBlockPlacement Method312(BlockPos blockPos) {
      this.position = blockPos;
      return this;
   }

   public C08PacketPlayerBlockPlacement Method313(int integer) {
      this.placedBlockDirection = integer;
      return this;
   }

   public C08PacketPlayerBlockPlacement Method314(ItemStack itemStack) {
      this.stack = itemStack;
      return this;
   }

   public C08PacketPlayerBlockPlacement Method315(ItemStack itemStack) {
      this.e = itemStack;
      return this;
   }

   public C08PacketPlayerBlockPlacement Method316(float float1) {
      this.f = float1;
      return this;
   }

   public C08PacketPlayerBlockPlacement Method317(float float1) {
      this.g = float1;
      return this;
   }

   public C08PacketPlayerBlockPlacement Method318(float float1) {
      this.h = float1;
      return this;
   }
}
