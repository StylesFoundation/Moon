package net.minecraft.network;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ByteProcessor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.util.UUID;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;

public class PacketBuffer extends ByteBuf {
   private final ByteBuf buf;

   public PacketBuffer(ByteBuf byteBuf) {
      this.buf = byteBuf;
   }

   public static int getVarIntSize(int integer) {
      for (int var4 = 1; var4 < 5; var4++) {
         if ((integer & -1 << var4 * 7) == 0) {
            return var4;
         }
      }

      return 5;
   }

   public void writeByteArray(byte[] arr) {
      this.writeVarIntToBuffer(arr.length);
      this.writeBytes(arr);
   }

   public byte[] readByteArray() {
      byte[] var1 = new byte[this.readVarIntFromBuffer()];
      this.readBytes(var1);
      return var1;
   }

   public BlockPos readBlockPos() {
      return BlockPos.fromLong(this.readLong());
   }

   public void writeBlockPos(BlockPos blockPos) {
      this.writeLong(blockPos.toLong());
   }

   public IChatComponent readChatComponent() {
      return IChatComponent.Serializer.jsonToComponent(this.readStringFromBuffer(32767));
   }

   public void writeChatComponent(IChatComponent iChatComponent) {
      this.writeString(IChatComponent.Serializer.componentToJson(iChatComponent));
   }

   public <T extends Enum<T>> T readEnumValue(Class<T> class1) {
      return (T)class1.getEnumConstants()[this.readVarIntFromBuffer()];
   }

   public void writeEnumValue(Enum<?> enum1) {
      this.writeVarIntToBuffer(enum1.ordinal());
   }

   public int readVarIntFromBuffer() {
      int var4 = 0;
      int var5 = 0;

      byte var6;
      do {
         var6 = this.readByte();
         var4 |= (var6 & 127) << var5++ * 7;
         if (var5 > 5) {
            throw new RuntimeException("VarInt too big");
         }
      } while ((var6 & 128) == 128);

      return var4;
   }

   public long readVarLong() {
      long var4 = 0L;
      int var6 = 0;

      byte var7;
      do {
         var7 = this.readByte();
         var4 |= (long)(var7 & 127) << var6++ * 7;
         if (var6 > 10) {
            throw new RuntimeException("VarLong too big");
         }
      } while ((var7 & 128) == 128);

      return var4;
   }

   public void writeUuid(UUID uUID) {
      this.writeLong(uUID.getMostSignificantBits());
      this.writeLong(uUID.getLeastSignificantBits());
   }

   public UUID readUuid() {
      return new UUID(this.readLong(), this.readLong());
   }

   public void writeVarIntToBuffer(int integer) {
      while ((integer & -128) != 0) {
         this.writeByte(integer & 127 | 128);
         integer >>>= 7;
      }

      this.writeByte(integer);
   }

   public void writeVarLong(long long1) {
      while ((long1 & -128L) != 0L) {
         this.writeByte((int)(long1 & 127L) | 128);
         long1 >>>= 7;
      }

      this.writeByte((int)long1);
   }

   public void writeNBTTagCompoundToBuffer(NBTTagCompound nBTTagCompound) {
      if (nBTTagCompound == null) {
         this.writeByte(0);
      } else {
         try {
            CompressedStreamTools.write(nBTTagCompound, new ByteBufOutputStream(this));
         } catch (IOException var6) {
            throw new EncoderException(var6);
         }
      }
   }

   public NBTTagCompound readNBTTagCompoundFromBuffer() throws IOException {
      int var4 = this.readerIndex();
      byte var5 = this.readByte();
      if (var5 == 0) {
         return null;
      }

      this.readerIndex(var4);
      return CompressedStreamTools.read(new ByteBufInputStream(this), new NBTSizeTracker(2097152L));
   }

   public void writeItemStackToBuffer(ItemStack itemStack) {
      if (itemStack == null) {
         this.writeShort(-1);
      } else {
         this.writeShort(Item.getIdFromItem(itemStack.getItem()));
         this.writeByte(itemStack.stackSize);
         this.writeShort(itemStack.getMetadata());
         NBTTagCompound var5 = null;
         if (itemStack.getItem().isDamageable() || itemStack.getItem().getShareTag()) {
            var5 = itemStack.getTagCompound();
         }

         this.writeNBTTagCompoundToBuffer(var5);
      }
   }

   public ItemStack readItemStackFromBuffer() throws IOException {
      ItemStack var4 = null;
      short var5 = this.readShort();
      if (var5 >= 0) {
         byte var6 = this.readByte();
         short var7 = this.readShort();
         var4 = new ItemStack(Item.getItemById(var5), var6, var7);
         var4.setTagCompound(this.readNBTTagCompoundFromBuffer());
      }

      return var4;
   }

   public String readStringFromBuffer(int integer) {
      int var5 = this.readVarIntFromBuffer();
      if (var5 > integer * 4) {
         throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + var5 + " > " + integer * 4 + ")");
      } else if (var5 < 0) {
         throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
      } else {
         byte[] var6 = new byte[var5];
         this.readBytes(var6);
         String var7 = new String(var6, Charsets.UTF_8);
         if (var7.length() > integer) {
            throw new DecoderException("The received string length is longer than maximum allowed (" + var5 + " > " + integer + ")");
         } else {
            return var7;
         }
      }
   }

   public PacketBuffer writeString(String string) {
      byte[] var5 = string.getBytes(Charsets.UTF_8);
      if (var5.length > 32767) {
         throw new EncoderException("String too big (was " + string.length() + " bytes encoded, max 32767)");
      }

      this.writeVarIntToBuffer(var5.length);
      this.writeBytes(var5);
      return this;
   }

   @Override
   public int capacity() {
      return this.buf.capacity();
   }

   @Override
   public ByteBuf capacity(int integer) {
      return this.buf.capacity(integer);
   }

   @Override
   public int maxCapacity() {
      return this.buf.maxCapacity();
   }

   @Override
   public ByteBufAllocator alloc() {
      return this.buf.alloc();
   }

   @Override
   public ByteOrder order() {
      return this.buf.order();
   }

   @Override
   public ByteBuf order(ByteOrder byteOrder) {
      return this.buf.order(byteOrder);
   }

   @Override
   public ByteBuf unwrap() {
      return this.buf.unwrap();
   }

   @Override
   public boolean isDirect() {
      return this.buf.isDirect();
   }

   @Override
   public boolean isReadOnly() {
      return this.buf.isReadOnly();
   }

   @Override
   public ByteBuf asReadOnly() {
      return this.buf.asReadOnly();
   }

   @Override
   public int readerIndex() {
      return this.buf.readerIndex();
   }

   @Override
   public ByteBuf readerIndex(int integer) {
      return this.buf.readerIndex(integer);
   }

   @Override
   public int writerIndex() {
      return this.buf.writerIndex();
   }

   @Override
   public ByteBuf writerIndex(int integer) {
      return this.buf.writerIndex(integer);
   }

   @Override
   public ByteBuf setIndex(int integer1, int integer2) {
      return this.buf.setIndex(integer1, integer2);
   }

   @Override
   public int readableBytes() {
      return this.buf.readableBytes();
   }

   @Override
   public int writableBytes() {
      return this.buf.writableBytes();
   }

   @Override
   public int maxWritableBytes() {
      return this.buf.maxWritableBytes();
   }

   @Override
   public boolean isReadable() {
      return this.buf.isReadable();
   }

   @Override
   public boolean isReadable(int integer) {
      return this.buf.isReadable(integer);
   }

   @Override
   public boolean isWritable() {
      return this.buf.isWritable();
   }

   @Override
   public boolean isWritable(int integer) {
      return this.buf.isWritable(integer);
   }

   @Override
   public ByteBuf clear() {
      return this.buf.clear();
   }

   @Override
   public ByteBuf markReaderIndex() {
      return this.buf.markReaderIndex();
   }

   @Override
   public ByteBuf resetReaderIndex() {
      return this.buf.resetReaderIndex();
   }

   @Override
   public ByteBuf markWriterIndex() {
      return this.buf.markWriterIndex();
   }

   @Override
   public ByteBuf resetWriterIndex() {
      return this.buf.resetWriterIndex();
   }

   @Override
   public ByteBuf discardReadBytes() {
      return this.buf.discardReadBytes();
   }

   @Override
   public ByteBuf discardSomeReadBytes() {
      return this.buf.discardSomeReadBytes();
   }

   @Override
   public ByteBuf ensureWritable(int integer) {
      return this.buf.ensureWritable(integer);
   }

   @Override
   public int ensureWritable(int integer, boolean boolean2) {
      return this.buf.ensureWritable(integer, boolean2);
   }

   @Override
   public boolean getBoolean(int integer) {
      return this.buf.getBoolean(integer);
   }

   @Override
   public byte getByte(int integer) {
      return this.buf.getByte(integer);
   }

   @Override
   public short getUnsignedByte(int integer) {
      return this.buf.getUnsignedByte(integer);
   }

   @Override
   public short getShort(int integer) {
      return this.buf.getShort(integer);
   }

   @Override
   public short getShortLE(int integer) {
      return this.buf.getShortLE(integer);
   }

   @Override
   public int getUnsignedShort(int integer) {
      return this.buf.getUnsignedShort(integer);
   }

   @Override
   public int getUnsignedShortLE(int integer) {
      return this.buf.getUnsignedShortLE(integer);
   }

   @Override
   public int getMedium(int integer) {
      return this.buf.getMedium(integer);
   }

   @Override
   public int getMediumLE(int integer) {
      return this.buf.getMediumLE(integer);
   }

   @Override
   public int getUnsignedMedium(int integer) {
      return this.buf.getUnsignedMedium(integer);
   }

   @Override
   public int getUnsignedMediumLE(int integer) {
      return this.buf.getUnsignedMediumLE(integer);
   }

   @Override
   public int getInt(int integer) {
      return this.buf.getInt(integer);
   }

   @Override
   public int getIntLE(int integer) {
      return this.buf.getIntLE(integer);
   }

   @Override
   public long getUnsignedInt(int integer) {
      return this.buf.getUnsignedInt(integer);
   }

   @Override
   public long getUnsignedIntLE(int integer) {
      return this.buf.getUnsignedIntLE(integer);
   }

   @Override
   public long getLong(int integer) {
      return this.buf.getLong(integer);
   }

   @Override
   public long getLongLE(int integer) {
      return this.buf.getLongLE(integer);
   }

   @Override
   public char getChar(int integer) {
      return this.buf.getChar(integer);
   }

   @Override
   public float getFloat(int integer) {
      return this.buf.getFloat(integer);
   }

   @Override
   public double getDouble(int integer) {
      return this.buf.getDouble(integer);
   }

   @Override
   public ByteBuf getBytes(int integer, ByteBuf byteBuf) {
      return this.buf.getBytes(integer, byteBuf);
   }

   @Override
   public ByteBuf getBytes(int integer1, ByteBuf byteBuf, int integer3) {
      return this.buf.getBytes(integer1, byteBuf, integer3);
   }

   @Override
   public ByteBuf getBytes(int integer1, ByteBuf byteBuf, int integer3, int integer4) {
      return this.buf.getBytes(integer1, byteBuf, integer3, integer4);
   }

   @Override
   public ByteBuf getBytes(int integer, byte[] arr) {
      return this.buf.getBytes(integer, arr);
   }

   @Override
   public ByteBuf getBytes(int integer1, byte[] arr, int integer3, int integer4) {
      return this.buf.getBytes(integer1, arr, integer3, integer4);
   }

   @Override
   public ByteBuf getBytes(int integer, ByteBuffer byteBuffer) {
      return this.buf.getBytes(integer, byteBuffer);
   }

   @Override
   public ByteBuf getBytes(int integer1, OutputStream outputStream, int integer3) throws IOException {
      return this.buf.getBytes(integer1, outputStream, integer3);
   }

   @Override
   public int getBytes(int integer1, GatheringByteChannel gatheringByteChannel, int integer3) throws IOException {
      return this.buf.getBytes(integer1, gatheringByteChannel, integer3);
   }

   @Override
   public int getBytes(int integer1, FileChannel fileChannel, long long3, int integer4) throws IOException {
      return this.buf.getBytes(integer1, fileChannel, long3, integer4);
   }

   @Override
   public CharSequence getCharSequence(int integer1, int integer2, Charset charset) {
      return this.buf.getCharSequence(integer1, integer2, charset);
   }

   @Override
   public ByteBuf setBoolean(int integer, boolean boolean2) {
      return this.buf.setBoolean(integer, boolean2);
   }

   @Override
   public ByteBuf setByte(int integer1, int integer2) {
      return this.buf.setByte(integer1, integer2);
   }

   @Override
   public ByteBuf setShort(int integer1, int integer2) {
      return this.buf.setShort(integer1, integer2);
   }

   @Override
   public ByteBuf setShortLE(int integer1, int integer2) {
      return this.buf.setShortLE(integer1, integer2);
   }

   @Override
   public ByteBuf setMedium(int integer1, int integer2) {
      return this.buf.setMedium(integer1, integer2);
   }

   @Override
   public ByteBuf setMediumLE(int integer1, int integer2) {
      return this.buf.setMediumLE(integer1, integer2);
   }

   @Override
   public ByteBuf setInt(int integer1, int integer2) {
      return this.buf.setInt(integer1, integer2);
   }

   @Override
   public ByteBuf setIntLE(int integer1, int integer2) {
      return this.buf.setIntLE(integer1, integer2);
   }

   @Override
   public ByteBuf setLong(int integer, long long2) {
      return this.buf.setLong(integer, long2);
   }

   @Override
   public ByteBuf setLongLE(int integer, long long2) {
      return this.buf.setLongLE(integer, long2);
   }

   @Override
   public ByteBuf setChar(int integer1, int integer2) {
      return this.buf.setChar(integer1, integer2);
   }

   @Override
   public ByteBuf setFloat(int integer, float float2) {
      return this.buf.setFloat(integer, float2);
   }

   @Override
   public ByteBuf setDouble(int integer, double double2) {
      return this.buf.setDouble(integer, double2);
   }

   @Override
   public ByteBuf setBytes(int integer, ByteBuf byteBuf) {
      return this.buf.setBytes(integer, byteBuf);
   }

   @Override
   public ByteBuf setBytes(int integer1, ByteBuf byteBuf, int integer3) {
      return this.buf.setBytes(integer1, byteBuf, integer3);
   }

   @Override
   public ByteBuf setBytes(int integer1, ByteBuf byteBuf, int integer3, int integer4) {
      return this.buf.setBytes(integer1, byteBuf, integer3, integer4);
   }

   @Override
   public ByteBuf setBytes(int integer, byte[] arr) {
      return this.buf.setBytes(integer, arr);
   }

   @Override
   public ByteBuf setBytes(int integer1, byte[] arr, int integer3, int integer4) {
      return this.buf.setBytes(integer1, arr, integer3, integer4);
   }

   @Override
   public ByteBuf setBytes(int integer, ByteBuffer byteBuffer) {
      return this.buf.setBytes(integer, byteBuffer);
   }

   @Override
   public int setBytes(int integer1, InputStream inputStream, int integer3) throws IOException {
      return this.buf.setBytes(integer1, inputStream, integer3);
   }

   @Override
   public int setBytes(int integer1, ScatteringByteChannel scatteringByteChannel, int integer3) throws IOException {
      return this.buf.setBytes(integer1, scatteringByteChannel, integer3);
   }

   @Override
   public int setBytes(int integer1, FileChannel fileChannel, long long3, int integer4) throws IOException {
      return this.buf.setBytes(integer1, fileChannel, long3, integer4);
   }

   @Override
   public ByteBuf setZero(int integer1, int integer2) {
      return this.buf.setZero(integer1, integer2);
   }

   @Override
   public int setCharSequence(int integer, CharSequence charSequence, Charset charset) {
      return this.buf.setCharSequence(integer, charSequence, charset);
   }

   @Override
   public boolean readBoolean() {
      return this.buf.readBoolean();
   }

   @Override
   public byte readByte() {
      return this.buf.readByte();
   }

   @Override
   public short readUnsignedByte() {
      return this.buf.readUnsignedByte();
   }

   @Override
   public short readShort() {
      return this.buf.readShort();
   }

   @Override
   public short readShortLE() {
      return this.buf.readShortLE();
   }

   @Override
   public int readUnsignedShort() {
      return this.buf.readUnsignedShort();
   }

   @Override
   public int readUnsignedShortLE() {
      return this.buf.readUnsignedShortLE();
   }

   @Override
   public int readMedium() {
      return this.buf.readMedium();
   }

   @Override
   public int readMediumLE() {
      return this.buf.readMediumLE();
   }

   @Override
   public int readUnsignedMedium() {
      return this.buf.readUnsignedMedium();
   }

   @Override
   public int readUnsignedMediumLE() {
      return this.buf.readUnsignedMediumLE();
   }

   @Override
   public int readInt() {
      return this.buf.readInt();
   }

   @Override
   public int readIntLE() {
      return this.buf.readIntLE();
   }

   @Override
   public long readUnsignedInt() {
      return this.buf.readUnsignedInt();
   }

   @Override
   public long readUnsignedIntLE() {
      return this.buf.readUnsignedIntLE();
   }

   @Override
   public long readLong() {
      return this.buf.readLong();
   }

   @Override
   public long readLongLE() {
      return this.buf.readLongLE();
   }

   @Override
   public char readChar() {
      return this.buf.readChar();
   }

   @Override
   public float readFloat() {
      return this.buf.readFloat();
   }

   @Override
   public double readDouble() {
      return this.buf.readDouble();
   }

   @Override
   public ByteBuf readBytes(int integer) {
      return this.buf.readBytes(integer);
   }

   @Override
   public ByteBuf readSlice(int integer) {
      return this.buf.readSlice(integer);
   }

   @Override
   public ByteBuf readRetainedSlice(int integer) {
      return this.buf.readRetainedSlice(integer);
   }

   @Override
   public ByteBuf readBytes(ByteBuf byteBuf) {
      return this.buf.readBytes(byteBuf);
   }

   @Override
   public ByteBuf readBytes(ByteBuf byteBuf, int integer) {
      return this.buf.readBytes(byteBuf, integer);
   }

   @Override
   public ByteBuf readBytes(ByteBuf byteBuf, int integer2, int integer3) {
      return this.buf.readBytes(byteBuf, integer2, integer3);
   }

   @Override
   public ByteBuf readBytes(byte[] arr) {
      return this.buf.readBytes(arr);
   }

   @Override
   public ByteBuf readBytes(byte[] arr, int integer2, int integer3) {
      return this.buf.readBytes(arr, integer2, integer3);
   }

   @Override
   public ByteBuf readBytes(ByteBuffer byteBuffer) {
      return this.buf.readBytes(byteBuffer);
   }

   @Override
   public ByteBuf readBytes(OutputStream outputStream, int integer) throws IOException {
      return this.buf.readBytes(outputStream, integer);
   }

   @Override
   public int readBytes(GatheringByteChannel gatheringByteChannel, int integer) throws IOException {
      return this.buf.readBytes(gatheringByteChannel, integer);
   }

   @Override
   public CharSequence readCharSequence(int integer, Charset charset) {
      return this.buf.readCharSequence(integer, charset);
   }

   @Override
   public int readBytes(FileChannel fileChannel, long long2, int integer) throws IOException {
      return this.buf.readBytes(fileChannel, long2, integer);
   }

   @Override
   public ByteBuf skipBytes(int integer) {
      return this.buf.skipBytes(integer);
   }

   @Override
   public ByteBuf writeBoolean(boolean boolean1) {
      return this.buf.writeBoolean(boolean1);
   }

   @Override
   public ByteBuf writeByte(int integer) {
      return this.buf.writeByte(integer);
   }

   @Override
   public ByteBuf writeShort(int integer) {
      return this.buf.writeShort(integer);
   }

   @Override
   public ByteBuf writeShortLE(int integer) {
      return this.buf.writeShortLE(integer);
   }

   @Override
   public ByteBuf writeMedium(int integer) {
      return this.buf.writeMedium(integer);
   }

   @Override
   public ByteBuf writeMediumLE(int integer) {
      return this.buf.writeMediumLE(integer);
   }

   @Override
   public ByteBuf writeInt(int integer) {
      return this.buf.writeInt(integer);
   }

   @Override
   public ByteBuf writeIntLE(int integer) {
      return this.buf.writeIntLE(integer);
   }

   @Override
   public ByteBuf writeLong(long long1) {
      return this.buf.writeLong(long1);
   }

   @Override
   public ByteBuf writeLongLE(long long1) {
      return this.buf.writeLongLE(long1);
   }

   @Override
   public ByteBuf writeChar(int integer) {
      return this.buf.writeChar(integer);
   }

   @Override
   public ByteBuf writeFloat(float float1) {
      return this.buf.writeFloat(float1);
   }

   @Override
   public ByteBuf writeDouble(double double1) {
      return this.buf.writeDouble(double1);
   }

   @Override
   public ByteBuf writeBytes(ByteBuf byteBuf) {
      return this.buf.writeBytes(byteBuf);
   }

   @Override
   public ByteBuf writeBytes(ByteBuf byteBuf, int integer) {
      return this.buf.writeBytes(byteBuf, integer);
   }

   @Override
   public ByteBuf writeBytes(ByteBuf byteBuf, int integer2, int integer3) {
      return this.buf.writeBytes(byteBuf, integer2, integer3);
   }

   @Override
   public ByteBuf writeBytes(byte[] arr) {
      return this.buf.writeBytes(arr);
   }

   @Override
   public ByteBuf writeBytes(byte[] arr, int integer2, int integer3) {
      return this.buf.writeBytes(arr, integer2, integer3);
   }

   @Override
   public ByteBuf writeBytes(ByteBuffer byteBuffer) {
      return this.buf.writeBytes(byteBuffer);
   }

   @Override
   public int writeBytes(InputStream inputStream, int integer) throws IOException {
      return this.buf.writeBytes(inputStream, integer);
   }

   @Override
   public int writeBytes(ScatteringByteChannel scatteringByteChannel, int integer) throws IOException {
      return this.buf.writeBytes(scatteringByteChannel, integer);
   }

   @Override
   public int writeBytes(FileChannel fileChannel, long long2, int integer) throws IOException {
      return this.buf.writeBytes(fileChannel, long2, integer);
   }

   @Override
   public ByteBuf writeZero(int integer) {
      return this.buf.writeZero(integer);
   }

   @Override
   public int writeCharSequence(CharSequence charSequence, Charset charset) {
      return this.buf.writeCharSequence(charSequence, charset);
   }

   @Override
   public int indexOf(int integer1, int integer2, byte byte3) {
      return this.buf.indexOf(integer1, integer2, byte3);
   }

   @Override
   public int bytesBefore(byte byte1) {
      return this.buf.bytesBefore(byte1);
   }

   @Override
   public int bytesBefore(int integer, byte byte2) {
      return this.buf.bytesBefore(integer, byte2);
   }

   @Override
   public int bytesBefore(int integer1, int integer2, byte byte3) {
      return this.buf.bytesBefore(integer1, integer2, byte3);
   }

   @Override
   public int forEachByte(ByteProcessor byteProcessor) {
      return this.buf.forEachByte(byteProcessor);
   }

   @Override
   public int forEachByte(int integer1, int integer2, ByteProcessor byteProcessor) {
      return this.buf.forEachByte(integer1, integer2, byteProcessor);
   }

   @Override
   public int forEachByteDesc(ByteProcessor byteProcessor) {
      return this.buf.forEachByteDesc(byteProcessor);
   }

   @Override
   public int forEachByteDesc(int integer1, int integer2, ByteProcessor byteProcessor) {
      return this.buf.forEachByteDesc(integer1, integer2, byteProcessor);
   }

   @Override
   public ByteBuf copy() {
      return this.buf.copy();
   }

   @Override
   public ByteBuf copy(int integer1, int integer2) {
      return this.buf.copy(integer1, integer2);
   }

   @Override
   public ByteBuf slice() {
      return this.buf.slice();
   }

   @Override
   public ByteBuf retainedSlice() {
      return this.buf.retainedSlice();
   }

   @Override
   public ByteBuf slice(int integer1, int integer2) {
      return this.buf.slice(integer1, integer2);
   }

   @Override
   public ByteBuf retainedSlice(int integer1, int integer2) {
      return this.buf.retainedSlice(integer1, integer2);
   }

   @Override
   public ByteBuf duplicate() {
      return this.buf.duplicate();
   }

   @Override
   public ByteBuf retainedDuplicate() {
      return this.buf.retainedDuplicate();
   }

   @Override
   public int nioBufferCount() {
      return this.buf.nioBufferCount();
   }

   @Override
   public ByteBuffer nioBuffer() {
      return this.buf.nioBuffer();
   }

   @Override
   public ByteBuffer nioBuffer(int integer1, int integer2) {
      return this.buf.nioBuffer(integer1, integer2);
   }

   @Override
   public ByteBuffer internalNioBuffer(int integer1, int integer2) {
      return this.buf.internalNioBuffer(integer1, integer2);
   }

   @Override
   public ByteBuffer[] nioBuffers() {
      return this.buf.nioBuffers();
   }

   @Override
   public ByteBuffer[] nioBuffers(int integer1, int integer2) {
      return this.buf.nioBuffers(integer1, integer2);
   }

   @Override
   public boolean hasArray() {
      return this.buf.hasArray();
   }

   @Override
   public byte[] array() {
      return this.buf.array();
   }

   @Override
   public int arrayOffset() {
      return this.buf.arrayOffset();
   }

   @Override
   public boolean hasMemoryAddress() {
      return this.buf.hasMemoryAddress();
   }

   @Override
   public long memoryAddress() {
      return this.buf.memoryAddress();
   }

   @Override
   public String toString(Charset charset) {
      return this.buf.toString(charset);
   }

   @Override
   public String toString(int integer1, int integer2, Charset charset) {
      return this.buf.toString(integer1, integer2, charset);
   }

   @Override
   public int hashCode() {
      return this.buf.hashCode();
   }

   @Override
   public boolean equals(Object object) {
      return this.buf.equals(object);
   }

   @Override
   public int compareTo(ByteBuf byteBuf) {
      return this.buf.compareTo(byteBuf);
   }

   @Override
   public String toString() {
      return this.buf.toString();
   }

   @Override
   public ByteBuf retain(int integer) {
      return this.buf.retain(integer);
   }

   @Override
   public ByteBuf retain() {
      return this.buf.retain();
   }

   @Override
   public ByteBuf touch() {
      return this.buf.touch();
   }

   @Override
   public ByteBuf touch(Object object) {
      return this.buf.touch(object);
   }

   @Override
   public int refCnt() {
      return this.buf.refCnt();
   }

   @Override
   public boolean release() {
      return this.buf.release();
   }

   @Override
   public boolean release(int integer) {
      return this.buf.release(integer);
   }
}
