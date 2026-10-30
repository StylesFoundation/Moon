package net.minecraft.client.multiplayer;

import java.net.IDN;
import java.util.Hashtable;
import javax.naming.directory.InitialDirContext;

public class ServerAddress {
   private final String ipAddress;
   private final int serverPort;

   private ServerAddress(String string, int integer) {
      this.ipAddress = string;
      this.serverPort = integer;
   }

   public String getIP() {
      return IDN.toASCII(this.ipAddress);
   }

   public int getPort() {
      return this.serverPort;
   }

   public static ServerAddress fromString(String string) {
      if (string == null) {
         return null;
      }

      String[] var4 = string.split(":");
      if (string.startsWith("[")) {
         int var5 = string.indexOf("]");
         if (var5 > 0) {
            String var6 = string.substring(1, var5);
            String var7 = string.substring(var5 + 1).trim();
            if (var7.startsWith(":") && var7.length() > 0) {
               var7 = var7.substring(1);
               var4 = new String[]{var6, var7};
            } else {
               var4 = new String[]{var6};
            }
         }
      }

      if (var4.length > 2) {
         var4 = new String[]{string};
      }

      String var8 = var4[0];
      int var9 = var4.length > 1 ? parseIntWithDefault(var4[1], 25565) : 25565;
      if (var9 == 25565) {
         String[] var11 = getServerAddress(var8);
         var8 = var11[0];
         var9 = parseIntWithDefault(var11[1], 25565);
      }

      return new ServerAddress(var8, var9);
   }

   private static String[] getServerAddress(String string) {
      try {
         Class.forName("com.sun.jndi.dns.DnsContextFactory");
         Hashtable var4 = new Hashtable();
         var4.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
         var4.put("java.naming.provider.url", "dns:");
         var4.put("com.sun.jndi.dns.timeout.retries", "1");
         String[] var7 = new InitialDirContext(var4).getAttributes("_minecraft._tcp." + string, new String[]{"SRV"}).get("srv").get().toString().split(" ", 4);
         return new String[]{var7[3], var7[2]};
      } catch (Throwable var8) {
         return new String[]{string, Integer.toString(25565)};
      }
   }

   private static int parseIntWithDefault(String string, int integer) {
      try {
         return Integer.parseInt(string.trim());
      } catch (Exception var2) {
         return integer;
      }
   }
}
