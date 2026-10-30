package net.minecraft.client.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.properties.PropertyMap.Serializer;
import com.moon.MCHook;
import java.io.File;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.List;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.NonOptionArgumentSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class Main {
   public static void main(String[] arr) {
      System.setProperty("java.net.preferIPv4Stack", "true");
      OptionParser var4 = new OptionParser();
      var4.allowsUnrecognizedOptions();
      var4.accepts("demo");
      var4.accepts("fullscreen");
      var4.accepts("checkGlErrors");
      ArgumentAcceptingOptionSpec var5 = var4.accepts("server").withRequiredArg();
      ArgumentAcceptingOptionSpec var6 = var4.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(25565, new Integer[0]);
      ArgumentAcceptingOptionSpec var7 = var4.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."), new File[0]);
      ArgumentAcceptingOptionSpec var8 = var4.accepts("assetsDir").withRequiredArg().ofType(File.class);
      ArgumentAcceptingOptionSpec var9 = var4.accepts("resourcePackDir").withRequiredArg().ofType(File.class);
      ArgumentAcceptingOptionSpec var10 = var4.accepts("proxyHost").withRequiredArg();
      ArgumentAcceptingOptionSpec var11 = var4.accepts("proxyPort").withRequiredArg().defaultsTo("8080", new String[0]).ofType(Integer.class);
      ArgumentAcceptingOptionSpec var12 = var4.accepts("proxyUser").withRequiredArg();
      ArgumentAcceptingOptionSpec var13 = var4.accepts("proxyPass").withRequiredArg();
      ArgumentAcceptingOptionSpec var14 = var4.accepts("username").withRequiredArg().defaultsTo("Player" + Minecraft.getSystemTime() % 1000L, new String[0]);
      ArgumentAcceptingOptionSpec var15 = var4.accepts("uuid").withRequiredArg();
      ArgumentAcceptingOptionSpec var16 = var4.accepts("accessToken").withRequiredArg().required();
      ArgumentAcceptingOptionSpec var17 = var4.accepts("version").withRequiredArg().required();
      ArgumentAcceptingOptionSpec var18 = var4.accepts("width").withRequiredArg().ofType(Integer.class).defaultsTo(854, new Integer[0]);
      ArgumentAcceptingOptionSpec var19 = var4.accepts("height").withRequiredArg().ofType(Integer.class).defaultsTo(480, new Integer[0]);
      ArgumentAcceptingOptionSpec var20 = var4.accepts("userProperties").withRequiredArg().defaultsTo("{}", new String[0]);
      ArgumentAcceptingOptionSpec var21 = var4.accepts("profileProperties").withRequiredArg().defaultsTo("{}", new String[0]);
      ArgumentAcceptingOptionSpec var22 = var4.accepts("assetIndex").withRequiredArg();
      ArgumentAcceptingOptionSpec var23 = var4.accepts("userType").withRequiredArg().defaultsTo("legacy", new String[0]);
      NonOptionArgumentSpec var24 = var4.nonOptions();
      OptionSet var25 = var4.parse(arr);
      List var26 = var25.valuesOf(var24);
      if (!var26.isEmpty()) {
         System.out.println("Completely ignored arguments: " + var26);
      }

      String var27 = (String)var25.valueOf(var10);
      Proxy var28 = Proxy.NO_PROXY;
      if (var27 != null) {
         try {
            var28 = new Proxy(Type.SOCKS, new InetSocketAddress(var27, (Integer)var25.valueOf(var11)));
         } catch (Exception var49) {
         }
      }

      final String var29 = (String)var25.valueOf(var12);
      final String var30 = (String)var25.valueOf(var13);
      if (!var28.equals(Proxy.NO_PROXY) && isNullOrEmpty(var29) && isNullOrEmpty(var30)) {
         Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(var29, var30.toCharArray());
            }
         });
      }

      int var31 = (Integer)var25.valueOf(var18);
      int var32 = (Integer)var25.valueOf(var19);
      boolean var33 = var25.has("fullscreen");
      boolean var34 = var25.has("checkGlErrors");
      boolean var35 = var25.has("demo");
      String var36 = (String)var25.valueOf(var17);
      Gson var37 = new GsonBuilder().registerTypeAdapter(PropertyMap.class, new Serializer()).create();
      PropertyMap var38 = (PropertyMap)var37.fromJson((String)var25.valueOf(var20), PropertyMap.class);
      PropertyMap var39 = (PropertyMap)var37.fromJson((String)var25.valueOf(var21), PropertyMap.class);
      File var40 = (File)var25.valueOf(var7);
      File var41 = var25.has(var8) ? (File)var25.valueOf(var8) : new File(var40, "assets/");
      File var42 = var25.has(var9) ? (File)var25.valueOf(var9) : new File(var40, "resourcepacks/");
      String var43 = var25.has(var15) ? (String)var15.value(var25) : (String)var14.value(var25);
      String var44 = var25.has(var22) ? (String)var22.value(var25) : null;
      String var45 = (String)var25.valueOf(var5);
      Integer var46 = (Integer)var25.valueOf(var6);
      Session var47 = new Session((String)var14.value(var25), var43, (String)var16.value(var25), (String)var23.value(var25));
      GameConfiguration var48 = new GameConfiguration(
         new GameConfiguration.UserInformation(var47, var38, var39, var28),
         new GameConfiguration.DisplayInformation(var31, var32, var33, var34),
         new GameConfiguration.FolderInformation(var40, var42, var41, var44),
         new GameConfiguration.GameInformation(var35, var36),
         new GameConfiguration.ServerInformation(var45, var46)
      );
      Runtime.getRuntime().addShutdownHook(new Thread("Client Shutdown Thread") {
         @Override
         public void run() {
            Minecraft.stopIntegratedServer();
         }
      });
      Thread.currentThread().setName("Client thread");
      new MCHook(var48).run();
   }

   private static boolean isNullOrEmpty(String string) {
      return string != null && !string.isEmpty();
   }
}
