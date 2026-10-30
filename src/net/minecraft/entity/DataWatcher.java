package net.minecraft.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.minecraft.crash.CrashReport;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Rotations;
import net.minecraft.world.biome.BiomeGenBase;
import org.apache.commons.lang3.ObjectUtils;

public class DataWatcher {
   private final Entity owner;
   private boolean isBlank = true;
   private static final Map<Class<?>, Integer> dataTypes = Maps.newHashMap();
   private final Map<Integer, DataWatcher.WatchableObject> watchedObjects = Maps.newHashMap();
   private boolean objectChanged;
   private final ReadWriteLock lock = new ReentrantReadWriteLock();
   public BiomeGenBase spawnBiome = BiomeGenBase.plains;
   public BlockPos spawnPosition = BlockPos.ORIGIN;

   public DataWatcher(Entity entity) {
      this.owner = entity;
   }

   public <T> void addObject(int integer, T object) {
      Integer var6 = dataTypes.get(object.getClass());
      if (var6 == null) {
         throw new IllegalArgumentException("Unknown data type: " + object.getClass());
      }

      if (integer > 31) {
         throw new IllegalArgumentException("Data value id is too big with " + integer + "! (Max is 31)");
      }

      if (this.watchedObjects.containsKey(integer)) {
         throw new IllegalArgumentException("Duplicate id value for " + integer + "!");
      }

      DataWatcher.WatchableObject var7 = new DataWatcher.WatchableObject(var6, integer, object);
      this.lock.writeLock().lock();
      this.watchedObjects.put(integer, var7);
      this.lock.writeLock().unlock();
      this.isBlank = false;
   }

   public void addObjectByDataType(int integer1, int integer2) {
      DataWatcher.WatchableObject var3 = new DataWatcher.WatchableObject(integer2, integer1, null);
      this.lock.writeLock().lock();
      this.watchedObjects.put(integer1, var3);
      this.lock.writeLock().unlock();
      this.isBlank = false;
   }

   public byte getWatchableObjectByte(int integer) {
      return (Byte)this.getWatchedObject(integer).getObject();
   }

   public short getWatchableObjectShort(int integer) {
      return (Short)this.getWatchedObject(integer).getObject();
   }

   public int getWatchableObjectInt(int integer) {
      return (Integer)this.getWatchedObject(integer).getObject();
   }

   public float getWatchableObjectFloat(int integer) {
      return (Float)this.getWatchedObject(integer).getObject();
   }

   public String getWatchableObjectString(int integer) {
      return (String)this.getWatchedObject(integer).getObject();
   }

   public ItemStack getWatchableObjectItemStack(int integer) {
      return (ItemStack)this.getWatchedObject(integer).getObject();
   }

   private DataWatcher.WatchableObject getWatchedObject(int integer) {
      this.lock.readLock().lock();

      DataWatcher.WatchableObject var2;
      try {
         var2 = this.watchedObjects.get(integer);
      } catch (Throwable var5) {
         CrashReport var4 = CrashReport.makeCrashReport(var5, "Getting synched entity data");
         var4.makeCategory("Synched entity data").addCrashSection("Data ID", integer);
         throw new ReportedException(var4);
      }

      this.lock.readLock().unlock();
      return var2;
   }

   public Rotations getWatchableObjectRotations(int integer) {
      return (Rotations)this.getWatchedObject(integer).getObject();
   }

   public <T> void updateObject(int integer, T object) {
      DataWatcher.WatchableObject var6 = this.getWatchedObject(integer);
      if (ObjectUtils.notEqual(object, var6.getObject())) {
         var6.setObject(object);
         this.owner.onDataWatcherUpdate(integer);
         var6.setWatched(true);
         this.objectChanged = true;
      }
   }

   public void setObjectWatched(int integer) {
      this.getWatchedObject(integer).watched = true;
      this.objectChanged = true;
   }

   public boolean hasObjectChanged() {
      return this.objectChanged;
   }

   public static void writeWatchedListToPacketBuffer(List<DataWatcher.WatchableObject> list, PacketBuffer packetBuffer) throws IOException {
      if (list != null) {
         for (DataWatcher.WatchableObject var6 : list) {
            writeWatchableObjectToPacketBuffer(packetBuffer, var6);
         }
      }

      packetBuffer.writeByte(127);
   }

   public List<DataWatcher.WatchableObject> getChanged() {
      ArrayList var4 = null;
      if (this.objectChanged) {
         this.lock.readLock().lock();

         for (DataWatcher.WatchableObject var6 : this.watchedObjects.values()) {
            if (var6.isWatched()) {
               var6.setWatched(false);
               if (var4 == null) {
                  var4 = Lists.newArrayList();
               }

               var4.add(var6);
            }
         }

         this.lock.readLock().unlock();
      }

      this.objectChanged = false;
      return var4;
   }

   public void writeTo(PacketBuffer packetBuffer) throws IOException {
      this.lock.readLock().lock();

      for (DataWatcher.WatchableObject var6 : this.watchedObjects.values()) {
         writeWatchableObjectToPacketBuffer(packetBuffer, var6);
      }

      this.lock.readLock().unlock();
      packetBuffer.writeByte(127);
   }

   public List<DataWatcher.WatchableObject> getAllWatched() {
      ArrayList var4 = null;
      this.lock.readLock().lock();

      for (DataWatcher.WatchableObject var6 : this.watchedObjects.values()) {
         if (var4 == null) {
            var4 = Lists.newArrayList();
         }

         var4.add(var6);
      }

      this.lock.readLock().unlock();
      return var4;
   }

   private static void writeWatchableObjectToPacketBuffer(PacketBuffer packetBuffer, DataWatcher.WatchableObject watchableObject) throws IOException {
      int var5 = (watchableObject.getObjectType() << 5 | watchableObject.getDataValueId() & 31) & 0xFF;
      packetBuffer.writeByte(var5);
      switch (watchableObject.getObjectType()) {
         case 0:
            packetBuffer.writeByte((Byte)watchableObject.getObject());
            break;
         case 1:
            packetBuffer.writeShort((Short)watchableObject.getObject());
            break;
         case 2:
            packetBuffer.writeInt((Integer)watchableObject.getObject());
            break;
         case 3:
            packetBuffer.writeFloat((Float)watchableObject.getObject());
            break;
         case 4:
            packetBuffer.writeString((String)watchableObject.getObject());
            break;
         case 5:
            ItemStack var6 = (ItemStack)watchableObject.getObject();
            packetBuffer.writeItemStackToBuffer(var6);
            break;
         case 6:
            BlockPos var7 = (BlockPos)watchableObject.getObject();
            packetBuffer.writeInt(var7.getX());
            packetBuffer.writeInt(var7.getY());
            packetBuffer.writeInt(var7.getZ());
            break;
         case 7:
            Rotations var8 = (Rotations)watchableObject.getObject();
            packetBuffer.writeFloat(var8.getX());
            packetBuffer.writeFloat(var8.getY());
            packetBuffer.writeFloat(var8.getZ());
      }
   }

   public static List<DataWatcher.WatchableObject> readWatchedListFromPacketBuffer(PacketBuffer packetBuffer) throws IOException {
      ArrayList var4 = null;

      for (byte var5 = packetBuffer.readByte(); var5 != 127; var5 = packetBuffer.readByte()) {
         if (var4 == null) {
            var4 = Lists.newArrayList();
         }

         int var6 = (var5 & 224) >> 5;
         int var7 = var5 & 31;
         DataWatcher.WatchableObject var8 = null;
         switch (var6) {
            case 0:
               var8 = new DataWatcher.WatchableObject(var6, var7, packetBuffer.readByte());
               break;
            case 1:
               var8 = new DataWatcher.WatchableObject(var6, var7, packetBuffer.readShort());
               break;
            case 2:
               var8 = new DataWatcher.WatchableObject(var6, var7, packetBuffer.readInt());
               break;
            case 3:
               var8 = new DataWatcher.WatchableObject(var6, var7, packetBuffer.readFloat());
               break;
            case 4:
               var8 = new DataWatcher.WatchableObject(var6, var7, packetBuffer.readStringFromBuffer(32767));
               break;
            case 5:
               var8 = new DataWatcher.WatchableObject(var6, var7, packetBuffer.readItemStackFromBuffer());
               break;
            case 6:
               int var9 = packetBuffer.readInt();
               int var10 = packetBuffer.readInt();
               int var11 = packetBuffer.readInt();
               var8 = new DataWatcher.WatchableObject(var6, var7, new BlockPos(var9, var10, var11));
               break;
            case 7:
               float var12 = packetBuffer.readFloat();
               float var13 = packetBuffer.readFloat();
               float var14 = packetBuffer.readFloat();
               var8 = new DataWatcher.WatchableObject(var6, var7, new Rotations(var12, var13, var14));
         }

         var4.add(var8);
      }

      return var4;
   }

   public void updateWatchedObjectsFromList(List<DataWatcher.WatchableObject> list) {
      this.lock.writeLock().lock();

      for (DataWatcher.WatchableObject var6 : list) {
         DataWatcher.WatchableObject var7 = this.watchedObjects.get(var6.getDataValueId());
         if (var7 != null) {
            var7.setObject(var6.getObject());
            this.owner.onDataWatcherUpdate(var6.getDataValueId());
         }
      }

      this.lock.writeLock().unlock();
      this.objectChanged = true;
   }

   public boolean getIsBlank() {
      return this.isBlank;
   }

   public void func_111144_e() {
      this.objectChanged = false;
   }

   static {
      dataTypes.put(Byte.class, 0);
      dataTypes.put(Short.class, 1);
      dataTypes.put(Integer.class, 2);
      dataTypes.put(Float.class, 3);
      dataTypes.put(String.class, 4);
      dataTypes.put(ItemStack.class, 5);
      dataTypes.put(BlockPos.class, 6);
      dataTypes.put(Rotations.class, 7);
   }

   public static class WatchableObject {
      private final int objectType;
      private final int dataValueId;
      private Object watchedObject;
      private boolean watched;

      public WatchableObject(int integer1, int integer2, Object object) {
         this.dataValueId = integer2;
         this.watchedObject = object;
         this.objectType = integer1;
         this.watched = true;
      }

      public int getDataValueId() {
         return this.dataValueId;
      }

      public void setObject(Object object) {
         this.watchedObject = object;
      }

      public Object getObject() {
         return this.watchedObject;
      }

      public int getObjectType() {
         return this.objectType;
      }

      public boolean isWatched() {
         return this.watched;
      }

      public void setWatched(boolean boolean1) {
         this.watched = boolean1;
      }
   }
}
