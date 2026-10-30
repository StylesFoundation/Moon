package net.minecraft.world.storage;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S34PacketMaps;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec4b;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

public class MapData extends WorldSavedData {
   public int xCenter;
   public int zCenter;
   public byte dimension;
   public byte scale;
   public byte[] colors = new byte[16384];
   public List<MapData.MapInfo> playersArrayList = Lists.newArrayList();
   private final Map<EntityPlayer, MapData.MapInfo> playersHashMap = Maps.newHashMap();
   public Map<String, Vec4b> mapDecorations = Maps.newLinkedHashMap();

   public MapData(String string) {
      super(string);
   }

   public void calculateMapCenter(double double1, double double2, int integer) {
      int var8 = 128 * (1 << integer);
      int var9 = MathHelper.floor_double((double1 + 64.0) / var8);
      int var10 = MathHelper.floor_double((double2 + 64.0) / var8);
      this.xCenter = var9 * var8 + var8 / 2 - 64;
      this.zCenter = var10 * var8 + var8 / 2 - 64;
   }

   @Override
   public void readFromNBT(NBTTagCompound nBTTagCompound) {
      this.dimension = nBTTagCompound.getByte("dimension");
      this.xCenter = nBTTagCompound.getInteger("xCenter");
      this.zCenter = nBTTagCompound.getInteger("zCenter");
      this.scale = nBTTagCompound.getByte("scale");
      this.scale = (byte)MathHelper.clamp_int(this.scale, 0, 4);
      short var5 = nBTTagCompound.getShort("width");
      short var6 = nBTTagCompound.getShort("height");
      if (var5 == 128 && var6 == 128) {
         this.colors = nBTTagCompound.getByteArray("colors");
      } else {
         byte[] var7 = nBTTagCompound.getByteArray("colors");
         this.colors = new byte[16384];
         int var8 = (128 - var5) / 2;
         int var9 = (128 - var6) / 2;

         for (int var10 = 0; var10 < var6; var10++) {
            int var11 = var10 + var9;
            if (var11 >= 0 || var11 < 128) {
               for (int var12 = 0; var12 < var5; var12++) {
                  int var13 = var12 + var8;
                  if (var13 >= 0 || var13 < 128) {
                     this.colors[var13 + var11 * 128] = var7[var12 + var10 * var5];
                  }
               }
            }
         }
      }
   }

   @Override
   public void writeToNBT(NBTTagCompound nBTTagCompound) {
      nBTTagCompound.setByte("dimension", this.dimension);
      nBTTagCompound.setInteger("xCenter", this.xCenter);
      nBTTagCompound.setInteger("zCenter", this.zCenter);
      nBTTagCompound.setByte("scale", this.scale);
      nBTTagCompound.setShort("width", (short)128);
      nBTTagCompound.setShort("height", (short)128);
      nBTTagCompound.setByteArray("colors", this.colors);
   }

   public void updateVisiblePlayers(EntityPlayer entityPlayer, ItemStack itemStack) {
      if (!this.playersHashMap.containsKey(entityPlayer)) {
         MapData.MapInfo var6 = new MapData.MapInfo(entityPlayer);
         this.playersHashMap.put(entityPlayer, var6);
         this.playersArrayList.add(var6);
      }

      if (!entityPlayer.inventory.hasItemStack(itemStack)) {
         this.mapDecorations.remove(entityPlayer.getName());
      }

      for (int var9 = 0; var9 < this.playersArrayList.size(); var9++) {
         MapData.MapInfo var7 = this.playersArrayList.get(var9);
         if (!var7.entityplayerObj.isDead && (var7.entityplayerObj.inventory.hasItemStack(itemStack) || itemStack.isOnItemFrame())) {
            if (!itemStack.isOnItemFrame() && var7.entityplayerObj.dimension == this.dimension) {
               this.updateDecorations(
                  0,
                  var7.entityplayerObj.worldObj,
                  var7.entityplayerObj.getName(),
                  var7.entityplayerObj.posX,
                  var7.entityplayerObj.posZ,
                  var7.entityplayerObj.rotationYaw
               );
            }
         } else {
            this.playersHashMap.remove(var7.entityplayerObj);
            this.playersArrayList.remove(var7);
         }
      }

      if (itemStack.isOnItemFrame()) {
         EntityItemFrame var10 = itemStack.getItemFrame();
         BlockPos var12 = var10.getHangingPosition();
         this.updateDecorations(
            1, entityPlayer.worldObj, "frame-" + var10.getEntityId(), var12.getX(), var12.getZ(), var10.facingDirection.getHorizontalIndex() * 90
         );
      }

      if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("Decorations", 9)) {
         NBTTagList var11 = itemStack.getTagCompound().getTagList("Decorations", 10);

         for (int var13 = 0; var13 < var11.tagCount(); var13++) {
            NBTTagCompound var8 = var11.getCompoundTagAt(var13);
            if (!this.mapDecorations.containsKey(var8.getString("id"))) {
               this.updateDecorations(
                  var8.getByte("type"), entityPlayer.worldObj, var8.getString("id"), var8.getDouble("x"), var8.getDouble("z"), var8.getDouble("rot")
               );
            }
         }
      }
   }

   private void updateDecorations(int integer, World world, String string, double double4, double double5, double double6) {
      int var13 = 1 << this.scale;
      float var14 = (float)(double4 - this.xCenter) / var13;
      float var15 = (float)(double5 - this.zCenter) / var13;
      byte var16 = (byte)(var14 * 2.0F + 0.5);
      byte var17 = (byte)(var15 * 2.0F + 0.5);
      byte var18 = 63;
      byte var19;
      if (var14 >= -63.0F && var15 >= -63.0F && var14 <= 63.0F && var15 <= 63.0F) {
         var19 = (byte)((double6 + (double6 < 0.0 ? -8.0 : 8.0)) * 16.0 / 360.0);
         if (this.dimension < 0) {
            int var20 = (int)(world.getWorldInfo().getWorldTime() / 10L);
            var19 = (byte)(var20 * var20 * 34187121 + var20 * 121 >> 15 & 15);
         }
      } else {
         if (Math.abs(var14) >= 320.0F || Math.abs(var15) >= 320.0F) {
            this.mapDecorations.remove(string);
            return;
         }

         integer = 6;
         var19 = 0;
         if (var14 <= -63.0F) {
            var16 = -128;
         }

         if (var15 <= -63.0F) {
            var17 = -128;
         }

         if (var14 >= 63.0F) {
            var16 = 127;
         }

         if (var15 >= 63.0F) {
            var17 = 127;
         }
      }

      this.mapDecorations.put(string, new Vec4b((byte)integer, var16, var17, var19));
   }

   public Packet getMapPacket(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      MapData.MapInfo var7 = this.playersHashMap.get(entityPlayer);
      return var7 == null ? null : var7.getPacket(itemStack);
   }

   public void updateMapData(int integer1, int integer2) {
      super.markDirty();
      Iterator var6 = this.playersArrayList.iterator();

      while (var6.hasNext()) {
         ((MapData.MapInfo)var6.next()).update(integer1, integer2);
      }
   }

   public MapData.MapInfo getMapInfo(EntityPlayer entityPlayer) {
      MapData.MapInfo var5 = this.playersHashMap.get(entityPlayer);
      if (var5 == null) {
         var5 = new MapData.MapInfo(entityPlayer);
         this.playersHashMap.put(entityPlayer, var5);
         this.playersArrayList.add(var5);
      }

      return var5;
   }

   public class MapInfo {
      public final EntityPlayer entityplayerObj;
      private boolean field_176105_d = true;
      private int minX = 0;
      private int minY = 0;
      private int maxX = 127;
      private int maxY = 127;
      private int field_176109_i;
      public int field_82569_d;

      public MapInfo(EntityPlayer entityPlayer) {
         this.entityplayerObj = entityPlayer;
      }

      public Packet getPacket(ItemStack itemStack) {
         if (this.field_176105_d) {
            this.field_176105_d = false;
            return new S34PacketMaps(
               itemStack.getMetadata(),
               MapData.this.scale,
               MapData.this.mapDecorations.values(),
               MapData.this.colors,
               this.minX,
               this.minY,
               this.maxX + 1 - this.minX,
               this.maxY + 1 - this.minY
            );
         } else {
            return this.field_176109_i++ % 5 == 0
               ? new S34PacketMaps(itemStack.getMetadata(), MapData.this.scale, MapData.this.mapDecorations.values(), MapData.this.colors, 0, 0, 0, 0)
               : null;
         }
      }

      public void update(int integer1, int integer2) {
         if (this.field_176105_d) {
            this.minX = Math.min(this.minX, integer1);
            this.minY = Math.min(this.minY, integer2);
            this.maxX = Math.max(this.maxX, integer1);
            this.maxY = Math.max(this.maxY, integer2);
         } else {
            this.field_176105_d = true;
            this.minX = integer1;
            this.minY = integer2;
            this.maxX = integer1;
            this.maxY = integer2;
         }
      }
   }
}
