package net.minecraft.client.multiplayer;

import com.google.common.collect.Lists;
import java.io.File;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerList {
   private static final Logger logger = LogManager.getLogger();
   private final Minecraft mc;
   private final List<ServerData> servers = Lists.newArrayList();

   public ServerList(Minecraft minecraft) {
      this.mc = minecraft;
      this.loadServerList();
   }

   public void loadServerList() {
      try {
         this.servers.clear();
         NBTTagCompound var4 = CompressedStreamTools.read(new File(this.mc.mcDataDir, "servers.dat"));
         if (var4 == null) {
            return;
         }

         NBTTagList var5 = var4.getTagList("servers", 10);

         for (int var6 = 0; var6 < var5.tagCount(); var6++) {
            this.servers.add(ServerData.getServerDataFromNBTCompound(var5.getCompoundTagAt(var6)));
         }
      } catch (Exception var7) {
         logger.error("Couldn't load server list", var7);
      }
   }

   public void saveServerList() {
      try {
         NBTTagList var4 = new NBTTagList();

         for (ServerData var6 : this.servers) {
            var4.appendTag(var6.getNBTCompound());
         }

         NBTTagCompound var8 = new NBTTagCompound();
         var8.setTag("servers", var4);
         CompressedStreamTools.safeWrite(var8, new File(this.mc.mcDataDir, "servers.dat"));
      } catch (Exception var7) {
         logger.error("Couldn't save server list", var7);
      }
   }

   public ServerData getServerData(int integer) {
      return this.servers.get(integer);
   }

   public void removeServerData(int integer) {
      this.servers.remove(integer);
   }

   public void addServerData(ServerData serverData) {
      this.servers.add(serverData);
   }

   public int countServers() {
      return this.servers.size();
   }

   public void swapServers(int integer1, int integer2) {
      ServerData var3 = this.getServerData(integer1);
      this.servers.set(integer1, this.getServerData(integer2));
      this.servers.set(integer2, var3);
      this.saveServerList();
   }

   public void func_147413_a(int integer, ServerData serverData) {
      this.servers.set(integer, serverData);
   }

   public static void func_147414_b(ServerData serverData) {
      ServerList var4 = new ServerList(Minecraft.getMinecraft());
      var4.loadServerList();

      for (int var5 = 0; var5 < var4.countServers(); var5++) {
         ServerData var6 = var4.getServerData(var5);
         if (var6.serverName.equals(serverData.serverName) && var6.serverIP.equals(serverData.serverIP)) {
            var4.func_147413_a(var5, serverData);
            break;
         }
      }

      var4.saveServerList();
   }
}
