package net.minecraft.client.multiplayer;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class ServerData {
   public String serverName;
   public String serverIP;
   public String c;
   public String d;
   public long pingToServer;
   public int version = 47;
   public String g = "1.8.9";
   public boolean field_78841_f;
   public String i;
   private ServerData.ServerResourceMode resourceMode = ServerData.ServerResourceMode.PROMPT;
   private String serverIcon;
   private boolean lanServer;

   public ServerData(String string1, String string2, boolean boolean3) {
      this.serverName = string1;
      this.serverIP = string2;
      this.lanServer = boolean3;
   }

   public NBTTagCompound getNBTCompound() {
      NBTTagCompound var4 = new NBTTagCompound();
      var4.setString("name", this.serverName);
      var4.setString("ip", this.serverIP);
      if (this.serverIcon != null) {
         var4.setString("icon", this.serverIcon);
      }

      if (this.resourceMode == ServerData.ServerResourceMode.ENABLED) {
         var4.setBoolean("acceptTextures", true);
      } else if (this.resourceMode == ServerData.ServerResourceMode.DISABLED) {
         var4.setBoolean("acceptTextures", false);
      }

      return var4;
   }

   public ServerData.ServerResourceMode getResourceMode() {
      return this.resourceMode;
   }

   public void setResourceMode(ServerData.ServerResourceMode serverResourceMode) {
      this.resourceMode = serverResourceMode;
   }

   public static ServerData getServerDataFromNBTCompound(NBTTagCompound nBTTagCompound) {
      ServerData var4 = new ServerData(nBTTagCompound.getString("name"), nBTTagCompound.getString("ip"), false);
      if (nBTTagCompound.hasKey("icon", 8)) {
         var4.setBase64EncodedIconData(nBTTagCompound.getString("icon"));
      }

      if (nBTTagCompound.hasKey("acceptTextures", 1)) {
         if (nBTTagCompound.getBoolean("acceptTextures")) {
            var4.setResourceMode(ServerData.ServerResourceMode.ENABLED);
         } else {
            var4.setResourceMode(ServerData.ServerResourceMode.DISABLED);
         }
      } else {
         var4.setResourceMode(ServerData.ServerResourceMode.PROMPT);
      }

      return var4;
   }

   public String getBase64EncodedIconData() {
      return this.serverIcon;
   }

   public void setBase64EncodedIconData(String string) {
      this.serverIcon = string;
   }

   public boolean isOnLAN() {
      return this.lanServer;
   }

   public void copyFrom(ServerData serverData) {
      this.serverIP = serverData.serverIP;
      this.serverName = serverData.serverName;
      this.setResourceMode(serverData.getResourceMode());
      this.serverIcon = serverData.serverIcon;
      this.lanServer = serverData.lanServer;
   }

   public enum ServerResourceMode {
      ENABLED("enabled"),
      DISABLED("disabled"),
      PROMPT("prompt");

      private final IChatComponent motd;

      ServerResourceMode(String string3) {
         this.motd = new ChatComponentTranslation("addServer.resourcePack." + string3);
      }

      public IChatComponent getMotd() {
         return this.motd;
      }

      private static ServerData.ServerResourceMode[] Method344() {
         return new ServerData.ServerResourceMode[]{ENABLED, DISABLED, PROMPT};
      }
   }
}
