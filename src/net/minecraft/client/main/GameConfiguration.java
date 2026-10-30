package net.minecraft.client.main;

import com.mojang.authlib.properties.PropertyMap;
import java.io.File;
import java.net.Proxy;
import net.minecraft.util.Session;

public class GameConfiguration {
   public final GameConfiguration.UserInformation userInfo;
   public final GameConfiguration.DisplayInformation displayInfo;
   public final GameConfiguration.FolderInformation folderInfo;
   public final GameConfiguration.GameInformation gameInfo;
   public final GameConfiguration.ServerInformation serverInfo;

   public GameConfiguration(
      GameConfiguration.UserInformation userInformation,
      GameConfiguration.DisplayInformation displayInformation,
      GameConfiguration.FolderInformation folderInformation,
      GameConfiguration.GameInformation gameInformation,
      GameConfiguration.ServerInformation serverInformation
   ) {
      this.userInfo = userInformation;
      this.displayInfo = displayInformation;
      this.folderInfo = folderInformation;
      this.gameInfo = gameInformation;
      this.serverInfo = serverInformation;
   }

   public static class DisplayInformation {
      public final int width;
      public final int height;
      public final boolean fullscreen;
      public final boolean checkGlErrors;

      public DisplayInformation(int integer1, int integer2, boolean boolean3, boolean boolean4) {
         this.width = integer1;
         this.height = integer2;
         this.fullscreen = boolean3;
         this.checkGlErrors = boolean4;
      }
   }

   public static class FolderInformation {
      public final File mcDataDir;
      public final File resourcePacksDir;
      public final File assetsDir;
      public final String assetIndex;

      public FolderInformation(File file1, File file2, File file3, String string) {
         this.mcDataDir = file1;
         this.resourcePacksDir = file2;
         this.assetsDir = file3;
         this.assetIndex = string;
      }
   }

   public static class GameInformation {
      public final boolean isDemo;
      public final String version;

      public GameInformation(boolean boolean1, String string) {
         this.isDemo = boolean1;
         this.version = string;
      }
   }

   public static class ServerInformation {
      public final String serverName;
      public final int serverPort;

      public ServerInformation(String string, int integer) {
         this.serverName = string;
         this.serverPort = integer;
      }
   }

   public static class UserInformation {
      public final Session session;
      public final PropertyMap userProperties;
      public final PropertyMap profileProperties;
      public final Proxy proxy;

      public UserInformation(Session session, PropertyMap propertyMap2, PropertyMap propertyMap3, Proxy proxy) {
         this.session = session;
         this.userProperties = propertyMap2;
         this.profileProperties = propertyMap3;
         this.proxy = proxy;
      }
   }
}
