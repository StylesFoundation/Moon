package net.minecraft.tileentity;

import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StringUtils;

public class TileEntitySkull extends TileEntity {
   private int skullType;
   private int skullRotation;
   private GameProfile playerProfile = null;

   @Override
   public void writeToNBT(NBTTagCompound nBTTagCompound) {
      super.writeToNBT(nBTTagCompound);
      nBTTagCompound.setByte("SkullType", (byte)(this.skullType & 0xFF));
      nBTTagCompound.setByte("Rot", (byte)(this.skullRotation & 0xFF));
      if (this.playerProfile != null) {
         NBTTagCompound var5 = new NBTTagCompound();
         NBTUtil.writeGameProfile(var5, this.playerProfile);
         nBTTagCompound.setTag("Owner", var5);
      }
   }

   @Override
   public void readFromNBT(NBTTagCompound nBTTagCompound) {
      super.readFromNBT(nBTTagCompound);
      this.skullType = nBTTagCompound.getByte("SkullType");
      this.skullRotation = nBTTagCompound.getByte("Rot");
      if (this.skullType == 3) {
         if (nBTTagCompound.hasKey("Owner", 10)) {
            this.playerProfile = NBTUtil.readGameProfileFromNBT(nBTTagCompound.getCompoundTag("Owner"));
         } else if (nBTTagCompound.hasKey("ExtraType", 8)) {
            String var5 = nBTTagCompound.getString("ExtraType");
            if (!StringUtils.isNullOrEmpty(var5)) {
               this.playerProfile = new GameProfile(null, var5);
               this.updatePlayerProfile();
            }
         }
      }
   }

   public GameProfile getPlayerProfile() {
      return this.playerProfile;
   }

   @Override
   public Packet getDescriptionPacket() {
      NBTTagCompound var1 = new NBTTagCompound();
      this.writeToNBT(var1);
      return new S35PacketUpdateTileEntity(this.pos, 4, var1);
   }

   public void setType(int integer) {
      this.skullType = integer;
      this.playerProfile = null;
   }

   public void setPlayerProfile(GameProfile gameProfile) {
      this.skullType = 3;
      this.playerProfile = gameProfile;
      this.updatePlayerProfile();
   }

   private void updatePlayerProfile() {
      this.playerProfile = updateGameprofile(this.playerProfile);
      this.markDirty();
   }

   public static GameProfile updateGameprofile(GameProfile gameProfile) {
      if (gameProfile != null && !StringUtils.isNullOrEmpty(gameProfile.getName())) {
         if (gameProfile.isComplete() && gameProfile.getProperties().containsKey("textures")) {
            return gameProfile;
         }

         if (MinecraftServer.getServer() == null) {
            return gameProfile;
         }

         GameProfile var4 = MinecraftServer.getServer().getPlayerProfileCache().getGameProfileForUsername(gameProfile.getName());
         if (var4 == null) {
            return gameProfile;
         }

         if ((Property)Iterables.getFirst(var4.getProperties().get("textures"), null) == null) {
            var4 = MinecraftServer.getServer().getMinecraftSessionService().fillProfileProperties(var4, true);
         }

         return var4;
      } else {
         return gameProfile;
      }
   }

   public int getSkullType() {
      return this.skullType;
   }

   public int getSkullRotation() {
      return this.skullRotation;
   }

   public void setSkullRotation(int integer) {
      this.skullRotation = integer;
   }
}
